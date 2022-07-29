package egovframework.mbl.com.geo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.mbl.com.geo.service.EgovGeoLocationService;
import egovframework.mbl.com.geo.service.GeoLocation;
import egovframework.mbl.com.geo.service.GeoLocationVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 개요
 * - 위치정보연계에 대한 Service Interface를 구현한다.
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
@Service("EgovGeoLocationService")
public class EgovGeoLocationServiceImpl extends EgovAbstractServiceImpl implements EgovGeoLocationService {

    /** GeoLocationDAO */
    @Resource(name="geoLocationDAO")
    private GeoLocationDAO geoLocationDAO;
    
    /** ID Generation */    
    @Resource(name="egovGeoLocationIdGnrService")
    private EgovIdGnrService idgenService;

    /**
     * 건물의 위치정보 목록을 조회한다.
     * @param geoLocationVO
     * @return Map<String, Object> 건물 위치정보 조회결과 리스트, 조회건수
     * @throws Exception
    */
    public Map<String, Object> selectBuildingLocationInfoList(GeoLocationVO geoLocationVO) throws Exception {
        List<GeoLocationVO> result = geoLocationDAO.selectBuildingLocationInfoList(geoLocationVO);

        int cnt = geoLocationDAO.selectBuildingLocationInfoListCnt(geoLocationVO);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }
 
    /**
     * 건물의 위치정보를 조회한다.
     *
     * @param geoLocationVO
     * @return Geolocation 건물의 위치정보
     * @throws Exception
    */
    public GeoLocation selectBuildingLocationInfo(GeoLocationVO geoLocationVO) throws Exception {
        return geoLocationDAO.selectBuildingLocationInfo(geoLocationVO);
    }
 
    /**
     * 건물의 위치정보를 DB에 등록한다.
     * @param geoLocation
     * @throws Exception
    */
    public void insertBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
        int sn = idgenService.getNextIntegerId();
        
        geoLocation.setSn(sn);
        geoLocationDAO.insertBuildingLocationInfo(geoLocation);
    }

    /**
     * 건물의 위치정보를 수정한다.
     * @param geoLocation
     * @throws Exception
    */
    public void updateBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
        geoLocationDAO.updateBuildingLocationInfo(geoLocation);
    }
    
    /**
     * 건물의 위치정보를 삭제한다.
     * @param geoLocation
     * @throws Exception
    */
    public void deleteBuildingLocationInfo(GeoLocation geoLocation) throws Exception {
        geoLocationDAO.deleteBuildingLocationInfo(geoLocation);
    }
}
