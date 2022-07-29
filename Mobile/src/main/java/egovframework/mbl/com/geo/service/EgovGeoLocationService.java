package egovframework.mbl.com.geo.service;

import java.util.Map;

/**
 * 개요
 * - 위치정보연계에 대한 Service Interface를 정의한다.
 * 
 * 상세내용
 * - 건물의 위치정보에 대한 등록, 수정, 삭제, 상세조회 기능을 제공한다.
 * - 건물의 위치정보의 조회기능은 목록, 상세조회로 구분된다.
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
public interface EgovGeoLocationService {

    /**
     * 건물의 위치정보 목록을 조회하는 Service interface 메서드
     * @param geoLocationVO
     * @return Map<String, Object> 주변건물 위치정보 리스트
     * @throws Exception
    */
    public Map<String, Object> selectBuildingLocationInfoList(GeoLocationVO geoLocationVO) throws Exception;

    /**
     * 건물의 위치정보를 상세조회하는 Service interface 메서드
     * @param geoLocationVO
     * @return GeoLocation 주변건물 위치정보
     * @throws Exception
    */
    public GeoLocation selectBuildingLocationInfo(GeoLocationVO geoLocationVO) throws Exception;

    /**
     * 건물의 위치정보를 등록하는 Service interface 메서드
     * @param geoLocation
     * @throws Exception
    */
    public void insertBuildingLocationInfo(GeoLocation geoLocation) throws Exception;

    /**
     * 건물의 위치정보를 수정하는 Service interface 메서드
     * @param geoLocation
     * @throws Exception
    */
    public void updateBuildingLocationInfo(GeoLocation geoLocation) throws Exception;

    /**
     * 건물의 위치정보를 삭제하는 Service interface 메서드
     * @param geoLocation
     * @throws Exception
    */
    public void deleteBuildingLocationInfo(GeoLocation geoLocation) throws Exception;

}