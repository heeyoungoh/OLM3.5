<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<script src="${root}cmm/js/jquery/jquery-1.9.1.min.js" type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/jquery.sumoselect.js" type="text/javascript"></script> 
<link rel="stylesheet" type="text/css" href="${root}cmm/common/sumoselect.css"/>
  
<script type="text/javascript">
	var p_gridArea;				
	
	$(document).ready(function() {	
		
		// 초기 표시 화면 크기 조정 
		$("#grdCtsGridArea").attr("style","height:"+(setWindowHeight() - 290)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdCtsGridArea").attr("style","height:"+(setWindowHeight() - 290)+"px;");
		};
		

		$("#excel").click(function(){p_gridArea.toExcel("${root}excelGenerate");});		
		$("input.datePicker").each(generateDatePicker);
		$('.layout').click(function(){
			var changeLayout = $(this).attr('alt');
			setLayout(changeLayout);
		});
					
		window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
        //window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
        //window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
        //window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });
       
		
		gridCtsInit();
		doSearchCtsList();
		
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
	
	/* CTS List */
	function gridCtsInit(){
		var d = setGridPjtData();
		p_gridArea = fnNewInitGrid("grdCtsGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		fnSetColType(p_gridArea, 1, "ch");
		p_gridArea.setColumnHidden(8, true);
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnPjtRowSelect(id,ind);});		
		p_gridArea.enablePaging(true,10,10,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridPjtData(){
		var result = new Object();		
		result.title   = "${title}";
		result.key     = "sk_cts_SQL.getCTS";
		result.header  = ",#master_checkbox,변경요청ID,시스템,서브시스템,긴급,요청유형,요청제목,요청내용,진행상태,요청자명,요청일,적용시스템/Client,1차 검토자,1차 검토일,승인자명,"; 
		result.cols    = "CHK|CtsReqID|SystemNM|SubSystemNM|UrgentYN|ReqTypeNM|ReqSubject|ReqContent|ProgressNM|ReqLoginNM|ReqDate|ApplySystemClient|FtReviewLoginNM|FtReviewDate|ApproveLoginNM|ProgressCD";
		result.widths  = "0,30,100,65,70,60,85,400,400,65,60,70,100,70,70,60,0";
		result.sorting = "str,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns  = "center,center,center,center,center,center,center,left,left,center,center,center,center,center,center,center,center";
		result.data    = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					     + "&pageNum=" + $("#currPage").val()
					     ;
							
		/* 검색 조건 설정 */		
		// 시스템
		if($("#systemCD").val() != '' & $("#systemCD").val() != null){
			result.data = result.data +"&systemCD=" + $("#systemCD").val();
		}	
		// 서브시스템
		if($("#subSystemCD").val() != '' & $("#subSystemCD").val() != null){
			/*
			var subSystemCDVal = "";
			var subSystemCD = $("#subSystemCD").val()+"";
			
			var subSystemCDArr = subSystemCD.split(",");
			for(var i=0; i<subSystemCDArr.length; i++){
				subSystemCDVal = subSystemCDVal + "'"+ subSystemCDArr[i]+"'";
				if(i != subSystemCDArr.length-1){
					subSystemCDVal = subSystemCDVal + ",";
				}
			}
			
			result.data = result.data +"&subSystemCD=" + subSystemCDVal;
			*/
			
			result.data = result.data +"&subSystemCD=" + $("#subSystemCD").val();
		}	
		
		// 요청유형
		if($("#reqTypeCD").val() != '' & $("#reqTypeCD").val() != null){
			/*
			var reqTypeCDVal = "";
			var reqTypeCD = $("#reqTypeCD").val()+"";
			
			var reqTypeCDArr = reqTypeCD.split(",");
			for(var i=0; i<reqTypeCDArr.length; i++){
				reqTypeCDVal = reqTypeCDVal + "'"+ reqTypeCDArr[i]+"'";
				if(i != reqTypeCDArr.length-1){
					reqTypeCDVal = reqTypeCDVal + ",";
				}
			}
			//alert(reqTypeCDVal);
			
			result.data = result.data +"&reqTypeCD=" + reqTypeCDVal;
			*/
			result.data = result.data +"&reqTypeCD=" + $("#reqTypeCD").val();
		}	
		// 진행상태
		if($("#progressCD").val() != '' & $("#progressCD").val() != null){
			result.data = result.data +"&progressCD=" + $("#progressCD").val();
		}	
		
		// 요청자
		if($("#reqLoginID").val() != '' & $("#reqLoginID").val() != null){
			result.data = result.data +"&reqLoginID=" + $("#reqLoginID").val();
		}	
		// 요청제목
		if($("#reqSubject").val() != '' & $("#reqSubject").val() != null){
			result.data = result.data +"&reqSubject=" + $("#reqSubject").val();
		}	
		// 조회 기간
		if($("#reqStartDT").val() != '' & $("#reqStartDT").val() != null){
			result.data = result.data + "&reqStartDT=" + $("#reqStartDT").val().replace(/-/g, "");
	        result.data = result.data + "&reqEndDT=" + $("#reqEndDT").val().replace(/-/g, "");
		}	
		return result;
	}
	
	/* List 조회 */
	function doSearchCtsList(){
		fnSearchCtsCount();		
		var d = setGridPjtData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data,false,"N");	
	}
	
	/* Count 조회 */
	function fnSearchCtsCount(){
		var param = "systemCD="    + $("#systemCD").val()
			      + "&progressCD=" + $("#progressCD").val()
			      + "&reqLoginID=" + $("#reqLoginID").val()
			      + "&reqSubject=" + $("#reqSubject").val()
			      + "&reqStartDT=" + $("#reqStartDT").val().replace(/-/g, "")
			      + "&reqEndDT="   + $("#reqEndDT").val().replace(/-/g, "")
		  	      ; 
		
		if($("#subSystemCD").val() != '' & $("#subSystemCD").val() != null){
			param = param + "&subSystemCD="+ $("#subSystemCD").val();
		}
		if($("#reqTypeCD").val() != '' & $("#reqTypeCD").val() != null){
			param = param + "&reqTypeCD="+ $("#reqTypeCD").val();
		}
		var url = "ctsCount.do";
		var target = "blankFrame";
		ajaxPage(url, param, target);
	} 
	
	function fnSetCtsCount(TOTAL_CNT,CD_01,CD_02,CD_03,CD_04,CD_06,CD_07,CD_08,CD_09){	
		$("#cntTotal").html(TOTAL_CNT); 
    	$("#cntCD01").html(CD_01); 
    	//$("#cntCD02").html(data.cntMap.CD_02); 
    	//$("#cntCD0304").html(data.cntMap.CD_03_04);
    	$("#cntCD03").html(CD_03);
    	$("#cntCD04").html(CD_04);
    	//$("#cntCD05").html(data.cntMap.CD_05); 
    	$("#cntCD06").html(CD_06); 
    	$("#cntCD07").html(CD_07); 
    	$("#cntCD08").html(CD_08);
    	$("#cntCD09").html(CD_09);
	}
	
	/* 상세 조회 */
	function gridOnPjtRowSelect(id, ind){
		var ctsReqID = p_gridArea.cells(id, 2).getValue();
		
	    fnOpenCTSDetailPopup(ctsReqID);			

	}
	
	/* CTS 신규 화면 */
	function fnOpenCTSNewPopup() {
		//var url = "ctsNew.do?";
		//var data = "s_itemID=100097&languageID=${sessionScope.loginInfo.sessionCurrLangType}"; 
		//var option = "dialogWidth:850px; dialogHeight:575px;";
	    //window.showModalDialog(url + data , self, option);
	    
	    var url = "ctsNew.do";
	    window.open(url,'ctsNew','width=850, height=575, left=300, top=300,scrollbar=yes,resizable=yes');
	}
	
	/* CTS 상세 화면 */
	function fnOpenCTSDetailPopup(ctsReqID) {
	    var url = "ctsDetail.do?CtsReqID="+ctsReqID;
	    window.open(url,'ctsDetail','width=850, height=575, left=300, top=300,scrollbar=yes,resizable=yes');
	}
	
	/* 요청자 조회 */
	function fnOpenSearchPopup(url){
		$('#reqLoginID').val("");
		$('#reqLoginNM').val("");
		window.open(url,'userPopup','width=300, height=300, left=300, top=300,scrollbar=yes,resizble=0');
	}
	
	function setSearchName(loginID,memberNM){
		$('#reqLoginID').val(loginID);
		$('#reqLoginNM').val(memberNM);
	}
	
	
	/* 조건 초기화 */
	function fnInitSearchArea(){
		$('#systemCD').val('');
		
		//$('#subSystemCD').val('');
		//$('#reqTypeCD').val('');
		
		$('#subSystemCD')[0].sumo.unSelectItem(0);
		$('#subSystemCD')[0].sumo.unSelectItem(1);
		$('#subSystemCD')[0].sumo.unSelectItem(2);
		$('#subSystemCD')[0].sumo.unSelectItem(3);
		$('#subSystemCD')[0].sumo.unSelectItem(4);
		$('#subSystemCD')[0].sumo.unSelectItem(5);
		$('#subSystemCD')[0].sumo.unSelectItem(6);
		$('#subSystemCD')[0].sumo.unSelectItem(7);
		$('#subSystemCD')[0].sumo.unSelectItem(8);
		$('#subSystemCD')[0].sumo.unSelectItem(9);
		$('#subSystemCD')[0].sumo.unSelectItem(10);
		$('#subSystemCD')[0].sumo.unSelectItem(11);
		
		
		$('#reqTypeCD')[0].sumo.unSelectItem(0);
		$('#reqTypeCD')[0].sumo.unSelectItem(1);
		$('#reqTypeCD')[0].sumo.unSelectItem(2);
		$('#reqTypeCD')[0].sumo.unSelectItem(3);
		$('#reqTypeCD')[0].sumo.unSelectItem(4);
		
		
		$('#progressCD').val('');
		
		$('#reqLoginID').val('');
		$('#reqLoginNM').val('');
		
		$('#reqSubject').val('');
		
		$('#reqStartDT').val("${beforeYmd}");
		$('#reqEndDT').val("${thisYmd}");
		
	}
	
	function fnDeleteCts(){
		if(p_gridArea.getCheckedRows(1).length == 0){
			alert("항목을 한 개 이상 선택하여 주십시오.");
			return;
		}
		
		if(confirm("삭제하시겠습니까?")){		
			var checkedRows = p_gridArea.getCheckedRows(1).split(",");	
			var ctsReqIDs = "";
			var prgCD = "";
			
			for(var i = 0 ; i < checkedRows.length; i++ ){
				prgCD = p_gridArea.cells(checkedRows[i], 16).getValue();
				//alert(prgCD);
				if(prgCD != "01"){
					alert("검토 요청 이후는 삭제할 수 없습니다.");
					return;
				}
				
				
				if (ctsReqIDs == "") {
					ctsReqIDs = p_gridArea.cells(checkedRows[i], 2).getValue();
				} else {
					ctsReqIDs = ctsReqIDs + "," + p_gridArea.cells(checkedRows[i], 2).getValue();
				}
			}
			
			var url    = "ctsDelete.do";
			var data   = "ctsReqIDs=" + ctsReqIDs;
			var target = "blankFrame";		
				
			ajaxPage(url, data, target);
		}
		
		
	}
	
	/* Excel Download */
	function fnDownloadExcel() {	
		p_gridArea.setColumnHidden(8, false);
		p_gridArea.toExcel("${root}excelGenerate");
		p_gridArea.setColumnHidden(8, true);
	}
</script>

<style type="text/css">
    body{font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color:#444;font-size:13px;}
    p,div,ul,li{padding:0px; margin:0px;}
</style>
</head>

<body>
<form name="changeReqListFrm" id="changeReqListFrm" action="#" method="post" onsubmit="return false;" >
	<input type="hidden" id="currPage" name="currPage" value="${currPage}"></input> 
	<input type="hidden" id="reqLoginID" name="reqLoginID" value="${sessionScope.loginInfo.sessionLoginId}" />

<div class="floatL msg" style="width:100%;"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;CTS관리</div>
<BR></BR>

<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_blue01"  id="search">
	<colgroup>
	    <col width="10%">
	    <col width="20%">
	    <col width="10%">
	    <col width="20%">
	    <col width="10%">
	    <col>
    </colgroup>
    <tr>
       	<th>시스템</th>
        <td class="alignL">     
	       	<select id="systemCD" Name="systemCD" style="width:100px">
	           	<option value=''>${menu.LN00113}</option>
	           	<c:forEach var="i" items="${systemList}">
	               	<option value="${i.ID}">${i.NAME}</option>
	           	</c:forEach>
	       	</select>
       	</td>

        <th>서브시스템</th>
        <td class="alignL">     
	       <select id="subSystemCD" Name="subSystemCD" multiple="multiple" placeholder="${menu.LN00113}" class="SlectBox" style="width:100px">
				<c:forEach var="i" items="${subSystemList}">
					<option value="${i.ID}">${i.NAME}</option>
				</c:forEach>
	       </select>
		</td>
		
        <th>  요청유형</th>
        <td class="alignL last">     
	       <select id="reqTypeCD" Name="reqTypeCD" multiple="multiple" placeholder="${menu.LN00113}" class="SlectBox" style="width:100px">
	        	<c:forEach var="i" items="${reqTypeList}">
	            	<option value="${i.ID}">${i.NAME}</option>
	            </c:forEach>
	       </select>
         </td>
	</tr>    

    <tr>
        <th> 진행상태</th>
        <td class="alignL">               
            <select id="progressCD" Name="progressCD" style="width:100px">
	            <option value=''>${menu.LN00113}</option>
	            <c:forEach var="i" items="${progressList}">
		             <option value="${i.ID}">${i.NAME}</option>
		        </c:forEach>
            </select>
        </td>
        <th> 요청자</th>
        <td class="alignL">               
            <input type="text" class="stext"  id="reqLoginNM" name="reqLoginNM" readonly="readonly" onclick="fnOpenSearchPopup('searchNamePopFromMDM.do')" value="${sessionScope.loginInfo.sessionUserNm}" style="width:150px;ime-mode:active;"/>
        </td>
        <th> 요청제목</th>
        <td class="alignL last">               
            <input type="text" id="reqSubject" name="reqSubject" value="" class="stext" style="width:200px;ime-mode:active;" onkeydown="if (event.keyCode == 13){ doSearchCtsList();return false;}">
        </td>
    </tr>   

    <tr>
        <th>요청일자</th>
        <td class="alignL " colspan="2">     
            <font><input type="text" id="reqStartDT" name="reqStartDT" value="${beforeYmd}"	class="input_off datePicker stext" size="8"
				style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10" >
			</font>			
			~
			<font><input type="text" id=reqEndDT	name="reqEndDT" value="${thisYmd}"	class="input_off datePicker stext" size="8"
				style="width: 70px;text-align: center;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
			</font>
		 </td>
         <td class="alignR pdR20 last" colspan="4">
               <span class="btn_pack medium icon"><span class="search"></span><input value="Search" type="button" onclick="doSearchCtsList()"></span>
               <span class="btn_pack medium icon"><span class="reload"></span><input value="Reset"  type="button" onclick="fnInitSearchArea()"></span>
         </td>
     </tr>  
     
</table>
	   

<!--<div class="floatL msg" style="width:100%;"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;CTS관리 내역 - 전체(0) / 생성(0) / 검토요청(0) / 승인요청(0) / 승인완료(0) / 작업완료(0) / 반려(0)</div>-->
<BR></BR>
<!--<div class="floatL" style="width:100%;">&nbsp;CTS관리 내역 - 전체(<span id="cntTotal"></span>) / 생성(<span id="cntCD01"></span>)  / 1차 검토(<span id="cntCD03"></span>) / 2차 검토(<span id="cntCD04"></span>) / 승인완료(<span id="cntCD06"></span>) / 작업완료(<span id="cntCD07"></span>)  / 종결(<span id="cntCD09"></span>) / 반려(<span id="cntCD08"></span>)</div>-->
<div class="floatL" style="width:100%;">&nbsp;CTS관리 내역 - 전체(<span id="cntTotal"></span>) / 생성(<span id="cntCD01"></span>)  / 1차 검토(<span id="cntCD03"></span>) / 승인완료(<span id="cntCD06"></span>) / 작업완료(<span id="cntCD07"></span>)  / 종결(<span id="cntCD09"></span>) / 반려(<span id="cntCD08"></span>)</div>
<div class="alignBTN">
	&nbsp;<span id="btnAdd"  class="btn_pack small icon"> <span class="add"></span>     <input value="신규"       type="submit" onclick="fnOpenCTSNewPopup()"></span>
	&nbsp;<span id="btnDel"  class="btn_pack small icon"> <span class="del"></span>     <input value="삭제"       type="submit" onclick="javascript:fnDeleteCts()"></span>
	&nbsp;<span id="btnDown" class="btn_pack medium icon"><span class="download"></span><input value="엑셀다운로드" type="submit" onclick="javascript:fnDownloadExcel()"></span>
	<!--
	&nbsp;<span id="btnAdd"  class="btn_pack small icon"> <span class="save"></span>    <input value="저장"       type="submit" onclick="javascript:alert('구현중')"></span>
	&nbsp;<span id="btnAdd"  class="btn_pack small icon"> <span class="add"></span>     <input value="일괄등록"    type="submit" onclick="javascript:alert('구현중')"></span>
	-->
</div>

<div class="countList">
    <li>Total  <span id="TOT_CNT"></span></li>
    <li class="floatR">&nbsp;</li>
</div>

<div id="gridPjtDiv" class="mgT10 mgB10 clear" style="align:center">
	<div id="grdCtsGridArea" style="height:300px; width:100%;"></div>
</div>
<!-- END :: LIST_GRID -->
<!-- START :: PAGING -->
<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
<!-- END :: PAGING -->	

</form>
<!-- START :: FRAME --> 		

<iframe name="blankFrame" id="blankFrame" src="about:blank" frameborder="0"></iframe> 

</body>
</html>