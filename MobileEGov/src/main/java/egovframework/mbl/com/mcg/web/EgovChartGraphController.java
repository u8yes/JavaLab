package egovframework.mbl.com.mcg.web;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mcg.service.ChartGraph;
import egovframework.mbl.com.mcg.service.ChartGraphVO;
import egovframework.mbl.com.mcg.service.EgovChartGraphService;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * - 차트/그래프 데이터에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 차트/그래프 데이터에 대한 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 정홍규
 * @since 2011.08.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.16  정홍규          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovChartGraphController {

    /**
     * EgovChartGraphService
     */
    @Resource(name = "ChartGraphService")
    private EgovChartGraphService chartGraphService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 모바일 차트/그래프 화면으로 이동한다.
     * @param model
     * @return "/mbl/com/mcg/EgovMobileChartGraph"
     * @throws Exception
     */
    @IncludedMblInfo(name="모바일 차트/그래프",order = 405 ,gid = 40)
    @RequestMapping(value = "/mbl/com/mcg/goMobileChartGraph.mdo")
    public String goMobileChartGraph(ModelMap model) throws Exception {
        return "egovframework/mbl/com/mcg/EgovMobileChartGraph";
    }

    /**
     * 모바일 차트/그래프 데이터 목록 조회 Service interface 호출 및 결과를
     * 반환한다.(JSON 통신)
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/selectMobileChartGraphList.mdo")
    public ModelAndView selectMobileChartGraphList(ModelMap model)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");

        ChartGraphVO searchVO = new ChartGraphVO();

        searchVO.setFirstIndex(0);
        searchVO.setRecordCountPerPage(1000);

        List<?> ChartGraphList = chartGraphService.selectChartGraphList(searchVO);
        modelAndView.addObject("resultList", ChartGraphList);

        return modelAndView;
    }

    /**
     * 차트/그래프 데이터 등록 화면으로 이동한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mcg/EgovChartGraphRegist"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/goChartGraphRegist.mdo")
    public String goChartGraphRegist(
            @ModelAttribute("searchVO") ChartGraphVO searchVO, ModelMap model)
            throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        model.addAttribute("chartGraph", new ChartGraph());
        return "egovframework/mbl/com/mcg/EgovChartGraphRegist";
    }

    /**
     * 차트/그래프 데이터 등록 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param chartGraph
     * @param bindingResult
     * @param model
     * @return
     *         "forward:/mbl/com/mcg/selectChartGraphList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/insertChartGraph.mdo")
    public String insertChartGraph(
            @ModelAttribute("searchVO") ChartGraphVO searchVO,
            @ModelAttribute("chartGraph") ChartGraph chartGraph,
            BindingResult bindingResult, ModelMap model) throws Exception {

    	 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        beanValidator.validate(chartGraph, bindingResult);

        if (bindingResult.hasErrors()) {
            return "egovframework/mbl/com/mcg/EgovChartGraphRegist";
        }

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        chartGraph.setMberId(mberId);

        chartGraphService.insertChartGraph(chartGraph);
        return "forward:/mbl/com/mcg/selectChartGraphList.mdo";
    }

    /**
     * 차트/그래프 데이터 목록 조회 Service interface 호출 및 결과를
     * 반환한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mcg/EgovChartGraphList"
     * @throws Exception
     */
    @IncludedMblInfo(name="모바일 차트/그래프",order = 503 ,gid = 50)
    @RequestMapping(value = "/mbl/com/mcg/selectChartGraphList.mdo")
    public String selectChartGraphList(
            @ModelAttribute("searchVO") ChartGraphVO searchVO, ModelMap model)
            throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** paging */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> ChartGraphList = chartGraphService.selectChartGraphList(searchVO);
        model.addAttribute("resultList", ChartGraphList);

        int totCnt = chartGraphService.selectChartGraphListTotCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mcg/EgovChartGraphList";
    }

    /**
     * 차트/그래프 상세정보 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param chartGraph
     * @param model
     * @return "/mbl/com/mcg/EgovChartGraphDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/selectChartGraph.mdo")
    public String selectChartGraph(
            @ModelAttribute("searchVO") ChartGraphVO searchVO,
            ChartGraph chartGraph, ModelMap model) throws Exception {

    	 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        ChartGraph vo = chartGraphService.selectChartGraph(chartGraph);
        model.addAttribute("result", vo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mcg/EgovChartGraphDetail";
    }

    /**
     * 차트/그래프 데이터 수정 화면으로 이동한다.
     * @param searchVO
     * @param chartGraph
     * @param model
     * @return "/mbl/com/mcg/EgovChartGraphUpdt"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/goChartGraphUpdt.mdo")
    public String goChartGraphUpdt(
            @ModelAttribute("searchVO") ChartGraphVO searchVO,
            ChartGraph chartGraph, ModelMap model) throws Exception {

    	 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        ChartGraph vo = chartGraphService.selectChartGraph(chartGraph);
        model.addAttribute("chartGraph", vo);

        return "egovframework/mbl/com/mcg/EgovChartGraphUpdt";
    }

    /**
     * 차트/그래프 데이터 수정 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param chartGraph
     * @param model
     * @return
     *         "forward:/mbl/com/mcg/selectChartGraphList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/updateChartGraph.mdo")
    public String updateChartGraph(
            final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") ChartGraphVO searchVO,
            @ModelAttribute("chartGraph") ChartGraph chartGraph,
            BindingResult bindingResult, ModelMap model) throws Exception {

    	 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        // Validation
        beanValidator.validate(chartGraph, bindingResult);

        if (bindingResult.hasErrors()) {
            return "egovframework/mbl/com/mcg/EgovChartGraphUpdt";

        }

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        chartGraph.setMberId(mberId);

        chartGraphService.updateChartGraph(chartGraph);
        return "forward:/mbl/com/mcg/selectChartGraphList.mdo";
    }

    /**
     * 차트/그래프 데이터 삭제 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param chartGraph
     * @return
     *         "forward:/mbl/com/mcg/selectChartGraphList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mcg/deleteChartGraph.mdo")
    public String deleteChartGraph(
            @ModelAttribute("searchVO") ChartGraphVO chartGraphVO,
            ChartGraph chartGraph) throws Exception {

    	 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	// 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        chartGraph.setMberId(mberId);

        chartGraphService.deleteChartGraph(chartGraph);
        return "forward:/mbl/com/mcg/selectChartGraphList.mdo";
    }
}
