package egovframework.mbl.com.rns.service;

import java.io.Serializable;

/**
 * 개요
 * - 실시간 공지 서비스에 대한 VO 클래스를 정의한다.
 * 
 * 상세내용
 * - 실시간 공지 서비스 정보를 조회하기 위해 필요한 정보를 관리한다.
 * @author 조준형
 * @since 2011.08.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.10  조준형          최초 생성
 *
 * </pre>
 */

@SuppressWarnings("serial")
public class RealtimeNoticeVO extends RealtimeNotice implements Serializable {
	/**
	 * 회원ID
	 */
	private String mberId = "";
	
	/**
	 * 일련번호
	 */	
	private String searchSn = "";
	
	/**
	 * 목록 조회 갯수
	 */
	private int fetchRow = 0;
	
	/**
	 * 실시간 공지 서비스 목록 조회 갯수를 획득한다.
	 * 
	 * @return int
	 */
	public int getFetchRow() {
		return fetchRow;
	}
	
	/**
	 * 실시간 공지 서비스 목록 조회 갯수를 할당한다.
	 * 
	 * @param fetchRow
	 */
	public void setFetchRow(int fetchRow) {
		this.fetchRow = fetchRow;
	}

	/**
	 * 실시간 공지 서비스 회원ID를 획득한다.
	 * 
	 * @return String 회원ID
	 */
	public String getMberId() {
		return mberId;
	}

	/**
	 * 실시간 공지 서비스 회원ID를 할당한다.
	 * 
	 * @param mberId the mberId to set
	 */
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	/**
	 * 실시간 공지 서비스 일련번호를 획득한다.
	 * 
	 * @return String 일련번호
	 */
	public String getSearchSn() {
		return searchSn;
	}

	/**
	 * 실시간 공지 서비스 일변로호를 할당한다.
	 * 
	 * @param searchSn
	 */
	public void setSsearchSn(String searchSn) {
		this.searchSn = searchSn;
	}
	
}
