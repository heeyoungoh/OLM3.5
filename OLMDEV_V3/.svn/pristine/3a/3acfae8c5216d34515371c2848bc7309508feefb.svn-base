package xbolt.custom.sk.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.controller.XboltController;
import xbolt.cmm.service.CommonService;

/**
 * 공통 서블릿 처리
 * @Class Name : MainActionController.java
 * @Description : 공통화면을 제공한다.
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
public class MainSKActionController extends XboltController{
	private final Log _log = LogFactory.getLog(this.getClass());
	
	@Resource(name = "commonService")
	private CommonService commonService;

	@RequestMapping(value="/ssoIndex.do")
	public String ssoIndex(Map cmmMap,ModelMap model) throws Exception {
		Map sso1Map = new HashMap();
		model.put("ssoauthkey", StringUtil.checkNull(cmmMap.get("ssoauthkey")));
		model.put("ssolangkey", StringUtil.checkNull(cmmMap.get("ssolangkey")));

		return nextUrl("custom/sk/ssoSK/sso_index");
	}
	
	@RequestMapping(value="/olmLinkSK.do")
	public String olmLinkPopup(Map cmmMap,ModelMap model) throws Exception {
		String url = "/custom/sk/olmLinkPopup";
		String defaltLangCode = GlobalVal.DEFAULT_LANG_CODE;
		String languageID  = StringUtil.checkNull(cmmMap.get("languageID"));
		String linkID  = StringUtil.checkNull(cmmMap.get("linkID"));
		model.put("DEFAULT_LANG_CODE", defaltLangCode);
		model.put("languageID",languageID);
		model.put("linkID", linkID);
		//System.out.println("***olmLinkSK::"+url+"???ssolangkey="+ssolangkey);
		
		return nextUrl(url);
	}
	
	@RequestMapping(value="/mainHomeSKH.do")
	public String mainHomeSK(HttpServletRequest request, Map cmmMap,ModelMap model) throws Exception {
		model.put("menu", getLabel(request, commonService));
		return nextUrl("/custom/sk/mainHomeSKH");
	}
	
	@RequestMapping(value="/searchNamePopFromMDM.do")
	public String searchNamePopFromMDM(HttpServletRequest request, ModelMap model) throws Exception{
		try{
			if(!StringUtil.checkNull(request.getParameter("searchValue"),"").equals("")){
				model.put("searchValue", request.getParameter("searchValue"));
				
				List getList = new ArrayList();
				Map getMap = new HashMap();
				
				getMap.put("Name", request.getParameter("searchValue"));
				getMap.put("languageID", request.getParameter("languageID"));				
				getList = commonService.selectList("sk_cmm_SQL.searchNamePopFromMDM", getMap);	
				
				model.put("getList", getList);
			}
			
			model.put("gubun", StringUtil.checkNull(request.getParameter("gubun"),""));	
			model.put("menu", getLabel(request, commonService));	/*Label Setting*/		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return nextUrl("/custom/sk/searchNamePopFromMDM");
	}
	
	@RequestMapping(value="/indexSK.do")
	public String index(Map cmmMap,ModelMap model) throws Exception {
		return nextUrl("/custom/sk/sk_sso_login");
	}

}
