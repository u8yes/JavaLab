<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>위치정보연계 - Daum Map</title>
	
		<!-- eGovFrame Common import -->
	    <link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css'/>"/>
	    <link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css'/>"/>
	    <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery-1.11.2.js'/>"></script>
	    
		
	    <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js'/>"></script>
	    <script type="text/javascript" src="<c:url value='/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js'/>"></script>
	
		<!-- 신규공통컴포넌트 import -->
		<link rel="stylesheet" href="<c:url value='/css/egovframework/mbl/mcomd/egovMcomd.css'/>"/>
		<script type="text/javascript" src="<c:url value='/js/egovframework/mbl/com/geo/geo.js'/>"></script>
	
		<!-- 다음맵 API -->
		<script type="text/javascript" src="http://apis.daum.net/maps/maps3.js?apikey="></script>
		
		<script type="text/javascript" language="javascript" defer="defer">
			var buildingList = new Array();
		
			$(document).on('pageshow', "#main", init);

			/*********************************************************
			 * 건물 위치정보 조회
			 ******************************************************** */
	        function init() {
	            if (!navigator.geolocation) {
	            	jAlert("This browser doesn't support geolocation", "GeoLocation", "a");
	            } else {
	                $.getJSON("<c:url value='/mbl/com/geo/selectMobileBuildingLocationInfoList.mdo'/>", $('#searchVO').serialize().replace(/%/g,'%25'), function(json){
	               		$("#searchCondition option[value='" + json.searchVO.searchCondition + "']").attr('selected', 'selected');
						$('#mapCanvas').html("");
						
	               		buildingList = json.resultList;
	               		
						// 현재 위치정보 취득
		                navigator.geolocation.getCurrentPosition(successCallback, errorCallback, { maximumAge: 0, timeout: 30000, enableHighAccuracy: true });
	                });
	            }
	        }

	        /*********************************************************
			 * 위치정보 취득 성공시 처리
			 ******************************************************** */
	        function successCallback(position) {
	        	// 현재 위치정보 표시
	        	var html = "";
	       		html += '<ul class="mcom-posi">';
	       		html += '<li><strong>위도 :</strong> ' + position.coords.latitude + '</li>';
	       		html += '<li><strong>경도 :</strong> ' + position.coords.longitude + '</li>';
	       		html += '</ul>';
	
	       		$('#latlngInfo').html(html);
		        
		        var zoomLevel = 4;
	
		        // 조회 범위에 따른 ZoomLevel 설정*
		        if ($('#searchCondition').val() == "100") {
					zoomLevel = 3;
		        } else if ($('#searchCondition').val() == "500") {
					zoomLevel = 5;
				} else if ($('#searchCondition').val() == "1000") {
					zoomLevel = 6;
				} else if ($('#searchCondition').val() == "2000") {
					zoomLevel = 7;
				}
	
		        // 현재 위치 좌표*
	        	var latlng =  new daum.maps.LatLng(position.coords.latitude, position.coords.longitude);
	
				// 지도 설정*
	        	var myOptions = {
					level: zoomLevel,
	        		center: latlng,
	        		mapTypeId: daum.maps.MapTypeId.ROADMAP
	        	};
	
				// 지도 생성*
	        	var map = new daum.maps.Map(document.getElementById("mapCanvas"), myOptions);
	        	map.addControl(new daum.maps.ZoomControl());
				
	        	// 현재 위치 마커 표시*
	        	var curMarker = new daum.maps.Marker({
	        		position: latlng
	        	});
	        	curMarker.setMap(map);
	
	        	var infowindow = new daum.maps.InfoWindow({ 
	        		content: '현재위치'
	        	});
	        	infowindow.open(map, curMarker);
	        	
	        	var buildingMarker = new Array();
	        	
	        	// 조회 범위 내의 건물 데이터 조회
	        	var resultList = fn_select_building(position.coords.latitude, position.coords.longitude, $('#searchCondition').val(), buildingList);
	
	        	// 건물 아이콘
	        	var icon = new daum.maps.MarkerImage(
	        		"<c:url value='/images/egovframework/mbl/com/geo/icon/ic_building.png'/>",
	        		new daum.maps.Size(32, 32),
	        		new daum.maps.Point(16, 0)
	        	);
	
				// 조회 범위 내의 건물 마커 표시*
	        	for (var i = 0; i < resultList.length; i++) {        		
		        	var buildingLatLng = new daum.maps.LatLng(resultList[i].la, resultList[i].lo);
	        		buildingMarker[i] = new daum.maps.Marker({
	        			position: buildingLatLng,
						image : icon
		        	});
	        		buildingMarker[i].setMap(map);
	
	        		infowindow = new daum.maps.InfoWindow({ 
		        		content: resultList[i].buldNm + '<br>' + resultList[i].telno
		        	});
		        	infowindow.open(map, buildingMarker[i]);
	        	}
	
	        	// 조회 범위 원 그리기*
	        	var circle =  new daum.maps.Circle({
	        		center: latlng,
	        		radius: parseInt($('#searchCondition').val())*1,
	        		strokeWeight: 2,
	        		strokeColor: "#ff0000",
	        		strokeOpacity: 1,
	        		fillOpacity : 0
	        	});
	        	circle.setMap(map);
	        }

	        /*********************************************************
			 * 위치정보 취득 실패시 처리
			 ******************************************************** */
	        function errorCallback(error) {
				jAlert("에러발생, 에러코드: " + error.code + " 메시지: " + error.message, "Error", "a");
			}
		</script>
	</head>
	<body>
		<div id="main" data-role="page">
		
			<!-- header start -->
			<div data-role="header" data-theme="z">
				<a href="<c:url value='/index.jsp'/>" data-ajax="false" data-icon="home" class="ui-btn-left">메인</a>
				<h1><img src="<c:url value='/images/egovframework/mbl/mcomd/h1_logo.png'/>"/></h1>
				<div class="ui-body-a mcomd-title"><h3>위치정보연계</h3></div>
			</div>
			<!-- header end -->
			
			<!-- content start -->
			<div data-role="content">
				<h3 class="mcom-h3">위도, 경도 좌표</h3>
				<div id="latlngInfo" class="ui-body-c">
					
				</div>
				<div id="mapTitle">
					<div style="padding:10px 0">
	                	<div class="egov-grid">
	                		<div class="egov-wid2">
	                			<div align="left">							
	                				<b>MAP</b>
	                			</div>
	                		</div>
	                		<div class="egov-wid10">
	                			<div align="right">	
	                				<form id="searchVO" name="searchVO" method="post">
	                					<p>조회범위
	                					<select id="searchCondition" name="searchCondition" onchange="javascript:init();" data-role="none">
	                						<option value="100">100m 이내</option>
	                						<option value="200">200m 이내</option>
	                						<option value="300">300m 이내</option>
	                						<option value="500">500m 이내</option>
	                						<option value="1000">1km 이내</option>
	                						<option value="2000">2km 이내</option>
	               						</select></p>
	               					</form>
	               				</div>
	               			</div>
	               		</div>
	               	</div>
				</div>
				<div id="mapCanvas" class="ui-body-c" style="font-size:0.75em">
					
				</div>
			</div>
			<!-- content end -->
			
			<!-- footer start -->
			<div data-role="footer" data-theme="z" class="egovBar">
				<h4>Copyright(c)2011 Ministry of Government Administration and Home Affairs.</h4>
			</div>
			<!-- footer end -->
			
		</div>
	</body>
</html> 