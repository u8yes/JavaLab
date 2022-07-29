<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>

<!DOCTYPE html>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행사/이벤트/캠페인 입력</title>
		
       	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script> 	
        
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
		<validator:javascript formName="eventCmpgnVO" staticJavascript="false" xhtml="true" cdata="false"/>
		<script type="text/javascript">
		<!--
			$(function(){
				$('input[type="radio"]').change(function(){
					var value = $('input[name="eventConfmAt"]:checked').val();
					if(value == "N") {
						$('#eventConfmDe').val("");
					} 
				});
			});
		
			/* ********************************************************
			 * 저장처리화면(등록창에서 사용)
			 ******************************************************** */
			 function fn_egov_save_EventCmpgn(){
					
					//var varFrom = document.getElementById("eventCmpgnVO");
					var varFrom = document.registForm;
					
					varFrom.action =  "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnRegist.mdo";
					if(!validateEventCmpgnVO(varFrom)){
						
						return;
					}else{
						<%-- 업무사용자만 승인 가능하도록 처리--%>
						<c:if test="${SUserSe eq 'USR'}">
						
						if($('input[name="eventConfmAt"]:checked').val() == "Y" && $('#eventConfmDe').val() == ""){
							jAlert("승인일을 입력하세요.", '알림', 'a');
							document.getElementById("eventConfmDe").focus();
							return;
						}
						if($('input[name="eventConfmAt"]:checked').val() == "N" && $('#eventConfmDe').val() != ""){
							$('#eventConfmDe').val("");
						}
						</c:if>
						if(document.getElementById("svcUseNmprCo").value == ""){
						
							document.getElementById("svcUseNmprCo").value = "0" ;
						}
						if($('#eventSvcBeginDe').val() > $('#eventSvcEndDe').val()) {
							jAlert("행사종료일자가 \n행사시작일자보다\n빠릅니다.", '알림', 'a');
							return;
						}
						varFrom.submit();
					} 
				}
	
				function fn_show_list() {

					var url = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo";
					$('#searchVO').attr('action', url);
					$('#searchVO').attr('data-ajax', 'false');
					$('#searchVO').submit();
				}
		-->
		</script>
		
		<!-- datebox  import-->        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script>
        
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="eventCmpgnVO" staticJavascript="false" xhtml="true" cdata="false"/>
	</head>
	
	<body>
		
		<div id="view" data-role="page">

			<div data-role="header">
				<a href="javascript:fn_show_list();" data-icon="arrow-l">뒤로</a>
			    <h1>행사/이벤트/캠페인 입력</h1>
			</div>
			
			
			<!-- searchVO start -->				
			<form id="searchVO" name="searchVO" method="post">
				<input type="hidden" id="selDate" name="selDate" value="${selDate}"/>
				<input type="hidden" id="path" name="path" value="${path}"/>
				<input type="hidden" id="searchCondition" name="searchCondition" value="${searchVO.searchCondition}"/>
				<input type="hidden" id="searchKeyword" name="searchKeyword" value="${searchVO.searchKeyword}"/>
			</form>
			<!-- searchVO end -->
			
			<form:form commandName="eventCmpgnVO" name="registForm" method="post">
			<form:hidden path="eventId" value=""/>
			<form:hidden path="eventConfmDe" value="9999-12-31"/>
			
			<input name="cmd" id="cmd" type="hidden" value="<c:out value='save'/>"/>
			<div data-role="content" class="com-ussContent">
				<dl class="uss-registOk">
					<dt><label for="eventTyCode"><strong>행사유형</strong></label></dt>
					<dd><select name="eventTyCode" id="eventTyCode" data-role="none"> 
						<c:forEach items="${comCode035}" var="comCodeList"
							varStatus="status">
							<option value="${fn:trim(comCodeList.code)}">${comCodeList.codeNm}</option>
						</c:forEach>
						</select></dd>
						
					<dt><label for="eventCn"><strong>행사내용</strong></label></dt>
					<dd><form:input path="eventCn" maxlength="300"/></dd>
					
					<dt><label for="eventSvcBeginDe"><strong>행사시작일자</strong></label></dt>
					<dd><input name="eventSvcBeginDe" id="eventSvcBeginDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
					<dt><label for="eventSvcEndDe"><strong>행사종료일자</strong></label></dt>
					<dd><input name="eventSvcEndDe" id="eventSvcEndDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
					
					<dt><label for="svcUseNmprCo"><strong>서비스이용인원수</strong></label></dt>
					<dd><form:input path="svcUseNmprCo" maxlength="30"/></dd>
					
					<dt><label for="chargerNm"><strong>담당자명</strong></label></dt>
					<dd><form:input path="chargerNm" maxlength="30"/></dd>
					
					<dt><label for="prparetgCn"><strong>준비물내용</strong></label></dt>
					<dd><form:input path="prparetgCn" maxlength="30"/></dd>
					
					<%-- 업무사용자만 승인 가능하도록 처리 --%>
					<c:if test="${SUserSe eq 'USR'}">
					<dt><label for="eventConfmAt"><strong>승인여부</strong></label></dt>
					<dd><fieldset data-role="controlgroup" data-type="horizontal"  data-inline="true"> 				
							<form:radiobutton path="eventConfmAt" value="Y" label="승인"/>
							<form:radiobutton path="eventConfmAt" value="N" label="미승인" checked="checked"/> 
						</fieldset></dd>
					
					<dt><label for="eventConfmDe"><strong>승인일</strong></label></dt>
					<dd><input name="eventConfmDe" id="eventConfmDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
					</c:if>
				</dl>
				<div class="ui-grid-a">
					<div class="ui-block-a"><a href="javascript:fn_egov_save_EventCmpgn()" data-role="button" data-theme="b">등록</a></div>
					<div class="ui-block-b"><a href='javascript:fn_show_list();' data-role="button" data-theme="b">목록</a></div>
				</div>
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

