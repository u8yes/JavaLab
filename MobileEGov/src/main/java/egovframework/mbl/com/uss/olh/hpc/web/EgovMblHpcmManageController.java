package egovframework.mbl.com.uss.olh.hpc.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.olh.hpc.service.EgovHpcmManageService;
import egovframework.com.uss.olh.hpc.service.HpcmManageDefaultVO;
import egovframework.com.uss.olh.hpc.service.HpcmManageVO;
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
 * 도움말을 처리하는 비즈니스 구현 클래스
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
public class EgovMblHpcmManageController {

    @Resource(name = "HpcmManageService")
    private EgovHpcmManageService hpcmManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /**
     * 개별 배포시 메인메뉴를 조회한다.
     * @param model
     * @return	"/uss/olh/hpc/"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olh/hpc/EgovMain.mdo")
    public String EgovMain(ModelMap model) throws Exception {
    	return "egovframework/mbl/com/uss/olh/hpc/EgovMain";
    }

    /**
     * 메뉴를 조회한다.
     * @param model
     * @return	"/uss/olh/hpc/EgovLeft"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olh/hpc/EgovLeft.mdo")
    public String EgovLeft(ModelMap model) throws Exception {
    	return "egovframework/mbl/com//uss/olh/hpc/EgovLeft";
    }

    /**
     * json데이터를 호출하기 위한 페이지를 호출한다.
     * @param commandMap
     * @param model
     * @return
     *         "/uss/olh/omm/EgovOnlineManual"
     * @throws Exception
     */
    @IncludedMblInfo(name="도움말",order = 203 ,gid = 20)
    @RequestMapping(value="/uss/olh/hpc/HpcmListInqire.mdo")
    public String mainBoard(@ModelAttribute("searchVO") HpcmManageDefaultVO searchVO, ModelMap model)
    throws Exception {

		return "egovframework/mbl/com/uss/olh/hpc/EgovHpcmListInqire";

    }

    /**
     * 도움말내용 목록을 조회한다. (pageing)
     * @param searchVO
     * @param model
     * @return	"/uss/olh/hpc/EgovHpcmListInqire"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olh/hpc/HpcmListView.mdo")
    public ModelAndView selectHpcmList(@ModelAttribute("searchVO") HpcmManageDefaultVO searchVO, ModelMap model) throws Exception {

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

        List<?> HpcmList = hpcmManageService.selectHpcmList(searchVO);
        //model.addAttribute("resultList", HpcmList);
        modelAndView.addObject("reusltList", HpcmList);
		modelAndView.addObject("listSize", HpcmList.size());

        int totCnt = hpcmManageService.selectHpcmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        //model.addAttribute("paginationInfo", paginationInfo);
        modelAndView.addObject("paginationInfo", paginationInfo);
        return modelAndView;
    }

    /**
     * 도움말내용 목록에 대한 상세정보를 조회한다.
     * @param hpcmManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/hpc/EgovHpcmDetailInqire"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/hpc/HpcmDetailInqire.mdo")
    public String	selectHpcmDetail(HpcmManageVO hpcmManageVO,
            @ModelAttribute("searchVO") HpcmManageDefaultVO searchVO,
            @RequestParam("hpcmId") String hpcmId ,
            ModelMap model) throws Exception {

    	hpcmManageVO.setHpcmId(hpcmId);

		HpcmManageVO vo = hpcmManageService.selectHpcmDetail(hpcmManageVO);

		model.addAttribute("result", vo);

        return	"egovframework/mbl/com/uss/olh/hpc/EgovHpcmDetailInqire";
    }

}
