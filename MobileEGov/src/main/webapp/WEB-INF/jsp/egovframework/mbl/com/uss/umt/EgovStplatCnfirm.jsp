<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>

		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>약관 확인</title>
		
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
				

	</head>
	
	<body>
		
		<!-- 게시판 View start -->
		<div id="view" data-role="page">
			
			<!-- header start -->
			<div data-role="header">
			    <a href="#list" data-icon="arrow-l">뒤로</a>
			    <h1 id="viewTitle">이용약관 확인</h1>
			</div>
			<!-- header end -->

			<div data-role="content" class="com-copContent">
				<form:form commandName="stplatList" name="stplatForm">
					<c:forEach var="result" items="${stplatList}" varStatus="status">
					<dl class="uss-stpDetail">
						<dt><strong>이용약관 내용</strong></dt>
						<dd><label for="name-d"><c:out value="${result.useStplatCn}"/></label></dd>
						<dd class="uss-stpBtn">
							<fieldset data-role="controlgroup">
								<input type="checkbox" name="checkField" id="checkField1" class="custom"/>
								<label for="checkField1">약관 내용에 동의합니다</label>
								<input name="checkuseStplatCn" type="hidden" value="<c:out value='${result.useStplatId}'/>"/>
							</fieldset>
						</dd>
						<dt><strong>정보제공동의내용</strong></dt>
						<dd><label for="name-d"><c:out value="${result.infoProvdAgeCn}"/></label></dd>
						<dd class="uss-stpBtn">
							<fieldset data-role="controlgroup">
								<input type="checkbox" name="checkField" id="checkField2" class="custom"/>
								<label for="checkField2">정보제공동의내용에 동의합니다</label>
								<input name="checkinfoProvdAgeCn" type="hidden" value="<c:out value='${result.useStplatId}'/>"/>
							</fieldset>
						</dd>
					</dl>
					<div class="ui-grid-a">
						<div class="ui-block-a"><a href="javascript:fnAgree();" data-role="button" data-theme="b">동의</a></div>
		            	<div class="ui-block-b"><a href="#list" data-role="button" data-theme="b">비동의</a></div>
					</div>
				</c:forEach>
				</form:form>					
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
						
		</div>
		<!-- 게시판 View end -->	
							
	</body>
	
</html>
