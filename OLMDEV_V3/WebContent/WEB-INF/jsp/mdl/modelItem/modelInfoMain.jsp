<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8"></meta>
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
 <link rel="stylesheet" type="text/css" href="${root}cmm/common/css/pim.css"/>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<head>

<script type="text/javascript">
	
	$(document).ready(function(){	
		$("#modelPropertiesDiv").innerHeight(document.body.clientHeight - 34);

		window.onresize = function() {
			$("#modelPropertiesDiv").innerHeight(document.body.clientHeight -34);
		};
		
		fnGoModelInfo('view');		
		setFrame('viewModelElmTree', 1);
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	//ajax에서 페이지에 넘길 변수값들 지정
	function getData(avg, avg1, avg2){
		var Data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
				+"&s_itemID=${s_itemID}"
				+"&MenuID="+avg2
				+ avg + "&fromModelYN=${fromModelYN}"
				+ "&modelID=${modelID}";
		
		// 테이불 TB_MENU_ALLOC 컬럼 Varfilter 내용 추가
		if(avg1 != '' || avg1 != null){
			Data = Data + "&varFilter=" + avg1;
		}
		
		return Data;
	}
	
	function setFrame(avg, avg2, avg3, avg4,avg5){
		var browserType="";
		var IS_IE11=!!navigator.userAgent.match(/Trident\/7\./);
		if(IS_IE11){browserType="IE11";}
		var url = avg+".do";
		var data = getData(avg3,avg4,avg5);
		var target = "tabFrame";
		var src = url +"?" + data+"&browserType="+browserType;
		var idx = (window.location.href).lastIndexOf('/');
// 		var height = (setWindowHeight() - $(".wrapView").height())-110;
		$("#clickedURL").val((window.location.href).substring(0,idx)+"/"+src);
		$("#tabFrame").empty();
		$("#tabFrame").attr("style", "display:block;");
		ajaxTabPage(url, data, target);
	}
	
	var SubTabNum = 1; /* 처음 선택된 tab메뉴 ID값*/
	$(function(){
		$("#cli"+SubTabNum).addClass('on');
		
		$('.SubTab ul li').mouseover(function(){
			$(this).addClass('on');
		}).mouseout(function(){
			if($(this).attr('id').replace('cli', '') != SubTabNum) {
				$(this).removeClass('on');
			}
			$('#tempDiv').html('SubTabNum : ' + SubTabNum);
		}).click(function(){
			$(".SubTab ul li").removeClass("on"); //Remove any "active" class
			$(this).addClass('on');
			SubTabNum = $(this).attr('id').replace('cli', '');
		});
	});
	
	function fnGoModelInfo(scrnType){
		var url = "editModelInfo.do";
		if(scrnType == "view"){
			url = "viewModelInfo.do";
		}
		var data = "&s_itemID=${s_itemID}&modelID=${modelID}";
		var target = "modelInfoDiv";
		
		ajaxPage(url, data, target);
	}
	
	function fnViewModel(){
		if("${pop}" == "pop"){
	 		parent.fnViewModelInfo("newDiagramViewer");
		}else{
			var url = 'newDiagramViewer';
			if("${modelViewHYN}" == "Y"){ url = 'modelView_H'; }
			parent.setActFrame(url,2, '', '${varFilter}');
		}
	}
	
	function fnHideTable() {
		var tempSrc = $("#fitWindow").attr("src");
		if($("#fitWindow").hasClass("frame_show")) {
			var height = $("#modelInfoDiv").height();
			
			$("#modelInfoDiv").attr("style","display:none;");
			$(".child_search_head").attr("style","display:none;");
			$("#bottomView").attr("style","position:relative;top:0;");
			$(".subtabs").css("margin-top","0");
			//$("#viewSRDIV").scrollTop(0);
			$("#fitWindow").attr("class","frame_hide");
			$("#fitWindow").attr("alt","${WM00159}");
			$("#digramFrame").animate({scrollTop: 0}, 200);
			//$("#mainLayer").animate({scrollTop: 0}, 200);
		}
		else {
			$("#modelInfoDiv").attr("style","display:block;");
			$(".child_search_head").attr("style","display:block;");
			$("#bottomView").attr("style","position:relative;top:40px;");
			$(".subtabs").removeAttr("style");
			$("#fitWindow").attr("class","frame_show");
			$("#fitWindow").attr("alt","${WM00158}");
		}
	}
	
</script>
</head>
<body >
	<div class="child_search_head mgL10">
	<span class="floatL mgR10 mgT5"><img style="cursor:auto;" src="${root}cmm/common/images/icon_back_btn.png" width="26" height="24" OnClick="fnViewModel();"></span>
	<span class="floatR pdL20" ></span><p>Model Properties</p></span>
	</div>	
	<div id="modelPropertiesDiv" style="width:100%;overflow-y:auto;overflow-x:hidden;" >
		<div id="modelInfoDiv" style="width:100%;" ></div>	
		<div id="bottomView" class="pdB15 pdL10 pdR10">
			<div class="SubTab mgT10 mgB10" >
				<ul>
					<li id="cli1" onclick="setFrame('viewModelElmTree', 1)"><a><span>Element</span></a></li>
					<!-- <li id="cli2" onclick=""><a><span></span></a></li> -->
				</ul>
				<div class="instance_top_btn mgR10" ><a id="fitWindow" class="frame_show" onclick="fnHideTable()"></a></div>
			</div>
		
			<div id="tabFrame"></div>
		</div>
	</div>
</body>
</html>