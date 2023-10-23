<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<c:url value="/" var="root"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00033" var="WM00033" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00049" var="WM00049" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00085" var="WM00085_1" arguments="${menu.LN00004}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00085" var="WM00085_2" arguments="${menu.LN00023}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00023" var="CM00023" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00024" var="CM00024" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00049" var="CM00049" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00059" var="CM00059" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001" />
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00085" var="WM00085_3" arguments="${menu.LN00035}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00085" var="WM00085_4" arguments="${menu.LN00017}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00085" var="WM00085_5" arguments="${menu.LN00022}"/>

<script type="text/javascript">

	var revListSize = "${revisionList.size()}";
	var nOdListSize = "${nOdList.size()}";
	var changeType = "${getData.ChangeTypeCode}";
	
	$(document).ready(function(){
		$("input.datePicker").each(generateDatePicker);
				
		$('#saveChangeSetInfo').click(function(e){
			saveChangeSetInfo();
		});
		
		var data = "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";	
		fnSelect('changeType', data+"&category=CNGT1", 'getDictionaryOrdStnm', '${getData.ChangeTypeCode}', 'Select');	
		
		if(document.getElementById('itemListBox')!=null&&document.getElementById('itemListBox')!=undefined){
			document.getElementById('itemListBox').style.height = (setWindowHeight() - 460)+"px";			
			window.onresize = function() {
				document.getElementById('itemListBox').style.height = (setWindowHeight() - 480)+"px";	
			};
		}

		var desHeight = 150;
		
		if(revListSize > 0) {
			desHeight += 40;
		}
		
		if(nOdListSize > 0 && changeType == 'MOD') {
			desHeight += 40;
		}

		$("#description").attr("style","width:100%;height:"+desHeight+"px;");
	});
	
	function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}
	
	// [edit] or [back] click
	function goEditOrView(avg) {
		var isItemInfo = "${isItemInfo}";
		var isStsCell = "${isStsCell}";
		var isMyTask = "${isMyTask}";
		var url = "viewItemChangeInfo.do";
		var target = "changeInfoViewFrm";
		var data = "screenMode="+avg+"&changeSetID=${getData.ChangeSetID}&StatusCode=${StatusCode}&isAuthorUser=Y"
				+ "&ProjectID=${ProjectID}&LanguageID=${sessionScope.loginInfo.sessionCurrLangType}&isNew=${isNew}"
				+ "&mainMenu=${mainMenu}&s_itemID=${s_itemID}&isItemInfo=${isItemInfo}&seletedTreeId=${seletedTreeId}"
				+ "&currPageA=${currPageA}&isMyTask=${isMyTask}";
		
		if (isItemInfo == "Y" && isStsCell == "Y") {
			url = "itemHistory.do"; // 변경이력
			data = "s_itemID=${seletedTreeId}";
		}
		if (isMyTask == "Y") {
			target = "changeInfoLstTskFrm";
		}

		ajaxPage(url, data, target);
	}
	
	// [Item] click
	function goItemPopUpNoID() {
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id=${getData.ItemID}&scrnType=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,"${getData.ItemID}");
	}
	
	// [List] click
	function goBack() {
		var isItemInfo = "${isItemInfo}";
		var isStsCell = "${isStsCell}";
		var isMyTask = "${isMyTask}";
		var url = "changeInfoList.do"; // 변경항목 목록으로 이동
		var target = "help_content";
		var data = "ProjectID=${ProjectID}&mainMenu=${mainMenu}&screenMode=${screenMode}" 
			+ "&isItemInfo=${isItemInfo}&seletedTreeId=${seletedTreeId}&isNew=${isNew}"
			+ "&currPageA=${currPageA}&isFromPjt=${isFromPjt}&s_itemID=${s_itemID}"
			+ "&isMyTask=${isMyTask}";
			
		if (isItemInfo == "Y" && isStsCell == "Y") {
			url = "itemHistory.do"; // 변경이력
			data = "s_itemID=${seletedTreeId}";
		}
		if (isMyTask == "Y") {
			url = "myChangeSet.do";
		}
		
		ajaxPage(url, data, target);
	}
		
	// [Rework] click
	function rework() {
		if (confirm("${CM00059}")) {
			var url = "rework.do";
			$("#item").val("${getData.ItemID}");
			$("#cngt").val("${getData.ChangeSetID}");
			$("#pjtId").val("${ProjectID}");
			ajaxSubmit(document.changeInfoViewFrm, url);
		}
	}
	
	function fnCallBack(){
		var isItemInfo = "${isItemInfo}"; 
		if(isItemInfo == "Y"){ opener.fnCngCallBack();
		}else{ opener.doSearchCngtList(); }		
		self.close();		
	}
	
	function fnEvaluation(){ 
		$("#evDiv").attr("style","visibility:visible");
	}
	
	function fnSaveEvaluation(){
		if (confirm("${CM00001}")) {
			var url = "saveEVSore.do";			
			ajaxSubmit(document.changeInfoEditFrm, url, "saveFrame");
		}
	}
	
	function fnSetEvScore(evScore,attrTypeCode,lovCode){
		$("#evScore"+attrTypeCode).val(evScore);
		$("#lovCode"+attrTypeCode).val(lovCode);
	}
		
	
	function fnCallBack(){
		var isFromPjt = "${isFromPjt}";
		if(isFromPjt == 'Y'){
			opener.doSearchCngtList();
		}else{
			opener.doSearchList();
		}
		self.close();
	}
	
	// 최신 changeSet 이전 changSet 정보 
	function fnOpenViewVersionItemInfo(changeSetID){
		var projectID = "${ProjectID}";
		var authorID = "${getData.AuthorID}";
		var status = "${StatusCode}"
		var version = "${getData.Version}";
		var url = "viewVersionItemInfo.do?s_itemID=${s_itemID}"
					+"&changeSetID="+changeSetID
					+"&projectID="+projectID
					+"&authorID="+authorID
					+"&status="+status
					+"&version="+version;
		window.open(url,'window','width=1100, height=800, left=200, top=100,scrollbar=yes,resizable=yes,resizblchangeTypeListe=0');
	}
	
	// 전자결제 상신 페이지 호출
	function goAprvDetail() {
		var url = "${wfURL}.do";
		var data = "wfInstanceID=${getData.WFInstanceID}&actionType=view&changeSetID=${getData.ChangeSetID}&isMulti=N&wfDocType=CS&projectID=${ProjectID}&docSubClass=${getData.ItemClassCode}";
		var target = "changeInfoViewFrm";
		ajaxPage(url, data, target);
	}		

	// [Item] click
	function goItemPopUp(avg1) {
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+avg1+"&scrnType=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,avg1);
	}

	function goEdit(){
		var url = "editItemChangeInfo.do"
		var data = "changeSetID=${getData.ChangeSetID}&StatusCode=${StatusCode}"
			+ "&LanguageID=${sessionScope.loginInfo.sessionCurrLangType}"
			+ "&mainMenu=${mainMenu}&seletedTreeId=${seletedTreeId}"
			+ "&isItemInfo=${isItemInfo}"
			+ "&screenMode=edit&isMyTask=Y";
		var target = "changeInfoViewFrm";
		
		ajaxPage(url, data, target);
	}

	function goApprovalPop() {
		var url = "${wfURL}.do?";
		var data = "isPop=Y&changeSetID=${getData.ChangeSetID}&isMulti=N&wfInstanceID=${getData.WFInstanceID}&wfDocType=CS&ProjectID=${getData.ProjectID}&docSubClass=${getData.ItemClassCode}";
				
		var w = 1500;
		var h = 1050; 
		itmInfoPopup(url+data,w,h);
		self.close();
	}
	
	function goWfStepInfo(wfDocType,wfUrl,wfInstanceID) {
		var loginUser = "${sessionScope.loginInfo.sessionUserId}";
		var url = wfUrl;
		var data = "isNew=Y&wfDocType="+wfDocType+"&isMulti=N&isPop=Y&categoryCnt=1&changeSetID=${getData.ChangeSetID}&wfInstanceID="+wfInstanceID;
				
		ajaxPage(url,data,"changeInfoViewFrm");
	}

	function setChsFrame(avg){
		
		for(var i=1; i < 4; i++) {
			if(i == avg) {
				$("#tabList"+i).attr("style","display:block;");
				$("#pliOM"+i).addClass("on");
			}
			else {
				$("#tabList"+i).attr("style","display:none;");
				$("#pliOM"+i).removeClass("on");
			}
		}
		
	}

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
		var url  = "fileDownload.do?seq="+seq+"&scrnType=CS";

		ajaxSubmitNoAdd(document.changeInfoViewFrm, url,"blankFrame");
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
		var url  = "fileDownload.do?seq="+seq+"&scrnType=CS";
		ajaxSubmitNoAdd(document.changeInfoViewFrm, url,"blankFrame");
	}
