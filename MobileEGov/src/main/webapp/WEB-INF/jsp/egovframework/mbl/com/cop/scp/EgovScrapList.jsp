<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>  

<!DOCTYPE html> 
<html> 
    <head>
    <title>스크랩 목록 조회</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        
        
        <!-- eGovFrame Common import -->
     	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
	    
		 		  
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>

		<script type="text/javascript" language="javascript">
		  <!--		
			$(document).on('pagehide', "#view", function(){ $(this).remove(); });	
			function press(event) {
				if (event.keyCode==13) {
					fn_egov_select_scrap('0');
				}
			}
			
			function fn_egov_select_scrap(pageNo) {

				if(document.frm.pageIndex.value == pageNo) {
					return;
				} 

				document.frm.pageIndex.value = pageNo == 0 ? 1 : pageNo;
				

				document.frm.action = "${pageContext.request.contextPath}/cop/scp/selectScrapList.mdo";
				document.frm.submit();	
			}
			
			function fn_egov_inqire_scrap(scrapId) {
				document.frm.scrapId.value = scrapId;
				document.frm.action = "${pageContext.request.contextPath}/cop/scp/selectScrap.mdo";
				document.frm.submit();			
			}
		  	-->
		</script>
</head>

<body>
<!-- 메인 페이지 -->

<div data-role="page" id="list" >

	<div data-role="header">
		<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
		<h1>스크랩</h1> 
	</div>

    <div data-role="content">
    	<!-- 장규완 추가. submit 전달항목 -->
		<form id="frm" name="frm" method="post" data-role="none">
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input type="hidden" name="scrapId">			
				<div id="searchview">
					<div class="uss-Search">
						     <select id="searchCnd" name="searchCnd" data-role="none">
			               		<option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >스크랩명</option>			   
		  					</select>
			               	<div class="uss-SearchBox">
				                <input type="text" name="searchWrd" id="searchWrd" class="type-text" value="${searchVO.searchWrd }" data-role="none"/>
							</div>
				            <input type="button" name="btnSearch" id="btnSearch" value="조회" onclick="javascript:fn_egov_select_scrap(0);" class="uss-SearchBtn" data-role="none"/>
					</div>
				</div>
		</form>
		
		<ul data-role="listview" style="clear:both;">
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<c:choose>
					<c:when test="${empty resultList}">
						<li class="com-egovNodata">
		            		<spring:message code="common.nodata.msg"/>
		            	</li>			
					</c:when>
				    <c:otherwise>
				   		<c:choose>
							<c:when test="${result.useAt == 'N'}">
								<li>
							    	<h3>
					    				<c:out value="${result.nttSj}"/>
					    			</h3>
								</li>
				    		</c:when>
						    <c:otherwise>
							    <li>
							       <a href="javascript:fn_egov_inqire_scrap('<c:out value="${result.scrapId}"/>')" data-transition="slide">
								        <h3>${result.scrapNm}
								        </h3>
										<p>
											<span class="uss-txtBlack">${result.frstRegisterNm}</span>
											<span class="uss-txtDate">${fn:substring(result.frstRegisterPnttm, 0, 10)}</span>
										</p>
							        </a>
							    </li>            
						    </c:otherwise>  
						</c:choose> 
				    </c:otherwise>
				</c:choose>
	    					
			</c:forEach>
		  	<c:if test="${fn:length(resultList) == 0}">
				<li class="com-egovNodata">
			   		<spring:message code="common.nodata.msg"/>
				</li>
			</c:if>	 			
		</ul>
		
			<div id="pageNavi" class="com-egovPaging">
			<ui:pagination paginationInfo="${paginationInfo}" type="mblImage" jsFunction="fn_egov_select_scrap"/>
				
			</div>
    </div>
	
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->

</div>
	
</body>
</html>
