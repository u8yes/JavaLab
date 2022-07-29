<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  Class Name : EgovMenuList.jsp
  Description :  menu management list
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
        <title><spring:message code="mgr.menuList" /></title> 
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <!-- eGovFrame Common import -->
        <link href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css' />" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css' />" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js' />"></script>
        <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js' />"></script>
   
        <!-- 개발환경 import -->
 		<link href="<c:url value='/css/egovframework/mbl/com/mgr/egovMgr.css' />" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript">

			function fn_egov_select_menuList(menuCode) {

				document.menuVO.action = "<c:url value='/mgr/EgovMenuListDetail.mdo'/>";
				document.menuVO.menuCode.value = menuCode;
				document.menuVO.submit();
			}

			function fn_egov_regist() {

				document.menuVO.action = "<c:url value='/mgr/EgovMenuRegistPage.mdo'/>";
				document.menuVO.submit();
			}

		    function fn_egov_back() {
	           
				document.menuVO.action = "<c:url value='/mgr/EgovMainMenu.mdo'/>";
				document.menuVO.submit();
			}
		</script>

	</head>
	
<body> 
	
<div data-role="page">
	<div data-role="header">
		<h1 class="bodLogo"><spring:message code="mgr.menuList" /></h1>
	 	<a href="javascript:fn_egov_back()" data-icon="arrow-l" data-theme="d"><spring:message code="button.back" /></a>
		<a href="javascript:fn_egov_regist()" data-ajax="false" data-icon="plus" class="ui-btn-right" data-theme="d"><spring:message code="button.create" /></a> 
	</div>
	
	<form name="menuVO" method="post" action="">
	<div data-role="content" data-theme="d">				
		<ul data-role="listview" data-inset="true" data-split-theme="d" style="clear:both;">
			<c:forEach var="result" items="${resultList}" varStatus="status">
	        <li>
	        	<a href="<c:url value='/mgr/EgovMenuListDetail.mdo'/>?menuCode=<c:out value='${result.menuCode}'/>" onclick="event(this.href); return false;" data-ajax="false">
	        	<h3><strong><c:out value="${result.menuNm}"/></strong></h3>
	        	<p><c:out value="${result.menuCode}"/></p>
	        	<p class="ui-li-aside paddT10"><c:out value="${result.upperMenuNm}"/></p>
	        	</a>
	      	</li>
	      	<input type="hidden" name="upperMenuId" value="${result.upperMenuId}"/>
		     </c:forEach> 
		</ul>
		
	</div>
	</form>
	
	<div data-role="footer" data-theme="a" data-position="fixed">	
		<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
	</div>
			
</div>

</body>
</html>