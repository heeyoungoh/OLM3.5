<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/uiInc.jsp"%>
 
<script src="${root}cmm/js/jquery/jquery-1.9.1.min.js" type="text/javascript"></script> 
<script src="${root}cmm/js/jquery/jquery.timepicker.min.js" type="text/javascript"></script> 
<script src="${root}cmm/js/jquery/jquery.timepicker.js" type="text/javascript"></script> 
<link rel="stylesheet" type="text/css" href="${root}cmm/common/css/jquery.timepicker.css"/>

<script type="text/javascript" src="${root}cmm/js/xbolt/tinyEditorHelper.js"></script>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00009" var="CM00009"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00049" var="WM00049"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00025" var="WM00025_1" arguments="${srAreaLabelNM1}"/> <!-- 도메인 입력 체크 -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00025" var="WM00025_2" arguments="${srAreaLabelNM2}"/> <!-- 시스템 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00025" var="WM00025_3" arguments="${menu.LN00272}"/> <!-- 카테고리 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_1" arguments="${menu.LN00002}"/> <!-- 제목 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_2" arguments="${menu.LN00003}"/> <!-- 개요 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_3" arguments="${menu.LN00072}"/> <!-- 사용자 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_4" arguments="${menu.LN00025}"/> <!-- 요청자 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_5" arguments="${menu.LN00222}"/> <!-- 완료요청일 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_6" arguments="요청 목적 및 사유"/> <!-- 요청 목적 및 사유 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00014" var="WM00014" arguments="${menu.LN00222}" />
<script type="text/javascript">
	var fileSize = "${itemFiles.size()}";
	var scrnType = "${scrnType}";
	var srType = "${srType}";
	var p_gridArea;
	var propItemList = "";
	var srArea1ListSQL = "${srArea1ListSQL}";
	
	jQuery(document).ready(function() {
		$("input.datePicker").each(generateDatePicker);
		if(srArea1ListSQL == null || srArea1ListSQL == "") srArea1ListSQL = "getESMSRArea1";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}";
		fnSelect('srArea1', data + "&itemTypeCode=${itemTypeCode}", srArea1ListSQL, '${srArea1}', 'Select','esm_SQL');
		fnSelect('category',data+"&level=1", 'getESMSRCategory', '', 'Select', 'esm_SQL');
		
		$('#reqDueDateTime').timepicker({
            timeFormat: 'H:i:s',
        });
		
		if("${itemProposal}" == "Y") {
			$("#itemListTR").attr("style","padding-top:10px;display:done;");
			
			gridCSListInit();
			changeClassCode("${stItemType}"); // 속성 option 초기화
		}
		
	});
	
	function changeClassCode(avg){
		var url    = "getClassCodeOption.do"; // 요청이 날라가는 주소
		var data   = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&option="+avg; //파라미터들
		var target = "classCode";             // selectBox id
		var defaultValue = "";              // 초기에 세팅되고자 하는 값
		var isAll  = "select";                        // "select" 일 경우 선택, "true" 일 경우 전체로 표시
		ajaxSelect(url, data, target, defaultValue, isAll);
	}	

	function itemTypeCodeTreePop() {
		var url = "itemTypeCodeTreePop.do";
		var data = "openMode=add&ItemTypeCode=${stItemType}&languageID=${sessionScope.loginInfo.sessionCurrLangType}&option=${option}";
		fnOpenLayerPopup(url,data,doCallBack,617,436);
	}
	
	function fnCheckValidation(){
		var isCheck = true;		
		var srArea2 = $("#srArea2").val();
		var category = $("#category").val();
		var subject = $("#subject").val();
		var opinion = $("#opinion").val();
		var description = tinyMCE.get('description').getContent();
		$("#description").val(description);
		
		var reqdueDate = $("#reqdueDate").val().replaceAll("-","");
		var currDate = "${thisYmd}";
		var requestUser = $("#requestUserID").val();
		
		if(requestUser == "" || requestUser == null ){ alert("${WM00034_4}"); isCheck = false; return isCheck;}
		if(description == "" ){ alert("${WM00034_2}"); isCheck = false; return isCheck;}
		if(srArea2 == ""){ alert("${WM00025_2}"); isCheck = false; return isCheck;}
		if(category == ""){ alert("${WM00025_3}"); isCheck = false; return isCheck;}
		if(subject == ""){ alert("${WM00034_1}"); isCheck = false; return isCheck;}
		if(reqdueDate == ""){alert("${WM00034_5}"); isCheck = false; return isCheck;}
// 		if(opinion == ""){alert("${WM00034_6}"); isCheck = false; return isCheck;}
		
		if(parseInt(reqdueDate) < parseInt(currDate) ){ 	
			alert("${WM00014}"); isCheck = false; return isCheck;
		} 
	 
		return isCheck;
	}
	
	function fnSaveSR(){		
		if(!confirm("${CM00001}")){ return;}
		if(!fnCheckValidation()){return;}
		$('#srMode').val('N');
		setItemIDs();
		var url  = "createCSPMst.do";
		ajaxSubmit(document.srFrm, url,"saveFrame");
	}
	
	function doDelete(){
		if(confirm("${CM00002}")){
			var url = "deleteBoard.do";
			ajaxSubmit(document.boardFrm, url,"saveFrame");
		}
	}	

	function fnGoSRList(){ 
		var fromSRID = $("#fromSRID").val();
		if(fromSRID == null || fromSRID == ''){
			var url = "itspList.do";
			var data = "srType=${srType}&scrnType=${scrnType}&srMode=${srMode}"
					+ "&pageNum=${pageNum}&category=${category}&searchSrCode=${searchSrCode}&itemProposal=${itemProposal}"
					+ "&srArea2=${srArea2}&subject=${subject}&srStatus=${srStatus}"; 
			var target = "mainLayer";
		} else {
			var url = "esrList.do";
			var data = "fromSRID="+fromSRID;
			var target = "tabFrame";
		}
		
		ajaxPage(url, data, target);
	}
	
	//browser detect
	var browser = (function() {
	  var s = navigator.userAgent.toLowerCase();
	  var match = /(webkit)[ \/](\w.]+)/.exec(s) ||

	              /(opera)(?:.*version)?[ \/](\w.]+)/.exec(s) ||

	              /(msie) ([\w.]+)/.exec(s) ||               

	              /(mozilla)(?:.*? rv:([\w.]+))?/.exec(s) ||

	             [];
	  return { name: match[1] || "", version: match[2] || "0" };
	}());
	
	function doAttachFile(){
		var browserType="";
		if(browser.name == 'msie'){browserType="IE";}
		var url="addFilePop.do";
		var data="scrnType=SR&browserType="+browserType+"&fltpCode=SRDOC";
		
		if(browserType=="IE"){fnOpenLayerPopup(url,data,"",400,400);}
		else{openPopup(url+"?"+data,490,360, "Attach File");}
	}
	
	function fnDeleteItemFile(BoardID, seq){
		var url = "boardFileDelete.do";
		var data = "&delType=1&BoardID="+BoardID+"&Seq="+seq;
		ajaxPage(url, data, "blankFrame");
		
		fnDeleteFileHtml(seq);
	}
	
	//*************** addFilePop 설정 **************************//
	var fileIDMap = new Map();
	var fileNameMap = new Map();

	function fnDeleteFileHtml(seq, fileName){			
		if(fileName == "" || fileName == undefined){
			try{fileName = document.getElementById(seq).innerText;}catch(e){}
		}
		
		fileIDMap.delete(String(seq));
		fileNameMap.delete(String(seq));
		
		if(fileName != "" && fileName != null && fileName != undefined){
			$("#"+seq).remove();
			var url  = "removeFile.do";
			var data = "fileName="+fileName;	
			ajaxPage(url,data,"blankFrame");
		}
	}
	
	function fnAttacthFileHtml(fileID, fileName){ 			
		fileIDMap.set(fileID,fileID);
		fileNameMap.set(fileID,fileName);
	}
	
	function fnDisplayTempFile(){
		var sampleTimestamp = Date.now(); //현재시간 타임스탬프 13자리 예)1599891939914
				
		display_scripts=$("#tmp_file_items").html(); 
		fileIDMap.forEach(function(fileID) {			  
			  display_scripts = display_scripts+
				'<div id="'+fileID+sampleTimestamp+'"  class="mm" name="'+fileID+sampleTimestamp+'">'+ fileNameMap.get(fileID) +
					'	<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteFileHtml('+fileID+sampleTimestamp+')">'+
					'	<br>'+
					'</div>';		
		});
		document.getElementById("tmp_file_items").innerHTML = display_scripts;		
		$("#tmp_file_items").attr('style', 'display: done');
	
		fileIDMap = new Map();
		fileNameMap = new Map();
	}
	//*************** addFilePop 설정 **************************//
	
	/* 첨부문서 다운로드 */
	function FileDownload(checkboxName, isAll){
		var originalFileName = new Array();
		var sysFileName = new Array();
		var seq = new Array();
		var j =0;
		var checkObj = document.all(checkboxName);
		
		// 모두 체크 처리를 해준다.
		if (isAll == 'Y') {
			if (checkObj.length == undefined) {
				checkObj.checked = true;
			}
			for (var i = 0; i < checkObj.length; i++) {
				checkObj[i].checked = true;
			}
		}

		// 하나의 파일만 체크 되었을 경우
		if (checkObj.length == undefined) {
			if (checkObj.checked) {
				seq[0] = checkObj.value;
				j++;
			}
		};
		for (var i = 0; i < checkObj.length; i++) {
			if (checkObj[i].checked) {
				seq[j] = checkObj[i].value;
				j++;
			}
		}
		if(j==0){
			alert("${WM00049}");
			return;
		}
		j =0;
		var url  = "fileDownload.do?seq="+seq+"&scrnType=BRD";
		//alert(url);
		ajaxSubmitNoAdd(document.boardFrm, url,"blankFrame");
		// 모두 체크 해제
		if (isAll == 'Y') {
			if (checkObj.length == undefined) {
				checkObj.checked = false;
			}
			for (var i = 0; i < checkObj.length; i++) {
				checkObj[i].checked = false;
			}
		}
	}
	
	function fileNameClick(avg1){
		var seq = new Array();
		seq[0] = avg1;
		var url  = "fileDownload.do?seq="+seq+"&scrnType=BRD";
		ajaxSubmitNoAdd(document.boardFrm, url,"blankFrame");
	}	
	
	function fnGetSRArea2(memberID){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&memberID="+memberID;
		fnSelect('srArea2', data, 'getItemByCustomer', '', 'Select','crm_SQL');
	}
	
	function searchPopupWf(avg){
		var searchValue = $("#ReqUserNM").val();
		if(searchValue == ""){
			alert("${WM00034_3}");
			return;
		}
		var url = avg + "&searchValue=" + encodeURIComponent($('#ReqUserNM').val()) 
					+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		window.open(url,'window','width=340, height=300, left=300, top=300,scrollbar=yes,resizble=0');
	}
	
	function setSearchNameWf(avg1,avg2,avg3){
		// window.opener.setSearchNameWf(avg1, avg2, avg3, $('#objId').val(), $('#objName').val());
		$("#ReqUserNM").val(avg2+"("+avg3+")");
		$("#requestUserID").val(avg1);
		fnGetSRArea2(avg1);
	}

	function fnCreateItem(){	
		$("#addNewItem").removeAttr('style', 'display: none');
		
		
		$("#divTapItemAdd").removeAttr('style', 'display: none');
		$("#transDiv").attr('style', 'display: none');
		$("#moveOrg").attr('style', 'display: none');		
		$("#newIdentifier").focus();
		
	}
	

	function setItemIDs() {
		var items = "";
		var status = "";
		if(p_gridArea != undefined && p_gridArea.getRowsNum() > 0) {
			var checkedRows = p_gridArea.getCheckedRows(1).split(",");	
			
			for(var i = 0 ; i < checkedRows.length; i++ ){			
				status = p_gridArea.cells(checkedRows[i], 9).getValue();
			
				if (items == "") {
					items = p_gridArea.cells(checkedRows[i], 8).getValue();
				} else {
					items = items + "," + p_gridArea.cells(checkedRows[i], 8).getValue();
				}
				
			}
		}
		
		$("#itemIDs").val(items);
	}
	

	function newItemInsert(addYN){
		// 입력 필수 체크 : 계층, 명칭, CSR 입력 필수
		if($("#newItemName").val() == ""){alert("${WM00034_1}");$("#newItemName").focus();return false;}
		var newItemName = encodeURIComponent($("#newItemName").val());
		
		if(confirm("${CM00009}")){		
		
			var url = "createItemForSR.do";
			var data = "s_itemID="+$("#parentID").val()+"&option=${option}&authorID=${sessionScope.loginInfo.sessionUserId}"
						+"&itemClassCode="+$("#classCode").val()
						+"&itemTypeCode=${stItemType}"
						+"&newIdentifier="+$("#newIdentifier").val()
						+"&newItemName="+newItemName
						+"&projectID=${projectID}"
						+"&srID=${getMap.SRID}"
						+"&addYN="+addYN;
						
			var target = "blankFrame";		
			ajaxPage(url, data, target);
		}
	}
	
	function doSearchList() {

		var d = setGridData();		
		
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data, false, "", "", "", "", "${WM00119}", 1000);		
	}
	
	function doReturnInsert(addYN, itemID){
			
		setPropItemList(itemID);
		
		$("#newIdentifier").val("");
		$("#newItemName").val("");
		$("#parentNM").val("");
		$("#parentID").val("");
		
		if(addYN == "Y"){	
			$("#addNewItem").attr('style', 'display: done');	
			$("#divTapItemAdd").attr('style', 'display: done');
		}else{	
			$("#addNewItem").attr('style', 'display: none');	
			$("#divTapItemAdd").attr('style', 'display: none');
			
		}
		
		doSearchList();
	}
	
	function gridCSListInit(){
		var d = setGridData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");
		
		p_gridArea.attachEvent("onRowSelect", function(id,ind){ //id : ROWNUM, ind:Index
			gridOnRowSelect(id,ind);
		});
		
		fnSetColType(p_gridArea, 1, "ch");
		p_gridArea.setColumnHidden(8, true);
		p_gridArea.setColumnHidden(9, true);
			
	}
	
	function setGridData(){
		var result = new Object();
		result.title = "${title}";
		
		result.key = "esm_SQL.getSrItemList";
		
		result.header = "${menu.LN00024},#master_checkbox,${menu.LN00015},${menu.LN00028},${menu.LN00043},${menu.LN00027},${menu.LN00004},${menu.LN00070},22,33";
		result.cols = "CHK|Identifier|ItemNM|Path|StatusNM|AuthorName|LastUpdated|ItemID|Status";
		result.widths = "30,30,100,200,300,80,80,100,10,10";
		result.sorting = "int,int,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,center,center,center,center,center,center,center";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					+ "&authorID=${sessionScope.loginInfo.sessionUserId}&isNew=Y"
					+ "&itemTypeCode=${stItemType}"
					+ "&itemIDs=" + propItemList
					+ "&status=REL" ;					
					
		return result;
	}
	
	// 그리드ROW선택시
	function gridOnRowSelect(id, ind){
		
		var itemId = p_gridArea.cells(id, 8).getValue();
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemId+"&scrnType=pop&screenMode=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,itemId);
		
	}

	function doCallBack(){}
	
	// After [Add -> Assign]
	function setParentItem(parentItemId, parentItemPath){
		$("#parentID").val(parentItemId);
		$("#parentNM").val(parentItemPath);
		
		$(".popup_div").hide();
		$("#mask").hide();	
	}	
	
	function fnAssignItem(){
		
		$("#newItemArea").attr("style", "display:none;");
		$("#relatedItemList").attr("style","height:"+(setWindowHeight() - 90)+"px;");
		
		var connectionType = "To";
		var s_itemID = $("#srArea2").val();
		
		if(s_itemID == "" || s_itemID == undefined){
			alert("${srAreaLabelNM2} Select");
		}
		else {
			var url = "itemTypeCodeTreePop.do";
			var data = "openMode=assign&ItemTypeCode=${stItemType}&languageID=${sessionScope.loginInfo.sessionCurrLangType}&tFilterCode=PRM"
						+"&rootItemID="+s_itemID+"&connectionType=" + connectionType;
						
			fnOpenLayerPopup(url,data,doCallBack,617,436);
		}
	
	}

	//After [Assign -> Assign]
	function setCheckedItems(checkedItems){
		
		setPropItemList(checkedItems);
		
		doSearchList();
		$(".popup_div").hide();
		$("#mask").hide();	
		
	}
	
	function setPropItemList(avg) {
		if(propItemList == "") {
			propItemList = avg;
		}
		else {
			propItemList = propItemList + "," + avg;
		}
	}
	
	function searchPopupWf(avg){
		var searchValue = $("#ReqUserNM").val();
		if(searchValue == ""){
			alert("${WM00034_3}");
			return;
		}
		var url = avg + "&searchValue=" + encodeURIComponent($('#ReqUserNM').val()) 
					+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		window.open(url,'window','width=340, height=300, left=300, top=300,scrollbar=yes,resizble=0');
	}
	
	function addSharer(){
		var sharers = $("#sharers").val();
		

		var url = "selectMemberPop.do?mbrRoleType=R&projectID=${projectID}&s_memberIDs="+sharers;
		window.open(url,"srFrm","width=900 height=700 resizable=yes");					
	}
	
	function addApprover(){
		var approvers = $("#approvers").val();
		
		var url = "selectMemberPop.do?mbrRoleType=APRV&projectId=${projectID}&s_memberIDs="+approvers+"&addApprover=Y";
		window.open(url,"srFrm","width=900 height=700 resizable=yes");					
	}
	
	function setSharer(memberIds,memberNames) {
		$("#sharers").val(memberIds);
		$("#sharerNames").val(memberNames);
	}
	
	function setApprovers(memberIds,memberNames) {
		$("#approvers").val(memberIds);
		$("#approverNames").val(memberNames);
	}

	function fnMSClear(){
		//input[type=text]::-ms-clear
		//$("#Identifier").attr('disabled',true);
	}
	
	//************** addFilePop V4 설정 START ************************//
	function doAttachFileV4(){
		var url="addFilePopV4.do";
		var data="scrnType=SR&fltpCode=SRDOC";
		openPopup(url+"?"+data,490,450, "Attach File");
	} 
	
	var fileIDMapV4 = new Map();
	var fileNameMapV4 = new Map();
	function fnAttacthFileHtmlV4(fileID, fileName){ 
		fileID = fileID.replace("u","");
		fileIDMapV4.set(fileID,fileID);
		fileNameMapV4.set(fileID,fileName);
	}
	
	// addFilePopV4에서 파일 삭제시, fileMap에서 해당파일 제거 
	function fnDeleteFileMapV4(fileID){ 
		fileID = fileID.replace("u","");		
		fileIDMapV4.delete(String(fileID));
		fileNameMapV4.delete(String(fileID));
	}
	
	function fnDisplayTempFileV4(){				
		display_scripts=$("#tmp_file_items").html(); 
		fileIDMapV4.forEach(function(fileID) {			  
			  display_scripts = display_scripts+
				'<div id="'+fileID+'"  class="mm" name="'+fileID+'">'+ fileNameMapV4.get(fileID) +
					'	<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteFileHtmlV4('+fileID+')">'+
					'	<br>'+
					'</div>';		
		});
		document.getElementById("tmp_file_items").innerHTML = display_scripts;		
		$("#tmp_file_items").attr('style', 'display: done');
	
		fileIDMapV4 = new Map();
		fileNameMapV4 = new Map();
	}
	 
	//  dhtmlx v 4.0 delete file  
	function fnDeleteFileHtmlV4(fileID){		
		var fileName = document.getElementById(fileID).innerText;		
		fileIDMapV4.delete(String(fileID));
		fileNameMapV4.delete(String(fileID));
		
		if(fileName != "" && fileName != null && fileName != undefined){
			$("#"+fileID).remove();
			var url  = "removeFile.do";
			var data = "fileName="+fileName;	
			ajaxPage(url,data,"blankFrame");
		}
	} 
	//************** addFilePop V4 설정 END ************************//
	
