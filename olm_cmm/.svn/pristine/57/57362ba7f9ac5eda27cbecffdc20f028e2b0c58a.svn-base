package xbolt.cmm.framework.val;

import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;

public class GlobalVal {	
	public static final String DB_KEY = "2DAE49B9205DC3E7";	

	public static final String BASE_ATCH_URL = StringUtil.checkNull(GetProperty.getProperty("BASE_ATCH_URL")).toLowerCase();
	public static final String HTML_LOGIN_PAGE = (BASE_ATCH_URL.length()>0?"custom/"+BASE_ATCH_URL+"/":"custom/")+"login";//"template/login/"+(BASE_ATCH_URL.length()>0?"login_"+GlobalVal.BASE_ATCH_URL:"login");	
	public static final String IMG_DEFAULT_DIR = StringUtil.checkNull(GetProperty.getProperty("IMG_DEFAULT_DIR"), "images");
	public static final String HTML_IMG_DIR = BASE_ATCH_URL.length()>0?"cmm/"+BASE_ATCH_URL+"/"+IMG_DEFAULT_DIR+"/":"cmm/"+IMG_DEFAULT_DIR+"/";
	public static final String HTML_IMG_DIR_ARC=HTML_IMG_DIR+StringUtil.checkNull(GetProperty.getProperty("IMG_DIR_ARC"))+"/";
	public static final String HTML_IMG_DIR_SHORTCUT=HTML_IMG_DIR+StringUtil.checkNull(GetProperty.getProperty("IMG_DIR_SHORTCUT"))+"/";
	public static final String HTML_IMG_DIR_POPUP=HTML_IMG_DIR+StringUtil.checkNull(GetProperty.getProperty("IMG_DIR_POPUP"))+"/";
	public static final String HTML_IMG_DIR_MODEL_SYMBOL=HTML_IMG_DIR+StringUtil.checkNull(GetProperty.getProperty("IMG_DIR_MODEL_SYMBOL"))+"/";
	public static final String HTML_IMG_DIR_ITEM=HTML_IMG_DIR+StringUtil.checkNull(GetProperty.getProperty("IMG_DIR_ITEM"))+"/";
	
	public static final String OLM_SERVER_URL=StringUtil.checkNull(GetProperty.getProperty("OLM_SERVER_URL"));
	public static final String EMP_PHOTO_URL=StringUtil.checkNull(GetProperty.getProperty("EMP_PHOTO_URL"));
	public static final String EMP_PHOTO_ITEM_DISPLAY=StringUtil.checkNull(GetProperty.getProperty("EMP_PHOTO_ITEM_DISPLAY"));
	
	public static final String HTML_CSS_DIR = BASE_ATCH_URL.length()>0?"cmm/"+BASE_ATCH_URL+"/css/":"cmm/css/";
	public static final String HTML_TITLE = GetProperty.getProperty("HTML_TITLE");
	
	public static final String DEFAULT_LANGUAGE = GetProperty.getProperty("DEFAULT_LANGUAGE");
	public static final String DEFAULT_LANG_CODE = GetProperty.getProperty("DEFAULT_LANG_CODE");
	public static final String LANGUAGE_KR = "1042";	//Kor
	public static final String LANGUAGE_EN = "1033";	//Eng
	public static final String LANGUAGE_ZH = "2052";	//Cn
	
	public static final String DEFAULT_PASSWRORD = StringUtil.checkNull(GetProperty.getProperty("DEFAULT_PASSWRORD"));
	//MODELER : OLM or ARIS
	public static final String DEFAULT_MODELER = StringUtil.checkNull(GetProperty.getProperty("DEFAULT_MODELER"),"OLM");
	
