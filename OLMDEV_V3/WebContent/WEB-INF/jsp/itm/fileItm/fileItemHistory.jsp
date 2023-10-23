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

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00049" var="WM00049"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00123" var="WM00123"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00120" var="WM00120"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00050" var="WM00050"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00021" var="WM00021" arguments="Mod "/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00021" var="WM00021D" arguments="Delete "/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00021" var="WM00021B" arguments="Block "/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>


<script type="text/javascript">

	var gridArea;				//그리드 전역변수
	var skin = "dhx_skyblue";
	var fileOption = "${fileOption}";
	var itemFileOption = "${itemFileOption}";
	var schCntnLayout;	//layout적용
	var now;
	
	var userId = "${sessionScope.loginInfo.sessionUserId}";
	
	var Authority = "${sessionScope.loginInfo.sessionAuthLev}";
	var selectedItemLockOwner = "${selectedItemLockOwner}";
	var selectedItemAuthorID = "${selectedItemAuthorID}";
	var selectedItemBlocked = "${selectedItemBlocked}";
	var selectedItemStatus = "${selectedItemStatus}";
	var varFilter = "${varFilter}";
	var itemFileOption = "${itemFileOption}";
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#gridArea").attr("style","height:"+(setWindowHeight() - 240)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#gridArea").attr("style","height:"+(setWindowHeight() - 240)+"px;");
		};		
		fnSelect('languageID', '', 'langType', '${sessionScope.loginInfo.sessionCurrLangType}', 'Select');
		gridInit();		
		doSearchList();
		$("#excel").click(function(){
			doExcel();
		});
	});
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	function doSearchList(){
		var d = setGridData();
		//fnLoadDhtmlxGridJson(gridArea, d.key, d.cols, d.data, false,"", $("#totalPage").val(),"","doReturnGridSet();");
		fnLoadDhtmlxGridJson(gridArea, d.key, d.cols, d.data);
	}
	
	function gridInit(){	
		var d = setGridData();
		var varFilter = "${varFilter}";
		gridArea = fnNewInitGrid("gridArea", d);
		gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		gridArea.setIconPath("${root}${HTML_IMG_DIR}/btn_file_down.png");

		fnSetColType(gridArea, 1, "ch");
		fnSetColType(gridArea, 4, "img");
		//fnSetColType(gridArea, 18, "img");
		gridArea.setColumnHidden(6, true);
		gridArea.setColumnHidden(12, true);
		gridArea.setColumnHidden(13, true);
		gridArea.setColumnHidden(14, true);
		gridArea.setColumnHidden(15, true);
		gridArea.setColumnHidden(16, true);
		gridArea.setColumnHidden(17, true);
		gridArea.setColumnHidden(18, true);
		gridArea.setColumnHidden(19, true);
		gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind,varFilter);});						
		gridArea.enablePaging(true,100,20,"pagingArea",true,"recInfoArea");
		gridArea.setPagingSkin("bricks");
		gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridData(){
		var result 	= new Object();
		result.title 	= "${title}";
		result.key 	= "fileMgt_SQL.getFile";
		result.header = "No,#master_checkbox,${menu.LN00091},Version,File,${menu.LN00101},${menu.LN00102},${menu.LN00147},${menu.LN00070},${menu.LN00060},${menu.LN00018},${menu.LN00030},Seq,SysFileWidthPath,FileRealName,FltpCode,FilePath,DocumentID,ExtFileURL,Blocked";
		result.cols 	= "CHK|FltpName|CSVersion|down|FileRealName|Path|LanguageCode|LastUpdated|WriteUserNM|TeamName|DownCNT|Seq|SysFile|FileName|FltpCode|FilePath|DocumentID|ExtFileURL|Blocked";
		result.widths = "30,30,120,50,30,200,150,60,80,80,130,60,50,60,80,80,80,80,60,60";
		result.sorting = "int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,left,center,left,left,center,center,center,center,center,left,center,left,center,center,center,center,center,center";
		result.data = "&DocumentID=${DocumentID}"
					+ "&s_itemID=${DocumentID}&rltdItemId=${rltdItemId}"
					+ "&isPublic=N"
					+ "&DocCategory=ITM"
					+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
				
    	return result;
	}
	
	// 상세 화면 
	function gridOnRowSelect(id, ind,varFilter){
		if(fileOption=="ExtLink"){
			var extFileURL = gridArea.cells(id,18).getValue();
			var url  = extFileURL;						
			var w = 1200;
			var h = 900;
			url = url.replace(/&amp;/gi,"&");
			itmInfoPopup(url,w,h); 
			
			//여기다 AjaxPage개발
			fnExtFileUpdateCount(gridArea.cells(id, 12).getValue());

		}
		
		else if("${sessionScope.loginInfo.sessionMlvl}" != "SYS" && "${myItem}" != 'Y' && itemFileOption == "VIEWER"){
			
			var extFileURL = gridArea.cells(id,18).getValue();
			var sysFileName = gridArea.cells(id, 14).getValue();
			var seq = gridArea.cells(id, 12).getValue();
			var url = "openViewerPop.do?seq="+seq;
			var w = 1200;
			var h = 900;
			if(extFileURL != "") { 
				w = screen.availWidth-38;
				h = screen.avilHeight;
				url = url + "&isNew=N";	
			}
			else {
				url = url + "&isNew=Y";
			}
			
			itmInfoPopup(url,w,h); 
			
		}else{
				if(ind==4){ // 다운로드 이미지 클릭시 
					var originalFileName = gridArea.cells(id, 5).getValue();
					var sysFileName = gridArea.cells(id, 13).getValue();
					var seq = gridArea.cells(id, 12).getValue();
					var url  = "fileDownload.do?seq="+seq;
					var frmType = "${frmType}";
					
					ajaxSubmitNoAdd(document.fileFrm, url,"fileFrame");
				}else{
					var isNew = "N";
					var seq = gridArea.cells(id, 12).getValue();
					var fileName =  gridArea.cells(id, 5).getValue();
					var fltpCode =  gridArea.cells(id, 15).getValue();
					var creationTime  =  gridArea.cells(id, 7).getValue();
					var writeUserNM = gridArea.cells(id, 9).getValue();
					var sysFile = gridArea.cells(id,13).getValue(); //sysfile 
					var fltpName = gridArea.cells(id,2).getValue();
					var DocumentID = gridArea.cells(id,17).getValue();
						
					var url  = "fileDetail.do?&isNew="+isNew
							+"&seq="+seq
							+"&DocumentID="+DocumentID
							+"&pageNum=" + $("#currPage").val()
							+"&itemClassCode=${itemClassCode}"
							+"&selectedItemBlocked="+selectedItemBlocked
							+"&selectedItemAuthorID="+selectedItemAuthorID
							+"&selectedItemLockOwner="+selectedItemLockOwner;
					var w = 1200;
					var h = 500;
					itmInfoPopup(url,w,h); 	
			}
		}
	}	
	function fnFileDownload(){
		var cnt  = gridArea.getRowsNum();
		var originalFileName = new Array();
		var sysFileName = new Array();
		var seq = new Array();
		var chkVal;
		var j =0;	
		
		if (gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00049}");
			return;
		}
		
		for(var i=0; i<cnt; i++){
			chkVal = gridArea.cells2(i,1).getValue();
			if(chkVal == 1){				
				sysFileName[j] =  gridArea.cells2(i,13).getValue(); //sysfile
				originalFileName[j] =  gridArea.cells2(i,5).getValue(); // orignalfile
				seq[j] = gridArea.cells2(i,12).getValue(); 
				j++;
			}
		}		
		var frmType = "${frmType}";
		var url  = "fileDownload.do?seq="+seq;
		if(frmType =="documentDetailFrm"){ 
			ajaxSubmitNoAdd(document.documentDetailFrm, url,"saveFrame");
		}else{
			ajaxSubmitNoAdd(document.fileFrm, url,"fileFrame");
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
		ajaxSubmit(document.fileFrm, url);
	}	
	
	function fnMultiUpload(){ 
			var browserType="";
			var IS_IE11=!!navigator.userAgent.match(/Trident\/7\./);
			if(IS_IE11){browserType="IE11";}
			var url="addFilePop.do";
			var data="scrnType=ITM_M&delTmpFlg=Y&docCategory=ITM&browserType="+browserType+"&mgtId="+""+"&id="+$('#DocumentID').val();
			if(browserType=="IE"){fnOpenLayerPopup(url,data,"",400,400);}
			else{openPopup(url+"?"+data,490,410, "Attach File");}
	}
	
	function fnMultiUploadV4(){ 
		var url="addFilePopV4.do";
		var data="scrnType=ITM_M&docCategory=ITM&id="+$('#DocumentID').val();
		openPopup(url+"?"+data,480,450, "Attach File");
	}	

	function goNewItemInfo() {
		var url = "NewItemInfoMain.do";
		var target = "actFrame";
		var data = "s_itemID=${DocumentID}&languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}"; 
		
		ajaxPage(url, data, target);
	}	
	function fnReload(){
		document.location.reload();
	}
	function fnDeleteFile(){
		if(selectedItemBlocked == "0"){
			var cnt  = gridArea.getRowsNum();
			var sysFileName = new Array();
			var filePath = new Array();
			var seq = new Array();
			var chkVal;
			var chkBlock;
			var itemID = "${DocumentID}";
			var documentID;
			var j =0;	
			
			if (gridArea.getCheckedRows(1).length == 0) {
				alert("${WM00021D}");
				return;
			}
			
			for(var i=0; i<cnt; i++){
				chkVal = gridArea.cells2(i,1).getValue();
				chkBlock = gridArea.cells2(i,19).getValue();
				documentID = gridArea.cells2(i,17).getValue();
				
				if(chkVal == 1 && chkBlock != "Y" && documentID == itemID){				
					sysFileName[j] =  gridArea.cells2(i,14).getValue(); //sysfile
					filePath[j] = gridArea.cells2(i,16).getValue(); // 파일경로
					seq[j] = gridArea.cells2(i,12).getValue(); 
					j++;
				}
				else if(chkVal == 1 && documentID != itemID) {
					var fileName = gridArea.cells2(i,5).getValue();
					
					var msg = "<spring:message code='${sessionScope.loginInfo.sessionCurrLangCode}.WM00112' var='WM00112' arguments='"+fileName+"'/>";
					alert("${WM00112}"); return;
				}else if(chkVal == 1) {
					var fileName = gridArea.cells2(i,5).getValue();
					
					var msg = "<spring:message code='${sessionScope.loginInfo.sessionCurrLangCode}.WM00125' var='WM00125' arguments='${menu.LN00134},Blocked'/>";
					alert("${WM00125}"); return;
				}
			}	
	
			if(confirm("${CM00002}")){
				var url = "deleteFileFromLst.do";
				var data = "&itemId=${itemId}&sysFile="+sysFileName+"&filePath="+encodeURIComponent(filePath)+"&seq="+seq;
				var target = "fileFrame";
				ajaxPage(url, data, target);	
			}
		}else{
			if(selectedItemStatus == "REL"){
				 alert("${WM00120}"); // [변경 요청 안된 상태]
		     } else {
		         alert("${WM00050}"); // [승인요청중]
		     }
		}
		
	}

	function doExcel() {
		gridArea.toExcel("${root}excelGenerate");
	}
	
	function fnDeleteFileHtml(seq){	
		var divId = "divDownFile"+seq;
		$('#'+divId).hide();
	}
	
	function fnExtFileUpdateCount(seq){
		var url = "extFileUpdateCount.do";
		var data = "&fileSeq="+seq+"&scrnType=ITEM";
		var target = "fileFrame";
		ajaxPage(url, data, target);	
	}
	
	function fnAddExtFile() {
		var browserType="";
		//if($.browser.msie){browserType="IE";}
		var IS_IE11=!!navigator.userAgent.match(/Trident\/7\./);
		if(IS_IE11){browserType="IE11";}
		var url="modExtFilePop.do";
		var data="isNew=Y&browserType="+browserType+"&itemClassCode=${itemClassCode}&DocumentID=${DocumentID}";
		if(browserType=="IE"){fnOpenLayerPopup(url,data,"",1000,400);}
		else{openPopup(url+"?"+data,1000,410, "Modify File");}
	}
	
	function fdModExtFile() {
		if(selectedItemBlocked == "0"){
			var cnt = gridArea.getRowsNum();
			var sCnt = 0;
			var seq = new Array();
			var j = 0;
			
			for(var i=0; i<cnt; i++){
				chkVal = gridArea.cells2(i,1).getValue();
				if(chkVal == 1){				
					sCnt++;
					seq[j] = gridArea.cells2(i,12).getValue(); 
					j++;
				}
			}
			
			if(sCnt < 1) {
				alert("${WM00023}");
				return;
			}
			else if(sCnt > 10) {
				alert("${WM00021}");
				return;
			}
		
			var browserType="";
			//if($.browser.msie){browserType="IE";}
			var IS_IE11=!!navigator.userAgent.match(/Trident\/7\./);
			if(IS_IE11){browserType="IE11";}
			var url="modExtFilePop.do";
			var data="seqList="+seq+"&browserType="+browserType;
			if(browserType=="IE"){fnOpenLayerPopup(url,data,"",1000,400);}
			else{openPopup(url+"?"+data,1000,410, "Modify File");}
		
		}
	}
	
	function fnChangeRegMember(){
		var cnt  = gridArea.getRowsNum();
		var fileSeqs = new Array();
		var j =0;
		
		
		if (gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00023}");
			return;
		}
				
		for(var i=0; i<cnt; i++){
			chkVal = gridArea.cells2(i,1).getValue();
			if(chkVal == 1){
				fileSeqs[j] = gridArea.cells2(i,12).getValue(); 
				j++;
			}
		}
		$("#fileSeqs").val(fileSeqs);
		if(fileSeqs != ""){
			var url = "searchPluralNamePop.do?objId=resultID&objName=resultName&UserLevel=ALL"
						+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
			window.open(url,'window','width=340, height=300, left=300, top=300,scrollbar=yes,resizble=0');
		}
	}
	
	function setSearchNameWf(avg1, avg2, avg3, avg4){
		if(confirm("${CM00001}")){
			var url = "updateFileRegMember.do";
			var target = "fileFrame";
			var data = "fileSeqs="+$("#fileSeqs").val()+"&memberID="+avg1; 
		 	ajaxPage(url, data, target);
		}
	}
	

	function fnBlock(){
		var cnt  = gridArea.getRowsNum();
		var seq = "";
		
		if (gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00021B}");
			return;
		}
		
		for(var i=0; i<cnt; i++){
			chkVal = gridArea.cells2(i,1).getValue();
			
			if(chkVal == 1){		
				if(seq == "")
					seq = gridArea.cells2(i,12).getValue(); 
				else 
					seq = seq + "," + gridArea.cells2(i,12).getValue(); 
			}
		}		
		
		if(confirm("${CM00001}")){	
			var url  = "updateFileBlocked.do";
			var data = "&seq="+seq;
			ajaxPage(url,data,"fileFrame");
		}
	}
	
	function editFileName(){
		
	    var url = "editFileNamePop.do"; 
	    var option = "width=550, height=570, left=100, top=100,scrollbar=yes,resizble=0";
	    window.open("editFileNamePop.do?DocumentID="+$("#DocumentID").val(), "SelectOwner", option);	 
   }
	
	function fnExtFileDownload(){
		var cnt  = gridArea.getRowsNum();
		var originalFileName = new Array();
		var sysFileName = new Array();
		var seq = new Array();
		var chkVal;
		var j =0;	
		
		if (gridArea.getCheckedRows(1).length == 0) {
			alert("${WM00049}");
			return;
		}
		
		for(var i=0; i<cnt; i++){
			chkVal = gridArea.cells2(i,1).getValue();
			if(chkVal == 1){				
				sysFileName[j] =  gridArea.cells2(i,13).getValue(); //sysfile
				originalFileName[j] =  gridArea.cells2(i,5).getValue(); // orignalfile
				seq[j] = gridArea.cells2(i,12).getValue(); 
				j++;
			}
		}		
		var frmType = "${frmType}";
		var url  = "downloadExtLinkFile.do?seq="+seq;
		if(frmType =="documentDetailFrm"){ 
			ajaxSubmitNoAdd(document.documentDetailFrm, url,"saveFrame");
		}else{
			ajaxSubmitNoAdd(document.fileFrm, url,"fileFrame");
		}
	}	
	
</script>
</head>
<body>
<form name="fileFrm" id="fileFrm" action="" method="post" onsubmit="return false">
	<input type="hidden" id="itemId" name="itemId" value="${itemId}">
	<input type="hidden" id="itemAthId" name="itemAthId" value="${selectedItemAuthorID}">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}"></input> 
	<input type="hidden" id="Blocked" name="Blocked" value="${selectedItemBlocked}" />
	<input type="hidden" id="LockOwner" name="LockOwner" value="${selectedItemLockOwner}" />
	<input type="hidden" id="DocumentID" name="DocumentID" value="${DocumentID}" />
	<input type="hidden" id="fileSeqs" name="fileSeqs" >
	<div class="countList">
        <li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR">&nbsp;</li>
     </div>
	<div id="gridDiv" class="mgB10 clear">
		<div id="gridArea" style="width: 100%"></div>
	</div>	
	<!-- START :: PAGING -->
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
	<!-- END :: PAGING -->		
</form>
<iframe name="fileFrame" id="fileFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</body></html>