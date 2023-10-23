<%@ page import = "java.util.List, java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<c:url value="/" var="root"/>

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
   var grdGridArea;   
   var dateList ="";
	<c:forEach var="date" items="${dateList}" varStatus="status">
		dateList += ",${date}"
	</c:forEach>
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
		};

		$("input.datePicker").each(generateDatePicker); 
	
		$("#excel").click(function(){
			doExcel();
		});
      
		$('.searchList').click(function(){
			doSearchList();
			return false;
		});
     
		gridInit();   
		doSearchList();
	});
	
	function getDateList(){
		var selectDate = $("#selectDate").val();
		var dateList = "";
		for(var i=6; i>=0; i--){
			dateList += "," + dateAddDel(selectDate, -i, 'd');
		}
		return dateList;
	}
	
	function dateAddDel(sDate, nNum, type) {
	    var yy = parseInt(sDate.substr(0, 4), 10);
	    var mm = parseInt(sDate.substr(5, 2), 10);
	    var dd = parseInt(sDate.substr(8), 10);
	    
	    if (type == "d") {
	        d = new Date(yy, mm - 1, dd + nNum);
	    }
	    else if (type == "m") {
	        d = new Date(yy, mm - 1, dd + (nNum * 31));
	    }
	    else if (type == "y") {
	        d = new Date(yy + nNum, mm - 1, dd);
	    }
	 
	    yy = d.getFullYear();
	    mm = d.getMonth() + 1; mm = (mm < 10) ? '0' + mm : mm;
	    dd = d.getDate(); dd = (dd < 10) ? '0' + dd : dd;
	 
	    return '' + yy + '-' +  mm  + '-' + dd;
	}


	function setWindowHeight(){
		var size = window.innerHeight;
    	var height = 0;
		if( size == null || size == undefined){
        	 height = document.body.clientHeight;
      	}else{
        	height=window.innerHeight;
      	}return height;
	}
   
   //===============================================================================
   // BEGIN ::: GRID
	function gridInit(){      
		var d = setGridData();
		grdGridArea = fnNewInitGrid("grdGridArea", d);

		grdGridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		grdGridArea.setIconPath("${root}${HTML_IMG_DIR}/");   
		grdGridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});      
		grdGridArea.enablePaging(true,50,null,"pagingArea",true,"recInfoArea");
		grdGridArea.setPagingSkin("bricks");   
		//grdGridArea.setColumnHidden(0, true);
		//grdGridArea.enableUTFencoding(true);
		grdGridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}

	function setGridData(){
		var projectID;
		var result = new Object();
		result.title = "${title}";
		result.key = "analysis_SQL.getVisitLogStatisticsUser";
		result.header = "${menu.LN00024}, ${menu.LN00072}, ${menu.LN00028}, ${menu.LN00247}"+getDateList()+", ${menu.LN00260}";
		result.cols = "loginID|visitorName|teamName|D6|D5|D4|D3|D2|D1|today|total";
		result.widths = "30,150,150,150,100,100,100,100,100,100,100,100";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,left,left,left,left,left,left,left,left,left,left";
		result.data = "selectDate="+$("#selectDate").val();
		return result;
	}
	
	function doSearchList(){
		gridInit();
	  	var d = setGridData();
		fnLoadDhtmlxGridJson(grdGridArea, d.key, d.cols, d.data);
	}
   
	var delay = 0;
	function doExcel() {
		if(!confirm("Excel download 를 진행 하시겠습니까?")){ return;}
		var start = new Date().getTime();	
		if(start > delay || delay == 0){
			delay = start + 300000; // 1000 -> 1초
			//console.log("start :"+start+", delay :"+delay);
			grdGridArea.toExcel("${root}excelGenerate");
		}else{
			alert("Excel DownLoad 가 진행 중입니다.");
			return;
		}
	} 

   function goBack() {
		var url = "objectReportList.do";
		var data = "s_itemID=${s_itemID}"; 
		var target = "proDiv";
	 	ajaxPage(url, data, target);
	}
   
</script>
<form name="downPrcFrm" id="downPrcFrm" action="" method="post"  onsubmit="return false;">
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}">
<input type="hidden" id="currPage" name="currPage" value="${pageNum}">

<div id="proDiv" style="overflow:auto;">
	<div class="floatL msg" style="width:100%">
		<img src="${root}${HTML_IMG_DIR}/bullet_blue.png" id="subTitle_baisic">&nbsp;&nbsp;Business Process Master List with Rule/To Check/Role </span>
	</div>	
	<div class="countList" >
		<ul>
			<li class="floatL count">Total  <span id="TOT_CNT"></span></li>
			<li class="floatL mgL40">
			   <input type="text" id="selectDate" name="selectDate" value="${thisYmd}"	class="text datePicker" size="10" style="width:80%;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
	
			 </li>
			<li class="floatL mgL10">
				<input type="image" class="image searchList" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="search" />
			 </li>
			<li class="floatR">
			   <span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
			 </li>
		</ul>
	</div>
	<div id="gridDiv" style="width:100%;" class="clear">
		<div id="grdGridArea" style="width:100%"></div>
	</div>
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL5"></div></div>
</div>
</form>
<iframe name="blankFrame" id="blankFrame" style="width:0px;height:0px; display:none;"></iframe>