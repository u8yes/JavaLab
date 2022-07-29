<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>주간 일정 보기</title>
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
		
		<script type="text/javascript">
		<!--
			$(document).on('pageshow', "#list", initList);
		
			function initList() {
							
	   			$.getJSON("${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageWeekListActor.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
	       			
	                $("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
					$("#term").text(json.schdulBgnde + "~" + json.schdulEndde);
					$("#selDate").val(json.selDate);
					
	                var html = "";
	                for ( var i = 0; i < json.weekGroup.length; i++) {
		                var week = json.weekGroup[i];
		                html += '<li data-role="list-divider"> ' + week.substring(0, 4) + '년' + week.substring(4, 6) + '월' + week.substring(6, 8) + '일</li>';
		                
		                var result = json.resultList[i];
						if(result.length == 0) {
							html += '<li class="com-egovNodata">';
		               		html += '    <h1> 일정이 없습니다. </h1>';
		               		html += '</li>';
						}
						else {
							for ( var j = 0; j < result.length; j++) {
								
								var schdulInfo =  result[j];
								var beginDe = schdulInfo.schdulBgnde.substring(8, 10) + '시 ' + schdulInfo.schdulBgnde.substring(10, 12) + '분';
								var endDe = schdulInfo.schdulEndde.substring(8, 10) + '시 ' + schdulInfo.schdulEndde.substring(10, 12) + '분';
								
								html += '<li>';
								html += '	<a href="javascript:fn_show_view(\'' + schdulInfo.schdulId + '\');" >';
								html += '		<p class="uss-darkgray"> <strong> ' + schdulInfo.schdulNm + '</strong> | ' + beginDe + ' ~ ' + endDe + '</p>';
								html += '	</a>';
								html += '</li>';			
											
							}
						}
	                }
	
					$('div[id="list"] ul[data-role="listview"]').html(html).listview('refresh');
					
	   		 	});
	   		 	
			}
			
			function fn_search(status) {
				
				var val = (status == "before" ? -7 : 7);
				var selDate = $('#selDate').val();
				
				var year = selDate.split('-')[0];
				var month = selDate.split('-')[1];
				var day = selDate.split('-')[2];

				var date = new Date(year, month, day);
				date.setDate(date.getDate()+val);			

				selDate = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate();
				$('#selDate').val(selDate);
		
				initList();		
			}

			function fn_show_view(id) {

				$('#schdulId').val(id);
				
				var url = "${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageDetail.mdo";
				$('#searchVO').attr('action', url);
				$('#searchVO').attr('data-ajax', 'false');
				$('#searchVO').submit();
			}

			function fn_show_insert(id) {

				$('#schdulId').val(id);
				
				var url = "${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageRegist.mdo";
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
		    <h1>일정관리목록</h1>
			<a href="javascript:fn_show_insert();" data-icon="arrow-left" data-ajax="false">등록</a>
			<div data-role="navbar">
				<ul>
					<li><a href="${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageWeekList.mdo" class="ui-btn-active" id="week" name="week" data-ajax="false">주간별 일정보기</a></li>
					<li><a href="${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo" id="date" name="date" data-ajax="false">일별 일정보기</a></li>
				</ul>
			</div>
		</div>			
		<!-- header end -->
			
		<!-- contents start -->
		<div data-role="content">
			
			<div id="searchview">
				<form id="searchVO" name="searchVO" method="post" data-role="none">
					<div class="uss-calSearch">
	                	<select id="searchCondition" name="searchCondition" data-role="none" onchange="javascript:initList();">
	       					<c:forEach items="${schdulSe}" var="schdul">	
	       						<option value="${schdul.code}" <c:if test='${searchVO.searchCondition eq schdul.code}'> selected="selected" </c:if>> ${schdul.codeNm} </option>	
	       					</c:forEach>
	                	</select>
                	</div>
	                <div class="egov-grid uss-calpadd">
		                <div class="egov-wid3"><a name="btnSearch_before" id="btnSearch_before" data-role="none" href='javascript:fn_search("before");' class="uss-btnBack">이전</a></div>
						<div class="egov-wid7 uss-calendar" id="term"></div>
						<div class="egov-wid2"><a name="btnSearch_after" id="btnSearch_after" data-role="none" href='javascript:fn_search("after");' class="uss-btnNext">다음</a></div>
					</div>
					<input type="hidden" id="selDate" name="selDate" value="${selDate}"/>
					<input type="hidden" id="schdulId" name="schdulId" value=""/>
					<input type="hidden" id="path" name="path" value="week"/>
				</form>
			</div>
			
			<ul data-role="listview">
								
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
