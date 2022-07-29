package egovframework.mbl.com.mlt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import egovframework.com.cmm.service.FileVO;
import egovframework.mbl.com.mlt.service.EgovMultimediaService;
import egovframework.mbl.com.mlt.service.Multimedia;
import egovframework.mbl.com.mlt.service.MultimediaFileInfo;
import egovframework.mbl.com.mlt.service.MultimediaVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

/**
 * 개요
 * - 멀티미디어 제어에 대한 Service Interface를 구현한다.
 *
 * 상세내용
 * - 멀티미디어에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 멀티미디어에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.23  정홍규          최초 생성
 *   2017.02.17  김준호          시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
 *   2017.03.02     조성원          시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		        	
 * 
 * </pre>
 */

@Service("MultimediaService")
public class EgovMultimediaServiceImpl extends EgovAbstractServiceImpl implements
        EgovMultimediaService {

    /**
     * MultimediaDAO
     */
    @Resource(name = "MultimediaDAO")
    private MultimediaDAO multimediaDAO;

    /** ID Generation */
    @Resource(name = "egovMultimediaIdGnrService")
    private EgovIdGnrService idgenService;
    //2017.02.17     김준호          시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovMultimediaServiceImpl.class);         

    /**
     * 멀티미디어 정보 삭제 관련 비즈니스 구현 메서드
     * @param multimedia
     * @throws Exception
     */
    @Override
	public void deleteMultimedia(Multimedia multimedia) throws Exception {
        multimediaDAO.deleteMultimedia(multimedia);
    }

    /**
     * 멀티미디어 정보 등록 관련 비즈니스 구현 메서드
     * @param multimedia
     * @throws Exception
     */
    @Override
	public void insertMultimedia(Multimedia multimedia) throws Exception {

    	egovLogger.debug(multimedia.toString());

        int sn = idgenService.getNextIntegerId();

        multimedia.setSn(sn);

        multimediaDAO.insertMultimedia(multimedia);
    }

    /**
     * 멀티미디어 정보 상세 조회 관련 비즈니스 구현 메서드
     * @param multimedia
     * @return Multimedia 멀티미디어 정보
     * @throws Exception
     */
    @Override
	public Multimedia selectMultimedia(Multimedia multimedia) throws Exception {
        Multimedia ret =
            multimediaDAO.selectMultimedia(multimedia);
        return ret;
    }

    /**
     * 멀티미디어 목록을 조회 관련 비즈니스 구현 메서드
     * @param searchVO
     * @return List 멀티미디어 목록
     * @throws Exception
     */
    @Override
	public List<?> selectMultimediaList(MultimediaVO searchVO) throws Exception {
        return multimediaDAO.selectMultimediaList(searchVO);
    }

    /**
     * 멀티미디어 정보 수정 관련 비즈니스 구현 메서드
     * @param multimedia
     * @throws Exception
     */
    @Override
	public void updateMultimedia(Multimedia multimedia) throws Exception {
        multimediaDAO.updateMultimedia(multimedia);
    }

    /**
     * 멀티미디어 정보의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     * @throws Exception
     */
    @Override
	public int selectMultimediaListTotCnt(MultimediaVO searchVO)
            throws Exception {
        return multimediaDAO.selectMultimediaListTotCnt(searchVO);
    }

    /**
     * 멀티미디어 파일을 상대 경로에 저장한다.
     * @param fileList
     * @return void
     * @throws Exception
     */
    @Override
	public void copyFileToRelativePath(List<FileVO> fileList) throws Exception {

        String currPath = this.getClass().getResource("").getPath();
        String absolutePath = "";

        absolutePath =
            currPath.substring(0, currPath.lastIndexOf("WEB-INF"))
                + "multimedia";

        // 디렉토리 생성
        if (!(new File(absolutePath)).isDirectory()) {
        	//(new File(absolutePath)).mkdirs();
        	//2017.02.17     김준호          시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
        	if((new File(absolutePath)).mkdirs()){
        		LOGGER.debug("[file.mkdirs] absolutePath : Creation Success ");
        	}else{
        	    LOGGER.error("[file.mkdirs] absolutePath : Creation Fail ");
        	}            
        }

        for (int i = 0; i < fileList.size(); i++) {
            File sFile =
                new File(fileList.get(i).getFileStreCours(), fileList.get(i)
                    .getStreFileNm());
            File dFile =
                new File(absolutePath, fileList.get(i).getStreFileNm() + "."
                    + fileList.get(i).getFileExtsn());

            int fSize = (int) sFile.length();

            // 파일 복사
            if (fSize > 0) {

                FileInputStream fis = null;
                FileOutputStream fos = null;

                try {
                    fis = new FileInputStream(sFile);
                    fos = new FileOutputStream(dFile);

                    FileCopyUtils.copy(fis, fos);
                //2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
                } catch(MalformedURLException e) {
                	LOGGER.error("["+e.getClass()+"] Try/Catch...MalformedURLException : " + e.getMessage());
        		} catch(IOException e) {
        			LOGGER.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
        		} catch (Exception e) {
        			LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
        	    } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e){
                        	LOGGER.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
                        } catch (Exception e) {
                        	LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e){
                        	LOGGER.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
                        } catch (Exception e) {
                        	LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 파일별 지원 브라우저 정보를 조회한다.
     * @return List<MultimediaFileInfo>
     * @throws Exception
     */
    @Override
	public List<MultimediaFileInfo> getMultimediaFileInfoFromXML()
            throws Exception {
        String XMLPath =
            this.getClass().getResource("").getPath()
                + "MultimediaFileInfo.xml";
        List<MultimediaFileInfo> multimediaFileInfoList = new ArrayList<MultimediaFileInfo>();

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(XMLPath));

        Element rootElement = document.getRootElement();

        List<Element> multimediaFileInfoElements = rootElement.getChildren();

        for (Element multimediaFileInfoElement : multimediaFileInfoElements) {

            MultimediaFileInfo multimediaFileInfo = new MultimediaFileInfo();

            // 파일 구분
            multimediaFileInfo.setClassification(multimediaFileInfoElement
                .getName());

            // 파일 확장자
            multimediaFileInfo.setExtension(multimediaFileInfoElement
                .getAttributeValue("extension"));

            // 파일 타입
            multimediaFileInfo.setType(multimediaFileInfoElement
                .getAttributeValue("type"));

            // 지원 브라우저
            List<Element> elements = multimediaFileInfoElement.getChildren();
            List<String> browserList = new ArrayList<String>();

            for (int j = 0; j < elements.size(); j++) {
                if (elements.get(j).getName().equalsIgnoreCase("browser")) {
                    browserList.add(elements.get(j).getText());
                }
            }
            multimediaFileInfo.setBrowserList(browserList);

            multimediaFileInfoList.add(multimediaFileInfo);
        }

        return multimediaFileInfoList;
    }

    /**
     * 지원 브라우저 정보를 조회한다.
     * @param String 멀티미디어명
     * @param List <String> 확장자
     * @return String
     * @throws Exception
     */
    @Override
	@SuppressWarnings("unused")
	public String getBrowserInfoFromXML(String mltmdNm, List<String> extList)
            throws Exception {
        String browserInfo = "";
        String mltmdNmType = "";

        // 멀티미디어 파일 구분 명 변경 (XML 태그명과 일치)
        if (mltmdNm.equalsIgnoreCase("동영상")) {
        	mltmdNmType = "Video";
        } else if (mltmdNm.equalsIgnoreCase("음악")) {
        	mltmdNmType = "Audio";
        }

        String XMLPath =
            this.getClass().getResource("").getPath()
                + "MultimediaFileInfo.xml";
        List<MultimediaFileInfo> multimediaFileInfoList = new ArrayList<MultimediaFileInfo>();

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(XMLPath));

        Element rootElement = document.getRootElement();

        List<Element> multimediaFileInfoElements = rootElement.getChildren();

        for (Element multimediaFileInfoElement : multimediaFileInfoElements) {

            for (int i = 0; i < extList.size(); i++) {

                // 파일 구분 비교
                if (multimediaFileInfoElement.getName().equalsIgnoreCase(mltmdNmType)) {
                    // 파일 확장자 비교
                    if (multimediaFileInfoElement
                        .getAttributeValue("extension").equalsIgnoreCase(extList.get(i))) {
                        // 지원 브라우저 입력
                        List<Element> elements =
                            multimediaFileInfoElement.getChildren();

                        for (int j = 0; j < elements.size(); j++) {
                            if (elements.get(j).getName().equalsIgnoreCase("browser")) {
                                String elementsText = elements.get(j).getText();
                                if (browserInfo.indexOf(elementsText) < 0) {
                                    browserInfo += elementsText + " ";
                                }
                            }
                        }
                    }
                }
            }
        }

        return browserInfo;
    }
}
