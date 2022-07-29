package egovframework.mbl.com.mgr.web;

import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mgr.service.EgovMobileMenuService;
import egovframework.mbl.com.mgr.service.MenuVO;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * @Class Name : EgovMobileMenuController.java
 * @Description : 메뉴관리 위한 Controller
 * @Modification Information
 *
 *   수정일          수정자              수정내용
 *  ----------		--------	---------------------------
 *  2014.09.11		 표준프레임워크              최초 생성
 *
 *  @author 표준프레임워크
 *  @since 2014.09.11
 *  @version 1.0
 *
 */

@Controller
public class EgovMobileMenuController {

	@Autowired
	private DefaultBeanValidator beanValidator;

	@Resource(name="egovMobileMenuService")
	private EgovMobileMenuService mobileMenuService;

	@Resource(name = "egovMobileMenuIdGnrService")
    private EgovIdGnrService idgenService;

	/**
	 * 첫번째 메인 menu화면 출력
	 *
	 * @param MenuVO
     * @return EgovMainMenu화면
     * @throws Exception
	 * */
	@IncludedMblInfo(name = "모바일 메뉴", order = 256, gid = 20)
	@RequestMapping("/mgr/EgovMainMenu.mdo")
	public String showFirstPage(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
								HttpServletRequest request, ModelMap model) throws Exception {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated ){
			return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
		}
		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		Map<String, Object> map = mobileMenuService.selectUseList(vo);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("loginVO", loginVo);

		return "egovframework/mbl/com/mgr/EgovMainMenu";
	}


	/**
	 * db 내에 생성한 목록 조회
	 *
	 * @param MenuVO
	 * @return EgovMenuList화면
     * @throws Exception
	 * */
	@IncludedMblInfo(name = "모바일 메뉴등록", order = 257, gid = 20)
	@RequestMapping("/mgr/EgovMenuList.mdo")
	public String selectMenuList(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
			 					 HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		Map<String, Object> map = mobileMenuService.selectMenuList(vo);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("loginVO", loginVo);

		return "egovframework/mbl/com/mgr/EgovMenuList";
	}


	/**
	 * 생성한 Menu목록 중 선택한 사항에 대한 상세조회
	 *
	 * @param MenuVO
	 * @return EgovMenuListDetail화면
     * @throws Exception
	 * */
	@RequestMapping("/mgr/EgovMenuListDetail.mdo")
	public String selectMenuDetail(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
			 						HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		MenuVO copVo = mobileMenuService.selectMenuDetail(vo);
		model.addAttribute("result", copVo);

		return "egovframework/mbl/com/mgr/EgovMenuListDetail";
	}

	/**
	 * 상세조회에서 수정페이지로 이동
	 *
	 *
	 * */
	@RequestMapping("/mgr/EgovMenuUpdatePage.mdo")
	public String updateMenuListPage(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
			 						  HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		Map<String, Object> map = mobileMenuService.selectUpperMenuList(vo);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultUpdate", vo);

		return "egovframework/mbl/com/mgr/EgovMenuUpdate";
	}

	/**
	 * 상세조회한 내용 수정
	 *
	 * @param MenuVO
	 * @return EgovMenuList화면
     * @throws Exception
	 * */
	@RequestMapping("/mgr/EgovMenuUpdate.mdo")
	public String updateMenuList(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
			 					  HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		String lastUpdusrId = loginVo.getId();
		vo.setLastUpdusrId(lastUpdusrId);
		mobileMenuService.updateMenu(vo);

		return "forward:/mgr/EgovMenuList.mdo";
	}

    /**
	 * 등록 페이지로 이동
	 *
	 * @return EgovMenuRegist화면
     * @throws Exception
	 * */
	@RequestMapping("/mgr/EgovMenuRegistPage.mdo")
	public String registMenuListPage(@ModelAttribute("menuVO") MenuVO copVO, @ModelAttribute("loginVO") LoginVO loginVO,
									  HttpServletRequest request, ModelMap model) throws Exception{

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		Map<String, Object> map = mobileMenuService.selectUpperMenuList(copVO);
		model.addAttribute("resultList", map.get("resultList"));

		return "egovframework/mbl/com/mgr/EgovMenuRegist";
	}

	/**
	 * 등록 로직
	 *
	 * @return EgovMenuList화면
     * @throws Exception
	 * */
	@RequestMapping("/mgr/EgovMenuRegist.mdo")
	public String registMenuList(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
								 BindingResult bindingResult, HttpServletRequest request,
					             ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		//서버  validate 체크
        beanValidator.validate(vo, bindingResult);
		if(bindingResult.hasErrors()){
	        return "egovframework/mbl/com/mgr/EgovMenuRegist";
		}

		String frstRegisterId = loginVo.getId();
		vo.setFrstRegisterId(frstRegisterId);

		vo.setMenuCode(idgenService.getNextStringId()); //메뉴코드 set
		mobileMenuService.insertMenu(vo);

		return "forward:/mgr/EgovMenuList.mdo";
	}

	/**
	 * 삭제 요청시 삭제
	 *
	 * @param MenuVO
	 * @return EgovMenuList화면
     * @throws Exception
	 * */
	@RequestMapping("/mgr/EgovDelete.mdo")
	public String deleteMenu(@ModelAttribute("menuVO") MenuVO vo, @ModelAttribute("loginVO") LoginVO loginVO,
							  HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO loginVo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated || !"USR".equals(loginVo.getUserSe())){
			return "forward:/index.jsp";
		}

		mobileMenuService.deleteMenu(vo);

		return "forward:/mgr/EgovMenuList.mdo";
	}
}
