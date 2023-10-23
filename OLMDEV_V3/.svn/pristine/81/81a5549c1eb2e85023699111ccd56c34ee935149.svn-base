package xbolt.link.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;

@Controller
public class ExtLinkActionController extends XboltController{
	private final Log _log = LogFactory.getLog(this.getClass());
	
	
	@Autowired
    @Qualifier("commonService")
    private CommonService commonService;
	
	@Resource(name = "commonOraService")
	private CommonService commonOraService;
	
	@RequestMapping(value="/runSAPTransaction.do")
	public String runSAPTransaction(Map cmmMap,ModelMap model, HttpServletRequest request) throws Exception {
		Map setMap = new HashMap();
		String url = "/custom/sap/runSAPTransaction";
		String itemID = StringUtil.checkNull(cmmMap.get("itemID"));
		String attrTypeCode = "AT00039";
		
		
		String t_code = "";
		String languageID = GlobalVal.DEFAULT_LANGUAGE;
		
		setMap.put("attrTypeCode", attrTypeCode);
		setMap.put("itemID", itemID);
		
		setMap.put("languageID", languageID);
		t_code = commonService.selectString("link_SQL.getAttrValue", setMap);
		
	
		setMap.put("attrTypeCode", attrTypeCode);
		setMap.put("itemID", itemID);
		setMap.put("languageID", languageID);
		t_code = commonService.selectString("link_SQL.getAttrValue", setMap);
		
		setMap.put("s_itemID", itemID);
		Map tempMap = commonService.select("link_SQL.getAttrVarfilter", setMap);
		String SAPshortCutPath = StringUtil.checkNull(tempMap.get("VarFilter"));
		if(t_code == null || t_code.equals("")) {
			setMap.put("ITEM_ID", itemID);
			Map itemInfo = commonService.select("item_SQL.selectItemInfo", setMap);
			t_code = StringUtil.checkNull(itemInfo.get("Identifier"));
		}
		String sapUrl = SAPshortCutPath + t_code;
		
		model.put("sapUrl",sapUrl);
		return nextUrl(url);
	
	}	
	
	@RequestMapping (value="/getAttrLinkList.do")
	public String getAttrLinkList(Map cmmMap,ModelMap model, HttpServletResponse response) throws Exception {
		Map setMap = new HashMap();
		try{
			String itemID = StringUtil.checkNull(cmmMap.get("itemID"),"");
			setMap.put("itemID", StringUtil.checkNull(cmmMap.get("itemID"),""));
			setMap.put("languageID", StringUtil.checkNull(cmmMap.get("sessionCurrLangType"),""));
			setMap.put("itemClassCode", StringUtil.checkNull(cmmMap.get("itemClassCode"),""));
			List resultData = commonService.selectList("link_SQL.getLinkListFromAttAlloc", setMap);
			
			Map linkMap = new HashMap();
			String url = "";
			String link = "";
			String lovCode = "";
			String attrTypeCode = "";
			for(int i=0; i < resultData.size(); i++){
				linkMap = (HashMap)resultData.get(i);
				if(i == 0){
					url = StringUtil.checkNull(linkMap.get("URL"),"");
					link = StringUtil.checkNull(linkMap.get("LinkName"),"");
					lovCode = StringUtil.checkNull(linkMap.get("LovCode"),"");
					attrTypeCode = StringUtil.checkNull(linkMap.get("AttrTypeCode"),"");
				}else{
					url += ","+StringUtil.checkNull(linkMap.get("URL"),"");
					link += ","+StringUtil.checkNull(linkMap.get("LinkName"),"");
					lovCode = ","+StringUtil.checkNull(linkMap.get("LovCode"),"");
					attrTypeCode = ","+StringUtil.checkNull(linkMap.get("AttrTypeCode"),"");
				}
			}						
		
			response.setCharacterEncoding("UTF-8"); // 한글깨짐현상 방지
			PrintWriter out = response.getWriter();
		    out.append(link);
		    out.append("/");
		    out.append(url);
		    out.append("/");
		    out.append(lovCode);
		    out.append("/");
		    out.append(attrTypeCode);
		    out.append("/");
		    out.append(StringUtil.checkNull(resultData.size()));
		    out.flush();
				
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/runSysLink.do")
	public String runSysLink(Map commandMap,ModelMap model, HttpServletRequest request) throws Exception {
		Map setMap = new HashMap();
		String itemID = StringUtil.checkNull(commandMap.get("itemID"));
		// String attrTypeCode = "AT00014";
		String attrTypeCode = StringUtil.checkNull(commandMap.get("attrTypeCode"),"AT00014");
		
		setMap.put("attrTypeCode", attrTypeCode);
		setMap.put("itemID", itemID);
		setMap.put("languageID", commandMap.get("sessionDefLanguageId"));			
		String LinkParameter = commonService.selectString("link_SQL.getAttrValue", setMap);
		String url = commonService.selectString("link_SQL.getAttrUrl", setMap);
		
		if(attrTypeCode.equals("AT00014")) {
			url = StringUtil.checkNull(url) + StringUtil.checkNull(LinkParameter);
		} else{
			url = StringUtil.checkNull(url);
		}
		return redirect(url);	
	}
	
	@RequestMapping(value="/extWebServiceMgt.do")
	public String extWebServiceMgt(HttpServletRequest request, Map cmmMap,ModelMap model) throws Exception {
		String url = "/itm/sub/extWebServiceMgt";
		String languageID = StringUtil.checkNull(cmmMap.get("languageID"));
		String s_itemID = StringUtil.checkNull(cmmMap.get("s_itemID"));
		String extUrl =  StringUtil.checkNull(cmmMap.get("extUrl"));
				
		cmmMap.put("arcCode", "AR000000");
		cmmMap.put("menuID", "MN015");
		cmmMap.put("languageID", languageID);
		
		Map ewInfo = commonService.select("link_SQL.getExtWebServiceInfo", cmmMap);
		cmmMap.put("attrTypeCode", "AT00014");
		cmmMap.put("s_itemID", s_itemID);

		Map tempMap = commonService.select("link_SQL.getAttrVarfilter", cmmMap);
		
		if(tempMap != null && !tempMap.isEmpty()) {
			extUrl = "?" + StringUtil.checkNull(tempMap.get("VarFilter")) + "=" + StringUtil.checkNull(tempMap.get("PlainText"));
		}
		model.put("menu", getLabel(request, commonService)); /* Label Setting */
		model.put("ewInfo",ewInfo);
		model.put("extUrl",extUrl);

		return nextUrl(url);
	}
}