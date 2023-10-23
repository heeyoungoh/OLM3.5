<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<style type="text/css">
* html body{ /*IE6 hack*/
padding: 0 0 0 200px; /*Set value to (0 0 0 WidthOfFrameDiv)*/
}
* html #carcontent{ /*IE6 hack*/
width: 100%; 
}
#mainLayer{
    background: none;
    height: auto;
}
</style>
<script type="text/javascript">
var pmenuIndex = "1 2 3 10 11 12 13 14"; // 각 menu index (submenu 포함)
var maxIndex = "${maxIndex}";
var startBoardIndex = "${startBoardIndex+1}";
var scrnType = "${scrnType}";
var mainType = "${mainType}";
var srMode = "${srMode}";	
var srID = "${srID}";
	$(document).ready(function(){
		var focusMenu = "${focusMenu}";
		var menuIndex = 1;
		if(focusMenu != ""){
			menuIndex = parseInt(focusMenu);
			if(menuIndex <= 3){
				clickOpenClose(1);				
			}else if(3 < menuIndex && menuIndex <=6){
				clickOpenClose(2);				
			}else if(6 < menuIndex && menuIndex <=9){
				clickOpenClose(3);				
			}else if(9 < menuIndex && menuIndex <=14){
				clickOpenClose(4);				
			}
			setSubFrame(menuIndex);
		}else{
			if(mainType =="mySRDtl" || mainType =="SRDtl") {
				clickOpenClose(1);
				setSubFrame(1, 'srview');	
			}else{
				clickOpenClose(1);
				setSubFrame(menuIndex);
			}
		} 

		$("#btnFold").click(function(){
			$("#foldframe").attr("style","display:none;");
			$("#foldframeTop").addClass('foldframeTop');
			//$("#foldcontent").addClass('foldcontent');
			$("#foldcontent").removeClass('unfoldcontent');
			$("#foldcontent").addClass('foldcontent');	
			$("#title1").attr("style","display:none;");	
			$("#title2").attr("style","display:block;");
			fnSetGridResizing();
			
	     });
		$("#btnUnfold").click(function(){
			$("#foldframe").attr("style","display:block;");
			$("#foldframeTop").addClass('unfoldframeTop');
			$("#foldcontent").removeClass('foldcontent');
			$("#foldcontent").addClass('unfoldcontent');
			$("#title1").attr("style","display:block;");
			$("#title2").attr("style","display:none;");				
			fnSetGridResizing(230);
	     });
		
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
	
	function fnSetGridResizing(avg){
		$("#mainLayer").innerHTML = $("#grdGridArea").attr("style","width:"+(setWindowWidth()-avg)+"px;");
		$("#mainLayer").innerHTML = p_gridArea.setSizes();
	}
	
	// [Menu] Click
	function setSubFrame(avg, avg2){
		clickSubMenu(avg); // 클릭한 변경
		
		var target = "mainLayer";
		var url = "";
		var data = "scrnType=${scrnType}&srType=${srType}&sysCode=${sysCode}&srArea1ListSQL=${srArea1ListSQL}&reqDateLimit=${reqDateLimit}";		
		
		if(avg == "1") {
			url = "cspList.do";
			if(srID != ""){
				/* My SR */
				data = "scrnType=${scrnType}&sysCode=${sysCode}&proposal=${proposal}&itemProposal=${itemProposal}";
				if(avg2 == "srcreate"){
					data = data + "&srType=${srType}&pageNum=1&srMode="+srMode;					
				}else if(avg2 == "srview"){
					data = data + "&srID=${srID}&srType=${srType}&pageNum=1&mainType="+mainType;  
				}else  {
					data = data + "&srType=${srType}&srMode=mySR&pageNum=1";    
				}
			 
			}
			data = data + "&srStatus=ING&pageNum=1";		
		} else if(avg == "2") {
			url =  "cspList.do";
			data =  data + "&srStatus=COMPL&pageNum=1";
		} else if(avg == "3") {
			url = "cspList.do";
			data = data + "&srStatus=ALL&pageNum=1&srMode=${srMode}";
			
		
		}  else if(avg == "11") { // 상신 : AREQ
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=AREQ&screenType=MyPg&wfStepID=AREQ";
		} else if(avg == "10") { // 결재할 문서 CurAprv
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=CurAprv&screenType=MyPg";
		} else if(avg == "12") { // 결재 예정 ToDoAprv
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=ToDoAprv&screenType=MyPg";
		} else if(avg == "13") { // 승인완료 Cls
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=Cls&screenType=MyPg";
		} else if(avg == "14") { // 참조 Ref + 관리조직 참조
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=RefMgt&screenType=MyPg";
		 }
		console.log(data);
		ajaxPage(url, data, target);
	}
	
	/* TODO ::::: Board (공지사항 (공통) 포함 ) */
	function setBrdFrame(noticType, avg, url, boardTypeCD) {
		//clickSubMenu(avg); // 클릭한 변경
		var target = "mainLayer";		
		var data = "s_itemID=${projectID}&noticType="+noticType+"&boardTypeCD="+boardTypeCD+"&url="+url+"&scrnType=${scrnType}";
		var url = "boardList.do";
		ajaxPage(url, data, target);
	}
	
	/* Report */
	function setRptFrame(curIndex, url) {
		clickSubMenu(curIndex); // 클릭한 변경
		var target = "mainLayer";
		var data = "s_itemID=${projectID}";
		
		ajaxPage(url, data, target);
	}
	
	// [set link color]
	function clickSubMenu(avg) {
		var realMenuIndex = pmenuIndex.split(' ');
		var menuName = "menuCng";
		for(var i = 0 ; i < realMenuIndex.length; i++){
			if (realMenuIndex[i] == avg) {
				$("#"+menuName+realMenuIndex[i]).addClass("on");
			} else {
				$("#"+menuName+realMenuIndex[i]).removeClass("on");
			}
		}
	}
	
	// [+][-] button event
	function clickOpenClose(avg) {
		if ( avg == 4 || $(".smenu" + avg).css("display") == "none" ||  $(".plus"+ avg).css("display") != "none") {
			$(".smenu" + avg).css("display", "block");
			$(".plus" + avg).css("display", "none");
			$(".minus" + avg).css("display", "block");
			setOtherArea(avg); // 그외 내용을 닫아줌
		} else {
			$(".smenu" + avg).css("display", "none");
			$(".plus" + avg).css("display", "block");
			$(".minus" + avg).css("display", "none");
		}
		
		// board, change Management, Report 이외의 menu remove [on]
		if (avg == 1 || avg == 2 || avg == 3 || avg == 4 ) { 
			clickSubMenu(avg);
		}
	}
	
	function setOtherArea(avg) {
		var indexArray = pmenuIndex.split(' ');
		for(var i = 0 ; i < indexArray.length; i++){
			var index = i + 1;
			if(index != avg){
				$(".smenu" + index).css("display", "none");
				$(".plus" + index).css("display", "block");
				$(".minus" + index).css("display", "none");
			}
		}
	}
	
</script>
</head>
<body id="mainMenu">
	<input id="chkSearch" type="hidden" value="false"></input>
	<div>
    	<ul class="help_menu">
    		 <li class="helptitle2" id="title1">
	    		 <span style="font-size:14px;"> <img src="${root}${HTML_IMG_DIR}/icon_pjt.png">&nbsp;CSP 서비스 처리 
	    		 <img id="btnFold" class="floatR mgT10 mgR15" src="${root}${HTML_IMG_DIR}/btn_layout_previous.png">
	    		 </span> 	    		
    		 </li>
    		 <li id="title2">
	    		 <img id="btnUnfold" name="btnUnfold" class="mgT10 mgL15" src="${root}${HTML_IMG_DIR}/btn_layout_next.png"> 
    		 </li>
    	</ul>
    </div>
   	<div id="foldframe" class="foldframe">
		<div>
	       	<ul class="help_menu">	        
	            <li class="helpstitle plus1"><a onclick="clickOpenClose(1);"><img src="${root}${HTML_IMG_DIR}/icon_pjt_chorder.png"><span class="fontchange">&nbsp;Service Request</span></a></li>
                <li class="helpstitle line minus1" style="display:none;"><a class="on" onclick="clickOpenClose(1);"><img src="${root}${HTML_IMG_DIR}/icon_pjt_chorder.png"><span class="fontchange">&nbsp;Service Request</span></a></li>
             	 	<li class="hlepsub line smenu1" style="display:none;"><a id="menuCng1" onclick="setSubFrame(1);">&nbsp;&nbsp;Processing</a></li>
             	 	<li class="hlepsub line smenu1" style="display:none;"><a id="menuCng2" onclick="setSubFrame(2);">&nbsp;Completed</a></li>
             		<li class="hlepsub line smenu1" style="display:none;"><a id="menuCng3" onclick="setSubFrame(3);">&nbsp;All</a></li>

             	<li class="helpstitle line plus4"><a onclick="clickOpenClose(4);"><img src="${root}${HTML_IMG_DIR}/icon_stor_01.png"><span class="fontchange">&nbsp;${menu.LN00242}</span></a></li>
				<li class="helpstitle line minus4" style="display:none;"><a class="on" onclick="clickOpenClose(4);"><img src="${root}${HTML_IMG_DIR}/icon_stor_01.png"><span class="fontchange">&nbsp;${menu.LN00242}</span></a></li>
	             	 <li class="hlepsub line smenu4" style="display:none;"><a id="menuCng10" onclick="setSubFrame(10);">&nbsp;${menu.LN00243}</a></li><!-- 결재할문서 -->
	             	 <li class="hlepsub line smenu4" style="display:none;"><a id="menuCng11" onclick="setSubFrame(11);">&nbsp;${menu.LN00211}</a></li><!-- 상신 -->
	             	 <li class="hlepsub line smenu4" style="display:none;"><a id="menuCng12" onclick="setSubFrame(12);">&nbsp;${menu.LN00244}</a></li><!-- 결재예정문서 -->
	             	 <li class="hlepsub line smenu4" style="display:none;"><a id="menuCng13" onclick="setSubFrame(13);">&nbsp;${menu.LN00118}</a></li><!-- 완료 -->
	             	 <li class="hlepsub line smenu4" style="display:none;"><a id="menuCng14" onclick="setSubFrame(14);">&nbsp;${menu.LN00245}</a></li><!-- 참조 -->
	        </ul>
	   	</div>
	</div>
	<div id="foldcontent" class="unfoldcontent"><div id="mainLayer" class="pdL20 pdR20" ></div></div>
</body>
</html>


