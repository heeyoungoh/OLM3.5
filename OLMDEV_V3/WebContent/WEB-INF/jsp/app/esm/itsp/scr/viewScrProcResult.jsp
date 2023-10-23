<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<script>
	var p_gridArea; //그리드 전역변수
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 600)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 600)+"px;");
		};
		
		gridInit();		
		doSearchList();
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	function doSearchList(){
		var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	function gridInit(){	
		var d = setGridData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
	}
	
	function setGridData(){
		var result = new Object();
		result.title = "${title}";
		result.key =  "scr_SQL.getSCRMbrRoleList";
		result.header = "${menu.LN00024},Name,Position,${menu.LN00202},MemberID,CNT,공수(M/D),업무설명";
		result.cols = "UserName|Position|TeamPath|MemberID|CNT|ActualManDay|RoleDescription";
		result.widths = "30,100,100,250,0,0,120,*";
		result.sorting = "int,str,str,str,str,str,int,str";
		result.aligns = "center,center,center,left,center,left,center,left";
		result.data = "scrId=${scrId}";
		return result;
	}

	function fnEditScrResult(){
		var url = "editScrProcResult.do";
		var data = "scrId=${scrId}"
		var target = "viewScrDiv";
		ajaxPage(url, data, target);
	}
</script>
<div id="viewScrDiv">
	<form name="scrResultFrm" id="scrResultFrm" action="#" method="post" onsubmit="return false;">
		<div class="floatC" style="overflow:auto;overflow-x:hidden;">
			<c:if test="${scrUserID eq sessionScope.loginInfo.sessionUserId && scrStatus eq 'APREL'}">
			<div class="floatR mgR10 mgB10">
				<span class="btn_pack small icon"><span class="edit"></span><input value="Edit" type="submit" onclick="fnEditScrResult()"></span>
			</div>
			</c:if>
			<table class="cb_module tbl_blue01 mgT10" style="table-layout:fixed;" width="98%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tr>
					<th class="alignL pdL10 viewline">종료코드</th>
					<td class="pdL10">${scrInfo.ClosingCodeName}</td>
					<th class="alignL pdL10 viewline">실제투입공수(M/D)</th>
					<td class="pdL10 viewline last">${scrInfo.ActualManDay}</td>
				</tr>
				<tr>
					<th class="alignL pdL10 viewline">처리내용</th>
					<td colspan=3 class="last"><textarea style="width:100%;height:50px;background: #fff;" readOnly="true">${scrInfo.ChangeNotice}</textarea></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<th class="alignL pdL10 viewline">실제투입인력</th> -->
<!-- 					<td colspan=3 style="border-right: 1px solid #ddd;"> -->
<!-- 						<div id="gridDiv" class="clear"> -->
<!-- 							<div id="grdGridArea" style="width:100%; border: none;"></div> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>
			<div class="mgT10">
				<h3 class="mgL10" style="padding: 6px 0;">실제투입인력</h3>
				<div id="gridDiv">
					<div id="grdGridArea" style="width:100%; border: none;"></div>
				</div>
			</div>
		</div>
	</form>
</div>