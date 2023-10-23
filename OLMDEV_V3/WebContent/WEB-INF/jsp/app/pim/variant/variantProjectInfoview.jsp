<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00093" var="WM00093_1" arguments="${menu.LN00028}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00093" var="WM00093_2" arguments="${menu.LN00004}"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>

<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00131" var="WM00131"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00002" var="CM00002"/>

<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/cmm/fileAttachHelper.jsp"%>

<c:if test="${isNew == 'R'}">
	<script type="text/javascript">
		var chkReadOnly = true;
	</script>
</c:if>

<!--script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script -->

<script type="text/javascript">
var p_gridArea;				//그리드 전역변수
$(document).ready(function(){
	$("input.datePicker").each(generateDatePicker);
	
	fnSelect('Status','&Category=pjtsts','getDicWord','${getMap.Status}');
	fnSelect('TMPL','&Category=TMPL','getDicWord','${getMap.TemplCode}');
	fnSelect('refPgID', 'languageID=${sessionScope.loginInfo.sessionCurrLangType}', 'getRefPgID', '${getMap.RefPGID}');
	
	$("#htmlReport").attr("style","height:"+(setWindowHeight() - 110)+"px;");
	// 화면 크기 조정
	window.onresize = function() {
		$("#htmlReport").attr("style","height:"+(setWindowHeight() - 110)+"px;");
	};
});

//===============================================================================
	
function savePrj(){
	// 명칭 & 담당자 필수 Check
	if ($('#ProjectName').val() == "") {
		//alert("생성 할 프로젝트 명칭을 입력 해 주세요.");
		alert("${WM00093_1}");
		return;
	}
	
	if ($('#AuthorID').val() == "") {
		//alert("생성 할 프로젝트 담당자를 입력 해 주세요.");
		alert("${WM00093_2}");
		return;
	}
	
	//if(confirm("저장하시겠습니까?")){
	if(confirm("${CM00001}")){
		var url = "savePrjInfo.do"; // 요청이 날라가는 주소
		ajaxSubmit(document.projectInfoFrm, url);
	}
}
function searchPopup(url){
	window.open(url,'window','width=300, height=300, left=300, top=300,scrollbar=yes,resizble=0');
}

function setSearchName(memberID,memberName){
	$('#AuthorID').val(memberID);
	$('#AuthorName').val(memberName);
}
function setSearchTeam(teamID,teamName){
	$('#ownerTeamCode').val(teamID);
	$('#TeamName').val(teamName);
}

function projectGroupList(){
	var url = "projectGroupList.do";
	var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
	var target = "help_content";
	ajaxPage(url, data, target);
}

function setWindowHeight(){var size = window.innerHeight;var height = 0;if( size == null || size == undefined){height = document.body.clientHeight;}else{height=window.innerHeight;}return height;}

// [Member] click
function searchMember(url){
	var data = "";
	var isNew = "${isNew}";
	var target = "htmlReport";
	if ("N" == isNew) {
		data = "projectID=${getMap.ProjectID}&listEditable=N&isNew=${isNew}";
	}
	ajaxPage(url, data, target);
}

function searchTaskType(url){
	var data = "";
	var isNew = "${isNew}";
	var target = "htmlReport";
	if ("N" == isNew) {
		data = "projectID=${getMap.ProjectID}&listEditable=N&isNew=${isNew}";
	}
	ajaxPage(url, data, target);
}


