package xbolt.custom.sk.cts;


import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.DateUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;
import xbolt.custom.sk.val.skGlobalVal;


/**
 * 업무 처리
 * 
 * @Class Name : ChangeInfoActionController.java.java
 * @Description : CTS 관리
 * @Modification Information
 * @수정일 수정자 수정내용
 * @--------- --------- -------------------------------
 * @2015. 02. 02. 이성민 최초생성
 * 
 * @since @2015. 02. 02.
 * @version 1.0
 */

@Controller
@SuppressWarnings("unchecked")
public class CtsActionController extends XboltController {

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "ctsService")
	private CommonService ctsService;


	@RequestMapping("/ctsList.do")
	public String ctsList(HttpServletRequest request, HashMap<String, String> commandMap, ModelMap model) throws Exception {

		String url = "/custom/sk/cts/ctsList";
		Map<String, String> paramMap = new HashMap<String, String>();

		try {

			Map<String, String> labelMap = getLabel(request, commonService);

			paramMap.put("LanguageID", String.valueOf(commandMap.get("sessionCurrLangType")));
			
			List<Object> systemList    = commonService.selectList("sk_cts_SQL.getSystemList",    paramMap);
			List<Object> subSystemList = commonService.selectList("sk_cts_SQL.getSubSystemList", paramMap);
			List<Object> reqTypeList   = commonService.selectList("sk_cts_SQL.getReqTypeList",   paramMap);
			List<Object> progressList  = commonService.selectList("sk_cts_SQL.getProgressList",  paramMap);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			String thisYmd = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			cal.add(Calendar.DATE, -7);
			String beforeYmd = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			model.put("menu", labelMap); 

			model.put("thisYmd",   thisYmd);
			model.put("beforeYmd", beforeYmd);
			model.put("currPage",  StringUtil.checkNull(request.getParameter("currPage"), "1"));
			
			model.put("systemList",    systemList);
			model.put("subSystemList", subSystemList);
			model.put("reqTypeList",   reqTypeList);
			model.put("progressList",  progressList);
			
			//sendMailHtml();

		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("EM00001");
		}
		return nextUrl(url);
	}
	
	@RequestMapping("/ctsCount.do")
	public String ctsCount(HttpServletRequest request, Map<Object, Object> commandMap, ModelMap model) throws Exception{
		HashMap target = new HashMap();
		try{
			Map<String, String> paramMap = new HashMap<String, String>();
			
			String systemCD    = StringUtil.checkNull(request.getParameter("systemCD"));
			String subSystemCD = StringUtil.checkNull(request.getParameter("subSystemCD"));
			String reqTypeCD   = StringUtil.checkNull(request.getParameter("reqTypeCD"));
			String progressCD  = StringUtil.checkNull(request.getParameter("progressCD"));
			String reqLoginID  = StringUtil.checkNull(request.getParameter("reqLoginID"));
			String reqSubject  = StringUtil.checkNull(request.getParameter("reqSubject"));
			String reqStartDT  = StringUtil.checkNull(request.getParameter("reqStartDT"));
			String reqEndDT    = StringUtil.checkNull(request.getParameter("reqEndDT"));
			
			paramMap.put("systemCD",    systemCD);
			paramMap.put("subSystemCD", subSystemCD);
			paramMap.put("reqTypeCD",   reqTypeCD);
			paramMap.put("progressCD",  progressCD);
			paramMap.put("reqLoginID",  reqLoginID);
			paramMap.put("reqSubject",  reqSubject);
			paramMap.put("reqStartDT",  reqStartDT);
			paramMap.put("reqEndDT",    reqEndDT);
			
			Map cntMap = commonService.select("sk_cts_SQL.getCTSCntFirst", paramMap);
			
			HttpSession session = request.getSession(true); 
			Map loginInfo = (Map)session.getAttribute("loginInfo");
			if(loginInfo == null || loginInfo.size() == 0) {
				cntMap.put("SessionYN", "N");
			} else {
				cntMap.put("SessionYN", "Y");
			}
			
			//model.put("cntMap", cntMap);
    		String TOTAL_CNT = StringUtil.checkNull(cntMap.get("TOTAL_CNT"));
    		String CD_01 = StringUtil.checkNull(cntMap.get("CD_01"));
    		String CD_02 = StringUtil.checkNull(cntMap.get("CD_02"));
    		String CD_03 = StringUtil.checkNull(cntMap.get("CD_03"));
    		String CD_04 = StringUtil.checkNull(cntMap.get("CD_04"));
    		String CD_06 = StringUtil.checkNull(cntMap.get("CD_06"));
    		String CD_07 = StringUtil.checkNull(cntMap.get("CD_07"));
    		String CD_08 = StringUtil.checkNull(cntMap.get("CD_08"));
    		String CD_09 = StringUtil.checkNull(cntMap.get("CD_09"));
    		System.out.println("TOTAL_CNT :"+TOTAL_CNT);
			target.put(AJAX_SCRIPT, "fnSetCtsCount('"+TOTAL_CNT+"','"+CD_01+"','"+CD_02+"','"+CD_03+"','"+CD_04+"','"+CD_06+"','"+CD_07+"','"+CD_08+"','"+CD_09+"');parent.$('#isSubmit').remove();");
		
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		model.addAttribute(AJAX_RESULTMAP, target);
		return nextUrl(AJAXPAGE);		
	}
	
	
	@RequestMapping(value="/ctsNew.do")
	public String ctsNew(HttpServletRequest request, HashMap<String, String> commandMap, ModelMap model) throws Exception{
		try {
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("LanguageID", String.valueOf(commandMap.get("sessionCurrLangType")));
			
			List<Object> systemList      = commonService.selectList("sk_cts_SQL.getSystemList",      paramMap);
			List<Object> subSystemList   = commonService.selectList("sk_cts_SQL.getSubSystemList",   paramMap);
			List<Object> reqTypeList     = commonService.selectList("sk_cts_SQL.getReqTypeList",     paramMap);
			List<Object> progressList    = commonService.selectList("sk_cts_SQL.getProgressList",    paramMap);
			List<Object> urgentTypeList  = commonService.selectList("sk_cts_SQL.getUrgentTypeList",  paramMap);
			List<Object> urgentExecList  = commonService.selectList("sk_cts_SQL.getUrgentExecList",  paramMap);
			
			model.put("menu", getLabel(request, commonService)); 
			
			model.put("CTSReqID",        getCTSReqID());
			
			model.put("systemList",      systemList);
			model.put("subSystemList",   subSystemList);
			model.put("reqTypeList",     reqTypeList);
			model.put("progressList",    progressList);
			model.put("urgentTypeList",  urgentTypeList);
			model.put("urgentExecList",  urgentExecList);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			String thisYmd  = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			String thisTime = new SimpleDateFormat("hh:mm").format(cal.getTime());
			
			model.put("thisYmd",   thisYmd);
			model.put("thisTime",  thisTime);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return nextUrl("/custom/sk/cts/ctsNew");
	}
	
	@RequestMapping(value="/ctsSave.do")
	public String ctsSave(MultipartHttpServletRequest request, HashMap<String, String> commandMap, ModelMap model) throws Exception{		
		HashMap<String, String> target = new HashMap<String, String>();
		String ctsReqID   = getCTSReqID();
		Map<String, String> failParam = new HashMap<String, String>();
		failParam.put("CtsReqID", ctsReqID);
		
		try{
			
			HashMap<String, String> insertParam = new HashMap<String, String>();

			//String ctsReqID   = getCTSReqID(); 160120 예외처리 적용 조성우
			String reqContent = request.getParameter("reqContent");
			String pgStatus   = request.getParameter("progressCD");
			String urgentYN   = request.getParameter("urgentYN");

			
			// CTS INSERT
			failParam.put("step", "CTS");
			insertParam.put("CtsReqID",           ctsReqID);
			insertParam.put("SystemCD",           request.getParameter("systemCD"));
			insertParam.put("SubSystemCD",        request.getParameter("subSystemCD"));
			insertParam.put("ProgressCD",         pgStatus);
			insertParam.put("ReqTypeCD",          request.getParameter("reqTypeCD"));
			insertParam.put("ReqLoginID",         request.getParameter("reqLoginID"));
			insertParam.put("ReqDate",            request.getParameter("reqDate"));
			insertParam.put("ReqTime",            request.getParameter("reqTime"));
			insertParam.put("DueDate",            request.getParameter("dueDate"));
			insertParam.put("ReqSubject",         request.getParameter("reqSubject"));
			insertParam.put("ReqContent",         reqContent);
			insertParam.put("ApplySystemClient",  request.getParameter("applySystemClient"));
			insertParam.put("HiSOSReqInfo",       request.getParameter("hiSOSReqInfo"));
			insertParam.put("MpmDocumentInfo",    request.getParameter("mpmDocumentInfo"));
			insertParam.put("TestResult",         request.getParameter("testResult"));
			insertParam.put("FtReviewLoginID",    request.getParameter("ftReviewLoginID"));
			insertParam.put("FtReviewDate",       StringUtil.checkNull(request.getParameter("ftReviewDate")));
			insertParam.put("FtReviewTime",       request.getParameter("ftReviewTime"));
			insertParam.put("FtReviewOpinion",    request.getParameter("ftReviewOpinion"));
			insertParam.put("SdReviewLoginID",    request.getParameter("sdReviewLoginID"));
			insertParam.put("SdReviewDate",       request.getParameter("sdReviewDate"));
			insertParam.put("SdReviewTime",       request.getParameter("sdReviewTime"));
			insertParam.put("SdReviewOpinion",    request.getParameter("sdReviewOpinion"));
			insertParam.put("ApproveLoginID",     request.getParameter("approveLoginID"));
			insertParam.put("ApproveDate",        request.getParameter("approveDate"));
			insertParam.put("ApproveTime",        request.getParameter("approveTime"));
			insertParam.put("ApproveOpinion",     request.getParameter("approveOpinion"));
			insertParam.put("ProcessLoginID",     request.getParameter("processLoginID"));
			insertParam.put("ProcessDate",        request.getParameter("processDate"));
			insertParam.put("ProcessTime",        request.getParameter("processTime"));
			insertParam.put("ProcessOpinion",     request.getParameter("processOpinion"));
			insertParam.put("ModuleEffectYN",     request.getParameter("moduleEffectYN"));
			insertParam.put("ModuleReviewResult", request.getParameter("moduleReviewResult"));
			insertParam.put("UrgentYN",           urgentYN);
			insertParam.put("UrgentTypeCD",       request.getParameter("urgentTypeCD"));
			insertParam.put("UrgentExecCD",       request.getParameter("urgentExecCD"));
			insertParam.put("UrgentReason",       request.getParameter("urgentReason"));
			insertParam.put("Etc01",              request.getParameter("etc01"));
			insertParam.put("Etc02",              request.getParameter("etc02"));
			commonService.insert("sk_cts_SQL.insertCTS", insertParam);
			
			
			// CTS HISTORY INSERT
			failParam.put("step", "HIS");
			String reqLoginNM = request.getParameter("reqLoginNM");			
			String historySeq = getCTSHistorySeq(ctsReqID);
			
			insertParam.put("UpdateLoginNM", reqLoginNM);
			insertParam.put("UpdateContent", reqContent);
			insertParam.put("HistorySeq", historySeq);
			commonService.insert("sk_cts_SQL.insertCTSHistory", insertParam);
			
			
			/* 160120 CTS TEST 첨부파일 로직 추가 */			
			failParam.put("step", "ATTCH");
			failParam.put("HistorySeq", historySeq);
			
			Iterator fileNameIter = request.getFileNames();
			String regDate = DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			insertParam.put("RegDT", regDate);	
			insertParam.put("ProcessCD", "01");	// 공통코드 :: TEST 첨부
			if(fileNameIter != null){
				int attachCnt = 0;
				while (fileNameIter.hasNext()) {
					MultipartFile mFile = request.getFile((String)fileNameIter.next());
					if (ctsAttachFile(mFile, insertParam)) {
						attachCnt++;
					}
				}
				
				System.out.println(  "=============================================================\n\n"
									+"CTS ATTACH FILE REGIST COUNT :: " + attachCnt + "건"
									+"\n=============================================================");
				/*
				logger.debug("=============================================================\n\n"
							+"CTS ATTACH FILE REGIST COUNT :: " + attachCnt + "건"
							+"\n=============================================================");
				*/
			}

			
			// 긴급인 경우, 결재라인 대상자에 메일 보내기 
			if("Y".equals(urgentYN)){
				sendMailUrgent(ctsReqID);	
			}
			
			// 생성 시 검토자 메일 발송 
			sendMailCreate(ctsReqID);	
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "parent.returnSaveCts();");
			
		}catch(Exception e){
			//logger.debug(e.getMessage());
			
			// 실패처리
			ctsFailProc(failParam);
			
			target.put(AJAX_SCRIPT, "this.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
		model.addAttribute(AJAX_RESULTMAP, target);
		
		return nextUrl(AJAXPAGE);
	}	
	
	@RequestMapping(value="/ctsUpdate.do")
	public String ctsUpdate(HttpServletRequest request, HashMap<String, String> commandMap, ModelMap model) throws Exception{		
		HashMap<String, String> target = new HashMap<String, String>();
		
		try{
			
			String ctsReqID     = request.getParameter("CtsReqID");
			String prePgStatus  = request.getParameter("progressCD");
			//String nextPgStatus = "";
			//String histPgStatus = "";
			String sqlID        = "sk_cts_SQL.updateCTS";
			
			String UpdateContent = "";
			String UpdateLoginNM = "";
			
			Map<String, String> updateParam = new HashMap<String, String>();
			updateParam.put("CtsReqID",           ctsReqID);
			if("01".equals(prePgStatus)){
				UpdateLoginNM = request.getParameter("reqLoginNM");
				UpdateContent = request.getParameter("reqContent");
			} else if("02".equals(prePgStatus)){
				UpdateContent = "";
				UpdateLoginNM = request.getParameter("reqLoginNM");
			// 	1차 검토
			} else if("03".equals(prePgStatus)){
				UpdateContent = request.getParameter("ftReviewOpinion");
				UpdateLoginNM = request.getParameter("ftReviewLoginNM");
				sqlID = "sk_cts_SQL.updateCTSFtReview";
			// 2차 검토
			} else if("04".equals(prePgStatus)){
				UpdateContent = request.getParameter("sdReviewOpinion");
				UpdateLoginNM = request.getParameter("sdReviewLoginNM");
				sqlID = "sk_cts_SQL.updateCTSSdReview";
			// 승인요청
			} else if("05".equals(prePgStatus)){
				UpdateLoginNM = commandMap.get("sessionUserNm");
				sqlID = "sk_cts_SQL.updateCTSApprove";	
			// 승인완료
			} else if("06".equals(prePgStatus)){
				UpdateContent = request.getParameter("approveOpinion");
				UpdateLoginNM = request.getParameter("approveLoginNM");
				sqlID = "sk_cts_SQL.updateCTSApprove";
			// 처리완료	
			} else if("07".equals(prePgStatus)){
				UpdateContent = request.getParameter("processOpinion");
				UpdateLoginNM = request.getParameter("processLoginNM");
				
				sqlID = "sk_cts_SQL.updateCTSProcess";
			// 반려	
			} else if("08".equals(prePgStatus)){
				String pgCD = request.getParameter("currentPgCD");
				if("01".equals(pgCD)){
					UpdateContent = request.getParameter("ftReviewOpinion");
					UpdateLoginNM = request.getParameter("ftReviewLoginNM");
					sqlID = "sk_cts_SQL.updateCTSFtReview";
				/*	
				} else if("03".equals(pgCD)){
					UpdateContent = request.getParameter("sdReviewOpinion");
					UpdateLoginNM = request.getParameter("sdReviewLoginNM");
					sqlID = "sk_cts_SQL.updateCTSSdReview";
				*/
				} else if("03".equals(pgCD)){
					UpdateContent = request.getParameter("approveOpinion");
					UpdateLoginNM = request.getParameter("approveLoginNM");
					sqlID = "sk_cts_SQL.updateCTSApprove";	
				}
			// 종결	
			} else if("09".equals(prePgStatus)){
				UpdateContent = "";
				UpdateLoginNM = commandMap.get("sessionUserNm");
				sqlID = "sk_cts_SQL.updateCTSEnd";	
			}
			
			
			updateParam.put("SystemCD",           request.getParameter("systemCD"));
			updateParam.put("SubSystemCD",        request.getParameter("subSystemCD"));
			//updateParam.put("ProgressCD",         nextPgStatus);
			updateParam.put("ProgressCD",         prePgStatus);
			updateParam.put("ReqTypeCD",          request.getParameter("reqTypeCD"));
			updateParam.put("ReqLoginID",         request.getParameter("reqLoginID"));
			updateParam.put("ReqDate",            request.getParameter("reqDate"));
			updateParam.put("ReqTime",            request.getParameter("reqTime"));
			updateParam.put("DueDate",            request.getParameter("dueDate"));
			updateParam.put("ReqSubject",         request.getParameter("reqSubject"));
			updateParam.put("ReqContent",         request.getParameter("reqContent"));
			updateParam.put("ApplySystemClient",  request.getParameter("applySystemClient"));
			updateParam.put("HiSOSReqInfo",       request.getParameter("hiSOSReqInfo"));
			updateParam.put("MpmDocumentInfo",    request.getParameter("mpmDocumentInfo"));
			updateParam.put("TestResult",         request.getParameter("testResult"));
			updateParam.put("FtReviewLoginID",    request.getParameter("ftReviewLoginID"));
			updateParam.put("FtReviewDate",       StringUtil.checkNull(request.getParameter("ftReviewDate")));
			updateParam.put("FtReviewTime",       request.getParameter("ftReviewTime"));
			updateParam.put("FtReviewOpinion",    request.getParameter("ftReviewOpinion"));
			updateParam.put("SdReviewLoginID",    request.getParameter("sdReviewLoginID"));
			updateParam.put("SdReviewDate",       request.getParameter("sdReviewDate"));
			updateParam.put("SdReviewTime",       request.getParameter("sdReviewTime"));
			updateParam.put("SdReviewOpinion",    request.getParameter("sdReviewOpinion"));
			updateParam.put("ApproveLoginID",     request.getParameter("approveLoginID"));
			updateParam.put("ApproveDate",        request.getParameter("approveDate"));
			updateParam.put("ApproveTime",        request.getParameter("approveTime"));
			updateParam.put("ApproveOpinion",     request.getParameter("approveOpinion"));
			updateParam.put("ProcessLoginID",     request.getParameter("processLoginID"));
			updateParam.put("ProcessDate",        request.getParameter("processDate"));
			updateParam.put("ProcessTime",        request.getParameter("processTime"));
			updateParam.put("ProcessOpinion",     request.getParameter("processOpinion"));
			updateParam.put("ModuleEffectYN",     request.getParameter("moduleEffectYN"));
			updateParam.put("ModuleReviewResult", request.getParameter("moduleReviewResult"));
			updateParam.put("UrgentYN",           request.getParameter("urgentYN"));
			updateParam.put("UrgentTypeCD",       request.getParameter("urgentTypeCD"));
			updateParam.put("UrgentExecCD",       request.getParameter("urgentExecCD"));
			updateParam.put("UrgentReason",       request.getParameter("urgentReason"));
			updateParam.put("Etc01",              request.getParameter("etc01"));
			updateParam.put("Etc02",              request.getParameter("etc02"));
			commonService.update(sqlID, updateParam);
			
			
			// CTS HISTORY INSERT
			// 01 : 생성인 경우 이력 쌓지 않는다.
			if("01".equals(prePgStatus)){
				updateParam.put("UpdateLoginNM", UpdateLoginNM);
				updateParam.put("UpdateContent", UpdateContent);
				commonService.update("sk_cts_SQL.updateCTSHistory", updateParam);
			} else {
				updateParam.put("UpdateLoginNM", UpdateLoginNM);
				updateParam.put("UpdateContent", UpdateContent);
				updateParam.put("HistorySeq",    getCTSHistorySeq(ctsReqID));
				//updateParam.put("ProgressCD",    histPgStatus);
				updateParam.put("ProgressCD",    prePgStatus);
				commonService.insert("sk_cts_SQL.insertCTSHistory", updateParam);				
			}

			
//			if("04".equals(prePgStatus)){
//				updateParam.put("UpdateLoginNM", UpdateLoginNM);
//				updateParam.put("UpdateContent", "");
//				updateParam.put("HistorySeq",    getCTSHistorySeq(ctsReqID));
//				//updateParam.put("ProgressCD",    nextPgStatus);
//				updateParam.put("ProgressCD",    prePgStatus);
//				commonService.insert("sk_cts_SQL.insertCTSHistory", updateParam);				
//			}
			
			
			// 메일발송 (1차검토완료)
			if("03".equals(prePgStatus)){
				sendMailReview(ctsReqID);	
			}
						
			// 메일발송 (작업완료)
			if("07".equals(prePgStatus)){
				sendMailComplete(ctsReqID);	
			}
			
			// 메일발송 (검토반려,승인반려)
			if("08".equals(prePgStatus)){
				String pgCD = request.getParameter("currentPgCD");
				if("01".equals(pgCD)){
					sendMailRejectReview(ctsReqID);	
				} else if("03".equals(pgCD)){
					sendMailRejectApprove(ctsReqID);	
				}
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "parent.returnUpdateCts();");
			
		}catch(Exception e){
			System.out.println(e);
			target.put(AJAX_SCRIPT, "this.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
		model.addAttribute(AJAX_RESULTMAP, target);
		
		return nextUrl(AJAXPAGE);
	}	
	
	@RequestMapping(value="/ctsDetail.do")
	public String ctsDetail(HttpServletRequest request, HashMap<String, String> commandMap, ModelMap model) throws Exception{
		try {
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("LanguageID", String.valueOf(commandMap.get("sessionCurrLangType")));
			
			List<Object> systemList      = commonService.selectList("sk_cts_SQL.getSystemList",      paramMap);
			List<Object> subSystemList   = commonService.selectList("sk_cts_SQL.getSubSystemList",   paramMap);
			List<Object> reqTypeList     = commonService.selectList("sk_cts_SQL.getReqTypeList",     paramMap);
			List<Object> progressList    = commonService.selectList("sk_cts_SQL.getProgressList",    paramMap);
			List<Object> urgentTypeList  = commonService.selectList("sk_cts_SQL.getUrgentTypeList",  paramMap);
			List<Object> urgentExecList  = commonService.selectList("sk_cts_SQL.getUrgentExecList",  paramMap);
			
			model.put("menu", getLabel(request, commonService)); 
			
			model.put("CTSReqID",        getCTSReqID());
			
			model.put("systemList",      systemList);
			model.put("subSystemList",   subSystemList);
			model.put("reqTypeList",     reqTypeList);
			model.put("progressList",    progressList);
			model.put("urgentTypeList",  urgentTypeList);
			model.put("urgentExecList",  urgentExecList);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			String thisYmd  = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			String thisTime = new SimpleDateFormat("HH:mm").format(cal.getTime());
			
			model.put("thisYmd",   thisYmd);
			model.put("thisTime",  thisTime);
			
			
			Map<Object, Object> detailMap = new HashMap<Object, Object>();
			paramMap.put("CtsReqID",    request.getParameter("CtsReqID"));
			detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
			
			// 160125 CTS 첨부파일 리스트 로직 추가
			List<Object> attchFileList   = commonService.selectList("sk_cts_SQL.getAttchFileList", paramMap); 
			
			model.put("detailMap", detailMap);
			model.put("attchFileList", attchFileList);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return nextUrl("/custom/sk/cts/ctsDetail");
	}
	
	@RequestMapping("/ctsDelete.do")
	public String ctsDelete(HttpServletRequest request,	HashMap<String, String> commandMap, ModelMap model) throws Exception {
		Map<String, String> target = new HashMap<String, String>();

		try {
			Map<String, String> paramMap = new HashMap<String, String>();

			String ctsReqIDs = StringUtil.checkNull(request.getParameter("ctsReqIDs"), "");
			String[] ctsReqIDArray;

			if (!ctsReqIDs.isEmpty()) {
				ctsReqIDArray = ctsReqIDs.split(","); 

				for (int i = 0; i < ctsReqIDArray.length; i++) {
					String ctsReqID = ctsReqIDArray[i];
					paramMap.put("CtsReqID", ctsReqID);
					
					// 160125 CTS Attach File 삭제처리 조성우
					commonService.delete("sk_cts_SQL.deleteCTSAttch", paramMap);
					
					// 160125 deleteCTS CTS MASTER와 HISTORY 분리처리 조성우
					commonService.delete("sk_cts_SQL.deleteCTSHistory", paramMap);
					
					commonService.delete("sk_cts_SQL.deleteCTS", paramMap);	
				}
			}

			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00069")); // 삭제 성공
			target.put(AJAX_SCRIPT, "this.$('#isSubmit').remove();this.doSearchCtsList();");
		} catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "this.$('#isSubmit').remove()");
			target.put(AJAX_ALERT,MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00070")); // 삭제 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	

	/* 160121 CTS FAIL 처리 */
	private void ctsFailProc(Map<String, String> failParam) {
		try {
			String failStep = null;
			failStep = failParam.get("step");
	
			if (failStep == null || failStep.isEmpty()) {
				return;
			} 
			
			if ("CTS".equals(failStep)) {
				// CTS 등록하다 에러
				;;
			} else if ("HIS".equals(failStep)) {
				// HIS 등록하다 에러
				commonService.delete("sk_cts_SQL.deleteCTS" , failParam);
				
			} else if ("ATTCH".equals(failStep)) {
				// ATTCH 등록하다 에러
				commonService.delete("sk_cts_SQL.deleteCTSHistory" , failParam);
				commonService.delete("sk_cts_SQL.deleteCTS" , failParam);
			}
			/*
			logger.error("=============================================================\n\n"
						+"CTS REGIST ERROR POINT :: " + ((failStep == null || failStep.isEmpty()) ? "Not Catch Point" : failStep)
						+"\n=============================================================");
			*/
		} catch (Exception e) {
			//logger.error("" + e.getMessage());
		}
	}
	
	/* 160120 CTS TEST 첨부파일 로직 */
	private boolean ctsAttachFile(MultipartFile mFile, HashMap<String, String> commandMap) {
		boolean result = true;
		
		try {
			String savePath = skGlobalVal.FILE_UPLOAD_CTS_DIR; // C://Tomcat 7.0//webapps//ROOT//doc//cts//
			
			int Seq = Integer.parseInt(commonService.selectString("sk_cts_SQL.getCtsFile_nextVal", commandMap));
			
			if (mFile.getSize() > 0) {
				
				Map<String, Object> fileMap = new HashMap<String, Object>();
				HashMap<String, String> resultMap = FileUtil.uploadFile(mFile, savePath, true);
				
			    fileMap.put("CtsReqID", commandMap.get("CtsReqID"));		// PK1
			    fileMap.put("ProcessCD", commandMap.get("ProcessCD"));		// PK2
			    fileMap.put("Seq", Seq);									// PK3
				
				fileMap.put("FileNM", resultMap.get(FileUtil.UPLOAD_FILE_NM));			// 코드화된 파일명
				fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));	// 원래 첨부파일명
				fileMap.put("FilePath", resultMap.get(FileUtil.FILE_PATH));				// 파일경로
				fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));				// 파일사이즈
	
				fileMap.put("RegDT", commandMap.get("RegDT"));							// 등록일자
				fileMap.put("RegUserID", commandMap.get("ReqLoginID"));					// 등록자ID
				fileMap.put("DownCNT", 0);												// 다운횟수
				
				ctsService.save(fileMap);
			}
			
		} catch(Exception e) {
			/*
			logger.error("=============================================================\n\n"
						+"CTS ATTACH FILE ERROR MESSAGE >>> CtsReqID :: " + commandMap.get("CtsReqID") + ", ProcessCD :: " + commandMap.get("ProcessCD") + ", Seq :: " + commandMap.get("Seq") + "\n\n"
						+"                      Message >>> " + e.getMessage()
						+"\n=============================================================");
			*/
		}
		
		return result;
	}
	
	private String getCTSReqID() throws Exception{
		StringBuffer ctsReqID = new StringBuffer();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		long date = System.currentTimeMillis();
		String nowDate = formatter.format(date);
		
		ctsReqID.append("CTS-");
		ctsReqID.append(nowDate);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prefix",    ctsReqID.toString());
		String maxSeq = commonService.selectString("sk_cts_SQL.getMaxCTSReqID", paramMap);
		ctsReqID.append("-");
		ctsReqID.append(maxSeq);
		
		return ctsReqID.toString();
		
	}
	
	private String getCTSHistorySeq(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("CtsReqID",    ctsReqID);
		String maxSeq = commonService.selectString("sk_cts_SQL.getMaxCTSHistorySeq", paramMap);
		
		return maxSeq;
		
	}
	
	public void sendMailUrgent(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : CTS 긴급 진행 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddrList = commonService.selectString("sk_cts_SQL.getReqEmailAddrUrgent", paramMap);
		String[] mailAddrArr = emailAddrList.split(",");
		for(int idx=0; idx<mailAddrArr.length; idx++){
			if(!"".equals(mailAddrArr[idx])){
				 HtmlEmail email = new HtmlEmail();

				 email.setCharset("UTF-8");     
				 email.setHostName(hostIP);
				 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
				 email.addTo(mailAddrArr[idx]); 
				 email.setSubject(mailTitle);
				 email.setHtmlMsg(getHtmlMsgUrgent(detailMap));

				 email.send();
			}
		}

		
	}
	
	public void sendMailComplete(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : 작업 완료 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddr = commonService.selectString("sk_cts_SQL.getReqEmailAddr", paramMap);
		if(!"N".equals(emailAddr)){
			 HtmlEmail email = new HtmlEmail();

			 email.setCharset("UTF-8");     
			 email.setHostName(hostIP);
			 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
			 email.addTo(emailAddr); 
			 email.setSubject(mailTitle);
			 email.setHtmlMsg(getHtmlMsgComplete(detailMap));

			 email.send();
		}
		
	}
	
	public void sendMailCreate(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : CTS 검토 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddrList = commonService.selectString("sk_cts_SQL.getReqEmailAddrCreate", paramMap);
		String[] mailAddrArr = emailAddrList.split(",");
		for(int idx=0; idx<mailAddrArr.length; idx++){
			if(!"".equals(mailAddrArr[idx])){
				 HtmlEmail email = new HtmlEmail();				
				 
				 email.setCharset("UTF-8");     
				 email.setHostName(hostIP);	 
				 email.setAuthentication("support@smartfactory.co.kr", "support2016!");
				 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
				 email.addTo(mailAddrArr[idx]); 
				 email.setSubject(mailTitle);
				 email.setHtmlMsg(getHtmlMsgCreate(detailMap));				
				 email.send();
			}
		}

		
	}
	
	public void sendMailRejectReview(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : CTS 반려 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddrList = commonService.selectString("sk_cts_SQL.getReqEmailAddrRejectReview", paramMap);
		String[] mailAddrArr = emailAddrList.split(",");
		for(int idx=0; idx<mailAddrArr.length; idx++){
			if(!"".equals(mailAddrArr[idx])){
				 HtmlEmail email = new HtmlEmail();

				 email.setCharset("UTF-8");     
				 email.setHostName(hostIP);
				 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
				 email.addTo(mailAddrArr[idx]); 
				 email.setSubject(mailTitle);
				 email.setHtmlMsg(getHtmlMsgRejectReview(detailMap));

				 email.send();
			}
		}

		
	}
	
	public void sendMailRejectApprove(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : CTS 반려 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddrList = commonService.selectString("sk_cts_SQL.getReqEmailAddrRejectApprove", paramMap);
		String[] mailAddrArr = emailAddrList.split(",");
		for(int idx=0; idx<mailAddrArr.length; idx++){
			if(!"".equals(mailAddrArr[idx])){
				 HtmlEmail email = new HtmlEmail();

				 email.setCharset("UTF-8");     
				 email.setHostName(hostIP);
				 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
				 email.addTo(mailAddrArr[idx]); 
				 email.setSubject(mailTitle);
				 email.setHtmlMsg(getHtmlMsgRejectApprove(detailMap));

				 email.send();
			}
		}

		
	}
	
	public void sendMailReview(String ctsReqID) throws Exception{
		
		Map<String, String> paramMap  = new HashMap<String, String>();
		Map<Object, Object> detailMap = new HashMap<Object, Object>();
		paramMap.put("CtsReqID",    ctsReqID);
		
		detailMap = commonService.select("sk_cts_SQL.getCtsDetail", paramMap);
		
		String mailTitle = "[MPM] CTS관리 : CTS 승인 공지";
		String hostIP = GlobalVal.EMAIL_HOST_IP;
		String emailAddrList = commonService.selectString("sk_cts_SQL.getReqEmailAddrReview", paramMap);
		String[] mailAddrArr = emailAddrList.split(",");
		for(int idx=0; idx<mailAddrArr.length; idx++){
			if(!"".equals(mailAddrArr[idx])){
				 HtmlEmail email = new HtmlEmail();

				 email.setCharset("UTF-8");     
				 email.setHostName(hostIP);
				 email.setFrom(GlobalVal.EMAIL_SENDER, GlobalVal.EMAIL_SENDER_NAME); 
				 email.addTo(mailAddrArr[idx]); 
				 email.setSubject(mailTitle);
				 email.setHtmlMsg(getHtmlMsgReview(detailMap));

				 email.send();
			}
		}

		
	}
		
	private String getHtmlMsgComplete(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] 변경요청 작업완료 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>MPM시스템 - 변경관리 - CTS 관리 메뉴에서 요청하신 사항이 작업완료 되었습니다. ");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("감사합니다. </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title02'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_02.gif' width='13' height='13' align='absmiddle'> 기본 정보</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청제목</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqSubject"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>처리자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ProcessLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>처리일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ProcessDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>처리의견</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ProcessOpinion"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	private String getHtmlMsgCreate(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] 변경요청 검토 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>MPM시스템 - 변경관리 - CTS 관리 메뉴에서 아래 항목 건에 대하여 검토 작업이 필요합니다. ");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("감사합니다. </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title02'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_02.gif' width='13' height='13' align='absmiddle'> 기본 정보</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청제목</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqSubject"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	private String getHtmlMsgRejectReview(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] 변경요청 반려 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>MPM시스템 - 변경관리 - CTS 관리 메뉴에서 아래 항목 건에 대하여 반려처리 되었습니다. ");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("감사합니다. </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title02'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_02.gif' width='13' height='13' align='absmiddle'> 기본 정보</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청제목</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqSubject"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>1차 검토자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("FtReviewLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>반려의견</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("FtReviewOpinion"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	private String getHtmlMsgRejectApprove(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] 변경요청 반려 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>MPM시스템 - 변경관리 - CTS 관리 메뉴에서 아래 항목 건에 대하여 반려처리 되었습니다. ");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("감사합니다. </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title02'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_02.gif' width='13' height='13' align='absmiddle'> 기본 정보</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청제목</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqSubject"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>승인자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ApproveLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>반려의견</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ApproveOpinion"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	private String getHtmlMsgReview(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] 변경요청 승인 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>MPM시스템 - 변경관리 - CTS 관리 메뉴에서 아래 항목 건에 대하여 승인 작업이 필요합니다. ");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("감사합니다. </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title02'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_02.gif' width='13' height='13' align='absmiddle'> 기본 정보</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청제목</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqSubject"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청일자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqDate"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	private String getHtmlMsgUrgent(Map<Object, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type='text/css'>");
		sb.append("BODY {  margin-left: 0px;	margin-top: 0px;	margin-right: 0px;	margin-bottom: 0px; scrollbar-face-color: #FFFFFF; scrollbar-shadow-color: cccccc; scrollbar-highlight-color: #FFFFFF; scrollbar-3dlight-color: cccccc;   scrollbar-darkshadow-color: #FFFFFF;  scrollbar-track-color: #F3F3F3;  scrollbar-arrow-color: #189096;}");
		sb.append("FONT {	FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움';}");
		sb.append("TD {	FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';}");
		sb.append(".b {	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #5d5d5d; LINE-HEIGHT: normal;  font-family: '돋움';}");
		sb.append(".input {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; HEIGHT: 20px; BACKGROUND-COLOR: white}");
		sb.append(".select2 {	border:1px solid #7F9DB9; background-color:#ffffff; height:178px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".textarea {	BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; COLOR: #5D5D5D; BORDER-BOTTOM: 1px solid; FONT-FAMILY: '돋움'; BACKGROUND-COLOR: white}");
		sb.append("A:link {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:visited {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:hover {	COLOR: #F68022; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append("A:active {	COLOR: #5d5d5d; FONT-FAMILY: '돋움'; TEXT-DECORATION: none}");
		sb.append(".menu {	padding-top:3px; FONT-SIZE: 12px; COLOR: #ffffff;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;  }");
		sb.append(".menu A:link {	COLOR: #ffffff;  }");
		sb.append(".menu A:visited {	COLOR: #ffffff; }");
		sb.append(".menu A:hover {	COLOR: #ffffff;  }");
		sb.append(".menu A:active {	COLOR: #ffffff; }");
		sb.append(".main_small {	padding-top:3px; FONT-SIZE: 11px; COLOR: #7d7d7d;  font-family:  '돋움';TEXT-DECORATION: none; }");
		sb.append(".main_tab_o {	padding-top:6px; FONT-SIZE: 12px; COLOR: #119399;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append("a.main_tab_o:link , a.main_tab_o:visited , a.main_tab_o:active { text-decoration: none; color: #119399; font-weight:bold;}");
		sb.append("a.main_tab_o:hover { text-decoration:none; color: #F68022;}");
		sb.append(".main_tab {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5D5D5D;  font-family:  '돋움';TEXT-DECORATION: none; font-weight:bold; text-align:center;}");
		sb.append(".main_td {	padding-top:4px;}");
		sb.append(".title01 {	padding-top:3px; FONT-SIZE: 14px; COLOR: #333F3E;  font-family:  '돋움'; font-weight:bold; height:30;}");
		sb.append(".title01_line {	height:2; background-color: #48ADA9;}");
		sb.append(".title02 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #044841;  font-family:  '돋움'; font-weight:bold; height:24;}");
		sb.append(".input_usual {	border:1px solid #9EBACB; background-color:#FAF9F9; height:18px; color: #000000;  font-family:  '돋움';FONT-SIZE: 12px;  }");
		sb.append(".green_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #219A7C;  font-family:  '돋움'; }");
		sb.append(".orange_s {	padding-top:3px; FONT-SIZE: 11px; COLOR: #FF5800;  font-family:  '돋움'; }");
		sb.append(".green {	padding-top:3px; FONT-SIZE: 12px; COLOR: #219A7C;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".orange {	padding-top:3px; FONT-SIZE: 12px; COLOR: #FF5800;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".blue2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".red {	padding-top:3px; FONT-SIZE: 12px; COLOR: #F90F44;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q1 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #51A2A2;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q2 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #18827D;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_q3 {	padding-top:3px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; font-weight:bold; }");
		sb.append(".td_a1 {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".td_a1_b {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #0478D7;  font-family:  '돋움'; }");
		sb.append(".td_a1_c {	padding-top:3px; padding-left:10px; FONT-SIZE: 12px; COLOR: #FF0000;  font-family:  '돋움'; }");
		sb.append(".td_a2 { padding-top:3px; text-align:center; FONT-SIZE: 12px; COLOR: #5d5d5d;  font-family:  '돋움'; }");
		sb.append(".box01 {	background-color: #ffffff; padding:15;}");
		sb.append(".box02 {	background-color: #FAFEF4; padding:15;}");
		sb.append(".button_01 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_04.gif);  width:86px; height:22px; padding:2 3 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1; cursor:hand;}");
		sb.append(".button_02 {    border:0px solid #C7E3DF; background-image:url(http://mpm.skhynix.com/cmm/sk/images/mpm_mail_05.gif);  width:116px; height:22px; padding:2 4 0 0;  FONT-SIZE: 12px; COLOR: #18827D;  font-family: '돋움';TEXT-DECORATION: none;font-weight:bold;  letter-spacing:-1;cursor:hand;}");
		sb.append(".bar01 {	height:2; background-color: #89CBC8;}");
		sb.append(".zone01 { background-color: #FCFEF8;}");
		sb.append(".leftmargin5 { 	TEXT-ALIGN: left; PADDING-LEFT: 5px; }");
		sb.append(".leftmargin10	{ 	TEXT-ALIGN: left; PADDING-LEFT: 10px; }");
		sb.append(".leftmargin15	{ 	TEXT-ALIGN: left; PADDING-LEFT: 15px; }");
		sb.append(".leftmargin20	{ 	TEXT-ALIGN: left; PADDING-LEFT: 20px; }");
		sb.append(".rightmargin5	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 5px; }");
		sb.append(".rightmargin10	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 10px; }");
		sb.append(".rightmargin15 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 15px; }");
		sb.append(".rightmargin20 	{ 	TEXT-ALIGN: right; PADDING-RIGHT: 20px; }");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align='left' valign='top' style='padding:10 20 0 20;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td height='40'><img src='http://mpm.skhynix.com/cmm/sk/images/logo.png' width='200' height='40'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#E7E3DF' style='border-collapse: collapse;'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td bgcolor='#FFFFFF' style='padding:15;'>");
		sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td class='title01'><img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_03.gif' width='13' height='13' align='absmiddle'> [Notice] CTS 긴급 진행 안내 </td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class='title01_line'></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>아래와 같은 사유로 인해 CTS를 긴급 진행하오니, 테스트, 검토, 승인 확인 바랍니다. ");
		sb.append("<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table width='100%' border='1' bordercolor='#A6D4C7' cellspacing='0' cellpadding='0' class='table_border01'>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>요청자</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("ReqLoginNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>긴급 유형</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("UrgentTypeNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>긴급 수행 내용</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("UrgentExecNM"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='30%' height='25' align='left' bgcolor='#E7F6F3' class='td_q2' style='padding-left:15;'>");
		sb.append("<img src='http://mpm.skhynix.com/cmm/sk/images/mpm_mail_01.gif' width='5' height='11' align='absmiddle'>긴급 사유</td>");
		sb.append("<td class='td_a1'>");
		sb.append("<p><strong>");
		sb.append(paramMap.get("UrgentReason"));
		sb.append("<br>");
		sb.append("</strong></p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<BR>* 긴급 진행 건은 개인/사유 별로 취합 보고합니다.");
		sb.append("<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='20'></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	/**
	 * 160125 CTS Attach File Down 기능 추가
	 * 파일 다운로드 기능을 제공한다.
	 */
	@RequestMapping(value = "/ctsFileDown.do")
	public String ctsFileDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = request.getParameter("filename");
		String original = request.getParameter("original");

		Map<String, String> updateParam = new HashMap<String, String>();
		updateParam.put("CtsReqID", request.getParameter("CtsReqID"));
		updateParam.put("ProcessCD", request.getParameter("ProcessCD"));
		updateParam.put("Seq", request.getParameter("Seq"));
		
		String downFile = StringUtil.checkNull(request.getParameter("downFile"), "");
		if(downFile == null || downFile.equals("")) downFile = skGlobalVal.FILE_UPLOAD_CTS_DIR + filename; 

		String enOriginal = new String(original.getBytes("8859_1"), "UTF-8");
		//original = new String(original.getBytes("8859_1"), "UTF-8");
		

		System.out.println("enOriginal = "+enOriginal);
		System.out.println("original = "+original);
		
		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return "cmm/utl/EgovFileDown";
		}

		if ("".equals(original)) {
			original = filename;
		}

		request.setAttribute("downFile", downFile);
		// request.setAttribute("orginFile", enOriginal); 20140627 수정
		request.setAttribute("orginFile", original); 

		FileUtil.downFile(request, response);

		// 다운횟수 관리
		commonService.update("sk_cts_SQL.updateCtsAttchDownCNT", updateParam);
		return null;
	}	
	
	/* CTS ATTACH FILE DELETE 로직 */
	@RequestMapping(value="/ctsFileDelete.do")
	public String ctsFileDelete(HashMap cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();		
		cmmMap.put("CtsReqID", StringUtil.checkNull(cmmMap.get("CtsReqID"), ""));
		cmmMap.put("ProcessCD", StringUtil.checkNull(cmmMap.get("ProcessCD"), "01"));
		cmmMap.put("Seq", StringUtil.checkNull(cmmMap.get("Seq"), ""));
		
		target = commonService.select("sk_cts_SQL.ctsAttchFile_select", cmmMap);	//new mode

		try {
			
			String realFile = skGlobalVal.FILE_UPLOAD_CTS_DIR + target.get("FileName");
			File existFile = new File(realFile);
			existFile.delete();
			commonService.delete("sk_cts_SQL.deleteCTSAttch", cmmMap);	//new mode

			//target.put(AJAX_ALERT, "파일 삭제가 성공하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00075")); // 성공
		}
		catch (Exception e) {
			System.out.println(e);
			//target.put(AJAX_ALERT, "파일 삭제중 오류가 발생하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00076")); // 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}


}

