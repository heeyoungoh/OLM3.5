<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
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
<script type="text/javascript">
var type="<%=type%>";
var atchUrl = "<%=GlobalVal.BASE_ATCH_URL%>";
var lgnUrl="/custom/hyundai/loginHKFCForm.do";
jQuery(document).ready(function() {
	var olmI = "${olmI}";
	if(olmI != ""){
		var submitForm = document.mainForm;
		submitForm.target="main";
		submitForm.action=lgnUrl;
		submitForm.submit();
	}else{
		alert("You don't have access right. Please contact the system administrator");
		/*	
		var submitForm = document.mainForm;
		submitForm.target="main";
		submitForm.action=lgnUrl;
		submitForm.submit();*/
		
	}
});
function fnLoginForm() {main.location = lgnUrl;}
</script>
</head><body>
	<form name="mainForm" action="#" method=post target='main'>
	<input type="hidden" id="loginid" name="loginid" value="${olmI}"/>
	<input type="hidden" id="PWD_KEY" name="PWD_KEY" value="${PWD_KEY}"/>
	<input type="hidden" id="iv" name="iv" value="${iv}"/>
	<input type="hidden" id="salt" name="salt" value="${salt}"/>
	<input type="hidden" id="pwd" name="pwd" value="${olmP}"/> 
	<input type="hidden" id="lng" name="lng" value="${olmLng}"/>
	<input type="hidden" id="loginIdx" name="loginIdx" value="${loginIdx}"/>
	<input type="hidden" id="screenType" name="screenType" value="${screenType}"/>	
	<input type="hidden" id="mainType" name="mainType" value="${mainType}"/>	
	<input type="hidden" id="srID" name="srID" value="${srID}"/>
	<input type="hidden" id="sysCode" name="sysCode" value="${sysCode}"/>	
	<input type="hidden" id="proposal" name="proposal" value="${proposal}"/>
	</form>
	<iframe name="main" id="main" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
</body></html>