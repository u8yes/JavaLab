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
		<title>승인(탈퇴) 요청</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
				
	</head>
	
	<body>
		
		<!-- View start -->
		<div id="view" data-role="page">
						
				<!-- header start -->
				<div data-role="header">
				    <h1 id="viewTitle">승인(탈퇴) 요청</h1>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
				<div data-role="content" class="com-msgContent">
					<c:choose>
						<c:when test="${returnMsg==''}">
							<spring:message code="cop.request.msg"/>
						</c:when>
						<c:when test="${returnMsg=='DEL_REQ_SUCCESS'}">
							<spring:message code="success.request.msg"/>
						</c:when>
						<c:when test="${returnMsg=='ING'}">
							<spring:message code="cop.ing.msg"/>
						</c:when>
						<c:otherwise>
							<spring:message code="common.isExist.msg"/>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- contents end -->
				
				<!-- footer start -->
				<div data-role="footer" data-position="fixed">
					<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
				</div>
				<!-- footer end -->
						
		</div>
		<!-- view end -->
		
	</body>
	
</html>