package egovframework.mbl.com.uss.olh.faq.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olh.faq.service.EgovFaqManageService;
import egovframework.com.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.com.uss.olh.faq.service.FaqManageVO;
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
 * FAQ내용을 처리하는 비즈니스 구현 클래스
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
public class EgovMblFaqManageController {

    @Resource(name = "FaqManageService")
    private EgovFaqManageService faqManageService;

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
     * 메뉴를 조회한다.
     * @param model
     * @return	"/egovframework/uss/olh/faq/EgovLeft"
     * @throws Exception
     */
    @IncludedMblInfo(name="FAQ",order = 206 ,gid = 20)
    @RequestMapping(value="/uss/olh/faq/FaqListInqire.mdo")
    public String mainBoard(@ModelAttribute("searchVO") FaqManageDefaultVO searchVO,
            ModelMap model)
    throws Exception {

        return "egovframework/mbl/com/uss/olh/faq/EgovFaqListInqire";

    }
    /**
     * FAQ 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	"/egovframework/uss/olh/faq/EgovFaqListInqire"
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping(value="/uss/olh/faq/FaqListInqireView.mdo")
    public ModelAndView selectFaqList(@ModelAttribute("searchVO") FaqManageDefaultVO searchVO, ModelMap model) throws Exception {
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

    		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
    		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

            //List FaqList = faqManageService.selectFaqList(searchVO);
            //model.addAttribute("resultList", FaqList);
            List<?> FaqList = faqManageService.selectFaqList(searchVO);
    		modelAndView.addObject("FaqList", FaqList);
    		modelAndView.addObject("listSize", FaqList.size());

            int totCnt = faqManageService.selectFaqListTotCnt(searchVO);
    		paginationInfo.setTotalRecordCount(totCnt);
    		modelAndView.addObject("paginationInfo", paginationInfo);

    		model.addAttribute("paginationInfo", paginationInfo);

            return modelAndView;
        }


    /**
     * FAQ 목록에 대한 상세정보를 조회한다.
     * @param faqManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/faq/EgovFaqDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/faq/FaqListDetailInqire.mdo")
    public String	selectFaqListDetail(FaqManageVO faqManageVO,
            @ModelAttribute("searchVO") FaqManageDefaultVO searchVO,
            ModelMap model) throws Exception {

		FaqManageVO vo = faqManageService.selectFaqListDetail(faqManageVO);

		model.addAttribute("result", vo);

        return	"egovframework/mbl/com/uss/olh/faq/EgovFaqDetailInqire";
    }

    /**
     * FAQ 조회수를  수정처리
     * @param faqManageVO
     * @param searchVO
     * @return	"forward:/uss/olh/faq/FaqListDetailInqire.do"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/faq/FaqInqireCoUpdt.mdo")
    public String updateFaqInqireCo(
            FaqManageVO faqManageVO,
            @ModelAttribute("searchVO") FaqManageDefaultVO searchVO)
            throws Exception {

    	// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

    	String	lastUpdusrId = loginVO.getUniqId();

    	faqManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID

    	faqManageService.updateFaqInqireCo(faqManageVO);

        return "forward:/uss/olh/faq/FaqListDetailInqire.mdo";

    }
}
