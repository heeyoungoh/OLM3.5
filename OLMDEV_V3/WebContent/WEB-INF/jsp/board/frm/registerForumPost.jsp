<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/" var="root"/>

<link rel="stylesheet" href="${root}cmm/common/css/materialdesignicons.min.css"/>

<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>
<%@ include file="/WEB-INF/jsp/cmm/fileAttachHelper.jsp"%>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_1" arguments="${menu.LN00245}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00034" var="WM00034_2" arguments="${menu.LN00389}"/>

<script type="text/javascript">
//파일 업로드 처리 이동 [fileAttachHelper.js]
var screenType = "${screenType}";
var fileSize = "${itemFiles.size()}";
var mailRcvListSQL = $("#mailRcvListSQL").val();

$(document).ready(function() { 
	
	var emailCode = $("#emailCode").val();
	
	$("input.datePicker").each(generateDatePicker);
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&BoardMgtID=${BoardMgtID}&projectType=${projectType}&templProjectID=${templProjectID}";
	fnSelect('category', data, 'getBoardMgtCategory', '${resultMap.Category}', 'Select');
	
	$("#send").click(function(e) {
		if(mailRcvListSQL == "manual"){
			if($("#sharers").val() == ""){
			alert("${WM00034_1}");
			return false;
			}
		}
		if(emailCode == "REQITMRW"){
			if($("#SC_END_DT").val() == ""){
				alert("${WM00034_2}");
				return false;	
			}
		}
		if(confirm("${CM00001}")){
			var url  = "saveForumPost.do";
			ajaxSubmit(document.newFormFrm, url);
		}
	});
	
	$('#back').click(function(e){
		doReturn();
	});
	
});

//[Add] 버튼 Click
function addSharer(){
	var projectID = $("#project").val();
	var sharers = $("#sharers").val();
	
	var url = "selectMemberPop.do?mbrRoleType=R&&s_memberIDs="+sharers;
	window.open(url,"schedlFrm","width=900 height=700 resizable=yes");			
	
}

function setSharer(memberIds,memberNames) {
	$("#sharers").val(memberIds);
	$("#sharerNames").val(memberNames);
	$("#sharerNamesText").val(memberNames);
}

