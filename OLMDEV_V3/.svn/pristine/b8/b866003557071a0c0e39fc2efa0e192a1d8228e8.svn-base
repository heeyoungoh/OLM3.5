<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>

<!-- 화면 표시 메세지 취득  -->
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00038" var="CM00038"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00039" var="CM00039"/> 
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.CM00004" var="CM00004"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00023" var="WM00023"/>
<spring:message code="${sessionScope.loginInfo.sessionCurrLangCode}.WM00042" var="WM00042"/>
 
<script type="text/javascript">
	var au_gridArea;	//그리드 전역변수
	var groupID = "${userGrMgtList[0].GroupID}";
	
	$(document).ready(function() {	
		au_gridInit();
		doSearchList();
		
		$("#userGroup").change(function(){
			fnChangeUserGroup($(this).val());
		})
	});	
	//===============================================================================
	// BEGIN ::: GRID
	//그리드 초기화
	function au_gridInit(){		
		var d = setGridAUData();
		au_gridArea = fnNewInitGrid("grdAUArea", d);
		au_gridArea.setImagePath("${root}${HTML_IMG_DIR}/");//path to images required by grid
		au_gridArea.setIconPath("${root}cmm/common/images/");
		fnSetColType(au_gridArea, 1, "ch");
		fnSetColType(au_gridArea, 8, "img");
		au_gridArea.setColumnHidden(2, true);	
				
		au_gridArea.enablePaging(true,100,10,"pagingArea",true,"recInfoArea");
		au_gridArea.setPagingSkin("bricks");
		au_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});		
		
	}	
	function setGridAUData(){
		var getCode = "TMPL001";
		var result = new Object();
		result.title = "${title}";
		result.key = "user_SQL.allocateUsers";
		result.header = "${menu.LN00024},#master_checkbox,MemberID,ID,Name,${menu.LN00018},${menu.LN00014},${menu.LN00042},,Default Template";//10
		result.cols = "CHK|MemberID|LoginID|UserName|TeamName|CompanyName|AuthorityName|SuperiorTypeImg|DefaultTemplate";
		result.widths = "30,50,100,100,120,80,90,90,90,150";
		result.sorting = "int,int,str,str,str,str,str,str,str,str";
		result.aligns = "center,center,center,left,center,left,left,left,center,center";
		result.data = "s_itemID=" + groupID
					+ "&languageID=${sessionScope.loginInfo.sessionCurrLangType}";
		return result;
	}
	
	function doSearchList(){
		var d = setGridAUData();
		fnLoadDhtmlxGridJson(au_gridArea, d.key, d.cols, d.data);
	}
			
	function fnSearchUser(){
		var data = "groupID=${s_itemID}"; 
		var url = "searchUser.do";
		var target = "allocUserDiv";
		ajaxPage(url, data, target);
	}
	
	function fnCallBack(){ 
		au_gridInit();
		doSearchList();
	}
	
	function fnChangeUserGroup(id){
		groupID = id;
		doSearchList();
	}
	
	function fnChangeAuthority(){
		if(au_gridArea.getCheckedRows(1).length == 0){
			alert("${WM00023}");
			return;
		}
		
		var checkedRows = au_gridArea.getCheckedRows(1).split(",");
		var memberIDs = new Array;
		for(var i = 0 ; i < checkedRows.length; i++ ){
			memberIDs[i] = au_gridArea.cells(checkedRows[i], 2).getValue();
		}
		
		// 시스템 관리자 제외
		var url  = "setUserAuthority.do?groupManagerYN=Y&memberIDs="+memberIDs;
		window.open(url,'window','width=400, height=250, left=300, top=300,scrollbar=no,resizble=0');
	}
	
</script>
<div id="allocUserDiv" >
	<h3 class="mgT10 mgB12" style="width:100%"><img src="${root}${HTML_IMG_DIR}/bullet_blue.png">&nbsp;&nbsp;User Group</span></h3>
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tbl_search" id="search">
	<colgroup>
	    <col width="8%">
	    <col>
    </colgroup>
    <tr>
   		<!-- 프로젝트 그룹 -->
       	<th class="alignL pdL10">Group</th>       	
       	<td class="alignL pdL10"> 
       		<select id="userGroup" Name="userGroup" style="width:15%">
       	    	<c:forEach var="list" items="${userGrMgtList}">
	            	<option value="${list.GroupID}">${list.GroupName }</option>
	            </c:forEach>
	       	</select> 
		</td>
	</tr>
	</table>
	
	<div class="countList">
    	<li  class="count pdT10">Total  <span id="TOT_CNT"></span></li>     
    	<li class="floatR pdR20 mgB10">	
    		&nbsp;<span class="btn_pack medium icon"><span class="reload"></span><input value="Change Authority" onclick="fnChangeAuthority();" type="submit"></span>
    	</li>  	
   	</div>
	<div id="gridDiv" class="mgB10">
		<div id="grdAUArea" style="height:330px; width:100%"></div>
	</div>	
	
		<!-- START :: PAGING -->
		<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL10"></div></div>
		<!-- END :: PAGING -->	
			
	<div class="schContainer" id="schContainer">
		<iframe name="blankFrame" id="blankFrame" src="about:blank" frameborder="0" style="display:none"></iframe>
	</div>
</div>