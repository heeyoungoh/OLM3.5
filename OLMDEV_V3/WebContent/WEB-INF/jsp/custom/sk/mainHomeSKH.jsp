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
body {background:url("${root}${HTML_IMG_DIR}/bg_mainhomelayer.png")}
.row {padding:10px;border:0px solid #000}
.col1 {float: left;width: 49%; margin: 0; padding:12px 0 0 0; clear: both;margin:0 5px;}
.coltop {border:1px solid #d7d7d7;background:url("${root}${HTML_IMG_DIR}/bg_mainhome_title.png");height:34px;line-height:34px;vertical-align:middle;}
.colbottom{border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;background:#fff;}
.coltop01 {border-top:1px solid #d7d7d7;border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;background:#fff;;height:34px;line-height:34px;vertical-align:middle;}
.colbottom01{border-left:1px solid #d7d7d7;border-right:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;background:#fff;height:233px;}
.tbl_nstyle, .tbl_nstyle th, .tbl_nstyle td{border:0px solid #000;margin:0 auto;}
.tbl_nstyle{width:100%;font-family:'Malgun Gothic' !important;font-size:12px;text-align:center}
.tbl_nstyle td{padding:5px;;text-align:left;vertical-align:middle;}
.tbl_nstyle td.hr1 {border-top:#ddd dotted 1px;border-left:#FFFFFF solid 0px;border-right:#FFFFFF solid 0px;font-size:1;line-height:0;height:1px;  padding-top:0px;}

.nstylewrap{float: left;width: 98%;margin:0 auto;}
.nstyleR{margin-left: 155px;}
.nstyleL{float: left;width: 135px; margin-left: -98%;padding:10px}
.col2 {width: 48%;margin-left:50%; padding: 0; }
.noform{border:1px solid #d7d7d7;background:#ffff;}
</style>
<script type="text/javascript">
    var noticType;var menuIndex = "1 2";  
    
    $(document).ready(function(){
    	var screenType = parent.$("#screenType").val();
    	if(screenType == "srRqst"){ // 외부에서 SR등록 호출시 실행 
    		parent.$("#screenType").val("");
    		fnGetSRCreate();
    	}
        setNoticFrame("1"); 
        setProcessFrame();   
    });
    
    function setProcessFrame(){
        var url = "mainSttProcessSKH.do";   
        var target = "prcRMPSFrame";
        var data = "";
        ajaxPage(url, data, target);
    }
    
    function setNoticFrame(avg){
    	$("#boardMgtID").val(avg);
        var url = "mainBoardList.do";   
        var target = "subCngFrame";
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&noticType="+avg;
        ajaxPage(url, data, target);
        setSubTab(menuIndex, avg, "pli");
    }
   
    function fileNameClick(avg1, avg2, avg3, avg4, avg5){
    	var originalFileName = new Array();
    	var sysFileName = new Array();
    	var filePath = new Array();
    	var seq = new Array();
    	sysFileName[0] =  avg3 + avg1;
    	originalFileName[0] =  avg3;
    	filePath[0] = avg3;
    	seq[0] = avg4;
    	var url  = "fileDownload.do?sysFileName="+sysFileName+"&originalFileName="+encodeURIComponent(originalFileName)+"&filePath="+filePath+"&seq="+seq+"&scrnType="+avg5;
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
    	var data = "itemTypeCode=OJ00001&classCode=CL01005&screenType=main&searchKey=AT00001&searchValue="+searchValue; 
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
    	if(boardMgtID == "1"){
    		parent.clickMainMenu('BOARD', 'BOARD','','','','','', boardMgtID);
    	}else{ // ChangeSet Full Screen    		
        	var url = "changeInfoList.do";
        	var target = "mainLayer";
        	var data = "mainMenu=0&chgsts=MOD&screenType=mainSKH"; 
        	ajaxPage(url, data, target);
        	$('#layerBody').css('background-image', 'url(${root}${HTML_IMG_DIR}/blank.png)');
    	}
    }
    
    // SR 등록 페이지 바로 가기 
    function fnGetSRCreate(){
    	$('#layerBody').css('background-image', 'url(${root}${HTML_IMG_DIR}/blank.png)');
    	var url = "srMgt.do";
    	var target = "mainLayerFrm";
    	var data = "screenType=srRqst&srType=ITSP&srMode=REG";
    	ajaxPage(url, data, target);
    }
    
    function fnSetChangeSet(avg){
    	$("#boardMgtID").val(avg);
    	 var url = "mainCngStList.do";
         var target = "subCngFrame";
         var data = "";
         ajaxPage(url, data, target);
         setSubTab(menuIndex, avg, "pli");
    }
    
</script>
</head>
<BODY id="layerBody" name="layerBody" >
<div class="noform" id="mainLayer" style="padding: 5px 5px 5px 5px;">
<form name="mainLayerFrm" id="mainLayerFrm" enctype="multipart/form-data" method="post" action="#" onsubmit="return false;">
<input id="chkSearch" type="hidden" value="false"></input>
<input id="boardMgtID" type="hidden" value="" >
<div class="row">
	<div class="col1">
		<img src="${root}${HTML_IMG_DIR}/img_mainHome.png">
		<div>
			<ul>
				<li style="padding: 40px 0 0 60px;" >
					<input type="radio" name="searchMode" value=1 checked="checked">&nbsp;&nbsp;					
					<span style="vertical-align:middle;padding-right:9px;"><b>Process</b></span>
					<input type="radio" name="searchMode" value=2>&nbsp;&nbsp;
					<span style="vertical-align:middle;padding-right:22px;"><b>File</b></span>
				</li>
				<li style="padding: 20px 0 0 60px;margin-right:10px;" >
					<input type="text" id="searchValue" name="searchValue" class="text" style="width:350px">
					<input id="btnSearch" type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="Search" style="display:inline-block;" Onclick="fnSearch();">
				</li>
			</ul>
		</div>
	</div>	
	<div class="col2">
		<!--  Business Process -->
		<div class="coltop mgT10"><span><img src="${root}${HTML_IMG_DIR}/icon_mainhome01.png"></span><span class="pdL20"><b>Business Process</b></span><span class="floatR pdR10"></span></div>
		<div class="colbottom">
		   <div id="prcRMPSFrame"  name="prcRMPSFrame" width="98%" height="100%"></div>
		</div>
		<!--  공지사항/자료실 -->
		<div class="coltop01 pdT10 mgT10">
			<div class="nSubinfoTabs">
				<ul>
					<li id="pli1" class="on" onclick="setNoticFrame('1');"><a><span>${menu.LN00001}</span></a></li>
					<li id="pli2" onclick="fnSetChangeSet('2');"><a><span>Revision</span></a></li>
				</ul>
				<span class="floatR pdR20" onClick="javascript:fnClickMoreBoard();"><img src="${root}${HTML_IMG_DIR}/more.png"></span>
			</div>
		</div>
		<div class="colbottom01">
			<div id="subCngFrame" width="100%" height="100%"></div>		
		</div>	
		<%-- <div class="nstyleL"><img src="${root}${HTML_IMG_DIR}/img_nstyle01.png"  OnClick="fnGetSRCreate();"></div> --%>
		
	</div>

</div>
</div>
</form>
</div>
<iframe id="saveFrame" name="saveFrame" style="width:0px;height:0px;display:none;"></iframe>
</BODY>