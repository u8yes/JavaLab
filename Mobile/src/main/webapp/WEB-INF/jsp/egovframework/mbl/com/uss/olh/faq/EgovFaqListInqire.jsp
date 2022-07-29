<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        
		<title>FAQ 목록조회</title>

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
			   	$(document).on('pagehide', "#view", function(){ $(this).remove(); });
				function initList() {
					//$.mobile.changePage( "#list", { transition: "slideup" } );
					   
	       			$.getJSON("${pageContext.request.contextPath}/uss/olh/faq/FaqListInqireView.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
	           			
	        	    var html = ""						
	               		html += '<form id="searchVO" name="searchVO">';
	               		html += '<div class="uss-Search">';
	                    html += '<select id="searchCondition" name="searchCondition"  >';
		                html += '    <option value="qestnSj" <c:if test='${searchCondition == "qestnSj"}' >selected</c:if> >질문제목</option>';
		                html += '</select>';
		                html += '	<div class="uss-SearchBox">';
		                html += '		<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value=\'' + json.searchVO.searchKeyword +'\'/>';
	                    html += '	</div>';
						html += '<input type="hidden" name="pageIndex" id="pageIndex" value=\"'+ json.searchVO.pageIndex +'\"/>';
	                    html += '<input type="button" name="btnFaqSearch" id="btnFaqSearch" value="조회" onclick="javascript:showList(1);" class="uss-SearchBtn">';	                    
		                html += '</div>';
		                html += '</form>';

		                $('#searchview').html(html);
		                $("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
		                
		             	html = "";
	                if(json.FaqList.length==0){
	                	html += '<li class="com-egovNodata">';
	                	html += '       <spring:message code="common.nodata.msg"/> ';
	                	html += '</li>'; 
	           			 }
	               else{
	                    	
	        	        for (var i = 0; i < json.FaqList.length; i++) {
		        	        
		               		 var faq = json.FaqList[i];
		               		 
		               		html += '<li>';
		               		html += "    <a href=\"${pageContext.request.contextPath}/uss/olh/faq/FaqInqireCoUpdt.mdo?faqId=" + faq.faqId  + " \">";
		               		html += '       <h3>' + faq.qestnSj + '</h3><span class="ui-li-count">' + faq.inqireCo + '</span>';
		               		html += '       <p>' + faq.lastUpdusrPnttm + '</p>';
		               		html += '    </a>';
		               		html += '</li>';					                		                 		 
	           			}
	        		}

                   $('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');

                   $('#pageNavi').html(pagenationRenderer(json.paginationInfo, "showList"));
                   	
       		});
			}

				function fn_show_page() {
						
					$.mobile.changePage( $("#list"), { transition: "slideup" });
				}
				function showList(pageIndex) {
					
					$('#pageIndex').val(pageIndex);
					initList();
					
				}
	        	-->

       </script>

	</head>
	
	
	<body>
		<!-- 게시판 List start -->
		<div id="list" data-role="page" >
			<!-- header start -->
			<div data-role="header">		
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>		
			    <h1>FAQ 목록조회</h1>			    
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
