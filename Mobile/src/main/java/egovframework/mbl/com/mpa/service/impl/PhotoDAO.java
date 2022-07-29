package egovframework.mbl.com.mpa.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.mpa.service.Photo;
import egovframework.mbl.com.mpa.service.PhotoVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 사진에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 사진에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 사진에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.10  정홍규          최초 생성
 *
 * </pre>
 */

@Repository("PhotoDAO")
public class PhotoDAO extends EgovComAbstractDAO {

    /**
     * 사진 정보 DB 삭제 메서드
     * @param photo
     * @throws Exception
     */
    public void deletePhoto(Photo photo) throws Exception {
        delete("PhotoDAO.deletePhoto", photo);
    }

    /**
     * 사진 정보 DB 등록 메서드
     * @param photo
     * @throws Exception
     */
    public void insertPhoto(Photo photo) throws Exception {
        insert("PhotoDAO.insertPhoto", photo);
    }

    /**
     * 사진 정보 상세 DB 조회 메서드
     * @param photo
     * @return Photo 사진 정보
     * @throws Exception
     */
    public Photo selectPhoto(Photo photo) throws Exception {
        return (Photo) select("PhotoDAO.selectPhoto", photo);
    }

    /**
     * 사진 목록 DB 조회 메서드
     * @param searchVO
     * @return List 사진 목록
     * @throws Exception
     */
    public List<?> selectPhotoList(PhotoVO searchVO) throws Exception {
        return list("PhotoDAO.selectPhotoList", searchVO);
    }

    /**
     * 사진 정보 DB 수정 메서드
     * @param photo
     * @throws Exception
     */
    public void updatePhoto(Photo photo) throws Exception {
        update("PhotoDAO.updatePhoto", photo);
    }

    /**
     * 사진 정보의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     * @throws Exception
     */
    public int selectPhotoListTotCnt(PhotoVO searchVO) throws Exception {
        return (Integer) select(
            "PhotoDAO.selectPhotoListTotCnt", searchVO);
    }
}
