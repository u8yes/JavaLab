<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>  


 
<!DOCTYPE html> 
<html>  
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
        	
    <title>${bdMstr.bbsNm}</title> 

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
	<script type="text/javascript">

			function fn_showList() {
				var bbsId = document.board.bbsId.value;
				document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardList.mdo";
				document.board.bbsId.value = bbsId;
				document.board.submit();
				
			}
			function fn_egov_regist_notice() {
				var ntceBgnde = document.board.ntceBgnde.value; 
				var ntceEndde = document.board.ntceEndde.value;
				var bbsId = document.board.bbsId.value;

				if(ntceBgnde > ntceEndde){
					alert("???????????? ???????????? ??????????????? ????????????.");
					return;		
				}

				if (!validateBoard(document.board)){
					return;
				}
					document.board.bbsId.value=bbsId;
					document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/insertBoardArticle.mdo";
					document.board.submit();
			}

      </script>
</head>

<body>

<!--?????? ????????? -->
<div data-role="page" id="Add_Page">
	<div data-role="header">
		<a href="javascript:fn_showList();" data-icon="arrow-l">??????</a>
		<h1>????????? ??????</h1> 
	</div>
<form:form commandName="board" name="board" method="post" enctype="multipart/form-data">
					
	<div data-role="content" class="com-ussContent">
		<div data-inline="true">
			<div class="uss-regist" data-inline="true">
				<label for="nttSj"><strong>??????</strong></label>
        		<input name="nttSj" id="name"  type="text" maxlength="20"  size="20" placeholder="????????? ??????????????????." >
			</div>
			<div class="uss-regist" data-inline="true">
				<label for="nttCn"><strong>?????????</strong></label>
	        	<textarea cols="40" rows="40" name="nttCn" id="nttCn" placeholder="???????????? ??????????????????." class="com-textContents"></textarea>
			</div>
			<c:choose>
			  	<c:when test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}"> 
					<div class="uss-regist" data-inline="true">
						<label for="ntceBgnde"><strong>????????????</strong></label><br>
		        		<input name="ntceBgnde" id="defandroid" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
					</div>
					<div class="uss-regist" data-inline="true">
						<label for="ntceEndde"><strong>????????????</strong></label><br>
			        	<input name="ntceEndde" id="defandroid" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
					</div>
				</c:when>
			  	<c:when test="${anonymous == 'true'}"><!-- ????????? -->
					<div class="uss-regist" data-inline="true">
						<label for="ntcrNm"><strong>?????????</strong></label><br>
		        		<input name="ntcrNm" type="text" maxlength="10" size="20">
					</div>
					<div class="uss-regist" data-inline="true">
						<label for="password"><strong>????????????</strong></label><br>
			        	<input name="password" type="password" maxlength="20" >
					</div>
				</c:when>
			  	<c:otherwise>
			  	
			  	</c:otherwise> 
			</c:choose>
        <!-- 
        <div data-role="navbar" data-iconpos="top">
            <ul>
                <li><a href="fn_egov_regist_notice()" data-theme="b" data-icon="plus" class="ui-btn-active ui-state-persist" data-ajax="false">??????</a></li>
                <li><a href="#page1" data-theme="c" data-icon="refresh">?????????</a></li>
            </ul>
        </div>
         -->
		 
		<div class="ui-grid-a addBgBtn">
			<div class="ui-block-a">
                <input type="button" value="??????" onclick="fn_egov_regist_notice()" data-icon="plus" data-theme="b" data-ajax="false" >
			</div>
			<div class="ui-block-b">
                <input type="reset" value="?????????" data-theme="b" data-icon="refresh"/>
			</div>
		</div>
		
		
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
		<!-- ??????????????? bbsId=BBSMSTR_000000000550 mappping java?????? -->
		<input type="hidden" name="bbsId" value="<c:out value='${brdMstrVO.bbsId}'/>"/>
		<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>"/>
		<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>"/>
		<input type="hidden" name="replyPosblAt" value="<c:out value='${brdMstrVO.replyPosblAt}'/>"/>
		<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${brdMstrVO.fileAtchPosblAt}'/>"/>
		<input type="hidden" name="tmplatId" value="<c:out value='${brdMstrVO.tmplatId}'/>"/>
		<input type="hidden" name="frstRegisterNm" value="<c:out value='${brdMstrVO.frstRegisterNm}'/>"/>
		
		<input type="hidden" name="searchCnd" value="<c:out value='${searchVO.searchCnd}'/>">
		<input type="hidden" name="searchWrd" value="<c:out value='${searchVO.searchWrd}'/>">
			
		<!-- <input type="hidden" name="cal_url" value="${pageContext.request.contextPath}/sym/cmm/EgovNormalCalPopup.do"/>  -->
		<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>"/>
		
		<c:if test="${anonymous != 'true'}">
		<input type="hidden" name="ntcrNm" value="dummy">   <!-- validator ????????? ?????? ?????? -->
		<input type="hidden" name="password" value="dummy"> <!-- validator ????????? ?????? ?????? -->
		</c:if>
		
		<c:if test="${bdMstr.bbsAttrbCode != 'BBSA01'}">
		<input name="ntceBgnde" type="hidden" value="10000101">
		<input name="ntceEndde" type="hidden" value="99991231">
		</c:if>
		</div>
		
	</div>
</form:form>
	
	
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
			
</div>	
</body>
</html>
