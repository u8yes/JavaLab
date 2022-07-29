package egovframework.mbl.com.mms.service.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.dkitec.mose.client.MessageStatus;
import com.dkitec.mose.client.MessageStatusParser;
import com.dkitec.mose.client.MoseClient;
import com.dkitec.mose.client.SoapFault;
import com.dkitec.mose.client.SubmitRequest;
import com.dkitec.mose.client.SubmitResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.mbl.com.mms.service.AttachFile;
import egovframework.mbl.com.mms.service.AttachFileVO;
import egovframework.mbl.com.mms.service.EgovMmsService;
import egovframework.mbl.com.mms.service.MmsTransInfo;
import egovframework.mbl.com.mms.service.MmsTransInfoVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;

/**
 * 개요
 * - MMS서비스연계에 대한 Service Interface를 구현한다.
 * 
 * 상세내용
 * - MMS 전송, MMS 전송 결과 등록, 수정, 조회 기능과 MMS 첨부파일에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - MMS 첨부파일에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 조재만
 * @since 2011.08.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.26  조재만          최초 생성
 *   2017.03.02  조성원          시큐어코딩(ES)-Null Pointer 역참조[CWE-476]
 *   2017.03.06  조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
 *
 * </pre>
 */
@Service("EgovMmsService")
public class EgovMmsServiceImpl extends EgovAbstractServiceImpl implements EgovMmsService {

	/** MmsDAO */
	@Resource(name="mmsDAO")
	private MmsDAO mmsDAO;

	/** ID Generation */    
	@Resource(name="egovAttachFileIdGnrService")
	private EgovIdGnrService attachFileIdgenService;

	@Resource(name="egovMmsIdGnrService")
	private EgovIdGnrService mmsIdgenService;

	/** EgovPropertyService */
	@Resource(name="propertiesService")
	protected EgovPropertyService propertyService;

