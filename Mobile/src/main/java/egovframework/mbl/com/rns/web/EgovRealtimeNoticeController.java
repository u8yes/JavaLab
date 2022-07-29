package egovframework.mbl.com.rns.web;

import java.net.URLEncoder;
import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.rns.service.EgovRealtimeNoticeService;
import egovframework.mbl.com.rns.service.RealtimeNotice;
import egovframework.mbl.com.rns.service.RealtimeNoticeVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 개요
 * -실시간 공지 서비스에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 실시간 공지 등록, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 조준형
 * @since 2011.08.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.10  조준형          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovRealtimeNoticeController {

	@Resource(name = "RealtimeNoticeService")
	private EgovRealtimeNoticeService realtimeNoticeService;


	/**
	 * 실시간 공지 서비스 메시지 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@IncludedMblInfo(name="실시간 공지 서비스",order = 401 ,gid = 40)
	@RequestMapping(value="/mbl/com/rns/goRealtimeNoticeMsg.mdo")
	public ModelAndView goRealtimeNoticeMsg(
			@ModelAttribute RealtimeNoticeVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNoticeMsg");
		return modelAndView;
	}

	/**
	 * 실시간 공지 서비스 공지내용 조회 : Server sent Event용 메서드
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/goEvent.mdo")
	public ModelAndView goEvent(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		// 1. 실시간 공지 전송 메시지를 조회
		RealtimeNotice oRealtimeNotice = realtimeNoticeService.selectRealtimeNoticeMsg();
		if(oRealtimeNotice != null) {
			// 2. 공지 메시지 조합
			// 'data:' 는 server sent event 구현을 위해 필요한 키워드로 변경 하면 실행 불가함..
			String noticeMsg = oRealtimeNotice.getSn() + "|" + oRealtimeNotice.getNoticeSj() + "|" + oRealtimeNotice.getNoticeCn();

			model.addAttribute("data", URLEncoder.encode(noticeMsg, "utf-8"));
			model.addAttribute("retry", "5000");

		}

		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNoticeData");

		return modelAndView;
	}

	/**
	 * 실시간 공지서비스 메시지 조회
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/selectRealtimeNoticeMsg.mdo")
	public ModelAndView selectRealtimeNoticeMsg(@ModelAttribute RealtimeNoticeVO searchVO,
			Model model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");
		RealtimeNotice oRealtimeNotice = realtimeNoticeService.selectRealtimeNoticeMsg();
		modelAndView.addObject("rnsMsg", oRealtimeNotice);
		modelAndView.addObject("success", true);
		//modelAndView.setViewName("/mbl/com/rns/EgovMobileRealtimeNoticeMsg");

		return modelAndView;
	}

	/**
	 * [관리자] 실시간 공지 서비스 목록 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@IncludedMblInfo(name="실시간 공지 서비스",order = 500 ,gid = 50)
	@RequestMapping(value="/mbl/com/rns/goRealtimeNoticeList.mdo")
	public ModelAndView goRealtimeNoticeList(
			@ModelAttribute("searchVO") RealtimeNoticeVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNoticeList");
		return modelAndView;
	}

	/**
	 * [관리자] 실시간 공지 등록 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mbl/com/rns/goRealtimeNoticeInsert.mdo")
	public ModelAndView goRealtimeNoticeInsert(
			@ModelAttribute("searchVO") RealtimeNoticeVO searchVO,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNoticeInsert");

		return modelAndView;
	}

	/**
	 * [관리자] 실시간 공지 상세 페이지로 이동
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/mbl/com/rns/goRealtimeNotice.mdo")
	public ModelAndView goRealtimeNotice(
			@RequestParam("searchSn") String searchSn,
			Model model) {

		ModelAndView modelAndView = new ModelAndView();

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            modelAndView.setViewName("egovframework/mbl/com/uat/uia/EgovLoginUsr");
            return modelAndView;
        }

		modelAndView.addObject("param", searchSn);
		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNotice");
		return modelAndView;
	}

	/**
	 * [관리자] 실시간공지서비스 공지 목록 조회
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/selectRealtimeNoticeList.mdo")
	public ModelAndView selectRealtimeNoticeList(
			@ModelAttribute("searchVO") RealtimeNoticeVO searchVO,
			Model model) throws Exception {

		List<?> rtnList = realtimeNoticeService.selectRealtimeNoticeList(searchVO);
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("rtnList", rtnList);
		modelAndView.addObject("success", true);

		return modelAndView;
	}

	/**
	 * [관리자] 실시간공지서비스 공지 상세 조회
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/selectRealtimeNotice.mdo")
	public ModelAndView selectRealtimeNotice(@ModelAttribute("searchVO") RealtimeNoticeVO searchVO,
			Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 1. 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	searchVO.setMberId(mberId);

		// 2. 실시간 공지 서비스 모바일 목록을 조회 한다.
		RealtimeNotice oRealtimeNotice = realtimeNoticeService.selectRealtimeNotice(searchVO);

		// 3. 조회 결과를 ModelAndView에 할당한다.
		modelAndView.addObject("resultView", oRealtimeNotice);
		modelAndView.addObject("searchVO", searchVO);

		return modelAndView;
	}

	/**
	 * [관리자] 실시간공지서비스 공지 삭제
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/deleteRealtimeNotice.mdo")
	public ModelAndView deleteRealtimeNotice(
			@ModelAttribute RealtimeNoticeVO searchVO,
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
    	searchVO.setMberId(mberId);

		realtimeNoticeService.deleteRealtimeNotice(searchVO);
		modelAndView.setViewName("egovframework/mbl/com/rns/EgovMobileRealtimeNoticeList");

        return modelAndView;
	}

	/**
	 * [관리자] 실시간공지서비스 공지 등록
	 *
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mbl/com/rns/insertRealtimeNotice.mdo")
	public ModelAndView insertRealtimeNotice(
			@ModelAttribute("realtimeNotice")RealtimeNotice realtimeNotice,
			Model model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		// 로그인VO에서  사용자 정보 가져오기
    	LoginVO	loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	String	mberId = loginVO.getId();
    	realtimeNotice.setMberId(mberId);

		int rtn = realtimeNoticeService.insertRealtimeNotice(realtimeNotice);
		if(rtn > 0) {
			modelAndView.addObject("success", true);
		} else {
			modelAndView.addObject("success", false);

		}

        return modelAndView;
	}

}
