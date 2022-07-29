package egovframework.mbl.com.syn.service.impl;

import java.util.List;

import egovframework.mbl.com.syn.service.EgovSyncService;
import egovframework.mbl.com.syn.service.Sync;
import egovframework.mbl.com.syn.service.SyncVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

//import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
/**
 * 개요
 * - 동기화 서비스에 대한 Service Interface를 구현한다.
 *
 * 상세내용
 * - 동기화 서비스에 대한 등록, 삭제, 조회 기능을 제공한다.
 * - 동기화 서비스에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 조준형
 * @since 2011.08.12
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.12  조준형          최초 생성
 *   2011.09.06  조준형          동기화 기능 추가
 *
 * </pre>
 */
@Service("SyncService")
public class EgovSyncServiceImpl extends EgovAbstractServiceImpl implements EgovSyncService {

	/** ID Generation */
	//@Resource(name="egovRealtimeNoticeIdGnrService")
	//private EgovIdGnrService idgenService;

	@Resource(name = "SyncDAO")
	private SyncDAO syncDAO;

	/**
	 * 동기화 서비스 글 총 갯수
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectSyncListTotCnt(SyncVO searchVO) throws Exception {
		int rtn = 0;

		rtn = syncDAO.selectSyncListTotCnt(searchVO);

		return rtn;
	}

	/**
	 * 동기화 서비스 글 목록 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<?> selectSyncList(SyncVO searchVO) throws Exception {
		List<?> rtnList = null;

		searchVO.setFetchRow(searchVO.getFetchRow() * 5);
		rtnList = syncDAO.selectSyncList(searchVO);

		return rtnList;
	}

	/**
	 * 동기화 서비스글 상세 조회
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public Sync selectSync(SyncVO searchVO) throws Exception {
		Sync sync = null;

		sync = syncDAO.selectSync(searchVO);

		return sync;
	}

	/**
	 * 동기호 서비스 글 등록
	 *
	 * @param sync
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertSync(Sync sync) throws Exception {
		int rtn = 0;

		int newSn = syncDAO.selectSyncNewSn();
		sync.setSn(newSn);

		rtn = syncDAO.insertSync(sync);

		return rtn;
	}

	/**
	 * 동기화 서비스 글 삭제
	 *
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public int deleteSync(Sync sync) throws Exception {
		int rtn = 0;

		rtn = syncDAO.deleteSync(sync);

		return rtn;

	}

	/**
	 * 동기화 서비스 글 수정
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateSync(Sync sync) throws Exception {
		int rtn = 0;

		rtn = syncDAO.updateSync(sync);

		return rtn;

	}

	/**
	 * 동기화 서비스 글 '동기화'를 실행
	 *
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int executeSync(Sync sync, String localData) throws Exception {
		int rtn = 0;

		String[] localDataArr = localData.split("∀");
		for (int i = 0; i < localDataArr.length; i++) {
			String[] syncDataArr = localDataArr[i].split("\\|");

			if ("I".equals(syncDataArr[0].toString())) {
				sync.setSyncSj(syncDataArr[2]);
				sync.setSyncCn(syncDataArr[3]);
				rtn = insertSync(sync);

			} else if ("U".equals(syncDataArr[0].toString())) {
				int updateSn = Integer.parseInt(syncDataArr[1]);

				sync.setSn(updateSn);
				sync.setSyncSj(syncDataArr[2]);
				sync.setSyncCn(syncDataArr[3]);
				rtn = updateSync(sync);

			} else if ("D".equals(syncDataArr[0].toString())) {
				int deleteSn = Integer.parseInt(syncDataArr[1]);

				sync.setSn(deleteSn);
				rtn = deleteSync(sync);

			}
		}

		return rtn;
	}
}
