<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@ page import="xbolt.custom.sk.val.skGlobalVal"%>
<%@ page import="com.rathontech.common.crypt.*" %>
<% 
//String loginid = request.getParameter("olmLoginid") == null ? "" : request.getParameter("olmLoginid");
//String languageID = request.getParameter("languageID");
String languageID = request.getParameter("languageID") == null ? "" : request.getParameter("languageID");
String keyId = request.getParameter("keyId") == null ? "" : request.getParameter("keyId");
String object = request.getParameter("object") == null ? "" : request.getParameter("object");
String linkType = request.getParameter("linkType") == null ? "" : request.getParameter("linkType");
String linkID = request.getParameter("linkID") == null ? "" : request.getParameter("linkID");
String iType = request.getParameter("iType") == null ? "" : request.getParameter("iType");
String aType = request.getParameter("aType") == null ? "" : request.getParameter("aType");
%> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
<jsp:include page="/WEB-INF/jsp/template/uiInc.jsp" flush="true"/>
<script src="${pageContext.request.contextPath}/cmm/js/jquery/jquery.js" type="text/javascript"></script>

<%
		//Header에서 SM-Session 확인
		String value = request.getHeader("Cookie");	
        // System.out.println("value: "+value);
        
    	String SM = null;
		String SMUSER = "mpm";
		String url="http://heps.skhynix.com/sso/agentless/agentless_redirect.jsp?initpage=http://mpm.skhynix.com/olmLinkSK.do?object=itm&linkType=id&languageID=1042&linkID"+linkID;
				
		
		if (null != value && value.indexOf(";")!= -1) {
			String[] tC = value.split("; ");
	    	for (int i = tC.length - 1; i >= 0; i--) 
			{
				String[] x = tC[i].split("=");
        
       	 		if ("SMSESSION".equals(x[0]))
	        	{
		        	SM = x[1];  
			    	break;                        
        		}
	    	}	
		}

		
		if(null != SM && !"LOGGEDOFF".equals(SM)){

				Cookie[] ck = null;
				ck = request.getCookies();


				if(ck != null) {
					String ckname = "SMOFC";	
					int ckfind = -1;
					String ckimport = null;
				
					for(int i=0;i<ck.length;i++){
						if(ck[i].getName().equals(ckname)){
							ckfind = i;
							break;
						}
					}		
					if(ckfind != -1 && ck[ckfind].getValue() != "" && ck[ckfind].getValue() != null){
						// 암호화된 SMOFC 쿠키값 get
						ckimport = ck[ckfind].getValue();
						// 암호화된 SMOFC 쿠키값 복호화
						CaCrypt cry = new CaCrypt();
						String devalue = cry.extractValue(ckimport);	
						//System.out.println("devalue: "+devalue);

						//복호화된 SMOFC값에서 시스템 사용자 인증에 필요한 정보 추출 (ex. 사번,회사코드 등...)
						String[] OFC = devalue.split(";");
						
						for(int x = OFC.length -1; x >= 0 ; x--){
							String[] cksp = OFC[x].split("=");
							//System.out.println("cksp[0]::"+cksp[0]);
							if("EMPSKHY".equals(cksp[0])){
								SMUSER = cksp[1];
								//System.out.println("SMUSER: "+SMUSER);
								break;
							}
						}
					}else{	// SM세션이 없으면 로그인페이지로 이동
						
				//		response.sendRedirect(url);
						
					}
				}else{	// SM세션이 없으면 로그인페이지로 이동
				//	 response.sendRedirect(url);
				
				}
		}else{
			// SM세션이 없으면 로그인페이지로 이동
		//response.sendRedirect(url);
	}
	
%>

