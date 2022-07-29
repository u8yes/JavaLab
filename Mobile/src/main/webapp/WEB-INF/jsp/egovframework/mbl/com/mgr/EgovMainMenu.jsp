<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  Class Name : EgovMainMenu.jsp
  Description :  menu list
  Modification Information

       수정일         수정자           수정내용
    -------------    --------    ---------------------
     2014.09.11      표준프레임워크         최초 생성
 
    author   : 표준프레임워크
    since    : 2014.09.11
--%>
<!DOCTYPE html> 
<html> 
  <head> 
        <title>eGovframe Mobile Framework</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  
        <!-- eGovFrame Common import -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
   
        <!-- 개발환경 import -->
        <link href="<c:url value='/css/egovframework/mbl/com/mgr/egovMgr.css' />" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript">

	        function fn_egov_forward_upperMenuId() {

				document.menuVO.action = "<c:url value='/mgr/EgovMenuList.mdo' />";
				document.menuVO.submit();
			}
        
			function fn_egov_forward_menu(cnncUrl) {
				
				//document.menuVO.action = "<c:url value='"+cnncUrl+"'/>";
				document.menuVO.action = "${pageContext.request.contextPath}"+cnncUrl;
				document.menuVO.submit();
			}
		</script>
	</head>
	
<body> 
<div data-role="page">
	<div data-role="header" data-position="inline" data-theme="z">
	   <h1><img src="<c:url value='/images/egovframework/mbl/bod/logo.png' />"  /></h1>
	</div>
	<form name="menuVO" method="post" action="">
       <input type="hidden" name="menuCode" value="<c:out value='${result.menuCode}'/>" >
        <input type="hidden" name="menuNm" value="<c:out value='${result.menuNm}'/>" >
   
        <input type="hidden" name="cnncUrl" value="<c:out value='${result.cnncUrl}'/>" >
        <input type="hidden" name="menuDc" value="<c:out value='${result.menuDc}'/>" >
        <input type="hidden" name="acyvtyAt" value="<c:out value='${result.acyvtyAt}'/>" >
        <input type="hidden" name="upperMenuId" value="<c:out value='${result.upperMenuId}'/>" >
		<div data-role="content" data-theme="d">	
					
			<div class="content-primary">
				<ul data-role="listview" data-inset="true" >
					<li data-role="list-divider" class="egov-bar-main3"><spring:message code="mgr.mobileEgov" /></li>
					<li><p>모바일 전자정부 공통컴포넌트 메뉴</p></li>
				</ul>				
					<div data-role="collapsible-set">
					<% int cnt=0; %>
					<c:forEach var="result" items="${resultList}" varStatus="status">
	
						<c:if test="${result.menuLvl == '1'}">
						<% cnt++; 
						   if(cnt != 1){
						%>
							</div> <!-- collapsible Lv1 -->
						<%
						   }
						%>
						<div data-role="collapsible" data-collapsed="true" style="width:95%; margin-left:auto; margin-right:auto;" >
							<h3><c:out value="${result.menuNm}"/></h3>
							 <ul data-role="listview" data-inset="true" data-theme="d"> 
						</c:if>
								
						<c:if test="${result.menuLvl == '2'}">
							<li>
								<a href="javascript:fn_egov_forward_menu('${result.cnncUrl}');" data-ajax="false">
								<c:out value="${result.menuNm}"/>
								</a>
							</li>
						</c:if>
							
					</c:forEach>
							</ul>
					</div>
					
					<c:if test="${loginVO.userSe == 'USR'}">
					<div data-role="collapsible" data-collapsed="true" style="width:95%; margin-left:auto; margin-right:auto;" >
					<h3><spring:message code="mgr.menuMngt" /></h3>
						<ul data-role="listview" data-inset="true" data-theme="d">
						<% int count=0; %>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<c:if test="${result.menuLvl != '1'}">
							<% count++; %>
							</c:if>
						</c:forEach>
							<li><a href="javascript:fn_egov_forward_upperMenuId();" data-ajax="false"><spring:message code="mgr.menuList" /><span class="ui-li-count"><%=count %></span></a></li>
						</ul>
					</div>
					</c:if>

			</div><!--/content-primary-->	
		</div>

	</form>
	
	<!-- footer start -->
	<div data-role="footer" data-position="fixed">
		<h1>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h1>
	</div>
	<!-- footer end -->
	
</div>

</body>
</html>