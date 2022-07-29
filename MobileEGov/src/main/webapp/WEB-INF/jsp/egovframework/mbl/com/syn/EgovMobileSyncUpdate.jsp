<%
 /**
  * @Class Name : EgovMobileSyncUpdate.jsp
  * @Description : EgovMobileSyncUpdate 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.30	조준형		최초 생성
  *
  *  @author 조준형
  *  @since 2011.08.30
  *  @version 1.0
  *  @see
  *  
  */
%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html> 
<html manifest="<c:url value='/offlineInfo_sync.manifest'/>">
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
    		var syncSn = "";		// 동기화 서비스 상세글 일련번호
	    	var rowData = "";		// 동기화 서비스 상세조회 데이터
			var seperator = "|";	// 동기화 서비스 상세조회 내역 구분자         

			/*********************************************************
			 * 동기화 서비스 수정 init
			 ******************************************************** */
			$(document).ready(function() {
		    	rowData = localStorage.getItem(localStorage.getItem("localSn")).split("|");
		    	fn_modify_view();
	        });
        </script>
		<!-- 동기화 서비스 JS -->
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/com/syn/syn.js'/>"></script>
	</head>

	<body>
		
		<!-- form -->
		<form:form commandName="syn" name="syn" method="post">
			<input type="hidden" id="syncSn"/>
		</form:form>		
		
		<!-- 동기화 서비스 등록 -->
	    <div data-role="page" data-theme="d">
		    <!-- header start -->
		    <div data-role="header" data-theme="z">
				<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
				<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
				<div class="ui-body-a mcomd-title"><h3>동기화서비스 수정</h3></div>
			</div>
		    <!-- header end -->
		
		    <!-- content start -->
			<div data-role="content"  class="egov-mcomdContent">
				<dl class="realtimeView">
					<dt><label for="syncSj"><strong>제목</strong></label></dt>
					<dd><input type="text" id="syncSj" size="30" value=""/></dd>
					<dt><label for="syncCn"><strong>내용</strong></label></dt>
					<dd><textarea id="syncCn" rows="8" cols="40"></textarea></dd>
				</dl>
				<div class="ui-grid-b realBtn">
					<div class="ui-block-a"><a href="javascript:fn_cancel();" data-role="button" data-theme="f">초기화</a></div>
					<div class="ui-block-b"><a href="javascript:fn_save();" data-role="button" data-theme="b">저장</a></div>
					<div class="ui-block-c"><a href="<c:url value='/mbl/com/syn/goMobileSyncList.mdo'/>" data-ajax="false" data-role="button" data-theme="a">목록</a></div>			
				</div>
			</div>
			<!-- content end -->
		
		    <!-- footer start -->
			<div data-role="footer" data-theme="z" data-position="fixed" class="egovBar">
				<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
			</div>
			<!-- footer end -->
	    </div>

</body>
</html>    