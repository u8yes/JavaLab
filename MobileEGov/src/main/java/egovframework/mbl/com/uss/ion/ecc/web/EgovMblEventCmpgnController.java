package egovframework.mbl.com.uss.ion.ecc.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.ecc.service.EgovEventCmpgnService;
import egovframework.com.uss.ion.ecc.service.EventCmpgnVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;
/**
 * 행사/이벤트/캠페인을 처리하는 Controller Class 구현
 * @author 공통서비스 장동한
 * @since 2009.03.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  장동한          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblEventCmpgnController {

	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "egovEventCmpgnService")
	private EgovEventCmpgnService egovEventCmpgnService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

    /**
     * 개별 배포시 메인메뉴를 조회한다.
     * @param model
     * @return	"/uss/ion/ecc/"
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/ecc/EgovMain.mdo")
    public String EgovMain(ModelMap model) throws Exception {
    	return "egovframework/uss/ion/ecc/EgovMain";
    }

    /**
     * 메뉴를 조회한다.
     * @param model
     * @return	"/uss/ion/ecc/EgovLeft"
     * @throws Exception
     */
    @RequestMapping(value="/uss/ion/ecc/EgovLeft.mdo")
    public String EgovLeft(ModelMap model) throws Exception {
    	return "egovframework/uss/ion/ecc/EgovLeft";
    }

	/**
	 * 행사/이벤트/캠페인 목록 팝업을 조회한다.
	 * @param searchVO
	 * @param eventCmpgnVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovEventCmpgnListPopup"
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/ion/ecc/EgovEventCmpgnListPopup.mdo")
	public String EgovEventCmpgnListPopup(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			EventCmpgnVO eventCmpgnVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	/** EgovPropertyService.sample */
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

        List<?> sampleList = egovEventCmpgnService.selectEventCmpgnList(searchVO);
        model.addAttribute("resultList", sampleList);

        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));

        int totCnt = egovEventCmpgnService.selectEventCmpgnListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

     	//공통코드  행사유형 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM035");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode035", listComCode);

		return "egovframework/mbl/com/uss/ion/ecc/EgovEventCmpgnListPopup";

	}

	/**
	 * 행사/이벤트/캠페인 목록을 조회한다.
	 * @param searchVO
	 * @param eventCmpgnVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovEventCmpgnList
	 * @throws Exception
	 */
	@IncludedMblInfo(name="행사/이벤트/캠페인",order = 214 ,gid = 20)
	@RequestMapping(value="/uss/ion/ecc/EgovEventCmpgnList.mdo")
	public String EgovEventCmpgnList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			EventCmpgnVO eventCmpgnVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

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

        List<?> sampleList = egovEventCmpgnService.selectEventCmpgnList(searchVO);
        model.addAttribute("resultList", sampleList);

        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));

        int totCnt = egovEventCmpgnService.selectEventCmpgnListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);


     	//공통코드  행사유형 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM035");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode035", listComCode);

		return "egovframework/mbl/com/uss/ion/ecc/EgovEventCmpgnList";

	}

	/**
	 * 행사/이벤트/캠페인 상세조회 한다.
	 * @param searchVO
	 * @param eventCmpgnVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovEventCmpgnDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/ion/ecc/EgovEventCmpgnDetail.mdo")
	public String EgovEventCmpgnDetail(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			EventCmpgnVO eventCmpgnVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
    	}
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("SUserSe", loginVO.getUserSe());

		String sLocationUrl = "egovframework/mbl/com/uss/ion/ecc/EgovEventCmpgnDetail";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

		if(sCmd.equals("del")){
			egovEventCmpgnService.deleteEventCmpgn(eventCmpgnVO);
			sLocationUrl = "redirect:/uss/ion/ecc/EgovEventCmpgnList.mdo";
		}else{
	     	//공통코드  행사유형 조회
	    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
	    	voComCode.setCodeId("COM035");
	    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
	    	model.addAttribute("comCode035", listComCode);

	        //List sampleList = egovEventCmpgnService.selectEventCmpgnDetail(eventCmpgnVO);

	        model.addAttribute("resultList", egovEventCmpgnService.selectEventCmpgnDetail(eventCmpgnVO));

		}

		return sLocationUrl;
	}

	/**
	 * 행사/이벤트/캠페인을 수정 한다.
	 * @param searchVO
	 * @param eventCmpgnVO
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovEventCmpgnModify"
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/ion/ecc/EgovEventCmpgnModify.mdo")
	public String EventCmpgnModify(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("eventCmpgnVO") EventCmpgnVO eventCmpgnVO,
			BindingResult bindingResult,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
    	}

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("SUserSe", loginVO.getUserSe());

		String sLocationUrl = "egovframework/mbl/com/uss/ion/ecc/EgovEventCmpgnModify";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

     	//공통코드  행사유형 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM035");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode035", listComCode);


        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(eventCmpgnVO, bindingResult);
    		if (bindingResult.hasErrors()){
                List<?> resultList = egovEventCmpgnService.selectEventCmpgnDetail(eventCmpgnVO);
                model.addAttribute("resultList", resultList);
    	        return sLocationUrl;
    		}
    		//아이디 설정
    		eventCmpgnVO.setFrstRegisterId(loginVO.getUniqId());
    		eventCmpgnVO.setLastUpdusrId(loginVO.getUniqId());

        	egovEventCmpgnService.updateEventCmpgn(eventCmpgnVO);
        	sLocationUrl = "redirect:/uss/ion/ecc/EgovEventCmpgnList.mdo";
        }else{

            List<?> resultList = egovEventCmpgnService.selectEventCmpgnDetail(eventCmpgnVO);

            Map<?, ?> cmpgnMap = (Map<?, ?>)resultList.get(0);

	        BeanUtils.populate(eventCmpgnVO, (Map<String, ? extends Object>) cmpgnMap);

            model.addAttribute("eventCmpgnVO", eventCmpgnVO);
        }

		return sLocationUrl;
	}

	/**
	 * 행사/이벤트/캠페인을 등록 한다.
	 * @param searchVO
	 * @param eventCmpgnVO
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovEventCmpgnRegist"
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/ion/ecc/EgovEventCmpgnRegist.mdo")
	public String EventCmpgnRegist(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("eventCmpgnVO") EventCmpgnVO eventCmpgnVO,
			BindingResult bindingResult,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
    	}

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("SUserSe", loginVO.getUserSe());

		String sLocationUrl = "egovframework/mbl/com/uss/ion/ecc/EgovEventCmpgnRegist";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

     	//공통코드  행사유형 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM035");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode035", listComCode);

        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(eventCmpgnVO, bindingResult);
    		if(bindingResult.hasErrors()){
    	        return sLocationUrl;
    		}
    		//아이디 설정
    		eventCmpgnVO.setFrstRegisterId(loginVO.getUniqId());
    		eventCmpgnVO.setLastUpdusrId(loginVO.getUniqId());

        	egovEventCmpgnService.insertEventCmpgn(eventCmpgnVO);
        	sLocationUrl = "redirect:/uss/ion/ecc/EgovEventCmpgnList.mdo";
        }

		return sLocationUrl;
	}
	/*
	*//**
	 * 외부인사정보 목록을 조회한다.
	 * @param searchVO
	 * @param TnextrlHrInfo
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovTnextrlHrInfoList"
	 * @throws Exception
	 *//*
	@RequestMapping(value="/uss/ion/ecc/EgovTnextrlHrInfoList.mdo")
	public String EgovTnextrlHrInfoList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			TnextrlHrInfoVO TnextrlHrInfo,
			@RequestParam Map commandMap,
    		ModelMap model)
    throws Exception {

    	*//** EgovPropertyService.sample *//*
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	*//** pageing *//*
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List sampleList = egovEventCmpgnService.selectTnextrlHrInfoList(searchVO);
        model.addAttribute("resultList", sampleList);

        model.addAttribute("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
        model.addAttribute("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));

        int totCnt = (Integer)egovEventCmpgnService.selectTnextrlHrInfoListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

     	//성별코드조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM014");
    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode014", listComCode);

    	//직업코드조회
    	voComCode.setCodeId("COM034");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode034", listComCode);


		return "mbl/com/uss/ion/ecc/EgovTnextrlHrInfoList";

	}

	*//**
	 * 외부인사정보 상세조회 한다.
	 * @param searchVO
	 * @param TnextrlHrInfo
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovTnextrlHrInfoDetail"
	 * @throws Exception
	 *//*
	@RequestMapping(value="/uss/ion/ecc/EgovTnextrlHrInfoDetail.mdo")
	public String EgovTnextrlHrInfoDetail(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			TnextrlHrInfoVO TnextrlHrInfo,
			@RequestParam Map commandMap,
    		ModelMap model)
    throws Exception {

		String sLocationUrl = "mbl/com/uss/ion/ecc/EgovTnextrlHrInfoDetail";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

		if(sCmd.equals("del")){
			egovEventCmpgnService.deleteTnextrlHrInfo(TnextrlHrInfo);
			sLocationUrl = "redirect:/uss/ion/ecc/EgovTnextrlHrInfoList.mdo";
		}else{
	     	//성별코드조회
	    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
	    	voComCode.setCodeId("COM014");
	    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
	    	model.addAttribute("comCode014", listComCode);

	    	//직업코드조회
	    	voComCode.setCodeId("COM034");
	    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
	    	model.addAttribute("comCode034", listComCode);

			List sampleList = egovEventCmpgnService.selectTnextrlHrInfoDetail(TnextrlHrInfo);
        	model.addAttribute("resultList", sampleList);

		}

		return sLocationUrl;
	}

	*//**
	 * 외부인사정보을 수정 한다
	 * @param searchVO
	 * @param tnextrlHrInfoVO
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovTnextrlHrInfoModify"
	 * @throws Exception
	 *//*
	@RequestMapping(value="/uss/ion/ecc/EgovTnextrlHrInfoModify.mdo")
	public String TnextrlHrInfoModify(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("tnextrlHrInfoVO") TnextrlHrInfoVO tnextrlHrInfoVO,
			BindingResult bindingResult,
			@RequestParam Map commandMap,
    		ModelMap model)
    throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "mbl/com/uat/uia/EgovLoginUsr";
    	}

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		String sLocationUrl = "mbl/com/uss/ion/ecc/EgovTnextrlHrInfoModify";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

     	//성별코드조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM014");
    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode014", listComCode);

    	//직업코드조회
    	voComCode.setCodeId("COM034");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode034", listComCode);
        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(tnextrlHrInfoVO, bindingResult);
    		if (bindingResult.hasErrors()){
                List sampleList = egovEventCmpgnService.selectTnextrlHrInfoDetail(tnextrlHrInfoVO);
                model.addAttribute("resultList", sampleList);
    	        return sLocationUrl;
    		}
    		//아이디 설정
    		tnextrlHrInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
    		tnextrlHrInfoVO.setLastUpdusrId((String)loginVO.getUniqId());

        	egovEventCmpgnService.updateTnextrlHrInfo(tnextrlHrInfoVO);
        	sLocationUrl = "redirect:/uss/ion/ecc/EgovTnextrlHrInfoList.mdo";
        }else{
            List sampleList = egovEventCmpgnService.selectTnextrlHrInfoDetail(tnextrlHrInfoVO);
            model.addAttribute("resultList", sampleList);
        }

		return sLocationUrl;
	}

	*//**
	 * 외부인사정보을 등록 한다.
	 * @param searchVO
	 * @param tnextrlHrInfoVO
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "/uss/ion/ecc/EgovTnextrlHrInfoRegist"
	 * @throws Exception
	 *//*
	@RequestMapping(value="/uss/ion/ecc/EgovTnextrlHrInfoRegist.mdo")
	public String TnextrlHrInfoRegist(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("tnextrlHrInfoVO") TnextrlHrInfoVO tnextrlHrInfoVO,
			BindingResult bindingResult,
			@RequestParam Map commandMap,
    		ModelMap model)
    throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "mbl/com/uat/uia/EgovLoginUsr";
    	}

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		String sLocationUrl = "mbl/com/uss/ion/ecc/EgovTnextrlHrInfoRegist";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");


     	//성별코드조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM014");
    	List listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode014", listComCode);

    	//직업코드조회
    	voComCode.setCodeId("COM034");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("comCode034", listComCode);

        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(tnextrlHrInfoVO, bindingResult);
    		if (bindingResult.hasErrors()){
    	        return sLocationUrl;
    		}
    		//아이디 설정
    		tnextrlHrInfoVO.setFrstRegisterId((String)loginVO.getUniqId());
    		tnextrlHrInfoVO.setLastUpdusrId((String)loginVO.getUniqId());

        	egovEventCmpgnService.insertTnextrlHrInfo(tnextrlHrInfoVO);
        	sLocationUrl = "redirect:/uss/ion/ecc/EgovTnextrlHrInfoList.mdo";
        }

		return sLocationUrl;
	}*/

}


