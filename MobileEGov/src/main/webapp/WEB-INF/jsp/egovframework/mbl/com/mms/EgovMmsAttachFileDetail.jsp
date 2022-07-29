<% 
/**
 * @Class Name : EgovMmsAttachFileDetail.jsp
 * @Description : 첨부파일 상세조회
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.29   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.29
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

	function fn_egov_select_mmsAttachFileList(pageNo) {
		document.frm.action = "<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>";
		document.frm.submit();
	}

	function fn_egov_updt_mmsAttachFile(sn){
	    document.frm.sn.value = sn;  
	    document.frm.action = "<c:url value='/mbl/com/mms/goMmsAttachFileUpdt.mdo'/>";
	    document.frm.submit(); 
	}

	function fn_egov_delete_mmsAttachFile(sn){
	    if (confirm("<spring:message code="common.delete.msg"/>"))    {   
	        document.frm.sn.value = sn;
	        document.frm.action = "<c:url value='/mbl/com/mms/deleteMmsAttachFile.mdo'/>";
	        document.frm.submit();
    	}
	}
</script>
<title>MMS 첨부파일 상세조회</title>

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
		<a href="<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="attachFile" class="contents2">
	
		<table width="100%" cellpadding="8" class="table-search" border="0">
		 <tr>
		  <td width="100%"class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">
		   &nbsp;MMS 첨부파일 - 상세 조회</td>
		 </tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="MMS 첨부파일에 대한 상세정보를 제공합니다.">
		<tbody>
		  <tr> 
		    <th width="20%" height="23" nowrap >제목&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${attachFile.mmsAtchFileSj}"/>
		    </td>
		  </tr>
		  <c:if test="${attachFile.atchFileId != ''}">
		  <tr>
		    <th width="20%" height="23" nowrap >파일명&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:import url="/cmm/fms/selectFileInfs.do">
	            	<c:param name="param_atchFileId" value="${attachFile.atchFileId}"/>
	            </c:import>
		    </td>
		  </tr>
		  </c:if>
		  <tr> 
		    <th width="20%" height="23" nowrap >생성일시&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${attachFile.creatDt}"/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" nowrap >수정일시&nbsp;</th>
		    <td width="80%" nowrap>
		    	<c:out value="${attachFile.updtDt}"/>
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
	  	<form name="frm" method="post" action="<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>">
		<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
	      <td>
	      	<input name="sn" type="hidden" value="0">
	      	<input name="atchFileId" type="hidden" value="<c:out value='${attachFile.atchFileId}'/>">
	      	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	      	<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>">
	      	<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>">
	        <span class="button">
	        <a href="<c:url value='/mbl/com/mms/updateMmsAttachFile.mdo'/>"
	           onclick="fn_egov_updt_mmsAttachFile('<c:out value="${attachFile.sn}"/>'); return false;"><spring:message code="button.update"/></a>
	        </span> 
		  </td>
		  <td><span class="button"><input type="submit" value="<spring:message code="button.delete"/>" onclick="fn_egov_delete_mmsAttachFile('<c:out value="${attachFile.sn}"/>'); return false;"></span></td>
		  <td>
		  	<span class="button">
		    <a href="<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>" onclick="fn_egov_select_mmsAttachFileList('<c:out value='${searchVO.pageIndex}'/>'); return false;"><spring:message code="button.list"/></a>
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