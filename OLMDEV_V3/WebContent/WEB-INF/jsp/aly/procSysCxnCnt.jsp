<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:url value="/" var="root"/>

<!-- 1. Include JSP -->
<link rel="STYLESHEET" type="text/css" href="dhtmlxchart.css">
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script>
	var p_gridArea;				//그리드 전역변수
	var grid_skin = "dhx_brd";
	var isMainMenu = "${isMainMenu}";
	
	$(document).ready(function(){
		// 초기 표시 화면 크기 조정 
		$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 110)+"px;");
		// 화면 크기 조정
		window.onresize = function() {
			$("#grdGridArea").attr("style","height:"+(setWindowHeight() - 110)+"px;");
		};
		
		$("#excel").click(function(){
			doExcel();
			return false;
		});
		
		// 검색 버튼 클릭
		$('.searchList').click(function(){
			var url = "procSysCxnCnt.do";
			var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
			
			/* [Dimension] 조건 선택값 */
			if ($("#dimTypeId").val() != "") {
				data = data + "&DimTypeID=" + $("#dimTypeId").val();
				if ($("#dimValueId").val() != "") {
					data = data + "&DimValueID=" + $("#dimValueId").val();
				}
			}
			
			var target = "help_content";
			ajaxPage(url, data, target);
		});
		
		// DimensionTypeList change event
		$('#dimTypeId').change(function(){changeDimValue($(this).val());});
		if ("${DimTypeID}" != "") {
			changeDimValue("${DimTypeID}");
		}
		
		//GRID
		gridInit();	
		doSearchList();
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}

	//===============================================================================
	// BEGIN ::: GRID
	//그리드 초기화
	function gridInit(){		
		var d = setGridData();		
		
		p_gridArea = fnNewInitGridMultirowHeader("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");//path to images required by grid
		
	}
	function setGridData(){
		var result = new Object();
		var widths = "";
		var sorting = "";
		var aligns = "";
		var aCnt = "${cnt}";
		var colWidth = (document.body.clientWidth * 0.65) / aCnt;
		
		for (var i = 0; i < aCnt; i++) {
			widths = widths + "," + colWidth;
			sorting = sorting + ",str";
			aligns = aligns + ",center";
		}
		
		result.title = "";
		result.key = "";
		result.header = "${level1Name}";
		
		if(isMainMenu != "Y") {
			result.widths = "100" + widths;
		}
		
		result.sorting = "str" + sorting;
		result.aligns = "center" + aligns;
		result.data = "";
		return result;
	}
	
	//조회
	function doSearchList(){
		p_gridArea.enableRowspan(true);
		p_gridArea.enableColSpan(true);
		p_gridArea.load("${root}" + "${xmlFilName}");
	}
	
	// END ::: GRID	
	//===============================================================================
		
		
	//===============================================================================
	// BEGIN ::: EXCEL
	function doExcel() {		
		p_gridArea.toExcel("${root}excelGenerate");
	}

	// END ::: EXCEL
	//===============================================================================
	
	// [Process Update] Click
	function processUpdate() {
		var url = "procSysCxnCnt.do";
		var data = "eventMode=twCXNUpdate&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		var target = "help_content";
		ajaxPage(url, data, target);
	}
	
	// [dimValue option] 설정
	function changeDimValue(avg){
		var url    = "getDimValueSelectOption.do"; // 요청이 날라가는 주소
		var data   = "dimTypeId="+avg; //파라미터들
		var target = "dimValueId";            // selectBox id
		var defaultValue = "${DimValueID}";              // 초기에 세팅되고자 하는 값
		var isAll  = "select";    // "select" 일 경우 선택, "true" 일 경우 전체로 표시
		ajaxSelect(url, data, target, defaultValue, isAll);
		setTimeout(appendOption, 1000);
	}
	
	function appendOption(){
		$("#dimValueId").val("${DimValueID}").attr("selected", "selected");
	}
	
</script>

<div class="pdL10 pdR10">
	<div class="cop_hdtitle" style="width:100%;"><h3 style="padding: 6px 0;     border-bottom: 1px solid #ccc;"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;Process Statistics</h3></div>
	<div class="child_search01">
		<li class="pdL20">
			<select id="dimTypeId" style="width:120px">
	    		<option value=''>Select</option>
	         	   		<c:forEach var="i" items="${dimTypeList}">
	                 		<option value="${i.DimTypeID}" <c:if test="${DimTypeID == i.DimTypeID}">selected="selected"</c:if>>${i.DimTypeName}</option>
	         	    	</c:forEach>
			</select>
			<select id="dimValueId" <c:if test="${isMainMenu eq 'Y' }"> style="width:120px;" </c:if> >
				<option value="">Select</option>
			</select>	
		</li>
		<li>
			<input type="image" class="image searchList" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="검색" />
		</li>
		<li class="floatR pdR20">
			<span class="btn_pack small icon"><span class="edit"></span><input value="Process Update" type="submit" onclick="processUpdate();"></span>
			<span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
		</li>
	</div>
	
	<form name="itemStatisticsForm" id="itemStatisticsForm" action="" method="post" >	
		<div id="gridDiv" class="mgB10 mgT5">
			<div id="grdGridArea" style="width:100%"></div>
		</div>
		<!-- END :: LIST_GRID -->				
			
	</form>
</div>