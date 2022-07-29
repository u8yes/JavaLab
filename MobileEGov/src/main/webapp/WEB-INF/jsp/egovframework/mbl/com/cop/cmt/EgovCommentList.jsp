<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
		
	<!--
	<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css'/>"/>
  	<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/com/uss/ussCommon.css'/>"/>
		
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js'/>"></script>   	
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js'/>"></script>
	-->

<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
<c:if test="${resultCnt> 0}">

<!-- 댓글 삽입 페이지 -->
<form name="commentList">
	<div class="com-commentT">댓글 <span><c:out value="${resultCnt}"/></span></div>

	<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" >
	<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" >
	<input type="hidden" name="parnts" value="<c:out value='${result.parnts}'/>" >
	<input type="hidden" name="sortOrdr" value="<c:out value='${result.sortOrdr}'/>" >
	<input type="hidden" name="replyLc" value="<c:out value='${result.replyLc}'/>" >
	<input type="hidden" name="nttSj" value="<c:out value='${result.nttSj}'/>" >

	<input name="confirmPassword" type="hidden">
	<input name="commentNo" type="hidden">
	<input name="modified" type="hidden">

	<c:if test="${anonymous != 'true'}">
	<input type="hidden" name="commentPassword" value="dummy">	<!-- validator 처리를 위해 지정 -->
	</c:if>

	<c:forEach var="result" items="${resultList}" varStatus="status">        
		<div class="com-commentList">
			<p>	
				<c:choose>
				   <c:when test="${not empty result.wrterNm}">
				       <b><c:out value="${result.wrterNm}"/></b>&nbsp;
				   </c:when>
				   <c:otherwise>
				       <b><c:out value="${result.frstRegisterNm}"/></b>&nbsp;
				   </c:otherwise>
				</c:choose>
			</p>
            <p class="com-date">
                 <c:out value="${result.frstRegisterPnttm}"/>
            </p>
            <p class="com-delete">
                 <a href="javascript:fn_egov_deleteCommentList('<c:out value="${result.commentNo}"/>', '<c:out value="${status.index}"/>'); " data-ajax="false" ><span>삭제</span></a>
            </p>
            <p class="com-modify">
            	 <a href="javascript:fn_egov_selectCommentForupdt2('<c:out value="${result.commentNo}"/>','<c:out value="${result.wrterNm}"/>', '<c:out value="${result.frstRegisterNm}"/>', '<c:out value="${result.commentCn}"/>', 'update');" data-ajax="false"><span>수정</span></a>
            </p>
			<c:if test="${anonymous == 'true'}">
			    <dl class="com-egovCommentPw">
			    	<dt>댓글 삭제하기</dt>
			    	<dd>비밀번호 <input name="testPassword" type="password" size="20" value="" maxlength="20" ></dd> 
			    </dl>
		    </c:if>
            <div class="com-commContent"><c:out value="${result.commentCn}"/></div>
				
		</div>          
	</c:forEach>
    
</form>
</c:if>