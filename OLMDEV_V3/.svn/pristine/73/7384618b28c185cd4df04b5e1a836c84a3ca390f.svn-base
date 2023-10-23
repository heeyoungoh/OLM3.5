<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/" var="root"/>
 
<link rel="stylesheet" href="${root}cmm/common/css/materialdesignicons.min.css"/>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00005" var="CM00005" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002" />
<script type="text/javascript">
	
//script에러 방지용 cosole선언
var console = console || {
	log:function(){},
	warn:function(){},
	error:function(){}
};
var screenType = "${screenType}";
var emailCode = "${emailCode}";
var listType = "${listType}";
const boardIds = "${boardIds}";

const inputTarget = document.getElementById("input_target");
inputTarget.addEventListener("focus", (e) => {
	e.target.parentNode.className = "scroll_box writing"
});

inputTarget.addEventListener("blur", (e) => {
		if(e.target.innerHTML.replaceAll('<br>','') != "") {
			e.target.parentNode.className = "scroll_box writing"
		} else {

			e.target.parentNode.className = "scroll_box"
		}
});

const btnTop = document.getElementById("top");
let scrollArea = document.getElementById("carcontent");
if(!scrollArea) scrollArea = document.getElementById("help_content");
const actFrame = document.getElementById("actFrame");
btnTop.addEventListener("click", (e) => {
	scrollArea.scroll({
		  top: 0,
		  behavior: 'smooth'
	});
});

const prev = document.getElementById("prev");
const next = document.getElementById("next");
const curIndex = boardIds.split(",").findIndex(e => e === "${boardID}");
if(curIndex - 1 < 0 ) {
	document.getElementById("next").style.display = "none";
}
if(curIndex + 1 == boardIds.split(",").length) {
	document.getElementById("prev").style.display = "none";
}
prev.addEventListener("click", () => goPage(curIndex + 1));
next.addEventListener("click", () => goPage(curIndex - 1));

function goPage (index) {
	fnCallBack(boardIds.split(",")[index],"${ItemID}");
}

$(document).ready(function(){
	var listType = "${listType}";
	
	$("#send").click(function(e) {
		var commentContent = document.getElementById("input_target").innerText.replaceAll("\n","<br>");
		document.getElementById("content_new").value = commentContent;
		document.getElementById("boardID").value = "";
		if(confirm("${CM00001}")){
			var actionUrl = "saveForumReply.do";
			ajaxSubmit(document.formDetailFrm, actionUrl);
		}
	});

	if("${replyOption}" == "0") document.getElementById("register_box").style.display = "block";
	if("${replyOption}" == "1" && ("${ItemMgtUserMap.AuthorID}" == "${sessionScope.loginInfo.sessionUserId}" || "${boardMap.ReplyFlag}" == 'T')) document.getElementById("register_box").style.display = "block";
	if("${replyOption}" == "2" && ("${BoardMgtInfo.MgtUserID}" == "${sessionScope.loginInfo.sessionUserId}" || "${boardMap.ReplyFlag}" == 'T')) document.getElementById("register_box").style.display = "block";
	if("${replyOption}" == "3" && "${boardMap.ReplyFlag}" == 'T') document.getElementById("register_box").style.display = "block";
});

$("#change_item").click(function(e){
	var itemTypeCode = $("#itemTypeCode").val();
	if(itemTypeCode == null || itemTypeCode == ""){
		//itemTypeCode = "OJ00001";	
		selectItemTypeCodePopup();
		
	}else{
		orgItemTreePop(itemTypeCode);
	}
});

function selectItemTypeCodePopup(){
	var url = "selectItemTypePop.do?s_itemID=${s_itemID}&varFilter=${varFilter}&screenMode="; 
	var w = 500;
	var h = 300;
	itmInfoPopup(url,w,h);
}

function orgItemTreePop(itemTypeCode){
	var url = "orgItemTreePop.do";
	var data = "s_itemID=${s_itemID}&ItemID="+$("#ItemID").val()+"&ItemTypeCode="+itemTypeCode+"&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
	fnOpenLayerPopup(url,data,doCallBack,617,436);
}

