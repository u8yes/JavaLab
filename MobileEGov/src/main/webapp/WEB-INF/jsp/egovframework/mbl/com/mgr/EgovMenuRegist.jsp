<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--
  Class Name : EgovMenuRegist.jsp
  Description :  register
  Modification Information

       수정일         수정자           수정내용
    -------------    --------    ---------------------
     2014.09.11      표준프레임워크         최초 생성
 
    author   : 표준프레임워크
    since    : 2014.09.11
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
    <head>
    <title><spring:message code="mgr.menuRegist" /></title> 
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- eGovFrame Common import -->
    <link href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css' />" rel="stylesheet" type="text/css" />
    
    <!-- 개발환경 import -->
 	<link href="<c:url value='/css/egovframework/mbl/com/mgr/egovMgr.css' />" rel="stylesheet" type="text/css" />
 	
    <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js' />"></script>
	<!-- validator  import-->   
	<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
    <validator:javascript formName="menuVO" staticJavascript="false" xhtml="true" cdata="false"/>
    <!-- datebox  import-->        
    <link rel="stylesheet" href="/css/egovframework/mbl/cmm/datepicker/jqm-datebox.css" />

	<script type="text/javascript" src="/js/egovframework/mbl/cmm/datepicker/jqm-datebox.core.js"></script>
	<script type="text/javascript" src="/js/egovframework/mbl/cmm/datepicker/jqm-datebox.mode.calbox.js"></script>
	<script type="text/javascript" src="/js/egovframework/mbl/cmm/datepicker/jqm-datebox.mode.datebox.js"></script>
	<script type="text/javascript" src="/js/egovframework/mbl/cmm/datepicker/jqm-datebox.mode.flipbox.js"></script>
	<script type="text/javascript" src="/js/egovframework/mbl/cmm/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script> 
    <script type="text/javascript">
       function egov_menu_regist(){

          if(!validateMenuVO(document.menuVO)){
		     return;
          }
       	  document.menuVO.action = "<c:url value='/mgr/EgovMenuRegist.mdo'/>";
		  document.menuVO.submit();	
       }


       function fn_egov_home() {
           
	      document.menuVO.action = "<c:url value='/mgr/EgovMainMenu.mdo'/>";
		  document.menuVO.submit();
	   }

       function fn_egov_back() {
           
		  document.menuVO.action = "<c:url value='/mgr/EgovMenuList.mdo'/>";
		  document.menuVO.submit();
	   }
   	
   </script>
</head>

<body onload="document.menuVO.menuNm.focus();">
   <!-- 메인 페이지 -->

   <div data-role="page" id="NoticeInire">
	
		<div data-role="header" data-theme="a">
            <a href="javascript:fn_egov_home()" data-icon="home" class="ui-btn-left" data-theme="d"><spring:message code="button.home" /></a>
            <h1><spring:message code="mgr.menuRegist" /></h1> 
            <a href="javascript:fn_egov_back()" data-icon="back" data-theme="d"><spring:message code="button.back" /></a>
		</div>
		
		<form:form commandName="menuVO" name="menuVO" method="post">
		<div data-role="content" class="com-ussContent">
			<dl class="dev-registOk">
				<dt><label for="menuNm"><b><spring:message code="mgr.menuNm" /></b></label></dt>
				<dd><input type="text" name="menuNm" id="menuNm" /></dd>
				
				<dt><label for="upperMenuId"><b><spring:message code="mgr.UpperMenu" /></b></label></dt>
				<dd>
					<select data-native-menu="false" name="upperMenuId" id="upperMenuId"  class="select">			           	
		           		<c:forEach var="result" items="${resultList}" varStatus="status">
		           			<c:if test="${result.menuLvl == '1'}">
		           				<option value="${result.menuCode} "><c:out value="${result.menuNm}"></c:out></option>
		           			</c:if>
		           		</c:forEach>							
		           </select>
				</dd>
				
				<dt><label for="cnncUrl"><b><spring:message code="mgr.cnncUrl" /></b></label></dt>
				<dd><input type="text" name="cnncUrl" id="cnncUrl" /></dd>
				
				<dt><label><b><spring:message code="mgr.actvtyAt" /></b></label></dt>
				<dd><fieldset data-role="controlgroup" data-type="horizontal">
		      			<input type="radio" name="actvtyAt" id="actvtyAt1" value="true" checked="checked"/>
		      			<label for="actvtyAt1"><spring:message code="button.yes" /></label>
			  			<input type="radio" name="actvtyAt" id="actvtyAt2" value="false"  />
		      			<label for="actvtyAt2"><spring:message code="button.no" /></label>
		         	</fieldset></dd>
				
				<dt><label for="menuDescription"><b><spring:message code="mgr.menuDc" /></b></label></dt>
				<dd><textarea class="com-textContents" cols="20" rows="10" name="menuDc" id="menuDc" ></textarea></dd>
				<input type="hidden" name="menuLvl" id="menuLvl" value="2"/>
			</dl>
			<fieldset>
				<div class="egovDevBtn">	
					<a href="javascript:egov_menu_regist()" data-role="button" data-theme="b"><spring:message code="button.create" /></a>
				</div>						
			</fieldset>
       	</div>
		</form:form>

		<!-- footer start -->
		<div data-role="footer" data-position="fixed">
			<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
		</div>
		<!-- footer end -->	
   </div>
</body>
</html>