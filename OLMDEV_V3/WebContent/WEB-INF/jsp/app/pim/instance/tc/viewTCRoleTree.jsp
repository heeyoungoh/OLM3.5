<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<c:url value="/" var="root"/>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00168" var="WM00168" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00042" var="CM00042"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00132" var="WM00132" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00114" var="WM00114" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034" arguments="${menu.LN00004}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00168" var="WM00168" arguments="${menu.LN00148}"/>
<!-- 2. Script -->
<script type="text/javascript">

	var gridArea;				
	var skin = "dhx_skyblue";
	var objIds = new Array;
	var elmInstNos = new Array;
	var elmItemIDs = new Array;
	var procInstNos= new Array;
	
	$(document).ready(function() {	
		// 초기 표시 화면 크기 조정
// 		$("#gridArea").attr("style","height:calc(100% - 182px)");
// 		// 화면 크기 조정
// 		window.onresize = function() {
// 			$("#gridArea").attr("style","height:calc(100% - 182px)");
// 		};
		
		setElmGridList();
	});	
	
	function fnDownLoadExcel() {
		$('#fileDownLoading').removeClass('file_down_off');
		$('#fileDownLoading').addClass('file_down_on');
		
		var url = "testObjectReportExcel.do";
		ajaxSubmit(document.testObjectFrm, url);
	}
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	//===============================================================================
	// BEGIN ::: GRID
	function setElmGridList(){
		var treePData="${elmTreeXml}";
	    gridArea = new dhtmlXGridObject('gridArea');
	    gridArea.selMultiRows = true;
	    gridArea.imgURL = "${root}${HTML_IMG_DIR_ITEM}/";
	    gridArea.setImagePath("${root}${HTML_IMG_DIR_ITEM}/");
	    gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");
		gridArea.setHeader("${menu.LN00028},${menu.LN00043},Link,CxnItemID,LinkURL,LovCode,AttrTypeCode,${menu.LN00027},테스트 결과,Test Date,ElmInstNo");
		gridArea.setInitWidths("380,360,50,50,50,50,50,150,120,120,80");
		gridArea.setColAlign("left,left,center,left,left,left,left,center,center,center,center");
		gridArea.setColTypes("tree,ro,img,ro,ro,ro,ro,ro,icon,ro,ro");
		//gridArea.setColSorting("int,str,str,str,str,str,str,str,str");
   	  	gridArea.init();
		gridArea.setSkin("dhx_web");
		gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		gridArea.loadXMLString(treePData);
	
		gridArea.setColumnHidden(3, true);
		gridArea.setColumnHidden(4, true);
		gridArea.setColumnHidden(5, true); 
		gridArea.setColumnHidden(6, true);
		gridArea.setColumnHidden(10, true);
		gridArea.enableTreeCellEdit(false);
		gridArea.checkAll(false);
	}
	
	// END ::: GRID	
	//===============================================================================
	
	function gridOnRowSelect(id, ind){
		 if(ind == 2){
			var itemID = gridArea.cells(id, 3).getValue();
			var linkUrl = gridArea.cells(id, 4).getValue();
			var lovCode = gridArea.cells(id, 5).getValue();
			var attrTypeCode = gridArea.cells(id, 6).getValue();
			if(linkUrl != ""){
				fnOpenLink(itemID,linkUrl,lovCode,attrTypeCode);
			}
		}else{
			var elmInstNo=gridArea.cells(id, 10).getValue();
			if(elmInstNo==""||elmInstNo==undefined){				
			}else{fnPimElementInfo("${instanceNo}", elmInstNo);}
		}
	}
	
	function fnPimElementInfo(procInstNo,elmInstNo){
		var url = "viewTCDetail.do?";
		var data = "procInstNo="+procInstNo+"&elmInstNo="+elmInstNo+"&instanceClass=ELM"; 
	    var w = "1000";
		var h = "800";
	    window.open(url+data, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");	
	}
	
	function fnOpenLink(itemID,url,lovCode,attrTypeCode){
		var url = url+".do?itemID="+itemID+"&lovCode="+lovCode+"&attrTypeCode="+attrTypeCode;
	
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h);
	}

	function fnViewTODetail(procInstNo,elmInstNo){
		var url = "viewTODetail.do?";
		var data = "procInstNo="+procInstNo+"&elmInstNo="+elmInstNo+"&instanceClass=ELM&testCase=Y&List=Y"; 
	    var w = "1400";
		var h = "800";
	    window.open(url+data, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");	

	}
	
	function fnCopyElm(){
		var url = "selectTestObject.do?modelID=${procModelID}&instanceNo=${instanceNo}&processID=${nodeID}";
		var w = 800;
		var h = 900;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}
	
	function fnCallBackSubmit() {
		doSearchList();
	}
	function fnCallBackEnsembleUserList(errorMessage, errortype) {
		if(errortype == 0){
			alert(errorMessage + "${WM00114}");	
		}else if(errortype == 1){
			alert(errorMessage + "${WM00168}");	
		}
	}
	
	function fnDeleteAll(){
		if (confirm("${CM00042}")) {
			var url  = "deleteElmInst.do";
			var data = "&procInstNo=${instanceNo}";
			var target = "saveFrame";
			ajaxPage(url,data,target);
		}
	}
	
	function fnReload() {
		goProcInstanceInfoEdit("V");
	}
	
	function doFileDown(avg1, avg2) {
		var url = "fileDown.do";
		$('#original').val(avg1);
		$('#filename').val(avg1);
		$('#scrnType').val(avg2);
		
		ajaxSubmitNoAlert(document.testObjectFrm, url);
		$('#fileDownLoading').addClass('file_down_off');
		$('#fileDownLoading').removeClass('file_down_on');
	}
</script>
<style type="text/css">
	
	 .row20px div img{  height:18px;  }
	 .objbox{
		overflow-x:hidden!important;
		}
	div.gridbox_dhx_web.gridbox .odd_dhx_web {background:none;}
	.fa {
	    width: 9px;
	    height: 9px;
	    display: block;
	    margin: 0 auto;
	    border-radius: 2px;
	}
	.fa-gray {
		background: #bbb;
	}
	.fa-blue {
		background: #183ec5;
	}
	.fa-red {
		background: #ea0c0c;
	}
}
</style>
</head>
<body>
<div id="fileDownLoading" class="file_down_off">
	<img src="${root}${HTML_IMG_DIR}/loading_circle.gif"/>
