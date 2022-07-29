<%
 /**
  * @Class Name : EgovSyncList.jsp
  * @Description : EgovSyncList 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.25	서형주		최초 생성
  *
  *  @author 서형주 
  *  @since 2011.08.25
  *  @version 1.0
  *  @see
  *  
  */
%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>EgovSyncList</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>
<script type="text/javaScript" language="javascript">

/*********************************************************
 * 초기화
 ******************************************************** */
function fn_egov_initl_Synclist(){

    // 첫 입력란에 포커스..
    document.searchVO.searchKeyword.focus();

}

/*********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function fn_egov_select_linkPage(pageNo){
    
    document.searchVO.pageIndex.value = pageNo;
    document.searchVO.action = "<c:url value='/mbl/com/syn/selectSyncList.mdo'/>";
    document.searchVO.submit();
    
}

/*********************************************************
 * 조회 처리 함수
 ******************************************************** */
function fn_egov_search_Sync(){

    document.searchVO.pageIndex.value = 1;
    document.searchVO.submit();
    
}

/*********************************************************
 * 등록 처리 함수
 ******************************************************** */
function fn_egov_regist_Sync(){

    document.searchVO.action = "<c:url value='/mbl/com/syn/goSyncRegist.mdo'/>";
    document.searchVO.submit(); 
    
}

/*********************************************************
 * 수정 처리 함수
 ******************************************************** */
function fn_egov_updt_Synclist(){

    document.searchVO.action = "<c:url value='/mbl/com/syn/goSyncUpdt.mdo'/>";
    document.searchVO.submit(); 

}
/* ********************************************************
 * 상세화면 처리 함수
 ******************************************************** */
function fn_egov_inquire_Synclistdetail(sn) {       

    // 순번
    document.searchVO.sn.value = sn;
    document.searchVO.action = "<c:url value='/mbl/com/syn/selectSync.mdo'/>";
    document.searchVO.submit(); 
                
}


</script>
</head>
<body onLoad="fn_egov_initl_Synclist();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/syn/selectSyncList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents">
	
	<form name="searchVO" action="<c:url value='/mbl/com/syn/selectSyncList.mdo'/>" method="post">
	<table width="100%" cellpadding="8" class="table-search" border="0" summary="리스트 조회기능을 제공한다.">
	 <tr>
	  <td width="40%" class="title_left">
	   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀이미지">&nbsp;동기화서비스목록조회</td>
	  <th>
	  </th>
	  <td width="10%">
	    <select name="searchCondition" class="select" title="검색조건구분">
	           <option selected value=''>--선택하세요--</option>
	           <option value="mberId" <c:if test="${searchVO.searchCondition == 'mberId'}">selected="selected"</c:if> >회원ID</option>
	           <option value="syncSj" <c:if test="${searchVO.searchCondition == 'syncSj'}">selected="selected"</c:if> >제목</option>        
	       	   <option value="syncAll" <c:if test="${searchVO.searchCondition == 'syncAll'}">selected="selected"</c:if> >전체</option>  
	       </select>
	    </td>
	  
	  <td width="35%">
	    <input name="searchKeyword" type="text" size="35" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="35" title="검색키워드"> 
	  </td>
	  
	  <th width="10%">
	   <table border="0" cellspacing="0" cellpadding="0" >
	    <tr> 
	      <td><span class="button"><input type="submit" value="<spring:message code="button.inquire"/>" onclick="fn_egov_search_Sync(); return false;"></span></td>
	      <td>&nbsp;&nbsp;</td>
	      <td>
	           <span class="button">
	           <a href="<c:url value='/mbl/com/mcg/goSyncRegist.mdo'/>"
	              onclick="fn_egov_regist_Sync(); return false;"><spring:message code="button.create"/></a>
	           </span>
	      </td>     
	      
	      <td>&nbsp;&nbsp;</td>
	        
	    </tr>
	   </table>
	  </th>  
	 </tr>
	</table>
	
	<table width="100%" cellspacing="0" cellpadding="0" border="0" >
	<tr>
	    <td height="3px"></td>
	</tr>
	</table>
	
	<table width="100%" cellpadding="8" class="table-line" border="0" summary="동기화서비스목록을조회한다.">
	<thead>
	<tr>      
	    <th scope="col" class="title" width="10%" nowrap>번호</th>        
	    <th scope="col" class="title" width="30%" nowrap>제목</th>        
	    <th scope="col" class="title" width="30%" nowrap>작성자</th>               
	    <th scope="col" class="title" width="30%" nowrap>생성일시</th>        
	</tr>
	</thead>
	
	 <c:if test="${fn:length(resultList) == 0}">
	  <tr> 
	    <td class="lt_text3" colspan=10>
	        <spring:message code="common.nodata.msg"/>
	    </td>
	  </tr>                                            
	 </c:if>
	    
	<tbody>      
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	  <tr> 
	        <td class="lt_text3"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>                 
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/syn/selectSync.mdo'/>"
	               onclick="fn_egov_inquire_Synclistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.syncSj}"/></a>
	        </td>
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/syn/selectSync.mdo'/>"
	               onclick="fn_egov_inquire_Synclistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.mberId}"/></a>
	        </td>
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/syn/selectSync.mdo'/>"
	               onclick="fn_egov_inquire_Synclistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.creatDt}"/></a>
	        </td>     
	  </tr>   
	</c:forEach>
	</tbody>  
	</table>
	
	
	<table width="100%" cellspacing="0" cellpadding="0" border="0" >
	<tr>
	    <td height="3px"></td>
	</tr>
	</table>
	
	<div align="center">
	    <div>
	        <ui:pagination paginationInfo = "${paginationInfo}"
	                type="image"
	                jsFunction="fn_egov_select_linkPage"
	              />
	    </div>
	</div>
	
	<input name="sn" type="hidden" value="0">
	
	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	<input name="deviceType" type="hidden" value="W">
	
	</form>
	
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>