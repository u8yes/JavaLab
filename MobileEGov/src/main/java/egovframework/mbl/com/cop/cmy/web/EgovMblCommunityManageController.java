package egovframework.mbl.com.cop.cmy.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.EgovBBSManageService;
import egovframework.com.cop.clb.service.Club;
import egovframework.com.cop.clb.service.ClubVO;
import egovframework.com.cop.clb.service.EgovClubManageService;
import egovframework.com.cop.cmy.service.CommunityUser;
import egovframework.com.cop.cmy.service.CommunityVO;
import egovframework.com.cop.cmy.service.ConfirmHistory;
import egovframework.com.cop.cmy.service.EgovCommunityManageService;
import egovframework.com.cop.cmy.service.EgovConfirmManageService;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 커뮤니티 정보를 관리하기 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *   -------       --------    ---------------------------
 *   2009. 4. 2  이삼섭          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblCommunityManageController {
	
    @Resource(name = "EgovCommunityManageService")
    private EgovCommunityManageService cmmntyService;

    @Resource(name = "EgovBBSAttributeManageService")
    private EgovBBSAttributeManageService bbsAttrbService;

    @Resource(name = "EgovClubManageService")
    private EgovClubManageService clubService;

    @Resource(name = "EgovConfirmManageService")
    private EgovConfirmManageService confmService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;

    /**
     * 포트릿을 위한 커뮤니티 정보 목록 정보를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cus/CmmntyListPortlet.mdo")
    public String selectCmmntyListPortlet(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
	List<CommunityVO> result = cmmntyService.selectCmmntyListPortlet(cmmntyVO);
	
	model.addAttribute("resultList", result);

	return "egovframework/mbl/com/cop/cmy/EgovCmmntyListPortlet";
    }

    /**
     * 커뮤니티 메인페이지를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedMblInfo(name="커뮤니티",order = 105 ,gid = 10, listUrl = "/cop/cmy/CmmntyMainPage.mdo?cmmntyId=CMMNTY_0000000000001")
    @RequestMapping("/cop/cmy/CmmntyMainPage.mdo")
    public String selectCmmntyMainPage(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	if(!isAuthenticated) {
        return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
    }

	cmmntyVO.setEmplyrId(user.getUniqId());

	String tmplatCours = cmmntyService.selectCmmntyTemplat(cmmntyVO);
	if ("".equals(tmplatCours) || tmplatCours == null) {
	  
		tmplatCours = "egovframework/com/cop/tpl/EgovCmmntyBaseTmpl";
	}
	
	// 모바일 접근의 경우 mbl/com/을 추가하여 모바일 템플릿으로 경로를 변경한다.
	/*
	 * 모바일용 커뮤니티를 사용하기 위해서는 웹용 커뮤니티 템플릿 외에 모바일용 커뮤니티 템플릿이  필요함
	 * 경로는 기존 커뮤니티 템플릿이 있는 위치와 동일한 mbl하위의 경로에 추가함
	 * 예::웹용 템플릿 url : egovframework/com/cop/tpl/EgovCmmntyBaseTmpl
	 *   모바일용 템플릿 url : egovframework/mbl/com/cop/tpl/EgovCmmntyBaseTmpl
	 */
	tmplatCours = tmplatCours.replace("/com/", "/mbl/com/");
	
	
	Map<String, Object> map = cmmntyService.selectCommunityInf(cmmntyVO);

	//model.addAttribute("cmmntyVO", cmmntyVO);
	model.addAttribute("cmmntyVO", (CommunityVO)map.get("cmmntyVO"));
	model.addAttribute("cmmntyUser", (CommunityUser)map.get("cmmntyUser"));
		
	//--------------------------------
	// 게시판 목록 정보 처리
	//--------------------------------
	BoardMasterVO bbsVo = new BoardMasterVO();
	
	bbsVo.setTrgetId(cmmntyVO.getCmmntyId());
	
	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);

	model.addAttribute("bbsList", bbsResult);
	////------------------------------

	//--------------------------------
	// 동호회 목록 정보
	//--------------------------------
	Club clubVo = new Club();
	
	clubVo.setCmmntyId(cmmntyVO.getCmmntyId());
	
	List<ClubVO> clubResult = clubService.selectClubListPortletByTrget(clubVo);

	model.addAttribute("clubList", clubResult);
	////------------------------------

	if (isAuthenticated) {
		
	    model.addAttribute("isAuthenticated", "Y");
	} else {
		
	    model.addAttribute("isAuthenticated", "N");
	}
	
	return tmplatCours;
    }
	
    /**
     * 커뮤니티 메인페이지의 기본 내용(게시판 4개 표시) 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/CmmntyMainContents.mdo")
    public String selectCmmntyMainContents(@ModelAttribute("searchVO") CommunityVO cmmntyVO, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	@SuppressWarnings("unused")
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	cmmntyVO.setEmplyrId(user.getUniqId());

	//--------------------------------
	// 게시판 목록 정보 처리
	//--------------------------------
	BoardMasterVO bbsVo = new BoardMasterVO();
	
	bbsVo.setTrgetId(cmmntyVO.getCmmntyId());
	
	List<BoardMasterVO> bbsResult = bbsAttrbService.selectAllBdMstrByTrget(bbsVo);

	// 방명록 제외 처리
	for (int i = 0; i < bbsResult.size(); i++) {
	    if ("BBST04".equals(bbsResult.get(i).getBbsTyCode())) {
		bbsResult.remove(i);
	    }
	}

	model.addAttribute("bbsList", bbsResult);

	//--------------------------------
	// 게시물 목록 정보 처리
	//--------------------------------
	BoardVO boardVo = null;
	BoardMasterVO masterVo = null;
	
	ArrayList<Object> target = new ArrayList<Object>();	// Object => List<BoardVO>
	for (int i = 0; i < bbsResult.size() && i < 4; i++) {
	    masterVo = bbsResult.get(i);
	    boardVo = new BoardVO();

	    boardVo.setBbsId(masterVo.getBbsId());
	    boardVo.setBbsNm(masterVo.getBbsNm());

	    boardVo.setPageUnit(4);
	    boardVo.setPageSize(4);

	    boardVo.setFirstIndex(0);
	    boardVo.setRecordCountPerPage(4);

	    Map<String, Object> map = bbsMngService.selectBoardArticles(boardVo, masterVo.getBbsAttrbCode());

	    target.add(map.get("resultList"));
	}

	model.addAttribute("articleList", target);

	return "egovframework/mbl/com/cop/com/template/EgovCmmntyBaseTmplContents";
    }

    /**
     * 커뮤니티 사용신청을 등록한다.
     * 
     * @param cmmntyUser
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCmmntyUserBySelf.mdo")
    public String insertCmmntyUserBySelf(@ModelAttribute("searchVO") CommunityVO cmmntyVO, @ModelAttribute("cmmntyUser") CommunityUser cmmntyUser, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	String retVal = "";

	if ("".equals(cmmntyUser.getMngrAt())) {
	    cmmntyUser.setMngrAt("N");
	}
	cmmntyUser.setUseAt("Y");
	cmmntyUser.setFrstRegisterId(user.getUniqId());
	cmmntyUser.setEmplyrId(user.getUniqId());

	String tmplatCours = cmmntyService.selectCmmntyTemplat(cmmntyVO);
	if ("".equals(tmplatCours) || tmplatCours == null) {
	    tmplatCours = "egovframework/cop/tpl/EgovCmmntyBaseTmpl";
	}
	
	// 모바일 접근의 경우 mbl/com/을 추가하여 모바일 템플릿으로 경로를 변경한다.
	tmplatCours = "egovframework/mbl/com/"+tmplatCours;
	
	if (isAuthenticated) {

	    //---------------------------------------------
	    // 승인요청 처리
	    //---------------------------------------------
	    //retVal = cmmntyService.insertCommunityUserInf(cmmntyUser);
	    retVal = cmmntyService.checkCommunityUserInf(cmmntyUser);

	    if (!retVal.equals("EXIST")) {
		
		//CommunityVO cmmntyVO = new CommunityVO();
		
		cmmntyVO.setCmmntyId(cmmntyUser.getCmmntyId());
		
		CommunityUser manager = cmmntyService.selectManager(cmmntyVO);

		ConfirmHistory history = new ConfirmHistory();

		history.setConfmRqesterId(user.getUniqId()); 		// 요청자 ID
		history.setConfmerId(manager.getEmplyrId()); 		// 관리자
		history.setConfmTyCode("CF11"); 			// 커뮤니티사용자등록
		history.setConfmSttusCode("AP01"); 			// 승인요청
		history.setOpertTyCode("WC01"); 			// 회원가입
		history.setOpertId(""); 				// 작업자 ID
		history.setTrgetJobTyCode("CMY"); 			// 대상작업구분
		history.setTrgetJobId(cmmntyUser.getCmmntyId());	// 대상작업 ID

		int cnt = confmService.countConfirmRequest(history);

		if (cnt == 0) {
		    confmService.insertConfirmRequest(history);
		} else {
		    retVal = "ING";
		}
	    }
	    ////-------------------------------------------
	}

	model.addAttribute("returnMsg", retVal);
	
	return "egovframework/mbl/com/cop/cmy/EgovCmmntyMsg";
    }

    /**
     * 커뮤니티 탈퇴신청을 처리한다.
     * 
     * @param cmmntyUser
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/deleteCmmntyUserBySelf.mdo")
    public String deleteCmmntyUserBySelf(@ModelAttribute("cmmntyUser") CommunityUser cmmntyUser, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	String retVal = "DEL_REQ_SUCCESS";

	cmmntyUser.setUseAt("N");
	cmmntyUser.setLastUpdusrId(user.getUniqId());
	cmmntyUser.setEmplyrId(user.getUniqId());
	cmmntyUser.setSecsnDe(EgovDateUtil.getToday());

	if (isAuthenticated) {
	    ConfirmHistory history = new ConfirmHistory();
	    
	    history.setConfmRqesterId(user.getUniqId());
	    history.setConfmerId(cmmntyUser.getEmplyrId());
	    history.setConfmTyCode("CF12"); //006
	    history.setConfmSttusCode("AP01"); //007
	    history.setOpertTyCode("WC03");
	    history.setOpertId("");
	    history.setTrgetJobTyCode("CMY");
	    history.setTrgetJobId(cmmntyUser.getCmmntyId());

	    confmService.insertConfirmRequest(history);

	    //cmmntyService.deleteCommunityUserInf(cmmntyUser);
	}

	model.addAttribute("returnMsg", retVal);
	
	return "egovframework/mbl/com/cop/cmy/EgovCmmntyMsg";
    }
}
