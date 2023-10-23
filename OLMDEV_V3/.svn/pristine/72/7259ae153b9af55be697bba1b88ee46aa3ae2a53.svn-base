<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00049" var="WM00049"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00095" var="WM00095"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00033" var="WM00033"/>

<script type="text/javascript">

	var p_gridArea;				//그리드 전역변수
	var skin = "dhx_skyblue";
	var schCntnLayout;	//layout적용
	var now;
	var listScale = "<%=GlobalVal.LIST_SCALE%>";
	var screenType = "${screenType}";
	var isPublic = "${isPublic}";
	var docType = "${docType}";
	var fltpCode = "";
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 290)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 290)+"px;");
		};
		$("input.datePicker").each(generateDatePicker);
		$('.layout').click(function(){
			var changeLayout = $(this).attr('alt');
			setLayout(changeLayout);
		});

		fnSelect('fltp','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&docCategory=${DocCategory}','getFltpByDocCategory','${fltpCode}','Select', 'fileMgt_SQL');
		
		if(screenType != "csrDtl") fnSelect('languageID', "", 'langType', '${sessionScope.loginInfo.sessionCurrLangType}', 'Select');
		
		if("${screenType}" == "main"){
			$("#searchKey").val("${searchKey}").attr("selected", "selected");
			$("#searchValue").val("${searchValue}");
			fltpCode = "${fltpCode}";
			$("#regMemberName").val("${regMemberName}");
		}
		if(screenType == "csrDtl"){ // mainHomeLayerV3			
			$("#parent").attr('disabled', 'disabled: disabled');
			fnGetCsrCombo("${parentID}");
		}else{
			fnSelect('parent','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&isDoc=Y','getProject','${parentID}');
		}
	
		if(screenType == "csrDtl"){
			fnSelect('project','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&projectID=${projectID}','getCsrOrder','${projectID}');		
		}else{
			fnSelect('project','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&parentID=${parentID}','getCsrOrder','${projectID}','Select');
		}
		
		$("#excel").click(function(){
			doExcel();
		});
		
		$("#fltp").change(function() {
			fltpCode = $(this).val();
		});
		
		$('#searchClear').click(function(){
			clearSearchCon();
			return false;
		});
		
		setTimeout(function() {gridInit();doSearchList();}, 700);
		
	});
	
	function setWindowHeight(){
		var size = window.innerHeight;
		var height = 0;
		if( size == null || size == undefined){
			height = document.body.clientHeight;
		}else{
			height=window.innerHeight;
		}return height;
	}
		
	function doSearchList(){ 
		if($("#REG_STR_DT").val() != "" && $("#REG_END_DT").val() == "")	$("#REG_END_DT").val(new Date().toISOString().substring(0,10));
		
		var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	function gridInit(){	
		var d = setGridData(); 
		p_gridArea = fnNewInitGrid("grdGridArea", d); 
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR}/btn_file_down.png");
		
		fnSetColType(p_gridArea, 1, "ch");
		fnSetColType(p_gridArea, 3, "img");
		p_gridArea.setColumnHidden(11, true);
		p_gridArea.setColumnHidden(12, true);
		p_gridArea.setColumnHidden(13, true);
		p_gridArea.setColumnHidden(14, true);
		p_gridArea.setColumnHidden(15, true);
		p_gridArea.setColumnHidden(16, true);
		p_gridArea.setColumnHidden(17, true);
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		
		p_gridArea.enablePaging(true,listScale,10,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridData(){ 
		var result 	= new Object();	
		var DocCategory = "${DocCategory}";
		result.title 	= "${title}";	
		if(docType=="myDoc"){ result.key 	= "fileMgt_SQL.getMyFile";}else{result.key 	= "fileMgt_SQL.getFile";}
		
		result.header = "No,#master_checkbox,${menu.LN00091},File,${menu.LN00101},${menu.LN00102},${menu.LN00147},${menu.LN00070},${menu.LN00060},${menu.LN00104},${menu.LN00030},Seq,SysFileWidthPath,FileRealName,FltpCode,FilePath,DocumentID,ClassCode"; //18
		result.cols 	= "CHK|FltpName|down|FileRealName|Path|LanguageCode|LastUpdated|WriteUserNM|TeamName|DownCNT|Seq|SysFile|FileName|FltpCode|FilePath|DocumentID|ClassCode"; //17
		if(screenType != "csrDtl"){
			result.widths = "30,30,140,40,450,*,60,80,80,100,60,50,60,80,80,80,80,80"; // 18
		} else {
			result.widths = "30,30,140,40,150,*,60,80,80,100,60,50,60,80,80,80,80,80"; // 18
		}
		result.sorting = "int,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"; // 18
		result.aligns = "center,center,center,center,left,left,center,center,center,center,center,left,center,left,center,center,center,center"; // 18
		result.data = "&pageNum=" + $("#currPage").val()
					+ "&authLev=${sessionScope.loginInfo.sessionAuthLev}"
					//+ "&parentID="+ $("#parent").val()				
					+ "&screenType="+screenType			
					+ "&isPublic="+isPublic
					+ "&DocCategory=${DocCategory}";
					console.log($("#REG_END_DT").val())
					if(screenType == "csrDtl"){
						result.data = result.data + "&projectID=${projectID}";
						result.data = result.data + "&refPjtID=${refPjtID}";
					}else{
						result.data = result.data + "&projectID="+ $("#project").val();
						result.data = result.data + "&refPjtID="+ $("#parent").val();
						result.data = result.data + "&updatedStartDT="+ $("#REG_STR_DT").val()
						result.data = result.data + "&updatedEndDT="+ $("#REG_END_DT").val()
					}
					
					if(screenType != "csrDtl"){
						result.data = result.data + "&searchKey="+ $("#searchKey").val();
						result.data = result.data + "&searchValue=" + $("#searchValue").val();
						result.data = result.data + "&regMemberName="+$("#regMemberName").val();
						result.data = result.data + "&fltpCode="+ fltpCode;
					}
					
					if(screenType != "csrDtl" && $("#languageID").val() != "") {
						result.data += "&languageID=" + $("#languageID").val();
					}
					else {
						result.data += "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
					}
							
		return result;
	}
	
	function fnCheckedRow(id){
		p_gridArea.setCheckedRows(id,0);
	}
	
	// 상세 화면 
	function gridOnRowSelect(id, ind){		
		var documentID = p_gridArea.cells(id, 16).getValue();
		if(ind==3){ // 다운로드 이미지 클릭시 
			var seq = p_gridArea.cells(id, 11).getValue();
			var url  = "fileDownload.do?seq="+seq;
			if( '${loginInfo.sessionMlvl}' != "SYS"){
				fnCheckUserAccRight(documentID, "fnFileDownloadRow("+seq+")", "${WM00033}");
			}else{
				fnFileDownloadRow(seq);
			}
		}else{
			var seq = p_gridArea.cells(id, 11).getValue();
			var documentID = p_gridArea.cells(id,16).getValue();
			
			if( '${loginInfo.sessionMlvl}' != "SYS"){
				fnCheckUserAccRight(documentID, "fnDocumentDetail("+seq+","+documentID+")", "${WM00033}");
			}else{
				fnDocumentDetail(seq, documentID );
			}
		}
	}
	
	function fnDocumentDetail(seq, documentID ){
		var isNew = "N";					
		var url  = "documentDetail.do?isNew"+isNew
				+"&seq="+seq+"&DocumentID="+documentID
				+"&pageNum="+$("#currPage").val()
				+"&itemTypeCode=${itemTypeCode}&fltpCode=${fltpCode}"
				+"&docType="+docType
				+"&screenType="+screenType
				+"&isMember=${isMember}";
		var w = 1200;
		var h = 500;
		itmInfoPopup(url,w,h); 
	}
	
	function fnFileDownloadRow(seq){
		var url = "fileDownload.do?seq="+seq;
		ajaxSubmitNoAdd(document.fileMgtFrm, url,"subFrame");
	}
	
	function fnFileDownload(){
		var cnt  = p_gridArea.getRowsNum();
		var originalFileName = new Array();
		var seq = new Array();
		var chkVal;
		var j =0;
		var itemIDs = new Array();
		
		if (p_gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00049}");
			return;
		}
				
		for(var i=0; i<cnt; i++){
			chkVal = p_gridArea.cells2(i,1).getValue();
			if(chkVal == 1){
				originalFileName[j] =  p_gridArea.cells2(i,4).getValue(); // orignalfile
				seq[j] = p_gridArea.cells2(i,11).getValue(); 
				itemIDs[j] = p_gridArea.cells2(i,16).getValue(); 
				j++;
			}
		}
		if( '${loginInfo.sessionMlvl}' != "SYS"){
			fnCheckItemArrayAccRight(seq, itemIDs); // 접근권한 체크후 DownLoad
		}else{
			var url  = "fileDownload.do?seq="+seq;
			var frmType = "${frmType}";
			ajaxSubmitNoAdd(document.fileMgtFrm, url,"subFrame");
		}
	}
	
	function fnCheckItemArrayAccRight(seq, itemIDs){
		$.ajax({
			url: "checkItemArrayAccRight.do",
			type: 'post',
			data: "&itemIDs="+itemIDs+"&seq="+seq,
			error: function(xhr, status, error) { 
			},
			success: function(data){				
				data = data.replace("<script>","").replace(";<\/script>","");		
				fnCheckAccCtrlFileDownload(data, seq);
			}
		});	
	}
	
	function fnCheckAccCtrlFileDownload(data, seq){
		var dataArray = data.split(",");
		var accRight = dataArray[0];
		var fileName = dataArray[1];
		
		if(accRight == "Y"){
			var url  = "fileDownload.do?seq="+seq;
			var frmType = "${frmType}";
			ajaxSubmitNoAdd(document.fileMgtFrm, url,"subFrame");
		}else{			
			alert(fileName + "은 ${WM00033}"); return;
		}
	}
	
	function fnFileUpload(){
		
	    var fileCount = document.getElementById("files_upload").files.length;

	    var fileSize = new Array();
	    var fileName = new Array();
	
	 	for(var i=0; i<fileCount; i++){
	 		fileSize[i] = document.getElementById("files_upload").files[i].size;
	 		fileName[i] = document.getElementById("files_upload").files[i].name;
	 	}
		  
		var files =  $("#files-upload").val();
		var url  = "fileUpload.do?files="+files+"&fileSize="+fileSize+"&fileName="+fileName;
		ajaxSubmit(document.fileMgtFrm, url);
	}
	
	function fnMultiUpload(){ 
		var browserType="";
		var agent = navigator.userAgent.toLowerCase();
		if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
			//browserType="IE";
		} 
		var url="addFilePop.do";
		var data="scrnType=ITM_M&delTmpFlg=Y&browserType="+browserType+"&mgtId="+""+"&id=${projectID}&projectID=${projectID}&docCategory=${DocCategory}&fltpCode=CSRDF&screenType="+screenType;
		
		if(browserType=="IE"){fnOpenLayerPopup(url,data,"",400,400);}
		else{openPopup(url+"?"+data,490,360, "Attach File");}
	}
	
	function fnMultiUploadV4(){ 
		var url="addFilePopV4.do";
		var data="scrnType=ITM_M&delTmpFlg=Y&mgtId="+""+"&id=${projectID}&projectID=${projectID}&docCategory=${DocCategory}&fltpCode=CSRDF&screenType="+screenType;
		openPopup(url+"?"+data,480,450, "Attach File");
	}	
	
	function goNewItemInfo() {
		var url = "NewItemInfoMain.do";
		var target = "actFrame";
		var data = "s_itemID=${DocumentID}&languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}"; 
	 	ajaxPage(url, data, target);
	}
	
	function fnGetCsrCombo(parentID){
		fnSelect('project','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&parentID='+parentID,'getCsrOrder','${projectID}');
	}
	
	function doExcel() {
		p_gridArea.toExcel("${root}excelGenerate");
	}	
	
	// file upload callback
	function fnCallBack(){ 
		doSearchList();
	}
	
	function fnDeleteFile(){
		var cnt  = p_gridArea.getRowsNum();
		var sysFileName = new Array();
		var filePath = new Array();
		var seq = new Array();
		var chkVal;
		var j =0;	
		
		if (p_gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00095}");
			return;
		}
		for(var i=0; i<cnt; i++){
			chkVal = p_gridArea.cells2(i,1).getValue();
			if(chkVal == 1){				
				sysFileName[j] =  p_gridArea.cells2(i,12).getValue(); //sysfile
				filePath[j] = p_gridArea.cells2(i,15).getValue(); // 파일경로
				seq[j] = p_gridArea.cells2(i,11).getValue(); 
				j++;
			}
		}	

		if(confirm("${CM00002}")){
			var url = "deleteFileFromLst.do";
			var data = "&sysFile=&filePath=&seq="+seq;
			var target = "subFrame";
			ajaxPage(url, data, target);	
		}
	}
	
	function fnDeleteFileHtml(seq){	
		var divId = "divDownFile"+seq;
		$('#'+divId).hide();
	}
	
	function clearSearchCon() {
		$("#DocCategory").val("");
		$("#fltp").val("");
		$("#searchValue").val("");
		$("#REG_STR_DT").val("");
		$("#REG_END_DT").val("");
		$("#regMemberName").val("");
		$("#parent").val("");
		$("#project").val("");
		$("#searchValue").val("");
		$("#searchKey").val("Name");
	}