function viewMbrInfo() {
	var url = "viewMbrInfo.do?memberID=${ItemMgtUserMap.AuthorID}";		
	window.open(url,'window','width=1200, height=700, left=400, top=100,scrollbar=yes,resizble=0');
}

function editComment(seq, ItemID, regUserID) {	
	// 다른 편집기 삭제
	document.querySelectorAll(".edit_box").forEach((e) => {
	    e.remove();
	})
	
	// display:none -> 원복
	document.querySelectorAll(".reply_list").forEach((item) => {
	    for(let i=0; i< item.children.length; i++) {
	        if(item.children[i].hasAttribute("id")) {
	        	item.children[i].style.display = "";
	        }
	    }
	})

	// 편집기로 바꾸기
	document.querySelector("#reply_content"+seq).style.display = "none";
	const replyContent = document.querySelector("#reply_content"+seq+" p").innerHTML;
	
	let replyFile = "";
	if(document.querySelector("#tmp_file_items"+seq)) {
		document.querySelector("#tmp_file_items"+seq).style.display = "none";
		replyFile = document.querySelector("#tmp_file_items"+seq).outerHTML;
	}
	
	const registerBox = '<div id="editBox_'+seq+'" class="edit_box mgL23"><div class="wrapper mgT10">'+
		    '<div class="scroll_box writing" id="scroll_target">'+
				'<div class="input_box pdT10" id="input_target'+seq+'" contenteditable="true">'+replyContent+'</div>'+
				'<span class="placeholder pdT10">Please enter a comment</span>'+
			'</div>'+
			replyFile.replace('ul class="file_box mgT10 mgL23"','ul class="file_box mgB15 mgL15 mgT10 mgL23 tmp_file_items"').replaceAll('none','block').replaceAll("reply_file","tmp_file")+
		'</div>'+
		'<div class="btn_box">'+
			'<div class="attach_btns icon_color_inherit" onclick="doAttachFileV4('+seq+')" style="cursor:pointer"><i class="mdi mdi-paperclip"></i></div>'+
			'<div class="register_btns">'+
				'<button type="button" class="point cancel" id="reply-cancel">Cancel</button>'+
				'<button type="button" class="point" id="edit">Input</button>'+
		  	'</div>'+
		'</div></div>';
	document.querySelector("#reply_list"+seq).innerHTML += registerBox;
	

	const inputTarget = document.getElementById("input_target"+seq);
	inputTarget.addEventListener("focus", (e) => {
		e.target.parentNode.className = "scroll_box writing"
	});

	inputTarget.addEventListener("blur", (e) => {
			if(e.target.innerHTML.replaceAll('<br>','') != "") {
				e.target.parentNode.className = "scroll_box writing"
			} else {

				e.target.parentNode.className = "scroll_box"
			}
	});
	
	// 취소 버튼 클릭시 이벤트
	document.querySelector("#reply-cancel").addEventListener("click", (btn) => {
		const editReplyBox = btn.target.parentNode.parentNode.parentNode;
		const showSeq = editReplyBox.getAttribute("id");
		editReplyBox.remove();
		document.querySelector("#reply_content"+showSeq.split("_")[1]).style.display = "";
		if(document.querySelector("#tmp_file_items"+seq)) document.querySelector("#tmp_file_items"+showSeq.split("_")[1]).style.display = "";
	});
	
	document.querySelector("#edit").addEventListener("click", (btn) => {
		const editReplyBox = btn.target.parentNode.parentNode.parentNode;
		const showSeq = editReplyBox.getAttribute("id");
		const commentContent = document.getElementById("input_target"+showSeq.split("_")[1]).innerText.replaceAll("\n","<br>");
		document.getElementById("content_new").value = commentContent;
		document.getElementById("boardID").value = seq;
		if(confirm("${CM00001}")){
			var actionUrl = "saveForumReply.do";
			ajaxSubmit(document.formDetailFrm, actionUrl);
		}
	});
}

