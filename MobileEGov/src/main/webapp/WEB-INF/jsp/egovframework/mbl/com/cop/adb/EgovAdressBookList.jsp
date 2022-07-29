<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>나의 주소록목록</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		
		<script type="text/javaScript" language="javascript" defer="defer">
			<!--		
			
			$(document).on('pageshow', "#list", initList);
			
			function initList() {
				
        			$.getJSON("${pageContext.request.contextPath}/cop/adb/selectAdbkListActor.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
		            $("#searchCnd option[value='" + json.searchVO.searchCnd + "']").attr('selected', 'selected');

			        var html = "";
			        
					if(json.resultList.length == 0) {
						html += '<li class="com-egovNodata">';
	               		html += '    <spring:message code="common.nodata.msg"/>';
	               		html += '</li>';
					}
					else {
						for ( var i = 0; i < json.resultList.length; i++) {
							var result =  json.resultList[i];
							
							html += '<li>';
							html += '	<a href="javascript:fn_update_adbk(\'' + result.adbkId + '\');" data-ajax="false">';
							html += '		<h3>' + result.adbkNm + '<span class="uss-txtAddress">주소록</span><span class="uss-graySmall">' + result.othbcScope + '</span></h3>';
							html += '		<p><span class="uss-txtBlack">' + result.wrterId + '</span><span class="uss-txtDate">' + result.frstRegisterPnttm.substring(0, 10) + '</span></p>';
							html += '	</a>';
							
							if (result.wrterId == json.userId) {
								html += '<a href="javascript:fn_delete_adbk(\'' + result.adbkId + '\');">삭제</a>';
							}
							
							html += '</li>';			
						}
					}

					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');
					$('#pageNavi').html(pagenationRenderer(json.paginationInfo, "fn_show_list"));
       		 	});
			}

			function fn_delete_adbk(adbkId) {
				jConfirm('삭제하시겠습니까?', '알림', 'a', function(r) {

					if(r) {
						$.getJSON("${pageContext.request.contextPath}/cop/adb/deleteAdbkInf.mdo", {adbkId:adbkId}, function(json){
							initList();
						});
					}
				});
			}

			function fn_regist_adbk() {
				document.searchVO.action = "${pageContext.request.contextPath}/cop/adb/addAdbkInf.mdo";
				document.searchVO.submit();
			}
			
			function fn_update_adbk(adbkId) {
				$('#adbkId').val(adbkId);
				document.searchVO.action = "${pageContext.request.contextPath}/cop/adb/updateAdbkInf.mdo?checkCnd=update";
				document.searchVO.submit();
			}

			function fn_show_list(pageIndex) {
				$('#pageIndex').val(pageIndex);
				initList();
			}
			        	
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 주소록 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>나의 주소록목록</h1>
			    <a href="javascript:fn_regist_adbk();" data-icon="plus" data-ajax="false">등록</a>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post">
					<div id="searchview">
						<div class="uss-Search">
		                	<select id="searchCnd" name="searchCnd" data-role="none">
		                    	<option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if>>주소록명</option>
		                    	<option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if>>공개범위</option>
		                    	<option value="2" <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if>>등록자명</option>
		                	</select>
		                	
		                	<div class="uss-SearchBox">
		                		<input type="text" name="searchWrd" id="searchWrd" class="type-text" value="${searchVO.searchWrd}" data-role="none"/>
		                	</div>
		                	
							<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex}"/>
							<input type="hidden" name="adbkId" id="adbkId" value=""/>
		                	<input type="button" name="btnAdbkSearch" id="btnAdbkSearch" value="조회" class="uss-SearchBtn" onclick="javascript:fn_show_list(1);" data-role="none">
						</div>
					</div>
				</form>
				
				<ul data-role="listview" data-split-icon="delete">
				
				</ul>
				
				<div id="pageNavi" class="com-egovPaging">
				
				</div>
				
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 주소록 List end -->		
		
	</body>
	
</html>
