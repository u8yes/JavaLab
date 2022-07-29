<%
 /**
  * @Class Name : EgovMobileSync.jsp
  * @Description : EgovMobileSync 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.11	조준형		최초 생성
  *
  *  @author 조준형
  *  @since 2011.08.24
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
<html manifest="<c:url value='/offlineInfo_ows.manifest'/>">
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

			/*********************************************************
			 * 오프라인웹 서비스 상세조회 초기화
			 ******************************************************** */        
	    	$(document).ready(function() {
				if(window.navigator.onLine == true) {

					// 1. 오프라인웹 서비스 상세조회
                    $.ajax({
                        type       : "POST",
                        cache      : false,                   
                        url        : "<c:url value='/mbl/com/ows/selectMobileOfflineWeb.mdo'/>",
                        data       : {sn:<c:out value='${sn}'/>},            
                        dataType   : "json",                 
                        success :function(json) {
    
                            var offlineWebView = eval(json.offlineWebView);
                            
                            $("#offlineWebSj").text(offlineWebView.offlineWebSj);
                            $("#mberId").text(offlineWebView.mberId);
                            $("#creatDt").text(offlineWebView.creatDt);
                            $("#updtDt").text(offlineWebView.updtDt);
                            $("#offlineWebCn").text(offlineWebView.offlineWebCn);
                            networkStatus = "ON";
                        }
                    });
                    		    		
				} else {
					// 2. [오프라인] 오프라인웹 서비스 상세조회 webstorage에 적재
                    $("#offlineWebSj").text(localStorage.getItem("localView_SJ"));
                    $("#offlineWebCn").text(localStorage.getItem("localView_CN"));
                    $("#creatDt").text(localStorage.getItem("localView_CREAT_DT"));
				}
	        });
		
        </script>
	</head>

	<body>
		
		<!-- form -->
	 	<form:form commandName="offlineWebView" name="offlineWebView" method="post">
	 		<input type="hidden" name="sn"/>
	 	</form:form>	
		
		<!-- 오프라인 서비스 상세화면 -->
	    <div id="regist" data-role="page" data-theme="d">
		    <!-- header start -->
		    <div data-role="header" data-theme="z">
	   			<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
				<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
				<a href="<c:url value='/mbl/com/ows/goMobileOfflineWebList.mdo'/>" data-ajax="false" data-icon="back" class="ui-btn-right">이전</a>
				<div class="ui-body-a mcomd-title"><h3>오프라인서비스 상세화면</h3></div>
			</div>
		    <!-- header end -->
		
		    <!-- content start -->
			<div data-role="content"  class="egov-mcomdContent">
				<dl class="realtimeView">
					<dt>제목</dt>
					<dd id="offlineWebSj" class="subj"></dd>
					<dt>생성일시</dt>
					<dd id="creatDt" class="date"></dd>
					<dt>내용</dt>
					<dd id="offlineWebCn"></dd>
				</dl>
				<div class="realBtn">
					<a href="<c:url value='/mbl/com/ows/goMobileOfflineWebList.mdo'/>" data-ajax="false"  data-role="button" data-theme="z">목록</a>
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