package xbolt.app.esm.isp.web;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.text.StringEscapeUtils;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.filter.XSSRequestWrapper;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.EmailUtil;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;

@Controller
@SuppressWarnings("unchecked")
public class ISPActionController extends XboltController {
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@Resource(name = "esmService")
	private CommonService esmService;
	
	@RequestMapping(value="/ispMain.do")
	public String ispMain(HttpServletRequest request, Map cmmMap, ModelMap model) throws Exception{
		Map setData = new HashMap();
		setData.put("sessionCurrLangType", cmmMap.get("sessionCurrLangType"));
		setData.put("sessionUserId", cmmMap.get("sessionUserId"));
		setData.put("templCode", "TMPL003");		
		Map templateMap = commonService.select("menu_SQL.mainTempl_select",setData);
		
		model.put("templateMap", templateMap);
		model.put("menu", getLabel(request, commonService));	//Label Setting
		model.put("screenType", request.getParameter("screenType"));
		return nextUrl("/app/esm/isp/ispMain"); 
	}
	
	@RequestMapping(value = "/ispMgt.do")
	public String ispMgt(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		String url = "/app/esm/isp/ispMgt"; 
		try {
				String parentId = StringUtil.checkNull(request.getParameter("s_itemID"));
				String srMode = StringUtil.checkNull(request.getParameter("srMode"));				
				String scrnType = StringUtil.checkNull(cmmMap.get("screenType"),request.getParameter("scrnType")); 
				String mainType = StringUtil.checkNull(cmmMap.get("mainType"),request.getParameter("mainType"));
				String srType = StringUtil.checkNull(request.getParameter("srType"));
				String varFilter = StringUtil.checkNull(request.getParameter("varFilter"));
				String itemProposal = StringUtil.checkNull(cmmMap.get("itemProposal"),request.getParameter("itemProposal"));
				String focusMenu = StringUtil.checkNull(request.getParameter("focusMenu"));
				String arcCode = StringUtil.checkNull(request.getParameter("arcCode"));
				String defCategory = StringUtil.checkNull(request.getParameter("defCategory"));

				cmmMap.put("srTypeCode", srType);
				Map srTypeInfo = commonService.select("esm_SQL.getESMSRTypeInfo",cmmMap);
				String srVarFilter = StringUtil.checkNull(srTypeInfo.get("VarFilter"));
				
				cmmMap.put("typeCode", srType);
				cmmMap.put("languageID", cmmMap.get("sessionCurrLangType"));
				cmmMap.put("category", "SRTP");
				String srTypeNM = commonService.selectString("common_SQL.getNameFromDic",cmmMap);
				
//				if(scrnType.equals("srRqst") || scrnType.equals("srDsk")){ 
//					url = "/app/esm/itsp/itspReqMain";
//				}
			
				List boardMgtList = commonService.selectList("board_SQL.boardMgtSRtypeAllocList", cmmMap);
				
				model.put("boardMgtList", boardMgtList);
				model.put("varFilter", varFilter );
				model.put("itemProposal", itemProposal );
				model.put("scrnType", scrnType );
				model.put("srMode",  srMode);
				model.put("srType",  srType);			
				model.put("srID",  StringUtil.checkNull(request.getParameter("srID")));	
				model.put("sysCode",  StringUtil.checkNull(request.getParameter("sysCode")));	
				model.put("parentID", parentId);
				model.put("mainType", mainType);
				model.put("menu", getLabel(request, commonService)); /* Label Setting */		
				model.put("focusMenu", focusMenu);
				model.put("arcCode", arcCode);
				model.put("srVarFilter", srVarFilter);
				model.put("srTypeNM", srTypeNM);
				model.put("defCategory", defCategory);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value = "/ispList.do")
	public String ispList(HttpServletRequest request, HashMap cmmMap,  HashMap commandMap,   ModelMap model) throws Exception {
		String url = "/app/esm/isp/ispList";
		try {
			Map setData = new HashMap();
			
			String srType = StringUtil.checkNull(cmmMap.get("srType"),request.getParameter("srType")); 
			String itemID = StringUtil.checkNull(commandMap.get("s_itemID"),request.getParameter("itemID")); // Item Menu에서 리스트 출력 시 사용
			String projectID = StringUtil.checkNull(commandMap.get("projectID"),""); 
			String srMode = StringUtil.checkNull(request.getParameter("srMode"));
			String arcCode = StringUtil.checkNull(request.getParameter("arcCode"));
			String varFilter = StringUtil.checkNull(request.getParameter("varFilter"));
			String multiComp = StringUtil.checkNull(request.getParameter("multiComp"));
			String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"));
			String menuStyle = StringUtil.checkNull(request.getParameter("menuStyle"));
			String itemTypeCodeItemID = null;
			
			
			setData.put("srType",srType);
			setData.put("s_itemID", itemID);
			setData.put("languageID", commandMap.get("sessionCurrLangType"));
			
			setData.put("srType",StringUtil.checkNull(srType,varFilter));
			
			setData.put("level", 1);
			String srAreaLabelNM1 = commonService.selectString("esm_SQL.getESMSRAreaLabelName",setData);
			String srArea1ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
			setData.put("level", 2);
			String srAreaLabelNM2 = commonService.selectString("esm_SQL.getESMSRAreaLabelName",setData);
			String srArea2ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);

			model.put("srArea1ClsCode", srArea1ClsCode);
			model.put("srArea2ClsCode", srArea2ClsCode);
		
			setData.put("typeCode", "SRArea1");
			String srArea1 = commonService.selectString("common_SQL.getNameFromDic",setData);
			setData.put("typeCode", "SRArea2");
			String srArea2 = commonService.selectString("common_SQL.getNameFromDic",setData);
			
			
			Map setMap = new HashMap();
			setMap.put("srTypeCode", StringUtil.checkNull(srType,varFilter));				
			Map SRTypeMap = commonService.select("esm_SQL.getESMSRTypeInfo",setMap);

			model.put("refID", projectID);
			model.put("scrnType", StringUtil.checkNull(request.getParameter("scrnType")));
			model.put("srStatus", StringUtil.checkNull(request.getParameter("srStatus"),"ALL") );
			model.put("srMode", srMode);
			model.put("srType", StringUtil.checkNull(srType,varFilter));
			model.put("projectID", projectID);
			model.put("itemID", itemID);
			model.put("pageNum", StringUtil.checkNull(request.getParameter("pageNum")) );
			model.put("menu", getLabel(request, commonService)); /* Label Setting */
			model.put("srID", StringUtil.checkNull(request.getParameter("srID")));
			model.put("mainType", StringUtil.checkNull(request.getParameter("mainType")));
			model.put("sysCode", StringUtil.checkNull(request.getParameter("sysCode")));
			model.put("multiComp", multiComp);
			model.put("itemTypeCode", StringUtil.checkNull(itemTypeCodeItemID,itemTypeCode));
			model.put("srAreaLabelNM1", srAreaLabelNM1);
			model.put("srAreaLabelNM2", srAreaLabelNM2);
			model.put("menuStyle",menuStyle);
			
			//검색조건 setting		
			model.put("category", StringUtil.checkNull(cmmMap.get("category")));
			model.put("subCategory", StringUtil.checkNull(cmmMap.get("subCategory")));
			model.put("srArea1", StringUtil.checkNull(cmmMap.get("srArea1")));
			model.put("srArea2", StringUtil.checkNull(cmmMap.get("srArea2")));
			model.put("subject", StringUtil.checkNull(cmmMap.get("subject")));
			model.put("status", StringUtil.checkNull(cmmMap.get("status")));				
			model.put("receiptUser", StringUtil.checkNull(cmmMap.get("receiptUser")));
			model.put("requestUser", StringUtil.checkNull(cmmMap.get("requestUser")));
			model.put("requestTeam", StringUtil.checkNull(cmmMap.get("requestTeam")));
			model.put("startRegDT", StringUtil.checkNull(cmmMap.get("startRegDT")));
			model.put("endRegDT", StringUtil.checkNull(cmmMap.get("endRegDT")));
			model.put("stSRDueDate", StringUtil.checkNull(cmmMap.get("stSRDueDate")));
			model.put("endSRDueDate", StringUtil.checkNull(cmmMap.get("endSRDueDate")));
			model.put("searchSrCode", StringUtil.checkNull(cmmMap.get("searchSrCode")));
			model.put("srReceiptTeam", StringUtil.checkNull(cmmMap.get("srReceiptTeam")));
			model.put("arcCode", arcCode);
			model.put("varFilter", varFilter);
			model.put("searchStatus", StringUtil.checkNull(request.getAttribute("searchStatus"),request.getParameter("searchStatus")));
			
			model.put("defCategory", StringUtil.checkNull(cmmMap.get("defCategory")));
			if(srMode.equals("mySr")){
				model.put("requstUser", cmmMap.get("sessionUserNm"));
			}
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value = "/registerISP.do")
	public String registerISP(HttpServletRequest request, HashMap cmmMap, HashMap commandMap, ModelMap model) throws Exception {
		String url = "/app/esm/isp/registerISP";
		try {
				List attachFileList = new ArrayList();
				//Map setMap = new HashMap();
				
				Map setData = new HashMap();
				String languageID = StringUtil.checkNull(cmmMap.get("sessionCurrLangType")); 
				String srType = StringUtil.checkNull(cmmMap.get("srType"),request.getParameter("srType")); 
				String parentId = StringUtil.checkNull(request.getParameter("parentID")); 
				String itemProposal = StringUtil.checkNull(cmmMap.get("itemProposal"));
				String arcCode = StringUtil.checkNull(cmmMap.get("arcCode"));
//				if(itemProposal.equals("Y")){
//					url = "/app/esm/itmp/registerItsp";
//				}
				String varFilter = StringUtil.checkNull(cmmMap.get("varFilter"));
				String itemID = StringUtil.checkNull(cmmMap.get("itemID"));
				String itemTypeCode = StringUtil.checkNull(cmmMap.get("itemTypeCode"));
				
				
				setData.put("srType",srType);
				setData.put("s_itemID", itemID);
				setData.put("languageID", commandMap.get("sessionCurrLangType"));
				
				setData.put("srType",StringUtil.checkNull(srType,varFilter));
				
				setData.put("level", 1);
				String srAreaLabelNM1 = commonService.selectString("esm_SQL.getESMSRAreaLabelName",setData);
				String srArea1ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
				setData.put("level", 2);
				String srAreaLabelNM2 = commonService.selectString("esm_SQL.getESMSRAreaLabelName",setData);
				String srArea2ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
				
				
				
				
				//임시저장된 파일이 존재할 수 있으므로 삭제
				String path=GlobalVal.FILE_UPLOAD_BASE_DIR + cmmMap.get("sessionUserId");
				if(!path.equals("")){FileUtil.deleteDirectory(path);}	
						
				// 시스템 날짜 
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(System.currentTimeMillis()));
				String thisYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());

				cal.add(Calendar.DATE, +14);
				String defaultDueDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +7);
				String currDateAdd7 = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
				
