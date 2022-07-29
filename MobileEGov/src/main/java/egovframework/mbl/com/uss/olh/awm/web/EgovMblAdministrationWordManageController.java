package egovframework.mbl.com.uss.olh.awm.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.olh.awm.service.AdministrationWordManage;
import egovframework.com.uss.olh.awm.service.EgovAdministrationWordManageService;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 행정전문용어사전을 처리하는 Controller 클래스
 * @author 이율경
 * @since 2011.08.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.09  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblAdministrationWordManageController {

    /** EgovMessageSource */
    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** egovOnlinePollService */
    @Resource(name = "egovAdministrationWordManageService")
    private EgovAdministrationWordManageService egovAdministrationWordManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /**
     * 행정전문용어사전목록 화면을 출력한다.
     * @param searchVO
     * @param model
     * @return	"mbl/com/uss/olh/awa/EgovAdministrationWordList"
     * @throws Exception
     */
    @IncludedMblInfo(name="행정전문용어사전",order = 208 ,gid = 20)
    @RequestMapping(value="/uss/olh/awa/listAdministrationWord.mdo")
    public String mainAdministrationWordList(@ModelAttribute("searchVO") AdministrationWordManage searchVO,
            ModelMap model)
    throws Exception {

        return "egovframework/mbl/com/uss/olh/awa/EgovAdministrationWordList";
    }

    /**
     * 행정전문용어사전목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return modelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/uss/olh/awa/listAdministrationWordActor.mdo")
    public ModelAndView egovAdministrationWordManageList(
            @RequestParam Map<?, ?> commandMap,
            @ModelAttribute("searchVO") AdministrationWordManage searchVO,
            ModelMap model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** pageing */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(1);

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> resultList = egovAdministrationWordManageService.selectAdministrationWordManageList(searchVO);
        modelAndView.addObject("resultList", resultList);

        int totCnt = egovAdministrationWordManageService.selectAdministrationWordManageListCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
    }

    /**
     * 행정전문용어사전 목록을 상세조회 조회한다.
     * @param administrationWordManage
     * @param commandMap
     * @param model
     * @return "egovframework/com/uss/olh/awa/EgovAdministrationWordDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/olh/awa/detailAdministrationWord.mdo")
    public String egovAdministrationWordDetail(
            @ModelAttribute("searchVO") AdministrationWordManage administrationWordManage,
            @RequestParam Map<?, ?> commandMap,
            ModelMap model) throws Exception {

        AdministrationWordManage administrationWordManageVO = egovAdministrationWordManageService.selectAdministrationWordManageDetail(administrationWordManage);
        model.addAttribute("administrationWordManage", administrationWordManageVO);

        return "egovframework/mbl/com/uss/olh/awa/EgovAdministrationWordDetail";
    }

    /**
     * 마이페이지에 Main으로 출력할 행정전문용어사전목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return "egovframework/com/uss/olh/awa/EgovAdministrationWordMainList"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/olh/awa/listAdministrationWordMain.mdo")
    public String egovAdministrationWordManageMainList(@RequestParam Map<?, ?> commandMap,
            @ModelAttribute("searchVO") AdministrationWordManage searchVO,
            ModelMap model)
            throws Exception {

        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** pageing */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(3);
        paginationInfo.setPageSize(1);

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> resultList = egovAdministrationWordManageService.selectAdministrationWordManageList(searchVO);
        model.addAttribute("resultList", resultList);

        int totCnt = egovAdministrationWordManageService.selectAdministrationWordManageListCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/uss/olh/awa/EgovAdministrationWordMainList";
    }
}
