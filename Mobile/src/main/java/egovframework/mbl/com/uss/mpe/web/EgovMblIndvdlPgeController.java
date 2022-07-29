package egovframework.mbl.com.uss.mpe.web;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.mpe.service.EgovIndvdlPgeService;
import egovframework.com.uss.mpe.service.IndvdlPge;
import egovframework.com.uss.mpe.service.IndvdlPgeCntntsVO;
import egovframework.com.uss.mpe.service.IndvdlPgeVO;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 모바일 마이페이지를 처리 하는 Mobile Controller 클래스
 * @author 공통컴포넌트전환팀 이율경
 * @since 2011.08.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.16  이율경          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMblIndvdlPgeController {

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Resource(name = "EgovIndvdlPgeService")
    private EgovIndvdlPgeService egovIndvdlPgeSVC;

	/**
	 * 사용자가 마이페이지를 조회한다.
	 * @param indvdlPgeVO - 마이페이지 VO
	 * @return "egovframework/com/uss/mpe/EgovIndvdlpgeDetail"
	 */
    @IncludedMblInfo(name="마이페이지",order = 215 ,gid = 20)
	@RequestMapping(value="/uss/mpe/selectIndvdlpgeResult.mdo")
	public String selectIndvdlpgeResult(@ModelAttribute("searchVO") IndvdlPgeVO indvdlPgeVO,
			ModelMap model) throws Exception {

    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(indvdlPgeVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(1);

		indvdlPgeVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		indvdlPgeVO.setLastIndex(paginationInfo.getLastRecordIndex());
		indvdlPgeVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		indvdlPgeVO.setUserId(user.getId());

		List<?> indvdlPgeDetailList = egovIndvdlPgeSVC.selectIndvdlpgeResultDetail(indvdlPgeVO);

		// 마이페이지 상세목록설정
		model.addAttribute("indvdlPgeDetailList", indvdlPgeDetailList);

        // 마이페이지 상세목록 카운트 설정
        int totDetailCnt = egovIndvdlPgeSVC.selectIndvdlpgeResultDetailTotCnt(indvdlPgeVO);
        model.addAttribute("indvdlPgeDetailListCount", totDetailCnt);

        model.addAttribute("searchVO", indvdlPgeVO);

        return "egovframework/mbl/com/uss/mpe/EgovIndvdlpgeDetail";
	}

	/**
	 * 마이페이지에서  컨텐츠를 바로 삭제한다.
	 * @param indvdlPge - 마이페이지 model
	 * @param ModelMap model
	 * @return modelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/mpe/EgovIndvdlpgeDelCntnts.mdo")
	public ModelAndView deleteIndvdlpgeCntnts(@ModelAttribute("indvdlPgeCntntsVO") IndvdlPgeCntntsVO indvdlPgeCntntsVO,
			@ModelAttribute("defaultVO") ComDefaultVO defaultVO,
			ModelMap model) throws Exception{

		ModelAndView modelAndView =  new ModelAndView("jsonView");

		//ID를 받아서 VO에 설정한다.
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		indvdlPgeCntntsVO.setUserId(user.getId());
		//디비 작업 성공여부에 따라 메세지 설정 및 이동 페이지를 결정한다.
		if(egovIndvdlPgeSVC.delIndvdlpgeCntnts(indvdlPgeCntntsVO)){
			modelAndView.addObject("message", egovMessageSource.getMessage("success.common.delete"));
		}else{
			modelAndView.addObject("message", egovMessageSource.getMessage("fail.common.insert"));
		}

		return modelAndView;
	}

	/**
	 * 마이페이지에 컨텐츠를 추가 위한 목록조회 화면을 출력한다.
	 * @param indvdlPgeCntntsVO - 마이페이지 콘텐츠 Vo
	 * @return modelAndView
	 */
	@RequestMapping(value="/uss/mpe/EgovIndvdlpgeAddCntntsList.mdo")
	public String addIndvdlpgeCntntsListView(@ModelAttribute("searchVO") ComDefaultVO comDefaultVO,
			ModelMap model) throws Exception {

		return "egovframework/mbl/com/uss/mpe/EgovIndvdlpgeList";
	}

	/**
	 * 마이페이지에 컨텐츠를 추가 위해 등록된 마이페이지 컨텐츠 목록을 조회한다.
	 * @param indvdlPgeCntntsVO - 마이페이지 콘텐츠 Vo
	 * @return modelAndView
	 */
	@RequestMapping(value="/uss/mpe/EgovIndvdlpgeAddCntntsListActor.mdo")
	public ModelAndView addIndvdlpgeCntntsList(@ModelAttribute("searchVO") IndvdlPgeCntntsVO indvdlPgeCntntsVO,
			ModelMap model) throws Exception {

		ModelAndView modelAndView = new ModelAndView("jsonView");

		//ID를 받아서 VO에 설정한다.
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		indvdlPgeCntntsVO.setUserId(user.getId());

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(indvdlPgeCntntsVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(1);

		indvdlPgeCntntsVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		indvdlPgeCntntsVO.setLastIndex(paginationInfo.getLastRecordIndex());
		indvdlPgeCntntsVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 사용자가 마이페이지에 컨텐츠를 추가하기 위해 등록되어 있는 마이페이지 목록을 조회한다.
		indvdlPgeCntntsVO.setIndvdlPgeList(egovIndvdlPgeSVC.addIndvdlpgeCntntsList(indvdlPgeCntntsVO));
		modelAndView.addObject("indvdlCntntsList", indvdlPgeCntntsVO.getIndvdlPgeList());

        // 목록의 페이징을 위해 등록되어 있는 마이페이지 개수를 조회한다.
        int totCnt = egovIndvdlPgeSVC.selectIndvdlpgeAddCntntsTotCnt(indvdlPgeCntntsVO);
		paginationInfo.setTotalRecordCount(totCnt);

		modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
	}

	/**
	 * 마이페이지 컨텐츠의 미리보기를 위한 jsp URL을 리턴한다.
	 * @param indvdlPgeCntntsVO - 마이페이지 VO
	 * @param ModelMap model
	 * @return "egovframework/com/uss/mpe/EgovIndvdlpgeInfoDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/uss/mpe/indvdlCntntsPreview.mdo")
	public String indvdlCntntsPreview(@ModelAttribute("indvdlPge") IndvdlPge indvdlPge,
			ModelMap model) throws Exception{

		IndvdlPge indvdlPgeInfo = egovIndvdlPgeSVC.selectIndvdlpgeCntnts(indvdlPge);
		model.addAttribute("indvdlPgeInfo", indvdlPgeInfo);


		return "egovframework/mbl/com/uss/mpe/EgovIndvdlpgeInfoDetail";
	}

	/**
	 * 마이페이지에  컨텐츠를 바로 추가한다.
	 * @param indvdlPge - 마이페이지 model
	 * @param ModelMap model
	 * @return modelAndView
	 * @throws Exception
	 * @param indvdlPge
	 */
	@RequestMapping(value="/uss/mpe/EgovIndvdlpgeAddCntnts.mdo")
	public ModelAndView addIndvdlpgeCntnts(@ModelAttribute("indvdlPgeCntntsVO") IndvdlPgeCntntsVO indvdlPgeCntntsVO,
			@ModelAttribute("defaultVO") ComDefaultVO defaultVO,
			ModelMap model) throws Exception{

		ModelAndView modelAndView = new ModelAndView("jsonView");

		//ID를 받아서 VO에 설정한다.
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		indvdlPgeCntntsVO.setUserId(user.getId());



		//디비 작업 성공여부에 따라 메세지 설정 및 이동 페이지를 결정한다.
		if(egovIndvdlPgeSVC.addIndvdlpgeCntnts(indvdlPgeCntntsVO)){

			modelAndView.addObject("message", egovMessageSource.getMessage("success.common.insert"));

		}else{

			modelAndView.addObject("message", egovMessageSource.getMessage("fail.common.insert"));

		}

		return modelAndView;
	}

}
