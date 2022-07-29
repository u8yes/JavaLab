<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>추천사이트 목록조회</title>
	
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
							
       			$.getJSON("${pageContext.request.contextPath}/uss/ion/rec/RecomendSiteListInqireActor.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
           			
					var html = "";						
						html += '<form id="searchVO" name="searchVO" method="post">';
						html += '	<div class="uss-Search">';
		                html += '	<select id="searchCondition" name="searchCondition">';
		                html += '    	<option value="recomendSiteNm" >추천사이트명</option>';
		                html += '    	<option value="recomendSiteUrl" >추천사이트URL</option>';
		                html += '	</select>';
		                html += '	<div class="uss-SearchBox">';
		                html += '	<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value=\'' + json.searchVO.searchKeyword +'\'/>';
		                html += '	</div>';
						html += '	<input type="hidden" name="pageIndex" id="pageIndex" value=\"'+ json.searchVO.pageIndex +'\"/>';
		                html += '	<input type="button" name="btnWordDicarySearch" id="btnWordDicarySearch" value="조회" class="uss-SearchBtn" onclick="javascript:fn_show_list(1);">';
		                html += '	</div>';
						html += '</form>';
						

		                $('#searchview').html(html);
		                $("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
    
		             	html = "";
					if(json.resultList.length == 0) {
						html += '<li class="com-egovNodata">';
	               		html += '    <spring:message code="common.nodata.msg"/>';
	               		html += '</li>';
					}
					else {
						for ( var i = 0; i < json.resultList.length; i++) {
							
							var result =  json.resultList[i];
							var date = (result.confmDe) == "" ? "" : (result.confmDe.substring(0, 4) + '-' + result.confmDe.substring(4, 6) + '-' + result.confmDe.substring(6, 8));
							var confmAt = result.recomendConfmAt == "Y" ? "승인" : "미승인";
							
							html += '<li>';
							html += '	<a href="${pageContext.request.contextPath}/uss/ion/rec/RecomendSiteDetailInqire.mdo?recomendSiteId=' + result.recomendSiteId +'">';
							html += '		<h3>' + result.recomendSiteNm + '</h3>';
							html += '		<p>' + result.recomendSiteUrl + '</p>';
							html += '		<p><span class="uss-txtBlue">' + confmAt + '</span><span class="uss-txtDate"> ' + date + '</span></p>';
							html += '	</a>';
							html += '</li>';			
										
						}
					}

					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');

					$('#pageNavi').html(pagenationRenderer(json.paginationInfo, "fn_show_list"));
					
       		 	});
       		 	
			}

			function fn_show_page() {
				
				$.mobile.changePage( $("#list"), { transition: "slideup" });
			}

			function fn_show_list(pageIndex) {

				$('#pageIndex').val(pageIndex);
				initList();
				
			}

			        	
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 추천사이트 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>추천사이트 목록조회</h1>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content">
				
				<div id="searchview">

				</div>
				
				<ul data-role="listview">
				
				</ul>
				
				<div id="pageNavi" class="com-egovPaging">
				
				</div>
				
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 추천사이트 List end -->		
		
	</body>
	
</html>
