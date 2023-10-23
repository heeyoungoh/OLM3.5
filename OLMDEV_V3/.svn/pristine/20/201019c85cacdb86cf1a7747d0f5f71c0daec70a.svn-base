<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<c:url value="/" var="root"/>
<script src="${root}cmm/js/tinymce_v5/tinymce.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var chkReadOnly = true;
</script>
<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>
<script>

	function fnOpenTeamInfoMain(teamID){
		var w = "1200";
		var h = "800";
		var url = "orgMainInfo.do?id="+teamID;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}

	var layerWindow = $('.item_layer_photo');
	$('#authorInfo').click(function(){			
		var url = "viewMbrInfo.do?memberID=${prcList.AuthorID}";		
		window.open(url,'window','width=1200, height=700, left=400, top=100,scrollbar=yes,resizble=0');
	});
	
	function clickItemEvent(itemID) {
		var url = "popupMasterItem.do?"
				+"languageID=${sessionScope.loginInfo.sessionCurrLangType}"
 				+"&id="+itemID
				+"&scrnType=pop";
		var w = 1200;
		var h = 900;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}
	
	function fileNameClick(avg1, avg2, avg3, avg4, avg5){
		
		if(avg3 == "VIEWER") {
			var url = "openViewerPop.do?seq="+avg4;
			var w = screen.width;
			var h = screen.height;
			
			if(avg5 != "") { 
				url = url + "&isNew=N";
			}
			else {
				url = url + "&isNew=Y";
			}
			window.open(url, "openViewerPop", "width="+w+", height="+h+",top=0,left=0,resizable=yes");
			//window.open(url,1316,h); 
		}
		else {	
			var url  = "fileDownload.do?seq="+avg4;
			ajaxSubmitNoAdd(document.frontFrm, url,"blankFrame");
		}
						
	}
</script>
<style>
	.borderR td:not(:last-child), .borderR th:not(:last-child){
		border-right: 1px solid #dfdfdf;
	}
</style>

<form name="frontFrm" id="frontFrm" action="#" method="post" onsubmit="return false;" style="height: 100%;"> 
	<div id="htmlReport" style="width:100%;overflow-y:auto;overflow-x:hidden;">		
	