</div>
<div style="width:100%;height:100%;">
 <div class="countList">
		<ul>
			<c:if test="${sessionScope.loginInfo.sessionUserId eq procInstanceInfo.OwnerID || sessionScope.loginInfo.sessionUserId eq prcMap.AuthorID}">
		    <li class="floatR mgR20">
				<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel" onclick="fnDownLoadExcel()"></span>
			</li>
			</c:if>
		</ul>
    </div>
	<form name="testObjectFrm" id="testObjectFrm" action="#" method="post" onsubmit="return false;" style="height:100%;">
		<input type="hidden" id="original" name="original" value="">
		<input type="hidden" id="filename" name="filename" value="">
		<input type="hidden" id="downFile" name="downFile" value="">
		<input type="hidden" id="scrnType" name="scrnType" value="">
		<input type="hidden" id="currPage" name="currPage" value="${pageNum}">
		
		<input type="hidden" id="procModelID" name="procModelID" value="${procModelID }">
		<input type="hidden" id="instanceNo" name="instanceNo" value="${instanceNo }">
		<input type="hidden" id="processID" name="processID" value="${nodeID }">
		<input type="hidden" id="instanceClass" name="instanceClass" value="${instanceClass }">
	   
		<div id="gridDiv" class="mgB10 clear" style="height:100%;">
			<div id="gridArea" style="width:100%;height:calc(100% - 50px);"></div>
		</div>
	</form>
	</div>

	<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</body>
</html>