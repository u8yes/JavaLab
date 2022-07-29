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
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행사/이벤트/캠페인 수정</title>
		
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	 	
        
        
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<!-- datebox  import-->        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script>
        
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="eventCmpgnVO" staticJavascript="false" xhtml="true" cdata="false"/> 
		<script type="text/javaScript" language="javascript">
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
         * 목록 으로 가기
         ******************************************************** */
        function fn_egov_list_EventCmpgn(){
        	var vForm = document.detailForm;
            vForm.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo";
            vForm.submit();
        }
		
		/* ********************************************************
		 * 저장처리화면
		 ******************************************************** */
		 function fn_egov_save_EventCmpgn(){
			//var varFrom = document.getElementById("eventCmpgnVO");;
			var varFrom = document.detailForm;
			varFrom.cmd.value = 'save';
			varFrom.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnModify.mdo";
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
				if(document.detailForm.svcUseNmprCo.value == ""){
					document.detailForm.svcUseNmprCo.value = "0" ;
				}
				if($('#eventSvcBeginDe').val() > $('#eventSvcEndDe').val()) {
					jAlert("행사종료일자가 \n행사시작일자보다\n빠릅니다.", '알림', 'a');
					return;
				}
				
				varFrom.submit();
			} 
		}

		/* ********************************************************
		 * 상세회면 처리 함수
		 ******************************************************** */
		 function fn_egov_detail_EventCmpgn(){
				var vFrom = document.detailForm;
				vFrom.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnDetail.mdo";
				vFrom.submit();
		}
		
		-->
		</script>
	</head>
	
	<body>
		
		<div id="view" data-role="page">
									
			
				<div data-role="header">
				    <a href="javascript:fn_egov_detail_EventCmpgn();" data-icon="arrow-l">뒤로</a>
				    <h1>행사/이벤트/캠페인 수정</h1>
				</div>
				
				<div data-role="content" class="com-ussContent">
				<form:form commandName="eventCmpgnVO" id="detailForm" name="detailForm" method="post">
				<input name="cmd" id="cmd" type="hidden" value="<c:out value='save'/>"/>
				<form:hidden path="eventId" value="${eventCmpgnVO.eventId}"/>
				<form:hidden path="searchCondition" value="${eventCmpgnVO.searchCondition}"/>
				<form:hidden path="searchKeyword" value="${eventCmpgnVO.searchKeyword}"/>
				<form:hidden path="pageIndex" value="${eventCmpgnVO.pageIndex}"/>
				<form:hidden path="eventConfmDe" value="9999-12-31"/>
				
					<dl class="uss-registOk">
						<dt><label for="eventTyCode"><strong>행사유형</strong></label></dt>
						<dd class="last"><select name="eventTyCode" id="eventTyCode" data-role="none">  
						<c:forEach items="${comCode035}" var="comCodeList" varStatus="status">
							<option value="${fn:trim(comCodeList.code)}" 
								<c:if test="${comCodeList.code eq eventCmpgnVO.eventTyCode}">selected</c:if>>
							${comCodeList.codeNm}</option>
						</c:forEach>  
						</select></dd>
						
						<dt><label for="eventSvcBeginDe"><strong>행사내용</strong></label></dt>
						<dd><form:input path="eventCn" maxlength="300"/></dd>
						
						<dt><label for="eventSvcBeginDe"><strong>행사시작일자</strong></label></dt>
						<dd><form:input path="eventSvcBeginDe" id="eventSvcBeginDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
						
						<dt><label for="eventSvcEndDe"><strong>행사종료일자</strong></label></dt>
						<dd><form:input path="eventSvcEndDe" id="eventSvcEndDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
						
						<dt><label for="svcUseNmprCo"><strong>서비스이용인원수</strong></label></dt>
						<dd><form:input path="svcUseNmprCo"/></dd>
						
						<dt><label for="chargerNm"><strong>담당자명</strong></label></dt>
						<dd><form:input path="chargerNm" maxlength="300"/></dd>
						
						<dt><label for="prparetgCn"><strong>준비물내용</strong></label></dt>
						<dd><form:input path="prparetgCn" maxlength="300"/></dd>
						
						<%-- 업무사용자만 승인 가능하도록 처리 --%>
						<c:if test="${SUserSe eq 'USR'}">
						<dt><label for="eventConfmAt"><strong>승인여부</strong></label></dt>
						<dd><fieldset data-role="controlgroup" data-type="horizontal"  data-inline="true"> 				
								<input type="radio" name="eventConfmAt" id="radio-1" value="Y" <c:if test="${eventCmpgnVO.eventConfmAt eq 'Y'}">checked</c:if>/> 
								<label for="radio-1">승인</label> 
								<input type="radio" name="eventConfmAt" id="radio-2" value="N" <c:if test="${eventCmpgnVO.eventConfmAt eq 'N'}">checked</c:if>/> 
								<label for="radio-2">미승인</label> 
							</fieldset></dd>
						<dt><label for="eventConfmDe"><strong>승인일</strong></label></dt>
						<dd><form:input path="eventConfmDe" id="eventConfmDe" type="date" data-role="datebox" data-options='{"mode": "datebox"}' style="width:60%;"/></dd>
						</c:if>	
		        	</dl>
	        						
					<div class="ui-grid-a uss-clear">	
						<div class="ui-block-a"><a href="javascript:fn_egov_save_EventCmpgn()" data-role="button" data-theme="b">저장</a></div>
						<div class="ui-block-b"><a href='JavaScript:fn_egov_list_EventCmpgn();' data-role="button" data-theme="b">목록</a></div>
					</div>				
				</form:form>
				</div>
				
				<!-- footer start -->
				<div data-role="footer" data-position="fixed">
					<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
				</div>
				<!-- footer end -->
				
		</div>
		
	</body>
</html>