<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview">
	<colgroup> 
		<col width="11%">
		<col width="14%">
		<col width="11%">
		<col width="14%">
		<col width="11%">
		<col width="14%">
		<col width="11%">
		<col width="14%">
	</colgroup>
	<tr>
		<!-- 항목계층 -->
		<th class="viewtop">${menu.LN00016}</th>
		<td class="viewtop">${prcList.ClassName}</td>
		<!-- 상태 -->
		<th class="viewtop">${menu.LN00027}</th>
		<td class="viewtop">${prcList.StatusName}</td>				
		<!-- 수정일 -->
		<th class="viewtop" >${menu.LN00070}</th>
		<td class="viewtop">${prcList.LastUpdated}</td>
		<!-- 생성일 -->
		<th class="viewtop" >${menu.LN00013}</th>
		<td class="tdLast viewtop">${prcList.CreateDT}</td>
	</tr>
	<tr>				
		<!-- 관리조직 -->
		<th>${menu.LN00018}</th>
		<td style="cursor:pointer;color: #0054FF;text-decoration: underline;"  OnClick="fnOpenTeamInfoMain(${prcList.OwnerTeamID})" >${prcList.OwnerTeamName}</td>
		<input type="hidden" id="orderTeamName" name="orderTeamName"  value="${prcList.OwnerTeamName}"/>
		<!-- 담당자 -->	
		<th>${menu.LN00004}</th>
		<td id="authorInfo" style="cursor:pointer;_cursor:hand;color: #0054FF;text-decoration: underline;">${prcList.Name} </td>
		 <!-- 법인 -->	
		<th>${menu.LN00352}</th>
		<td>${prcList.TeamName}</td>
		<!-- 프로젝트 -->
		<th>${menu.LN00131}</th>
		<td>${prcList.ProjectName}</td>
	</tr>
	<c:if test="${itemFileOption ne 'VIEWER'}">
	<tr>
		<!-- 첨부 및 관련 문서 --> 
		<th style="height:53px;">${menu.LN00019}</th>
		<td style="height:53px;" class="tdLast alignL pdL5" colspan="7">
			<div style="height:53px;width:100%;overflow:auto;overflow-x:hidden;">
				<div class="floatR pdR20">
				<c:if test="${attachFileList.size() > 0}">
					<span class="btn_pack medium icon mgB2"><span class="download"></span><input value="&nbsp;Save All&nbsp;&nbsp;" type="button" onclick="FileDownload('checkedFile', 'Y')"></span><br>
					<span class="btn_pack medium icon"><span class="download"></span><input value="Download" type="button" onclick="FileDownload('checkedFile')"></span><br>
				</c:if>
				</div>
				<c:forEach var="fileList" items="${attachFileList}" varStatus="status">
					<input type="checkbox" name="checkedFile" value="${fileList.FileName}/${fileList.FileRealName}//${fileList.Seq}" class="mgL2 mgR2">
					<span style="cursor:pointer;" onclick="fileNameClick('${fileList.FileName}','${fileList.FileRealName}','OLM','${fileList.Seq}','${fileList.ExtFileURL}');">${fileList.FileRealName}</span><br>
				</c:forEach>					
			</div>
		</td> 
	</tr>
	</c:if>			
	<c:if test="${itemFileOption eq 'VIEWER'}">
	<tr>
		<!-- 첨부 및 관련 문서 --> 
		<th style="height:53px;">${menu.LN00019}</th>
		<td style="height:53px;" class="tdLast alignL pdL5" colspan="7">
			<div style="height:53px;width:100%;overflow:auto;overflow-x:hidden;">
			<div class="floatR pdR20">
			</div>
			<c:forEach var="fileList" items="${attachFileList}" varStatus="status">
				<span style="cursor:pointer;" onclick="fileNameClick('${fileList.FileName}','${fileList.FileRealName}','${itemFileOption}','${fileList.Seq}','${fileList.ExtFileURL}');">${fileList.FileRealName}</span>
					<img src="${root}${HTML_IMG_DIR}/btn_view_en.png" onclick="fileNameClick('${fileList.FileName}','${fileList.FileRealName}','${itemFileOption}','${fileList.Seq}','${fileList.ExtFileURL}');" style="cursor:pointer;">
				<br>
			</c:forEach>
			<c:forEach var="relatedfileList" items="${pertinentFileList}" varStatus="status">
				<span style="cursor:pointer;" onclick="fileNameClick('${relatedfileList.FileName}','${relatedfileList.FileRealName}','${itemFileOption}','${relatedfileList.Seq}','${relatedfileList.ExtFileURL}');">${relatedfileList.FileRealName}</span>
				<img src="${root}${HTML_IMG_DIR}/btn_view_en.png" onclick="fileNameClick('${relatedfileList.FileName}','${relatedfileList.FileRealName}','${itemFileOption}','${relatedfileList.Seq}','${relatedfileList.ExtFileURL}');" style="cursor:pointer;">
				<br>
			</c:forEach>
			</div>
		</td> 
	</tr>
	</c:if>
</table>

<table style="table-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview mgT20">
	<colgroup> 
		<col width="11%">
		<col width="39%">
		<col width="11%">
		<col width="39%">
	</colgroup>
	<tr>
		<th>업태(Division)</th>
		<td class="alignL pdL5">
		<c:forEach var="list" items="${dimResultList}">
			<c:if test="${list.dimTypeName eq 'Division' }">${list.dimValueNames}</c:if>
		</c:forEach>
		</td>
		<th>Center</th>
		<td class="alignL pdL5">
		<c:forEach var="list" items="${dimResultList}">
			<c:if test="${list.dimTypeName eq 'Center' }">${list.dimValueNames}</c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<th>Category</th>
		<td class="alignL pdL5">
		<c:forEach var="list" items="${dimResultList}">
			<c:if test="${list.dimTypeName eq 'Category' }">${list.dimValueNames}</c:if>
		</c:forEach>
		</td>
		<th>${attrNameMap.AT00007}</th>
		<td class="alignL pdL5"><textarea style="width:100%;height:20px;" readonly="readonly">${attrMap.AT00007}</textarea></td>
	</tr>
	<tr>
		<th>${attrNameMap.AT00003}</th>
		<td class="alignL pdL5" colspan="3">
			<textarea class="tinymceText" style="width:100%;height:150px;" readonly="readonly">
				 <div class="mceNonEditable">${attrMap.AT00003}</div>
			 </textarea>
		</td>
	</tr>
	<tr>
		<th>${attrNameMap.AT00015}</th>
		<td class="alignL pdL5"><textarea style="width:100%;height:70px;" readonly="readonly">${attrMap.AT00015}</textarea></td>
		<th>${attrNameMap.AT00016}</textarea></th>
		<td class="alignL pdL5"><textarea style="width:100%;height:70px;" readonly="readonly">${attrMap.AT00016}</textarea></td>
	</tr>
	<tr>
		<th>${attrNameMap.AT00042}</th>
		<td class="alignL pdL5" colspan="3">
			<textarea style="width:100%;height:150px;" readonly="readonly">${attrMap.AT00042}</textarea>
		</td>
	</tr>
