<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:scriptlet>

	pageContext.setAttribute("crlf", "\r\n");

</jsp:scriptlet>

<!DOCTYPE html>
<html>
	
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>설문 참여 </title>
		       	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		
		<!-- datebox javascript-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script>
		<script type="text/javascript">
		<!--
			var msg = "";
	
			function validateQues() {
	
				var info = $('select').serializeArray();
				for(var i=0; i < 2; i++){
					var value = info[i].value;
					var name = info[i].name;
	
					if(value == "") {
						var infoName = $('#' + name);
						msg = infoName.parent().prev().text()  + "을 선택해주세요.";	 					
						return false;
					}
				}
	
				if($('#brth').val().replace(/-/gm, "") == "") {
					msg = $('label[for="brth"]').text() + "을 선택해주세요.";
					return false;
				}
	
				else if($('#respondNm').val() == "") {
					msg = $('label[for="respondNm"]').text()  + "을 선택해주세요.";
					return false;
				}
				
				var arr = $(".idHidden").serializeArray();
				for(var i=0; i < arr.length; i++){
					value = arr[i].value;
					name = arr[i].name.replace("ID_", "");
					var type = $('#' + name).attr('type');
	
					switch (type) {
	
						case 'checkbox' :
						case 'radio' :
	
							var checkArr = $('input[name="' + name + '"]:checked');
							var ques = $('#' + name).parent().prev().prev();
							
							if(checkArr.length == 0) {
								$('#' + name).focus();
								msg = ques.text() + "을 작성해주세요.";
								return false;
							}
							else {
								for(var j = 0; j < checkArr.length; j++) {
									if($('#AT_' + checkArr[j].value).val() == "Y" && $('#ETC_' + checkArr[j].value).val() == "") {
										msg = ques.text() + "의 기타답변을 작성해주세요.";
										return false;
									}
								}
							}
							break;
						default : 
	
							if($('#' + name).val() == ""){
								$('#' + name).focus();
								var ques = $('#' + name).prev().prev();
								msg = ques.text() + "을 작성해주세요.";
								return false;
							}
					}
							
				}
				return true;				
			}
			
			function fn_save() {
				
				if(!validateQues()) {
					jAlert(msg, "알림", "a");
					return false;
				}
	
				var url = "${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageRegistActor.mdo";
				$('#detailForm').attr('action', url);
				$('#detailForm').attr('data-ajax', 'false');
				$('#detailForm').submit();
			}

			function fn_show_list() {

				var url = "${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageListView.mdo";
				
				$('#detailForm').attr('action', url);
				$('#detailForm').attr('data-ajax', 'false');
				$('#detailForm').submit();
			}


		-->
		</script>
	</head>
	
	<body>
		
		<!-- View start -->
		<div id="regist_view" data-role="page">
						
				<!-- header start -->
				<div data-role="header">
					<a href="javascript:fn_show_list();" data-icon="arrow-l">뒤로</a>
				    <h1 id="viewTitle">설문 참여</h1>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
			<form id="detailForm" name="detailForm" method="post">
				
				<input type="hidden" id="qestnrId" name="qestnrId" value="${qestnrId}"/>
				<div data-role="content" class="com-ussContent">
						
					<div data-inline="true">
						<dl class="uss-registOk">
							<dt><label for="sexdstn">성별</label></dt>
							<dd>
								<select id="sexdstnCode" name="sexdstnCode" data-role="none">
				        				<option value="">선택</option>
					        		<c:forEach items="${sexdstnCode}" var="codeList">
					        			<option value="${codeList.code}" <c:if test="${codeList.code eq emplyrinfo.sexdstnCode}">selected</c:if>>${codeList.codeNm }</option>
					        		</c:forEach>
				        		</select>
							</dd>
							
							<dt><label for="occpTy">직업</label></dt>
							<dd>
								<select id="occpTyCode" name="occpTyCode" data-role="none">
				        				<option value="">선택</option>
					        		<c:forEach items="${occpTyCode}" var="codeList">
					        			<option value="${codeList.code}">${codeList.codeNm}</option>
					        		</c:forEach>
				        		</select>
							</dd>
							
							<dt><label for="brth">생년월일</label></dt>
							<dd><input id="brth" name="brth" value="${fn:substring(emplyrinfo.brthdy, 0, 4)}-${fn:substring(emplyrinfo.brthdy, 4, 6)}-${fn:substring(emplyrinfo.brthdy, 6, 8)}" type="date" data-role="datebox" data-options='{"mode": "datebox"}'/></dd>
							
							<dt><label for="respondNm">응답자명</label></dt>
							<dd><input type="text" id="respondNm" name="respondNm" value="${emplyrinfo.userNm}"/></dd>
						</dl>
					
						<ul class="uss-hpcDetail">
						
							<li>
								<span class="uss-tit">설문제목 </span>
								<span class="uss-con gray">
									<label for="name-d"><c:out value="${comtnqestnrinfo.qestnrSj}"/></label>
								</span>
							</li>
							
							<li>
								<span class="uss-tit">설문목적 </span>
								<span class="uss-con gray">
									<label for="name-d">${fn:replace(comtnqestnrinfo.qestnrPurps , crlf , '<br>')}</label>
								</span>
							</li>
							
							<li>
								<span class="uss-tit">설문안내내용 </span>
								<span class="uss-con gray">
									<label for="name-d">${fn:replace(comtnqestnrinfo.qestnrWritngGuidanceCn , crlf , '<br>')}</label>
								</span>
							</li>						
							
							<li>
								<span class="uss-tit">설문대상 </span>
								<span class="uss-con gray">
									<label for="name-d">
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '1'}">학생</c:if>
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '2'}">대학생</c:if>
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '3'}">직장인</c:if>
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '4'}">군인</c:if>
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '5'}">교사</c:if>
										<c:if test="${comtnqestnrinfo.qestnrTrget ==  '6'}">기타</c:if>
									</label>
								</span>
							</li>
							
							<li>
								<span class="uss-tit">설문기간 </span>
								<span class="uss-con gray">
									<label for="name-d"><c:out value="${comtnqestnrinfo.qestnrBeginDe} ~ ${comtnqestnrinfo.qestnrEndDe}"/></label>
								</span>
							</li>
							
						</ul>
						
						<div id="accordion">
						<%-- 설문템플릿설정 --%>
						<c:import charEncoding="utf-8" url="/uss/olp/qri/template/template.mdo" > 
							<c:param name="templateUrl" value="${qustnrTmplatManage.qestnrTmplatCours}"/> 
						</c:import>
						</div>	 
						<input type="hidden" id="qestnrTmplatId" name="qestnrTmplatId" value="${qustnrTmplatManage.qestnrTmplatId }"/>
						
						<div class="ui-grid-a">
							<div class="ui-block-a"><a href='javascript:javascript:fn_save();' data-role="button" data-theme="b">저장</a></div>				
							<div class="ui-block-b"><a href='javascript:fn_show_list();' data-role="button" data-theme="b">목록</a></div>
						</div>					
				</div>
				<!-- contents end -->
				
				</div>
			</form>
						
			<!-- footer start -->
			<div data-role="footer">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
								
		</div>
		<!-- view end -->
		
	</body>
</html>

