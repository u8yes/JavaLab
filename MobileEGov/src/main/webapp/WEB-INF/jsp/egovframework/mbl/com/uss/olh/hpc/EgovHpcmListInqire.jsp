<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>

		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>도움말목록조회</title>

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
	        
			
	    	// 목록 function
			function initList() {

	    		 $.getJSON("${pageContext.request.contextPath}/uss/olh/hpc/HpcmListView.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
					var html = "";
					var html2 = "";
					
						html += '<form id="searchVO" name="searchVO" >';	
						html += '	<div class="uss-Search">';						
						html += '	<select id="searchCondition" name="searchCondition">';
						html += '		<option value="hpcmDf">도움말정의</option>';
		                html += '	</select>';
		                html += '	<div class="uss-SearchBox">';
		                html += '		<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value=\'' + json.searchVO.searchKeyword +'\'/>';
		                html += '	</div>';
						html += '	<input type="hidden" name="pageIndex" id="pageIndex" value=\"'+ json.searchVO.pageIndex +'\"/>';
		                html += '	<input type="button" value="조회" onclick="javascript:fn_show_list(1);" class="uss-SearchBtn">';		                
		                html += '	</div>';
		                html += '</form>';

		            
					if(json.reusltList.length == 0) {
						 html2 += '<li class="com-egovNodata">';
	               		 html2 += '    <spring:message code="common.nodata.msg"/>';
	               		 html2 += '</li>';
					}
					else {
						 for (var i = 0; i < json.listSize; i++) {
		               		 var resultList = json.reusltList[i];
							
		               		 html2 += '<li>';
		               	  	 html2 += "	<a href='${pageContext.request.contextPath}/uss/olh/hpc/HpcmDetailInqire.mdo?hpcmId=" + resultList.hpcmId + "'>";
							 html2 += '		<h3>' + resultList.hpcmDf+ '</h3>';
							 html2 += '		<p><span class="uss-txtBlack">' + resultList.hpcmSeCodeNm  + '</span><span class="uss-txtDate">' + resultList.frstRegistPnttm + '</span></p>';
		               	     html2 += '</a>';
		               	     html2 += '</li>';	
		               	     
	           			 }
					}
					$('#searchview').html(html);
					$("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
					
					$('div[id="list"] ul[data-role="listview"]').html(html2).listview('refresh');
					
	     			$('#pageNavi').html(pagenationRenderer(json.paginationInfo, "fn_show_list"));
	
	    		 });
			};

			// 목록 function
			function fn_show_list(pageIndex) {
				
				$('#pageIndex').val(pageIndex);
	        	initList();		
			}

			function fn_show_page() {
				
				$.mobile.changePage( $("#list"), { transition: "slideup" });
			}
	
			-->

		</script>

	</head>
	
	<body>

		<!-- 게시판 List start -->
		<div id="list" data-role="page" >
			<form name="list" id="list">
				<input name="hpcmId" type="hidden" id="hpcmId" value="">
			</form>
			<!-- header start -->
			<div data-role="header">				    
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
			    <h1>도움말목록조회</h1>	    
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
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
						
		</div>
		<!-- 게시판 List end -->
	</body>
	
</html>
