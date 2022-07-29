package egovframework.mbl.com.rns.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.rns.service.RealtimeNotice;
import egovframework.mbl.com.rns.service.RealtimeNoticeVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 실시간 공지 서비스에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 실시간 공지 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
 * - 실시간 공지 서비스에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 조준형
 * @since 2011.08.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.11  조준형          최초 생성
 *
 * </pre>
 */
@Repository("RealtimeNoticeDAO")
public class RealtimeNoticeDAO extends EgovComAbstractDAO {

	/**
	 * [관리자] 실시간공지서비스 NEW 일련번호 조회
	 *
	 * @return
	 * @throws Exception
	 */
	public int selectRealtimeNoticeNewSn() throws Exception {
		return (Integer)select("RealtimeNoticeDAO.selectRealtimeNoticeNewSn", "");
	}

	/**
	 * [관리자] 실시간공지서비스 목록 조회
	 *
	 * @param searchVO
	 * @return List
	 * @throws Exception
	 */
	public List<?> selectRealtimeNoticeList(RealtimeNoticeVO searchVO) throws Exception {
		return list("RealtimeNoticeDAO.selectRealtimeNoticeList", searchVO);
	}

	/**
	 * [관리자/사용자] 실시간공지서비스 상세조회 / 실시간공지 서비스 내용
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public RealtimeNotice selectRealtimeNotice(RealtimeNoticeVO searchVO) throws Exception {
		return (RealtimeNotice) select("RealtimeNoticeDAO.selectRealtimeNotice", searchVO);
	}

	/**
	 * [관리자] 실시간공지서비스 등록
	 *
	 * @param realtimeNotice
	 * @throws Exception
	 */
	public int insertRealtimeNotice(RealtimeNotice realtimeNotice) throws Exception {
		return update("RealtimeNoticeDAO.insertRealtimeNotice", realtimeNotice);
	}

	/**
	 * 실시간공지서비스 SN 최신구분 변경
	 *
	 * @param realtimeNotice
	 * @return
	 * @throws Exception
	 */
//	public int updateRealtimeNoticeRecent(RealtimeNotice realtimeNotice) throws Exception {
//		return update("RealtimeNoticeDAO.updateRealtimeNoticeRecent", realtimeNotice);
//	}

	/**
	 * [관리자] 실시간공지서비스 삭제
	 *
	 * @param searchVO
	 * @throws Exception
	 */
	public int deleteRealtimeNotice(RealtimeNotice searchVO) throws Exception {
		return delete("RealtimeNoticeDAO.deleteRealtimeNotice", searchVO);
	}

	/**
	 * 실시간 공지서비스 메시지 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public RealtimeNotice selectRealtimeNoticeMsg() throws Exception {
		return (RealtimeNotice) select("RealtimeNoticeDAO.selectRealtimeNoticeMsg", "");
	}
}
