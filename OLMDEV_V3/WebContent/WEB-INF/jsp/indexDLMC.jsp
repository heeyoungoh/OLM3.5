<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.rathontech2018.sso.sp.agent.web.WebAgent"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.regex.*"%>
<%@ page import="xbolt.cmm.framework.util.StringUtil"%>

<%String type = request.getParameter("type") == null ? "" : request.getParameter("type");%> 
<%
	// 환경설정 시작
	String SSO_URL	= "https://gw.daelimcloudtest.com";
	String returnScript = "";   // 로그아웃,로그인, 메인 해야 할 경우 사용할 스크립트
	// 환경설정 끝

	// 기본변수 끝
	String ssoEmp = "";
	String olmI = "";

	// 인증 시작
	WebAgent agent = new WebAgent(); //agent 호출
	agent.requestAuthentication(request, response);
	
	// SSO 에러 메세지 있을 경우 출력
	String errorMsg =  request.getParameter("ErrorMsg");
	StringBuffer errorScript = new StringBuffer();
	if (errorMsg!=null) {
		errorScript.append("<script>");
		errorScript.append("alert("+errorMsg+");");
		errorScript.append("</script>");
	}
	
	ssoEmp = StringUtil.checkNull(session.getAttribute("RathonSSO_PERSON_NO"));
	
	// ssoEmp 없으면 sso 로그인 페이지 
	if(!"".equals(ssoEmp) && ssoEmp != null){
		if(agent.requestAuthentication(request, response,false)){
			//인증처리 필요 (DL 그룹웨어로 이동)
			if(!response.isCommitted()) {
				//response.sendRedirect(SSO_URL);
			}
		} else {
			//SSO 인증완료
			olmI = ssoEmp;
		}
	}
	
%>


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
var lgnUrl="${pageContext.request.contextPath}/custom/dlmc/logindlmcForm.do";
jQuery(document).ready(function() {
	var olmI = <%=olmI %>;	// 사번
	var submitForm = document.mainForm;
		
	if(olmI == ""){
		alert("Identification failed");
	}
	
	submitForm.action=lgnUrl;
	submitForm.target="main";
	submitForm.submit();
	
});
function fnLoginForm() {main.location = lgnUrl;}
</script>
</head><body>

	<form name="mainForm" action="#" method=post target='main'>
		<input type="hidden" id="loginid" name="loginid" value="<%=olmI %>"/>
		<input type="hidden" id="pwd" name="pwd" value="${olmP}"/> 
		<input type="hidden" id="lng" name="lng" value="${olmLng}"/>
		<input type="hidden" id="loginIdx" name="loginIdx" value="${loginIdx}"/>
		<input type="hidden" id="screenType" name="screenType" value="${screenType}"/>	
		<input type="hidden" id="mainType" name="mainType" value="${mainType}"/>	
		<input type="hidden" id="srID" name="srID" value="${srID}"/>
		<input type="hidden" id="sysCode" name="sysCode" value="${sysCode}"/>	
		<input type="hidden" id="proposal" name="proposal" value="${proposal}"/>
		<input type="hidden" id="status" name="status" value="${status}"/>		
	</form>
	<iframe name="main" id="main" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
</body>
</html>