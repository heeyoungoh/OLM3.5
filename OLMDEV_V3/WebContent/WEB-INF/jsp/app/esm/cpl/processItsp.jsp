<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!--1. Include JSP -->
<%@ include file="/WEB-INF/jsp/cmm/fileAttachHelper.jsp"%>
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>

<script type="text/javascript" src="${root}cmm/js/xbolt/tinyEditorHelper.js"></script>


<script src="${root}cmm/js/jquery/jquery-1.9.1.min.js" type="text/javascript"></script> 
<script src="${root}cmm/js/jquery/jquery.timepicker.min.js" type="text/javascript"></script> 
<script src="${root}cmm/js/jquery/jquery.timepicker.js" type="text/javascript"></script> 
<link rel="stylesheet" type="text/css" href="${root}cmm/common/css/jquery.timepicker.css"/>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00049" var="WM00049"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00016" var="CM00016"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00054" var="CM00054"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00056" var="CM00056"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_1" arguments="${menu.LN00274}"/> <!-- 도메인 입력 체크 -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_2" arguments="${menu.LN00185}"/> <!-- 시스템 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_3" arguments="${menu.LN00272}"/> <!-- 카테고리 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_4" arguments="${menu.LN00273}"/> <!-- 서브카테고리 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_5" arguments="${menu.LN00067}"/> <!-- 우선순위 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_6" arguments="Comment"/> <!-- Comment 입력 체크  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_7" arguments="${menu.LN00221}"/> <!-- 완료예정일  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_8" arguments="SR Classfication"/> <!-- SR Classfication  -->
<script type="text/javascript">
	var fileSize = "${itemFiles.size()}";
	var scrnType = "${scrnType}";
	var srType = "${srType}";
	var p_gridArea;

	jQuery(document).ready(function() {
		
		$("input.datePicker").each(generateDatePicker);
		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}";
		fnSelect('srArea1', data, 'getESMSRArea1', '${srInfoMap.SRArea1}', 'Select', 'esm_SQL');
		fnSelect('category', data+"&level=1", 'getESMSRCategory', '${srInfoMap.Category}', 'Select', 'esm_SQL');
		fnSelect('priority', data+"&category=PRT", 'getDictionaryOrdStnm', '${srInfoMap.Priority}', 'Select');
		fnSelect('proposal', data+"&category=PROPSSTS", 'getDictionaryOrdStnm', '${srInfoMap.Proposal}', 'Select');
		// fnSelect('classification', data+"&category=SRCLS", 'getDictionaryOrdStnm', '${srInfoMap.Classification}', 'Select');
		fnGetSRArea2("${srInfoMap.SRArea1}","${srInfoMap.SRArea2}");
		fnGetSubCategory("${srInfoMap.Category}","${srInfoMap.SubCategory}");
		
		if("${itemProposal}" == "Y") 
			$("#itemListTR").attr("style","display:done");		
	
		$('#dueDateTime').timepicker({
            timeFormat: 'H:i:s',
        });
		
		gridCSListInit();
	});

		
	function fnSaveReceiveItspInfo(){
		if(!confirm("${CM00001}")){ return;}
		$('#srMode').val('N');
		var priority = $("#priority").val() ; if(priority == ""){ $("#priority").val("03")}
		var url  = "saveItspInfo.do";
		ajaxSubmit(document.receiptSRFrm, url,"saveFrame");
	}
	
	function fnCallBackSR(){
		var url = "processItsp.do";
		var data = "srCode=${srCode}&pageNum=${pageNum}"
					+"&srMode=${srMode}&srType=${srType}&scrnType=${scrnType}&srID=${srInfoMap.SRID}"
					+"&receiptUserID=${srInfoMap.ReceiptUserID}&status=${status}&projectID=${projectID}&itemID=${itemID}";
		var target = "help_content";
		if("${scrnType}"=="ITM"){
			target = "processSRDIV";
		}
		ajaxPage(url, data, target);
	}
	
	function fnGoSRList(){
		var url = "itspList.do";
		var data = "srType=${srType}&scrnType=${scrnType}&srMode=${srMode}&pageNum=${pageNum}&projectID=${projectID}"
					+ "&status=${status}&category=${category}&subCategory=${subCategory}"
					+ "&srArea1=${srArea1}&srArea2=${srArea2}&subject=${subject}"
					+ "&receiptUser=${receiptUser}&requestUser=${requestUser}&requestTeam=${requestTeam}"
					+ "&startRegDT=${startRegDT}&endRegDT=${endRegDT}&searchSrCode=${searchSrCode}"
					+ "&stSRDueDate=${stSRDueDate}&endSRDueDate=${endSRDueDate}&stCRDueDate=${stCRDueDate}&endCRDueDate=${endCRDueDate}"
					+ "&srReceiptTeam=${srReceiptTeam}&crReceiptTeam=${crReceiptTeam}";
		var target = "help_content"; 
		if("${scrnType}"=="ITM"){
			target = "processSRDIV";
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
	
	function fnDeleteSRFile(srID, seq, fileName, filePath){
		var url = "deleteSRFile.do";
		var data = "srID="+srID+"&Seq="+seq+"&realFile="+filePath+fileName;
		ajaxPage(url, data, "saveFrame");
		$('#divDownFile'+seq).hide();
	}
	
	function fnDeleteFileHtml(seq){	
		var divId = "divDownFile"+seq;
		$('#'+divId).hide();		
	}
	
	function fnAttacthFileHtml(seq, fileName){ 
		display_scripts=$("#tmp_file_items").html();
		display_scripts = display_scripts+
			'<div id="divDownFile'+seq+'"  class="mm" name="divDownFile'+seq+'">'+fileName+
			'	<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteFileHtml('+seq+')">'+
			'	<br>'+
			'</div>';		
		document.getElementById("tmp_file_items").innerHTML = display_scripts;		
	}
	
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
		var url  = "fileDownload.do?seq="+seq+"&scrnType=SR";
		ajaxSubmitNoAdd(document.receiptSRFrm, url,"saveFrame");
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
		var originalFileName = new Array();
		var sysFileName = new Array();
		var filePath = new Array();
		var seq = new Array();
		seq[0] = avg1;
		var url  = "fileDownload.do?seq="+seq+"&scrnType=SR";
		ajaxSubmitNoAdd(document.receiptSRFrm, url,"saveFrame");
	}	
	
	function fnGetSRArea2(SRArea1ID,srArea2){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&parentID="+SRArea1ID;
		fnSelect('srArea2', data, 'getSrArea2', srArea2, 'Select');
	}
	
	function fnGetSubCategory(category,subCategory){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&parentID="+category+"&level=2";
		fnSelect('subCategory', data, 'getESMSRCategory', subCategory, 'Select', 'esm_SQL');
	}
	
	function fnCheckValidation(){
		var isCheck = true;
		
		var subCategory = $("#subCategory").val() ;
		//var priority = $("#priority").val() ;
		var comment = tinyMCE.get('comment').getContent();
		var dueDate = $("#dueDate").val() ; 
		//var classification = $("#classification").val() ;
	
		if(subCategory == ""){ alert("${WM00034_4}"); isCheck = false; return isCheck;}
		//if(priority == ""){ alert("${WM00034_5}"); isCheck = false; return isCheck;}
		if(comment == ""){ alert("${WM00034_6}"); isCheck = false; return isCheck;}
		if(dueDate == ""){ alert("${WM00034_7}"); isCheck = false; return isCheck;}
		//if(classification == ""){ alert("${WM00034_8}"); isCheck = false; return isCheck;}
		
		return isCheck;
	}
	
	// SR 이관 
	function fnOpenTransferPop(){ 	
		var url = "goTransferItspPop.do?srID=${srInfoMap.SRID}&srType=${srType}";
		window.open(url,'window','width=1100, height=320, left=200, top=100,scrollbar=yes,resizble=0');
	}
		
	// [Create CSR] 버튼 이벤트
	function addNewCsrPjt(avg) {
		/* var isNew ="Y";		
		var url = "csrDetailPop.do?isNew="+ isNew + "&btn=" + avg 
				   + "&mainMenu=${mainMenu}&s_itemID=${s_itemID}&scrnType=${scrnType}&srID=${srInfoMap.SRID}&fromSR=Y"; */
		var url = "registerCSR.do?&mainMenu=${mainMenu}&s_itemID=${s_itemID}&scrnType=${scrnType}&srID=${srInfoMap.SRID}&fromITSP=Y";
		var w = 1100;
		var h = 420;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=yes,resizable=yes,scrollbars=yes");
	}
	
	function fnCompletion(){		
		if(!confirm("${CM00054}")){ return;}
		if(!fnCheckValidation()){return;}
		var url  = "completionItsp.do?speCode=${srInfoMap.LastSpeCode}&svcCompl=Y";
		ajaxSubmit(document.receiptSRFrm, url,"saveFrame");
	}
	
	function fnOpenDocument(PID,url,varFilter,ActID,procSeq){
		if(varFilter == "CSR"){
			var url = url+"?ProjectID="+PID+"&screenMode=V";
		}else if(varFilter == "SCR"){
			var url = url+"?srID=${srInfoMap.SRID}&scrID="+PID+"&screenMode=V";	
		}else if(varFilter == "WF"){
			var url = url+"?srID=${srInfoMap.SRID}&documentID=${srInfoMap.SRID}&wfInstanceID="+PID+"&isPop=Y&isMulti=N&actionType=view&docCategory=SR";	
		}else{
			url = url +"?PID="+PID+"&activityID="+ActID+"&procSeq="+procSeq;
		}
		window.open(url,'window','width=1200, height=714, scrollbars=yes, top=100,left=100,toolbar=no,status=yes,resizable=yes');
	}
	
	function fnUpdateProposal(){
		if(!confirm("${CM00056}")){ return;}
		var url  = "updateProposal.do";
		ajaxSubmit(document.receiptSRFrm, url,"saveFrame");
	}
	
	function fnReload(){
		fnCallBackSR();
	}
	
	function fnCallBack(){
		fnCallBackSR();
	}
	
	function gridCSListInit(){
		var d = setGridData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");
				
		fnSetColType(p_gridArea, 1, "ch");
		p_gridArea.setColumnHidden(8, true);
		p_gridArea.setColumnHidden(9, true);
		
		p_gridArea.attachEvent("onRowSelect", function(id,ind){ //id : ROWNUM, ind:Index
			gridOnRowSelect(id,ind);
		});
		
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data, false, "", "", "", "", "${WM00119}", 1000);		
	}
	
	function setGridData(){
		var result = new Object();
		result.title = "${title}";

		result.key = "esm_SQL.getSrItemList";
		
		result.header = "${menu.LN00024},#master_checkbox,${menu.LN00015},${menu.LN00028},${menu.LN00043},${menu.LN00027},${menu.LN00004},${menu.LN00070},1,2";
		result.cols = "CHK|Identifier|ItemNM|Path|StatusNM|AuthorName|LastUpdated|ItemID|Status";
		result.widths = "30,30,100,200,300,80,80,100,10,10";
		result.sorting = "int,int,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,center,center,center,center,center,center,center";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					+ "&authorID=${sessionScope.loginInfo.sessionUserId}"
					+ "&itemTypeCode=${stItemType}"
					+ "&srID=${srInfoMap.SRID}";					
					
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
	
	function fnApproveItspPop(){
		var url = "wfDocMgt.do?actionType=create&srID=${srInfoMap.SRID}&srType=${srType}"
				+ "&WFDocURL=${WFDocURL}&wfDocType=SR&ProjectID=${srInfoMap.ProjectID}"
				+ "&srArea1=${srInfoMap.SRArea1}"
				+ "&srArea2=${srInfoMap.SRArea2}"
				+ "&srRequestUserID=${srInfoMap.RequestUserID}"
				+ "&isPop=Y";
		window.open(url,'window','width=1000, height=700, left=300, top=300,scrollbar=yes,resizble=0');
	}
	
	function fnRegistSCR(){
		var url = "registerSCR.do?srID=${srInfoMap.SRID}";
		var w = 1100;
		var h = 650;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=yes,resizable=yes,scrollbars=yes");
	}
	
	function fnViewScrDetail(scrID){
		var screenMode = "V";
		var url = "viewScrDetail.do";
		var data = "scrID="+scrID+"&screenMode="+screenMode+"&srID=${srInfoMap.SRID}"; 
		var w = 1100;
		var h = 800;
		window.open(url+"?"+data, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=yes,resizable=yes,scrollbars=yes");
		
		fnCallBackSR();
	}

</script>

<div id="processSRDIV"> 
	<form name="receiptSRFrm" id="receiptSRFrm" enctype="multipart/form-data" action="" method="post" onsubmit="return false;">
	<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}">
	<input type="hidden" id="scrnType" name="scrnType" value="${scrnType}">
	<input type="hidden" id="srMode" name="srMode" value="${srMode}">
	<input type="hidden" id="srType" name="srType" value="${srType}">
	<input type="hidden" id="crCnt" name="crCnt" value="${crCnt}">
	<input type="hidden" id="srID" name="srID" value="${srInfoMap.SRID}">
	<input type="hidden" id="srCode" name="srCode" value="${srInfoMap.SRCode}">
	<input type="hidden" id="receiptUserID" name="receiptUserID" value="${srInfoMap.ReceiptUserID}">
	<input type="hidden" id="receiptTeamID" name="receiptTeamID" value="${srInfoMap.ReceiptTeamID}">
	<input type="hidden" id="requestUserID" name="requestUserID" value="${srInfoMap.RequestUserID}">
	<input type="hidden" id="requestTeamID" name="requestTeamID" value="${srInfoMap.RequestTeamID}">
	<input type="hidden" id="subject" name="subject" value="${srInfoMap.Subject}">
	<div class="cop_hdtitle" style="border-bottom:1px solid #ccc">
		<h3 style="padding: 6px 0 6px 0"><img src="${root}${HTML_IMG_DIR}/icon_folder_upload_title.png"></img>&nbsp;${menu.LN00282}</h3>
	</div>
	<table class="tbl_brd mgT10" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="10%">
		    <col width="23%">
		 	<col width="10%">
		    <col width="23%">
		 	<col width="10%">
		    <col width="24%">
		</colgroup>		
		<tr>
		<!-- SR No -->
		<th class="alignL pdL10">SR No.</th>
		<td class="sline tit last" >${srInfoMap.SRCode}</td>	
		  <th class="alignL pdL10">${menu.LN00025}</th>
		  <c:if test="${MULTI_COMPANY != 'Y'}" >
				<td class="sline tit last" >${srInfoMap.ReqUserNM}(${srInfoMap.ReqTeamNM})</td>
		  </c:if>
		  <c:if test="${MULTI_COMPANY == 'Y'}" >
				<td class="sline tit last" >${srInfoMap.ReqUserNM}(${srInfoMap.ReqTeamNM}/${srInfoMap.CompanyName})</td>
		  </c:if>			
		  <!-- 완료요청일 -->
		  <th class="alignL pdL10">${menu.LN00222}</th>
		  <td class="sline tit  last" >${srInfoMap.ReqDueDate}&nbsp;${srInfoMap.ReqDueDateTime}</td>			
		</tr>
		<tr>
			<!-- 도메인 -->
			<th class="alignL pdL10">${srInfoMap.SRArea1NM}</th>
			<td class="sline tit last" >
				<select id="srArea1" Name="srArea1" OnChange="fnGetSRArea2(this.value);" style="width:90%">
	       			<option value=''>Select</option>
	     	  	</select>
			</td>
			<!-- 시스템 -->
			 <th class="alignL pdL10">${srInfoMap.SRArea2NM}</th>
			<td class="sline tit last">
				<select id="srArea2" Name="srArea2" style="width:90%">
         		   <option value=''>Select</option>
         		  </select>
			</td>		
			<!-- 완료예정일 -->
			<th class="alignL pdL10">${menu.LN00221}</th>
			<td class="sline tit last" >
				<c:if test="${srInfoMap.DueDate !=  null}" >
					<font><input type="text" id="dueDate" name="dueDate" value="${srInfoMap.DueDate}"	class="input_off datePicker stext" size="8"
						style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
					</font>
					<input type="text" id="dueDateTime" name="dueDateTime" value="${srInfoMap.DueDateTime}" class="input_off text" size="8" style="width:40%; text-align: center;" maxlength="10">
				</c:if>
				<c:if test="${srInfoMap.DueDate ==  null}" >
					<font><input type="text" id="dueDate" name="dueDate" value="${srInfoMap.ReqDueDate}"	class="input_off datePicker stext" size="8"
						style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
					</font>
					<input type="text" id="dueDateTime" name="dueDateTime" value="${srInfoMap.ReqDueDateTime}" class="input_off text" size="8" style="width:40%; text-align: center;" maxlength="10">
				</c:if>
			</td>	
			
		</tr>
		<tr>
			<!-- 카테고리 -->
			<th class="alignL pdL10">${menu.LN00272}</th>
			<td class="sline tit last">
				<select id="category" name="category" class="sel" OnChange="fnGetSubCategory(this.value);" style="width:90%;margin-left=5px;"></select>
			</td>
			<!-- 서브카테고리 -->
			<th class="alignL pdL10">${menu.LN00273}</th>
			<td class="sline tit last">
				<select id="subCategory" name="subCategory" class="sel" style="width:90%;margin-left=5px;"></select>
			</td>
			<!-- 우선순위  -->
			<th class="alignL pdL10">${menu.LN00067}</th>
			<td class="sline tit last">
				<select id="priority" name="priority" class="sel" style="width:90%;margin-left=5px;"></select>
			</td>
			
		</tr>			
		<tr>	
			<th class="alignL pdL10">${menu.LN00002}</th>
			<td class="sline tit last" colspan="5" >${srInfoMap.Subject}</td>					
			<!-- <th class="alignL pdL10">Classification</th>
			<td class="sline tit last" >
				<select id="classification" name="classification" class="sel" style="width:90%;margin-left=5px;"></select>
			</td> -->	
		</tr>
		<tr>	
			<th  colspan="6"  class="alignC" style="width:100%;height:10px;">${menu.LN00286}&nbsp;${menu.LN00003}</th>			
		</tr>
		<tr>
			<td colspan="6" class="sline tit last"  valign="top"  style="width:100%;height:200px;">${srInfoMap.Description}</td>
		</tr>		
	</table>
	<table class="tbl_brd mgT5" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="10%">
				<col>
				<col width="10%">
				<col>
			</colgroup>	
			<tr>
				<!-- 첨부문서 -->
				<th style="height:53px;">${menu.LN00111}</th>
				<td colspan="3" style="height:53px;" class="alignL pdL5 last">
					<div style="height:53px;width:100%;overflow:auto;overflow-x:hidden;">
					<div id="tmp_file_items" name="tmp_file_items"></div>
					<div class="floatR pdR20" id="divFileImg">
					<c:if test="${SRFiles.size() > 0}">
						<span class="btn_pack medium icon mgB2"><span class="download"></span><input value="&nbsp;Save All&nbsp;&nbsp;" type="button" onclick="FileDownload('attachFileCheck', 'Y')"></span><br>
						<span class="btn_pack medium icon"><span class="download"></span><input value="Download" type="button" onclick="FileDownload('attachFileCheck', 'N')"></span><br>
					</c:if>
					</div>
					<c:forEach var="fileList" items="${SRFiles}" varStatus="status">
						<div id="divDownFile${fileList.Seq}"  class="mm" name="divDownFile${fileList.Seq}">
							<input type="checkbox" name="attachFileCheck" value="${fileList.Seq}" class="mgL2 mgR2">
							<span style="cursor:pointer;" onclick="fileNameClick('${fileList.Seq}');">${fileList.FileRealName}</span>
							<c:if test="${sessionScope.loginInfo.sessionUserId == srInfoMap.RegUserID}">
							<img src="${root}${HTML_IMG_DIR}/btn_filedel.png" style="cursor:pointer;width:13;height:13;padding-left:10px" alt="파일삭제" align="absmiddle" onclick="fnDeleteSRFile('${fileList.SRID}','${fileList.Seq}','${fileList.FileName}','${srFilePath}');">
							</c:if>
							<br>
						</div>
					</c:forEach>
					</div>
				</td>
			</tr>		
		</table>

	<table class="tbl_brd mgT5 mgB5 " style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0">
		<colgroup>
			<col width="10%">
			<col>
		</colgroup>		
		<tr>
		  	<th class="alignC pdL10">Comment</th>		  
			<td style="height:150px;" class="tit last alignL pdL5 pdR5">
				<textarea class="tinymceText"  id="comment" name="comment" style="width:100%;height:150px;">${srInfoMap.Comment}</textarea>
			</td>
		</tr>	
		<tr id="itemListTR" style="display:none;">			
			<th class=" alignL pdL10 ">Item's</th>
			<td class="alignL pdL10 last">		
				<div id="gridCngtDiv" style="width:100%;height:160px;" class="clear">
					<div id="grdGridArea" style="width:100%;height:150px;"></div>
				</div>		
			</td>
		</tr>		
	</table>
	
	<div class="alignBTN">				
		<c:if test="${(srInfoMap.Status == 'SPE001' || srInfoMap.Status == 'SPE002')}" >
			<span class="btn_pack medium icon"><span class="upload"></span><input value="Attach" type="submit" onclick="doAttachFile()"></span>
			<span class="btn_pack medium icon"><span class="save"></span><input value="Save" type="submit" onclick="fnSaveReceiveItspInfo()"></span>
			<span class="btn_pack medium icon"><span class="gov"></span><input value="Transfer" type="submit" onclick="fnOpenTransferPop();"></span>
		</c:if>		
		<c:if test="${srInfoMap.SRNextStatus eq 'SPE003' && appBtn eq 'Y'}" >	
			<span class="btn_pack medium icon"><span class="confirm"></span><input value="Request Approval" type="submit" onclick="fnApproveItspPop()"></span>
		</c:if>
		<c:if test="${srInfoMap.SRNextStatus eq 'SPE005' && appBtn eq 'Y'}" >
			<span class="btn_pack medium icon"><span class="add"></span><input value="Create SCR" type="submit"  onclick="fnRegistSCR();" style="cursor:hand;"></span>	
		</c:if>			
		 <c:if test="${srInfoMap.Status eq 'SPE002' && srInfoMap.SRNextStatus eq srInfoMap.LastSpeCode && appBtn eq 'Y' && srInfoMap.ReceiptUserID == sessionScope.loginInfo.sessionUserId }" >
			<span class="btn_pack medium icon"><span class="confirm"></span><input value="Complete" type="submit" onclick="fnCompletion()"></span>
		</c:if>
		
		<c:if test="${isPopup != 'Y'}" >
			<span id="viewList" class="btn_pack medium icon"><span class="list"></span><input value="List" type="submit"  onclick="fnGoSRList();"></span>
		</c:if>	
	   
	</div>
	* Status history
	<table class="tbl_brd mgT5 mgB5" style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="10%">
		    <col width="20%">
		 	<col width="10%">
		    <col width="20%">
		 	<col width="30%">
		</colgroup>		
		<tr>
		  <th class="alignC pdL10">${menu.LN00132}</th> <!-- 진행단계 -->
		  <th class="alignC pdL10">${menu.LN00104}</th> <!-- 담당조직 -->
		  <th class="alignC pdL10">${menu.LN00004}</th> <!-- 담당자 -->
		  <th class="alignC pdL10">Time</th>
		  <th class="alignC pdL10">Comment</th>
		</tr>
		<c:forEach var="list" items="${prLgInfoList}" varStatus="status">
			<c:if test="${list.Link != ''}" ><tr style="height:26px; cursor:pointer;" OnClick="fnOpenDocument('${list.ActionID}','${list.Link}','${list.VarFilter}','${list.ActivityID}','${list.SEQ}');"></c:if>  
			<c:if test="${list.Link == ''}" ><tr style="height:26px;"></c:if>
				<c:choose>
				<c:when test="${status.last}">
					<td class="sline tit last alignL " style="font-weight:bold;color:blue;"> * ${list.ActivityName}</td>
					<td class="sline tit last alignC " style="font-weight:bold;color:blue;">${list.TeamName}</td>
					<td class="sline tit last alignC " style="font-weight:bold;color:blue;">${list.ActorName}</td>
					<td class="sline tit last alignC " style="font-weight:bold;color:blue;">${list.EndTime}</td>
					<td class="sline tit last alignL " style="font-weight:bold;color:blue;" id="historyCmt${status.count}"><span title="${list.Comment}">${list.CommentFiltered}</span></td>
				</c:when>
				<c:otherwise>
					<td class="sline tit last alignL " >${list.ActivityName}</td>
					<td class="sline tit last alignC " >${list.TeamName}</td>
					<td class="sline tit last alignC " >${list.ActorName}</td>
					<td class="sline tit last alignC " >${list.EndTime}</td>
					<td class="sline tit last alignL " id="historyCmt${status.count}"><span title="${list.Comment}">${list.CommentFiltered}</span></td>
				</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	</form>
</div>
<iframe id="saveFrame" name="saveFrame" style="width:0px;height:0px;display:none"></iframe>