var boardTitle = "${boardTitle}";
function doReturn(){
	var listType = "${listType}";
	var url = "boardForumList.do";
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
				+"&pageNum="+$("#currPage").val()+"&noticType=${noticType}&BoardMgtID=${BoardMgtID}&isMyCop=${isMyCop}"
				+"&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
				+"&searchType=${searchType}"+"&searchValue=${searchValue}"
				+"&screenType=${screenType}&projectID=${projectID}&listType=${listType}&srID=${srID}&srType=${srType}"
				+"&instanceNo=${instanceNo}&changeSetID=${changeSetID}"
				+"&mailRcvListSQL="+mailRcvListSQL+"&emailCode="+emailCode+"&showItemInfo=${showItemInfo}&scrnType=${scrnType}"
				+"&boardTitle="+encodeURIComponent(boardTitle);
	if(listType == 1){data = data + "&s_itemID="+$('#s_itemID').val();}			
	var target = "help_content";
	ajaxPage(url, data, target);
}
	
	function doAttachFile(){
		var browserType="";
		//if($.browser.msie){browserType="IE";}
		var IS_IE11=!!navigator.userAgent.match(/Trident\/7\./);
		if(IS_IE11){browserType="IE11";}
		var url="addFilePop.do";
		var data="scrnType=BRD&browserType="+browserType+"&mgtId="+$("#BoardMgtID").val()+"&id="+$('#BoardID').val();
		//fnOpenLayerPopup(url,data,"",400,400);
		if(browserType=="IE"){fnOpenLayerPopup(url,data,"",400,400);}
		else{openPopup(url+"?"+data,490,360, "Attach File");}
	}
	
	function fnDeleteItemFile(BoardID, seq){
		var url = "boardFileDelete.do";
		var data = "&delType=1&BoardID="+BoardID+"&Seq="+seq;
		ajaxPage(url, data, "blankFrame");
		
		$('#divDownFile'+seq).remove();	
		
		document.querySelectorAll(".file_box").forEach((item) => {
		    if(item.innerText == "") item.style.display = "";
		})
	}
	
	//************** addFilePop V4 설정 START ************************//
	function doAttachFileV4(){
		var url="addFilePopV4.do";
		var data="scrnType=BRD&mgtId="+$("#BoardMgtID").val()+"&id="+$('#BoardID').val();
		openPopup(url+"?"+data,480,450, "Attach File");
	}
	
	var fileIDMapV4 = new Map();
	var fileNameMapV4 = new Map();
	function fnAttacthFileHtmlV4(fileID, fileName){ 
		fileID = fileID.replace("u","");
		fileIDMapV4.set(fileID,fileID);
		fileNameMapV4.set(fileID,fileName);
	}
	
	// addFilePopV4에서 파일 삭제시, fileMap에서 해당파일 제거 
	function fnDeleteFileMapV4(fileID, removeAll){ 
		if(removeAll == "Y"){
			fileIDMapV4 = new Map();
			fileNameMapV4 = new Map();
		}else{
			fileID = fileID.replace("u","");		
			fileIDMapV4.delete(String(fileID));
			fileNameMapV4.delete(String(fileID));
		}
	}
	
	function fnDisplayTempFileV4(){
		display_scripts=$("#tmp_file_items").html();

		let fileFormat = "";
		fileIDMapV4.forEach(function(fileID) {
			fileFormat = fileNameMapV4.get(fileID).split(".")[1];
			switch (true) {
				case fileFormat.includes("do") : fileFormat = "doc"; break;
				case fileFormat.includes("xl") : fileFormat = "xls"; break;
				case fileFormat.includes("pdf") : fileFormat = "pdf"; break;
				case fileFormat.includes("hw") : fileFormat = "hwp"; break;
				case fileFormat.includes("pp") : fileFormat = "ppt"; break;
				default : fileFormat = "log"
			}
			  display_scripts = display_scripts+
			  	'<li id="'+fileID+'"  class="flex icon_color_inherit justify-between mm align-center" name="'+fileID+'">'+ 
				'<span><span class="btn_pack small icon mgR25">'+
				'	<span class="'+fileFormat+'"></span></span>' +
				'	<span style="line-height:24px;">'+fileNameMapV4.get(fileID) + '</span></span>' +
				'<i class="mdi mdi-window-close" onclick="fnDeleteFileHtmlV4('+fileID+')"></i>'+
				'</li>';
		});
		document.getElementById("tmp_file_items").innerHTML = display_scripts;		
		$("#tmp_file_items").attr('style', 'display: block;');
	
		fileIDMapV4 = new Map();
		fileNameMapV4 = new Map();
	}
	
	//  dhtmlx v 4.0 delete file  
	function fnDeleteFileHtmlV4(fileID){			
		var fileName = fileName = document.getElementById(fileID).innerText;
		//console.log("fnDeleteFileHtml fileID : "+fileID+" , fileName  :"+fileName); // fileID.textContent
		
		fileIDMapV4.delete(String(fileID));
		fileNameMapV4.delete(String(fileID));
		
		if(fileName != "" && fileName != null && fileName != undefined){
			$("#"+fileID).remove();
			var url  = "removeFile.do";
			var data = "fileName="+fileName;	
			ajaxPage(url,data,"blankFrame");
		}
		
		if(!document.querySelector("#tmp_file_items").innerHTML) {
			document.querySelector("#tmp_file_items").style.display = "";
		}
	}
	//************** addFilePop V4 설정 END ************************//
