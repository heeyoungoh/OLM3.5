package xbolt.cmm.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.AESUtil;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.SessionConfig;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GetProperty;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;

/**
 * @Class Name : LoginController.java
 * @Description : LoginController.java
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 * @see
 */

@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value="/login/*.do")
public class LoginActionController extends XboltController{
	private final Log _log = LogFactory.getLog(this.getClass());

	@Resource(name = "commonService")
	private CommonService commonService;
	private AESUtil aesAction;
	
	 @RequestMapping
	 public String form(ModelMap model, HashMap cmmMap) throws Exception {
		  List langList = commonService.selectList("common_SQL.langType_commonSelect", cmmMap);
		  if( langList.size() > 0){
			  for(int i=0; i<langList.size();i++){
				  Map langInfo = (HashMap) langList.get(i);
				  if(langInfo.get("IsDefault").equals("1")){
					  model.put("langType", StringUtil.checkNull(langInfo.get("CODE"),""));
					  model.put("langName", StringUtil.checkNull(langInfo.get("NAME"),""));
				  }
			  }		  
		  }else{model.put("langType", "");model.put("langName", "");}
		  model.put("langList", langList);
		  
		  return nextUrl("custom/sf/login");
	 }
	 
	 @RequestMapping
	 public String logoutForm(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
	  HttpSession session = request.getSession(true);
	  session.invalidate();
	  model=setLoginScrnInfo(model,cmmMap);
	  return nextUrl("custom/sf/login");
	 }
	 
