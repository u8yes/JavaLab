package egovframework.mbl.com.mms.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import egovframework.mbl.com.mms.service.AttachFile;
import egovframework.mbl.com.mms.service.AttachFileVO;
import egovframework.mbl.com.mms.service.EgovMmsService;
import egovframework.mbl.com.mms.service.MmsTransInfo;
import egovframework.mbl.com.mms.service.MmsTransInfoVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 개요
 * -  MMS서비스연계에 대한 Controller를 정의한다.
 *
 * 상세내용
 * -  MMS 전송, MMS 전송 결과 등록, 수정, 조회 요청과 MMS 첨부파일에 대한 등록, 수정, 삭제, 조회 요청 사항을 Service와 매핑 처리한다.
 * @author 조재만
 * @since 2011.08.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.26  조재만          최초 생성
 *
 * </pre>
 */
@Controller
public class EgovMmsController {

    /** EgovMmsService */
    @Resource(name="EgovMmsService")
    protected EgovMmsService egovMmsService;

    /** EgovPropertyService */
    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;

    /** EgovFileMngService */
    @Resource(name="EgovFileMngService")
    private EgovFileMngService fileMngService;

    /** EgovFileMngUtil */
    @Resource(name="EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    /**
     * MMS 전송 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @return String MMS 작성 화면
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/sendMms.mdo")
    public String sendMms(@ModelAttribute("mmsTransInfo") MmsTransInfo mmsTransInfo,
            @RequestParam(value="atchFileSn", required=false)String atchFileSn,
            @RequestParam(value="atchFileNm", required=false)String atchFileNm,
            ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        if (isAuthenticated) {
            AttachFileVO attachFileVO = new AttachFileVO();
            AttachFile attachFile = new AttachFile();

            if (atchFileSn != null && !atchFileSn.equals("") && !atchFileSn.equals("-1")) {
                attachFileVO.setSn(Integer.parseInt(atchFileSn));
                attachFile = egovMmsService.selectAttachFile(attachFileVO);
            } else {
                attachFile.setSn(-1);
            }

            mmsTransInfo.setAttachFile(attachFile);

            Map<String, Object> map = egovMmsService.sendMms(mmsTransInfo);
            boolean requestResult = false;

            mmsTransInfo.setMberId(user.getId());
            requestResult = (Boolean)map.get("requestResult");
            String messageId = (String)map.get("messageId");

            if (requestResult) {
                mmsTransInfo.setRqesterResultNm("요청성공");
                mmsTransInfo.setMssageId(messageId);
                model.addAttribute("requestResult", requestResult);
            } else {
                mmsTransInfo.setRqesterResultNm("요청실패");
                mmsTransInfo.setMssageId("");
                model.addAttribute("requestResult", requestResult);
            }

            // MMS 전송 정보 insert
            egovMmsService.insertMmsTransmissionResult(mmsTransInfo);
        }

        return "egovframework/mbl/com/mms/EgovMobileMmsRegist";
    }

    /**
     * MMS 결과 반환 Service interface 호출 및 결과를 반환한다.
     * @param request
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/receiveMmsTransmissionResult.mdo")
    public void receiveMmsTransmissionResult(HttpServletRequest request) throws Exception {

        MmsTransInfo mmsTransInfo = new MmsTransInfo();
        Map<String, Object> map = egovMmsService.receiveMmsTransmissionResult(request);

        String status = (String)map.get("status");

        // 실제  전송결과 갱신
        if (!map.isEmpty()) {
            if (status.equals("성공")) {
                mmsTransInfo.setMssageId((String)map.get("messageId"));
                mmsTransInfo.setTrnsmisResultNm("전송수신성공");
                egovMmsService.updateMmsTransmissionResult(mmsTransInfo);
            } else {
                mmsTransInfo.setMssageId((String)map.get("messageId"));
                mmsTransInfo.setTrnsmisResultNm("전송수신실패");
                egovMmsService.updateMmsTransmissionResult(mmsTransInfo);
            }
        }
    }

    /**
     * MMS 결과 반환 Service interface 호출 및 결과를 반환한다.
     * @param mmsTransInfo
     * @param model
     * @return String MMS 전송 결과 목록 조회 화면
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/getMmsTransmissionResult.mdo")
    public String getMmsTransmissionResult(@ModelAttribute("searchVO") MmsTransInfoVO mmsTransInfoVO,
            MmsTransInfo mmsTransInfo) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        Map<String, Object> map = egovMmsService.getMmsTransmissionResult(mmsTransInfoVO.getMssageId());

        String status = (String)map.get("status");

        // 실제  전송결과 갱신
        if (!map.isEmpty()) {
            if (status.equals("성공")) {
                mmsTransInfo.setTrnsmisResultNm("전송수신성공");
                egovMmsService.updateMmsTransmissionResult(mmsTransInfo);
            } else {
                mmsTransInfo.setTrnsmisResultNm("전송수신실패");
                egovMmsService.updateMmsTransmissionResult(mmsTransInfo);
            }
        }

        return "forward:/mbl/com/mms/selectMmsTransResultList.mdo";
    }

    /**
     * 모바일 MMS 작성 화면으로 이동한다.
     * @param mmsTransInfo
     * @return String MMS 작성 화면
     * @throws Exception
     */
    @IncludedMblInfo(name="MMS 서비스 연계",order = 412 ,gid = 40)
    @RequestMapping(value="/mbl/com/mms/goMmsWrite.mdo")
    public String goMmsWrite(@ModelAttribute("mmsTransInfo") MmsTransInfo mmsTransInfo) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

     // 첨부파일 순번 초기화
        mmsTransInfo.getAttachFile().setSn(-1);

        return "egovframework/mbl/com/mms/EgovMobileMmsRegist";
    }

