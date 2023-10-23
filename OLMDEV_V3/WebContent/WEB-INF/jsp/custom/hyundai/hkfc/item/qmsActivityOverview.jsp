﻿<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
 
<script type="text/javascript">
	var chkReadOnly = true;	
</script>
<script src="<c:url value='/cmm/js/tinymce_v5/tinymce.min.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="${root}cmm/js/xbolt/tinyEditorHelper.js"></script>
<script type="text/javascript">	
	function fnItemPopUp(itemId){
		var url = "popupMasterItem.do?languageID=${sessionScope.loginInfo.sessionCurrLangType}&id="+itemId+"&scrnType=pop&screenMode=pop";
		var w = 1200;
		var h = 900;
		itmInfoPopup(url,w,h,itemId);
	}
	
	function fnOpenTeamInfoMain(teamID){
		var w = "1200";
		var h = "800";
		var url = "orgMainInfo.do?id="+teamID;
		window.open(url, "", "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");
	}
	
	function fnOpenUserInfo(id){		
		var url = "viewMbrInfo.do?memberID="+id;		
		window.open(url,'window','width=1200, height=700, left=400, top=100,scrollbar=yes,resizble=0');
	}
	

	function fileNameClick(avg1, avg2, avg3, avg4, avg5){
		var seq = new Array();
		seq[0] = avg4;
		var url  = "fileDownload.do?seq="+seq;
		ajaxSubmitNoAdd(document.frontFrm, url,"saveFrame");
	}
</script>
<style>
.btn_pack * {
	cursor: default;
}
</style>
<!-- BIGIN :: -->
<form name="frontFrm" id="frontFrm" action="#" method="post" onsubmit="return false;" style="height: 100%;">
<div id="processItemInfo" style="height:100%;overflow-y:auto;">
<input type="hidden" id="currIdx" value="">
<input type="hidden" id="openItemList" value="">

