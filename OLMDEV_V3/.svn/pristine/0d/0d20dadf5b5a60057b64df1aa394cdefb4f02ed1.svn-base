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
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00009" var="CM00009"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00004" var="CM00004"/>

<script type="text/javascript">

	var p_gridArea;	
	var listScale = "<%=GlobalVal.LIST_SCALE%>";
		
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 310)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 310)+"px;");
		};

		$("#excel").click(function(){
			doExcel();
		});
		
		gridInit(); doSearchList();
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
		var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	function gridInit(){	
		var d = setGridData(); 
		p_gridArea = fnNewInitGrid("grdGridArea", d); 
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR}/");
		
		fnSetColType(p_gridArea, 1, "ch");
		fnSetColType(p_gridArea, 8, "img");
		fnSetColType(p_gridArea, 9, "img");
		p_gridArea.setColumnHidden(0, true);
		p_gridArea.setColumnHidden(7, true);
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		
		p_gridArea.enablePaging(true,listScale,10,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
		
	}
	
	function setGridData(){ 
		var result 	= new Object();	
		result.title 	= "${title}";	
		result.key 	= "model_SQL.getDiagramList";		
		result.header = "No,#master_checkbox,No,${menu.LN00028},${menu.LN00032},${menu.LN00060},${menu.LN00070},ItemID,Action,#cspan"; 
		result.cols 	= "CHK|DiagramID|DiagramNM|ModelTypeName|CreatorName|LastUpdated|ItemID|ViewDiagram|EditDiagram";
		result.widths = "30,30,100,*,180,80,100,80,80,80";
		result.sorting = "int,int,in,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,center,center,center,center,center,center,center"; 
		result.data = "&languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}"
						+ "&searchValue="+$("#searchValue").val();
		return result;
	}
	
	function doExcel() {
		p_gridArea.toExcel("${root}excelGenerate");
	}	
	
	function fnOpenCreateDiagram(){	
		var ItemTypeCode = $("#ItemTypeCode").val(); 
		var data = "&ItemTypeCode=";

		$("#divTabDiagramAdd").removeAttr('style', 'display: none');
		$("#newDiagram").removeAttr('style', 'display: none');
		$("#newDiagram").focus();		
		fnSelect('modelTypeCode', data, 'getMDLTypeCode', '', 'Select'); 
	}
	
	function fnCreateDiagram(){
		if(confirm("${CM00009}")){	
			var url = "createDiagram.do";
			var data = "&modelTypeCode="+$("#modelTypeCode").val()
						+"&newDiagramName="+$("#newDiagramName").val();
			var target = "blankFrame";		
			ajaxSubmit(document.diaFrm, url, target);
		}
	}
	
	function gridOnRowSelect(id, ind){
		$("#newDiagram").attr('style', 'display: none');
		$("#divTabDiagramAdd").attr('style', 'display: none');
	
		var diagramID = p_gridArea.cells(id,2).getValue();
	
		$("#diagramID").val(diagramID);
		var scrnType = "edit";
		if(ind == 8){ scrnType = "view"; 	}	
		$("#scrnType").val(scrnType);
		
		var url = "diagramMasterPop.do"; 
		var option = "width=1200, height=700, left=300, top=300,scrollbar=yes,resizble=0";
		window.open("", "diagramMaster", option);
		document.diaFrm.action=url;
		document.diaFrm.target="diagramMaster";
		document.diaFrm.submit();
	}
	
	function fnCallback(){
		$("#newDiagram").attr('style', 'display: none');
		$("#divTabDiagramAdd").attr('style', 'display: none');
		doSearchList();
	}
	
	function fnDeleteDiagram(){
		var checkedRows = p_gridArea.getCheckedRows(1);
		if(checkedRows != ""){checkedRows = checkedRows.split(",");}
		if(checkedRows.length == 0){
			alert("${WM00023}");
			return;
		}
		if(confirm("${CM00004}")){
			var diagramIDs = new Array();
			
			for(var i = 0 ; i < checkedRows.length; i++ ){
				diagramIDs[i] = p_gridArea.cells(checkedRows[i], 2).getValue();	
			}
			
			if(diagramIDs != ""){
				var url = "deleteDiagram.do";
				var data = "diagramIDs="+diagramIDs;
				var target = "blankFrame";
				ajaxPage(url, data, target);
			}
		}
	}

</script>
</head>
<body>
<div class="pdL10 pdR10 pdT10" style="background:#fff;height:100%;">
<form name="diaFrm" id="diaFrm" action="#" method="post" onsubmit="return false;" >
	<input type="hidden" id="diagramID" name="diagramID" >
	<input type="hidden" id="scrnType" name="scrnType" >
	<input type="hidden" id="autoSave" name="autoSave" value="${autoSave}">
		
	<div class="child_search" >
		<li class="shortcut"><h3 style="padding: 6px 0"><img src="${root}${HTML_IMG_DIR}/img_folderClosed.png">&nbsp;&nbsp;Diagram List</h3></li>
	</div>
  	<div class="countList">
    	<li class="count">Total  <span id="TOT_CNT"></span></li>
    	<li style="padding-left:80px !important;float:left;">
    		<input type="text" class="text"  id="searchValue" name="searchValue" value="${searchValue}" style="width:150px;">
			<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="doSearchList()" value="검색">
    	</li>
    	<li class="floatR pdR20">
			<c:if test="${sessionScope.loginInfo.sessionLogintype == 'editor'}">
			<span class="btn_pack small icon"><span class="add"></span><input value="Create" type="submit" onclick="fnOpenCreateDiagram();"></span>&nbsp;
			<span class="btn_pack small icon"><span class="del"></span><input value="Del" type="submit" onclick="fnDeleteDiagram()"></span>&nbsp;
			</c:if>
			<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
		</li>		
    </div>
	<div id="gridDiv" class="mgB10 clear">
		<div id="grdGridArea" style="width: 100%"></div>
	</div>	
	<!-- START :: PAGING -->
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
	<!-- END :: PAGING -->	
	<div id="divTabDiagramAdd" class="ddoverlap" style="display: none;">
		<ul>
			<li class="selected" ><a><span>Create Diagram</span></a></li>
		</ul>
	</div>
	<table id="newDiagram" class="tbl_blue01 mgT5" width="100%"  cellpadding="0" cellspacing="0" style="display: none;">
		<tr>
			<th>${menu.LN00028}</th>
			<th>${menu.LN00032}</th>
			<th></th>
		</tr>
		<tr>
			<td><input type="text" class="text" id="newDiagramName" name="newDiagramName"  value=""/></td>
			<td><select id="modelTypeCode" name="modelTypeCode" class="sel"></select></td>
			<td>
				<c:if test="${sessionScope.loginInfo.sessionLogintype == 'editor'}">
					<span class="btn_pack medium icon"><span  class="save"></span><input value="Save" onclick="fnCreateDiagram()"  type="submit"></span>
				</c:if>	
		    </td>
		</tr>	
	</table>
</form>
<iframe name="blankFrame" id="blankFrame" src="about:blank" style="display:none" frameborder="0" scrolling='no'></iframe>
</div>
</body>
</html>