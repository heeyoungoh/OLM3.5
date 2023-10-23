<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<script type="text/javascript">
var g_AC = "text-align:center;";var g_AL = "text-align:left;";var g_AR = "text-align:right;";
var dhx_skin_skyblue = "dhx_skyblue";var dhx_skin_web = "dhx_web"; var dhx_skin_brd="dhx_brd";var df_dhx_skin = dhx_skin_web;
var layout_1C = "1C";var layout_2E = "2E";var layout_2U = "2U";var lMinWidth = 230, rMinWidth=750;
var df_pageSize = 10;
var chkReadOnly = false; var df_isDebug=false;
</script>
<c:if test="${screenType ne 'model'}"><%@ include file="uiInc.jsp" %></c:if>
<c:if test="${screenType eq 'model'}"><%@ include file="uiInc_model.jsp" %></c:if>
<%@ include file="sessionCheck.jsp" %>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00018" var="WM00018" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00066" var="WM00066" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00033" var="CM00033" />

<script type="text/javascript">
if (_isIE) _isIE = 8;//dhtmlx grid
var authLev = '${sessionScope.loginInfo.sessionAuthLev}';
var languageCode = '${sessionScope.loginInfo.sessionCurrLangCode}';
var sessionDefFont = "${sessionScope.loginInfo.sessionDefFont}";
//if(document.documentMode<=8 && "${sessionScope.loginInfo.sessionCurrLangType}"=="2052"){$(".SubinfoTabs ul li span").attr("style","padding: 7px 12px 5px 6px;");}

