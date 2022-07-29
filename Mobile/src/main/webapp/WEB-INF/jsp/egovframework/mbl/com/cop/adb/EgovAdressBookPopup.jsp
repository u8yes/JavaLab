<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>사용자목록</title>
	
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
				var userId = $('#userId').val();
       			$.getJSON("${pageContext.request.contextPath}/cop/adb/selectManList.mdo?userId=" + userId, $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
					
					if (json.searchVO.searchCnd == '0') { 
       					$('#headerText').text('사용자 목록');
					}
					/*
					else {
						$('#headerText').text('명함 목록');
					}
					*/

					var html = "";
						
					if(json.resultList.length == 0) {
						html += '<li class="com-egovNodata">';
	               		html += '    <spring:message code="common.nodata.msg"/>';
	               		html += '</li>';
					} else {
						for ( var i = 0; i < json.resultList.length; i++) {
							
							var result =  json.resultList[i];
							
							html += '<li class="uss-listView">';

							if (json.searchVO.searchCnd == '0') {
								if (fn_egov_judge_sameUser(json.userId, result.emplyrId)) {
									html += '<a href="javascript:fn_egov_add_User(\'' + result.emplyrId + '\')" data-ajax="false">';
								} else {
									html += '<a href="javascript:jAlert(\'이미 등록된 사용자입니다.\', \'정보\', \'a\');">';
								}
								
								html += '	<p><span class="uss-txtId">' + result.emplyrId + '</span><span class="uss-txtName">' + result.nm + '</span><span class="uss-txtEmail">[' + result.emailAdres + ']</span></p>';
							}
							/*
							else {
								if (fn_egov_judge_sameUser(json.userId, result.emplyrId)) {
									html += '<a href="javascript:fn_egov_add_User(\'' + result.ncrdId + '\')" data-ajax="false">';
								} else {
									html += '<a href="javascript:jAlert(\'이미 등록된 사람입니다.\', \'정보\', \'a\');">';
								}

								html += '	<p><span class="uss-txtId">' + result.ncrdId + '</span><span class="uss-txtName">' + result.nm + '</span><span class="uss-txtEmail">[' + result.emailAdres + ']</span></p>';
							}
							*/
							
							html += '		<p><span class="uss-txtHome">집&nbsp;:&nbsp;' + result.homeTelno + '</span><span class="uss-txtPhone">휴대폰&nbsp;:&nbsp;' + result.moblphonNo + '</span></p>';
							html += '		<p><span class="uss-txtHome">회사&nbsp;:&nbsp;' + result.offmTelno + '</span><span class="uss-txtPhone">팩스&nbsp;:&nbsp;' + result.fxnum + '</span></p>';
							html += '	</a>';
							html += '</li>';						
						}
					}

					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');
					$('#pageNavi').html(pagenationRenderer(json.paginationInfo, "fn_show_list"));
       		 	});
			}

			function fn_show_list(pageIndex) {

				$('#pageIndex').val(pageIndex);
				initList();
			}

			function fn_egov_judge_sameUser(baseList, targetId) {
				var checkId = baseList.split(",");
				
				for(var i = 0; i < checkId.length; i++){
					if(targetId == checkId[i]){
						return false;
					}
				}

				return true;
			}

			function fn_egov_add_User(addUserId) {
				$('#userId').val($('#userId').val() + ',' + addUserId);
				$('#pageIndex').val($('#mainPageIndex').val());
				$('#searchWrd').val($('#mainSearchWrd').val());
				$('#searchCnd').val($('#mainSearchCnd').val());
				document.searchVO.action = "${pageContext.request.contextPath}/cop/adb/addUser.mdo";
				document.searchVO.submit();
			}
			
			function fn_egov_change_titleText() {
				if ($('#searchCnd').val() == '0') { 
					$('#headerText').text('사용자 목록');
				}
				/*
				else {
					$('#headerText').text('명함 목록');
				}
				*/
			}
			        	
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 사용자 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="javascript:history.back();" data-icon="arrow-l" data-ajax="false">뒤로</a>
				<h1 id="headerText"></h1>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content">
				
				<div id="searchview">
					<div class="uss-Search">
						<form id="searchVO" name="searchVO" method="post">
		                	<select id="searchCnd" name="searchCnd" onchange="javascript:fn_egov_change_titleText();" data-role="none">
		                    	<option value="0">사용자목록</option>
		                    	<!-- <option value="1">명함목록</option> -->
		                	</select>
		                	<div class="uss-SearchBox">
		                		<input type="text" name="searchWrd" id="searchWrd" class="type-text" value="" data-role="none"/>
		                	</div>
							<input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
							<input type="hidden" name="mainPageIndex" id="mainPageIndex" value="${searchVO.pageIndex}"/>
							<input type="hidden" name="mainSearchWrd" id="mainSearchWrd" value="${searchVO.searchWrd}"/>
							<input type="hidden" name="mainSearchCnd" id="mainSearchCnd" value="${searchVO.searchCnd}"/>
							<input type="hidden" name="checkCnd" id="checkCnd" value="${checkCnd}"/>
							<input type="hidden" name="adbkNm" id="adbkNm" value="${searchVO.adbkNm}"/>
							<input type="hidden" name="adbkId" id="adbkId" value="${searchVO.adbkId}"/>
							<input type="hidden" name="othbcScope" id="othbcScope" value="${searchVO.othbcScope}"/>
							<input type="hidden" name="userId" id="userId" value="${userId}"/>
		                	<input type="button" name="btnAdbkSearch" id="btnAdbkSearch" value="조회" class="uss-SearchBtn" onclick="javascript:fn_show_list(1);" data-role="none">
						</form>
					</div>
				</div>
				
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
		<!-- 사용자 List end -->	
	</body>
	
</html>
