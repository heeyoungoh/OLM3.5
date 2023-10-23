<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ page import="xbolt.custom.sk.val.skGlobalVal"%>
<%@ page import="com.rathontech.common.crypt.*" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.*" %>

<% 
	String authKey = "";
	String langKey = "";
%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>::: MPM ::: </title>

<!-- jquery js -->
<!-- version up -->
<script src="<c:url value='/cmm/js/jquery/jquery.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/jquery/ui/jquery.ui.core.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/jquery/ui/jquery.ui.widget.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/jquery/ui/jquery.ui.mouse.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/jquery/ui/jquery.ui.draggable.js'/>" type="text/javascript"></script> 

<!-- XBOLT js -->
<script src="<c:url value='/cmm/js/xbolt/common.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/popupHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/EgovMultiFile.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/ajaxHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/pagingHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/dhtmlxGridHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/dhtmlxTreeHelper.js'/>" type="text/javascript"></script>
<script src="<c:url value='/cmm/js/xbolt/dhtmlxChartHelper.js'/>" type="text/javascript"></script>

<%
		//Header에서 SM-Session 확인
		String value = request.getHeader("Cookie");	
	
      //  System.out.println("MPMvalue1: "+value);
        
    	String SM = null;
		String SMUSER = "1sf";
		String url = skGlobalVal.SK_MPM_URL;
			
		
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
						System.out.println("devalue: "+devalue);

						//복호화된 SMOFC값에서 시스템 사용자 인증에 필요한 정보 추출 (ex. 사번,회사코드 등...)
						String[] OFC = devalue.split(";");
						
						for(int x = OFC.length -1; x >= 0 ; x--){
							String[] cksp = OFC[x].split("=");
							//System.out.println("cksp[0]::"+cksp[0]);
							if("EMPSKHY".equals(cksp[0])){
								SMUSER = cksp[1];
								System.out.println("SMUSER: "+SMUSER);
								break;
							}
						}
					}else{	// SM세션이 없으면 로그인페이지로 이동
						
					//	response.sendRedirect(url);
						
					}
				}else{	// SM세션이 없으면 로그인페이지로 이동
				//	 response.sendRedirect(url);
				
				}
		}else{
			// SM세션이 없으면 로그인페이지로 이동
	//	response.sendRedirect(url);
	}
	
%>

<script type="text/javascript">

var authKey="<%=SMUSER%>";
var langKey="<%=langKey%>";
jQuery(document).ready(function(){

	if("${isType}"=="LOGOUT"){
		fnReload("${isType}");
	}else{
		if(authKey ==""){
			//alert("통합 로그인후 사용하여 주십시오.");			
			//fnLoadUrl("F",url);
		}
		else{
			if(langKey==""){langKey=1042;}			
		    var url = "<c:url value='sk/login.do'/>";
		    var target = "resultLogin";
		    var data ="AUTH_KEY="+authKey+"&LANGUAGE="+langKey+"&IS_CHECK=N"+"&WUXI_YN=N";
		    //alert("data:"+data);
		    ajaxPage(url, data, target);
		}
	}
});

function fnReload(isType) {
	//alert("isType:"+isType);
	if(isType == "SUCCESS"){
	   // var url = "${root}mainHomSingle.do";
	    var url = "${root}mainpage.do";
	    fnLoadUrl("I", url);
	}
	else if(isType == "ISMULTIUSER"){
		alert("MPM시스템 사용자에 같은 사번으로 다수의 사용자가 등록되어있습니다.");
		//개발계
		//url="http://ssodev.skhynix.com/common/OnAccessReject.htm";
		//운영계
		//url="http://heps.skhynix.com/common/OnAccessReject.htm"
		//fnLoadUrl("F", url);		
	}
	else if(isType == "ISNOTUSER"){
		alert("MPM시스템 사용자가 아닙니다.");			
	}
	else if(isType == "ISNOTPWD"){
		
	}
	else if(isType=="LOGOUT"){
		//개발계
		//var url="http://ssodev.skhynix.com/common/logout.asp?TARGET=http://10.156.20.117:8080/sso_login.jsp";
		//운영계
		//var url="http://heps.skhynix.com/common/logout.asp?TARGET=http://mpm.skhynix.com/sso_login.jsp";
		//fnLoadUrl("F", url);
	}
}
function fnLoadUrl(isType, url){	
	if(isType=="F") {
		/*alert(":::url="+url);*/
		window.location.replace(url);
		/*$('#main').attr('src',url);*/
	}else {
		var form = $("form")[0];
		form.action = url;
		form.submit();
	}
}
function fnLoginForm(){
	alert("logout");
}
//1.대상시스템에서 SSO인증페이지 호출시 SSO에서 제공하는 Portlet페이지 호출
//개발:http://ssodev.skhynix.com/Portlets/mpmPortlet.asp
//운영:http://heps.skhynix.com/Portlets/mpmPortlet.asp
//4.SSO에서 전달한 ID를 수신(ssoauthkey)
//http://ssodev.skhynix.com/common/SSOOnAccessReject.htm
</script>
</head>
<body>
   	<form action="" method="post"></form>
   	<div id="resultLogin" style="display:none;"></div> 
	</body>
</html>