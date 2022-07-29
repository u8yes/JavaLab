package egovframework.mbl.com.mpa.service;

import java.util.List;

/**
 * 개요
 * - 사진 앨범에 대한 Service Interface를 정의한다.
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

public interface EgovPhotoService {

    /**
     * 사진 정보를 삭제하는 Service interface 메서드 *
     * @param photo
     * @throws Exception
     */
    public void deletePhoto(Photo photo) throws Exception;

    /**
     * 사진 정보를 등록하는 Service interface 메서드
     * @param photo
     * @throws Exception
     */
    public void insertPhoto(Photo photo) throws Exception;

    /**
     * 사진 상세정보를 조회하는 Service interface 메서드
     * @param photo
     * @return Photo 사진 정보
     * @throws Exception
     */
    public Photo selectPhoto(Photo photo) throws Exception;

    /**
     * 사진 목록을 조회하는 Service interface 메서드
     * @param searchVO
     * @return List 사진 목록
     * @throws Exception
     */
    public List<?> selectPhotoList(PhotoVO searchVO) throws Exception;

    /**
     * 사진 정보를 수정하는 Service interface 메서드
     * @param photo
     * @throws Exception
     */
    public void updatePhoto(Photo photo) throws Exception;

    /**
     * 사진 정보의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     * @throws Exception
     */
    public int selectPhotoListTotCnt(PhotoVO searchVO) throws Exception;
}
