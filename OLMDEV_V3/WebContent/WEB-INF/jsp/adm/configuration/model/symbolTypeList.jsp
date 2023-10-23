<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 OTitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00004" var="CM00004" />

<script type="text/javascript" src="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.js?v=7.1.8"></script>
<link rel="stylesheet" href="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.css?v=7.1.8">

<style>
.dhx_grid-cell {
	color: rgba(0, 0, 0, 0.95);
}
.dhx_grid-cell .edit-button, .dhx_grid-cell .save-button {
	padding: 0 25px;
}
.dhx_layout-rows {
	flex: 1 auto;
}
.dhx_layout-columns .dhx_layout-rows:first-child .dhx_form-element {
	padding-right:20px;
}
</style>

<!-- 2. Script -->
<script type="text/javascript">
$(document).ready(function() {	
	// 초기 표시 화면 크기 조정
	$("#layout").attr("style","height:"+(setWindowHeight() - 140)+"px;");
	// 화면 크기 조정
	window.onresize = function() {
		$("#layout").attr("style","height:"+(setWindowHeight() - 140)+"px;");
	};
	
	$("#excel").click(function (){
		grid.export.xlsx({
	        url: "//export.dhtmlx.com/excel"
	    });
	});
});

function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}

//[Add Click] popup 창 띄우기, 창 크기 부분
function addModelTypePop() {
	var url = "symbolTypeDetail.do";
	var data = "&pageNum=" + $("#currPage").val()+
			   "&viewType=N";
	var target = "symbolTypeDiv";
	ajaxPage(url,data,target);
}

//[Del Click]
function delModelFLow() {
	var selectedCell = grid.data.findAll(function (data) {
        return data.checkbox;
    });
	if(!selectedCell.length){
		alert("${WM00023}");	
	} else {
		if(confirm("${CM00004}")){
			for(idx in selectedCell){
				var url = "DeleteSymbolType.do";
				var data = "&SymTypeCode=" + selectedCell[idx].SymTypeCode
				+ "&LanguageID=${sessionScope.loginInfo.sessionCurrLangType}"
				+ "&gubun=1";
				
				var i = Number(idx) + 1;
				if (i == selectedCell.length) {
					data = data + "&FinalData=Final";
				}
				
				var target = "ModelFrame";
				ajaxPage(url, data, target);
				grid.data.remove(selectedCell[idx].id);
			}
		}
	}
}
</script>
</head>
<body>
<div id="symbolTypeDiv">
<form name="SymbolTypeList" id="SymbolTypeList" action="#" method="post" onsubmit="return false;">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}"></input> 
	<div class="cfgtitle" >					
		<ul>
			<li style="font-size:14px;"><a style="font-weight:400;font-size:14px;">Master data&nbsp;></a>&nbsp;Symbol Type</li>
		</ul>
	</div>

	<div class="child_search01 mgB5 mgL10 mgR10">
		<ul>
			<li class="floatR pdR10">
			<c:if test="${sessionScope.loginInfo.sessionLogintype == 'editor'}">
				<button class="cmm-btn mgR5" style="height: 30px;" onclick="addModelTypePop()" value="Add" >Add Symbol Type</button>
				<button class="cmm-btn mgR5" style="height: 30px;" id="excel" value="Down" >Download List</button>
				<button class="cmm-btn mgR5" style="height: 30px;" onclick="delModelFLow()" value="Del">Delete</button>
			</c:if>	
			</li>
		</ul>
	</div>
</form>

	<div style="width: 100%;" id="layout"></div>
	<div id="pagination"></div>

</div>

<!-- START :: FRAME -->
<div class="schContainer" id="schContainer">
	<iframe name="cFrame" id="cFrame" src="about:blank" style="display:none" frameborder="0" scrolling='no'></iframe>
</div>
<!-- END :: FRAME -->

<script>
	var layout = new dhx.Layout("layout", {
	    rows: [
	        {
	            id: "a",
	        },
	    ]
	});

	var gridData = ${gridData};
	var grid = new dhx.Grid("grdOTGridArea", {
	    columns: [
	        { width: 50, id: "RNUM", header: [{ text: "${menu.LN00024}", align: "center" }], align: "center" },
	        { width: 50, id: "checkbox", header: [{ text: "" }], align: "center", type: "boolean",  editable: true},
	        { width: 80, id: "SymTypeCode", header: [{ text: "${menu.LN00169}", align: "center" }, { content: "inputFilter" }], align: "center" },
	        { width: 80, id: "SymbolIcon", header: [{ text: "${menu.LN00176}", align: "center" }], align: "center", htmlEnable: true,
	    		template: function (text, row, col) {
	            	var result = "";
	            	if(text) result += "<img src='${root}${HTML_IMG_DIR_MODEL_SYMBOL}/symbol/" + row.SymbolIcon + "' />";
	                return result;
	            }	
	        },
	        { width: 120, id: "SymbolName", header: [{ text: "${menu.LN00028}", align: "center" }, { content: "inputFilter" }], align: "center" },
	        {
	            id: "action", width: 150, header: [{ text: "Actions", align: "center" }],
	            htmlEnable: true, align: "center",
	            template: function () {
	            	return '<span class="btn_pack small icon mgR10 edit"><span class="config"></span><input value="Config." type="submit"></span>';
	            }
	        },
	        { width: 80, id: "ItemCategory", header: [{ text: "${menu.LN00033}", align: "center" }, { content: "selectFilter" }], align: "center" },
	        { width: 150, id: "ItemTypeName", header: [{ text: "${menu.LN00021}", align: "center" }, { content: "selectFilter" }], align: "center" },
	        { width: 100, id: "ClassName", header: [{ text: "ClassCode", align: "center" }, { content: "selectFilter" }], align: "center" },
	        { width: 370, id: "ImagePath", header: [{ text: "ImagePath", align: "center" }, { content: "inputFilter" }] },
	        { width: 90, id: "Deactivated", header: [{ text: "Deactivated", align: "center" }, { content: "selectFilter" }], align: "center" },
	    ],
	    eventHandlers: {
	        onclick: {
	            "edit": function (e, data) {
	            	fnEditSymbol(data.row);
	            }
	        }
	    },
	    autoWidth: true,
	    resizable: true,
	    selection: "row",
	    tooltip: false,
	    data: gridData
	});
	layout.getCell("a").attach(grid);
	
	var pagination = new dhx.Pagination("pagination", {
	    data: grid.data,
	    pageSize: 20,
	});
	
	function fnEditSymbol(data){
		var url = "symbolTypeDetail.do";
		var data = "&SymTypeCode="+ data.SymTypeCode + 
				   "&LanguageID=${sessionScope.loginInfo.sessionCurrLangType}"+
				   "&pageNum=" + $("#currPage").val()+
				   "&viewType=E";
		var target = "symbolTypeDiv";
		ajaxPage(url, data, target);
	}

	function urlReload() {
		var url = "modelType.do";
		var target = "ModelFrame";
		var data = "LanguageID=${sessionScope.loginInfo.sessionCurrLangType}" +
						"&pageNum=" + $("#currPage").val(); 
	 	ajaxPage(url,data,target);
	}
</script>


</body>
</html>