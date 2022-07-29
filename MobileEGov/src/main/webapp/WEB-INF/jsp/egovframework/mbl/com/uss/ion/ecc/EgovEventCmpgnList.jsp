<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행사/이벤트/캠페인 목록</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	
        
		   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<!-- datebox  import-->        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="eventCmpgnVO" staticJavascript="false" xhtml="true" cdata="false"/>
		
		<script type="text/javaScript" language="javascript">
			<!--		


			/* ********************************************************
			 * 페이징 처리 함수
			 ******************************************************** */
			function linkPage(pageNo){
				
				if(document.subForm.pageIndex.value == pageNo) {
					return;
				}
				
				document.subForm.pageIndex.value = pageNo;
				document.subForm.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo";
			   	document.subForm.submit();
			}
			/* ********************************************************
			 * 상세회면 처리 함수
			 ******************************************************** */
			 function fn_egov_detail_EventCmpgn(eventId){
					var vFrom = document.searchVO;
					vFrom.eventId.value = eventId;
					vFrom.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnDetail.mdo";
					vFrom.submit();
			}
				
			/* ********************************************************
			 * 검색 함수
			 ******************************************************** */
			function fn_egov_search_EventCmpgn(pageIndex){
				var vFrom = document.searchVO;
				document.searchVO.pageIndex.value = pageIndex;
				vFrom.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo";
				vFrom.submit();
				
			}

			
        	-->
        	
		</script>
				
	</head>
	
	<body>
	
		<!-- 행사/이벤트/캠패인 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>행사/이벤트/캠페인 목록</h1>
			    <a href="${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnRegist.mdo" data-icon="arrow-left" rel="external">등록</a>
			</div>
			<!-- header end -->
			
			
			
			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div id="searchview">
						<div class="uss-Search">						
							<input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex }"/>
							<input type="hidden" id="eventId" name="eventId"/>
							<select id="searchCondition" name="searchCondition" data-role="none">
							   <option value='EVENT_CN' <c:if test="${searchCondition == 'EVENT_CN'}">selected</c:if>>행사내용</option>
							   <option value='FRST_REGISTER_ID' <c:if test="${searchCondition == 'FRST_REGISTER_ID'}">selected</c:if>>작성자</option>
						    </select>
			               	<div class="uss-SearchBox">
				                <input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword }" data-role="none"/>
							</div>
				            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:fn_egov_search_EventCmpgn(1);" class="uss-SearchBtn" data-role="none"/>						
						</div>
					</div>
				</form>
				
				<ul data-role="listview">
					
					<c:choose>
						<c:when test="${empty resultList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="resultInfo" items="${resultList}">
								<li>
									<a href="javascript:fn_egov_detail_EventCmpgn('${resultInfo.eventId}')" data-ajax="false">
										<h3> ${resultInfo.eventCn }  </h3>
										<p> 
											<span class="uss-txtBlue">
												<c:forEach items="${comCode035}" var="comCodeList" varStatus="status">
													<c:if test="${comCodeList.code eq fn:trim(resultInfo.eventTyCode)}">	
														${comCodeList.codeNm}
													</c:if>
												</c:forEach>
											</span>
											<span class="uss-txtDate"> ${resultInfo.eventSvcBeginDe} ~ ${resultInfo.eventSvcEndDe} </span>
										</p>
										<p>
											<span class="uss-txtBlack"> ${resultInfo.frstRegisterNm } </span>
											<span class="uss-txtDate"> ${resultInfo.eventSvcBeginDe}</span>
										</p>
									</a>
								</li>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
					
				</ul>
	
				
				<div id="pageNavi" class="com-egovPaging">
					<ui:pagination paginationInfo = "${paginationInfo}" type="mblImage" jsFunction="linkPage"/>
				</div>
				
			</div>
			<!-- contents end -->
			<form name="subForm" method="Post"> 
				<input type="hidden" name="eventId" value="<c:out value='${resultInfo.eventId}'/>"/>
				<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			</form>
			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 행사/이벤트/캠패인 List end -->		
		
	</body>
	
</html>
