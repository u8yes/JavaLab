package egovframework.mbl.com.cop.bbs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.EgovBBSCommentService;
import egovframework.com.cop.bbs.service.EgovBBSManageService;
import egovframework.com.cop.bbs.service.EgovBBSSatisfactionService;
import egovframework.com.cop.bbs.service.EgovBBSScrapService;
import egovframework.com.utl.sim.service.EgovFileScrty;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *
 * </pre>
 */
@Controller
public class EgovMblBBSManageController {

    @Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;

    @Resource(name = "EgovBBSAttributeManageService")
    private EgovBBSAttributeManageService bbsAttrbService;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    //---------------------------------
    // 2009.06.29 : 2단계 기능 추가
    // 2011.07.01 : 댓글, 스크랩, 만족도 조사 기능의 종속성 제거
    //---------------------------------
    @Autowired(required=false)
    private EgovBBSCommentService bbsCommentService;

    @Autowired(required=false)
    private EgovBBSSatisfactionService bbsSatisfactionService;

    @Autowired(required=false)
    private EgovBBSScrapService bbsScrapService;
    ////-------------------------------

    @Autowired
    private DefaultBeanValidator beanValidator;

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

    /** 첫 화면 공지게시판 링크를 위한 더미 메소드 */
    @IncludedMblInfo(name="공지게시판",order = 102 ,gid = 10, listUrl = "/cop/bbs/selectBoardList.mdo?bbsId=BBSMSTR_000000000003")
    public void noticeBoard() {}

    /** 첫 화면 유효게시판 링크를 위한 더미 메소드 */
    @IncludedMblInfo(name="유효게시판",order = 103 ,gid = 10, listUrl = "/cop/bbs/selectBoardList.mdo?bbsId=BBSMSTR_000000000004")
    public void effectiveBoard() {}


