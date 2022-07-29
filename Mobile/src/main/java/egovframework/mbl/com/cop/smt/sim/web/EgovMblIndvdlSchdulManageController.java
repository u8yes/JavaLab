package egovframework.mbl.com.cop.smt.sim.web;

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
import egovframework.com.cop.smt.sim.service.EgovIndvdlSchdulManageService;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * 일정관리를 처리하는 Mobile Controller Class 구현
 * @author 공통컴포넌트 전환팀 이율경
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
public class EgovMblIndvdlSchdulManageController {

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "egovIndvdlSchdulManageService")
	private EgovIndvdlSchdulManageService egovIndvdlSchdulManageService;

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/**
	 * 일정(일별) 목록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageDailyList"
	 * @throws Exception
	 */
    @IncludedMblInfo(name="일정관리",order = 107 ,gid = 10)
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo")
	public String egovIndvdlSchdulManageDailyView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
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

		return  "egovframework/mbl/com/cop/smt/sim/EgovIndvdlSchdulManageDailyList";
	}

	/**
	 * 일정(일별) 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return modelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageDailyListActor.mdo")
	public ModelAndView egovIndvdlSchdulManageDailyList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<String, String> commandMap,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
    		ModelMap model)
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

		List<?> indvdlSchdulManageList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(commandMap);
		model.addAttribute("resultList", indvdlSchdulManageList);

		return modelAndView;
	}

	/**
	 * 일정(주간별) 목록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageWeekList"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageWeekList.mdo")
	public String egovIndvdlSchdulManageWeekView(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
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

		return "egovframework/mbl/com/cop/smt/sim/EgovIndvdlSchdulManageWeekList";
	}

	/**
	 * 일정(주간별) 목록을 조회한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return modelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "static-access", "unchecked", "unused" })
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageWeekListActor.mdo")
	public ModelAndView egovIndvdlSchdulManageWeekList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<String, String> commandMap,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
    		ModelMap model)
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

		List<EgovMap> indvdlSchdulManageList = (List<EgovMap>) egovIndvdlSchdulManageService.selectIndvdlSchdulManageRetrieve(commandMap);

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

			for (EgovMap map : indvdlSchdulManageList) {
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
	 * 일정 목록을 상세조회 조회한다.
	 * @param searchVO
	 * @param indvdlSchdulManageVO
	 * @param commandMap
	 * @param model
	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageDetail.mdo")
	public String egovIndvdlSchdulManageDetail(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
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

        List<?> indvdlSchdulManageList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetail(indvdlSchdulManageVO);
        // Map -> VO 변환
        BeanUtils.populate(indvdlSchdulManageVO, (EgovMap) indvdlSchdulManageList.get(0));
        indvdlSchdulManageVO.setSchdulBgnde((String)((EgovMap) indvdlSchdulManageList.get(0)).get("schdulBgnde"));

        model.addAttribute("result", indvdlSchdulManageVO);

		/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
        String path = (String)commandMap.get("path");
        model.addAttribute("path", path);

        String selDate = (String)commandMap.get("selDate");
        model.addAttribute("selDate", selDate);

        model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/cop/smt/sim/EgovIndvdlSchdulManageDetail";
	}

	/**
	 * 일정 등록 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageRegist"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageRegist.mdo")
	public String IndvdlSchdulManageRegist(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,
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

    	return "egovframework/mbl/com/cop/smt/sim/EgovIndvdlSchdulManageRegist";

	}

	/**
	 * 일정를 등록 처리 한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageRegistActor.mdo")
	public String IndvdlSchdulManageRegistActor(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		// 시간 변환
		String schdulBgndeYYYMMDD = (String)commandMap.get("schdulBgndeYYYMMDD");
		String schdulEnddeYYYMMDD = (String)commandMap.get("schdulEnddeYYYMMDD");
		String schdulBgndeHH = (String)commandMap.get("schdulBgndeHH");
		String schdulEnddeHH = (String)commandMap.get("schdulEnddeHH");

		indvdlSchdulManageVO.setSchdulBgnde(schdulBgndeYYYMMDD.replaceAll("-", "") + schdulBgndeHH.replaceAll(":", ""));
		indvdlSchdulManageVO.setSchdulEndde(schdulEnddeYYYMMDD.replaceAll("-", "") + schdulEnddeHH.replaceAll(":", ""));

		// kind Code 설정
		indvdlSchdulManageVO.setSchdulKindCode("2");

		//아이디 설정
		indvdlSchdulManageVO.setFrstRegisterId(loginVO.getUniqId());
		indvdlSchdulManageVO.setLastUpdusrId(loginVO.getUniqId());

    	egovIndvdlSchdulManageService.insertIndvdlSchdulManage(indvdlSchdulManageVO);

        return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo";

	}

	/**
	 * 일정 수정 화면을 출력한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "egovframework/com/cop/smt/sim/EgovIndvdlSchdulManageModify"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageModify.mdo")
	public String IndvdlSchdulManageModify(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
			BindingResult bindingResult,
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

    	List<?> resultList = egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetail(indvdlSchdulManageVO);
    	BeanUtils.populate(indvdlSchdulManageVO, (EgovMap) resultList.get(0));
    	indvdlSchdulManageVO.setSchdulBgnde((String)((EgovMap) resultList.get(0)).get("schdulBgnde"));

    	String sSchdulBgnde = indvdlSchdulManageVO.getSchdulBgnde();
    	String sSchdulEndde = indvdlSchdulManageVO.getSchdulEndde();

    	indvdlSchdulManageVO.setSchdulBgndeYYYMMDD(sSchdulBgnde.substring(0, 4) +"-"+sSchdulBgnde.substring(4, 6)+"-"+sSchdulBgnde.substring(6, 8) );
    	indvdlSchdulManageVO.setSchdulBgndeHH(sSchdulBgnde.substring(8, 10) + ":" + sSchdulBgnde.substring(10, 12));

    	indvdlSchdulManageVO.setSchdulEnddeYYYMMDD(sSchdulEndde.substring(0, 4) +"-"+sSchdulEndde.substring(4, 6)+"-"+sSchdulEndde.substring(6, 8) );
    	indvdlSchdulManageVO.setSchdulEnddeHH(sSchdulEndde.substring(8, 10) + ":" + sSchdulEndde.substring(10, 12));

    	model.addAttribute("indvdlSchdulManageVO", indvdlSchdulManageVO);

    	/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
		String path = (String)commandMap.get("path");
		model.addAttribute("path", path);

		String selDate = (String)commandMap.get("selDate");
		model.addAttribute("selDate", selDate);

		model.addAttribute("searchVO", searchVO);

		return "egovframework/mbl/com/cop/smt/sim/EgovIndvdlSchdulManageModify";
	}

	/**
	 * 일정을 수정 처리 한다.
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlSchdulManageVO
	 * @param model
	 * @return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDetail.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sim/EgovIndvdlSchdulManageModifyActor.mdo")
	public String IndvdlSchdulManageModifyActor(
			ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap,
			@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,
    		ModelMap model)
    throws Exception {

		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		// 시간 변환
		String schdulBgndeYYYMMDD = (String)commandMap.get("schdulBgndeYYYMMDD");
		String schdulEnddeYYYMMDD = (String)commandMap.get("schdulEnddeYYYMMDD");
		String schdulBgndeHH = (String)commandMap.get("schdulBgndeHH");
		String schdulEnddeHH = (String)commandMap.get("schdulEnddeHH");

		indvdlSchdulManageVO.setSchdulBgnde(schdulBgndeYYYMMDD.replaceAll("-", "") + schdulBgndeHH.replaceAll(":", ""));
		indvdlSchdulManageVO.setSchdulEndde(schdulEnddeYYYMMDD.replaceAll("-", "") + schdulEnddeHH.replaceAll(":", ""));

		// kind Code 설정
		indvdlSchdulManageVO.setSchdulKindCode("2");

		//아이디 설정
		indvdlSchdulManageVO.setFrstRegisterId(loginVO.getUniqId());
		indvdlSchdulManageVO.setLastUpdusrId(loginVO.getUniqId());

		/* *****************************************************************
    	// 일정관리정보 업데이트 처리
		****************************************************************** */
    	egovIndvdlSchdulManageService.updateIndvdlSchdulManage(indvdlSchdulManageVO);

    	/* *****************************************************************
    	// 검색조건 유지
		****************************************************************** */
		String path = (String)commandMap.get("path");
		model.addAttribute("path", path);

		String selDate = (String)commandMap.get("selDate");
		model.addAttribute("selDate", selDate);

		model.addAttribute("searchVO", searchVO);

		return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo";
	}

	/**
	 *  일정을 삭제 처리한다.
	 * @param searchVO
	 * @param indvdlSchdulManageVO
	 * @param commandMap
	 * @param model
	 * @return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo"
	 * @throws Exception
	 */
	@RequestMapping(value="/cop/smt/sdm/EgovIndvdlSchdulManageDelete.mdo")
	public String EgovIndvdlSchdulManageDelete(
			@ModelAttribute("searchVO") ComDefaultVO searchVO,
			IndvdlSchdulManageVO indvdlSchdulManageVO,
			@RequestParam Map<?, ?> commandMap,
    		ModelMap model)
    throws Exception {

		egovIndvdlSchdulManageService.deleteIndvdlSchdulManage(indvdlSchdulManageVO);

		return "redirect:/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo";
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


