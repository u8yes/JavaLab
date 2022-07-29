package egovframework.mbl.com.ows.service;

import java.util.List;
/**
 * 개요
 * - 오프라인웹 서비스에 대한 Service Interface를 정의한다.
 *
 * 상세내용
 * - 오프라인웹 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
 * - 오프라인웹 서비스 조회기능은 목록, 상세조회로 구분된다.
 * @author 조준형
 * @since 2011.09.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.09.01  조준형          최초 생성
 *
 * </pre>
 */

public interface EgovOfflineWebService {

	/**
	 * 오프라인웹 서비스 글 목록 조회
	 *
	 * @param searchVO
	 *
	 * @return List;
	 * @throws Exception
	 */
	public List<?> selectOfflineWebList(OfflineWebVO searchVO) throws Exception;

	/**
	 * 오프라인웹 서비스글 상세 조회
	 *
	 * @param searchVO
	 * @return OfflineWeb;
	 * @throws Exception
	 */
	public OfflineWeb selectOfflineWeb(OfflineWebVO searchVO) throws Exception;

	/**
	 * 동기호 서비스 글 등록
	 *
	 * @param OfflineWeb
	 * @return int;
	 * @throws Exception
	 */
	public int insertOfflineWeb(OfflineWeb OfflineWeb) throws Exception;

	/**
	 * 오프라인웹 서비스 글 삭제
	 *
	 * @param searchVO
	 *
	 * @return int;
	 * @throws Exception
	 */
	public int deleteOfflineWeb(OfflineWebVO searchVO) throws Exception;

	/**
	 * 오프라인웹 서비스 글 수정
	 *
	 * @param searchVO
	 *
	 * @return int;
	 * @throws Exception
	 */
	public int updateOfflineWeb(OfflineWeb OfflineWeb) throws Exception;

	/**
	 * 오프라인웹 서비스 글 목록 총갯수
	 *
	 * @return
	 * @throws Exception
	 */
	public int selectOfflineWebListTotCnt(OfflineWebVO searchVO) throws Exception;

}