function fnDeleteItemFile(BoardID, seq){
	document.querySelector("#tmp_file"+seq).onclick="";
	
	var url = "boardFileDelete.do";
	var data = "&delType=1&BoardID="+BoardID+"&Seq="+seq;
	ajaxPage(url, data, "blankFrame");
	
	document.querySelector("#tmp_file"+seq).remove();
	document.querySelector("#reply_file"+seq).remove();
	
	document.querySelectorAll(".file_box").forEach((item) => {
	    if(item.innerText == "") item.style.display = "";
	})
	
}

function deleteComment(boardID, ItemID){
	if(confirm("${CM00002}")){
		var url = "boardCommentDelete.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
			+ "&s_itemID="+ItemID
			+ "&ItemID="+ItemID
			+ "&userId="+$('#userId').val()
			+ "&parentID=${boardID}"
			+ "&boardID="+boardID
			+ "&BoardMgtID=${BoardMgtID}"
			+ "&pageNum=${pageNum}"
			+ "&noticType=${noticType}"
			+" &pageNum="+$("#currPage").val()
			+" &scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
			+" &searchType=${searchType}"+"&searchValue=${searchValue}"
			+ "&listType=${listType}"
			+ "&emailCode="+emailCode
			+ "&mailRcvListSQL=${mailRcvListSQL}"
			+ "&screenType=${screenType}&projectID=${projectID}";
		var target = "help_content";
		ajaxPage(url, data, target);
	}
} 

var boardTitle = "${boardTitle}";
function actionForum(avg){
	var confirmStr = "${CM00005}";
	var actionUrl =  "editForumPost.do";
	var target = "help_content";
	
	if(avg == "del"){
		confirmStr = "${CM00002}";
		actionUrl = "boardForumDelete.do";
		
	}
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
		+ "&noticType="+$('#noticType').val() 
		+ "&BoardID=${boardID}"
		+ "&BoardMgtID=${BoardMgtID}"
		+ "&s_itemID="+$('#s_itemID').val()
		+ "&ItemID="+$('#ItemID').val()
		+ "&userId="+$('#userId').val()
		+ "&filter="+$('#filter').val()
		+ "&pageNum="+$("#currPage").val()
		+ "&noticType=${noticType}"
		+ "&isMyCop=${isMyCop}"
		+ "&category=${category}"
		+ "&categoryIndex=${categoryIndex}"
		+ "&categpryCnt=${categoryCnt}"
		+ "&searchType=${searchType}"+"&searchValue=${searchValue}"
		+ "&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
		+ "&listType=${listType}"
		+ "&screenType=${screenType}"
		+ "&emailCode="+emailCode
		+ "&mailRcvListSQL=${mailRcvListSQL}"
		+ "&srID=${srID}"
		+ "&srType=${srType}"
		+ "&showItemInfo=${showItemInfo}"
		+ "&scrnType=${scrnType}"
		+ "&boardTitle="+encodeURIComponent(boardTitle);
	if(confirm(confirmStr)){
		ajaxPage(actionUrl, data, target);
	}
}

function fileNameClick(avg1){
	var url  = "fileDown.do?seq=" + avg1 + "&scrnType=BRD";
	ajaxSubmitNoAdd(document.formDetailFrm, url,"saveFrame");
}

function fnCallBack(parentID,s_itemID){ 	
	var data = "NEW=N&BoardMgtID=${BoardMgtID}&noticType=${noticType}&boardID="+parentID
				+ "&emailCode="+emailCode
				+ "&mailRcvListSQL=${mailRcvListSQL}"
				+ "&ItemID="+s_itemID+"&s_itemID="+s_itemID+"&pageNum=${pageNum}&isMyCop=${isMyCop}&listType=${listType}&boardIds=${boardIds}"
				+ "&srID=${srID}&srType=${srType}&showItemInfo=${showItemInfo}&scrnType=${scrnType}&boardTitle="+encodeURIComponent(boardTitle);
	var url = "viewForumPost.do";
	var target = "help_content"; 
	ajaxPage(url, data, target);
}

