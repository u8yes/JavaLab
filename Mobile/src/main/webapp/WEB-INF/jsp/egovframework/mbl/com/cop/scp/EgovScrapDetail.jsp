<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<!DOCTYPE html>
<html>
<head> 
    <title>스크랩 상세조회</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        
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
			function fn_egov_select_scrapList(pageNo) {
				document.frm.pageIndex.value = pageNo; 
				document.frm.action = "${pageContext.request.contextPath}/cop/scp/selectScrapList.mdo";
				document.frm.submit();
			}
			function fn_egov_select_scrapList() {
				document.frm.action = "${pageContext.request.contextPath}/cop/scp/selectScrapList.mdo";
				document.frm.submit();
			}
			function fn_egov_delete_scrap() {	
				jConfirm('삭제하시겠습니까?', '알림', 'a', function(r) {
					if(r){
							document.frm.action = "${pageContext.request.contextPath}/cop/scp/deleteScrap.mdo";
							document.frm.submit();
					    }
			    });	
			}
			
			function fn_egov_moveUpdt_scrap() {
				document.frm.action = "${pageContext.request.contextPath}/cop/scp/forUpdateScrap.mdo";
				document.frm.submit();			
			}

			function fn_showList() {
			
				document.frm.action = "${pageContext.request.contextPath}/cop/scp/selectScrapList.mdo";
				document.frm.submit();
				
			}
		-->
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/cop/bbs/EgovBBSMng.js"></script>
		<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
		
</head>

<body>

<form name="frm" method="post" action="">
	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	<input type="hidden" name="bbsId" value="<c:out value='${scrap.bbsId}'/>" >
	<input type="hidden" name="nttId" value="<c:out value='${scrap.nttId}'/>" >
	<input type="hidden" name="scrapId" value="<c:out value='${scrap.scrapId}'/>" >
</form>

<!-- 메인 페이지 -->
<div data-role="page" id="NoticeInire" data-url="page_main" >

	<div data-role="header">
		<a href="javascript:fn_showList();" data-icon="arrow-l" rel="external">뒤로</a>
		<h1>스크랩 상세조회</h1> 
	</div>
	
	
	<div data-role="content" class="com-copContent">
	
		<ul class="uss-hpcDetail">
			<li>
				<span class="uss-tit">스크랩명</span>
				<span class="uss-con"><c:out value="${scrap.scrapNm}"/></span>
			</li>
			<li>
				<span class="uss-tit">스크랩 작성자</span>
				<span class="uss-con"><label for="name-d"><c:out value="${scrap.frstRegisterNm}"/></label></span>
			</li>
			<li>
				<span class="uss-tit">스크랩 작성시간 </span>
				<span class="uss-con"><label for="name-d"><c:out value="${scrap.frstRegisterPnttm}"/></label></span>
			</li>
			<li>
				<span class="uss-tit">게시판 제목 </span>
				<span class="uss-con"><c:out value="${board.bbsNm}"/></span>
			</li>
			<li>
				<span class="uss-tit">게시물 제목 </span>
				<span class="uss-con"><label for="name-d"><c:out value="${board.nttSj}"/></label></span>
			</li>
			<li>
				<span class="uss-tit">게시물 작성자 </span>
					<span class="uss-con">
						<label for="name-d">                  
							<c:choose>
								<c:when test="${board.ntcrNm == ''}">
									<c:out value="${board.frstRegisterNm}"/>
								</c:when>
								<c:otherwise>
								    <c:out value="${board.ntcrNm}"/>
								</c:otherwise>
							</c:choose>
						</label>
					</span>
			</li>
			<li>
				<span class="uss-tit">게시물 작성시간 </span>
				<span class="uss-con"><c:out value="${board.frstRegisterPnttm}"/></span>
			</li>
			<li>
				<span class="uss-tit">게시물 조회수 </span>
				<span class="uss-con"><label for="name-d"><c:out value="${board.inqireCo}"/></label></span>
			</li>
			<li>
				<span class="uss-tit">글내용 </span>
			</li>		
			<li class="uss-contentsView">
				<c:out value="${fn:replace(board.nttCn , crlf , '<br>')}"  escapeXml="false"/>  
			</li>
		</ul>
	</div>

	<c:if test="${useComment == 'true'}">
		<c:import url="${pageContext.request.contextPath}/cop/cmt/selectCommentList.mdo" charEncoding="utf-8">
			<c:param name="type" value="body"/>
		</c:import>
	</c:if>
	<div class="ui-grid-b">
						<div class="ui-block-a"><a href="javascript:fn_egov_moveUpdt_scrap(1)" data-role="button" data-theme="b" data-ajax="false">수정</a></div>
						<div class="ui-block-b"><a href='javascript:fn_egov_delete_scrap(1)' data-role="button" data-theme="b">삭제</a></div>
						<div class="ui-block-c"><a href='javascript:fn_egov_select_scrapList()' data-role="button" data-theme="b">목록</a></div>
	</div>

	<div data-role="footer" data-theme="c">	
		<div data-role="controlgroup"  class="com-egovFooterBtn">
			<c:if test="${scrap.frstRegisterId == sessionUniqId}">
				<form name="modifyFrm" method="post" action="${pageContext.request.contextPath}/cop/scp/forUpdateScrap.mdo">
					<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
					<input type="hidden" name="bbsId" value="<c:out value='${scrap.bbsId}'/>" >
					<input type="hidden" name="nttId" value="<c:out value='${scrap.nttId}'/>" >
					<input type="hidden" name="scrapId" value="<c:out value='${scrap.scrapId}'/>" > 
				</form>
			</c:if>
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