package xbolt.cmm.framework.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;

/**
 * 공통 서블릿 처리
 * @Class Name : XboltFilter.java
 * @Description : Filter Url 체크하기 위해 제공한다.
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 * @see

 */
public class XboltFilter implements Filter{
	protected FilterConfig	config	= null;
	private static String [] ignoreUrl = {
		"start"
		, "/menuInc.do"
		, "/header.do"
		, "/login/"
		, "/lf/" // LG fashion
		, "/hanwha/" // hanwha-total
		, "/mando/" // mando
		, "daelim" // Daelim
		, "/sk/"
		, "/olmLinkSSOPop.do"
		, "/loginsamsungSSO.do"
		, "indexSEMES.do"
		, "/samsungSSO.do"
		, "ctsCount.do" //SKH
		, "/ajax"
		, "index"
		, "indexHSO"
		, "ssoIndex.do"
		, "index.do?type=Dft"		
		, "loadLabelMap"
		, "/popup/findIDPasswordPopup.do" //아이디 비밀번호 찾기 폼 
		, "/admin/user/requestLoingId.do" //아이디 찾기
		, "/admin/user/requestPassword.do" //비밀번호 찾기
		, "/popup/searchTeamPop.do" //팀 조회 폼
		, "/popup/searchNamePop.do" //작성자 조회 폼
		, "/searchTeamPop.do" //팀 조회 폼
		, "/searchNamePop.do" //작성자 조회 폼
		, "/searchPluralNamePop.do" //작성자 조회 폼(복수 사용인 경우)
		, "olmPopup.do"//외부인터페이스 팝업
		, "olmLink.do"//OLM 외부인터페이스 팝업
		, "olmObjLink.do"//OLM 외부인터페이스 팝업
		, "olmLinkTest"
		, "olmLinkLF.do"
		, "olmLinkSK.do"
		, "olmLinkMD.do"
		, "olmAprvDueLink.do"
		, "olmChangeSetLink.do"
		, "olmLinkSamsung.do"
		, "srConfirmFromEmail.do"
		, "confirmSR.do"
		, "indexCJ.do"
		, "indexIEP.do"
		, "zDli2Olm.do"
		, "indexDS.do"
		, "openViewerPop.do"
		, "/cj/" // CJ GLOBAL
		, "sessionTimeout.do"
		, "viewerFileDownload.do"
		, "zmd_UpdateWFReturnValue.do"
		, "/kdnvn/"
		, "indexKDNVN.do"
		, "getBatchList.do"
		, "/daesang/" // Daelim
		, "srAprvDetailEmail.do"
		, "reqSRDueDateChangeEmail.do"
		, "olmLinkArc.do"		
		, "olmLinkMaster.do"
		, "/custom/"
		, "/link/"
		, "/batch/"
		, "/hyundai/" // Hyundai Engineering
		, "/hec/" // Hyndai Engineering
		, "/checkOlmService.do"
		, "/checkSessionPage.do"
		, "/confirmDuplicateLogin.do"
	};
	
	private static String [] adminUrl = {
		"configurationMgt"
		, "userInfoMgt"
		, "boardAdminMgt"
		, "changeAdmin"
		
	};
	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest f_req 	= (HttpServletRequest)request;
		//Context Path
		String context 				= f_req.getContextPath();
		//요청페이지 주소
		String reqUrl 				= f_req.getServletPath();
		//이전페이지 주소
		String refUrl				= f_req.getHeader("referer");
		
		HttpSession session = f_req.getSession();
		String baseUrl = "login";
		if(StringUtil.hanSubstr(7, reqUrl).equals("/ssoSK/")){baseUrl+="ssoSK";}
		
		String BASE_ATCH_URL = StringUtil.checkNull(GlobalVal.BASE_ATCH_URL); 
		if(!BASE_ATCH_URL.equals("sf")) {			
			baseUrl = "custom/" + BASE_ATCH_URL;
			BASE_ATCH_URL = BASE_ATCH_URL.toUpperCase();
		} else { BASE_ATCH_URL = ""; }
		
		/*
		if(!StringUtil.chkAuthority("svUrl",refUrl,"0")) {		     		
			((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/logoutForm.do");	
			return;
     	}     	
		
		*/

		if((!session.isNew() && session != null) ||(session != null)){
			
			Map loginInfo = (Map)session.getAttribute("loginInfo");
	     	String sessionActive = StringUtil.checkNull(session.getAttribute("sessionActive"));	     	
	     	
			if(loginInfo == null && isIgnoreUrl(reqUrl)) {
				((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/login"+BASE_ATCH_URL+"Form.do");
				return;
			}
			else if(loginInfo != null){
				if(!sessionActive.equals("N")) {
					String templCode = StringUtil.checkNull(loginInfo.get("templ_code"));
			     	String sessionAuthLev = StringUtil.checkNull(loginInfo.get("sessionAuthLev"));
			     	
			     	/*	
			     	 
					String sessionUserIP = (String) session.getAttribute("sessionUserIP");
					String nowUserIP = request.getRemoteAddr();
					
					if(sessionUserIP != null && !"".equals(sessionUserIP) && !nowUserIP.equals(sessionUserIP)) {
						((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/logoutForm.do");
						return;
					}
					else {		
						session.setAttribute("sessionUserIP", nowUserIP);
					}
					
			     	if(!StringUtil.chkAuthority("admin",templCode,sessionAuthLev)) {		     		
						((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/logoutForm.do");
						return;	
			     	}
			     	
			     	if(!StringUtil.chkAuthority("admP",reqUrl,sessionAuthLev)) {		     		
						((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/logoutForm.do");
						return;	
			     	}
			     	*/
			     	
			     	if(!isAdminUrl(reqUrl) && !"1".equals(sessionAuthLev)) {	 	     		
						((HttpServletResponse) response).sendRedirect(context+"/"+baseUrl+"/logoutForm.do");	
						return;
			     	}
				}else {
					((HttpServletResponse) response).sendRedirect(context + "/login/checkSessionPage.do");	
					session.setAttribute("sessionActive","N2");
					return;
				}
		     			     			     	
			}			
		}else{
			if(session.getAttribute("verified") == null){session.setAttribute("verified", true);}
			Enumeration<?> enumeration = f_req.getParameterNames();
			/*
			String parameter="";
			if(enumeration != null){while(enumeration.hasMoreElements()){
				String key = (String) enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				if(values!=null){parameter+=key+"="+((values.length > 1)?values:values[0])+"&";}
			}}*/
			reqUrl = context+reqUrl;
			//if(!parameter.equals("")){reqUrl = reqUrl+"?"+parameter.substring(0, parameter.length()-1);}			
			((HttpServletResponse) response).sendRedirect(reqUrl);
			return;
			//System.out.println(">>>>"+context+"|||"+reqUrl+"//////");
		}

		if (chain != null) {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	private boolean isIgnoreUrl(String url) {
		boolean result = true;
		for (String ignore : ignoreUrl) {
			if (url.indexOf(ignore)!=-1) {
				result = false;
			}
		}
		return result;
	}
	private boolean isAdminUrl(String url) {
		boolean result = true;
		for (String ignore : adminUrl) {
			if (url.indexOf(ignore)!=-1) {
				result = false;
			}
		}
		return result;
	}

}
