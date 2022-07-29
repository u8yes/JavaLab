/**
 *  Class Name : EgovPdfCnvr.java
 *  Description : xls,doc,ppt를 Pdf로 변환하는 화면 Business Interface class
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.02.02    이 용          최초 생성
 *   2017.02.17          김준호       시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		        	
 *   2017.03.02    조성원       시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
 *
 *  @author 공통 서비스 개발팀 이 용
 *  @since 2009. 02. 02
 *  @version 1.0
 *  @see
 * The type com.sun.star.lang.XeventListener cannot be resolved. It is indirectly referenced from required .class files
 *  Copyright (C) 2009 by EGOV  All right reserved.
 */

package egovframework.com.utl.sim.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovStringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;


public class EgovPdfCnvr
{
	public static String addrIP = "";
    static final char FILE_SEPARATOR     = File.separatorChar;
    // 최대 문자길이
    static final int MAX_STR_LEN = 1024;

	public static final int BUFF_SIZE = 2048;
	//2017.02.17     김준호          시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovPdfCnvr.class);         
    /**
     * <pre>
     * Comment : doc, xls 파일등을 PDF변환 변환한다. .
     * </pre>
     * @param String pdfFileSrc        doc, xls 파일 전체경로
     * @param String targetPdf         변환파일명(확장자 제외)
     * @return boolean  status         true/false 를 리턴한다.
     * @version 1.0 (2009.02.10)
     * @see
     */
	@SuppressWarnings("unused")
	public static boolean getPDF(String targetPdf, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean status = false;

		try {
			MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
			Iterator<?> file_iter = mptRequest.getFileNames();

			while(file_iter.hasNext()){
				MultipartFile mFile = mptRequest.getFile((String)file_iter.next());

				if(mFile.getSize()>0){

					//Write File 이후 Move File????
					String newName = "";
					String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
					String orginFileName = mFile.getOriginalFilename();

					int _index = orginFileName.lastIndexOf(".");
			        String fileExt = orginFileName.substring(_index+1);

					//newName 은 Naming Convention에 의해서 생성
					newName = EgovStringUtil.getTimeStamp();	// 2012.11 KISA 보안조치
					writeFile(mFile, newName, stordFilePath);

					String pdfFileSrcValue = stordFilePath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
					File inputFile = new File(EgovWebUtil.filePathBlackList(pdfFileSrcValue + newName));

					if(inputFile.exists()){
						// connect to an OpenOffice.org instance running on port 8100
						SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
						connection.connect();
						//원본 디렉토리에 targetPdf 명칭지정
						String valueFile = null;
						valueFile  = inputFile.getParent().replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
						File outputFile = new File(valueFile+"/"+targetPdf+".pdf");
						// convert
						DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
						converter.convert(inputFile, outputFile);
						// close the connection
						connection.disconnect();

			            if(inputFile.exists())
			            {
	                        //3. 삭제해줍니다.
			                status = inputFile.delete();
			            }
						status = true;
					}else{
						status = false;
					}
				}
			}
		//2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
		} catch(IllegalArgumentException e) {
		    LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
			status = false;
		    //2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]			
		} catch ( Exception ex ) {
		    //ex.printStackTrace();
		    //System.out.println(ex);	// 보안점검 후속조치
			//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]	
			LOGGER.error("["+ ex.getClass() +"] Try/Catch...Exception : " + ex.getMessage());
			status = false;
		}
		// 메소드 종료 Log
		return status;
	}

	/**
	 * 파일을 실제 물리적인 경로에 생성한다.
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	// 2011.10.10 보안점검 후속조치
    protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));

			if (!cFile.isDirectory())
	        	//2017.02.17 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		        	
	        	if(cFile.mkdir()){
	        		LOGGER.debug("[file.mkdirs] cFile : Creation Success ");
	        	}else{
	        	    LOGGER.error("[file.mkdirs] cFile : Creation Fail ");
	        	}
			bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + newName));

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch(IllegalArgumentException e) {
			//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		
		    LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
		} catch (Exception e) {
			//e.printStackTrace();
			//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]		
			LOGGER.error("["+ e.getClass() +"] Try/Catch...Exception : " + e.getMessage());
			throw e;
		} finally {
			if (bos != null) {
				try {
					bos.close();
				//2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
				} catch(IllegalArgumentException e) {
		    		LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
				//2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]					
				} catch (Exception ignore) {
					LOGGER.error("["+ ignore.getClass() +"] Try/Catch...Exception : " + ignore.getMessage());
				}
			}
			if (stream != null) {
				try {
					stream.close();
				//2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
				} catch(IllegalArgumentException e) {
		    		LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
				//2017.02.27 김준호 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]					
				} catch (Exception ignore) {
					//System.out.println("IGNORE: " + ignore);
					LOGGER.error("["+ ignore.getClass() +"] Try/Catch...Exception : " + ignore.getMessage());
					//2017.03.02 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
				}
			}
		}
        }
}