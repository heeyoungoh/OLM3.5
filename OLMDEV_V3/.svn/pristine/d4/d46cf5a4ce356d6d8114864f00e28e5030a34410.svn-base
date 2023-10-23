<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script type="text/javascript">
	var chkReadOnly = true;	
</script>
<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00001" var="CM00001"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00040" var="WM00040"/>
<head>

<script type="text/javascript">
	
	$(document).ready(function(){		
		
	});
	
	function updateModel(){
		var blocked = "${Blocked}";
		var itemAthId = "${itemAthId}";
		var userId = "${sessionScope.loginInfo.sessionUserId}";
		var modelBlocked = "${modelInfo.ModelBlocked}";
	
		if(blocked != "2" && itemAthId == userId && modelBlocked == "0" || '${loginInfo.sessionMlvl}' == "SYS"){
			if(confirm("${CM00001}")){
				doBlockedChecked();
				doIsPublicChecked();
				var url  = "updateModel.do";
				ajaxSubmit(document.objectInfoFrm, url,"subFrame");
			}
		}else{			
			alert("${WM00040}"); return;
		}
		
	}
	
	function callbackUpdate(){
		self.close();
		opener.reloadList();
	}
	
	function doBlockedChecked(){		
		if ($("input:checkbox[id='Blocked']").is(":checked") == true){
			$("#Blocked").val(1);
		}else{
			$("#Blocked").val(0);
		}
	}
	
	function doIsPublicChecked(){		
		if ($("input:checkbox[id='IsPublic']").is(":checked") == true){
			$("#IsPublic").val(1);
		}else{
			$("#IsPublic").val(0);
		}
	}
	
	function fnpopupMaterMdlEdt() {
		var url = "popupMasterMdlEdt.do?"
			+"languageID=${sessionScope.loginInfo.sessionCurrLangType}"
			+"&s_itemID=${s_itemID}"
			+"&modelID=${modelInfo.RefModelID}"
			+"&scrnType=view";
			
		var w = 1200;
		var h = 900;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}
</script>
</head>
<body >	
	<div id="objectInfoDiv" class="hidden" style="padding:0 10px;">
		<table class="tbl_blue01 mgT10" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="8%">
				<col width="17%">
				<col width="8%">
				<col width="17%">
				<col width="8%">
				<col width="17%">
				<col width="8%">
				<col width="17%">
			</colgroup>		
			<tr>
				<th>${menu.LN00367}</th>
				<td>${modelInfo.ModelName}</td>
				<th>${menu.LN00032}</th>
				<td>${modelInfo.ModelTypeName}</td>
				<th>${menu.LN00033}</th>
				<td>${modelInfo.MTCategoryName}</td>			
				<th>${menu.LN00027}</th>
				<td class="last">${modelInfo.StatusName}</td>		
			</tr>		
			<tr>
				<th>Version</th>
				<td>${modelInfo.Version}</td>
				<th>${menu.LN00368}</th>
				<td <c:if test="${modelInfo.RefModelName ne 'N/A'}"> style="text-decoration: underline; cursor:pointer;" onClick="fnpopupMaterMdlEdt()"</c:if>>
					${modelInfo.RefModelName}
				</td>				
				<th>Valid From</th>
				<td>${modelInfo.ValidFrom}</td>
				<th>Valid To</th>
				<td class="last">${modelInfo.ValidTo}</td>
			</tr>
				<tr>
				<th>${menu.LN00105}</th>
				<td>${modelInfo.LastUserNm}</td>
				<th>${menu.LN00070}</th>
				<td>${modelInfo.LastUpdated}</td>
				<th>${menu.LN00200}</th>
				<td>${modelInfo.CreatorNm}</td>
				<th>${menu.LN00013}</th>
				<td class="last">${modelInfo.CreationTime}</td>
			</tr>			
			<tr>
				<th>${menu.LN00035}</th>
				<td colspan="7" class="last">
					<textarea id='description' name='description' style="width:100%;height:130px;" readOnly >${modelInfo.Description}</textarea>
				</td>
			</tr>
		</table>
		<div class="alignBTN" id="divUpdateModel" >			
		<c:if test="${modelInfo.ModelBlocked eq '0' && ( modelInfo.IsPublic eq '1' || myItem eq 'Y') }">
		<span class="btn_pack medium icon"><span class="edit"></span><input value="Edit" onclick="fnGoModelInfo('edit')" type="submit"></span>
		</c:if>	
		</div>	
	</div>
	<iframe name="subFrame" id="subFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</body>
</html>