<script type="text/javascript"> 
function ajaxPage(url, data, target, preHtml, debug, debugTarget) {
	$.ajax({
		url: url,type: 'post',data: data,async: true,error: function(xhr, status, error) {alert(status+"||"+error);$('#loading').fadeOut(150); },beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}}
		,success: function(data){$('#loading').fadeOut(10);if(debug){if(debugTarget==null){	alert(data);}else {	$("#debugMod").val(data);$("#debugMod").show();}} $("#" + target).hide();if(preHtml!=null){data=preHtml+data;}$("#" + target).html(data);$("#" + target).fadeIn(10);}
	});
}
var object = "<%=object%>";
var authKey="<%=SMUSER%>";
var languageID="<%=languageID%>";
jQuery(document).ready(function() {
	if(authKey ==""){
		alert("통합 로그인후 사용하여 주십시오.");
		return;
	} else {	
	    if(languageID==""){languageID=1042;}			
	    var url = "<c:url value='sk/login.do'/>";
	    var target = "divResult";
	    var data ="AUTH_KEY="+authKey+"&LANGUAGE="+languageID+"&IS_CHECK=N"+"&WUXI_YN=N";
	    //alert("data:"+data);
	    ajaxPage(url, data, target);
	}
});

//http://localhost/dev_xbolt/popupMasterItem.do?languageID=1042&id=100031&scrnType=pop
<spring:message code="${DEFAULT_LANG_CODE}.WM00104" var="WM00104" />

function fnReload(isType) {
	var msg = "${WM00104}";	
	var url="";
	if(isType == "SUCCESS"){
		if(object==null||object==undefined||object==""){alert(msg);}
		else{
			if(object.toUpperCase()=="ITM"){
				url="olmPopupMasterItem.do?languageID="+languageID
						+ "&scrnType=pop&id=<%=keyId%>&object=<%=object%>"
						+ "&linkType=<%=linkType%>&linkID=<%=linkID%>"
						+ "&iType=<%=iType%>&aType=<%=aType%>";
			}
			else if(object.toUpperCase()=="CSR"){url="csrDetailPop.do?scrnType=pop&ProjectID=<%=linkID%>&screenMode=V";}
			else if(object.toUpperCase()=="PJT"){url="${root}mainpage.do?mainType=pjt&projectCode=<%=linkID%>";}
			if(url.length>0){ 
				//main.location = url;
				var dsWindow=window.open (url,'_self', "popup"+(new Date()).valueOf(),"status=1,toolbar=0,menubar=0,resizable=0,width=1014,height=680,padding=10px");
				dsWindow.moveTo(0, 0);	
				//closeThis();
			}else{alert(msg);}
		}
	}else if(isType == "ISMULTIUSER"){
		alert("MPM시스템 사용자에 같은 사번으로 다수의 사용자가 등록되어있습니다.");
		var url="<%=skGlobalVal.SK_ACC_RJC_URL%>";
		//개발계
		//url="http://ssodev.skhynix.com/common/OnAccessReject.htm";
		//운영계
		//url="http://heps.skhynix.com/common/OnAccessReject.htm"
		fnLoadUrl("F", url);		
	}
	else if(isType == "ISNOTUSER"){
		alert("MPM시스템 사용자가 아닙니다.");
		var url="<%=skGlobalVal.SK_ACC_RJC_URL%>";
		//개발계
		//url="http://ssodev.skhynix.com/common/OnAccessReject.htm";
		//운영계
		//url="http://heps.skhynix.com/common/OnAccessReject.htm"
		fnLoadUrl("F", url);		
	}else if(isType == "ISNOTPWD"){
		
	}else if(isType=="LOGOUT"){
		var url="<%=skGlobalVal.SK_LOGOUT_URL%>";
		//개발계
		//var url="http://ssodev.skhynix.com/common/logout.asp?TARGET=http://10.156.20.117:8080/sso_login.jsp";
		//운영계
		//var url="http://heps.skhynix.com/common/logout.asp?TARGET=http://mpm.skhynix.com/sso_login.jsp";
		fnLoadUrl("F", url);
	}	
}
function closeThis() {
	//window.close();
	if(/MSIE/.test(navigator.userAgent)) {
		if(navigator.appVersion.indexOf("MSIE 8") >= 0 ||
				navigator.appVersion.indexOf("MSIE 7") >= 0 ) {
			window.open('about:blank','_self').close();
		} else {
			window.opener = self;
			self.close();
		}
	} else {
		window.close();
	}
}
function fnLoadUrl(isType, url){alert(isType+"///"+url);window.location.replace(url);}
</script>
</head>
<body><div id="divResult" style="display:none;"></div>  <!--iframe name="main" id="main" width="100%" height="100%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe--></body>
</html>