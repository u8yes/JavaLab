package egovframework.mbl.com.uss.olh.omm.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.uss.olh.omm.service.EgovOnlineManualService;
import egovframework.com.uss.olh.omm.service.OnlineManual;
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
 * 온라인메뉴얼를 처리하는 Controller Class 구현
 * @author 공통서비스 장동한
 * @since 2009.07.03
 * @version 1.0
 * @see <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.07.03  장동한          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblOnlineManualController {

    /** EgovMessageSource */
    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** egovOnlinePollService */
    @Resource(name = "egovOnlineManualService")
    private EgovOnlineManualService egovOnlineManualService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** Egov Common Code Service */
    @Resource(name="EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    /**
     * json데이터를 호출하기 위한 페이지를 호출한다.
     * @param commandMap
     * @param model
     * @return
     *         "/uss/olh/omm/EgovOnlineManual"
     * @throws Exception
     */
    @IncludedMblInfo(name="온라인매뉴얼",order = 209 ,gid = 20)
    @RequestMapping(value="/uss/olh/omm/listOnlineManual.mdo")
    public String mainBoard(@ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map<?, ?> commandMap,
            OnlineManual onlineManual, ModelMap model)
    throws Exception {
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("onlineMnlId", commandMap.get("onlineMnlId") == null ? "" : (String) commandMap.get("onlineMnlId"));

		return "egovframework/mbl/com/uss/olh/omm/EgovOnlineManualList";

    }

    /**
     * 사용자 온라인메뉴얼 조회한다.
     * @param commandMap
     * @param model
     * @return
     *         "/uss/olh/omm/EgovOnlineManual"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/olh/omm/setOnlineManual.mdo")
    public String EgovOnlineManualUserDetail(
            @RequestParam Map<?, ?> commandMap,
            ModelMap model) throws Exception {

        String sLocationUrl = "egovframework/mbl/com/uss/olh/omm/EgovOnlineManual";

        model.addAttribute("onlineMnlId", commandMap.get("onlineMnlId") == null ? "" : (String) commandMap.get("onlineMnlId"));

        return sLocationUrl;
    }

    /**
     * 사용자 온라인메뉴얼 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param onlineManual
     * @param model
     * @return "/uss/olh/omm/EgovOnlineManualList"
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping(value = "/uss/olh/omm/listOnlineManualView.mdo")
    public ModelAndView EgovOnlineManualUserList(
            @ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map<?, ?> commandMap,
            OnlineManual onlineManual, ModelMap model)
            throws Exception {
    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

        String sSearchMode = commandMap.get("searchMode") == null ? "" : (String) commandMap.get("searchMode");

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

        List<?> reusltList = egovOnlineManualService.selectOnlineManualList(searchVO);
        //model.addAttribute("resultList", reusltList);
        modelAndView.addObject("reusltList", reusltList);
		modelAndView.addObject("listSize", reusltList.size());

       // model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String) commandMap.get("searchKeyword"));
        //model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String) commandMap.get("searchCondition"));
        modelAndView.addObject("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String) commandMap.get("searchKeyword"));
        modelAndView.addObject("searchCondition", commandMap.get("searchCondition") == null ? "" : (String) commandMap.get("searchCondition"));

        int totCnt = egovOnlineManualService.selectOnlineManualListCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        //model.addAttribute("paginationInfo", paginationInfo);
        modelAndView.addObject("paginationInfo", paginationInfo);
        //온라인메뉴얼 구분 설정
        ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
        voComCode = new ComDefaultCodeVO();

        voComCode.setCodeId("COM041");
        List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);

       // model.addAttribute("onlineMnlSeCodeList", listComCode );
        modelAndView.addObject("onlineMnlSeCodeList", listComCode );
        //modelAndView.addObject("searchVO", searchVO );
        return modelAndView;
    }

    /**
     * 사용자 온라인메뉴얼 목록을 상세조회 조회한다.
     * @param searchVO
     * @param onlineManual
     * @param commandMap
     * @param model
     * @return
     *         "/uss/olh/omm/EgovOnlineManualDetail"
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping(value = "/uss/olh/omm/detailOnlineManual.mdo")
    public String EgovOnlineManualUserDetail(
            @ModelAttribute("searchVO") ComDefaultVO searchVO,
            OnlineManual onlineManual, @RequestParam Map<?, ?> commandMap,
            @RequestParam("onlineMnlId") String onlineMnlId ,
            ModelMap model) throws Exception {

    	onlineManual.setOnlineMnlId(onlineMnlId);

        String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");

        OnlineManual reusltList = egovOnlineManualService.selectOnlineManualDetail(onlineManual);
        model.addAttribute("onlineManual", reusltList);

        //온라인메뉴얼 구분 설정
        ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
        voComCode = new ComDefaultCodeVO();
        voComCode.setCodeId("COM041");
        voComCode.setCodeNm(reusltList.getOnlineMnlSeCode());
        List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
        model.addAttribute("onlineMnlSeCodeList", listComCode );
        model.addAttribute("searchVO", searchVO );

        return "egovframework/mbl/com/uss/olh/omm/EgovOnlineManualDetail";

    }
}
