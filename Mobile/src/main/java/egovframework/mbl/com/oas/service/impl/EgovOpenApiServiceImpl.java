package egovframework.mbl.com.oas.service.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.mbl.com.oas.service.EgovOpenApiService;
import egovframework.mbl.com.oas.service.OpenApi;
import egovframework.mbl.com.oas.service.OpenApiVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xmlbeans.XmlObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

/**
 * 개요
 * - OpenAPI연계에 대한 대한민국정부포털 검색 정보 Service Interface를 구현한다.
 * 
 * 상세내용
 * - OpenAPI연계 대한민국정부포털 검색 정보에 대한 등록, 조회, OpenAPI 서비스 내용 조회 기능을 제공한다.
 * - OpenAPI연계 대한민국정부포털 검색 정보의 조회기능은 목록, 상세조회로 구분된다.
 * @author 조재만
 * @since 2011.08.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.08  조재만          최초 생성
 *
 * </pre>
 */
@Service("EgovOpenApiService")
public class EgovOpenApiServiceImpl extends EgovAbstractServiceImpl implements EgovOpenApiService {

    /** OpenApiDAO */
    @Resource(name="openApiDAO")
    private OpenApiDAO openApiDAO;

    /** ID Generation */
    @Resource(name="egovOpenApiIdGnrService")
    private EgovIdGnrService idgenService;
   
    /**
     * OpenAPI 연계정보 목록을 조회한다.
     * @param openApiVO
     * @return Map<String, Object> OpenAPI 연계정보 조회결과 리스트, 조회건수
     * @throws Exception
    */
    public Map<String, Object> selectOpenApiInquiryHistoryList(OpenApiVO openApiVO) throws Exception {
        List<OpenApiVO> result = openApiDAO.selectOpenApiInquiryHistoryList(openApiVO);

        int cnt = openApiDAO.selectOpenApiInquiryHistoryListCnt(openApiVO);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /**
     * OpenAPI 연계정보를 조회한다.
     * @param openApiVO
     * @return OpenApi OpenAPI 연계정보
     * @throws Exception
    */
    public OpenApi selectOpenApiInquiryHistory(OpenApiVO openApiVO) throws Exception {
        return openApiDAO.selectOpenApiInquiryHistory(openApiVO);
    }

    /**
     * OpenAPI 연계정보를 DB에 등록한다.
     * @param openApi
     * @throws Exception
    */
    public void insertOpenApiInquiryHistory(OpenApi openApi) throws Exception {
        int sn = idgenService.getNextIntegerId();
        
        openApi.setSn(sn);
        openApiDAO.insertOpenApiInquiryHistory(openApi);
    }
    
    /**
     * 기관별 OpenAPI 서비스를 이용하여 필요한 정보를 가져온다.
     * @param requestUrl
     * @param obj
     * @return XmlObject OpenAPI 서비스 반환값이 매핑된 객체
     * @throws Exception
    */
    public XmlObject getOpenApiSvcInfo(String requestUrl) throws Exception {
        // OPEN-API 서비스 URL을 호출하면 반환되는 XML형태의 값을 String으로 변환
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
        Document doc = documentBuilderFactory.newDocumentBuilder().parse(requestUrl); 
        StringWriter stw = new StringWriter();
        Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
        serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
        String xmlString = stw.toString();

        // 라이브러리가 파싱 못하는 특수문자 변환
        xmlString = xmlString.replaceAll("\\<\\!\\[CDATA\\[", "");
        xmlString = xmlString.replaceAll("\\]\\]\\>", "");
        xmlString = xmlString.replaceAll("&middot;", "·");
        xmlString = xmlString.replaceAll("&lsquo;", "‘");
        xmlString = xmlString.replaceAll("&rsquo;", "’");
        xmlString = xmlString.replaceAll("&ldquo;", "“");
        xmlString = xmlString.replaceAll("&rdquo;", "”");
        xmlString = xmlString.replaceAll("&lt;", "〈");
        xmlString = xmlString.replaceAll("&gt;", "〉");
        xmlString = xmlString.replaceAll("&amp;", "＆");
        xmlString = xmlString.replaceAll("&apos;", "′");
        xmlString = xmlString.replaceAll("&quot;", "");
        xmlString = xmlString.replaceAll("&", "＆");

        // 객체에 매핑
        XmlObject obj = XmlObject.Factory.parse(xmlString);


        
        return obj;
    }
}