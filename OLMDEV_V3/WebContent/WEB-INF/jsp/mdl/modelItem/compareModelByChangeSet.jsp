<!-- localhost OLM_DeV 1.1.2.5에서 테스트 -->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8"/>

<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<jsp:include page="/WEB-INF/jsp/template/tagInc.jsp" flush="true"/>
<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00087" var="WM00087"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00042" var="WM00042"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00012" var="CM00012"/>


<!-- 2. Script -->
<script type="text/javascript">
	var pp_grid1;				//그리드 전역변수
	
	$(document).ready(function() {	
		// 초기 표시 화면 크기 조정 
		$("#grdPAArea").attr("style","height:"+(setWindowHeight() - 100)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdPAArea").attr("style","height:"+(setWindowHeight() - 100)+"px;");
			$(".objbox").css("height",setWindowHeight() - 133);
		};
		
		PgridInit();
		doPSearchList();
		setTimeout(function() {$('.row20px').rowspan(0);}, 150);
		
	});	
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	$.fn.rowspan = function(colIdx, isStats) {
		return this.each(function(){
			var that;
			$('tr', this).each(function(row) {
				$('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
					if ($(this).html() == $(that).html()&& (!isStats|| isStats && $(this).prev().html() == $(that).prev().html())) {
						rowspan = $(that).attr("rowspan") || 1;
						rowspan = Number(rowspan)+1;
					
						$(that).attr("rowspan",rowspan);
						$(this).hide();
					} else {
						that = this;
					}
					that = (that == null) ? this : that;
				});
			});
		});
	};
	
	//그리드 초기화
	function PgridInit(){		
		var d = setPGridData();
		pp_grid1 = fnNewInitGridMultirowHeader("grdPAArea", d);
		pp_grid1.setColumnHidden(1,true);
		pp_grid1.setColumnHidden(8,true);
		pp_grid1.setColumnHidden(9,true);
		pp_grid1.setColumnHidden(10,true);
		pp_grid1.setColumnHidden(11,true);
		pp_grid1.setColumnHidden(12,true);
		pp_grid1.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		pp_grid1.attachEvent("onHeaderClick", function(id,ind){return;});
	}
	
	function setPGridData(){
		var result = new Object();
		result.title = "";
		result.header = "${menu.LN00016},Element,${menu.LN00106},${menu.LN00365} (${LastUpdated1}),#cspan,${menu.LN00366} (${LastUpdated2}),#cspan,${menu.LN00022},ObjectID,ElementID1,ElementID2,PreCSID,CurCSID,${menu.LN00384}";
		result.widths = "120,80,100,250,30,250,30,80,50,50,50,50,50,80";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,left,left,right,left,right,center,left,center,center,center,center,center";
		result.data = "";
		return result;
	}

	function doPSearchList(){
		var d = setPGridData();
		pp_grid1.enableRowspan(true);
		pp_grid1.enableColSpan(true);
		pp_grid1.loadXML("${root}" + "${xmlFilName}");
		
	}
	
	function fnGetNewElements(){
		var rowsNum  = pp_grid1.getRowsNum();
		var chagneMode = "";
		var elementIDs = new Array();
		var index = 0;
		for(var i=0; i<rowsNum; i++){
			changeMode = pp_grid1.cells2(i,7).getValue();
			if(changeMode == "New"){
				elementIDs[index] = pp_grid1.cells2(i,10).getValue();
				index++;
			}
		}
		return elementIDs;
	}
	
	function fnGetDelElements(){
		var rowsNum  = pp_grid1.getRowsNum();
		var chagneMode = "";
		var elementIDs = new Array();
		var index = 0;
		for(var i=0; i<rowsNum; i++){
			changeMode = pp_grid1.cells2(i,7).getValue();
			if(changeMode == "Deleted"){
				elementIDs[index] = pp_grid1.cells2(i,9).getValue();
				index++;
			}
		}
		return elementIDs;
	}

	function gridOnRowSelect(id,ind){
		var itemID = pp_grid1.cells(id,8).getValue();
		var changeMode = pp_grid1.cells(id,7).getValue();
		var preChangeSet = pp_grid1.cells(id,11).getValue();
		var changeSet = pp_grid1.cells(id,12).getValue();
		if(id == 0){
			var modelID1 = "${ModelID1}";
			var modelID2 = "${ModelID2}";
			var modelName1 = "${ModelName1}";
			var modelName2 = "${ModelName2}";
			var modelTypeName1 = "${ModelTypeName1}"
			var modelTypeName2 = "${ModelTypeName2}"
			
			if(ind == 3 || ind == 4){
				var delElementIDs = fnGetDelElements();	
				var url = "popupMasterMdlEdt.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					 + "&s_itemID=${itemID1}&modelID="+modelID1+"&scrnType=view&MTCategory=${MTCategory1}"
					 + "&modelName="+encodeURIComponent(modelName1)
					 + "&modelTypeName="+encodeURIComponent(modelTypeName1)
					 + "&delElementIDs="+delElementIDs; 
				var w = 1200;
				var h = 900;
			    window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
			}else if(ind == 5 || ind == 6){
				var newElementIDs = fnGetNewElements();			
				var url = "popupMasterMdlEdt.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					 + "&s_itemID=${itemID2}&modelID="+modelID2+"&scrnType=view&MTCategory=${MTCategory2}"
					 + "&modelName="+encodeURIComponent(modelName2)
					 + "&modelTypeName="+encodeURIComponent(modelTypeName2)
					 + "&newElementIDs="+newElementIDs;
				var w = 1200;
				var h = 900;
			    window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
			}else if(ind == 13){
				var preChangeSet = pp_grid1.cells(id,11).getValue();
				var changeSet = pp_grid1.cells(id,12).getValue();
				var url = "compareAttribute.do?s_itemID="+itemID+"&preChangeSet="+preChangeSet+"&changeSet="+changeSet;
				var w = 1200;
				var h = 800;
				window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
			}else {
				var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemID+"&scrnType=pop";
				var w = 1200;
				var h = 900; 
				itmInfoPopup(url,w,h,itemID);		
			}
		} else {
			if(ind != 13){
				var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemID+"&scrnType=pop";
				var w = 1200;
				var h = 900; 
				itmInfoPopup(url,w,h,itemID);		
			} 
			else{
				var url = "compareAttribute.do?s_itemID="+itemID+"&preChangeSet="+preChangeSet+"&changeSet="+changeSet;
				var w = 1200;
				var h = 800;
				window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
			}
		}
	}
</script>
<form name="symbolFrm" id="symbolFrm" action="" method="post" onsubmit="return false;">	
	<c:choose>
		<c:when test="${empty ModelID1 || empty ModelID2}">
			Only one model exist. Model cannot be compared!!
		</c:when>
		<c:otherwise>
			<div class="child_search_head mgB10">
				<p><img src="${root}${HTML_IMG_DIR}/category.png">&nbsp;&nbsp;Model comparison report</p>
			</div>
			<div class="countList">
		        <li class=" mgL10">
			        <span style="color:blue;">${menu.LN00381} : ${New}</span> , 
			        <span style="color:red;">${menu.LN00382} : ${Deleted}</span> , 
			        <span style="color:green;">${menu.LN00383} : ${Modified}</span>
		        </li>
		    </div>
			<div id="gridDiv" class="clear">
			    <div id="grdPAArea" style="width:100%;"></div>	
			</div>
		    
		</c:otherwise>
	</c:choose>
</form>
<iframe id="saveFrame" name="saveFrame" style="display:none" frameborder="0"></iframe>