package egovframework.mbl.com.cop.smt.sdm.web;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.sdm.service.DeptSchdulManageVO;
import egovframework.com.cop.smt.sdm.service.EgovDeptSchdulManageService;
import egovframework.com.uss.olp.mgt.service.EgovMeetingManageService;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 부서일정을 처리하는 Controller 클래스
 * @author 이율경
 * @since 2011.08.30
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.30  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblDeptSchdulManageController {

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "egovDeptSchdulManageService")
	private EgovDeptSchdulManageService egovDeptSchdulManageService;

	/** egovMeetingManageService Member Variable */
	@Resource(name = "egovMeetingManageService")
	private EgovMeetingManageService egovMeetingManageService;

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/**
	 *  부서일정(일별) 목록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sdm/EgovDeptSchdulManageDailyList"
	 * @throws Exception
	 */
    @IncludedMblInfo(name="부서일정관리",order = 106 ,gid = 10)
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo")
	public String egovDeptSchdulManageDailyView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//공통코드 부서일정종류
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
	   	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulSe", listComCode);

    	/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
    	String strDate = (String)commandMap.get("selDate");
    	model.addAttribute("selDate", strDate);

		return "egovframework/mbl/com/cop/smt/sdm/EgovDeptSchdulManageDailyList";
	}

    /**
	 *  부서일정(일별) 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return modelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageDailyListActor.mdo")
	public ModelAndView egovDeptSchdulManageDailyList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<String, String> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model1)
    throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		//공통코드 부서일정종류
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
	   	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	modelAndView.addObject("schdulSe", listComCode);

		/* *****************************************************************
    	// 캘런더 설정 로직
		****************************************************************** */
        Calendar calNow = Calendar.getInstance();

		String strDate = commandMap.get("selDate");

		String strSearchDay = "";
		int iNowYear = calNow.get(Calendar.YEAR);
		int iNowMonth = calNow.get(Calendar.MONTH) + 1;
		int iNowDay = calNow.get(Calendar.DATE);

		if(strDate != null && !"".equals(strDate))
		{
		  iNowYear = Integer.parseInt(strDate.split("-")[0]);
		  iNowMonth = Integer.parseInt(strDate.split("-")[1]);
		  iNowDay = Integer.parseInt(strDate.split("-")[2]);
		}

		strSearchDay = Integer.toString(iNowYear);
		strSearchDay += DateTypeIntForString(iNowMonth);
		strSearchDay += DateTypeIntForString(iNowDay);

		commandMap.put("searchKeyword", searchVO.getSearchCondition());
		commandMap.put("searchCondition", "SCHDUL_SE");
		commandMap.put("searchMode", "DAILY");
		commandMap.put("searchDay", strSearchDay);

		strDate = Integer.toString(iNowYear) + "-" + DateTypeIntForString(iNowMonth) + "-" + DateTypeIntForString(iNowDay);
		modelAndView.addObject("selDate", strDate);

		List<?> resultList = egovDeptSchdulManageService.selectDeptSchdulManageRetrieve(commandMap);
		modelAndView.addObject("resultList", resultList);

		return modelAndView;
	}

	/**
	 * 부서일정(주간별) 목록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sdm/EgovDeptSchdulManageWeekList"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageWeekList.mdo")
	public String egovDeptSchdulManageWeekView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//공통코드 부서일정종류
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
	   	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulSe", listComCode);

    	/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
    	String strDate = (String)commandMap.get("selDate");
    	model.addAttribute("selDate", strDate);

		return "egovframework/mbl/com/cop/smt/sdm/EgovDeptSchdulManageWeekList";
	}

	/**
	 * 부서일정(주간별) 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return modelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "static-access", "unused", "unchecked" })
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageWeekListActor.mdo")
	public ModelAndView egovDeptSchdulManageWeekList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<String, String> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model1)
    throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
		modelAndView.addObject("searchKeyword", searchVO.getSearchCondition() == null ? "" : searchVO.getSearchCondition());
		modelAndView.addObject("searchCondition", searchVO.getSearchCondition() == null ? "" : searchVO.getSearchCondition());

		/* *****************************************************************
    	// 캘런더 설정 로직
		****************************************************************** */
        Calendar calNow = Calendar.getInstance();
        Calendar calBefore = Calendar.getInstance();
        Calendar calNext = Calendar.getInstance();

        String strDate = commandMap.get("selDate");

		int iNowYear = calNow.get(Calendar.YEAR);
		int iNowMonth = calNow.get(Calendar.MONTH);
		int iNowDate = calNow.get(Calendar.DATE);

        if(strDate != null && !"".equals(strDate)) {

  		  iNowYear = Integer.parseInt(strDate.split("-")[0]);
		  iNowMonth = Integer.parseInt(strDate.split("-")[1]);
		  iNowDate = Integer.parseInt(strDate.split("-")[2]);

        }

        calNow.set(iNowYear, iNowMonth, iNowDate);
        calBefore = (Calendar) calNow.clone();
        calNext = (Calendar) calNow.clone();

        // 한 주의 시작날과 마지막날 설정 (일 ~ 금)
        int day = calNow.get(Calendar.DAY_OF_WEEK);
        calBefore.add(calNow.DATE, -(day));
        calNext.add(calNow.DATE, 7 - day);

		commandMap.put("searchMode", "WEEK");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		commandMap.put("schdulBgnde", dateFormat.format(calBefore.getTime()));
		commandMap.put("schdulEndde", dateFormat.format(calNext.getTime()));
		commandMap.put("searchKeyword", searchVO.getSearchCondition());
		commandMap.put("searchCondition", "SCHDUL_SE");

		List<EgovMap> resultList = (List<EgovMap>) egovDeptSchdulManageService.selectDeptSchdulManageRetrieve(commandMap);

		// 한 주의 일정 그룹
		ArrayList<ArrayList<EgovMap>> weekSchdul = new ArrayList<ArrayList<EgovMap>>();
		// 당일 일정
		ArrayList<EgovMap> dailySchdul = null;
		// 한 주의 그룹
		ArrayList<String> weekGroup = new ArrayList<String>();

		/* *****************************************************************
    	// 한 주의 일정 저장
		****************************************************************** */
		calBefore.add(calBefore.DATE, 1);
		Calendar calTmp = (Calendar)calBefore.clone();
		for(int i=0; calTmp.getTime().compareTo(calNext.getTime()) <= 0; calTmp.add(calTmp.DATE, 1), i++) {

			dailySchdul = new ArrayList<EgovMap>();
			String calSchdul = dateFormat.format(calTmp.getTime());

			for (EgovMap map : resultList) {
				String schdulBeginde = (String)map.get("schdulBgnde");
				String schdulEndde = (String)map.get("schdulEndde");

				if(schdulBeginde.substring(0, 8).compareTo(calSchdul) <= 0 && schdulEndde.substring(0, 8).compareTo(calSchdul) >= 0) {
					dailySchdul.add(map);
				}
			}
			weekGroup.add(calSchdul);
			weekSchdul.add(dailySchdul);

		}

		strDate = Integer.toString(iNowYear) + "-" + DateTypeIntForString(iNowMonth) + "-" + DateTypeIntForString(iNowDate);
		modelAndView.addObject("selDate", strDate);
		modelAndView.addObject("resultList", weekSchdul);
		modelAndView.addObject("weekGroup", weekGroup);

		dateFormat.applyPattern("yyyy-MM-dd");
		modelAndView.addObject("schdulBgnde", dateFormat.format(calBefore.getTime()));
		modelAndView.addObject("schdulEndde", dateFormat.format(calNext.getTime()));

		return modelAndView;
	}

	/**
	 *  부서일정 목록을 상세조회 조회한다.
	 * @param searchVO
	 * @param deptSchdulManageVO
	 * @param commandMap
	 * @param model
	 * @return "egovframework/com/cop/smt/sdm/EgovDeptSchdulManageDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageDetail.mdo")
	public String egovDeptSchdulManageDetail(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			DeptSchdulManageVO deptSchdulManageVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

     	//공통코드  중요도 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM019");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulIpcrCode", listComCode);
    	//공통코드  일정구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulSe", listComCode);
    	//공통코드  반복구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM031");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("reptitSeCode", listComCode);

        List<?> schdulList = egovDeptSchdulManageService.selectDeptSchdulManageDetail(deptSchdulManageVO);
        // Map -> VO 변환
        BeanUtils.populate(deptSchdulManageVO, (EgovMap) schdulList.get(0));
        deptSchdulManageVO.setSchdulBgnde((String)((EgovMap) schdulList.get(0)).get("schdulBgnde"));

        model.addAttribute("result", deptSchdulManageVO);

		/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
        String path = (String)commandMap.get("path");
        model.addAttribute("path", path);

        String selDate = (String)commandMap.get("selDate");
        model.addAttribute("selDate", selDate);

        model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/cop/smt/sdm/EgovDeptSchdulManageDetail";
	}

	/**
	 *  부서일정를 등록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sdm/EgovDeptSchdulManageRegist"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageRegist.mdo")
	public String DeptSchdulManageRegist(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("deptSchdulManageVO") DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

     	//공통코드  중요도 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM019");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulIpcrCode", listComCode);
    	//공통코드  일정구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulSe", listComCode);
    	//공통코드  반복구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM031");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	listComCode.remove(3);
    	model.addAttribute("reptitSeCode", listComCode);

    	String path = (String)commandMap.get("path");
		model.addAttribute("path", path);

		String selDate = (String)commandMap.get("selDate");
		model.addAttribute("selDate", selDate);

		model.addAttribute("searchVO", searchVO);

    	return "egovframework/mbl/com/cop/smt/sdm/EgovDeptSchdulManageRegist";

	}

	/**
	 *  부서일정를 등록한다. / 등록 처리 한다.
	 * @param multiRequest
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return  "redirect:/cop/smt/sdm/EgovDeptSchdulManageList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageRegistActor.mdo")
	public String DeptSchdulManageRegistActor(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("deptSchdulManageVO") DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		// 시간 변환
		String schdulBgndeYYYMMDD = (String)commandMap.get("schdulBgndeYYYMMDD");
		String schdulEnddeYYYMMDD = (String)commandMap.get("schdulEnddeYYYMMDD");
		String schdulBgndeHH = (String)commandMap.get("schdulBgndeHH");
		String schdulEnddeHH = (String)commandMap.get("schdulEnddeHH");

		deptSchdulManageVO.setSchdulBgnde(schdulBgndeYYYMMDD.replaceAll("-", "") + schdulBgndeHH.replaceAll(":", ""));
		deptSchdulManageVO.setSchdulEndde(schdulEnddeYYYMMDD.replaceAll("-", "") + schdulEnddeHH.replaceAll(":", ""));

		// kind Code 설정
		deptSchdulManageVO.setSchdulKindCode("1");

		//아이디 설정
		deptSchdulManageVO.setFrstRegisterId(loginVO.getUniqId());
		deptSchdulManageVO.setLastUpdusrId(loginVO.getUniqId());

    	egovDeptSchdulManageService.insertDeptSchdulManage(deptSchdulManageVO);

        return "redirect:/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo";

	}

	/**
	 * 부서일정를 수정 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sdm/EgovDeptSchdulManageModify"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageModify.mdo")
	public String DeptSchdulManageModifyView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

     	//공통코드  중요도 조회
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM019");
    	List<?> listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulIpcrCode", listComCode);
    	//공통코드  일정구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM030");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	model.addAttribute("schdulSe", listComCode);
    	//공통코드  반복구분 조회
    	voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("COM031");
    	listComCode = cmmUseService.selectCmmCodeDetail(voComCode);
    	listComCode.remove(3);
    	model.addAttribute("reptitSeCode", listComCode);

		List<?> sampleList = egovDeptSchdulManageService.selectDeptSchdulManageDetail(deptSchdulManageVO);
		BeanUtils.populate(deptSchdulManageVO, (EgovMap) sampleList.get(0));
		deptSchdulManageVO.setSchdulBgnde((String)((EgovMap) sampleList.get(0)).get("schdulBgnde"));

    	String sSchdulBgnde = deptSchdulManageVO.getSchdulBgnde();
    	String sSchdulEndde = deptSchdulManageVO.getSchdulEndde();

    	deptSchdulManageVO.setSchdulBgndeYYYMMDD(sSchdulBgnde.substring(0, 4) +"-"+sSchdulBgnde.substring(4, 6)+"-"+sSchdulBgnde.substring(6, 8) );
    	deptSchdulManageVO.setSchdulBgndeHH(sSchdulBgnde.substring(8, 10) + ":" + sSchdulBgnde.substring(10, 12));

    	deptSchdulManageVO.setSchdulEnddeYYYMMDD(sSchdulEndde.substring(0, 4) +"-"+sSchdulEndde.substring(4, 6)+"-"+sSchdulEndde.substring(6, 8) );
    	deptSchdulManageVO.setSchdulEnddeHH(sSchdulEndde.substring(8, 10) + ":" + sSchdulEndde.substring(10, 12));

    	model.addAttribute("deptSchdulManageVO", deptSchdulManageVO);

		/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
		String path = (String)commandMap.get("path");
		model.addAttribute("path", path);

		String selDate = (String)commandMap.get("selDate");
		model.addAttribute("selDate", selDate);

		model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/cop/smt/sdm/EgovDeptSchdulManageModify";
	}

	/**
	 * 부서일정를 수정 처리 한다.
	 * @param searchVO
	 * @param commandMap
	 * @param deptSchdulManageVO
	 * @param model
	 * @return "redirect:/cop/smt/sdm/EgovDeptSchdulManageDetail.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageModifyActor.mdo")
	public String DeptSchdulManageModifyActor(
			ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("deptSchdulManageVO") DeptSchdulManageVO deptSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		// 시간 변환
		String schdulBgndeYYYMMDD = (String)commandMap.get("schdulBgndeYYYMMDD");
		String schdulEnddeYYYMMDD = (String)commandMap.get("schdulEnddeYYYMMDD");
		String schdulBgndeHH = (String)commandMap.get("schdulBgndeHH");
		String schdulEnddeHH = (String)commandMap.get("schdulEnddeHH");

		deptSchdulManageVO.setSchdulBgnde(schdulBgndeYYYMMDD.replaceAll("-", "") + schdulBgndeHH.replaceAll(":", ""));
		deptSchdulManageVO.setSchdulEndde(schdulEnddeYYYMMDD.replaceAll("-", "") + schdulEnddeHH.replaceAll(":", ""));

		// kind Code 설정
		deptSchdulManageVO.setSchdulKindCode("1");

		//아이디 설정
		deptSchdulManageVO.setFrstRegisterId(loginVO.getUniqId());
		deptSchdulManageVO.setLastUpdusrId(loginVO.getUniqId());

		/* *****************************************************************
		// 일정관리정보 업데이트 처리
		****************************************************************** */
		egovDeptSchdulManageService.updateDeptSchdulManage(deptSchdulManageVO);

		/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
		String path = (String)commandMap.get("path");
		model.addAttribute("path", path);

		String selDate = (String)commandMap.get("selDate");
		model.addAttribute("selDate", selDate);

		model.addAttribute("searchVO", searchVO);

		return "redirect:/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo";
	}

	/**
	 *  부서일정을 삭제 처리한다.
	 * @param searchVO
	 * @param deptSchdulManageVO
	 * @param commandMap
	 * @param model
	 * @return "redirect:/cop/smt/sdm/EgovDeptSchdulManageList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovDeptSchdulManageDelete.mdo")
	public String EgovDeptSchdulManageDelete(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			DeptSchdulManageVO deptSchdulManageVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

		egovDeptSchdulManageService.deleteDeptSchdulManage(deptSchdulManageVO);

		return "redirect:/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo";
	}

	/**
     * 부서목록 화면을 출력한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return "egovframework/com/cop/smt/sdm/EgovMeetingManageLisAuthorGroupPopup"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageLisAuthorGroupPopupView.mdo")
	public String EgovMeetingManageLisAuthorGroupPopupView (
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	return "egovframework/mbl/com/cop/smt/sdm/EgovMeetingManageLisAuthorGroupPopup";
    }

    /**
     * 부서목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return modelAndView
     * @throws Exception
     */
    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageLisAuthorGroupPopupList.mdo")
	public ModelAndView EgovMeetingManageLisAuthorGroupPopupPost (
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	List<?> resultList = egovMeetingManageService.egovMeetingManageLisAuthorGroupPopup(searchVO);
    	model.addAttribute("resultList", resultList);

    	return modelAndView;
    }

    /**
     * 회원목록 화면을 출력한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return  "mbl/com/cop/smt/sdm/EgovMeetingManageLisEmpLyrPopup"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageLisEmpLyrPopupView.mdo")
	public String EgovMeetingManageLisEmpLyrPopupView (
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	return "egovframework/mbl/com/cop/smt/sdm/EgovMeetingManageLisEmpLyrPopup";
    }

    /**
     * 회원목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param model
     * @return  "/uss/olp/mgt/EgovMeetingManageLisEmpLyrPopup"
     * @throws Exception
     */
    @RequestMapping(value="/uss/olp/mgt/EgovMeetingManageLisEmpLyrPopupList.mdo")
	public ModelAndView EgovMeetingManageLisEmpLyrPopupPost (
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

    	ModelAndView modelAndView = new ModelAndView("jsonView");

    	searchVO.setSearchKeyword(URLDecoder.decode(searchVO.getSearchKeyword(), "UTF-8"));

    	List<?> resultList = egovMeetingManageService.egovMeetingManageLisEmpLyrPopup(searchVO);
        model.addAttribute("resultList", resultList);

    	return modelAndView;
    }

	/**
	 * 0을 붙여 반환
	 * @return  String
	 * @throws
	 */
    public String DateTypeIntForString(int iInput){
		String sOutput = "";
		if(Integer.toString(iInput).length() == 1){
			sOutput = "0" + Integer.toString(iInput);
		}else{
			sOutput = Integer.toString(iInput);
		}

       return sOutput;
    }
}