// 본문 글 삭제 콜백
function fnCallBackDel(){ 	
	var url = "boardForumList.do";
	if("${s_itemID}" == "") {
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&category=${category}"
			+ "&pageNum="+$("#currPage").val()+"&noticType=${noticType}&BoardMgtID=${BoardMgtID}&isMyCop=${isMyCop}"
			+ "&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
			+ "&searchType=${searchType}"+"&searchValue=${searchValue}&ItemTypeCode=${itemTypeCode}&showItemInfo=${showItemInfo}"
			+ "&emailCode="+emailCode
			+ "&mailRcvListSQL=${mailRcvListSQL}"
			+ "&screenType=${screenType}&projectID=${projectID}&listType=${listType}&srID=${srID}&srType=${srType}" 
			+ "&regUserName=${regUserName}&authorName=${authorName}&myBoard=${myBoard}&scrnType=${scrnType}&boardTitle="+encodeURIComponent(boardTitle);
	} else {
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&s_itemID=${s_itemID}&BoardMgtID=${BoardMgtID}&noticType=${noticType}&category=${category}"
			+ "&emailCode="+emailCode
			+ "&mailRcvListSQL=${mailRcvListSQL}&ItemTypeCode=${itemTypeCode}&showItemInfo=${showItemInfo}"
			+ "&isMyCop=${isMyCop}&listType=${listType}&srID=${srID}&srType=${srType}&regUserName=${regUserName}&authorName=${authorName}&myBoard=${myBoard}&scrnType=${scrnType}&boardTitle="+encodeURIComponent(boardTitle);
	}
	var target = "help_content";

	ajaxPage(url, data, target);
}

function fnOpenItemPop(itemID){
	var changeSetID = ${boardMap.BrdChangeSetID};
	var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemID+"&scrnType=pop";
	if (changeSetID != "") var url = url + "&option=CNGREW&changeSetID="+changeSetID;
	var w = 1200;
	var h = 900;
	itmInfoPopup(url,w,h,itemID);
}


function doLike(likeInfo){
	if(likeInfo == 'Y') {
		if(!confirm("Do you really cancel Like?")){ return;}
	}
	var url  = "saveBoardLike.do";
	ajaxSubmit(document.formDetailFrm, url,"saveFrame");
}

function doCallBack(itemID){
	var url = "boardForumChangeItem.do";
	var data = "ItemID="+$("#ItemID").val()
				+"&languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
				+ "&s_itemID="+itemID
				+ "&BoardID=${boardID}"
				+ "&BoardMgtID=${BoardMgtID}"
				+ "&subject="+$("#subject").val()
				+ "&content="+$("#content").text();
	var target = "help_content";
	ajaxPage(url, data, target);
}

function thisReload(boardID,s_itemID){
	$(".popup_div").hide();
	$("#mask").hide();
	fnCallBack(boardID,s_itemID);
}

let attachReplyFileSeq = "";
//************** addFilePop V4 설정 START ************************//
function doAttachFileV4(seq){
	attachReplyFileSeq = seq;
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

//addFilePopV4에서 파일 삭제시, fileMap에서 해당파일 제거 
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
	if(attachReplyFileSeq) {
		// 첨부된 문서가 없다면 ul 박스부터 생성
		if(!document.querySelector("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items")) {
			document.querySelector("#editBox_"+attachReplyFileSeq).firstChild.innerHTML += '<ul class="file_box mgB15 mgL15 mgT10 tmp_file_items" id="tmp_file_items'+attachReplyFileSeq+'" style="display: block;"></ul>';
		}
		document.querySelector("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items").style.display = "block";
		display_scripts=$("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items").html();
	} else {
		attachReplyFileSeq = "";
		document.getElementById("tmp_file_items").style.display = "block";
		display_scripts=$("#tmp_file_items").html();
	}
	
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
				'<span><span class="btn_pack small icon mgR30"><span class="'+fileFormat+'"></span></span>' +
				'<span style="line-height:24px;">'+fileNameMapV4.get(fileID) + '</span></span>' +
				'	<i class="mdi mdi-window-close" onclick="fnDeleteFileHtmlV4('+fileID+')"></i>'+
				'</li>';	
	});
	
	document.querySelector("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items").innerHTML = display_scripts;

	fileIDMapV4 = new Map();
	fileNameMapV4 = new Map();
}

