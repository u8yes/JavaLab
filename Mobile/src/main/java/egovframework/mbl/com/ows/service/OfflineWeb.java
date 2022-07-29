package egovframework.mbl.com.ows.service;
import java.io.Serializable;
/**
 * 개요
 * - 오프라인웹 서비스에 대한 Model 클래스를 정의한다.
 * 
 * 상세내용
 * - 오프라인웹 서비스 정보를 관리한다.
 * @author 조준형
 * @since 2011.09.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.09.01  조준형          최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class OfflineWeb implements Serializable {

	/**
	 * 오프라인웹 서비스 일련번호
	 */
	private int sn = 0;
	
	/**
	 * 오프라인웹 서비스 회원ID
	 */
	private String mberId = "";
	
	/**
	 * 오프라인웹 서비스 제목
	 */
	private String offlineWebSj = "";
	
	/**
	 * 오프라인웹 서비스 내용
	 */
	private String offlineWebCn = "";
		
	/**
	 * 오프라인웹 서비스 생성일시 
	 */	
	private String creatDt = "";
	
	/**
	 * 오프라인웹 서비스 수정일시 
	 */
	private String updtDt = "";	
	
	/**
	 * 오프라인웹 서비스 일련번호를 획득한다.
	 * 
	 * @return int 일련번호 
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * 오프라인웹 서비스 일련번호를 할당한다.
	 * 
	 * @param sn 일련번호
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}
	
	/**
	 * 오프라인웹 서비스 회원ID를 획득한다.
	 * 
	 * @return int 회원ID 
	 */
	public String getMberId() {
		return mberId;
	}
	
	/**
	 * 오프라인웹 서비스 회원ID를 할당한다.
	 * 
	 * @param mberId 회원ID 
	 */
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	/**
	 * 오프라인웹 서비스 글 제목을 획득한다.
	 * 
	 * @return String 글 제목 
	 */
	public String getOfflineWebSj() {
		return offlineWebSj;
	}
	
	/**
	 * 오프라인웹 서비스 글 제목을 할당한다.
	 * 
	 * @param offlineWebSj 글 제목 
	 */
	public void setOfflineWebSj(String offlineWebSj) {
		this.offlineWebSj = offlineWebSj;
	}
	
	/**
	 * 오프라인웹 서비스 글 내용을 획득한다.
	 * 
	 * @return String 글 내용 
	 */
	public String getOfflineWebCn() {
		return offlineWebCn;
	}

	/**
	 * 오프라인웹 서비스 글 내용을 할당한다.
	 * 
	 * @param offlineWebCn 글 내용 
	 */
	public void setOfflineWebCn(String offlineWebCn) {
		this.offlineWebCn = offlineWebCn;
	}
	
	/**
	 * 오프라인웹 서비스 등록일자를 획득한다.
	 * 
	 * @return String 등록일자 
	 */
	public String getCreatDt() {
		return creatDt;
	}
	
	/**
	 * 오프라인웹 서비스 등록일자를 할당한다.
	 * 
	 * @param creatDt 등록일자 
	 */
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	
	/**
	 * 오프라인웹 서비스 수정일자를 획득한다.
	 * 
	 * @return String 수정일자 
	 */
	public String getUpdtDt() {
		return updtDt;
	}

	/**
	 * 오프라인웹 서비스 수정일자를 할당한다.
	 * 
	 * @param updtDt 수정일자
	 */
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
}
