package xbolt.custom.sk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.StringUtil;
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
@RequestMapping(value="/sk/*.do")
public class LoginSKActionController extends XboltController{

	private String BASE_SSO_DIR="custom/sk/ssoSK/";
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@RequestMapping
	public String ssoLogin(ModelMap model, HttpServletRequest request) throws Exception {
		return nextUrl(BASE_SSO_DIR+"sso_login");
	}
	@RequestMapping
	public String ssoLogout(ModelMap model, HttpServletRequest request) throws Exception {
		return nextUrl(BASE_SSO_DIR+"sso_logout");
	}	
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
	  
		 return nextUrl(BASE_SSO_DIR+"sso_login");
	 }
	 
	 @RequestMapping
	 public String logoutForm(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		  HttpSession session = request.getSession(true);
		  session.invalidate();
		  model=setLoginScrnInfo(model,cmmMap,request);
		  //model.put("isType", "LOGOUT");
		  return nextUrl(BASE_SSO_DIR+"sso_login");
	 }
	
	 @RequestMapping
	 public String loginForm(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		model=setLoginScrnInfo(model,cmmMap,request);
		return nextUrl(BASE_SSO_DIR+"sso_login");
	 }

	 private ModelMap setLoginScrnInfo(ModelMap model,HashMap cmmMap, HttpServletRequest request) throws Exception {
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
				  
		return model;
	 }
	
	@RequestMapping
	public String login(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		Map resultMap = new HashMap();
		try {
			//SSO확인
			String isType="SUCCESS";
			// TODO : String EMP_NUM = hynix.sso.SsoUtil.decrypt(StringUtil.checkNull(request.getParameter("AUTH_KEY"), ""));
			String EMP_NUM = StringUtil.checkNull(request.getParameter("AUTH_KEY"), "");
			cmmMap.put("EMP_NUM", EMP_NUM);
			System.out.println("cmmMap:"+cmmMap);
			
			HttpSession session = request.getSession(true);
			//if(session.equals(null)){session = request.getSession(true);}
			
			//1.SSO관련 사용자가 있는지 확인
			//1.1 없는 경우는 Is Not User - I/F테이블 IDM_USR_TBL 비교 삭제(20140626)
			//1.2 있는 경우는 MPM에서 사용자 정보 조회
			//Map empInfo = commonService.select("login_SQL.empNo_SSO_select", cmmMap);
			//if(empInfo == null || empInfo.size() == 0){
			//	isType = "ISNOTUSER";
			//	//resultMap.put(AJAX_ALERT, "해당 사용자 정보가 존재하지 않습니다.");
			//}else{	
				//2.MPM 사용자가 있는지 확인 ex)2
				Map idInfo = commonService.select("sk_cmm_SQL.empNo_MPM_select", cmmMap);
				//2.1 MPM사용자 정보가 없는 경우는 Guest로 처리
				if(idInfo == null || idInfo.size() == 0) {
					//isType = "ISNOTUSER";
					//resultMap.put(AJAX_ALERT, "해당아이디가 존재하지 않습니다.\\n\\n아이디를 잊으신경우[ID/PW찾기]해주세요.");
					//resultMap.put(AJAX_MESSAGE, "해당아이디가 존재하지 않습니다.아이디를 잊으신경우[ID/PW찾기]해주세요.");	
					cmmMap.put("LOGIN_ID", "guest");
					Map loginInfo = commonService.select("sk_cmm_SQL.login_select", cmmMap);
					//List langList  = new ArrayList();
					//langList = (List) commonService.selectList("common_SQL.label_commonSelect", cmmMap); 
					loginInfo.put("loginType", "viewer");	
					session.setAttribute("loginInfo", loginInfo);
				}
				//2.2 MPM사용자 정보가 많은 경우는 
				else if( idInfo.size()>1){
					isType = "ISMULTIUSER";					
				}
				//2.3 MPM사용자 정보가 있는 경우는 해당 사용자로 Return
				else {
					cmmMap.put("LOGIN_ID", StringUtil.checkNull(idInfo.get("LOGIN_ID")));
					
					Map loginInfo = commonService.select("sk_cmm_SQL.login_select", cmmMap);
					//List langList  = new ArrayList();
					//langList = (List) commonService.selectList("common_SQL.label_commonSelect", cmmMap); 
					if(loginInfo == null || loginInfo.size() == 0) {
						isType = "ISNOTPWD";
						//resultMap.put(AJAX_ALERT, "비밀번호가 일치하지 않습니다.");
					}
					else {
						// [Authority] < 4 인 경우, 수정가능하게 변경
						if(loginInfo.get("sessionAuthLev").toString().compareTo("4") < 0){loginInfo.put("loginType", "editor");}
						else{loginInfo.put("loginType", "viewer");	}
						//String companyType = loginInfo.get("sessionCompanyTypCd").toString();
						//resultMap.put(AJAX_SCRIPT, "fnReload('"+companyType+"')");
					}
					session.setAttribute("loginInfo", loginInfo);
				}
			//}
			resultMap.put(AJAX_SCRIPT, "fnReload('"+isType+"');");
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		model.addAttribute(AJAX_RESULTMAP,resultMap);
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping
	public String logout(ModelMap model, HttpServletRequest request, HashMap cmmMap) throws Exception {
		try {
			//commonService.update("login_SQL.logout_process", cmmMap);
			Map resultMap = new HashMap<String, String>();
			HttpSession session = request.getSession(true);
			session.invalidate();
			resultMap.put(AJAX_ALERT, "Successfully logged out!");
			resultMap.put(AJAX_SCRIPT, "parent.fnLoginForm('LOGOUT')");
			model.addAttribute(AJAX_RESULTMAP,resultMap);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
		//return nextUrl(BASE_SSO_DIR+"sso_logout");
	}
}
