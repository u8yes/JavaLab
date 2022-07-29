<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>일지관리목록</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
		<validator:javascript formName="diaryManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
		
		<script type="text/javaScript" language="javascript" defer="defer">
			<!--

			$(document).on('pagehide', "#view", function(){ $(this).remove(); });		

			/* ********************************************************
			 * 페이징 처리 함수
			 ******************************************************** */
			function linkPage(pageNo){
				if(document.subForm.pageIndex.value == pageNo) {
					return;
				}
				document.subForm.pageIndex.value = pageNo;
				document.subForm.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageList.mdo";
			   	document.subForm.submit();
			}
			/* ********************************************************
			 * 등록 처리 함수 
			 ******************************************************** */
			function fn_egov_regist_DiaryManage(){
				location.href = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageRegist.mdo";
			}
			/* ********************************************************
			 * 상세회면 처리 함수
			 ******************************************************** */
			function fn_egov_detail_DiaryManage(diaryId){
				var vFrom = document.subForm; 
				vFrom.diaryId.value = diaryId; 
				vFrom.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageDetail.mdo"; 
				vFrom.submit();
			}

			/* ********************************************************
			 * 검색 함수
			 ******************************************************** */
			function fn_egov_search_DiaryManage(pageNo){
				var vFrom = document.searchVO;
				vFrom.pageIndex.value = pageNo;
				
				vFrom.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageList.mdo";
				vFrom.submit();
				
			}

			/* ********************************************************
			 * 저장처리화면(등록창에서 사용)
			 ******************************************************** */
			function fn_egov_save_DiaryManage(){

				var vForm = document.registForm;
				
				vForm.action =  "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageRegistActor.mdo";
				
				if(!validateDiaryManageVO(vForm)){
				 			
					return;
				}else{
				
					vForm.submit();
				} 
			}  

			 /* ********************************************************
				 * 검색 함수(팝업창에서 사용)
				 ******************************************************** */
				function fn_egov_search_DeptSchdulManage(){
					var vFrom = document.searchPopVO;
					
					vFrom.action = "${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageListPopup.mdo";
					vFrom.submit();
				}
				/* ********************************************************
				* 선택 처리 함수(팝업창에서 사용)
				******************************************************** */
				function fn_egov_open_Popup(cnt, schdulId, schdulCn){

					var vFrom = document.subPopForm;
					vFrom.schdulId.value=schdulId;
					vFrom.schdulCn.value=schdulCn;
					
					vFrom.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageRegist.mdo";
					vFrom.submit();
				}

				/* ********************************************************
				* 등록 화면 호출 함수
				******************************************************** */
				function fn_egov_show_regist(url) {

					var vFrom = document.searchVO;
					
					vFrom.action = url;
					vFrom.submit();
				}

				
        	-->
        	
		</script>
				
	</head>
	
	<body>
	
		<!-- 일지관리 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header" >
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>일지관리목록</h1>
			    <a href='javascript:fn_egov_show_regist("${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageRegist.mdo");' data-icon="arrow-right">등록</a>
			</div>
			<!-- header end -->
			
			
			
			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div id="searchview">
						<div class="uss-Search">
							<input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex }"/>
							<select id="searchCondition" name="searchCondition" data-role="none">
							   <option value='DIARY_NM' <c:if test="${searchCondition == 'DIARY_NM'}">selected="selected"</c:if>>일지명</option>
							   <option value='DRCT_MATTER' <c:if test="${searchCondition == 'DRCT_MATTER'}">selected="selected"</c:if>>지시사항</option>
							   <option value='PARTCLR_MATTER' <c:if test="${searchCondition == 'PARTCLR_MATTER'}">selected="selected"</c:if>>특이사항</option>
						    </select>
			               	<div class="uss-SearchBox">
				                <input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword }" data-role="none"/>
							</div>
				            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:fn_egov_search_DiaryManage(1);" class="uss-SearchBtn" data-role="none"/>
						</div>
					</div>
				</form>
				
				<ul data-role="listview">
					
					<c:choose>
						<c:when test="${empty resultList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="resultInfo" items="${resultList}">
								<li>
									<a href="javascript:fn_egov_detail_DiaryManage('${resultInfo.diaryId}')" data-ajax="false">
										<h3> ${resultInfo.diaryNm } </h3>
										<p> 
											<span class="uss-txtBlue">${resultInfo.diaryProcsPte}%</span>
											<span class="uss-txtBlack">${resultInfo.frstRegisterNm }</span> 
											<span class="uss-txtDate"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
										</p>
									</a>
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
			<input type="hidden" name="diaryId" value="<c:out value='${resultInfo.diaryId}'/>"/>
			<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			</form>
			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 일지관리 List end -->		
		
	</body>
	
</html>
