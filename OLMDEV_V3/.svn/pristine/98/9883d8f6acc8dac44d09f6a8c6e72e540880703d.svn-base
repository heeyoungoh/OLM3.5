package xbolt.cmm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.org.json.JSONArray;

import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.JsonUtil;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.service.CommonService;

/**
 * 공통 서블릿 처리
 * @Class Name : CmmActionController.java
 * @Description : 공통화면을 제공한다.
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 * @see
 * 
 * Copyright (C) 2012 by SMARTFACTORY All right reserved.
 */

@Controller
@SuppressWarnings("unchecked")
public class CmmActionController extends XboltController{

	@Resource(name = "commonService")
	private CommonService commonService;

	private final Log _log = LogFactory.getLog(this.getClass());
	
	/**
	 * <input>등의 value을 반환한다.
	 * @param paging
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxPage.do")
	public String ajaxPage(HashMap cmmMap,ModelMap model) throws Exception {
		String SQL_CODE = SQL_COMMON_HEADER+getString(cmmMap.get("menuId"), "commonCode")+SQL_COMMON_TAIL;
		try {
			Map result = new HashMap();
			result.put(AJAX_MESSAGE,  commonService.selectToObject(SQL_CODE,cmmMap));
			model.put(AJAX_RESULTMAP, result);
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxPage::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}
		return nextUrl(AJAXPAGE);
	}

	/**
	 * <select>의 <option>을 반환한다.
	 * @param paging
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxCodeSelect.do")
	public String ajaxCodeSelect(HashMap cmmMap,ModelMap model) throws Exception {
				
		String sqlCommonHeader = SQL_COMMON_HEADER;
		String sqlCommonTail = SQL_COMMON_TAIL;
		String headerKey = StringUtil.checkNull(cmmMap.get("headerKey"));
		if(!headerKey.equals("") && !headerKey.equals("undefined")){
			sqlCommonHeader  = headerKey+".";
			sqlCommonTail = "";
		}
		String SQL_CODE = sqlCommonHeader+getString(cmmMap.get("menuId"), "commonCode")+sqlCommonTail;
		
		// System.out.println("SQL_CODE = "+SQL_CODE);
		
		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxCodeSelect::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}

		//System.out.println("AJAXPAGE_SELECTOPTION = "+AJAXPAGE_SELECTOPTION);
		
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	@RequestMapping(value="/ajaxCodeSelectJson.do", produces = "application/text; charset=utf-8")
	public void ajaxCodeSelectJson(HttpServletRequest request,  HashMap cmmMap,HttpServletResponse response) throws Exception{
		try{	
			String sqlCommonHeader = SQL_COMMON_HEADER;
			String sqlCommonTail = SQL_COMMON_TAIL;
			String headerKey = StringUtil.checkNull(cmmMap.get("headerKey"));
			if(!headerKey.equals("") && !headerKey.equals("undefined")){
				sqlCommonHeader  = headerKey+".";
				sqlCommonTail = "";
			}
			String SQL_CODE = sqlCommonHeader+getString(cmmMap.get("menuId"), "commonCode")+sqlCommonTail;
			
			JSONArray jsonData = new JSONArray(commonService.selectList(SQL_CODE,cmmMap));
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonData);
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/**
	 * <input>의 type radio를 반환한다.
	 * @param paging
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxCodeRadio.do")
	public String ajaxCodeRadio(HashMap cmmMap,ModelMap model) throws Exception {
				
		String sqlCommonHeader = SQL_COMMON_HEADER;
		String sqlCommonTail = SQL_COMMON_TAIL;

		String SQL_CODE = sqlCommonHeader+getString(cmmMap.get("menuId"), "commonCode")+sqlCommonTail;
		
		
		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxCodeSelect::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}

		return nextUrl(AJAXPAGE_RADIO);
	}

	/**
	 * 체크박스 목록을 가져오는 역활
	 *  - 통계현황
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxCheckbox.do")
	public String ajaxCheckbox(HashMap cmmMap,ModelMap model) throws Exception {
		String SQL_CODE = SQL_COMMON_HEADER+getString(cmmMap.get("menuId"), "commonCode")+SQL_COMMON_TAIL;
		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
			model.put("name", cmmMap.get("name"));
			model.put("checkYn", cmmMap.get("checkYn"));
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxCheckbox::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}

		return nextUrl(AJAXPAGE_CHECKBOX);
	}

	/**
	 * 업로드된 image file을 반환한다.
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxImgPage.do")
	public String ajaxImgPage(HashMap cmmMap,ModelMap model) throws Exception {
		//String SQL_CODE = "CommonFile.commFile_selectList";
		String SQL_CODE = "CommonFile.cmmFile_selectList";	//new mode

		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxImgPage::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}

		return nextUrl(AJAXPAGE_IMG);
	}

	/**
	 * 업로드된 image file을 반환한다.
	 *  - 삭제 기능이 포함된 화면
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxImgDeleteabledPage.do")
	public String ajaxImgDeleteabledPage(HashMap cmmMap,ModelMap model) throws Exception {
		//String SQL_CODE = "CommonFile.commFile_selectList";
		String SQL_CODE = "CommonFile.cmmFile_selectList";	//new mode

		String nextUrl = nextUrl(AJAXPAGE_IMGDELETE);
		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::ajaxImgDeleteabledPage::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}
		if( cmmMap.get("NOIMG") != null && !"".equals(cmmMap.get("NOIMG")) &&"Y".equals(cmmMap.get("NOIMG").toString())){
			nextUrl = nextUrl(AJAXPAGE_NOIMGDELETE);
		}
		if( cmmMap.get("NODEL") != null && !"".equals(cmmMap.get("NODEL")) &&"Y".equals(cmmMap.get("NODEL").toString())){
			List li = new ArrayList();
			li.add(0, cmmMap.get("NODEL"));
			model.put("RST", li);
		}

		return nextUrl;
	}

	/**
	 * 업로드된 파일목록을 반환한다.
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxFilePage.do")
	public String ajaxFilePage(HashMap cmmMap,ModelMap model) throws Exception {
		//String SQL_CODE = "CommonFile.commFile_selectList";
		//String SQL_CODE = "CommonFile.cmmFile_selectList";	//new mode

		try {
			//model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			System.out.println(e);
			throw e;
		}

		return nextUrl(AJAXPAGE_FILE);
	}

	/**
	 * DHTMLX 그리드의 목록을 조회한다.
	 * - sqlId
	 * - cols
	 */
	@RequestMapping(value="/jsonDhtmlxList.do")
	public void doJsonDhtmlxList(HashMap cmmMap
			,HttpServletResponse response) throws Exception{
		String SQL_CODE = getString(cmmMap.get("menuId"), "commonCode");	// menuId가 없은 경우가 가끔 있음

		List <Map>result = commonService.selectList(SQL_CODE+SQL_GRID_LIST, cmmMap);
		String [] cols = ((String)cmmMap.get("cols")).split("[|]");
		
		//System.out.println("cols = "+cmmMap.get("cols")+" // size = " +cols.length);
		int pageNum=NumberUtil.getIntValue(cmmMap.get("pageNum"),1);
		int totalPage = 0;
		try {
			if(cmmMap.get("pagingArea")!=null) {
				totalPage = NumberUtil.getIntValue(commonService.selectString(SQL_CODE+"_ListCount", cmmMap));
			}
		}
		catch(Exception e) {}

		JsonUtil.returnGridJson(result, cols, totalPage, pageNum, response, (String)cmmMap.get("contextPath"));
	}
	
