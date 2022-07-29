<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>상담목록조회</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="cnsltManageVO" staticJavascript="false" xhtml="true" cdata="false"/> 
		
		<script type="text/javaScript" language="javascript">
			<!--
		
			function showView(cnsltId, othbcAtConfirm) {

					if(othbcAtConfirm == "N"){
						jPassword("비밀번호 입력", "알림", "a", function(writngPassword){
							if(writngPassword != null){
								$.getJSON("${pageContext.request.contextPath}/uss/olp/cns/CnsltPasswordConfirm.mdo", {cnsltId:cnsltId, writngPassword:writngPassword}, function(json){
		
									if(json.passwordConfirmAt == "Y") {
										document.searchVO.action = "${pageContext.request.contextPath}/uss/olp/cnm/CnsltDetailInqire.mdo?cnsltId=" + cnsltId;					         
										document.searchVO.submit();
									}
									else {
										jAlert("비밀번호 오류", "알림", "a");
									}
								});
							}
						});
					}
					else {
						document.searchVO.action = "${pageContext.request.contextPath}/uss/olp/cnm/CnsltDetailInqire.mdo?cnsltId=" + cnsltId;						         
						document.searchVO.submit();
					}
		
			}

			function showList_page(pageIndex) {

				if(document.searchVO.pageIndex.value == pageIndex) {
					return;
				} 

				document.searchVO.pageIndex.value = pageIndex == 0 ? 1 : pageIndex;
				document.searchVO.action = "${pageContext.request.contextPath}/uss/olp/cns/CnsltListInqire.mdo";					         
				document.searchVO.submit();
			}

			function fn_egov_save() {
				if(!validateCnsltManageVO($('#detailForm').get(0))) {
					return;
				}
				
				var url = "${pageContext.request.contextPath}/uss/olp/cns/CnsltDtlsRegist.mdo";					         
				$('#detailForm').attr('action', url);
				$('#detailForm').attr('data-ajax', 'false');
				$('#detailForm').submit();	
				
			}
      		
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 용어사전 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>상담 목록조회</h1>
			    <a href="${pageContext.request.contextPath}/uss/olp/cns/CnsltDtlsRegistView.mdo" data-icon="plus">등록</a>
			</div>
			<!-- header end -->
			
			
			
			<!-- contents start -->
			<div data-role="content">

				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div id="searchview">
						<div class="uss-Search">
							<input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex }"/>
			                <select id="searchCondition" name="searchCondition" data-role="none">
			               		<option value="wrterNm" <c:if test='${searchVO.searchCondition == "wrterNm"}'>selected="selected"</c:if>>작성자명</option>
			                	<option value="cnsltSj" <c:if test='${searchVO.searchCondition == "cnsltSj"}'>selected="selected"</c:if>>상담제목</option>
			               	</select>
			               	<div class="uss-SearchBox">
				                <input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword }" data-role="none"/>
							</div>
				            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:showList_page(0);" class="uss-SearchBtn" data-role="none"/>
						</div>
					</div>
				</form>
				
				<ul data-role="listview" data-count-theme="d"> 
					
					<c:choose>
						<c:when test="${empty cnsltManageList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="cnsltManage" items="${cnsltManageList}">
								<li>
									<a href="javascript:showView('${cnsltManage.cnsltId}', '${cnsltManage.othbcAt}')" data-ajax="false">
										<h3> ${cnsltManage.cnsltSj } </h3>
										<p><span class="uss-txtBlue">${cnsltManage.qnaProcessSttusCodeNm }</span><span class="uss-txtBlack">${cnsltManage.wrterNm }</span><span class="uss-txtDate">${fn:substring(cnsltManage.writngDe, 0, 10)}</span></p>
										<span class="ui-li-count">${cnsltManage.inqireCo}</span>
									</a> 
								</li>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
					
				</ul>
	
				
				<div id="pageNavi" class="com-egovPaging">
					<ui:pagination paginationInfo = "${paginationInfo}" type="mblImage" jsFunction="showList_page"/>
				</div>
				
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 용어사전 List end -->		
		
	</body>
	
</html>
