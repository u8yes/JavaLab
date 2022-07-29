package egovframework.mbl.com.cop.smt.dsm.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.dsm.service.DiaryManageVO;
import egovframework.com.cop.smt.dsm.service.EgovDiaryManageService;
import egovframework.com.cop.smt.sdm.service.DeptSchdulManageVO;
import egovframework.com.cop.smt.sdm.service.EgovDeptSchdulManageService;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;
/**
 * 일지관리를 처리하는 Controller Class 구현
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
public class EgovMblDiaryManageController {

	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "egovDiaryManageService")
	private EgovDiaryManageService egovDiaryManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Resource(name = "egovDeptSchdulManageService")
	private EgovDeptSchdulManageService egovDeptSchdulManageService;

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;


	/**
	 * 일지관리 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param diaryManageVO
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageList"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@IncludedMblInfo(name="일지관리",order = 108 ,gid = 10)
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageList.mdo")
	public String EgovDiaryManageList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DiaryManageVO diaryManageVO,
    		ModelMap model)
    throws Exception {

		String sSearchMode = commandMap.get("searchMode") == null ? "" : (String)commandMap.get("searchMode");

    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	int pageIndex = commandMap.get("searchKeyword") == null ? searchVO.getPageIndex() : Integer.parseInt((String)commandMap.get("pageIndex"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageIndex);
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(1);

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        if(commandMap.get("schdulId") != null && "save".equals((commandMap.get("schdulId")))){
        	searchVO.setSearchCondition("SCHDUL_ID");
        	searchVO.setSearchKeyword((String)commandMap.get("schdulId"));
        }

        String searchKeyword = commandMap.get("searchKeyword") == null ? searchVO.getSearchKeyword() : (String)commandMap.get("searchKeyword");
        searchVO.setSearchKeyword(searchKeyword);

        String searchCondition = commandMap.get("searchCondition") == null ? searchVO.getSearchCondition() : (String)commandMap.get("searchCondition");
        searchVO.setSearchCondition(searchCondition);



        List<?> sampleList = egovDiaryManageService.selectDiaryManageList(searchVO);
        model.addAttribute("resultList", sampleList);

        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchCondition", searchCondition);

        int totCnt = egovDiaryManageService.selectDiaryManageListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageList";
	}

	/**
	 * 일지관리 목록을 상세조회 조회한다.
	 * @param searchVO
	 * @param diaryManageVO
	 * @param commandMap
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageDetail.mdo")
	public String EgovDiaryManageDetail(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			DiaryManageVO diaryManageVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {
		String sLocationUrl = "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageDetail";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

		if(sCmd.equals("del")){
			egovDiaryManageService.deleteDiaryManage(diaryManageVO);
			sLocationUrl = "redirect:/cop/smt/dsm/EgovDiaryManageList.mdo";
		}else{
	        model.addAttribute("diaryManageVO",  egovDiaryManageService.selectDiaryManageDetail(diaryManageVO));
			model.addAttribute("searchVO", searchVO);

		}

		return sLocationUrl;
	}

	/**
	 * 일지관리를 수정한다. / 초기페이지
	 * @param searchVO
	 * @param commandMap
	 * @param diaryManageVO
	 * @param bindingResult
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageModify"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageModify.mdo")
	public String DiaryManageModify(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DiaryManageVO diaryManageVO,
			BindingResult bindingResult,
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

		String sLocationUrl = "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageModify";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

		model.addAttribute("diaryManageVO",  egovDiaryManageService.selectDiaryManageDetail(diaryManageVO));

		return sLocationUrl;
	}

	/**
	 * 일지관리를 수정한다. / 수정처리작업
	 * @param multiRequest
	 * @param searchVO
	 * @param commandMap
	 * @param diaryManageVO
	 * @param bindingResult
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageModifyActor"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageModifyActor.mdo")
	public String DiaryManageModifyActor(
			//final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,
			BindingResult bindingResult,
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

		String sLocationUrl = "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageModify";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(diaryManageVO, bindingResult);
    		if(bindingResult.hasErrors()){

    			return sLocationUrl;
    		}
    		/* *****************************************************************
        	// 아이디설정
			****************************************************************** */
    		diaryManageVO.setFrstRegisterId(loginVO.getUniqId());
    		diaryManageVO.setLastUpdusrId(loginVO.getUniqId());
    		/* *****************************************************************
        	// 첨부파일 관련 ID 생성 start....
			****************************************************************** */
    		/*String _atchFileId = diaryManageVO.getAtchFileId();


    		final Map<String, MultipartFile> files = multiRequest.getFileMap();

    		if(!files.isEmpty()){
    			String atchFileAt = commandMap.get("atchFileAt") == null ? "" : (String)commandMap.get("atchFileAt");
    			if("N".equals(atchFileAt)){
    				List<FileVO> _result = fileUtil.parseFileInf(files, "DIARY_", 0, _atchFileId, "");
    				_atchFileId = fileMngService.insertFileInfs(_result);

    				// 첨부파일 ID 셋팅
    				diaryManageVO.setAtchFileId(_atchFileId);    	// 첨부파일 ID

    			}else{
    				FileVO fvo = new FileVO();
    				fvo.setAtchFileId(_atchFileId);
    				int _cnt = fileMngService.getMaxFileSN(fvo);
    				List<FileVO> _result = fileUtil.parseFileInf(files, "DIARY_", _cnt, _atchFileId, "");
    				fileMngService.updateFileInfs(_result);
    			}
    		}	*/

    		/* *****************************************************************
        	// 일지정보 업데이트
			****************************************************************** */
        	egovDiaryManageService.updateDiaryManage(diaryManageVO);
        	sLocationUrl = "redirect:/cop/smt/dsm/EgovDiaryManageList.mdo";
		}

		return sLocationUrl;
	}

	/**
	 * 일지관리를 등록한다. / 등록 초기페이지
	 * @param searchVO
	 * @param commandMap
	 * @param diaryManageVO
	 * @param bindingResult
	 * @param model
	 * @return  "/cop/smt/dsm/EgovDiaryManageRegist"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageRegist.mdo")
	public String DiaryManageRegist(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,
			BindingResult bindingResult,
    		ModelMap model)
    throws Exception {

		String schdulId = commandMap.get("schdulId") == null ? "" : (String)commandMap.get("schdulId");
        String schdulCn = commandMap.get("schdulCn") == null ? "" : (String)commandMap.get("schdulCn");

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
    	}else{
    		diaryManageVO.setSchdulId(schdulId);
    		diaryManageVO.setSchdulCn(schdulCn);
    		model.addAttribute("diaryManageVO", diaryManageVO);
    	}

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		String sLocationUrl = "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageRegist";

		return sLocationUrl;
	}

	/**
	 * 일지관리를 등록한다. / 등록처리작업
	 * @param multiRequest
	 * @param searchVO
	 * @param commandMap
	 * @param diaryManageVO
	 * @param bindingResult
	 * @param model
	 * @return "/cop/smt/dsm/DiaryManageRegistActor"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/dsm/EgovDiaryManageRegistActor.mdo")
	public String DiaryManageRegistActor(
			//final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,
			BindingResult bindingResult,
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

		String sLocationUrl = "egovframework/mbl/com/cop/smt/dsm/EgovDiaryManageRegist";

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");

        if(sCmd.equals("save")){
    		//서버  validate 체크
            beanValidator.validate(diaryManageVO, bindingResult);
    		if(bindingResult.hasErrors()){

    			return sLocationUrl;
    		}

        	/*// 첨부파일 관련 첨부파일ID 생성
    		List<FileVO> _result = null;
    		String _atchFileId = "";

    		final Map<String, MultipartFile> files = multiRequest.getFileMap();

    		if(!files.isEmpty()){
    		 _result = fileUtil.parseFileInf(files, "DIARY_", 0, "", "");
    		 _atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
    		}

        	// 리턴받은 첨부파일ID를 셋팅한다..
    		diaryManageVO.setAtchFileId(_atchFileId);*/

    		//아이디 설정
    		diaryManageVO.setFrstRegisterId(loginVO.getUniqId());
    		diaryManageVO.setLastUpdusrId(loginVO.getUniqId());

        	egovDiaryManageService.insertDiaryManage(diaryManageVO);
        	sLocationUrl = "redirect:/cop/smt/dsm/EgovDiaryManageList.mdo";
        }

		return sLocationUrl;
	}

	/**
	 * 일지관리 목록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageList"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageListPopupView.mdo")
	public String EgovDeptSchdulManageListPopupView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

		return "egovframework/mbl/com/cop/smt/dsm/EgovDeptSchdulManageListPopup";
	}

	/**
	 * 일지관리 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "/cop/smt/dsm/EgovDiaryManageList"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageListPopup.mdo")
	public ModelAndView EgovDeptSchdulManageListPopup(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model1)
    throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	//** EgovPropertyService.sample *//
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	//** pageing *//
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//공통코드  일정구분 조회
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
		voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);

    	String searchKeyword = (String)commandMap.get("searchKeyword");
    	if("SCHDUL_SE".equals((commandMap.get("searchCondition")))) {
	    	for (Object code : listComCode) {
				if(searchKeyword.equals(((CmmnDetailCode)code).getCodeNm())){
					searchVO.setSearchKeyword(((CmmnDetailCode)code).getCode());
				}
	    	}
		}
    	modelAndView.addObject("schdulSe", listComCode);


        List<?> sampleList = egovDeptSchdulManageService.selectDeptSchdulManageList(searchVO);

        modelAndView.addObject("resultList", sampleList);
        modelAndView.addObject("searchKeyword", commandMap.get("searchKeyword") == null ? "" : (String)commandMap.get("searchKeyword"));
        modelAndView.addObject("searchCondition", commandMap.get("searchCondition") == null ? "" : (String)commandMap.get("searchCondition"));

        int totCnt = egovDeptSchdulManageService.selectDeptSchdulManageListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
	}
}


