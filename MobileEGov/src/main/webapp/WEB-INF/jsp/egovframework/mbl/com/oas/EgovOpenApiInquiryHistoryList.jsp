<% 
/**
 * @Class Name : EgovOpenApiInquiryHistoryList.jsp
 * @Description : Open-API 조회 이력 목록조회
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.09   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.09
 *  @version 1.0 
 *  @see
 *  
 */
%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Open-API 조회 이력 목록조회</title>
		
		<link href="<c:url value='/css/egovframework/com/cmm/com.css'/>" rel="stylesheet" type="text/css">
		<link href="<c:url value='/css/egovframework/com/cmm/button.css'/>" rel="stylesheet" type="text/css">
		
		<!-- 신규공통컴포넌트 import -->
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>
		<script type="text/javascript">
			
			function press(event) {
				if (event.keyCode==13) {
					fn_egov_select_openApi('1');
				}
			}
			
			function fn_egov_select_openApi(pageNo) {
				document.frm.pageIndex.value = pageNo;
				document.frm.action = "<c:url value='/mbl/com/oas/selectOpenApiInquiryHistoryList.mdo'/>";
				document.frm.submit();
			}
			
			function fn_egov_inqire_openApi(sn) {
				document.frm.sn.value = sn;
				document.frm.action = "<c:url value='/mbl/com/oas/selectOpenApiInquiryHistory.mdo'/>";
				document.frm.submit();
			}
		</script>
		
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
			<a href="<c:url value='/mbl/com/oas/selectOpenApiInquiryHistoryList.mdo'/>"><span class="btnBack"></span></a>
		</div>
		<!-- header End -->
		
		<div id="openApi" class="contents2">
			<form name="frm" method="post" action="<c:url value='/mbl/com/oas/selectOpenApiInquiryHistoryList.mdo'/>">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="sn" type="hidden" value="0">
			
				<table width="100%" cellpadding="8" class="table-search" border="0">
					<tbody>
						<tr>
					  		<td width="40%" class="title_left">
					   			<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">&nbsp;Open-API 조회 이력 목록조회
					   		</td>
							<td width="10%" >
					   			<select name="searchCondition" class="select">
									<option value=''>--선택하세요--</option>
									<option value="0" <c:if test="${searchVO.searchCondition == '0'}">selected="selected"</c:if> >Open-API 제공기관</option>
									<option value="1" <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >Open-API 서비스 구분</option>
						   		</select>
							</td>
					  		<td width="35%">
					    		<input name="searchKeyword" type="text" size="35" value='<c:out value="${searchVO.searchKeyword}"/>' maxlength="35" onkeypress="press(event);"> 
					  		</td>
					  		<th width="10%">
					   			<table border="0" cellspacing="0" cellpadding="0">
					    			<tr>
					      				<td><span class="button"><input type="submit" value="조회" onclick="javascript:fn_egov_select_openApi('1'); return false;"></span></td>
					    			</tr>
					   			</table>
					  		</th>  
						</tr>
					</tbody>
				</table>
					
				<table width="100%" cellpadding="8" class="table-list" summary="Open-API 조회 이력에 대한 목록을 제공합니다.">
					<thead>
				  		<tr>
				    		<th class="title" width="10%" nowrap>번호</th>
				    		<th class="title" width="35%" nowrap>Open-API 제공기관</th>
				    		<th class="title" width="35%" nowrap>Open-API 서비스 구분</th>
				    		<th class="title" width="20%" nowrap>수집일시</th>   
				  		</tr>
				 	</thead>    
				 	<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
					  		<tr>
					    		<td class="lt_text3" nowrap><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>	
					    		<td class="lt_text3" nowrap>
					    			<a href="javascript:fn_egov_inqire_openApi('<c:out value="${result.sn}"/>')">
					    				<c:out value="${result.openApiProvdInsttNm}"/>
					    			</a>
					    		</td>
					    		<td class="lt_text3" nowrap><c:out value="${result.openApiSvcNm}"/></td>
					    		<td class="lt_text3" nowrap><fmt:formatDate value="${result.collectDt}" pattern="yyyy-MM-dd"/></td>
					  		</tr>
					 	</c:forEach>	  
					 	<c:if test="${fn:length(resultList) == 0}">
					  		<tr>
					    		<td class="lt_text3" nowrap colspan="4"><spring:message code="common.nodata.msg"/></td>  
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
					<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_select_openApi"/>
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