<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}">	
<input type="hidden" id="viewScrn" name="viewScrn" value="${viewScrn}">	
<input type="hidden" id="editScrn" name="editScrn" value="${editScrn}">	
<input type="hidden" id="defAccMode" name="defAccMode" value="${defAccMode}">	
<input type="hidden" id="scrnMode" name="scrnMode" value="${scrnMode}">	
<input type="hidden" id="option" name="option" value="${option}">	
<input type="hidden" id="accMode" name="accMode" value="${accMode}">	

	<div style="width:100%;overflow-y:auto;overflow-x:hidden;">
		<div id="itemDiv">
			<!-- BIGIN :: 기본정보 -->
			<div id="process" class="mgB30 mgT10">
				<table class="tbl_preview mgB30">
					<colgroup>
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="10%">	
						<col width="15%">
					</colgroup>
					
					<tr>
						<th>${menu.LN00016 }</th> <!-- 클래스 -->
						<td class="alignL pdL10">${prcList.ClassName}</td>
						<th>${menu.LN00027 }</th> <!-- 상태  -->
						<td class="alignL pdL10">${prcList.StatusName}</td>
						<th>${menu.LN00070}</th> <!-- 수정일 -->
						<td class="alignL pdL10">${prcList.LastUpdated}</td>
						<th>${menu.LN00013}</th> <!-- 생성일 -->
						<td class="alignL pdL10">${prcList.CreateDT}</td>
					</tr>
					
					<tr>
						<th>${menu.LN00018}</th> <!-- 관리조직 -->
						<td class="alignL pdL10" style="cursor:pointer;color: #0054FF;text-decoration: underline;"  OnClick="fnOpenTeamInfoMain(
							${teamMap.OwnerTeamID}
						)" >${teamMap.OwnerTeamName}</td>
						<th>${menu.LN00004}</th> <!-- 담당자 -->
		 				<td class="alignL pdL10" style="cursor:pointer;_cursor:hand;color: #0054FF;text-decoration: underline;" onclick="fnOpenUserInfo(${teamMap.AuthorID})">${teamMap.Name} </td>
						<th>${menu.LN00352}</th> <!-- 관리법인 -->
						<td class="alignL pdL10">${teamMap.TeamName}</td>
						<th>${menu.LN00131}</th> <!-- 프로젝트 -->
						<td class="alignL pdL10">${teamMap.ProjectName}</td>
					</tr>
					<tr>
						<td colspan="8" class="hr1">&nbsp;</td>
					</tr>
					<!--  //end 기본정보 -->
					
					<tr>
						<th>${attrNameMap.AT00003}</th> <!-- 개요 -->
						<td class="alignL pdL10" colspan="7">
							<textarea class="tinymceText" id="AT00003" name="AT00003" style="width:100%;height:300px;">
								<div class="mceNonEditable">${attrMap.AT00003}</div>
							</textarea>
						</td>
					</tr>
					<tr>
						<th>${attrNameMap.AT00062 }</th> <!-- 수행업무 세부사항 -->
						<td class="alignL pdL10" colspan="7">
							<textarea class="tinymceText" id="AT00062" name="AT00062" style="width:100%;height:300px;">
								<div class="mceNonEditable">${attrMap.AT00062}</div>
							</textarea>
						</td>
					</tr>
					<tr>
						<th>${attrNameMap.Z1301 }</th> <!-- 시점 -->
						<td class="alignL pdL10" colspan="7">${attrMap.Z1301}</td>
					</tr>		
					<tr>
						<th>${attrNameMap.AT00015 }</th> <!-- 입력물 -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00015}</td>
						<th>${attrNameMap.AT00016 }</th> <!-- 출력물 -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00016}</td>
					</tr>
					<tr>
						<th>${attrNameMap.AT00017 }</th> <!-- 업무 주관부서 (자동입력) -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00017}</td>
						<th>${attrNameMap.AT00033 }</th> <!-- AT00033 -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00033}</td>
					</tr>
					<tr>
						<th>${attrNameMap.AT00083 }</th> <!-- 단계 -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00083}</td>
						<th>${attrNameMap.AT00084 }</th> <!-- AT00084 -->
						<td class="alignL pdL10" colspan="3">${attrMap.AT00084}</td>
					</tr>
					<!-- BIGIN :: Dimension -->
					<c:if test="${dimResultList.size() > 0}">
						<c:forEach var="dimList" items="${dimResultList}" varStatus="status">
							<tr>
								<th>${dimList.dimTypeName}</th>
								<td class="alignL pdL10" colspan="7">
									${dimList.dimValueNames}
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<!-- END :: Dimension -->
				</table>
			</div>
			
			<!-- 첨부 및 관련 문서 --> 
			<div id="file" class="mgB30">
				<p class="cont_title">${menu.LN00019 }</p>
				<table class="tbl_preview">
					<colgroup>
						<col width="5%">
						<col width="15%">
						<col width="60%">
						<col width="10%">
						<col width="10%">
					</colgroup>	
					<tr>
						<th>No</th>
						<th>${menu.LN00091}</th>
						<th>${menu.LN00101}</th>
						<th>${menu.LN00060}</th>
						<th>${menu.LN00078}</th>
					</tr>
					<c:set value="1" var="no" />
					<c:forEach var="fileList" items="${attachFileList}" varStatus="status">
						<tr>
							<td>${no }</td>
							<td>${fileList.FltpName}</td>
							<td class="alignL pdL10 flex align-center">
									<span class="btn_pack small icon mgR30">
									<c:set var="FileFormat" value="${fileList.FileFormat}" />
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
									<span style="cursor:pointer;margin-left:10px;" onclick="fileNameClick('${fileList.FileName}','${fileList.FileRealName}','','${fileList.Seq}','${fileList.ExtFileURL}');">${fileList.FileRealName}</span>
									(<span id="fileSize">${fileList.FileSize}</span>)
							</td> 
							<td>${fileList.WriteUserNM}</td>
							<td>${fileList.LastUpdated}</td>
						</tr>
					<c:set var="no" value="${no+1}"/>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>
</form>
<iframe id="saveFrame" name="saveFrame" style="display:none" frameborder="0"></iframe>
</head>
