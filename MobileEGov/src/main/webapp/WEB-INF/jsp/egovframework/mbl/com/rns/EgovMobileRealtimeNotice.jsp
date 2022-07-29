<%
 /**
  * @Class Name : EgovMobileRealtimeNotice.jsp
  * @Description : EgovMobileRealtimeNotice 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.11	조준형		최초 생성
  *
  *  @author 조준형
  *  @since 2011.08.17
  *  @version 1.0
  *  @see
  *  
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html> 
<html> 
    <head> 
       	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
       	<title>모바일 화면</title> 
        
		<!-- eGovFrame Common import -->
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css'/>"/>
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css'/>"/>
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js'/>"></script>
		
		
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js'/>"></script>
	  
		<!-- 신규공통컴포넌트 import -->
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomd.css'/>"/>
        <script type="text/javascript">
        	var contextPath = "${pageContext.request.contextPath}";	// js uri 적용을 위한 contextpath
			var sn = "<c:out value='${param.searchSn}'/>";
			/*********************************************************
			 * 실시간 공지 서비스 상세조회 init
			 ******************************************************** */
	    	$(document).ready(function() {
	    		fn_view(sn);
	        });
        </script>
		<!-- 실시간공지 서비스 js -->
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/com/rns/rns.js'/>"></script>
        
	</head>

	<body>
		
		<!-- form -->
	 	<form:form commandName="searchVO" name="searchVO" method="post">
	 		<input type="hidden" name="sn"/>
	 	</form:form>	
		
		<!-- 실시간공지 서비스 등록 -->
	    <div id="regist" data-role="page" data-theme="d">
		    <!-- header start -->
		    <div data-role="header" data-theme="z">
	   			<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
				<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
				<a href="<c:url value='/mbl/com/rns/goRealtimeNoticeList.mdo'/>" data-ajax="false" data-icon="back" class="ui-btn-right">이전</a>
				<div class="ui-body-a mcomd-title"><h3>실시간 공지 상세화면</h3></div>
			</div>
		    <!-- header end -->
		
		    <!-- content start -->
			<div data-role="content"  class="egov-mcomdContent">
				<dl class="realtimeView">
					<dt>제목</dt>
					<dd id="noticeSj" class="subj"></dd>
					<dt>내용</dt>
					<dd id="noticeCn"></dd>
					<dt>게시기간</dt>
					<dd id="noticeDt" class="date"></dd>
					<dt>생성일시</dt>
					<dd id="creatDt" class="date"></dd>
				</dl>
				<div class="ui-grid-a realBtn">
					<div class="ui-block-a"><a href="javascript:fn_delete();" data-role="button" data-theme="a">삭제</a></div>
					<div class="ui-block-b"><a href="<c:url value='/mbl/com/rns/goRealtimeNoticeList.mdo'/>" data-ajax="false" data-role="button" data-theme="a">목록</a></div>			
				</div>
			</div>
			<!-- content end -->
		
		    <!-- footer start -->
			<div data-role="footer" data-theme="z" data-position="fixed">
				<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
			</div>
		    <!-- footer end -->
	    </div>

</body>
</html>    