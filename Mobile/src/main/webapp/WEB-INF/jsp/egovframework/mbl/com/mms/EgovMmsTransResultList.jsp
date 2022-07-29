<% 
/**
 * @Class Name : EgovMmsTransResultList.jsp
 * @Description : MMS 전송 결과 목록조회
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.30   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.30
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
	
	function press(event) {
		if (event.keyCode==13) {
			fn_egov_select_mmsTransResultList('1');
		}
	}
	
	function fn_egov_select_mmsTransResultList(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/mbl/com/mms/selectMmsTransResultList.mdo'/>";
		document.frm.submit();
	}

	function fn_egov_get_trnsmisResult(messageId) {
		document.frm.mssageId.value = messageId;
		document.frm.action = "<c:url value='/mbl/com/mms/getMmsTransmissionResult.mdo'/>";
		document.frm.submit();
	}
	
</script>
<title>MMS 전송 결과 목록조회</title>

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
		<a href="<c:url value='/mbl/com/mms/selectMmsTransResultList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="attachFile" class="contents2">
	
		<form name="frm" method="post" action="<c:url value='/mbl/com/mms/selectMmsTransResultList.mdo'/>">
	
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
		<input type="hidden" name="sn" value="0">
		<input type="hidden" name="mssageId" value="">
		
		<table width="100%" cellpadding="8" class="table-search" border="0">
		<tbody>
		 <tr>
		  <td width="40%" class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">&nbsp;MMS 전송 결과 목록</td>
			<td width="10%" >
		   		<select name="searchCondition" class="select">
					<option value=''>--선택하세요--</option>
					<option value="0" <c:if test="${searchVO.searchCondition == '0'}">selected="selected"</c:if> >송신번호</option>
					<option value="1" <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >수신번호</option>
					<option value="2" <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >전송결과</option>
					<option value="3" <c:if test="${searchVO.searchCondition == '3'}">selected="selected"</c:if> >요청결과</option>
			   </select>
			</td>
		  <td width="35%">
		    <input name="searchKeyword" type="text" size="35" value='<c:out value="${searchVO.searchKeyword}"/>' maxlength="35" onkeypress="press(event);"> 
		  </td>
		  <th width="10%">
		   <table border="0" cellspacing="0" cellpadding="0">
		    <tr>
		      <td><span class="button"><input type="submit" value="조회" onclick="javascript:fn_egov_select_mmsTransResultList('1'); return false;"></span></td>
		      <td>&nbsp;&nbsp;</td>
		    </tr>
		   </table>
		  </th>  
		 </tr>
		</tbody>
		</table>
			
		<table width="100%" cellpadding="8" class="table-list" summary="MMS 전송 결과에 대한 목록을 제공합니다.">
		 <thead>
		  <tr>
		    <th class="title" width="5%" nowrap>번호</th>
		    <th class="title" width="15%" nowrap>송신번호</th>
		    <th class="title" width="15%" nowrap>수신번호</th>
		    <th class="title" width="10%" nowrap>요청결과</th>
		    <th class="title" width="15%" nowrap>전송결과</th>
		    <th class="title" width="15%" nowrap>전송일자</th>
		    <th class="title" width="15%" nowrap>전송확인일자</th>
		    <th class="title" width="10%" nowrap>결과확인</th>
		  </tr>
		 </thead>    
		 <tbody>
			 <c:forEach var="result" items="${resultList}" varStatus="status">
			  <tr>
			    <td class="lt_text3" nowrap><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>	
			    <td class="lt_text3" nowrap><c:out value="${result.trnsmisNo}"/></td>
			    <td class="lt_text3" nowrap><c:out value="${result.recptnNo}"/></td>
			    <td class="lt_text3" nowrap><c:out value="${result.rqesterResultNm}"/></td>
			    <td class="lt_text3" nowrap>
				    <c:if test="${result.trnsmisResultNm == null}">
				    	<c:if test="${result.rqesterResultNm == '요청실패'}">
			    			-
			    		</c:if>
			    		<c:if test="${result.rqesterResultNm == '요청성공'}">
			    			<c:out value="${result.trnsmisResultNm}"/>
			    		</c:if>
				    </c:if>
				    <c:if test="${result.trnsmisResultNm != null}">
			    		<c:out value="${result.trnsmisResultNm}"/>
				    </c:if>
			    </td>
			    <td class="lt_text3" nowrap><c:out value="${result.trnsmisDt}"/></td>
			    <td class="lt_text3" nowrap><c:out value="${result.trnsmisConfirmDt}"/></td>
			    <td class="lt_text3" nowrap>
		    		<c:if test="${result.rqesterResultNm == '요청성공'}">
		    			<c:if test="${result.trnsmisResultNm != '전송수신성공'}">
			    			<span class="button">
			    				<input type="button" value="확인" onclick="javascript:fn_egov_get_trnsmisResult('<c:out value="${result.mssageId}"/>');"/>
			    			</span>
			    		</c:if>
			    	</c:if>
			    </td>
			  </tr>
			 </c:forEach>	  
			 <c:if test="${fn:length(resultList) == 0}">
			  <tr>
			    <td class="lt_text3" nowrap colspan="8"><spring:message code="common.nodata.msg"/></td>  
			  </tr>		 
			 </c:if>
		 </tbody>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
		    <td height="10"></td>
		  </tr>
		</table>
		<div align="center">
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_select_mmsTransResultList"/>
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