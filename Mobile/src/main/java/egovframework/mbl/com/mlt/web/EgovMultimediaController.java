package egovframework.mbl.com.mlt.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mlt.service.EgovMultimediaService;
import egovframework.mbl.com.mlt.service.Multimedia;
import egovframework.mbl.com.mlt.service.MultimediaVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * - 멀티미디어 제어에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 멀티미디어에 대한 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 정홍규
 * @since 2011.08.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.23  정홍규          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovMultimediaController {

    /**
     * EgovMultimediaService
     */
    @Resource(name = "MultimediaService")
    private EgovMultimediaService multimediaService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    // 첨부파일 관련
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Autowired
    private DefaultBeanValidator beanValidator;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService egovCmmUseService;

    /**
     * 모바일 멀티미디어 목록 화면으로 이동한다.
     * @param model
     * @return "/mbl/com/mlt/EgovMobileMultimediaList"
     * @throws Exception
     */
    @IncludedMblInfo(name="멀티미디어 제어",order = 408 ,gid = 40)
    @RequestMapping(value = "/mbl/com/mlt/goMobileMultimediaList.mdo")
    public String goMobileMultimediaList(ModelMap model) throws Exception {
        return "egovframework/mbl/com/mlt/EgovMobileMultimediaList";
    }

    /**
     * 모바일 멀티미디어 목록 조회 Service interface 호출 및 결과를
     * 반환한다.(JSON 통신)
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/selectMobileMultimediaList.mdo")
    public ModelAndView selectMobileMultimediaList(ModelMap model)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");

        MultimediaVO searchVO = new MultimediaVO();

        searchVO.setFirstIndex(0);
        searchVO.setRecordCountPerPage(1000);

        List<?> multimediaList = multimediaService.selectMultimediaList(searchVO);
        List<?> multimediaFileInfoList =
            multimediaService.getMultimediaFileInfoFromXML();

        modelAndView.addObject("resultList", multimediaList);
        modelAndView.addObject("fileInfoList", multimediaFileInfoList);

        return modelAndView;
    }

    /**
     * 멀티미디어 파일 목록 조회 Service interface 호출 및 결과를
     * 반환한다.(JSON 통신)
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/getMultimediaFileList.mdo")
    public ModelAndView getMultimediaFileList(
            @RequestParam("atchFileId") String atchFileId, ModelMap model)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");

        FileVO fvo = new FileVO();
        fvo.setAtchFileId(atchFileId);
        List<?> multimediaFileList = fileMngService.selectFileInfs(fvo);

        modelAndView.addObject("fileList", multimediaFileList);
        modelAndView.addObject("relativePath", "multimedia/");

        return modelAndView;
    }

    /**
     * 멀티미디어 정보 등록 화면으로 이동한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mlt/EgovMultimediaRegist"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/goMultimediaRegist.mdo")
    public String goMultimediaRegist(
            @ModelAttribute("searchVO") MultimediaVO searchVO, ModelMap model)
            throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        model.addAttribute("multimedia", new Multimedia());
        model.addAttribute("mltmdCmmCodeDetailList",
            getCmmCodeDetailList("COM077"));

        return "egovframework/mbl/com/mlt/EgovMultimediaRegist";
    }

    /**
     * 멀티미디어 정보 등록 Service interface 호출 및 결과를 반환한다.
     * @param multiRequest
     * @param searchVO
     * @param multimedia
     * @param bindingResult
     * @param model
     * @return
     *         "forward:/mbl/com/mlt/selectMultimediaList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/insertMultimedia.mdo")
    public String insertMultimedia(
            final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") MultimediaVO searchVO,
            @ModelAttribute("multimedia") Multimedia multimedia,
            BindingResult bindingResult, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        beanValidator.validate(multimedia, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("mltmdCmmCodeDetailList",
                getCmmCodeDetailList("COM077"));
            return "egovframework/mbl/com/mlt/EgovMultimediaRegist";
        }

        // 첨부파일 관련 첨부파일ID 생성
        List<FileVO> _result = null;
        String _atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        if (!files.isEmpty()) {
            _result = fileUtil.parseFileInf(files, "MLT_", 0, "", "");
            _atchFileId = fileMngService.insertFileInfs(_result); // 파일이
                                                                  // 생성되고나면
                                                                  // 생성된
                                                                  // 첨부파일
                                                                  // ID를
                                                                  // 리턴한다.

            // 파일을 상대 경로에 저장한다.
            multimediaService.copyFileToRelativePath(_result);

            // 파일 구분을 조회한다.
            List<?> cmmCode = getCmmCodeDetailList("COM077");
            String mltmdNm = "";
            for (int i = 0; i < cmmCode.size(); i++) {
                if (((CmmnDetailCode) cmmCode.get(i)).getCode()
                    .equalsIgnoreCase(multimedia.getMltmdCode())) {
                    mltmdNm = ((CmmnDetailCode) cmmCode.get(i)).getCodeNm();
                    break;
                }
            }
            multimedia.setMltmdNm(mltmdNm);

            // 파일 확장자를 통해 지원브라우저를 조회한다.
            List<String> extList = new ArrayList<String>();

            for (int i = 0; i < _result.size(); i++) {
                extList.add(_result.get(i).getFileExtsn());
            }
            multimedia.setBrowserNm(multimediaService.getBrowserInfoFromXML(
                mltmdNm, extList));
        }

        // 리턴받은 첨부파일ID를 셋팅한다..
        multimedia.setAtchFileId(_atchFileId); // 첨부파일
                                               // ID

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        multimedia.setMberId(mberId);

        multimediaService.insertMultimedia(multimedia);
        return "forward:/mbl/com/mlt/selectMultimediaList.mdo";
    }

    /**
     * 멀티미디어 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mlt/EgovMultimediaList"
     * @throws Exception
     */
    @IncludedMblInfo(name="멀티미디어 제어",order = 506 ,gid = 50)
    @RequestMapping(value = "/mbl/com/mlt/selectMultimediaList.mdo")
    public String selectMultimediaList(
            @ModelAttribute("searchVO") MultimediaVO searchVO, ModelMap model)
            throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** paging */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> MultimediaList = multimediaService.selectMultimediaList(searchVO);
        model.addAttribute("resultList", MultimediaList);

        int totCnt = multimediaService.selectMultimediaListTotCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mlt/EgovMultimediaList";
    }

    /**
     * 멀티미디어 상세정보 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param multimedia
     * @param model
     * @return "/mbl/com/mlt/EgovMultimediaDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/selectMultimedia.mdo")
    public String selectMultimedia(
            @ModelAttribute("searchVO") MultimediaVO searchVO,
            Multimedia multimedia, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        Multimedia vo = multimediaService.selectMultimedia(multimedia);
        model.addAttribute("result", vo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mlt/EgovMultimediaDetail";
    }

    /**
     * 멀티미디어 정보 수정 화면으로 이동한다.
     * @param searchVO
     * @param multimedia
     * @param model
     * @return "/mbl/com/mlt/EgovMultimediaUpdt"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/goMultimediaUpdt.mdo")
    public String goMultimediaUpdt(
            @ModelAttribute("searchVO") MultimediaVO searchVO,
            Multimedia multimedia, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        Multimedia vo = multimediaService.selectMultimedia(multimedia);
        model.addAttribute("mltmdCmmCodeDetailList",
            getCmmCodeDetailList("COM077"));
        model.addAttribute("multimedia", vo);

        return "egovframework/mbl/com/mlt/EgovMultimediaUpdt";
    }

    /**
     * 멀티미디어 정보 수정 Service interface 호출 및 결과를 반환한다.
     * @param atchFileAt
     * @param multiRequest
     * @param searchVO
     * @param multimedia
     * @param model
     * @return
     *         "forward:/mbl/com/mlt/selectMultimediaList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/updateMultimedia.mdo")
    public String updateMultimedia(
            @RequestParam("atchFileAt") String atchFileAt,
            final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") MultimediaVO searchVO,
            @ModelAttribute("multimedia") Multimedia multimedia,
            BindingResult bindingResult, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        // Validation
        beanValidator.validate(multimedia, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("mltmdCmmCodeDetailList",
                getCmmCodeDetailList("COM077"));
            return "egovframework/mbl/com/mlt/EgovMultimediaUpdt";

        }

        // 첨부파일 관련 첨부파일ID 생성
        List<FileVO> _result = null;
        String _atchFileId = multimedia.getAtchFileId();

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        if (!files.isEmpty()) {
            if ("N".equals(atchFileAt)) {
                _result =
                    fileUtil.parseFileInf(files, "MLT_", 0, _atchFileId, "");
                _atchFileId = fileMngService.insertFileInfs(_result);

                // 첨부파일 ID 셋팅
                multimedia.setAtchFileId(_atchFileId); // 첨부파일
                                                       // ID

            } else {
                FileVO fvo = new FileVO();
                fvo.setAtchFileId(_atchFileId);
                int _cnt = fileMngService.getMaxFileSN(fvo);
                _result =
                    fileUtil.parseFileInf(files, "MLT_", _cnt, _atchFileId, "");
                fileMngService.updateFileInfs(_result);
            }

            // 파일을 상대 경로에 저장한다.
            multimediaService.copyFileToRelativePath(_result);

            // 파일 구분을 조회한다.
            List<?> cmmCode = getCmmCodeDetailList("COM077");
            String mltmdNm = "";

            for (int i = 0; i < cmmCode.size(); i++) {
                if (((CmmnDetailCode) cmmCode.get(i)).getCode()
                    .equalsIgnoreCase(multimedia.getMltmdCode())) {
                    mltmdNm = ((CmmnDetailCode) cmmCode.get(i)).getCodeNm();
                    break;
                }
            }

            // 파일 확장자를 통해 지원브라우저를 조회한다.
            FileVO fvo = new FileVO();
            fvo.setAtchFileId(_atchFileId);
            List<?> multimediaFileList = fileMngService.selectFileInfs(fvo);

            List<String> extList = new ArrayList<String>();

            for (int i = 0; i < multimediaFileList.size(); i++) {
                extList
                    .add(((FileVO) multimediaFileList.get(i)).getFileExtsn());
            }
            multimedia.setBrowserNm(multimediaService.getBrowserInfoFromXML(
                mltmdNm, extList));
        }

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        multimedia.setMberId(mberId);

        multimediaService.updateMultimedia(multimedia);
        return "forward:/mbl/com/mlt/selectMultimediaList.mdo";
    }

    /**
     * 멀티미디어 정보 삭제 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param multimedia
     * @return
     *         "forward:/mbl/com/mlt/selectMultimediaList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mlt/deleteMultimedia.mdo")
    public String deleteMultimedia(
            @ModelAttribute("searchVO") MultimediaVO multimediaVO,
            Multimedia multimedia) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	// 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        multimedia.setMberId(mberId);

        multimediaService.deleteMultimedia(multimedia);
        return "forward:/mbl/com/mlt/selectMultimediaList.mdo";
    }

    /**
     * 공통코드 호출
     * @param codeId
     *        String
     * @return List
     * @exception Exception
     */
    public List<?> getCmmCodeDetailList(String codeId) throws Exception {
        ComDefaultCodeVO comDefaultCodeVO = new ComDefaultCodeVO();
        comDefaultCodeVO.setCodeId(codeId);
        return egovCmmUseService.selectCmmCodeDetail(comDefaultCodeVO);
    }
}
