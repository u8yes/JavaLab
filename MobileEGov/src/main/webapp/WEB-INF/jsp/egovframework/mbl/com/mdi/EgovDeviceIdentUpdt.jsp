<%
 /**
  * @Class Name : EgovDeviceIdentUpdt.jsp
  * @Description : EgovDeviceIdentUpdt 화면
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
<title>모바일 기기 식별 정보 수정</title>

<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="deviceIdent" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javaScript" language="javascript">

	/* ********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_initl_deviceIdent(){
	
	    // 첫 입력란에 포커스..
	    document.deviceIdent.browserNm.focus();
	}
	
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_updt_deviceIdent(form){
	
		if (!validateDeviceIdent(form)) {
			return;				
		} else {
			form.action = "<c:url value='/mbl/com/mdi/updateDeviceIdent.mdo'/>";
			form.submit();
		}
	}
	
	/* ********************************************************
	 * 목록 으로 가기
	 ******************************************************** */
	function fn_egov_inqire_deviceIdentlist() {
	
		document.deviceIdent.action = "<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>";
		document.deviceIdent.submit();
	}

</script>
</head>

<body onLoad="fn_egov_initl_deviceIdent();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>" ><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents">
	
		<!-- 상단타이틀(파일첨부를 위한 폼명 및 Enctype 설정 -->
		<form:form commandName="deviceIdent" name="deviceIdent" action="" method="post"  enctype="multipart/form-data">
		
		<!-- 상단 타이틀  영역 -->
		<table width="100%" cellpadding="8" class="table-search" border="0">
		 <tr>
		  <td width="100%"class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="타이틀이미지">&nbsp;모바일 기기 식별 정보 수정</td>
		 </tr>
		</table>
		
		<!-- 줄간격조정  -->
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		    <td height="3px"></td>
		</tr>
		</table>
		
		<!-- 등록  폼 영역  -->
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="모바일 기기 식별 정보 수정테이블.">
			<tr> 
				<th width="20%" height="23" class="required_text" nowrap >
					브라우저
					<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
				</th>
				<td width="80%" nowrap>  
					<form:select path="browserCode" title="브라우저종류">
			            <c:forEach var="browserCmmCodeDetail" items="${browserCmmCodeDetailList}" varStatus="status">
			            	<option value="<c:out value="${browserCmmCodeDetail.code}"/>" <c:if test="${browserCmmCodeDetail.code == deviceIdent.browserCode}">selected</c:if>><c:out value="${browserCmmCodeDetail.codeNm}"/></option>
			            </c:forEach>
					</form:select>
					<div><form:errors path="browserCode"/></div>      	
				</td>
			</tr>
			<tr> 
				<th width="20%" height="23" class="required_text" nowrap >
					운영체제
					<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
				</th>
				<td width="80%" nowrap>  
					<form:select path="osCode" title="운영체제종류">
			            <c:forEach var="osCmmCodeDetail" items="${osCmmCodeDetailList}" varStatus="status">
			            	<option value="<c:out value="${osCmmCodeDetail.code}"/>" <c:if test="${osCmmCodeDetail.code == deviceIdent.osCode}">selected</c:if>><c:out value="${osCmmCodeDetail.codeNm}"/></option>
			            </c:forEach>
					</form:select>
					<div><form:errors path="osCode"/></div>      	
				</td>
			</tr>
			<tr> 
				<th width="20%" height="23" class="required_text" nowrap >
					User-Agent
					<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
				</th>
				<td width="80%" nowrap>
					<form:input path="uagentInfo" size="70" maxlength="1000"/>
					<div><form:errors path="uagentInfo"/></div>      	  	      	      	
				</td>
			</tr>
			<form:input path="recentCode" type="hidden" value="REG02"/>
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
		    <td><span class="button"><input type="submit" value="<spring:message code="button.save"/>" onclick="fn_egov_updt_deviceIdent(document.forms[0]); return false;"></span></td>
		    <td>&nbsp;&nbsp;</td>
		    <td>
		        <span class="button">
		        <a href="<c:url value='/mbl/com/mdi/selectDeviceIdentList.mdo'/>"
		           onclick="javascript:fn_egov_inqire_deviceIdentlist(); return false;"><spring:message code="button.list"/></a>
		        </span> 
		    </td>
		        
		</tr>
		</table>
		
		<input name="sn" type="hidden" value="${deviceIdent.sn}">
		</form:form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>