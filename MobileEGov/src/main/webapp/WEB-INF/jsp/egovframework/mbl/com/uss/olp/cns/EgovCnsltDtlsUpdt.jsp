<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>상담내역 수정</title>
		
       <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="cnsltManageVO" staticJavascript="false" xhtml="true" cdata="false"/> 
		<script type="text/javaScript" language="javascript">
		<!--
			
			function fn_egov_update() {
					if($('#managtCn').val() == "") {
						$('#managtCn').val("Testing...");
					}
						
					if(!validateCnsltManageVO(document.detailForm)) {
						return;
					}
					
					document.detailForm.action = "${pageContext.request.contextPath}/uss/olp/cns/CnsltDtlsUpdt.mdo";			
					document.detailForm.submit();
											
				}
			function fn_showList() {
				
				document.detailForm.action = "${pageContext.request.contextPath}/uss/olp/cns/CnsltListInqire.mdo";
				document.detailForm.submit();
				
			}

			function fn_show_view(cnsltId) {
				
				document.detailForm.action = "${pageContext.request.contextPath}/uss/olp/cnm/CnsltDetailInqire.mdo";
				document.detailForm.submit();
				
			}
		-->
		</script>
	</head>
	
	<body>
		
		<div id="view" data-role="page">
									
			
				<div data-role="header">
					<a href="javascript:fn_show_view();" data-icon="arrow-l" data-ajax="false">뒤로</a>
				    <h1>상담내역 수정</h1>
				</div>
				
				<form:form commandName="cnsltManageVO" name="detailForm" method="post">
				<div data-role="content" class="com-ussContent">
					<form:hidden path="cnsltId"/>
					<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
					<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
					<form:hidden path="pageIndex" value="${searchVO.pageIndex}"/>
				
					<div data-inline="true">
						<dl class="uss-registOk">
							<dt><label for="name"><strong>작성자명</strong> </label></dt>
							<dd><form:input path="wrterNm" maxlength="30"/></dd>
							
							<dt><label for="writingPassword"><strong>비밀번호</strong> </label></dt>
							<dd><form:password path="writngPassword" maxlength="30" autocomplete="off"/></dd>
							
							<dt><label for="useYn"><strong>공개여부</strong> </label></dt>
							<dd><fieldset data-role="controlgroup" data-type="horizontal">				
									<form:radiobutton path="othbcAt" value="Y" label="공개"/>
									<form:radiobutton path="othbcAt" value="N" label="비공개"/> 
									<form:radiobutton path="othbcAt" value="C" label="내용공개"/>
								</fieldset>
							</dd>
							
							<dt><label for="useYn"><strong>이메일</strong> </label></dt>
							<dd><form:input path="emailAdres" maxlength="30"/></dd>
							
							<dt><label for="useYn"><strong>이메일답변여부</strong></label></dt>
							<dd>
								<fieldset data-role="controlgroup" data-type="horizontal"  data-inline="true"> 	
									<form:radiobutton path="emailAnswerAt" value="Y" label="Yes"/>
									<form:radiobutton path="emailAnswerAt" value="N" label="No"/>
								</fieldset>
							</dd>
							
							<dt><label for="areaNo"><strong>전화번호</strong> </label></dt>
							<dd><fieldset data-role="controlgroup" data-type="horizontal">
									<div class="uss-phone">
										<div class="uss-wid2"><form:input path="areaNo" maxlength="3" title="전화번호(지역)"/></div>
										<div class="uss-wid3"><form:input path="middleTelno" maxlength="4" title="전화번호(국번)"/></div>
										<div class="uss-wid3"><form:input path="endTelno" maxlength="4"  title="전화번호(지번)"/></div>
									</div>
							    </fieldset>
							</dd>
							
							<dt><label for="description"><strong>휴대전화번호</strong></label></dt>
							<dd><fieldset data-role="controlgroup" data-type="horizontal">
									<div class="uss-phone">
										<div class="uss-wid2"><form:input path="firstMoblphonNo" type="text" maxlength="3" title="휴대폰전화번호(앞번)"/></div>
										<div class="uss-wid3"><form:input path="middleMbtlnum" type="text" maxlength="4" title="휴대폰전화번호(국번)"/></div>
										<div class="uss-wid3"><form:input path="endMbtlnum" type="text" maxlength="4" title="휴대폰전화번호(지번)"/></div>
									</div>
								</fieldset></dd>
							
							<dt><label for="useYn"><strong>상담제목</strong> </label></dt>
							<dd><form:input path="cnsltSj" size="70" maxlength="70" title="상담제목"/></dd>
							
							<dt><label for="useYn"><strong>상담내용</strong> </label></dt>
							<dd><form:textarea path="cnsltCn" cols="300" rows="20" cssClass="txaClass" title="상담내용"/></dd>
						</dl>
						<form:hidden path="managtCn"/>
						<form:hidden path="managtDe"/>
						<form:hidden path="atchFileId"/>
					</div>
					
					<div class="ui-grid-a">	
						<div class="ui-block-a"><a href="javascript:fn_egov_update();" data-role="button" data-theme="b" data-ajax="false">수정</a></div>
						<div class="ui-block-b"><a href='javascript:fn_showList();' data-role="button" data-theme="b">목록</a></div>				
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

