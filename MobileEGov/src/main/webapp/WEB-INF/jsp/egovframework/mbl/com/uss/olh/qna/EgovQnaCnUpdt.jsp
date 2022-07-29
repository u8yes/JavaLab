<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <title>Q&A 내용수정</title>
		
		
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	
	    
		 	  
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="qnaManageVO" staticJavascript="false" xhtml="true" cdata="false"/> 
		<script type="text/javaScript" language="javascript">
		<!--
		
		function integerValidator(){
			if($('#areaNo').val().length != $('#areaNo').val().replace(/[^0-9\.]/g, '').length) {
				alert($('#areaNo').attr('title') + " 은 숫자만 입력 가능합니다.");
				return false;
			}
			if($('#middleTelno').val().length != $('#middleTelno').val().replace(/[^0-9\.]/g, '').length) {
				alert($('#middleTelno').attr('title') + " 은 숫자만 입력 가능합니다.");
				return false;
			}
			if($('#endTelno').val().length != $('#endTelno').val().replace(/[^0-9\.]/g, '').length) {
				alert($('#endTelno').attr('title') + " 은 숫자만 입력 가능합니다.");
				return false;
			}

			return true;
		}
		
		function fn_egov_update() {
				if(!validateQnaManageVO(document.detailForm)) {
					return;
				}
				if(!integerValidator()) {
					return;
				}
				
				document.detailForm.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaCnUpdt.mdo";			
				document.detailForm.submit();
										
			}

		function fn_showView(qaId) {
			document.detailForm.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaDetailInqire.mdo";					         
			
			document.detailForm.submit();
			
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
									
			<div data-role="header">
			    <a href="javascript:fn_showView();" data-icon="arrow-l" data-ajax="false">뒤로</a>
			    <h1>Q&A 내용수정</h1>
			</div>
				
			<form:form commandName="qnaManageVO" name="detailForm" method="post">
				<div data-role="content" class="com-ussContent">
					<form:hidden path="qaId"/>
					<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
					<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
					<form:hidden path="pageIndex" value="${searchVO.pageIndex}"/>

					<div data-inline="true">
						<dl class="uss-registOk">
							<dt><label for="name">작성자명  </label></dt>
							<dd><form:input path="wrterNm" maxlength="20"/></dd>
							
							<dt><label for="writngPassword">비밀번호</label></dt>
							<dd><form:password path="writngPassword" maxlength="20" autocomplete="off"/></dd>
							
							<dt><label for="useYn">전화번호</label></dt>
							<dd>
								<fieldset data-role="controlgroup" data-type="horizontal">
									<div class="uss-phone">
										<div class="uss-wid2"><form:input path="areaNo" maxlength="3" title="전화번호(지역)"/></div>
										<div class="uss-wid3"><form:input path="middleTelno" maxlength="4" title="전화번호(국번)"/></div>
										<div class="uss-wid3"><form:input path="endTelno" maxlength="4"  title="전화번호(지번)"/></div>
									</div>
							    </fieldset>
							</dd>
							
							<dt><label for="useYn">이메일</label></dt>
							<dd>
								<form:input path="emailAdres" maxlength="30"/>
							</dd>
							
							<dt><label for="useYn"><strong>이메일답변여부</strong></label></dt>
							<dd>
								<fieldset data-role="controlgroup" data-type="horizontal"  data-inline="true"> 	
									<form:radiobutton path="emailAnswerAt" value="Y" label="Yes"/>
									<form:radiobutton path="emailAnswerAt" value="N" label="No"/>
								</fieldset>
							</dd>
							
							<dt><label for="useYn">질문제목</label></dt>
							<dd><form:input path="qestnSj" size="70" maxlength="70" title="질문제목"/></dd>
							
							<dt><label for="useYn">질문내용</label></dt>
							<dd><form:textarea path="qestnCn" cols="300" rows="20"  cssClass="txaClass" title="상담내용"/></dd>
						</dl>
						<input name="answerCn" type="hidden" value="Testing...">
					</div>
					
				
				<div class="ui-grid-a uss-clear">	
					<div class="ui-block-a"><a href="javascript:fn_egov_update();" data-role="button" data-theme="b">수정</a></div>
					<div class="ui-block-b"><a href='javascript:fn_showList();' data-role="button" data-theme="b">목록</a></div>
				</div>		
				</div>
				
				</form:form>
				
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
		</div>
		
	</body>
</html>