	/**
	 * DHTMLX Chart을 조회한다.
	 * - sqlId
	 * - cols
	 */
	@RequestMapping(value="/jsonDhtmlxChart.do")
	public void jsonDhtmlxChart(HashMap cmmMap
			,HttpServletResponse response) throws Exception{
		String SQL_CODE = getString(cmmMap.get("chartId"), "chart");	// menuId가 없은 경우가 가끔 있음

		List <Map>result = commonService.selectList(SQL_CODE+SQL_CHART, cmmMap);

		String [] cols = ((String)cmmMap.get("cols")).split("[|]");
		JsonUtil.returnChartJson(result, cols, response, (String)cmmMap.get("contextPath"));
	}
	

	/**
	 * DHTMLX Tree의 목록을 조회한다.
	 * - sqlId
	 * - cols
	 */
	@RequestMapping(value="/jsonDhtmlxTreeList.do")
	public void doJsonDhtmlxTreeList(HashMap cmmMap, HttpServletResponse response, HttpServletRequest request) throws Exception{
		String SQL_CODE=StringUtil.checkNull(commonService.selectString("menu_SQL.getMenuTreeSqlName", cmmMap) ,"commonCode");

		String tFilterCode = StringUtil.checkNull(cmmMap.get("tFilterCode"));
		
		if(!"".equals(tFilterCode)) {
			SQL_CODE=StringUtil.checkNull(commonService.selectString("menu_SQL.getSqlNameForTfilterCode", cmmMap));
		}

		/* 해당 프로젝트 ID 설정 */
		Map projectInfoMap = new HashMap();
		String sessionTmplCode = StringUtil.checkNull(cmmMap.get("sessionTemplCode"));
		String projectID = "";
		if(sessionTmplCode.equals("TMPL003")){
			projectID = StringUtil.checkNull(cmmMap.get("projectID"));
			cmmMap.put("projectID", projectID);
		}else{
			projectInfoMap = commonService.select("main_SQL.getPjtInfoFromTEMPL", cmmMap);
			cmmMap.put("projectID", projectInfoMap.get("ProjectID"));
		}
		
		List <Map>result = commonService.selectList("menu_SQL." + SQL_CODE, cmmMap);
		
		String [] cols = ((String)cmmMap.get("cols")).split("[|]");

		JsonUtil.returnTreeJson(result, cols, response, (String)cmmMap.get("contextPath"));
	}
	
