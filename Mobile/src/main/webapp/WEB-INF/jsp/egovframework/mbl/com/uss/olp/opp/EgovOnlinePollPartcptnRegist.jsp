<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>

	<head>
	
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>온라인POLL참여 등록</title>
		
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
				
        <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        
        /* ********************************************************
         * 저장처리화면
         ******************************************************** */
        function fn_egov_save_OnlinePollPartcptn(){
        	var vForm = document.OnlinePollPartcptn;

			vForm.action =  "${pageContext.request.contextPath}/uss/olp/opp/registOnlinePollPartcptn.mdo";
			vForm.submit();
        }

        function fn_showList() {

        	var vForm = document.searchVO;
			
        	vForm.action = "${pageContext.request.contextPath}/uss/olp/opp/listOnlinePollPartcptn.mdo";
        	vForm.submit();
			
		}
		-->
        </script>
        
	</head>
	
	<body>
		
		<!-- View start -->
		<div id="view" data-role="page">
						
				<!-- header start -->
				<div data-role="header">
					<a href="javascript:fn_showList();" data-icon="arrow-l">뒤로</a>
				    <h1 id="viewTitle">온라인POLL참여 등록</h1>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
				<div data-role="content" class="com-copContent">
						
						<!-- searchVO start --> 
						<form name="searchVO" id="searchVO" method="post">
							<input type="hidden" name="searchCondition" value="${searchVO.searchCondition}"/>
							<input type="hidden" name="searchKeyword" value="${searchVO.searchKeyword}"/>
							<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}"/>
						</form>
						<!-- searchVO end -->
						
					<form:form commandName="OnlinePollPartcptn" name="OnlinePollPartcptn" method="post">

						<ul class="uss-hpcDetail">

							<li>	
								<span class="uss-tit">POLL명</span>
								<span class="uss-con">
						        	<label for="name-d">
						        		<c:out value="${PollManage[0].pollNm}"/>
						        	</label>
								</span>
							</li>
							
							<li>
								<span class="uss-tit">POLL시작일자 </span>
								<span class="uss-con">
									<label for="name-d" class="uss-gray">
										<c:out value="${PollManage[0].pollBeginDe}"/>
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">POLL종료일자 </span>
								<span class="uss-con">
									<label for="name-d" class="uss-gray">
										<c:out value="${PollManage[0].pollEndDe}"/>
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">POLL종류</span>
								<span class="uss-con">
									<label for="name-d"> 
										<c:forEach items="${pollKindCodeList}" var="resultInfo" varStatus="pollKindStatus">
											<c:if test="${resultInfo.code eq PollManage[0].pollKindCode}">
											<c:out value="${resultInfo.codeNm}" escapeXml="false"/>
											</c:if>
										</c:forEach>
									</label>
								</span>
							</li>
						</ul>
						<div class="uss-poll">
							<fieldset data-role="controlgroup" >
								<c:forEach items="${PollItem}" var="resultInfo" varStatus="status">
									<input type="radio" name="pollIemId" id="${status.count}" value="${resultInfo.pollIemId}" <c:if test="${status.count == '1'}">checked</c:if>>
						  				<label for="${status.count}"><c:out value="${resultInfo.pollIemNm}" escapeXml="false"/></label>
								</c:forEach>
							</fieldset>
						</div>
						<div class="ui-grid-a">
							<div class="ui-block-a"><a href="JavaScript:fn_egov_save_OnlinePollPartcptn();" data-role="button" data-theme="b" data-ajax="false">등록</a></div>
							<div class="ui-block-b"><a href='javascript:fn_showList();' data-role="button" data-theme="b">목록</a></div>
						</div>
						<input name="pollId" type="hidden" value="${PollManage[0].pollId}"/>
						<input name="cmd" type="hidden" value="<c:out value='save'/>"/>
						<input name="linkType" type="hidden" value="2">
					</form:form>
				</div>
				<!-- contents end -->
				 
				<!-- footer start -->
				<div data-role="footer" data-position="fixed">
					<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
				</div>
				<!-- footer end -->
						
								
		</div>
		<!-- view end -->
		
	</body>
	
</html>
