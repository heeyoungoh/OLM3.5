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
<link rel="stylesheet" type="text/css" href="<c:url value='/cmm/js/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css'/>">

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>

<script type="text/javascript">

	var gridArea;				//그리드 전역변수
	var skin = "dhx_skyblue";
	var elmItemID = "${elmItemID}";
	
	fnSelect('status','&languageID=${sessionScope.loginInfo.sessionCurrLangType}&category=INSTSTS','getDictionary','','Select');
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#gridArea").attr("style","height:"+(setWindowHeight() - 250)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#gridArea").attr("style","height:"+(setWindowHeight() - 250)+"px;");
		};		
		$("#excel").click(function(){
			doExcel();
		});
		
		gridInit();	
		doSearchList();
	});
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	function doSearchList(){
		var d = setGridData();
		fnLoadDhtmlxGridJson(gridArea, d.key, d.cols, d.data);
	}
	
	function gridInit(){	
		var d = setGridData();
		gridArea = fnNewInitGrid("gridArea", d);
		gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		gridArea.setColumnHidden(1, true);
		gridArea.setColumnHidden(9, true);
		gridArea.setColumnHidden(10, true);
		gridArea.setColumnHidden(11, true);
		gridArea.setColumnHidden(12, true);
		gridArea.attachEvent("onRowSelect", function(id,ind){gridOnRowSelect(id,ind);});						
		gridArea.enablePaging(true,20,10,"pagingArea",true,"recInfoArea");
		gridArea.setPagingSkin("bricks");
		gridArea.setColumnHidden(0, true);
		gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridData(){
		var result 	= new Object();
		result.title = "${title}";
		result.key= "instance_SQL.getProcInstList";
		result.header = "RNUM,Type,No,PLAN Name,${menu.LN00131},${menu.LN01006},${menu.LN00153} ,${menu.LN00027},${menu.LN00063},${menu.LN00062},${menu.LN00064},InstanceClass,ProcInstNo,${menu.LN00078}";
		result.cols = "ProcType|projectNo|ProcInstanceName|csrName|OwnerName|OwnerTeamName|StatusNM|StartDate|DueDate|EndDate|InstanceClass|ProcInstNo|CreationTime";
		result.widths = "50,100,140,*,200,120,150,100,90,90,90,40,30,100";
		result.sorting = "int,str,str,str,str,str,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,left,center,center,center,center,center,center,center,center,center,center";
		result.data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					 + "&instanceName="+$("#searchValue").val();
					 
		if(elmItemID != ""){
			result.data += "&elmItemID="+elmItemID;
		}else{
			result.data += "&processID=${masterItemID}";
		}
		if( $("#status").val() != ""){
			result.data += "&status="+$("#status").val();
		}
		
    	return result;
	}
	
	function gridOnRowSelect(id, ind){
		var procType = gridArea.cells(id,1).getValue();
		var instanceNo = gridArea.cells(id,12).getValue();
		var instanceClass = gridArea.cells(id, 11).getValue();
		
		var url = "viewTSDetail.do";
		var data = "&instanceNo="+instanceNo+"&instanceClass="+instanceClass+"&masterItemID=${masterItemID}&procType="+procType;
		var target = "instanceListDiv";	
		ajaxPage(url, data, target);
	}
	
	function fnSelectModelPop(){
		var url = "createTCInstPop.do?masterItemID=${masterItemID}";
		var w = 550;
		var h = 475;
		window.open(url, "", "width="+w+", height="+h+", top=300,left=500,toolbar=no,status=no,resizable=yes");
	}
	
	function fnRegistTCInst(){
		var url = "registerTS.do";
		var target = "instanceListDiv";
		var data = "masterItemID=${masterItemID}";
		ajaxPage(url, data, target);
	}
</script>
</head>
<body>
<div id="instanceListDiv" style="height:100%;">
<form name="plmFrm" id="plmFrm" action="" method="post" onsubmit="return false">
	<div class="child_search" >
		<li class="shortcut">
	 	 <img src="${root}${HTML_IMG_DIR}/bullet_blue.png"></img>&nbsp;&nbsp;<b>Test Plan List</b>
	   </li>
	</div>
	<div class="countList pdT10">
              <li class="count">Total  <span id="TOT_CNT"></span></li>
              <li class="pdL55 floatL">
		   		<b>${menu.LN00028}</b>&nbsp;&nbsp;
		   		<input type="text" id="searchValue" name="searchValue" value="${searchValue}" class="text" style="width:150px;ime-mode:active;">&nbsp;&nbsp;
		   		<b>${menu.LN00027}</b>&nbsp;&nbsp;
		   		<select style="width:150px;ime-mode:active;" id="status">
		   			<option value="">select</option>
		   		</select>
				<input type="image" class="image searchList" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="doSearchList()" value="Search"  style="cursor:pointer;" />
			</li>
			<li class="floatR">	   		
		  		 <c:if test="${sessionScope.loginInfo.sessionUserId ==  authorID}">
		    	<span class="btn_pack small icon"><span class="add"></span><input value="Register" type="submit" onclick="fnRegistTCInst()" style="cursor:hand;"></span>
				</c:if>
			</li>
     </div>
	<div id="gridDiv" class="mgB10 clear">
		<div id="gridArea" style="width: 98%"></div>
	</div>	
	<!-- START :: PAGING -->
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
	<!-- END :: PAGING -->		
</form>
</div>
<iframe name="subFrame" id="subFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</body></html>