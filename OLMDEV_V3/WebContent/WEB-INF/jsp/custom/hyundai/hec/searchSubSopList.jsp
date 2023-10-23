﻿﻿<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
 
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/js/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_skyblue.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/js/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css'/>">

<script src="${root}cmm/js/jquery/jquery-1.9.1.min.js" type="text/javascript"></script> 
<script src="${root}cmm/js/xbolt/jquery.sumoselect.js" type="text/javascript"></script> 
<link rel="stylesheet" type="text/css" href="${root}cmm/common/css/sumoselect.css"/>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00041" var="WM00041" arguments="${menu.LN00021}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00119" var="WM00119" arguments="1000"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00158" var="WM00158"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00159" var="WM00159"/>

<style>
.DimensionTd .SumoSelect{
	float:left;
	margin-right:7px;
}
</style>

<!-- 2. Script -->
<script type="text/javascript">
	var p_gridArea;				//그리드 전역변수
	
	$(document).ready(function() {
		$("input.datePicker").each(generateDatePicker);
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 208)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			if($("#frame_sh").hasClass("frame_show")) {
				$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 208)+"px;");
			} else {
				$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 408)+"px;");
			}
		};  
		
		$("#detailOwnerTeam").val("${defOwnerTeamID}");
		$("#detailAuthor").val("${defAuthorID}");
		$("#AttrCode").val("${defAttrTypeCode}");
		
		  $("#excel").click(function(){p_gridArea.toExcel("${root}excelGenerate");doExcel();});
		  $('#btnSearch').click(function(){
		   $("#currPage").val("");
		   doSearchList();
		   return false;
		  });

		  $("#frame_sh").mouseover(function(){
		   var tmp = $(this).attr("src");
		   if($(this).hasClass("frame_show")) {
		    $(this).attr("src",tmp.replace("btn_frame_show","btn_frame_hide"));
		   }
		   else {
		    $(this).attr("src",tmp.replace("btn_frame_hide","btn_frame_show"));
		   }
		  });

		  $("#frame_sh").mouseout(function(){
		   var tmp = $(this).attr("src");
		   if($(this).hasClass("frame_show")) {
		    $(this).attr("src",tmp.replace("btn_frame_hide","btn_frame_show"));
		   }
		   else {
		    $(this).attr("src",tmp.replace("btn_frame_show","btn_frame_hide"));
		   }
		  });
		  
		   $('#AttrCode').SumoSelect();
		   
		  changeClassCode("CL05003", 'OJ00005');
		  
		  $('#constraint').change(function(){changeConstraint($(this).val(), "");});
		  
		  $('#searchValue').keypress(function(onkey){
		   if(onkey.keyCode == 13){
		    $("#searchValueAT00001").val($(this).val());
		    doSearchList();
		    return false
		   ;}
		  });  
		  
		  gridInit();

		$("#loading").fadeIn(100);
		checkLoadingBar();

	});	
	
	function checkLoadingBar() {
		if($("#TOT_CNT").html() != "") {
			$("#loading").fadeOut(100);
		}		
		else {
			$("#loading").fadeIn(100);
			setTimeout(function() { checkLoadingBar(); },500);
			
		}
	}	
	
	function checkAttrCode(value,text,isNew) {
		var ari = $("#attrIndex").val();
		var bf = $("#isSelect"+value).val();
		
		if($("#option"+value).hasClass("selected") && isNew != "NEW") {	
			
			if(ari*1 > 0)
				$("#attrIndex").val(ari*1 - 1);
			
			$("."+value).remove();

			if(bf != "") {
				$("#asDiv"+bf).empty();
				$("#beforeCode").val(bf);
			}
			$("#AttrCode")[0].sumo.attrOptClick("option"+value);
		}
		else if(ari*1 < 4){
			changeAttrCode(value);
			checkAttrDiv(value,text,ari);
			$("#AttrCode")[0].sumo.attrOptClick("option"+value);
			$("#attrIndex").val(ari*1+1);
		}
		else {
			//문구 추가 필요
			alert("최대 4개 선택 가능 합니다.");
		}
	}
	
	function checkAttrDiv(divClassName,text,ari){
		var html = "";
		var bfAttr = $("#beforeCode").val();

		html += '<div class="'+divClassName+'" style="margin-top:10px;">';

		html += "<div style=\"text-align: right; line-height: 25px; float:left;\" ><b>"+text+"</b></div>";	
			
		html += "<select id=\"constraint"+divClassName+"\" name=\"constraint[]\" class=\"SlectBox\" style=\"width:150px;margin-left:20px;\" onChange=\"changeConstraint($(this).val(),'"+divClassName+"')\" >";
		html += "<option value=\"\">포함(또는 같음)</option>";
		html += "<option value=\"3\">포함하지 않음(또는 다름)</option>";
		html += "</select>&nbsp;";
		html += "<input type=\"text\" id=\"searchValue"+divClassName+"\" value=\"\" class=\"stext\" style=\"width:20%;height:23px;margin-left:10px;\">";
		html += "<select id=\"AttrLov"+divClassName+"\" name=\"AttrLov[]\" style=\"display:none;width:120px;margin-left:20px;\" multiple=\"multiple\">";
		html += "<option value=\"\">Select</option>	";
		html += "</select><input type=\"hidden\" id=\"isLov"+divClassName+"\" value=\"\">";
		html += "<input type=\"hidden\" id=\"isSelect"+divClassName+"\" value=\""+bfAttr+"\">";
		html += '<div id="asDiv'+divClassName+'" style="height: 25px; margin-left: 10px; display: inline;"></div>';

		if(ari > 0) {		
			var html2 = "";
				html2 += '<select id="selectOption'+divClassName+'" name="selectOption'+divClassName+'" style="width:80px; " >';
				html2 += "<option value=\"AND\" selected=\"selected\">AND</option>";
				html2 += "<option value=\"OR\">OR</option>	";
				html2 += '</select>';
			$("#asDiv"+bfAttr).append(html2);
			$("#selectOption"+divClassName).SumoSelect({csvDispCount: 3});
		}
		
		html += "</div>";
		
		
		if($("div").hasClass(divClassName)) {
			$("."+divClassName).remove();
		}
		else {
			$("#appendDiv").append(html);
			$("#constraint"+divClassName).SumoSelect({csvDispCount: 3});
		}
		$("#beforeCode").val(divClassName);
	}
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	//===============================================================================
	// BEGIN ::: GRID
	function gridInit(){		
		var d = setGridData();		
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR_ITEM}/");
		
		p_gridArea.setColumnHidden(2, true);
		p_gridArea.setColumnHidden(4, true);
		p_gridArea.setColumnHidden(13, true); // Report
		p_gridArea.setColumnHidden(14, true);
 		p_gridArea.setColumnHidden(15, true);
 		p_gridArea.setColumnHidden(16, true);
		p_gridArea.setColumnHidden(9, true);
		
		fnSetColType(p_gridArea, 1, "ch");
		fnSetColType(p_gridArea, 12, "img");
		
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});
		//START - PAGING
		p_gridArea.enablePaging(true, 25, null, "pagingArea", true, "recinfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
		//END - PAGING
	}	
	function setGridData(){
		var result = new Object();
		result.title = "${title}";
		result.key = "custom_SQL.zhec_getSearchMultiList";
		result.header = "${menu.LN00024},#master_checkbox,${menu.LN00021},SOP No.,${menu.LN00247}, ${menu.LN00353}, ${menu.LN00354}, SOP명, ${menu.LN00018}, ${menu.LN01007}, ${menu.LN00356}, ${menu.LN00357}, File, ItemID, ClassCode, SeqLevel, authorID";
		result.cols = "CHK|ItemTypeName|Identifier|TeamName|L1Name|L2Name|ItemName|OwnerTeamName|Name|CSVersion|ValidFrom|FileIcon|ItemID|ClassCode|SeqLevel|AuthorID";
		result.widths = "30,30,60,120,150,120,150,*,120,80,80,60,60,0,0,0,0"; // item 검색
		result.sorting = "int,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,center,center,center,center,left,center,center,center,center,center,center,center,center,center";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&s_itemID=${s_itemID}&ownerType=${ownerType}&showTOJ=${showTOJ}"
					+ "&defaultLang=" + $("#defaultLang").val()
					+ "&isComLang=" + $("#isComLang").val()
				//	+ "&childItems=${childItems}"
		 			+ "&pageNum=" + $("#currPage").val();
		
		/* [기본정보] 조건 선택, 입력값 */
	    result.data = result.data + "&ClassCode=CL05003"+ setAllCondition();
		
		if($("#AttrCode").val() != '' & $("#AttrCode").val() != null){
			var attrArray = new Array();
			$("#AttrCode :selected").each(function(i, el){ 
				var valueArray = new Array();
				var aval = $(el).val();	
				var lovCode = "";
				var searchValue = "";
				var AttrCodeEscape = "";
				var constraint = "";
				var selectOption = "";
				
				if(i*1 > 0) {
					selectOption = $("#selectOption"+aval).val();
				}
				else if(i == 0)
					selectOption = "AND";
				

				/* [속성] 조건 선택, 입력값 */
				if ($("#constraint"+aval).val() == "" || $("#constraint"+aval).val() == "3") {
					if ($("#AttrLov"+aval).val() != "" || $("#searchValue"+aval).val() != "") {
						if ("Y" == $("#isLov"+aval).val()) {
							lovCode = $("#AttrLov"+aval).val() + "";
							lovCode = lovCode.replace(/,/gi,"*");
							//result.data = result.data+ "&lovCode=" + $("#AttrLov"+aval).val();
						} else {
							$("#isSpecial").val("");
							searchValue = setSpecialChar($("#searchValue"+aval).val().replace(",","comma"));
							AttrCodeEscape = $("#isSpecial").val();
							//result.data = result.data+ "&searchValue=" + setSpecialChar($("#searchValue"+aval).val());
							//result.data = result.data+ "&AttrCodeEscape=" + $("#isSpecial"+aval).val();
						}
					}
					if($("#constraint"+aval).val() == "3")
						constraint = $("#constraint"+aval).val();
						//result.data = result.data+ "&constraint"+aval+"=" + $("#constraint"+aval).val();
					
				} else {
					constraint = $("#constraint"+aval).val();
					//result.data = result.data+ "&constraint"+aval+"=" + $("#constraint"+aval).val();
				}
				
				valueArray.push($(el).val());
				valueArray.push(lovCode);
				valueArray.push(searchValue);
				valueArray.push(AttrCodeEscape);
				valueArray.push(constraint);
				valueArray.push(selectOption);
				
				attrArray.push(valueArray);
			});
			result.data = result.data+ "&AttrCodeOLM_MULTI_VALUE=" + attrArray;
			
		}	

		return result;
	}
	
	// [기본정보] 모든 조건 검색 입력
	function setAllCondition() {
	  var condition = "";
	  
	  if ($("#detailID").val() != "" ) { // Identifier 
	   condition = condition+ "&AttrCodeBase2=Identifier";
	   $("#isSpecial").val("");
	   condition = condition+ "&baseCondition2=" + setSpecialChar($("#detailID").val());
	   condition = condition+ "&baseCon2Escape=" + $("#isSpecial").val();
	  }
	
	  if ($("#detailOwnerTeam").val() != "" ) { // 관리조직
	   $("#isSpecial").val("");
	   condition = condition+ "&OwnerTeam=" + setSpecialChar($("#detailOwnerTeam").val());
	   condition = condition+ "&ownerTeamEscape=" + $("#isSpecial").val();
	  }
	  if ($("#detailAuthor").val() != "" ) { // 담당자
	   $("#isSpecial").val("");
	   condition = condition+ "&Name=" + setSpecialChar($("#detailAuthor").val());
	   condition = condition+ "&nameEscape=" + $("#isSpecial").val();
	  }
	  if ($("#SC_STR_DT2").val() != "" && $("#SC_END_DT2").val() != "" ) { // 수정일
	   condition = condition+ "&LastUpdated=Y";
	   condition = condition+ "&scStartDt2=" + $("#SC_STR_DT2").val();
	   condition = condition+ "&scEndDt2=" + $("#SC_END_DT2").val();
	  }
	
	  return condition;
	 }
	
	function gridOnRowSelect(id, ind){
		if(ind != 1) {
			
			if(ind != 12) {
				doDetail(p_gridArea.cells(id, 13).getValue());			
			}
			else if(ind != 1 && ind == 12) {
				var fileCheck = p_gridArea.cells(id,12).getValue();
	
				if(fileCheck.indexOf("blank.gif") < 1) {
					var url = "selectFilePop.do";
					var data = "?hideBlocked=Y&s_itemID="+p_gridArea.cells(id, 13).getValue(); 
				   
				    var w = "400";
					var h = "350";
				    window.open(url+data, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");	
				}
			}
		}
	}
	// END ::: GRID	
	//===============================================================================
		
	// [검색 조건] 특수 문자 처리
	function setSpecialChar(avg) {
		var result = avg;
		var strArray =  result.split("[");
		
		if (strArray.length > 1) {
			result = result.split("[").join("[[]");
		}
		
		strArray =  result.split("%");
		if (strArray.length > 1) {
			result = result.split("%").join("!%");
			$("#isSpecial").val("Y");
		}
		
		strArray =  result.split("_");
		if (strArray.length > 1) {
			result = result.split("_").join("!_");
			$("#isSpecial").val("Y");
		}
		
		strArray =  result.split("@");
		if (strArray.length > 1) {
			result = result.split("@").join("!@");
			$("#isSpecial").val("Y");
		}
		
		return result;
	}

	function setSubFrame(avg, avg2){
		$("#"+avg2).attr('style', 'display: none');
		$("#"+avg).removeAttr('style', 'display: none');
		
		if(avg == 'addNewItem'){
			setSubFrame('saveOrg','editOrg');
		}else if(avg == 'OrganizationInfo'){
			setSubFrame('editOrg','saveOrg');
		}
	}
	function doSearchList(){
		var d = setGridData();
		//fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
		/* 건수 제한 메세지 표시 */
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data, false, "", "", "", "", "${WM00119}", 1000);
	}
	
	// [Report] Click
	function goReportList(avg) {
		/*
		var url = "objectReportList.do";
		var target = "processList";
		var data = "s_itemID="+avg+"&kbn=searchList&pageNum=" + $("#currPage").val()
		+ "&ItemTypeCode="		+ $('#ItemTypeCode').val()
		+ "&ClassCode="     	+ $("#ClassCode").val()
		+ "&AttrCode="     	+ $("#AttrCode").val()
		+ "&searchValue="     	+ $("#searchValue").val()
		; 
	 	ajaxPage(url, data, target);*/
	 	
	 	var url = "objectReportList.do?s_itemID="+avg;
		var w = 1000;
		var h = 800;
		openPopup(url,w,h,avg);
	}

	function doDetail(avg){
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}"
				+"&id="+avg+"&scrnType=pop&screenMode=pop&accMode=${accMode}";

		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,avg);
	}
	
	// [속성 option] 설정
	// 항목계층 SelectBox 값 선택시  속성 SelectBox값 변경
	function changeClassCode(avg1, avg2, avg3){
		$("#attrIndex").val("0");
		$("#appendDiv").empty();
		$("#displayLabel").empty();
		var url    = "getSearchSelectOption.do"; // 요청이 날라가는 주소
		var data   = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&sqlID=search_SQL.attrBySearch&s_itemID="+avg2+"&s_itemID2="+avg1; //파라미터들
		var target = "AttrCode";            // selectBox id
		var defaultValue = "${defClassCode}";              // 초기에 세팅되고자 하는 값
		var isAll  = "no";    // "select" 일 경우 선택, "true" 일 경우 전체로 표시
		ajaxMultiSelect(url, data, target, defaultValue, isAll);
		setTimeout(appendOption,1000);
	}
	
	function appendOption(){
		 var optionName = '${menu.LN00028}';
		 $("#AttrCode").prepend("<option value='AT00001'>"+optionName+"</option>");
		 
		 $('#AttrCode')[0].sumo.reload();
		 if("${defAttrTypeCode}" != ""){
		 	checkAttrCode("${defAttrTypeCode}","${defAttrTypeName}", "NEW");
		 }else{
		 	checkAttrCode("AT00001",optionName, "NEW");
		 }
		 doSearchList();
	}

	function setAttrLovMulti(atrCode){
		
		 $('#AttrLov'+atrCode).SumoSelect({ csvDispCount: 3 });
	}
	
	// [LOV option] 설정
	// 화면에서 선택된 속성의 DataType이 Lov일때, Lov selectList를 화면에 표시
	function changeAttrCode(avg){
		var url = "getAttrLov.do";		
		var data = "attrCode="+avg;
		var target="blankFrame";
		ajaxPage(url, data, target);
	}
	
	function changeAttrCode2(attrCode, dataType, isComLang) {
		var languageID = "${sessionScope.loginInfo.sessionCurrLangType}";
		// isComLang == 1 이면, 속성 검색 의 언어 조건을 defaultLang으로 설정 해줌
		if (isComLang == '1') {
			languageID = "${defaultLang}";
			$("#isComLang").val("Y");
		} else {
			$("#isComLang").val("");
		}

		if (dataType == "LOV" || dataType == "MLOV") {
			$("#isLov"+attrCode).val("Y");
			$("#searchValue"+attrCode).attr('style', 'display:none;width: 20%; height: 23px; margin-left: 10px;');	
			
			var url    = "getAttrLovSelectOption.do"; // 요청이 날라가는 주소
			var data   = "languageID="+languageID+"&attrCode="+attrCode; //파라미터들
			var target = "AttrLov"+attrCode;            // selectBox id
			var defaultValue = "";              // 초기에 세팅되고자 하는 값
			var isAll  = "no";    // "select" 일 경우 선택, "true" 일 경우 전체로 표시
			ajaxMultiSelect(url, data, target, defaultValue, isAll);
			setTimeout("setAttrLovMulti('"+attrCode+"')",500);
		} else {
			$("#isLov"+attrCode).val("");
			$("#searchValue"+attrCode).attr('style', 'width: 20%; height: 23px; margin-left: 10px; display: inline; ');
			$("#AttrLov"+attrCode).attr('style', 'display:none;width:120px;margin-left:30px;');	
		}
		
		
	}

	function setAttrLovMulti(atrCode){
		 $('#AttrLov'+atrCode).SumoSelect();
		 $('#ssAttrLov'+atrCode).attr("style","width:235px;margin-left:30px;");
	}
	
	
	// [dimValue option] 설정
	function changeDimValue(avg, avg2){
		var url    = "getDimValueSelectOption.do"; // 요청이 날라가는 주소
		var data   = "dimTypeId="+avg+"&searchYN=Y"; //파라미터들
		var target = "dimValueId";            // selectBox id
		var defaultValue = avg2;              // 초기에 세팅되고자 하는 값
		var isAll  = "no";    // "select" 일 경우 선택, "true" 일 경우 전체로 표시
		
		ajaxMultiSelect(url, data, target, defaultValue, isAll);
		
		setTimeout(function() {appendDimOption(avg2);}, 1000);
	}
	
	function appendDimOption(avg2){
		$("#dimValueId")[0].sumo.reload();
		if(avg2 != ""){
			$("#dimValueId")[0].sumo.selectItem(avg2);
		}
	}
	
	// [속성 검색 제약] 설정
	function changeConstraint(avg, avg2) {
		if (avg == "" || avg == "3") {
			if ($("#isLov"+avg2).val() == "Y") {
				$("#searchValue"+avg2).attr('style', 'display:none;width: 20%; height: 23px; margin-left: 10px; ');
				$("#ssAttrLov"+avg2).attr('style', 'width:235px;margin-left:30px;');
			} else {
				$("#searchValue"+avg2).attr('style', 'display:inline;width: 20%; height: 23px; margin-left: 10px;');
				$("#ssAttrLov"+avg2).attr('style', 'display:none;width:120px;margin-left:30px;');
			}
		} else {
			$("#searchValue"+avg2).val("");
			$("#searchValue"+avg2).attr('style', 'display:none;width: 20%; height: 23px; margin-left: 10px;');
			$("#ssAttrLov"+avg2).attr('style', 'display:none;width:120px;margin-left:30px;');
		}
	}
	
	
	// [Clear] click
	function clearSearchCon() {
		// 계층
		
		$("#appendDiv").empty();
		//$("#classCode").val("").attr("selected", "selected");
		// 기본정보 상세
		$("#detailID").val('');
		$("#detailOwnerTeam").val('');
		$("#detailAuthor").val('');
		$("#SC_STR_DT1").val('');
		$("#SC_END_DT1").val('');
		$("#SC_STR_DT2").val('');
		$("#SC_END_DT2").val('');
		$("#searchValue").val('');


		$("#attrIndex").val("0");
		changeClassCode("", '${ItemTypeCode}');
		// Dimension

		$("#dimTypeId")[0].sumo.selectItem(0);
		fnResetSelectBox("dimValueId[]","");
		$('#dimValueId')[0].sumo.reload();
		// 상태
		
		$("#Status")[0].sumo.selectItem(0);
		$("#detailClassCode")[0].sumo.selectItem(0);
	
		$("#detailCompanyId")[0].sumo.selectItem(0);
	}
	
	/**  
	 * [Owner][Attribute] 버튼 이벤트
	 */
	function editCheckedAllItems(avg){ 
		if(p_gridArea.getCheckedRows(1).length == 0){
			alert("${WM00023}");
			return;
		}

		var checkedRows = p_gridArea.getCheckedRows(1).split(",");	
		var items = "";
		var classCodes = "";
		var nowClassCode = "";
		var SeqLevel = "";
		var AuthorID = "";
		
		for(var i = 0 ; i < checkedRows.length; i++ ){			
			// 이동 할 ITEMID의 문자열을 셋팅
			if (items == "") {
				items = p_gridArea.cells(checkedRows[i], 13).getValue();
				classCodes = p_gridArea.cells(checkedRows[i], 14).getValue();
				nowClassCode = p_gridArea.cells(checkedRows[i], 14).getValue();
				SeqLevel = p_gridArea.cells(checkedRows[i], 15).getValue();
				AuthorID = p_gridArea.cells(checkedRows[i], 16).getValue();
			} else {
				items = items + "," + p_gridArea.cells(checkedRows[i], 13).getValue();
				SeqLevel = SeqLevel + "," + p_gridArea.cells(checkedRows[i], 15).getValue();
				AuthorID = AuthorID + "," + p_gridArea.cells(checkedRows[i], 16).getValue();
				if (nowClassCode != p_gridArea.cells(checkedRows[i], 14).getValue()) {
					classCodes = classCodes + "," + p_gridArea.cells(checkedRows[i], 14).getValue();
					nowClassCode = p_gridArea.cells(checkedRows[i], 14).getValue();
				}
			}
		}

		if (items != "") {
			if (avg == "Attribute") {
				var url = "selectAttributePop.do?";
				var data = "classCodes="+classCodes+"&items="+items; 
			    var option = "dialogWidth:400px; dialogHeight:250px;";			
			    //window.showModalDialog(url + data , self, option);
			   
			    var w = "400";
				var h = "250";
				document.getElementById("items").value = items;
				document.getElementById("classCodes").value = classCodes;
			    window.open(url+data, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes"); 
			} else if(avg == "Owner") {
				var url = "selectOwnerPop.do?";
				var data = "items="+items; 
			    var option = "dialogWidth:450px; dialogHeight:370px;";
			    var w = "400";
				var h = "370";
			    window.open(url + data , "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
			} else if(avg == "Subscribe") {
				if(confirm("Do you really subscibe this item?")){
					var url = "saveRoleAssignment.do";
					var data = "itemID="+items+"&assignmentType=SUBSCR&accessRight=R&assigned=1&memberID=${sessionScope.loginInfo.sessionUserId}&seq=";
					var target = "blankFrame";
					ajaxPage(url, data, target);
				}
			} else if(avg == "Report"){
				var item = "";
				var items = items.split(",");
				var SeqLevel = SeqLevel.split(",");
				var AuthorID = AuthorID.split(",");
				for(var j=0; j<items.length; j++){
					if(SeqLevel[j] > 1){
						alert("보안등급으로 인해 리포트를 실행할 수 없습니다.");
					} else if (SeqLevel[j] == 1){
						if(AuthorID[j] == "${sessionScope.loginInfo.sessionUserId}") {
							if (item == "") item += items[j];
							else item += ","+items[j];
						}
					} else {
						if (item == "") item += items[j];
						else item += ","+items[j];
					}
				}
				$("#s_itemIDs").val(item);
				if(item != ""){
					var url = "subItemInfoReport.do?";
					var data = "itemInfoRptUrl=${itemInfoRptUrl}";
					window.open(url+data, "", "width=420, height=200,top=100,left=100,toolbar=no,status=no,resizable=yes");
					return false;
				}
			}
		}
		 
	}
	
	function subItemInfoRpt(URL, classCodeList, outputType) {
		$('#fileDownLoading').removeClass('file_down_off');
		$('#fileDownLoading').addClass('file_down_on');
		timer = setTimeout(function() {checkDocDownCom();}, 1000);
		var url = "subItemInfoReportEXE.do";	
		$('#URL').val(URL);
		$('#outputType').val(outputType);
		ajaxSubmitNoAdd(document.processList, url, "blankFrame");
	}
	
	function checkDocDownCom(){
		$.ajax({
			url: "checkDocDownComplete.do",
			type: 'post',
			data: "",
			error: function(xhr, status, error) { 
			},
			success: function(data){
				data = data.replace("<script>","").replace(";<\/script>","");
			
				if(data == "Y") {
					afterWordReport();
					clearTimeout(timer);
				}
				else {
					clearTimeout(timer);				
					timer = setTimeout(function() {checkDocDownCom();}, 1000);
				}
			}
		});	
	}
	
	function urlReload(){
		gridInit();
		doSearchList();
	}
	
	// [back] click
	function goBack() {
		var url = "subItemList.do";
		var data = "s_itemID=${s_itemID}&option=${option}&pop=${pop}";
		var target = "actFrame";
		ajaxPage(url, data, target);
	}

	function fnResetSelectBox(objName,defaultValue)
	{
		$("select[name='"+ objName +"'] option").not("[value='"+defaultValue+"']").remove(); 
	}

	function showMultiSearchDiv() {
		var avg = $("#multiSearch").val();

		if(avg == "N") {
			$("#search").hide();
			$("#mSearch").show();
			$("#buttonGroup").show();
			$("#multiSearch").val("Y");
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 408)+"px;");
        }
		else {
			$("#search").show();
			$("#mSearch").hide();
			$("#buttonGroup").hide();
			$("#multiSearch").val("N")
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 208)+"px;");
		}
	}
	
	function fnHideSearch() {
		var tempSrc = $("#frame_sh").attr("src");
		var avg = $("#multiSearch").val();

		if($("#frame_sh").hasClass("frame_show")) {
			if(avg == "N") {
				$("#search").hide();
			}
			else {
				$("#mSearch").hide();
				$("#buttonGroup").hide();
			}

			$("#frame_sh").attr("class","frame_hide");
			$("#frame_sh").attr("alt","${WM00159}");
			$("#frame_sh").attr("src",tempSrc.replace("btn_frame_show","btn_frame_hide"));

			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 108)+"px;");
		} else {
			if(avg == "N") {
				$("#search").show();
			}
			else {
				$("#mSearch").show();
				$("#buttonGroup").show();
			}
			$("#frame_sh").attr("class","frame_show");
			$("#frame_sh").attr("alt","${WM00158}");
			$("#frame_sh").attr("src",tempSrc.replace("btn_frame_hide","btn_frame_show"));
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 208)+"px;");
		}
	}

	function doNameSearchList() {
		$("#searchValueAT00001").val($("#searchValue").val());
		doSearchList();
	}
	
	function fnItemMenuReload(){
		urlReload();
	}
	
	function fnChangeMenu(menuID,menuName) {
		if(menuID == "management"){
			parent.fnGetMenuUrl("${s_itemID}", "Y");
		}
	}
