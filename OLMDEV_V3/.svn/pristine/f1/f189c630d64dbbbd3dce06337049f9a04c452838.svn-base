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

var p_gridArea_Cts;

$(document).ready(function(){		
	$("input.datePicker").each(generateDatePicker);
	
	//$("#grdCtsGridArea").attr("style","height:"+(setWindowHeight() - 200)+"px;");
	document.getElementById('editArea').style.height = (setWindowHeight() - 45);
	
	gridCtsInit();
	doSearchCtsHistList();
	
	// 일자, 일시 설정
	var pgStatus = "${detailMap.ProgressCD}";
	var thisYmd  = "${thisYmd}";
	var thisTime = "${thisTime}";
	//alert(pgStatus);
	if(pgStatus == "01"){
		$('#ftReviewDate').val(thisYmd);
		$('#ftReviewTime').val(thisTime);
	} else if(pgStatus == "03"){
		//$('#sdReviewDate').val(thisYmd);
		//$('#sdReviewTime').val(thisTime);	
		$('#approveDate').val(thisYmd);
		$('#approveTime').val(thisTime);
	} else if(pgStatus == "04"){
		$('#approveDate').val(thisYmd);
		$('#approveTime').val(thisTime);		
	} else if(pgStatus == "06"){
		$('#processDate').val(thisYmd);
		$('#processTime').val(thisTime);	
		
		fnProcessAuth();
	}
	
	
	var moduleEffectYN = "${detailMap.ModuleEffectYN}";
	if(moduleEffectYN == "Y"){
		$("#moduleReviewResultDiv").html("*");	
	}
	var urgentYN = "${detailMap.UrgentYN}";
	if(urgentYN == "Y"){
		$("#urgentTypeCDDiv").html("*"); 
		$("#urgentExecCDDiv").html("*"); 
		$("#urgentReasonDiv").html("*"); 	
	}	

});


function fnProcessAuth(){
	var loginID = "${sessionScope.loginInfo.sessionLoginId}";
	var loginNM = "${sessionScope.loginInfo.sessionUserNm}";
	if(loginID == "I0100375" || loginID == "I0100184" || loginID == "I0100227" || loginID == "I0100222" || loginID == "I0100497" || loginID == "I0100288"){
		$('#processLoginID').val(loginID);
		$('#processLoginNM').val(loginNM);
		
		$('#processLoginNM').removeAttr("disabled"); 
		
	}
}

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