//  dhtmlx v 4.0 delete file  
function fnDeleteFileHtmlV4(fileID){			
	var fileName = document.getElementById(fileID).innerText;
	//console.log("fnDeleteFileHtml fileID : "+fileID+" , fileName  :"+fileName); // fileID.textContent
	
	
	fileIDMapV4.delete(String(fileID));
	fileNameMapV4.delete(String(fileID));
	
	if(fileName != "" && fileName != null && fileName != undefined){
		$("#"+fileID).remove();
		var url  = "removeFile.do";
		var data = "fileName="+fileName;	
		ajaxPage(url,data,"blankFrame");
	}
	
	if(!document.querySelector("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items").innerHTML) {
		document.querySelector("#tmp_file_items"+attachReplyFileSeq+".tmp_file_items").style.display = "";
	}
}
//************** addFilePop V4 설정 END ************************//

function doReturn(){
	var listType = "${listType}";
	var url = "viewForumPost.do";
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
		+ "&noticType=${noticType}" 
		+ "&boardID=${boardID}" 
		+ "&BoardMgtID=${BoardMgtID}" 
		+ "&s_itemID="+$('#s_itemID').val()
		+ "&ItemID="+$('#ItemID').val()
		+ "&userId="+$('#userId').val()
		+ "&privateId="+$('#privateId').val()
		+ "&filter=editEnd"
		+ "&pageNum=${pageNum}"
		+ "&isMyCop=${isMyCop}"
		+ "&screenType=${screenType}"
		+ "&projectID=${projectID}"
		+ "&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
		+ "&emailCode="+emailCode
		+ "&mailRcvListSQL=${mailRcvListSQL}"		
		+ "&searchType=${searchType}&listType=${listType}&srID=${srID}&srType=${srType}&boardIds=${boardIds}"
		+ "&scrnType=${scrnType}&boardTitle="+encodeURIComponent(boardTitle);
	if(listType == 1){data = data + "&s_itemID="+$('#s_itemID').val();}		
		
	var target = "help_content";
	ajaxPage(url, data, target);
}

