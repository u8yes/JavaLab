<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html> 
<html> 
    <head>
    <title>EgovFrame 모바일게시판</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        
        <!-- eGovFrame Common import -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>	    
    
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/cop/bbs/EgovBBSMng.js" ></script>
        
		<script type="text/javascript">
			function fn_egov_validateForm(obj){
				return true;
			}
			function fn_check_date(start, end){

				if(end<start){
					jAlert('시작일자, 종료일자를 확인하세요', '알림', 'a');	
					return false;
				}else{
					return true;
				}
			}

			function fn_egov_regist_notice(){

				var ntceBgnde = document.board.ntceBgnde.value; 
				var ntceEndde = document.board.ntceEndde.value;

				if(ntceBgnde > ntceEndde){
					alert("게시기간 종료일이 시작일보다 빠릅니다.");
					return;		
				}

				if (!validateBoard(document.board)){
					return;
				}

					document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/updateBoardArticle.mdo";
					document.board.submit();
			}
			
			function fn_egov_select_noticeList() {
				document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardList.mdo";
				document.board.submit();	
			}
			

			//상세조회
			function fn_egov_inqire_notice(nttId, bbsId) {
				document.board.nttId.value = nttId;
				document.board.bbsId.value = bbsId;
				document.board.action =  "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardArticle.mdo";
				document.board.submit();			
			}	
			function fn_showList() {
			
				document.board.action = "${pageContext.request.contextPath}/cop/bbs${prefix}/selectBoardList.mdo";
				document.board.submit();
				
			}
		</script>
    </head>

<body>

    <!-- 메인 페이지 -->
    <div data-role="page" id="NoticeInire" data-url="page_main" >

		<form name="board" method="post" action=""  enctype="multipart/form-data">
		
			<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
			<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" >
			<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" >
			<input type="hidden" name="parnts" value="<c:out value='${result.parnts}'/>" >
			<input type="hidden" name="sortOrdr" value="<c:out value='${result.sortOrdr}'/>" >
			<input type="hidden" name="replyLc" value="<c:out value='${result.replyLc}'/>" >
			<input type="hidden" name="searchCnd" value="<c:out value='${searchVO.searchCnd}'/>">
			<input type="hidden" name="searchWrd" value="<c:out value='${searchVO.searchWrd}'/>">
			
			<div data-role="header">
				<a href="javascript:fn_egov_inqire_notice('<c:out value="${result.nttId}"/>','<c:out value="${result.bbsId}"/>');" data-icon="arrow-l">뒤로</a>
			<h1>게시글 수정</h1> 	
		</div>
			
	        <div data-role="content" class="com-ussContent">
	        
				<div data-role="fieldcontain" data-inline="true">
					<div class="uss-regist" data-inline="true">
						<label for="nttSj"><strong>제목</strong></label><br>
		        		<input name="nttSj"  type="text" id="name" maxlength="20"  size="20"  value="<c:out value='${result.nttSj}'/>"  >
					</div>
					<div class="uss-regist" data-inline="true">
						<label for="nttCn"><strong>글내용</strong></label><br>
			        	<textarea cols="40" rows="40" name="nttCn" id="nttCn"class="com-textContents"><c:out value="${result.nttCn}" escapeXml="false"/></textarea>
					</div>
					<c:choose>
					  	<c:when test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}"> 
							<div class="uss-regist" data-inline="true">
								<label for="ntceBgnde">시작일자</label><br>
				        		<input name="ntceBgnde" id="defandroid" value="<c:out value='${result.ntceBgnde}'/>" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
							</div>
							<div class="uss-regist" data-inline="true">
								<label for="ntceEndde">종료일자</label><br>
					        	<input name="ntceEndde" id="defandroid" value="<c:out value='${result.ntceEndde}'/>" type="date" data-role="datebox" data-options='{"mode": "datebox"}'>
							</div>
						</c:when>
					</c:choose>
				  	<c:if test="${anonymous == 'true'}"><!-- 익명글 -->
						<div class="uss-regist" data-inline="true">
							<label for="ntcrNm"><strong>작성자</strong></label><br>
			        		<input name="ntcrNm" type="text" id="ntcrNm" maxlength="10" size="20" value="<c:out value='${result.ntcrNm}'/>" >
						</div>
						<div class="uss-regist" data-inline="true">
							<label for="password"><strong>비밀번호</strong></label><br>
				        	<input name="password" type="password" id="password" maxlength="20" autocomplete="off"  >
						</div>
					</c:if>
				</div>
	            <!-- <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/> -->
	            <!-- 잠시적으로 bbsId=BBSMSTR_000000000550 mappping java에서 -->
	            <!-- <input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>"/> -->
				<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>"/>
				<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>"/>
				<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>"/>
				<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>"/>
				<!-- <input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>"/>
				<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>"/> -->
				<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>"/>
				
				<input type="hidden" name="cal_url" value="${pageContext.request.contextPath}/sym/cmm/EgovNormalCalPopup.do"/>
				<input type="hidden" name="authFlag" value="<c:out value='${bdMstr.authFlag}'/>"/>
				
				<c:if test="${anonymous != 'true'}">
					<input type="hidden" name="ntcrNm" value="dummy">   <!-- validator 처리를 위해 지정 -->
					<input type="hidden" name="password" value="dummy"> <!-- validator 처리를 위해 지정 -->
				</c:if>
				
				<c:if test="${bdMstr.bbsAttrbCode != 'BBSA01'}">
					<input name="ntceBgnde" type="hidden" value="10000101">
					<input name="ntceEndde" type="hidden" value="99991231">
				</c:if>
				
				<div class="ui-grid-a uss-clear">	
					<div class="ui-block-a"><a href="javascript:fn_egov_regist_notice();" data-role="button" data-theme="b">수정</a></div>
					<div class="ui-block-b"><a href='javascript:fn_showList();' data-role="button" data-theme="b">목록</a></div>
				</div>	
	        </div>
	    
	    </form> 
                
	
			<!-- footer start -->
			<div data-role="footer"  data-theme="a" data-position="fixed">
				<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
			</div>
			<!-- footer end -->
</div>	
	
</body>

</html>