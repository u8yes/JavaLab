<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>사이트맵</title>
	
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/com/sym/mnu/mcm/EgovMblMenuCreatSiteMap.js"></script>
		
		<script type="text/javaScript" language="javascript" defer="defer">
			<!--		

       			$.getJSON("${pageContext.request.contextPath}/sym/mnu/mcm/EgovSiteMapngActor.mdo", function(json){
           			
           			var arr = new Array();
           			for(var i=0; i<json.menulist.length; i++) {
               			var menu = json.menulist[i];
               			arr[i] = menu.menuNo + '|' + menu.upperMenuId + '|' + menu.menuNm + '|' + menu.menuOrdr + '|' + menu.chkURL + '|';
           			}


					var html = createTree(arr, "fn_link_url");
					$('div[data-role="collapsible-set"]').append(html);

					$('#list').find('div[data-role="collapsible"]').collapsible({theme:'c',refresh:true});
					$('ul').listview({refresh:true});
					
					
       		 	});

   		 		function fn_link_url(url) {
   	   		 		
	   		 		var lastIndex = url.lastIndexOf(".");
					
					if(url.substr(lastIndex+1, 3) == "mdo") {
						$.mobile.changePage(url);
					}
					else {
						jAlert("모바일에서는 지원하지 않는 페이지입니다.", "알림", "a");
					}
   		 		}
			        	
        	-->
		</script>
				
	</head>
	
	<body>
	
		<!-- 사이트맵 List start -->
		<div id="list" data-role="page">

			<!-- header start -->
			<div data-role="header">
				<a href="${pageContext.request.contextPath}/index.jsp" data-icon="home" data-ajax="false">홈</a>
			    <h1>사이트맵</h1>
			    <a href="${pageContext.request.contextPath}/sym/mnu/mcm/EgovWebSiteMapView.mdo" data-icon="forward" rel="external">Type A</a>
			</div>
			<!-- header end -->
			
			<!-- contents start -->
			<div data-role="content" class="com-siteContent">
				
				<div data-role="collapsible-set">
					
				</div>
				
			</div>
			<!-- contents end -->

			<!-- footer start -->
			<div data-role="footer" data-position="fixed">
				<h1>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h1>
			</div>
			<!-- footer end -->
			
		</div>
		<!-- 사이트맵 List end -->		
		
	</body>
	
</html>
