package egovframework.mbl.com.syn.service;

import java.util.List;
/**
 * 개요
 * - 동기화 서비스에 대한 Service Interface를 정의한다.
 *
 * 상세내용
 * - 동기화 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
 * - 동기화 서비스 조회기능은 목록, 상세조회로 구분된다.
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
 *   2011.08.22  조준형          최초 생성
 *
 * </pre>
 */

public interface EgovSyncService {

	/**
	 * 동기화 서비스 글 목록 조회
	 *
	 * @param searchVO
	 *
	 * @return List;
	 * @throws Exception
	 */

	public List<?> selectSyncList(SyncVO searchVO) throws Exception;

	/**
	 * 동기화 서비스글 상세 조회
	 *
	 * @param searchVO
	 * @return Sync;
	 * @throws Exception
	 */
	public Sync selectSync(SyncVO searchVO) throws Exception;

	/**
	 * 동기화 서비스 글 등록
	 *
	 * @param sync
	 * @return int;
	 * @throws Exception
	 */
	public int insertSync(Sync sync) throws Exception;

	/**
	 * 동기화 서비스 글 삭제
	 *
	 * @param searchVO
	 *
	 * @return int;
	 * @throws Exception
	 */
	public int deleteSync(Sync sync) throws Exception;

	/**
	 * 동기화 서비스 글 수정
	 *
	 * @param searchVO
	 *
	 * @return int;
	 * @throws Exception
	 */
	public int updateSync(Sync sync) throws Exception;

	/**
	 * 동기화 서비스 글 '동기화'를 실행한다.
	 *
	 * @param sync
	 *
	 * @return int;
	 * @throws Exception
	 */
	public int executeSync(Sync sync, String localData) throws Exception;

	/**
	 * 동기화 서비스 글 목록 총갯수
	 *
	 * @return
	 * @throws Exception
	 */
	public int selectSyncListTotCnt(SyncVO searchVO) throws Exception;
}
