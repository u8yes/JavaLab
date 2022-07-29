package egovframework.mbl.com.rns.service;

import java.util.List;

/**
 * 개요
 * - 실시간 공지 서비스에 대한 Service Interface를 정의한다.
 *
 * 상세내용
 * - 실시간 공지 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
 * - 실시간 공지 서비스 조회기능은 목록, 상세조회로 구분된다.
 * @author 조준형
 * @since 2011.08.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.10  조준형          최초 생성
 *
 * </pre>
 */
public interface EgovRealtimeNoticeService {

	/**
	 * [관리자] 실시간공지서비스 목록 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<?> selectRealtimeNoticeList(RealtimeNoticeVO searchVO) throws Exception;

	/**
	 * [관리자/사용자] 실시간공지서비스 상세조회 / 실시간공지 서비스 내용
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public RealtimeNotice selectRealtimeNotice(RealtimeNoticeVO searchVO) throws Exception;

	/**
	 * [관리자] 실시간공지서비스 등록
	 *
	 * @param realtimeNotice
	 * @throws Exception
	 */
	public int insertRealtimeNotice(RealtimeNotice realtimeNotice) throws Exception;

	/**
	 * [관리자] 실시간공지서비스 삭제
	 *
	 * @param searchVO
	 * @throws Exception
	 */
	public int deleteRealtimeNotice(RealtimeNoticeVO searchVO) throws Exception;


	/**
	 * 실시간공지서비스 메시지 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public RealtimeNotice selectRealtimeNoticeMsg() throws Exception;

}