function showCallStack(){var f=showCallStack,result="Call stack:\n";while((f=f.caller)!==null){var sFunctionName = f.toString().match(/^function (\w+)\(/);sFunctionName = (sFunctionName) ? sFunctionName[1] : 'anonymous function';result += sFunctionName;result += getArguments(f.toString(), f.arguments);result += "\n";}return result;}
function fnGetRootUrl() {return "${root}";}
function fnAnySelect(id, data, menuId, defaultValue, isAll) {url = "<c:url value='/ajaxCodeSelect.do'/>";data += "&menuId="+menuId;if(isAll==null) {isAll = 'select';}ajaxSelect(url, data, id, defaultValue, isAll);}
function fnSelect(id, code, menuId, defaultValue, isAll, headerKey, debug) {url = "<c:url value='/ajaxCodeSelect.do'/>";data = "ajaxParam1="+code+"&menuId="+menuId+"&headerKey="+headerKey;if(isAll==null) {isAll = 'select';}ajaxSelect(url, data, id, defaultValue, isAll, headerKey, debug);}
function fnSelectJson(e,n,a,t){var r=new Array;return $.ajax({url:"ajaxCodeSelectJson.do",data:"ajaxParam1="+e+"&menuId="+n+"&headerKey="+a,type:"POST",async:!1,dataType:"json",success:function(e){t&&((n=new Object).value="",n.content=t,n=JSON.stringify(n),r.push(JSON.parse(n)));for(idx in e)if(0!=e[idx].length){var n=new Object,a=e[idx].CODE;e[idx].CODE||(a="");var s=e[idx].NAME;e[idx].NAME||(s=""),n.value=a,n.content=s,n=JSON.stringify(n),r.push(JSON.parse(n))}},error:function(e,n,a){console.log("ERR :["+e.status+"]"+a)}}),r}
function fnRadio(id, code, menuId, defaultValue, radioID, isDisabled) {url = "<c:url value='/ajaxCodeRadio.do'/>";data = "ajaxParam1="+code+"&menuId="+menuId;ajaxRadio(url, data, id, defaultValue, radioID, isDisabled);}
function fnGridCombo(grid, colIndex, code, menuId) {url = "<c:url value='/ajaxDhtmlxCombo.do'/>";data = "ajaxParam1="+code+"&menuId="+menuId;ajaxGridCombo(url, data, grid, colIndex, false);fnSetColType(grid, colIndex, "coro");}
function fnSelectNone(id, code, menuId, defaultValue) {var url = "<c:url value='/ajaxCodeSelect.do'/>";var data = "ajaxParam1="+code+"&menuId="+menuId;ajaxSelect(url, data, id, defaultValue, 'n');}
function fnCheckbox(id, code, menuId, name, checkYn, preHtml, debug) {if(name && name!=null&& name!='') {}else {name="chkBox";}var url = "<c:url value='/ajaxCheckbox.do'/>";var data = "name="+name+"&checkYn="+checkYn+"&ajaxParam1="+code+"&menuId="+menuId;ajaxPage(url, data, id, preHtml, debug);}
function fnPage(id, code, menuId, preHtml, debug) {var url = "<c:url value='/ajaxPage.do'/>";var data = "ajaxParam1="+code+"&menuId="+menuId;ajaxPage(url, data, id, preHtml, debug);}
function fnImgPage(id, imgKey, debug) {var url = "<c:url value='/ajaxImgPage.do'/>";var data = "ATTFILE_ID="+imgKey;ajaxPage(url, data, id, '', debug);}
function fnImgUpdatePage(id, imgKey, debug, noImg, noDel) {var url = "<c:url value='/ajaxImgDeleteabledPage.do'/>";var data = "ATTFILE_ID="+imgKey;if( noImg == 'Y' ){data = data +"&NOIMG=Y";}if( noDel == 'Y' ){data = data +"&NODEL=Y";}ajaxPage(url, data, id, '', debug);}
function fnFilePage(id, imgKey, debug) {var url = "<c:url value='/ajaxFilePage.do'/>";var data = "ATTFILE_ID="+imgKey;ajaxPage(url, data, id, '', debug);}
function fnValue(id, code, menuId, preHtml, debug) {var url = "<c:url value='/ajaxPage.do'/>";var data = "ajaxParam1="+code+"&menuId="+menuId;ajaxValue(url, data, id, preHtml, debug);}
function fnFileDelete(ATTFILE_ID, SEQ, debug) {if(confirm("${CM00033}")) {var url = "<c:url value='/fileDelete.do'/>";var data = "ATTFILE_ID="+ATTFILE_ID+"&SEQ="+SEQ;ajaxPage(url, data, "file"+ATTFILE_ID+"_"+SEQ);}}

function fnNewGrid(id, title, skin) {if( skin == undefined || skin == "" ){skin = df_dhx_skin;}grid = new dhtmlXGridObject('gridbox');grid.setImagePath("<c:url value='/cmm/js/dhtmlx/dhtmlxGrid/codebase/imgs/'/>");grid.setDateFormat("%Y-%m-%d");grid.setSkin(skin);grid.enableAlterCss("even", "uneven");grid.enableKeyboardSupport(true);if(title) {var gridUid = grid.entBox.id;var align = new Array();var colSorting = '';var colAlign = '';var colType = '';for (var ti = 0; ti<title.split(",").length; ti++) {align[ti]=g_AC;if(ti > 0) {colSorting += ',';colAlign += ',';colType += ',';}colSorting += 'str';colAlign += 'center';colType += 'ro';}grid.setHeader(title, null, align);grid.setColSorting	(colSorting);grid.setColAlign	(colAlign);grid.setColTypes	(colType);$add("gridHeaders_"+gridUid, title);}return grid;}
function fnNewInitGrid(id, data, skin, isMulti) {if( skin == undefined || skin == "" ){skin = df_dhx_skin;}grid = new dhtmlXGridObject(id);grid.setImagePath("<c:url value='/cmm/js/dhtmlx/dhtmlxGrid/codebase/imgs/'/>");grid.setDateFormat("%Y-%m-%d");grid.setSkin(skin);grid.enableAlterCss("even", "uneven");grid.enableKeyboardSupport(true);if(data!=null) {var gridUid = grid.entBox.id;var align = new Array();var colWidth = '';var colSorting = '';var colAlign = '';var colType = '';var colHidden = '';for (var ti = 0; ti<data.header.split(",").length; ti++) {align[ti]=g_AC;colWidth += ti>0 ? ',80' : '50';colSorting += ti>0 ? ',str' : 'str';colAlign += ti>0 ? ',center' : 'center';colType += ti>0 ? ',ro' : 'ro';colHidden += ti>0 ? ',false' : 'false';}grid.$columnName = (data.cols).split("|");grid.$cells = $cell(data.cols);grid.$header = data.header.split(",");grid.setHeader(data.header, null, align);grid.setInitWidths(fnNvl(data.widths,colWidth));grid.setColAlign(fnNvl(data.aligns,colAlign));grid.setColTypes(fnNvl(data.types,colType));grid.setColSorting(fnNvl(data.sorting,colSorting));
	grid.enableMultiline(isMulti); ;grid.init();$add("gridHeaders_"+gridUid, data.header);$add("gridCols_"+gridUid, data.cols);} return grid;}
//Multirow header
function fnNewInitGridMultirowHeader(id, data, skin) {
	if( skin == undefined || skin == "" ){skin = df_dhx_skin;}
	grid = new dhtmlXGridObject(id);
	grid.setImagePath("<c:url value='/cmm/js/dhtmlx/dhtmlxGrid/codebase/imgs/'/>");
	grid.setDateFormat("%Y-%m-%d");
	grid.setSkin(skin);grid.enableAlterCss("even", "uneven");
	grid.enableKeyboardSupport(true);
	if(data!=null) {
		var gridUid = grid.entBox.id;var align = new Array();var colWidth = '';var colSorting = '';var colAlign = '';var colType = '';var colHidden = '';
		for (var ti = 0; ti<data.header.split(",").length; ti++){align[ti]=g_AC;colWidth += ti>0 ? ',80' : '50';colSorting += ti>0 ? ',str' : 'str';colAlign += ti>0 ? ',center' : 'center';colType += ti>0 ? ',ro' : 'ro';colHidden += ti>0 ? ',false' : 'false';}
		grid.setHeader(data.header, null, align);
		if(data.attachHeader1 != undefined) {grid.attachHeader(data.attachHeader1);}
		if(data.attachHeader2 != undefined) {grid.attachHeader(data.attachHeader2);}
		grid.setInitWidths(fnNvl(data.widths,colWidth));
		grid.setColAlign(fnNvl(data.aligns,colAlign));
		grid.setColTypes(fnNvl(data.types,colType));
		grid.setColSorting(fnNvl(data.sorting,colSorting));
		grid.init();
	}
	return grid;
}
function getArguments(sFunction, a) {var ii = sFunction.indexOf('(');var iii = sFunction.indexOf(')');var aArgs = sFunction.substr(ii+1, iii-ii-1).split(',');var sArgs = '';for(var i=0; i<a.length; i++) {var q = ('string' == typeof a[i]) ? '"' : '';sArgs+=((i>0) ? ', ' : '')+(typeof a[i])+' '+aArgs[i]+':'+q+a[i]+q+'';}return '('+sArgs+')';}
function fnLoadPramValue(key, cols, value, noMsg){var result = "";try{if(key==null || key == ""){fnLog("ERROR fnLoadDhtmlxJson() data: " + data);fnLog("ERROR fnLoadDhtmlxJson() callstack : " + showCallStack());}} catch(e) {}ajaxPramLoad("<c:url value='/jsonPram.do'/>", data, false, noMsg);return result;	}
function fnLoadDhtmlxGridJson(grid, key, cols, value, debug, noMsg, totCntrNm, url, callbackFnc, msg2, maxCntNum, lockColNum){
	var msg = "${WM00018}";	
	if(noMsg=="Y"){fnCheckSearch('E');}
	if(!fnCheckSearch('S')){
		grid.clearAll();
		//if(debug){alert(2+":::"+grid.getAllRowIds("|"));}
		var data = "menuId="+key;data += "&cols=" + cols;data += "&" + value;
		try{if(key==null || key == ""){fnLog("ERROR fnLoadDhtmlxJson() data: " + data);fnLog("ERROR fnLoadDhtmlxJson() callstack : " + showCallStack());}} catch(e) {}
		if(url == undefined || url == null || url=="") url = "<c:url value='/jsonDhtmlxList.do'/>";
		if(noMsg == undefined || noMsg == null || noMsg==""){ noMsg="N";}
		if(debug){alert("data:::"+data);}
	 	ajaxGridLoad(url, data, grid, debug, noMsg, totCntrNm,callbackFnc, msg, msg2, maxCntNum, lockColNum);
	 	var gridUid = grid.entBox.id;
		$add("gridCols_"+gridUid, cols);
	}
}

function fnLoadDhtmlxTreeJson(tree, key, cols, value, select, noMsg, callbackFnc) {var msg = "${WM00018}";var data = "menuId="+key;data += "&cols=" + cols;data += "&SelectMenuId=" + select;data += "&" + value;try{if(key==null || key == ""){fnLog("ERROR fnLoadDhtmlxTreeJson() data: " + data);fnLog("ERROR fnLoadDhtmlxTreeJson() callstack : " + showCallStack());}} catch(e) {}if(callbackFnc == undefined || callbackFnc == null){ callbackFnc="";}ajaxTreeLoad("<c:url value='/jsonDhtmlxTreeList.do'/>", data, tree, false, noMsg, msg, callbackFnc);}

function fnNewInitChart(config, container) {
	var chart;
	if(config.view == "PIE"){
		chart = new dhtmlXChart({
			view: "pie",
			container: container,
			value: config.value,
			//value:function(obj) {return "<font color='#fff'>" + obj.value + "</font>";},
	        label: config.label,
			color: config.color,
			pieInnerText: config.value,shadow: 0});
	} else if(config.view == "donut"){
		dhtmlxChart = new dhtmlXChart({
			view: "donut",
			container: container,
			value: config.value,
			legend: {width: 75,align: "right",valign: "middle",template: config.label},
			gradient: 1,shadow: false});
	} else if(config.view == "BAR"){
		chart =  new dhtmlXChart({
			view:"bar",
			container : container,
			value:config.value,
			label:config.value,
			radius:0,
			border:true,
			xAxis:{
				template:config.label
				},
			yAxis:{
				start:0,
				end:100,
				step:10,
				template:function(obj){
					return (obj%20?"":obj);}
			}});
	} else if(config.view == "BAR2"){
		chart =  new dhtmlXChart({
			view:"bar",
			container : container,
			value:config.ttl,
			radius:0,
			border:true,
			color:"#36abee",
			width:15,
			xAxis:{
				title:"변경 오더",
				template:config.label
				},
			yAxis:{
				start:0,
				end:1000,
				step:100,
				template:function(obj){
					return (obj%20?"":obj);}
			},
			legend:{
				values:[{text:"Total",color:"#36abee"},{text:"In Change",color:"#58dccd"},{text:"Completed",color:"#a7ee70"}],
				valign:"middle",
				align:"left",
				width:50,
				layout:"x"
			}
		});
		chart.addSeries({
			value:config.mod,
			color:"#58dccd"
		});
		chart.addSeries({
			value:config.cls,
			color:"#a7ee70"
		});
	} else if(config.view == "BAR3"){
		chart =  new dhtmlXChart({
			view:"bar",
			container : container,
			value:config.value,
			//label:config.value,
			radius:0,
			border:true,
			color:"#ffc000",
			width:6,
			xAxis:{
				template:config.label
				},
			yAxis:{
				start:0,
				end:1000,
				step:100,
				template:function(obj){
					return (obj%20?"":obj);} 
				
			}, 
			legend:{
				values:[{text:"Manual",color:"#ffc000"},{text:"System",color:"#0070C0"}],
				valign:"middle",
				align:"left",
				width:10,
				layout:"x"
			}
		});
		chart.addSeries({
			value:config.value2,
			color:"#0070C0"
		});
	}
	return chart;
}
function fnLoadDhtmlxChartJson(chart, container, key, cols, value, debug, noMsg) {var msg = "${WM00018}";var data = "chartId="+key;data += "&cols=" + cols;data += "&"+value;ajaxChartLoad(chart, "<c:url value='/jsonDhtmlxChart.do'/>", data, container, debug, noMsg, msg);}
function fnLoadDhtmlxChartWithGrid(chart, grid, chartCntr, config, debug, noMsg) {chart.clearAll();integrationChartLoad(chart, grid, chartCntr, config, debug, noMsg);}
function fnLoadDhtmlxChartJson2(chart, container, data){var msg = "${WM00018}";ajaxChartLoad(chart, "<c:url value='/statisticsChart.do'/>", data, container,"","",msg);}

function fnDownExcel(grid, title, headers, key, cols, value, coltype, debug) {var form = document.commandMap;if(form==null) {form = $form();}$add("title", title);$add("headers", headers);$add("cols", cols);$add("key", key);$add("coltype", coltype);form.action = "<c:url value='/excelDown.do?'/>"+value;try {form.submit();} catch(e) {}}
function fnLog(cont) {parent.mainMenu.fnLog(cont);}
function generateDatePicker(){$(this).css({"min-width":"85px", "text-align":"left","padding-left":"5px"});$(this).css({"ime-mode":"disabled"});$(this).attr("size", "10");$(this).attr("maxlength", "10");$(this).click(function() {doClickCalendar("Y", this);});var root = fnGetRootUrl();}
// TODO ::: IE8 대응으로 입시 삭제 
function generateDatePicker(){$(this).css({"min-width":"85px", "text-align":"left","padding-left":"5px"});$(this).css({"ime-mode":"disabled"});$(this).attr("size", "10");$(this).attr("maxlength", "10");$(this).click(function() {doClickCalendar("Y", this);});$(this).blur(function() {closeCalendar();});var root = fnGetRootUrl();}
function generateDatePickerNoClck(){$(this).css({"ime-mode":"disabled"});$(this).attr("size", "10");$(this).attr("maxlength", "10"); $(this).blur(function() {closeCalendar();});var root = fnGetRootUrl();var tag = "<img src='${root}cmm/common/images/icon_crd.gif' name=\"Image100\" width=\"16\" height=\"22\" border=\"0\" align=\"absmiddle\" style='cursor:hand' onclick=\"doClickCalendar('N','"+this.id+"');\" onblur=\"closeCalendar();\">";$(tag).appendTo($(this).parent());}
function setPagingHTML( totCnt, pageNo, scale, pageScale, divTag ){if(totCnt==null||totCnt==""){totCnt = "0";}if(pageNo==null||pageNo==""){pageNo = "1";}if(scale==null||scale==""||scale==undefined){scale = "";}if(pageScale==null||pageScale==""||pageScale==undefined){pageScale = "";}if(divTag==null||divTag==""||divTag==undefined){divTag = "paging";}var pgUrl = "<c:url value='/cmm/js/xbolt/paging.jsp'/>";var pgParam = "?totCnt="+totCnt;pgParam += "&page="+pageNo;pgParam += "&scale="+scale;pgParam += "&pageScale="+pageScale;var loader = dhtmlxAjax.postSync(pgUrl+pgParam);if(document.getElementById(divTag)!=null&&document.getElementById(divTag).length>0){document.getElementById(divTag).innerHTML = loader.xmlDoc.responseText;}}
function setSubTab(menuIndex, avg, tabNm, classNm){var realMenuIndex = menuIndex.split(' '); if(tabNm == null || tabNm == "undefined"){tabNm = "pli";}if(classNm == null || classNm == "undefined"){classNm = "on";}for(var i = 0 ; i < realMenuIndex.length; i++){if(realMenuIndex[i] == avg){$('#'+tabNm+realMenuIndex[i]).addClass(classNm);}else{$('#'+tabNm+realMenuIndex[i]).removeClass(classNm);}}}
function openMaskLayer(){var $mask = $('#mask');if($mask.length == 0){$('body').append("<div id='mask' class='mask' style='display:none; filter:alpha(opacity=20); opacity:0.2; -moz-opacity:0.2;' ></div>");$mask = $('#mask');}var maskHeight = $(document).height();var maskWidth = $(window).width();$mask.css({'width':maskWidth,'height':maskHeight ,'position':'absolute','left':0,'top':0,'z-index':'9000','display':'none' ,'background-color':'black'});$mask.show();}
function closeMaskLayer(){if($("#mask").length >0){$("#mask").hide();}}

function fnSetMenuTreeData(data){if(data == 'undefined' || data == null){data = "";}var result = new Object();result.title = "${title}";result.key = "menu_SQL.menuTreeList";result.header = "TREE_ID, PRE_TREE_ID,TREE_NM";result.cols = "TREE_ID|PRE_TREE_ID|TREE_NM";result.data = data ;return result;}
function fnSetScriptMasterDiv(){var divScpt = new Object();divScpt.treeTop="<div id='schTreeArea'>";divScpt.treeTop=divScpt.treeTop+"<input type='text' class='tree_search' id='schTreeText' style='width:80px;ime-mode:active;' placeholder='Search' value='' text=''/>&nbsp;<a onclick='searchTreeText(\"1\")'><img src='${root}cmm/common/images/btn_icon_search.png'></a> <a onclick='searchTreeText(\"2\")'><img src='${root}cmm/common/images/icon_arrow_left.png'></a> | <a onclick='searchTreeText(\"3\")'><img src='${root}cmm/common/images/icon_arrow_right.png'></a>&nbsp;";divScpt.treeTop = divScpt.treeTop+"<a onclick='fnRefreshTree(null,true)'><img src='${root}cmm/common/images/img_refresh.png'></a>";divScpt.treeTop=divScpt.treeTop+"</div>";divScpt.cntnTop="";return divScpt;}
function fnSetButtonDiv(isViewSave, isViewDel, isViewList){
	if(isViewSave="" || isViewSave == undefined){}else if(isViewSave){$("#viewSave").attr("style", "display:block");}else{$("#viewSave").attr("style", "display:none");}
	if(isViewDel="" || isViewDel == undefined){}else if(isViewDel){$("#viewDel").attr("style", "display:block");}else{$("#viewDel").attr("style", "display:none");}
	if(isViewList="" || isViewList == undefined){}else if(isViewList){$("#viewList").attr("style", "display:block");}else{$("#viewList").attr("style", "display:none");}
}
function fnCheckSearch(isType){
	var schNm = 'chkSearch';
	var sch = $('#'+schNm);
	if(sch.length == 0){
		$('body').append("<input id='"+schNm+"' type='hidden' value=''></input>");
	}
	if(isType == "S"){
		
		if(sch.val()=='true') {
			//alert('조회 중입니다 잠시만 기다려 주십시오.');
			alert("${WM00066}");
			return false;
		}else{
			sch.val("true");
			return false;
		}
		}else if(isType =="E"){
			sch.val("false"); 
			return true;}
	}
function fnCheckIEVer(){var myNav = navigator.userAgent.toLowerCase();return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : -1;}
function fnChangeLangComboStyle(style){$('#nav2 li.top').attr("style",style);$('#nav2 li ul.sub2').attr("style",style);$('#nav2 li ul.sub2 li a').attr("style",style);}
function fnChangeCmpnyComboStyle(style){$('#nav3 li.top').attr("style",style);$('#nav3 li ul.sub3').attr("style",style);$('#nav3 li ul.sub3 li a').attr("style",style);
}
</script>