</table>

<table style="able-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview borderR mgT20">
	<colgroup> 
		<col width="25%">
		<col width="25%">
		<col width="25%">
		<col width="25%">
	</colgroup>
	<tr>
		<th>업태구분</th>
		<th>프로세스 오너</th>
		<th>주관부서</th>
		<th>관련부서</th>
	</tr>
	<tr>
		<td>이마트</td>
		<td>${attrMap.ZAT0001}</td>
		<td>${attrMap.ZAT0002}</td>
		<td>${attrMap.ZAT0003}</td>
	</tr>
	<tr>
		<td>노브랜드</td>
		<td>${attrMap.ZAT0004}</td>
		<td>${attrMap.ZAT0005}</td>
		<td>${attrMap.ZAT0006}</td>
	</tr>
	<tr>
		<td>트레이더스</td>
		<td>${attrMap.ZAT0007}</td>
		<td>${attrMap.ZAT0008}</td>
		<td>${attrMap.ZAT0009}</td>
	</tr>
	<tr>
		<td>SSG푸드마켓</td>
		<td>${attrMap.ZAT0010}</td>
		<td>${attrMap.ZAT0011}</td>
		<td>${attrMap.ZAT0012}</td>
	</tr>
	<tr>
		<td>전문점</td>
		<td>${attrMap.ZAT0013}</td>
		<td>${attrMap.ZAT0014}</td>
		<td>${attrMap.ZAT0015}</td>
	</tr>
</table>

<table style="able-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview borderR mgT20">
	<colgroup> 
		<col width="25%">
		<col width="25%">
		<col width="25%">
		<col width="25%">
	</colgroup>
	<tr>
		<th>센터구분</th>
		<th>프로세스 오너</th>
		<th>주관부서</th>
		<th>관련부서</th>
	</tr>
	<tr>
		<td>TC</td>
		<td>${attrMap.ZAT0016}</td>
		<td>${attrMap.ZAT0017}</td>
		<td>${attrMap.ZAT0018}</td>
	</tr>
	<tr>
		<td>DC</td>
		<td>${attrMap.ZAT0019}</td>
		<td>${attrMap.ZAT0020}</td>
		<td>${attrMap.ZAT0021}</td>
	</tr>
	<tr>
		<td>RDC</td>
		<td>${attrMap.ZAT0022}</td>
		<td>${attrMap.ZAT0023}</td>
		<td>${attrMap.ZAT0024}</td>
	</tr>
	<tr>
		<td>전문점</td>
		<td>${attrMap.ZAT0025}</td>
		<td>${attrMap.ZAT0026}</td>
		<td>${attrMap.ZAT0027}</td>
	</tr>
	<tr>
		<td>PC</td>
		<td>${attrMap.ZAT0028}</td>
		<td>${attrMap.ZAT0029}</td>
		<td>${attrMap.ZAT0030}</td>
	</tr>
	<tr>
		<td>APC</td>
		<td>${attrMap.ZAT0031}</td>
		<td>${attrMap.ZAT0032}</td>
		<td>${attrMap.ZAT0033}</td>
	</tr>
</table>

<table style="able-layout:fixed;" width="100%" cellpadding="0" cellspacing="0" border="0" class="tbl_preview borderR mgT20">
	<colgroup> 
		<col width="12%">
		<col width="13%">
		<col width="42%">
		<col width="10%">
		<col width="10%">
		<col width="13%">
	</colgroup>
	<tr>
		<th>Activity ID</th>
		<th>Activity 명</th>
		<th>Guideline</th>
		<th>Role</th>
		<th>System</th>
		<th>T-Code</th>
	</tr>
	<c:forEach var="list" items="${activityList}">
		<tr>
			<td><span  style="color: blue; cursor:pointer; border-bottom: 1px solid;" onclick="clickItemEvent(${list.ItemID})">${list.Identifier }</span></td>
			<td class="alignL pdL10">${list.ItemName }</td>
			<td class="alignL pdL10">${list.AT00003}</td>
			<td class="alignL pdL10">${list.AT00017 }</td>
			<td class="alignL pdL10">
				<c:if test="${list.conList.size() > 0 }">
					<c:forEach var="list2" items="${list.conList}" varStatus="status">
						<c:if test="${list2.ClassCode eq 'CNL0104' }">
							<c:if test="${status.index ne 0}"><br></c:if>${list2.itemName}						
						</c:if>
					</c:forEach>
				</c:if>
			</td>
			<td class="alignL pdL5">${list.AT00014 }</td>
		</tr>
	</c:forEach>
</table>
	</div>
</form>