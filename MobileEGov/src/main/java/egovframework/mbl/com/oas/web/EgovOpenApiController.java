package egovframework.mbl.com.oas.web;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.oas.service.EgovOpenApiService;
import egovframework.mbl.com.oas.service.OpenApi;
import egovframework.mbl.com.oas.service.OpenApiVO;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import kma.websky.client.stub.forecast.CurrentWeatherModel;
import kma.websky.client.stub.forecast.SurfaceServiceImplService_Impl;
import kma.websky.client.stub.forecast.SurfaceServiceImpl_Stub;
import noNamespace.SearchDocument;
import noNamespace.SearchDocument.Search;
import noNamespace.TotalResultsDocument.TotalResults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * 개요
 * - OpenAPI연계에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - OpenAPI연계 정보에 대한 등록, 조회, OpenAPI 서비스 내용 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 조재만
 * @since 2011.08.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.08  조재만          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovOpenApiController {


    /** EgovOpenApiService */
    @Resource(name="EgovOpenApiService")
    private EgovOpenApiService egovOpenApiService;

    /** EgovPropertyService */
    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;

    /** EgovCmmUseService */
    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService egovCmmUseService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EgovOpenApiController.class);

    /**
     * OpenAPI 연계정보 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param openApiVO
     * @param model
     * @return String OpenAPI 연계정보 목록 조회 화면
     * @throws Exception
    */
    @IncludedMblInfo(name="OPEN-API 연계 서비스",order = 502 ,gid = 50)
    @RequestMapping("/mbl/com/oas/selectOpenApiInquiryHistoryList.mdo")
    public String selectOpenApiInquiryHistoryList(@ModelAttribute("searchVO") OpenApiVO openApiVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        openApiVO.setPageUnit(propertyService.getInt("pageUnit"));
        openApiVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(openApiVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(openApiVO.getPageUnit());
        paginationInfo.setPageSize(openApiVO.getPageSize());

        openApiVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        openApiVO.setLastIndex(paginationInfo.getLastRecordIndex());
        openApiVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = egovOpenApiService.selectOpenApiInquiryHistoryList(openApiVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/oas/EgovOpenApiInquiryHistoryList";
    }

    /**
     * OpenAPI 연계정보 상세조회 Service interface 호출 및 결과를 반환한다.
     * @param openApiVO
     * @param model
     * @return String OpenAPI 연계정보 상세조회 화면
     * @throws Exception
    */
    @RequestMapping("/mbl/com/oas/selectOpenApiInquiryHistory.mdo")
    public String selectOpenApiInquiryHistory(@ModelAttribute("searchVO") OpenApiVO openApiVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        OpenApi openApi = egovOpenApiService.selectOpenApiInquiryHistory(openApiVO);

        model.addAttribute("openApi", openApi);

        return "egovframework/mbl/com/oas/EgovOpenApiInquiryHistoryDetail";
    }

    /**************** 대한민국정부포털 검색 서비스 부분 ****************/

    /**
     * 대한민국정부포털 검색 서비스를 이용한 결과를 반환한다.
     * @param openApiVO
     * @param openApi
     * @param searchOrder
     * @return ModelAndView JsonView
     * @throws Exception
    */
    @SuppressWarnings("deprecation")
    @RequestMapping(value="/mbl/com/oas/selectKoreaGovPortalSearchResultList.mdo")
    public ModelAndView selectKoreaGovPortalSearchResultList(@ModelAttribute("searchVO") OpenApiVO openApiVO, OpenApi openApi,
            @RequestParam("searchOrder") String searchOrder) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");

        openApiVO.setSearchKeyword(URLDecoder.decode(openApiVO.getSearchKeyword(), "UTF-8"));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(openApiVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(openApiVO.getPageUnit());
        paginationInfo.setPageSize(1);

        openApiVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        openApiVO.setLastIndex(paginationInfo.getLastRecordIndex());
        openApiVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        if (openApiVO.getSearchCondition().equals("")) {
            openApiVO.setSearchCondition("site");
            paginationInfo.setTotalRecordCount(0);
            modelAndView.addObject("searchVO", openApiVO);
            modelAndView.addObject("paginationInfo", paginationInfo);

            return modelAndView;
        }

        // 검색어 URL UTF-8 인코딩
        openApiVO.setSearchKeyword(URLEncoder.encode(openApiVO.getSearchKeyword() ,"UTF-8"));

        // 대한민국정부포털 검색서비스 호출 URL 생성
        String service_key = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.OASConfPath"), "koreaGovPortalSearchKey");

        String requestUrl = "";
        requestUrl = "http://www.korea.go.kr/src/support/openapi.do";
        requestUrl += "?service_key=" + service_key;
        requestUrl += "&query=" + openApiVO.getSearchKeyword();
        requestUrl += "&pageSize=" + openApiVO.getPageUnit();
        requestUrl += "&pageNum=" + openApiVO.getPageIndex();
        requestUrl += "&collection=" + openApiVO.getSearchCondition();
        requestUrl += "&searchOrder=" + searchOrder;
        requestUrl += "&searchType=" + "r";

        // 검색어 Decoding
        openApiVO.setSearchKeyword(URLDecoder.decode(openApiVO.getSearchKeyword() ,"UTF-8"));

        // xsd 파일을 통해 만들어진 서비스 특화된 객체에 값을 매핑
        SearchDocument sDoc = null;
        sDoc = (SearchDocument)egovOpenApiService.getOpenApiSvcInfo(requestUrl);
        Search search = sDoc.getSearch();
        TotalResults totalResult = search.getTotalResults();
        noNamespace.ResourceDocument.Resource[] resourceList = totalResult.getCollection().getResourceArray();

        List<String> titleList = new ArrayList<String>();
        List<String> contentsList = new ArrayList<String>();
        List<String> urlList = new ArrayList<String>();
        List<String> platformList = new ArrayList<String>();
        List<String> registdateList = new ArrayList<String>();
        List<String> modifydateList = new ArrayList<String>();
        List<String> downloadsList = new ArrayList<String>();

        for (noNamespace.ResourceDocument.Resource resource : resourceList) {
            if (resource.getTitle() != null) titleList.add(resource.getTitle().trim());
            if (resource.getContents() != null) contentsList.add(resource.getContents().trim());
            if (resource.getLinkurl() != null) urlList.add(resource.getLinkurl().trim().replaceAll("＆", "&"));

            if (openApiVO.getSearchCondition().equals("mobapp")) {
                if (resource.getPlatform() != null) platformList.add(resource.getPlatform().trim());
                if (resource.getRegistdate() != null) registdateList.add(resource.getRegistdate().trim());
                if (resource.getModifydate() != null) modifydateList.add(resource.getModifydate().trim());
                if (resource.getDownloads() != null) downloadsList.add(resource.getDownloads().trim());
            }
        }
        
        DataInputStream resultXML = null;
        
        try{
	        // 검색 이력 Insert
	        URL url = new URL(requestUrl);
	        resultXML = new DataInputStream(new BufferedInputStream(url.openStream()));
	        String tmpStr = "";
	        StringBuffer xmlString = new StringBuffer();
	
	        while ((tmpStr = resultXML.readLine()) != null) {
	            xmlString.append(new String(tmpStr.getBytes("8859_1"), "utf-8"));
	        }
	        
	        resultXML.close();
	
	        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	
	        openApi.setMberId(user.getId());
	        openApi.setOpenApiSvcNm("검색");
	        openApi.setOpenApiProvdInsttNm("대한민국정부포털");
	
	        // 검색 결과가 컬럼 크기를 초과할 경우 잘라서 DB에 insert
	        if (xmlString.toString().getBytes().length > 4000) {
	            openApi.setOpenApiSvcCn(cutStringByByte(xmlString.toString(), 4000));
	        } else {
	            openApi.setOpenApiSvcCn(xmlString.toString());
	        }
	
	        egovOpenApiService.insertOpenApiInquiryHistory(openApi);
	
	        int totCnt = Integer.parseInt(totalResult.getTotalCount());
	
	        paginationInfo.setTotalRecordCount(totCnt);
	
	        modelAndView.addObject("titleList", titleList);
	        modelAndView.addObject("contentsList", contentsList);
	        modelAndView.addObject("urlList", urlList);
	
	        // 모바일앱 검색 결과 항목 추가
	        if (openApiVO.getSearchCondition().equals("mobapp")) {
	            modelAndView.addObject("platformList", platformList);
	            modelAndView.addObject("registdateList", registdateList);
	            modelAndView.addObject("modifydateList", modifydateList);
	            modelAndView.addObject("downloadsList", downloadsList);
	        }
	
	        modelAndView.addObject("resultCnt", totCnt);
	        modelAndView.addObject("paginationInfo", paginationInfo);
	    //2017.03.02 조성원 시큐어코딩(ES)-부적절한 자원 해제[CWE-404]
        } catch(IOException e) {
			LOGGER.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
		} finally{
			if (resultXML != null){
				try{
					resultXML.close();
				} catch (IOException e){
					LOGGER.error("["+e.getClass()+"] Try/Catch...IOException : " + e.getMessage());
                } catch (Exception e) {
                	LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
				}
			}
		}

        return modelAndView;
    }

    /**
     * 대한민국정부포털 검색서비스 화면으로 이동한다.
     * @return String 대한민국정부포털 검색서비스 화면
     * @throws Exception
    */
    @IncludedMblInfo(name="OPEN-API 연계 서비스_대한민국정부포털 검색",order = 403 ,gid = 40)
    @RequestMapping(value="/mbl/com/oas/goKoreaGovPortalSearchResultList.mdo")
    public String goKoreaGovPortalSearchResultList(@ModelAttribute("searchVO") OpenApiVO openApiVO) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        return "egovframework/mbl/com/oas/EgovMobileKoreaGovPortalSearchResultList";
    }

    /**
     * 대한민국정부포털 검색서비스 행정용어 상세화면으로 이동한다.
     * @return String 대한민국정부포털 검색서비스 행정용어 상세화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/oas/goAdministrationWordDetail.mdo")
    public String goAdministrationWordDetail(@RequestParam("title") String title,
            @RequestParam("contents") String contents, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        model.addAttribute("title", title);
        model.addAttribute("contents", contents);

        return "egovframework/mbl/com/oas/EgovMobileKoreaGovPortalSearchResultDetail";
    }

    /**
     * 바이트 단위로 문자열을 자른다.
     * @param str
     * @param endIndex
     * @return String 바이트로 잘린 문자열
    */
    private static String cutStringByByte(String str, int endIndex) {
        if (str.toString().getBytes().length < 0) {
            return "";
        } else if (str.toString().getBytes().length < endIndex) {
            return str;
        }

        StringBuffer tempStr = new StringBuffer(endIndex);
        int totalIndex = 0;

        for(char c: str.toCharArray()) {
            totalIndex += String.valueOf(c).getBytes().length;

            if (totalIndex > endIndex) {
                break;
            }

            tempStr.append(c);
        }

        return tempStr.toString();
    }

    /**************** 기상청 날씨 조회 서비스 부분 ****************/

    /**
     * 기상청 날씨 조회서비스를 통해 날씨를 조회한다.
     * @return String 기상청 날씨 조회서비스 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/oas/selectWeather.mdo")
    public ModelAndView selectWeather() throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");

        SurfaceServiceImplService_Impl impl = new SurfaceServiceImplService_Impl();
        SurfaceServiceImpl_Stub service = (SurfaceServiceImpl_Stub)impl.getSurfaceService();

        // 인증 ID, PW 취득
        String serviceId= EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.OASConfPath"), "kmaWeatherInquiryId");
        String servicePw = EgovProperties.getProperty(EgovProperties.getPathProperty("Globals.OASConfPath"), "kmaWeatherInquiryPassword");

        // 인증 ID, PW 설정
        service._setProperty(javax.xml.rpc.Stub.USERNAME_PROPERTY, serviceId);
        service._setProperty(javax.xml.rpc.Stub.PASSWORD_PROPERTY, servicePw);

        // 지역코드 취득
        ComDefaultCodeVO comDefaultCodeVO = new ComDefaultCodeVO();
        comDefaultCodeVO.setCodeId("COM086");
        List<CmmnDetailCode> stationId = egovCmmUseService.selectCmmCodeDetail(comDefaultCodeVO);

        CurrentWeatherModel weatherModel = null;
        List<CurrentWeatherModel> weatherInfo = new ArrayList<CurrentWeatherModel>();

        // 조회 내용을 저장하기 위한 임시 변수
        StringBuffer weather = new StringBuffer();

        // 날씨 정보 취득
        for (int i=0; i < stationId.size(); i++) {
            weatherModel = service.getCurrentWeather(stationId.get(i).getCode().toString(), null);
            weatherInfo.add(weatherModel);

            // 날씨 조회 내용을 날씨 이력 테이블에 insert 하기 위한 내용 구성
            weather.append("지역명 : " + stationId.get(i).getCodeNm());
            weather.append(" / ");
            weather.append("날씨 : " + weatherModel.getCurrent_weather());
            weather.append(" / ");
            weather.append("온도 : " + weatherModel.getTemperature() + "˚C");
            weather.append(" / ");
            weather.append("전운량 : " + weatherModel.getCloud_amount());
            weather.append(" / ");
            weather.append("현상번호 : " + weatherModel.getWeather_status_number());
            weather.append("<br>");
        }

        // 검색 이력 Insert
        OpenApi openApi = new OpenApi();
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        openApi.setMberId(user.getId());
        openApi.setOpenApiSvcNm("날씨");
        openApi.setOpenApiProvdInsttNm("기상청");
        openApi.setOpenApiSvcCn(weather.toString());
        egovOpenApiService.insertOpenApiInquiryHistory(openApi);

        modelAndView.addObject("weatherInfo", weatherInfo);
        modelAndView.addObject("stationId", stationId);
        modelAndView.addObject("count", stationId.size());

        return modelAndView;
    }

    /**
     * 기상청 날씨 조회서비스 화면으로 이동한다.
     * @return String 기상청 날씨 조회서비스 화면
     * @throws Exception
    */
    @IncludedMblInfo(name="OPEN-API 연계 서비스_기상청 날씨 조회",order = 404 ,gid = 40)
    @RequestMapping(value="/mbl/com/oas/goWeatherInqire.mdo")
    public String goWeatherInqire() throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        return "egovframework/mbl/com/oas/EgovMobileWeatherInqire";
    }
}