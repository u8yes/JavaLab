<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">

<%
 /**
  * @Class Name : EgovPhotoRegist.jsp
  * @Description : EgovPhotoRegist 화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2011.08.11	정홍규		최초 생성
  *
  *  @author 정홍규 
  *  @since 2011.08.11
  *  @version 1.0
  *  @see
  *  
  */
%>

<%@ page contentType="text/html; charset=utf-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<html>
<head>
<title>사진 정보 등록</title>

<meta http-equiv="content-type" content="text/html; charset=utf-8" >
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/button.css'/>"/>

<!-- 신규공통컴포넌트 import -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomdAdmin.css'/>"/>

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="photo" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javaScript" language="javascript">
	
	/* ********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_initl_photo(){
	
		// 첫 입력란에 포커스..
		document.photo.photoSj.focus();
	
	}
	
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_regist_photo(form){

		var aa = document.getElementById("egovComFileUploader");
		var bb = document.getElementById("egovComFileList");
		
		// 파일 Validator
		var newFileNum;		
		if(document.getElementById('egovComFileList') != null){
			newFileNum = document.getElementById('egovComFileList').childNodes.length;
		}else{
			newFileNum = 0;
		}
		if(newFileNum <= 0){
			alert("첨부파일은 필수 입력입니다.");
			return;
		}
		
		if (!validatePhoto(form)) {
			return;	
		} else {
			form.action = "<c:url value='/mbl/com/mpa/insertPhoto.mdo'/>";
			form.submit();
		} 		
		
	}
	
	/* ********************************************************
	 * 목록 으로 가기
	 ******************************************************** */
	function fn_egov_inqire_photolist() {
	
		document.photo.action = "<c:url value='/mbl/com/mpa/selectPhotoList.mdo'/>";
		document.photo.submit();
			
	}

</script>
</head>

<body onLoad="fn_egov_initl_photo();">

	<!-- header Start -->
	<div id="header">
		<a href="<c:url value='/index.jsp'/>"><span class="btnHome"></span></a>
		<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
		<a href="<c:url value='/mbl/com/mpa/selectPhotoList.mdo'/>"><span class="btnBack"></span></a>
	</div>
	<!-- header End -->

	<DIV id="content" class="contents">

	<!-- ------------------------------------ 상단타이틀(파일첨부를 위한 폼명 및 Enctype 설정 ---------------------------->
	<form:form commandName="photo" name="photo" action="" method="post" enctype="multipart/form-data"> 
	
	<!-- ----------------------------- 첨부파일 갯수를 위한 hidden --------------------------->	
	<input type="hidden" name="posblAtchFileNumber" id="posblAtchFileNumber" value="1"/>  
	
	<!-- ----------------- 상단 타이틀  영역 -->
	<table width="100%" cellpadding="8" class="table-search" border="0">
		<tr>
			<td width="100%"class="title_left">
				<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/tit_icon.gif'/>" width="16" height="16" hspace="3" align="left" alt="사진 정보 등록">&nbsp;사진 정보 등록
			</td>
		</tr>
	</table>
	
	<!-- ------------------------------------------------------------------ 줄간격조정  -->
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td height="3px"></td>
		</tr>
	</table>
	
	<!-- ------------------------------------------------------------------ 등록  폼 영역  -->
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register">
		<tr> 
			<th width="20%" height="23" class="required_text" nowrap >
				사진 제목
				<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
			</th>
			<td width="80%" nowrap>  
				<form:input path="photoSj" size="70" maxlength="100"/>
				<div><form:errors path="photoSj"/></div>      	  	      	      	
			</td>
		</tr>
	   
		<!-------------------- 첨부파일 테이블 레이아웃 설정 Start..-------------------------------------------->
		<tr>
			<th width="20%" height="23" class="required_text" nowrap >
				사진 파일
				<img src="<c:url value='/images/egovframework/mbl/mcomd/icon/required.gif'/>" width="15" height="15" alt="필수입력사항표시">
			</th>
			<td colspan="3">
			
			<table width="580px" cellspacing="0" cellpadding="0" border="0" align="center" class="UseTable">
				<tr>
					<td>
						<input name="file_1" id="egovComFileUploader" type="file" title="첨부파일 입력"/>
					</td>
				</tr>
				
				<tr>
					<td>
				    	<div id="egovComFileList"></div>
					</td>
				</tr>
			</table>		      
			</td>
		</tr>
	  <!-------------------- 첨부파일 테이블 레이아웃 End.--------------------------------------------------->
	</table>
	
	<!-------------------- 첨부파일 업로드 가능화일 설정 Start..-------------------------------------------->  
	<script type="text/javascript">

		var maxFileNum = document.getElementById('posblAtchFileNumber').value;
		   
		   if(maxFileNum==null || maxFileNum==""){
			   
		     maxFileNum = 1;
		     
		    }		    
		        
		   var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum );
		   
		   multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
	   	
	</script> 
	<!-- ------------------ 첨부파일 업로드 가능화일 설정 End.----------------------------------------------->
	  
	<!-- ------------------------------------------------------------------ 줄간격조정  -->
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td height="5px"></td>
		</tr>
	</table>
		
	<!-- ------------------------------------------------------------------ 목록/저장버튼  -->
	<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr> 
			<td>
				<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="저장버튼이미지">
			</td>
			<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
				<a href="JavaScript:fn_egov_regist_photo(document.forms[0]);">저장</a>
			</td>
			<td>
				<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="저장버튼이미지">
			</td>
			<td>&nbsp;</td>  
			<td>
				<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_left.gif'/>" width="8" height="20" alt="목록버튼이미지">
			</td>
			<td background="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_bg.gif'/>" class="text_left" nowrap>
				<a href="javascript:fn_egov_inqire_photolist();">목록</a> 
			</td>
			<td>
				<img src="<c:url value='/images/egovframework/mbl/mcomd/button/bu2_right.gif'/>" width="8" height="20" alt="목록버튼이미지">
			</td>              
		</tr>
	</table>
	    
	</form:form>
	</DIV>
	
	<!-- footer Start-->
	<div id="footer">
		<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
	</div>
	<!-- footer End -->
</body>
</html>