function fnUpdateCts(){
	
	var pgStatusBf = "${detailMap.ProgressCD}";
	var pgStatusAf = $('#progressCD').val();
	
	if(pgStatusBf == "01"){
		if(pgStatusAf != "03" && pgStatusAf != "08"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}		
	}
	if(pgStatusBf == "03"){
		if(pgStatusAf != "06" && pgStatusAf != "08"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}
	}
	/*
	if(pgStatusBf == "04"){
		if(pgStatusAf != "06" && pgStatusAf != "08"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}
	}
	if(pgStatusBf == "05"){
		if(pgStatusAf != "06" && pgStatusAf != "08"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}
	}
	*/
	if(pgStatusBf == "06"){
		if(pgStatusAf != "07"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}
	}
	if(pgStatusBf == "07"){
		//alert("처리완료 상태이므로 저장할 수 없습니다.");
		//return;
		if(pgStatusAf != "09"){
			alert("진행 상태를 확인해 주세요.");
			$('#progressCD').focus();
			return;			
		}
	}
	if(pgStatusBf == "08"){
		alert("반려 상태이므로 저장할 수 없습니다.");
		return;
	}
	if(pgStatusBf == "09"){
		alert("종결 상태이므로 저장할 수 없습니다.");
		return;
	}
	
	// 1차 검토
	if(pgStatusAf == "03"){
		var ftReviewDate    =  $('#ftReviewDate').val();
		var ftReviewOpinion =  $('#ftReviewOpinion').val();
		var ftReviewLoginID =  $('#ftReviewLoginID').val();
		var sessionLoginID  = "${sessionScope.loginInfo.sessionLoginId}";
		//alert(ftReviewLoginID+" "+sessionLoginID);
		
		if(ftReviewDate != ''){
			if(ftReviewLoginID != sessionLoginID){
				alert("1차 검토자에 해당하지 않습니다. ");
				return;
			}
			
			if(ftReviewOpinion == ""){
				alert("1차 검토/반려 의견을 입력하여 주세요.");
				$('#ftReviewOpinion').focus();
				return;
			}
			
			if(confirm("1차 검토 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 

	// 2차 검토	
	} else if(pgStatusAf == "04"){
		var sdReviewDate    =  $('#sdReviewDate').val();
		var sdReviewOpinion =  $('#sdReviewOpinion').val();
		var sdReviewLoginID =  $('#sdReviewLoginID').val();
		var sessionLoginID  = "${sessionScope.loginInfo.sessionLoginId}";
		
		if(sdReviewDate != ''){
			if(sdReviewLoginID != sessionLoginID){
				alert("2차 검토자에 해당하지 않습니다. ");
				return;
			}
			
			if(sdReviewOpinion == ""){
				alert("2차 검토/반려 의견을 입력하여 주세요.");
				$('#sdReviewOpinion').focus();
				return;
			}
			
			if(confirm("2차 검토 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
	// 승인요청	
	} else if(pgStatusAf == "05"){
		if(confirm("승인요청 하시겠습니까?")){
			var url = "ctsUpdate.do";	
			ajaxSubmit(document.ctsDtlFrm, url);
		}
	// 승인완료	
	} else if(pgStatusAf == "06"){
		var approveDate    =  $('#approveDate').val();
		var approveOpinion =  $('#approveOpinion').val();
		var approveLoginID =  $('#approveLoginID').val();
		var sessionLoginID = "${sessionScope.loginInfo.sessionLoginId}";
		
		if(approveDate != ''){
			if(approveLoginID != sessionLoginID){
				alert("승인자에 해당하지 않습니다. ");
				return;
			}
			
			if(approveOpinion == ""){
				alert("승인 검토/반려 의견을 입력하여 주세요.");
				$('#approveOpinion').focus();
				return;
			}
			
			if(confirm("승인완료 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
	// 처리완료
	} else if(pgStatusAf == "07"){
		var processDate    =  $('#processDate').val();
		var processOpinion =  $('#processOpinion').val();
		var processLoginNM =  $('#processLoginNM').val();
		
		if(processDate != ''){
			if(processLoginNM == ""){
				alert("처리자에 해당하지 않습니다.");
				return;
			}
			if(processOpinion == ""){
				alert("작업 의견을 입력하여 주세요.");
				$('#processOpinion').focus();
				return;
			}
			
			if(confirm("처리완료 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
    // 반려
	} else if(pgStatusAf == "08"){
		if(confirm("반려 처리하시겠습니까?")){
			var url = "ctsUpdate.do";	
			ajaxSubmit(document.ctsDtlFrm, url);
		}
	// 종결	
	} else if(pgStatusAf == "09"){
		
		var ReqLoginID     = "${detailMap.ReqLoginID}";
		var sessionLoginID = "${sessionScope.loginInfo.sessionLoginId}";
		if(ReqLoginID != sessionLoginID){
			alert("요청자에 해당하지 않습니다. ");
			return;
		}
		
		if(confirm("종결 처리하시겠습니까?")){
			var url = "ctsUpdate.do";	
			ajaxSubmit(document.ctsDtlFrm, url);
		}		
	} else if(pgStatusAf == "01"){
		if(confirm("${CM00001}")){
			var url = "ctsUpdate.do";	
			ajaxSubmit(document.ctsDtlFrm, url);
		}
	}
	//alert(pgStatusBf+" : "+pgStatusAf);
	
	
	// 진행상태 체크
	/*
	if(pgStatusBf == "01"){
		if(pgStatusAf == "01" || pgStatusAf == "02"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	} else if(pgStatusBf == "03"){
		if(pgStatusAf == "03" || pgStatusAf == "08"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	} else if(pgStatusBf == "04"){
		if(pgStatusAf == "04" || pgStatusAf == "08"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	} else if(pgStatusBf == "05"){
		if(pgStatusAf == "05" || pgStatusAf == "08"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	} else if(pgStatusBf == "06"){
		if(pgStatusAf == "06"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	} else if(pgStatusBf == "07"){
		if(pgStatusAf == "07"){
			
		} else {
			alert("진행 상태를 확인하여 주십시오.");
			$('#progressCD').focus();
			return;
		}
	}
	
	
	// 1차 검토중
	if(pgStatusBf == "03"){
		var ftReviewDate    =  $('#ftReviewDate').val();
		var ftReviewOpinion =  $('#ftReviewOpinion').val();
		var ftReviewLoginID =  $('#ftReviewLoginID').val();
		var sessionLoginID  = "${sessionScope.loginInfo.sessionLoginId}";
		//alert(ftReviewLoginID+" "+sessionLoginID);
		
		if(ftReviewDate != ''){
			if(ftReviewLoginID != sessionLoginID){
				alert("1차 검토자에 해당하지 않습니다. ");
				return;
			}
			
			if(ftReviewOpinion == ""){
				alert("1차 검토/반려 의견을 입력하여 주세요.");
				$('#ftReviewOpinion').focus();
				return;
			}
			
			if(confirm("1차 검토 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
		
	// 2차 검토중	
	} else if(pgStatusBf == "04"){
		var sdReviewDate    =  $('#sdReviewDate').val();
		var sdReviewOpinion =  $('#sdReviewOpinion').val();
		var sdReviewLoginID =  $('#sdReviewLoginID').val();
		var sessionLoginID  = "${sessionScope.loginInfo.sessionLoginId}";
		
		if(sdReviewDate != ''){
			if(sdReviewLoginID != sessionLoginID){
				alert("2차 검토자에 해당하지 않습니다. ");
				return;
			}
			
			if(sdReviewOpinion == ""){
				alert("2차 검토/반려 의견을 입력하여 주세요.");
				$('#sdReviewOpinion').focus();
				return;
			}
			
			if(confirm("2차 검토 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
		
	// 승인요청	
	} else if(pgStatusBf == "05"){
		var approveDate    =  $('#approveDate').val();
		var approveOpinion =  $('#approveOpinion').val();
		var approveLoginID =  $('#approveLoginID').val();
		var sessionLoginID = "${sessionScope.loginInfo.sessionLoginId}";
		
		if(approveDate != ''){
			if(approveLoginID != sessionLoginID){
				alert("승인자에 해당하지 않습니다. ");
				return;
			}
			
			if(approveOpinion == ""){
				alert("승인 검토/반려 의견을 입력하여 주세요.");
				$('#approveOpinion').focus();
				return;
			}
			
			if(confirm("승인완료 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
	
	// 승인완료	
	} else if(pgStatusBf == "06"){
		var processDate    =  $('#processDate').val();
		var processOpinion =  $('#processOpinion').val();
		var processLoginID =  $('#processLoginID').val();
		var sessionLoginID = "${sessionScope.loginInfo.sessionLoginId}";
		
		if(processDate != ''){
			if(processLoginID != sessionLoginID){
				alert("처리자에 해당하지 않습니다. ");
				return;
			}
			
			if(processOpinion == ""){
				alert("작업 의견을 입력하여 주세요.");
				$('#processOpinion').focus();
				return;
			}
			
			if(confirm("처리완료 처리하시겠습니까?")){
				var url = "ctsUpdate.do";	
				ajaxSubmit(document.ctsDtlFrm, url);
			}
		} 
	
	//처리완료
	} else if(pgStatusBf == "07"){
		alert("처리완료 된 상태여서 저장할 수 없습니다.");
		return;
	} else if(pgStatusBf == "08"){
		alert("반려된 상태여서 저장할 수 없습니다.");
		return;
	} else {
		
		if(confirm("${CM00001}")){
			var url = "ctsUpdate.do";	
			ajaxSubmit(document.ctsDtlFrm, url);
		}
		
	}
	*/

}

function returnUpdateCts(){
	opener.doSearchCtsList();
	self.close();
}


function gridOnCTSRowSelect(id, ind){

}

function gridCtsInit(){
	var d = setGridPjtData();
	p_gridArea_Cts = fnNewInitGrid("grdCtsGridArea", d);
	p_gridArea_Cts.setImagePath("${root}${HTML_IMG_DIR}/");
	//p_gridArea_Cts.setColumnHidden(12, true);
	
	p_gridArea_Cts.attachEvent("onRowSelect", function(id,ind){gridOnCTSRowSelect(id,ind);});
	
	p_gridArea_Cts.enablePaging(false,10,10,"pagingArea",true,"recInfoArea");
	p_gridArea_Cts.setPagingSkin("bricks");
	p_gridArea_Cts.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
}

/* List 조회 */
function doSearchCtsHistList(){
	var d = setGridPjtData();
	fnLoadDhtmlxGridJson(p_gridArea_Cts, d.key, d.cols, d.data,false);
}

function setGridPjtData(){
	var result = new Object();
	var CtsReqID = "${detailMap.CtsReqID}";
	
	result.title   = "${title}";
	result.key     = "sk_cts_SQL.getCTSHistory";
	result.header  = ",순번,상태,담당자,상세내용,변경일시";
	result.cols    = "HistorySeq|ProgressNM|UpdateLoginNM|UpdateContent|UpdateTime";
	result.widths  = "0,50,100,70,*,120";
	result.sorting = "str,str,str,str,str,str";
	result.aligns  = "center,center,center,center,left,center";
	result.data    = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
				     + "&pageNum=" + $("#currPage").val()
				     + "&CtsReqID="+ CtsReqID
				     ;
						
	return result;
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


/* 160125 첨부파일 삭제 로직 */
function fnDeleteItemFile(CtsReqID, ProcessCD, Seq){
	// 삭제권한 체크
	if (1==1) {
	var url = "ctsFileDelete.do";
	var data = "&CtsReqID="+CtsReqID+"&ProcessCD="+ProcessCD+"&Seq="+Seq;
	ajaxPage(url, data, "saveFrame");
	
	doReturnDelete(Seq);
	} else {
		
	}
}

function doReturnDelete(Seq){	
	var divId = "divDownFile"+Seq;
	$('#'+divId).hide();
}
</script>

</head>

<form name="ctsDtlFrm" id="ctsDtlFrm" enctype="multipart/form-data" action="ctsUpdate.do" method="post" onsubmit="return false;">
    <input type="hidden" id="CtsReqID"           name="CtsReqID"           value="${detailMap.CtsReqID}" />
    <input type="hidden" id="reqLoginID"         name="reqLoginID"         value="${detailMap.ReqLoginID}" />
    <input type="hidden" id="ftReviewLoginID"    name="ftReviewLoginID"    value="${detailMap.FtReviewLoginID}" />
    <input type="hidden" id="sdReviewLoginID"    name="sdReviewLoginID"    value="${detailMap.SdReviewLoginID}" />
    <input type="hidden" id="approveLoginID"     name="approveLoginID"     value="${detailMap.ApproveLoginID}" />
    <input type="hidden" id="processLoginID"     name="processLoginID"     value="${detailMap.ProcessLoginID}" />
    <input type="hidden" id="currentPgCD"        name="currentPgCD"        value="${detailMap.ProgressCD}" />

<div class="child_search_head">
	<p><img src="${root}${HTML_IMG_DIR}/category.png">&nbsp;&nbsp;CTS 관리</p>
</div>
<div id="editArea" class="hidden" style="width:100%;overflow:auto;margin-bottom:5px;overflow-x:hidden;">
	
			<div class="alignBTN">
				<span class="btn_pack medium icon"><span class="save"></span><input value="Save" onclick="javascript:fnUpdateCts()" type="submit"></span>
			</div>
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
					<td class="viewtop alignL pdL5">${detailMap.CtsReqID}</td>
					<!-- 시스템 -->
					<th class="viewtop">시스템 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
			        	<select id="systemCD" Name="systemCD" style="width:100px">
			            	<c:forEach var="i" items="${systemList}">
			                	<option value="${i.ID}" <c:if test="${i.ID == detailMap.SystemCD}">selected="selected"</c:if>>${i.NAME}</option>
			            	</c:forEach>
			        	</select>
					</td>
					<!-- 서브시스템 -->
					<th class="viewtop">서브시스템 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
						<select id="subSystemCD" Name="subSystemCD" style="width:100px">
							<c:forEach var="i" items="${subSystemList}">
								<option value="${i.ID}" <c:if test="${i.ID == detailMap.SubSystemCD}">selected="selected"</c:if>>${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<!-- 진행상태 -->
					<th>진행상태 <span class="red">*</span></th>
					<td class="alignL pdL5">
						<select id="progressCD" Name="progressCD" style="width:100px">
							<c:forEach var="i" items="${progressList}">
								<option value="${i.ID}" <c:if test="${i.ID == detailMap.ProgressCD}">selected="selected"</c:if>>${i.NAME}</option>
			 				</c:forEach>
			            </select>
					</td>
					<!-- 요청유형 -->	
					<th>요청유형 <span class="red">*</span></th>
					<td class="alignL pdL5">
				        <select id="reqTypeCD" Name="reqTypeCD" style="width:100px">
				        	<c:forEach var="i" items="${reqTypeList}">
				            	<option value="${i.ID}" <c:if test="${i.ID == detailMap.ReqTypeCD}">selected="selected"</c:if>>${i.NAME}</option>
				            </c:forEach>
				         </select>
					</td>
					<!-- 요청자 -->	
					<th>요청자 <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" class="stext"  id="reqLoginNM" name="reqLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','reqLoginNM')" value="${detailMap.ReqLoginNM}" style="width:100px;ime-mode:active;"/>
					</td>
				</tr>
				<tr>
					<!-- 요청일자 -->
					<th>요청일자 <span class="red">*</span></th>
					<td class="alignL pdL5">
			            <font><input type="text" id="reqDate" name="reqDate" value="${detailMap.ReqDate}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" >
						</font>
					</td>
					<!-- 요청일시 -->	
					<th>요청일시 <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" id="reqTime" name="reqTime" value="${detailMap.ReqTime}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
					<!-- 완료희망일자 -->	
					<th>완료희망일자 <span class="red">*</span></th>
					<td class="alignL pdL5">
			            <font><input type="text" id="dueDate" name="dueDate" value="${detailMap.DueDate}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" >
						</font>
					</td>
				</tr>
				<tr>
					<!-- 요청제목 -->
					<th>요청제목 <span class="red">*</span></th>
					<td colspan="5" class="tdLast alignL pdL5">
					    <input type="text" id="reqSubject" name="reqSubject" value="${detailMap.ReqSubject}" class="stext" style="width:650px;ime-mode:active;" maxlength="100">
					</td>
				</tr>
				<tr>
					<!-- 요청내용 -->
					<th>요청내용 <span class="red">*</span></th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow"  id="reqContent" name="reqContent" style="width:100%;height:80px;">${detailMap.ReqContent}</textarea></td>
				</tr>
				<tr>
					<!-- 적용시스템/Client -->
					<th>적용시스템/Client <span class="red">*</span></th>
					<td class="alignL pdL5">
                        <input type="text" id="applySystemClient" name="applySystemClient" value="${detailMap.ApplySystemClient}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
					<!-- HiSOS 요청정보 -->	
					<th>HiSOS 요청정보</th>
					<td  colspan="3" class="alignL pdL5">
                        <input type="text" id="hiSOSReqInfo" name="hiSOSReqInfo" value="${detailMap.HiSOSReqInfo}" class="stext" style="width:250px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<!-- MPM관련 문서정보 -->
					<th>MPM관련 문서정보</th>
					<td colspan="5" class="tdLast alignL pdL5">
					    <input type="text" id="mpmDocumentInfo" name="mpmDocumentInfo" value="${detailMap.MpmDocumentInfo}" class="stext" style="width:650px;ime-mode:active;" >
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
				            <option value="N" <c:if test="${'N' == detailMap.ModuleEffectYN}">selected="selected"</c:if>>없음</option>
				            <option value="Y" <c:if test="${'Y' == detailMap.ModuleEffectYN}">selected="selected"</c:if>>있음</option>
				         </select>
					</td>
					<th class="viewtop alignL pdL5">타모듈 검토 결과 <span id="moduleReviewResultDiv" class="red"></span></th>
					<td class="viewtop alignL pdL5" colspan="3">
					    <input type="text" id="moduleReviewResult" name="moduleReviewResult" value="${detailMap.ModuleReviewResult}" class="stext" style="width:250px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>긴급 여부 <span class="red">*</span></th>
					<td class="alignL pdL5">
				        <select id="urgentYN" Name="urgentYN" style="width:100px" onchange="fnUrgentYN();">
				            <option value="N" <c:if test="${'N' == detailMap.UrgentYN}">selected="selected"</c:if>>미대상</option>
				            <option value="Y" <c:if test="${'Y' == detailMap.UrgentYN}">selected="selected"</c:if>>대상</option>
				         </select>
					</td>
					<th>긴급 유형 <span id="urgentTypeCDDiv" class="red"></span></th>
					<td class="alignL pdL5">
						<select id="urgentTypeCD" Name="urgentTypeCD" style="width:140px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${urgentTypeList}">
								<option value="${i.ID}" <c:if test="${i.ID == detailMap.UrgentTypeCD}">selected="selected"</c:if>>${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
					<th>긴급 수행 내용 <span id="urgentExecCDDiv" class="red"></span></th>
					<td class="alignL pdL5">
						<select id="urgentExecCD" Name="urgentExecCD" style="width:140px">
							<option value=''>${menu.LN00113}</option>
							<c:forEach var="i" items="${urgentExecList}">
								<option value="${i.ID}" <c:if test="${i.ID == detailMap.UrgentExecCD}">selected="selected"</c:if>>${i.NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>긴급 사유 <span id="urgentReasonDiv" class="red"></span></th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="urgentReason" name="urgentReason" style="width:100%;height:80px;">${detailMap.UrgentReason}</textarea></td>
				</tr>
				<tr>
					<th>기타1</th>
					<td class="alignL pdL5">
					    <input type="text" id="etc01" name="etc01" value="${detailMap.Etc01}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
					<th>기타2</th>
					<td  colspan="3" class="alignL pdL5">
					    <input type="text" id="etc02" name="etc02" value="${detailMap.Etc02}" class="stext" style="width:250px;ime-mode:active;" >
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
						<textarea class="tinymceText" id="testResult" name="testResult" style="width:100%;height:400px;">${detailMap.TestResult}</textarea>
					</td>
				</tr>
				
				<!-- 160125 CTS TEST 첨부파일 기능 추가 -->
				<tr>
					<th>
					<div style="position:relative;">${menu.LN00111}					
						<img src="${root}${HTML_IMG_DIR}/btn_file_attach.png" style="cursor:pointer;width:11;height:11;padding-top:3px" align="absmiddle">
						<div id="file_item_P">
						<div id='file_items' onclick="check()"></div>
						<span id="file_item0" class="file_attach">
								<input type="file" name='file0' id='file0' onchange='attach("1", this)' size='1'>
						</span>			
						</div>
					</div>
					</th>
					<td colspan="5" class="tit" style="position:relative">
						<!-- 하단 div 높이값으로 간격 수정 -->
						<div class="pdT5"></div>
						<!-- 파일 다운로드 -->
						<div id='down_file_items'></div>
						<c:if test="${attchFileList.size() > 0}">
							<c:forEach var="result" items="${attchFileList}" varStatus="status" >
								<div id="divDownFile${result.Seq}"  class="mm" name="divDownFile${result.Seq}">
									<a href="${root}ctsFileDown.do?original=${result.FileRealName}&filename=${result.FileName}&downFile=${result.fullFileName}&CtsReqID=${result.CtsReqID}&ProcessCD=${result.ProcessCD}&Seq=${result.Seq}"><img src="${root}${HTML_IMG_DIR}/btn_fileadd.png" style="cursor:pointer;width:13;height:13;padding-right:10px;" alt="파일다운로드" align="absmiddle">${result.FileRealName}</a>
									<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px;" alt="파일삭제" align="absmiddle" onclick="fnDeleteItemFile('${result.CtsReqID}','${result.ProcessCD}','${result.Seq}')">
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
                        <input type="text" class="stext"  id="ftReviewLoginNM" name="ftReviewLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','ftReviewLoginNM')" value="${detailMap.FtReviewLoginNM}" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">검토 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="ftReviewDate" name="ftReviewDate" value="${detailMap.FtReviewDate}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" >
						</font>
					</td>
					<th class="viewtop">검토 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="ftReviewTime" name="ftReviewTime" value="${detailMap.FtReviewTime}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>검토/반려 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="ftReviewOpinion" name="ftReviewOpinion" style="width:100%;height:80px;">${detailMap.FtReviewOpinion}</textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>
			
						
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
					    <input type="text" class="stext"  id="approveLoginNM" name="approveLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','approveLoginNM')" value="${detailMap.ApproveLoginNM}" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">승인 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="approveDate" name="approveDate" value="${detailMap.ApproveDate}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" readonly="readonly" >
						</font>
					</td>
					<th class="viewtop">승인 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="approveTime" name="approveTime" value="${detailMap.ApproveTime}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>검토/반려 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="approveOpinion" name="approveOpinion" style="width:100%;height:80px;">${detailMap.ApproveOpinion}</textarea></td>
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
					<th class="viewtop">처리자 <span class="red">*</span></th>
					<td class="viewtop alignL pdL5">
					    <input type="text" class="stext"  id="processLoginNM" name="processLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do','processLoginNM')" value="${detailMap.ProcessLoginNM}"  disabled="disabled" style="width:100px;ime-mode:active;"/>
					</td>
					<th class="viewtop">처리 일자</th>
					<td class="viewtop alignL pdL5">
			            <font><input type="text" id="processDate" name="processDate" value="${detailMap.ProcessDate}"	class="input_off datePicker stext" size="8"
							style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10"  readonly="readonly" >
						</font>
					</td>
					<th class="viewtop">처리 시간</th>
					<td class="viewtop alignL pdL5">
                        <input type="text" id="processTime" name="processTime" value="${detailMap.ProcessTime}" class="stext" style="width:100px;ime-mode:active;" >
					</td>
				</tr>
				<tr>
					<th>작업 의견</th>
					<td colspan="5" class="tdLast alignL pdL5"><textarea class="textgrow" id="processOpinion" name="processOpinion" style="width:100%;height:80px;">${detailMap.ProcessOpinion}</textarea></td>
				</tr>
				<tr>
				  <td colspan="6" class="hr1">&nbsp;</td>
				</tr>
			</table>

	        <BR></BR>  
		    <div class="floatL msg" style="width:100%;">&nbsp;진행상태 내역정보 </div>
			<div id="gridCTSHistDiv" class="mgT10 mgB10 clear">
				<div id="grdCtsGridArea" style="width:100%;height:190px;"></div>
			</div>
			<!--  
			<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
		    -->
		<div class="alignBTN">
			<span class="btn_pack medium icon"><span class="save"></span><input value="Save" onclick="javascript:fnUpdateCts()" type="submit"></span>
		</div>
</div>	



</form>					
<iframe id="saveFrame" name="saveFrame" style="display:none" frameborder="0"></iframe>
	
