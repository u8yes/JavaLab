<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>

	<head>
	
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>행사/이벤트/캠페인 상세 </title>
		
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/jquery.mobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/cmm/EgovMobile-1.4.5.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/egovframework/mbl/com/uss/ussCommon.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery-1.11.2.js"></script>
		
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/jquery.mobile-1.4.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/egovframework/mbl/cmm/EgovMobile-1.4.5.js"></script>
				
        <script type="text/javaScript" language="javascript" defer="defer">
        <!--

        /* ********************************************************
         * 목록 으로 가기
         ******************************************************** */
        function fn_egov_list_EventCmpgn(){
        	var vForm = document.MeetingManageForm;
            vForm.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnList.mdo";
            vForm.submit();
        }
        /* ********************************************************
         * 수정처리화면
         ******************************************************** */
         function fn_egov_modify_EventCmpgn(){
       		var vForm = document.MeetingManageForm;
       		vForm.cmd.value = '';
       		vForm.action = "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnModify.mdo";
       		vForm.submit();
        }
        /* ********************************************************
         * 삭제처리
         ******************************************************** */
         function fn_egov_delete_EventCmpgn(){

    			jConfirm('삭제시 행사/이벤트/캠페인, 외부인사정보 \n정보가 함께 삭제됩니다!\n\n삭제 하시겠습니까?', '알림', 'a', function(r) {
    				

    			    if(r){	   			
    			    	$('#cmd').val('del');
    	        		$('#eventCmpgnVO').attr('action', "${pageContext.request.contextPath}/uss/ion/ecc/EgovEventCmpgnDetail.mdo");
						$('#eventCmpgnVO').attr('data-ajax', 'false');
    	        		$('#eventCmpgnVO').submit();
    	        		
    				}else{
    					$('#cmd').val('');
    				}
    			});
        }
			
		-->
        </script>
        
	</head>
	
	<body>
		
		<!-- View start -->
		<div id="view" data-role="page">
						
				<!-- header start -->
				<div data-role="header">
				    <a href="javascript:fn_egov_list_EventCmpgn();" data-icon="arrow-l" data-ajax="false">뒤로</a>
				    <h1 id="viewTitle">행사/이벤트/캠페인 상세</h1>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
				<div data-role="content" class="com-copContent">
						
					<form:form commandName="eventCmpgnVO" name="MeetingManageForm" method="post">

						<!-- searchVO start -->
						<form:hidden path="searchCondition" value="${resultList[0].searchCondition}"/>
						<form:hidden path="searchKeyword" value="${resultList[0].searchKeyword}"/>
						<form:hidden path="pageIndex" value="${resultList[0].pageIndex}"/>
						<!-- searchVO end -->	
						<input id="cmd" name="cmd" type="hidden" value=""/>
						
						<form:hidden path="eventId" value=""/>
						<ul class="uss-hpcDetail">

							<li>	
								<span class="uss-tit">행사유형</span>
								<span class="uss-con">
						        	<label for="name-d">
						        		<c:forEach items="${comCode035}" var="comCodeList" varStatus="status">
											<c:if test="${comCodeList.code eq fn:trim(resultList[0].eventTyCode)}">	
												<pre><c:out value="${fn:replace(comCodeList.codeNm , crlf , '<br/>')}" escapeXml="false"/></pre>
											</c:if>
										</c:forEach>
									</label>
								</span>
							</li>
							<li>	
								<span class="uss-tit">행사내용</span>
								<span class="uss-con">
						        	<label for="name-d">
						        		<c:out value="${resultList[0].eventCn}"/>
						        	</label>
								</span>
							</li>
							
							<li>
								<span class="uss-tit">행사시작일자 </span>
								<span class="uss-con">
									<label for="name-d" class="uss-gray">
										<c:out value="${resultList[0].eventSvcBeginDe}"/>
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">행사종료일자 </span>
								<span class="uss-con">
									<label for="name-d" class="uss-gray">
										<c:out value="${resultList[0].eventSvcEndDe}"/>
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">서비스이용인원수</span>
								<span class="uss-con">
									<label for="name-d"> 
										<c:out value="${resultList[0].svcUseNmprCo}"/>
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">담당자명 </span>
								<span class="uss-con">								
									<label for="name-d"> 
										<c:out value="${resultList[0].chargerNm}"/>
					      			</label>
					      		</span>
							</li>
							<li>
								<span class="uss-tit">준비물내용 </span>
								<span class="uss-con">								
									<label for="name-d"> 
										<c:out value="${resultList[0].prparetgCn}"/>
					      			</label>
					      		</span>
							</li>
							<li>
								<span class="uss-tit">승인여부 </span>
								<span class="uss-con">								
									<label for="name-d"> 
										<c:if test="${resultList[0].eventConfmAt eq 'Y'}">승인</c:if>
     									<c:if test="${resultList[0].eventConfmAt eq 'N'}">미승인</c:if>
					      			</label>
					      		</span>
							</li>
							<li>
								<span class="uss-tit">승인일 </span>
								<span class="uss-con">								
									<label for="name-d"> 
										<c:out value="${resultList[0].eventConfmDe}"/>
					      			</label>
					      		</span>
							</li>
						</ul>
						
						<div class="ui-grid-b">
							<div class="ui-block-a"><a href="JavaScript:fn_egov_modify_EventCmpgn();" data-role="button" data-theme="b" data-ajax="false">수정</a></div>
							<div class="ui-block-b"><a href='JavaScript:fn_egov_delete_EventCmpgn();' data-role="button" data-theme="b">삭제</a></div>
							<div class="ui-block-c"><a href='JavaScript:fn_egov_list_EventCmpgn();' data-role="button" data-theme="b">목록</a></div>
						</div>
						
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

