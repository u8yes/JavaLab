package egovframework.mbl.com.syn.web;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.syn.service.EgovSyncService;
import egovframework.mbl.com.syn.service.Sync;
import egovframework.mbl.com.syn.service.SyncVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * -동기화 서비스에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 동기화 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 조준형
 * @since 2011.08.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.22  조준형          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovSyncController {

	@Resource(name = "SyncService")
	private EgovSyncService syncService;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	//---------------------------------------------------------------------------------------------------------/
	// 동기화 서비스 모바일 ----------------------------------------------------------------------------------------/
	//---------------------------------------------------------------------------------------------------------/
	/**
	 * 동기화 서비스 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 */
	@IncludedMblInfo(name="동기화 서비스",order = 407 ,gid = 40)
	@RequestMapping(value="/mbl/com/syn/goMobileSyncList.mdo")
	public ModelAndView goMobileSyncList(
			@ModelAttribute SyncVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            // return "com/uat/uia/EgovLoginUsr";
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.setViewName("egovframework/mbl/com/syn/EgovMobileSyncList");

		return modelAndView;
	}

	/**
	 * 동기화 서비스 글 목록 조회
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/selectMobileSyncList.mdo")
	public ModelAndView selectMobileSyncList(
			@ModelAttribute("searchVO") SyncVO searchVO,
			Model model) throws Exception {
		List<?> rtnList = syncService.selectSyncList(searchVO);
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("synList", rtnList);
		modelAndView.addObject("success", true);

		return modelAndView;
	}

	/**
	 * 동기화 상페조회로 페이지 이동
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 */
	@RequestMapping(value="/mbl/com/syn/goMobileSync.mdo")
	public ModelAndView goMobileSync(
			@ModelAttribute("searchVO") SyncVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            // return "com/uat/uia/EgovLoginUsr";
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.addObject("sn", searchVO.getSn());
		modelAndView.setViewName("egovframework/mbl/com/syn/EgovMobileSync");

		return modelAndView;
	}

	/**
	 * 동기화 서비스 상세 글 조회
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/selectMobileSync.mdo")
	public ModelAndView selectMobileSync(@ModelAttribute("searchVO") SyncVO searchVO,
			Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 1. 동기화 서비스 목록을 조회 한다.
		Sync oSync = syncService.selectSync(searchVO);

		// 2. 조회 결과를 ModelAndView에 할당한다.
		modelAndView.addObject("syncView", oSync);
		modelAndView.addObject("searchVO", searchVO);

		return modelAndView;
	}

	/**
	 * 동기화 서비스 글 등록 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mbl/com/syn/goMobileSyncInsert.mdo")
	public ModelAndView goMobileSyncInsert(
			@ModelAttribute("searchVO") SyncVO searchVO,
			Model model) {

		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            // return "com/uat/uia/EgovLoginUsr";
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.setViewName("egovframework/mbl/com/syn/EgovMobileSyncInsert");

		return modelAndView;
	}

	/**
	 * 동기화 서비스 글 등록
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/mbl/com/syn/insertMobileSync.mdo")
	public ModelAndView insertMobileSync(
			@ModelAttribute("sync")Sync sync,
			Model model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);

		int rtn = syncService.insertSync(sync);
		modelAndView.addObject("success", true);

        return modelAndView;
	}

	/**
	 * 동기화 서비스 글 수정 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mbl/com/syn/goMobileSyncUpdate.mdo")
	public ModelAndView goMobileSyncUpdate(
			@ModelAttribute("searchVO") SyncVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            // return "com/uat/uia/EgovLoginUsr";
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.addObject("sn", searchVO.getSn());
		modelAndView.setViewName("egovframework/mbl/com/syn/EgovMobileSyncUpdate");

		return modelAndView;
	}

	/**
	 * 동기화 서비스 글 수정
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/mbl/com/syn/updateMobileSync.mdo")
	public ModelAndView updateMobileSync(
			@ModelAttribute("sync")Sync sync,
			Model model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);

		int rtn = syncService.updateSync(sync);
		modelAndView.addObject("success", true);

		return modelAndView;
	}

	/**
	 * 동기화 서비스 글 삭제
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/deleteMobileSync.mdo")
	public ModelAndView deleteMobileSync(
			@ModelAttribute("sync")Sync sync,
			Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		 // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            // return "com/uat/uia/EgovLoginUsr";
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);

    	syncService.deleteSync(sync);
		modelAndView.setViewName("redirect:/mbl/com/syn/goMobileSyncList.mdo");

        return modelAndView;
	}

	/**
	 * 동기화 서비스 글 '동기화' 를 실행한다.
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/mbl/com/syn/executeMobileSync.mdo")
	public ModelAndView executeMobileSync(
			@RequestParam("localData") String localData,
			@ModelAttribute Sync sync,
			Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);

		int rtn = syncService.executeSync(sync, localData);
		modelAndView.addObject("success", true);

        return modelAndView;
	}

	//---------------------------------------------------------------------------------------------------------/
	// 동기화 서비스 관리자 ----------------------------------------------------------------------------------------/
	//---------------------------------------------------------------------------------------------------------/
	/**
	 * 동기화 서비스 등록 화면으로 이동한다.
	 * @param searchVO
	 * @param model
	 * @return "/mbl/com/syn/EgovSyncInsert"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/goSyncRegist.mdo")
	public String goSynRegist(
			@ModelAttribute("syncForm") SyncVO searchVO,
			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		model.addAttribute("Sync", new Sync());

        return "egovframework/mbl/com/syn/EgovSyncInsert";
	}

	/**
	 * 동기화 서비스 등록 Service interface 호출 및 결과를 반환한다.
	 * @param sync
	 * @param bindingResult
	 * @param model
	 * @return "forward:/mbl/com/syn/selectSyncList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/insertSync.mdo")
	public String insertSyn(
			@ModelAttribute("syncForm") Sync sync,
			BindingResult bindingResult,
			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	beanValidator.validate(sync, bindingResult);

		if(bindingResult.hasErrors()){
			return "egovframework/mbl/com/syn/EgovSyncInsert";
		}

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);
    	syncService.insertSync(sync);

        return "forward:/mbl/com/syn/selectSyncList.mdo";
	}

	/**
	 * 동기화 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param sync
     * @param model
     * @return "/mbl/com/syn/EgovSyncList"
     * @throws Exception
     */
	@IncludedMblInfo(name="동기화 서비스",order = 505 ,gid = 50)
	@RequestMapping(value="/mbl/com/syn/selectSyncList.mdo")
	public String selectSynList (
			@ModelAttribute("searchVO") SyncVO searchVO,
			@ModelAttribute("syncForm") Sync sync,
			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> SyncList = syncService.selectSyncList(searchVO);
        model.addAttribute("resultList", SyncList);

        int totCnt = syncService.selectSyncListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/syn/EgovSyncList";
	}

	/**
	 * 동기화 상세정보 조회 Service interface 호출 및 결과를 반환한다.
	 * @param sync
	 * @param model
	 * @return "/mbl/com/syn/EgovSyncDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/selectSync.mdo")
	public String selectSyn(
			@ModelAttribute("searchVO") SyncVO sync,
 			ModelMap model
 			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		Sync vo = syncService.selectSync(sync);
		model.addAttribute("result", vo);
		model.addAttribute("searchVO", sync);

		return "egovframework/mbl/com/syn/EgovSyncDetail";
	}

	/**
	 * 동기화 서비스 수정 화면으로 이동한다.
     * @param syncVO
	 * @param model
	 * @return "/mbl/com/syn/EgovSyncUpdt"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/goSyncUpdt.mdo")
	public String goSynUpdt(
			@ModelAttribute("syncForm") SyncVO syncVO,
 			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		Sync vo = syncService.selectSync(syncVO);
		model.addAttribute("syncForm", vo);

        return "egovframework/mbl/com/syn/EgovSyncUpdt";
	}

	/**
	 * 동기화 서비스 수정 Service interface 호출 및 결과를 반환한다.
	 * @param sync
	 * @param bindingResult
	 * @param model
	 * @return "forward:/mbl/com/syn/selectSyncList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/updateSync.mdo")
	public String updateSyn(
    		@ModelAttribute("syncForm") Sync sync,
            BindingResult bindingResult,
            ModelMap model)
            throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	beanValidator.validate(sync, bindingResult);

		if(bindingResult.hasErrors()){
			return "forward:/mbl/com/syn/goSyncUpdt.mdo";
		}

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	sync.setMberId(mberId);
		syncService.updateSync(sync);

        return "forward:/mbl/com/syn/selectSyncList.mdo";
	}

	/**
	 * 동기화 서비스 삭제 Service interface 호출 및 결과를 반환한다.
	 * @param syncVO
	 * @return "forward:/mbl/com/syn/selectSyncList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/syn/deleteSync.mdo")
	public String deleteSync(
			@ModelAttribute("syncForm") SyncVO syncVO,
			Sync sync
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		syncService.deleteSync(syncVO);

        return "forward:/mbl/com/syn/selectSyncList.mdo";
	}
}
