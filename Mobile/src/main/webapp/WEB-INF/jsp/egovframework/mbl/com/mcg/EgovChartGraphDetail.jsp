<%
/**
 * @Class Name : EgovChartGraphDetail.jsp
 * @Description : EgovChartGraphDetail 화면
 * @Modification Information
 * @
 * @  수정일   	수정자		수정내용
 * @ ----------	------		---------------------------
 * @ 2011.08.17	정홍규		최초 생성
 *
 *  @author 정홍규 
 *  @since 2011.08.17
 *  @version 1.0
 *  @see
 *  
 */
%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>차트/그래프 데이터 상세 조회</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript">
	/* ********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_initl_chartGraph(){

	}

	/* ********************************************************
	 * 목록 으로 가기
	 ******************************************************** */
	function fn_egov_inqire_chartGraphlist() {

	    document.ChartGraphForm.action = "<c:url value='/mbl/com/mcg/selectChartGraphList.mdo'/>";
	    document.ChartGraphForm.submit();
	}

	/* ********************************************************
	 * 수정처리화면
	 ******************************************************** */
	function fn_egov_updt_chartGraph(sn){

	    // Update하기 위한 키값(sn)을 셋팅
	    document.ChartGraphForm.sn.value = sn;  
	    document.ChartGraphForm.action = "<c:url value='/mbl/com/mcg/goChartGraphUpdt.mdo'/>";
	    document.ChartGraphForm.submit();   
	}

	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
	function fn_egov_delete_chartGraph(sn){

	    if  (confirm("<spring:message code="common.delete.msg"/>"))    {   
	        // Delete하기 위한 키값(newsId)을 셋팅
	        document.ChartGraphForm.sn.value = sn;  
	        document.ChartGraphForm.action = "<c:url value='/mbl/com/mcg/deleteChartGraph.mdo'/>";
	        document.ChartGraphForm.submit();
	    }    
	}
</script>
</head>

<body onLoad="fn_egov_initl_chartGraph();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mcg/selectChartGraphList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents2">
		<!-- 상단타이틀 -->
		<form name="ChartGraphForm" action="<c:url value='/mbl/com/mcg/selectChartGraph.mdo'/>" method="post">
			
			<!-- 상단 타이틀  영역 -->
			<table width="100%" cellpadding="8" class="table-search" border="0">
			 <tr>
			  <td width="100%"class="title_left">
			   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀">&nbsp;차트/그래프 데이터 상세 조회</td>
			 </tr>
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="3px"></td>
			</tr>
			</table>
			
			<!-- 등록  폼 영역  -->
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="차트/그래프 데이터 정보테이블.">
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >범례명&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			        <c:out value="${result.lgdNm}"/>  
			    </td>
			  </tr>
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >X축 값&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			    	<c:out value="${result.xaxis}"/>
			    </td>
			  </tr>
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >Y축 값&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			    	<c:out value="${result.yaxis}"/>
			    </td>
			  </tr>
			  <tr> 
			    <th height="23" class="required_text" nowrap >생성일시&nbsp;&nbsp;</th>
			    <td width="80%" nowrap>
			        <c:out value="${result.creatDt}"/>
			    </td>
			  </tr> 
			
			   
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="10px"></td>
			</tr>
			</table>
			
			<!-- 목록/저장버튼  -->
			<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr> 
			    <td>
			        <span class="button">
			        <a href="<c:url value='/mbl/com/mcg/updateChartGraph.mdo'/>"
			           onclick="fn_egov_updt_chartGraph('<c:out value="${result.sn}"/>'); return false;"><spring:message code="button.update"/></a>
			        </span> 
			    </td>
			    <td><span class="button"><input type="submit" value="<spring:message code="button.delete"/>" onclick="fn_egov_delete_chartGraph('<c:out value="${result.sn}"/>'); return false;"></span></td>
			    <td>
			        <span class="button">
			        <a href="<c:url value='/mbl/com/mcg/selectChartGraphList.mdo'/>"
			           onclick="fn_egov_inqire_chartGraphlist(); return false;"><spring:message code="button.list"/></a>
			        </span> 
			    </td>
			
			</tr>
			</table>
			
			<input name="sn" type="hidden" value="0">
		</form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>