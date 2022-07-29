<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html> 
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>부서일정관리목록</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		
		<!-- datebox javascript-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script>
		
		<script type="text/javascript" defer="defer" >
		<!--

			$(document).on('pageshow', "#list", initList);
			
			function initList() {
				
	   			$.getJSON("${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageDailyListActor.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){

					var html = "";
		                $("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
						$("#selDate").val(json.selDate);

					if(json.resultList.length == 0) {
						html += '<li class="com-egovNodata">';
	               		html += '    <h1> 일정이 없습니다 </h1>';
	               		html += '</li>';
					}
					else {
						for ( var i = 0; i < json.resultList.length; i++) {
							
							var result =  json.resultList[i];
							var beginDe = result.schdulBgnde.substring(8, 10) + '시 ' + result.schdulBgnde.substring(10, 12) + '분';
							var endDe = result.schdulEndde.substring(8, 10) + '시 ' + result.schdulEndde.substring(10, 12) + '분';
							
							html += '<li>';
							html += '	<a href="javascript:fn_show_view(\'' + result.schdulId + '\');" >';
							html += '		<h3>' + result.schdulNm + '</h3>';
							html += '		<p class="uss-darkgray">' + beginDe + ' ~ ' + endDe +'</p>';
							html += '	</a>';
							html += '</li>';	
										
						}
					}
	
					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');
					
	   		 	});
	   		 	
			}

			function fn_show_view(id) {

				$('#schdulId').val(id);
				
				var url = "${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageDetail.mdo";
				$('#searchVO').attr('action', url);
				$('#searchVO').attr('data-ajax', 'false');
				$('#searchVO').submit();
			}

			function fn_show_insert(id) {

				$('#schdulId').val(id);

				var url = "${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageRegist.mdo";
				$('#searchVO').attr('action', url);
				$('#searchVO').attr('data-ajax', 'false');
				$('#searchVO').submit();
				
			}

		-->
		</script>
	</head>
	
	<body>

	<div id="list" data-role="page">
	
		<!-- header start -->
		<div data-role="header">
			<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
		    <h1>부서일정관리목록</h1>
			<a href="javascript:fn_show_insert();" data-icon="arrow-left" data-ajax="false">등록</a>
			<div data-role="navbar">
				<ul>
					<li><a href="${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageWeekList.mdo" id="week" name="week" data-ajax="false">주간별 일정보기</a></li>
					<li><a href="${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo" class="ui-btn-active" id="date" name="date" data-ajax="false">일별 일정보기</a></li>
				</ul>
			</div>
		</div>			
		<!-- header end -->
			
		<!-- contents start -->
		<div data-role="content">
			
			<div id="searchview">
				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div class="uss-typ5">
	                	<select id="searchCondition" name="searchCondition" data-role="none" onchange="javascript:initList();">
	       					<c:forEach items="${schdulSe}" var="schdul">	
	       						<option value="${schdul.code}" <c:if test='${searchVO.searchCondition eq schdul.code}'> selected="selected" </c:if>> ${schdul.codeNm} </option>	
	       					</c:forEach>
	                	</select>
                	</div>
                	<div class="uss-typ6">
	                	<input id="selDate" name="selDate" value="${selDate}" data-options='{"closeCallback": "javascript:initList();"}' readonly="readonly"/>
	                	<input type="hidden" id="schdulId" name="schdulId" value=""/>
	                	<input type="hidden" id="path" name="path" value="daily"/>
                	</div>
				</form>
			</div>
			
			<ul data-role="listview" class="uss-clear">
				
			</ul>
			
		</div>
		<!-- contents end -->
		
		<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
		<!-- footer end -->
	</div>

	</body>
	
</html>
