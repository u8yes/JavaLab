package egovframework.mbl.com.ows.web;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.ows.service.EgovOfflineWebService;
import egovframework.mbl.com.ows.service.OfflineWeb;
import egovframework.mbl.com.ows.service.OfflineWebVO;

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
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * -오프라인 서비스에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 오프라인 서비스 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
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
public class EgovOfflineWebController {

	@Resource(name = "OfflineWebService")
	private EgovOfflineWebService offlineWebService;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	//---------------------------------------------------------------------------------------------------------/
	// 오프라인 서비스 모바일 ----------------------------------------------------------------------------------------/
	//---------------------------------------------------------------------------------------------------------/
	/**
	 * 오프라인 서비스 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 */
	@IncludedMblInfo(name="오프라인 웹 서비스",order = 409 ,gid = 40)
	@RequestMapping(value="/mbl/com/ows/goMobileOfflineWebList.mdo")
	public ModelAndView goMobileOfflineWebList(
			@ModelAttribute OfflineWebVO searchVO,
			Model model) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("egovframework/mbl/com/ows/EgovMobileOfflineWebList");
		return modelAndView;
	}

	/**
	 * 오프라인 서비스 글 목록 조회
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/selectMobileOfflineWebList.mdo")
	public ModelAndView selectMobileOfflineWebList(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			Model model) throws Exception {

		List<?> rtnList = offlineWebService.selectOfflineWebList(searchVO);
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("offlineList", rtnList);
		modelAndView.addObject("success", true);

		return modelAndView;
	}

	/**
	 * 오프라인 상세조회 페이지 이동
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 */
	@RequestMapping(value="/mbl/com/ows/goMobileOfflineWeb.mdo")
	public ModelAndView goMobileOfflineWeb(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			Model model) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("searchVO", searchVO);
		modelAndView.addObject("sn", searchVO.getSn());

		modelAndView.setViewName("egovframework/mbl/com/ows/EgovMobileOfflineWeb");
		return modelAndView;
	}

	/**
	 * 오프라인 서비스 상세 글 조회
	 *
	 * @param searchVO
	 * @param model
	 *
	 * @return ModelAndView;
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/selectMobileOfflineWeb.mdo")
	public ModelAndView selectMobileOfflineWeb(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			Model model) throws Exception {

		OfflineWeb OfflineWeb = offlineWebService.selectOfflineWeb(searchVO);
		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 1. 조회 결과를 ModelAndView에 할당한다.
		modelAndView.addObject("offlineWebView", OfflineWeb);
		modelAndView.addObject("searchVO", searchVO);

		modelAndView.addObject("success", true);
		return modelAndView;
	}

	//---------------------------------------------------------------------------------------------------------/
	// 오프라인 서비스 관리자 ----------------------------------------------------------------------------------------/
	//---------------------------------------------------------------------------------------------------------/
	/**
	 * 오프라인 서비스 등록 화면으로 이동한다.
	 * @param searchVO
	 * @param model
	 * @return "/mbl/com/ows/EgovOfflineWebRegist"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/goOfflineWebRegist.mdo")
	public String goOfflineWebRegist(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		model.addAttribute("offlineWebForm", new OfflineWeb());

        return "egovframework/mbl/com/ows/EgovOfflineWebInsert";
	}

	/**
	 * 오프라인 서비스 등록 Service interface 호출 및 결과를 반환한다.
	 * @param multiRequest
	 * @param searchVO
	 * @param OfflineWeb
	 * @param bindingResult
	 * @param model
	 * @return "forward:/mbl/com/ows/selectOfflineWebList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/insertOfflineWeb.mdo")
	public String insertOfflineWeb(
			@ModelAttribute("offlineWebForm") OfflineWeb offlineWeb,
			BindingResult bindingResult,
			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	beanValidator.validate(offlineWeb, bindingResult);

		if(bindingResult.hasErrors()){
			return "egovframework/mbl/com/ows/EgovOfflineWebInsert";
		}

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	offlineWeb.setMberId(mberId);

    	offlineWebService.insertOfflineWeb(offlineWeb);
        return "forward:/mbl/com/ows/selectOfflineWebList.mdo";
	}

	/**
	 * 오프라인 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/ows/EgovOfflineWebList"
     * @throws Exception
     */
	@IncludedMblInfo(name="오프라인 웹 서비스",order = 507 ,gid = 50)
	@RequestMapping(value="/mbl/com/ows/selectOfflineWebList.mdo")
	public String selectOfflineWebList (
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			@ModelAttribute("offlineWebForm") OfflineWeb offlineWeb,
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

        List<?> OfflineWebList = offlineWebService.selectOfflineWebList(searchVO);
        model.addAttribute("resultList", OfflineWebList);

        int totCnt = offlineWebService.selectOfflineWebListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/ows/EgovOfflineWebList";
	}

	/**
	 * 오프라인 상세정보 조회 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param OfflineWeb
	 * @param model
	 * @return "/mbl/com/ows/EgovOfflineWebDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/selectOfflineWeb.mdo")
	public String selectOfflineWeb(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
			OfflineWeb offlineWeb,
 			ModelMap model
 			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		OfflineWeb vo = offlineWebService.selectOfflineWeb(searchVO);
		model.addAttribute("result", vo);
		model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/ows/EgovOfflineWebDetail";
	}

	/**
	 * 오프라인 서비스 수정 화면으로 이동한다.
     * @param searchVO
	 * @param OfflineWeb
	 * @param model
	 * @return "/mbl/com/ows/EgovOfflineWebUpdt"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/goOfflineWebUpdt.mdo")
	public String goOfflineWebUpdt(
			@ModelAttribute("searchVO") OfflineWebVO searchVO,
 			OfflineWeb offlineWeb,
 			ModelMap model
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		OfflineWeb vo = offlineWebService.selectOfflineWeb(searchVO);

		model.addAttribute("offlineWebForm", vo);

        return "egovframework/mbl/com/ows/EgovOfflineWebUpdt";
	}

	/**
	 * 오프라인 서비스 수정 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param OfflineWeb
	 * @param model
	 * @return "forward:/mbl/com/ows/selectOfflineWebList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/updateOfflineWeb.mdo")
	public String updateOfflineWeb(
    		@ModelAttribute("offlineWebForm") OfflineWeb offlineWeb,
            BindingResult bindingResult,
            ModelMap model)
            throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	beanValidator.validate(offlineWeb, bindingResult);

		if(bindingResult.hasErrors()){
			return "forward:/mbl/com/syn/goOfflineWebUpdt.mdo";
		}

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	offlineWeb.setMberId(mberId);
		offlineWebService.updateOfflineWeb(offlineWeb);

		return "forward:/mbl/com/ows/selectOfflineWebList.mdo";
	}

	/**
	 * 오프라인 서비스 삭제 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param OfflineWeb
	 * @return "forward:/mbl/com/ows/selectOfflineWebList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/ows/deleteOfflineWeb.mdo")
	public String deleteOfflineWeb(
			@ModelAttribute("offlineWebForm") OfflineWebVO offlineWebVO,
			OfflineWeb offlineWeb
			) throws Exception {

		// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

		offlineWebService.deleteOfflineWeb(offlineWebVO);
        return "forward:/mbl/com/ows/selectOfflineWebList.mdo";
	}
}
