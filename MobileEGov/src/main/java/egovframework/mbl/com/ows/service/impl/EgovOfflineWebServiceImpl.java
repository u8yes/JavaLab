package egovframework.mbl.com.ows.service.impl;

import java.util.List;

import egovframework.mbl.com.ows.service.EgovOfflineWebService;
import egovframework.mbl.com.ows.service.OfflineWeb;
import egovframework.mbl.com.ows.service.OfflineWebVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
// import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
/**
 * 개요
 * - 오프라인웹 서비스에 대한 Service Interface를 구현한다.
 *
 * 상세내용
 * - 오프라인웹 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
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
 *   2011.08.12  조준형          최초 생성
 *
 * </pre>
 */
@Service("OfflineWebService")
public class EgovOfflineWebServiceImpl extends EgovAbstractServiceImpl implements EgovOfflineWebService{
	/** ID Generation */
	//@Resource(name="egovRealtimeNoticeIdGnrService")
	//private EgovIdGnrService idgenService;

	@Resource(name="OfflineWebDAO")
    private OfflineWebDAO offlineWebDAO;

	/**
	 * 오프라인웹 서비스 글 총 갯수
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectOfflineWebListTotCnt(OfflineWebVO searchVO) throws Exception {
		int rtn = 0;

		rtn = offlineWebDAO.selectOfflineWebListTotCnt(searchVO);

		return rtn;
	}

	/**
	 * 오프라인웹 서비스 글 목록 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<?> selectOfflineWebList(OfflineWebVO searchVO) throws Exception {
		List<?> rtnList = null;

		searchVO.setFetchRow(searchVO.getFetchRow() * 5);
		rtnList = offlineWebDAO.selectOfflineWebList(searchVO);

		return rtnList;
	}

	/**
	 * 오프라인웹 서비스글 상세 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public OfflineWeb selectOfflineWeb(OfflineWebVO searchVO) throws Exception {
		OfflineWeb offlineWeb = null;

		offlineWeb = offlineWebDAO.selectOfflineWeb(searchVO);

		return offlineWeb;
	}

	/**
	 * 동기호 서비스 글 등록
	 *
	 * @param offlineWeb
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertOfflineWeb(OfflineWeb offlineWeb) throws Exception {
		int rtn = 0;

		int newSn = offlineWebDAO.selectOfflineWebNewSn();
		offlineWeb.setSn(newSn);
		rtn = offlineWebDAO.insertOfflineWeb(offlineWeb);

		return rtn;
	}

	/**
	 * 오프라인웹 서비스 글 삭제
	 *
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public int deleteOfflineWeb(OfflineWebVO searchVO) throws Exception {
		int rtn = 0;

		rtn = offlineWebDAO.deleteOfflineWeb(searchVO);

		return rtn;
	}

	/**
	 * 오프라인웹 서비스 글 수정
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateOfflineWeb(OfflineWeb offlineWeb) throws Exception {
		int rtn = 0;

		rtn = offlineWebDAO.updateOfflineWeb(offlineWeb);

		return rtn;
	}
}
