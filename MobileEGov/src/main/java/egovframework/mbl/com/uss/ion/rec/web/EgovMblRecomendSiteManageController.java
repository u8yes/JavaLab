package egovframework.mbl.com.uss.ion.rec.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.ion.rec.service.EgovRecomendSiteManageService;
import egovframework.com.uss.ion.rec.service.RecomendSiteManageDefaultVO;
import egovframework.com.uss.ion.rec.service.RecomendSiteManageVO;
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
 * 추천사이트처리를 하는 Mobile Controller 클래스
 * @author 공통컴포넌트전환팀 이율경
 * @since 2011.08.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.16  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblRecomendSiteManageController {

    @Resource(name = "RecomendSiteManageService")
    private EgovRecomendSiteManageService recomendSiteManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;


    /**
     * 추천사이트정보 목록을 조회하기 위한 화면을 출력한다.
     * @param searchVO
     * @param model
     * @return	"mbl/com/uss/ion/rec/EgovRecomendSiteListInqire"
     * @throws Exception
     */
    @IncludedMblInfo(name="추천사이트",order = 213 ,gid = 20)
    @RequestMapping(value="/uss/ion/rec/RecomendSiteListInqire.mdo")
    public String selectRecomendSiteListView(@ModelAttribute("searchVO") RecomendSiteManageDefaultVO searchVO, ModelMap model) throws Exception {

    	return "egovframework/mbl/com/uss/ion/rec/EgovRecomendSiteListInqire";
    }

    /**
     * 추천사이트정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	modelAndView
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/rec/RecomendSiteListInqireActor.mdo")
    public ModelAndView selectRecomendSiteList(@ModelAttribute("searchVO") RecomendSiteManageDefaultVO searchVO, ModelMap model) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	/** EgovPropertyService.SiteList */
    	searchVO.setPageUnit(10);
    	searchVO.setPageSize(1);

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> RecomendSiteList = recomendSiteManageService.selectRecomendSiteList(searchVO);
        modelAndView.addObject("resultList", RecomendSiteList);

        int totCnt = recomendSiteManageService.selectRecomendSiteListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
    }

    /**
     * 추천사이트정보 목록에 대한 상세정보를 조회한다.
     * @param recomendSiteManageVO
     * @param searchVO
     * @param model
     * @return	"mbl/com/uss/ion/rec/EgovRecomendSiteDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/ion/rec/RecomendSiteDetailInqire.mdo")
    public String	selectRecomendSiteDetail(RecomendSiteManageVO recomendSiteManageVO,
            @ModelAttribute("searchVO") RecomendSiteManageDefaultVO searchVO,
            ModelMap model) throws Exception {

		RecomendSiteManageVO vo = recomendSiteManageService.selectRecomendSiteDetail(recomendSiteManageVO);

		model.addAttribute("result", vo);

        return "egovframework/mbl/com/uss/ion/rec/EgovRecomendSiteDetailInqire";
    }

    /**
     * 마이페이지용 Main 추천사이트정보 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	"mbl/com/uss/ion/rec/EgovRecomendSiteMainList"
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/rec/RecomendSiteMainList.mdo")
    public String selectRecomendSiteMainList(@ModelAttribute("searchVO") RecomendSiteManageDefaultVO searchVO, ModelMap model) throws Exception {

    	/** EgovPropertyService.SiteList */
    	searchVO.setPageUnit(3);
    	searchVO.setPageSize(1);

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> RecomendSiteList = recomendSiteManageService.selectRecomendSiteList(searchVO);
        model.addAttribute("resultList", RecomendSiteList);

        int totCnt = recomendSiteManageService.selectRecomendSiteListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/uss/ion/rec/EgovRecomendSiteMainList";
    }
}
