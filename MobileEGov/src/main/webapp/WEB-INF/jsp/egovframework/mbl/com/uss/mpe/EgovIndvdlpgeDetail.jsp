<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>마이페이지</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		
		<script type="text/javaScript" language="javascript" defer="defer">
			<!--		

			function fn_show_page() {

				$(location).attr('href', "${pageContext.request.contextPath}/uss/mpe/selectIndvdlpgeResult.mdo");
				
			}
			
			function fn_delete_cntnts(cntntsId) {
						
				$.getJSON("${pageContext.request.contextPath}/uss/mpe/EgovIndvdlpgeDelCntnts.mdo", {cntntsId:cntntsId}, function(json){

					fn_show_page();
					
				});

			}

			function fn_show_list(pageIndex) {

				$('#pageIndex').val(pageIndex);
				$('#searchVO').attr('action', url);
				$('#searchVO').attr('data-ajax', 'false');
				$('#searchVO').submit();	
				
			}

			function fn_show_content(id, url) {

				if($('#' + id).css('display') == "none"){
					
					$('#' + id).css('display', 'block');
					$('#' + id).load(url);
					
				}
				else {
					
					$('#' + id).css('display', 'none');
					$('#' + id).unload();
					
				}
			
			}

			
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 마이페이지 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" data-ajax="false">홈</a>
				<a href="${pageContext.request.contextPath}/uss/mpe/EgovIndvdlpgeAddCntntsList.mdo" data-icon="plus" data-ajax="false">등록</a>
			    <h1>마이페이지</h1>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post">
					<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex}"/>
				</form>
				
				<ul data-role="listview" data-split-icon="delete">
					<c:choose>
						<c:when test="${empty indvdlPgeDetailList}">
							<li class="com-egovNodata">
		               			등록된 컨텐츠가 없습니다.
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="indvdlPgeDetail" items="${indvdlPgeDetailList}">
								<li>
									<a href='javascript:fn_show_content("${indvdlPgeDetail.cntntsId}", "${pageContext.request.contextPath}${indvdlPgeDetail.cntcUrl}");' id="btn_detail" >
										<h3> ${indvdlPgeDetail.cntntsNm} </h3>
										<p> ${indvdlPgeDetail.cntntsDc} </p>
									</a>
									<a href="javascript:fn_delete_cntnts('${indvdlPgeDetail.cntntsId}');">삭제</a>
									</li>
										<div id="${indvdlPgeDetail.cntntsId}" style="display: none;">		
										</div>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
				</ul>
				
				<div id="pageNavi" class="com-egovPaging">
	   				<ui:pagination paginationInfo="${paginationInfo}" type="mblImage" jsFunction="fn_show_list"/>
				</div>
				
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 마이페이지 List end -->		
		
	</body>
	
</html>