    /**
     * 게시물에 대한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedMblInfo(name="일반게시판",order = 100 ,gid = 10, listUrl = "/cop/bbs/selectBoardList.mdo?bbsId=BBSMSTR_000000000001")
    @RequestMapping("/cop/bbs/selectBoardList.mdo")
    public String selectBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		/*log.debug(this.getClass().getName() + " user.getId() "+ user.getId());
		log.debug(this.getClass().getName() + " user.getName() "+ user.getName());
		log.debug(this.getClass().getName() + " user.getUniqId() "+ user.getUniqId());
		log.debug(this.getClass().getName() + " user.getOrgnztId() "+ user.getOrgnztId());
		log.debug(this.getClass().getName() + " user.getUserSe() "+ user.getUserSe());
		log.debug(this.getClass().getName() + " user.getEmail() "+ user.getEmail());*/
		//String attrbFlag = "";
	
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());
	
		BoardMasterVO vo = new BoardMasterVO();
	
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId(user.getUniqId());
	
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);
		System.out.println("##### master bbsId >>> " + master.getBbsId());
		//-------------------------------
		// 방명록이면 방명록 URL로 forward
		//-------------------------------
		if (master.getBbsTyCode().equals("BBST04")) {
		    return "forward:/cop/bbs/selectGuestList.mdo";
		}
		////-----------------------------
	
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
	
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(1);
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());//2011.09.07
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
		paginationInfo.setTotalRecordCount(totCnt);
	
		//-------------------------------
		// 기본 BBS template 지정
		//-------------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
		}
		////-----------------------------
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("brdMstrVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/mbl/com/cop/bbs/EgovNoticeList";
    }


    /**
     * 게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectBoardArticle.mdo")
    public String selectBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	// 조회수 증가 여부 지정
	boardVO.setPlusCount(true);

	//---------------------------------
	// 2009.06.29 : 2단계 기능 추가
	//---------------------------------
	if (!boardVO.getSubPageIndex().equals("")) {
	    boardVO.setPlusCount(false);
	}
	////-------------------------------

	boardVO.setLastUpdusrId(user.getUniqId());
	BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

	model.addAttribute("result", vo);

	model.addAttribute("sessionUniqId", user.getUniqId());

	//----------------------------
	// template 처리 (기본 BBS template 지정  포함)
	//----------------------------
	BoardMasterVO master = new BoardMasterVO();

	master.setBbsId(boardVO.getBbsId());
	master.setUniqId(user.getUniqId());

	BoardMasterVO masterVo = bbsAttrbService.selectBBSMasterInf(master);

	if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
	    masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", masterVo);
	////-----------------------------

	//----------------------------
	// 2009.06.29 : 2단계 기능 추가
	// 2011.07.01 : 댓글, 스크랩, 만족도 조사 기능의 종속성 제거
	//----------------------------
	if (bbsCommentService != null){
		if (bbsCommentService.canUseComment(boardVO.getBbsId())) {
		    model.addAttribute("useComment", "true");
		}
	}
	if (bbsSatisfactionService != null){
		if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
		    model.addAttribute("useSatisfaction", "true");
		}
	}
	if (bbsScrapService != null){
		if (bbsScrapService.canUseScrap()) {
		    model.addAttribute("useScrap", "true");
		}
	}

	////--------------------------

	// 김연수 추가 2011.07.20
	// 상세화면 조회시 댓글을 위한 작성자 명 추가
	//model.addAttribute("wrterNm", user.getName());

	//댓글테스트
    //model.addAttribute("useComment", "true");
	return "egovframework/mbl/com/cop/bbs/EgovNoticeInqire";
    }

    /**
     *
     *
     * 사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::
     *
     *
     *
     * 게시물 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/addBoardArticle.mdo")
    public String addBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	BoardMasterVO bdMstr = new BoardMasterVO();

	if (isAuthenticated) {

	    BoardMasterVO vo = new BoardMasterVO();
	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId(user.getUniqId());

	    bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
	    model.addAttribute("bdMstr", bdMstr);
	}
	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
	    bdMstr.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bdMstr);
	////-----------------------------

	return "egovframework/mbl/com/cop/bbs/EgovNoticeRegist";
    }

    /**
     * 게시물을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping("/cop/bbs/insertBoardArticle.mdo")
    public String insertBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId(user.getUniqId());

	    master = bbsAttrbService.selectBBSMasterInf(vo);

	    model.addAttribute("bdMstr", master);

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeRegist";
	}

	//if (isAuthenticated) {
	    List<FileVO> result = null;
	    String atchFileId = "";

	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
		result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
		atchFileId = fileMngService.insertFileInfs(result);
	    }
	    board.setAtchFileId(atchFileId);
	    board.setFrstRegisterId(user.getUniqId());
	    board.setBbsId(board.getBbsId());

	    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
	    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	//}

	//status.setComplete();
	return "redirect:/cop/bbs/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/addReplyBoardArticle.mdo")
    public String addReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	BoardMasterVO master = new BoardMasterVO();
	BoardMasterVO vo = new BoardMasterVO();

	vo.setBbsId(boardVO.getBbsId());
	vo.setUniqId(user.getUniqId());

	master = bbsAttrbService.selectBBSMasterInf(vo);

	model.addAttribute("bdMstr", master);
	model.addAttribute("result", boardVO);

	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
	    master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", master);
	////-----------------------------

	return "egovframework/mbl/com/cop/bbs/EgovNoticeReply";
    }

    /**
     * 게시물에 대한 답변을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/replyBoardArticle.mdo")
    public String replyBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model,
	    SessionStatus status) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {
	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId(user.getUniqId());

	    master = bbsAttrbService.selectBBSMasterInf(vo);

	    model.addAttribute("bdMstr", master);
	    model.addAttribute("result", boardVO);

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeReply";
	}

	if (isAuthenticated) {
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    String atchFileId = "";

	    if (!files.isEmpty()) {
		List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
		atchFileId = fileMngService.insertFileInfs(result);
	    }

	    board.setAtchFileId(atchFileId);
	    board.setReplyAt("Y");
	    board.setFrstRegisterId(user.getUniqId());
	    board.setBbsId(board.getBbsId());
	    board.setParnts(Long.toString(boardVO.getNttId()));
	    board.setSortOrdr(boardVO.getSortOrdr());
	    board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));

	    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
	    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	}

	return "redirect:/cop/bbs/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 게시물 수정을 위한 수정페이지로 이동한다.
     *
     * @param boardVO
     * @param vo
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/forUpdateBoardArticle.mdo")
    public String selectBoardArticleForUpdt(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model)
	    throws Exception {

	//log.debug(this.getClass().getName()+"selectBoardArticleForUpdt getNttId "+boardVO.getNttId());
	//log.debug(this.getClass().getName()+"selectBoardArticleForUpdt getBbsId "+boardVO.getBbsId());

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	boardVO.setFrstRegisterId(user.getUniqId());

	BoardMaster master = new BoardMaster();
	BoardMasterVO bmvo = new BoardMasterVO();
	BoardVO bdvo = new BoardVO();

	vo.setBbsId(boardVO.getBbsId());

	master.setBbsId(boardVO.getBbsId());
	master.setUniqId(user.getUniqId());

	if (isAuthenticated) {
	    bmvo = bbsAttrbService.selectBBSMasterInf(master);
	    bdvo = bbsMngService.selectBoardArticle(boardVO);
	}

	model.addAttribute("result", bdvo);
	model.addAttribute("bdMstr", bmvo);

	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
	    bmvo.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bmvo);
	////-----------------------------

	return "egovframework/mbl/com/cop/bbs/EgovNoticeUpdt";
    }

    /**
     * 게시물에 대한 내용을 수정한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateBoardArticle.mdo")
    public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model,
	    SessionStatus status) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	String atchFileId = boardVO.getAtchFileId();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    boardVO.setFrstRegisterId(user.getUniqId());

	    BoardMaster master = new BoardMaster();
	    BoardMasterVO bmvo = new BoardMasterVO();
	    BoardVO bdvo = new BoardVO();

	    master.setBbsId(boardVO.getBbsId());
	    master.setUniqId(user.getUniqId());

	    bmvo = bbsAttrbService.selectBBSMasterInf(master);
	    bdvo = bbsMngService.selectBoardArticle(boardVO);

	    model.addAttribute("result", bdvo);
	    model.addAttribute("bdMstr", bmvo);

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeUpdt";
	}

	/*
	boardVO.setFrstRegisterId(user.getUniqId());
	BoardMaster _bdMstr = new BoardMaster();
	BoardMasterVO bmvo = new BoardMasterVO();
	BoardVO bdvo = new BoardVO();
	vo.setBbsId(boardVO.getBbsId());
	_bdMstr.setBbsId(boardVO.getBbsId());
	_bdMstr.setUniqId(user.getUniqId());

	if (isAuthenticated) {
	    bmvo = bbsAttrbService.selectBBSMasterInf(_bdMstr);
	    bdvo = bbsMngService.selectBoardArticle(boardVO);
	}
	//*/

	if (isAuthenticated) {
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
		if ("".equals(atchFileId)) {
		    List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
		    atchFileId = fileMngService.insertFileInfs(result);
		    board.setAtchFileId(atchFileId);
		} else {
		    FileVO fvo = new FileVO();
		    fvo.setAtchFileId(atchFileId);
		    int cnt = fileMngService.getMaxFileSN(fvo);
		    List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
		    fileMngService.updateFileInfs(_result);
		}
	    }

	    board.setLastUpdusrId(user.getUniqId());

	    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
	    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.updateBoardArticle(board);
	}

	return "redirect:/cop/bbs/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 게시물에 대한 내용을 삭제한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/deleteBoardArticle.mdo")
    public String deleteBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	if (isAuthenticated) {
	    board.setLastUpdusrId(user.getUniqId());

	    bbsMngService.deleteBoardArticle(board);
	}

	return "redirect:/cop/bbs/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 방명록에 대한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectGuestList.mdo")
    public String selectGuestList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	@SuppressWarnings("unused")
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	// 수정 및 삭제 기능 제어를 위한 처리
	model.addAttribute("sessionUniqId", user.getUniqId());

	BoardVO vo = new BoardVO();

	vo.setBbsId(boardVO.getBbsId());
	vo.setBbsNm(boardVO.getBbsNm());
	vo.setNtcrNm(user.getName());
	vo.setNtcrId(user.getUniqId());
	vo.setPageIndex(boardVO.getPageIndex());
	vo.setPageUnit(boardVO.getPageUnit());
	vo.setPageSize(boardVO.getPageSize());

	BoardMasterVO masterVo = new BoardMasterVO();

	masterVo.setBbsId(vo.getBbsId());
	masterVo.setUniqId(user.getUniqId());

	BoardMasterVO mstrVO = bbsAttrbService.selectBBSMasterInf(masterVo);

	PaginationInfo paginationInfo = new PaginationInfo();
	paginationInfo.setCurrentPageNo(vo.getPageIndex());
	paginationInfo.setRecordCountPerPage(10);
	paginationInfo.setPageSize(1);

	vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
	vo.setLastIndex(paginationInfo.getLastRecordIndex());
	vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = bbsMngService.selectGuestList(vo);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	paginationInfo.setTotalRecordCount(totCnt);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("brdMstrVO", mstrVO);
	model.addAttribute("boardVO", vo);
	model.addAttribute("paginationInfo", paginationInfo);

	return "egovframework/mbl/com/cop/bbs/EgovGuestList";
    }

    /**
     * 방명록 수정을 위한 특정 내용을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectSingleGuestList.mdo")
    public String selectSingleGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("brdMstrVO") BoardMasterVO brdMstrVO,
	    ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	@SuppressWarnings("unused")
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

	boardVO.setBbsId(boardVO.getBbsId());
	boardVO.setBbsNm(boardVO.getBbsNm());
	boardVO.setNtcrNm(user.getName());

	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
	boardVO.setPageSize(propertyService.getInt("pageSize"));

	PaginationInfo paginationInfo = new PaginationInfo();
	paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
	//paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
	//paginationInfo.setPageSize(boardVO.getPageSize());
	paginationInfo.setRecordCountPerPage(10);
	paginationInfo.setPageSize(1);

	boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
	boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = bbsMngService.selectGuestList(boardVO);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	paginationInfo.setTotalRecordCount(totCnt);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("boardVO", vo);
	model.addAttribute("brdMstrVO", brdMstrVO);
	model.addAttribute("paginationInfo", paginationInfo);

	return "egovframework/mbl/com/cop/bbs/EgovGuestList";
    }

    /**
     * 방명록에 대한 내용을 삭제한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/deleteGuestList.mdo")
    public String deleteGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board, ModelMap model) throws Exception {
	@SuppressWarnings("unused")
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	if (isAuthenticated) {
	    bbsMngService.deleteGuestList(boardVO);
	}

	return "forward:/cop/bbs/selectGuestList.mdo";
    }

    /**
     * 방명록 수정의 위한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping("/cop/bbs/updateGuestList.mdo")
    public String updateGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

	//BBST02, BBST04
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    BoardVO vo = new BoardVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setBbsNm(boardVO.getBbsNm());
	    vo.setNtcrNm(user.getName());
	    vo.setNtcrId(user.getUniqId());

	    BoardMasterVO masterVo = new BoardMasterVO();

	    masterVo.setBbsId(vo.getBbsId());
	    masterVo.setUniqId(user.getUniqId());

	    BoardMasterVO mstrVO = bbsAttrbService.selectBBSMasterInf(masterVo);

	    vo.setPageUnit(propertyService.getInt("pageUnit"));
	    vo.setPageSize(propertyService.getInt("pageSize"));

	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(vo.getPageIndex());
	    //paginationInfo.setRecordCountPerPage(vo.getPageUnit());
	    //paginationInfo.setPageSize(vo.getPageSize());
	    paginationInfo.setRecordCountPerPage(10);
	    paginationInfo.setPageSize(1);

	    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
	    vo.setLastIndex(paginationInfo.getLastRecordIndex());
	    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	    Map<String, Object> map = bbsMngService.selectGuestList(vo);
	    int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	    paginationInfo.setTotalRecordCount(totCnt);

	    model.addAttribute("resultList", map.get("resultList"));
	    model.addAttribute("resultCnt", map.get("resultCnt"));
	    model.addAttribute("brdMstrVO", mstrVO);
	    model.addAttribute("boardVO", vo);
	    model.addAttribute("paginationInfo", paginationInfo);

	    return "egovframework/mbl/com/cop/bbs/EgovGuestList";
	}

	//if (isAuthenticated) {
	    bbsMngService.updateBoardArticle(board);
	    boardVO.setNttCn("");
	    boardVO.setPassword("");
	    boardVO.setNtcrId("");
	    boardVO.setNttId(0);
	//}
	return "redirect:/cop/bbs/selectGuestList.mdo?bbsId="+boardVO.getBbsId();

    }

    /**
     * 방명록에 대한 내용을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertGuestList.mdo")
    public String insertGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

	//그러니까 무인증은 아니고  - _- 익명으로 등록이 가능한 부분임
	// 무인증이 되려면 별도의 컨트롤러를 하나 더 등록해야함

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    BoardVO vo = new BoardVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setBbsNm(boardVO.getBbsNm());
	    vo.setNtcrNm(user.getName());
	    vo.setNtcrId(user.getUniqId());

	    BoardMasterVO masterVo = new BoardMasterVO();

	    masterVo.setBbsId(vo.getBbsId());
	    masterVo.setUniqId(user.getUniqId());

	    BoardMasterVO mstrVO = bbsAttrbService.selectBBSMasterInf(masterVo);

	    vo.setPageUnit(propertyService.getInt("pageUnit"));
	    vo.setPageSize(propertyService.getInt("pageSize"));

	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(vo.getPageIndex());
	    //paginationInfo.setRecordCountPerPage(vo.getPageUnit());
	    //paginationInfo.setPageSize(vo.getPageSize());
	    paginationInfo.setRecordCountPerPage(10);
	    paginationInfo.setPageSize(1);

	    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
	    vo.setLastIndex(paginationInfo.getLastRecordIndex());
	    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	    Map<String, Object> map = bbsMngService.selectGuestList(vo);
	    int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	    paginationInfo.setTotalRecordCount(totCnt);

	    model.addAttribute("resultList", map.get("resultList"));
	    model.addAttribute("resultCnt", map.get("resultCnt"));
	    model.addAttribute("brdMstrVO", mstrVO);
	    model.addAttribute("boardVO", vo);
	    model.addAttribute("paginationInfo", paginationInfo);

	    return "egovframework/mbl/com/cop/bbs/EgovGuestList";

	}

	if (isAuthenticated) {

	    board.setFrstRegisterId(user.getUniqId());

	    bbsMngService.insertBoardArticle(board);

	    boardVO.setNttCn("");
	    boardVO.setPassword("");
	    boardVO.setNtcrId("");
	    boardVO.setNttId(0);
	}

	return "redirect:/cop/bbs/selectGuestList.mdo?bbsId="+boardVO.getBbsId();

    }

    /**
     * 익명게시물에 대한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedMblInfo(name="익명게시판",order = 101 ,gid = 10, listUrl = "/cop/bbs/anonymous/selectBoardList.mdo?bbsId=BBSMSTR_000000000002")
    @RequestMapping("/cop/bbs/anonymous/selectBoardList.mdo")
    public String selectAnonymousBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	//log.debug(this.getClass().getName() + " user.getId() "+ user.getId());
	//log.debug(this.getClass().getName() + " user.getName() "+ user.getName());
	//log.debug(this.getClass().getName() + " user.getUniqId() "+ user.getUniqId());
	//log.debug(this.getClass().getName() + " user.getOrgnztId() "+ user.getOrgnztId());
	//log.debug(this.getClass().getName() + " user.getUserSe() "+ user.getUserSe());
	//log.debug(this.getClass().getName() + " user.getEmail() "+ user.getEmail());

	//String attrbFlag = "";

	boardVO.setBbsId(boardVO.getBbsId());
	boardVO.setBbsNm(boardVO.getBbsNm());

	BoardMasterVO vo = new BoardMasterVO();

	vo.setBbsId(boardVO.getBbsId());
	vo.setUniqId("ANONYMOUS");	// 익명

	BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

	//-------------------------------
	// 익명게시판이 아니면.. 원래 게시판 URL로 forward
	//-------------------------------
	if (!master.getBbsTyCode().equals("BBST02")) {
		return "forward:/cop/bbs/selectBoardList.mdo";
	}
	////-----------------------------

	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
	boardVO.setPageSize(propertyService.getInt("pageSize"));

	PaginationInfo paginationInfo = new PaginationInfo();

	paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(10);
	paginationInfo.setPageSize(1);

	boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
	boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	paginationInfo.setTotalRecordCount(totCnt);

	//-------------------------------
	// 기본 BBS template 지정
	//-------------------------------
	if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
	    master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}
	////-----------------------------

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("boardVO", boardVO);
	model.addAttribute("brdMstrVO", master);
	model.addAttribute("paginationInfo", paginationInfo);

	model.addAttribute("anonymous", "true");

	return "egovframework/mbl/com/cop/bbs/EgovNoticeList";
    }


    /**
     *
     * 사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::사용안함:::::
     *
     *
     * 익명게시물 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/addBoardArticle.mdo")
    public String addAnonymousBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;

	BoardMasterVO bdMstr = new BoardMasterVO();

	if (isAuthenticated) {
	    BoardMasterVO vo = new BoardMasterVO();
	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId("ANONYMOUS");

	    bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
	    model.addAttribute("bdMstr", bdMstr);
	}

	//-------------------------------
	// 익명게시판이 아니면.. 원래 게시판 URL로 forward
	//-------------------------------
	if (!bdMstr.getBbsTyCode().equals("BBST02")) {
	    return "forward:/cop/bbs/addBoardArticle.mdo";
	}
	////-----------------------------

	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
	    bdMstr.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bdMstr);
	////-----------------------------

	model.addAttribute("anonymous", "true");

	return "egovframework/mbl/com/cop/bbs/EgovNoticeRegist";
    }

    /**
     * 익명게시물을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/insertBoardArticle.mdo")
    public String insertAnonymousBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {

	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId("ANONYMOUS");

	    master = bbsAttrbService.selectBBSMasterInf(vo);

	    model.addAttribute("bdMstr", master);

	    //-------------------------------
	    // 익명게시판이 아니면.. 원래 게시판 URL로 forward
	    //-------------------------------
	    if (!bdMstr.getBbsTyCode().equals("BBST02")) {
	    	return "forward:/cop/bbs/insertBoardArticle.mdo";
	    }
	    ////-----------------------------

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    model.addAttribute("anonymous", "true");

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeRegist";
	}

	if (isAuthenticated) {
	    List<FileVO> result = null;
	    String atchFileId = "";

	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
		result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
		atchFileId = fileMngService.insertFileInfs(result);
	    }
	    board.setAtchFileId(atchFileId);
	    board.setFrstRegisterId("ANONYMOUS");
	    board.setBbsId(board.getBbsId());

	    // 익명게시판 관련
	    board.setNtcrNm(board.getNtcrNm());
	    board.setPassword(EgovFileScrty.encryptPassword(board.getPassword(), board.getBbsId()));

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	}

    model.addAttribute("anonymous", "true");
	//status.setComplete();
	return "redirect:/cop/bbs/anonymous/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 익명게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/selectBoardArticle.mdo")
    public String selectAnonymousBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	// 조회수 증가 여부 지정
	boardVO.setPlusCount(true);

	//---------------------------------
	// 2009.06.29 : 2단계 기능 추가
	//---------------------------------
	if (!boardVO.getSubPageIndex().equals("")) {
	    boardVO.setPlusCount(false);
	}
	////-------------------------------

	boardVO.setLastUpdusrId("ANONYMOUS");
	BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

	model.addAttribute("result", vo);

	model.addAttribute("sessionUniqId", "ANONYMOUS");

	//----------------------------
	// template 처리 (기본 BBS template 지정  포함)
	//----------------------------
	BoardMasterVO master = new BoardMasterVO();

	master.setBbsId(boardVO.getBbsId());
	master.setUniqId("ANONYMOUS");

	BoardMasterVO masterVo = bbsAttrbService.selectBBSMasterInf(master);

	//-------------------------------
	// 익명게시판이 아니면.. 원래 게시판 URL로 forward
	//-------------------------------
	if (!masterVo.getBbsTyCode().equals("BBST02")) {
		return "forward:/cop/bbs/selectBoardArticle.mdo";
	}
	////-----------------------------

	if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
	    masterVo.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", masterVo);
	////-----------------------------

	model.addAttribute("anonymous", "true");

	//----------------------------
	// 2009.06.29 : 2단계 기능 추가
	//----------------------------
	if (bbsCommentService != null){
		if (bbsCommentService.canUseComment(boardVO.getBbsId())) {
		    model.addAttribute("useComment", "true");
		}
	}
	if (bbsSatisfactionService != null){
		if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
		    model.addAttribute("useSatisfaction", "true");
		}
	}
	if (bbsScrapService != null){
		if (bbsScrapService.canUseScrap()) {
		    model.addAttribute("useScrap", "true");
		}
	}

	////--------------------------

	return "egovframework/mbl/com/cop/bbs/EgovNoticeInqire";
    }

    /**
     * 익명게시물에 대한 내용을 삭제한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/deleteBoardArticle.mdo")
    public String deleteAnonymousBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model) throws Exception {

	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;
	//--------------------------------------------------
	// 마스터 정보 얻기
	//--------------------------------------------------
	BoardMasterVO master = new BoardMasterVO();

	master.setBbsId(boardVO.getBbsId());
	master.setUniqId("ANONYMOUS");

	BoardMasterVO masterVo = bbsAttrbService.selectBBSMasterInf(master);

	//-------------------------------
	// 익명게시판이 아니면.. 원래 게시판 URL로 forward
	//-------------------------------
	if (!masterVo.getBbsTyCode().equals("BBST02")) {
	    return "forward:/cop/bbs/deleteBoardArticle.mdo";
	}
	////-----------------------------

	//-------------------------------
	// 패스워드 비교
	//-------------------------------
	String dbpassword = bbsMngService.getPasswordInf(board);
	String enpassword = EgovFileScrty.encryptPassword(board.getPassword(), board.getBbsId());

	if (!dbpassword.equals(enpassword)) {

	    model.addAttribute("msg", egovMessageSource.getMessage("cop.password.not.same.msg"));

	    return "forward:/cop/bbs/anonymous/selectBoardArticle.mdo";
	}
	////-----------------------------

	if (isAuthenticated) {
	    board.setLastUpdusrId("ANONYMOUS");

	    bbsMngService.deleteBoardArticle(board);
	}

	return "redirect:/cop/bbs/anonymous/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 익명게시물 수정을 위한 수정페이지로 이동한다.
     *
     * @param boardVO
     * @param vo
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/forUpdateBoardArticle.mdo")
    public String selectAnonymousBoardArticleForUpdt(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model)
	    throws Exception {

	//log.debug(this.getClass().getName()+"selectBoardArticleForUpdt getNttId "+boardVO.getNttId());
	//log.debug(this.getClass().getName()+"selectBoardArticleForUpdt getBbsId "+boardVO.getBbsId());

	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;

	boardVO.setFrstRegisterId("ANONYMOUS");

	BoardMaster master = new BoardMaster();
	BoardMasterVO bmvo = new BoardMasterVO();
	BoardVO bdvo = new BoardVO();

	vo.setBbsId(boardVO.getBbsId());

	master.setBbsId(boardVO.getBbsId());
	master.setUniqId("ANONYMOUS");

	if (isAuthenticated) {
	    bmvo = bbsAttrbService.selectBBSMasterInf(master);

	    //-------------------------------
	    // 익명게시판이 아니면.. 원래 게시판 URL로 forward
	    //-------------------------------
	    if (!bmvo.getBbsTyCode().equals("BBST02")) {
	    	return "forward:/cop/bbs/forUpdateBoardArticle.mdo";
	    }
	    ////-----------------------------

	    //-------------------------------
	    // 패스워드 비교
	    //-------------------------------
	    String dbpassword = bbsMngService.getPasswordInf(boardVO);
	    String enpassword = EgovFileScrty.encryptPassword(boardVO.getPassword(), boardVO.getBbsId());

	    if (!dbpassword.equals(enpassword)) {

		model.addAttribute("msg", egovMessageSource.getMessage("cop.password.not.same.msg"));

		return "forward:/cop/bbs/anonymous/selectBoardArticle.mdo";
	    }
	    ////-----------------------------

	    bdvo = bbsMngService.selectBoardArticle(boardVO);
	}

	model.addAttribute("result", bdvo);
	model.addAttribute("bdMstr", bmvo);

	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
	    bmvo.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bmvo);
	////-----------------------------

	model.addAttribute("anonymous", "true");

	return "egovframework/mbl/com/cop/bbs/EgovNoticeUpdt";
    }

    /**
     * 익명게시물에 대한 내용을 수정한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/updateBoardArticle.mdo")
    public String updateAnonymousBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model,
	    SessionStatus status) throws Exception {

	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;

	String atchFileId = boardVO.getAtchFileId();

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    boardVO.setFrstRegisterId("ANONYMOUS");

	    BoardMaster master = new BoardMaster();
	    BoardMasterVO bmvo = new BoardMasterVO();
	    BoardVO bdvo = new BoardVO();

	    master.setBbsId(boardVO.getBbsId());
	    master.setUniqId("ANONYMOUS");

	    bmvo = bbsAttrbService.selectBBSMasterInf(master);

	    //-------------------------------
	    // 익명게시판이 아니면.. 원래 게시판 URL로 forward
	    //-------------------------------
	    if (!bdMstr.getBbsTyCode().equals("BBST02")) {
	    	return "forward:/cop/bbs/updateBoardArticle.mdo";
	    }
	    ////-----------------------------

	    bdvo = bbsMngService.selectBoardArticle(boardVO);

	    model.addAttribute("result", bdvo);
	    model.addAttribute("bdMstr", bmvo);

	    model.addAttribute("anonymous", "true");

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeUpdt";
	}

	if (isAuthenticated) {
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
		if ("".equals(atchFileId)) {
		    List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
		    atchFileId = fileMngService.insertFileInfs(result);
		    board.setAtchFileId(atchFileId);
		} else {
		    FileVO fvo = new FileVO();
		    fvo.setAtchFileId(atchFileId);
		    int cnt = fileMngService.getMaxFileSN(fvo);
		    List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
		    fileMngService.updateFileInfs(_result);
		}
	    }

	    board.setLastUpdusrId("ANONYMOUS");

	    // 익명게시판 관련
	    board.setNtcrNm(board.getNtcrNm());
	    board.setPassword(EgovFileScrty.encryptPassword(board.getPassword(), board.getBbsId()));

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.updateBoardArticle(board);
	}

	return "redirect:/cop/bbs/anonymous/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 익명게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/addReplyBoardArticle.mdo")
    public String addAnonymousReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	BoardMasterVO master = new BoardMasterVO();
	BoardMasterVO vo = new BoardMasterVO();

	vo.setBbsId(boardVO.getBbsId());
	vo.setUniqId("ANONYMOUS");

	master = bbsAttrbService.selectBBSMasterInf(vo);

	//-------------------------------
	// 익명게시판이 아니면.. 원래 게시판 URL로 forward
	//-------------------------------
	if (!master.getBbsTyCode().equals("BBST02")) {
		return "forward:/cop/bbs/addReplyBoardArticle.mdo";
	}
	////-----------------------------

	model.addAttribute("bdMstr", master);
	model.addAttribute("result", boardVO);

	//----------------------------
	// 기본 BBS template 지정
	//----------------------------
	if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
	    master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", master);
	////-----------------------------

	model.addAttribute("anonymous", "true");

	return "egovframework/mbl/com/cop/bbs/EgovNoticeReply";
    }

    /**
     * 익명게시물에 대한 답변을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/anonymous/replyBoardArticle.mdo")
    public String replyAnonymousBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model,
	    SessionStatus status) throws Exception {

	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	Boolean isAuthenticated = true;

	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {
	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId("ANONYMOUS");

	    master = bbsAttrbService.selectBBSMasterInf(vo);

		//-------------------------------
		// 익명게시판이 아니면.. 원래 게시판 URL로 forward
		//-------------------------------
		if (!master.getBbsTyCode().equals("BBST02")) {
			return "forward:/cop/bbs/replyBoardArticle.mdo";
		}
		////-----------------------------

	    model.addAttribute("bdMstr", master);
	    model.addAttribute("result", boardVO);

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		master.setTmplatCours("/css/egovframework/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    model.addAttribute("anonymous", "true");

	    return "egovframework/mbl/com/cop/bbs/EgovNoticeReply";
	}

	if (isAuthenticated) {
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    String atchFileId = "";

	    if (!files.isEmpty()) {
		List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
		atchFileId = fileMngService.insertFileInfs(result);
	    }

	    board.setAtchFileId(atchFileId);
	    board.setReplyAt("Y");
	    board.setFrstRegisterId("ANONYMOUS");
	    board.setBbsId(board.getBbsId());
	    board.setParnts(Long.toString(boardVO.getNttId()));
	    board.setSortOrdr(boardVO.getSortOrdr());
	    board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));

	    // 익명게시판 관련
	    board.setNtcrNm(board.getNtcrNm());
	    board.setPassword(EgovFileScrty.encryptPassword(board.getPassword(), board.getBbsId()));

	    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	}

	return "redirect:/cop/bbs/anonymous/selectBoardList.mdo?bbsId="+board.getBbsId();
    }

    /**
     * 템플릿에 대한 미리보기용 게시물 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/previewBoardList.mdo")
    public String previewBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	String template = boardVO.getSearchWrd();	// 템플릿 URL

	BoardMasterVO master = new BoardMasterVO();

	master.setBbsNm("미리보기 게시판");

	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
	boardVO.setPageSize(propertyService.getInt("pageSize"));

	PaginationInfo paginationInfo = new PaginationInfo();

	paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(10);
	paginationInfo.setPageSize(1);

	boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
	boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	BoardVO target = null;
	List<BoardVO> list = new ArrayList<BoardVO>();

	target = new BoardVO();
	target.setNttSj("게시판 기능 설명");
	target.setFrstRegisterId("ID");
	target.setFrstRegisterNm("관리자");
	target.setFrstRegisterPnttm("2009-01-01");
	target.setInqireCo(7);
	target.setParnts("0");
	target.setReplyAt("N");
	target.setReplyLc("0");
	target.setUseAt("Y");

	list.add(target);

	target = new BoardVO();
	target.setNttSj("게시판 부가 기능 설명");
	target.setFrstRegisterId("ID");
	target.setFrstRegisterNm("관리자");
	target.setFrstRegisterPnttm("2009-01-01");
	target.setInqireCo(7);
	target.setParnts("0");
	target.setReplyAt("N");
	target.setReplyLc("0");
	target.setUseAt("Y");

	list.add(target);

	boardVO.setSearchWrd("");

	int totCnt = list.size();

	paginationInfo.setTotalRecordCount(totCnt);

	master.setTmplatCours(template);

	model.addAttribute("resultList", list);
	model.addAttribute("resultCnt", Integer.toString(totCnt));
	model.addAttribute("boardVO", boardVO);
	model.addAttribute("brdMstrVO", master);
	model.addAttribute("paginationInfo", paginationInfo);

	model.addAttribute("preview", "true");

	return "egovframework/mbl/com/cop/bbs/EgovNoticeList";
    }
}
