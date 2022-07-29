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
		<title>설문참여목록조회</title>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		
		
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
							
	   			$.getJSON("${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageList.mdo", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
	       			
	                $("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');

	                var html = "";
	                
					if(json.resultList.length == 0) {
						html += '<li class="com-egovNodata">';
	               		html += '    <spring:message code="common.nodata.msg"/>';
	               		html += '</li>';
					}
					else {
						for ( var i = 0; i < json.resultList.length; i++) {
							
							var result = json.resultList[i];
							
							html += '<li>'; 
							html += '	<a href="javascript:fn_show_info(\'' + result.qestnrId + '\', \'' + result.qestnrTmplatId + '\', \'' + result.qestnrBeginDe + '\', \'' + result.qestnrEndDe + '\');" >';
							html += '		<h3>' + result.qestnrSj + '</h3>';
							html += ' 		<p><span class="uss-darkgray">' + result.qestnrBeginDe + ' ~ ' + result.qestnrEndDe + '</span></p>';
							html += '		<p><span class="uss-txtBlack"> ' + result.frstRegisterNm + '</span><span class="uss-txtDate">' + result.frstRegisterPnttm + '</span></p>';
							html += '	</a>';
							html += '	<a href="javascript:fn_show_stat(\'' + result.qestnrId + '\', \'' + result.qestnrTmplatId + '\');">통계</a>';
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

			function fn_show_info(qestnrId, qustnrTmplatId, sDate, eDate) {

				var date = new Date();
				var day = date.getMonth() + 1;

				day = (day < 10) ? "0" + day : day;
				var curDate = date.getFullYear() + "" + day + "" + ((date.getDate() < 10) ? "0" + date.getDate() : date.getDate());
				curDate = Number(curDate);

				var beginDate = Number(sDate.replace(/-/gi,""));
				var endDate = Number(eDate.replace(/-/gi,""));

				if(curDate >= beginDate && curDate <= endDate){
					
					var url = "${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageRegist.mdo";

					$('#qestnrId').val(qestnrId);
					$('#qestnrTmplatId').val(qustnrTmplatId);
					
					$('#searchVO').attr('action', url);
					$('#searchVO').attr('data-ajax', 'false');
					$('#searchVO').submit();
					
				}
				else {

					jAlert("설문조사 기간이 아닙니다.", "알림", "a");
				}
				
			}

			function fn_show_stat(qestnrId, qustnrTmplatId) {
					
					$.mobile.changePage("${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageStatistics.mdo",{
						data: {qestnrId: qestnrId, qestnrTmplatId: qustnrTmplatId},
						type: 'post',
						changeHash: false,
						reloadPage: true
					});
				
			}
			
				   
						
		-->
		</script>
	</head>
	
	<body>

	<div id="list" data-role="page">
	
		<!-- header start -->
		<div data-role="header">
			<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
		    <h1>설문참여목록조회</h1>
		</div>			
		<!-- header end -->
			
		<!-- contents start -->
		<div data-role="content">			
			<form id="searchVO" name="searchVO" method="post" data-role="none">
				<div id="searchview">
					<div class="uss-Search">					
	                	<select id="searchCondition" name="searchCondition" data-role="none">
							<option value='QUSTNR_SJ' >설문제목</option>
	                	</select>
	                	<div class="uss-SearchBox">
	                		<input type="text" name="searchKeyword" id="searchKeyword" class="type-text" value="${searchVO.searchKeyword}" data-role="none"/>
	                	</div>
						<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex}"/>
	                	<input type="hidden" id="qestnrId" name="qestnrId"/>
						<input type="hidden" id="qestnrTmplatId" name="qestnrTmplatId"/>
	                	<input type="button" name="btnSearch" id="btnSearch" value="조회" class="uss-SearchBtn" onclick="javascript:initList();" data-role="none">						
					</div>
				</div>
			</form>
			
			<ul data-role="listview" data-split-icon="search">
								
			</ul>
			
			<div id="pageNavi" class="com-egovPaging">
				
			</div>
			
		</div>
		<!-- contents end -->
		
		<!-- footer start -->
			<div data-role="footer">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
		<!-- footer end -->
	</div>

	</body>
	
</html>
