package egovframework.mbl.com.uss.olp.cns.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.cns.service.CnsltManageDefaultVO;
import egovframework.com.uss.olp.cns.service.CnsltManageVO;
import egovframework.com.uss.olp.cns.service.EgovCnsltManageService;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 상담관리를 처리하는 Controller 클래스
 * @author 이율경
 * @since 2011.08.05
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.05  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblCnsltManageController {

    @Resource(name = "CnsltManageService")
    private EgovCnsltManageService cnsltManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /**
     * 상담내역등록 화면을 출력한다.
     * @param searchVO
     * @param cnsltManageVO
     * @param model
     * @return "egovframework/com/uss/olp/cns/EgovCnsltDtlsRegist"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olp/cns/CnsltDtlsRegistView.mdo")
    public String insertCnsltDtlsView(@ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
    		CnsltManageVO cnsltManageVO,
            ModelMap model)
    throws Exception {

        // 로그인VO에서  사용자 정보 가져오기
        LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String	wrterNm 	= loginVO.getName();					// 사용자명
        String	emailAdres 	= loginVO.getEmail();					// email 주소

        cnsltManageVO.setWrterNm(wrterNm);							// 작성자명
        cnsltManageVO.setEmailAdres(emailAdres);    				// email 주소
        cnsltManageVO.setOthbcAt("Y");								// 공개여부 초기화

    	model.addAttribute("cnsltManageVO", cnsltManageVO);

    	return "egovframework/mbl/com/uss/olp/cns/EgovCnsltDtlsRegist";

    }

    /**
     * 상담내역수정 화면을 출력한다.
     * @param searchVO
     * @param model
     * @param cnsltManageVO
     * @return "egovframework/com/uss/olp/cns/EgovCnsltDtlsUpdt"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping(value="/uss/olp/cns/CnsltDtlsUpdtView.mdo")
    public String updateCnsltManage(@ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
    		CnsltManageVO cnsltManageVO,
            ModelMap model)
    throws Exception {

    	CnsltManageVO cnsltManage = cnsltManageService.selectCnsltListDetail(cnsltManageVO);
		String passwordConfirmAt = "Y";

		cnsltManage.setPasswordConfirmAt(passwordConfirmAt);    		// 작성비밀번호 확인여부

		// 작성 비밀번호를 얻는다.
		String	writngPassword = cnsltManage.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
		cnsltManage.setWritngPassword(EgovFileScrty.decode(writngPassword));

		model.addAttribute("cnsltManageVO", cnsltManage);

    	return "egovframework/mbl/com/uss/olp/cns/EgovCnsltDtlsUpdt";

    }

    /**
     * 상담관리목록 조회한다.
     * @param searchVO
     * @param model
     * @return mbl/com/uss/olp/cns/EgovCnsltManageListInqire
     * @throws Exception
     */
    @IncludedMblInfo(name="상담관리",order = 201 ,gid = 20)
    @RequestMapping(value="/uss/olp/cns/CnsltListInqire.mdo")
    public String selectCnsltManageList(
    		@ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
    		ModelMap model)
    throws Exception {

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

        List<?> cnsltManageList = cnsltManageService.selectCnsltList(searchVO);

        model.addAttribute("cnsltManageList", cnsltManageList);
        model.addAttribute("listSize", cnsltManageList.size());

        /** paging */
        int totCnt = cnsltManageService.selectCnsltListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/uss/olp/cns/EgovCnsltManageListInqire";

    }


    /**
     * 상담정보 목록에 대한 상세정보를 조회한다.
     * @param cnsltManageVO
     * @param searchVO
     * @param model
     * @return	"mbl/com/uss/olp/cns/EgovCnsltManageDetailInqire"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olp/cnm/CnsltDetailInqire.mdo")
    public String	selectCnsltListDetail(
    		CnsltManageVO cnsltManageVO,
            @ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
            ModelMap model) throws Exception {

    	// 조회 수 증가
    	cnsltManageService.updateCnsltInqireCo(cnsltManageVO);

    	CnsltManageVO vo = cnsltManageService.selectCnsltListDetail(cnsltManageVO);

		String passwordConfirmAt = "Y";
		vo.setPasswordConfirmAt(passwordConfirmAt);    		// 작성비밀번호 확인여부

		// 작성 비밀번호를 얻는다.
		String	writngPassword = vo.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
    	vo.setWritngPassword(EgovFileScrty.decode(writngPassword));

		model.addAttribute("cnsltManageVO", vo);
		model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/uss/olp/cns/EgovCnsltManageDetailInqire";
    }

    /**
     * 상담정보를 등록한다.
     * @param searchVO
     * @param cnsltManageVO
     * @param model
     * @return "redirect:/uss/olp/cns/CnsltListInqire.mdo"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olp/cns/CnsltDtlsRegist.mdo")
    public String insertCnsltDtls(
            @ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
            @ModelAttribute("cnsltManageVO") CnsltManageVO cnsltManageVO,
            ModelMap model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView();

    	// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	frstRegisterId = loginVO.getUniqId();

    	cnsltManageVO.setFrstRegisterId(frstRegisterId);		// 최초등록자ID
    	cnsltManageVO.setLastUpdusrId(frstRegisterId);    	// 최종수정자ID

    	// 작성비밀번호를 암호화 하기 위해서 Get
    	String	writngPassword = cnsltManageVO.getWritngPassword();

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	cnsltManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

        cnsltManageService.insertCnsltDtls(cnsltManageVO);

        modelAndView.addObject("searchVO", searchVO);

        return "redirect:/uss/olp/cns/CnsltListInqire.mdo";
    }

    /**
     * 상담정보를 수정처리한다.
     * @param searchVO
     * @param cnsltManageVO
     * @param model
     * @return "redirect:/uss/olp/cnm/CnsltListInqire.mdo"
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping("/uss/olp/cns/CnsltDtlsUpdt.mdo")
    public String updateCnsltDtls(@ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
            @ModelAttribute("cnsltManageVO") CnsltManageVO cnsltManageVO,
            ModelMap model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView();

    	// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

    	String	frstRegisterId = loginVO.getUniqId();

    	cnsltManageVO.setFrstRegisterId(frstRegisterId);		// 최초등록자ID
    	cnsltManageVO.setLastUpdusrId(frstRegisterId);    		// 최종수정자ID

    	// 작성비밀번호를 암호화 하기 위해서 Get
    	String	writngPassword = cnsltManageVO.getWritngPassword();

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	cnsltManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

    	cnsltManageService.updateCnsltDtls(cnsltManageVO);

        model.addAttribute("cnsltManageVO", cnsltManageVO);

        return "redirect:/uss/olp/cns/CnsltListInqire.mdo";
    }

    /**
     * 상담정보를 삭제처리한다.
     * @param cnsltManageVO
     * @param searchVO
     * @param model
     * @return	"redirect:/uss/olp/cns/CnsltListInqire.mdo"
     * @throws Exception
     */
    @RequestMapping("/uss/olp/cns/CnsltDtlsDelete.mdo")
    public String deleteCnsltDtls(
            CnsltManageVO cnsltManageVO,
            @ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
    		ModelMap model)
            throws Exception {

    	model.addAttribute("searchVO", searchVO);
    	cnsltManageService.deleteCnsltDtls(cnsltManageVO);

        return "redirect:/uss/olp/cns/CnsltListInqire.mdo";
    }

    /**
     * 상담정보 목록에 대한 상세정보 공개 여부를 조회한다.
     * @param cnsltManageVO
     * @param searchVO
     * @param model
     * @return modelAndView
     * @throws Exception
     */
    @RequestMapping("/uss/olp/cns/EgovCnsltManageOthbcAtConfirm.mdo")
    public ModelAndView	selectOthbcAtConfirm(
    		@RequestParam("cnsltId") String cnsltId,
            @ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
            ModelMap model) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	CnsltManageVO cnsltManageVO = new CnsltManageVO();
    	cnsltManageVO.setCnsltId(cnsltId);

		CnsltManageVO vo = cnsltManageService.selectCnsltListDetail(cnsltManageVO);

		if("N".equals(vo.getOthbcAt())) {
			modelAndView.addObject("othbcAtConfirm", "N");
		}
		else {
			modelAndView.addObject("othbcAtConfirm", "Y");
		}

        return	modelAndView;
    }

    /**
     * 작성 비밀번호를 확인한다.
     * @param cnsltManageVO
     * @param searchVO
     * @param model
     * @return	modelAndView
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olp/cns/CnsltPasswordConfirm.mdo")
    public ModelAndView selectPasswordConfirm(
            CnsltManageVO cnsltManageVO,
            @ModelAttribute("searchVO") CnsltManageDefaultVO searchVO,
            Model	model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	cnsltManageVO.setWritngPassword(EgovFileScrty.encode(cnsltManageVO.getWritngPassword()));
        int searchCnt = cnsltManageService.selectCnsltPasswordConfirmCnt(cnsltManageVO);

        String	passwordConfirmAt = "";

        if ( searchCnt > 0) {		// 작성 비밀번호가 일치하는 경우
        	passwordConfirmAt = "Y";
        } else	{					// 작성비밀번호가 틀린경우
        	passwordConfirmAt = "N";
        }

        modelAndView.addObject("passwordConfirmAt", passwordConfirmAt);

        return modelAndView;
    }

}