</script>
</head>

<style>
	a:hover{
		text-decoration:underline;
	}
	input[type=text]::-ms-clear{
		display: done;
	}
</style>

<body>
<div>
	<form name="srFrm" id="srFrm" enctype="multipart/form-data" action="" method="post" onsubmit="return false;">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}">
	<input type="hidden" id="scrnType" name="scrnType" value="${scrnType}">
	<input type="hidden" id="srMode" name="srMode" value="${srMode}">
	<input type="hidden" id="srType" name="srType" value="${srType}">
	<input type="hidden" id="requestUserID" name="requestUserID" />
	<input type="hidden" id="sysCode" name="sysCode" value="${sysCode}" />
	<input type="hidden" id="proposal" name="proposal" value="${proposal}" />
	<input type="hidden" id="itemIDs" name="itemIDs" value="" />
	<input type="hidden" id="fromSRID" name="fromSRID" value="${fromSRID}" />
	<div class="cop_hdtitle mgT">
		<h3 style="padding: 8px 0;"><img src="${root}${HTML_IMG_DIR}/icon_folder_upload_title.png"></img>&nbsp;${menu.LN00280}</h3>
	</div>
	<table class="tbl_brd" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0"> 
		<colgroup>
			<col width="15%">
			<col>
			<col width="15%">
			<col>
		</colgroup>
		<tr>
		    <th class="alignL pdL10" style="height:15px;">${menu.LN00025}</th>
		  	<td class="sline tit last" >
				<input type="text" class="text" id="ReqUserNM" name="ReqUserNM"  style="ime-mode:active;width:250px;" />
				<input type="hidden" id="requestUserID" name="requestUserID" value="${sessionScope.loginInfo.sessionUserId}" />
				<input type="image" class="image" id="searchRequestBtn" name="searchRequestBtn" src="${root}${HTML_IMG_DIR}/btn_icon_search.png" onclick="searchPopupWf('searchPluralNamePop.do?objId=resultID&objName=resultName&UserLevel=ALL')" value="검색">
			</td>
			<!-- srArea2 -->
			 <th class="alignL pdL10">${srAreaLabelNM2}<font color="red">&nbsp;*</font></th>
			<td class="sline tit last">
				<select id="srArea2" Name="srArea2" style="width:250px">
         		   <option value=''>Select</option>
         		  </select>
			</td>
		</tr>
		<tr>
			<!-- 카테고리 -->
			<th class="alignL pdL10">${menu.LN00272}<font color="red">&nbsp;*</font></th>
			<td class="sline tit last">
				<select id="category" name="category" class="sel" style="width:250px;margin-left=5px;"></select>
			</td>
			<!-- 완료요청일 -->
			<th class="alignL pdL10">${menu.LN00222}<font color="red">&nbsp;*</font></th>
			<td class="sline tit last" >
				<font><input type="text" id="reqdueDate" name="reqdueDate" class="text datePicker stext" size="8"
					style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
				</font>
				<input type="text" id="reqDueDateTime" name="reqDueDateTime" class="input_off text" size="8" style="width:20%; text-align: center;" maxlength="10" value="18:00:00">
			</td>
		</tr>
		<!-- <tr>
			<th class="alignL pdL10">요청 목적 및 사유</th>
			<td class="sline tit last" colspan="3" >
				<input type="text" class="text" id="opinion" name="opinion" value="" style="ime-mode:active;" placeholder="※ 현재 업무 대비 영향도 및 요청자의 요청 의도를 정확히 확인하기 위해 꼭 기입해주시기 바랍니다."/>
			</td>
		</tr> -->
		<tr>
			<th class="alignL pdL10">${menu.LN00002}</th><!-- 제목 -->
			<td class="sline tit last" colspan="3" >
				<input type="text" class="text" id="subject" name="subject" value="" style="ime-mode:active;" />
			</td>
		</tr>		
	</table>
	<table  width="100%"  cellpadding="0"  cellspacing="0">
		<tr>
			<td style="height:300px;" class="tit last">
				<textarea  class="tinymceText" id="description" name="description" style="width:100%;height:300px;"></textarea>					
			</td>
		</tr>	
	</table>
	
	<table class="tbl_brd mgT5" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="15%">
			<col>
			<col width="15%">
			<col>
		</colgroup>	
		<tr>
			<!-- 첨부문서 -->
			<th class="alignL pdL10" style="height:53px;">${menu.LN00111}</th>
			<td colspan="3" style="height:53px;" class="alignL pdL5 last">
				<div style="height:53px;width:100%;overflow:auto;overflow-x:hidden;">
				<div id="tmp_file_items" name="tmp_file_items" style="display:none;"></div>
				<div class="floatR pdR20" id="divFileImg">
				<c:if test="${itemFiles.size() > 0}">
					<span class="btn_pack medium icon mgB2"><span class="download"></span><input value="&nbsp;Save All&nbsp;&nbsp;" type="button" onclick="FileDownload('attachFileCheck', 'Y')"></span><br>
					<span class="btn_pack medium icon"><span class="download"></span><input value="Download" type="button" onclick="FileDownload('attachFileCheck', 'N')"></span><br>
				</c:if>
				</div>
				<c:forEach var="fileList" items="${itemFiles}" varStatus="status">
					<div id="divDownFile${fileList.Seq}"  class="mm" name="divDownFile${fileList.Seq}">
						<input type="checkbox" name="attachFileCheck" value="${fileList.Seq}" class="mgL2 mgR2">
						<span style="cursor:pointer;" onclick="fileNameClick('${fileList.Seq}');">${fileList.FileRealName}</span>
						<c:if test="${sessionScope.loginInfo.sessionUserId == resultMap.RegUserID}"><img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteItemFile('${fileList.BoardID}','${fileList.Seq}')"></c:if>
						<br>
					</div>
				</c:forEach>
				</div>
			</td>
		</tr>
		<tr>
			<th class="alignL pdL10"><a onclick="addSharer();">${menu.LN00245}<img class="searchList mgL5" src="${root}${HTML_IMG_DIR}/btn_icon_sharer.png" style="cursor:pointer;"></a></th>
			<td class="sline tit last" colspan="3">
				<input type="text" class="text" id="sharerNames" name="sharerNames" />			
				<input type="hidden" class="text" id="sharers" name="sharers" size="10"/>
			</td>
		</tr>	
		<!-- <tr>
			<th class="alignL pdL10"><a onclick="addApprover();">승인자 추가<img class="searchList mgL5" src="${root}${HTML_IMG_DIR}/btn_icon_sharer.png" style="cursor:pointer;"></a></th>
			<td class="sline tit last" colspan="3">
				<input type="text" class="text" id="approverNames" name="approverNames" readonly/>			
				<input type="hidden" class="text" id="approvers" name="approvers" size="10"/>
			</td>
		</tr> -->
	</table>
	
	<div id="itemListTR" style="display:none;padding-top:10px;">		
		<li class="org_title">
           <h3><img src="${root}${HTML_IMG_DIR}/icon_folder_upload_title.png"></img>&nbsp;Item List</h3>
        </li>		
		<li class="floatR" style="padding-bottom:5px;">	
			<span class="btn_pack small icon"><span class="add"></span><input value="Add" type="submit" id="newButton1"  onclick="fnCreateItem()" ></a></span>&nbsp;
			<span class="btn_pack small icon"><span class="add"></span><input value="Assign" type="submit" id="newButton1"  onclick="fnAssignItem()" ></a></span>&nbsp;
		</li>		
		<li>
			<div id="gridCngtDiv" style="width:100%;height:200px;" class="clear">
				<div id="grdGridArea" style="width:100%;height:160px;"></div>
			</div>		
		</li>
	</div>
	<div id="divTapItemAdd" class="ddoverlap mgB5" style="display: none;">
		<ul>
			<li class="selected" ><a><span>${menu.LN00096}</span></a></li>
		</ul>
	</div>
	<table id="addNewItem" class="tbl_blue01" width="100%"  cellpadding="0" cellspacing="0" style="display: none;">
		<tr>
			<th>${menu.LN00015}</th>
			<th>${menu.LN00028}</th>
			<th>${menu.LN00016}</th>
			<th class="last">Parent Code</th>
		</tr>
		<tr>
			<td style="width:15%"><input type="text" class="text" id="newIdentifier" name="newIdentifier"  value=""/></td>
			<td><input type="text" class="text" id="newItemName" name="newItemName"  value=""/></td>	
			
            <td>
            	<select id="classCode" name="classCode" style="width:100%;" >
                    <option value="">Select</option>    
				</select>
			</td>		
			<td class="last" style="width:30%">
				<input type="text" class="text" id="parentNM" name="parentNM"  value="" onClick="itemTypeCodeTreePop()" readonly="readonly"/>
				<input type="hidden"  id="parentID" name="parentID"  value=""/>
			</td>
		</tr>	
		<tr>
			<td colspan="4" class="last" align="right">
				<span class="btn_pack medium icon"><span class="save"></span><input value="Save" onclick="newItemInsert()"  type="submit"></span>&nbsp;
				<span class="btn_pack medium icon"><span class="save"></span><input value="Save and Add" onclick="newItemInsert('Y')"  type="submit"></span>&nbsp;
			</td>
		</tr>						
	</table>
	
	<table class="tbl_brd mgT5" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0">	
	<tr> 
		 <th class="alignR pdR20 last" bgcolor="#f9f9f9" colspan="4"  style="vertical-align:middle;" >
			<!-- span id="viewList" class="btn_pack medium icon"><span class="list"></span><input value="List" type="submit"  onclick="fnGoSRList();"></span -->
			<span class="btn_pack medium icon"><span class="upload"></span><input value="Attach" type="submit" onclick="doAttachFileV4()"></span>
			<span id="viewSave" class="btn_pack medium icon"><span class="confirm"></span><input value="Submit" type="submit" onclick="fnSaveSR()"></span>&nbsp;&nbsp;
		 </th>		 
	</tr>
	</table>
	<div class="alignR pdL10">${menu.LN00291}</div>

	</form>
</div>
<!-- END :: DETAIL -->
<iframe id="saveFrame" name="saveFrame" style="width:0px;height:0px;display: none;" ></iframe>
</body>
</html>
