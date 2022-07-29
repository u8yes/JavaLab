package egovframework.mbl.com.uss.sam.stp.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.sam.stp.service.EgovStplatManageService;
import egovframework.com.uss.sam.stp.service.StplatManageDefaultVO;
import egovframework.com.uss.sam.stp.service.StplatManageVO;
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
 * 약관내용을 처리하는 비즈니스 구현 클래스
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
public class EgovMblStplatManageController {

    @Resource(name = "StplatManageService")
    private EgovStplatManageService stplatManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /**
     * 개별 배포시 메인메뉴를 조회한다.
     * @param model
     * @return	"/uss/sam/stp/EgovMain"
     * @throws Exception
     */
    @RequestMapping(value="/uss/sam/stp/EgovMain.mdo")
    public String EgovMain(ModelMap model) throws Exception {
    	return "egovframework/mbl/com/uss/sam/stp/EgovMain";
    }

    /**
     * 메뉴를 조회한다.
     * @param model
     * @return	"/uss/sam/stp/EgovLeft"
     * @throws Exception
     */
    @RequestMapping(value="/uss/sam/stp/EgovLeft.mdo")
    public String EgovLeft(ModelMap model) throws Exception {
    	return "egovframework/mbl/com/uss/sam/stp/EgovLeft";
    }

    /**
     * json데이터를 호출하기 위한 페이지를 호출한다.
     * @param commandMap
     * @param model
     * @return
     *         "/uss/olh/omm/EgovOnlineManual"
     * @throws Exception
     */
    @IncludedMblInfo(name="약관",order = 200 ,gid = 20)
    @RequestMapping(value="/uss/sam/stp/StplatListInqire.mdo")
    public String mainBoard(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model)
    throws Exception {

    	return "egovframework/mbl/com/uss/sam/stp/EgovStplatListInqire";

    }

    /**
     * 약관정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	"/uss/sam/stp/EgovStplatListInqire"
     * @throws Exception
     */
    @RequestMapping(value="/uss/sam/stp/StplatList.mdo")
    public ModelAndView selectStplatList(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	/** EgovPropertyService.SiteList */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> StplatList = stplatManageService.selectStplatList(searchVO);
        //model.addAttribute("resultList", StplatList);

        modelAndView.addObject("reusltList", StplatList);
        modelAndView.addObject("listSize", StplatList.size());

        int totCnt = stplatManageService.selectStplatListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        //model.addAttribute("paginationInfo", paginationInfo);
		modelAndView.addObject("paginationInfo", paginationInfo);

		return modelAndView;
    }

    /**
     * 약관정보상세내용을 조회한다.
     * @param stplatManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/sam/stp/EgovStplatDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/sam/stp/StplatDetailInqire.mdo")
    public String	selectStplatDetail(StplatManageVO stplatManageVO,
            @ModelAttribute("searchVO") StplatManageDefaultVO searchVO,
            ModelMap model) throws Exception {

		StplatManageVO vo = stplatManageService.selectStplatDetail(stplatManageVO);

		model.addAttribute("result", vo);

        return	"egovframework/mbl/com/uss/sam/stp/EgovStplatDetailInqire";
    }

}