function goBack(){
	var url = "boardForumList.do";
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&category=${category}"
				+ "&pageNum="+$("#currPage").val()+"&noticType=${noticType}&BoardMgtID=${BoardMgtID}&isMyCop=${isMyCop}"
				+ "&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
				+ "&searchType=${searchType}"+"&searchValue=${searchValue}&ItemTypeCode=${itemTypeCode}"
				+ "&mailRcvListSQL=${mailRcvListSQL}"
				+ "&emailCode="+emailCode
				+ "&screenType=${screenType}&projectID=${projectID}&listType=${listType}&srID=${srID}&srType=${srType}"
				+ "&regUserName=${regUserName}&authorName=${authorName}&myBoard=${myBoard}&showItemInfo=${showItemInfo}&scrnType=${scrnType}"
				+ "&boardTitle="+encodeURIComponent(boardTitle);
	if(listType == 1){data = data + "&s_itemID=${s_itemID}"}			
	var target = "help_content";
	ajaxPage(url, data, target);
}
</script>
<div class="pdB15 mgT10" style="width: 80%; margin: 0 auto;">
<form name="formDetailFrm" id="formDetailFrm" method="post" onsubmit="return false;" enctype="multipart/form-data">
	<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}">
	<input type="hidden" id="filter" name="filter" value="edit">
	<input type="hidden" id="boardID" name="boardID">
	<input type="hidden" id="noticType" name="noticType" value="${noticType}">
	<input type="hidden" id="userId" name="userId" value="${sessionScope.loginInfo.sessionUserId}">
	<input type="hidden" id="regId" name="regId" value="${boardMap.RegID}">
	<input type="hidden" id="languageID" name="languageID" value="${sessionScope.loginInfo.sessionCurrLangType}">
	<input type="hidden" id="ItemID" name="ItemID" value="${ItemID}">
	<input type="hidden" id="score" name="score" value="${score}">
	<input type="hidden" id="filter" name="filter" value="">
	<input type="hidden" id="currPage" name="currPage" value="${pageNum}">
	<input type="hidden" id="refID" name="refID" value="${boardMap.RefID}">
	<input type="hidden" id="BoardMgtID" name="BoardMgtID" value="${BoardMgtID}">
	<input type="hidden" id="screenType" name="screenType" value="${screenType}">
	<input type="hidden" id="likeInfo" name="likeInfo" value="${boardMap.LikeInfo}" >
	<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="${itemTypeCode}" >
	<input type="hidden" id="replyLev" name="replyLev" value="1">
	<input type="hidden" id="parentRefID" name="parentRefID" value="${boardMap.RefID}">
	<input type="hidden" id="content_new" name="content_new" value="">
	<input type="hidden" id="parentID" name="parentID" value="${boardID}">
	<input type="hidden" id="mailRcvListSQL" name="mailRcvListSQL" value="${mailRcvListSQL}">
	
	<div class="align-center flex justify-between pdT20 pdB10"  style="border-bottom: 1px solid #dfdfdf;">
		<p style="font-size: 13px;color: #8d8d8d;">${boardTitle}</p>
		<button class="cmm-btn" style="height: 32px;" onclick="goBack()">List</button>
	</div>
	
	<div class="subject"><input type="text" class="subject" value="${boardMap.Subject}" style="width:90%;border:0px;"> </div>
	<div style="border-bottom: 1px solid #dfdfdf;">
		<div class="align-center flex justify-between mgT5 pdB20">
			<div class="flex">
				<span class="mem_list">
					<span class="thumb mini">
						<span lang="ko" class="initial_profile" style="background-color: #4265ee;">
					    	<em>${fn:substring(boardMap.WriteUserNM,0,1)}</em>
					    </span>
				    </span>
				     <span class="name_info"><span class="name_txt">${boardMap.WriteUserNM}(${boardMap.TeamName })</span></span>
				</span>
				<span class="lastUpdated pdL10">${boardMap.LastUpdated }</span>
				<div class="flex view_count icon_color_inherit mgL30"><i class='mdi mdi-eye-outline mgR10'></i><span>${boardMap.ReadCNT }</span></div>
			</div>
			<div class="flex">
				<c:if test="${!empty boardMap.ItemID && boardMap.ItemID ne '0'}">
				<img src="${root}${HTML_IMG_DIR_ITEM}/${boardMap.ClassIcon}" >
				<label onclick="fnOpenItemPop(${boardMap.ItemID})" class="mgL5">${boardMap.Path}/${boardMap.ItemName}</label>
				</c:if>
				<c:if test="${boardMap.Category != '-' || boardMap.Category ne '-'}">
					<span class="mgL20">Category :	${boardMap.Category}</span>
				</c:if>
			</div>
		</div>
		
		<c:if test="${!empty(boardMap.sharerNames) }">
		<div>
	    	<ul>
	    		<li class="flex mgB10">
	    			<p style="width: 60px; font-weight:700; ">${menu.LN00245}</p>
	    			<span class="flex" style="flex-wrap: wrap;width: fit-content;">
	    				<c:if test="${!empty boardMap.sharerNames}">
	    				<c:set var="sharer" value="${fn:split(boardMap.sharerNames,',')}" />
	    				<c:forEach var="i" items="${sharer}">
	    					<div class="mgR20">
								<span class="mem_list">
									<span class="thumb mini">
										<span lang="ko" class="initial_profile" style="background-color: rgb(134, 164, 212);">
									    	<em>${fn:substring(i,0,1)}</em>
									    </span>
								    </span>
								     <span class="name_info"><span class="name_txt">${i}</span></span>
								</span>
							</div>
	    				</c:forEach>
	    				</c:if>
	    			</span>
	    		</li>
	    		<li class="align-center flex mgB20">
	    			<p style="width: 60px; font-weight:700;">${menu.LN00233}</p>
	    			<div>${boardMap.SC_END_DT}</div>
	    		</li>
	    	</ul>
		</div>
	    </c:if>
	</div>
	
	<div class="mgB50 mgT25" style="color:#000;min-height: 80px;">${boardMap.Content}</div>
	
	<c:set value="0" var="f_size" />
	<c:forEach var="f_result" items="${fileList}" varStatus="status">
		<c:if test="${f_result.BoardID == boardMap.BoardID}">
			<c:set var="f_size" value="${f_size+1}"/>
		</c:if>
	</c:forEach>
		<div class="mgB25">
			<c:if test="${f_size !=0}">
			<p class="mgB10">${menu.LN00111 }<span>&nbsp;<c:set value="${fileList.size()}" var="f_count" />${f_size }</span></p>
			</c:if>
			<div class="flex justify-between align-end">
			<ul class="file_box" <c:if test="${f_size eq 0}">style="visibility: hidden;"</c:if>>
			<c:if test="${f_size !=0}">
				<c:forEach var="f_result" items="${fileList}" varStatus="status">
					<c:if test="${f_result.BoardID == boardMap.BoardID}">
					<li onclick="fileNameClick('${f_result.Seq}')">
						<span class="btn_pack small icon mgR30">
						<c:set var="FileFormat" value="${fn:split(f_result.FileName,'.')[1]}" />
							<span class="
									<c:choose>
										<c:when test="${fn:contains(FileFormat, 'do')}">doc</c:when>
										<c:when test="${fn:contains(FileFormat, 'xl')}">xls</c:when>
										<c:when test="${fn:contains(FileFormat, 'pdf')}">pdf</c:when>
										<c:when test="${fn:contains(FileFormat, 'hw')}">hwp</c:when>
										<c:when test="${fn:contains(FileFormat, 'pp')}">ppt</c:when>
										<c:otherwise>log</c:otherwise>
									</c:choose>
							"></span>
						</span>
						<span style="line-height:24px;">${f_result.FileRealName}</span></li>
					</c:if>
				</c:forEach>
			</c:if>
			</ul>
			<div>
				<c:if test="${sessionScope.loginInfo.sessionUserId == boardMap.RegUserID}">
		        	<button class="cmm-btn mgR5" style="height: 32px;" onclick="actionForum('edit')">Edit</button>
					<button class="cmm-btn mgR5" style="height: 32px;" onclick="actionForum('del')">Del</button>
		        </c:if>
				<c:if test="${ItemMgtUserMap.AuthorID == sessionScope.loginInfo.sessionUserId || sessionScope.loginInfo.sessionAuthLev == '1'}">
				<button class="cmm-btn mgR5" id="change_item" style="height: 32px;">Change Item</button>
				</c:if>
			</div>
			</div>
		</div>
		
	<div class="reply_box">
	<p>Reply<span style="color:#4265ee;">&nbsp;${replyListCnt}</span></p>
	<c:forEach var="result" items="${replyList}" varStatus="status">
		<ul class="reply_list" id="reply_list${result.BoardID}">
			<li class="flex align-center justify-between">
				<div>
				<span class="mem_list mgR10">
					<span class="thumb mini">
						<span lang="ko" class="initial_profile" style="background-color: rgb(134, 164, 212);">
					    	<em>${fn:substring(result.WriteUserNM,0,1)}</em>
					    </span>
				    </span>
				     <span class="name_info">
					     <span class="name_txt">${result.WriteUserNM}</span>
					     <c:if test="${!empty result.NameEN }">
					     <span>(${result.NameEN})</span>
					     </c:if>
				     </span>
				</span>
				<span class="reply_regDT">${result.RegDT }</span>
				</div>
				<c:if test="${sessionScope.loginInfo.sessionUserId == result.RegUserID}">
					<div class="reply_btn">
						<button onclick="editComment(${result.BoardID}, ${ItemID}, ${result.RegUserID})" class="cmm-btn" style="    border-right: 1px solid #dfdfdf;">Edit</button>
						<button onclick="deleteComment(${result.BoardID}, ${ItemID})" class="cmm-btn">Del</button>
					</div>
				</c:if>
			</li>
			<li class="flex justify-between mgT10 mgL23" id="reply_content${result.BoardID}">
				<p>${result.Content }</p>
			</li>
			<c:set value="0" var="f_size" />
			<c:forEach var="f_result" items="${fileList}" varStatus="status">
					<c:if test="${f_result.BoardID == result.BoardID}">
					<c:set var="f_size" value="${f_size+1}"/>
				</c:if>
			</c:forEach>
			<c:if test="${f_size!=0}">
			<ul class="file_box mgT10 mgL23" id="tmp_file_items${result.BoardID }">
				<c:forEach var="f_result" items="${fileList}" varStatus="status">
					<c:if test="${f_result.BoardID == result.BoardID}">
					<li onclick="fileNameClick('${f_result.Seq}')" id="reply_file${f_result.Seq}" class="flex icon_color_inherit justify-between mm align-center">
						<span>
							<span class="btn_pack small icon mgR30">
							<c:set var="FileFormat" value="${fn:split(f_result.FileName,'.')[1]}" />
								<span class="
										<c:choose>
											<c:when test="${fn:contains(FileFormat, 'do')}">doc</c:when>
											<c:when test="${fn:contains(FileFormat, 'xl')}">xls</c:when>
											<c:when test="${fn:contains(FileFormat, 'pdf')}">pdf</c:when>
											<c:when test="${fn:contains(FileFormat, 'hw')}">hwp</c:when>
											<c:when test="${fn:contains(FileFormat, 'pp')}">ppt</c:when>
											<c:otherwise>log</c:otherwise>
										</c:choose>
												"></span>
							</span>
							<span style="line-height:24px;">${f_result.FileRealName}</span>
						</span>
						<i class="mdi mdi-window-close" onclick="fnDeleteItemFile('${f_result.BoardID}','${f_result.Seq}')" style="display:none;"></i>
					</li>
					</c:if>
				</c:forEach>
			</ul>
			</c:if>
		</ul>
	</c:forEach>
	</div>
	
	<div class="register_box" id="register_box" style="display:none;">
		<div class="wrapper">
			<div class="user_Name">${sessionScope.loginInfo.sessionUserNm}</div>
			<div class="scroll_box" id="scroll_target">
				<div class="input_box" id="input_target" contenteditable="true"></div>
				<span class="placeholder">Please enter a comment</span>
			</div>
			<ul id="tmp_file_items" name="tmp_file_items" class="file_box mgB15 mgL15 mgT10 tmp_file_items"></ul>
		</div>
		<div class="btn_box">
			<div class="attach_btns icon_color_inherit" onclick="doAttachFileV4()" style="cursor:pointer"><i class='mdi mdi-paperclip'></i></div>
			<div class="register_btns">
				<button type="button" class="point" id="send">Input</button>
	      	</div>
		</div>
	</div>
	
	<div class="flex align-center justify-between mgT20 pdB10">
		<div></div>
		<div>
	   		<button class="cmm-btn mgR5" id="prev" style="height: 32px;"><span class="arrow-left mgR5"></span>Prev</button>
	   		<button class="cmm-btn mgR5" id="next" style="height: 32px;">Next<span class="arrow-right mgL5"></span></button>
			<button class="cmm-btn mgR5" style="height: 32px;" onclick="goBack()">List</button>
	   		<button class="cmm-btn" id="top" style="height: 32px;">top</button>
	   	</div>
	</div>
	
</form>	
</div>
<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>