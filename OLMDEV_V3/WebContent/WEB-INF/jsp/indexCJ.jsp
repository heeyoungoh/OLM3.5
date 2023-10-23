<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@ page import="xbolt.custom.cj.val.CJGlobalVal"%>
<%
	String userId = request.getParameter("userId");
	String token = request.getParameter("token");
	String language = request.getParameter("language");
%>

<%String type = request.getParameter("type") == null ? "" : request.getParameter("type");%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/cmm/<%=GlobalVal.BASE_ATCH_URL%>/css/style.css"/>
<script src="${pageContext.request.contextPath}/cmm/js/jquery/jquery.js" type="text/javascript"></script>
<style type="text/css">html,body {overflow-y:hidden;width:100%;height:100%}</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/cmm/cj/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=CJGlobalVal.CJ_SSO_URL%>/officeon/js/api/officeon.sso.js"></script>

<script type="text/javascript">

var type="<%=type%>";
var atchUrl = "<%=GlobalVal.BASE_ATCH_URL%>";
var lgnUrl="${pageContext.request.contextPath}/cj/logincjForm.do";
jQuery(document).ready(function() {
	var userId = "<%=userId%>";
	var token = "<%=token%>";
	var language = "<%=language%>";
	

	ooSSO.openPage(userId, token, language , function(retData){ //alert("retData.data :"+retData.data);
		if(retData.data == "S"){
			//인증성공 시 페이지 오픈처리
			//alert("Identification success");
			var submitForm = document.mainForm;
			submitForm.target="main";
			submitForm.action=lgnUrl;
			submitForm.submit();
		 }else{
			//인증실패 시 처리
			alert("Identification failed");
		} 
	});
});

function officeonSSOCallBack(e){
	alert(e);
}
function fnLoginForm() {main.location = lgnUrl;}
</script>
</head>
<body>
	<form name="mainForm" action="#" method=post target='main'>
	<input type="hidden" id="loginID" name="loginID" value="${loginID}"/>
	<input type="hidden" id="token" name="token" value="${token}"/>
	<input type="hidden" id="language" name="language" value="${language}"/>
	<input type="hidden" id="loginIdx" name="loginIdx" value="${loginIdx}"/>	
	</form>
	<iframe name="main" id="main" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
</body></html>