    /**
     * MMS 전송 결과 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param mmsTransInfoVO
     * @param model
     * @return String MMS 전송 결과 목록 조회 화면
     * @throws Exception
     */
    @IncludedMblInfo(name="MMS 서비스 연계_MMS 전송 결과 조회",order = 509 ,gid = 50)
    @RequestMapping("/mbl/com/mms/selectMmsTransResultList.mdo")
    public String selectMmsTransResultList(@ModelAttribute("searchVO") MmsTransInfoVO mmsTransInfoVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        mmsTransInfoVO.setPageUnit(propertyService.getInt("pageUnit"));
        mmsTransInfoVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(mmsTransInfoVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(mmsTransInfoVO.getPageUnit());
        paginationInfo.setPageSize(mmsTransInfoVO.getPageSize());

        mmsTransInfoVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        mmsTransInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
        mmsTransInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = egovMmsService.selectMmsTransmissionResultList(mmsTransInfoVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/mms/EgovMmsTransResultList";
    }


    //////////////////////////// 첨부파일 부분 /////////////////////////////////

    /**
     * 모바일 첨부파일 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/selectMblMmsAttachFileList.mdo")
    public ModelAndView selectMblMmsAttachFileList(@ModelAttribute("searchVO") AttachFileVO attachFileVO) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");

        attachFileVO.setPageUnit(propertyService.getInt("pageUnit"));
        attachFileVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(attachFileVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(attachFileVO.getPageUnit());
        paginationInfo.setPageSize(1);

        attachFileVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        attachFileVO.setLastIndex(paginationInfo.getLastRecordIndex());
        attachFileVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = egovMmsService.selectAttachFileList(attachFileVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        modelAndView.addObject("resultList", map.get("resultList"));
        modelAndView.addObject("resultCnt", map.get("resultCnt"));
        modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
    }

    /**
     * 모바일 MMS 첨부파일 선택 화면으로 이동한다.
     * @param attachFileVO
     * @return String MMS 첨부파일 선택 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/mms/goAtchFileSelect.mdo")
    public String goAtchFileSelect(@ModelAttribute("searchVO") AttachFileVO attachFileVO) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        return "egovframework/mbl/com/mms/EgovMobileAttachFileList";
    }

    /**
     * 첨부파일 목록 조회 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @param model
     * @return String 첨부파일 목록 조회 화면
     * @throws Exception
     */
    @IncludedMblInfo(name="MMS 서비스 연계_MMS 첨부파일 관리",order = 510 ,gid = 50)
    @RequestMapping("/mbl/com/mms/selectMmsAttachFileList.mdo")
    public String selectMmsAttachFileList(@ModelAttribute("searchVO") AttachFileVO attachFileVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        attachFileVO.setPageUnit(propertyService.getInt("pageUnit"));
        attachFileVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(attachFileVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(attachFileVO.getPageUnit());
        paginationInfo.setPageSize(attachFileVO.getPageSize());

        attachFileVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        attachFileVO.setLastIndex(paginationInfo.getLastRecordIndex());
        attachFileVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = egovMmsService.selectAttachFileList(attachFileVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);

        return "egovframework/mbl/com/mms/EgovMmsAttachFileList";
    }

    /**
     * 첨부파일 상세정보 조회 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @param model
     * @return String 첨부파일 정보 상세조회 화면
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/selectMmsAttachFile.mdo")
    public String selectMmsAttachFile(@ModelAttribute("searchVO") AttachFileVO attachFileVO, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        AttachFile attachFile = egovMmsService.selectAttachFile(attachFileVO);

        model.addAttribute("attachFile", attachFile);

        return "egovframework/mbl/com/mms/EgovMmsAttachFileDetail";
    }

    /**
     * 첨부파일 등록 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @param attachFile
     * @param multiRequest
     * @param model
     * @return String 첨부파일 목록 조회 화면
     * @throws Exception
     */
    @RequestMapping("/mbl/com/mms/insertMmsAttachFile.mdo")
    public String insertMmsAttachFile(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") AttachFileVO attachFileVO,
            @ModelAttribute("attachFile") AttachFile attachFile,
            ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        // 사용자 인증여부 판단
        if (isAuthenticated) {

            // 첨부파일 관련 첨부파일ID 생성
            List<FileVO> _result = null;
            String _atchFileId = "";

            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            if(!files.isEmpty()){
                _result = fileUtil.parseFileInf(files, "MMS_", 0, "", "");
                int fileSize = Integer.parseInt(_result.get(0).getFileMg());

                if (_result.get(0).getFileExtsn().equals("skm") || _result.get(0).getFileExtsn().equals("k3g")) {
                    if (fileSize > 307200) {
                        model.addAttribute("fileValidation", "movieFalse");
                        return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                    }
                } else {
                    if (fileSize > 20480) {
                        model.addAttribute("fileValidation", "otherFalse");
                        return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                    }
                }
            }

            //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
            _atchFileId = fileMngService.insertFileInfs(_result);

            // 리턴받은 첨부파일ID를 셋팅한다..
            attachFile.setAtchFileId(_atchFileId);
            attachFile.setMberId(user.getId());

            egovMmsService.insertAttachFile(attachFile);
        }

        return "forward:/mbl/com/mms/selectMmsAttachFileList.mdo";
    }

    /**
     * 첨부파일 등록 화면으로 이동한다.
     * @param attachFileVO
     * @return String 첨부파일 등록 화면
     * @throws Exception
     */
    @RequestMapping(value="/mbl/com/mms/goMmsAttachFileRegist.mdo")
    public String goMmsAttachFileRegist(@ModelAttribute("searchVO") AttachFileVO attachFileVO) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
    }

    /**
     * 첨부파일 수정 Service interface 호출 및 결과를 반환한다.
     * @param attachFileVO
     * @param attachFile
     * @param multiRequest
     * @param model
     * @return String 첨부파일 목록 조회 화면
     * @throws Exception
     */
    @RequestMapping(value="/mbl/com/mms/updateMmsAttachFile.mdo")
    public String updateMmsAttachFile(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("searchVO") AttachFileVO attachFileVO,
            @ModelAttribute("attachFile") AttachFile attachFile,
            ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        // 사용자 인증여부 판단
        if (isAuthenticated) {
            String _atchFileId = attachFile.getAtchFileId();
            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            if(!files.isEmpty()) {
                if("".equals(_atchFileId)) {
                    List<FileVO> _result = fileUtil.parseFileInf(files, "MMS_", 0, _atchFileId, "");

                    int fileSize = Integer.parseInt(_result.get(0).getFileMg());

                    if (_result.get(0).getFileExtsn().equals("skm") || _result.get(0).getFileExtsn().equals("k3g")) {
                        if (fileSize > 307200) {
                            model.addAttribute("fileValidation", "movieFalse");
                            return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                        }
                    } else {
                        if (fileSize > 20480) {
                            model.addAttribute("fileValidation", "otherFalse");
                            return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                        }
                    }

                    _atchFileId = fileMngService.insertFileInfs(_result);
                    attachFile.setAtchFileId(_atchFileId);
                } else {
                    FileVO fvo = new FileVO();
                    fvo.setAtchFileId(_atchFileId);
                    int _cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> _result = fileUtil.parseFileInf(files, "MMS_", _cnt, _atchFileId, "");

                    int fileSize = Integer.parseInt(_result.get(0).getFileMg());

                    if (_result.get(0).getFileExtsn().equals("skm") || _result.get(0).getFileExtsn().equals("k3g")) {
                        if (fileSize > 307200) {
                            model.addAttribute("fileValidation", "movieFalse");
                            return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                        }
                    } else {
                        if (fileSize > 20480) {
                            model.addAttribute("fileValidation", "otherFalse");
                            return "egovframework/mbl/com/mms/EgovMmsAttachFileRegist";
                        }
                    }

                    fileMngService.updateFileInfs(_result);
                }
            }

            attachFile.setMberId(user.getId());
            egovMmsService.updateAttachFile(attachFile);
        }

        return "forward:/mbl/com/mms/selectMmsAttachFileList.mdo";
    }

    /**
     * 첨부파일 정보 수정 화면으로 이동한다.
     * @param attachFileVO
     * @param atchFileDelFlag
     * @param attachFile
     * @param model
     * @return String 첨부파일 정보 수정 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/mms/goMmsAttachFileUpdt.mdo")
    public String goMmsAttachFileUpdt(@ModelAttribute("searchVO") AttachFileVO attachFileVO,
            @RequestParam(value="atchFileDelFlag", required=false) String atchFileDelFlag,
            AttachFile attachFile, ModelMap model) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        attachFileVO.setSn(attachFile.getSn());
        AttachFile result = egovMmsService.selectAttachFile(attachFileVO);

        if (atchFileDelFlag != null && atchFileDelFlag.equals("Y")) {
            result.setAtchFileId("");
            result.setAtchFileNm("");
        }

        model.addAttribute("attachFile", result);

        return "egovframework/mbl/com/mms/EgovMmsAttachFileUpdt";
    }

    /**
     * 첨부파일 삭제 Service interface 호출 및 결과를 반환한다.
     *
     * @param attachFile
     * @return String  첨부파일 목록 조회 화면
     * @throws Exception
    */
    @RequestMapping(value="/mbl/com/mms/deleteMmsAttachFile.mdo")
    public String deleteMmsAttachFile(@ModelAttribute("attachFile") AttachFile attachFile) throws Exception {

        // 권한 체크
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/mbl/com/uat/uia/EgovLoginUsr";
        }

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        // 사용자 인증여부 판단
        if (isAuthenticated) {
            // 첨부파일 정보 삭제
                attachFile.setMberId(user.getId());
            egovMmsService.deleteAttachFile(attachFile);

            // 첨부파일 삭제
            FileVO fvo = new FileVO();
            fvo.setAtchFileId(attachFile.getAtchFileId());
            fvo.setFileSn("0");

            fileMngService.deleteFileInf(fvo);
        }

        return "forward:/mbl/com/mms/selectMmsAttachFileList.mdo";
    }


}