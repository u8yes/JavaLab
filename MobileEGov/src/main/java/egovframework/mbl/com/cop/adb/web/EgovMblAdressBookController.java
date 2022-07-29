package egovframework.mbl.com.cop.adb.web;

import java.net.URLDecoder;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.adb.service.AdressBook;
import egovframework.com.cop.adb.service.AdressBookUser;
import egovframework.com.cop.adb.service.AdressBookUserVO;
import egovframework.com.cop.adb.service.AdressBookVO;
import egovframework.com.cop.adb.service.EgovAdressBookService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.mbl.com.cmm.annotation.IncludedMblInfo;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 주소록정보를 관리하기 위한 컨트롤러 클래스
 * @author 조재만
 * @since 2011.08.24
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.24  조재만          최초 생성
 *   2017.03.02  조성원          시큐어코딩(ES)-Null Pointer 역참조[CWE-476]
 * </pre>
 */
@Controller
public class EgovMblAdressBookController {

    @Resource(name = "EgovAdressBookService")
    private EgovAdressBookService adbkService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    /**
     * 주소록 정보에 대한 목록 화면을 출력한다.
     * @param adbkVO
     * @return  "mbl/com/cop/adb/EgovAdressBookList"
     * @throws Exception
     */
    @IncludedMblInfo(name="주소록관리",order = 110 ,gid = 10)
    @RequestMapping(value="/cop/adb/selectAdbkList.mdo")
    public String selectAdressBookList(@ModelAttribute("searchVO") AdressBookVO adbkVO) throws Exception {
        return "egovframework/mbl/com/cop/adb/EgovAdressBookList";
    }

