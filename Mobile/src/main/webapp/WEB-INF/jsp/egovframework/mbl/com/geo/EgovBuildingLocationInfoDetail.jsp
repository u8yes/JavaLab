<% 
/**
 * @Class Name : EgovBuildingLocationInfoDetail.jsp
 * @Description : 건물 위치정보 상세조회
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.19   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.19
 *  @version 1.0 
 *  @see
 *  
 */
%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/cmm/com.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css'/>" rel="stylesheet" type="text/css">

<!-- 신규공통컴포넌트 import -->
<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript">

	function fn_egov_select_geoLocationList(pageNo) {
		document.frm.action = "<c:url value='/mbl/com/geo/selectBuildingLocationInfoList.mdo'/>";
		document.frm.submit();
	}

	function fn_egov_updt_geoLocation(sn){
	    document.frm.sn.value = sn;  
	    document.frm.action = "<c:url value='/mbl/com/geo/goBuildingLocationInfoUpdt.mdo'/>";
	    document.frm.submit();   
	}

	function fn_egov_delete_geoLocation(sn){
	    if (confirm("<spring:message code="common.delete.msg"/>"))    {   
	        document.frm.sn.value = sn;
	        document.frm.action = "<c:url value='/mbl/com/geo/deleteBuildingLocationInfo.mdo'/>";
	        document.frm.submit();
    	}
	}
</script>
<title>건물 위치 정보 상세조회</title>

<style type="text/css">
	h1 {font-size:12px;}
	caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}
</style>

</head>
<body>

<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	
	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/geo/selectBuildingLocationInfoList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="openApi" class="contents2">
	
		<table width="100%" cellpadding="8" class="table-search" border="0">
		 <tr>
		  <td width="100%"class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">
		   &nbsp;건물 위치 정보 - 상세 조회</td>
		 </tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="건물 위치 정보에 대한 상세정보를 제공합니다.">
		<tbody>
		  <tr> 
		    <th width="20%" height="23" nowrap >건물명&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${geoLocation.buldNm}"/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >전화번호&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${geoLocation.telno}"/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >주소&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${geoLocation.adres}"/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >위도&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${geoLocation.la}"/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >경도&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${geoLocation.lo}"/>
		    </td>
		  </tr>
		</tbody>
		</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
		    <td height="10"></td>
		  </tr>
		</table>
	  	<div align="center">
	  	<form name="frm" method="post" action="<c:url value='/mbl/com/geo/selectBuildingLocationInfoList.mdo'/>">
		<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
	      <td>
	      	<input name="sn" type="hidden" value="0">
	      	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	      	<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>">
	      	<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>">
	        <span class="button">
	        <a href="<c:url value='/mbl/com/geo/updateBuildingLocationInfo.mdo'/>"
	           onclick="fn_egov_updt_geoLocation('<c:out value="${geoLocation.sn}"/>'); return false;"><spring:message code="button.update"/></a>
	        </span> 
		  </td>
		  <td><span class="button"><input type="submit" value="<spring:message code="button.delete"/>" onclick="fn_egov_delete_geoLocation('<c:out value="${geoLocation.sn}"/>'); return false;"></span></td>
		  <td>
		  	<span class="button">
		    <a href="<c:url value='/mbl/com/geo/selectBuildingLocationInfoList.mdo'/>" onclick="fn_egov_select_geoLocationList('<c:out value='${searchVO.pageIndex}'/>'); return false;"><spring:message code="button.list"/></a>
		    </span> 
		  </td>
		</tr>
		</table>
		</form>
		</div>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>