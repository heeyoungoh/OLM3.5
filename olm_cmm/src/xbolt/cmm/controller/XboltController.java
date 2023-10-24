package xbolt.cmm.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import xbolt.cmm.framework.util.DateUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;




/**
 * @Class Name : XboltController.java
 * @Description : 공통
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
public class XboltController {

	
	protected final static String SQL_KEY = "sqkKey";
	protected final static String SQL_SELECT_LIST = "_selectList";
	protected final static String SQL_TREE_LIST = "_treeList";
	protected final static String SQL_GRID_LIST = "_gridList";
	protected final static String SQL_CHART = "_chart";
	protected final static String SQL_SELECT_LIST_CNT = "_selectListTotCnt";
	protected final static String SQL_REPORT = "_report";
	protected final static String SQL_COMMON_HEADER = "common_SQL.";
	protected final static String SQL_COMMON_TAIL = "_commonSelect";

	protected final static String HTML_HEADER = "title";

	private final static String FORWARD = "forward:/WEB-INF/jsp/";
	private final static String FORWARD_JSP = ".jsp";
	protected final static String FORWARD_EXCEL = "forward:/WEB-INF/jsp/cmm/excelTemplate.jsp";

	private final static String PAGE_UNIT = "10";
	private final static String PAGE_SIZE = "10";

	//parameter 정보
	protected final static String PARAMMAP = "paramMap";
	/*
	 * ajax 공통적인 결과 페이지
	 */
	protected final static String AJAX_ATTRLI = "/cmm/ajaxResult/ajaxAttrLi";
			
	protected final static String AJAXPAGE = "/cmm/ajaxResult/ajaxPage";
	protected final static String AJAXNOPAGE = "/cmm/ajaxResult/ajaxNoScriptPage";
	
	protected final static String AJAXPAGE_CODEJSON = "/cmm/ajaxResult/ajaxPageCodeJson";
	protected final static String AJAXPAGE_SELECTOPTION = "/cmm/ajaxResult/ajaxOption";
	protected final static String AJAXPAGE_RADIO = "/cmm/ajaxResult/ajaxRadio";
	protected final static String AJAXPAGE_COMBO = "/cmm/ajaxResult/ajaxGridCombo";
	protected final static String AJAXPAGE_CHECKBOX = "/cmm/ajaxResult/ajaxCheckbox";
	protected final static String AJAXPAGE_IMG = "/cmm/ajaxResult/ajaxUploadImg";
	protected final static String AJAXPAGE_IMGDELETE = "/cmm/ajaxResult/ajaxDeleteableImg";
	protected final static String AJAXPAGE_NOIMGDELETE = "/cmm/ajaxResult/ajaxDeleteableNoImg";
	protected final static String AJAXPAGE_FILE = "/cmm/ajaxResult/ajaxUploadFile";

	protected final static String AJAX_RESULTMAP = "resultMap";
	protected final static String AJAX_ALERT= "ALERT";
	protected final static String AJAX_MESSAGE= "MESSAGE";
	protected final static String AJAX_SCRIPT= "SCRIPT";
	protected final static String AJAX_NEXTPAGE= "NEXTPAGE";
	
	protected final static String WEBAPP_ROOT = System.getProperty("sf-olm.root");


	protected void fatchPaging(Map commandMap) throws Exception {
		commandMap.put(PAGE_UNIT, PAGE_UNIT);
		commandMap.put(PAGE_SIZE, PAGE_SIZE);
	}
	protected String nextUrl(String next) {
		String url = FORWARD + next + FORWARD_JSP;
		System.out.println("nextURL ===> " + url);
		return url;
	}
	protected String redirect(String next) {
		String url = "redirect:" + next;
		System.out.println("nextURL ===> " + url);
		return url;
	}
	protected String nextUrl(Object next) {
		return nextUrl((String)next);
	}

	protected ModelAndView getModelAndView(String next, String title){
		ModelAndView mav = new ModelAndView(next);
		mav.addObject("title",   title);
		return mav;
	}

	protected static String getString(Object key) {
		return (String) key;
	}
	protected static String getString(Object key, String defaultValue) {
		if (key==null||"".equals(key)) {
			return defaultValue;
		}
		return (String) key;
	}

	/**
	 * 여러건의 단순 저장처리 시 쿼리 설정을 한다.
	 * @param sqlId
	 * @param type
	 * @return
	 */
	/*
	protected Map newSqlMap(String sqlId, Object type) {
		Map map = new HashMap();
		map.put(CommonMsDao.SQL_KEY, sqlId);
		map.put(CommonMsDao.SQL_TYPE, type);
		return map;
	}
	*/

	protected Map getNextId(Map commandMap, CommonService commonService, String COL_NAME) throws Exception{
		commandMap.put("COL_NAME", COL_NAME);
		commandMap.put(COL_NAME, commonService.selectToObject("common_SQL.commonSeqId_select", commandMap));
		return commandMap;
	}
	
	public static String getWebappRoot(){
		return WEBAPP_ROOT;
	}
	
	public static String getUserCurrLangType(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession(true);
		String langType = GlobalVal.DEFAULT_LANGUAGE;
		String reqLangType = StringUtil.checkNull(request.getParameter("languageID"), "");
		String sessLangType = "";
		
		if(reqLangType.equals("")){
			Map userInfo = (Map)session.getAttribute("loginInfo");
			if(userInfo!=null){
				sessLangType = String.valueOf(userInfo.get("sessionCurrLangType"));
				langType = sessLangType;
			}
		}else{langType=reqLangType;}
		//System.out.println("default="+GlobalVal.DEFAULT_LANGUAGE+", reqLangType="+reqLangType+",sessionLang="+sessLangType+",langType="+langType);
		return langType;
	}	
	
	/*
	public static Map getUserLabel(HttpServletRequest request) throws Exception{
		String langType = XboltController.getUserCurrLangType(request);
		Map labelMap = (Map)xbolt.cmm.framework.handler.LabelHandler.getLabelMap();
		Map userLabel = (Map)labelMap.get(langType);
		return userLabel;
	}
	*/
	
	public static Map getLabel(HttpServletRequest request, CommonService commonService) throws Exception{
		HashMap cmmMap = new HashMap();
		HashMap getMap = new HashMap();
		String langType = XboltController.getUserCurrLangType(request);
		cmmMap.put("languageID", langType);
		cmmMap.put("mnCategory", "LN");
		List labelList = commonService.selectList("menu_SQL.menuName",cmmMap);
		
		cmmMap = new HashMap();
		for(int i = 0; i < labelList.size(); i++){
			cmmMap = (HashMap)labelList.get(i);
			getMap.put(cmmMap.get("TypeCode"), cmmMap.get("Name"));
		}
		
		return getMap;
	}	
	///HashMap으로 변경
	public static Map getLabel(HashMap cmmMap, CommonService commonService) throws Exception{
		cmmMap.put("languageID", StringUtil.checkNull(cmmMap.get("languageID"), GlobalVal.DEFAULT_LANGUAGE));
		cmmMap.put("mnCategory", "LN");
		
		List labelList = commonService.selectList("menu_SQL.menuName",cmmMap);
		
		HashMap getMap = new HashMap();
		HashMap listMap = new HashMap();
		for(int i = 0; i < labelList.size(); i++){
			listMap = (HashMap)labelList.get(i);
			getMap.put(listMap.get("TypeCode"), listMap.get("Name"));
		}
		
		return getMap;
	}
	
	public static Map getLabel(HttpServletRequest request, CommonService commonService, String category) throws Exception{
		HashMap cmmMap = new HashMap();
		HashMap getMap = new HashMap();

		String langType = XboltController.getUserCurrLangType(request);
		cmmMap.put("languageID", langType);
		cmmMap.put("mnCategory", category);
		List labelList = commonService.selectList("menu_SQL.menuName",cmmMap);
		
		cmmMap = new HashMap();
		for(int i = 0; i < labelList.size(); i++){
			cmmMap = (HashMap)labelList.get(i);
			getMap.put(cmmMap.get("TypeCode"), cmmMap.get("Name"));
		}
		
		return getMap;
	}	
	
	
	//===================================================================================
	// INIT PROG_LOG
	public static HttpSession setInitProcLog(HttpServletRequest request) throws Exception{
		String callStartTime = DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		
		//SessionUtil.setSessionAttrString(request, "callStartTime", callStartTime);
		HttpSession session = request.getSession(true);
		session.removeAttribute("callStartTime");
		session.setAttribute("callStartTime", callStartTime);
		
		return session;
	}	
	public static String getInitProcLog(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession(true);
		String callStartTime = (String)session.getAttribute("callStartTime");
		return callStartTime;
	}
	
	//===================================================================================
	//PROCESS PROG_LOG
	public static Map setProcLog(HttpServletRequest request, CommonService commonService, Map cmmMap) throws Exception{
		
		HashMap resultMap = new HashMap();
		//=======================
		//set Parameter
		//HashMap getParam = new HashMap();
		//Context Path
		String context 				= request.getContextPath();
		//요청페이지 주소
		String reqUrl 				= request.getServletPath();
		/*
		Enumeration<?> enumeration = request.getParameterNames();
		String parameter="";
		if(enumeration != null){while(enumeration.hasMoreElements()){
			String key = (String) enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if(values!=null){getParam.put(key, ((values.length > 1)?values:values[0]));}
		}}
		System.out.println("XboltController.setProcLog : getParam===>>>"+getParam);
		*/
		System.out.println("XboltController.setProcLog : cmmMap===>>>"+cmmMap);
		//=======================
		//get PROC_LOG_CONFIG
		reqUrl = reqUrl.replace("/", "");
		cmmMap.put("FunctionNM", reqUrl);
		System.out.println("XboltController.setProcLog : reqUrl===>>>"+reqUrl);
		String procLogId = commonService.selectString("procLog_SQL.procLog_nextVal", cmmMap); 
		List procLogCnfList = commonService.selectList("procLog_SQL.procLogConfigList_select",cmmMap);
		HashMap listMap = new HashMap();
		HashMap setMap = new HashMap();
		String key = "";
		int cnfSize =  procLogCnfList.size();
		if( cnfSize <1){}		
		else if( cnfSize == 1){
			listMap = (HashMap)procLogCnfList.get(0);
			key = StringUtil.checkNull(listMap.get("PIDParameter"));			
			setMap.put("PID", cmmMap.get(key));
			key = StringUtil.checkNull(listMap.get("ActionParameter"));			
			setMap.put("ActionID", cmmMap.get(key));
			setMap.put("ActivityCD", StringUtil.checkNull(listMap.get("ActivityCD")));					
			key = StringUtil.checkNull(listMap.get("CommentParameter"));			
			setMap.put("Comment", cmmMap.get(key));	
		}
		else{
			for(int i = 0; i < cnfSize; i++){
				listMap = (HashMap)procLogCnfList.get(i);
				key = StringUtil.checkNull(listMap.get("EventParameter"));
				if(!"".equals(key) && !"".equals(StringUtil.checkNull(cmmMap.get(key)))){
					String cnfValue = StringUtil.checkNull(listMap.get("EventValue"));
					if(cnfValue.equals(StringUtil.checkNull(cmmMap.get(key)))){
						key = StringUtil.checkNull(listMap.get("PIDParameter"));			
						setMap.put("PID", cmmMap.get(key));
						key = StringUtil.checkNull(listMap.get("ActionParameter"));			
						setMap.put("ActionID", cmmMap.get(key));
						setMap.put("ActivityCD", StringUtil.checkNull(listMap.get("ActivityCD")));		
						key = StringUtil.checkNull(listMap.get("CommentParameter"));			
						setMap.put("Comment", cmmMap.get(key));		
					
						break;
					}
				}
			}
		}
		
		//=======================
		//save PROC_LOG
		try{
			if(setMap!=null && setMap.size() > 0){
				//SessionUtil.setSessionAttrString(request, "callStartTime", callStartTime);
				HttpSession session = request.getSession(true);
				Map loginInfo = (Map)session.getAttribute("loginInfo");
				
				String callStartTime = StringUtil.checkNull(session.getAttribute("callStartTime"),"");
				setMap.put("SEQ", procLogId);
				setMap.put("StartTime", callStartTime);		
				setMap.put("TeamID", StringUtil.checkNull(loginInfo.get("sessionTeamId")));					
				setMap.put("TeamName", StringUtil.checkNull(loginInfo.get("sessionTeamName")));
				setMap.put("ActorID", StringUtil.checkNull(loginInfo.get("sessionUserId")));	
				System.out.println("XboltController.setProcLog : setMap===>>>"+setMap);
				commonService.insert("procLog_SQL.insertProcLog", setMap);
			}
			resultMap.put("type", "SUCESS");
		}catch(Exception ex){
			resultMap.put("type", "FAILE");
			resultMap.put("msg", ex.getMessage());
		}
		
		return resultMap;
	}	
	
	//===================================================================================
	//Send Email
	public static Map setEmailLog(HttpServletRequest request, CommonService commonService, Map cmmMap, String dicTypeCode) throws Exception{
		
		HashMap resultMap = new HashMap();		
		if( StringUtil.checkNull(GlobalVal.USE_EMAIL).equals("Y")){
			Map mMailInfo = new HashMap();
			mMailInfo.put("dicTypeCode", dicTypeCode);
			try{
				Map setMap = new HashMap();
				//	String receiptEmail =  "skyi@smartfactory.co.kr";//StringUtil.checkNull(commonService.selectString("user_SQL.userEmail", setData));		
				//	String senderEmail =  "jhwan.cho@hanwha-total.com";//StringUtil.checkNull(commandMap.get("sessionEmail"));
				List receiverList = (List)cmmMap.get("receiverList");
				//보내는 사람
				HttpSession session = request.getSession(true);
				Map loginInfo = (Map)session.getAttribute("loginInfo");
				String sendUserID= StringUtil.checkNull(loginInfo.get("sessionUserId"));
				setMap.put("userID", sendUserID);
				//String senderEmail = StringUtil.checkNull(commonService.selectString("user_SQL.userEmail", setMap));
				String emailSender = GlobalVal.EMAIL_SENDER;
				
				if(receiverList.size()>0 && !emailSender.equals("")){
					Map setDicMap = new HashMap();
					setDicMap.put("Category", "EMAILCODE");
					setDicMap.put("TypeCode", dicTypeCode);
					setDicMap.put("LanguageID", StringUtil.checkNull(loginInfo.get("sessionCurrLangType")));
					List mailDtlList = new ArrayList();
					mailDtlList = commonService.selectList("common_SQL.label_commonSelect",setDicMap);
					String mailSubject="";
					for(int i=0; i<mailDtlList.size(); i++){
						mailSubject = StringUtil.checkNull(((HashMap)mailDtlList.get(i)).get("LABEL_NM")); break;
					}
					String subject = StringUtil.checkNull(cmmMap.get("subject"));
					mMailInfo.put("mailSubject", mailSubject+" "+subject);
					///insert emailLog
					String maxId = null;
					List receiverInfoList = new ArrayList();
					Map receiverInfoMap = new HashMap();
					Map receiverListMap = new HashMap();
					String receiptEmail = null;
					
					mMailInfo.put("Sender", emailSender);					
					mMailInfo.put("EmailCode", dicTypeCode);
					
					for(int i=0; receiverList.size()>i; i++){
						receiverInfoMap = new HashMap();
						receiverListMap = (Map)receiverList.get(i);
						maxId = StringUtil.checkNull(commonService.selectString("email_SQL.emailLog_nextVal", setMap)).trim();
						receiverInfoMap.put("seqID", maxId);
						setMap.put("userID", receiverListMap.get("receiptUserID"));
						receiptEmail =  StringUtil.checkNull(commonService.selectString("user_SQL.userEmail", setMap));	
						receiverInfoMap.put("receiptEmail", receiptEmail);						
						receiverInfoMap.put("receiptType", StringUtil.checkNull(receiverListMap.get("receiptType"),"TO") );
						receiverInfoMap.put("receiptUserID", receiverListMap.get("receiptUserID"));
						
						receiverInfoList.add(i, receiverInfoMap);						
						mMailInfo.put("Receiver", receiptEmail);
						mMailInfo.put("SEQ", maxId);
						commonService.insert("email_SQL.insertEmailLog", mMailInfo);
					}
					
					mMailInfo.put("receiverInfoList", receiverInfoList);
					mMailInfo.put("sendUserID", sendUserID);	
					
					resultMap.put("type", "SUCESS");
					resultMap.put("mailLog", mMailInfo);
				}else{
					resultMap.put("type", "FAILE");
					resultMap.put("msg", "not exists email address : emailSender="+emailSender+", receiverList.Size="+receiverList.size());
				}
			}catch(Exception ex){
				resultMap.put("type", "FAILE");
				resultMap.put("msg", ex.getMessage());
			}
		}else{
			resultMap.put("type", "DONT");
			resultMap.put("msg", "not use email");
		}
		return resultMap;
	}
	
	//===================================================================================
	//PROCESS PROG_LOG
	public static Map decideSRProcPath(HttpServletRequest request, CommonService commonService, Map cmmMap) throws Exception{
		
		HashMap resultMap = new HashMap();
		//=======================
		//set Parameter
		//HashMap getParam = new HashMap();
		//Context Path
		String context 				= request.getContextPath();
		//요청페이지 주소
		String reqUrl 				= request.getServletPath();
		//=======================
		//get PROC_LOG_CONFIG
		reqUrl = reqUrl.replace("/", "");
		cmmMap.put("FunctionNM", reqUrl);
		System.out.println("XboltController.decideSRProcPath : reqUrl===>>>"+reqUrl);
		
		List procLogCnfList = commonService.selectList("procLog_SQL.getESMProcConfig",cmmMap);
		HashMap listMap = new HashMap();
		String key = "";		
		for(int i = 0; i < procLogCnfList.size(); i++){
			listMap = (HashMap)procLogCnfList.get(i);
			key = StringUtil.checkNull(listMap.get("EventParameter"));
			if(!"".equals(key) && !"".equals(StringUtil.checkNull(cmmMap.get(key)))){
				String cnfValue = StringUtil.checkNull(listMap.get("EventValue"));
				if(cnfValue.equals(StringUtil.checkNull(cmmMap.get(key)))){
					key = StringUtil.checkNull(listMap.get("PIDParameter"));			
					resultMap.put("PID", cmmMap.get(key));
					key = StringUtil.checkNull(listMap.get("ActionParameter"));			
					resultMap.put("ActionID", cmmMap.get(key));
					resultMap.put("ActivityCD", StringUtil.checkNull(listMap.get("ActivityCD")));		
					key = StringUtil.checkNull(listMap.get("CommentParameter"));			
					resultMap.put("Comment", cmmMap.get(key));	
					
					resultMap.put("SpeCode", listMap.get("SpeCode"));	
					resultMap.put("ProcPathID", listMap.get("ProcPathID"));
				
					break;
				}
			}
		}
		return resultMap;
	}
			

}
