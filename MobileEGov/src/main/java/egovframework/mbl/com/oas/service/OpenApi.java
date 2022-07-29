package egovframework.mbl.com.oas.service;

import java.util.Date;

/**
 * 개요
 * - OpenAPI연계에 대한 model을 정의한다.
 * 
 * 상세내용
 * - OpenAPI연계 정보를 관리한다.
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
public class OpenApi {

    /**
     * 순번
     */
    private int sn;

    /**
     * 회원ID
     */
    private String mberId;

    /**
     * OpenAPI서비스명
     */
    private String openApiSvcNm;

    /**
     * OpenAPI 제공 기관명
     */
    private String openApiProvdInsttNm;

    /**
     * 서비스내용
     */
    private String openApiSvcCn;

    /**
     * 수집일시
     */
    private Date collectDt;

    /**
     * 순번을 가져온다.
     * @return int 순번
     */
    public int getSn(){
        return sn;
    }

    /**
     * 회원ID를 가져온다.
     * @return String 회원ID
     */
    public String getMberId(){
        return mberId;
    }

    /**
     * OpenAPI 서비스명을 가져온다.
     * @return String OpenAPI 서비스명
     */
    public String getOpenApiSvcNm(){
        return openApiSvcNm;
    }

    /**
     * OpenAPI 제공 기관명을 가져온다.
     * @return String OpenAPI 제공 기관명
     */
    public String getOpenApiProvdInsttNm(){
        return openApiProvdInsttNm;
    }

    /**
     * 서비스 내용을 가져온다.
     * @return String 서비스 내용
     */
    public String getOpenApiSvcCn(){
        return openApiSvcCn;
    }

    /**
     * 수집일시를 가져온다.
     * @return Date 수집일시
     */
    public Date getCollectDt(){
        return collectDt;
    }

    /**
     * 순번을 저장한다.
     * 
     * @param sn
     */
    public void setSn(int sn){
        this.sn = sn;
    }

    /**
     * 회원ID를 저장한다.
     * 
     * @param mberId
     */
    public void setMberId(String mberId){
        this.mberId = mberId;
    }

    /**
     * OpenAPI 서비스명을 저장한다.
     * 
     * @param opnApiSvcNm
     */
    public void setOpenApiSvcNm(String openApiSvcNm){
        this.openApiSvcNm = openApiSvcNm;
    }

    /**
     * OpenAPI 제공 기관명을 저장한다.
     * 
     * @param openApiProvdInsttNm
     */
    public void setOpenApiProvdInsttNm(String openApiProvdInsttNm){
        this.openApiProvdInsttNm = openApiProvdInsttNm;
    }

    /**
     * 서비스 내용을 저장한다.
     * 
     * @param openApiSvcCn
     */
    public void setOpenApiSvcCn(String openApiSvcCn){
        this.openApiSvcCn = openApiSvcCn;
    }

    /**
     * 수집일시를 저장한다.
     * 
     * @param collectDt
     */
    public void setCollectDt(Date collectDt){
        this.collectDt = collectDt;
    }
}