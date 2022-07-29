<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<jsp:useBean id="now" class="java.util.Date"/>

<!DOCTYPE html>
<%--  자마스크립트 메세지 출력 --%>
${reusltScript}
<html lang="ko">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>온라인POLL참여 목록</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
        
		<script type="text/javaScript" language="javascript">
			<!--		
			/* ********************************************************
			 * 페이징 처리 함수
			 ******************************************************** */
			function linkPage(pageNo){
				document.subForm.pageIndex.value = pageNo;
				document.subForm.action = "${pageContext.request.contextPath}/uss/olp/opp/listOnlinePollPartcptn.mdo";
			   	document.subForm.submit();
			}

			/* ********************************************************
			 * 상세회면 처리 함수
			 ******************************************************** */
			function fn_egov_regist_OnlinePollPartcptn(pollId,sDate,eDate){
				var iToDate = <fmt:formatDate value="${now}" pattern="yyyyMMdd"/>;
				var iBeginDate = Number(sDate.replaceAll("-",""));
				var iEndDate = Number(eDate.replaceAll("-",""));

				if(iToDate >= iBeginDate && iToDate <= iEndDate){

					var vFrom = document.subForm; 
					vFrom.pollId.value = pollId; 
					vFrom.action = "${pageContext.request.contextPath}/uss/olp/opp/registOnlinePollPartcptn.mdo"; 
					vFrom.submit();
				}else{
					jAlert("지금은 온라인POLL 투표기간이 아닙니다!", '알림', 'a');
					return;
				}

			}

			/* ********************************************************
			 * 검색 함수
			 ******************************************************** */
			function fn_egov_search_OnlinePollPartcptn(pageIndex){
				var vFrom = document.searchVO;
				vFrom.pageIndex.value = pageIndex;
				vFrom.action = "${pageContext.request.contextPath}/uss/olp/opp/listOnlinePollPartcptn.mdo";
				vFrom.submit();
				
			}

			/* ********************************************************
			 * 통계보기
			 ******************************************************** */
			function fn_show_stat(pollId) {

				$.mobile.changePage("${pageContext.request.contextPath}/uss/olp/opp/statisticsOnlinePollPartcptn.mdo",{
					data: {pollId: pollId},
					type: 'post',
					changeHash: false,
					reloadPage: true
				});
				
			}

			/* ********************************************************
			* PROTOTYPE JS FUNCTION
			******************************************************** */
			String.prototype.trim = function(){
				return this.replace(/^\s+|\s+$/g, "");
			}

			String.prototype.replaceAll = function(src, repl){
				 var str = this;
				 if(src == repl){return str;}
				 while(str.indexOf(src) != -1) {
				 	str = str.replace(src, repl);
				 }
				 return str;
			}
        	-->
        	
		</script>
				
	</head>
	
	<body>
	
		<!-- 온라인POLL 목록 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>온라인POLL참여 목록</h1>
			</div>
			<!-- header end -->
			
			
			
			<!-- contents start -->
			<div data-role="content">
			
			<form id="searchVO" name="searchVO" method="post" data-role="none">
				<div id="searchview">
					<div class="uss-Search">
						<input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex }"/>
						<input type="hidden" id="pollId" name="pollId"/>
						<select id="searchCondition" name="searchCondition" data-role="none">
						   <option value='POLL_NM' <c:if test="${searchCondition == 'POLL_NM'}">selected</c:if>>POLL명</option>
					    </select>
		               	<div class="uss-SearchBox">
			                <input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword }" data-role="none"/>
						</div>
			            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:fn_egov_search_OnlinePollPartcptn(1);" class="uss-SearchBtn" data-role="none"/>
					</div>
				</div>
			</form>	
				<ul data-role="listview" data-split-icon="search">
					
					<c:choose>
						<c:when test="${empty resultList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="resultInfo" items="${resultList}">
								<li>
									<a href="javascript:fn_egov_regist_OnlinePollPartcptn('${resultInfo.pollId}','${resultInfo.pollBeginDe}','${resultInfo.pollEndDe}')" data-ajax="false">
										<h3> ${resultInfo.pollNm } </h3>
										<p class="uss-gray"><strong>${resultInfo.pollBeginDe } ~  ${resultInfo.pollEndDe }</strong></p>
										<p> <span class="uss-txtBlack">${resultInfo.frstRegisterNm }</span><span class="uss-txtDate">${resultInfo.frstRegisterPnttm}</span></p>
									</a>
									<a href="javascript:fn_show_stat('${resultInfo.pollId}');">통계</a>
								</li>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
					
				</ul>
	
				
				<div id="pageNavi" class="com-egovPaging">
	   				<ui:pagination paginationInfo="${paginationInfo}" type="mblImage" jsFunction="linkPage"/>
				</div>
				
			</div>
			<!-- contents end -->
		<form name="subForm" method="Post"> 
		<input type="hidden" name="pollId" value="<c:out value='${resultInfo.pollId}'/>"/>
		<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
		<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
		</form>
			<!-- footer start -->
			<div data-role="footer">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 행사/이벤트/캠패인 List end -->		
		
	</body>
	
</html>	