	 @RequestMapping
	 public String checkSessionPage(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		 try {
			// Visit Log
			cmmMap.put("ActionType","LOGOUT");
			
			 String ip = request.getHeader("X-FORWARDED-FOR");
	        if (ip == null)
	            ip = request.getRemoteAddr();
	        
	        cmmMap.put("IpAddress",ip);
	        
			commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
			String langCode = GlobalVal.DEFAULT_LANG_CODE;			
			Map resultMap = new HashMap<String, String>();
			HttpSession session = request.getSession(false);
			
			String loginID = StringUtil.checkNull(session.getAttribute("login_id"));
			
			String activeSessionID = SessionConfig.getSessionIDInfo("login_id",loginID,"Y"); // 동일 아이디 활성화된 sessionID			 
			 
			cmmMap.put("sessionID", activeSessionID);
			cmmMap.put("loginID", loginID);
		    String activeLoginIp = commonService.selectString("gloval_SQL.getVisitLogIpAddress", cmmMap);
			
		    
		  
			resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00170"));
			resultMap.put(AJAX_SCRIPT, "if(typeof(top.fnCheckedDuplicateLogin) != 'undefined'){ top.fnCheckedDuplicateLogin('"+activeLoginIp+"');}else{ opener.top.fnCheckedDuplicateLogin('"+activeLoginIp+"');self.close();}"); 
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::logout::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	 }
	 
	 @RequestMapping
	 public String checkedDuplicateLogin(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		 String activLoginIp = StringUtil.checkNull(cmmMap.get("activLoginIp"));
		 model.put("activLoginIp",activLoginIp);
		  return nextUrl("cmm/session/checkedDuplicateLogin");
	  }
	 
	 @RequestMapping
	 public String confirmDuplicateLogin(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		model.put("loginId", StringUtil.checkNull(cmmMap.get("LOGIN_ID"),""));
		model.put("pwd", StringUtil.checkNull(cmmMap.get("PASSWORD"),""));
		model.put("lng", StringUtil.checkNull(cmmMap.get("LANGUAGE"),""));
		model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx"),""));
		
		String loginID = StringUtil.checkNull(cmmMap.get("LOGIN_ID"),"");		
		String activeSessionID = SessionConfig.getSessionIDInfo("login_id",loginID,"Y"); // 동일 아이디 활성화된 sessionID			 
		 
		cmmMap.put("sessionID", activeSessionID);
		cmmMap.put("loginID", loginID);
	    String activeLoginIp = commonService.selectString("gloval_SQL.getVisitLogIpAddress", cmmMap);
	    model.put("activeLoginIp", activeLoginIp);
	    
		return nextUrl("cmm/session/confirmDuplicateLogin");
	 }
	
	 @RequestMapping
	 public String loginForm(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		 HttpSession session = request.getSession(true);
		  model=setLoginScrnInfo(model,cmmMap);
		  
		  Map loginInfo = (Map)session.getAttribute("loginInfo");
		  if(loginInfo != null && !loginInfo.isEmpty()) {
				model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx"))); //singleSignOn 구분
				model.put("ssoYN", "Y");
		  }
		  model.put("mainType", StringUtil.checkNull(cmmMap.get("mainType"),""));
		  model.put("srID", StringUtil.checkNull(cmmMap.get("srID"),""));
		  model.put("wfInstanceID", StringUtil.checkNull(cmmMap.get("wfInstanceID"),""));
		  model.put("keepLoginYN", StringUtil.checkNull(cmmMap.get("keepLoginYN"),""));
		  
		  return nextUrl("custom/sf/login");
	  }
		
	 @RequestMapping
	 public String loginDftForm(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		 String url="template/login";
		 model=setLoginScrnInfo(model,cmmMap);
		 return nextUrl(url);
	 }	 	 

	 private ModelMap setLoginScrnInfo(ModelMap model,HashMap cmmMap) throws ExceptionUtil {
		  String pass = StringUtil.checkNull(cmmMap.get("pwd"));
		  try {
			  //String pass = URLDecoder.decode(StringUtil.checkNull(cmmMap.get("pwd")), "UTF-8");
			  model.addAttribute("loginid",StringUtil.checkNull(cmmMap.get("loginid"), ""));
			  model.addAttribute("pwd",pass);
			  model.addAttribute("lng",StringUtil.checkNull(cmmMap.get("lng"), ""));
			  		  
			  List langList = commonService.selectList("common_SQL.langType_commonSelect", cmmMap);
			  if( langList!=null &&langList.size() > 0){
				  for(int i=0; i<langList.size();i++){
					  Map langInfo = (HashMap) langList.get(i);
					  if(langInfo.get("IsDefault").equals("1")){
						  model.put("langType", StringUtil.checkNull(langInfo.get("CODE"),""));
						  model.put("langName", StringUtil.checkNull(langInfo.get("NAME"),""));
					  }
				  }
			  }else{model.put("langType", "");model.put("langName", "");}
			  model.put("langList", langList);
			  model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx")));
          } catch(Exception e) {
        	 throw new ExceptionUtil(e.toString());
          }
		  
		  return model;
	 	}
	 	
		@RequestMapping
		public String login(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
			try {
				Map resultMap = new HashMap();
				String langCode = GlobalVal.DEFAULT_LANG_CODE;
				
				cmmMap.put("LANGUAGE", GlobalVal.DEFAULT_LANGUAGE);				
				Map idInfo = commonService.select("login_SQL.login_id_select", cmmMap);
				if(idInfo == null || idInfo.size() == 0) {
					//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.");
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
					resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
				}
				else {
					aesAction = new AESUtil();
					cmmMap.put("LOGIN_ID", idInfo.get("LoginId"));
					String pwd = (String) cmmMap.get("PASSWORD");
					String IS_CHECK = GlobalVal.PWD_CHECK;					

					if("".equals(IS_CHECK))
						IS_CHECK = "Y";					
					
					if("Y".equals(IS_CHECK)) {
						if("Y".equals(GlobalVal.PWD_ENCODING)) {
							aesAction.setIV(request.getParameter("iv"));
							aesAction.setSALT(request.getParameter("salt"));
							
							pwd =  aesAction.decrypt(pwd);
												
							aesAction.init();
							
							pwd = aesAction.encrypt(pwd);
						}

						cmmMap.put("PASSWORD", pwd); 
						cmmMap.put("IS_CHECK", "Y");
					}
					
					Map loginInfo = commonService.select("login_SQL.login_select", cmmMap);
					if(loginInfo == null || loginInfo.size() == 0) {
						//resultMap.put(AJAX_ALERT, "System에 해당 사용자 정보가 없습니다.등록 요청바랍니다.");
						resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
						resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00102"));					
					}
					else {
						// [Authority] < 4 인 경우, 수정가능하게 변경
						if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0){loginInfo.put("loginType", "editor");}
						else{loginInfo.put("loginType", "viewer");}						
						resultMap.put(AJAX_SCRIPT, "parent.fnReload('Y')");
						//resultMap.put(AJAX_MESSAGE, "Login성공");		
						loginInfo.put("ACC_MODE", "DEV");
						loginInfo.put("Secu_Lvl", GlobalVal.PRV_SECU_LVL);	
						
						String session_duplicate = GlobalVal.SESSION_DUPLICATE;	
						String currSessionID = "";
						if(session_duplicate.equals("N")) { // session duplicate check
							String keepLoginYN = StringUtil.checkNull(request.getParameter("keepLoginYN"));
							if(keepLoginYN.equals("Y")) {

							}else {
								currSessionID = SessionConfig.getSessionIDInfo("login_id", StringUtil.checkNull(idInfo.get("LoginId")),"");	
							}
						}
						//System.out.println("currSessionID:"+currSessionID);
						
						String browser 	 = "";
						String userAgent = StringUtil.checkNull(request.getHeader("User-Agent"));		
							
						if(userAgent.indexOf("Trident") > -1) {												// IE
							browser = "ie";
						} else if(userAgent.indexOf("Edge") > -1) {											// Edge
							browser = "edge";
						} else if(userAgent.indexOf("Whale") > -1) { 										// Naver Whale
							browser = "whale";
						} else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { 		// Opera
							browser = "opera";
						} else if(userAgent.indexOf("Firefox") > -1) { 										 // Firefox
							browser = "firefox";
						} else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) {	 // Safari
							browser = "safari";		
						} else if(userAgent.indexOf("Chrome") > -1) {										 // Chrome	
							browser = "chrome";
						}
						
						HttpSession session = request.getSession(true);
						String sessionBrowser = StringUtil.checkNull(session.getAttribute("sessionBrowser"));
						if(!currSessionID.equals("") && !sessionBrowser.equals(browser)) {
							String loginID = StringUtil.checkNull(request.getParameter("LOGIN_ID")); 
							String password = StringUtil.checkNull( request.getParameter("PASSWORD")); 
							String languageID = StringUtil.checkNull( request.getParameter("LANGUAGE")); 
							String loginIdx = StringUtil.checkNull(request.getParameter("loginIdx"));
							resultMap.put(AJAX_SCRIPT, "parent.fnConfirmDuplicateLogin('"+loginID+"','"+password+"','"+languageID+"','"+loginIdx+"')");
						} else {
							session.setAttribute("login_id", idInfo.get("LoginId"));
							session.setAttribute("loginInfo", loginInfo);
							session.setAttribute("sessionBrowser", browser);
						}
					}
				}
				model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx"))); //singleSignOn 구분
				model.addAttribute(AJAX_RESULTMAP,resultMap);
			}
			catch (Exception e) {
				if(_log.isInfoEnabled()){_log.info("LoginActionController::login::Error::"+e.toString().replaceAll("\r|\n", ""));}
				throw new ExceptionUtil(e.toString());
			}
			return nextUrl(AJAXPAGE);
		}	 
	
	@RequestMapping
	public String quitSystem(ModelMap model, HttpServletRequest request, HashMap cmmMap) throws Exception {
		try {
			// Visit Log
			cmmMap.put("ActionType","LOGOUT");			
			String ip = request.getHeader("X-FORWARDED-FOR");
	        if (ip == null)
	            ip = request.getRemoteAddr();
	        
	        cmmMap.put("IpAddress",ip);	        
			commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
						
			Map resultMap = new HashMap<String, String>();
			HttpSession session = request.getSession(true);
			session.invalidate();
			
			resultMap.put(AJAX_SCRIPT, "parent.fnLoginForm()");
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::quitSystem::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping
	public String logout(ModelMap model, HttpServletRequest request, HashMap cmmMap) throws Exception {
		try {
			// Visit Log
			cmmMap.put("ActionType","LOGOUT");
			
			 String ip = request.getHeader("X-FORWARDED-FOR");
	        if (ip == null)
	            ip = request.getRemoteAddr();
	        
	        cmmMap.put("IpAddress",ip);
	        
			commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
			String langCode = GlobalVal.DEFAULT_LANG_CODE;			
			Map resultMap = new HashMap<String, String>();
			HttpSession session = request.getSession(true);
			session.invalidate();
			//resultMap.put(AJAX_ALERT, "로그아웃이 성공하였습니다.");
			resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00103"));
			resultMap.put(AJAX_SCRIPT, "parent.fnLoginForm()");
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::logout::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping
	public String toDoWhenClosing(ModelMap model, HttpServletRequest request, HashMap cmmMap) throws Exception {
		try {
			
			HttpSession session = request.getSession(true);
			String sessionLoginID = StringUtil.checkNull(session.getAttribute("sessionLoginId"));			
			if(!sessionLoginID.equals("master")) {
				// Visit Log
				cmmMap.put("ActionType","WINCLS");			
				String ip = request.getHeader("X-FORWARDED-FOR");
		        if (ip == null) ip = request.getRemoteAddr();	        
		        cmmMap.put("IpAddress",ip);
		        
				commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
			}
			
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::logout::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	//===============================================================================
	// CHECK BY LOGIN ID
	//===============================================================================
	@RequestMapping
	public String loginCheck(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			Map resultMap = new HashMap();
			String langCode = GlobalVal.DEFAULT_LANG_CODE;
			
			cmmMap.put("LOGIN_ID", StringUtil.checkNull(cmmMap.get("loginid")));
			cmmMap.put("LANGUAGE", StringUtil.checkNull(cmmMap.get("languageID"), GlobalVal.DEFAULT_LANGUAGE));
			cmmMap.put("IS_CHECK", "N");
			
			Map idInfo = commonService.select("login_SQL.login_id_select", cmmMap);
			if(idInfo == null || idInfo.size() == 0) {
				//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.");
				resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
			}
			else {
				Map loginInfo = commonService.select("login_SQL.login_select", cmmMap);
				if(loginInfo == null || loginInfo.size() == 0) {
					//resultMap.put(AJAX_ALERT, "System에 해당 사용자 정보가 없습니다.등록 요청바랍니다.");
					resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00102"));					
				}
				else {
					// [Authority] < 4 인 경우, 수정가능하게 변경
					if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0){loginInfo.put("loginType", "editor");}
					else{loginInfo.put("loginType", "viewer");}					
					//resultMap.put(AJAX_MESSAGE, "Login성공");					
					session.setAttribute("loginInfo", loginInfo);
					resultMap.put(AJAX_SCRIPT, "fnReload()");
				}
			}
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::loginCheck::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	
	
	//===============================================================================
	// By Company
	//===============================================================================
	/* BASE */
	@RequestMapping
	public String loginbase(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		try {
			Map resultMap = new HashMap();
			String langCode = GlobalVal.DEFAULT_LANG_CODE;

			
			String IS_CHECK = GlobalVal.PWD_CHECK;
			String PWD_ENCODING = (GlobalVal.PWD_ENCODING).toUpperCase();
			
			if("".equals(IS_CHECK))
				IS_CHECK = "Y";
			
//			String url_CHECK = StringUtil.chkURL(ref, protocol);
			
//			if("".equals(url_CHECK)) {
//				resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
//				resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));	
//			}
//				else {		
				Map idInfo = commonService.select("login_SQL.login_id_select", cmmMap);
				model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx")));
				if(idInfo == null || idInfo.size() == 0) {
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
					//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.");
					resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
				}
				else {
					aesAction = new AESUtil();
					cmmMap.put("LOGIN_ID", idInfo.get("LoginId"));
					
					if("Y".equals(IS_CHECK)) {
						cmmMap.put("IS_CHECK", "Y");
					}
					else {
						cmmMap.put("IS_CHECK", "N");
					}
					
					String pwd = (String) cmmMap.get("PASSWORD");
					
					if("Y".equals(PWD_ENCODING)) {
						aesAction.setIV(request.getParameter("iv"));
						aesAction.setSALT(request.getParameter("salt"));
						
						pwd =  aesAction.decrypt(pwd);
											
						aesAction.init();
						
						pwd = aesAction.encrypt(pwd);
					}
	
					cmmMap.put("PASSWORD", pwd); 
					
					Map loginInfo = commonService.select("login_SQL.login_select", cmmMap);
					if(loginInfo == null || loginInfo.size() == 0) {
						resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
						//resultMap.put(AJAX_ALERT, "System에 해당 사용자 정보가 없습니다.등록 요청바랍니다.");
						resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));					
					}
					else {
						// visit log
						cmmMap.put("ActionType","LOGIN");						
						String ip = request.getHeader("X-FORWARDED-FOR");
				        if (ip == null) ip = request.getRemoteAddr();
				        cmmMap.put("IpAddress",ip);
						commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
						
						// [Authority] < 4 인 경우, 수정가능하게 변경
						if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0)	loginInfo.put("loginType", "editor");
						else	loginInfo.put("loginType", "viewer");	
						
						resultMap.put(AJAX_SCRIPT, "parent.fnReload('Y')");
						//resultMap.put(AJAX_MESSAGE, "Login성공");	
						loginInfo.put("ACC_MODE", "DEV");
						loginInfo.put("Secu_Lvl", GlobalVal.PRV_SECU_LVL);
						
					
						String session_duplicate = GlobalVal.SESSION_DUPLICATE;	
						String returnValue = "";
						String currSessionID = "";
						if(session_duplicate.equals("N")) { // session duplicate check
							String keepLoginYN = StringUtil.checkNull(request.getParameter("keepLoginYN"));
							if(keepLoginYN.equals("Y")) {
								returnValue = SessionConfig.getSessionCheck("login_id", StringUtil.checkNull(idInfo.get("LoginId")),"N","");	
							}else {
								currSessionID = SessionConfig.getSessionIDInfo("login_id", StringUtil.checkNull(idInfo.get("LoginId")),"");	
							}
						}
						//System.out.println("loginbase currSessionID:"+currSessionID);
						
						String browser 	 = "";
						String userAgent = StringUtil.checkNull(request.getHeader("User-Agent"));		
							
						if(userAgent.indexOf("Trident") > -1) {												// IE
							browser = "ie";
						} else if(userAgent.indexOf("Edge") > -1) {											// Edge
							browser = "edge";
						} else if(userAgent.indexOf("Whale") > -1) { 										// Naver Whale
							browser = "whale";
						} else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { 		// Opera
							browser = "opera";
						} else if(userAgent.indexOf("Firefox") > -1) { 										 // Firefox
							browser = "firefox";
						} else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) {	 // Safari
							browser = "safari";		
						} else if(userAgent.indexOf("Chrome") > -1) {										 // Chrome	
							browser = "chrome";
						}
						
						HttpSession session = request.getSession(true);
						String sessionBrowser = StringUtil.checkNull(session.getAttribute("sessionBrowser"));
						if(!currSessionID.equals("") && !sessionBrowser.equals(browser)) {
							String loginID = StringUtil.checkNull(request.getParameter("LOGIN_ID")); 
							String password = StringUtil.checkNull( request.getParameter("PASSWORD")); 
							String languageID = StringUtil.checkNull( request.getParameter("LANGUAGE")); 
							String loginIdx = StringUtil.checkNull(request.getParameter("loginIdx"));
							resultMap.put(AJAX_SCRIPT, "parent.fnConfirmDuplicateLogin('"+loginID+"','"+password+"','"+languageID+"','"+loginIdx+"')");
						} else {
							session.setAttribute("login_id", idInfo.get("LoginId"));
							session.setAttribute("loginInfo", loginInfo);
							session.setAttribute("sessionBrowser", browser);
						}
					}
				}
				model.put("loginIdx", StringUtil.checkNull(cmmMap.get("loginIdx")));
				model.addAttribute(AJAX_RESULTMAP,resultMap);
//			}
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::loginbase::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}	
	
	//===============================================================================
	/* Hanwha Solarone */
	@RequestMapping
	public String loginhso(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		try {
			//System.out.println("loginhso 1:::"+StringUtil.checkNull(cmmMap.get("LOGIN_ID"))+","+StringUtil.checkNull(cmmMap.get("PASSWORD"))+","+StringUtil.checkNull(cmmMap.get("LANGUAGE")));
			HttpSession session = request.getSession(true);
			Map resultMap = new HashMap();
			
			//사용자 정보를 hanwha-solarone에서 사용하기 위한 것
			String LANGUAGE=StringUtil.checkNull(cmmMap.get("LANGUAGE_CH"),"")==""?StringUtil.checkNull(cmmMap.get("LANGUAGE"),GlobalVal.DEFAULT_LANGUAGE):StringUtil.checkNull(cmmMap.get("LANGUAGE_CH"),"");
			cmmMap.put("IS_CHECK", "N");
			cmmMap.put("LANGUAGE",LANGUAGE);cmmMap.put("LanguageID", LANGUAGE);
			String langCode = StringUtil.checkNull(commonService.selectString("common_SQL.getLanguageCode", cmmMap), GlobalVal.DEFAULT_LANG_CODE); 
			System.out.println("loginhso 2:::"+StringUtil.checkNull(cmmMap.get("LOGIN_ID"))+","+StringUtil.checkNull(cmmMap.get("PASSWORD"))+","+LANGUAGE+",langCode="+langCode);
			Map idInfo = commonService.select("login_SQL.login_id_select", cmmMap);
			//Map idInfo = getHSOLogin(StringUtil.checkNull(cmmMap.get("LOGIN_ID")), StringUtil.checkNull(cmmMap.get("PASSWORD")));
			if(idInfo == null || idInfo.size() == 0 || StringUtil.checkNull(idInfo.get("RESULT")).equals("E")) {
				resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
				//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.");
				resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
			}
			else if(StringUtil.checkNull(idInfo.get("RESULT")).equals("N")) {
				resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
				//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.\\n\\n아이디를 잊으신경우 정보를 확인하십시오.");
				resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
			}
			else {
				Map loginInfo = commonService.select("login_SQL.login_select", cmmMap);
				if(loginInfo == null || loginInfo.size() == 0) {
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
					//resultMap.put(AJAX_ALERT, "System에 해당 사용자 정보가 없습니다.등록 요청바랍니다.");
					resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00102"));					
				}
				else {
					// [Authority] < 4 인 경우, 수정가능하게 변경
					if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0)	loginInfo.put("loginType", "editor");
					else	loginInfo.put("loginType", "viewer");	
					
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('Y')");
					//resultMap.put(AJAX_MESSAGE, "Login성공");					
					session.setAttribute("loginInfo", loginInfo);
				}
			}
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::loginhso::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	
	/* green */
	@RequestMapping
	public String loginother(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			Map resultMap = new HashMap();
			String langCode = GlobalVal.DEFAULT_LANG_CODE;			
			Map idInfo = commonService.select("login_SQL.login_id_select", cmmMap);
			if(idInfo == null || idInfo.size() == 0) {
				resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
				//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.");
				resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00002"));				
			}
			else {
				Map loginInfo = commonService.select("login_SQL.login_select", cmmMap);
				if(loginInfo == null || loginInfo.size() == 0) {
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('N')");
					//resultMap.put(AJAX_ALERT, "System에 해당 사용자 정보가 없습니다.등록 요청바랍니다.");
					resultMap.put(AJAX_ALERT, MessageHandler.getMessage(langCode + ".WM00102"));					
				}
				else {
					// [Authority] < 4 인 경우, 수정가능하게 변경
					if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0)	loginInfo.put("loginType", "editor");
					else	loginInfo.put("loginType", "viewer");	
					
					resultMap.put(AJAX_SCRIPT, "parent.fnReload('Y')");
					//resultMap.put(AJAX_MESSAGE, "Login성공");					
					session.setAttribute("loginInfo", loginInfo);
				}
			}
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("LoginActionController::loginbase::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}		
	public static final String HSO_DOMAIN = GetProperty.getProperty("HSO_DOMAIN"); 
	public static final String HSO_LDAPHOST = GetProperty.getProperty("HSO_LDAPHOST");
	
	private Map getHSOLogin(String user, String pass) throws NamingException {
        Hashtable env = new Hashtable();
        System.out.println("getHSOLogin():::"+user + "@" + HSO_DOMAIN+","+pass);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, HSO_LDAPHOST);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@" + HSO_DOMAIN);
        env.put(Context.SECURITY_CREDENTIALS, pass);

        LdapContext ctxGC = null;
        Map amap = new HashMap();
        amap.put("RESULT", "N");
        try {ctxGC = new InitialLdapContext(env, null);amap.put("RESULT", "Y");
        } catch (NamingException ex) {System.out.println("NamingException::::"+ex); amap.put("RESULT", "E");
        } catch (Exception ex) {System.out.println("Exception::::"+ex);amap.put("RESULT", "E");} 
        finally{if (ctxGC != null) {ctxGC.close();}}

        return amap;	
	}

	
}
