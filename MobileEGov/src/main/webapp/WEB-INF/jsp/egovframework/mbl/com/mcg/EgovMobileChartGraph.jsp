<%
 /**
  * @Class Name : EgovMobileChartGraphList.jsp
  * @Description : EgovMobileChartGraphList 화면
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

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html> 
<html> 
<head>
<title>모바일 화면</title> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        
	<!-- eGovFrame Common import -->
	<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css'/>"/>
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js'/>"></script>
	
		
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js'/>"></script>
   
    <!-- 신규공통컴포넌트 import -->
    <link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomd.css'/>"/>
	
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/com/mcg/mcg.js'/>"></script>

</head>

<body onLoad="fn_egov_initl_mobilechartgraphlist();">

	<!-- 차트/그래프 초기 페이지 -->
	<div id="ChartGraphIntro" data-role="page" data-theme="d">
		
		<div data-role="header" data-position="inline" data-theme="z">
			<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
			<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
			<div class="ui-body-a mcomd-title"><h3>모바일 차트/그래프</h3></div>
		</div>
		
		<div data-role="content">
			<div data-role="none">
				<p class="chartTitle">차트를 선택 하세요</p> 
				<select name="select-chart" id="select-chart" multiple="multiple" data-native-menu="false">
				</select>
				<a href="javascript:fn_egov_show_chartGraph();" data-role="button">차트/그래프 보기</a>
			</div>
		</div>
		
		<div data-role="footer" data-theme="z" data-position="fixed" class="egovBar">
			<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
		</div>
	</div>

	<!--차트/그래프 페이지 -->
	<div id="ChartGraph" data-role="page" data-theme="d">
		
		<div data-role="header" data-position="inline" data-theme="z">
			<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
			<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
			<a href="<c:url value='/mbl/com/mcg/goMobileChartGraph.mdo'/>" data-ajax="false" data-icon="back" class="ui-btn-right">이전</a>
			<div class="ui-body-a mcomd-title"><h3>모바일 차트/그래프</h3></div>
			<div data-role="tabs">
				<ul id="ChartGraphNavi">
				</ul>
			</div>
		</div>
	
		<div data-role="content">
			<div id="lineChartGraph">
				<canvas id="lineChartGraphCanvas" width="500" height="400">
				</canvas>
			</div>
			<div id="columnChartGraph">
				<canvas id="columnChartGraphCanvas" width="500" height="400">
				</canvas>
			</div>
			<div id="pieChartGraph">
				<canvas id="pieChartGraphCanvas" width="500" height="400">
				</canvas>
			</div>
			<div id="scatterChartGraph">
				<canvas id="scatterChartGraphCanvas" width="500" height="400">
				</canvas>
			</div>
			<div id="barChartGraph">
				<canvas id="barChartGraphCanvas" width="500" height="400">
				</canvas>
			</div>
		</div>
	
		<div data-role="footer" data-theme="z" data-position="fixed" class="egovBar">
			<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
		</div>
	</div>
	
</body>
</html>