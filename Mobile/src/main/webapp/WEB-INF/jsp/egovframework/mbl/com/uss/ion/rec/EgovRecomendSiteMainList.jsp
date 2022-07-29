<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>추천사이트 목록조회</title>
		
	</head>
	
	<body>

	<div>
		<ul data-inset="true">
			<c:choose>
				<c:when test="${empty resultList}">
					<li><spring:message code="common.nodata.msg"/></li>
				</c:when>
				<c:otherwise>
					<c:forEach items="${resultList}" var="result" varStatus="status">
					<li class="uss-myList">
						<a href="${pageContext.request.contextPath}/uss/ion/rec/RecomendSiteDetailInqire.mdo?recomendSiteId=${result.recomendSiteId}">
							<dl class="uss-mylistView">
							<dt>${result.recomendSiteNm}</dt>
							<dd>${result.recomendSiteUrl}</dd>
							</dl>
						</a>
					</li>
					</c:forEach>				
				</c:otherwise>	
			</c:choose>
		</ul>
	</div>
	
	<div class="uss-btnG">
		<a href="${pageContext.request.contextPath}/uss/ion/rec/RecomendSiteListInqire.mdo" data-role="button" data-theme="b" data-ajax="false" class="btnGogo">바로가기</a>
	</div>

	</body>
	
</html>
