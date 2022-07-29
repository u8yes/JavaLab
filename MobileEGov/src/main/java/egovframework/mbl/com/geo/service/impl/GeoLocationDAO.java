package egovframework.mbl.com.geo.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.geo.service.GeoLocation;
import egovframework.mbl.com.geo.service.GeoLocationVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 건물 위치정보에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 건물 위치정보에 대한 등록, 수정, 삭제 기능을 제공한다.
 * - 건물 위치정보의 조회기능은 목록, 상세조회로 구분된다.
 * @author 조재만
 * @since 2011.08.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.19  조재만          최초 생성
 *
 * </pre>
 */
@Repository("geoLocationDAO")
public class GeoLocationDAO extends EgovComAbstractDAO {

    /**
     * 건물 위치정보 목록을 조회한다.
     * @param geoLocationVO
     * @return List<GeoLocationVO> 건물 위치정보 리스트
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
	public List<GeoLocationVO> selectBuildingLocationInfoList(GeoLocationVO geoLocationVO) throws Exception {
        return (List<GeoLocationVO>) list("GeoLocationDAO.selectBuildingLocationInfoList", geoLocationVO);
    }

    /**
     * 건물 위치정보에 대한 목록 건수를 조회 한다.
     * @param geoLocationVO
     * @return int 건물 위치정보 목록 건수
     * @throws Exception
    */
    public int selectBuildingLocationInfoListCnt(GeoLocationVO geoLocationVO) throws Exception {
        return (Integer)select("GeoLocationDAO.selectBuildingLocationInfoListCnt", geoLocationVO);
    }

    /**
     * 건물 위치정보를 조회한다.
     * @param geoLocationVO
     * @return GeoLocation 주변건물 위치정보
     * @throws Exception
    */
    public GeoLocation selectBuildingLocationInfo(GeoLocationVO geoLocationVO) throws Exception {
        return (GeoLocation)select("GeoLocationDAO.selectBuildingLocationInfo", geoLocationVO);
    }

    /**
     * 건물 위치정보를 DB에 등록한다.
     *
     * @param geoLocation
     * @throws Exception
     */
    public void insertBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
        insert("GeoLocationDAO.insertBuildingLocationInfo", geoLocation);
    }

    /**
     * 건물 위치정보를 DB에서 수정한다.
     *
     * @param geoLocation
     * @throws Exception
     */
    public void updateBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
            update("GeoLocationDAO.updateBuildingLocationInfo", geoLocation);
    }

    /**
     * 주변건물 위치정보를 DB에서 삭제한다.
     *
     * @param geoLocation
     * @throws Exception
     */
    public void deleteBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
            delete("GeoLocationDAO.deleteBuildingLocationInfo", geoLocation);
    }
}