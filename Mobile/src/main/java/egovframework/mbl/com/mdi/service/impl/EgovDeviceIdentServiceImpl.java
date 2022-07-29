package egovframework.mbl.com.mdi.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.mbl.com.mdi.service.DeviceIdent;
import egovframework.mbl.com.mdi.service.DeviceIdentVO;
import egovframework.mbl.com.mdi.service.EgovDeviceIdentService;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 개요
 * - 모바일기기식별에 대한 Service Interface를 구현한다.
 *
 * 상세내용
 * - 모바일기기 식별정보에 대한 등록, 수정, 삭제, 조회 기능과 User-Agent값 조회, 모바일기기 정보 추출 기능을 제공한다.
 * - 모바일기기 식별정보에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.19  정홍규          최초 생성
 *
 * </pre>
 */

@Service("DeviceIdentService")
public class EgovDeviceIdentServiceImpl extends EgovAbstractServiceImpl implements EgovDeviceIdentService {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovDeviceIdentServiceImpl.class);

	/**
	 * DeviceIdentDAO
	 */
	@Resource(name = "DeviceIdentDAO")
	private DeviceIdentDAO deviceIdentDAO;

	/** ID Generation */
	@Resource(name = "egovDeviceIdentIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 조회된 모바일 기기 정보를 삭제한다.
	 * @param deviceIdent
	 * @throws Exception
	 */
	@Override
	public void deleteDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		deviceIdentDAO.deleteDeviceIdent(deviceIdent);
	}

	/**
	 * 모바일 기기 정보를 등록한다.
	 * @param deviceIdent
	 * @throws Exception
	 */
	@Override
	public void insertDeviceIdent(DeviceIdent deviceIdent) throws Exception {

		LOGGER.debug(deviceIdent.toString());

		int sn = idgenService.getNextIntegerId();

		deviceIdent.setSn(sn);
		deviceIdentDAO.insertDeviceIdent(deviceIdent);
	}

	/**
	 * 모바일 기기의 상세정보를 조회한다.
	 * @param deviceIdent
	 * @return deviceIdent 모바일 기기 정보
	 * @throws Exception
	 */
	@Override
	public DeviceIdent selectDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		DeviceIdent ret = deviceIdentDAO.selectDeviceIdent(deviceIdent);
		return ret;
	}

	/**
	 * 모바일 기기 정보 목록을 조회한다.
	 * @param searchVO
	 * @return List 모바일기기 정보 리스트
	 * @throws Exception
	 */
	@Override
	public List<?> selectDeviceIdentList(DeviceIdentVO searchVO) throws Exception {
		return deviceIdentDAO.selectDeviceIdentList(searchVO);
	}

	/**
	 * 조회된 모바일 기기 정보를 수정한다.
	 * @param deviceIdent
	 * @throws Exception
	 */
	@Override
	public void updateDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		deviceIdentDAO.updateDeviceIdent(deviceIdent);
	}

	/**
	 * 모바일 기기 정보의 총 갯수를 조회한다.
	 * @param searchVO
	 * @return int
	 */
	@Override
	public int selectDeviceIdentListTotCnt(DeviceIdentVO searchVO) throws Exception {
		return deviceIdentDAO.selectDeviceIdentListTotCnt(searchVO);
	}

	/**
	 * 모바일 기기 정보를 XML 파일로 생성한다.
	 * @throws Exception
	 */
	@Override
	public void createDeviceIndentListToXML() throws Exception {

		// DOM 파서 생성
		Element rootElement = new Element("DeviceIdents");
		Document document = new Document(rootElement);

		// 모바일 기기 정보 목록 읽기
		DeviceIdentVO searchVO = new DeviceIdentVO();
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(1000);
		List<?> deviceIdentList = selectDeviceIdentList(searchVO);

		// 모바일 기기 정보를 Document 변환
		for (int i = 0; i < deviceIdentList.size(); i++) {
			Element deviceIdentElement = new Element("DeviceIdent");

			EgovMap egovMap = (EgovMap) deviceIdentList.get(i);

			Element uagentInfoElement = new Element("UserAgent");
			uagentInfoElement.setText((String) egovMap.get("uagentInfo"));
			deviceIdentElement.addContent(uagentInfoElement);

			Element browserElement = new Element("Browser");
			browserElement.setText((String) egovMap.get("browserNm"));
			deviceIdentElement.addContent(browserElement);

			Element osElement = new Element("OS");
			osElement.setText((String) egovMap.get("osNm"));
			deviceIdentElement.addContent(osElement);

			rootElement.addContent(deviceIdentElement);
		}

		// 파일로 저장하기
		XMLOutputter output = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		output.setFormat(format);

		String xmlPath = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MDIConfPath"), "deviceIdentXMLPath");

		//KISA 보안 점검 결과 반영
		FileOutputStream out = null;
		OutputStreamWriter writer = null;

		try {
			out = new FileOutputStream(xmlPath);
			writer = new OutputStreamWriter(out, "utf-8");
			output.output(document, writer);

		} catch (FileNotFoundException e) {
			LOGGER.debug("Fail to createDeviceIndentListToXML : FileNotFoundException");
		} catch (UnsupportedEncodingException e) {

			LOGGER.debug("Fail to createDeviceIndentListToXML : UnsupportedEncodingException");
		} catch (IOException e) {

			LOGGER.debug("Fail to createDeviceIndentListToXML : IOException");
		} finally {//2011.11.23  보안점검 결과 반영
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}

		}

	}

	/**
	 * user-Agent 값에서 모바일기기 정보를 추출한다.
	 * @param userAgent
	 * @return DeviceIdent 모바일기기 정보
	 * @throws Exception
	 */
	@Override
	public DeviceIdent getDeviceIdentFromXML(String userAgent) throws Exception {

		DeviceIdent deviceIdent = null;

		SAXBuilder builder = new SAXBuilder();

		String xmlPath = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.MDIConfPath"), "deviceIdentXMLPath");

		if (!((new File(xmlPath)).isFile())) {
			return null;
		}

		Document document = builder.build(new File(xmlPath));
		Element rootElement = document.getRootElement();
		List<Element> deviceIdentElements = rootElement.getChildren();

		for (Element deviceIdentElement : deviceIdentElements) {

			List<Element> elements = deviceIdentElement.getChildren();
			Element uagentInfoElement = elements.get(0);
			Element browserElement = elements.get(1);
			Element osElement = elements.get(2);
			if (uagentInfoElement.getText().equalsIgnoreCase(userAgent) && uagentInfoElement.getName().equalsIgnoreCase("UserAgent")
					&& browserElement.getName().equalsIgnoreCase("Browser") && osElement.getName().equalsIgnoreCase("OS")) {
				deviceIdent = new DeviceIdent();
				deviceIdent.setUagentInfo(uagentInfoElement.getText());
				deviceIdent.setBrowserNm(browserElement.getText());
				deviceIdent.setOsNm(osElement.getText());

				break;
			}
		}

		return deviceIdent;
	}
}
