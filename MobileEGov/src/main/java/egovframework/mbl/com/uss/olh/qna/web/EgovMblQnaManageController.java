package egovframework.mbl.com.uss.olh.qna.web;

import java.net.URLDecoder;
import java.util.List;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olh.qna.service.EgovQnaManageService;
import egovframework.com.uss.olh.qna.service.QnaManageDefaultVO;
import egovframework.com.uss.olh.qna.service.QnaManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * Q&A를 처리하는 Controller 클래스
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
public class EgovMblQnaManageController {

    @Resource(name = "QnaManageService")
    private EgovQnaManageService qnaManageService;

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
     * @return	"/uss/olh/qna/EgovLeft"
     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/qna/EgovLeft.mdo")
//    public String EgovLeft(ModelMap model) throws Exception {
//    	return "/uss/olh/qna/EgovLeft";
//    }

    @RequestMapping(value="/uss/olh/qna/QnaList.mdo")
    public String mainBoard(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            ModelMap model)
    throws Exception {
        return "egovframework/mbl/com/uss/olh/qna/EgovQnaListInqire";

    }
    /**
     * Q&A정보 목록을 조회한다. (pageing)
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaListInqire"
     * @throws Exception
     */
    @IncludedMblInfo(name="Q&A",order = 207 ,gid = 20)
    @RequestMapping(value="/uss/olh/qna/QnaListInqire.mdo")
    public String selectQnaList(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");
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

        List<?> qnaManageList = qnaManageService.selectQnaList(searchVO);
        model.addAttribute("qnaManageList", qnaManageList);
        model.addAttribute("listSize", qnaManageList.size());

    	// 인증여부 체크
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

    	//isAuthenticated = false;

    	if(!isAuthenticated) {

    		modelAndView.addObject("certificationAt", "N");

    	} else	{

    		modelAndView.addObject("certificationAt", "Y");

    	}


        /** paging */
        int totCnt = qnaManageService.selectQnaListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/uss/olh/qna/EgovQnaListInqire";

    }


    /**
     * Q&A정보 목록에 대한 상세정보를 조회한다.
     * @param passwordConfirmAt
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaDetailInqire"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	//@RequestParam("passwordConfirmAt") String passwordConfirmAt ,
    @RequestMapping("/uss/olh/qna/QnaDetailInqire.mdo")
    public String	selectQnaListDetail(
    		QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            ModelMap model) throws Exception {

		QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);

		String passwordConfirmAt = "Y";
		vo.setPasswordConfirmAt(passwordConfirmAt);    		// 작성비밀번호 확인여부

		// 작성 비밀번호를 얻는다.
		String	writngPassword = vo.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
    	vo.setWritngPassword(EgovFileScrty.decode(writngPassword));

		model.addAttribute("qnaManageVO", vo);
		model.addAttribute("searchVO", searchVO);

        return	"egovframework/mbl/com/uss/olh/qna/EgovQnaDetailInqire";
    }

    /**
     * Q&A 조회수를  수정처리한다.
     * @param qnaManageVO
     * @param searchVO
     * @return	"forward:/uss/olh/qna/QnaDetailInqire.do"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/qna/QnaInqireCoUpdt.mdo")
    public String updateQnaInqireCo(
            QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO)
            throws Exception {
    	qnaManageService.updateQnaInqireCo(qnaManageVO);

        return "forward:/uss/olh/qna/QnaDetailInqire.mdo";

    }


    /**
     * 로그인/실명확인 처리
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	/uss/olh/qna/EgovLoginRealnmChoice
     * @throws Exception
     */
    @RequestMapping("/uss/olh/qna/LoginRealnmChoice.mdo")
    public String selectLoginRealnmChoice(
    		QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            Model	model)
            throws Exception {

        model.addAttribute("QnaManageVO", new QnaManageVO());

        return "egovframework/mbl/com/uss/olh/qna/EgovQnaLoginRealnmChoice";
    }


