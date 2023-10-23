<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00042" var="CM00042"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">  
<script type="text/javascript">
	var p_gridArea;				
	var srID ="${srID}";
	var scrID ="${scrID}";
	var thisYmd = "${thisYmd}";
	var beforeYmd = "${beforeYmd}";
	var status = "${status}";
	var size = 300;
	var searchData = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&ctrMode=${ctrMode}&myCTR=${myCTR}";
	$(document).ready(function() {	

		fnSelect('sysArea1', "itemTypeCode=${itemTypeCode}&srType=${srType}", 'getESMSRArea1', '${sysArea1}', 'Select','esm_SQL');
  		fnSelect('regTeam', '', 'getctrRegTeam', '', 'Select', 'ctr_SQL');
  		fnSelect('status', "&category=CTSSTS", 'getDictionaryOrdStnm_commonSelect', status, 'Select', 'common_SQL');
		if(srID != "" && scrID != ""){
			size = 540;
		}
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - size)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - size)+"px;");
		};
		
		$('#viewSearch').keypress(function(onkey){
			if(onkey.keyCode == 13){
				doSearchctrList();
				return false;
			}
		});		

		$("#excel").click(function(){p_gridArea.toExcel("${root}excelGenerate");});		
		$("input.datePicker").each(generateDatePicker);
		$('.layout').click(function(){
			var changeLayout = $(this).attr('alt');
			setLayout(changeLayout);
		});
					
		gridctrInit();
		doSearchctrList();
	});	
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	
	/* CTS List */
	function gridctrInit(){
		var d = setGridData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnPjtRowSelect(id,ind);});		
		p_gridArea.enablePaging(true,30,null,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridData(){
		var result = new Object();		
		result.title   = "${title}";
		result.key     = "ctr_SQL.getCTRMst";
		result.header  = "${menu.LN00024},ctrID,CTS No.,${sysArea1LabelNM},${sysArea2LabelNM},상태,긴급,요청자,요청일,검토자,검토일,승인자,승인일,승인상태,실행자,실행일,IF"; 
		result.cols    = "ctrID|ctrCode|sysArea1NM|sysArea2NM|statusNM|urgencyYN|regTName|regDT|reviewerTName|reviewDT|approverTName|approvalDT|aprvStatusNM|CTUserNM2|CTExeDT|ifStatus";
		result.widths  = "35,0,*,120,120,100,70,125,100,125,100,125,100,100,125,100,70";
		result.sorting = "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns  = "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center";
		result.data    = searchData + setParm() + "&pageNum=" + $("#currPage").val();
		return result;
	}
	
	function setParm(){
		var parmData = "";
		if($("#loginUserId").val() != '' & $("#loginUserId").val() != null){
			parmData = parmData +"&loginUserId=" + $("#loginUserId").val();
		}
		// SCR
		if($("#scrID").val() != '' & $("#scrID").val() != null){
			parmData = parmData +"&scrID=" + $("#scrID").val();
		}
		// SR
		if($("#srID").val() != '' & $("#srID").val() != null){
			parmData = parmData +"&srID=" + $("#srID").val();
		}
		// 코드
		if($("#ctrCode").val() != '' & $("#ctrCode").val() != null){
			parmData = parmData +"&ctrCode=" + $("#ctrCode").val();
		}
		// 진행상태
		if($("#status").val() != '' & $("#status").val() != null){
			parmData = parmData +"&status=" + $("#status").val();
		}else if(status != ""){
			parmData = parmData +"&status=" + status;			
		}
		//긴급
		if($("#urgencyYN").val() != '' & $("#urgencyYN").val() != null){
			//if( document.all("urgencyYN").checked == true){ $("#urgencyYN").val("Y"); }else{ $("#urgencyYN").val("N"); }
			parmData = parmData +"&urgencyYN=" + $("#urgencyYN").val();
		}
		// 제목
		if($("#subject").val() != '' & $("#subject").val() != null){
			parmData = parmData +"&subject=" + encodeURIComponent($("#subject").val());
		}
		// 요청자
		if($("#regUserName").val() != '' & $("#regUserName").val() != null){
			parmData = parmData +"&regUserName=" + $("#regUserName").val();
		}			
		// 요청 기간
		if($("#reg_STR_DT").val() != '' & $("#reg_STR_DT").val() != null){
			parmData = parmData + "&reg_STR_DT=" + $("#reg_STR_DT").val();
		}	
		// 요청 기간
		if($("#reg_END_DT").val() != '' & $("#reg_END_DT").val() != null){
	        parmData = parmData + "&reg_END_DT=" + $("#reg_END_DT").val();
		}	
		// 검토자
		if($("#reviewerName").val() != '' & $("#reviewerName").val() != null){
			parmData = parmData +"&reviewerName=" + $("#reviewerName").val();
		}			
		// 검토 기간
		if($("#reviewe_STR_DT").val() != '' & $("#reviewe_STR_DT").val() != null){
			parmData = parmData + "&reviewe_STR_DT=" + $("#reviewe_STR_DT").val();
		}	
		// 검토 기간
		if($("#reviewe_END_DT").val() != '' & $("#reviewe_END_DT").val() != null){
	        parmData = parmData + "&reviewe_END_DT=" + $("#reviewe_END_DT").val();
		}	
		// 승인자
		if($("#approverName").val() != '' & $("#approverName").val() != null){
			parmData = parmData +"&approverName=" + $("#approverName").val();
		}			
		// 승인 기간
		if($("#approval_STR_DT").val() != '' & $("#approval_STR_DT").val() != null){
			parmData = parmData + "&approval_STR_DT=" + $("#approval_STR_DT").val();
		}	
		// 승인 기간
		if($("#approval_END_DT").val() != '' & $("#approval_END_DT").val() != null){
	        parmData = parmData + "&approval_END_DT=" + $("#approval_END_DT").val();
		}	
		// 실행자
		if($("#CTUserNM").val() != '' & $("#CTUserNM").val() != null){
			parmData = parmData +"&CTUserNM=" + $("#CTUserNM").val();
		}			
		// 실행 기간
		if($("#oprAp_STR_DT").val() != '' & $("#oprAp_STR_DT").val() != null){
			parmData = parmData + "&oprAp_STR_DT=" + $("#oprAp_STR_DT").val();
		}
		// 실행 기간
		if($("#oprAp_END_DT").val() != '' & $("#oprAp_END_DT").val() != null){
	        parmData = parmData + "&oprAp_END_DT=" + $("#oprAp_END_DT").val();
		}
		// sysArea1
		if($("#sysArea1").val() != '' & $("#sysArea1").val() != null){
			parmData = parmData +"&sysArea1=" + $("#sysArea1").val();
		}
		// sysArea2
		if($("#sysArea2").val() != '' & $("#sysArea2").val() != null){
			parmData = parmData +"&sysArea2=" + $("#sysArea2").val();
		}
		// 담당 조직
		if($("#regTeam").val() != '' & $("#regTeam").val() != null){
			parmData = parmData +"&regTeamID=" + $("#regTeam").val();
		}
		return parmData;
	}
	
	/* List 조회 */
	function doSearchctrList(){
		if($("#reg_STR_DT").val() != "" && $("#reg_END_DT").val() == "")							$("#reg_END_DT").val(new Date().toISOString().substring(0,10));
		if($("#reviewe_STR_DT").val() != "" && $("#reviewe_END_DT").val() == "")			$("#reviewe_END_DT").val(new Date().toISOString().substring(0,10));
		if($("#approval_STR_DT").val() != "" && $("#approval_END_DT").val() == "")		$("#approval_END_DT").val(new Date().toISOString().substring(0,10));
		if($("#oprAp_STR_DT").val() != "" && $("#oprAp_END_DT").val() == "")				$("#oprAp_END_DT").val(new Date().toISOString().substring(0,10));
		
		var d = setGridData();
		//fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data,false,"N");	
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	/* 상세 조회 */
	function gridOnPjtRowSelect(id, ind){
		var ctrID = p_gridArea.cells(id, 1).getValue();
	    fnOpenctrDetailPopup(ctrID);	
	}
	
	/* CTS 신규 화면 */
	function fnOpenctrNewPopup() {
		var url = "registerCTR.do?srID="+srID+"&scrID="+scrID;
	    window.open(url,'ctsNew','width=850, height=615, left=300, top=100,scrollbars=yes,resizable=yes');
	}
	
	/* CTS 상세 화면 */
	function fnOpenctrDetailPopup(ctrID) {
	    var url = "ctrDetail.do?ctrID="+ctrID;
	    window.open(url,'ctrDetail','width=850, height=750, left=300, top=150,scrollbars=yes,resizable=yes');
	}
	
	/* 조건 초기화 */
	function fnInitSearchArea(){
		$('#reg_STR_DT, #reg_END_DT, #reviewe_STR_DT, #reviewe_END_DT').val('');
		$('#approval_STR_DT, #approval_END_DT, #oprAp_STR_DT, #approval_END_DT').val('');
		$('#subject, #ctrCode, #regUserName, #reviewerName, #approverName, #CTUserNM').val('');		
		$('#sysArea1, #sysArea2, #status, #regTeam, #urgencyYN').val('');
	}

	/* Excel Download */
	function fnDownloadExcel() {	
		p_gridArea.setColumnHidden(8, false);
		p_gridArea.toExcel("${root}excelGenerate");
		p_gridArea.setColumnHidden(8, true);
	}
	function fnGetSYSArea2(SYSArea1ID){
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&srType=${srType}&parentID="+SYSArea1ID+"&itemTypeCode=${itemTypeCode}";
		fnSelect('sysArea2', data, 'getSrArea2', '${sysArea2}', 'Select');
	}
	
	// [CTR_INSERT_IF EXE] Click
	function exeCTSInsertIF() {
		if (confirm("${CM00042}")) {
			var data = searchData + setParm();
			var url  = "exeCTSInsertIF.do";
			ajaxSubmit(document.CTSFrm, url,"subFrame");
		}
	}
</script>

</head>
<form name="CTSFrm" id="CTSFrm" action="" method="post" onsubmit="return false;" >
	<input type="hidden" id="loginUserId"      name="loginUserId"      value="${sessionScope.loginInfo.sessionUserId}" />
    <input type="hidden" id="currPage" name="currPage" value="${currPage}"></input> 
	<input type="hidden" id="srID" name="srID" value="${srID}" />
	<input type="hidden" id="scrID" name="scrID" value="${scrID}" />

	<div class="floatL mgT10 mgB12" style="width:100%;"><h3><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;CTS관리</h3></div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_search" id="search">
		<colgroup>
		    <col width="8%">
		    <col width="17%">
		 	<col width="8%">
		    <col width="17%">
		 	<col width="8%">
		    <col width="17%">
		    <col width="8%">
		    <col width="17%">
	    </colgroup>    
	     <tr>
	     	<!-- CTS No. -->
	        <th class="alignL pdL10 viewline">CTS No.</th>
	        <td class="alignL pdL5"><input type="text" class="text" id="ctrCode" name="ctrCode" value="" style="ime-mode:active;width:98%;" /></td>
	        <!-- sysArea1 -->
	        <th class="alignL pdL10" >${sysArea1LabelNM}</th>     
		    <td class="alignL pdL5">
		    	<select id="sysArea1" Name="sysArea1" OnChange="fnGetSYSArea2(this.value);" style="width:90%">
	       			<option value=''>Select</option>
	       		</select>
		    </td>
		    <!-- sysArea2 -->
	        <th class="alignL pdL10" >${sysArea2LabelNM}</th>     
		    <td class="alignL pdL5">
		    	<select id="sysArea2" Name="sysArea2"  style="width:90%">
	       			<option value=''>Select</option>
	       		</select>
		    </td>
	       	<!-- 상태 -->
	       	<th class="alignL pdL10">${menu.LN00027}</th>
	        <td class="alignL pdL5 last">
	        	<select id="status" Name="status" style="width:98%"></select>
	       	</td>
	  		  	        
	    </tr>
	    <tr>
	    	<!-- 요청자 -->
	        <th class="alignL pdL10 viewline">${menu.LN00025}</th>
	        <td class="alignL pdL5"><input type="text" class="text" id="regUserName" name="regUserName" value="" style="ime-mode:active;width:98%;" /></td>  
	        <!-- 요청일-->
	        <th class="alignL pdL10">${menu.LN00093}</th>     
	        <td class="alignL pdL5">  
	            <font><input type="text" id="reg_STR_DT" name="reg_STR_DT" value=""	class="input_off datePicker stext" size="8"
					style="width:63px;text-align: center;" maxlength="15" >
				</font>
				~
				<font><input type="text" id="reg_END_DT" name="reg_END_DT" value=""	class="input_off datePicker stext" size="8"
					style="width: 63px;text-align: center;" maxlength="15">
				</font>
	         </td>
	        <!-- 검토자 -->
	        <th class="alignL pdL10">검토자</th>
	        <td class= "alignL pdL5"><input type="text" class="text" id="reviewerName" name="reviewerName" value="" style="ime-mode:active;width:98%;" /></td>  
	        <!-- 검토일-->
	        <th class="alignL pdL10">검토일</th>     
	        <td class="alignL pdL5 last">     
	            <font><input type="text" id="reviewe_STR_DT" name="reviewe_STR_DT" value="${reviewe_STR_DT}"	class="input_off datePicker stext" size="8"
					style="width:63px;text-align: center;" 	maxlength="15" >
				</font>
				~
				<font><input type="text" id="reviewe_END_DT" name="reviewe_END_DT" value="${reviewe_END_DT}"	class="input_off datePicker stext" size="8"
					style="width: 63px;text-align: center;" maxlength="15">
				</font>
	         </td>
	    </tr>
	    <tr>
	    	<!-- 승인자 -->
	        <th class="alignL pdL10 viewline">승인자</th>
	        <td class="alignL pdL5"><input type="text" class="text" id="approverName" name="approverName" value="" style="ime-mode:active;width:98%;" /></td>  
	        <!-- 승인일-->
	        <th class="alignL pdL10">승인일</th>     
	        <td class="alignL pdL5">     
	            <font><input type="text" id="approval_STR_DT" name="approval_STR_DT" value="${approval_STR_DT}"	class="input_off datePicker stext" size="8"
					style="width:63px;text-align: center;" maxlength="15" >
				</font>
				~
				<font><input type="text" id="approval_END_DT" name="approval_END_DT" value="${approval_END_DT}"	class="input_off datePicker stext" size="8"
					style="width: 63px;text-align: center;" maxlength="15">
				</font>
	        </td>
	        <!-- 실행자 -->
	        <th class="alignL pdL10">실행자</th>
	        <td class="alignL pdL5">
	        	<input type="text" class="text" id="CTUserNM" name="CTUserNM" value="" style="ime-mode:active;width:98%;" />
	        </td>  
	        <!-- 실행일-->
	        <th class="alignL pdL10">실행일</th>     
	        <td class="alignL pdL5 last">     
	            <font><input type="text" id="oprAp_STR_DT" name="oprAp_STR_DT" value="${oprAp_STR_DT}"	class="input_off datePicker stext" size="8"
					style="width:63px;text-align: center;" maxlength="15" >
				</font>
				~
				<font><input type="text" id="oprAp_END_DT" name="oprAp_END_DT" value="${oprAp_END_DT}"	class="input_off datePicker stext" size="8"
					style="width: 63px;text-align: center;" maxlength="15">
				</font>
	        </td>
	    </tr>	 
	    <tr>
	    	<th class="alignL pdL10 viewline" >${menu.LN00002}</th>     
		    <td colspan="3" class="alignL pdL5"><input type="text" class="text" id="subject" name="subject" value="${subject}" style="ime-mode:active;width:98%;" /></td>
		    <!-- 담당조직 -->
	        <th class="alignL pdL10">담당조직</th>
	        <td class="alignL pdL5">     
		        <select id="regTeam" Name="regTeam" style="width:98%">
		            <option value=''>Select</option>
		        </select>
	        </td>
	    	<!-- 긴급 -->
	        <th class="alignL pdL10">긴급</th>
	        <td class="alignL pdL5 last">
	        	<!-- <input type="checkbox" id="urgencyYN" name="urgencyYN" > -->
	        	<select id="urgencyYN" name="urgencyYN" style="width:98%">
	        		<option value="">ALL</option>
	        		<option value="Y">  Y (긴급)</option>
	        		<option value="N">  N (일반)</option>
	        	</select>
	        </td>
	    </tr>
   	</table>

<!-- 	<div style="overflow:auto;overflow-x:hidden;"> -->
		<div class="countList pdT5 pdB5">
		     <li class="count">Total  <span id="TOT_CNT"></span></li>
		    <li class="floatR">
	    	   	<c:if test="${sessionScope.loginInfo.sessionAuthLev eq 1}" >
		    	&nbsp;<span id="btnAdd"  class="btn_pack small icon"> <span class="edit"></span><input value="EXECUTE CTS IF" type="submit" onclick="exeCTSInsertIF();"></span>
				</c:if>		
				&nbsp;<span id="viewSearch" class="btn_pack medium icon"><span class="search"></span><input value="Search" type="submit" onclick="doSearchctrList();" style="cursor:hand;"></span>
		   		&nbsp;<input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_search_clean.png" value="Clear" style="cursor:pointer;" onclick="fnInitSearchArea();">
		    </li>
		</div>
	
		<div id="gridDiv" class="mgB10 clear" style="align:center">
			<div id="grdGridArea" style="width:100%;"></div>
		</div>

		<!-- START :: PAGING -->
		<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
		<!-- END :: PAGING -->	

<!--  	</div> -->
</form>
<form name="CTSIFFrm" id="CTSIFFrm" action="" method="post" onsubmit="return false;" >
</form>
<iframe name="subFrame" id="subFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
