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
        <title>Q&A 목록조회</title>
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

			
		  $(document).on('pagehide', "#view", function(){ $(this).remove(); });
		  
			function integerValidator(){
				if($('#areaNo').val().length != $('#areaNo').val().replace(/[^0-9\.]/g, '').length) {
					alert($('#areaNo').attr('title') + " 은 숫자만 입력 가능합니다.");
					return false;
				}
				if($('#middleTelno').val().length != $('#middleTelno').val().replace(/[^0-9\.]/g, '').length) {
					alert($('#middleTelno').attr('title') + "숫자만 입력 가능합니다.");
					return false;
				}
				if($('#endTelno').val().length != $('#endTelno').val().replace(/[^0-9\.]/g, '').length) {
					alert($('#endTelno').attr('title') + "숫자만 입력 가능합니다.");
					return false;
				}

				return true;
			}
			
			function showView(qaId) {
				document.searchVO.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaInqireCoUpdt.mdo?qaId=" + qaId;					         
				document.searchVO.submit();
				
			}

		  /*********************************************************
		   * 페이징 처리 함수
		   ******************************************************** */
		  function showList_page(pageNo){

			if(document.searchVO.pageIndex.value == pageNo) {
				return;
			} 

			document.searchVO.pageIndex.value = pageNo == 0 ? 1 : pageNo;
		  	document.searchVO.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaListInqire.mdo";		
		    document.searchVO.submit();
		     	
		  }

			function showList() {

				document.searchVO.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaListInqire.mdo";							         
				document.searchVO.submit();
				
			}

			function fn_egov_save() {
				
				if(!validateQnaManageVO(document.detailForm)) {
					return;
				}
				if(!integerValidator()) {
					return;
				}
				
				document.detailForm.action = "${pageContext.request.contextPath}/uss/olh/qna/QnaCnRegist.mdo";							         
				document.detailForm.submit();
				
			}

	  	-->
		</script>
	</head>
	
	<body>
	
	<input name="answerCn" type="hidden" value="Testing...">
	<input name="passwordConfirmAt" type="hidden" value="">
	
		<!-- 용어사전 List start -->
		<div id="list" data-role="page">
			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>Q&A 목록조회</h1>
				<a href="${pageContext.request.contextPath}/uss/olh/qna/QnaCnRegistView.mdo" data-icon="plus">등록</a>
			</div>
			<!-- header end -->
			
			
			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div id="searchview">
						<div class="uss-Search">
							<input name="pageIndex"  id="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			                <select id="searchCondition" name="searchCondition" data-role="none">
			                	<option value="qestnSj"  <c:if test="${searchVO.searchCondition == 'qestnSj'}">selected="selected"</c:if> >질문제목</option>		
		  						<option value="wrterNm"  <c:if test="${searchVO.searchCondition == 'wrterNm'}">selected="selected"</c:if> >작성자명</option>
		  					</select>
			               	<div class="uss-SearchBox">
				                <input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword }" data-role="none"/>
							</div>
				            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:showList_page(0);" class="uss-SearchBtn" data-role="none"/>							
						</div>
					</div>
				</form>
				
				<ul data-role="listview">
					
					<c:choose>
						<c:when test="${empty qnaManageList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="qnaManage" items="${qnaManageList}">
								<li>
									<a href="javascript:showView('${qnaManage.qaId}')" data-ajax="false">
										<h3> ${qnaManage.qestnSj }</h3>
										<p><span class="uss-txtBlue">${qnaManage.qnaProcessSttusCodeNm }</span><span class="uss-txtBlack">${qnaManage.wrterNm}</span><span class="uss-txtDate">${fn:substring(qnaManage.writngDe, 0, 4)}-${fn:substring(qnaManage.writngDe, 4, 6)}-${fn:substring(qnaManage.writngDe, 6, 8)}</span></p>
										<span class="ui-li-count">${qnaManage.inqireCo}</span>
									</a>
								</li>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
					
				</ul>
	
			<div id="pageNavi" class="com-egovPaging">
				<ui:pagination paginationInfo="${paginationInfo}" type="mblImage" jsFunction="showList_page"/>
				
			</div>
				
			</div>
			<!-- contents end -->
			
			
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 용어사전 List end -->		
		
	</body>
	
</html>
