package egovframework.mbl.com.mlt.service;

import java.io.Serializable;
import java.util.List;

/**
 * 개요
 * - 지원브라우저 정보를 담는 XML을 정의한다.
 *
 * 상세내용
 * - 지원브라우저 정보를 관리한다.
 * @author 정홍규
 * @since 2011.08.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.22  정홍규          최초 생성
 *
 * </pre>
 */

public class MultimediaFileInfo implements Serializable {

	private static final long serialVersionUID = 6516852118866931277L;

	/**
     * 파일 구분
     */
    private String classification;

    /**
     * 파일 확장자
     */
    private String extension;

    /**
     * 파일 타입
     */
    private String type;

    /**
     * 지원 브라우저
     */
    private List<String> browserList;

    /**
     * 파일 구분을 가져온다.
     * @return String
     */
    public String getClassification() {
        return classification;
    }

    /**
     * 파일 구분을 저장한다.
     * @param classification
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * 파일 확장자를 가져온다.
     * @return String
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 파일 확장자를 저장한다.
     * @param extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * 파일 타입을 가져온다.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * 파일 타입을 저장한다.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 지원 브라우저를 가져온다.
     * @return String
     */
    public List<String> getBrowserList() {
        return browserList;
    }

    /**
     * 지원 브라우저를 저장한다.
     * @param browserList
     */
    public void setBrowserList(List<String> browserList) {
        this.browserList = browserList;
    }
}
