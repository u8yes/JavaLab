package egovframework.mbl.com.oas.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.oas.service.OpenApi;
import egovframework.mbl.com.oas.service.OpenApiVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - OpenAPI연계에 대한 DB상의 접근, 변경을 처리한다.
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
@Repository("openApiDAO")
public class OpenApiDAO extends EgovComAbstractDAO {

    /**
     * OpenAPI 연계정보 목록을 조회한다.
     * @param openApiVO
     * @return List<OpenApiVO> OpenAPI 연계정보 리스트
     * @throws Exception
    */
    @SuppressWarnings("unchecked")
	public List<OpenApiVO> selectOpenApiInquiryHistoryList(OpenApiVO openApiVO) throws Exception {
        return (List<OpenApiVO>) list("OpenApiDAO.selectOpenApiInquiryHistoryList", openApiVO);
    }

    /**
     * OpenAPI 연계정보에 대한 목록 건수를 조회 한다.
     *
     * @param openApiVO
     * @return int OpenAPI 연계정보 목록 건수
     * @throws Exception
    */
    public int selectOpenApiInquiryHistoryListCnt(OpenApiVO openApiVO) throws Exception {
        return (Integer)select("OpenApiDAO.selectOpenApiInquiryHistoryListCnt", openApiVO);
    }

    /**
     * OpenAPI 연계정보를 조회한다.
     * @param openApiVO
     * @return OpenApi OpenAPI 연계정보
     * @throws Exception
    */
    public OpenApi selectOpenApiInquiryHistory(OpenApiVO openApiVO) throws Exception {
        return (OpenApi)select("OpenApiDAO.selectOpenApiInquiryHistory", openApiVO);
    }

    /**
     * OpenAPI 연계정보를 DB에 등록한다.
     *
     * @param openApi
     * @throws Exception
    */
    public void insertOpenApiInquiryHistory(OpenApi openApi) throws Exception {
        insert("OpenApiDAO.insertOpenApiInquiryHistory", openApi);
    }
}