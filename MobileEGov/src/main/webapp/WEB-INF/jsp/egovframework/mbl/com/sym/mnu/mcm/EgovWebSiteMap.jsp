<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>사이트맵</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 	    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/sym/mnu/mcm/EgovMblMenuCreatSiteMap.js"></script>
				
	</head>
	
	<body>
	
		<!-- 사이트맵 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" data-ajax="false">홈</a>
			    <h1>사이트맵</h1>
			    <a href="${pageContext.request.contextPath}/sym/mnu/mcm/EgovSiteMapng.mdo" data-icon="forward" rel="external">Type B</a>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content" class="com-siteContent">
				<!--
				<div data-role="collapsible-set">
					
				</div> -->
				<div class="uss-sitemap">
					<p><span>사용자지원</span></p>
					<dl>
						<dt>설문관리</dt>
						<dd><a href="${pageContext.request.contextPath}/uss/olp/qnn/EgovQustnrRespondInfoManageListView.mdo" data-role="none" rel="external">설문참여</a></dd>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/sam/stp/StplatListInqire.mdo" data-role="none" rel="external">약관관리</a></dt>
					</dl>
					<dl>
						<dt>상담관리</dt>
						<dd><a href="${pageContext.request.contextPath}/uss/olp/cns/CnsltListInqire.mdo" data-role="none" rel="external">상담목록조회</a></dd>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/olh/hpc/HpcmListInqire.mdo" data-role="none" rel="external">도움말관리</a></dt>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/olh/wor/WordDicaryListInqire.mdo" data-role="none" rel="external">용어사전관리</a></dt>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/olh/faq/FaqListInqire.mdo" data-role="none" rel="external">FAQ관리</a></dt>
					</dl>
					<dl>
						<dt>Q&A관리</dt>
						<dd><a href="${pageContext.request.contextPath}/uss/olh/qna/QnaListInqire.mdo" data-role="none" rel="external">Q&A목록조회</a></dd>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/ion/nws/NewsInfoListInqire.mdo" data-role="none" rel="external">뉴스관리</a></dt>
					</dl>
					<dl>
						<dt>사이트관리</dt>
						<dd><a href="${pageContext.request.contextPath}/sym/mnu/mcm/EgovWebSiteMapView.mdo" data-role="none" rel="external">사이트맵</a></dd>
					</dl>
					<dl>
						<dt class="link"><a href="${pageContext.request.contextPath}/uss/ion/rec/RecomendSiteListInqire.mdo" data-role="none" rel="external">추천사이트관리</a></dt>
					</dl>
					<p><span>협업</span></p>
					<dl>
						<dt>게시판</dt>
						<dd><a href="${pageContext.request.contextPath}/cop/bbs/SelectBBSMasterInfs.do" data-role="none" rel="external">게시판목록조회</a></dd>
						<dd><a href="${pageContext.request.contextPath}/cop/com/selectBBSUseInfs.do" data-role="none" rel="external">게시판사용정보조회</a></dd>
					</dl>
					<dl>
						<dt>커뮤니티/동호회</dt>
						<dd><a href="${pageContext.request.contextPath}/cop/cmy/CmmntyMainPage.mdo?cmmntyId=CMMNTY_0000000000001" data-role="none" rel="external">커뮤니티목록조회</a></dd>
					</dl>
					<dl>
						<dt>행사/이벤트/캠페인</dt>
						<dd><a href="${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo" data-role="none" rel="external">행사/이벤트/캠페인조회</a></dd>
					</dl>
					<dl>
						<dt>일정관리</dt>
						<dd><a href="${pageContext.request.contextPath}/cop/smt/sdm/EgovDeptSchdulManageDailyList.mdo" data-role="none" rel="external">부서일정관리</a></dd>
						<dd><a href="${pageContext.request.contextPath}/cop/smt/sim/EgovIndvdlSchdulManageDailyList.mdo" data-role="none" rel="external">일정관리</a></dd>
						<dd><a href="${pageContext.request.contextPath}/cop/smt/sam/EgovAllSchdulManageList.mdo" data-role="none" rel="external">전체일정</a></dd>
						<dd><a href="${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageList.mdo" data-role="none" rel="external">일지관리</a></dd>
					</dl>
				</div>
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h1>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h1>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 사이트맵 List end -->		
		
	</body>
	
</html>
