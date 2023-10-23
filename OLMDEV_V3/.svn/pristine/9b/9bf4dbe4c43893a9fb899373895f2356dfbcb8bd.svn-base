<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<% request.setCharacterEncoding("utf-8"); %>
<c:url value="/" var="root"/>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 OTitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<script type="text/javascript" src="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.js?v=7.1.8"></script>
<link rel="stylesheet" href="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.css?v=7.1.8">

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00034" var="CM00034" />

<style>
	.dhx_grid-cell {
		color: rgba(0, 0, 0, 0.95);
	}
</style>

<!-- 2. Script -->
<script type="text/javascript">
	$(document).ready(function() {	
	});
	
	//[save] 이벤트 후 처리
	function selfClose() {
		//var opener = window.dialogArguments;
		opener.urlReload();
		self.close();
	}
</script>
<body>
	<div class="title-section">
		Attribute Type
		<span class="floatR btn_pack small icon"><span class="add"></span><input value="Add" type="submit" onclick="fnAdd()"></span>
	</div>
	<div id="layout" style="width:100%;height:530px;"></div>
	<!-- START :: FRAME -->
	<div class="schContainer" id="schContainer">
		<iframe name="ArcFrame" id="ArcFrame" src="about:blank"
			style="display: none" frameborder="0" scrolling='no'></iframe>
	</div>
</body>
<script>
	var layout = new dhx.Layout("layout", {
	    rows: [
	        {
	            id: "a",
	        },
	    ]
	});

	var gridData = ${gridData};
	var grid = new dhx.Grid("gridArea", {
	    columns: [
	        { width: 50, id: "RNUM", header: [{ text: "${menu.LN00024}" }], align: "center" },
	        { width: 150, id: "AttrTypeCode", header: [{ text: "${menu.LN00015}" }, { content: "inputFilter" }], align: "center" },
	        { width: 150,  id: "DataType", header: [{ text: "Category" }, { content: "selectFilter" }] },
	        { id: "Name", header: [{ text: "${menu.LN00028}" }, { content: "inputFilter" }] },
	        { id: "Description", header: [{ text: "${menu.LN00035}" }, { content: "inputFilter" }] }
	    ],
	    autoWidth: true,
	    resizable: true,
	    selection: "row",
	    tooltip: false,
	    data: gridData,
	    multiselection : true,
	});

	layout.getCell("a").attach(grid);
	
	function fnAdd(){
		var selectedCell =  grid.selection.getCells();
		if(!selectedCell.length){
			alert("${WM00023}");	
		}else{
			if(confirm("${CM00034}")){
				var attrCodes = "";
				for(var i=0; i<selectedCell.length; i++){
					if (attrCodes == "") {
						attrCodes = selectedCell[i].row.AttrTypeCode;
					} else {
						attrCodes += ","+selectedCell[i].row.AttrTypeCode;
					}
				}
				
				var url = "SaveAttrType.do";
				var target = "ArcFrame";
				var data = "attrCodes=" + attrCodes
						+ "&ItemClassCode=${TypeCode}" 
						+ "&ItemTypeCode=${ItemTypeCode}";
	 			ajaxPage(url, data, target);
			}
		}
	}
</script>

</html>