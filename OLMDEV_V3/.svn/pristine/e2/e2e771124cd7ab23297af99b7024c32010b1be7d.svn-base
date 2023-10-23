<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:url value="/" var="root"/>

<link rel="stylesheet" href="${root}cmm/common/css/materialdesignicons.min.css"/>
 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002" />

<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>
<%@ include file="/WEB-INF/jsp/cmm/fileAttachHelper.jsp"%>

<script type="text/javascript">

//파일 업로드 처리 이동 [fileAttachHelper.js]
var emailCode = "${emailCode}";
const category = "${category}" ? "${category}" : "${resultMap.CategoryCode}";

if(document.querySelector("#tmp_file_items").innerText.replaceAll("\n","").replaceAll("\t","") != "") {
	document.querySelector("#tmp_file_items").style.display = "block";
}

	$(document).ready(function() {		
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&userID=${sessionScope.loginInfo.sessionUserId}&BoardMgtID=${BoardMgtID}&projectType=${projectType}&templProjectID=${templProjectID}";
		fnSelect('category', data, 'getBoardMgtCategory', category, 'Select');
		
		$("#send").click(function(e) {
			if(confirm("${CM00001}")){
				saveDetail();
			}
		});
		
		$('#back').click(function(e){
			doReturn();
		});
	});
	
	function saveDetail() {
		var date = new Date();
		var str = date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate() + ' ' + date.getHours()
				+ ':' + date.getMinutes() + ':'
				+ date.getSeconds() + '.'
				+ date.getMilliseconds();
		$('#date').attr('value',str);
		$('#isNew').attr('value','1');
		$('#deleteSeq').attr('value',document.getElementById("deleteSeq").value);
		
		var url  = "updateForumPost.do";
		ajaxSubmit(document.editforumFrm, url);
	}
	var boardTitle = "${boardTitle}";
	function doReturn(){ 
		var listType = "${listType}";
		var url = "viewForumPost.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					+ "&pageNum=${pageNum}&noticType=${noticType}&BoardMgtID=${BoardMgtID}&boardID=${boardID}&isMyCop=${isMyCop}"
					+ "&s_itemID=${itemID}&ItemID=${itemID}&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
					+ "&searchType=${searchType}"+"&searchValue=${searchValue}"
					+ "&mailRcvListSQL=${mailRcvListSQL}"
					+ "&emailCode="+emailCode
					+ "&screenType=${screenType}&projectID=${projectID}&listType=${listType}"
					+ "&srID=${srID}&srType=${srType}&boardIds=${boardIds}&showItemInfo=${showItemInfo}&scrnType=${scrnType}"
					+ "&boardTitle="+encodeURIComponent(boardTitle);
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
	function fnDeleteFileMapV4(fileID){ 
		fileID = fileID.replace("u","");		
		fileIDMapV4.delete(String(fileID));
		fileNameMapV4.delete(String(fileID));
	}
	
	function fnDisplayTempFileV4(){
		document.getElementById("tmp_file_items").style.display = "block";
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
		
		document.querySelector("#tmp_file_items").innerHTML = display_scripts;

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
		
		if(document.querySelector("#tmp_file_items").innerText.replaceAll("\n","").replaceAll("\t","") == "") {
			document.querySelector("#tmp_file_items").style.display = "";
		}
	}
	//************** addFilePop V4 설정 END ************************//

	/* 첨부문서 다운로드 */
	function FileDownload(checkboxName, isAll){
		var seq = new Array();
		var j =0;
		var checkObj = document.all(checkboxName);
		
		// 모두 체크 처리를 해준다.
		if (isAll == 'Y') {
			if (checkObj.length == undefined) {
				checkObj.checked = true;
			}
			for (var i = 0; i < checkObj.length; i++) {
				checkObj[i].checked = true;
			}
		}
		
		// 하나의 파일만 체크 되었을 경우
		if (checkObj.length == undefined) {
			if (checkObj.checked) {
				seq[0] = checkObj.value;
				j++;
			}
		};
		for (var i = 0; i < checkObj.length; i++) {
			if (checkObj[i].checked) {
				seq[j] = checkObj[i].value;
				j++;
			}
		}
		if(j==0){
			alert("${WM00049}");
			return;
		}
		j =0;
		var url  = "fileDownload.do?seq="+seq;
		//alert(url);
		ajaxSubmitNoAdd(document.editforumFrm, url,"saveFrame");
		// 모두 체크 해제
		if (isAll == 'Y') {
			if (checkObj.length == undefined) {
				checkObj.checked = false;
			}
			for (var i = 0; i < checkObj.length; i++) {
				checkObj[i].checked = false;
			}
		}
	}
	function fileNameClick(avg1){
		var seq = new Array();
		seq[0] = avg1;
		var url  = "fileDownload.do?seq="+seq;
		ajaxSubmitNoAdd(document.editforumFrm, url,"saveFrame");
	}

</script>

<div class="pdB15 mgT10" style="width: 70%; margin: 0 auto;">
	<form name="editforumFrm" id="editforumFrm" enctype="multipart/form-data" action="editForumPost.do" method="post" onsubmit="return false;">
		<input type="hidden" id="noticType" name="noticType" value="${noticType}">
		<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}" />	
		<input type="hidden" id="languageID" name="languageID" value="${sessionScope.loginInfo.sessionCurrLangType}">
		<input type="hidden" id="boardID" name="boardID" value="${boardID}">
		<input type="hidden" id="BoardMgtID" name="BoardMgtID" value="${BoardMgtID}">
		<input type="hidden" id="userId" name="userId" value="${sessionScope.loginInfo.sessionUserId}">
		<input type="hidden" id="itemID" name="itemID" value="${itemID}">
		<input type="hidden" id="deleteSeq" name="deleteSeq">
		<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}">
		<input type="hidden" id="projectID" name="projectID" value="${projectID}" />
		<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
		<input type="hidden" id="searchValue" name="searchValue" value="${searchValue}" />
		<input type="hidden" id="scStartDt" name="scStartDt" value="${scStartDt}" />
		<input type="hidden" id="scEndDt" name="scEndDt" value="${scEndDt}" />	
		<input type="hidden" id="screenType" name="screenType" value="${screenType}" />	
		<input type="hidden" id="listType" name="listType" value="${listType}" />	
		<input type="hidden" id="mailRcvListSQL" name="mailRcvListSQL" value="${mailRcvListSQL}" />	
		<input type="hidden" id="showItemInfo" name="showItemInfo" value="${showItemInfo}" />	
		<div class="align-center flex justify-between pdT20 pdB10"  style="border-bottom: 1px solid #dfdfdf;">
			<p style="font-size: 13px;color: #8d8d8d;">${boardTitle}&nbsp;&gt;&nbsp;Edit</p>
		</div>
		<ul>
			<li class="flex align-center pdT15 pdB5">
				<c:if test="${CategoryYN == 'Y'}">
				<div class="align-center flex" style="flex: 2 1 0;">
					<h3 class="tx">${menu.LN00002 }</h3>
					<span class="wrap_sbj">
						<input type="text" name="subject" id="subject" value="${forumInfo.Subject}">
					</span>
				</div>
				<div class="align-center flex mgL30" style="flex: 1 1 0;">
					<h3 class="tx">${menu.LN00033 }</h3>
					<select id="category" name="category" class="form-sel" style="flex: 1 1 0;"></select>
				</div>
				</c:if>		
				<c:if test="${CategoryYN != 'Y'}">
				<div class="align-center flex" style="flex: 2 1 0;">
					<h3 class="tx">${menu.LN00002 }</h3>
					<span class="wrap_sbj">
						<input type="text" name="subject" id="subject" value="${forumInfo.Subject}">
					</span>
				</div>
				</c:if>
			</li>
			<c:if test="${!empty s_itemID && s_itemID ne '0'}">
			<li class="align-center flex pdT15 pdB15">
				<h3 class="tx">${menu.LN00087 }</h3>
				<span class="wrap_sbj">${path}</span>
			</li>
			<li class="align-center flex pdT15 pdB15">
				<h3 class="tx">${menu.LN00004}</h3>
				<span class="wrap_sbj">${ItemMgtUserMap.Name}(${ItemMgtUserMap.NameEN})/${ItemMgtUserMap.teamName}<input type="hidden" name="ItemMgtUserID" id="ItemMgtUserID" value="${ItemMgtUserMap.AuthorID}" /></span>
			</li>
			</c:if>
			<c:if test="${mailRcvListSQL eq 'manual' }">
			<li class="align-center flex pdT15 pdB15">
				<h3 class="tx">${menu.LN00245 }</h3>
				<span class="wrap_sbj">${forumInfo.sharerNames}</span>
			</li>
			</c:if>
			<c:if test="${!empty mailRcvListSQL && mailRcvListSQL ne ''}">
			<li class="align-center flex pdT15 pdB15">
				<h3 class="tx">${menu.LN00389}</h3>
				<span class="wrap_sbj">${forumInfo.SC_END_DT}</span>
			</li>
			</c:if>
			<li class="flex pdT5 pdB5">
				<h3 class="tx mgT12">${menu.LN00111 }</h3>
				<div style="width:calc(100% - 100px);">
					<button class="cmm-btn" onclick="doAttachFileV4()">Attach</button>
					<ul id="tmp_file_items" name="tmp_file_items" class="file_box mgB5 mgT10 tmp_file_items">
					<c:forEach var="fileList" items="${itemFiles}" varStatus="status">
						<li class="flex icon_color_inherit justify-between mm align-center" id="divDownFile${fileList.Seq}">
							<span>
								<span class="btn_pack small icon mgR25">
								<c:set var="fileName" value="${fn:split(fileList.FileRealName,'.')}" />
								<c:forEach var="FileFormat" items="${fileName}" varStatus="seq">
									<c:if test="${seq.count == 2}">
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
									</c:if>
								</c:forEach>
								</span>
								<span style="line-height:24px;" onclick="fileNameClick('${fileList.Seq}');">${fileList.FileRealName}</span>
							</span>
							<i class="mdi mdi-window-close" onclick="fnDeleteItemFile('${fileList.BoardID}','${fileList.Seq}')"></i>
						</li>
					</c:forEach>
					</ul>
				</div>
			</li>
			<li class="flex pdT10 pdB10">
				<textarea  class="tinymceText" id="Content" name="Content" style="width:100%;height:350px;">${forumInfo.Content}</textarea>
			</li>
		</ul>
		<div class="alignR">
			<input name="userId" id="userId" type="hidden"	value="${sessionScope.loginInfo.sessionUserId}" />
			<input name="date" id="date" type="hidden"/>
			<button class="cmm-btn mgT10 mgR5" id="back" style="color: #333333;">Cancel</button>
			<button class="btn-4265EE cmm-btn mgT10" id="send">Submit</button>
		</div>
	</form>
</div>

<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