</script>
<div id="htmlReport" style="width:100%; overflow:auto; overflow-x:hidden; padding:0;">
<form name="projectInfoFrm" id="projectInfoFrm" method="post" action="savePrjInfo.do" onsubmit="return false;">
	<input type="hidden" id="s_itemID" name="s_itemID"  value="${s_itemID}" />
	<input type="hidden" id="UserID" name="UserID" value="${sessionScope.loginInfo.sessionUserId}" />
	
	<c:if test="${isNew == 'N'}"> 
	<input type="hidden" id="AuthorID" name="AuthorID" value="${getMap.AuthorID}" />
	<input type="hidden" id="ownerTeamCode" name="ownerTeamCode" value="${getMap.OwnerTeamID}" />
	</c:if>
	<c:if test="${isNew == 'Y'}"> 
	<input type="hidden" id="AuthorID" name="AuthorID" value="${sessionScope.loginInfo.sessionUserId}" />
	<input type="hidden" id="ownerTeamCode" name="ownerTeamCode" value="${sessionScope.loginInfo.sessionTeamId}" />
	</c:if>
	
	<input type="hidden" id="languageID" name="languageID" value="${sessionScope.loginInfo.sessionCurrLangType}" />
	<input type="hidden" id="isNew" name="isNew" value="${isNew}" />
	
	<div id="objectInfoDiv" class="hidden floatC" style="width:100%;height:400px;" >
	   <div class="msg mgT5" style="width:100%;">
      	 <img src="${root}${HTML_IMG_DIR}/bullet_blue.png" id="subTitle_baisic">&nbsp;${menu.LN00277}
    	</div>
		<table class="tbl_blue01 mgT10" style="table-layout:fixed;" width="98%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="7%">
				<col width="13%">
				<col width="7%">
				<col width="13%">
				<col width="7%">
				<col width="13%">
				<col width="7%">
				<col width="13%">
				<col width="7%">
				<col>
			</colgroup>
			<tr>
				<!-- 코드 -->
				<th class="alignL pdL10"> ${menu.LN00015}</th>
				 <td class="alignL pdL10">  
					<c:if test="${isNew == 'N' || isNew == 'Y'}">  
						<input type="text" class="text" id="ProjectCode" name="ProjectCode" value="${getMap.ProjectCode}" maxlength="20" STYLE='IME-MODE:DISABLED'/>
					</c:if>
					<c:if test="${isNew == 'R'}"> 
						<p style="height:22px;">${getMap.ProjectCode}</p> 
					</c:if>
				</td>
				
				<!-- 명칭  -->
				<th class=" alignL pdL10"> ${menu.LN00028}</th>
				<td class=" alignL pdL10">  
					<c:if test="${isNew == 'N' || isNew == 'Y'}">  
						<input type="text" class="text" id="ProjectName" name="ProjectName" value="${getMap.ProjectName}"/>
					</c:if>
					<c:if test="${isNew == 'R'}"> 
						<p style="height:22px;">${getMap.ProjectName}</p> 
					</c:if>
				</td>
				<!-- 담당부서 -->
				<th class=" alignL pdL10"> ${menu.LN00018}</th>
				 <td class=" alignL pdL10">  
					<c:if test="${isNew == 'N'}">  
						<input type="text" class="text" id="TeamName" name="TeamName" readonly="readonly" onclick="searchPopup('searchTeamPop.do')" value="${getMap.TeamName}" />
					</c:if>
					<c:if test="${isNew == 'Y'}">  
						<input type="text" class="text" id="TeamName" name="TeamName" readonly="readonly" onclick="searchPopup('searchTeamPop.do')" value="${sessionScope.loginInfo.sessionTeamName}" />
					</c:if>
					<c:if test="${isNew == 'R'}"> 
						<p style="height:22px;">${getMap.TeamName}</p> 
					</c:if>
				</td>
				<!-- 담당자 -->				
				<th class=" alignL pdL10"> ${menu.LN00004}</th>
				 <td class=" alignL pdL10 last">
					<c:if test="${isNew == 'N'}"> 
						<input type="text" class="text"  id="AuthorName" name="AuthorName" readonly="readonly" onclick="searchPopup('searchNamePop.do')" value="${getMap.AuthorName}"/>
					</c:if>
					<c:if test="${isNew == 'Y'}"> 
						<input type="text" class="text"  id="AuthorName" name="AuthorName" readonly="readonly" onclick="searchPopup('searchNamePop.do')" value="${sessionScope.loginInfo.sessionUserNm}"/>
					</c:if>
					<c:if test="${isNew == 'R'}"> 
						<p style="height:22px;">${getMap.AuthorName}</p> 
					</c:if>
				</td>
			</tr>
			<tr>
			<!-- 실행기간 : 시작일 -->
				<th class=" alignL pdL10"> ${menu.LN00063}</th>
				<c:if test="${isNew == 'N' || isNew == 'Y'}">
					<td class="alignL pdL10">
						<input type="text" id="StartDate" name="StartDate" value="${getMap.StartDate}" class="text datePicker" style="width: 70px;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
					</td>
				</c:if>
				<c:if test="${isNew == 'R'}">
					<td class="alignL pdL10">
						<p style="height:22px;">${getMap.StartDate}</p> 
					</td>
				</c:if>
				
				<!-- 싱행기간 : 종료 예정일 -->
				<th class=" alignL pdL10"> ${menu.LN00062}</th>
				<c:if test="${isNew == 'N' || isNew == 'Y'}">
					<td  class="alignL pdL10 " >
						<input type="text" id="DueDate" name="DueDate" value="${getMap.DueDate}" class="text datePicker" style="width: 70px;" onchange="this.value = makeDateType(this.value);"	maxlength="10">
					</td>
				</c:if>
				<c:if test="${isNew == 'R'}">
					<td class="alignL pdL10 ">
						<p style="height:22px;">${getMap.DueDate}</p>
					</td>
				</c:if>
				
				<!-- 진행상태 -->
				<th class=" alignL pdL10"> ${menu.LN00065}</th>
				<td class="alignL pdL10 last" colspan="3">
					<c:if test="${isNew == 'N' || isNew == 'Y'}">
						<select id="Status" name="Status" style="width:40%"></select>
					</c:if>
					<c:if test="${isNew == 'R'}"> 
						<select id="Status" name="Status" style="width:40%" disabled="disabled"></select>
					</c:if>
				</td>	
				
			<tr>
				<!-- 개요 -->
				<th class=" alignL pdL10"> ${menu.LN00035}</th>
				<td colspan="7" class="alignL pdL10 last">
					<c:if test="${isNew == 'N' || isNew == 'Y'}">
					<textarea class="textgrow" id="Description" name="Description" style="width:100%;height:200px;">${getMap.Description}</textarea>
					</c:if>
					<c:if test="${isNew == 'R'}">
						<textarea class="textgrow" id="Description" name="Description" style="width:100%;height:200px;" readonly="readonly">${getMap.Description}</textarea>
					</c:if>
				</td>
			</tr>
			<!-- 전체 버튼 제어 -->
			
			<tr>
                <td colspan="8" class="alignR pdR20 last" bgcolor="#f9f9f9">
                  <c:if test="${isPjtMgt == 'Z'}">
               		<!-- [Member] 버튼 제어 -->
               		<c:if test="${isNew == 'N' && getMap.Status != 'CLS'}"> 
	              		<c:if test="${sessionScope.loginInfo.sessionUserId == getMap.AuthorID || sessionScope.loginInfo.sessionAuthLev == 1}">
	              			<span class="btn_pack medium icon"><span class="add"></span><input value="TaskType" onclick="searchTaskType('selectPjtTaskType.do');" type="submit"></span> 
	                		<span class="btn_pack medium icon"><span class="add"></span><input value="Member" onclick="searchMember('selectPjtMember.do');" type="submit"></span>    
	               		</c:if>
               		</c:if>
	             </c:if>
                </td>
            </tr>
            
		</table>
	</div>	
</form>
<iframe id="saveFrame" name="saveFrame" style="width:0px;height:0px;display:none;"></iframe>
</div>
