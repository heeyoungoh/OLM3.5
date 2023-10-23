<%@ page import = "java.util.List, java.util.HashMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>

<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<%@ include file="/WEB-INF/jsp/cmm/fileAttachHelper.jsp"%>
<head>
<title>CTS 관리</title>

<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>
 
 <!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
 
<script type="text/javascript">

$(document).ready(function(){		
	$("input.datePicker").each(generateDatePicker);
	
	//document.getElementById('objectInfoDiv').style.height = (setWindowHeight() - 45);
	document.getElementById('editArea').style.height = (setWindowHeight() - 45);
	
});

function setWindowHeight(){
	var size = window.innerHeight;
	var height = 0;
	if( size == null || size == undefined){
		height = document.body.clientHeight;
	}else{
		height=window.innerHeight;
	}
	
	return height;
}


var memberType = "";
function fnOpenSearchPopup(url, target){
	memberType = target;
	window.open(url,'userPopup','width=300, height=300, left=300, top=300,scrollbar=yes,resizble=0');
}

function setSearchName(loginID, memberNM){
	if(memberType == "reqLoginNM"){
		$('#reqLoginID').val(loginID);
		$('#reqLoginNM').val(memberNM);		
	} else if(memberType == "ftReviewLoginNM"){
		$('#ftReviewLoginID').val(loginID);
		$('#ftReviewLoginNM').val(memberNM);			
    } else if(memberType == "sdReviewLoginNM"){
    	$('#sdReviewLoginID').val(loginID);
		$('#sdReviewLoginNM').val(memberNM);		
    } else if(memberType == "approveLoginNM"){
    	$('#approveLoginID').val(loginID);
		$('#approveLoginNM').val(memberNM);		
    } else if(memberType == "processLoginNM"){
    	$('#processLoginID').val(loginID);
		$('#processLoginNM').val(memberNM);		
    }

}

function fnSaveCts(){
	
	// 체크조건
	if ($('#systemCD').val() == "") {
		alert("시스템 항목을 입력하여 주십시오.");
		$('#systemCD').focus();
		return;
	}
	if ($('#subSystemCD').val() == "") {
		alert("서브시스템 항목을 입력하여 주십시오.");
		$('#subSystemCD').focus();
		return;
	}
	if ($('#progressCD').val() == "") {
		alert("진행상태 항목을 입력하여 주십시오.");
		$('#progressCD').focus();
		return;
	}
	if ($('#progressCD').val() != "01") {
		alert("진행상태 항목을 확인하여 주십시오.");
		$('#progressCD').focus();
		return;
	}
	if ($('#reqTypeCD').val() == "") {
		alert("요청유형 항목을 입력하여 주십시오.");
		$('#reqTypeCD').focus();
		return;
	}
	if ($('#reqLoginID').val() == "") {
		alert("요청자 항목을 입력하여 주십시오.");
		$('#reqLoginNM').focus();
		return;
	}
	
	if ($('#reqDate').val() == "") {
		alert("요청일자 항목을 입력하여 주십시오.");
		$('#reqDate').focus();
		return;
	}
	if ($('#reqTime').val() == "") {
		alert("요청일시 항목을 입력하여 주십시오.");
		$('#reqTime').focus();
		return;
	}
	if ($('#dueDate').val() == "") {
		alert("완료희망일자 항목을 입력하여 주십시오.");
		$('#dueDate').focus();
		return;
	}
	if ($('#reqSubject').val() == "") {
		alert("요청제목 항목을 입력하여 주십시오.");
		$('#reqSubject').focus();
		return;
	}
	if ($('#reqContent').val() == "") {
		alert("요청내용 항목을 입력하여 주십시오.");
		$('#reqContent').focus();
		return;
	}
	if ($('#applySystemClient').val() == "") {
		alert("적용시스템/Client  항목을 입력하여 주십시오.");
		$('#applySystemClient').focus();
		return;
	}
	if ($('#hiSOSReqInfo').val() == "") {
		alert("HiSOS 요청정보  항목을 입력하여 주십시오.");
		$('#hiSOSReqInfo').focus();
		return;
	}
	
	/*
	if ($('#testResult').val() == "") {
		alert("테스트 결과 항목을 입력하여 주십시오.");
		$('#testResult').focus();
		return;
	}
	*/
	
	// 타모듈 영향
	var moduleEffectYN = $('#moduleEffectYN').val();
	if(moduleEffectYN == "Y"){
		if ($('#moduleReviewResult').val() == "") {
			alert("타모듈 검토 결과 항목을 입력하여 주십시오.");
			$('#moduleReviewResult').focus();
			return;
		}
	}
	// 긴급여부
	var urgentYN = $('#urgentYN').val();
	if(urgentYN == "Y"){
		if ($('#urgentTypeCD').val() == "") {
			alert("긴급 유형 항목을 입력하여 주십시오.");
			$('#urgentTypeCD').focus();
			return;
		}
		if ($('#urgentExecCD').val() == "") {
			alert("긴급 수행 내용 항목을 입력하여 주십시오.");
			$('#urgentExecCD').focus();
			return;
		}
		if ($('#urgentReason').val() == "") {
			alert("긴급사유 항목을 입력하여 주십시오.");
			$('#urgentReason').focus();
			return;
		}
	}
	
	
	if ($('#ftReviewLoginID').val() == "") {
		alert("1차 검토자 항목을 입력하여 주십시오.");
		$('#ftReviewLoginNM').focus();
		return;
	}
	/*
	if ($('#sdReviewLoginID').val() == "") {
		alert("2차 검토자 항목을 입력하여 주십시오.");
		$('#sdReviewLoginNM').focus();
		return;
	}
	*/
	if ($('#approveLoginID').val() == "") {
		alert("승인자 항목을 입력하여 주십시오.");
		$('#approveLoginNM').focus();
		return;
	}
	
	
	if(confirm("${CM00001}")){
		var url = "ctsSave.do";	
		ajaxSubmit(document.ctsNewFrm, url);
	}
}

