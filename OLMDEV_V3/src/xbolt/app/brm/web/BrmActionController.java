package xbolt.app.brm.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.filter.XSSRequestWrapper;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.EmailUtil;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;
import xbolt.project.chgInf.web.CSActionController;

/**
 * BrmActionController
 * 
 * @Class Name : BrmActionController.java
 * @Description : 업무화면을 제공한다.
 * @Modification Information
 * @수정일 수정자 수정내용
 * @--------- --------- -------------------------------
 * @2019. 05.08 . smartfactory 최초생성
 * 
 * @since 2019. 05. 08.
 * @version 1.0
 */

@Controller
@SuppressWarnings("unchecked")
public class BrmActionController extends XboltController {
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/costApprovalRequest.do")
	public String costApprovalRequest(HttpServletRequest request, ModelMap model, HashMap cmmMap) throws ExceptionUtil {
		model.put("ruleId", "ADRM1");
		return nextUrl("/app/brm/costApprovalRequest");
	}
	@RequestMapping(value="/decideApprovPath.do")
	public String decideApprovPath(HttpServletRequest request, ModelMap model, HashMap cmmMap) throws ExceptionUtil {
		String returnURL = "/app/brm/costApprovalRequest";
		String ruleId = StringUtil.checkNull(request.getParameter("ruleId"), "");
		String condition = StringUtil.checkNull(request.getParameter("condition"), "");
		int cost = Integer.parseInt(StringUtil.checkNull(request.getParameter("cost"), "0"));

		String operator = "";		
		int factor = 0;
		String sanctionType = "";
		
	 	List ruleDataList = new ArrayList();
		try {
			Map setMap = new HashMap();		 
		 	setMap.put("ruleId",ruleId);
		 	setMap.put("condition",condition);
			ruleDataList = commonService.selectList("brm_SQL.getRuleDataList", setMap);
			for(int i=0; i<ruleDataList.size(); i++){
				Map ruleDataMap = (Map) ruleDataList.get(i);
				operator = String.valueOf(ruleDataMap.get("OPERATOR"));
				factor = NumberUtil.getIntValue(ruleDataMap.get("FACTOR"));
				sanctionType = String.valueOf(ruleDataMap.get("SANCTIONTYPE"));
				switch(operator) {
					case "<":
				    	if(cost < factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
				    case "<=":
				    	if(cost <= factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
					case ">":
				    	if(cost > factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
					case ">=":
				    	if(cost >= factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
					case "==":
				    	if(cost == factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
					case "!=":
				    	if(cost != factor){
				    		returnURL = sanctionType;
				    	}
				    	break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nextUrl(returnURL);
	}	
}
