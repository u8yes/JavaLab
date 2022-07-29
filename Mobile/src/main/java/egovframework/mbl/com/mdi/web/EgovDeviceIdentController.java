package egovframework.mbl.com.mdi.web;

import java.util.List;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mdi.service.DeviceIdent;
import egovframework.mbl.com.mdi.service.DeviceIdentVO;
import egovframework.mbl.com.mdi.service.EgovDeviceIdentService;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * - 모바일 기기 식별에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 모바일 기기 식별정보에 대한 등록, 수정, 삭제, 조회 요청과 User-Agent값 조회, 모바일기기 정보 추출 요청 사항을 Service와
 * 매핑 처리한다.
 * @author 정홍규
 * @since 2011.08.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.19  정홍규          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovDeviceIdentController {

	/**
	 * EgovDeviceIdentService
	 */
	@Resource(name = "DeviceIdentService")
	private EgovDeviceIdentService deviceIdentService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService egovCmmUseService;

	/**
	 * 모바일 기기 식별 정보 화면으로 이동한다.
	 * @param model
	 * @return "/mbl/com/mdi/EgovMobileDeviceIdentList"
	 * @throws Exception
	 */
	@IncludedMblInfo(name = "모바일 기기 식별", order = 406, gid = 40)
	@RequestMapping(value = "/mbl/com/mdi/goMobileDeviceIdent.mdo")
	public String goMobileDeviceIdentList(ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		return "egovframework/mbl/com/mdi/EgovMobileDeviceIdent";
	}

	/**
	 * 모바일 기기 정보 조회 Service interface 호출 및 결과를
	 * 반환한다.(JSON 통신)
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/selectMobileDeviceIdent.mdo")
	public ModelAndView selectMobileDeviceIdentList(ModelMap model, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		String uagentInfo = request.getHeader("user-agent");
		//        String[] uagentInfoData = uagentInfo.split(";", 5);

		DeviceIdent deviceIdent = deviceIdentService.getDeviceIdentFromXML(uagentInfo);

		if (deviceIdent == null) {

			deviceIdent = new DeviceIdent();

			// 로그인VO에서 사용자 정보 가져오기
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			String mberId = loginVO.getId();

			deviceIdent.setMberId(mberId);

			// Unknown 코드 및 명 입력
			deviceIdent.setBrowserCode("BRS001");
			deviceIdent.setBrowserNm("Unknown");
			//            deviceIdent.setBrowserNm(uagentInfoData[1]);
			deviceIdent.setOsCode("OS01");
			deviceIdent.setOsNm("Unknown");
			//            deviceIdent.setOsNm(uagentInfoData[2]);

			//System.out.println("uerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfo");
			//System.out.println(uagentInfo);
			//System.out.println("uerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfouerinfo");

			// User-Agent 입력
			deviceIdent.setUagentInfo(uagentInfo);

			// 등록상태 입력
			deviceIdent.setRecentCode("REG01");

			// DB 저장
			deviceIdentService.insertDeviceIdent(deviceIdent);

			// XML 파일 생성
			deviceIdentService.createDeviceIndentListToXML();
		}

		modelAndView.addObject("result", deviceIdent);

		return modelAndView;
	}

	/**
	 * 모바일 기기 정보 등록 화면으로 이동한다.
	 * @param searchVO
	 * @param model
	 * @return "/mbl/com/mdi/EgovDeviceIdentRegist"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/goDeviceIdentRegist.mdo")
	public String goDeviceIdentRegist(@ModelAttribute("searchVO") DeviceIdentVO searchVO, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		model.addAttribute("deviceIdent", new DeviceIdent());
		model.addAttribute("browserCmmCodeDetailList", getCmmCodeDetailList("COM083"));
		model.addAttribute("osCmmCodeDetailList", getCmmCodeDetailList("COM084"));

		return "egovframework/mbl/com/mdi/EgovDeviceIdentRegist";
	}

	/**
	 * 모바일 기기 정보 등록 Service interface 호출 및 결과를 반환한다.
	 * @param multiRequest
	 * @param searchVO
	 * @param deviceIdent
	 * @param bindingResult
	 * @param model
	 * @return
	 *         "forward:/mbl/com/mdi/selectDeviceIdentList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/insertDeviceIdent.mdo")
	public String insertDeviceIdent(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") DeviceIdentVO searchVO,
			@ModelAttribute("deviceIdent") DeviceIdent deviceIdent, BindingResult bindingResult, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		beanValidator.validate(deviceIdent, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("browserCmmCodeDetailList", getCmmCodeDetailList("COM083"));
			model.addAttribute("osCmmCodeDetailList", getCmmCodeDetailList("COM084"));

			return "egovframework/mbl/com/mdi/EgovDeviceIdentRegist";
		}

		// 로그인VO에서 사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		String mberId = loginVO.getId();

		deviceIdent.setMberId(mberId);
		deviceIdent.setBrowserNm("COM083");
		deviceIdent.setOsNm("COM084");

		deviceIdentService.insertDeviceIdent(deviceIdent);

		// XML 파일 생성
		deviceIdentService.createDeviceIndentListToXML();

		return "forward:/mbl/com/mdi/selectDeviceIdentList.mdo";
	}

	/**
	 * 모바일 기기 목록 조회 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param model
	 * @return "/mbl/com/mdi/EgovDeviceIdentList"
	 * @throws Exception
	 */
	@IncludedMblInfo(name = "모바일 기기 식별", order = 504, gid = 50)
	@RequestMapping(value = "/mbl/com/mdi/selectDeviceIdentList.mdo")
	public String selectDeviceIdentList(@ModelAttribute("searchVO") DeviceIdentVO searchVO, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
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

		List<?> deviceIdentList = deviceIdentService.selectDeviceIdentList(searchVO);
		model.addAttribute("resultList", deviceIdentList);

		int totCnt = deviceIdentService.selectDeviceIdentListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/mdi/EgovDeviceIdentList";
	}

	/**
	 * 모바일 기기 상세정보 조회 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param deviceIdent
	 * @param model
	 * @return "/mbl/com/mdi/EgovDeviceIdentDetail"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/selectDeviceIdent.mdo")
	public String selectDeviceIdent(@ModelAttribute("searchVO") DeviceIdentVO searchVO, DeviceIdent deviceIdent, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		DeviceIdent vo = deviceIdentService.selectDeviceIdent(deviceIdent);
		model.addAttribute("result", vo);
		model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/mdi/EgovDeviceIdentDetail";
	}

	/**
	 * 모바일 기기 정보 수정 화면으로 이동한다.
	 * @param searchVO
	 * @param deviceIdent
	 * @param model
	 * @return "/mbl/com/mdi/EgovDeviceIdentUpdt"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/goDeviceIdentUpdt.mdo")
	public String goDeviceIdentUpdt(@ModelAttribute("searchVO") DeviceIdentVO searchVO, DeviceIdent deviceIdent, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		DeviceIdent vo = deviceIdentService.selectDeviceIdent(deviceIdent);
		model.addAttribute("deviceIdent", vo);
		model.addAttribute("browserCmmCodeDetailList", getCmmCodeDetailList("COM083"));
		model.addAttribute("osCmmCodeDetailList", getCmmCodeDetailList("COM084"));

		return "egovframework/mbl/com/mdi/EgovDeviceIdentUpdt";
	}

	/**
	 * 모바일 기기 정보 수정 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param deviceIdent
	 * @param model
	 * @return
	 *         "forward:/mbl/com/mdi/selectDeviceIdentList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/updateDeviceIdent.mdo")
	public String updateDeviceIdent(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") DeviceIdentVO searchVO,
			@ModelAttribute("deviceIdent") DeviceIdent deviceIdent, BindingResult bindingResult, ModelMap model) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		// Validation
		beanValidator.validate(deviceIdent, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("browserCmmCodeDetailList", getCmmCodeDetailList("COM083"));
			model.addAttribute("osCmmCodeDetailList", getCmmCodeDetailList("COM084"));

			return "egovframework/mbl/com/mdi/EgovDeviceIdentUpdt";

		}

		deviceIdentService.updateDeviceIdent(deviceIdent);

		// XML 파일 생성
		deviceIdentService.createDeviceIndentListToXML();

		return "forward:/mbl/com/mdi/selectDeviceIdentList.mdo";
	}

	/**
	 * 모바일 기기 정보 삭제 Service interface 호출 및 결과를 반환한다.
	 * @param searchVO
	 * @param deviceIdent
	 * @return
	 *         "forward:/mbl/com/mdi/selectDeviceIdentList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mbl/com/mdi/deleteDeviceIdent.mdo")
	public String deleteDeviceIdent(@ModelAttribute("searchVO") DeviceIdentVO deviceIdentVO, DeviceIdent deviceIdent) throws Exception {

		// 권한 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}

		deviceIdentService.deleteDeviceIdent(deviceIdent);

		// XML 파일 생성
		deviceIdentService.createDeviceIndentListToXML();

		return "forward:/mbl/com/mdi/selectDeviceIdentList.mdo";
	}

	/**
	 * 공통코드 호출
	 * @param codeId
	 *        String
	 * @return List
	 * @exception Exception
	 */
	public List<?> getCmmCodeDetailList(String codeId) throws Exception {
		ComDefaultCodeVO comDefaultCodeVO = new ComDefaultCodeVO();
		comDefaultCodeVO.setCodeId(codeId);
		return egovCmmUseService.selectCmmCodeDetail(comDefaultCodeVO);
	}
}
