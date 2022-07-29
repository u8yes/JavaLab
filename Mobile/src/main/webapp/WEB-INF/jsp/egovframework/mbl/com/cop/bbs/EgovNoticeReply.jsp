<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!-- 장규완 추가 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>  
<!-- 완료 -->
 
<!DOCTYPE html> 
<html> 
    <head>
    <title>${brdMstrVO.bbsNm}</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        
        <!-- eGovFrame Common import -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	    
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		<!-- datebox  import-->        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
        <validator:javascript formName="board" staticJavascript="false" xhtml="true" cdata="false"/>
        
		<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/cop/bbs/EgovBBSMng.js" ></script>
		
		<script type="text/javascript">
			<!--
			function fn_egov_validateForm(obj) {
				return true;
			}

			function fn_egov_regist_notice() {

				if (!validateBoard(document.board)){
					return;
				}

					document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/replyBoardArticle.mdo";
					document.board.submit();
			}
			function fn_egov_select_noticeList() {
			
				document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardList.mdo";
				document.board.submit();
				
			}
			//-->
			</script>
</head>

<body>
<!-- 메인 페이지 -->

<div data-role="page" id="page_main" data-url="page_main"   class="ui-page ui-body-c ui-page-active" >

			<!-- 장규완 추가. submit 전달항목 -->
			<form:form commandName="board" name="board" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="replyAt" value="Y"/>
				<input type="hidden" name="pageIndex"  value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input type="hidden" name="nttId" value="<c:out value='${searchVO.nttId}'/>"/>
				<input type="hidden" name="parnts" value="<c:out value='${searchVO.parnts}'/>"/>
				<input type="hidden" name="sortOrdr" value="<c:out value='${searchVO.sortOrdr}'/>"/>
				<input type="hidden" name="replyLc" value="<c:out value='${searchVO.replyLc}'/>"/>
				
				<input type="hidden" name="bbsId" value="<c:out value='${bdMstr.bbsId}'/>"/>
				<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>"/>
				<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>"/>
				<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>"/>
				<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>"/>
				<input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>"/>
				<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>"/>
				<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>"/>
				
				<input type="hidden" name="cal_url" value="${pageContext.request.contextPath}/sym/cmm/EgovNormalCalPopup.do"/>
			
				<c:if test="${anonymous != 'true'}">
					<input type="hidden" name="ntcrNm" value="dummy">	<!-- validator 처리를 위해 지정 -->
					<input type="hidden" name="password" value="dummy">	<!-- validator 처리를 위해 지정 -->
				</c:if>
			
				<c:if test="${bdMstr.bbsAttrbCode != 'BBSA01'}">
				   <input name="ntceBgnde" type="hidden" value="10000101">
				   <input name="ntceEndde" type="hidden" value="99991231">
				</c:if>
		
			
				<div data-role="header" data-theme="a">
					<a href="javascript:fn_egov_select_noticeList();" data-icon="arrow-l">뒤로</a>
					<h1>답글 쓰기</h1> 
				</div>
		
				<div data-role="content" class="com-ussContent">
					<form name="board">
						<div data-role="fieldcontain" data-inline="true">
						<div class="uss-regist" data-inline="true">
							<label for="nttSj"><strong>제목</strong></label><br>
			        		<input name="nttSj" id="name" type="text" maxlength="20" maxlength="20" placeholder="제목을 입력해주세요."  value="RE:">
						</div>
						<div class="uss-regist" data-inline="true">
							<label for="nttCn"><strong>글내용</strong></label><br>
				        	<textarea cols="40" rows="40" name="nttCn" id="nttCn" placeholder="글내용을 입력해주세요."></textarea>
						</div>
						<c:choose>
						  	<c:when test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}"> 
								<div class="uss-regist" data-inline="true">
									<label for="ntceBgnde">시작일자</label><br>
					        		<input name="ntceBgnde" id="defandroid" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
								</div>
								<div class="uss-regist" data-inline="true">
									<label for="ntceEndde">종료일자</label><br>
						        	<input name="ntceEndde" id="defandroid" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
								</div>
							</c:when>
						  	<c:when test="${anonymous == 'true'}"><!-- 익명글 -->
								<div class="uss-regist" data-inline="true">
									<label for="ntcrNm"><spring:message code="cop.ntcrNm"/></label><br>
					        		<input name="ntcrNm" type="text" maxlength="10" size="20">
								</div>
								<div class="uss-regist" data-inline="true">
									<label for="password"><spring:message code="cop.password"/></label><br>
						        	<input name="password" type="password" maxlength="20" >
								</div>
							</c:when>
						</c:choose>
					</div>
						<fieldset class="ui-grid-a">			
						<div class="ui-block-a">
						    <input type="button" value="등록" onclick="fn_egov_regist_notice()" data-icon="plus" data-theme="b" >
						</div>
						<div class="ui-block-b">  
							<input type="reset" value="초기화" data-theme="b" data-icon="refresh"/>
					     </div>
						</fieldset>
									 
					</form>
				</div>
			
			
				<!-- footer start -->
				<div data-role="footer"  data-theme="a" data-position="fixed">
					<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
				</div>
				<!-- footer end -->
					
		</form:form>
	</div>	
</body>
</html>
		