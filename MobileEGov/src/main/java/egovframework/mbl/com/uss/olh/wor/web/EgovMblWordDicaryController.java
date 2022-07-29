package egovframework.mbl.com.uss.olh.wor.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.olh.wor.service.EgovWordDicaryService;
import egovframework.com.uss.olh.wor.service.WordDicaryDefaultVO;
import egovframework.com.uss.olh.wor.service.WordDicaryVO;
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
 * 용어사전을 처리하는 Controller 클래스
 * @author 이율경
 * @since 2011.08.02
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.02  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblWordDicaryController {

    @Resource(name = "WordDicaryService")
    private EgovWordDicaryService wordDicaryService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /**
     * 용어사전목록 화면을 출력한다.
     * @param searchVO
     * @param model
     * @return	"/mbl/com/uss/olh/wor/EgovWordDicaryListInqire"
     * @throws Exception
     */
    @IncludedMblInfo(name="용어사전",order = 205 ,gid = 20)
    @RequestMapping(value="/uss/olh/wor/WordDicaryListInqire.mdo")
    public String mainWordDicaryList(@ModelAttribute("searchVO") WordDicaryDefaultVO searchVO,
            ModelMap model)
    throws Exception {

        return "egovframework/mbl/com/uss/olh/wor/EgovWordDicaryListInqire";
    }


	/**
     * 용어사전목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	modelAndView
     * @throws Exception
     */
    @RequestMapping(value="/uss/olh/wor/WordDicaryListInqireActor.mdo")
    public ModelAndView selectWordDicaryList(
    		@ModelAttribute("searchVO") WordDicaryDefaultVO searchVO, ModelMap model)
    		throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	/** EgovPropertyService.WordDicaryList */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** paging setting */
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

        List<?> wordDicaryList = wordDicaryService.selectWordDicaryList(searchVO);

        modelAndView.addObject("wordDicaryList", wordDicaryList);
        modelAndView.addObject("listSize", wordDicaryList.size());

        /** Paging */
        int totCnt = wordDicaryService.selectWordDicaryListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);

        modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
    }


    /**
     * 용어사전 목록에 대한 상세정보를 조회한다.
     * @param wordId
     * @param searchVO
     * @param model
     * @return	"/mbl/com/uss/olh/wor/EgovWordDicaryDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/wor/WordDicaryDetailInqire.mdo")
    public String	selectWordDicaryDetail(
    		@RequestParam("wordId") String wordId,
            @ModelAttribute("searchVO") WordDicaryDefaultVO searchVO,
            ModelMap model) throws Exception {

    	/** 조회 */
    	WordDicaryVO wordDicaryVO = new WordDicaryVO();
    	wordDicaryVO.setWordId(wordId);
		WordDicaryVO vo = wordDicaryService.selectWordDicaryDetail(wordDicaryVO);

		model.addAttribute("result", vo);

        return "egovframework/mbl/com/uss/olh/wor/EgovWordDicaryDetailInqire";
    }

    /**
     * 마이페이지용 Main 용어사전목록을 조회한다.
     * @param searchVO
     * @param model
     * @return	mbl/com/uss/olh/wor/EgovWordDicaryMainList
     * @throws Exception
     */
    @RequestMapping(value="/uss/olh/wor/WordDicaryMainList.mdo")
    public String selectWordDicaryMainList(
    		@ModelAttribute("searchVO") WordDicaryDefaultVO searchVO, ModelMap model)
    		throws Exception {

    	/** EgovPropertyService.WordDicaryList */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(3);
		paginationInfo.setPageSize(1);

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> wordDicaryList = wordDicaryService.selectWordDicaryList(searchVO);

        model.addAttribute("wordDicaryList", wordDicaryList);
        model.addAttribute("listSize", wordDicaryList.size());

        /** Paging */
        int totCnt = wordDicaryService.selectWordDicaryListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/uss/olh/wor/EgovWordDicaryMainList";
    }
}
