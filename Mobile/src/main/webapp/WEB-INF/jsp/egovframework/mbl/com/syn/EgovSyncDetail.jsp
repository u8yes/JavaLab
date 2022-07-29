<%
/**
 * @Class Name : EgovSyncDetail.jsp
 * @Description : EgovSyncDetail 화면
 * @Modification Information
 * @
 * @  수정일   	수정자		수정내용
 * @ ----------	------		---------------------------
 * @ 2011.08.25	서형주		최초 생성
 *
 *  @author 서형주 
 *  @since 2011.08.25
 *  @version 1.0
 *  @see
 *  
 */
%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>동기화 서비스 상세 조회</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javaScript" language="javascript">
/* ********************************************************
 * 초기화
 ******************************************************** */
function fn_egov_initl_Sync(){

}

/* ********************************************************
 * 목록 으로 가기
 ******************************************************** */
function fn_egov_inqire_Synclist() {

    document.syncForm.action = "<c:url value='/mbl/com/syn/selectSyncList.mdo'/>";
    document.syncForm.submit();
        
}

/* ********************************************************
 * 수정처리화면
 ******************************************************** */
function fn_egov_updt_sync(sn){

    // Update하기 위한 키값(sn)을 셋팅
    document.syncForm.sn.value = sn;  
    document.syncForm.action = "<c:url value='/mbl/com/syn/goSyncUpdt.mdo'/>";
    document.syncForm.submit();   
    
}

/* ********************************************************
 * 삭제 처리
 ******************************************************** */
function fn_egov_delete_Sync(sn){
	
    if  (confirm("<spring:message code="common.delete.msg"/>"))    {   

        // Delete하기 위한 키값(newsId)을 셋팅
        document.syncForm.sn.value = sn;  
        document.syncForm.action = "<c:url value='/mbl/com/syn/deleteSync.mdo'/>";
        document.syncForm.submit();
    }    
    
}

</script>
</head>

<body onLoad="fn_egov_initl_Sync();">
	<!-- header Start -->
	<div id="header">
		<a href="/index.jsp"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/syn/selectSyncList.mdo'/>"
	           onclick="javascript:fn_egov_inqire_Synclist(); return false;"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents">
	
	<!-- 상단타이틀 -->
	<form name="syncForm" method="post">
	
	<!-- 상단 타이틀  영역 -->
	<table width="100%" cellpadding="8" class="table-search" border="0">
	 <tr>
	  <td width="100%"class="title_left">
	   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀">&nbsp;동기화 서비스 상세 조회</td>
	 </tr>
	</table>
	
	<!-- 줄간격조정  -->
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
	    <td height="3px"></td>
	</tr>
	</table>
	
	<!-- 등록  폼 영역  -->
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="동기화서비스 상세테이블.">
	  <tr> 
	    <th width="20%" height="23" class="required_text" nowrap >제목&nbsp;&nbsp;</th>
	    <td width="80%" nowrap>
	        <c:out value="${result.syncSj}"/>  
	    </td>
	  </tr>
	  <tr> 
	    <th width="20%" height="23" class="required_text" nowrap >작성자&nbsp;&nbsp;</th>
	    <td width="80%" nowrap>
	    	<c:out value="${result.mberId}"/>
	  </tr>
	  <tr> 
	    <th width="20%" height="23" class="required_text" nowrap >생성일시&nbsp;&nbsp;</th>
	    <td width="80%" nowrap>
	    	<c:out value="${result.creatDt}"/>
	  </tr>
	  <tr> 
	    <th height="70" class="required_text" nowrap >글내용&nbsp;&nbsp;</th>
	    <td width="80%" nowrap>
	        <c:out value="${result.syncCn}"/>
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
	
	    <td><span class="button"><input type="submit" value="<spring:message code="button.update"/>" 
	    	   onclick="fn_egov_updt_sync('<c:out value="${result.sn}"/>'); return false;"></span></td>
	    	   
	    <td><span class="button"><input type="submit" value="<spring:message code="button.delete"/>" 
	    	   onclick="fn_egov_delete_Sync('<c:out value="${result.sn}"/>'); return false;"></span></td>
	    <td>
	        <span class="button">
	        <a href="<c:url value='/mbl/com/syn/selectSyncList.mdo'/>"
	           onclick="fn_egov_inqire_Synclist(); return false;"><spring:message code="button.list"/></a>
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