	/**
	 * Dhtmlx의 콤보박스를 완성한다
	 * @param paging
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxDhtmlxCombo.do")
	public String ajaxDhtmlxCombo(HashMap cmmMap,ModelMap model) throws Exception {
		String SQL_CODE = SQL_COMMON_HEADER+getString(cmmMap.get("menuId"), "commonCode")+SQL_COMMON_TAIL;
		try {
			model.put(AJAX_RESULTMAP, commonService.selectList(SQL_CODE,cmmMap));
		}
		catch (Exception e) {
			System.out.println(e);
			throw e;
		}

		return nextUrl(AJAXPAGE_COMBO);
	}


	
	/**
	 * Language Setting
	 * @param cmmMap.nextPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/languageSetting.do")
	public String languageSetting(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		
		Map target = new HashMap();
		Map setMap = new HashMap();
		Map memberMap = new HashMap();
		
		try{ 			
			String langType = StringUtil.checkNull(cmmMap.get("LANGUAGE").toString());
			String langName = StringUtil.checkNull(cmmMap.get("NAME").toString());
			
			HttpSession session = request.getSession(true);
			Map loginInfo = (Map)session.getAttribute("loginInfo");
			loginInfo.put("curr_lang_type", langType);
			loginInfo.put("curr_lang_nm", langName);
			setMap.put("LanguageID", langType);
			loginInfo.put("curr_lang_code", commonService.selectString("common_SQL.getLanguageCode", setMap));
			setMap.put("languageID", langType);
			Map languageMap = commonService.select("common_SQL.getLanguageInfo",setMap);
			loginInfo.put("DEF_FONT", languageMap.get("FontFamily"));
			loginInfo.put("DEF_FONT_SIZE", languageMap.get("FontSize"));
			loginInfo.put("DEF_FONT_STYLE", languageMap.get("FontStyle"));
			loginInfo.put("DEF_FONT_COLOR", languageMap.get("FontColor"));
			setMap.put("memberID", loginInfo.get("sessionUserId"));
			memberMap = commonService.select("user_SQL.getMemberInfo", setMap);

			loginInfo.put("TEAM_NAME", StringUtil.checkNull(memberMap.get("TeamName"),loginInfo.get("sessionTeamName").toString()));
			
			session.setAttribute("loginInfo", loginInfo);
			
			//Language 선언  -> Session에 저장
			session.removeAttribute("langType");
			session.setAttribute("langType", langType);
			
			target.put(AJAX_SCRIPT, "parent.doReturnLanguage();parent.$('#isSubmit').remove()");
		} catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::languageSetting::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}		
		
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	/**
	 * Template Setting
	 * @param cmmMap.nextPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/templateSetting.do")
	public String templateSetting(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		Map target = new HashMap();
		try{ 			
			String templCode = StringUtil.checkNull(cmmMap.get("templCode").toString());
			
			HttpSession session = request.getSession(true);			
			//Template 선언  -> Session에 저장
			Map loginInfo = (Map)session.getAttribute("loginInfo");
			loginInfo.put("templ_code", templCode);
			session.setAttribute("loginInfo", loginInfo);
			
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
		} catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::templateSetting::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}		
		
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}	
	
	/**
	 * Campany Setting
	 * @param cmmMap.nextPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/companySetting.do")
	public String companySetting(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		Map target = new HashMap();
		try{ 			
			String cmpnyCode = StringUtil.checkNull(cmmMap.get("cmpnyCode").toString());
			
			HttpSession session = request.getSession(true);			
			//선택한 회사 선언  -> Session에 저장
			Map loginInfo = (Map)session.getAttribute("loginInfo");
			loginInfo.put("sel_cmpny_code", cmpnyCode);
			session.setAttribute("loginInfo", loginInfo);
			
			Map setData = new HashMap();
			setData.put("templCode", "COMPANY");
			setData.put("sessionAuthLev", "1");
			setData.put("sessionCurrLangType", loginInfo.get("sessionCurrLangType"));
			setData.put("cmpnyYN", "Y");
			Map templInfo = commonService.select("menu_SQL.mainTempl_select", setData);
			
			String layerType = StringUtil.checkNull(templInfo.get("TemplCode"));
			String layerText = StringUtil.checkNull(templInfo.get("TemplText"));
			String mainUrl = StringUtil.checkNull(templInfo.get("MainURL"));
			String mainScnText = StringUtil.checkNull(templInfo.get("MainScnText"));
			String mainUrlFilter = StringUtil.checkNull(templInfo.get("URLFilter"));
			String tmplFilter = StringUtil.checkNull(templInfo.get("TmplFilter"));
			String tmplType = StringUtil.checkNull(templInfo.get("TmplType"));
			
			target.put(AJAX_SCRIPT, "parent.changeTempl('"+layerType+"','"+layerText+"','"+mainUrl+"','"+mainScnText+"','"+mainUrlFilter+"','"+tmplFilter+"','"+tmplType+"');$('#isSubmit').remove()");
		} catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::companySetting::Error::"+e.toString().replaceAll("\r|\n", ""));}
			throw e;
		}		
		
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}		
	/**
	 * Session에 Parameter Setting
	 * @param cmmMap.nextPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setSessionParameter.do")
	public String setSessionParameter(ModelMap model, HashMap cmmMap, HttpServletRequest request) throws Exception {
		
		try{ 			
			String subItems = cmmMap.get("subItems").toString();
			Map searchInfo = (Map) request.getSession(false).getAttribute("searchInfo");
			if(searchInfo!=null) {
				searchInfo.put("sessionParamSubItems", subItems);
			} else {
				HttpSession session = request.getSession(true);
				searchInfo = new HashMap();
				searchInfo.put("sessionParamSubItems", subItems);	
				
				session.setAttribute("searchInfo", searchInfo);
			}			
			
			String ip = request.getHeader("X-FORWARDED-FOR");
	        if (ip == null)
	            ip = request.getRemoteAddr();
	        
	        cmmMap.put("IpAddress",ip);
	        
			//Visit Log
			String itemId = StringUtil.checkNull(cmmMap.get("ItemId"), "");
			String visitLogYN = StringUtil.checkNull(cmmMap.get("visitLogYN"), "");
			if(!"".equals(itemId) && !visitLogYN.equals("N")){
				if( NumberUtil.isNumeric(itemId) )
					commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
			}
			

			//파라미터 선언
			if(_log.isInfoEnabled()){
				_log.info("searchInfo = " + searchInfo.toString().replaceAll("\r|\n", ""));
			}			
			
		} catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::setSessionParameter::Error::"+e.toString().replaceAll("\r|\n", ""));}
			//throw e;
		}	

		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/getTypeCodeOption.do")
	public String getTypeCodeOption(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("languageID", request.getParameter("languageID"));
		model.put(AJAX_RESULTMAP, commonService.selectList("item_SQL.getSearchItemTypeCode",setMap));
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}

	@RequestMapping(value="/getClassCodeOption.do")
	public String getClassCodeOption(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("languageID", request.getParameter("languageID"));
		setMap.put("option", request.getParameter("option"));
		if (!StringUtil.checkNull(request.getParameter("hasDim")).isEmpty()) {
			setMap.put("HasDimension", request.getParameter("hasDim"));
		}
		model.put(AJAX_RESULTMAP, commonService.selectList("item_SQL.getClassCodeOption",setMap));
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	@RequestMapping(value="/getClassCodeOptionForCsr.do")
	public String getClassCodeOptionForCsr(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("languageID", request.getParameter("languageID"));
		setMap.put("option", request.getParameter("option"));
		setMap.put("ChangeMgt", "1");
		model.put(AJAX_RESULTMAP, commonService.selectList("item_SQL.getClassCodeOption",setMap));
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	@RequestMapping(value="/getSelectOption.do")
	public String getSelectOption(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("languageID", request.getParameter("languageID"));
		setMap.put("s_itemID", request.getParameter("s_itemID"));
		
		/* TODO : 언어 설정 */
		// TB_ATTR_TYPE.IsComLang = 1 이면, 로그인된 언어와 상관없이 모두 
		// TB_LANGUAGE.IsDefault = 1 인 언어 코드의 ITEM_ATTR 을 insert 또는 update 함
		// get IsComLang
		setMap.put("AttrTypeCode", request.getParameter("s_itemID"));
		String isComLang = commonService.selectString("attr_SQL.getItemAttrIsComLang", setMap);
		