     /**
     * 주소록 정보에 대한 목록을 조회한다.
     *
     * @param adbkVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/selectAdbkListActor.mdo")
    public ModelAndView selectAdressBookListActor(@ModelAttribute("searchVO") AdressBookVO adbkVO, SessionStatus status) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        ModelAndView modelAndView = new ModelAndView("jsonView");

        adbkVO.setSearchWrd(URLDecoder.decode(adbkVO.getSearchWrd(), "UTF-8"));

        adbkVO.setPageUnit(propertyService.getInt("pageUnit"));
        adbkVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(adbkVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(adbkVO.getPageUnit());
        paginationInfo.setPageSize(1);

        adbkVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        adbkVO.setLastIndex(paginationInfo.getLastRecordIndex());
        adbkVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        adbkVO.setWrterId(user.getId());
        adbkVO.setTrgetOrgnztId(user.getOrgnztId());

        Map<String, Object> map = adbkService.selectAdressBookList(adbkVO);
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        modelAndView.addObject("resultList", map.get("resultList"));
        modelAndView.addObject("resultCnt", map.get("resultCnt"));
        modelAndView.addObject("userId", user.getId());
        modelAndView.addObject("paginationInfo", paginationInfo);

        return modelAndView;
    }

    /**
     * 주소록등록 화면으로 이동한다.
     *
     * @param adbkVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/addAdbkInf.mdo")
    public String addAdressBook(@ModelAttribute("searchVO") AdressBookVO adbkVO, SessionStatus status, ModelMap model) throws Exception {
        return "egovframework/mbl/com/cop/adb/EgovAdressBookRegist";
    }

    /**
     * 주소록을 삭제한다.
     *
     * @param adbkVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/deleteAdbkInf.mdo")
    public String deleteAdressBook(@ModelAttribute("searchVO") AdressBookVO adbkVO, SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        AdressBook adbk = adbkService.selectAdressBook(adbkVO);
        adbk.setUseAt("N");
        adbk.setLastUpdusrId(user.getId());
        adbkService.deleteAdressBook(adbk);

        return "forward:/cop/adb/selectAdbkListActor.mdo";
    }

    /**
     * 주소록의 구성원을 추가한다.
     *
     * @param userVO
     * @param adbkVO
     * @param checkCnd
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping("/cop/adb/addUser.mdo")
    public String addUser(@ModelAttribute("searchVO") AdressBookVO adbkVO, @ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO,
            @RequestParam("checkCnd")String checkCnd,
            SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String[] tempId = adbkUserVO.getUserId().split(",");

        for(int i =0; i < tempId.length; i++){
            if(!tempId[i].equals("")){
                AdressBookUser adbkUser = adbkService.selectAdbkUser(tempId[i]);
                if (adbkUser != null) {
                    adbkVO.getAdbkMan().add(adbkUser);
                }
            }
        }

        adbkUserVO.setUserId(adbkUserVO.getUserId());
        adbkVO.setAdbkId(adbkVO.getAdbkId());

        if(checkCnd.equals("regist"))
            return "egovframework/mbl/com/cop/adb/EgovAdressBookRegist";
        else{
            model.addAttribute("writer" , true);
            return "egovframework/mbl/com/cop/adb/EgovAdressBookUpdt";
        }
    }

    /**
     * 주소록의 구성원을 삭제한다.
     *
     * @param userVO
     * @param adbkVO
     * @param checkCnd
     * @param checkWord
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping("/cop/adb/deleteUser.mdo")
    public String deleteUser( @ModelAttribute("searchVO") AdressBookVO adbkVO, @ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO,
            @RequestParam("checkWord")String checkWord, @RequestParam("checkCnd")String checkCnd,
            @RequestParam(value="userId", required = false) String userId,
            SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String[] tempId = adbkUserVO.getUserId().split(",");

        String id = "";

        for(int i =0; i < tempId.length; i++){

            if(tempId[i].equals(checkWord)){
                continue;
            }

            if(!tempId[i].equals("")){
                AdressBookUser adbkUser = adbkService.selectAdbkUser(tempId[i]);
                adbkVO.getAdbkMan().add(adbkUser);
            }

            id += tempId[i] + ",";
        }

        adbkUserVO.setUserId(id);


        if(checkCnd.equals("regist"))
            return "egovframework/mbl/com/cop/adb/EgovAdressBookRegist";
        else{
            model.addAttribute("writer" , true);
            return "egovframework/mbl/com/cop/adb/EgovAdressBookUpdt";
        }
    }


    /**
     * 주소록 구성원 찾기 팝업화면으로 이동한다.
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/openPopup.mdo")
    public String openPopupWindow(@ModelAttribute("searchVO") AdressBookVO adbkVO,
            @RequestParam(value="userId", required = false) String userId,
            @RequestParam(value="checkCnd", required = false) String checkCnd,
            Model model) throws Exception {

        model.addAttribute("userId", userId);
        model.addAttribute("checkCnd", checkCnd);

        return "egovframework/mbl/com/cop/adb/EgovAdressBookPopup";
    }

    /**
     * 주소록 등록가능한 구성원을 조회한다.
     *
     * @param adbkUserVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/selectManList.mdo")
    public ModelAndView selectUserList(@ModelAttribute("searchVO") AdressBookUserVO adbkUserVO,
            @RequestParam(value="userId", required = false) String userId) throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");

        adbkUserVO.setSearchWrd(URLDecoder.decode(adbkUserVO.getSearchWrd(), "UTF-8"));

        if(adbkUserVO.getSearchCnd() == null || adbkUserVO.getSearchCnd().equals("")){
            adbkUserVO.setSearchCnd("0");
        }

        adbkUserVO.setPageUnit(propertyService.getInt("pageUnit"));
        adbkUserVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(adbkUserVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(adbkUserVO.getPageUnit());
        paginationInfo.setPageSize(1);

        adbkUserVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        adbkUserVO.setLastIndex(paginationInfo.getLastRecordIndex());
        adbkUserVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = null;

        int totCnt = 0;
        if(adbkUserVO.getSearchCnd().equals("0")){
            map = adbkService.selectManList(adbkUserVO);
            //2017.03.02 조성원 시큐어코딩(ES)-Null Pointer 역참조[CWE-476]
            totCnt = Integer.parseInt(EgovStringUtil.nullConvertInt(map.get("resultCnt")));
            paginationInfo.setTotalRecordCount(totCnt);
        }else{
            map = adbkService.selectCardList(adbkUserVO);
            //2017.03.02 조성원 시큐어코딩(ES)-Null Pointer 역참조[CWE-476]
            totCnt = Integer.parseInt(EgovStringUtil.nullConvertInt(map.get("resultCnt")));
            paginationInfo.setTotalRecordCount(totCnt);
        }
        paginationInfo.setTotalRecordCount(totCnt);

        modelAndView.addObject("resultList", map.get("resultList"));
        modelAndView.addObject("resultCnt", map.get("resultCnt"));
        modelAndView.addObject("paginationInfo", paginationInfo);
        modelAndView.addObject("userId", userId);

        return modelAndView;
    }

    /**
     * 주소록상세조회수정 화면으로 이동한다.
     *
     * @param adbkUserVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/updateAdbkInf.mdo")
    public String updateAdbkInf(@ModelAttribute("searchVO") AdressBookVO adbkVO,
           SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        AdressBookVO tempAdbkVO = adbkService.selectAdressBook(adbkVO);
        // 검색조건 유지
        tempAdbkVO.setPageIndex(adbkVO.getPageIndex());
        tempAdbkVO.setSearchWrd(adbkVO.getSearchWrd());
        tempAdbkVO.setSearchCnd(adbkVO.getSearchCnd());
        AdressBookUserVO adbkUserVO = new AdressBookUserVO();

        boolean writer = false;
        String id = "";

        for(int i = 0; i < tempAdbkVO.getAdbkMan().size(); i++){
            if (tempAdbkVO.getAdbkMan().get(i).getNcrdId() == null) {
                tempAdbkVO.getAdbkMan().get(i).setNcrdId("");
            }

            if (tempAdbkVO.getAdbkMan().get(i).getEmplyrId() == null) {
                tempAdbkVO.getAdbkMan().get(i).setEmplyrId("");
            }
        }

        for(int i = 0; i < tempAdbkVO.getAdbkMan().size(); i++){
            if (tempAdbkVO.getAdbkMan().get(i).getEmplyrId().equals("")){
                id += tempAdbkVO.getAdbkMan().get(i).getNcrdId() + ",";
            } else {
                id += tempAdbkVO.getAdbkMan().get(i).getEmplyrId() + ",";
            }
        }

        adbkUserVO.setUserId(id);

        if(tempAdbkVO.getWrterId().equals(user.getId())){
            writer = true;
        }

        tempAdbkVO.setSearchCnd(adbkVO.getSearchCnd());
        tempAdbkVO.setSearchWrd(adbkVO.getSearchWrd());

        model.addAttribute("searchVO", tempAdbkVO);
        model.addAttribute("adbkUserVO", adbkUserVO);
        model.addAttribute("writer" , writer);
        return "egovframework/mbl/com/cop/adb/EgovAdressBookUpdt";
    }

    /**
     * 주소록 정보를 등록한다.
     *
     * @param adbkVO
     * @param adbkUserVO
     * @param status
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/registAdbkInf.mdo")
    public String registadbk(@ModelAttribute("searchVO") AdressBookVO adbkVO, @ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO,
        SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        adbkVO.setWrterId(user.getId());
        adbkVO.setFrstRegisterId(user.getId());
        adbkVO.setLastUpdusrId(user.getId());

        String[] tempId = adbkUserVO.getUserId().split(",");


        for(int i =0; i < tempId.length; i++){
            if(!tempId[i].equals("")){
                AdressBookUser adbkUser = adbkService.selectAdbkUser(tempId[i]);
                adbkVO.getAdbkMan().add(adbkUser);
            }
        }

        adbkVO.setTrgetOrgnztId(user.getOrgnztId());
        adbkService.insertAdressBook(adbkVO);
        return "forward:/cop/adb/selectAdbkList.mdo";
    }

    /**
     * 주소록 정보를 수정한다.
     *
     * @param adbkVO
     * @param adbkUserVO
     * @param status
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/adb/updateAdressBook.mdo")
    public String UpdateAdressBook(@ModelAttribute("searchVO") AdressBookVO adbkVO,  @ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO,
        SessionStatus status, ModelMap model) throws Exception {

        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String[] tempId = adbkUserVO.getUserId().split(",");

        for(int i =0; i < tempId.length; i++){
            if(!tempId[i].equals("")){
                AdressBookUser adbkUser = adbkService.selectAdbkUser(tempId[i]);
                adbkVO.getAdbkMan().add(adbkUser);
            }
        }

        adbkVO.setLastUpdusrId(user.getId());
        adbkVO.setUseAt("Y");
        adbkService.updateAdressBook(adbkVO);

        return "forward:/cop/adb/selectAdbkList.mdo";
    }

}
