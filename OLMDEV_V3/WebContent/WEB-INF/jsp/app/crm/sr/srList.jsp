
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:url value="/" var="root"/>

<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/js/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css'/>">
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script>
	var p_gridArea;	
	var p_gridDownArea;
	var screenType = "${screenType}";
	var srMode = "${srMode}";
	var srType = "${srType}";
	var itemID = "${itemID}";
		
	$(document).ready(function(){	
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 320)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 320)+"px;");
		};
		
		$("#excel").click(function(){
			doExcel();
			return false;
		});
		
		setTimeout(function() {$('#srCode').focus();}, 0);
		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}";
		fnSelect('srArea1', data, 'getSrArea1', '${srArea1}', 'Select');
		fnSelect('category', data +"&level=1", 'getSrCategory', '${category}', 'Select');
		fnSelect('srStatus', data+"&category=SRSTS", 'getDictionaryOrdStnm', '${status}', 'Select');
  		fnSelect('requestTeam', data, 'getReqTeamID', '${requestTeam}', 'Select');	
  		fnSelect('srReceiptTeam', data, 'getSRReceiptTeamID', '${srReceiptTeam}', 'Select');	
  		fnSelect('crReceiptTeam', data, 'getCRReceiptTeamID', '${crReceiptTeam}', 'Select');	
  		fnSelect('classification', data+"&category=SRCLS", 'getDictionaryOrdStnm', '${srInfoMap.Classification}', 'Select');
  		
		if("${srArea1}" != ""){fnGetSRArea2("${srArea1}");}
		if("${category}" != ""){fnGetSubCategory("${category}");}
  		
		$("input.datePicker").each(generateDatePicker);
		/* $('.searchList').click(function(){
			doSearchList();
			return false;
		}); */
		$('#searchValue').keypress(function(onkey){
			if(onkey.keyCode == 13){
				doSearchList();
				return false;
			}
		});		
		
		$('#new').click(function(){ 
			fnRegistSR();
			return false;
		});	
		
		$('#createSRCR').click(function(){ 
			fnRegistSRCR();
			return false;
		});	

		setTimeout(function() {$('#searchValue').focus();}, 0);
		parent.top.$('#mainType').val("");
		if(srMode == "REG"){
			fnRegistSR();
		}else if("${srID}" != "" && parent.$("#screenType").val() == "srRcv"){ // 외부에서 접수 페이지 바로가기 
			fnGoDetail();
			parent.$("#screenType").val("");
		}else if("${mainType}"=="SRDtl" || "${mainType}" == "mySRDtl" || "${mainType}" == "SRDtlView"){
			if("${srID}" != ""){ fnGoDetail();
			}else{ setTimeout(function() { gridInit(); doSearchList();},500 );}
		}else{
			setTimeout(function() { gridInit(); doSearchList(); if(screenType=='srRcv'){gridDownInit();} },500 );
		}
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
		p_gridArea.enablePaging(true,30,null,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.setColumnHidden(0, true);
		p_gridArea.setColumnHidden(11, true);
		p_gridArea.setColumnHidden(12, true);
		p_gridArea.setColumnHidden(13, true);
		p_gridArea.setColumnHidden(16, true);
		p_gridArea.setColumnHidden(17, true);
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	function setGridData(){
		var projectID;
		var result = new Object();
		if($("#srMode").val() != ""){
			srMode = "myTR";
		}else{
			srMode = "${srMode}";
		}
		result.title = "${title}";
		result.key = "sr_SQL.getSrMSTList";                                                                                                                                     //SR완료예정일                                              // CR완료예정일
		result.header = "${menu.LN00024},SR No.,${menu.LN00002},${menu.LN00027},${menu.LN00025},${menu.LN00274},${menu.LN00185},${menu.LN00273},${menu.LN00004},${menu.LN00093},SR${menu.LN00221},srID,ReceiptUserID,Status,CR${menu.LN00221},${menu.LN00064},IsPublic,RequestUserID";
		result.cols = "SRCode|ReceiptDelay2|StatusName|RequestName|SRArea1Name|SRArea2Name|SubCategoryNM|ReceiptInfo|RegDate|SRDueDate|SRID|ReceiptUserID|Status|CRDueDate|SRCompletionDT|IsPublic|RequestUserID";
		result.widths = "0,90,*,80,80,90,80,80,70,70,80,70,70,70,80,70,70,70";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,left,left,center,center,center,center,center,center,center,left,center,left,left,left,left,left,left";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
						+ "&pageNum=" + $("#currPage").val()
						+ "&screenType=" + screenType
						+ "&srMode=" +srMode
						+ "&srType=" +srType
						+ "&itemID=" +itemID
						+ "&category=" + $("#category").val()
						+ "&srCode=" + $("#srCode").val()
						+ "&srArea1=" + $("#srArea1").val()
						+ "&srArea2=" + $("#srArea2").val()
						+ "&subject=" + $("#subject").val()
						+ "&requestUserName=" + $("#requestUser").val() ;
						if($("#subCategory").val() != '' & $("#subCategory").val() != null){
							result.data = result.data + "&subCategory=" + $("#subCategory").val();
						}else if("${subCategory}" != '' & "${subCategory}" != null){
							result.data = result.data + "&subCategory=${subCategory}";
						}
						if($("#classification").val() != undefined ||  $("#classification").val() != null){result.data = result.data +  "&classification=" + $("#classification").val()}
						if($("#srReceiptTeam").val() != undefined ||  $("#srReceiptTeam").val() != null){result.data = result.data +  "&srReceiptTeam=" + $("#srReceiptTeam").val()}
						if($("#crReceiptTeam").val() != undefined ||  $("#crReceiptTeam").val() != null){result.data = result.data +  "&crReceiptTeam=" + $("#crReceiptTeam").val()}
						if(screenType != "srRqst") {
							result.data = result.data  							
							+ "&regStartDate=" + $("#REG_STR_DT").val()
							+ "&regEndDate=" + $("#REG_END_DT").val()							
							+ "&stSRDueDate=" + $("#ST_SRDUE_DT").val() 
							+ "&endSRDueDate=" + $("#END_SRDUE_DT").val() 
							+ "&stCRDueDate=" + $("#ST_CRDUE_DT").val() 
							+ "&endCRDueDate=" + $("#END_CRDUE_DT").val() 							
							+ "&receiptUserName=" + $("#receiptUser").val()
							+ "&requestTeam=" + $("#requestTeam").val();							
						}
								
						if($("#srStatus").val() != '' & $("#srStatus").val() != null ) {
							result.data = result.data + "&srStatus=" + $("#srStatus").val();
						}else if("${srStatus}" != '' & "${srStatus}" != null ){
							result.data = result.data + "&srStatus=${srStatus}";
						}else{
							result.data = result.data + "&srStatus=${status}";
						}
						if (srMode == "mySR") {
							result.data = result.data + "&loginUserId=${sessionScope.loginInfo.sessionUserId}";
						}else if(srMode == "PG" || srMode == "PJT") {
							result.data = result.data + "&refID=${refID}";
						}else if (srMode == "myTeam") {
							result.data = result.data + "&myTeamId=${sessionScope.loginInfo.sessionTeamId}";
						}else if(srMode == "myTR"){
							result.data = result.data + "&loginUserId=${sessionScope.loginInfo.sessionUserId}";
						}
						//alert(result.data);
						
		return result;
	}
	
	function gridOnRowSelect(id, ind){
		var screenType = "${screenType}";
		var srCode = p_gridArea.cells(id, 1).getValue();
		var srID = p_gridArea.cells(id, 11).getValue();
		var receiptUserID = p_gridArea.cells(id, 12).getValue();
		var status = p_gridArea.cells(id, 13).getValue();
		var isPublic = p_gridArea.cells(id, 16).getValue();
		var requestUserID = p_gridArea.cells(id, 17).getValue();
		var userID = "${sessionScope.loginInfo.sessionUserId}";
		
		if(isPublic == '0'){
			if(userID != receiptUserID && userID != requestUserID){
				alert('비공개 요청입니다.');
				return;
			}
		}
		
		var srStatus = $("#srStatus").val();
		if(srStatus == ""){
			srStatus = "${srStatus}";
		}
		if(srStatus == ""){
			srStatus = "${status}";
		}
		var url = "processSR.do";
		var data = "srCode="+srCode+"&pageNum="+$("#currPage").val()
					+ "&srMode=${srMode}&srType=${srType}&screenType=${screenType}&srID="+srID
					+ "&receiptUserID="+receiptUserID+"&srStatus="+status+"&projectID=${projectID}&itemID="+itemID
					+ "&category=" + $("#category").val()
					+ "&subCategory=" + $("#subCategory").val()
					+ "&srArea1=" + $("#srArea1").val()
					+ "&srArea2=" + $("#srArea2").val()
					+ "&srCode=" + $("#srCode").val()
					+ "&subject=" + $("#subject").val()
					+ "&status=" +srStatus
					+ "&receiptUser=" + $("#receiptUser").val()
					+ "&requestUser=" +$("#requestUser").val()
					+ "&requestTeam=" +$("#requestTeam").val()
					+ "&startRegDT=" +$("#REG_STR_DT").val()
					+ "&endRegDT=" +$("#REG_END_DT").val()
					+ "&stSRDueDate=" + $("#ST_SRDUE_DT").val() 
					+ "&endSRDueDate=" + $("#END_SRDUE_DT").val() 
					+ "&stCRDueDate=" + $("#ST_CRDUE_DT").val() 
					+ "&endCRDueDate=" + $("#END_CRDUE_DT").val() 
					+ "&searchSrCode=" +$("#srCode").val()
					+ "&srReceiptTeam="+$("#srReceiptTeam").val()
					+ "&crReceiptTeam="+$("#crReceiptTeam").val();
		var target = "srListDiv";		
		ajaxPage(url, data, target);
	}
	
	function fnGoDetail(){
		var screenType = "${screenType}";
		var mainType = "${mainType}";
		var srID = "${srID}";
		var url = "processSR.do";
		var data = "&pageNum="+$("#currPage").val()
					+"&srMode=${srMode}&srType=${srType}&screenType=${screenType}&srID="+srID+"&mainType="+mainType;
		var target = "srListDiv";
		ajaxPage(url, data, target);
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
	
	function fnRegistSR(){
		var sysCode = parent.top.$('#sysCode').val();
		var proposal = parent.top.$('#proposal').val();
		var url = "registSR.do";
		var target = "srListDiv";
		if(srMode == "REG"){srMode = "";}
		var data = "srType=ITSP&srMode="+srMode+"&screenType=${screenType}"
				+ "&category="+$("#category").val()
				+ "&srArea1=" + $("#srArea1").val()
				+ "&srArea2=" + $("#srArea2").val()
				+ "&status=" + $("#status").val()
				+ "&searchSrCode=" + $("#srCode").val()
				+ "&subject=" + $("#subject").val()
				+ "&sysCode="+sysCode 
				+ "&proposal="+proposal
				+ "&classification="+$("#classification").val();
		parent.top.$('#sysCode').val("");
		ajaxPage(url, data, target);
	}
	
	function fnRegistSRCR(){
		var url = "registSRCR.do";
		var target = "srListDiv";
		var data = "srType=ITSP&srMode=${srMode}&screenType=${screenType}"
					+ "&category="+$("#category").val()
					+ "&srArea1=" + $("#srArea1").val()
					+ "&srArea2=" + $("#srArea2").val()
					+ "&status=" + $("#status").val()
					+ "&srCode=" + $("#srCode").val()
					+ "&subject=" + $("#subject").val()
					+ "&classification="+$("#classification").val();
		ajaxPage(url, data, target);
	}
	
	function fnGetSRArea2(SRArea1ID){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&parentID="+SRArea1ID;
		fnSelect('srArea2', data, 'getSrArea2', '${srArea2}', 'Select');
	}
	
	function fnGetSubCategory(parentID){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&parentID="+parentID;
		fnSelect('subCategory', data, 'getSrCategory', '${subCategory}', 'Select');
	}
		
	// 검색 조건 초기화 
	function fnClearSearchSR(){;
		$("#srArea1").val("");
		$("#srArea2").val("");
		$("#category").val("");
		$("#subCategory").val("");
		$("#srStatus").val("");
		$("#REG_STR_DT").val("");
		$("#REG_END_DT").val("");
		$("#ST_SRDUE_DT").val("");
		$("#END_SRDUE_DT").val("");
		$("#ST_CRDUE_DT").val("");
		$("#END_CRDUE_DT").val("");
		$("#requestUser").val("");
		$("#receiptUser").val("");
		$("#srCode").val("");
		$("#subject").val("");
		$("#classification").val("");
		return;
	}
	
	//===============================================================================
	// BEGIN ::: EXCEL
	function doExcel() {		
		p_gridArea.toExcel("${root}excelGenerate");
	}
	
	//===============================================================================
	// BEGIN ::: DATA EXCEL[SRSummaryViewList Excel downLoad]
	function fnDownData() {			
		var d = setGridDownData();
		fnLoadDhtmlxGridJson(p_gridDownArea, d.key, d.cols, d.data,"","","TOT_CNTD","","fnDownExcel()");
	}
	
	function fnDownExcel(){
		p_gridDownArea.toExcel("${root}excelGenerate");
	}
	
	function gridDownInit(){	
		var d = setGridDownData();
		p_gridDownArea = fnNewInitGrid("grdGridDownArea", d);
		p_gridDownArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridDownArea.setIconPath("${root}${HTML_IMG_DIR}/");		
		p_gridDownArea.setPagingSkin("bricks");
	}
	
	function setGridDownData(){
		var result = new Object();
		result.title = "${title}";
		result.key = "sr_SQL.getSRSummaryList";                                                                                                                                     
		result.header = "${menu.LN00024},SRCode,SR Status,SR Request Team,SR Receipt Team,CR Receipt Team,Domain,System,Category,Sub Category,SR Creation date,SR Request Due date,SR Due Date,SR Completion Date,Min SR Receipt date,Max SR Receipt date,SR Receipt Count,SR POINT,Min CSR Creation Date,Approval Date,CSR Count,Approval Count,CR Creation Date, CR Request Due date, CR Due Date, CR Receipt Date,CR Completion date,CR Count,Project ID";
		result.cols = "SRCode|SRSTSNM|SRReqTeamNM|SRReceiptTeamNM|CRReceiptTeamNM|Domain|System|CategoryNM|SubCategoryNM|SRRegDT|SRRDD|SRDueDate|SRCompletionDT|MinSRRCVDT|MaxSRRCVDT|SRRCVCount|SRPOINT|MinCSRDT|MaxAPRVDT|CSRCount|APRVCount|MinCRRegDT|MaxCRRDD|MaxCRDueDate|MinCRReceiptDT|maxCRCompletionDT|CRCount|ProjectID";
		result.widths = "80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,50";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center";
		result.data = "srRegStartDT="+$("#REG_STR_DT").val()+"&srRegEndDT="+$("#REG_END_DT").val();
		return result;
	}
	
</script>

<div id="srListDiv">
<form name="srFrm" id="srFrm" action="" method="post"  onsubmit="return false;">
	<input type="hidden" id="NEW" name="NEW" value="">
	<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}">
	<input type="hidden" id="srMode" name="srMode">
	<div class="floatL msg" style="width:100%"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png" id="subTitle_baisic">&nbsp;&nbsp;${menu.LN00275}</span>
	</div>
	

<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_search" id="search">
	<colgroup>
	    <col width="7%">
	    <col width="15%">
	 	<col width="8%">
	    <col width="18%">
	 	<col width="8%">
	    <col width="18%">
	    <col width="8%">
	    <col width="18%">

    </colgroup>
    
 <c:if test="${screenType != 'srRqst' }" >	  
    <tr>
   		<!-- 도메인 -->
       	<th class="alignL viewtop pdL5">${menu.LN00274}</th>
        <td class="viewtop alignL">     
	       	<select id="srArea1" Name="srArea1" OnChange="fnGetSRArea2(this.value);" style="width:90%">
	       		<option value=''>Select</option>
	       	</select>
       	</td>
       	<!-- 시스템 -->
        <th class="alignL viewtop pdL5">${menu.LN00185}</th>
       <td class="viewtop alignL">      
        <select id="srArea2" Name="srArea2" style="width:90%">
            <option value=''>Select</option>
        </select>
        </td>
       	<!-- 카테고리 -->
       	<th class="alignL viewtop pdL5">${menu.LN00033}</th>
         <td class="viewtop alignL">       
	       	<select id="category" Name="category" OnChange="fnGetSubCategory(this.value);" style="width:90%">
	       		<option value=''>Select</option>
	       	</select>
       	</td>       	
       	<!-- 서브 카테고리 -->
       <th class="alignL viewtop pdL5">${menu.LN00273}</th>
         <td class="viewtop alignL">      
	        <select id="subCategory" Name="subCategory" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td>     
    </tr>
     <tr>
      	<!-- 상태 -->
       	<th class="alignL pdL5">${menu.LN00027}</th>
        <td>      
	       	<select id="srStatus" Name="srStatus" style="width:90%">
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
        <!-- SR 완료예정일 -->
       	<th class="alignL pdL5">SR ${menu.LN00221}</th>
        <td >     
	       <font><input type="text" id="ST_SRDUE_DT" name="ST_SRDUE_DT" value="${stSRDueDate}" class="input_off datePicker stext" size="8"
				style="width:63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" >
			</font>
			~
			<font><input type="text" id="END_SRDUE_DT" name="END_SRDUE_DT" value="${endSRDueDate}"	class="input_off datePicker stext" size="8"
				style="width: 63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="15">
			</font>
       	</td> 
       	<!-- CR 완료예정일 -->
       	<th class="alignL pdL5">CR ${menu.LN00221}</th>
        <td >     
	       <font><input type="text" id="ST_CRDUE_DT" name="ST_CRDUE_DT" value="${stCRDueDate}" class="input_off datePicker stext" size="8"
				style="width: 63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" >
			</font>
			~
			 <font><input type="text" id="END_CRDUE_DT" name="END_CRDUE_DT" value="${endCRDueDate}" class="input_off datePicker stext" size="8"
				style="width: 63px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" >
			</font>
       	</td> 
   	 </tr>
    <tr>
       
        <!-- 요청자 -->
       	<th class="alignL pdL5">${menu.LN00025}</th>
        <td><input type="text" class="text" id="requestUser" name="requestUser" value="${requestUser}" style="ime-mode:active;width:90%;" /></td>
  		 <!-- 요청 부서 -->
        <th class="alignL pdL5">${menu.LN00026}</th>
        <td  class= "alignL">     
	        <select id="requestTeam" Name="requestTeam" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td> 
         <!-- SR 접수조직 -->
        <th class="alignL  pdL5">SR${menu.LN00153}</th>
        <td  class= "alignL">     
	        <select id="srReceiptTeam" Name="srReceiptTeam" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td>  
        <!-- CR 접수조직 -->
        <th class="alignL pdL5">CR${menu.LN00153}</th>
        <td  class= "alignL">     
	        <select id="crReceiptTeam" Name="crReceiptTeam" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td>             
     </tr>   
   	 <tr>
     	 <!-- SR 담당자 -->
       	<th class="alignL pdL5">${menu.LN00004}</th>
        <td><input type="text" class="text" id="receiptUser" name="receiptUser" value="${receiptUser}" style="ime-mode:active;width:90%;" />
        </td> 
  		 <!-- SR No -->
       	<th class="alignL pdL5">SR No.</th>
      		 <td ><input type="text" class="text" id="srCode" name="srCode" value="${searchSrCode}" style="ime-mode:active;width:90%;" /></td>
       	<!-- 제목-->
        <th class="alignL pdL5" >${menu.LN00002}</th>     
	    <td><input type="text" class="text" id="subject" name="subject" value="${subject}" style="ime-mode:active;width:100%;" /></td>
	    <th class="alignL pdL5">Classification</th>
	    <td  class= "alignL">     
	        <select id="classification" Name="classification" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td>       
    </tr>
  </c:if> 
  
  <c:if test="${screenType == 'srRqst' }" >	  
    <tr> 
     		
   		<!-- 도메인 -->
       	<th class="alignL viewtop pdL5">${menu.LN00274}</th>
        <td class="viewtop alignL">     
	       	<select id="srArea1" Name="srArea1" OnChange="fnGetSRArea2(this.value);" style="width:90%">
	       		<option value=''>Select</option>
	       	</select>
       	</td>
       	<!-- 시스템 -->
        <th class="alignL viewtop pdL5">${menu.LN00185}</th>
       <td class="viewtop alignL">      
        <select id="srArea2" Name="srArea2" style="width:90%">
            <option value=''>Select</option>
        </select>
        </td>    
        	<!-- 카테고리 -->
       	<th class="alignL viewtop pdL5">${menu.LN00033}</th>
         <td class="viewtop alignL">       
	       	<select id="category" Name="category" OnChange="fnGetSubCategory(this.value);" style="width:90%">
	       		<option value=''>Select</option>
	       	</select>
       	</td>       	
       	<!-- 서브 카테고리 -->
       <th class="alignL viewtop pdL5">${menu.LN00273}</th>
         <td class="viewtop alignL">      
	        <select id="subCategory" Name="subCategory" style="width:90%">
	            <option value=''>Select</option>
	        </select>
        </td>     
    </tr>
    <tr>  
    	<!-- SR No -->
       	<th class="alignL pdL5">SR No.</th>
      	 <td><input type="text" class="text" id="srCode" name="srCode" value="${searchSrCode}" style="ime-mode:active;width:90%;" /></td>
       	<!-- 제목-->
        <th class="alignL pdL5">${menu.LN00002}</th>     
	     <td><input type="text" class="text" id="subject" name="subject" value="${subject}" style="ime-mode:active;width:90%;" /></td>  
	      <!-- 요청자 -->
       	<th class="alignL pdL5">${menu.LN00025}</th>
        <td><input type="text" class="text" id="requestUser" name="requestUser" value="${requestUser}" style="ime-mode:active;width:90%;" /></td>
       	<!-- 상태 -->
       	<th class="alignL pdL5">${menu.LN00027}</th>
        <td>      
	       	<select id="srStatus" Name="srStatus" style="width:90%">
	       		<option value=''>Select</option>
	       	</select>
       	</td> 
   		
     </tr>
  </c:if>   
   </table>
	<div class="countList pdT5" >
        <li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR">
           	&nbsp;<span id="viewSearch" class="btn_pack medium icon"><span class="search"></span><input value="Search" type="submit" onclick="doSearchList();" style="cursor:hand;"></span>
           	<c:if test="${screenType != 'srRqst' }" >
		      	<span id="viewSave" class="btn_pack medium icon"><span class="search"></span><input value="Transfered" type="submit" onclick="doSearchMyTRList();" style="cursor:hand;"></span>
           	</c:if>
           	<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_search_clean.png" value="Clear" style="cursor:pointer;" onclick="fnClearSearchSR();">
           	<c:if test="${screenType == 'srRqst' }" >
        	&nbsp;<span class="btn_pack small icon"><span class="add"></span><input value="Create" type="submit" id="new" style="cursor:hand;"></span>&nbsp;
        	</c:if>
        	  	<!-- 삭제
        	<c:if test="${screenType != 'srRqst' }" >
        	&nbsp;<span class="btn_pack small icon"><span class="add"></span><input value="Create SR/CR" type="submit" id="createSRCR"></span>&nbsp;
        	</c:if>  -->
        	<c:if test="${screenType == 'srRcv'}" ><span class="btn_pack small icon"><span class="down"></span><input value="Data" type="button" id="data" OnClick="fnDownData()"></span></c:if>
        	<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
        </li>
    </div>
    
	<div id="gridDiv" style="width:100%;" class="clear" >
		<div id="grdGridArea"></div>
	</div>
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL5"></div></div>
	<!-- Data Excel [SRSummaryView List] -->
	<c:if test="${screenType == 'srRcv'}" >
	<div id="gridDownDiv" style="visibility:hidden;overflow:hidden;">
	<li class="count">Total <span id="TOT_CNTD"></span></li>
		<div id="grdGridDownArea" style="width:100%;height:40px;overflow:hidden;"></div>
	</div>
	</c:if>
</form>
</div>