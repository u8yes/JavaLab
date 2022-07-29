<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:scriptlet>

	pageContext.setAttribute("crlf", "\r\n");

</jsp:scriptlet>

<!DOCTYPE html>
<html>
	<head>

		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>도움말 상세조회</title>
		
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
				

        <script type="text/javaScript" language="javascript" defer="defer">
        </script>

	</head>
	
	<body>
		
		<!-- 게시판 View start -->
		<div id="view" data-role="page">
			
			<!-- header start -->
			<div data-role="header">
			    <a href="#list" data-icon="arrow-l">뒤로</a>
			    <h1 id="viewTitle">도움말 상세조회</h1>
			</div>
			<!-- header end -->

			<div data-role="content" class="com-copContent">
				<form:form commandName="result" name="detailForm">
					<!-- searchVO start -->
					<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
					<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
					<!-- searchVO end -->				
				<ul class="uss-hpcDetail">
					<li>
						<span class="uss-tit">도움말구분</span>
						<span class="uss-con">
							<label for="name-d"><c:out value="${result.hpcmSeCodeNm}"/></label><br>
						</span>
					</li>
					<li>
						<span class="uss-tit">도움말정의</span>
						<span class="uss-con"><label for="name-d">${fn:replace(result.hpcmDf , crlf , '<br>')}</label></span>
					</li>
					
					<li>
						<span class="uss-tit">도움말설명</span>
					</li>
					<li class="uss-contentsView"><label for="name-d">${fn:replace(result.hpcmDc , crlf , '<br>')}</label></li>
				
					<li>
						<span class="uss-tit">등록일자</span>
						<span class="uss-con"><label for="name-d"><c:out value="${result.frstRegisterPnttm}"/></label></span>
					</li>
				</ul>
				<fieldset style="clear:both">
					<div class="com-addBgBtn">
	            		<a href="javascript:fn_show_page();" data-role="button" data-theme="b">목록</a>
					</div>
				</fieldset>
				</form:form>					
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
						
		</div>
		<!-- 게시판 View end -->	
							
	</body>
	
</html>
