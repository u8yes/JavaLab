<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
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
        <title>Q&A 상세조회</title>
		
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	
	   	
		 	  
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
				
        <script type="text/javaScript" language="javascript" defer="defer">
        <!--

        function fn_password_confirm(url) {
			

        	jPassword("작성글 비밀번호 입력", "알림", "a", function(writngPassword){
        		if(writngPassword != null) {
	    	        	
	        		document.detailForm.writngPassword.value = writngPassword;
					$.getJSON("${pageContext.request.contextPath}/uss/olh/qna/QnaPasswordConfirm.mdo", $("#qnaManageVO").serialize().replace(/%/g,'%25'), function(json){
		
						if(json.passwordConfirmAt == "Y") {
		
							document.detailForm.action = url;
							document.detailForm.submit();
							
						}
						else {
		
							jAlert("비밀번호 오류", "알림", "a");
							
						}
	
					});
				}
    		});
		}
        function fn_egov_delete() {

			jConfirm('삭제하시겠습니까?', '알림', 'a', function(r) {

				if(r) {
						
					var url = "${pageContext.request.contextPath}/uss/olh/qna/QnaCnDelete.mdo";
					fn_password_confirm(url);
				}
				
			});
			
		}

		function fn_egov_updt_confirm() {

			var url = "${pageContext.request.contextPath}/uss/olh/qna/QnaCnUpdtView.mdo";
			fn_password_confirm(url);
		}

		function fn_showList() {
			
			document.detailForm.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaListInqire.mdo";
			document.detailForm.submit();
			
		}
		
	-->
         </script>

	</head>

	<body>
		<div id="view" data-role="page">
			
			<!-- header start -->
			<div data-role="header">		
			    <a href="javascript:fn_showList();" data-icon="arrow-l">뒤로</a>
			    <h1 id="viewTitle">Q&A 상세조회</h1>
			</div>
			<!-- header end -->

				<!-- contents start -->
				<div data-role="content" class="com-copContent">
					<form:form commandName="qnaManageVO" name="detailForm" method="post">
						
						<!-- searchVO start -->
						<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
						<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
						<form:hidden path="pageIndex" value="${searchVO.pageIndex}"/>
						<!-- searchVO end -->
						
						<form:hidden path="qaId" value="${qnaManageVO.qaId}"/>
						<form:hidden path="writngPassword" value="${qnaManageVO.writngPassword}"/>
								
						<div class="qus">
							<span class="ic_qa">qustion</span>
							<strong><label for="name-d"><c:out value="${qnaManageVO.qestnSj}"/></label></strong>
							<p><label for="name-d">${fn:replace(qnaManageVO.qestnCn , crlf , '<br>')}</label></p>
							<span class="time">
							<span class="uss-txtBlack"><c:out value="${qnaManageVO.wrterNm}"/></span>
							<span class="uss-txtBlack"><c:out value="${fn:substring(qnaManageVO.writngDe, 0, 4)}-${fn:substring(qnaManageVO.writngDe, 4, 6)}-${fn:substring(qnaManageVO.writngDe, 6, 8)}"/></span>
							조회&nbsp;<span class="uss-txtBlack"><c:out value="${qnaManageVO.inqireCo}"/></span>
							</span>
							<ul class="qusDetail">
								<li><span>전화번호</span> <span class="txtDeRight"><label for="name-d"><c:out value="${qnaManageVO.areaNo}-${qnaManageVO.middleTelno}-${qnaManageVO.endTelno}"/></label></span></li>
								<li><span>이메일</span> <span class="txtDeRight"><label for="name-d"><c:out value="${qnaManageVO.emailAdres}"/></label></span></li>
								<li><span>이메일답변여부</span> <span class="txtDeRight"><label for="name-d"><c:out value='${qnaManageVO.emailAnswerAt == "Y" ? "Yes" : "No"}'/></label></span></li>
								<li><span>진행처리상태</span><span class="txtDeRight"><label for="name-d"><c:out value="${qnaManageVO.qnaProcessSttusCodeNm}"/></label></span></li>
							</ul>
						</div>
						<c:if test="${qnaManageVO.qnaProcessSttusCode == '3'}">
						<div class="ans">
							<span class="ic_qa pos">answer</span>
							<label for="name-d">${fn:replace(qnaManageVO.answerCn , crlf , '<br>')}</label>
							<span class="time">
							<span class="uss-txtBlack"><c:out value="${qnaManageVO.emplyrNm}"/></span>
							<span class="uss-txtBlack"><c:out value="${fn:substring(qnaManageVO.answerDe, 0, 4)}-${fn:substring(qnaManageVO.answerDe, 4, 6)}-${fn:substring(qnaManageVO.answerDe, 6, 8)}"/></span>
							<span class="uss-txtBlack"><c:out value="${qnaManageVO.orgnztNm}"/></span>
							</span>
							<ul class="ansDetail">
								<li><span>전화번호</span> <span class="txtDeRight"><label for="name-d"><c:out value="${qnaManageVO.offmTelno}"/></label></span></li>
								<li><span>이메일</span> <span class="txtDeRight"><label for="name-d"><c:out value="${qnaManageVO.aemailAdres}"/></label></span></li>							
								<li><span>이메일답변여부</span> <span class="txtDeRight"><label for="name-d"><c:out value='${qnaManageVO.emailAnswerAt == "Y" ? "Yes" : "No"}'/></label>	</span></li>
							</ul>
						</div>	
						</c:if>
						
						<div class="ui-grid-b">
							<div class="ui-block-a"><a href="javascript:fn_egov_updt_confirm()" data-role="button" data-theme="b" data-ajax="false">수정</a></div>
							<div class="ui-block-b"><a href='javascript:fn_egov_delete()' data-role="button" data-theme="b">삭제</a></div>
							<div class="ui-block-c"><a href='javascript:fn_showList();' data-role="button" data-theme="b">목록</a></div>
						</div>
						
				</form:form>	
			</div>
			<!-- contents end -->
			
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
						
		</div>
		<!-- 게시판 View end -->	
					
	</body>
</html>