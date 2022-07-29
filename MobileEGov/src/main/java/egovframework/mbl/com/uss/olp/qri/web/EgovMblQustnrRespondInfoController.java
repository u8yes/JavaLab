package egovframework.mbl.com.uss.olp.qri.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.olp.qri.service.EgovQustnrRespondInfoService;
import egovframework.com.uss.olp.qri.service.QustnrRespondInfoVO;
import egovframework.com.uss.olp.qrm.service.EgovQustnrRespondManageService;
import egovframework.com.uss.olp.qrm.service.QustnrRespondManageVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 설문조사 Mobile Controller Class 구현
 * @author 공통 컴포넌트 전환팀 이율경
 * @since 2011.08.31
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.31  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblQustnrRespondInfoController {

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name = "egovQustnrRespondInfoService")
	private EgovQustnrRespondInfoService egovQustnrRespondInfoService;

	@Resource(name = "egovQustnrRespondManageService")
	private EgovQustnrRespondManageService egovQustnrRespondManageService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/**
	 * 설문템플릿을 적용한다.
	 * @param searchVO
	 * @param request
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/mgt/template/template"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olp/qri/template/template.mdo")
	public String EgovQustnrRespondInfoManageTemplate(@ModelAttribute("searchVO") ComDefaultVO searchVO, HttpServletRequest request, @RequestParam Map<?, ?> commandMap,
			ModelMap model) throws Exception {

		String sTemplateUrl = ((String) commandMap.get("templateUrl")).split("/com/")[0] + "/mbl/com/" + ((String) commandMap.get("templateUrl")).split("/com/")[1];

		return sTemplateUrl;
	}

	/**
	 * 설문조사(참여) 목록 화면을 출력한다.
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qnn/EgovQustnrRespondInfoManageList"
	 * @throws Exception
	 */
	@IncludedMblInfo(name = "설문참여", order = 202, gid = 20)
	@RequestMapping(value = "/uss/olp/qnn/EgovQustnrRespondInfoManageListView.mdo")
	public String EgovQustnrRespondInfoManageListView(@ModelAttribute("searchVO") ComDefaultVO searchVO, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

		return "egovframework/mbl/com/uss/olp/qnn/EgovQustnrRespondInfoManageList";
	}

	/**
	 * 설문조사(참여) 목록을 조회한다.
	 * @param searchVO
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qnn/EgovQustnrRespondInfoManageList"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olp/qnn/EgovQustnrRespondInfoManageList.mdo")
	public ModelAndView EgovQustnrRespondInfoManageList(@ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(1);

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = egovQustnrRespondInfoService.selectQustnrRespondInfoManageList(searchVO);
		modelAndView.addObject("resultList", sampleList);

		int totCnt = egovQustnrRespondInfoService.selectQustnrRespondInfoManageListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		modelAndView.addObject("paginationInfo", paginationInfo);

		return modelAndView;
	}

	/**
	 * 설문조사에 참여한다. (등록)
	 * @param searchVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qnn/EgovQustnrRespondInfoManageRegist"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olp/qnn/EgovQustnrRespondInfoManageRegist.mdo")
	public String EgovQustnrRespondInfoManageRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map<String, String> commandMap, ModelMap model)
			throws Exception {

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (loginVO == null) {
			loginVO = new LoginVO();
		}

		//성별코드조회
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
		voComCode.setCodeId("COM014");
		List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
		model.addAttribute("sexdstnCode", listComCode);

		//직업코드조회
		voComCode.setCodeId("COM034");
		listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
		model.addAttribute("occpTyCode", listComCode);

		if (loginVO.getUniqId() != null) {
			commandMap.put("uniqId", loginVO.getUniqId());

			//사용자정보
			model.addAttribute("emplyrinfo", egovQustnrRespondInfoService.selectQustnrRespondInfoManageEmplyrinfo(commandMap));

		}

		//설문템플릿정보
		model.addAttribute("qustnrTmplatManage", egovQustnrRespondInfoService.selectQustnrTmplatManage(commandMap).get(0));

		//설문정보
		model.addAttribute("comtnqestnrinfo", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(commandMap).get(0));
		//문항정보
		model.addAttribute("comtnqustnrqesitm", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(commandMap));
		//항목정보
		model.addAttribute("comtnqustnriem", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(commandMap));
		//설문템플릿ID 설정
		model.addAttribute("qestnrTmplatId", commandMap.get("qestnrTmplatId") == null ? "" : (String) commandMap.get("qestnrTmplatId"));

		//설문지정보ID 설정
		model.addAttribute("qestnrId", commandMap.get("qestnrId") == null ? "" : (String) commandMap.get("qestnrId"));

		return "egovframework/mbl/com/uss/olp/qnn/EgovQustnrRespondInfoManageRegist";
	}

	/**
	 * 설문조사에 참여한다. (등록)
	 * @param searchVO
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qnn/EgovQustnrRespondInfoManageRegist"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olp/qnn/EgovQustnrRespondInfoManageRegistActor.mdo")
	public String EgovQustnrRespondInfoManageRegistActor(@ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		//설문조사 처리 START
		String sKey = "";
		String sVal = "";
		for (Object key : commandMap.keySet()) {

			sKey = key.toString();

			//설문문항정보 추출
			if (sKey.length() > 6 && sKey.substring(0, 6).equals("QQESTN")) {

				//설문조사 등록
				//객관식 답안 처리
				if (((String) commandMap.get("TY_" + key)).equals("1")) {

					if (commandMap.get(key) instanceof String) {
						sVal = (String) commandMap.get(key);

						QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();

						qustnrRespondInfoVO.setQestnrTmplatId((String) commandMap.get("qestnrTmplatId"));
						qustnrRespondInfoVO.setQestnrId((String) commandMap.get("qestnrId"));
						qustnrRespondInfoVO.setQestnrQesitmId(sKey);
						qustnrRespondInfoVO.setQustnrIemId(sVal);

						qustnrRespondInfoVO.setRespondAnswerCn("");

						qustnrRespondInfoVO.setRespondNm(loginVO.getName());
						qustnrRespondInfoVO.setEtcAnswerCn((String) commandMap.get("ETC_" + sVal));

						qustnrRespondInfoVO.setFrstRegisterId(loginVO.getUniqId());
						qustnrRespondInfoVO.setLastUpdusrId(loginVO.getUniqId());

						egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
					} else {
						String[] arrVal = (String[]) commandMap.get(key);
						for (int g = 0; g < arrVal.length; g++) {
							//("QQESTN arr :" + arrVal[g]);
							QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();

							qustnrRespondInfoVO.setQestnrTmplatId((String) commandMap.get("qestnrTmplatId"));
							qustnrRespondInfoVO.setQestnrId((String) commandMap.get("qestnrId"));
							qustnrRespondInfoVO.setQestnrQesitmId(sKey);
							qustnrRespondInfoVO.setQustnrIemId(arrVal[g]);

							qustnrRespondInfoVO.setRespondAnswerCn("");

							qustnrRespondInfoVO.setRespondNm(loginVO.getName());
							qustnrRespondInfoVO.setEtcAnswerCn((String) commandMap.get("ETC_" + arrVal[g]));

							qustnrRespondInfoVO.setFrstRegisterId(loginVO.getUniqId());
							qustnrRespondInfoVO.setLastUpdusrId(loginVO.getUniqId());

							egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
						}
					}

					//주관식 답안 처리
				} else if (((String) commandMap.get("TY_" + key)).equals("2")) {

					sVal = (String) commandMap.get(key);

					QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();

					qustnrRespondInfoVO.setQestnrTmplatId((String) commandMap.get("qestnrTmplatId"));
					qustnrRespondInfoVO.setQestnrId((String) commandMap.get("qestnrId"));
					qustnrRespondInfoVO.setQestnrQesitmId(sKey);
					qustnrRespondInfoVO.setQustnrIemId(null);

					qustnrRespondInfoVO.setRespondAnswerCn(sVal);

					qustnrRespondInfoVO.setRespondNm(loginVO.getName());
					qustnrRespondInfoVO.setEtcAnswerCn(null);

					qustnrRespondInfoVO.setFrstRegisterId(loginVO.getUniqId());
					qustnrRespondInfoVO.setLastUpdusrId(loginVO.getUniqId());

					egovQustnrRespondInfoService.insertQustnrRespondInfo(qustnrRespondInfoVO);
				}

			}
		}

		//설문응답자 처리
		QustnrRespondManageVO qustnrRespondManageVO = new QustnrRespondManageVO();

		qustnrRespondManageVO.setQestnrId((String) commandMap.get("qestnrId"));
		qustnrRespondManageVO.setQestnrTmplatId((String) commandMap.get("qestnrTmplatId"));

		qustnrRespondManageVO.setSexdstnCode((String) commandMap.get("sexdstnCode"));
		qustnrRespondManageVO.setOccpTyCode((String) commandMap.get("occpTyCode"));
		qustnrRespondManageVO.setBrth(((String) commandMap.get("brth")).replaceAll("-", ""));
		qustnrRespondManageVO.setRespondNm((String) commandMap.get("respondNm"));

		qustnrRespondManageVO.setFrstRegisterId(loginVO.getUniqId());
		qustnrRespondManageVO.setLastUpdusrId(loginVO.getUniqId());
		egovQustnrRespondManageService.insertQustnrRespondManage(qustnrRespondManageVO);

		return "redirect:/uss/olp/qnn/EgovQustnrRespondInfoManageListView.mdo";

	}

	/**
	 * 설문조사 전체 통계를 조회한다.
	 * @param searchVO
	 * @param request
	 * @param commandMap
	 * @param model
	 * @return "/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics.mdo")
	public String EgovQustnrRespondInfoManageStatistics(@ModelAttribute("searchVO") ComDefaultVO searchVO, HttpServletRequest request, @RequestParam Map<String, String> commandMap, ModelMap model)
			throws Exception {

		//설문템플릿정보
		model.addAttribute("qustnrTmplatManage", egovQustnrRespondInfoService.selectQustnrTmplatManage(commandMap).get(0));

		//설문정보
		model.addAttribute("comtnqestnrinfo", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqestnrinfo(commandMap).get(0));
		//문항정보
		model.addAttribute("comtnqustnrqesitm", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnrqesitm(commandMap));
		//항목정보
		model.addAttribute("comtnqustnriem", egovQustnrRespondInfoService.selectQustnrRespondInfoManageComtnqustnriem(commandMap));
		//설문템플릿ID 설정
		model.addAttribute("qestnrTmplatId", commandMap.get("qestnrTmplatId") == null ? "" : (String) commandMap.get("qestnrTmplatId"));
		//설문지정보ID 설정
		model.addAttribute("qestnrId", commandMap.get("qestnrId") == null ? "" : (String) commandMap.get("qestnrId"));

		//객관식통계 답안
		model.addAttribute("qestnrStatistic1", egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics1(commandMap));

		//주관식통계 답안
		model.addAttribute("qestnrStatistic2", egovQustnrRespondInfoService.selectQustnrRespondInfoManageStatistics2(commandMap));

		return "egovframework/mbl/com/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics";
	}

}