</script>

<div class="pdL10 pdR10">
<div id="fileDownLoading" class="file_down_off">
	<img src="${root}${HTML_IMG_DIR}/img_circle.gif"/>
</div>
	<form name="processList" id="processList" action="#" method="post"  onsubmit="return false;">
	<input type="hidden" id="searchKey" name="searchKey" value="Name">
	<input type="hidden" id="currPage" name="currPage" value="${currPage}"></input>
	<input type="hidden" id="isLov" name="isLov" value="">
	<input type="hidden" id="defaultLang" name="defaultLang" value="${defaultLang}">
	<input type="hidden" id="isComLang" name="isComLang" value="">
	<input type="hidden" id="isSpecial" name="isSpecial" value="">
	
	<input type="hidden" id="items" name="items" >
	<input type="hidden" id="classCodes" name="classCodes" >
	<input type="hidden" id="attrIndex" value="0">
	<input type="hidden" id="beforeCode" value="">
	<input type="hidden" id="multiSearch" value="N">
	
	<input type="hidden" id="s_itemIDs" name="s_itemIDs" value="" />
	<input type="hidden" id="accMode" name="accMode" value="${accMode}" />
	<input type="hidden" id="URL" name="URL" value="" />
	<input type="hidden" id="outputType" name="outputType" value="" />
	
	<div class="cop_hdtitle" style="border-bottom:1px solid #ccc; padding: 6px 0 6px 0; ">
		<h3 style="display: inline-block"><img src="${root}${HTML_IMG_DIR}/icon_search_title.png">&nbsp;&nbsp;${itemTypeName} - ${selectedItemPath}</h3>
		<c:if test="${sessionScope.loginInfo.sessionAuthLev == 1}" >
			<span class="btn_pack small icon mgR25" style="float: right;"><input value="Standard Menu" type="button" style="padding-left: 2px;" onclick="fnChangeMenu('management','Management');"></span>
		</c:if>
	</div>

	<div id="search" align="center" style="margin-top: 20px;">
		<input type="text" id="searchValue" name="searchValue" value="${searchValue}" class="text" style="width:30%;ime-mode:active;">
		<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="doNameSearchList()" value="Search" style="cursor:pointer;">
		<input type="image" class="image" onclick="showMultiSearchDiv();" src="${root}${HTML_IMG_DIR}/btn_search_plus.png" value="Conditional Search" style="cursor:pointer;">
	</div>
	<div align="center">
		<table style="table-layout:fixed;display:none;" border="0" cellpadding="0" cellspacing="0" class="tbl_search"  id="mSearch">
		   <colgroup>
		       <col width="5%">
		       <col width="8%">
		       <col width="5%">
		       <col width="8%">
		       <col width="5%">
		       <col width="8%">
		       <col width="5%">
		       <col width="16%">
		      </colgroup>
		      <!-- 계층, ID, 상태 -->
		      <tr>
		      <th  class="viewtop">ID</th>
		      <td  class="viewtop"><input type="text" id="detailID" name="detailID" value="" class="stext"></td>
		      <th  class="viewtop">${menu.LN00018}</th>
		    <td  class="viewtop"><input type="text" id="detailOwnerTeam" name="detailOwnerTeam" value="" class="stext"></td>
		    <th  class="viewtop">${menu.LN00004}</th>
		    <td  class="viewtop"><input type="text" id="detailAuthor" name="detailAuthor" value="" class="stext"></td> 
		     
		       <th class="viewtop">${menu.LN00070}</th> 
		       <td class="viewtop last">
		        <font><input type="text" id="SC_STR_DT2" name="SC_STR_DT2" value="" class="input_off datePicker stext" size="8"
		      style="width: 42%;" onchange="this.value = makeDateType(this.value);" maxlength="10">
		     </font>
		      ~
		     <font><input type="text" id=SC_END_DT2 name="SC_END_DT2" value="" class="input_off datePicker stext" size="8"
		      style="width: 42%;" onchange="this.value = makeDateType(this.value);" maxlength="10">
		     </font>
		       </td> 
		      </tr>
		      
		      <!-- 속성, Dimension -->
		      <tr>
		       <!-- 속성 -->
		       <th>${menu.LN00031}</th>
		       <td colspan="7" class="alignL">
		     <select id="AttrCode" Name="AttrCode[]" multiple="multiple" class="SlectBox" >
		     </select>
		     
		     <div id="appendDiv"></div>
		    </td>
		      </tr>
		  </table>
	
		<li id="buttonGroup" class="floatC mgR20 mgT5" style="display:none;">
			<input id="btnSearch" type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="Search" style="cursor:pointer;">
			&nbsp;<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_search_clean.png" value="Clear" style="cursor:pointer;" onclick="clearSearchCon();">
			<input type="image" class="image" onclick="showMultiSearchDiv();" src="${root}${HTML_IMG_DIR}/btn_search_plus.png" value="Advanced Search" style="cursor:pointer;">
		</li>
		
	   <div class="countList pdT10">
	        <li class="count"><input type="image" id="frame_sh" class="frame_show" alt="${WM00158}" src="${root}${HTML_IMG_DIR}/btn_frame_show.png" value="Clear" style="cursor:pointer;width:20px;height:15px;margin-right:5px;" onclick="fnHideSearch();">Total  <span id="TOT_CNT"></span></li>
	        <li class="floatR"> 
	        <c:if test="${pop != 'pop'}">
				<c:if test="${sessionScope.loginInfo.sessionLogintype == 'editor'}">
	<%-- 			<c:if test="${myItem == 'Y'}"> --%>
	<!-- 				&nbsp;<span class="btn_pack small icon" style="display:inline-block;"><span class="gov"></span><input value="Gov" type="submit" onclick="editCheckedAllItems('Owner');"></span> -->
	<!-- 				&nbsp;<span class="btn_pack small icon" style="display:inline-block;"><span class="edit"></span><input value="Attribute" type="submit" onclick="editCheckedAllItems('Attribute');"></span> -->
	<%-- 			</c:if> --%>
				</c:if>
				</c:if>
				&nbsp;<span class="btn_pack small icon" style="display:inline-block;"><span class="subscribe"></span><input value="Book mark" type="button" onclick="editCheckedAllItems('Subscribe');"></span>
<c:if test="${selectedItemPath ne '지원/인사'}">
				&nbsp;<span class="btn_pack small icon" style="display:inline-block;"><span class="report"></span><input value="Report" type="submit" onclick="editCheckedAllItems('Report');"></span>
</c:if>
				&nbsp;<span class="btn_pack small icon" style="display:inline-block;"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
				&nbsp;&nbsp;&nbsp;&nbsp;
	        </li>
	   </div>
	</div>		
	</form>
	
	<div id="gridDiv" class="mgB10 clear" align="center">
		<div id="grdGridArea" style="width:100%"></div>
	</div>
	<!-- START :: PAGING -->
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
	<!-- END :: PAGING -->
	
	<div class="schContainer" id="schContainer">
		<iframe name="blankFrame" id="blankFrame" src="about:blank" style="display:none" frameborder="0" scrolling='no'></iframe>
	</div>
	</div>	
