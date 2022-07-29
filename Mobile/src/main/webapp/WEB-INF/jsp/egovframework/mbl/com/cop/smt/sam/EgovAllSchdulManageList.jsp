<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>전체일정 목록조회</title>

	    <!-- eGovFrame Common import -->
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
					   
	       			$.getJSON("${pageContext.request.contextPath}/cop/smt/sam/EgovAllSchdulManageListView.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){

			            var html = "";
			             if(json.AllSchdulList.length==0){
	
		                	html += '<li class="com-egovNodata">';
		                	html += '       <spring:message code="common.nodata.msg"/> ';
		                	html += '</li>'; 
			           	}

	               else{
                    	
        	        for (var i = 0; i < json.AllSchdulList.length; i++) {
	        	        
	               		 var schdul = json.AllSchdulList[i];
	               		 var schdulKindNm="";
		        	        if(schdul.schdulKindCode=="1") schdulKindNm="부서일정";
		        	        if(schdul.schdulKindCode=="2") schdulKindNm="개인일정";

							var schdulBgn = schdul.schdulBgnde.toString();
							var year = schdulBgn.substring(0,4); 
							var month = schdulBgn.substring(4,6); 
							var date =  schdulBgn.substring(6,8); 
							schdulBgn  = year + "-" + month  + "-" + date; 
							
		               		html += '<li>';
		               		html += '	<a href="javascript:fn_show_view(\'' + schdul.schdulKindCode + '\', \'' + schdul.schdulId + '\');" >';
							html += '       <h3>' + schdul.schdulNm + '</h3>';
		               		html += '		<input name="schdulId" type="hidden" value="">';
		               		html += '		<input name="pageIndex" type="hidden" value="<c:out value="${searchVO.pageIndex}"/>"/>';
		               		html += '    	 <p><span class="uss-txtBlack">' + schdul.frstRegisterNm + '</span>';
		               		html += '    	 <span class="uss-txtBlack">' + schdulKindNm + '</span>';
		               		html += '    	 <span class="uss-txtDate">' + schdul.frstRegisterPnttm + '</span></p>';
		               		html += '	</a>';
		               		html += '</li>';				                		                 		 
	           			}
	        		}

                   $('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');

                   $('#pageNavi').html(pagenationRenderer(json.paginationInfo, "showList"));
                   	
       		});
			}


				function fn_show_view(schdulKindCode, id) {

					$('#schdulId').val(id);

					if(schdulKindCode == "1"){
						var url = "${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageDetail.mdo";
					}else{
						var url = "${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageDetail.mdo";
					}
					
					$('#searchVO').attr('action', url);
					$('#searchVO').attr('data-ajax', 'false');
					$('#searchVO').submit();
				}
				
				function fn_show_page() {
						
					$.mobile.changePage( "#list", { transition: "slideup" });
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
			    <h1>전체일정 목록조회</h1>			    
			</div>
			<!-- header end -->

			<!-- contents start -->
			<div data-role="content">
				<form id="searchVO" name="searchVO" method="post">
					<div id="searchview">
						<div class="uss-Search">
		                    <select id="searchCondition" name="searchCondition" data-role="none" >
			                    <option value="SCHDUL_NM" <c:if test='${searchVO.searchCondition eq "SCHDUL_NM"}'> selected="selected" </c:if>>일정명</option>
			                    <option value="SCHDUL_CN" <c:if test='${searchVO.searchCondition eq "SCHDUL_CN"}'> selected="selected" </c:if>>일정내용</option>
			               	</select>
			               	<div class="uss-SearchBox">
			                		<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword}" data-role="none"/>
		                    	</div>
		                    <input type="hidden" name="schdulId" id="schdulId"/>
		                    <input type="hidden" name="path" id="path" value="all"/>
			                <input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex}"/>
		                    <input type="button" name="btnAllSchdulSearch" id="btnAllSchdulSearch" value="조회" onclick="javascript:showList(1);" class="uss-SearchBtn" data-role="none"/>
		                </div>
					</div>
				</form>

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
