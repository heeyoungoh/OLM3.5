<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/> 
<jsp:include page="/WEB-INF/jsp/template/tagInc.jsp" flush="true"/>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/<%=GlobalVal.BASE_ATCH_URL%>/css/dhtmlx/dhtmlxgrid_treegrid.css'/>">
<script src="<c:url value='/cmm/js/dhtmlx/dhtmlxTreeGrid/codebase/dhtmlxtreegrid.js'/>" type="text/javascript" charset="utf-8"></script> 

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00046" var="WM00046"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00124" var="WM00124"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00020" var="WM00020"/>

<script type="text/javascript">
	var ptg_gridArea;
	var modelID = "${ModelID}";
	var gridChk = "${gridChk}";
	$(document).ready(function() {
			$("#grdPtgArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
			window.onresize = function() {
				$("#grdPtgArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
			};
		
		$("#excel").click(function(){ptg_gridArea.toExcel("${root}excelGenerate");});
		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&s_itemID=${s_itemID}&modelID=${ModelID}&classCode=${groupClassCode}";
		//fnSelect('modelSelect', data, 'getModelName_commonSelect', '${ModelID}', 'Select','model_SQL');
		fnSelect('classCode', data, 'getElementClassCode', '${classCode}', 'Select');
		fnSelect('groupClassCode', data, 'getGroupElementClassCode', '${groupClassCode}', 'Select');
		fnSelect('groupElementCode', data, 'getGroupElementCode', '${groupElementCode}', 'Select');
		
		if(gridChk == "group"){
			$("#group").prop('checked', true);
			setPtgGridList();
		} else if(gridChk == "connection"){
			$("#connection").prop('checked', true);
			gridInit();		
			doSearchList(modelID);
		} else {
			$("#element").prop('checked', true);
			setPtgGridList();
		}
		
		$(":input:radio[name=gridChk]").change(function(){
			view($(":input:radio[name=gridChk]:checked").val());
		})
		
		$("#searchKey").val("${searchKey}").attr("selected","selected");
		
		if("${showChild}" == "true") {$("#showChild").attr("checked","checked");}
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	function fnGetGroupElementList(classCode){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&modelID=${ModelID}&classCode="+classCode;
		fnSelect('groupElementCode', data, 'getGroupElementCode', '', 'Select');
	}
	
	function doSearchList(modelID){
		var d = setGridData(modelID);
		fnLoadDhtmlxGridJson(ptg_gridArea, d.key, d.cols, d.data);
	}
	
	//===============================================================================
	// BEGIN ::: setPtgGridList
	function setPtgGridList(){	
		var treePData="${prcTreeXml}";
	    ptg_gridArea = new dhtmlXGridObject('grdPtgArea');
	    ptg_gridArea.selMultiRows = true;
	    ptg_gridArea.imgURL = "${root}${HTML_IMG_DIR_ITEM}/";
	    ptg_gridArea.setImagePath("${root}${HTML_IMG_DIR_ITEM}/");
		ptg_gridArea.setHeader("#master_checkbox,${menu.LN00028},${menu.LN00016},${menu.LN00043},${menu.LN00014},${menu.LN00018},${menu.LN00004},${menu.LN00070},${menu.LN00027},ItemID,ClassCode,AuthorID,Blocked,ElementID");
		ptg_gridArea.setInitWidths("30,350,90,*,0,90,80,90,80,0,0,0,0,0");
		ptg_gridArea.setColAlign("center,left,center,left,center,center,center,center,center,center,center,center,center,center");
		ptg_gridArea.setColTypes("ch,tree,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
		ptg_gridArea.setColSorting("int,str,str,str,str,str,str,str,str,str,str,str,str,str");
   	  	ptg_gridArea.init();
		ptg_gridArea.setSkin("dhx_web");
		ptg_gridArea.attachEvent("onCheck", function(rId,cInd,state){ptgGridOnCheck(rId,cInd,state);});
		ptg_gridArea.attachEvent("onRowSelect", function(id,ind){ptgGridOnRowSelect(id,ind);});
		ptg_gridArea.loadXMLString(treePData);
		if(gridChk == "element") ptg_gridArea.setColumnHidden(2, true);
		ptg_gridArea.setColumnHidden(4, true);
		ptg_gridArea.setColumnHidden(9, true);
		ptg_gridArea.setColumnHidden(10, true);
		ptg_gridArea.setColumnHidden(11, true);
		ptg_gridArea.setColumnHidden(12, true);
		ptg_gridArea.setColumnHidden(13, true);
		ptg_gridArea.enableTreeCellEdit(false);
	}
	
	function gridInit(){	
		var d = setGridData();
		ptg_gridArea = fnNewInitGrid("grdPtgArea", d);
		ptg_gridArea.setImagePath("${root}${HTML_IMG_DIR_ITEM}/");//path to images required by grid
		ptg_gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");		
		ptg_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		
		fnSetColType(ptg_gridArea, 1, "ch");
		fnSetColType(ptg_gridArea, 3, "img");
		fnSetColType(ptg_gridArea, 7, "img");
	}
	
	function setGridData(modelID){
		var result = new Object();
		result.title = "${title}";
		result.key =  "model_SQL.getElementCxnItemList";
		result.header = "${menu.LN00024},#master_checkbox,No,${menu.LN00042},${menu.LN00016},ID,Source Name,${menu.LN00042},${menu.LN00016},ID,Target Name,Connection Name,ObjectID,SourceItemID,TargetItemID";
		result.cols = "CHK|SortNum|FromItemTypeImg|SourceClassName|SourceID|SourceName|ToItemTypeImg|TargetClassName|TargetID|TargetName|ConnectionName|ObjectID|SourceItemID|TargetItemID";
		result.widths = "30,0,0,50,100,70,*,50,100,70,*,130,0,0,0";
		result.aligns = "center,center,center,center,center,center,left,center,center,center,left";
		result.sorting = "int,int,int,str,str,str,str,str,str,str,str,str,str";
		result.data = "modelID="+modelID;	
		return result;
	}
	
	function ptgGridOnCheck(rId,cInd,state){
		if( state ){
			var classCode =ptg_gridArea.cells(rId, 10).getValue();
			if(classCode==""||classCode==undefined||classCode==null){
				ptg_gridArea.cellById(rId,0).setValue(0);
			}
		}
		var hasChild = ptg_gridArea.hasChildren(rId);
		if (hasChild != 0){
			var value = ptg_gridArea.cellById(rId,0).getValue();
			var items = [];
			items = ptg_gridArea.getSubItems(rId);
			items = items.split(',');
			if(value == '1'){
				for(var i=0; i<items.length; i++){ ptg_gridArea.cells(items[i],0).setValue(1); }
			} else {
				for(var i=0; i<items.length; i++){ ptg_gridArea.cells(items[i],0).setValue(0); }
			}
		}
	}
	
	function ptgGridOnRowSelect(id, ind){
		var avg = ptg_gridArea.cells(id, 9).getValue();
		if(ind != '0'){
			if(id == '1'){
				modelPopDetail(avg);
			} else if(avg==""||avg==undefined||avg==null){
				
			} else {
				itemPopDetail(avg);
			}
		}
	}
	
	function gridOnRowSelect(id, ind){
		var itemID = ptg_gridArea.cells(id,12).getValue();
		if(ind == 0){ 
			return; 
		}
		else if (ind == 3 || ind == 4 || ind == 5 || ind == 6 ){
			itemID = ptg_gridArea.cells(id,13).getValue();
		}else if (ind == 7 || ind == 8 || ind == 9 || ind == 10){
			itemID = ptg_gridArea.cells(id,14).getValue();
		}else if (ind == 11){
			itemID = ptg_gridArea.cells(id,12).getValue();
		}
		itemPopDetail(itemID);
	}
	
	function modelPopDetail(mdlID){
		var url = "popupMasterMdlEdt.do?"
			+"languageID=${sessionScope.loginInfo.sessionCurrLangType}"
			+"&s_itemID=${s_itemID}"
			+"&modelID="+mdlID
			+"&scrnType=view"
// 			+"&MTCategory="+MTCategory
// 			+"&modelName="+encodeURIComponent(modelName)
// 		    +"&modelTypeName="+encodeURIComponent(ModelTypeName)
// 			+"&menuUrl="+menuUrl
// 			+"&changeSetID="+changeSetID
			+"&selectedTreeID=${s_itemID}";
	var w = 1200;
	var h = 900;
	window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}
	
	function itemPopDetail(itmID){
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itmID+"&scrnType=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,itmID);
	}
	
	function view(e){	
		gridChk = e;
		var url = "viewModelElmTree.do";
		var data = "s_itemID="+ "${s_itemID}" + 
// 							"&varFilter="+ "${varFilter}" + 
							"&modelID=" + modelID +
							"&gridChk="+gridChk +
							"&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		if(gridChk == "element"){
			var classCode = $("#classCode").val();
			var searchKey = $("#searchKey").val();
			var searchValue = $("#searchValue").val();
			if(classCode != undefined & classCode != '' & classCode != null) data += "&classCode="+$("#classCode").val();
			if(searchKey != undefined & searchKey != '' & searchKey != null) data += "&searchKey="+$("#searchKey").val();
			if(searchValue != undefined & searchValue != '' & searchValue != null) data += "&searchValue="+$("#searchValue").val();
			data += "&showChild="+ $("#showChild").is(":checked");
		}
		if(gridChk == "group"){
			var groupClassCode = $("#groupClassCode").val();
			var groupElementCode = $("#groupElementCode").val();
			if(groupClassCode != undefined & groupClassCode != '' & groupClassCode != null) data += "&groupClassCode="+$("#groupClassCode").val();
			if(groupElementCode != undefined & groupElementCode != '' & groupElementCode != null) data += "&groupElementCode="+$("#groupElementCode").val();
		}
		var target = "processDIV";
		ajaxPage(url,data,target);
	}
	
	function chgModel(){
		var url = "viewModelElmTree.do";
		var data = "s_itemID="+ "${s_itemID}" + 
							"&varFilter="+ "${varFilter}" + 
							"&modelID="+ $("#modelSelect").val() + 
							"&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		var target = "processDIV";
		ajaxPage(url,data,target);
	}
	
	function fnEditAttr(){
		var checkedRows = ptg_gridArea.getCheckedRows(0).split(",");
		if(ptg_gridArea.getCheckedRows(0).length == 0){	
			alert("${WM00023}");		
			return;
		}
		
		var itemIDs = new Array;
		var classCodes = new Array;
		var sessionUserID = "${sessionScope.loginInfo.sessionUserId}";
		var sessionAuthLev = "${sessionScope.loginInfo.sessionAuthLev}";
		
		for(var i = 0 ; i < checkedRows.length; i++ ){
			if(checkedRows[i].indexOf('CL') >= 0){}
			else {
				var blocked = ptg_gridArea.cells(checkedRows[i], 12).getValue();
				if(blocked != "0"){ // 
					alert("${WM00124}");
					return;
				} else if(sessionAuthLev != "1"&& sessionUserID != ptg_gridArea.cells(checkedRows[i], 11).getValue()){
					alert("${WM00040}");
					return;
				}
				
				itemIDs.push(ptg_gridArea.cells(checkedRows[i], 9).getValue());
				classCodes.push(ptg_gridArea.cells(checkedRows[i], 10).getValue());
			}
		}
		
		var url = "selectAttributePop.do?classCodes="+classCodes+"&items="+itemIDs+"&gridChk="+ $(":input:radio[name=gridChk]:checked").val();
		$("#items").val(itemIDs);
		var w = 600;
		var h = 600;
		itmInfoPopup(url,w,h);
	}
	
	function urlReload(){
		var gridChk = "${gridChk}";
		if(gridChk == "group"){
			$("#group").prop('checked', true);
			setPtgGridList();
		} else {
			$("#element").prop('checked', true);
			setPtgGridList();
		}
	}
	
	function fnEditSortNum(){
		var checkedRows = ptg_gridArea.getCheckedRows(0).split(",");
		if(ptg_gridArea.getCheckedRows(0).length == 0){	
			alert("${WM00020}");		
			return;
		}
		
		var itemIDs = new Array;
		for(var i = 0 ; i < checkedRows.length; i++ ){
			itemIDs[i] = ptg_gridArea.cells(checkedRows[i], 9).getValue();
		}
		for(var i = 0 ; i < itemIDs.length; i++ ){
			if(itemIDs[i] == '' || itemIDs[i] == null){ itemIDs.splice(i,1); }
		}
	
		var modelID = "${ModelID}";
		var url = "openEditModelSortNum.do?modelID="+modelID+"&itemIDs="+itemIDs;
		var w = 600;
		var h = 600;
		itmInfoPopup(url,w,h);
	}
	
	function fnShowChild(){
		view("element");
	}
</script>
<body>
<div id="processDIV">
	<input type="hidden" id="items" name="items" >
	<div class="child_search01">
		<li class="floatL pdL20">
			<!-- Model&nbsp;<select class="mgR20" id="modelSelect" name="modelSelect" onchange="chgModel()"></select> -->
			<input type="radio" name="gridChk" value="element" id="element"/><label for="element">&nbsp;Object</label>
			<input type="radio" name="gridChk" value="group" id="group" class="mgL10"/><label for="group">&nbsp;Group</label>
			<input type="radio" name="gridChk" value="connection" id="connection" class="mgL10"/><label for="connection">&nbsp;Connection</label>
<%-- 			<input type="image" class="image searchList mgL10" src="${root}${HTML_IMG_DIR}/btn_view2.png" value="View" onclick="view()"/> --%>
		</li>			
	</div>
	<c:if test="${gridChk eq 'element'}">
		<div class="child_search01 mgB5">		
			 <li class="floatL pdL20">
			 	${menu.LN00016}&nbsp;
				<select id="classCode" name="classCode" class="sel" style="width:130px;margin-left=5px;"></select>
		 	 	&nbsp;&nbsp;&nbsp;
		 	 	<select id="searchKey" name="searchKey" style="width:80px;">
					<option value="Name">Name</option>
					<option value="ID" >ID</option>
				</select>			
				<input type="text" class="text"  id="searchValue" name="searchValue" value="${searchValue}" style="width:250px;">
				<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="view('element')" value="Search">
				
				<input type="checkbox" id="showChild" name="showChild" OnClick="fnShowChild();" class="mgL20">&nbsp;<label for="showChild">Show Child</label>
		   	 </li>
			<li class="floatR pdR20">
				<c:if test="${sessionScope.loginInfo.sessionAuthLev < 4}">	
				<span class="btn_pack medium icon"><span class="edit"></span><input value="Attribute" onclick="fnEditAttr()" type="submit"></span>
				<span class="btn_pack medium icon"><span class="edit"></span><input value="Sequence" onclick="fnEditSortNum()" type="submit"></span>
				</c:if>
				<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>	
			</li>	
		</div>	
	</c:if>
	<c:if test="${gridChk eq 'group'}">
		<div class="child_search01 mgB5">		
			<li class="floatL pdL20">
			 	Element Group Class
				<select id="groupClassCode" name="groupClassCode" class="sel" OnChange="fnGetGroupElementList(this.value);" style="width:150px; margin:0 5px;"></select>
				 	&nbsp;Element Group
				<select id="groupElementCode" name="groupElementCode" class="sel" style="width:150px; margin:0 5px;"></select>
				<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="view('group')" value="Search">
			</li>
			<li class="floatR pdR20">
				<c:if test="${sessionScope.loginInfo.sessionAuthLev < 4}">	
				<span class="btn_pack medium icon"><span class="edit"></span><input value="Attribute" onclick="fnEditAttr()" type="submit"></span>
				</c:if>
				<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>	
			</li>		
		</div>
	</c:if>
	<div id="gridPtgDiv" class="mgB10 mgT10">
		<div id="grdPtgArea" style="width:100%;"></div>
	</div>
</div>
</body>