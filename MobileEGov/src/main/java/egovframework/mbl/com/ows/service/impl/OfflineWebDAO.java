package egovframework.mbl.com.ows.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.ows.service.OfflineWeb;
import egovframework.mbl.com.ows.service.OfflineWebVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 오프라인웹 서비스에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 오프라인웹 서비스에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 오프라인웹 서비스에 대한 조회기능은 목록, 상세조회로 구분된다.
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
@Repository("OfflineWebDAO")
public class OfflineWebDAO extends EgovComAbstractDAO {

	/**
	 * 오프라인웹 서비스 신규 일련번호 조회
	 *
	 * @return
	 * @throws Exception
	 */
	public int selectOfflineWebNewSn() throws Exception {
		return (Integer)select("OfflineWebDAO.selectOfflineWebNewSn", "");
	}

	/**
	 * 오프라인웹 서비스 글 총 갯수 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectOfflineWebListTotCnt(OfflineWebVO searchVO) throws Exception {
		return (Integer)select("OfflineWebDAO.selectOfflineWebTotCnt", searchVO);
	}

	/**
	 * 오프라인웹 서비스 목록 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<?> selectOfflineWebList(OfflineWebVO searchVO) throws Exception {
		return list("OfflineWebDAO.selectOfflineWebList", searchVO);
	}

	/**
	 * 오프라인웹 서비스 상세 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public OfflineWeb selectOfflineWeb(OfflineWebVO searchVO) throws Exception {
		return (OfflineWeb) select("OfflineWebDAO.selectOfflineWeb", searchVO);
	}

	/**
	 * 오프라인웹 서비스 등록
	 *
	 * @param OfflineWeb
	 * @return
	 * @throws Exception
	 */
	public int insertOfflineWeb(OfflineWeb OfflineWeb) throws Exception {
		return update("OfflineWebDAO.insertOfflineWeb", OfflineWeb);
	}

	/**
	 * 오프라인웹 서비스 수정
	 *
	 * @param OfflineWeb
	 * @return
	 * @throws Exception
	 */
	public int updateOfflineWeb(OfflineWeb OfflineWeb) throws Exception {
		return update("OfflineWebDAO.updateOfflineWeb", OfflineWeb);
	}

	/**
	 * 오프라인웹 서비스 수정
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int deleteOfflineWeb(OfflineWeb searchVO) throws Exception {
		return delete("OfflineWebDAO.deleteOfflineWeb", searchVO);
	}
}
