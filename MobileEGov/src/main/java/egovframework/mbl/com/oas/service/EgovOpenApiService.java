package egovframework.mbl.com.oas.service;

import java.util.Map;

import org.apache.xmlbeans.XmlObject;

/**
 * 개요
 * - OpenAPI연계에 대한 Service Interface를 정의한다.
 * 
 * 상세내용
 * - OpenAPI연계 정보에 대한 등록, 조회, OpenAPI 서비스 내용 조회 기능을 제공한다.
 * - OpenAPI연계 정보의 조회기능은 목록, 상세조회로 구분된다.
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
public interface EgovOpenApiService {
    
    /**
     * OpenAPI 연계정보 목록을 조회하는 Service interface 메서드
     * @param openApiVO
     * @return Map<String, Object> OpenAPI 연계정보 조회결과 리스트, 조회건수
     * @throws Exception
    */
    public Map<String, Object> selectOpenApiInquiryHistoryList(OpenApiVO openApiVO) throws Exception;

    /**
     * OpenAPI 연계정보를 상세조회하는 Service interface 메서드
     * @param openApiVO
     * @return OpenApi OpenAPI 연계정보
     * @throws Exception
    */
    public OpenApi selectOpenApiInquiryHistory(OpenApiVO openApiVO) throws Exception;

    /**
     * OpenAPI 연계정보를 등록하는 Service interface 메서드
     * @param openApi
     * @throws Exception
    */
    public void insertOpenApiInquiryHistory(OpenApi openApi) throws Exception;
    
    /**
     * 기관별 OpenAPI 서비스를 이용하여 필요한 정보를 가져오는 Service interface 메서드
     * @param requestUrl
     * @param obj
     * @return XmlObject OpenAPI 서비스 반환값이 매핑된 객체
     * @throws Exception
    */
    public XmlObject getOpenApiSvcInfo(String requestUrl) throws Exception;
}