﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:url value="/" var="root"/>

<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/js/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css'/>">

<script>
	var p_gridArea;	
		
	$(document).ready(function(){	
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 254)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 254)+"px;");
		};
		
		$("#excel").click(function(){
			doExcel();
			return false;
		});
		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		fnSelect('requestTeam', data, 'getReqTeamID', '', 'Select');	
		
  		
		$("input.datePicker").each(generateDatePicker);
		$('#searchValue').keypress(function(onkey){
			if(onkey.keyCode == 13){
				doSearchList();
				return false;
			}
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
	
	//===============================================================================
	// BEGIN ::: GRID
	function gridInit(){	
		var d = setGridData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.setIconPath("${root}${HTML_IMG_DIR}/");	
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});		
		p_gridArea.enablePaging(true,30,10,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	function setGridData(){
		var projectID;
		var result = new Object();
	
		result.title = "${title}";
		result.key = "sr_SQL.getProcessingSRStatusList";                                                       // subCategory                                                                                                                                                                    // SR 요청일                        //SR 완료요청일            // SR 완료예정일                     // SR 완료일                         // 최초 SR 접수일                         // 최초 SR 접수일  			 //SR 접수회수	       //결재생성일                         // 결재완료일                                    // CR 접수일                        // CR 종료일                          //28 CR 회수				
		result.header = "${menu.LN00024},SR No.,${menu.LN00002},${menu.LN00027},Domain,System,${menu.LN00272},${menu.LN00273},Classification,${menu.LN00025},${menu.LN00026},등록 ${menu.LN00247},SR ${menu.LN00227},CR ${menu.LN00227},SR ${menu.LN00093},${menu.LN00222},SR ${menu.LN00221},SR ${menu.LN00064},최초 SR ${menu.LN00077},최종 SR ${menu.LN00077},SR 접수회수,SR평가 접수 ,결재 ${menu.LN00013},결재 ${menu.LN00064},결재건수,승인건수,최초 CR 생성일,최종 CR 생성일,CR DueDate,CR ${menu.LN00077},CR ${menu.LN00233},CR 회수";                                  
		result.cols = "SRCode|Subject|SRSTSNM|Domain|System|CategoryNM|SubCategoryNM|ClassificationName|SRREGUSERNM|SRREGTMNM|SRReqTeamNM|SRReceiptTeamNM|CRReceiptTeamNM|SRRegDT|SRRDD|SRDueDate|SRCompletionDT|MinSRRCVDT|MaxSRRCVDT|SRRCVCount|SRPOINT|MinCSRDT|MaxAPRVDT|CSRCount|APRVCount|MinCRRegDT|MaxCRRDD|MaxCRDueDate|MinCRReceiptDT|maxCRCompletionDT|CRCount";
		result.widths = "0,100,150,100,120,120,100,110,110,110,120,120,90,90,90,90,90,90,90,90,90,100,90,90,90,90,90,90,90,90,90,90,90";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,left,left,center,center,center,center,center,center,center,center,center,center,center,left,left,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					+ "&reqTeamID=" + $("#requestTeam").val()
					+ "&regStartDate=" + $("#REG_STR_DT").val()
					+ "&regEndDate=" + $("#REG_END_DT").val();
		return result;
	}

	function doSearchList(){ 
		$("#srMode").val("");
		var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	function doSearchMyTRList(){
		$("#srMode").val("myTR");
		var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);		
	}
	
	
	
	//===============================================================================
	// BEGIN ::: EXCEL
	function doExcel() { p_gridArea.toExcel("${root}excelGenerate");}
	var delay = 0;
	function doExcel_org() {
		try{
			var start = new Date().getTime();	
			if(start > delay || delay == 0){
				delay = start + 300000; // 1000 -> 1초
				console.log("111 start :"+start+", delay :"+delay);
				p_gridArea.toExcel("${root}excelGenerate");
			}else{
				alert("Excel DownLoad 가 진행 중입니다."); console.log("222 start :"+start+", delay :"+delay);
				return;
			}
		}catch(e){}
			
	}



	
	
</script>

<div id="srListDiv">
<form name="srFrm" id="srFrm" action="" method="post"  onsubmit="return false;">
	
	<div class="floatL mgT10 mgB12">
		<h3><img src="${root}${HTML_IMG_DIR}/bullet_blue.png" id="subTitle_baisic">&nbsp;&nbsp;SR 처리현황</h3>
	</div>	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_search" id="search">
	<colgroup>
	    <col width="10%">
	    <col width="40%">
	 	<col width="10%">
	    <col width="40%">
    </colgroup>
     <tr> 
     	<!-- 요청부서  -->
       	<th class="alignL">${menu.LN00026}</th>
        <td class="alignL">     
	        <select id="requestTeam" Name="requestTeam" style="width:250px;">
	            <option value=''>Select</option>
	        </select>
        </td> 
   		<!-- 요청일-->
        <th class="alignL pdL5">${menu.LN00093}</th>     
        <td>     
            <font><input type="text" id="REG_STR_DT" name="REG_STR_DT" value="${startRegDT}"	class="input_off datePicker stext" size="8"
				style="width:63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="15" >
			</font>
			~
			<font><input type="text" id="REG_END_DT" name="REG_END_DT" value="${endRegDT}"	class="input_off datePicker stext" size="8"
				style="width: 63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="15">
			</font>
        </td> 
     </tr>
   </table>
	<div class="countList pdT5 pdB5" >
        <li class="count">Total  <span id="TOT_CNT"></span></li>      
        <li class="floatR">
           	&nbsp;<span id="viewSearch" class="btn_pack medium icon"><span class="search"></span><input value="Search" type="submit" onclick="doSearchList();" style="cursor:hand;"></span>
        	<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
        </li>
    </div>
    
	<div id="gridDiv" class="mgB10 clear" class="clear" >
		<div id="grdGridArea"></div>
	</div>
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL5"></div></div>
</form>
</div>