function returnSaveCts(){
	opener.doSearchCtsList();
	self.close();
}


function fnModuleEffectYN(){
	var moduleEffectYN = $('#moduleEffectYN').val();
	
	if(moduleEffectYN == "Y"){
		$("#moduleReviewResultDiv").html("*"); 
	} else {
		$("#moduleReviewResultDiv").html(""); 
	}
}

function fnUrgentYN(){
	var urgentYN = $('#urgentYN').val();
	
	if(urgentYN == "Y"){
		$("#urgentTypeCDDiv").html("*"); 
		$("#urgentExecCDDiv").html("*"); 
		$("#urgentReasonDiv").html("*"); 
	} else {
		$("#urgentTypeCDDiv").html(""); 
		$("#urgentExecCDDiv").html(""); 
		$("#urgentReasonDiv").html("");  
	}
}

</script>

</head>

<form name="ctsNewFrm" id="ctsNewFrm" enctype="multipart/form-data" action="ctsSave.do" method="post" onsubmit="return false;">
    <input type="hidden" id="reqLoginID"      name="reqLoginID"      value="${sessionScope.loginInfo.sessionLoginId}" />
    <input type="hidden" id="ftReviewLoginID" name="ftReviewLoginID" value="" />
    <input type="hidden" id="sdReviewLoginID" name="sdReviewLoginID" value="" />
    <input type="hidden" id="approveLoginID"  name="approveLoginID"  value="" />
    <input type="hidden" id="processLoginID"  name="processLoginID"  value="" />

<div class="child_search_head">
	<p><img src="${root}${HTML_IMG_DIR}/category.png">&nbsp;&nbsp;CTS 관리</p>