</script>
<div class="pdB15 mgT20" style="width: 70%; margin: 0 auto;">
	<form name="newFormFrm" id="newFormFrm" enctype="multipart/form-data" action="boardForumNew.do" method="post" onsubmit="return false;">
		<input type="hidden" id="noticType" name="noticType" value="${noticType}">
		<input type="hidden" id="noticType" name="BoardMgtID" value="${BoardMgtID}">
		<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}" />
		<input type="hidden" id="languageID" name="languageID" value="${sessionScope.loginInfo.sessionCurrLangType}" />
		<input name="userId" id="userId" type="hidden" value="${sessionScope.loginInfo.sessionUserId}" />
		<input name="date" id="date" type="hidden"/> 
		<input type="hidden" id="screenType" name="screenType" value="${screenType}" />
		<input type="hidden" id="projectID" name="projectID" value="${projectID}" />
		<input type="hidden" id="srID" name="srID" value="${srID}" />
		<input type="hidden" id="srType" name="srType" value="${srType}" />
		<input type="hidden" id="instanceNo" name="instanceNo" value="${instanceNo}" />
		<input type="hidden" id="changeSetID" name="changeSetID" value="${changeSetID}" />
		<input type="hidden" id="boardMgtName" name="boardMgtName" value="${boardMgtName}" />
		<input type="hidden" id="mailRcvListSQL" name="mailRcvListSQL" value="${mailRcvListSQL}" />
		<input type="hidden" id="emailCode" name="emailCode" value="${emailCode}" />
		<input type="hidden" name="ItemMgtUserID" id="ItemMgtUserID" value="${ItemMgtUserMap.AuthorID}" />
		<input type="hidden" name="showItemInfo" id="showItemInfo" value="${showItemInfo}" />
		<input type="hidden" name="boardTitle" id="boardTitle" value="${boardTitle}" />
		<div class="align-center flex justify-between pdT10 pdB10"  style="border-bottom: 1px solid #dfdfdf;">
			<p style="font-size: 15px;color: #b0b0b0;">${boardTitle}&nbsp;&gt;&nbsp;Write</p>
		</div>
		<ul>
			<li class="flex align-center pdT40 pdB5">
				<c:if test="${categoryYN == 'Y'}">
				<div class="align-center flex" style="flex: 2 1 0;">
					<h3 class="tx">${menu.LN00002 }</h3>
					<span class="wrap_sbj">
						<input type="text" name="subject" id="subject">
					</span>
				</div>
				<div class="align-center flex mgL30" style="flex: 1 1 0;">
					<h3 class="tx">${menu.LN00033 }</h3>
					<select id="category" name="category" class="form-sel" style="flex: 1 1 0;"></select>
				</div>
				</c:if>
				<c:if test="${categoryYN != 'Y'}">
				<div class="align-center flex" style="flex: 2 1 0;">
					<h3 class="tx">${menu.LN00002 }</h3>
					<span class="wrap_sbj">
						<input type="text" name="subject" id="subject">
					</span>
				</div>
				</c:if>
			</li>
			<c:if test="${mailRcvListSQL eq 'manual' }">
			<li class="flex align-center pdT5 pdB5">
				<h3 class="tx">${menu.LN00245 }</h3>
				<span class="wrap_sbj">
					<input type="text" placeholder="Click" onclick="addSharer()" id="sharerNamesText" readonly>
					<input type="hidden" class="text" id="sharerNames" name="sharerNames" value="">
					<input type="hidden" class="text" id="sharers" name="sharers" value="">
				</span>
			</li>
			</c:if>
			<c:if test="${!empty mailRcvListSQL && mailRcvListSQL ne ''}">
			<li class="flex align-center pdT5 pdB5">
				<h3 class="tx">${menu.LN00389 }</h3>
				<span class="wrap_sbj">
					<input type="text" id=SC_END_DT	name="SC_END_DT" class="text datePicker" size="10" style="width: 180px;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
				</span>
			</li>
			</c:if>
			<li class="flex pdT5 pdB5">
				<h3 class="tx mgT12">${menu.LN00111 }</h3>
				<div style="width:calc(100% - 100px);">
					<button class="cmm-btn" onclick="doAttachFileV4()">Attach</button>
					<ul id="tmp_file_items" name="tmp_file_items" class="file_box mgB5 mgT10 tmp_file_items"></ul>
				</div>
				</li>
			<li class="flex pdT5 pdB5">
				<textarea  class="tinymceText" id="content" name="content" style="width:100%;height:350px;"></textarea>
			</li>
		</ul>
		<div class="alignR">
			<button class="cmm-btn mgT10 mgR5" id="back" style="color: #333333;">Cancel</button>
			<button class="btn-4265EE cmm-btn mgT10" id="send">Submit</button>
		</div>
	</form>
</div>
<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