	public static final String FILE_UPLOAD_BASE_DIR = GetProperty.getProperty("FILE_UPLOAD_BASE_DIR");
	public static final String FILE_UPLOAD_TINY_DIR = GetProperty.getProperty("FILE_UPLOAD_TINY_DIR");
	public static final String FILE_UPLOAD_CHG_SET_DIR = GetProperty.getProperty("FILE_UPLOAD_CHG_SET_DIR");
	public static final String FILE_UPLOAD_ITEM_DIR = GetProperty.getProperty("FILE_UPLOAD_ITEM_DIR");
	public static final String FILE_UPLOAD_ZIPFILE_DIR = GetProperty.getProperty("FILE_UPLOAD_ZIPFILE_DIR");
	public static final String FILE_EXPORT_DIR = GetProperty.getProperty("FILE_EXPORT_DIR");
	public static final String FILE_UPLOAD_TMP_DIR = GetProperty.getProperty("FILE_UPLOAD_TMP_DIR");
	public static final String FILE_UPLOAD_PJT_DIR = GetProperty.getProperty("FILE_UPLOAD_PJT_DIR");
	public static final String FILE_UPLOAD_IS_DIR = GetProperty.getProperty("FILE_UPLOAD_IS_DIR");
	public static final String FILE_UPLOAD_SR_DIR = GetProperty.getProperty("FILE_UPLOAD_SR_DIR");
	public static final String FILE_MULTIMEDIA_DIR = GetProperty.getProperty("FILE_MULTIMEDIA_DIR");
	public static final String FILE_UPLOAD_BOARD_DIR = GetProperty.getProperty("FILE_UPLOAD_BOARD_DIR");
	public static final String FILE_FORMAT_WHITE_LIST = GetProperty.getProperty("FILE_FORMAT_WHITE_LIST");
	
	
	public static final String ARIS_XML_DIR = GetProperty.getProperty("ARIS_XML_DIR");
	public static final String MODELING_DIGM_DIR = GetProperty.getProperty("MODELING_DIGM_DIR");
	public static final String MODELING_DIGM_IMG_TYPE = GetProperty.getProperty("MODELING_DIGM_IMG_TYPE");
	public static final String MODELING_DIGM_IMG_MIN_H = "1000";
	public static final String MODELING_DIGM_IMG_MIN_W = "800";
	
	public static final String CHART_COLOR_8 = "#fc9d01 #f76556 #c8c8c8 #9c9c9c #fed6d6 #616160 #ffd35c #cf1d00";
	public static final String[][] ENCODING_STRING = {{"&","&amp;"},{"<","&lt;"},{">","&gt;"},{"#10;","&#10;"},{"#xa;","&#xa;"},{"/quo","&quot;"}};
	
	/** Work Flow 관련 */
	public static final String DEF_APPROVAL_SYSTEM = GetProperty.getProperty("DEF_APPROVAL_SYSTEM");
	public static final String CAR_APPROVAL_PATH = GetProperty.getProperty("CAR_APPROVAL_PATH");
	public static final String CSR_APPROVAL_PATH = GetProperty.getProperty("CSR_APPROVAL_PATH");
	public static final String CS_APPROVAL_PATH = GetProperty.getProperty("CS_APPROVAL_PATH");
	
	/**	CommonDao에서 LoginInfo를 전부 찍느냐 일부만 찍느냐 구분	*/
	public final static boolean SESSIONINFO_PRINT_ALL = false;
	
	/** HR Interface Procedure 관련 */
	public static final String HR_IF_PROC = StringUtil.checkNull(GetProperty.getProperty("HR_IF_PROC"));
	/**
	 * 페이징 관련 변수
	 */ 
	public static final int LIST_SCALE = NumberUtil.getIntValue(GetProperty.getProperty("DEFAULT_LIST_SCALE"), 15);//15; // 한페이지에 나오는 글 갯수	
	public static final int LIST_PAGE_SCALE = NumberUtil.getIntValue(GetProperty.getProperty("DEFAULT_LIST_PAGE_SCALE"), 10);//10; // 게시글 리스트 페이징
	
	//BASE
	public static final String SAP_HOST = GetProperty.getProperty("SAP_HOST");
	public static final String SAP_CLIENT = GetProperty.getProperty("SAP_CLIENT");
	public static final String SAP_LANGUAGE = GetProperty.getProperty("SAP_LANGUAGE");
	public static final String DEF_CLIENT_ID = GetProperty.getProperty("DEF_CLIENT_ID");
	
	//Email 사용여부
	public static final String USE_EMAIL = StringUtil.checkNull(GetProperty.getProperty("USE_EMAIL"),"N"); //Y:사용, N:미사용
	public static final String EMAIL_SENDER = StringUtil.checkNull(GetProperty.getProperty("EMAIL_SENDER"),""); 
	public static final String EMAIL_SENDER_NAME = StringUtil.checkNull(GetProperty.getProperty("EMAIL_SENDER_NAME"),""); 
	public static final String EMAIL_HOST_IP = StringUtil.checkNull(GetProperty.getProperty("EMAIL_HOST_IP"),""); 
	public static final String SMTP_AUTHENTIFICATION = StringUtil.checkNull(GetProperty.getProperty("SMTP_AUTHENTIFICATION"),""); 
	public static final String SMTP_SSL = StringUtil.checkNull(GetProperty.getProperty("SMTP_SSL"),""); 
	public static final String SMTP_ACCOUNT_PWD = StringUtil.checkNull(GetProperty.getProperty("SMTP_ACCOUNT_PWD"),"");
	public static final String GW_LINK_URL = StringUtil.checkNull(GetProperty.getProperty("GW_LINK_URL"),"");
	
