package egovframework.mbl.com.uss.ion.nws.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.nws.service.EgovNewsManageService;
import egovframework.com.uss.ion.nws.service.NewsManageDefaultVO;
import egovframework.com.uss.ion.nws.service.NewsManageVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



/**
 *
 * 뉴스정보를 처리하는 Controller 클래스
 * @author 공통서비스 개발팀 박정규
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.01  박정규          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblNewsManageController {

    @Resource(name = "NewsManageService")
    private EgovNewsManageService newsManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

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
     * 개별 배포시 메인메뉴를 조회한다.
     * @param model
     * @return	"/uss/ion/nws/"
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/nws/EgovMain.mdo")
    public String EgovMain(ModelMap model) throws Exception {
    	return "egovframework/mbl/com/uss/ion/nws/EgovMain";
    }

    /**
     * 메뉴를 조회한다.
     * @param model
     * @return	"/uss/ion/nws/EgovLeft"
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/nws/EgovLeft.mdo")
    public String EgovLeft(ModelMap model) throws Exception {
    	return "egovframework/mbl/com/uss/ion/nws/EgovLeft";
    }

    @IncludedMblInfo(name="뉴스 목록",order = 210 ,gid = 20)
    @RequestMapping(value="/uss/ion/nws/NewsInfoListInqire.mdo")
    public String mainBoard(@ModelAttribute("searchVO") NewsManageDefaultVO searchVO,
            ModelMap model)
    throws Exception {

        return "egovframework/mbl/com/uss/ion/nws/EgovNewsInfoListInqire";

    }
    /**
     * 뉴스정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	"/uss/ion/nws/EgovNewsInfoListInqire"
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping(value="/uss/ion/nws/NewsInfoListInqireView.mdo")
    public ModelAndView selectNewsList(@ModelAttribute("searchVO") NewsManageDefaultVO searchVO, ModelMap model) throws Exception {
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		ModelAndView modelAndView = new ModelAndView("jsonView");

		searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	/** EgovPropertyService.SiteList */
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

        List<?> NewsList = newsManageService.selectNewsList(searchVO);
        modelAndView.addObject("NewsList", NewsList);
		modelAndView.addObject("listSize", NewsList.size());

        int totCnt = newsManageService.selectNewsListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		modelAndView.addObject("paginationInfo", paginationInfo);

		model.addAttribute("paginationInfo", paginationInfo);
        return modelAndView;
    }

    /**
     * 뉴스정보 목록에 대한 상세정보를 조회한다.
     * @param newsManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/ion/nws/EgovNewsInfoDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/ion/nws/NewsInfoDetailInqire.mdo")
    public String	selectNewsDetail(NewsManageVO newsManageVO,
            @ModelAttribute("searchVO") NewsManageDefaultVO searchVO,
            ModelMap model) throws Exception {

		NewsManageVO vo = newsManageService.selectNewsDetail(newsManageVO);

		model.addAttribute("result", vo);

        return	"egovframework/mbl/com/uss/ion/nws/EgovNewsInfoDetailInqire";
    }

}