</div>
<div id="editArea" class="hidden" style="width:100%;overflow:auto;margin-bottom:5px;overflow-x:hidden;">
	
			<div class="alignBTN">
				<span class="btn_pack medium icon"><span class="save"></span><input value="Save" onclick="javascript:fnSaveCts()" type="submit"></span>
			</div>
	        <!--  
		        <div class="floatL msg" style="width:100%;">&nbsp;기본정보</div>
		    -->
		    <div class="floatL msg" style="width:100%;">&nbsp;기본정보</div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<!-- 변경요청 ID -->
					<th class="viewtop">변경요청 ID <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">${CTSReqID}</td>
					<!-- 시스템 -->
					<th class="viewtop">시스템 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
			        	<select id="systemCD" Name="systemCD" style="width:100px">
			            	<option value=''>${menu.LN00113}</option>
			            	<c:forEach var="i" items="${systemList}">
			                	<option value="${i.ID}">${i.NAME}</option>
			            	</c:forEach>
			        	</select>
					</td>
					<!-- 서브시스템 -->
					<th class="viewtop">서브시스템 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
						<select id="subSystemCD" Name="subSystemCD" style="width:100px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${subSystemList}">
								<option value="${i.ID}">${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<!-- 진행상태 -->
					<th>진행상태 <span class="red">*</span></th>
					<td class="alignL pdL5">
						<select id="progressCD" Name="progressCD" style="width:100px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${progressList}">
								<option value="${i.ID}">${i.NAME}</option>
			 				</c:forEach>
			            </select>
					</td>
					<!-- 요청유형 -->	
					<th>요청유형 <span class="red">*</span></th>
					<td class="alignL pdL5">
				        <select id="reqTypeCD" Name="reqTypeCD" style="width:100px">
				            <option value=''>${menu.LN00113}</option>
				        	<c:forEach var="i" items="${reqTypeList}">
				            	<option value="${i.ID}">${i.NAME}</option>
				            </c:forEach>
				         </select>
					</td>
					<!-- 요청자 -->	
					<th>요청자 <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" class="stext"  id="reqLoginNM" name="reqLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','reqLoginNM')" value="${sessionScope.loginInfo.sessionUserNm}" style="width:100px;ime-mode:active;"/>
                        <!--
                        <img src="${root}cmm/sk/images/btn_icon_search.png" style="cursor:pointer;width:20;height:20;vertical-align:middle;align:absmiddle;border:0;" alt="검색" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','reqLoginNM')">
                        -->
					</td>
				</tr>
				<tr>
					<!-- 요청일자 -->
					<th>요청일자 <span class="red">*</span></th>
					<td class="alignL pdL5">
			            <font><input type="text" id="reqDate" name="reqDate" value="${thisYmd}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly">
						</font>
					</td>
					<!-- 요청일시 -->	
					<th>요청일시 <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" id="reqTime" name="reqTime" value="${thisTime}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
					<!-- 완료희망일자 -->	
					<th>완료희망일자 <span class="red">*</span></th>
					<td class="alignL pdL5">
			            <font><input type="text" id="dueDate" name="dueDate" value="${thisYmd}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly">
						</font>
					</td>
				</tr>
				<tr>
					<!-- 요청제목 -->
					<th>요청제목 <span class="red">*</span></th>
					<td colspan="5" class="tdLast alignL pdL5">
					    <input type="text" id="reqSubject" name="reqSubject" value="" class="stext" style="width:650px;ime-mode:active;" maxlength="100">
					</td>
				</tr>
				<tr>
					<!-- 요청내용 -->
					<th>요청내용 <span class="red">*</span></th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow"  id="reqContent" name="reqContent" style="width:100%;height:80px;"></textarea></td>
				</tr>
				<tr>
					<!-- 적용시스템/Client -->
					<th>적용시스템/Client <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" id="applySystemClient" name="applySystemClient" value="" class="stext" style="width:100px;ime-mode:active;" maxlength="20">
					</td>
					<!-- HiSOS 요청정보 -->	
					<th>HiSOS 요청정보 <span class="red">*</span></th>
					<td  colspan="3" class="alignL pdL5">
                        <input type="text" id="hiSOSReqInfo" name="hiSOSReqInfo" value="" class="stext" style="width:250px;ime-mode:active;" maxlength="80">
					</td>
				</tr>
				<tr>
					<!-- MPM관련 문서정보 -->
					<th>MPM관련 문서정보</th>
					<td colspan="5" class="tdLast alignL pdL5">
					    <input type="text" id="mpmDocumentInfo" name="mpmDocumentInfo" value="" class="stext" style="width:650px;ime-mode:active;" maxlength="50">
					</td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;요청 상태 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<th class="viewtop">타모듈 영향 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
				        <select id="moduleEffectYN" Name="moduleEffectYN" style="width:100px" onchange="fnModuleEffectYN();">
				            <option value='N' selected="selected">없음</option>
				            <option value='Y'>있음</option>
				         </select>
					</td>
					<th class="viewtop alignL pdL5">타모듈 검토 결과 <span id="moduleReviewResultDiv" class="red"></span></th>
					<td class="viewtop alignL pdL5" colspan="3">
					    <input type="text" id="moduleReviewResult" name="moduleReviewResult" value="" class="stext" style="width:250px;ime-mode:active;" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>긴급 여부 <span class="red">*</span></th>
					<td class="alignL pdL5">
				        <select id="urgentYN" Name="urgentYN" style="width:100px" onchange="fnUrgentYN();">
				            <option value='N' selected="selected">미대상</option>
				            <option value='Y'>대상</option>
				         </select>
					</td>
					<th>긴급 유형 <span id="urgentTypeCDDiv" class="red"></span></th>
					<td class="alignL pdL5">
						<select id="urgentTypeCD" Name="urgentTypeCD" style="width:140px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${urgentTypeList}">
								<option value="${i.ID}">${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
					<th>긴급 수행 내용 <span id="urgentExecCDDiv" class="red"></span></th>
					<td class="alignL pdL5">
						<select id="urgentExecCD" Name="urgentExecCD" style="width:140px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${urgentExecList}">
								<option value="${i.ID}">${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>긴급 사유 <span id="urgentReasonDiv" class="red"></span></th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="urgentReason" name="urgentReason" style="width:100%;height:80px;"></textarea></td>
				</tr>
				<tr>
					<th>기타1</th>
					<td class="alignL pdL5">
					    <input type="text" id="etc01" name="etc01" value="" class="stext" style="width:100px;ime-mode:active;" maxlength="50">
					</td>
					<th>기타2</th>
					<td  colspan="3" class="alignL pdL5">
					    <input type="text" id="etc02" name="etc02" value="" class="stext" style="width:250px;ime-mode:active;" maxlength="50">
					</td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;테스트 결과 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<td colspan="6" class="viewtop tdLast alignL pdL5">
					    <textarea class="tinymceText" id="testResult" name="testResult" style="width:100%;height:400px;"></textarea>
					  </td>
				</tr>
				<tr>
					<th class="sline">
						<div style="position:relative;">${menu.LN00019}
							<img src="${root}${HTML_IMG_DIR}/btn_file_attach.png" style="cursor:pointer;width:11;height:11;padding-top:3px;" align="absmiddle">
							<div id="file_item_P">
							<div id='file_items' onclick="check()"></div>
							<span id="file_item0" class="file_attach">
									<input type="file" name='file0' id='file0' onchange='attach("1", this)' size='1' >
							</span>			
							</div>
						</div>
					</th>				
					<td colspan="5" class="tit" style="position:relative">	
						<div id='down_file_items'></div>
						<c:if test="${itemFiles.size() > 0}">
							<c:forEach var="result" items="${itemFiles}" varStatus="status" >
									<div id="divDownFile${result.Seq}"  class="mm" name="divDownFile${result.Seq}">
										<a href="${root}fileDown.do?seq=${result.Seq}"><img src="${root}${HTML_IMG_DIR}/btn_fileadd.png" style="cursor:pointer;width:13;height:13;padding-right:10px" alt="파일다운로드" align="absmiddle">${result.FileRealName}</a>
 										<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteItemFile('${result.BoardID}','${result.Seq}')">
										<br>
									</div>
							</c:forEach>
						</c:if>			
						<div id='display_items'></div>
						<input type="hidden" id="items" name="items" />
						<input type="hidden" id="isNew" name="isNew" />
					</td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;1차 검토 정보 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<th class="viewtop">1차 검토자 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
                        <input type="text" class="stext"  id="ftReviewLoginNM" name="ftReviewLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','ftReviewLoginNM')" value="" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">검토 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="ftReviewDate" name="ftReviewDate" value=""	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly">
						</font>
					</td>
					<th class="viewtop">검토 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="ftReviewTime" name="ftReviewTime" value="" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>검토/반려 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="ftReviewOpnion" name="ftReviewOpnion" style="width:100%;height:80px;"></textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
			<!--
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;2차 검토 정보 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<th class="viewtop">2차 검토자 </th>
					<td class="viewtop alignL pdL5">
					    <input type="text" class="stext"  id="sdReviewLoginNM" name="sdReviewLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','sdReviewLoginNM')" value="" disabled="disabled" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">검토 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="sdReviewDate" name="sdReviewDate" value=""	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" disabled="disabled">
						</font>
					</td>
					<th class="viewtop">검토 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="sdReviewTime" name="sdReviewTime" value="" class="stext" style="width:100px;ime-mode:active;" disabled="disabled">
					</td>
				</tr>
				<tr>
					<th>검토/반려 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="sdReviewOpnion" name="sdReviewOpnion" style="width:100%;height:80px;" disabled="disabled"></textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			-->
			
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;승인 정보 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<th class="viewtop">승인자 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
					    <input type="text" class="stext"  id="approveLoginNM" name="approveLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do?gubun=app','approveLoginNM')" value="" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">승인 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="approveDate" name="approveDate" value=""	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly">
						</font>
					</td>
					<th class="viewtop">승인 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="approveTime" name="approveTime" value="" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>검토/반려 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="approveOpnion" name="approveOpnion" style="width:100%;height:80px;"></textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;처리 결과 </div>
			<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
				<colgroup>
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
					<col width="11%">
					<col width="14%">
				</colgroup>
				<tr>
					<th class="viewtop">처리자</th>
					<td class="viewtop alignL pdL5">
					    <input type="text" class="stext"  id="processLoginNM" name="processLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','processLoginNM')" value="" disabled="disabled"  style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">처리 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="processDate" name="processDate" value=""	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" >
						</font>
					</td>
					<th class="viewtop">처리 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="processTime" name="processTime" value="" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>처리 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="processOpnion" name="processOpnion" style="width:100%;height:80px;"></textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
		
			<div class="alignBTN">
				<span class="btn_pack medium icon"><span class="save"></span><input value="Save" onclick="javascript:fnSaveCts()" type="submit"></span>
			</div>
</div>	
</form>					
<iframe id="saveFrame" name="saveFrame" style="display:none" frameborder="0"></iframe>
	
