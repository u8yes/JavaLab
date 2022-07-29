<%
 /**
  * @Class Name : EgovMultimediaList.jsp
  * @Description : EgovMultimediaList 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.23	정홍규		최초 생성
  *
  *  @author 정홍규 
  *  @since 2011.08.23
  *  @version 1.0
  *  @see
  *  
  */
%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>EgovMultimediaList</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javaScript" language="javascript">
	
	/*********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_initl_multimedialist(){
	
	    // 첫 입력란에 포커스..
	    document.MultimediaListForm.searchKeyword.focus();
	    
	}
	
	/*********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function fn_egov_select_linkPage(pageNo){
	    
	    document.MultimediaListForm.pageIndex.value = pageNo;
	    document.MultimediaListForm.action = "<c:url value='/mbl/com/mlt/selectMultimediaList.mdo'/>";
	    document.MultimediaListForm.submit();
	    
	}
	
	/*********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function fn_egov_search_multimedia(){
	
	    document.MultimediaListForm.pageIndex.value = 1;
	    document.MultimediaListForm.submit();
	    
	}
	
	/*********************************************************
	 * 등록 처리 함수
	 ******************************************************** */
	function fn_egov_regist_multimedia(){
	
	    document.MultimediaListForm.action = "<c:url value='/mbl/com/mlt/goMultimediaRegist.mdo'/>";
	    document.MultimediaListForm.submit(); 
	    
	}
	
	/*********************************************************
	 * 수정 처리 함수
	 ******************************************************** */
	function fn_egov_updt_multimedialist(){
	
	    document.MultimediaListForm.action = "<c:url value='/mbl/com/mlt/goMultimediaUpdt.mdo'/>";
	    document.MultimediaListForm.submit(); 
	
	}
	/* ********************************************************
	 * 상세화면 처리 함수
	 ******************************************************** */
	function fn_egov_inquire_multimedialistdetail(sn) {       
	
	    // 순번
	    document.MultimediaListForm.sn.value = sn;
	    document.MultimediaListForm.action = "<c:url value='/mbl/com/mlt/selectMultimedia.mdo'/>";
	    document.MultimediaListForm.submit(); 
	                
	}
</script>
</head>

<body onLoad="fn_egov_initl_multimedialist();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mlt/selectMultimediaList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents">
	
	<form name="MultimediaListForm" action="<c:url value='/mbl/com/mlt/selectMultimediaList.mdo'/>" method="post">
	<table width="100%" cellpadding="8" class="table-search" border="0" summary="리스트 조회기능을 제공한다.">
	 <tr>
	  <td width="40%" class="title_left">
	   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀이미지"/>&nbsp;멀티미디어 정보 목록 조회</td>
	  <th>
	  </th>
	  <td width="10%">
	    <select name="searchCondition" class="select" title="검색조건구분">
	           <option selected value=''>--선택하세요--</option>
	           <option value="mberId"  <c:if test="${searchVO.searchCondition == 'mberId'}">selected="selected"</c:if> >작성자명</option>
	           <option value="mltmdSj" <c:if test="${searchVO.searchCondition == 'mltmdSj'}">selected="selected"</c:if> >멀티미디어 제목</option>
	           <option value="mltmdNm" <c:if test="${searchVO.searchCondition == 'mltmdNm'}">selected="selected"</c:if> >멀티미디어 구분</option>  
	           <option value="browserNm" <c:if test="${searchVO.searchCondition == 'browserNm'}">selected="selected"</c:if> >지원브라우저</option>           
	       </select>
	    </td>
	  
	  <td width="35%">
	    <input name="searchKeyword" type="text" size="35" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="35" title="검색키워드"> 
	  </td>
	  
	  <th width="10%">
	   <table border="0" cellspacing="0" cellpadding="0" >
	    <tr> 
	      <td><span class="button"><input type="submit" value="<spring:message code="button.inquire"/>" onclick="fn_egov_search_multimedia(); return false;"></span></td>
	      <td>&nbsp;&nbsp;</td>
	      <td>
	           <span class="button">
	           <a href="<c:url value='/mbl/com/mlt/goMultimediaRegist.mdo'/>"
	              onclick="fn_egov_regist_multimedia(); return false;"><spring:message code="button.create"/></a>
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
	
	<table width="100%" cellpadding="8" class="table-line" border="0" summary="멀티미디어목록을 제공한다.">
	<thead>
	<tr>      
	    <th scope="col" class="title" width="10%" nowrap>순번</th>        
	    <th scope="col" class="title" width="30%" nowrap>멀티미디어 제목</th>
	    <th scope="col" class="title" width="10%" nowrap>멀티미디어 구분</th> 
	    <th scope="col" class="title" width="30%" nowrap>지원브라우저</th>         
	    <th scope="col" class="title" width="20%" nowrap>생성일시</th>    
	</tr>
	</thead>
	
	 <c:if test="${fn:length(resultList) == 0}">
	  <tr> 
	    <td class="lt_text3" colspan="10">
	        <spring:message code="common.nodata.msg"/>
	    </td>
	  </tr>                                            
	 </c:if>
	    
	<tbody>      
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	
	  <tr> 
	        <td class="lt_text3"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>                 
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/mlt/selectMultimedia.mdo'/>"
	               onclick="fn_egov_inquire_multimedialistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.mltmdSj}"/></a>
	        </td>
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/mlt/selectMultimedia.mdo'/>"
	               onclick="fn_egov_inquire_multimedialistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.mltmdNm}"/></a>
	        </td>
	        <td class="lt_text3">
	            <a href="<c:url value='/mbl/com/mlt/selectMultimedia.mdo'/>"
	               onclick="fn_egov_inquire_multimedialistdetail('<c:out value="${resultInfo.sn}"/>'); return false;"><c:out value="${resultInfo.browserNm}"/></a>
	        </td>
	        <td class="lt_text3"><fmt:formatDate value="${resultInfo.creatDt}"  pattern="yyyy-MM-dd"/></td>     
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
	
	</form>
	
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>