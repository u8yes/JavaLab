<% 
/**
 * @Class Name : EgovBuildingLocationInfoRegist.jsp
 * @Description : 건물 위치정보 등록
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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/cmm/com.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css'/>" rel="stylesheet" type="text/css">

<!-- 신규공통컴포넌트 import -->
<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="geoLocation" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">

	function fn_egov_select_geoLocationList(pageNo) {
		document.geoLocation.action = "<c:url value='/mbl/com/geo/selectBuildingLocationInfoList.mdo'/>";
		document.geoLocation.submit();
	}

	function fn_egov_regist_geoLocation(){
		if(!validateGeoLocation(document.geoLocation)) {
			return;
		}
		
	    document.geoLocation.action = "<c:url value='/mbl/com/geo/insertBuildingLocationInfo.mdo'/>";
	    document.geoLocation.submit();
	    
	}
</script>
<title>건물 위치 정보 등록</title>

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
	
	<div id="content" class="contents2">
		<form commandName="geoLocation" name="geoLocation" method="post">
		<table width="100%" cellpadding="8" class="table-search" border="0">
		 <tr>
		  <td width="100%"class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">
		   &nbsp;건물 위치 정보 - 등록</td>
		 </tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="건물 위치 정보를 등록합니다.">
		<tbody>
		  <tr> 
		    <th width="20%" height="23" class="required_text" nowrap >건물명
		    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
		    </th>
		    <td width="80%" nowrap>
		    	<input name="buldNm" type="text" style="width:50%;height:100%;" value="">
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >전화번호&nbsp;</th>
		    <td width="80%" nowrap>
		    	<input name="telno" type="text" style="width:50%;height:100%;" value="">
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >주소&nbsp;</th>
		    <td width="80%" nowrap>
		    	<input name="adres" type="text" style="width:50%;height:100%;" value="">
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" class="required_text" nowrap >위도&nbsp;
		    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
		    </th>
		    <td width="80%" nowrap>
		    	<input name="la" type="text" style="width:50%;height:100%;" value="">
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" class="required_text" nowrap >경도&nbsp;
		    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
		    </th>
		    <td width="80%" nowrap>
		    	<input name="lo" type="text" style="width:50%;height:100%;" value="">
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
		<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr> 
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="등록버튼이미지">
				</td>
				<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
					<a href="JavaScript:fn_egov_regist_geoLocation();">등록</a>
				</td>
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="등록버튼이미지">
				</td>
				<td>&nbsp;</td>  
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="목록버튼이미지">
				</td>
				<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
					<a href="javascript:fn_egov_select_geoLocationList('<c:out value='${searchVO.pageIndex}'/>');">목록</a> 
					<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	      			<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>">
	      			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>">
				</td>
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="목록버튼이미지">
				</td>              
			</tr>
		</table>
		</div>
		</form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>