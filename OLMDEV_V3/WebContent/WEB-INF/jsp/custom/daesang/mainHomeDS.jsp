<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<style>
/* body {background:url("${root}${HTML_IMG_DIR}/bg_mainhomelayer.png")} */
.row {padding:10px;border:0px solid #000}
.col1 {float: left;width: 49%; margin: 0; padding:12px 0 0 0; clear: both;margin:0 5px;}
.coltop {border:1px solid #d7d7d7;background:url("${root}${HTML_IMG_DIR}/bg_mainhome_title.png");height:34px;line-height:34px;vertical-align:middle;}
.colbottom{border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;background:#fff;}
.coltop01 {border-top:1px solid #d7d7d7;border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;background:#fff;;height:34px;line-height:34px;vertical-align:middle;}
.colbottom01{border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;background:#fff;height:230px;}
.tbl_nstyle, .tbl_nstyle th, .tbl_nstyle td{border:0px solid #000;margin:0 auto;}
.tbl_nstyle{width:100%;font-family:'Malgun Gothic' !important;font-size:12px;text-align:center}
.tbl_nstyle td{padding:5px;;text-align:left;vertical-align:middle;}
.tbl_nstyle td.hr1 {border-top:#ddd dotted 1px;border-left:#FFFFFF solid 0px;border-right:#FFFFFF solid 0px;font-size:1;line-height:0;height:1px;  padding-top:0px;}

.nstylewrap{float: left;width: 100%;margin:0 auto;}
.nstyleR{margin-left: 155px;}
.nstyleL{float: left;width: 135px; margin-left: -98%;padding:10px}
.col2 {width: 47%;margin-left:50%; padding: 0; }
.noform{border:1px solid #d7d7d7;background:#ffff;}
.mainimg {margin: 0 auto;}
</style>
<script type="text/javascript">
    var noticType;var menuIndex = "1 3 4";  
    
    $(document).ready(function(){
    	var screenType = parent.$("#screenType").val();
    	if(screenType == "srRqst"){ // 외부에서 SR등록 호출시 실행 
    		parent.$("#screenType").val("");
    		fnGetSRCreate();
    	}
        setNoticFrame("1"); 
        setProjectFrame();
        setProcessFrame();        
        setProgramFrame(); 

//        window.onresize = function() {
	    	var winWidth = setWindowWidth();
//	    	if(winWidth > 1400) {
 	    		$('#mainimg').css('width', '80%');
 	    		$('#mainimg').css('height', '80%');
// 		    } else {
//	    		$('#mainimg').css('width', '90%');
//	    		$('#mainimg').css('height', '90%');
//	    	}
//		}; 

		if(winWidth < 1590) {
			$(".nstylewrap").removeClass("mgT5");
			$(".colbottom01").css("height","192px");
		}
       
    });
    
    function setWindowWidth(){
		var size = window.innerWidth;
		var width = 0;
		if( size == null || size == undefined){
			width = document.body.clientWidth;
		}else{
			width=window.innerWidth;
		}return width;
	}
    
    function setNoticFrame(avg){
    	$("#boardMgtID").val(avg);
        var url = "mainBoardList.do";   
        var target = "subBrdFrame";var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&BoardMgtID="+avg;ajaxPage(url, data, target);
        setSubTab(menuIndex, avg, "pli");
    }
    function setProjectFrame(avg){
        var url = "mainProjectGroupList.do";   
        var target = "subPrjFrame";var data = "";ajaxPage(url, data, target);
    }
    function setProcessFrame(avg){
        var url = "mainSttProcess.do";   
        var target = "subPrcFrame";var data = "";ajaxPage(url, data, target);
    }
    function setProgramFrame(avg){
        //var url = "mainSttProgram.do";  
        var url = "mainChangeSetStatistics.do";
        var target = "subPrgFrame";var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";ajaxPage(url, data, target);
    } 
    
    function fileNameClick(avg1, avg2, avg3, avg4, avg5){
    	var originalFileName = new Array();
    	var sysFileName = new Array();
    	var seq = new Array();
    	sysFileName[0] =  avg3 + avg1;
    	originalFileName[0] =  avg3;
    	seq[0] = avg4;
    	var url  = "fileDownload.do?seq="+seq+"&scrnType="+avg5;
    	ajaxSubmitNoAdd(document.mainLayerFrm, url,"saveFrame");
    }
    
    function fnSearch(){
    	var radioObj = document.all("searchMode");
		var searchMode = "";
		for (var i = 0; i < radioObj.length; i++) {
			if (radioObj[i].checked) {
				searchMode = radioObj[i].value;
				break;
			}
		}
		if(searchMode == "1"){
			fnSearchProcess(); // Process
		}else{ // 2
			fnSearchFile(); // File
		}
    }
    
    function fnSearchProcess(){
    	var searchValue = $("#searchValue").val();
    	var url = "searchList.do";
    	var target = "mainLayerFrm";
    	var data = "itemTypeCode=OJ00001&screenType=main&searchKey=AT00001&searchValue="+searchValue; 
    	ajaxPage(url, data, target);
    }
    
    function fnSearchFile(avg){
    	var searchValue = $("#searchValue").val();
    	var url = "goDocumentList.do";
    	var target = "mainLayerFrm";
    	var data = "screenType=main&searchKey=Name&searchValue="+searchValue;
    	// LF Use ** var data = "screenType=main&fltpCode=FLTP012&searchKey=Name&searchValue="+searchValue;
    	ajaxPage(url, data, target);
    }
    
    function fnClickMoreBoard(){
    	var boardMgtID = $("#boardMgtID").val();
    	if(boardMgtID ==""){boardMgtID="1";}
    	parent.clickMainMenu('BOARD', 'BOARD','','','','','', boardMgtID);
    }
    
    // SR 등록 페이지 바로 가기 
    function fnGetSRCreate(){
    	$('#layerBody').css('background-image', 'url(${root}${HTML_IMG_DIR}/blank.png)');
    	var url = "srMgt.do";
    	var target = "mainLayerFrm";
    	var data = "screenType=srRqst&srType=ITSP&srMode=REG";
    	ajaxPage(url, data, target);
    }
    
</script>
</head>

<BODY id="layerBody" name="layerBody" >
<div class="noform" id="mainLayer">
<form name="mainLayerFrm" id="mainLayerFrm" enctype="multipart/form-data" method="post" action="#" onsubmit="return false;">
<input id="chkSearch" type="hidden" value="false"></input>
<input id="boardMgtID" type="hidden" value="" >
<div class="row">
	<div class="col1">
		<div align="center">
		<img id="mainimg" src="${root}${HTML_IMG_DIR}/img_DS_main.jpg" style="margin-top:1.3%;">
		</div>		
	</div>	
	<div class="col2">
		<div>
			<ul class="mgT20">
				<li style="display: inline-block;">
					<input type="radio" name="searchMode" value=1 checked="checked">&nbsp;&nbsp;					
					<span style="vertical-align:middle;padding-right:9px;"><b>Process</b></span>
					<input type="radio" name="searchMode" value=2>&nbsp;&nbsp;
					<span style="vertical-align:middle;padding-right:22px;"><b>File</b></span>
				</li>
				<li style="display: inline-block;">
					<input type="text" id="searchValue" name="searchValue" class="text" style="width:400px">
					<input id="btnSearch" type="image" class="image mgL10" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="Search" style="display:inline-block;" Onclick="fnSearch();">
				</li>
			</ul>
		</div>
		<!--  Business Process -->
		<div class="coltop mgT20"><span><img src="${root}${HTML_IMG_DIR}/icon_mainhome01.png"></span><span class="pdL20"><b>Business Process</b></span><span class="floatR pdR10"></span></div>
		<div class="colbottom">
		    <div id="subPrcFrame"  name="subPrcFrame" width="98%" height="100%"></div>
		</div>
		<!--  공지사항/자료실 -->
		<div class="coltop01 pdT10 mgT20">
			<div class="SubinfoTabs mgR10">
				<ul>
					<li id="pli1" class="on" onclick="setNoticFrame('1');"><a><span>${menu.LN00001}</span></a></li>
					<li id="pli3" onclick="setNoticFrame('3');"><a><span>${menu.LN00029}</span></a></li>
					<li id="pli4" onclick="setNoticFrame('4');"><a><span>${menu.LN00215}</span></a></li>
				</ul>
				    <span class="floatR pdR20" onClick="javascript:fnClickMoreBoard();"><img src="${root}${HTML_IMG_DIR}/more.png"></span>
			</div>
		</div>
		<div class="colbottom01">
			<div class="nstylewrap">
			<div class="mgR10 mgL10"><div id="subBrdFrame"  name="subBrdFrame" width="98%" height="100%"></div>
		</div>
	</div>
<%-- 	<div class="nstyleL"><img src="${root}${HTML_IMG_DIR}/img_nstyle01.png"  OnClick="fnGetSRCreate();"></div> --%>
</div>

    
		</div>
</div>

</form>
</div>
<iframe id="saveFrame" name="saveFrame" style="width:0px;height:0px;display:none;"></iframe>
</BODY>