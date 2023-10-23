package xbolt.custom.sk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.service.CommonService;

@Controller
public class MainHomSingleActionController extends XboltController{
	
	
	@Autowired
    @Qualifier("commonService")
    private CommonService commonService;
	
	@RequestMapping(value="/mainHomSingle.do")
	public String mainHomSingle(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		
		// 공지사항 조회
		Map<String, String> paramData = new HashMap<String, String>();
		
		List<Object> noticeList = new ArrayList<Object>();
		noticeList = commonService.selectList("sk_cmm_SQL.selectNoticeList", paramData);
		model.put("noticeList", noticeList);
		
		model.put("menu", getLabel(request, commonService));	
		
		return nextUrl("/custom/sk/mainHomSingle");
	}
	
	@RequestMapping(value="/sessionTimeout.do")
	public String sessionTimeout(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		
		return nextUrl("/cmm/err/sessionTimeout");
	}
	
	@RequestMapping(value="/mainHomSingleLogout.do")
	public ModelAndView mainHomSingleLogout(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		//HttpSession session = request.getSession(true);
		//session.invalidate();
		
		String url = request.getContextPath() +"/index.do";
		ModelAndView mav = new ModelAndView();
		RedirectView rv = new RedirectView();
		rv.setUrl(url);
		mav.setView(rv);
		
		return mav;
		
	}
	
	@RequestMapping(value="/changeLanguage.do")
	public ModelAndView changeLanguage(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		Map setMap = new HashMap();
		
		try{
			
			String langType = StringUtil.checkNull(request.getParameter("LANGUAGE").toString());
			String langName = StringUtil.checkNull(request.getParameter("NAME").toString());
			
			HttpSession session = request.getSession(true);
			Map loginInfo = (Map)session.getAttribute("loginInfo");
			loginInfo.put("curr_lang_type", langType);
			loginInfo.put("curr_lang_nm", langName);
			setMap.put("LanguageID", langType);
			loginInfo.put("curr_lang_code", commonService.selectString("common_SQL.getLanguageCode", setMap));
			session.setAttribute("loginInfo", loginInfo);
			
			
			String url = request.getContextPath() +"/mainHomSingle.do";
			ModelAndView mav = new ModelAndView();
			RedirectView rv = new RedirectView();
			rv.setUrl(url);
			mav.setView(rv);
			
			return mav;
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}		
	}
	
