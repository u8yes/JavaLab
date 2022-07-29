package egovframework.mbl.com.uss.umt.web;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 일반회원관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblMberManageController {

	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 일반회원가입신청등록처리후로그인화면으로 이동한다.
	 * @param mberManageVO 일반회원가입신청정보
	 * @return forward:/uat/uia/egovLoginUsr.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberSbscrb.mdo")
	public String sbscrbMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated) {
			return "index";
		}

		//가입상태 초기화
		mberManageVO.setMberSttus("A");
		//그룹정보 초기화
		//mberManageVO.setGroupId("1");
		//일반회원가입신청 등록시 일반회원등록기능을 사용하여 등록한다.
		mberManageService.insertMber(mberManageVO);
		return "forward:/uat/uia/egovLoginUsr.mdo";
	}

	/**
	 * 일반회원 약관확인
	 * @param model 화면모델
	 * @return uss/umt/EgovStplatCnfirm
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovStplatCnfirmMber.mdo")
	public String sbscrbEntrprsMber(Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated) {
			return "index";
		}
		*/

		//일반회원용 약관 아이디 설정
		String stplatId = "";
		//회원가입유형 설정-일반회원
		String sbscrbTy = "";

		// 로그인VO에서  사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		sbscrbTy = loginVO.getUserSe();

		if ("USR01".equals(sbscrbTy)) {
			stplatId = "STPLAT_0000000000001"; // 개인회원 약관
		} else {
			stplatId = "STPLAT_0000000000002"; // 기업회원 약관
		}

		//약관정보 조회
		List<?> stplatList = mberManageService.selectStplat(stplatId);
		model.addAttribute("stplatList", stplatList); //약관정보 포함
		model.addAttribute("sbscrbTy", sbscrbTy); //회원가입유형 포함

		return "egovframework/mbl/com/uss/umt/EgovStplatCnfirm";
	}

}