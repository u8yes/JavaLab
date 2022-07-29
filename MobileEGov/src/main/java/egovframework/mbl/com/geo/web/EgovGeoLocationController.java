package egovframework.mbl.com.geo.web;

import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.geo.service.EgovGeoLocationService;
import egovframework.mbl.com.geo.service.GeoLocation;
import egovframework.mbl.com.geo.service.GeoLocationVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * -  건물 위치정보에 대한 Controller를 정의한다.
 *
 * 상세내용
 * -  건물 위치정보에 대한 등록, 수정, 삭제, 조회, 상세조회 요청 사항을 Service와 매핑 처리한다.
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
@Controller
public class EgovGeoLocationController {

    /** EgovGeoLocationService */
    @Resource(name="EgovGeoLocationService")
    private EgovGeoLocationService egovGeoLocationService;

    /** EgovPropertyService */
    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;

    /** DefaultBeanValidator */
    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 건물 위치정보 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param geoLocationVO
     * @param model
     * @return String 건물 위치정보 목록 조회 화면
     * @throws Exception
    */
    @IncludedMblInfo(name="위치 정보 연계(공공/민간)",order = 508 ,gid = 50)
    @RequestMapping("/mbl/com/geo/selectBuildingLocationInfoList.mdo")
    public String selectBuildingLocationInfoList(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        geoLocationVO.setPageUnit(propertyService.getInt("pageUnit"));
        geoLocationVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(geoLocationVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(geoLocationVO.getPageUnit());
        paginationInfo.setPageSize(geoLocationVO.getPageSize());

        geoLocationVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        geoLocationVO.setLastIndex(paginationInfo.getLastRecordIndex());
        geoLocationVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = egovGeoLocationService.selectBuildingLocationInfoList(geoLocationVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/geo/EgovBuildingLocationInfoList";
    }

    /**
     * 건물 위치정보 상세조회 Service interface 호출 및 결과를 반환한다.
     * @param geoLocationVO
     * @param model
     * @return String 건물 위치정보 상세조회 화면
     * @throws Exception
    */
    @RequestMapping("/mbl/com/geo/selectBuildingLocationInfo.mdo")
    public String selectBuildingLocationInfo(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        GeoLocation geoLocation = egovGeoLocationService.selectBuildingLocationInfo(geoLocationVO);

        model.addAttribute("geoLocation", geoLocation);

        return "egovframework/mbl/com/geo/EgovBuildingLocationInfoDetail";
    }

    /**
     * 건물 위치정보 등록 Service interface 호출 및 결과를 반환한다.
     * @param geoLocation
     * @return String 건물 위치정보 목록 조회 화면
     * @throws Exception
    */
    @RequestMapping("/mbl/com/geo/insertBuildingLocationInfo.mdo")
    public String insertBuildingLocationInfo(@ModelAttribute("geoLocation") GeoLocation geoLocation) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        if (isAuthenticated) {
            geoLocation.setMberId(user.getId());
            egovGeoLocationService.insertBuildingLocationInfo(geoLocation);
        }

        return "forward:/mbl/com/geo/selectBuildingLocationInfoList.mdo";
    }

    /**
     * 건물 위치정보 등록 화면으로 이동한다.
     * @param geoLocationVO
     * @param model
     * @return String 건물 위치정보 등록 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/geo/goBuildingLocationInfoRegist.mdo")
    public String goBuildingLocationInfoRegist(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        model.addAttribute("geoLocation", new GeoLocation());
        return "egovframework/mbl/com/geo/EgovBuildingLocationInfoRegist";
    }

    /**
     * 건물 위치정보 수정 Service interface 호출 및 결과를 반환한다.
     * @param geoLocation
     * @return String 건물 위치정보 목록 조회 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/geo/updateBuildingLocationInfo.mdo")
    public String updateBuildingLocationInfo(@ModelAttribute("geoLocation") GeoLocation geoLocation) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        if (isAuthenticated) {
            geoLocation.setMberId(user.getId());
            egovGeoLocationService.updateBuildingLocationInfo(geoLocation);
        }

        return "forward:/mbl/com/geo/selectBuildingLocationInfoList.mdo";
    }

    /**
     * 건물 위치정보 수정 화면으로 이동한다.
     * @param geoLocationVO
     * @param model
     * @return String 건물 위치정보 수정 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/geo/goBuildingLocationInfoUpdt.mdo")
    public String goBuildingLocationInfoUpdt(@ModelAttribute("searchVO")GeoLocationVO geoLocationVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        GeoLocation result = egovGeoLocationService.selectBuildingLocationInfo(geoLocationVO);
        model.addAttribute("geoLocation", result);

        return "egovframework/mbl/com/geo/EgovBuildingLocationInfoUpdt";
    }

    /**
     * 건물 위치정보 삭제 Service interface 호출 및 결과를 반환한다.
     * @param geoLocation
     * @return String 건물 위치정보 목록 조회 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/geo/deleteBuildingLocationInfo.mdo")
    public String deleteBuildingLocationInfo(@ModelAttribute("geoLocation") GeoLocation geoLocation) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        // 사용자 인증여부 판단
        if (isAuthenticated) {
            geoLocation.setMberId(user.getId());
            egovGeoLocationService.deleteBuildingLocationInfo(geoLocation);
        }

        return "forward:/mbl/com/geo/selectBuildingLocationInfoList.mdo";
    }

    /**
     * 주변 건물 위치정보 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param geoLocationVO
     * @return ModelAndView JsonView
     * @throws Exception
    */
    @RequestMapping("/mbl/com/geo/selectMobileBuildingLocationInfoList.mdo")
    public ModelAndView selectMobileBuildingLocationInfoList(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	geoLocationVO.setSearchUseYn("Y");
        Map<String, Object> map = egovGeoLocationService.selectBuildingLocationInfoList(geoLocationVO);
        modelAndView.addObject("resultList", map.get("resultList"));

        return modelAndView;
    }

    /**
     * 현재 위치정보 조회 화면(공공지도)으로 이동한다.
     * @param geoLocation
     * @return String 현재 위치정보 조회 화면(공공지도)
     * @throws Exception
    */
    @IncludedMblInfo(name="공공 지도 연계 서비스",order = 410 ,gid = 40)
    @RequestMapping(value="/mbl/com/geo/goGeoLocationPublicInqire.mdo")
    public String goGeoLocationPublicInqire(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO) throws Exception {
        return "egovframework/mbl/com/geo/EgovMobileGeoLocationPublicInqire";
    }

    /**
     * 현재 위치정보 조회 화면(민간지도)으로 이동한다.
     * @param geoLocation
     * @return String 현재 위치정보 조회 화면(민간지도)
     * @throws Exception
    */
    @IncludedMblInfo(name="민간 지도 연계 서비스",order = 411 ,gid = 40)
    @RequestMapping(value="/mbl/com/geo/goGeoLocationPrivateInqire.mdo")
    public String goGeoLocationPrivateInqire(@ModelAttribute("searchVO") GeoLocationVO geoLocationVO) throws Exception {
        return "egovframework/mbl/com/geo/EgovMobileGeoLocationPrivateInqire";
    }
}