<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행정전문용어사전상세보기 </title>
		
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
					<a href="#list" data-icon="arrow-l">뒤로</a>
				    <h1 id="viewTitle">행정용어사전상세</h1>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
				<div data-role="content" class="com-copContent">
					<form:form commandName="administrationWordManage" name="detailForm" method="post">
						
						<!-- searchVO start -->
						<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
						<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
						<form:hidden path="pageIndex" value="${searchVO.pageIndex}"/>
						<form:hidden path="choseongA" value="${searchVO.choseongA}"/>
						<form:hidden path="choseongB" value="${searchVO.choseongB}"/>
						<form:hidden path="cmd" id="cmd" value="${searchVO.cmd}"/>
						<!-- searchVO end -->
						
						<form:hidden path="administWordId" value=""/>
						<ul class="uss-hpcDetail">
								<li>	
									<span class="uss-tit">행정용어명</span>
									<span class="uss-con">
							        	<label for="name-d">
							        		<c:out value="${administrationWordManage.administWordNm}"/>
							        	</label>
									</span>
								</li>
								
								<li>
									<span class="uss-tit">행정용어영문명</span>
									<span class="uss-con">
										<label for="name-d">
											<c:out value="${administrationWordManage.administWordEngNm}"/>
										</label>
									</span>
								</li>
								
								<li>
									<span class="uss-tit">행정용어약어명</span>
									<span class="uss-con">
										<label for="name-d">
											<c:out value="${administrationWordManage.administWordAbrv}"/>
										</label>
									</span>
								</li>		
								
								<li>			
									<span class="uss-tit">주제영역 </span>
									<span class="uss-con">
										<label for="name-d">
											<c:out value="${administrationWordManage.themaRelm}"/>
										</label>
									</span>
								</li>
								
								<li>			
									<span class="uss-tit">용어구분</span>
									<span class="uss-con">
										<label for="name-d">
											<c:if test="${administrationWordManage.wordDomn == '1'}">표준어</c:if>
    										<c:if test="${administrationWordManage.wordDomn == '2'}">동의어</c:if>
										</label>
									</span>
								</li>

								<li>
									<span class="uss-tit">행정용어정의</span>
									<span class="uss-con">
										<label for="name-d">
											<c:out value="${administrationWordManage.administWordDf}"/>
										</label>
									</span>
								</li>
								
								<li>			
									<span class="uss-tit">행정용어설명</span>
								</li>
								<li class="uss-contentsView">
									<label for="name-d"><c:out value="${administrationWordManage.administWordDc}"/></label>
								</li>
							
						</ul>
						
						<fieldset style="clear:both">
							<div class="com-addBgBtn">
								<a href='javascript:fn_show_page();' data-role="button" data-theme="b">목록</a>			
							</div>							
						</fieldset>
						
					</form:form>
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

