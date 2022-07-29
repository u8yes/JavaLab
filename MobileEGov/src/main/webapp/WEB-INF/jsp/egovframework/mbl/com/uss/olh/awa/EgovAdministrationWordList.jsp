<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행정용어사전목록</title>
	
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
				
       			$.getJSON("${pageContext.request.contextPath}/uss/olh/awa/listAdministrationWordActor.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
					
					var html = "";
						html += '<form id="searchVO" name="searchVO" method="post">';	
						html += '	<div class="uss-Search">';						
		                html += '	<select id="searchCondition" name="searchCondition">';
		                html += '    	<option value="ADMINIST_WORD_NM">행정용어명</option>';
		                html += '    	<option value="ADMINIST_WORD_ENG_NM" >행정용어영문명</option>';
		                html += '    	<option value="ADMINIST_WORD_ABRV_NM">행정용어약어명</option>';
		                html += '    	<option value="ADMINIST_WORD_DFN">행정용어정의</option>';
		                html += '    	<option value="ADMINIST_WORD_DC">행정용어설명</option>';
		                html += '	</select>';
		                html += '	<div class="uss-SearchBox">';
		                html += '		<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value=\'' + json.searchVO.searchKeyword +'\'/>';
		                html += '	</div>';
						html += '	<input type="hidden" name="pageIndex" id="pageIndex" value=\"'+ json.searchVO.pageIndex +'\"/>';
						html += '	<input type="hidden" name="choseongA" id="choseongA" value=\"'+ json.searchVO.choseongA +'\"/>';
						html += '	<input type="hidden" name="choseongB" id="choseongB" value=\"'+ json.searchVO.choseongB +'\"/>';
						html += '	<input type="hidden" name="cmd" id="cmd" value=\"'+ json.searchVO.cmd +'\"/>';
		                html += '	<input type="button" value="조회" onclick="javascript:fn_show_search(\'\', \'\', \'\', 1);" class="uss-SearchBtn">';
		                html += '	</div>';
						html += '</form>';
						

						$('#searchview').html(html);
						$("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');

					if(json.resultList.length == 0) {
						html = '<li class="com-egovNodata">';
	               		html += '    <spring:message code="common.nodata.msg"/>';
	               		html += '</li>';
					}
					else {
						html = "";
						for ( var i = 0; i < json.resultList.length; i++) {
							
							var result =  json.resultList[i];
							
							html += '<li>';
							html += '	<a href="${pageContext.request.contextPath}/uss/olh/awa/detailAdministrationWord.mdo?administWordId='+ result.administWordId +'">';
							html += '		<h3>' + result.administWordNm + '</h3>';
							html += '		<p><span class="uss-txtBlack">' + result.administWordAbrv +'</span> <span class="uss-txtBlack">' + result.themaRelm + '</span><span class="uss-txtDate">' + result.frstRegisterPnttm + '</span></p>';
							html += '	</a>';
							html += '</li>';			
										
						}
					}
					
					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');

					$('#pageNavi').html(pagenationRenderer(json.paginationInfo, "fn_show_list"));
					
       		 	});
       		 	
			}


			function fn_egov_show_SearchMenu() {

				$.mobile.changePage( $("#view"), { transition: "slidedown" } );

			}

			function fn_show_page() {
				
				$.mobile.changePage( $("#list"), { transition: "slideup" });
				
			}			

			function fn_show_list(pageIndex) {
				
				$('#pageIndex').val(pageIndex);		
				initList();
				
			}
			
			function fn_show_search(choseongA, choseongB, cmd, pageIndex) {

				if(cmd != ''){
					
					$('#searchKeyword').val("");
					$('#searchCondition').val("");
					
				}
								
				$('#pageIndex').val(pageIndex);
				$('#choseongA').val(choseongA);
				$('#choseongB').val(choseongB);
				$('#cmd').val(cmd);

				fn_show_page();
				initList();
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
			    <h5>행정용어사전목록</h5>
			    <a href="javascript:fn_egov_show_SearchMenu();" data-icon="search">초성검색</a>
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
		<!-- 용어사전 List end -->	
		
		<div id="view" data-role="page">
		
			<!-- header start -->
			<div data-role="header">
				<a href="#list" data-icon="arrow-l" data-ajax="false">뒤로</a>
			    <h1>행정전문용어조회</h1>
			</div>
			<!-- header end -->
			
			<div data-role="navbar">
				<ul>
		            <li><a href="javascript:fn_show_search('가', '나', 'H', '1');" data-role="button">ㄱ</a></li>
		            <li><a href="javascript:fn_show_search('나', '다', 'H', '1');" data-role="button">ㄴ</a></li>
		            <li><a href="javascript:fn_show_search('다', '라', 'H', '1');" data-role="button">ㄷ</a></li>
		        </ul>
		        <ul>  
		            <li><a href="javascript:fn_show_search('라', '마', 'H', '1');" data-role="button">ㄹ</a></li>
		           	<li><a href="javascript:fn_show_search('마', '바', 'H', '1');" data-role="button">ㅁ</a></li>
		            <li><a href="javascript:fn_show_search('바', '사', 'H', '1');" data-role="button">ㅂ</a></li>
				</ul>
				<ul>
					<li><a href="javascript:fn_show_search('사', '아', 'H', '1');" data-role="button">ㅅ</a></li>
					<li><a href="javascript:fn_show_search('아', '자', 'H', '1');" data-role="button">ㅇ</a></li>
					<li><a href="javascript:fn_show_search('자', '카', 'H', '1');" data-role="button">ㅈ-ㅊ</a></li>
				</ul>
				<ul>						
					<li><a href="javascript:fn_show_search('카', '타', 'H', '1');" data-role="button">ㅋ</a></li>
					<li><a href="javascript:fn_show_search('타', '파', 'H', '1');" data-role="button">ㅌ</a></li>
					<li><a href="javascript:fn_show_search('파', '하', 'H', '1');" data-role="button">ㅍ</a></li>
				</ul>
				<ul>
					<li><a href="javascript:fn_show_search('하', '하', 'E', '1');" data-role="button">ㅎ</a></li>
					<li><a href="javascript:fn_show_search('A', 'E', 'E', '1');" data-role="button">A-E</a></li>
					<li><a href="javascript:fn_show_search('E', 'J', 'E', '1');" data-role="button">E-J</a></li>
				</ul>
				<ul>
					<li><a href="javascript:fn_show_search('K', 'O', 'E', '1');" data-role="button">K-O</a></li>
					<li><a href="javascript:fn_show_search('P', 'T', 'E', '1');" data-role="button">P-T</a></li>
					<li><a href="javascript:fn_show_search('U', 'Z', 'E', '1');" data-role="button">U-Z</a></li>
				</ul>
												
			</div>
			<!-- contents end -->
			
		
			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
		</div>	
		
	</body>
	
</html>
