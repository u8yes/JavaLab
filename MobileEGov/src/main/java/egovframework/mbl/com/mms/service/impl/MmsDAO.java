package egovframework.mbl.com.mms.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.mms.service.AttachFile;
import egovframework.mbl.com.mms.service.AttachFileVO;
import egovframework.mbl.com.mms.service.MmsTransInfo;
import egovframework.mbl.com.mms.service.MmsTransInfoVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - MMS서비스연계에 대한 DB상의 접근, 변경을 처리한다.
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
 *
 * </pre>
 */
@Repository("mmsDAO")
public class MmsDAO extends EgovComAbstractDAO {

    /**
     * MMS 전송 정보를 DB에 등록한다.
     *
     * @param mmsTransInfo
     * @throws Exception
     */
    public void insertMmsTransmissionResult(MmsTransInfo mmsTransInfo) throws Exception {
        insert("MmsDAO.insertMmsTransmissionResult", mmsTransInfo);
    }

    /**
     * MMS 전송 결과를 수정한다.
     *
     * @param mmsTransInfo
     * @throws Exception
     */
    public void updateMmsTransmissionResult(MmsTransInfo mmsTransInfo) throws Exception {
        update("MmsDAO.updateMmsTransmissionResult", mmsTransInfo);
    }

    /**
     * MMS 전송 결과 목록을 조회한다.
     * @param attachFileVO
     * @return List<AttachFileVO> 첨부파일 리스트
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
	public List<AttachFileVO> selectMmsTransmissionResultList(MmsTransInfoVO mmsTransInfoVO) throws Exception {
        return (List<AttachFileVO>) list("MmsDAO.selectMmsTransmissionResultList", mmsTransInfoVO);
    }

    /**
     * MMS 전송 결과에 대한 목록 건수를 조회 한다.
     * @param attachFileVO
     * @return int 첨부파일 목록 건수
     * @throws Exception
    */
    public int selectMmsTransmissionResultListCnt(MmsTransInfoVO mmsTransInfoVO) throws Exception {
        return (Integer)select("MmsDAO.selectMmsTransmissionResultListCnt", mmsTransInfoVO);
    }

    /**
     * 첨부파일 목록을 조회한다.
     * @param attachFileVO
     * @return List<AttachFileVO> 첨부파일 리스트
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
	public List<AttachFileVO> selectAttachFileList(AttachFileVO attachFileVO) throws Exception {
        return (List<AttachFileVO>) list("MmsDAO.selectAttachFileList", attachFileVO);
    }

    /**
     * 첨부파일에 대한 목록 건수를 조회 한다.
     * @param attachFileVO
     * @return int 첨부파일 목록 건수
     * @throws Exception
    */
    public int selectAttachFileInfoListCnt(AttachFileVO attachFileVO) throws Exception {
        return (Integer)select("MmsDAO.selectAttachFileListCnt", attachFileVO);
    }

    /**
     * 첨부파일의 정보를 상세조회한다.
     * @param attachFileVO
     * @return AttachFile 첨부파일 정보
     * @throws Exception
    */
    public AttachFile selectAttachFile(AttachFileVO attachFileVO) throws Exception {
        return (AttachFile)select("MmsDAO.selectAttachFile", attachFileVO);
    }

    /**
     * 첨부파일의 정보를 DB에 등록한다.
     *
     * @param attachFile
     * @throws Exception
     */
    public void insertAttachFile(AttachFile attachFile) throws Exception {
        insert("MmsDAO.insertAttachFile", attachFile);
    }

    /**
     * 조회된 첨부파일의 정보를 DB에서 수정한다.
     *
     * @param attachFile
     * @throws Exception
     */
    public void updateAttachFile(AttachFile attachFile) throws Exception {
        update("MmsDAO.updateAttachFile", attachFile);
    }

    /**
     * 조회된 첨부파일의 정보를 DB에서 삭제한다.
     *
     * @param attachFile
     * @throws Exception
     */
    public void deleteAttachFile(AttachFile attachFile) throws Exception {
            delete("MmsDAO.deleteAttachFile", attachFile);
    }
}