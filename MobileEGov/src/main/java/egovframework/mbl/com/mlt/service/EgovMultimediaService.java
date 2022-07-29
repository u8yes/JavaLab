package egovframework.mbl.com.mlt.service;

import java.util.List;

import egovframework.com.cmm.service.FileVO;

/**
 * 개요
 * - 멀티미디어 제어에 대한 Service Interface를 정의한다.
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
 *
 * </pre>
 */

public interface EgovMultimediaService {

    /**
     * 멀티미디어 정보를 삭제하는 Service interface 메서드 *
     * @param multimedia
     * @throws Exception
     */
    public void deleteMultimedia(Multimedia multimedia) throws Exception;

    /**
     * 멀티미디어 정보를 등록하는 Service interface 메서드
     * @param multimedia
     * @throws Exception
     */
    public void insertMultimedia(Multimedia multimedia) throws Exception;

    /**
     * 멀티미디어 상세정보를 조회하는 Service interface 메서드
     * @param multimedia
     * @return multimedia 멀티미디어 정보
     * @throws Exception
     */
    public Multimedia selectMultimedia(Multimedia multimedia) throws Exception;

    /**
     * 멀티미디어 목록을 조회하는 Service interface 메서드
     * @param searchVO
     * @return List 멀티미디어 목록
     * @throws Exception
     */
    public List<?> selectMultimediaList(MultimediaVO searchVO) throws Exception;

    /**
     * 멀티미디어 정보를 수정하는 Service interface 메서드
     * @param multimedia
     * @throws Exception
     */
    public void updateMultimedia(Multimedia multimedia) throws Exception;

    /**
     * 멀티미디어 정보의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     * @throws Exception
     */
    public int selectMultimediaListTotCnt(MultimediaVO searchVO)
            throws Exception;

    /**
     * 멀티미디어 파일을 상대 경로에 저장한다.
     * @param fileList
     * @return void
     * @throws Exception
     */
    public void copyFileToRelativePath(List<FileVO> fileList) throws Exception;

    /**
     * 파일별 지원 브라우저 정보를 조회한다.
     * @return List<MultimediaFileInfo>
     * @throws Exception
     */
    public List<MultimediaFileInfo> getMultimediaFileInfoFromXML()
            throws Exception;

    /**
     * 지원 브라우저 정보를 조회한다.
     * @param List
     *        <String> 확장자
     * @return String
     * @throws Exception
     */
    public String getBrowserInfoFromXML(String mltmdNm, List<String> extList)
            throws Exception;
}
