<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />
<c:if test="${!empty title}"><script>parent.document.title="${htmlTitle}";</script></c:if>
<script type="text/javascript">
var defaultLang = <%=GlobalVal.DEFAULT_LANGUAGE%>;
var defaultLangCode = "<%=GlobalVal.DEFAULT_LANG_CODE%>";
var atchUrl = "<%=GlobalVal.BASE_ATCH_URL%>";
</script>
<!-- 1. Include JSP -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/cmm/<%=GlobalVal.BASE_ATCH_URL%>/css/language.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/cmm/js/xbolt/lovCssHelper.js"></script>

<jsp:include page="/WEB-INF/jsp/template/uiInc.jsp" flush="true"/>
<script src="${pageContext.request.contextPath}/cmm/js/xbolt/cookieHelper.js" type="text/javascript"></script>

<script type="text/javascript">
$(document).ready(function(){    
    fnInit();
    $(".doLogin").keyup(function() {if(event.keyCode == '13') {fnLogin();}});
    $(".login").click(function() {fnLogin('1');});
});
function fnInit(){  
    //fnSelect('LANGUAGE', '', 'langType', defaultLang, '언어 선택');
    changeLanguage(defaultLang, '${langName}');
    $('#LANGUAGE').val(defaultLang);
    fnInitCooki();
}
function fnSelect(id, code, menuId, defaultValue, isAll){
    url = "<c:url value='/ajaxCodeSelect.do'/>";
    data = "ajaxParam1="+code+"&menuId="+menuId;
    ajaxSelect(url, data, id, defaultValue, "n");
}
function fnInitCooki(){
    var val = getCookie("sfolmLgId");
    if( val == false){document.getElementById("LOGIN_ID").focus();} else{$('#LOGIN_ID').val(val) ;document.getElementById("IS_CK").checked = true;}
}
function fnLogin(loginType) {
    if( !fnCheckValidation()){return;}  
    fnSetCookie();
    var url = "${pageContext.request.contextPath}/login/loginbase.do";
    var target = "resultLogin";
    //var data =fnAddData("LOGIN_ID")   + fnAddData("LANGUAGE") + fnAddData("PASSWORD") +"&IS_CHECK=Y";
    //var data ="LOGIN_ID="+$("#LOGIN_ID").val()+"&LANGUAGE="+$("#LANGUAGE").val()+"&PASSWORD="+encodeURIComponent($("#PASSWORD").val()) +"&IS_CHECK=Y";
    ajaxSubmitNoAdd(document.loginForm, url,"saveLgFrame");
    //ajaxPage(url, data, target);
}
function fnCheckValidation(){
    var isCheck = true;
   
    if($('#LOGIN_ID').val() == ""){alert("<spring:message code='KO.WM00099'/>");$('#LOGIN_ID').focus();return false;}
    if($('#PASSWORD').val() == ""){alert("<spring:message code='KO.WM00100'/>");$('#PASSWORD').focus();return false;}
    if($('#LANGUAGE').val() == ""){alert("<spring:message code='KO.WM00101'/>"); return false;}   
    return isCheck;
}
function fnSetCookie(){
    if(document.getElementById("IS_CK").checked) { setCookie("sfolmLgId", $('#LOGIN_ID').val(), 180);} else {  setCookie("sfolmLgId", "", -1);  }
}
function fnReload(isScs) {if(isScs=='Y'){var url = "${root}mainpage.do";var form = $("form")[0]; form.action = url;form.submit();}}
function changeLanguage(langType,langText){$("#LANGUAGE").val(langType);$("#cmbLanguage").html(langText);}

function clickId() {$('#LOGIN_ID').val('');}
function clickPass() {$('#PASSWORD').val('');}
//function blurId() {if ($('#LOGIN_ID').val() == '') {$('#LOGIN_ID').val('아이디를 입력해주세요');}}
//function blurPass(){if ($('#PASSWORD').val() == '') {$('#PASSWORD').val('비밀번호를 입력해주세요');}}

</script>
</head>

<body>
<form id="dfForm" name="dfForm" action="" method="post"></form> 
<div id="login_main">
    <form id="loginForm" name="loginForm" action="" method="post" onsubmit="return false;">
        <input type="hidden" id="IS_CHECK" name="IS_CHECK" value="Y"/>
		<input type="hidden" id="LANGUAGE" name="LANGUAGE">
		<input type="hidden" id="LangCode" name="LangCode">
		<div class="lang">
		  <ul id="nav">
		       <li class="top"><span id="cmbLanguage">${langName}</span>
		           <ul class="sub">
		           <c:forEach var="lang" items="${langList}" varStatus="status" >
		               <li onclick="changeLanguage('${lang.CODE}','${lang.NAME}');"><a href="#" alt="${lang.NAME}" class="topmenu" id="${lang.CODE}">${lang.NAME}</a>
		               </li>
		           </c:forEach>                            
		           </ul>
		       </li>                       
		   </ul>                  
		</div>
		<div id="wrapper1">
		        <div class="loginarea1">
		            <div class="loginForm1">
		                    <li>
		                        <input class="log" type="text" name="LOGIN_ID" id="LOGIN_ID" size="30" title="Login ID"  style="ime-mode:inactive;"/>
		                    </li>
		                    <li>
		                        <input class="doLogin log" type="password" name="PASSWORD" id="PASSWORD" size="30" title="Password"  style="ime-mode:inactive;"/>
		                    </li>
		            	</div>
		            	<div class="loginbtn">
                                <a href="#" class="login"><img src="${pageContext.request.contextPath}/${HTML_IMG_DIR}/btn_login.png" title="Log In" id="login"/></a>
                    	</div>
                       <div  class="pdL10  pdT5">
                             <input  class="pdL40" type="checkbox" name="IS_CK" id="IS_CK" value=""/>&nbsp;<font color="#ccc" class="pdR10">Save ID</font>
              			</div>
		        </div>
		</div>
	</form>
</div>

<div id="resultLogin"></div>    
<iframe name="saveLgFrame" id="saveLgFrame" src="about:blank" style="display:none" frameborder="0"></iframe>
</body>
</html>