package egovframework.com.cmm;

import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : EgovComExcepHndlr.java
 * @Description : 공통서비스의 exception 처리 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 13.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 13.
 * @version
 * @see
 *
 */
public class EgovComExcepHndlr implements ExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovComExcepHndlr.class);

    /*
    @Resource(name = "otherSSLMailSender")
    private SimpleSSLMail mailSender;
     */
    /**
     * 발생된 Exception을 처리한다.
     */
    public void occur(Exception ex, String packageName) {
	//log.debug(" EgovServiceExceptionHandler run...............");
	try {
	    //mailSender. send(ex, packageName);
	    //log.debug(" sending a alert mail  is completed ");
		LOGGER.error(packageName, ex);
		//2017.02.17 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		
	} catch(IllegalArgumentException e) {
	    LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());	
		//2017.02.17 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]	    
	} catch (Exception e) {
		LOGGER.error("["+e.getClass()+"] Try/Catch...Runing : " + e.getMessage());
	}
    }
}
