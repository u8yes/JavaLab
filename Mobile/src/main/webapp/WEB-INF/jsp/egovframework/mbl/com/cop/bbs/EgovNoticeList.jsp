<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>  

 
<!DOCTYPE html> 
<html> 
    <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        	
    <title>${brdMstrVO.bbsNm}</title> 

   	<!-- eGovFrame Common import -->
   	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	

      <script type="text/javascript">
		_editor_area = "nttCn";
		_editor_url = "${pageContext.request.contextPath}/html/egovframework/com/cmm/utl/htmlarea3.0/";
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/html/egovframework/com/cmm/utl/htmlarea3.0/htmlarea.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/EgovCom.js"></script>
		<!-- datebox  import-->        
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/datepicker/jqm-datebox.css"/>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.calbox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.datebox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jqm-datebox.mode.flipbox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/datepicker/jquery.mobile.datebox.i18n.ko.utf8.js"></script> 
      <script type="text/javascript" src="${pageContext.request.contextPath}/validator.do"></script>
      <validator:javascript formName="board" staticJavascript="false" xhtml="true" cdata="false"/>
      
	<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/com/cop/bbs/EgovBBSMng.js" ></script>

	<script type="text/javaScript" language="javascript">
		<!--


		$(document).on('pagehide', "#view", function(){ $(this).remove(); });	
		
		
		function fn_egov_regist(bbsAttrbCode) {
			document.frm.bbsAttrbCode.value = bbsAttrbCode;
			document.frm.nttId.value = document.frm.nttId.value;
			document.frm.bbsId.value=document.frm.bbsId.value;
			document.frm.action =  "${pageContext.request.contextPath}/cop/bbs${prefix}/addBoardArticle.mdo";
			document.frm.submit();		
		}
	
		function press(event) {
			if (event.keyCode==13) {
				fn_egov_select_noticeList('0');
			}
		}
	
		function fn_egov_select_noticeList(pageNo) {

			if(document.frm.pageIndex.value == pageNo) {
				return;
			} 

			document.frm.pageIndex.value = pageNo == 0 ? 1 : pageNo;

			document.frm.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardList.mdo";
			document.frm.submit();	
		}
	
		//????????????
		function fn_egov_inqire_notice(nttId, bbsId) {
			document.frm.nttId.value = nttId;
			document.frm.bbsId.value = bbsId;
			document.frm.action =  "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardArticle.mdo";
			document.frm.submit();			
		}	
		-->
	</script>
</head>

<body>
<!-- ?????? ????????? -->

<div data-role="page" id="list">

	<!-- header start -->
	<div data-role="header">
		<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">???</a>
		<h1 class="bodLogo">${brdMstrVO.bbsNm}</h1>
		<!--<a href="#Add_Page"  data-icon="plus">??????</a> -->
		<a href="javascript:fn_egov_regist('<c:out value="${brdMstrVO.bbsAttrbCode}"/>')" data-icon="plus">??????</a>
	</div>

	<!-- header end -->
			
			
	<!-- contents start -->
    <div data-role="content">
    	<!-- ????????? ??????. submit ???????????? -->
		<form id="frm" name="frm" method="post" data-role="none">
			<div id="searchview">
				<div class="uss-Search">
					<input type="hidden" name="bbsId" value="<c:out value='${boardVO.bbsId}'/>"/>
					<input type="hidden" name="nttId"  value="0"/>
					<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>"/>
					<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>"/>
					<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>"/>
					<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
					   <select id="searchCnd" name="searchCnd" title="???????????? ??????"  data-role="none">
		              		<option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >??????</option>
						   <option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >??????</option>			   
						   <c:choose>
							  	<c:when test="${anonymous != 'true'}"> 
									<option value="2" <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >?????????</option>
							  	</c:when>
							</c:choose>	   
						</select>
		              	<div class="uss-SearchBox">
		                <input type="text" name="searchWrd" id="searchWrd" class="type-text" value="${searchVO.searchWrd }" data-role="none"  onkeypress="press(event);"/>
					</div>
		            <input type="button" name="btnSearch" id="btnSearch" value="??????" onclick="javascript:fn_egov_select_noticeList('0');" class="uss-SearchBtn" data-role="none"/>
				</div>
			</div>
		</form>
		
		<ul data-role="listview">
					<c:choose>
						<c:when test="${empty resultList}">
							<li class="com-egovNodata">
		               			<spring:message code="common.nodata.msg"/>
		               		</li>			
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${resultList}">
								 	<c:choose>
						    		<c:when test="${result.isExpired=='Y' || result.useAt == 'N'}">
						    		<li>
						    			<h3>
						    				<c:out value="${result.nttSj}"/>
						    			</h3>
									</li>
						    		</c:when>
						    		<c:otherwise>
									<li>
										<a href="javascript:fn_egov_inqire_notice('<c:out value="${result.nttId}"/>','<c:out value="${result.bbsId}"/>')" data-transition="slide">
											<h3><c:if test="${result.replyLc!=0}">
									    		<c:forEach begin="0" end="${result.replyLc}" step="1">
									    		</c:forEach>
								    			<span><img src="${pageContext.request.contextPath}/images/egovframework/com/cmm/icon/reply_arrow.gif" alt=""/></span>
									    		</c:if>
									            <c:out value="${result.nttSj}"/>
											</h3>
											<span class="ui-li-count">${result.inqireCo}</span>
											<p>
								            	<c:choose>
												<c:when test="${result.ntcrNm == ''}">
													<span class="uss-txtBlack"><c:out value="${result.frstRegisterNm}"/></span>
												</c:when>
												<c:otherwise>
												    <span class="uss-txtBlack"><c:out value="${result.ntcrNm}"/></span>
												</c:otherwise>
												</c:choose><span class="uss-txtDate">${result.frstRegisterPnttm }</span>
												<!-- <span class="uss-txtBlack">${qnaManage.wrterNm }</span><span class="uss-txtDate">${qnaManage.writngDe }</span> -->
												</p>
										</a>
									</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>					
						</c:otherwise>
					</c:choose>
					
				</ul>
	
		<!-- ??????????????? bbsId=BBSMSTR_000000000550 mappping -->
		<form name="subForm" method="Post">
		<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>"/>
		<input type="hidden" name="nttId"  value="<c:out value="${result.nttId}"/>"/> 
		<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>"/>
		<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>"/>
		<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>"/>
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
		</form>    
			<div id="pageNavi" class="com-egovPaging">
			<ui:pagination paginationInfo="${paginationInfo}" type="mblImage" jsFunction="fn_egov_select_noticeList"/>
				
			</div>
		
    </div>
	<!-- contents end -->
	
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			

</div>

</body>
</html>
