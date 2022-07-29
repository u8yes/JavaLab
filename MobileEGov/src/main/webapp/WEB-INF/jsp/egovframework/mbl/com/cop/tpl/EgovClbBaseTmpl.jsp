<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<!DOCTYPE html>
<html>
<head>
	<title><c:out value='${clubVO.clbNm}'/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
	<script type="text/javascript">
	<!--
		function fn_egov_goCmmntyHome() {
			var url = "${pageContext.request.contextPath}/cop/cus/ClubMainContents.do";
			url += "?clbId=" + document.frm.clbId.value;
			document.getElementById("contentFrame").src = url;		
		}
		
		function fn_egov_loadMngrMenu(url) {
			url = url+"?trgetType=CLUB&trgetId="+document.frm.clbId.value;
			document.getElementById("contentFrame").src = url;		
		}
		
		function fn_egov_loadBdList(bbsId, bbsAttrbCode, bbsTyCode) {
			
			var url;
			if(bbsTyCode == 'BBST04'){
				url = "${pageContext.request.contextPath}/cop/bbs/selectGuestList.mdo?bbsId="+bbsId+"&bbsAttrbCode="+bbsAttrbCode;			
			}else if(bbsTyCode == 'BBST02'){ // 익명게시판의 경우 (2011.9.7 수정분)
				url = "${pageContext.request.contextPath}/cop/bbs/anonymous/selectBoardList.mdo?bbsId="+bbsId+"&bbsAttrbCode="+bbsAttrbCode;
			}else{
				url = "${pageContext.request.contextPath}/cop/bbs/selectBoardList.mdo?bbsId="+bbsId+"&bbsAttrbCode="+bbsAttrbCode;
			}
			
			location.href = url;
		}
		
		function fn_egov_registUser(clbId, cmmntyId) {

			document.frm.action = "${pageContext.request.contextPath}/cop/clb/insertClubUserBySelf.mdo?clbId="+clbId+"&cmmntyId="+cmmntyId;	
			document.frm.submit();

		}
		
		function fn_egov_deleteUser(clbId, cmmntyId) {

			document.frm.action = "${pageContext.request.contextPath}/cop/com/deleteClubUserBySelf.mdo?trgetId="+clbId+"&PopFlag=S";	
			document.frm.submit();

		}
	-->
	</script>
</head>
<body>

<!-- 모바일 페이지 start -->

<div data-role="page" >

<!-- header start -->
<div data-role="header" data-theme="z">
	<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" rel="external">홈</a>
	<h1><img src="${pageContext.request.contextPath}/images/egovframework/mbl/bod/logo.png" alt="logo"></h1>
	<div class="ui-body-a"><h3 class="uss-commTitle"><c:out value='${cmmntyVO.cmmntyNm}'/></h3></div>
</div>
<!-- header end -->

<!-- content start -->
<div data-role="content">
<form action="${pageContext.request.contextPath}/cop/cus/ClubMainContents.do" name="frm" method="post" >
	<input type="hidden" name="cmmntyId" value="<c:out value='${clubVO.cmmntyId}'/>"/>
	<input type="hidden" name="clbId" value="<c:out value='${clubVO.clbId}'/>"/>
	<div style="visibility:hidden;display:none;"><input name="iptSubmit" type="submit" value="전송" title="전송"></div>
</form>
	<div data-role="collapsible-set">
		<div data-role="collapsible" data-collapsed="false">
			<h3>게시판 목록</h3>
				<!-- 게시판 목록 부분 : Start -->
				<ul data-role="listview" data-inset="true" data-theme="d">
					<c:forEach var="bbs" items="${bbsList}" varStatus="status">
	              	<li>
	              		<a href="javascript:fn_egov_loadBdList('<c:out value="${bbs.bbsId}"/>','<c:out value="${bbs.bbsAttrbCode}"/>','<c:out value="${bbs.bbsTyCode}"/>');">
						<c:out value="${bbs.bbsNm}"/></a>
					</li>
					</c:forEach> 
	            </ul>
				<!-- 게시판 목록 부분 : End -->			
		</div>
		<div data-role="collapsible" data-collapsed="true">
			<!-- 회원관련 부분 : Start -->
			<c:if test="${isAuthenticated=='Y'}">
				<h3>가입/탈퇴</h3>			
				<ul data-role="listview" data-inset="true" data-theme="d">
					<li>
						<c:choose>
							<c:when test="${clubUser.useAt=='Y'}">
								<div class="uss-regist" data-inline="true">
									<a href="${pageContext.request.contextPath}/cop/com/selectClubOprtrList.mdo?trgetId=${clubVO.clbId}&PopFlag=S;" data-role="button" data-rel="dialog" data-transition="pop">탈퇴신청</a>
						        </div>
							</c:when>
							<c:otherwise>
								<div class="uss-regist" data-inline="true">
								<a href="${pageContext.request.contextPath}/cop/clb/insertClubUserBySelf.mdo?clbId=${clubVO.clbId}&cmmntyId=${clubVO.cmmntyId}" data-role="button" data-rel="dialog" data-transition="pop">가입신청</a>
					        	</div>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</c:if>
			<!-- 회원관련 부분 : End -->
		</div>
	</div>
</div>
<!-- content end -->

<!-- footer start -->
<div data-role="footer" data-theme="z" data-position="fixed">
	<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
</div>
<!-- footer end -->

</div>
<!-- 모바일 페이지 end -->
</body>
</html>