	/**
	 * MMS 전송 결과 목록을 조회한다.
	 * @param mmsTransInfo
	 * @return Map<String, Object> 전송 결과 목록 리스트 
	 * @throws Exception
	 */
	public Map<String, Object> selectMmsTransmissionResultList(MmsTransInfoVO mmsTransInfoVO) throws Exception {
		List<AttachFileVO> result = mmsDAO.selectMmsTransmissionResultList(mmsTransInfoVO);

		int cnt = mmsDAO.selectMmsTransmissionResultListCnt(mmsTransInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	/**
	 * MMS 전송정보를 등록한다.  
	 * @param mmsTransInfo
	 * @throws Exception
	 */
	public void insertMmsTransmissionResult(MmsTransInfo mmsTransInfo) throws Exception {
		int sn = mmsIdgenService.getNextIntegerId();

		mmsTransInfo.setSn(sn);
		mmsDAO.insertMmsTransmissionResult(mmsTransInfo);
	}

	/**
	 * MMS 전송결과를 DB에 반영한다.
	 * @param mmsTransInfo
	 * @throws Exception
	 */
	public void updateMmsTransmissionResult(MmsTransInfo mmsTransInfo) throws Exception {
		mmsDAO.updateMmsTransmissionResult(mmsTransInfo);
	}

	/**
	 * MMS를 전송한다.
	 * @param mmsTransInfo
	 * @return Map<String, Object> MMS 요청 결과, 메시지 ID
	 * @throws Exception
	 */
	public Map<String, Object> sendMms(MmsTransInfo mmsTransInfo) throws Exception {
		// 수정금지
		String soapUrl = "/";

		// 10 second  - 연결시도 10초후 연결되지 않으면 타임아웃
		int timeOut = 10000;
		// Service Port : 13000, Test Port : 14000
		int port = Integer.parseInt(EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsPort"));
		// Host IP
		String host = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsHost");
		// ID
		String connID = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsId");
		// Password
		String connPW = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsPassword");
		// 전송한 메시지 ID
		String messageId = "";

		Map<String, Object> map = new HashMap<String, Object>();

		DataInputStream in = null;
		DataOutputStream out = null;
		DataInputStream in2 = null;
		DataOutputStream out2 = null;

		try {
			// 파일첨부 변수
			String imgPath = "";        // JPEG(이미지)
			String sisPath = "";        // SIS(아이콘,gif)
			String mmfPath = "";        // 벨소리(mmf)
			String k3gPath = "";        // 동영상(KT,LGT)
			String skmPath = "";        // 동영상(SKT)

			// 첨부파일 사용 설정
			String fchk = "0";            // 0이면 파일 사용 안함
			String mType1 = "0";          // 메시지타입 : JPG
			String mType2 = "0";          // 메시지타입 : SIS (GIF:에니메이션)
			String mType3 = "0";          // 메시지타입 : MMF (벨소리,MAF)
			String mType4 = "0";          // 메세지타입 : K3G,SKM (동영상)

			if (mmsTransInfo.getAttachFile().getAtchFileNm() != null && !mmsTransInfo.getAttachFile().getAtchFileNm().equals("")) {
				fchk = "1";

				if (mmsTransInfo.getAttachFile().getAtchFileType().toUpperCase().equals("JPG")) {
					mType1 = "1";
				} else if (mmsTransInfo.getAttachFile().getAtchFileType().toUpperCase().equals("SIS")) {
					mType2 = "1";
				} else if (mmsTransInfo.getAttachFile().getAtchFileType().toUpperCase().equals("MMF")) {
					mType3 = "1";
				} else if (mmsTransInfo.getAttachFile().getAtchFileType().toUpperCase().equals("K3G") || mmsTransInfo.getAttachFile().getAtchFileType().toUpperCase().equals("SKM")) {
					mType4 = "1";
				}
			}

			byte[] buff = new byte[1024];
			int size;

			// 첨부파일 설정
			if (fchk.equals("1")) {
				// 선택한 첨부파일의 파일 경로와 파일명이 존재할 때
				if (!("".equals(mmsTransInfo.getAttachFile().getAtchFilePath())) && !("".equals(mmsTransInfo.getAttachFile().getStreFileNm()))
						&& mmsTransInfo.getAttachFile().getAtchFilePath() != null && mmsTransInfo.getAttachFile().getStreFileNm() != null) {
					// 실제파일 전체경로
					if (mType1.equals("1")) {
						imgPath = mmsTransInfo.getAttachFile().getAtchFilePath() +  mmsTransInfo.getAttachFile().getStreFileNm();
						in = new DataInputStream(new FileInputStream(imgPath));

						if (!("".equals(mmsTransInfo.getAttachFile().getAtchFileType())) && mmsTransInfo.getAttachFile().getAtchFileType() != null) {
							imgPath += "." + mmsTransInfo.getAttachFile().getAtchFileType();
						}

						out = new DataOutputStream(new FileOutputStream(imgPath));
						imgPath = imgPath.replace("/", "\\\\");

						while ((size = in.read(buff)) > -1) {
							out.write(buff, 0, size);
						}

						out.close();
						in.close();
					}
					if (mType2.equals("1")) { 
						sisPath = mmsTransInfo.getAttachFile().getAtchFilePath() +  mmsTransInfo.getAttachFile().getStreFileNm();
						in = new DataInputStream(new FileInputStream(sisPath));

						if (!("".equals(mmsTransInfo.getAttachFile().getAtchFileType())) && mmsTransInfo.getAttachFile().getAtchFileType() != null) {
							sisPath += "." + mmsTransInfo.getAttachFile().getAtchFileType();
						}

						out = new DataOutputStream(new FileOutputStream(sisPath));
						sisPath = sisPath.replace("/", "\\\\");

						while ((size = in.read(buff)) > -1) {
							out.write(buff, 0, size);
						}

						out.close();
						in.close();
					}
					if (mType3.equals("1")) { 
						mmfPath = mmsTransInfo.getAttachFile().getAtchFilePath() +  mmsTransInfo.getAttachFile().getStreFileNm();
						in = new DataInputStream(new FileInputStream(mmfPath));

						if (!("".equals(mmsTransInfo.getAttachFile().getAtchFileType())) && mmsTransInfo.getAttachFile().getAtchFileType() != null) {
							mmfPath += "." + mmsTransInfo.getAttachFile().getAtchFileType();
						}

						out = new DataOutputStream(new FileOutputStream(mmfPath));
						mmfPath = mmfPath.replace("/", "\\\\");

						while ((size = in.read(buff)) > -1) {
							out.write(buff, 0, size);
						}

						out.close();
						in.close();
					}

					//동영상 처리
					/**********************************
					 * 중요 !
					 * 동영상 첨부시 반드시 2가지 파일 요청!
					 * SKM, K3G  필수 임
					 ***********************************/
					if(mType4.equals("1")){
						k3gPath = mmsTransInfo.getAttachFile().getAtchFilePath() + mmsTransInfo.getAttachFile().getStreFileNm();
						in = new DataInputStream(new FileInputStream(k3gPath));
						k3gPath += ".k3g";
						out = new DataOutputStream(new FileOutputStream(k3gPath));
						k3gPath = k3gPath.replace("/", "\\\\");

						while ((size = in.read(buff)) > -1) {
							out.write(buff, 0, size);
						}

						out.close();
						in.close();

						skmPath = mmsTransInfo.getAttachFile().getAtchFilePath() + mmsTransInfo.getAttachFile().getStreFileNm();
						in2 = new DataInputStream(new FileInputStream(skmPath));
						skmPath += ".skm";
						out2 = new DataOutputStream(new FileOutputStream(skmPath));
						skmPath = skmPath.replace("/", "\\\\");

						while ((size = in2.read(buff)) > -1) {
							out2.write(buff, 0, size);
						}

						out2.close();
						in2.close();
					}
				}
			}

			MoseClient client = MoseClient.createMoseClient(host, port, soapUrl, connID, connPW);

			// 생성된 Client를 통해 전송 객체(SubmitRequest)를 생성한다.
			SubmitRequest request = MoseClient.createSubmitRequest();

			request.setSenderPhoneNo(mmsTransInfo.getTrnsmisNo());
			request.setReceiverList("", mmsTransInfo.getRecptnNo());
			request.setSubject(mmsTransInfo.getMssageSj());
			request.setMsgText(mmsTransInfo.getMssageCn());

			// 첨부파일
			if (fchk.equals("1")) {
				if (mType1.equals("1")) request.setAttachFiles(imgPath);
				if (mType2.equals("1")) request.setAttachFiles(sisPath);
				if (mType3.equals("1")) request.setAttachFiles(mmfPath);
				if (mType4.equals("1")) {
					request.setAttachFiles(k3gPath);
					request.setAttachFiles(skmPath);
				}
			}

			/**
			 * 생성된 client 객체를 이용하여 전송 객체(SubmitRequest)를 전송한다.
			 * 반환값으로 전송 요청 결과 객체(SubmitResponse)를 받는다.
			 */
			SubmitResponse response = client.send(request, timeOut);

			// 전송요청 성공 여부
			if(response.isSuccess()) { 
				messageId = response.getMessageId();     // 전송요청한 메시지 ID (전송 결과 요청시  필요)
				String status = response.getStatus();    // 전송요청 상태 정보(200일 경우 성공)

				egovLogger.info("ok 메시지 ID : [{}]", messageId);
				egovLogger.info("ok 상태 정보 : [{}]", status);
				map.put("messageId", messageId);
				map.put("status", status);
			} else {
				SoapFault soapFault = response.getSoapFault();          // 전송 실패 정보
				String faultCode = soapFault.getFaultCode();            // 전송 실패 코드
				String faultString = soapFault.getFaultString();        // 전송 실패 설명 정보
				String faultActor = soapFault.getFaultActor();          // 전송 실패 액터 정보

				egovLogger.info("no 실패 코드 : [{}]", faultCode);
				egovLogger.info("no 실패 설명 정보 : [{}]", faultString);
				egovLogger.info("no 실패 액터 정보 : [{}]", faultActor);
			}

			// 전송요청 성공 여부
			if(messageId != null && !messageId.equals("")) { 
				map.put("requestResult", true);
				map.put("messageId", messageId);

				return map;
			}
		} catch (IllegalArgumentException ie) {
			egovLogger.error("IllegalArgumentException has occurred", ie);
		} catch (Exception e) {
			egovLogger.error("Exception has occurred", e);
		} finally {
			//2017.03.02 조성원 시큐어코딩(ES)-Null Pointer 역참조[CWE-476]
			if(null != out){
				try {
					out.close();
				} catch (IOException e){
					egovLogger.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
	            } catch (Exception e) {
	            	egovLogger.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
				}
			}
			
			if(null != out2){
				try {
					out2.close();
				} catch (IOException e){
					egovLogger.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
	            } catch (Exception e) {
	            	egovLogger.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
				}
			}
			
			if(null != in){
				try {
					in.close();
				} catch (IOException e){
					egovLogger.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
	            } catch (Exception e) {
	            	egovLogger.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
				}
			}
			
			if(null != in2){
				try {
					in2.close();
				} catch (IOException e){
					egovLogger.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
	            } catch (Exception e) {
	            	egovLogger.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
				}
			}
		}

		map.put("requestResult", false);
		map.put("messageId", "");

		return map;
	}

	/**
	 * 전송한 MMS의 전송결과를 받는다. (Push 방식)
	 *
	 * @param request
	 * @return Map<String, Object> MMS 전송결과
	 * @throws Exception
	 */
	public Map<String, Object> receiveMmsTransmissionResult(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			MessageStatusParser parser = new MessageStatusParser();
			java.io.BufferedReader br = request.getReader();

			String responseTemp;
			String responseString = "";
			responseTemp = br.readLine();

			while (responseTemp != null ){
				responseTemp = br.readLine();

				//DR메시지 할당
				if(responseTemp != null){
					responseString += responseTemp;
				}
			}

			MessageStatus[] msgList = parser.parse(new java.io.StringReader(responseString));

			for(int i = 0; i < msgList.length; i++) {
				String messageId = msgList[i].getMessageId();           // 메시지 ID
				String status = msgList[i].getStatus();                 // 전송 상태
				String telco = msgList[i].getTelco();                   // 통신사
				String receiveNo = msgList[i].getPhoneNo();             // 수신자 번호
				String recipient = msgList[i].getRecipient();           // 수신자명
				String forwardDate = msgList[i].getForwardDate();       // 발신 날짜
				String deliveryDate = msgList[i].getDeliveryDate();     // 수신 날짜
				String ktfStatus = msgList[i].getKtfStatus();           // KTF 상태
				String lgtStatus = msgList[i].getLgtStatus();           // LGT 상태
				String sktStatus = msgList[i].getSktStatus();           // SKT 상태

				egovLogger.info("MESSAGE_ID= {}",messageId);
				egovLogger.info("STATUS= {}",status);
				egovLogger.info("TELCO= {}",telco);
				egovLogger.info("RECEIVE_NO= {}",receiveNo);
				egovLogger.info("RECIPIENT= {}",recipient);
				egovLogger.info("FORWARD_DATE= {}",forwardDate);
				egovLogger.info("DELIVERY_DATE= {}",deliveryDate);
				egovLogger.info("KTF_STATUS= {}",ktfStatus);
				egovLogger.info("LGT_STATUS= {}",lgtStatus);
				egovLogger.info("SKT_STATUS= {}",sktStatus);

				map.put("messageId", messageId);
				map.put("status", status);
				map.put("telco", telco);
				map.put("receiveNo", receiveNo);
				map.put("recipient", recipient);
				map.put("forwardDate", forwardDate);
				map.put("deliveryDate", deliveryDate);
				map.put("ktfStatus", ktfStatus);
				map.put("lgtStatus", lgtStatus);
				map.put("sktStatus", sktStatus);
			}
		//2017.03.03  조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
		} catch(IOException e) {
			egovLogger.error("[IOException] Try/Catch...connection close Runing : "+ e.getMessage());
		} catch (Exception e) {
			egovLogger.error("["+e.getClass()+"] Try/Catch...Runing : " + e.getMessage());
		}

		return map;
	}

	/**
	 * 전송한 MMS의 전송결과를 요청한다.
	 *
	 * @param messageId
	 * @return Map<String, Object> MMS 전송결과
	 * @throws Exception
	 */
	public Map<String, Object> getMmsTransmissionResult(String messageId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 수정금지
		String soapUrl = "/";

		// 10 second  - 연결시도 10초후 연결되지 않으면 타임아웃
		int timeOut = 10000;
		// Service Port : 13000, Test Port : 14000
		int port = Integer.parseInt(EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsPort"));
		// Host IP
		String host = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsHost");
		// ID
		String connID = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsId");
		// Password
		String connPW = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MMSConfPath"), "mmsPassword");

		try {
			/**
			 * Client 객체를 생성한다.             
			 * 객체 생성시 연동 및 인증 정보(host, port, soapUrl, connID, connPW)를 인자로 갖는다 
			 */
			MoseClient client = MoseClient.createMoseClient(host, port, soapUrl, connID, connPW);

			/**
			 * 전송결과 정보를 요청할 메시지의 아이디를 인자로 갖는다.
			 * 전송 결과는 MessageStatus을 통해 받는다.
			 */
			MessageStatus[] msgList = client.requestResult(messageId, timeOut);

			for (int i = 0; i < msgList.length; i++) {
				String status = msgList[i].getStatus();                 // 전송 상태
				String telco = msgList[i].getTelco();                   // 통신사
				String receiveNo = msgList[i].getPhoneNo();             // 수신자 번호
				String receiveName = msgList[i].getRecipient();         // 수신자명
				String deliveryDate = msgList[i].getDeliveryDate();     // 수신 날짜
				String ktfStatus = msgList[i].getKtfStatus();           // KTF 상태
				String lgtStatus = msgList[i].getLgtStatus();           // LGT 상태
				String sktStatus = msgList[i].getSktStatus();           // SKT 상태

				egovLogger.info("STATUS= {}",status);
				egovLogger.info("TELCO= {}",telco);
				egovLogger.info("RECEIVE_NO= {}",receiveNo);
				egovLogger.info("RECIPIENT= {}",receiveName);
				egovLogger.info("DELIVERY_DATE= {}",deliveryDate);
				egovLogger.info("KTF_STATUS= {}",ktfStatus);
				egovLogger.info("LGT_STATUS= {}",lgtStatus);
				egovLogger.info("SKT_STATUS= {}",sktStatus);

				map.put("status", status);
				map.put("telco", telco);
				map.put("receiveNo", receiveNo);
				map.put("recipient", receiveName);
				map.put("deliveryDate", deliveryDate);
				map.put("ktfStatus", ktfStatus);
				map.put("lgtStatus", lgtStatus);
				map.put("sktStatus", sktStatus);
			}
			
		} catch(IllegalArgumentException e) {
			//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			egovLogger.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
		} catch (Exception e) {
			//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			egovLogger.error("["+ e.getClass() +"] Try/Catch...Exception : " + e.getMessage());
		}

		return map;
	}

	/**
	 * 첨부파일 목록을 조회한다.
	 * @param attachFileVO
	 * @return Map<String, Object> 첨부파일 조회결과 리스트, 조회건수
	 * @throws Exception
	 */
	public Map<String, Object> selectAttachFileList(AttachFileVO attachFileVO) throws Exception {
		List<AttachFileVO> result = mmsDAO.selectAttachFileList(attachFileVO);

		int cnt = mmsDAO.selectAttachFileInfoListCnt(attachFileVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	/**
	 * 첨부파일의 정보를 상세조회한다.
	 *
	 * @param attachFileVO
	 * @return AttachFile 첨부파일 정보
	 * @throws Exception
	 */
	public AttachFile selectAttachFile(AttachFileVO attachFileVO) throws Exception {
		return mmsDAO.selectAttachFile(attachFileVO);
	}

	/**
	 * 첨부파일의 정보를 DB에 등록한다.
	 * @param attachFile
	 * @throws Exception
	 */
	public void insertAttachFile(AttachFile attachFile) throws Exception {
		int sn = attachFileIdgenService.getNextIntegerId();

		attachFile.setSn(sn);
		mmsDAO.insertAttachFile(attachFile);
	}

	/**
	 * 첨부파일의 정보를 수정한다.
	 * @param attachFile
	 * @throws Exception
	 */
	public void updateAttachFile(AttachFile attachFile) throws Exception {
		mmsDAO.updateAttachFile(attachFile);
	}

	/**
	 * 첨부파일의 정보를 삭제한다.
	 * @param attachFile
	 * @throws Exception
	 */
	public void deleteAttachFile(AttachFile attachFile) throws Exception {
		mmsDAO.deleteAttachFile(attachFile);
	}
}