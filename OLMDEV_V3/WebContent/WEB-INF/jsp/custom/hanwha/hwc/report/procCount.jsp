<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:url value="/" var="root"/>
<style>
		.even{
			background-color:#F6F0D6;
		}
		.uneven{
			background-color:;
		}
	</style>

<script>
	var p_gridArea;				//그리드 전역변수
	var grid_skin = "dhx_brd";
	$(document).ready(function(){
		gridInit();	
		doSearchList();
	});	
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}

	function gridInit(){		
		var d = setGridData();				
		p_gridArea = fnNewInitGridMultirowHeader("grdGridArea", d);
		p_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");//path to images required by grid
		p_gridArea.enableAlterCss("even","uneven");

	}
	
	function setGridData(){
		var result = new Object();
		result.header = "Mega Process,L2,L3,L4,L5,구축 진척도(L4기준),#cspan,#cspan,#cspan";
		result.attachHeader1 ="#rspan,#rspan,#rspan,#rspan,#rspan,N/A,작업 중,완료,승인" ;
		result.widths = "112,65,65,65,65,65,65,65,65";
		result.sorting = "str,str,str,str,str,str,str,str,str";
		result.aligns = "left,left,left,left,left,left,left,left,left";
		result.data = "";
		return result;
	}
	
	//조회
	function doSearchList(){
		p_gridArea.enableRowspan(true);
		p_gridArea.enableColSpan(true);
		p_gridArea.loadXML("${root}" + "${xmlFilName}");
	}
	
</script>

<form name="srRptFrm" id="srRptFrm" action="" method="post" >
	<div id="gridDiv" class="mgB10 mgT5">
		<div id="grdGridArea" style="width:100%;height:280px;"></div>
	</div>
</form>