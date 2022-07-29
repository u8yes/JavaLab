<% 
/**
 * @Class Name : EgovMobileAttachFile.jsp
 * @Description : 첨부파일 선택
 * @Modification Information
 * @
 * @  수정일      수정자            수정내용
 * @ -------        --------    ---------------------------
 * @ 2011.08.30   조재만          최초 생성
 *
 *  @author 모바일 신규공통컴포넌트개발팀 조재만
 *  @since 2011.08.30
 *  @version 1.0 
 *  @see
 *  
 */
%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html> 
<html> 
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    	<title>첨부파일 선택</title> 
        
        
        <!-- eGovFrame Common import -->
        <link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css'/>"/>
        <link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css'/>"/>
        <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js'/>"></script>
        
		
        <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js'/>"></script>
		
		<!-- 신규공통컴포넌트 import -->
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomd.css'/>"/>

    </head>

	<body>
		<!-- 첨부파일 선택 페이지 start -->
		<div id="selectAtchFile" data-role="page" data-theme="d">

			<!-- header start -->
			<div data-role="header" data-theme="z">
	   			<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
				<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
				<a href="#writeMms" data-icon="back" class="ui-btn-right">이전</a>
				<div class="ui-body-a mcomd-title"><h3>첨부파일 선택</h3></div>
			</div>
            <!-- header end -->
			
			<!-- contents start -->
			<div data-role="content">
				<ul data-role="listview">
				
				</ul>
				
				<div id="pageNavi" class="com-egovPaging">
				
				</div>
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-theme="z" data-position="fixed" class="egovBar">
				<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
			</div>
			<!-- footer end -->

		</div>
		<!-- 첨부파일 선택 페이지 end -->	
	
	</body>

</html>