				model.put("itemProposal", itemProposal);
				model.put("scrnType", StringUtil.checkNull(request.getParameter("scrnType")) );
				model.put("crMode", StringUtil.checkNull(request.getParameter("crMode")) );
				model.put("crFilePath", GlobalVal.FILE_UPLOAD_ITEM_DIR);
				model.put("menu", getLabel(request, commonService)); //  Label Setting 
				model.put("pageNum", StringUtil.checkNull(request.getParameter("pageNum"), "1"));
				model.put("thisYmd", thisYmd);
				model.put("defaultDueDate", defaultDueDate); // default 완료 예정일
				model.put("currDateAdd7", currDateAdd7);
				model.put("ParentID", parentId);
				model.put("scrnType", StringUtil.checkNull(request.getParameter("scrnType"), ""));
				model.put("srType", srType);
				model.put("ProjectID", StringUtil.checkNull(request.getParameter("ProjectID"), ""));
				model.put("srMode", StringUtil.checkNull(request.getParameter("srMode"), ""));
				model.put("defCategory", StringUtil.checkNull(cmmMap.get("defCategory")));
				
				// List 검색조건 setting
				model.put("category", StringUtil.checkNull(cmmMap.get("category")));
				model.put("srArea1", StringUtil.checkNull(cmmMap.get("srArea1")));
				model.put("srArea2", StringUtil.checkNull(cmmMap.get("srArea2")));
				model.put("subject", StringUtil.checkNull(cmmMap.get("subject")));
				model.put("status", StringUtil.checkNull(cmmMap.get("status")));				
				model.put("searchSrCode", StringUtil.checkNull(cmmMap.get("searchSrCode")));
				model.put("subject", StringUtil.checkNull(cmmMap.get("subject")));
				model.put("srReceiptTeam", StringUtil.checkNull(cmmMap.get("srReceiptTeam")));
				model.put("crReceiptTeam", StringUtil.checkNull(cmmMap.get("crReceiptTeam")));
				
				model.put("arcCode", arcCode);
				model.put("varFilter", varFilter);
				model.put("itemID", itemID);
				model.put("itemTypeCode", itemTypeCode);
				model.put("srAreaLabelNM1", srAreaLabelNM1);
				model.put("srAreaLabelNM2", srAreaLabelNM2);
				
