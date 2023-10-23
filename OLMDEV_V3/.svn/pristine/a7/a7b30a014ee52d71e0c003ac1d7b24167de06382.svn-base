<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/" var="root"/> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />

<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00012" var="CM00012"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00046" var="WM00046"/>
 
<script type="text/javascript">
	var gridArea;
	var dimTypeID = "${dimTypeID}";
	var dimValueID = "${dimValueID}";

	$(document).ready(function(){
		$("#grid").attr("style","height:"+(setWindowHeight() - 210)+"px;");
		window.onresize = function() {
			$("#grid").attr("style","height:"+(setWindowHeight() - 210)+"px;");
		};
		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		fnSelect('dimTypeId', data, 'getDimensionTypeID', dimTypeID, 'Select');
		if(dimTypeID != "") fnGetDimValueID(dimTypeID, dimValueID);
				
		setElmGridList();
		$("#excel").click(function(){gridArea.toExcel("${root}excelGenerate");});
	});

	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	function setElmGridList(){
		var treePData="${elmTreeXml}";
		console.log("gridArea:"+gridArea);
	    gridArea = new dhtmlXGridObject('grid');
	    gridArea.selMultiRows = true;
	    gridArea.imgURL = "${root}${HTML_IMG_DIR_ITEM}/";
	    gridArea.setImagePath("${root}${HTML_IMG_DIR_ITEM}/");
	    gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");
		gridArea.setHeader("#master_checkbox,${menu.LN00028},${menu.LN00043},${menu.LN00119},Link,ItemID,LinkURL,LovCode,AttrTypeCode,${menu.LN00018},${menu.LN00027},${menu.LN00070},RoleID,OwnerTeamID");
		gridArea.attachHeader(",,,#select_filter,#select_filter,,,,,#select_filter,,,");
		gridArea.setInitWidths("50,500,*,150,60,50,50,50,50,100,100,100,50,50");
		gridArea.setColAlign("center,left,left,center,center,left,left,left,left,center,center,center,center,center");
		gridArea.setColTypes("ch,tree,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
		gridArea.setColSorting("int,str,str,str,str,str,str,str,str,str,str,str,str");
   	  	gridArea.init();
		gridArea.setSkin("dhx_web");
		gridArea.attachEvent("onCheck", function(rId,cInd,state){gridOnCheck(rId,cInd,state);});
		gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		try{
		gridArea.loadXMLString(treePData);
		}catch(e){
			console.log("err");
		}
		gridArea.setColumnHidden(0, true);
		gridArea.setColumnHidden(5, true);
		gridArea.setColumnHidden(6, true);
		gridArea.setColumnHidden(7, true);
		gridArea.setColumnHidden(8, true);
		gridArea.setColumnHidden(12, true);
		gridArea.setColumnHidden(13, true);
		gridArea.enableTreeCellEdit(false);
		gridArea.checkAll(false);
		gridArea.setFiltrationLevel(2);
	}
	
	function gridOnRowSelect(id, ind){
		if(id != "1"){
			if(ind == 4){
				var itemID = gridArea.cells(id, 5).getValue();
				var linkUrl = gridArea.cells(id, 6).getValue();
				var lovCode = gridArea.cells(id, 7).getValue();
				var attrTypeCode = gridArea.cells(id, 8).getValue();
				if(linkUrl != ""){
					fnOpenLink(itemID,linkUrl,lovCode,attrTypeCode);
				}
			} else if(ind == 3) {	// 역할
				var itemID = gridArea.cells(id, 12).getValue();
				if(itemID != "") fnItemPopUp(itemID);
			} else if(ind == 9) {	// 관리조직
				var w = "1200";
				var h = "800";
				var url = "orgMainInfo.do?id="+gridArea.cells(id, 13).getValue();
				window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
				
			} else {
				var itemID = gridArea.cells(id, 5).getValue();
				fnItemPopUp(itemID);
			}
		}
	}
	
	function fnOpenLink(itemID,url,lovCode,attrTypeCode){
		var url = url+".do?itemID="+itemID+"&lovCode="+lovCode+"&attrTypeCode="+attrTypeCode;
	
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h);
	}
	
	function fnItemPopUp(itemID){
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemID+"&scrnType=pop&screenMode=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,itemID);
	}
	
	function fnGetDimValueID(dimTypeID, dimValueID) {
		var data = "&dimTypeId="+dimTypeID+"&LanguageID=${sessionScope.loginInfo.sessionCurrLangType}";
		fnSelect('dimValueId', data, 'getDimTypeValueId', dimValueID, 'Select');
	}
	
	function fnView() {
		var url = "e2eProcessTreeInfo.do";
		var data = "&s_itemID=${s_itemID}&elmClass=${elmClass}&modelID="+$("#modelList").val()
						+"&dimTypeID="+$("#dimTypeId").val()
						+"&dimValueID="+$("#dimValueId").val();
		var target = "actFrame";
		ajaxPage(url, data, target);
	}
	
	function fnCollapseL4(){
		var ch = gridArea.hasChildren(1);
		for(var i=0; i<ch; i++){
			var lev1 = gridArea.getChildItemIdByIndex(1,i);
			gridArea.closeItem(lev1);
		}
	}
</script>
<style type="text/css">
	div.gridbox_dhx_web.gridbox .odd_dhx_web {background:none;}
	div.gridbox_dhx_web.gridbox table.obj tr.rowselected {background:none;}
	div.gridbox_dhx_web.gridbox table.obj.row20px tr.rowselected td {background:none;}
	.row20px img{   height:11px;  }
	.row20px div img{  height:18px;  }
	#filter{margin-bottom:20px; margin-top: 10px;}
	.layout_cell {padding-right: 1.5%;float:left;}
	.layout_cell label:first-child { margin-right: 5px; cursor: auto; font-weight: 700;}
</style>
</head>
<body>
<div id="processDIV">
<form name="procInstFrm" id="procInstFrm" action="#" method="post" onsubmit="return false;">
	<div class="child_search pdB5">
		<li class="floatL"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;&nbsp;<b>E2E Process Tree Info</b></li>
	</div>
	<div id="filter">
		<div class="layout_cell">
			<label>${menu.LN00125}</label>
			<select id="modelList" name="modelList" style="width:250px;">
				<c:forEach var="i" items="${modelList}">
				 <option value="${i.ModelID }" <c:if test="${modelID eq  i.ModelID}">selected</c:if>>${i.Name} (${i.MTCName})</option>
				</c:forEach>
			</select>
		</div>
		<div class="layout_cell">
			<label>${menu.LN00088}</label>
			<select id="dimTypeId" name="dimTypeId" style="width:100px;" onChange="fnGetDimValueID(this.value)">
				<option value="">Select</option>
			</select>
			<select id="dimValueId" name="dimValueId" style="width:100px;">
				<option value="">Select</option>
			</select>
		</div>
		<span class="btn_pack medium icon mgR10"><span class="search"></span><input value="View" type="submit" onclick="fnView()"></span>
		<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
		<div class="floatR">
			<img src="${root}${HTML_IMG_DIR}/csr_right_blue.png" onclick="gridArea.expandAll()" style="width:16px;margin-right:5px;cursor: pointer;" title="expand">
			<img src="${root}${HTML_IMG_DIR}/csr_minus_blue.png" onclick="fnCollapseL4()" style="width:16px;cursor: pointer;" title="collapse">
		</div>
	</div>
		<div id="grid" style="width:100%;"></div>

	</div>
</form>
<iframe id="saveFrame" name="saveFrame" style="display:none;width:0px;height:0px;"></iframe>
</body></html>