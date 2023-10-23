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

   $(document).ready(function(){   
      // 초기 표시 화면 크기 조정 
      $("#grdGridArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
      // 화면 크기 조정
      window.onresize = function() {
         $("#grdGridArea").attr("style","height:"+(setWindowHeight() - 180)+"px;");
      };
      
     $("#excel").click(function(){
    	doExcel();
	 });
      
     $("#Search").click(function(){
    	doSearchList();
		return false;
	 });
     
      gridInit();   
      doSearchList();
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
      result.key = "custom_SQL.zKTNG_getAllProcessList";
               
/*
      result.header = "${menu.LN00024}, L1, Path, ID, Level, 명칭,Owner(IL),Owner(IT),Owner(AL),Owner(컨설턴트),Input,Output,관련조직,수행주체(Role),Business Rule,시스템 요구사항,Application Type,모듈/APP,Fiori ID,T-Code,Fit/GAP,GAP ID,GAP 유형,변화영향도 수준,주요 변화 항목(신규업무),주요 변화 항목(업무 수행 절차 변경),주요 변화 항목(업무R&R변경),주요 변화 항목(업무 수행 복잡도 증가),주요 변화 항목(활용 시스템 난이도 증가)";
      result.cols = "L1Code|Path|Code|Level|Name|ownerIL|ownerIT|ownerAL|ownerCST|Input|Output|DePartmentInCharge|ORG|BusinessRule|systemReq|APPLTYPE|MODUALAPP|FIORIID|TCODE|fitGAP|GAPID|GAPTYPE|CHANGELEVEL|CHANGETYPE1_1|CHANGETYPE1_2|CHANGETYPE1_3|CHANGETYPE2_1|CHANGETYPE2_2";
      result.widths = "30,80,120,80,80,90,180,180,80,80,80,80,80,80,180,180,80,80,100,120,80,100,120,120,80,80,80,80,80";
      result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
      result.aligns = "center,center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,center,center,center,center,center,center,center";
*/      

      result.header = "${menu.LN00024}, L1, Path, ID, Level, 명칭, 개요, Guideline, Check Point, Owner(IL),Owner(IT),Owner(AL),Owner(컨설턴트),Input,Output,관련조직,수행주체(Role),Business Rule,시스템 요구사항,Application Type,모듈/APP,Fiori ID,T-Code,Fit/GAP,GAP ID,GAP 유형,변화영향도 수준,주요 변화 항목(신규업무),주요 변화 항목(업무 수행 절차 변경),주요 변화 항목(업무R&R변경),주요 변화 항목(업무 수행 복잡도 증가),주요 변화 항목(활용 시스템 난이도 증가),변화관리 대응방안,KT&G 시스템,KGC 시스템,YJP 시스템,KT&G 화면 ID,KGC 화면 ID,YJP 화면 ID";
	  result.header += ", 변화사항(ASIS),기능요건,GAP Solution, 변화사항(TOBE),KT&G,태아산업,KT&G터키,KT&G브라질,KT&G러시아(제조),KT&G미국,KT&G인니,KT&G대만,KGC,KGC예본,라이프앤진,코스모코스,KGC대만,KGC미국,KGC중국,KGC일본,길림한정,코스모중국,코스모홍콩,YJP,KT&G러시아(물류),상상스테이,Owner,Last Updated,ItemID";
      result.cols = "L1Code|Path|Code|Level|Name|Description|Guideline|TOCheck|ownerIL|ownerIT|ownerAL|ownerCST|Input|Output|DePartmentInCharge|ORG|BusinessRule|systemReq|APPLTYPE|MODUALAPP|FIORIID|TCODE|fitGAP|GAPID|GAPTYPE|CHANGELEVEL|CHANGETYPE1_1|CHANGETYPE1_2|CHANGETYPE1_3|CHANGETYPE2_1|CHANGETYPE2_2|CHANGERESPON|KTNGSYS|KGCSYS|YJPSYS|KTNGSCRNM|KGCSCRNM|YJPSCRNM|CHGASIS|FCTREQ|GAPSLT|CHGTOBE|C001|C002|C003|C004|C005|C006|C007|C008|C009|C010|C011|C012|C013|C014|C015|C016|C017|C018|C019|C020|C021|C022|Owner|LastUpdated|ItemID";
      result.widths = "30,80,120,80,80,90,180,180,180,180,180,80,80,80,80,80,80,180,180,80,80,100,120,80,100,120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,80,100,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,100,100,100,0";
      result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
      result.aligns = "center,center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center";

      return result;
   }
   
   
   
   
   function doSearchList(){
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
        <li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR">
           <span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
         </li>
	</div>
	<div id="gridDiv" style="width:100%;" class="clear">
		<div id="grdGridArea" style="width:100%"></div>
	</div>
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL5"></div></div>
</div>
</form>
<iframe name="blankFrame" id="blankFrame" style="width:0px;height:0px; display:none;"></iframe>