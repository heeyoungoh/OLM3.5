﻿﻿﻿﻿﻿<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/" var="root"/>
 

<!-- 화면 표시 메세지 취득  -->
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
$(document).ready(function(){
	
// 	if(document.getElementById('help_content')!=null&&document.getElementById('help_content')!=undefined){
// 		document.getElementById('help_content').style.height = (setWindowHeight() - 60)+"px";			
// 		window.onresize = function() {
// 			document.getElementById('help_content').style.height = (setWindowHeight() - 80)+"px";	
// 		};
// 	}
	
	var listType = "${listType}";
	var commentNumArray = new Array();
	var commentNum = 0;
	$('#back').click(function(e){
		var url = "boardForumList.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}"
					+ "&pageNum="+$("#currPage").val()+"&noticType=${noticType}&BoardMgtID=${BoardMgtID}&isMyCop=${isMyCop}"
					+ "&scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
					+ "&searchType=${searchType}"+"&searchValue=${searchValue}"
					+ "&mailRcvListSQL=${mailRcvListSQL}"
					+ "&emailCode="+emailCode
					+ "&screenType=${screenType}&projectID=${projectID}&listType=${listType}&srID=${srID}&scrnType=${scrnType}";
		if(listType == 1){data = data + "&s_itemID=${s_itemID}"}			
		var target = "help_content";
		ajaxPage(url, data, target);
	});
	
	$('#make_new').click(function(e){
		var url = "registerForumPost.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
			+ "&s_itemID="+$('#s_itemID').val()
			+ "&ItemID="+$('#ItemID').val()
			+ "&userId="+$('#userId').val()
			+ "&parentID=${boardID}"
			+ "&pageNum=${pageNum}"
			+ "&replyLev=1"
			+ "&refID="+$('#refID').val()
			+ "&noticType=${noticType}"
			+ "&BoardMgtID=${BoardMgtID}"
			+ "&isMyCop=${isMyCop}"
			+ "&listType=${listType}"
			+ "&screenType=${screenType}&projectID=${projectID}"
			+ "&userId="+$('#userId').val()
			+ "&memberID=${boardMap.RegUserID}"
			+ "&subject=${boardMap.Subject}"
			+ "&mailRcvListSQL=${mailRcvListSQL}"
			+ "&emailCode="+emailCode
			+ "&srID=${srID}"
			+ "&scrnType=${scrnType}";
		var target = "help_content";
		ajaxPage(url, data, target);
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
	
	// 레이어 팝업 표시
	var layerWindow = $('.mw_layer');
	
	$('.layer_trigger').click(function(){
		var pos = $('#dvd70_file').position();   
		LeyerPopupView('dvd70_file', 'layerPopup', pos.top, -pos.left+30);
		layerWindow.addClass('open');
		// 화면 스크롤시 열려있는 레이어 팝업창을 모두 닫음
		document.getElementById("help_content").onscroll = function() {
			// 본문 레이어 팝업
			layerWindow.removeClass('open');
			for (var i = 0; i < commentNumArray.length; i++) {
				$('.mw_layer_comment' + commentNumArray[i]).removeClass('open');
			}
			commentNum = 0;
		}; 
		
	});
	
	
	// 레이어 팝업 닫기
	$('.closeBtn').click(function(){
		layerWindow.removeClass('open');
	});
	
	$('.layer_comment').click(function(){
		var commentNo = $(this).attr('alt');
		var layerWindowComment = $('.mw_layer_comment' + commentNo);
		$('.layer_comment').each(function(){
			LeyerPopupView('dvd70com_file' + commentNo, 'layerPopupComment' + commentNo, commentNo, 80);
			layerWindowComment.addClass('open');
		});
		$('.close' + commentNo).click(function(){
			layerWindowComment.removeClass('open');
		});
		
		commentNumArray[commentNum] = commentNo;
		commentNum++;
		document.getElementById("help_content").onscroll = function() {
			// 본문 레이어 팝업
			layerWindow.removeClass('open');
			for (var i = 0; i < commentNumArray.length; i++) {
				$('.mw_layer_comment' + commentNumArray[i]).removeClass('open');
			}
			commentNum = 0;
		};
		
	});
	
	// 로그인 유저의 입력된 별점을 화면 표시
	try{fnSetScore();}catch(e){}
	
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

function editComment(boardID, ItemID, regUserID) {
	if(confirm("${CM00005}")){
		var url = "registerForumPost.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
			+ "&s_itemID="+$('#s_itemID').val()
			+ "&ItemID="+ItemID
			+ "&userId="+$('#userId').val()
			+ "&parentID="+$('#boardID').val()
			+ "&boardID="+boardID
			+ "&replyLev=1"
			+ "&pageNum=${pageNum}"
			+ "&refID="+$('#refID').val()
			+ "&noticType=${noticType}"
			+ "&BoardMgtID=${BoardMgtID}"
			+ "&isMyCop=${isMyCop}"
			+" &pageNum="+$("#currPage").val()
			+" &scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
			+" &searchType=${searchType}"+"&searchValue=${searchValue}"
			+ "&listType=${listType}"
			+ "&emailCode="+emailCode
			+ "&screenType=${screenType}&projectID=${projectID}"
			+ "&regUserID="+regUserID;
		var target = "help_content"; 
		ajaxPage(url, data, target);
	}		
}

function deleteComment(boardID, ItemID){
	if(confirm("${CM00002}")){
		var url = "boardCommentDelete.do";
		var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}" 
			+ "&s_itemID="+ItemID
			+ "&ItemID="+ItemID
			+ "&userId="+$('#userId').val()
			+ "&parentID="+$('#boardID').val()
			+ "&boardID="+boardID
			+ "&BoardMgtID=${BoardMgtID}"
			+ "&pageNum=${pageNum}"
			+ "&noticType=${noticType}"
			+" &pageNum="+$("#currPage").val()
			+" &scStartDt=${scStartDt}"+"&scEndDt=${scEndDt}"
			+" &searchType=${searchType}"+"&searchValue=${searchValue}"
			+ "&listType=${listType}"
			+ "&emailCode="+emailCode
			+ "&screenType=${screenType}&projectID=${projectID}";
		var target = "help_content";
		ajaxPage(url, data, target);
	}
} 

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
		+ "&mailRcvListSQL=${mailRcvListSQL}"
		+ "&emailCode="+emailCode
		+ "&srID=${srID}&scrnType=${scrnType}";
	if(confirm(confirmStr)){
		ajaxPage(actionUrl, data, target);
	}
}


function LeyerPopupView(sLinkName, sDivName, nTopKbn, nLeft)  { 
	var oPopup = document.getElementById(sDivName);
	var oLink = document.getElementById(sLinkName);
	var scrollTop = document.getElementById("help_content").scrollTop;
	
	var nTop;
	// 레이어 팝업 위치 설정
	if (nTopKbn == 0) {
		nTop = 15;
	} else {
		nTop = 17 + (nTopKbn - 1);
	}
	
	oPopup.style.top = (oLink.offsetTop + nTop - scrollTop) + "px";    
	oPopup.style.left = (oLink.offsetLeft - nLeft) + "px";
} 

function fileNameClick(avg1){
	var url  = "fileDown.do?seq=" + avg1 + "&scrnType=BRD";
	ajaxSubmitNoAdd(document.formDetailFrm, url,"saveFrame");
}

function fnCallBack(parentID,s_itemID){ 	
	var data = "NEW=N&BoardMgtID=${BoardMgtID}&noticType=${noticType}&boardID="+parentID
				+ "&emailCode="+emailCode
				+ "&ItemID="+s_itemID+"&s_itemID="+s_itemID+"&pageNum=${pageNum}&isMyCop=${isMyCop}&listType=${listType}";
	var url = "viewForumPost.do";
	var target = "help_content"; 
	ajaxPage(url, data, target);
}

// 본문 글 삭제 콜백
function fnCallBackDel(){ 	
	var url = "boardForumList.do";
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&s_itemID=${s_itemID}&BoardMgtID=${BoardMgtID}&noticType=${noticType}"
				+ "&emailCode="+emailCode
				+ "&isMyCop=${isMyCop}&listType=${listType}&srID=${srID}";
	var target = "help_content";

	ajaxPage(url, data, target);
}

function fnOpenItemPop(itemID){
	
    var changeSetID = ${boardMap.BrdChangeSetID};
	
	var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemID+"&scrnType=pop";
	
	if (changeSetID != "")	{
		var url = url + "&option=CNGREW&changeSetID="+changeSetID;
	}
	
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


</script>
<div class="mgL10 mgR10">
<form name="formDetailFrm" id="formDetailFrm" method="post" onsubmit="return false;">
	<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}">
	<input type="hidden" id="filter" name="filter" value="edit">
	<input type="hidden" id="boardID" name="boardID" value="${boardID}">
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
	<input type="hidden" id="subject" name="subject" value="${boardMap.Subject}" >
	
	<div class="cop_hdtitle">
	<h3 style="padding: 8px 0;"><img src="${root}${HTML_IMG_DIR}/comment_user.png">&nbsp;&nbsp;${boardMgtName}&nbsp;</h3>
	</div>
	<!-- comment 상세화면 -->
	  <table cellpadding="0" cellspacing="0" width="100%" class="tbl_comview">
		<colgroup>
			<col width="15%">
			<col width="35%">
			<col width="35%">
			<col width="15%"> 
		 </colgroup>
		
		<tr>
			<th colspan="3">${boardMap.Subject}</th>
			<th class="ac alignR pdR10">${boardMap.LastUpdated}</th>
		</tr>
	     <tr>
	        <td colspan="3" >${boardMap.WriteUserNM}(${boardMap.LoginID})</td>
	        <td class="alignR pdR10">
	        <div id="dvd70reply_right">
				<c:set value="${fileList.size()}" var="f_count" />
				<c:set value="0" var="f_size" />
				<c:forEach var="f_result" items="${fileList}" varStatus="status">	
					<c:if test="${f_result.BoardID == boardMap.BoardID}" >				
						<c:set var="f_size" value="${f_size+1}"/>					
						<c:set var="f_count" value="${f_count-1}" />
					</c:if>
				</c:forEach>
				<c:if test="${f_size!=0}">
					<div id="dvd70_file" style="cursor:pointer;">
					<span class="layer_trigger"><img src="${root}${HTML_IMG_DIR}/btn_cop_filedown.png" style="padding-top:3px">&nbsp;<b>${menu.LN00111}<font color="#c01020">(${f_size})</font></b></span></div>
				</c:if>
			</div>
	        </td>
	    </tr>
	   <tr>
	       <td id="content" height="50px" colspan="4" >${boardMap.Content}</td>
	    </tr>
	  
		<tr>
			<td style="border-top:1px dashed #dfdfdf" colspan="4" class="alignR pdR10">
		    	<c:if test="${LikeYN == 'Y'}">
		    	<span id="saveLike" style="float:left;">
					<img src="${root}${HTML_IMG_DIR}/Like${boardMap.LikeInfo}.png" onclick="doLike('${boardMap.LikeInfo}')" style="cursor:pointer;">
				</span>
				<span style="float:left;padding-top:5px;">(${likeCNT})</span>
				</c:if>
				<c:if test="${boardMap.Category != '-' || boardMap.Category ne '-'}">
					<span style="display:inline-block;margin-top:5px;"><b>Category</b> :	${boardMap.Category} &nbsp;</span>
				</c:if>
				<c:if test="${!empty boardMap.ItemID && boardMap.ItemID ne '0'}">
					<span style="cursor:pointer; color:#0054FF;display:inline-block;margin-top:5px;" Onclick="fnOpenItemPop(${boardMap.ItemID})">
						<b style="color:#000;">Item</b> :	${boardMap.Path}/${boardMap.ItemName} &nbsp;
					</span>
					<span>
						 <b>${menu.LN00004}</b> :	${ItemMgtUserMap.Name}(${ItemMgtUserMap.NameEN})/${ItemMgtUserMap.teamName}
					</span>
					<input type="hidden" name="ItemMgtUserID" id="ItemMgtUserID" value="${ItemMgtUserMap.AuthorID}" />
				</c:if>
			</td>
		</tr>
		
		<c:if test="${!empty(boardMap.sharerNames) }">
		    <tr>		    
		   	 	<th>${menu.LN00245}</th>
				<td colspan="3"  style="border-top:1px dashed #dfdfdf" >${boardMap.sharerNames}</td>
		    </tr>
	    	<tr>
				<th>${menu.LN00233}</th>
				<td colspan="3" style="border-top:1px dashed #dfdfdf" >${boardMap.SC_END_DT}</td>
	    	</tr>
	    </c:if>
	    
    </table>
    <!-- //reply DIV-->
	<div class="clear"></div>
	<div class="alignBTN">
		<c:if test="${sessionScope.loginInfo.sessionUserId == boardMap.RegUserID}">
        	<span class="btn_pack small icon"><span class="edit"></span><input value="Edit" type="submit" id="edit" onClick="actionForum('edit')"></span>
        	<span class="btn_pack small icon"><span class="del"></span><input value="Del" type="submit" id="edit" onClick="actionForum('del')"></span>
        </c:if>
		<c:if test="${ItemMgtUserMap.AuthorID == sessionScope.loginInfo.sessionUserId || sessionScope.loginInfo.sessionAuthLev == '1'}">
		<span class="btn_pack small icon"><span class="move"></span><input value="Change Item" type="submit" id="change_item"></span>
		</c:if>
		<c:choose>
			<c:when test="${replyOption == '0'}">
				<span class="btn_pack medium icon"><span class="write"></span><input value="Reply" type="submit" id="make_new"></span>
			</c:when>
			<c:when test="${replyOption == '1' && (ItemMgtUserMap.AuthorID == sessionScope.loginInfo.sessionUserId or boardMap.ReplyFlag eq 'T') }">
				<span class="btn_pack medium icon"><span class="write"></span><input value="Reply" type="submit" id="make_new"></span>
			</c:when>
			<c:when test="${replyOption == '2' && (BoardMgtInfo.MgtUserID == sessionScope.loginInfo.sessionUserId or boardMap.ReplyFlag eq 'T')}">
				<span class="btn_pack medium icon"><span class="write"></span><input value="Reply" type="submit" id="make_new"></span>
			</c:when>
			<c:when test="${replyOption == '3' && boardMap.ReplyFlag eq 'T'}">
				<span class="btn_pack medium icon"><span class="write"></span><input value="Reply" type="submit" id="make_new"></span>
			</c:when>
		</c:choose>
		<span class="btn_pack medium icon"><span class="list"></span><input value="List" type="submit" id="back"></span>
	</div>
    
    <!-- comment Replay  -->
    <div class="cb_module cb_fluid mgT10">
    <h5 class="cb_h_type cb_h_type2">Reply <span>(<strong>${replyListCnt}</strong>)</span></h5>
	
    <!-- Comment List -->
    <div class="cb_lstcomment02">
    <ul>
    <c:if test="${not empty replyList}">
	<c:set value="${replyList.size()}" var="count" />
	<c:forEach var="result" items="${replyList}" varStatus="status">
	<c:set var="commentNo" value="${commentNo+1}"/>      
        <li class="cb_thumb_off">
            <div class="cb_comment_area">
                <div class="cb_info_area">
                    <div class="cb_section">
                        <span class="cb_nick_name"><img src="${root}${HTML_IMG_DIR}/icon_cop_reply.png">&nbsp;${result.WriteUserNM}</span>
                        <span class="cb_usr_id">(${result.LoginID})</span>
                        <span class="cb_date">${result.RegDT}</span>
                    </div>
                    <div class="cb_section2">
                    <c:if test="${sessionScope.loginInfo.sessionUserId == result.RegUserID}">
                        <span class="cb_nobar"><a href="javascript:editComment(${result.BoardID}, ${ItemID}, ${result.RegUserID})">Edit</a></span>
                        <span class="cb_nobar"><a href="javascript:deleteComment(${result.BoardID}, ${ItemID})">Del</a></span>
                    </c:if>
                    </div>
                     <div id="dvd70reply_right" style="float:right;margin-right:90px;cursor:pointer;" >                  
						<c:set value="${replyFileCnt}" var="f_count" />
						<c:set value="0" var="f_size" />
						<c:forEach var="f_result" items="${fileList}" varStatus="status">
							<c:if test="${result.BoardID == f_result.BoardID}" >
								<c:set var="f_size" value="${f_size+1}"/>
								<c:set var="f_count" value="${f_count-1}" />
							</c:if>
						</c:forEach>
						<c:if test="${f_size!=0}">
						<!-- 레이어 팝업 (코멘트용) DIV-->	
						<div class="mw_layer_comment mw_layer_comment${commentNo}" id="layerPopupComment${commentNo}"> 
							<c:set value="${fileList.size()}" var="f_count" />
							<c:set value="0" var="f_size" />
							<c:forEach var="f_result" items="${fileList}" varStatus="status">
								<c:if test="${f_result.BoardID == result.BoardID}">
								<a style="cursor:pointer;" onclick="fileNameClick('${f_result.Seq}')">${f_result.FileRealName}</a>
								<c:if test="${f_size==0}">
									<span class="close${commentNo}"><span style="position:absolute;right:0px;">X&nbsp;</span></span>			
								</c:if>
								<br/>
								<c:set var="f_size" value="${f_size+1}"/>
								</c:if>
								<c:set var="f_count" value="${f_count-1}" />
							</c:forEach> 
						</div>
						<div id="dvd70com_file${commentNo}" style="cursor:pointer;">
							<span class="layer_comment" alt="${commentNo}"><img src="${root}${HTML_IMG_DIR}/btn_cop_filedown.png" style="padding-top:3px">&nbsp;<b>${menu.LN00111}<font color="#c01020">(${f_size})</font></b></span>
						</div>
						</c:if>
					</div>
                </div>
                <div class="cb_dsc_comment" style="overflow:hidden;height:auto;">
                    <p class="cb_dsc">
                        ${result.Content}
                    </p>
                </div>
            </div>
        </li>
    <c:set var="count" value="${count-1}" />
	</c:forEach>
	</c:if>
    </div>
    </ul>
    </div>
	<!-- 첨부파일 레이어 팝업 (본문용) -->	
	<div class="mw_layer" id="layerPopup"> 
		<c:set value="${fileList.size()}" var="f_count" />
		<c:set value="0" var="f_size" />
		<c:forEach var="f_result" items="${fileList}" varStatus="status">
			<c:if test="${f_result.BoardID == boardMap.BoardID}">
			<a style="cursor:pointer;" onclick="fileNameClick('${f_result.Seq}')">${f_result.FileRealName}</a>
			<c:if test="${f_size==0}">
				<span class="closeBtn"><span style="position:absolute;right:0px;">X&nbsp;</span></span>		
			</c:if>
			<br/>
			<c:set var="f_size" value="${f_size+1}"/>
			<c:set var="f_count" value="${f_count-1}" />
			</c:if>
		</c:forEach>   
	</div> 
</form>	
</div>
<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>