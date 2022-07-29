<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
 
<!DOCTYPE html>

<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>주소록 등록</title>
		
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="adbk" staticJavascript="false" xhtml="true" cdata="false"/>

		<script type="text/javascript">
		
		function fn_egov_regist_adbkInf(){		
			if(!validateAdbk(document.adbk)) {
				return;
			}
			
			document.adbk.action = "${pageContext.request.contextPath}/cop/adb/registAdbkInf.mdo";
			document.adbk.submit();
			return true;
		}	
		
		function fn_egov_select_adbkInfs(){		
			document.adbk.action = "${pageContext.request.contextPath}/cop/adb/selectAdbkList.mdo";
			document.adbk.submit();
			return true;		
		}
		
		function fn_egov_delete_user(userId){	
			document.adbk.checkWord.value = userId;
			document.adbk.checkCnd.value = "regist";
			document.adbk.action = "${pageContext.request.contextPath}/cop/adb/deleteUser.mdo";
			document.adbk.submit();	
			return true;	
		}
		
		function fn_egov_inqire_user(){	
			document.adbk.checkCnd.value = "regist";			
			document.adbk.action = "${pageContext.request.contextPath}/cop/adb/openPopup.mdo";
			document.adbk.submit();
		}
		</script>

	</head>
	
	<body>
		
		<div id="view" data-role="page">

			<div data-role="header">
				<a href="javascript:fn_egov_select_adbkInfs();" data-icon="arrow-l" data-ajax="false">뒤로</a>
			    <h1>주소록 등록</h1>
			</div>
			
			<div data-role="content" class="com-siteContent">
				<form commandName="adbk" name="adbk" method="post">
					<input type = "hidden" name = "checkWord" value = "">
					<input type = "hidden" name = "checkCnd" value = "">
					<input type = "hidden" name = "userId" value = '<c:out value="${adbkUserVO.userId}"/>'>
					<input type = "hidden" name = "pageIndex" value = '<c:out value="${searchVO.pageIndex}"/>'>
					<input type = "hidden" name = "searchCnd" value = '<c:out value="${searchVO.searchCnd}"/>'>
					<input type = "hidden" name = "searchWrd" value = '<c:out value="${searchVO.searchWrd}"/>'>
					
					<div data-inline="true" style="padding:0 10px">
						<dl class="uss-registOk">
							<dt><label for="adbkNm"><strong>주소록명</strong></label></dt>
							<dd><input name="adbkNm" type="text" value='<c:out value="${searchVO.adbkNm}"/>' ></dd>
							
							<dt><label for="othbcScope"><strong>공개범위</strong></label></dt>
							<dd>
								<fieldset data-role="controlgroup" data-type="horizontal"  data-inline="true"> 				
									<input type="radio" name="othbcScope" id="othbcScope1" value="개인" <c:if test='${searchVO.othbcScope == "개인" || searchVO.othbcScope == ""}'> checked="checked"</c:if>/>
									<label for="othbcScope1">개인</label>
									<input type="radio" name="othbcScope" id="othbcScope2" value="부서" <c:if test='${searchVO.othbcScope == "부서"}'> checked="checked"</c:if>/>
									<label for="othbcScope2">부서</label> 
									<input type="radio" name="othbcScope" id="othbcScope3" value="회사" <c:if test='${searchVO.othbcScope == "회사"}'> checked="checked"</c:if>/>
									<label for="othbcScope3">회사</label>
								</fieldset>
							</dd>
							
							<dt><label for="adbkUser"><strong>구성원</strong></label></dt>
							<dd>
								<div class="uss-typ3"><input name="adbkUser" type="text" readonly="true"  class="uss-restBtn2"/></div>
								<div class="uss-typ4"><a href="javascript:fn_egov_inqire_user();" data-role="button" data-icon="search" data-iconpos="notext" data-ajax="false">조회</a></div>
							</dd>
						</dl>
					</div>
					<c:forEach var="result" items="${searchVO.adbkMan}" varStatus="status">
						<ul data-role="none" class="memberList">
							<li>
								<div class="memLeft">
								<c:if test="${result.ncrdId == '' || result.ncrdId == NULL}">
									
									<p><span class="uss-txtId"><c:out value="${result.emplyrId}"/></span>
									<span class="uss-txtName"><c:out value="${result.nm}"/></span>
									<span class="uss-txtEmail">[<c:out value="${result.emailAdres}"/>]</span></p>
								</c:if>
								<c:if test="${result.emplyrId == '' || result.emplyrId == NULL}">
									<p><span class="uss-txtId"><c:out value="${result.ncrdId}"/></span>
									<span class="uss-txtName"><c:out value="${result.nm}"/></span>
									<span class="uss-txtEmail">[<c:out value="${result.emailAdres}"/>]</span></p>
								</c:if>
									<p><span class="uss-txtHome">집 : <c:out value="${result.homeTelno}"/></span><span class="uss-txtPhone">휴대폰 : <c:out value="${result.moblphonNo}"/></span></p>
									<p><span class="uss-txtHome">회사 : <c:out value="${result.offmTelno}"/></span><span class="uss-txtPhone">팩스 : <c:out value="${result.fxnum}"/></span></p>
								</div>
								<div class="memRight">
								<c:if test="${result.ncrdId == '' || result.ncrdId == NULL}">
									<a href="javascript:fn_egov_delete_user('<c:out value="${result.emplyrId}"/>');" data-role="button" data-icon="delete" data-iconpos="notext" data-ajax="false">삭제</a>
								</c:if>
								<c:if test="${result.emplyrId == '' || result.emplyrId == NULL}">
									<a href="javascript:fn_egov_delete_user('<c:out value="${result.ncrdId}"/>');" data-role="button" data-icon="delete" data-iconpos="notext" data-ajax="false">삭제</a>
								</c:if>
								</div>
							</li>
						</ul>
					</c:forEach>
					<div class="ui-grid-a addBtn">
						<div class="ui-block-a"><a href="javascript:fn_egov_regist_adbkInf();" data-role="button" data-theme="b">등록</a></div>
						<div class="ui-block-b"><a href="javascript:fn_egov_select_adbkInfs();" data-role="button" data-theme="b">목록</a></div>					
					</div>
				</form>
				
			</div>
			
			
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
					
		</div>
		
	</body>
</html>

