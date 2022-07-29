package egovframework.mbl.com.cop.smt.sam.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cop.smt.sam.service.EgovAllSchdulManageService;
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
 * 전체일정을 처리하는 Controller Class 구현
 * @author 공통서비스 장동한
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  장동한          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblAllSchdulManageController {

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "egovAllSchdulManageService")
	private EgovAllSchdulManageService egovAllSchdulManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }
    /**
     * 메뉴를 조회한다.
     * @param model
     * @return	"/cop/smt/sam/AllSchdulList.mdo"
     * @throws Exception
     */
    @IncludedMblInfo(name="전체일정 목록",order = 109 ,gid = 10)
    @RequestMapping(value="/cop/smt/sam/EgovAllSchdulManageList.mdo")
    public String mainBoard(@ModelAttribute("searchVO") ComDefaultVO searchVO,
            ModelMap model)
    throws Exception {

        return "egovframework/mbl/com/cop/smt/sam/EgovAllSchdulManageList";

    }

	/**
	 * 전체일정을(를) 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qrm/EgovAllSchdulManageList"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/cop/smt/sam/EgovAllSchdulManageListView.mdo")
	public ModelAndView EgovAllSchdulManageList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");
		String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");

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

        List<?> AllSchdulList = egovAllSchdulManageService.selectAllSchdulManageeList(searchVO);
        modelAndView.addObject("AllSchdulList", AllSchdulList);

        int totCnt = egovAllSchdulManageService.selectAllSchdulManageListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		modelAndView.addObject("paginationInfo", paginationInfo);

		return modelAndView;
	}

}