		if (!isComLang.isEmpty()) {
			// get TB_LANGUAGE.IsDefault = 1 인 언어 코드
			String defaultLang = commonService.selectString("item_SQL.getDefaultLang", setMap);
			// 언어 코드를 TB_LANGUAGE.IsDefault = 1 인 언어 코드로 재설정
			setMap.put("languageID", defaultLang);
		}
		
		setMap.put("itemID", request.getParameter("itemID"));
		String refLovCode = commonService.selectString("attr_SQL.getRefLovCode", setMap);
		setMap.put("refLovCode", refLovCode);		
		
		model.put(AJAX_RESULTMAP, commonService.selectList(request.getParameter("sqlID") ,setMap));
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	//home-검색-AttrSelect
	@RequestMapping(value="/getSearchSelectOption.do")
	public String getSearchSelectOption(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("languageID", request.getParameter("languageID"));
		setMap.put("s_itemID", request.getParameter("s_itemID"));
		//옵션 추가
		setMap.put("s_itemID2", request.getParameter("s_itemID2"));
		setMap.put("itemTypeCodes", request.getParameter("itemTypeCodes"));
		setMap.put("teamType", request.getParameter("teamType"));
		setMap.put("parentIDs", request.getParameter("parentIDs"));
		
		List getList  = new ArrayList();
		getList = commonService.selectList(request.getParameter("sqlID") ,setMap);
		
		model.put(AJAX_RESULTMAP, getList);
		
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	/**
	 * 화면에서 선택된 속성의 DataType이 Lov일때, Lov selectList를 화면에 표시
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAttrLovSelectOption.do")
	public String getAttrLovSelectOption(HttpServletRequest request, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		setMap.put("LanguageID", request.getParameter("languageID"));
		setMap.put("AttrTypeCode", request.getParameter("attrCode"));
		
		List getList  = new ArrayList();
		getList = commonService.selectList("search_SQL.getAttrLovList" ,setMap);
		
		model.put(AJAX_RESULTMAP, getList);
		
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	@RequestMapping(value="/getAttrLov.do")
	public String getAttrLov(HttpServletRequest request, ModelMap model) throws Exception {
		Map resultMap = new HashMap();
		Map setMap = new HashMap();
		String attrCode = StringUtil.checkNull(request.getParameter("attrCode"));
		
		setMap.put("AttrTypeCode", attrCode);
		Map attrTypeMap = commonService.select("search_SQL.getAttrDataType", setMap);
		String dataType = String.valueOf(attrTypeMap.get("DataType"));
		String isComLang = String.valueOf(attrTypeMap.get("IsComLang"));
		
		resultMap.put(AJAX_SCRIPT, "changeAttrCode2('"+attrCode+"', '"+dataType+"', '"+isComLang+"')");
		model.addAttribute(AJAX_RESULTMAP, resultMap);
		return nextUrl(AJAXPAGE);
	}
	
	
	@RequestMapping(value="/setVisitLog.do")
	public String setVisitLog(HashMap cmmMap,ModelMap model) throws Exception {
		try{ 			
			
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				 String ip = request.getHeader("X-FORWARDED-FOR");
		        if (ip == null)
		            ip = request.getRemoteAddr();
		        
		        cmmMap.put("IpAddress",ip);
		        
				commonService.insert("gloval_SQL.insertVisitLog", cmmMap);
		} catch (Exception e) {
			if(_log.isInfoEnabled()){_log.info("CmmActionController::setVisitLog::Error::"+e.toString().replaceAll("\r|\n", ""));}
			//throw e;
		}	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/getDictionarySelectOption.do")
	public String getDictionarySelectOption(HttpServletRequest request,HashMap cmmMap , ModelMap model) throws Exception {
		Map setMap = new HashMap();
		String sqlID = StringUtil.checkNull(request.getParameter("sqlID"));
		setMap.put("languageID", cmmMap.get("sessionCurrLangType"));
		setMap.put("category", request.getParameter("category"));
		
		List getList = commonService.selectList( sqlID,setMap);
		
		model.put(AJAX_RESULTMAP, getList);
		
		return nextUrl(AJAXPAGE_SELECTOPTION);
	}
	
	@RequestMapping(value = "/jsonDhtmlxListV7.do")
	public void jsonDhtmlxListV7(HashMap cmmMap, HttpServletResponse response)  throws Exception {
		try {			
			String SQL_CODE = getString(cmmMap.get("sqlID"), "commonCode");	
					
			String tFilterCode = StringUtil.checkNull(cmmMap.get("tFilterCode"));
			String sqlGridList = StringUtil.checkNull(cmmMap.get("sqlGridList"));
			
			if(!"".equals(tFilterCode)) {
				SQL_CODE=StringUtil.checkNull(commonService.selectString("menu_SQL.getSqlNameForTfilterCode", cmmMap));
			}
			
			String sqlCode = "";
			if(sqlGridList.equals("N")) sqlCode = SQL_CODE; else sqlCode = SQL_CODE+SQL_GRID_LIST;
			List <Map>result = commonService.selectList(sqlCode, cmmMap);
			
			JSONArray resultJosnList = new JSONArray(result);
			sendToJsonV7(StringUtil.checkNull(resultJosnList), response);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void sendToJsonV7(String jObj, HttpServletResponse res) {
		try {
			res.setHeader("Cache-Control", "no-cache");
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			if(!jObj.equals("{rows: [ ]}")){
				res.getWriter().print(jObj);
			}
			else {
				PrintWriter pw = res.getWriter();
				pw.write("데이터가 존재하지 않습니다.");
			}			
		} catch (IOException e) {
			MessageHandler.getMessage("json.send.message");
			e.printStackTrace();
		}
	}
}