				//Call PROC_LOG START TIME
				setInitProcLog(request);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value = "/createISPMst.do")
	public String createISPMst(MultipartHttpServletRequest request, HashMap commandMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		XSSRequestWrapper xss = new XSSRequestWrapper(request);
		try {
			HashMap setData = new HashMap();
			HashMap insertData = new HashMap();
			HashMap updateData = new HashMap();
			HashMap setMap = new HashMap();
			
			String maxSrId = "";
			String curmaxSRCode ="";
			String maxSRCode = "";
			String userID = "";
			String proposal = StringUtil.checkNull(xss.getParameter("proposal"));
			String srMode = StringUtil.checkNull(xss.getParameter("srMode"));
			String scrnType = StringUtil.checkNull(xss.getParameter("scrnType"));
			String srType = StringUtil.checkNull(xss.getParameter("srType"));
			String requestUserID = StringUtil.checkNull(xss.getParameter("requestUserID"));
			String reqdueDate = StringUtil.checkNull(xss.getParameter("reqdueDate"));
			String reqDueDateTime = StringUtil.checkNull(xss.getParameter("reqDueDateTime"));
			String category = StringUtil.checkNull(xss.getParameter("category"));
			String subject = StringUtil.checkNull(xss.getParameter("subject"));
			String description = StringEscapeUtils.unescapeHtml4(StringUtil.checkNull(commandMap.get("description")));
			String srArea3 = StringUtil.checkNull(commandMap.get("rootItemID"));	// 개정 요청할 item
			String itemTypeCode = "";
			if(srArea3.isEmpty()) { itemTypeCode = xss.getParameter("itemTypeCd"); } 
			else { itemTypeCode = xss.getParameter("itemTypeCode"); }
			String emailCode = "SRREQ" ;
//			String itemProposal =  StringUtil.checkNull(xss.getParameter("itemProposal"));
//			String varFilter = StringUtil.checkNull(xss.getParameter("varFilter"));
//			model.put("varFilter", varFilter);
			
			setData.put("memberID", requestUserID);
			String companyID = StringUtil.checkNull(commonService.selectString("user_SQL.getMemberCompanyID", setData));
			
			/* 시스템 날짜 */
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			String thisYmd = new SimpleDateFormat("yyMMdd").format(cal.getTime());
			setData.put("thisYmd", thisYmd);
			maxSrId = StringUtil.checkNull(commonService.selectString("esm_SQL.getMaxESMSrID", setData)).trim();
			curmaxSRCode = StringUtil.checkNull(commonService.selectString("esm_SQL.getMaxESMSRCode", setData)).trim();				
			if(curmaxSRCode.equals("")){ // 당일 SR이 없으면
				maxSRCode = "SR"  + thisYmd + "0001";
			} else {
				curmaxSRCode = curmaxSRCode.substring(curmaxSRCode.length() - 4, curmaxSRCode.length());
				int curSRCode = Integer.parseInt(curmaxSRCode) + 1;
				maxSRCode =  "SR" +  thisYmd + String.format("%04d", curSRCode);	
			}
			
			// srArea1, srArea2 classCode 조회
			setData.put("languageID", StringUtil.checkNull(commandMap.get("sessionCurrLangType")) );			
			setData.put("srType", srType);
			setData.put("itemID", srArea3);
			setData.put("itemTypeCode", itemTypeCode);
			setData.put("level", "1");
			String srArea1ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
			setData.put("classCode", srArea1ClsCode);
			String srArea1 = commonService.selectString("esm_SQL.getSuperiorItemByClass", setData);
			
			setData.put("level", "2");
			String srArea2ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
			setData.put("classCode", srArea2ClsCode);
			String srArea2 = commonService.selectString("esm_SQL.getSuperiorItemByClass", setData);
			
//			setData.put("srCatID", subCategory);
//			Map categoryInfo= commonService.select("config_SQL.getAllSRCatMgtList_gridList", setData);
//			String category = StringUtil.checkNull(categoryInfo.get("ParentID"));
			
			// 변경제안 아이템의 projectID
			String projectID = commonService.selectString("item_SQL.getProjectIDFromItem",setData);
			insertData.put("projectID", projectID);
			insertData.put("srID", maxSrId);
			insertData.put("srCode", maxSRCode);
			insertData.put("srType", srType);
			insertData.put("subject", subject);
			insertData.put("description", description);
			insertData.put("category", category); 
//			insertData.put("subCategory", subCategory); 
			insertData.put("requestUserID", requestUserID);
			insertData.put("srArea1", srArea1);
			insertData.put("srArea2", srArea2);
			insertData.put("srArea3", srArea3);
			insertData.put("regUserID", commandMap.get("sessionUserId"));
			insertData.put("regTeamID", commandMap.get("sessionTeamId"));
			insertData.put("companyID", companyID);	
			insertData.put("itemTypeCode", itemTypeCode);	
			if(!reqDueDateTime.equals("") ) {
				reqdueDate = reqdueDate+" "+reqDueDateTime;	
			}
			insertData.put("reqdueDate", reqdueDate);
			
			// 선택된 카테고리의 접수자/접수팀  정보 취득
			setData.put("srType", srType);
			setData.put("srCatID", category);
			Map srCatInfo =  commonService.select("esm_SQL.getESMSRAreaFromSrCat", setData);
			insertData.put("procPathID", srCatInfo.get("ProcPathID"));
			
			
			setData.put("itemClassCode", "CL03004");
						
			setMap.put("procPathID", StringUtil.checkNull(srCatInfo.get("ProcPathID")));
			String startEventCode = StringUtil.checkNull(commonService.selectString("esm_SQL.getStartEventCode", setMap));
			insertData.put("status", startEventCode); 
			
			setData.put("userID", requestUserID);
			Map reqTeamInfoMap = commonService.select("user_SQL.memberTeamInfo", setData);
			insertData.put("requestTeamID",reqTeamInfoMap.get("TeamID"));
			
			String useCRM = StringUtil.checkNull(GlobalVal.USE_CRM);
			setData.put("teamID", companyID);
			String custGRNo = "";
			if(useCRM.equals("Y")){
				custGRNo =  StringUtil.checkNull(commonService.selectString("crm_SQL.getCustGRNo", setData));
				insertData.put("custGRNo", custGRNo);
			}
			
			Map authorInfo = new HashMap();
			// item 담당자 정보 // 선택된 srArea3의 Item Type 취득
			if(srArea3.isEmpty()) {
				setData.put("srCatID", category);
				authorInfo = commonService.select("esm_SQL.getESMSRCatMagager", setData);
			} else {
				setData.put("s_itemID", srArea3);
				authorInfo = commonService.select("item_SQL.getObjectInfo", setData);
			}

			insertData.put("receiptUserID", authorInfo.get("AuthorID"));
			insertData.put("receiptTeamID", authorInfo.get("OwnerTeamID"));
			
			Map RoleAssignMap =  commonService.select("esm_SQL.getESMSRAreaFromSrCat", setData);
			String processType = StringUtil.checkNull(RoleAssignMap.get("ProcessType"));
			
			Map receiptInfoMap = new HashMap();
			setData.put("teamID", reqTeamInfoMap.get("TeamID"));
			if(processType.equals("4")){ // 부서장 검토 
				receiptInfoMap = commonService.select("user_SQL.getUserTeamManagerInfo", setData);
				insertData.put("status", "MRV"); // 소속부서장 검토 
				emailCode = "SRMRV" ;
			}else{
				receiptInfoMap = authorInfo;
			}
			
			
			// 메일 수신자 설정
			Map receiverMap = new HashMap();
			List receiverList = new ArrayList();		
			String receiptUserID = StringUtil.checkNull(receiptInfoMap.get("AuthorID"));
			if(receiptInfoMap != null){							
				setData.put("memberID", receiptUserID);
				Map checkMemberActiveInfo = commonService.select("user_SQL.getCheckMemberActive", setData);
				if(checkMemberActiveInfo != null && !checkMemberActiveInfo.isEmpty()){
					insertData.put("receiptUserID", checkMemberActiveInfo.get("MemberID"));
					insertData.put("receiptTeamID", checkMemberActiveInfo.get("TeamID"));
					receiverMap.put("receiptUserID", checkMemberActiveInfo.get("MemberID"));
				}else{
					insertData.put("receiptUserID", receiptInfoMap.get("AuthorID"));
					insertData.put("receiptTeamID", receiptInfoMap.get("OwnerTeamID"));
					receiverMap.put("receiptUserID", receiptInfoMap.get("AuthorID"));
				}
				receiverList.add(0,receiverMap);
			}else{
				insertData.put("receiptUserID", "1");
				setData.put("userID", "1");
				Map recTeamInfoMap = commonService.select("user_SQL.memberTeamInfo", setData);
				insertData.put("receiptTeamID",recTeamInfoMap.get("TeamID"));
				receiverMap.put("receiptUserID", "1");
				receiverList.add(0,receiverMap);
			}
			
			// ItemProposal = Y
			String maxProcInstNo = "";
			// if(itemProposal.equals("Y")){
				insertData.put("proposal", proposal);
				
				maxProcInstNo = StringUtil.checkNull(commonService.selectString("instance_SQL.maxProcInstNo",setMap)).trim();
				maxProcInstNo = maxProcInstNo.substring(maxProcInstNo.length() - 5, maxProcInstNo.length());
				int curProcInstCode = Integer.parseInt(maxProcInstNo) + 1;
				String ProcInstNo = "P" + String.format("%09d", curProcInstCode);
				
				insertData.put("ProcInstNo", ProcInstNo);
			//}
			
			setMap.put("srTypeCode",srType);
			setMap.put("procPathID", StringUtil.checkNull(srCatInfo.get("ProcPathID")));
			Map srTypeMap = commonService.select("esm_SQL.getESMSRTypeInfo", setMap);
			
			insertData.put("defWFID", srTypeMap.get("DefWFID"));
			commonService.insert("esm_SQL.insertSRMst", insertData);
			commonService.insert("esm_SQL.insertIspNip", insertData);
			
			// Sr 첨부파일 등록 : TB_SR_FILE 
			commandMap.put("projcetID", projectID);
			insertESMSRFiles(commandMap, maxSrId);
			
			model.put("scrnType",scrnType);
			model.put("srMode", srMode);
			model.put("pageNum", StringUtil.checkNull(xss.getParameter("pageNum")));
			model.put("projectID", StringUtil.checkNull(xss.getParameter("projectID")));
			
			//Save PROC_LOG
			Map setProcMapRst = (Map)setProcLog(request, commonService, insertData);
			if(setProcMapRst.get("type").equals("FAILE")){
				System.out.println("SAVE PROC_LOG FAILE Msg : "+StringUtil.checkNull(setProcMapRst.get("msg")));
			}
			
			//Save PROC History			
			Map setProcHistory = new HashMap();
			
			Map ProcessInfo = commonService.select("esm_SQL.getProcPathItemID", setMap);
			
			setProcHistory.put("instanceNo", ProcInstNo);
			setProcHistory.put("processID", ProcessInfo.get("ItemID"));
			setProcHistory.put("ProcModelID", srTypeMap.get("ProcModelID"));
			setProcHistory.put("ProcPathID", StringUtil.checkNull(srCatInfo.get("ProcPathID")));
			setProcHistory.put("status", "00");
			setProcHistory.put("dueDate", reqdueDate);
			setProcHistory.put("InstanceClass", "CL03004");
			setProcHistory.put("ownerID", ProcessInfo.get("AuthorID"));
			setProcHistory.put("ownerTeamID", ProcessInfo.get("OwnerTeamID"));
			setProcHistory.put("procType", srType);
			setProcHistory.put("docCategory", "SR");
			setProcHistory.put("documentNo", maxSrId);
			
//			commonService.insert("instance_SQL.insertProcInst", setProcHistory);	
	
			// 참조자 메일 발송 
			String sharers = StringUtil.checkNull(request.getParameter("sharers"));
			String refMembers[] = sharers.split(",");
			int receiverIndex = receiverList.size();
			
			if(!sharers.equals("") && sharers != null){
				// Insert ESM_SR_MBR 				
				for(int i=0; refMembers.length > i; i++){
					setData = new HashMap();					
					setData.put("SRID", maxSrId);	
					setData.put("memberID", refMembers[i]);
		   			setData.put("sessionUserId", refMembers[i]);
		   			setData.put("mbrTeamID", commonService.selectString("user_SQL.userTeamID", setData));
					setData.put("assignmentType", "SRROLETP");	
//					setData.put("roleType", RoleAssignMap.get("RoleType"));	
					setData.put("orderNum", i+1);	
					setData.put("assigned", "1");	
					setData.put("accessRight", "R");	
					setData.put("creator", commandMap.get("sessionUserId"));
					
					commonService.insert("esm_SQL.insertESMSRMember", setData);
					
					receiverMap = new HashMap();
					receiverMap.put("receiptUserID", refMembers[i]); 
					receiverMap.put("receiptType", "CC");
					receiverList.add(receiverIndex,receiverMap);
					receiverIndex++;
				}
			}
			System.out.println("receiverList :"+receiverList);
									
			//======================================
			//send Email
			insertData.put("receiverList", receiverList);
			Map setMailMapRst = (Map)setEmailLog(request, commonService, insertData, emailCode);
			System.out.println("setMailMapRst : "+setMailMapRst );
			
			if(StringUtil.checkNull(setMailMapRst.get("type")).equals("SUCESS")){
				HashMap mailMap = (HashMap)setMailMapRst.get("mailLog");
				setMap.put("srID", maxSrId);
				setMap.put("srType", srType);
				setMap.put("languageID", String.valueOf(commandMap.get("sessionCurrLangType")));
				HashMap cntsMap = (HashMap)commonService.select("esm_SQL.getESMSRInfo", setMap);
									
				cntsMap.put("userID", insertData.get("receiptUserID"));
				cntsMap.put("languageID", String.valueOf(commandMap.get("sessionCurrLangType")));
				String receiptLoginID = StringUtil.checkNull(commonService.selectString("sr_SQL.getLoginID", cntsMap));
				cntsMap.put("loginID", receiptLoginID);
				cntsMap.replace("CategoryName", cntsMap.get("SubCategoryName"));
				
				cntsMap.put("emailCode", emailCode);
				String emailHTMLForm = StringUtil.checkNull(commonService.selectString("email_SQL.getEmailHTMLForm", cntsMap));
				cntsMap.put("emailHTMLForm", emailHTMLForm);
								
				Map resultMailMap = EmailUtil.sendMail(mailMap,cntsMap, getLabel(request, commonService));
				System.out.println("SEND EMAIL TYPE:"+resultMailMap+", Msg:"+StringUtil.checkNull(setMailMapRst.get("msg")));
				
			}else{
				System.out.println("SAVE EMAIL_LOG FAILE/DONT Msg : "+StringUtil.checkNull(setMailMapRst.get("msg")));
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "parent.fnGoSRList();parent.$('#isSubmit').remove();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}

		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);
	}
	
	private void insertESMSRFiles(HashMap commandMap, String srID) throws ExceptionUtil {
		Map fileMap  = new HashMap();
		List fileList = new ArrayList();
			try {
			int seqCnt = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", commandMap));		
			//Read Server File
			String orginPath = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(commandMap.get("sessionUserId"))+"//";
			fileMap.put("fltpCode", "SRDOC");
			String filePath = StringUtil.checkNull(commonService.selectString("fileMgt_SQL.getFilePath",fileMap)); 
			String targetPath = filePath;
			List tmpFileList = FileUtil.copyFiles(orginPath, targetPath);
			if(tmpFileList.size() != 0){
				for(int i=0; i<tmpFileList.size();i++){
					fileMap = new HashMap(); 
					HashMap resultMap = (HashMap)tmpFileList.get(i);
					fileMap.put("Seq", seqCnt);
					fileMap.put("DocumentID",srID);
					fileMap.put("DocCategory","SR");
					fileMap.put("projectID", commandMap.get("projectID"));
					fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
					fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
					fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
					fileMap.put("FileMgt", "SR");
					fileMap.put("FltpCode", "SRDOC");
					fileMap.put("userId", commandMap.get("sessionUserId"));
					fileMap.put("RegUserID", commandMap.get("sessionUserId"));
					fileMap.put("LastUser", commandMap.get("sessionUserId"));
					fileMap.put("LanguageID", commandMap.get("sessionCurrLangType"));
					
					fileMap.put("KBN", "insert");
					fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");					
					
					fileList.add(fileMap);
					seqCnt++;
				}
			}
			if(fileList.size() != 0){
				esmService.save(fileList, fileMap);
			}
			
			if (!orginPath.equals("")) {
				FileUtil.deleteDirectory(orginPath);
			}
        } catch(Exception e) {
        	throw new ExceptionUtil(e.toString());
        }
	}
	
	@RequestMapping(value = "/processISP.do")
	public String processISP(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		String url = "/app/esm/isp/viewISPDetail";
		HashMap setData = new HashMap();
		try {
				List attachFileList = new ArrayList();
				Map setMap = new HashMap();
				Map getSRInfo = new HashMap();		
				Map getItemMap = new HashMap();
				
				String languageID = StringUtil.checkNull(cmmMap.get("sessionCurrLangType")); 
				String srID = StringUtil.checkNull(cmmMap.get("srID")); 
				String srType = StringUtil.checkNull(cmmMap.get("srType")); 
				String scrnType = StringUtil.checkNull(cmmMap.get("scrnType")); 
				String srMode = StringUtil.checkNull(cmmMap.get("srMode"));
				String pageNum = StringUtil.checkNull(cmmMap.get("pageNum"));
				String srCode = StringUtil.checkNull(cmmMap.get("srCode"));
				String sessionUserID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
				String srStatus = StringUtil.checkNull(cmmMap.get("srStatus"));
				String status = StringUtil.checkNull(cmmMap.get("status"));
				String receiptUserID = StringUtil.checkNull(cmmMap.get("receiptUserID"));
				String projectID = StringUtil.checkNull(cmmMap.get("projectID"));
				String itemID = StringUtil.checkNull(cmmMap.get("itemID"));
				String isPopup = StringUtil.checkNull(cmmMap.get("isPopup"));	
				String mainType = StringUtil.checkNull(cmmMap.get("mainType"));	
				String varFilter = StringUtil.checkNull(cmmMap.get("varFilter"));	
				String multiComp = StringUtil.checkNull(cmmMap.get("multiComp"));
				String itemTypeCode = StringUtil.checkNull(cmmMap.get("itemTypeCode"));
				
				String itemProposal = StringUtil.checkNull(cmmMap.get("itemProposal"));
				String defCategory = StringUtil.checkNull(cmmMap.get("defCategory"));
				
				if(srCode.equals("")){; // 외부에서 호출시 srID만 넘어옮
					setData.put("srID", srID);
					setData.put("srType", srType);
					setData.put("languageID", languageID);
					getSRInfo = commonService.select("esm_SQL.getESMSRInfo", setData);					
	
					if(!getSRInfo.isEmpty()){
						status = StringUtil.checkNull(getSRInfo.get("Status"));
						receiptUserID = StringUtil.checkNull(getSRInfo.get("ReceiptUserID"));
					}
				}
				
				// 시스템 날짜 
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(System.currentTimeMillis()));
				String thisYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
				
				/* 임시 문서 보관 디렉토리 삭제 */
				String path = GlobalVal.FILE_UPLOAD_BASE_DIR + cmmMap.get("sessionUserId");
				FileUtil.deleteDirectory(path);
				//String updateSRStatus = srStatus;
				setMap.put("srID", srID);
				String startEventCode = StringUtil.checkNull(commonService.selectString("esm_SQL.getStartEventCode", setMap));
				setMap.put("status", startEventCode);
				String nextEventCode = StringUtil.checkNull(commonService.selectString("esm_SQL.getNextEventCode", setMap));
				if(sessionUserID.equals(receiptUserID) && !scrnType.equals("srRqst") && status.equals(startEventCode)){ // status 변경 --> RCV
					setMap.put("srID", srID);
					setMap.put("status", nextEventCode);
					setMap.put("lastUser", sessionUserID);
					commonService.update("esm_SQL.updateESMSR", setMap);	
					
					//Save PROC_LOG
					Map setProcMapRst = (Map)setProcLog(request, commonService, setMap);
					if(setProcMapRst.get("type").equals("FAILE")){
						String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
						System.out.println("Msg : "+Msg);
					}
				}				
				
				setData.put("srID", srID);
				setData.put("srType", srType);
				setData.put("srCode", srCode);
				setData.put("languageID", languageID);
				Map srInfoMap = commonService.select("esm_SQL.getESMSRInfo", setData);	
								
				String blocked = StringUtil.checkNull(srInfoMap.get("Blocked"));
				
				if(!scrnType.equals("srRqst") &&  // SR 등록 메뉴가 아니고
						 sessionUserID.equals(receiptUserID) &&  //  사용자 = 접수자 
						 blocked.equals("0")
						 ) { // 상태는 조치완료나 마감이 아닐 경우  SR 접수 처리로 이동 
					if(!isPopup.equals("Y")){url = "/app/esm/isp/processISP";} // SR모니터링 팝업이 아니면
				}
				
				
				// proposal == 01 이메일 전송
				if(sessionUserID.equals(receiptUserID) && status.equals("SPE018") && StringUtil.checkNull(srInfoMap.get("Proposal")).equals("01")){ 
					//==============================================
					Map setMailData = new HashMap();
					//send Email
					setMailData.put("EMAILCODE", "PROPS");
					setMailData.put("subject", StringUtil.checkNull(srInfoMap.get("Subject")));
					//setMailData.put("receiptUserID", StringUtil.checkNull(srInfoMap.get("RequestUserID")));
					
					List receiverList = new ArrayList();
					Map receiverMap = new HashMap();
					receiverMap.put("receiptUserID", StringUtil.checkNull(srInfoMap.get("RequestUserID")));
					receiverList.add(0,receiverMap);
					setMailData.put("receiverList", receiverList);
					
					Map setMailMapRst = (Map)setEmailLog(request, commonService, setMailData, "PROPS");
					System.out.println("setMailMapRst( [PRIME - 제안연계 알림] ) : "+setMailMapRst );
					
					HashMap setMailMap = new HashMap();
					if(StringUtil.checkNull(setMailMapRst.get("type")).equals("SUCESS")){
						HashMap mailMap = (HashMap)setMailMapRst.get("mailLog");
						setMailMap.put("srID", srID);
						setMailMap.put("srType", srType);
						setMailMap.put("languageID", String.valueOf(cmmMap.get("sessionCurrLangType")));
						HashMap cntsMap = (HashMap)commonService.select("esm_SQL.getESMSRInfo", setMailMap);
						
						cntsMap.put("srID", srID);	
						cntsMap.put("teamID", StringUtil.checkNull(srInfoMap.get("RequestTeamID")));					
						cntsMap.put("userID", StringUtil.checkNull(srInfoMap.get("RequestUserID")));
						cntsMap.put("languageID", String.valueOf(cmmMap.get("sessionCurrLangType")));
						String requestLoginID = StringUtil.checkNull(commonService.selectString("sr_SQL.getLoginID", cntsMap));
						cntsMap.put("loginID", requestLoginID);
						
						Map resultMailMap = EmailUtil.sendMail(mailMap, cntsMap, getLabel(request, commonService));
						System.out.println("SEND EMAIL TYPE:"+resultMailMap+ "Msg :" + StringUtil.checkNull(setMailMapRst.get("type")));
					}else{
						System.out.println("SAVE EMAIL_LOG FAILE/DONT Msg : "+StringUtil.checkNull(setMailMapRst.get("msg")));
					}
					//==============================================
				}
				
							
				List prLgInfoList = commonService.selectList("sr_SQL.getProLogInfo", setData);	// 진행 history		
				
				setData.put("srID", srID);
				setData.put("itemClassCode", "CL03004");
				setData.remove("srType");
				List procStatusList = commonService.selectList("esm_SQL.getProcStatusList", setData);    // 예상 진행 리스트
				
								
				setData.put("procPathID", StringUtil.checkNull(srInfoMap.get("ProcPathID")) );
				setData.put("languageID", cmmMap.get("sessionCurrLangType"));
				//List srProcCaseList = commonService.selectList("esm_SQL.getProcPathList", setData);
				
				/* 첨부문서 취득 */
				attachFileList = commonService.selectList("sr_SQL.getSRFileList", setData);
				
				String appBtn = "N"; // app button 제어  
				if(!StringUtil.checkNull(srInfoMap.get("SubCategory")).equals("")
						&& !StringUtil.checkNull(srInfoMap.get("Comment")).equals("")
						){
					appBtn = "Y";
				}
				
				/* 담당자 정보 취득 */
				setData.put("MemberID", receiptUserID);
				Map authorInfoMap = commonService.select("item_SQL.getAuthorInfo", setData);	
				model.put("authorInfoMap", authorInfoMap); // 담당자 정보
				model.put("appBtn", appBtn);
				
				String Description = StringUtil.checkNull(srInfoMap.get("Description")).replaceAll("&lt;","<").replaceAll("&gt;", ">").replaceAll("&quot;","\"");
				String Comment = StringUtil.checkNull(srInfoMap.get("Comment")).replaceAll("&lt;","<").replaceAll("&gt;", ">").replaceAll("&quot;","\"");
				srInfoMap.put("Description",Description);
				srInfoMap.put("Comment",Comment);
				
				setMap = new HashMap();
				setMap.put("srTypeCode", srType);				
				Map srTypeInfo = commonService.select("esm_SQL.getESMSRTypeInfo",setMap);
				
				setMap.put("srID", srID);
				setMap.put("languageID", cmmMap.get("sessionCurrLangType"));				
				List esmSRMbrList = commonService.selectList("esm_SQL.getESMSRMbr",setMap);
				String sharerNames = "";
				if(esmSRMbrList.size()>0){
					for(int i=0; i<esmSRMbrList.size(); i++){
						Map esmSRMbrInfo = (Map)esmSRMbrList.get(i);
						if(i==0){
							sharerNames = StringUtil.checkNull(esmSRMbrInfo.get("esmSRMbr"));
						}else{
							sharerNames = sharerNames + ", " + StringUtil.checkNull(esmSRMbrInfo.get("esmSRMbr"));
						}
					}
				}
				
				List spePrePostList = commonService.selectList("esm_SQL.getSpePrePostList",setData);
				Map spePrePostMap = new HashMap();
				Long statusNo = null;
				for(int i = 0; i < spePrePostList.size(); i++){
					Map getMap = (HashMap)spePrePostList.get(i);
					if(getMap.get("Identifier").equals(status)) {
						statusNo = (Long) getMap.get("RNUM");	// procPath에서의 현재상태 순서 구하기
					}
				}
				for(int i = 0; i < spePrePostList.size(); i++){
					Map getMap = (HashMap)spePrePostList.get(i);
					if((Long)getMap.get("RNUM") < statusNo) spePrePostMap.put(getMap.get("Identifier"), "PRE");
					if((Long)getMap.get("RNUM") == statusNo) spePrePostMap.put(getMap.get("Identifier"), "ON");
					if((Long)getMap.get("RNUM") > statusNo) spePrePostMap.put(getMap.get("Identifier"), "POST");
				}
				
				setData.put("srCatID", srTypeInfo.get("Category"));
				setData.put("srType", srType);
				Map RoleAssignMap =  commonService.select("esm_SQL.getESMSRAreaFromSrCat", setData);
				
				setData.put("SRID",srID);
				setData.put("roleType", RoleAssignMap.get("RoleType"));
				setData.put("assignmentType", "SRROLETP");
				setData.put("languageID", languageID);
				List srSharers = commonService.selectList("esm_SQL.getESMSRMember",setData);
				String srSharerName = "";
				String srSharerID = "";
				if(srSharers.size()>0){
					for(int i=0; i<srSharers.size(); i++){
						Map srSharerInfo = (Map)srSharers.get(i);
						if(i==0){
							srSharerName = StringUtil.checkNull(srSharerInfo.get("SRRefMember"));
							srSharerID = StringUtil.checkNull(srSharerInfo.get("MemberID"));
						}else{
							srSharerName = srSharerName + ", " + StringUtil.checkNull(srSharerInfo.get("SRRefMember"));
							srSharerID = srSharerID + "," + StringUtil.checkNull(srSharerInfo.get("MemberID"));
						}
					}
				}
				
				setMap = new HashMap();
				setMap.put("userId", sessionUserID);
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				setMap.put("s_itemID", srInfoMap.get("SRArea3"));
				setMap.put("ProjectType", "CSR");
				setMap.put("isMainItem", "Y");
				List projectNameList = commonService.selectList("project_SQL.getProjectNameList", setMap);
				model.put("projectNameList", projectNameList.size());
				
				setData.put("languageID", languageID);
				Map itemInfo = commonService.select("item_SQL.getObjectInfo", setMap);
				
				model.put("sharerNames", sharerNames);				
				model.put("WFDocURL", StringUtil.checkNull(srTypeInfo.get("WFDocURL")));				
				model.put("itemProposal", itemProposal);
				model.put("getItemMap",getItemMap);
				model.put("srInfoMap", srInfoMap);
				model.put("prLgInfoList", prLgInfoList);
				model.put("procStatusList", procStatusList);
			//	model.put("srProcCaseList", srProcCaseList);
				model.put("srFilePath", GlobalVal.FILE_UPLOAD_ITEM_DIR);
				model.put("SRFiles", attachFileList);
				model.put("scrnType", scrnType );
				model.put("srMode", srMode );
				model.put("srType", srType);				
				model.put("menu", getLabel(request, commonService)); //  Label Setting 
				model.put("pageNum", pageNum);
				model.put("thisYmd", thisYmd);
				model.put("projectID", projectID);
				model.put("srStatus", srStatus);
				model.put("itemID", itemID);
				model.put("isPopup", isPopup);
				model.put("srSharerName", srSharerName);
				model.put("srSharerID", srSharerID);
				model.put("multiComp", multiComp);
				model.put("itemTypeCode", itemTypeCode);
				model.put("status", StringUtil.checkNull(cmmMap.get("status")));
				model.put("itemStatus",itemInfo.get("Status"));
				
				// 검색조건 Setting
				model.put("category", StringUtil.checkNull(cmmMap.get("category")));
				model.put("subCategory", StringUtil.checkNull(cmmMap.get("subCategory")));
				model.put("srArea1", StringUtil.checkNull(cmmMap.get("srArea1")));
				model.put("srArea2", StringUtil.checkNull(cmmMap.get("srArea2")));
				model.put("subject", StringUtil.checkNull(cmmMap.get("subject")));
				model.put("searchStatus", StringUtil.checkNull(cmmMap.get("searchStatus")));
				model.put("receiptUser", StringUtil.checkNull(cmmMap.get("receiptUser")));
				model.put("requestUser", StringUtil.checkNull(cmmMap.get("requestUser")));
				model.put("requestTeam", StringUtil.checkNull(cmmMap.get("requestTeam")));
				model.put("startRegDT", StringUtil.checkNull(cmmMap.get("startRegDT")));
				model.put("endRegDT", StringUtil.checkNull(cmmMap.get("endRegDT")));
				model.put("stSRDueDate", StringUtil.checkNull(cmmMap.get("stSRDueDate")));
				model.put("endSRDueDate", StringUtil.checkNull(cmmMap.get("endSRDueDate")));	
				model.put("searchSrCode", StringUtil.checkNull(cmmMap.get("searchSrCode")));
				model.put("srReceiptTeam", StringUtil.checkNull(cmmMap.get("srReceiptTeam")));
				model.put("MULTI_COMPANY", GlobalVal.MULTI_COMPANY);			
				model.put("spePrePostMap", spePrePostMap);
				model.put("varFilter", varFilter);
				model.put("defCategory", defCategory);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value = "/updateITMStatus.do")
	public String updateITMStatus(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		try {
			HashMap setData = new HashMap();		
		
			String srID = StringUtil.checkNull(cmmMap.get("srID"));
			String status = StringUtil.checkNull(cmmMap.get("status"));
			String svcCompl = StringUtil.checkNull(cmmMap.get("svcCompl"));
			String wfInstanceID = StringUtil.checkNull(cmmMap.get("wfInstanceID"));
			String wfInstanceStatus = StringUtil.checkNull(cmmMap.get("wfInstanceStatus"));
			String blockSR = StringUtil.checkNull(cmmMap.get("blockSR"));
			
			setData.put("wfInstanceID", wfInstanceID);
			setData.put("srID", srID);
			setData.put("status", status);		
			setData.put("lastUser", StringUtil.checkNull(cmmMap.get("sessionUserId")) );
			setData.put("svcCompl", svcCompl);	
			if(blockSR.equals("Y")) setData.put("blocked", "1");
			
			// System.out.println("Fixed Path YN : " + fixedPathYN + newProcPathID)	; 	
			String fixedPathYN = commonService.selectString("esm_SQL.getFixedPathYN",setData);	
			
			// ESM_PROC_CONFIG : SpeCode, ProcPathID
			Map srInfo = commonService.select("esm_SQL.getESMSRInfo", setData);
			setData.put("srType", srInfo.get("SRType"));
			setData.put("wfInstanceID", wfInstanceID);
			setData.put("wfInstanceStatus", wfInstanceStatus);
			
			Map esmProcInfo = (Map)decideSRProcPath(request, commonService, setData);
			//System.out.println("wfInstanceStatus :"+wfInstanceStatus);
			//System.out.println("esmProcInfo :"+esmProcInfo);
			String procPathID = "";
			String speCode = "";
			if(esmProcInfo != null && !esmProcInfo.isEmpty()){
				procPathID = StringUtil.checkNull(esmProcInfo.get("ProcPathID"));
				speCode = StringUtil.checkNull(esmProcInfo.get("SpeCode"));
				if(!procPathID.equals("") && procPathID != null && fixedPathYN.equals("N")) setData.put("procPathID", procPathID); 
				if(!speCode.equals("") && speCode != null) setData.put("status", speCode); 
			}
			
			commonService.update("esm_SQL.updateESMSR", setData);		
			
			//Save PROC_LOG		
			Map setProcMapRst = (Map)setProcLog(request, commonService, setData);
			if(setProcMapRst.get("type").equals("FAILE")){
				String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
				System.out.println("Msg : "+Msg);
			}
					
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "fnCallBack();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
	
		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value = "/rejectISP.do")
	public String rejectISP(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		try {
			HashMap setData = new HashMap();		
		
			String srID = StringUtil.checkNull(request.getParameter("srID"));
			String blockSR = StringUtil.checkNull(request.getParameter("blockSR"));
			String svcCompl = StringUtil.checkNull(request.getParameter("svcCompl"));
			
			setData.put("srID", srID);
			setData.put("languageID", cmmMap.get("sessionCurrLangType"));
			
			// System.out.println("Fixed Path YN : " + fixedPathYN + newProcPathID)	; 	
			String fixedPathYN = commonService.selectString("esm_SQL.getFixedPathYN",setData);	
			
			Map srInfo = commonService.select("esm_SQL.getESMSRInfo", setData);
			setData.put("srType", srInfo.get("SRType"));
			setData.put("docCategory", "SR");
			setData.put("lastUser", StringUtil.checkNull(cmmMap.get("sessionUserId")));
			if(blockSR.equals("Y")) setData.put("blocked", "1");
			setData.put("svcCompl", svcCompl);
			
			// ESM_PROC_CONFIG : SpeCode, ProcPathID
			Map esmProcInfo = (Map)decideSRProcPath(request, commonService, setData);
			//System.out.println("wfInstanceStatus :"+wfInstanceStatus);
			//System.out.println("esmProcInfo :"+esmProcInfo);
			String procPathID = "";
			String speCode = "";
			if(esmProcInfo != null && !esmProcInfo.isEmpty()){
				procPathID = StringUtil.checkNull(esmProcInfo.get("ProcPathID"));
				speCode = StringUtil.checkNull(esmProcInfo.get("SpeCode"));
				if(!procPathID.equals("") && procPathID != null && fixedPathYN.equals("N")) setData.put("procPathID", procPathID); 
				if(!speCode.equals("") && speCode != null) setData.put("status", speCode); 
			}
			
			commonService.update("esm_SQL.updateESMSR", setData);		
			
			//Save PROC_LOG		
			Map setProcMapRst = (Map)setProcLog(request, commonService, setData);
			if(setProcMapRst.get("type").equals("FAILE")){
				String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
				System.out.println("Msg : "+Msg);
			}
					
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "fnCallBack();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
	
		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value = "/changeISPItem.do")
	public String changeISPItem(HttpServletRequest request, HashMap commandMap, ModelMap model)throws Exception {
		HashMap target = new HashMap();
		try{
			String itemID = StringUtil.checkNull(request.getParameter("itemID"), "");
			String languageID = StringUtil.checkNull(request.getParameter("languageID"), "");
			String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"), "");
			String srType = StringUtil.checkNull(request.getParameter("srType"), "");
			String srID = StringUtil.checkNull(request.getParameter("srID"), "");
			String subject = StringUtil.checkNull(request.getParameter("subject"), "");
			
			Map updateValMap = new HashMap();
			Map updateInfoMap = new HashMap();
			List updateList = new ArrayList();
			
			updateValMap.put("s_itemID",itemID);
			updateValMap.put("languageID",languageID);
			
			Map ItemMgtUserMap = new HashMap();
			ItemMgtUserMap = commonService.select("item_SQL.getObjectInfo", updateValMap);
			
			// srArea1, srArea2
			HashMap setData = new HashMap();
			setData.put("srID", srID);
			setData.put("itemID", itemID);
			setData.put("srType", srType);
			setData.put("languageID",languageID);
			setData.put("itemTypeCode", ItemMgtUserMap.get("ItemTypeCode"));
			setData.put("level", "1");
			String srArea1ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
			setData.put("classCode", srArea1ClsCode);
			String srArea1 = commonService.selectString("esm_SQL.getSuperiorItemByClass", setData);
			
			setData.put("level", "2");
			String srArea2ClsCode = commonService.selectString("esm_SQL.getSRAreaClsCode",setData);
			setData.put("classCode", srArea2ClsCode);
			String srArea2 = commonService.selectString("esm_SQL.getSuperiorItemByClass", setData);
			
			updateValMap.put("languageID",languageID);
			updateValMap.put("srID",srID);
			updateValMap.put("receiptUserID",ItemMgtUserMap.get("AuthorID"));
			updateValMap.put("receiptTeamID",ItemMgtUserMap.get("OwnerTeamID"));
			updateValMap.put("srArea1",srArea1);
			updateValMap.put("srArea2",srArea2);
			updateValMap.put("srArea3",itemID);
			updateValMap.put("lastUser",commandMap.get("sessionUserId"));
			updateInfoMap.put("KBN", "update");
			updateInfoMap.put("SQLNAME", "esm_SQL.updateESMSR");
			updateList.add(updateValMap);
			esmService.save(updateList, updateInfoMap);
			target.put(AJAX_SCRIPT,"this.fnGoSRList();");
			model.addAttribute(AJAX_RESULTMAP, target);
			
			//Save PROC_LOG		
			Map setProcMapRst = (Map)setProcLog(request, commonService, setData);
			if(setProcMapRst.get("type").equals("FAILE")){
				String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
				System.out.println("Msg : "+Msg);
			}
			
			Map insertData = new HashMap();
			// 메일 수신자 설정
			Map receiverMap = new HashMap();
			List receiverList = new ArrayList();		
			String receiptUserID = StringUtil.checkNull(ItemMgtUserMap.get("AuthorID"));
			if(receiptUserID != null){	
				setData.put("memberID", receiptUserID);
				Map checkMemberActiveInfo = commonService.select("user_SQL.getCheckMemberActive", setData);
				if(checkMemberActiveInfo != null && !checkMemberActiveInfo.isEmpty()){
					insertData.put("receiptUserID", checkMemberActiveInfo.get("MemberID"));
					insertData.put("receiptTeamID", checkMemberActiveInfo.get("TeamID"));
					receiverMap.put("receiptUserID", checkMemberActiveInfo.get("MemberID"));
				}else{
					insertData.put("receiptUserID", "1");
					setData.put("userID", "1");
					Map recTeamInfoMap = commonService.select("user_SQL.memberTeamInfo", setData);
					insertData.put("receiptTeamID",recTeamInfoMap.get("TeamID"));
					receiverMap.put("receiptUserID", "1");
				}
				receiverList.add(0,receiverMap);
			}else{
				insertData.put("receiptUserID", "1");
				setData.put("userID", "1");
				Map recTeamInfoMap = commonService.select("user_SQL.memberTeamInfo", setData);
				insertData.put("receiptTeamID",recTeamInfoMap.get("TeamID"));
				receiverMap.put("receiptUserID", "1");
				receiverList.add(0,receiverMap);
			}
			
			//======================================
			//send Email
			Map setMap = new HashMap();
			insertData.put("subject", subject);
			insertData.put("receiverList", receiverList);
			String emailCode = "SRREQ" ;
			Map setMailMapRst = (Map)setEmailLog(request, commonService, insertData, emailCode);
			System.out.println("setMailMapRst : "+setMailMapRst );
			
			if(StringUtil.checkNull(setMailMapRst.get("type")).equals("SUCESS")){
				HashMap mailMap = (HashMap)setMailMapRst.get("mailLog");
				setMap.put("srID", srID);
				setMap.put("srType", srType);
				setMap.put("languageID", String.valueOf(commandMap.get("sessionCurrLangType")));
				HashMap cntsMap = (HashMap)commonService.select("esm_SQL.getESMSRInfo", setMap);
									
				cntsMap.put("userID", insertData.get("receiptUserID"));
				cntsMap.put("languageID", String.valueOf(commandMap.get("sessionCurrLangType")));
				String receiptLoginID = StringUtil.checkNull(commonService.selectString("sr_SQL.getLoginID", cntsMap));
				cntsMap.put("loginID", receiptLoginID);
				
				cntsMap.put("emailCode", "SRREQ");
				cntsMap.replace("CategoryName", cntsMap.get("SubCategoryName"));
				String emailHTMLForm = StringUtil.checkNull(commonService.selectString("email_SQL.getEmailHTMLForm", cntsMap));
				cntsMap.put("emailHTMLForm", emailHTMLForm);
								
				Map resultMailMap = EmailUtil.sendMail(mailMap,cntsMap, getLabel(request, commonService));
				System.out.println("SEND EMAIL TYPE:"+resultMailMap+", Msg:"+StringUtil.checkNull(setMailMapRst.get("msg")));
				
			}else{
				System.out.println("SAVE EMAIL_LOG FAILE/DONT Msg : "+StringUtil.checkNull(setMailMapRst.get("msg")));
			}
			
			
		} catch(Exception e){
			System.out.println(e.toString());
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068"));
		}		
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value = "/acceptISP.do")
	public String acceptISP(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		try {
			HashMap setData = new HashMap();		
		
			String srID = StringUtil.checkNull(request.getParameter("srID"));
			String blockSR = StringUtil.checkNull(request.getParameter("blockSR"));
			
			setData.put("srID", srID);
			setData.put("languageID", cmmMap.get("sessionCurrLangType"));
			
			// System.out.println("Fixed Path YN : " + fixedPathYN + newProcPathID)	; 	
			String fixedPathYN = commonService.selectString("esm_SQL.getFixedPathYN",setData);	
			
			Map srInfo = commonService.select("esm_SQL.getESMSRInfo", setData);
			setData.put("srType", srInfo.get("SRType"));
			setData.put("docCategory", "SR");
			setData.put("lastUser", StringUtil.checkNull(cmmMap.get("sessionUserId")));
			if(blockSR.equals("Y")) setData.put("blocked", "1");
			setData.put("status", srInfo.get("SRNextStatus"));
			
			commonService.update("esm_SQL.updateESMSR", setData);		
			
			//Save PROC_LOG		
			Map setProcMapRst = (Map)setProcLog(request, commonService, setData);
			if(setProcMapRst.get("type").equals("FAILE")){
				String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
				System.out.println("Msg : "+Msg);
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "fnCallBack();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
	
		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value = "/completeISP.do")
	public String completeESR(MultipartHttpServletRequest request, HashMap  commandMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		XSSRequestWrapper xss = new XSSRequestWrapper(request);
		try {
			HashMap setData = new HashMap();	
			HashMap setMap = new HashMap();	
			HashMap updateData = new HashMap();
		
			String srID = StringUtil.checkNull(xss.getParameter("srID"));
			String srCode = StringUtil.checkNull(xss.getParameter("srCode"));
			String memberID = StringUtil.checkNull(xss.getParameter("memberID"));			
			String srMode = StringUtil.checkNull(xss.getParameter("srMode"));
			String screenType = StringUtil.checkNull(xss.getParameter("screenType"));
			String srType = StringUtil.checkNull(xss.getParameter("srType"));
//			String srArea1 = StringUtil.checkNull(xss.getParameter("srArea1"));
//			String srArea2 = StringUtil.checkNull(xss.getParameter("srArea2"));
			String category = StringUtil.checkNull(xss.getParameter("category"));
			String subCategory = StringUtil.checkNull(xss.getParameter("subCategory"));
			String subject = StringUtil.checkNull(xss.getParameter("subject"));
			String priority = StringUtil.checkNull(xss.getParameter("priority"));
			String comment = StringEscapeUtils.unescapeHtml4(StringUtil.checkNull(xss.getParameter("comment")));
			String dueDate = StringUtil.checkNull(xss.getParameter("dueDate"));
			String receiptUserID = StringUtil.checkNull(xss.getParameter("receiptUserID"));
			String receiptTeamID = StringUtil.checkNull(xss.getParameter("receiptTeamID"));
			String requestUserID = StringUtil.checkNull(xss.getParameter("requestUserID"));
			String requestTeamID = StringUtil.checkNull(xss.getParameter("requestTeamID"));
			String processType = StringUtil.checkNull(xss.getParameter("processType"));
			String speCode = StringUtil.checkNull(xss.getParameter("speCode"));
			String svcCompl = StringUtil.checkNull(xss.getParameter("svcCompl"));
			String blockSR = StringUtil.checkNull(xss.getParameter("blockSR"));
			String blocked = "";
			if(blockSR.equals("Y")) blocked = "1";
				
			// 선택된 시스템(srArea2)의 ProjectID 취득 
//			setData.put("srArea2", srArea2);
//			String projectID = StringUtil.checkNull(commonService.selectString("sr_SQL.getProjectIDFromL2", setData)).trim();
//			updateData.put("projectID", projectID);
			updateData.put("srID", srID);
			updateData.put("srType", srType);
			updateData.put("srCatID", category); 
			updateData.put("subCategory", subCategory); 
//			updateData.put("priority", priority); 
//			updateData.put("srArea1", srArea1);
//			updateData.put("srArea2", srArea2);
			updateData.put("comment", comment);	
			updateData.put("dueDate", dueDate);	
			updateData.put("lastUser", commandMap.get("sessionUserId"));
			updateData.put("status", speCode);				
			updateData.put("srCode", srCode);
			updateData.put("svcCompl", svcCompl);
			updateData.put("blocked", blocked);
			
			commonService.update("esm_SQL.updateESMSR", updateData);	
			// Sr 첨부파일 등록 : TB_SR_FILE 
			insertESMSRFiles(commandMap, srID);
			//Save PROC_LOG
			Map setProcMapRst = (Map)setProcLog(request, commonService, updateData);
			//System.out.println("setProcMapRst....."+setProcMapRst.get("type"));			
			if(setProcMapRst.get("type").equals("FAILE")){
				String Msg = StringUtil.checkNull(setProcMapRst.get("msg"));
				System.out.println("Msg : "+Msg);
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "parent.fnGoSRList();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}

		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);
	}
}
