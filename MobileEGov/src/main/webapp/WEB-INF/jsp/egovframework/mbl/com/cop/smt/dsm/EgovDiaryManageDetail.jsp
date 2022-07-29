<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:scriptlet>
	pageContext.setAttribute("crlf", "\r\n");
</jsp:scriptlet>
 
<!DOCTYPE html>
<html>

	<head>
	
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>일지관리상세보기 </title>
		
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
        function fn_egov_list_DiaryManage(){
            var vForm = document.DiaryManageForm;
            vForm.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageList.mdo";
            vForm.submit();
        }
        /* ********************************************************
         * 수정처리화면
         ******************************************************** */
        function fn_egov_modify_DiaryManage(){
        	var vFrom = document.DiaryManageForm;
        	vFrom.cmd.value = '';
        	vFrom.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageModify.mdo";
        	vFrom.submit();

        }
        /* ********************************************************
         * 삭제처리
         ******************************************************** */
        function fn_egov_delete_DiaryManage(){
        	var vFrom = document.DiaryManageForm;

			jConfirm('삭제 하시겠습니까?', 'EgovFrame', 'a', function(r) {
				
			    if(r){	   			
			    	vFrom.cmd.value = 'del';
	        		
	        		vFrom.action = "${pageContext.request.contextPath}/cop/smt/dsm/EgovDiaryManageDetail.mdo";
	        		vFrom.submit();
				}else{
					vFrom.cmd.value = '';
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
				    <h1 id="viewTitle">일지관리상세보기</h1>
				    <a href="javascript:fn_egov_list_DiaryManage();" data-icon="arrow-l" data-ajax="false">뒤로</a>
				</div>
				<!-- header end -->
				
				<!-- contents start -->
				<div data-role="content" class="com-copContent">
						
					<form:form commandName="diaryManageVO" name="DiaryManageForm" method="post">

						<!-- searchVO start -->
						<form:hidden path="searchCondition" value="${searchVO.searchCondition}"/>
						<form:hidden path="searchKeyword" value="${searchVO.searchKeyword}"/>
						<form:hidden path="pageIndex" value="${searchVO.pageIndex}"/>
						<!-- searchVO end -->						
						<input name="cmd" type="hidden" value="<c:out value=''/>"/>
						
						<form:hidden path="diaryId" value=""/>
						<ul class="uss-hpcDetail">

							<li>	
								<span class="uss-tit">일지정보 </span>
								<span class="uss-con">
						        	<label for="name-d">
						        		<c:out value="${diaryManageVO.schdulCn}"/>
						        	</label>
								</span>
							</li>
							<li>	
								<span class="uss-tit">일지명 </span>
								<span class="uss-con">
						        	<label for="name-d">
						        		<c:out value="${diaryManageVO.diaryNm}"/>
						        	</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">지시사항 </span>
								<span class="uss-contents">
									<label for="name-d">
										${fn:replace(diaryManageVO.drctMatter , crlf , '<br>')}
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">특이사항</span>
								<span class="uss-contents">
									<label for="name-d">
										${fn:replace(diaryManageVO.partclrMatter , crlf , '<br>')}
									</label>
								</span>
							</li>
							<li>
								<span class="uss-tit">진척률 </span>
								<span class="uss-con">								
									<label for="name-d"> 
										<c:out value="${diaryManageVO.diaryProcsPte}"/>%
					      			</label>
					      		</span>
							</li>
						</ul>
						
						<div class="ui-grid-b">
							<div class="ui-block-a"><a href="JavaScript:fn_egov_modify_DiaryManage();" data-role="button" data-theme="b" data-ajax="false">수정</a></div>
							<div class="ui-block-b"><a href='JavaScript:fn_egov_delete_DiaryManage();' data-role="button" data-theme="b">삭제</a></div>
							<div class="ui-block-c"><a href='JavaScript:fn_egov_list_DiaryManage();' data-role="button" data-theme="b">목록</a></div>
						</div>
						
					</form:form>
				</div>
				<!-- contents end -->
				
				<!-- footer start -->
				<div data-role="footer" data-theme="a" data-position="fixed">
					<h4>Copyright (c) MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION.</h4>
				</div>
				<!-- footer end -->
						
								
		</div>
		<!-- view end -->
		
	</body>
	
</html>

