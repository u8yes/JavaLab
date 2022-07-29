<% 
/**
 * @Class Name : EgovMmsAttachFileRegist.jsp
 * @Description : 첨부파일 등록
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.29   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.29
 *  @version 1.0 
 *  @see
 *  
 */
%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/cmm/com.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css'/>" rel="stylesheet" type="text/css">

<!-- 신규공통컴포넌트 import -->
<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>" ></script>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="atchFile" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">

	function fn_egov_select_mmsAttachFileList(pageNo) {
		document.atchFile.action = "<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>";
		document.atchFile.submit();
	}

	function fn_egov_regist_mmaAttachFile(){
		if(!validateAtchFile(document.atchFile)) {
			return;
		}

		if (!fn_egov_check_fileType(document.getElementById("egovComFileUploader").value)){
			alert("jpq, sis, mmf, k3g, skm 파일만 업로드 가능합니다.");
			return;
		}
		
	    document.atchFile.action = "<c:url value='/mbl/com/mms/insertMmsAttachFile.mdo'/>";
	    document.atchFile.submit();
	}

	// 파일 확장자 체크 함수
	function fn_egov_check_fileType(fileNm) {
		var lastIndex = fileNm.lastIndexOf('.');
		var fileType;

		if (lastIndex != -1) {
			fileType = fileNm.substring(lastIndex + 1, fileNm.len);

			if (fileType != 'jpg' && fileType != 'sis' && fileType != 'mmf' && fileType != 'k3g' && fileType != 'skm') {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	// 첨부파일 크기 validation check
	function fn_egov_check_fileValidation(flag) {
		if (flag == "movieFalse") {
			alert("300KB가 넘는 동영상 파일을 첨부할 수 없습니다.");
		} else if (flag == "otherFalse") {
			alert("20KB가 넘는 파일을 첨부할 수 없습니다.");
		}
	}
	
</script>
<title>MMS 첨부파일 등록</title>

<style type="text/css">
	h1 {font-size:12px;}
	caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}
</style>

</head>
<body onload="javascript:fn_egov_check_fileValidation('<c:out value="${fileValidation}"/>');">

<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	
	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mms/selectMmsAttachFileList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->
	
	<div id="content" class="contents2">
		<form commandName="atchFile" name="atchFile" action="" method="post" enctype="multipart/form-data"> 
		
		<!-- ----------------------------- 첨부파일 갯수를 위한 hidden --------------------------->	
		<input type="hidden" name="posblAtchFileNumber" id="posblAtchFileNumber" value="1"/>  
		
		<table width="100%" cellpadding="8" class="table-search" border="0">
		 <tr>
		  <td width="100%"class="title_left">
		   <img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" style="vertical-align: middle" alt="">
		   &nbsp;MMS 첨부파일 - 등록</td>
		 </tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="MMS 첨부파일 정보를 등록합니다.">
		<tbody>
		  <tr> 
		    <th width="20%" height="23" class="required_text" nowrap >제목
		    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
		    </th>
		    <td width="80%" nowrap>
		    	<input name="mmsAtchFileSj" type="text" style="width:50%;height:100%;" value='<c:out value="${attachFile.mmsAtchFileSj}"/>'/>
		    </td>
		  </tr>
		  <tr> 
		    <th width="20%" height="23" class="required_text" nowrap >첨부파일
		    	<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
		    </th>
		    <td width="80%" nowrap>
		    	<input name="file_1" id="egovComFileUploader" type="file" style="width:50%;height:100%;" title="첨부파일 입력"/>
		    </td>
		  </tr>
		</tbody>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
		    <td height="10"></td>
		  </tr>
		</table>
	  	<div align="center">
		<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr> 
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="등록버튼이미지">
				</td>
				<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
					<a href="JavaScript:fn_egov_regist_mmaAttachFile();">등록</a>
				</td>
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="등록버튼이미지">
				</td>
				<td>&nbsp;</td>  
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="목록버튼이미지">
				</td>
				<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
					<a href="javascript:fn_egov_select_mmsAttachFileList('<c:out value='${searchVO.pageIndex}'/>');">목록</a> 
					<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
	      			<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>">
	      			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>">
				</td>
				<td>
					<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="목록버튼이미지">
				</td>              
			</tr>
		</table>
		</div>
		</form>
	</div>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>