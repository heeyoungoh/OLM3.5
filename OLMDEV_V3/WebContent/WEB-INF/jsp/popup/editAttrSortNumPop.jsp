<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root" />

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00067" var="WM00067"/>

<title>Edit SortNum</title>

<!-- 2. Script -->
<script type="text/javascript">
	var gridArea;
	var dp;
	$(document).ready(function(){
		
		gridInit();		
		doSearchList();
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	// [save] 이벤트 후 처리
	function selfClose() {
		//var opener = window.dialogArguments;
		opener.objReload();
		self.close();
	}
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	function thisReload() {
		gridInit();		
		doSearchList();
	}
	
	function doSearchList(){
		var tcd = setGridData();fnLoadDhtmlxGridJson(gridArea, tcd.key, tcd.cols, tcd.data,false,false,"","");
	}
	
	function gridInit(){	
		var tcd = setGridData();
		gridArea = fnNewInitGrid("grdGridArea", tcd);
		gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		gridArea.setIconPath("${root}${HTML_IMG_DIR}/");
		gridArea.setColumnHidden(0, true);		
		
		dp = new dataProcessor("saveAttrSortNum.do?itemTypeCode=${itemTypeCode}"); 
		dp.enableDebug(true);
		dp.setTransactionMode("POST",true); 
		dp.setUpdateMode("off"); 
		
		dp.attachEvent("onAfterUpdateFinish", function(){
			fnAfterUpdateSendData();
		});
		
		dp.init(gridArea); 
		dp.styles={
			updated:"font-style:italic; color:black;",
			inserted:"font-weight:bold; color:green;",
			deleted:"font-weight:bold; color:red;",
			invalid:"color:orange; text-decoration:underline;",
			error:"color:red; text-decoration:underline;",
			clear:"font-weight:normal;text-decoration:none;"
		}; 
	}
	
	function fnAfterUpdateSendData(){
		alert("${WM00067}");
		thisReload();
	}
	
	function setGridData(){
		var result = new Object();
		result.title = "${title}";
		result.key = "config_SQL.getAttrListWithItemTypeCode";
		result.header = "${menu.LN00024},AttrTypeCode,Name,SortNum";
		result.cols = "AttrTypeCode|AttrTypeName|GSortNum";
		result.widths = "30,125,300,100";
		result.sorting = "int,str,str,int";
		result.aligns = "center,center,left,center";
		result.data = "ItemTypeCode=${itemTypeCode}&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		return result;
	}
	
	function fnMoveRowUp(){
		gridArea.moveRowUp(gridArea.getSelectedRowId());
	}
	
	function fnMoveRowDown(){
		gridArea.moveRowDown(gridArea.getSelectedRowId());
	}
	
	function fnSaveAttrTypeCodeSortNum(){			
		if(confirm("${CM00001}")){
			var ids = gridArea.getAllRowIds().split(",");
			
			for(var i=0; i < ids.length; i++){
				gridArea.cells(ids[i], 3).setValue(i);
				dp.setUpdated(ids[i], true, "updated");
			}
			dp.sendData(); 
		}
	}

</script>

<div style="width:98%;height:100%;">
<form name="attrSortNumFrm" id="attrSortNumFrm" action="#" method="post" onsubmit="return false;">
	<div class="msg" style="width:100%;"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;Edit SortNum</div>
    <div class="countList">
        <li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR">
       		&nbsp;<span class="btn_pack medium icon"><span class="upload"></span><input value="&nbsp;Up" type="submit" id="file" onclick="fnMoveRowUp();"></span>
 			&nbsp;<span class="btn_pack medium icon"><span class="download"></span><input value="&nbsp;Down" type="submit" id="file" onclick="fnMoveRowDown();"></span>
 			&nbsp;<span class="btn_pack medium icon"><span class="save"></span><input value="Save" type="submit" id="file" onclick="fnSaveAttrTypeCodeSortNum();"></span>
        </li>
    </div>
	<div id="gridDiv" class="clear pdL5 pdR5">
		<div id="grdGridArea" style="height:480px; width:99%"></div>
	</div>
</form>
</div>
<div class="schContainer" id="schContainer">
	<iframe name="blankFrame" id="blankFrame" src="about:blank" style="display: none" frameborder="0" scrolling='no'></iframe>
</div>
