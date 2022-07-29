package egovframework.mbl.com.syn.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.syn.service.Sync;
import egovframework.mbl.com.syn.service.SyncVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 동기화 서비스에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 동기화 서비스에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 동기화 서비스에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 조준형
 * @since 2011.08.22
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
@Repository("SyncDAO")
public class SyncDAO extends EgovComAbstractDAO {

	/**
	 * 동기화 서비스 신규 일련번호 조회
	 *
	 * @return
	 * @throws Exception
	 */
	public int selectSyncNewSn() throws Exception {
		return (Integer)select("SyncDAO.selectSyncNewSn", "");
	}

	/**
	 * 동기화 서비스 글 총 갯수 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectSyncListTotCnt(SyncVO searchVO) throws Exception {
		return (Integer)select("SyncDAO.selectSyncTotCnt", searchVO);
	}

	/**
	 * 동기화 서비스 목록 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectSyncList(SyncVO searchVO) throws Exception {
		return list("SyncDAO.selectSyncList", searchVO);
	}

	/**
	 * 동기화 서비스 상세 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public Sync selectSync(SyncVO searchVO) throws Exception {
		return (Sync) select("SyncDAO.selectSync", searchVO);
	}

	/**
	 * 동기화 서비스 등록
	 *
	 * @param Sync
	 * @return
	 * @throws Exception
	 */
	public int insertSync(Sync Sync) throws Exception {
		return update("SyncDAO.insertSync", Sync);
	}

	/**
	 * 동기화 서비스 수정
	 *
	 * @param sync
	 * @return
	 * @throws Exception
	 */
	public int updateSync(Sync sync) throws Exception {
		return update("SyncDAO.updateSync", sync);
	}

	/**
	 * 동기화 서비스 수정
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int deleteSync(Sync searchVO) throws Exception {
		return delete("SyncDAO.deleteSync", searchVO);
	}

}