</script>

<form name="changeInfoViewFrm" id="changeInfoViewFrm" enctype="multipart/form-data" action="#" method="post" onsubmit="return false;">
<div id="changeInfoViewDiv" class="hidden" style="overflow:auto; overflow-x:hidden; padding: 6px 6px 6px 6px;" >
	<input type="hidden" id="LanguageID" name="LanguageID" value="${sessionScope.loginInfo.sessionCurrLangType}">
	<input type="hidden" id="userId" name="userId" value="${sessionScope.loginInfo.sessionUserId}">
	<input type="hidden" id="itemId" name="itemId" value="${getData.ItemID}">
	<input type="hidden" id="AuthorID" name="AuthorID" value="${getData.AuthorID}" />
	<input type="hidden" id="ProjectID" name="ProjectID" value="${getData.ProjectID}" />
	<input type="hidden" id="ChangeSetID" name="ChangeSetID" value="${getData.ChangeSetID}" />
	<input type="hidden" id="screenMode" name="screenMode" value="${screenMode}" />
	<input type="hidden" id="StatusCode" name="StatusCode" value="${StatusCode}" />
	<input type="hidden" id="StatusCode" name="CSRStatusCode" value="${CSRStatusCode}" />
	<input type="hidden" id="item" name="item" value="" />
	<input type="hidden" id="cngt" name="cngt" value="" />
	<input type="hidden" id="pjtId" name="pjtId" value="" />
	<input type="hidden" id="evaluationClassCode" name="evaluationClassCode" value="${evaluationClassCode}" />
	<input type="hidden" id="attrTypeCodeList" name="attrTypeCodeList" value="${attrTypeCodeList}" />
	<input type="hidden" id="dataTypeList" name="dataTypeList" value="${dataTypeList}" />
	<input type="hidden" id="evType" name="evType" value="${evType}" />
	<!-- 화면 타이틀 : view - 변경항목 상세 조회, edit - 변경항목 상세 내역 편집 -->
	<div class="cop_hdtitle">
		<h3 style="padding:0 6px">
			<img src="${root}${HTML_IMG_DIR}/icon_csr.png">&nbsp;&nbsp;
				${menu.LN00206}   
		</h3>
	</div>
	<div id="tblChangeSet">
		<table style="table-layout:fixed;" class="tbl_blue01 mgT10">
			<colgroup>
				<col width="10%"/>
				<col width="22%"/>
				<col width="10%"/>
				<col width="22%"/>
				<col width="10%"/>
				<col width="23%"/>			
			</colgroup>
			<tr>
				
				<!-- ID -->
				<th  class="viewtop alignL pdL10">${menu.LN00106}</th>
				<td  class="viewtop">${getData.Identifier}</td>
				<!-- 명칭 -->
				<th  class="viewtop alignL pdL10">${menu.LN00028}</th>
				<td  class="viewtop">${getData.ItemName}	</td>
				<!-- Version -->
				<th  class="viewtop alignL pdL10">${menu.LN00017}</th>
				<td  class="viewtop Last">	${getData.Version}	</td>	
				
	         <tr>				
				<!-- 담당자 -->				
				<th  class="alignL pdL10">${menu.LN00004}</th>
				<td>${getData.AuthorName}</td>
				<!-- 상태 -->
				<th  class="alignL pdL10">${menu.LN00027}</th>
				<td>
				${getData.StatusName}<c:if test="${getData.WFStatusName != ''  && getData.WFStatusName != Null}">(${getData.WFStatusName})</c:if>
				</td>
				<!-- 변경구분 -->
				<th class="alignL pdL10">${menu.LN00022}</th> 	
				<td class="last">${getData.ChangeType}</td>	
				
			</tr>				
			<tr>					
		     	<!-- 생성일/완료일 -->				
				<th  class=" alignL pdL10">${menu.LN00063}/${menu.LN00064}</th>
				<td>${getData.CreationTime} ${getData.CompletionDTYNMark} ${getData.CompletionDT}</td>					
				<!-- 승인일 -->				
				<th  class="alignL pdL10">${menu.LN00095}</th>
				<td >${getData.ApproveDate}</td>	
				<!-- 시행일 -->				
				<th  class="alignL pdL10">${menu.LN00296}</th>
				<td class="last">${getData.ValidFrom}</td>				
				
			</tr>
			
			<tr>
				<!-- 참조문서 -->
				<th class="alignL pdL10">${menu.LN00122}</th>
				<td colspan="5" style="height:53px;" class="alignL pdL5 last">
					<div style="height:53px;width:100%;overflow:auto;overflow-x:hidden;">
					<div id="tmp_file_items" name="tmp_file_items"></div>
					<div class="floatR pdR20" id="divFileImg">
					<c:if test="${attachFileList.size() > 0}">
						<span class="btn_pack medium icon mgB2"><span class="download"></span><input value="&nbsp;Save All&nbsp;&nbsp;" type="button" onclick="FileDownload('checkedFile', 'Y')"></span><br>
						<span class="btn_pack medium icon"><span class="download"></span><input value="Download" type="button" onclick="FileDownload('checkedFile', 'N')"></span><br>
					</c:if>
					</div>
					<c:forEach var="fileList" items="${attachFileList}" varStatus="status">
						<input type="checkbox" name="checkedFile" value="${fileList.Seq}" class="mgL2 mgR2">
						<span style="cursor:pointer;" onclick="fileNameClick('${fileList.Seq}');">${fileList.FileRealName}</span><br>
					</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<!-- 개요 -->
				<th  class="alignL pdL10">${menu.LN00290}</th>
					<td colspan="5" class="last alignL pdL5" >
						<textarea id="description" name="description" style="" readOnly>${getData.Description}</textarea>
					</td>
			</tr>
			 <tr>
	           <td colspan="6" class="alignR pdR20 last" bgcolor="#f9f9f9" >
	           &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="View current Item" onclick="goItemPopUpNoID()" type="submit"></span>
	         	  	<c:if test="${lastChangeSet == 'N' }">
	           		&nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="View version Item" onclick="fnOpenViewVersionItemInfo(${getData.ChangeSetID})" type="submit"></span>
		            </c:if>
		            <c:if test="${getData.WFInstanceID != '' && getData.wfStatus != '0' }">
	           		&nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="View Approval Document" onclick="goAprvDetail()" type="submit"></span>
		            </c:if>
		            	           		
		            <!-- CS 담당자 실행 아이콘  -->		          
			          <c:if test="${getData.pjtAuthorId == sessionScope.loginInfo.sessionUserId || getData.AuthorID == sessionScope.loginInfo.sessionUserId}" >
			      	   <c:if test="${StatusCode == 'MOD'}" >     
		                  &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="Edit" onclick="goEdit()" type="submit"></span>	
		               </c:if> 
		                <!-- Check In 후 결재진행 안한 경우 -->
		               <c:if test="${StatusCode == 'CMP' && ( getData.CheckInOption == '03' || getData.CheckInOption == '03A' || getData.CheckInOption == '03B' )}" >   
					   	  &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="Approval Request" onclick="goApprovalPop()" type="submit"></span>   
					   </c:if> 
					   <!-- Check In 후 결재 진행했으나 상신완료 안된  경우 -->
					   <c:if test="${StatusCode == 'APRV' && getData.WFInstanceID != '' && getData.wfStatus == '0'}" > 
			              &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="Approval Request" onclick="goApprovalPop()" type="submit"></span>			    			        	          	   	  
		        	 	</c:if>	
		            </c:if>   			    			        	          	   	  
		        	 	
		            
		            <!-- CSR 담당자  또는 변경 담당자   Rework -->
		            <c:if test="${getData.pjtAuthorId == sessionScope.loginInfo.sessionUserId || getData.AuthorID == sessionScope.loginInfo.sessionUserId}" >
		                 <!-- CS 결재 상신 전인 경우 -->
		                <c:if test="${StatusCode == 'CMP'}" >
			              &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="Rework" onclick="rework()" type="submit"></span> 
		        		</c:if>	
		        		<!-- CS 결재 후 결재 진행 중 또는 결재반려된 경우 -->
		        		<c:if test="${getData.WFInstanceID != ''  && getData.wfStatus != '0' && StatusCode == 'HOLD'}" >
		        		  &nbsp;<span class="btn_pack medium icon"><span class="confirm"></span><input value="Rework" onclick="rework()" type="submit"></span>
		        		</c:if> 
		        	</c:if>
			    </td>
      		</tr>
		</table>
		
		<c:if test="${revisionList.size() > 0}">	
   			<span class="cop_hdtitle">＊ ${menu.LN00205}</span>
     		<div style="width:100%;height: calc(100% - 460px);overflow:auto;">
			<table class="tbl_blue01" width="100%" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="11%">
					<col width="30%">
					<col width="11%">
					<col width="67%">
				</colgroup>
				<tr>
					<th class="last">${menu.LN00106}</th>
					<th class="last">${menu.LN00028}</th>
					<th class="last">${menu.LN00016}</th>
					<th class="last">${menu.LN00290}</th>
				</tr>	
				<c:forEach var="rList" items="${revisionList}" varStatus="status">
				<tr>
					<td class="alignC pdL5 last">
						${rList.Identifier}
					</td>
					<td class="alignC pdL5 last">
						${rList.ItemName}
					</td>
					<td class="alignC pdL5 last">
						${rList.ClassName}
					</td>
					<td class="alignC pdL5 last" style="cursor:pointer;" onClick="goItemPopUp('${rList.ItemID}')">
						${rList.Description}
					</td>
				</tr>												
				</c:forEach>	
			</table>
		</div>
		</c:if>
	</div>
</div>
</form>

<iframe name="saveFrame" id="saveFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
