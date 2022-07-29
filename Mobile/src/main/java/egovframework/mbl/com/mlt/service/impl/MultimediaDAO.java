package egovframework.mbl.com.mlt.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.mlt.service.Multimedia;
import egovframework.mbl.com.mlt.service.MultimediaVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 멀티미디어에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 멀티미디어에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 멀티미디어에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.22  정홍규          최초 생성
 *
 * </pre>
 */

@Repository("MultimediaDAO")
public class MultimediaDAO extends EgovComAbstractDAO {

	/**
	 * 멀티미디어 정보 DB 삭제 메서드
	 * @param multimedia
	 * @throws Exception
	 */
	public void deleteMultimedia(Multimedia multimedia) throws Exception {
		delete("MultimediaDAO.deleteMultimedia", multimedia);
	}

	/**
	 * 멀티미디어 정보 DB 등록 메서드
	 * @param multimedia
	 * @throws Exception
	 */
	public void insertMultimedia(Multimedia multimedia) throws Exception {
		insert("MultimediaDAO.insertMultimedia", multimedia);
	}

	/**
	 * 멀티미디어 정보 상세 DB 조회 메서드
	 * @param multimedia
	 * @return Multimedia 멀티미디어 정보
	 * @throws Exception
	 */
	public Multimedia selectMultimedia(Multimedia multimedia) throws Exception {
		return (Multimedia) select("MultimediaDAO.selectMultimedia", multimedia);
	}

	/**
	 * 멀티미디어 목록 DB 조회 메서드
	 * @param searchVO
	 * @return List 멀티미디어 목록
	 * @throws Exception
	 */
	public List<?> selectMultimediaList(MultimediaVO searchVO) throws Exception {
		return list("MultimediaDAO.selectMultimediaList", searchVO);
	}

	/**
	 * 멀티미디어 정보 DB 수정 메서드
	 * @param multimedia
	 * @throws Exception
	 */
	public void updateMultimedia(Multimedia multimedia) throws Exception {
		update("MultimediaDAO.updateMultimedia", multimedia);
	}

	/**
	 * 멀티미디어 정보의 총 갯수를 조회한다.
	 * @param searchVO
	 * @return int
	 * @throws Exception
	 */
	public int selectMultimediaListTotCnt(MultimediaVO searchVO) throws Exception {
		return (Integer) select("MultimediaDAO.selectMultimediaListTotCnt", searchVO);
	}
}
