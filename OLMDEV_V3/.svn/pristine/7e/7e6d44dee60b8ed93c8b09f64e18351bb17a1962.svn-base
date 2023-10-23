<%@ page import = "java.util.List, java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>사용자 Search</title>
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script type="text/javascript">
$(function(){
	$('.sem001').click(function(){
		$('.sem001').css('background-color', '#ffffff');
		$('.sem001').attr('alt', '');
		$(this).css('background-color', '#eafafc');
		$(this).attr('alt', '1');
	}).mouseover(function(){
		$(this).css('background-color', '#eafafc');
	}).mouseout(function(){
		if($(this).attr('alt') != 1) 
			$(this).css('background-color', '#ffffff');
	});;
});

$(window).load(function(){
	$("#searchValue").focus();
	
});

function searchFrom(){
	var searchValue = $('#searchValue').val();
	if(searchValue == ""){
		alert("사용자명을 입력하세요.");
		return;
		
	}
	
	var gubun = "${gubun}";
	document.getElementById('processList').action = "searchNamePopFromMDM.do?gubun="+gubun";
	document.getElementById("processList").submit();
}


function setInfo(avg,avg2){

	// 승인자의 경우 권한 체크 필요
    var gubun = "${gubun}";
    if(gubun == "app"){
    	var idFirst = avg.substring(0,1);	
    	if(idFirst == "1" || idFirst == "2" ){
    		
    	} else {
    		alert("승인자는 담당모듈BR을 선택하여 주세요.");
    		return;
    	}
    		
    }
    
	window.opener.setSearchName(avg,avg2);
	self.close();
}


</script>
<script type="text/javascript">
function btndown(id,loc){
    var obj = document.getElementById(id);
    var orgSrc = obj.src;
    var newSrc = loc;
    
    obj.src = newSrc;
    
    obj.onmouseout = function(){
        obj.src = orgSrc;
    }
}
</script>
</head>

<body>

    <!-- BEGIN :: BOARD_ADMIN_FORM -->
    <form name="processList" id="processList" action="#" method="post" onsubmit="return false;">
    <input type="hidden" id="languageID" name="languageID" value="${sessionScope.loginInfo.sessionCurrLangType}" >
    <div id="processListDiv" class="hidden" style="width:100%;height:100%;">
	        <div class="child_search_head">
	              <p><img src="${root}${HTML_IMG_DIR}/user.png">&nbsp;사용자 Search</p>
	            </div>
            <div class="child_search_form">
		        <div class="child_search mgT5">
		               <li>사용자명</li>
		               <li>
		                <input type="text" id="searchValue" name="searchValue" value="${searchValue}" class="stext" style="width:120px;ime-mode:active;" onkeydown="if (event.keyCode == 13){ searchFrom();return false;}"/>
		                <input type="image" class="image" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" onclick="searchFrom()" value="검색">
		                </li>
		        </div>
			    <table class="tbl_blue01 mgT5" width="90%" cellpadding="0" cellspacing="0" border="0">
			        <tr>
			            <th class="viewtop">사용자명</th>
			            <th class="viewtop">Login ID</th>
			            <th class="viewtop last">${menu.LN00104}</th>
			        </tr>
			<c:forEach var="i" items="${getList}" varStatus="listStatus">
			            <tr class="sem001">
			                <td onclick="setInfo('${i.LoginID}','${i.UserName}')">${i.UserName}</td>
			                <td onclick="setInfo('${i.LoginID}','${i.UserName}')">${i.LoginID}</td>
			                <td class="tit" onclick="setInfo('${i.LoginID}','${i.UserName}')">${i.TeamName}</td>
			            </tr>
			</c:forEach>
			        </table>
        </div>
    </div>
    </form>
</body>
</html>