</script>
</head>
<body>
<div class="pdL10 pdR10" style="background:#fff;height:100%;">
<form name="fileMgtFrm" id="fileMgtFrm" action="#" method="post" onsubmit="return false;" >
	<input type="hidden" id="itemId" name="itemId" value="${itemId}">
	<input type="hidden" id="itemAthId" name="itemAthId" value="${itemAthId}">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}"></input> 
	
<c:if test="${screenType != 'csrDtl'}" >
<div class="cop_hdtitle">
		<h3 style="padding: 6px 0"><img src="${root}${HTML_IMG_DIR}/icon_folder_upload_title.png">&nbsp;&nbsp;${menu.LN00019}</h3>
</div>
<div style="width:100%">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_search mgT5" id="search">
	<colgroup>
	    <col width="9%">
		<col width="22%">
		<col width="9%">
		<col width="22%">
		<col width="9%">
		<col width="5%">
		<col width="9%">
		<col width="29%">
    </colgroup>
   
    <tr>
   		<!-- 문서유형 -->
       	<th class="alignL pdL10 ">${menu.LN00091}</th>
        <td class="alignL pdL10"> 
	       	<select id="fltp" Name="fltp" class="sel" style="width:98%;"></select>
       	</td>       	
        <!-- 수정일-->
        <th class="alignL pdL10">${menu.LN00070}</th>     
        <td class="alignL pdL10">     
            <font><input type="text" id="REG_STR_DT" name="REG_STR_DT" value="${beforeYmd}"	class="input_off datePicker stext" size="8"
				style="width: 39.25%;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" >
			</font>
			~
			<font><input type="text" id="REG_END_DT" name="REG_END_DT" value="${thisYmd}"	class="input_off datePicker stext" size="8"
				style="width: 39.25%;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
			</font>
         </td> 
         <!-- 작성자 -->
        <th class="alignL pdL10">${menu.LN00060}</th>
        <td class=" alignL pdL10" colspan=3><input type="text" id="regMemberName" name="regMemberName" class="stext"></td>   
        
	</tr>
    <tr>
     
		<!-- 프로젝트 -->
       	<th class="alignL pdL10">${menu.LN00131}</th>
        <td class="alignL pdL10"> 
        	<c:if test="${screenType != 'mainV3' && screenType != 'csrDtl'}" >
        		<select id="parent" Name="parent" onChange="fnGetCsrCombo(this.value)" class="sel" style="width:98%;"></select>
        	</c:if>
        	<c:if test="${screenType == 'pjpInfoMgt'}" >
        		 <select id="parent" Name="parent" class="sel" style="width:98%;">
	       		<option value="${projectMap.ProjectID}" selected="selected">${projectMap.ProjectName}</option>
	       	</select>
        	</c:if>
       	</td>
       	<!-- 변경오더 -->
       	<th class="alignL pdL10">${menu.LN00191}</th>
        <td class="alignL pdL10">
        	<select id="project" Name="project" class="sel" style="width:98%;">
        		<option value="">Select</option>
        	</select>
        </td>
        
        <th class="alignL pdL10">${menu.LN00147}</th>
        <td class="alignL pdL10">     
	       	<select id="languageID" Name="languageID" >
	       		<option value=''>Select</option>
	       	</select>
       	</td>
       	
        <th class="alignL pdL10">
        	<select id="searchKey" name="searchKey" class="sel">
				<option value="Name">${menu.LN00002}</option>
				<option value="Info" 
					<c:if test="${searchKey == 'Info'}"> selected="selected"</c:if>>
					${menu.LN00003}
				</option>					
			</select>
        </th>        
		<td class="pdL10 alignL ">
	   		<input type="text" id="searchValue" name="searchValue" value="${searchValue}" class="stext" style="width:96%">
	   	 </td>
	   	 
      </tr>
  	</table>  	 
  	<div class="mgT5" align="center">
   	    <input type="image" class="image searchList" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="doSearchList()" value="Search" />&nbsp;&nbsp;
   	    <input type="image" id="searchClear" class="image" src="${root}${HTML_IMG_DIR}/btn_search_clean.png" value="Clear" style="display:inline-block;">
  	</div>
  </div>
  </c:if>
  <div class="countList">
    	<li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR mgT2">
        	<c:if test="${screenType == 'csrDtl' && csrEditable == 'Y' }">
		   	  	<!-- <span class="btn_pack medium icon"><span class="upload"></span><input value="Upload" type="button" onclick="fnMultiUpload()"></span> -->
		   	  	<span class="btn_pack medium icon"><span class="upload"></span><input value="Upload" type="button" onclick="fnMultiUploadV4()"></span>
				<span class="btn_pack medium icon"><span class=del></span><input value="Del" type="submit" onclick="fnDeleteFile()"></span>	   	  
		   	 </c:if>	
		   		   	
        	<span class="btn_pack medium icon"><span class="download"></span>
			<input value="Download" type="button" onclick="fnFileDownload()"></span>
			<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span></li>
    </div>
	<div id="gridDiv" class="mgB10 clear">
		<div id="grdGridArea" style="width: 100%"></div>
	</div>	
	<!-- START :: PAGING -->
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
	<!-- END :: PAGING -->		
</form>
<iframe name="subFrame" id="subFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</div>
</body>
</html>