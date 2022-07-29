<%
/**
 * @Class Name : EgovDeviceIdentDetail.jsp
 * @Description : EgovDeviceIdentDetail 화면
 * @Modification Information
 * @
 * @  수정일   	수정자		수정내용
 * @ ----------	------		---------------------------
 * @ 2011.08.19	정홍규		최초 생성
 *
 *  @author 정홍규 
 *  @since 2011.08.19
 *  @version 1.0
 *  @see
 *  
 */
%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>차트/그래프 데이터 상세 조회</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javaScript" language="javascript">
	/* ********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_initl_deviceIdent(){
	
	}
	
	/* ********************************************************
	 * 목록 으로 가기
	 ******************************************************** */
	function fn_egov_inqire_deviceIdentlist() {
	
	    document.DeviceIdentForm.action = "<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>";
	    document.DeviceIdentForm.submit();
	}
	
	/* ********************************************************
	 * 수정처리화면
	 ******************************************************** */
	function fn_egov_updt_deviceIdent(sn){
	
	    // Update하기 위한 키값(sn)을 셋팅
	    document.DeviceIdentForm.sn.value = sn;  
	    document.DeviceIdentForm.action = "<c:url value='/mbl/com/mdi/goDeviceIdentUpdt.mdo'/>";
	    document.DeviceIdentForm.submit();   
	}

	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
	function fn_egov_delete_deviceIdent(sn){
	
	    if  (confirm("<spring:message code="common.delete.msg"/>"))    {   
	
	        // Delete하기 위한 키값(newsId)을 셋팅
	        document.DeviceIdentForm.sn.value = sn;  
	        document.DeviceIdentForm.action = "<c:url value='/mbl/com/mdi/deleteDeviceIdent.mdo'/>";
	        document.DeviceIdentForm.submit();
	    }    
	}
</script>
</head>

<body onLoad="fn_egov_initl_deviceIdent();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents">
		
		<!-- 상단타이틀 -->
		<form name="DeviceIdentForm" action="<c:url value='/mbl/com/mdi/selectDeviceIdent.mdo'/>" method="post">
			
			<!-- 상단 타이틀  영역 -->
			<table width="100%" cellpadding="8" class="table-search" border="0">
			 <tr>
			  <td width="100%"class="title_left">
			   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀">&nbsp;모바일 기기 식별 정보 상세 조회</td>
			 </tr>
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="3px"></td>
			</tr>
			</table>
			
			<!-- 등록  폼 영역  -->
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="모바일 기기 식별 정보테이블.">
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >브라우저&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			        <c:out value="${result.browserNm}"/>  
			    </td>
			  </tr>
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >운영체제&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			    	<c:out value="${result.osNm}"/>
			    </td>
			  </tr>
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >User-Agent&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			    	<c:out value="${result.uagentInfo}"/>
			    </td>
			  </tr>
			  <tr> 
			    <th height="23" class="required_text" nowrap >등록상태&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			        <c:out value="${result.recentNm}"/>
			    </td>
			  </tr>
			  <tr> 
			    <th height="23" class="required_text" nowrap >생성일시&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			        <c:out value="${result.creatDt}"/>
			    </td>
			  </tr> 
			
			   
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="10px"></td>
			</tr>
			</table>
			
			<!-- 목록/저장버튼  -->
			<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr> 
			    <td>
			        <span class="button">
			        <a href="<c:url value='/mbl/com/mdi/updateDeviceIdent.mdo'/>"
			           onclick="fn_egov_updt_deviceIdent('<c:out value="${result.sn}"/>'); return false;"><spring:message code="button.update"/></a>
			        </span> 
			    </td>
			    <td><span class="button"><input type="submit" value="<spring:message code="button.delete"/>" onclick="fn_egov_delete_deviceIdent('<c:out value="${result.sn}"/>'); return false;"></span></td>
			    <td>
			        <span class="button">
			        <a href="<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>"
			           onclick="fn_egov_inqire_deviceIdentlist(); return false;"><spring:message code="button.list"/></a>
			        </span> 
			    </td>
			</tr>
			</table>
			
			<input name="sn" type="hidden" value="0">
		</form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>