    /**
     * Q&A정보를 등록하기 위한 전 처리(인증체크)
     * @param searchVO
     * @param qnaManageVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaCnRegist"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/qna/QnaCnRegistView.mdo")
    public String insertQnaCnView(
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
    		QnaManageVO qnaManageVO,
            Model model)
            throws Exception {

//    	// 인증여부 체크
//    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//
//    	//isAuthenticated = false;
//
//    	if(!isAuthenticated) {
//
//    		model.addAttribute("qnaManageVO", qnaManageVO);
//
//    		return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnRegist";
//
//    	}

        // 로그인VO에서  사용자 정보 가져오기
        LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String	wrterNm 	= loginVO.getName();					// 사용자명
        String	emailAdres 	= loginVO.getEmail();					// email 주소

        qnaManageVO.setWrterNm(wrterNm);							// 작성자명
        qnaManageVO.setEmailAdres(emailAdres);    					// email 주소


        model.addAttribute("qnaManageVO", qnaManageVO);

        return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnRegist";

    }

    /**
     * Q&A정보를 등록한다.
     * @param searchVO
     * @param qnaManageVO
     * @param bindingResult
     * @return	"forward:/uss/olh/qna/QnaListInqire.do"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	//BindingResult bindingResult,
    @RequestMapping("/uss/olh/qna/QnaCnRegist.mdo")
    public String insertQnaCn(
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            @ModelAttribute("qnaManageVO") QnaManageVO qnaManageVO,

            ModelMap model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView();

    	//beanValidator.validate(qnaManageVO, bindingResult);
		//if(bindingResult.hasErrors()){
		//	return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnRegist";
		//}

    	// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

    	String	frstRegisterId = loginVO.getUniqId();

    	qnaManageVO.setFrstRegisterId(frstRegisterId);		// 최초등록자ID
    	qnaManageVO.setLastUpdusrId(frstRegisterId);    	// 최종수정자ID

    	// 작성비밀번호를 암호화 하기 위해서 Get
    	String	writngPassword = qnaManageVO.getWritngPassword();

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

        qnaManageService.insertQnaCn(qnaManageVO);
        modelAndView.addObject("searchVO", searchVO);

        return "redirect:/uss/olh/qna/QnaListInqire.mdo";
    }

    /**
     * 작성 비밀번호를 확인하기 위한 전 처리
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaPasswordConfirm"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/qna/QnaPasswordConfirmView.mdo")
    public String selectPasswordConfirmView(
            QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            Model	model)
            throws Exception {

        model.addAttribute("QnaManageVO", new QnaManageVO());

        return "egovframework/mbl/com/uss/olh/qna/EgovQnaPasswordConfirm";
    }

    /**
     * 작성 비밀번호를 확인한다.
     * @param qnaManageVO
     * @param searchVO
     * @return	"forward:/uss/olh/qna/QnaDetailInqire.do"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/QnaPasswordConfirm.mdo")
	public ModelAndView selectPasswordConfirm(QnaManageVO qnaManageVO, @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
			Model model) throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	// 작성비밀번호를 암호화 하기 위해서 Get
    	//String	writngPassword = qnaManageVO.getWritngPassword();

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	qnaManageVO.setWritngPassword(EgovFileScrty.encode(qnaManageVO.getWritngPassword()));
    	int searchCnt = qnaManageService.selectQnaPasswordConfirmCnt(qnaManageVO);

        String	passwordConfirmAt = "";

        if ( searchCnt > 0) {		// 작성 비밀번호가 일치하는 경우

        	passwordConfirmAt = "Y";
        	// Q&A를 수정할 수 있는 화면으로 이동.
        	//return	"forward:/uss/olh/qna/QnaCnUpdtView.mdo";

        } else	{					// 작성비밀번호가 틀린경우

        	// 작성비밀번호 확인 결과 세팅.
        	//qnaManageVO.setPasswordConfirmAt("N");

        	passwordConfirmAt = "N";

            //model.addAttribute("QnaManageVO", qnaManageVO);

        	// Q&A 상세조회 화면으로 이동.
        	//return "forward:/uss/olh/qna/QnaDetailInqire.mdo?passwordConfirmAt=" + passwordConfirmAt;


        }
        modelAndView.addObject("passwordConfirmAt", passwordConfirmAt);

        return modelAndView;

    }

    /**
     * Q&A정보를 수정하기 위한 전 처리(비밀번호 암호화)
     * @param qnaManageVO
     * @param searchVO
     * @param model
     * @return	"/uss/olh/qna/EgovQnaCnUpdt
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/QnaCnUpdtView.mdo")
    public String updateQnaCnView(
    		QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model)
            throws Exception {

    	QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);

		// 작성 비밀번호를 얻는다.
		String	writngPassword = vo.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
    	vo.setWritngPassword(EgovFileScrty.decode(writngPassword));

        // 복호화된 패스워드를 넘긴다..
        model.addAttribute("qnaManageVO", vo);

        // result에도 세팅(jstl 사용을 위해)
        //model.addAttribute(selectQnaListDetail("Y",qnaManageVO, searchVO, model));
        model.addAttribute(selectQnaListDetail(qnaManageVO, searchVO, model));

        return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnUpdt";
    }

    /**
     * Q&A정보를 수정처리한다.
     * @param searchVO
     * @param qnaManageVO
     * @param bindingResult
     * @return	"forward:/uss/olh/qna/QnaListInqire.do"
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/QnaCnUpdt.mdo")
    public String updateQnaCn(
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
            @ModelAttribute("qnaManageVO") QnaManageVO qnaManageVO,
            BindingResult bindingResult,
            ModelMap model)
            throws Exception {

//    	// Validation
//    	beanValidator.validate(qnaManageVO, bindingResult);
//
//		if(bindingResult.hasErrors()){
//
//			return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnUpdt";
//
//		}

    	// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

    	String	lastUpdusrId = loginVO.getUniqId();

    	qnaManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID

    	// 작성비밀번호를 암호화 하기 위해서 Get
    	String	writngPassword = qnaManageVO.getWritngPassword();

    	// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
    	qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

    	qnaManageService.updateQnaCn(qnaManageVO);

    	model.addAttribute("qaId", qnaManageVO.getQaId());
        model.addAttribute("qnaManageVO", qnaManageVO);

        return "redirect:/uss/olh/qna/QnaListInqire.mdo";

    }

    /**
     * Q&A정보를 삭제처리한다.
     * @param qnaManageVO
     * @param searchVO
     * @return	"forward:/uss/olh/qna/QnaListInqire.do"
     * @throws Exception
     */
    @RequestMapping("/uss/olh/qna/QnaCnDelete.mdo")
    public String deleteQnaCn(
            QnaManageVO qnaManageVO,
            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO)
            throws Exception {

    	qnaManageService.deleteQnaCn(qnaManageVO);

        return "redirect:/uss/olh/qna/QnaListInqire.mdo";
    }
//
//    /**
//     * Q&A답변정보 목록을 조회한다. (pageing)
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaAnswerListInqire"
//     * @throws Exception
//     */
//    @RequestMapping(value="/uss/olh/qnm/QnaAnswerListInqire.mdo")
//    public String selectQnaAnswerList(@ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model) throws Exception {
//
//    	/** EgovPropertyService.SiteList */
//    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
//    	searchVO.setPageSize(propertiesService.getInt("pageSize"));
//
//    	/** pageing */
//    	PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
//		paginationInfo.setPageSize(searchVO.getPageSize());
//
//		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//        List qnaManageList = qnaManageService.selectQnaList(searchVO);
//        model.addAttribute("qnaManageList", qnaManageList);
//        model.addAttribute("listSize", qnaManageList.size());
//
//        int totCnt = qnaManageService.selectQnaListTotCnt(searchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//        model.addAttribute("paginationInfo", paginationInfo);
//        model.addAttribute("searchVO", searchVO);
//
//        return "egovframework/mbl/com/uss/olh/qna/EgovQnaAnswerListInqire";
//    }
//
//    /**
//     * Q&A답변정보 목록에 대한 상세정보를 조회한다.
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaAnswerDetailInqire"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qnm/QnaAnswerDetailInqire.mdo")
//    public String	selectQnaAnswerListDetail(
//    		QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO,
//            ModelMap model) throws Exception {
//
//		QnaManageVO vo = qnaManageService.selectQnaListDetail(qnaManageVO);
//
//		model.addAttribute("result", vo);
//
//        return	"egovframework/mbl/com/uss/olh/qna/EgovQnaAnswerDetailInqire";
//    }
//
//
//    /**
//     * Q&A답변정보를 수정하기 위한 전 처리(공통코드 처리)
//     * @param qnaManageVO
//     * @param searchVO
//     * @param model
//     * @return	"/uss/olh/qna/EgovQnaCnAnswerUpdt"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qnm/QnaCnAnswerUpdtView.mdo")
//    public String updateQnaCnAnswerView(
//    		QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO, ModelMap model)
//            throws Exception {
//
//
//    	// 공통코드를 가져오기 위한 Vo
//    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
//		vo.setCodeId("COM028");
//
//		List _result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("resultList", _result);
//
//        // 변수명은 CoC 에 따라
//        model.addAttribute(selectQnaAnswerListDetail(qnaManageVO, searchVO, model));
//
//        return "egovframework/mbl/com/uss/olh/qna/EgovQnaCnAnswerUpdt";
//    }
//
//    /**
//     * Q&A답변정보를 수정처리한다.
//     * @param qnaManageVO
//     * @param searchVO
//     * @return	"forward:/uss/olh/qnm/QnaAnswerListInqire.do"
//     * @throws Exception
//     */
//    @RequestMapping("/uss/olh/qnm/QnaCnAnswerUpdt.mdo")
//    public String updateQnaCnAnswer(
//            QnaManageVO qnaManageVO,
//            @ModelAttribute("searchVO") QnaManageDefaultVO searchVO)
//            throws Exception {
//
//    	// 로그인VO에서  사용자 정보 가져오기
//    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//
//    	String	lastUpdusrId = loginVO.getUniqId();
//
//    	qnaManageVO.setLastUpdusrId(lastUpdusrId);    	// 최종수정자ID
//
//    	qnaManageService.updateQnaCnAnswer(qnaManageVO);
//
//        return "forward:/uss/olh/qnm/QnaAnswerListInqire.mdo";
//
//    }


}
