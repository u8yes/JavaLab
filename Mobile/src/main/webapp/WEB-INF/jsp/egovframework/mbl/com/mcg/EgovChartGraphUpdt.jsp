<%
 /**
  * @Class Name : EgovChartGraphUpdt.jsp
  * @Description : EgovChartGraphUpdt 화면
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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<title>사진 정보 수정</title>

<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="chartGraph" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
/* ********************************************************
 * 초기화
	 ******************************************************** */
	function fn_egov_initl_chartGraph(){

	    // 첫 입력란에 포커스..
	    document.chartGraph.lgdNm.focus();
	}

	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_updt_chartGraph(form){

		if (!validateChartGraph(form)) {
			return;				
		} else {
			form.action = "<c:url value='/mbl/com/mcg/updateChartGraph.mdo'/>";
			form.submit();
		}
	}

	/* ********************************************************
	 * 목록 으로 가기
	 ******************************************************** */
	function fn_egov_inqire_chartGraphlist() {

		document.chartGraph.action = "<c:url value='/mbl/com/mcg/selectChartGraphList.mdo'/>";
		document.chartGraph.submit();
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
	
		<!-- 상단타이틀(파일첨부를 위한 폼명 및 Enctype 설정 -->
		<form:form commandName="chartGraph" name="chartGraph" action="" method="post"  enctype="multipart/form-data">
		
			<!-- 상단 타이틀  영역 -->
			<table width="100%" cellpadding="8" class="table-search" border="0">
			 <tr>
			  <td width="100%"class="title_left">
			   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀이미지">&nbsp;차트/그래프 데이터 수정</td>
			 </tr>
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="3px"></td>
			</tr>
			</table>
			
			<!-- 등록  폼 영역  -->
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="차트/그래프 데이터 수정테이블.">
			  <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >
			    	<label id="lgdNm" for="lgdNm">범례명</label>
			    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력" ></th>
			    <td width="80%" nowrap>
			        <form:input path="lgdNm" size="70" maxlength="70"/>
			        <div><form:errors path="lgdNm"/></div>                               
			    </td>
			  </tr>
			    <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >
			    	<label id="xaxis" for="xaxis">X축 값</label>
			    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력" ></th>
			    <td width="80%" nowrap>
			        <form:input path="xaxis" size="70" maxlength="70"/>
			        <div><form:errors path="xaxis"/></div>                               
			    </td>
			  </tr>
			    <tr> 
			    <th width="20%" height="23" class="required_text" nowrap >
			    	<label id="yaxis" for="yaxis">Y축 값</label>
			    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력" ></th>
			    <td width="80%" nowrap>
			        <form:input path="yaxis" size="70" maxlength="70"/>
			        <div><form:errors path="yaxis"/></div>                               
			    </td>
			  </tr>
			</table>
			
			<!-- 줄간격조정  -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
			    <td height="3px"></td>
			</tr>
			</table>
			
			<!-- 목록/저장버튼  -->
			<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr> 
			    <td><span class="button"><input type="submit" value="<spring:message code="button.save"/>" onclick="fn_egov_updt_chartGraph(document.forms[0]); return false;"></span></td>
			    <td>&nbsp;&nbsp;</td>
			    <td>
			        <span class="button">
			        <a href="<c:url value='/mbl/com/mcg/selectChartGraphList.mdo'/>"
			           onclick="javascript:fn_egov_inqire_chartGraphlist(); return false;"><spring:message code="button.list"/></a>
			        </span> 
			    </td>
			        
			</tr>
			</table>
			
			<input name="sn" type="hidden" value="${chartGraph.sn}">
		</form:form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>