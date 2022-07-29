package egovframework.mbl.com.sym.mnu.mcm.web;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mcm.service.EgovMenuCreateManageService;
import egovframework.com.sym.mnu.mcm.service.MenuCreatVO;
import egovframework.com.sym.mnu.mcm.service.MenuSiteMapVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 모바일 사이트맵 조회 처리를 하는 비즈니스 구현 클래스
 * @author 공통컴포넌트 전환팀 이율경
 * @since 2011.08.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.19  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblSiteMapngController {

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** EgovMenuManageService */
	@Resource(name = "meunCreateManageService")
    private EgovMenuCreateManageService menuCreateManageService;

    /**
     * 사이트맵 화면을 출력한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mcm/EgovSiteMap"
     * @exception Exception
     */
	@IncludedMblInfo(name="사이트맵 Type B",order = 212 ,gid = 20)
    @RequestMapping(value="/sym/mnu/mcm/EgovSiteMapng.mdo")
    public String siteMapView(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {

        return "egovframework/mbl/com/sym/mnu/mcm/EgovSiteMap";
    }

    /**
     * 사이트맵을 조회한다.
     * @param searchVO ComDefaultVO
     * @return modelAndView
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mcm/EgovSiteMapngActor.mdo")
    public ModelAndView selectSiteMapng(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	searchVO.setSearchKeyword(user.getId());

    	MenuCreatVO menuCreteVO = menuCreateManageService.selectAuthorByUsr(searchVO);

    	MenuSiteMapVO menuSiteMapVO = new MenuSiteMapVO();
    	menuSiteMapVO.setAuthorCode(menuCreteVO.getAuthorCode());
    	List<?> menulist = menuCreateManageService.selectMenuCreatSiteMapList(menuSiteMapVO);

    	modelAndView.addObject("menulist", menulist);

        return modelAndView;
    }

    /**
     * 사이트맵을 출력한다. (Web Style)
     * @param searchVO ComDefaultVO
     * @return "egovframework/com/sym/mnu/mcm/EgovWebSiteMap"
     * @exception Exception
     */
    @IncludedMblInfo(name="사이트맵 Type A",order = 211 ,gid = 20)
    @RequestMapping(value="/sym/mnu/mcm/EgovWebSiteMapView.mdo")
    public String webSiteMapView(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {

        return "egovframework/mbl/com/sym/mnu/mcm/EgovWebSiteMap";
    }
}