	// Multi company 여부
	public static final String MULTI_COMPANY = StringUtil.checkNull(GetProperty.getProperty("MULTI_COMPANY"),""); 
	
	//Add on Component 사용여부
	public static final String USE_COMP_SR = StringUtil.checkNull(GetProperty.getProperty("USE_COMP_SR"),"N"); //Y:사용, N:미사용
	public static final String USE_COMP_CR = StringUtil.checkNull(GetProperty.getProperty("USE_COMP_CR"),"N"); //Y:사용, N:미사용
	public static final String USE_COMP_TASK = StringUtil.checkNull(GetProperty.getProperty("USE_COMP_TASK"),"N"); //Y:사용, N:미사용

	// USE DRM 여부 
	public static final String USE_DRM = StringUtil.checkNull(GetProperty.getProperty("USE_DRM"),""); //FASOO:사용, NULL:미사용
	public static final String DRM_DOWN_USE = StringUtil.checkNull(GetProperty.getProperty("DRM_DOWN_USE"),""); 
	public static final String DRM_UPLOAD_USE = StringUtil.checkNull(GetProperty.getProperty("DRM_UPLOAD_USE"),""); 
	
	// EMAIL TYPE
	public static final String EMAIL_TYPE = StringUtil.checkNull(GetProperty.getProperty("EMAIL_TYPE"),""); //SMTP, HANWHA 등
	
	// 비밀번호 암호화 여부
	public static final String PWD_ENCODING = StringUtil.checkNull(GetProperty.getProperty("PWD_ENCODING"),""); //Y, N
	public static final String PWD_KEY = StringUtil.checkNull(GetProperty.getProperty("PWD_KEY"),""); //Y, N
	
	// 비밀번호 체크 여부
	public static final String PWD_CHECK = StringUtil.checkNull(GetProperty.getProperty("PWD_CHECK"),"Y"); //Y, N
	public static final String PWD_TEXT_CHECK = StringUtil.checkNull(GetProperty.getProperty("PWD_TEXT_CHECK"),"N"); //Y, N
		
	// 회사별 css 적용 여부
	public static final String CUSTOM_CSS = StringUtil.checkNull(GetProperty.getProperty("CUSTOM_CSS"),"N"); //Y:고객사별 css 폴더 경로 사용, N:미사용
	
	// 만도 뷰어 url
	public static final String DOC_VIEWER_URL = StringUtil.checkNull(GetProperty.getProperty("DOC_VIEWER_URL"),""); //뷰어 호출 경로	
	
	public static final String OLM_SERVER_NAME = StringUtil.checkNull(GetProperty.getProperty("OLM_SERVER_NAME"),""); // SFOLM

	public static final String OLM_MASTER_URL = StringUtil.checkNull(GetProperty.getProperty("OLM_MASTER_URL"),""); // SFOLM
	public static final String OLM_INBOUND_LINK = StringUtil.checkNull(GetProperty.getProperty("OLM_INBOUND_LINK"),""); // SFOLM
	
	public static final String APPROVAL_SYS_URL = StringUtil.checkNull(GetProperty.getProperty("APPROVAL_SYS_URL"),""); // Other System Workflow URL
	public static final String APPORVAL_MULTI = StringUtil.checkNull(GetProperty.getProperty("APPORVAL_MULTI"),""); // 다중결제 상신 사용 여부
	
	// USE CRM 여부 
	public static final String USE_CRM = StringUtil.checkNull(GetProperty.getProperty("USE_CRM"),""); 
	
	// HTC PROPOSAL URL
	public static final String PROPOSAL_SERVER_URL = StringUtil.checkNull(GetProperty.getProperty("PROPOSAL_SERVER_URL"));
	
	public static final String PRV_SECU_LVL = StringUtil.checkNull(GetProperty.getProperty("PRV_SECU_LVL"),"0"); 
	
	// Session Duplicate check 여부 
	public static final String SESSION_DUPLICATE = StringUtil.checkNull(GetProperty.getProperty("SESSION_DUPLICATE"),""); 
	
}
