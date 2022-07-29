package egovframework.mbl.com.mgr.service;

/**
 * @Class Name : MenuVO.java
 * @Description : Menu VO class
 * @Modification Information
 * 
 *   수정일          수정자              수정내용
 *  ----------		--------	---------------------------
 *  2014.09.11		 표준프레임워크              최초 생성
 *
 *  @author 표준프레임워크
 *  @since 2014.09.11
 *  @version 1.0
 *  
 */

public class MenuVO {

	/** 메뉴코드 */
	private String menuCode;
	/** 메뉴명 */
	private String menuNm;
	/** 메뉴레벨 */
	private int menuLvl;
	/** 연결 URL */
	private String cnncUrl;
	/** 설명 */
	private String menuDc;
	/** 사용여부 */
	private Boolean actvtyAt;
	/** 상위 메뉴 */
	private String upperMenuId;
	/** 상위 메뉴 이름 */
	private String upperMenuNm;
	/** 최초등록일 */
	private String frstRegistPnttm;

	/** 최초등록자아이디 */
	private String frstRegisterId;

	/** 최종수정일 */
	private String lastUpdtPnttm;

	/** 최종수정자아이디 */
	private String lastUpdusrId;

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public int getMenuLvl() {
		return menuLvl;
	}

	public void setMenuLvl(int menuLvl) {
		this.menuLvl = menuLvl;
	}

	public String getCnncUrl() {
		return cnncUrl;
	}

	public void setCnncUrl(String cnncUrl) {
		this.cnncUrl = cnncUrl;
	}

	public String getMenuDc() {
		return menuDc;
	}

	public void setMenuDc(String menuDc) {
		this.menuDc = menuDc;
	}

	public Boolean getActvtyAt() {
		return actvtyAt;
	}

	public void setActvtyAt(Boolean actvtyAt) {
		this.actvtyAt = actvtyAt;
	}

	public String getUpperMenuId() {
		return upperMenuId;
	}

	public void setUpperMenuId(String upperMenuId) {
		this.upperMenuId = upperMenuId;
	}

	public String getUpperMenuNm() {
		return upperMenuNm;
	}

	public void setUpperMenuNm(String upperMenuNm) {
		this.upperMenuNm = upperMenuNm;
	}

	/**
	 * @param frstRegistPnttm the frstRegistPnttm to set
	 */
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	/**
	 * @return the frstRegistPnttm
	 */
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	/**
	 * @param frstRegisterId the frstRegisterId to set
	 */
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	/**
	 * @return the frstRegisterId
	 */
	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	/**
	 * @param lastUpdtPnttm the lastUpdtPnttm to set
	 */
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

	/**
	 * @return the lastUpdtPnttm
	 */
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	/**
	 * @param lastUpdusrId the lastUpdusrId to set
	 */
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

	/**
	 * @return the lastUpdusrId
	 */
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

}
