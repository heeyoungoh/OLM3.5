<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00039" var="WM00039"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00004" var="CM00004"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00119" var="WM00119" arguments="1000"/>

<script type="text/javascript">
	var p_gridArea;				// 그리드 전역변수(Project)
	
	$(document).ready(function() {	
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 280)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 280)+"px;");
		};
		
// 		setTimeout(function() { 
			gridPjtInit();
			doSearchPjtList();
// 		},1000 );
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
	
	/* Project List */
	function gridPjtInit(){
		var d = setGridPjtData();
		p_gridArea = fnNewInitGrid("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");
		
		fnSetColType(p_gridArea, 1, "ch");
		p_gridArea.setColumnHidden(0, true);
		p_gridArea.setColumnHidden(1, true);
		p_gridArea.setColumnHidden(11, true);
		p_gridArea.setColumnHidden(12, true);
		p_gridArea.setColumnHidden(13, true);
		p_gridArea.setColumnHidden(14, true);
		p_gridArea.setColumnHidden(15, true);
		p_gridArea.setColumnHidden(16, true);
		p_gridArea.setColumnHidden(17, true);
		p_gridArea.setColumnHidden(18, true);
		p_gridArea.setColumnHidden(19, true);
		p_gridArea.setColumnHidden(20, true);
		p_gridArea.setColumnHidden(22, true);

		
		p_gridArea.attachEvent("onRowSelect", function(id,ind){gridOnPjtRowSelect(id,ind);});
		p_gridArea.enablePaging(true,30,null,"pagingArea",true,"recInfoArea");
		p_gridArea.setPagingSkin("bricks");
		p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
	}
	
	function setGridPjtData(){
		var result = new Object();
		//var status = "'APRV','CLS'";
		var mainMenu = "${mainMenu}";
		
		result.title = "${title}";
		result.key = "project_SQL.getSetProjectListForCsr";
		result.header = "${menu.LN00024},#master_checkbox,${menu.LN00129},${menu.LN00028},${menu.LN00131},${menu.LN00153},${menu.LN00266},${menu.LN00013},${menu.LN00062},${menu.LN00067},${menu.LN00027},,,,,,,,,,,${menu.LN00139},";
		result.cols = "CHK|ProjectCode|ProjectName|ParentName|AuthorTeamName|AuthorName|CreationTime|DueDate|PriorityName|StatusName|WFName|CurWFStepName|ProjectID|ProjectType|WFID|AuthorID|Creator|PjtMemberIDs|CNGT_CNT|Status|ChangeStatus|ParentID";
		result.widths = "30,30,100,*,120,100,100,80,80,100,150,0,0,0,0,0,0,0,0,0,0,50";
		result.sorting = "int,int,str,str,str,str,str,str,str,str,str,str,str,int,int,int,int,str,str,str,str,str,str";
		result.aligns = "center,center,left,left,left,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center";
		result.data = "filter=CSR"
					+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}&memberId=${memberID}"
// 					+ "&screenType=" + screenType		
					+ "&pageNum=" + $("#currPage").val();
					
		
		return result;
	}
	
	function doSearchPjtList(){
		var d = setGridPjtData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
	}
	
	// 그리드ROW선택시 : 변경오더 조회 화면으로 이동
	function gridOnPjtRowSelect(id, ind){
		var projectID = p_gridArea.cells(id, 13).getValue();
		var status = p_gridArea.cells(id, 20).getValue();
		var pjtCreator = p_gridArea.cells(id, 17).getValue();
		var authorId = p_gridArea.cells(id, 16).getValue();
		
		var screenMode = "V";
		var mainMenu = "${mainMenu}";
		var parentID =  p_gridArea.cells(id, 22).getValue();
		var url = "csrDetailPop.do?ProjectID=" + projectID + "&screenMode=" + screenMode + "&mainMenu=" + mainMenu;
				
		var w = 1200;
		var h = 800;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=yes,resizable=yes,scrollbars=yes");
	}
</script>
<div id="gridPjtDiv" style="width:100%;" class="clear mgB10 mgT20" >
	<div id="grdGridArea" ></div>
</div>
<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
<iframe name="blankFrame" id="blankFrame" style="width:0px;height:0px; display:none;"></iframe>