	@RequestMapping(value="/mainHomSingleInner.do")
	public String mainHomSingleInner(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		String jspNM = "";
		
		try{
			Map<String, String> paramData = new HashMap<String, String>();
			String gubun    = StringUtil.checkNull(request.getParameter("gubun"),"");
			
			if("".equals(gubun) || "DO".equals(gubun)){
				// 자료실은 별도 페이지
				jspNM="/custom/sk/mainHomSingleInnerDocu";
				model.put("gubun", gubun);
				
			}else {
				// Process. Configuration. CBO Master. IF Maaster. 변경관리.
				jspNM="/custom/sk/mainHomSingleInner";
				
				paramData.put("LanguageID", String.valueOf(commandMap.get("sessionCurrLangType")));
				makeParamData(paramData, gubun);
				model.put("gubun", gubun);
				
				// Configuration
				List<Object> confInfoList = new ArrayList<Object>();
				confInfoList = commonService.selectList("sk_cmm_SQL.selectConfigurationList", paramData);
				model.put("confInfoList", confInfoList);
				
				
				// CBO, IF Master
				List<Object> subSystemList      = new ArrayList<Object>();
				List<Object> subIFList          = new ArrayList<Object>();
				List<Object> subCBOList         = new ArrayList<Object>();
				HashMap<String, Object> tempMap = null; 
				
				List<Object> orgList = new ArrayList<Object>();
				orgList = commonService.selectList("sk_cmm_SQL.selectSubSystemList", paramData);
				for(int ii=0; ii < orgList.size(); ii++){
					tempMap = (HashMap<String, Object> )orgList.get(ii);
					
					paramData.put("ItemIDIF", tempMap.get("TREE_ID")+""); 
					subIFList  = commonService.selectList("sk_cmm_SQL.selectIFMasterList",  paramData);
					subCBOList = commonService.selectList("sk_cmm_SQL.selectCBOMasterList", paramData);
					tempMap.put("subIFList",  subIFList);
					tempMap.put("subCBOList", subCBOList);
					subSystemList.add(tempMap);
				}
				model.put("subSystemList",  subSystemList);
				
				
				// Process
				String sqlID = "";
				if("FI".equals(gubun)){
					sqlID = "sk_cmm_SQL.selectProcessTotalListTR";
				} else {
					sqlID = "sk_cmm_SQL.selectProcessTotalList";
					paramData.put("Identifier", gubun);
				}
				paramData.put("ClassCode", "CL01002");
				List<Object> processLvl1List         = commonService.selectList(sqlID, paramData);
				paramData.put("ClassCode", "CL01004");
				List<Object> processLvl2List         = commonService.selectList(sqlID, paramData);
				paramData.put("ClassCode", "CL01005");
				List<Object> processLvl3List         = commonService.selectList(sqlID, paramData);				
				
				String parentItemID = "";
				String itemID       = "";
				HashMap<String, Object> tempAMap = null; 
				HashMap<String, Object> tempBMap = null; 
				List<Object> subProcessList      = new ArrayList<Object>();
				List<Object> tempList            = new ArrayList<Object>();
				for(int ii=0; ii < processLvl2List.size(); ii++){
					tempAMap = (HashMap<String, Object> )processLvl3List.get(ii);
					itemID   = tempAMap.get("TREE_ID")+"";
					for(int jj=0; jj < processLvl3List.size(); jj++){
						tempBMap     = (HashMap<String, Object> )processLvl3List.get(jj);
						parentItemID = tempBMap.get("PARENT_ID")+"";
						if(itemID.equals(parentItemID)){
							subProcessList.add(tempBMap);
						}
					}
					tempAMap.put("Lvl3ProcList", subProcessList);
					tempList.add(tempAMap);
					subProcessList      = new ArrayList<Object>();
				} 
				processLvl3List = tempList;
				tempList        = new ArrayList<Object>();
				for(int ii=0; ii < processLvl2List.size(); ii++){
					tempAMap = (HashMap<String, Object> )processLvl2List.get(ii);
					itemID   = tempAMap.get("TREE_ID")+"";
					for(int jj=0; jj < processLvl3List.size(); jj++){
						tempBMap     = (HashMap<String, Object> )processLvl3List.get(jj);
						parentItemID = tempBMap.get("PARENT_ID")+"";
						if(itemID.equals(parentItemID)){
							subProcessList.add(tempBMap);
						}
					}
					tempAMap.put("Lvl3ProcList", subProcessList);
					tempList.add(tempAMap);
					subProcessList = new ArrayList<Object>();
				}
				processLvl2List = tempList;
				tempList        = new ArrayList<Object>();
				for(int ii=0; ii < processLvl1List.size(); ii++){
					tempAMap = (HashMap<String, Object> )processLvl1List.get(ii);
					itemID   = tempAMap.get("TREE_ID")+"";
					for(int jj=0; jj < processLvl2List.size(); jj++){
						tempBMap     = (HashMap<String, Object> )processLvl2List.get(jj);
						parentItemID = tempBMap.get("PARENT_ID")+"";
						if(itemID.equals(parentItemID)){
							subProcessList.add(tempBMap);
						}
					}
					tempAMap.put("Lvl2ProcList", subProcessList);
					tempList.add(tempAMap);
					subProcessList      = new ArrayList<Object>();
				}
				processLvl1List = tempList;
				tempList        = new ArrayList<Object>();
				
				model.put("processLvl1List",  processLvl1List);
				
				/**
				// Show Process Data
				String temp1NM = "";
				String temp2NM = "";
				String temp3NM = "";
				String temp4NM = "";
				HashMap<String, Object> tempHMap = null;
				HashMap<String, Object> tempIMap = null;
				HashMap<String, Object> tempJMap = null;
				HashMap<String, Object> tempKMap = null;
				List<Object> tempHList            = new ArrayList<Object>();
				List<Object> tempIList            = new ArrayList<Object>();
				List<Object> tempJList            = new ArrayList<Object>();
				List<Object> tempKList            = new ArrayList<Object>();
				
				for(int kk=0; kk < processLvl1List.size(); kk++){
					tempKMap = (HashMap<String, Object> )processLvl1List.get(kk);
					temp1NM = (String) tempKMap.get("TREE_NM");
					System.out.println(temp1NM);
					tempKList = (List<Object>) tempKMap.get("Lvl2ProcList");
					for(int hh=0; hh < tempKList.size(); hh++){
						tempHMap = (HashMap<String, Object> )tempKList.get(hh);
						temp2NM = (String) tempHMap.get("TREE_NM");
						System.out.println("-- "+temp2NM);
						tempHList = (List<Object>) tempHMap.get("Lvl3ProcList");
						for(int ii=0; ii < tempHList.size(); ii++){
							tempIMap = (HashMap<String, Object> )tempHList.get(ii);
							temp3NM = (String) tempIMap.get("TREE_NM");
							System.out.println("---- "+temp3NM);
							tempIList = (List<Object>) tempIMap.get("Lvl4ProcList");
							for(int jj=0; jj < tempIList.size(); jj++){
								tempJMap = (HashMap<String, Object> )tempIList.get(jj);
								temp4NM = (String) tempJMap.get("TREE_NM");
								System.out.println("------ "+temp4NM);
							}
						}
						
					}
				}
				**/
			}
			
			model.put("menu", getLabel(request, commonService));

			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return nextUrl(jspNM);
	}
	
	@RequestMapping("/mainTreeInfo.do")
	public ModelAndView mainTreeInfo(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		try{
			Map<String, String> paramData = new HashMap<String, String>();
			
			String gubun    = StringUtil.checkNull(request.getParameter("gubun"));
			
			paramData.put("LanguageID", String.valueOf(commandMap.get("sessionCurrLangType")));
			makeParamData(paramData, gubun);
			
			List<Object> confInfoList = new ArrayList<Object>();
			confInfoList = commonService.selectList("sk_cmm_SQL.selectConfigurationList", paramData);
			
			model.put("confInfoList", confInfoList);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

		ModelAndView modelAndView = new ModelAndView("jsonView", model);
		return modelAndView;
		
	}
	
	private void makeParamData(Map<String, String> paramData, String gubun){
		paramData.put("gubun",    gubun);
		
		String confID     = "";
		String ifMasterID = "";
		
		if("MM".equals(gubun)){
			confID     = "'CFG01'";
			ifMasterID = "'114163'";
		} else if ("SD".equals(gubun)){
			confID     = "'CFG02'";
			ifMasterID = "'114167'";
		} else if ("PM".equals(gubun)){
			confID     = "'CFG03'";
			ifMasterID = "'114165'";
		} else if ("FI".equals(gubun)){
			confID     = "'CFG04','CFG05','CFG06','CFG07'";
			ifMasterID = "'114155','114157','114161','114169'";
			
		} else if ("HR".equals(gubun)){
			confID     = "'CFG08'";
			ifMasterID = "'114159'";
		}
		paramData.put("confID",     confID);
		paramData.put("ifMasterID", ifMasterID);
	}
	
	
	@RequestMapping("/insertVisitLog.do")
	public ModelAndView insertVisitLog(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		
		try{
			Map<String, String> paramMap = new HashMap<String, String>();
			
			String tabID    = StringUtil.checkNull(request.getParameter("tabID"));
			String tabLabel = StringUtil.checkNull(request.getParameter("tabLabel"));
			String tabUrl = StringUtil.checkNull(request.getParameter("tabUrl"));
			
			System.out.println("tabID : "+tabID);
			System.out.println("tabLabel : "+tabLabel);
			System.out.println("tabUrl : "+tabUrl);
			
			
			// Process, Configuration, CBO Master, IF Master
			paramMap.put("sessionUserId", String.valueOf(commandMap.get("sessionUserId")));
			paramMap.put("sessionTeamId", String.valueOf(commandMap.get("sessionTeamId")));
			if(tabID.indexOf("process_tab") == 0 ){
				String[] tabIDArr = tabID.split("_");
				paramMap.put("ItemId", tabIDArr[2]);
				String arcCode2 = commonService.selectString("sk_cmm_SQL.selectUpMoudle", paramMap);
				
				paramMap.put("ArcCode1", "AR010000");
				paramMap.put("ArcCode2", arcCode2);
				commonService.insert("gloval_SQL.insertVisitLog", paramMap);
				
			}
			if(tabID.indexOf("configuration_tab") == 0 || tabID.indexOf("cboMaster_tab") == 0 || tabID.indexOf("ifMaster_tab") == 0){
				String[] tabIDArr = tabID.split("_");
				paramMap.put("ItemId", tabIDArr[2]);
				
				paramMap.put("ArcCode1", "AR020100");
				paramMap.put("ArcCode2", getProgramSubMenu(tabLabel));
				commonService.insert("gloval_SQL.insertVisitLog", paramMap);
				
			}
			
			// Change Management
			if(tabID.indexOf("change_tab") == 0 ){
				paramMap.put("ArcCode1", "AR050200");
				commonService.insert("gloval_SQL.insertVisitLog", paramMap);
			}
			
			// Documents
			if(tabID.indexOf("output_tab") == 0 ){
				paramMap.put("ArcCode1", "AR010500");
				commonService.insert("gloval_SQL.insertVisitLog", paramMap);
			}
			
			//HashMap<String, String> cntMap = (HashMap<String, String>) commonService.select("cts_SQL.getCTSCntFirst", paramMap);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

		ModelAndView modelAndView = new ModelAndView("jsonView", model);
		return modelAndView;
		
	}
	
	private String getProgramSubMenu(String gubun){
		String result = "";
		
		if("Configuration".equals(gubun)){
			result = "AR020110";
		} else if("CBO Master".equals(gubun)){
			result = "AR020120";
		} else if("IF Master".equals(gubun)){
			result = "AR020130";
		}
		
		return result;
	}
}