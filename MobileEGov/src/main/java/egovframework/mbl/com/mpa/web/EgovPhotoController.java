package egovframework.mbl.com.mpa.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mpa.service.EgovPhotoService;
import egovframework.mbl.com.mpa.service.Photo;
import egovframework.mbl.com.mpa.service.PhotoVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 개요
 * - 사진 앨범에 대한 Controller를 정의한다.
 *
 * 상세내용
 * - 사진에 대한 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 정홍규
 * @since 2011.08.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.08.10  정홍규          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovPhotoController {

    /**
     * EgovPhotoService
     */
    @Resource(name = "PhotoService")
    private EgovPhotoService photoService;

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

    /**
     * 모바일 사진 목록 화면으로 이동한다.
     * @param model
     * @return "/mbl/com/mpa/EgovMobilePhotoList"
     * @throws Exception
     */
    @IncludedMblInfo(name="모바일 사진 앨범",order = 402 ,gid = 40)
    @RequestMapping(value = "/mbl/com/mpa/goMobilePhotoList.mdo")
    public String goMobilePhotoList(ModelMap model) throws Exception {
        return "egovframework/mbl/com/mpa/EgovMobilePhotoList";
    }

    /**
     * 모바일 사진 목록 조회 Service interface 호출 및 결과를
     * 반환한다.(JSON 통신)
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/selectMobilePhotoList.mdo")
    public ModelAndView selectMobilePhotoList(ModelMap model) throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");

        PhotoVO searchVO = new PhotoVO();

        searchVO.setFirstIndex(0);
        searchVO.setRecordCountPerPage(1000);

        List<?> PhotoList = photoService.selectPhotoList(searchVO);
        modelAndView.addObject("resultList", PhotoList);

        return modelAndView;
    }

    /**
     * 사진 정보 등록 화면으로 이동한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mpa/EgovPhotoRegist"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/goPhotoRegist.mdo")
    public String goPhotoRegist(@ModelAttribute("searchVO") PhotoVO searchVO,
            ModelMap model) throws Exception {
        model.addAttribute("photo", new Photo());

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        return "egovframework/mbl/com/mpa/EgovPhotoRegist";
    }

    /**
     * 사진 정보 등록 Service interface 호출 및 결과를 반환한다.
     * @param multiRequest
     * @param searchVO
     * @param photo
     * @param bindingResult
     * @param model
     * @return
     *         "forward:/mbl/com/mpa/selectPhotoList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/insertPhoto.mdo")
    public String insertPhoto(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") PhotoVO searchVO,
            @ModelAttribute("photo") Photo photo, BindingResult bindingResult,
            ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        beanValidator.validate(photo, bindingResult);

        if (bindingResult.hasErrors()) {
            return "egovframework/mbl/com/mpa/EgovPhotoRegist";
        }

        // 첨부파일 관련 첨부파일ID 생성
        List<FileVO> _result = null;
        String _atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        if (!files.isEmpty()) {
            _result = fileUtil.parseFileInf(files, "MPA_", 0, "", "");
            _atchFileId = fileMngService.insertFileInfs(_result); // 파일이
                                                                  // 생성되고나면
                                                                  // 생성된
                                                                  // 첨부파일
                                                                  // ID를
                                                                  // 리턴한다.
        }

        // 리턴받은 첨부파일ID를 셋팅한다..
        photo.setAtchFileId(_atchFileId); // 첨부파일 ID

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        photo.setMberId(mberId);

        photoService.insertPhoto(photo);
        return "forward:/mbl/com/mpa/selectPhotoList.mdo";
    }

    /**
     * 사진 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param model
     * @return "/mbl/com/mpa/EgovPhotoList"
     * @throws Exception
     */
    @IncludedMblInfo(name="모바일 사진 앨범",order = 501 ,gid = 50)
    @RequestMapping(value = "/mbl/com/mpa/selectPhotoList.mdo")
    public String selectPhotoList(@ModelAttribute("searchVO") PhotoVO searchVO,
            ModelMap model) throws Exception {

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

        List<?> PhotoList = photoService.selectPhotoList(searchVO);
        model.addAttribute("resultList", PhotoList);

        int totCnt = photoService.selectPhotoListTotCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mpa/EgovPhotoList";
    }

    /**
     * 사진 상세정보 조회 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param photo
     * @param model
     * @return "/mbl/com/mpa/EgovPhotoDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/selectPhoto.mdo")
    public String selectPhoto(@ModelAttribute("searchVO") PhotoVO searchVO,
            Photo photo, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        Photo vo = photoService.selectPhoto(photo);
        model.addAttribute("result", vo);
        model.addAttribute("searchVO", searchVO);

        return "egovframework/mbl/com/mpa/EgovPhotoDetail";
    }

    /**
     * 사진 정보 수정 화면으로 이동한다.
     * @param searchVO
     * @param photo
     * @param model
     * @return "/mbl/com/mpa/EgovPhotoUpdt"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/goPhotoUpdt.mdo")
    public String goPhotoUpdt(@ModelAttribute("searchVO") PhotoVO searchVO,
            Photo photo, ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        Photo vo = photoService.selectPhoto(photo);
        model.addAttribute("photo", vo);

        return "egovframework/mbl/com/mpa/EgovPhotoUpdt";
    }

    /**
     * 사진 정보 수정 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param photo
     * @param model
     * @return
     *         "forward:/mbl/com/mpa/selectPhotoList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/updatePhoto.mdo")
    public String updatePhoto(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") PhotoVO searchVO,
            @ModelAttribute("photo") Photo photo, BindingResult bindingResult,
            ModelMap model) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        // Validation
        beanValidator.validate(photo, bindingResult);

        if (bindingResult.hasErrors()) {
            return "egovframework/mbl/com/mpa/EgovPhotoUpdt";

        }

        // 첨부파일 관련 첨부파일ID 생성
        List<FileVO> _result = null;
        String _atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        if (!files.isEmpty()) {
            _result = fileUtil.parseFileInf(files, "MPA_", 0, "", "");
            _atchFileId = fileMngService.insertFileInfs(_result); // 파일이
                                                                  // 생성되고나면
                                                                  // 생성된
                                                                  // 첨부파일
                                                                  // ID를
                                                                  // 리턴한다.
        }

        // 리턴받은 첨부파일ID를 셋팅한다..
        if (_atchFileId != null) {
            photo.setAtchFileId(_atchFileId); // 첨부파일
                                              // ID
        }

        // 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        photo.setMberId(mberId);

        photoService.updatePhoto(photo);
        return "forward:/mbl/com/mpa/selectPhotoList.mdo";
    }

    /**
     * 사진 정보 삭제 Service interface 호출 및 결과를 반환한다.
     * @param searchVO
     * @param photo
     * @return
     *         "forward:/mbl/com/mpa/selectPhotoList.mdo"
     * @throws Exception
     */
    @RequestMapping(value = "/mbl/com/mpa/deletePhoto.mdo")
    public String deletePhoto(@ModelAttribute("searchVO") PhotoVO photoVO,
            Photo photo) throws Exception {

    	// 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

    	// 로그인VO에서 사용자 정보 가져오기
        LoginVO loginVO =
            (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String mberId = loginVO.getId();

        photo.setMberId(mberId);

        photoService.deletePhoto(photo);
        return "forward:/mbl/com/mpa/selectPhotoList.mdo";
    }
}
