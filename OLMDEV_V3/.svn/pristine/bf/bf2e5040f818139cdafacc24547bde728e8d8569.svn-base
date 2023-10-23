package xbolt.file.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.json.JSONArray;
import com.org.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.filter.XSSRequestWrapper;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.DRMUtil;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;
import xbolt.cmm.framework.val.DrmGlobalVal;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 업무 처리
 * @Class Name : BizController.java
 * @Description : 업무화면을 제공한다.
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 */

@Controller
@SuppressWarnings("unchecked")

public class FileMgtActionController  extends XboltController{
	
	@Resource(name = "commonService")
	private CommonService commonService;
	@Resource(name = "fileMgtService")
	private CommonService fileMgtService;
	
	/** 문자 인코딩 */
	private static final String CHARSET = "euc-kr";

	@RequestMapping(value="/goFileMgt.do")
	public String goFileMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/fileMgt";
		String itemId = StringUtil.checkNull(request.getParameter("s_itemID"), "-1");
		String option  = StringUtil.checkNull(request.getParameter("option"), "-1");
		String varFilter  = StringUtil.checkNull(request.getParameter("varFilter"));
		String fileOption  = StringUtil.checkNull(request.getParameter("fileOption"),"OLM");
		try {
			model.put("DocumentID", itemId);
			model.put("option", option);
			//model.put("menu", getLabel(request, commonService));
			if(!"".equals(varFilter)){
				model.put("fileOption", varFilter);
				}else {
				model.put("fileOption", fileOption);
			}
			model.put("itemBlocked", StringUtil.checkNull(request.getParameter("itemBlocked"),""));
			model.put("backBtnYN", request.getParameter("backBtnYN"));
			model.put("langFilter", StringUtil.checkNull(request.getParameter("langFilter")));
		}
		
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goFileList.do")
	public String goFileList(HttpServletRequest request, HttpServletResponse response, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/fileList";
		String DocumentID = StringUtil.checkNull(request.getParameter("DocumentID"), "");	
		String fileOption = StringUtil.checkNull(request.getParameter("fileOption"),"OLM");	
		String varFilter = StringUtil.checkNull(request.getParameter("varFilter"));
		Map fileMap = new HashMap();
		Map getData = new HashMap();
		try {
			model.put("menu", getLabel(request, commonService));	/*Label Setting*/
			fileMap.put("itemId", DocumentID);
			Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",fileMap);
			String itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",fileMap);
			
			model.put("selectedItemAuthorID", StringUtil.checkNull(result.get("AuthorID")));
			model.put("selectedItemBlocked", StringUtil.checkNull(result.get("Blocked")));
			model.put("selectedItemLockOwner", StringUtil.checkNull(result.get("LockOwner")));
			model.put("itemClassCode", StringUtil.checkNull(result.get("ClassCode")));
			model.put("selectedItemStatus", StringUtil.checkNull(result.get("Status")));
			model.put("DocumentID", DocumentID);
			model.put("s_itemID", DocumentID);
		
			if(!"".equals(varFilter)){
			model.put("fileOption", varFilter);
			}else {
			model.put("fileOption", fileOption);
			}
			
			getData.put("DocumentID", DocumentID);
			getData.put("s_itemID", DocumentID);
			String page = StringUtil.checkNull(request.getParameter("page"), "1");
			String uploadYN = commonService.selectString("fileMgt_SQL.getFileUploadYN", fileMap);
			
			String sessionUserID = cmmMap.get("sessionUserId").toString();
			// Login user editor 권한 추가
			String sessionAuthLev = String.valueOf(cmmMap.get("sessionAuthLev")); // 시스템 관리자
			
			if (StringUtil.checkNull(result.get("AuthorID")).equals(sessionUserID)
					|| StringUtil.checkNull(result.get("LockOwner")).equals(cmmMap.get("sessionUserId"))
					|| "1".equals(sessionAuthLev)) {
				model.put("myItem", "Y");
			}
			
			fileMap.put("s_itemID", DocumentID);
			fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
			//List itemList = commonService.selectList("item_SQL.getCxnItemList", fileMap);
			fileMap.put("fromToItemID", DocumentID);
			List cxnItemIDList = commonService.selectList("item_SQL.getCxnItemIDList", fileMap);
			Map getMap = new HashMap();
			/** 첨부문서 관련문서 합치기, 관련문서 itemClassCodep 할당된 fltpCode 로 filtering */
			String rltdItemId = "";
			for(int i = 0; i < cxnItemIDList.size(); i++){
				getMap = (HashMap)cxnItemIDList.get(i);
				getMap.put("ItemID", getMap.get("ItemID"));
				if (i < cxnItemIDList.size() - 1) {
				   rltdItemId += StringUtil.checkNull(getMap.get("ItemID")) + ",";
				}else{
					rltdItemId += StringUtil.checkNull(getMap.get("ItemID")) ;
				}
			}
			// model.put("fileListYN", "Y");
			model.put("rltdItemId", rltdItemId);
			
			model.put("uploadYN", uploadYN);
			model.put("itemFileOption", itemFileOption);
			model.put("frmType", request.getParameter("frmType"));
			model.put("itemBlocked", StringUtil.checkNull(result.get("Blocked"), "")); 
			model.put("backBtnYN", request.getParameter("backBtnYN"));	
			
			String langFilter = StringUtil.checkNull(cmmMap.get("langFilter"));
			getData.put("languageID", cmmMap.get("sessionCurrLangType"));
			getData.put("langFilter", langFilter);
			
			getData.put("rltdItemId", rltdItemId);
			getData.put("isPublic", "N");
			getData.put("DocCategory", "ITM");
			getData.put("hideBlocked", "N");
			
			List fileList = commonService.selectList("fileMgt_SQL.getFile_gridList",getData);
			JSONArray gridData = new JSONArray(fileList);
			model.put("gridData", gridData);
			
			String defaultLangCode = commonService.selectString("common_SQL.languageCode", getData);
			model.put("defaultLangCode", defaultLangCode);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/saveFileDetail.do")
	public String saveFileDetail(MultipartHttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		XSSRequestWrapper xss = new XSSRequestWrapper(request);
		
		for (Iterator i = cmmMap.entrySet().iterator(); i.hasNext();) {
		    Entry e = (Entry) i.next(); // not allowed
		    if(!e.getKey().equals("loginInfo") && e.getValue() != null) {
		    	cmmMap.put(e.getKey(), xss.stripXSS(e.getValue().toString()));
		    }
		}
		String Grp = StringUtil.checkNull(xss.getParameter("Grp"), "");
		String url = "file/fileGrpMgt";
		String fileSeq = StringUtil.checkNull(xss.getParameter("fileSeq"), "");
		String DocumentID = StringUtil.checkNull(xss.getParameter("DocumentID"), "");
		String fltpCode = StringUtil.checkNull(xss.getParameter("fltpCode"), "");
		String fileMgt = StringUtil.checkNull(xss.getParameter("fileMgt"), "");
		String linkType = StringUtil.checkNull(xss.getParameter("linkType"), "1");
		String userId = StringUtil.checkNull(xss.getParameter("userId"), "");
		String ID = StringUtil.checkNull(xss.getParameter("s_itemID"), "");
		
		List fileList = new ArrayList();
		Map fileMap = null;
		Map stusMap = new HashMap();
		try{
			String filePath = StringUtil.checkNull(fileMgtService.selectString("fileMgt_SQL.getFilePath",cmmMap));  			
			stusMap.put("itemId", DocumentID);
			Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",stusMap);
			String Status = result.get("Status").toString();
			
			String itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",stusMap);
						
			if("".equals(fileSeq)){ // 신규 등록
				
				Iterator fileNameIter = request.getFileNames();
				// String savePath = GlobalVal.FILE_UPLOAD_ITEM_DIR; // 폴더 바꾸기
				String savePath = filePath; // 폴더 바꾸기
				
				String fileName = "";
				int Seq = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", cmmMap));
				int seqCnt = 0;				
				while (fileNameIter.hasNext()) {
					   MultipartFile mFile = request.getFile((String)fileNameIter.next());
					   fileName = mFile.getName();
						
						if("VIEWER".equals(itemFileOption))
							filePath = "";
						
					   if (mFile.getSize() > 0) {						   
						   fileMap = new HashMap();
						   HashMap resultMap = FileUtil.uploadFile(mFile, savePath, true);
						   
						   fileMap.put("Seq", Seq);
						   fileMap.put("DocumentID", DocumentID);
						   fileMap.put("LinkType", linkType);
						   fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
						   fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
						   fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
						   fileMap.put("FilePath", resultMap.get(FileUtil.FILE_PATH));	
						   fileMap.put("FileFormat", resultMap.get(FileUtil.FILE_EXT));	
						   fileMap.put("FltpCode", fltpCode);
						   fileMap.put("FileMgt", fileMgt);
						   fileMap.put("userId", userId);
						   fileMap.put("projectID", StringUtil.checkNull(result.get("ProjectID")));
						   fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
						   fileMap.put("KBN", "insert");
						   fileMap.put("DocCategory", "ITM");
						   fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");
						
						   fileList.add(fileMap);
						   seqCnt++;
					   }
					}	
					fileMgtService.save(fileList, fileMap);
				
			}else{ // 파일 수정

				Iterator fileNameIter = request.getFileNames();
				String savePath = filePath;
				String fileName = "";
				int Seq = Integer.parseInt(fileSeq);
				int seqCnt = 0;
				
				while (fileNameIter.hasNext()) {
					   MultipartFile mFile = request.getFile((String)fileNameIter.next());
					   fileName = mFile.getName();
					   
					   if (mFile.getSize() > 0) {
						   
						   fileMap = new HashMap();
						   HashMap resultMap = FileUtil.uploadFile(mFile, savePath, true);
						   
						   if("VIEWER".equals(itemFileOption))
								fileMap.put("FilePath", "VIEWER");
						   
						   fileMap.put("Seq", fileSeq);
						   fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
						   fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
						   fileMap.put("fltpCode", fltpCode);
						   fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
						   fileMap.put("KBN", "update");
						   fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_update"); 
						   
						   fileList.add(fileMap);
						   seqCnt++;
					   }
					}	
					fileMgtService.save(fileList, fileMap);
				
			}
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT,  "parent.fnGoList();");
			
		}catch (Exception e) {
				System.out.println(e);
				throw new ExceptionUtil(e.toString());
			}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/fileDownload.do")
	public String fileDownload( HttpServletRequest request, HttpServletResponse response, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		
		Map target = new HashMap();
		Map setMap = new HashMap();
		Map setData = new HashMap();
		
		
		String reqOrgiFileName[] = request.getParameter("seq").split(",");//StringUtil.checkNull(request.getParameter("originalFileName")).split(",");
		String reqSysFileName[] = request.getParameter("seq").split(",");//StringUtil.checkNull(request.getParameter("sysFileName")).split(",");
		String reqFilePath[] = request.getParameter("seq").split(",");
		String reqSeq[] = request.getParameter("seq").split(",");
		
		String scrnType = StringUtil.checkNull(request.getParameter("scrnType"));
		String returnValue = "";

		for(int i=0; reqSeq.length>i; i++){
			setData.put("seq", reqSeq[i]);
			if(scrnType.equals("BRD")){
				reqFilePath[i] = commonService.selectString("boardFile_SQL.getFilePath", setData);
				reqOrgiFileName[i] = commonService.selectString("boardFile_SQL.getFileName", setData);
				reqSysFileName[i] = reqFilePath[i] + commonService.selectString("boardFile_SQL.getFileSysName", setData);
			}else if(scrnType.equals("INST")){
				setData.put("fileID", reqSeq[i]);
				reqFilePath[i] = commonService.selectString("instanceFile_SQL.getInstanceFilePath", setData);
				reqOrgiFileName[i] = commonService.selectString("instanceFile_SQL.getInstanceFileName", setData);
				reqSysFileName[i] = reqFilePath[i] + commonService.selectString("instanceFile_SQL.getInstanceFileSysName", setData);
			}else {
				reqFilePath[i] = commonService.selectString("fileMgt_SQL.getFilePathInSeq", setData);
				reqOrgiFileName[i] = commonService.selectString("fileMgt_SQL.getFileName", setData);
				reqSysFileName[i] = reqFilePath[i] + commonService.selectString("fileMgt_SQL.getFileSysName", setData);
			}
		}
		
		File orgiFileName = null;
		File sysFileName = null;
		String filePath = null;
		String viewName = null;
		
		try{
			int fileCnt = reqOrgiFileName.length;
			if(fileCnt==1){
				Map mapValue = new HashMap();
				List getList = new ArrayList();
				
				mapValue.put("Seq", reqSeq[0]);
				Map result  = new HashMap();
				if(scrnType.equals("BRD")){
					result=fileMgtService.select("boardFile_SQL.selectDownFile",mapValue);
					filePath = GlobalVal.FILE_UPLOAD_BOARD_DIR;			
				}else if(scrnType.equals("INST")){
					mapValue.put("fileID", reqSeq[0]);
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;
					mapValue.put("FilePath", GlobalVal.FILE_UPLOAD_ITEM_DIR);
					result=fileMgtService.select("instanceFile_SQL.selectDownInstanceFile", mapValue);				
				}else{
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;
					mapValue.put("FilePath", GlobalVal.FILE_UPLOAD_ITEM_DIR);
					result=fileMgtService.select("fileMgt_SQL.selectDownFile",mapValue);
				}
				
				String filename = StringUtil.checkNull(result.get("filename"));
				String original = StringUtil.checkNull(result.get("original"));
				String downFile = StringUtil.checkNull(result.get("downFile"));
				if (!new File(downFile).exists()) {					 
					 //target.put(AJAX_ALERT, "해당 파일 [ "+original+" ] 을 서버에서 찾을 수 없습니다");
					 target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00078", new String[]{original}));
					 target.put(AJAX_SCRIPT,  "setSubFrame();");
					 target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
					 // target.put(AJAX_NEXTPAGE, "jsp/file/fileGrpList.jsp");
					 model.addAttribute(AJAX_RESULTMAP, target);
				
					 return nextUrl(AJAXPAGE);
				}
				
				if(downFile == null || downFile.equals("")) downFile = FileUtil.FILE_UPLOAD_DIR + filename;
				if(filePath == null || filePath.equals("")) filePath = downFile.replace(filename, "");
				
				if ("".equals(filename)) {
					request.setAttribute("message", "File not found.");
					return "cmm/utl/EgovFileDown";
				}

				if ("".equals(original)) {
					original = filename;
				}
				setMap = new HashMap();
				setMap.put("Seq",reqSeq[0]);
				
				// 각 파일 테이블의 [DownCNT] update
				if(scrnType.equals("BRD")){
					setMap.put("TableName", "TB_BOARD_ATTCH");
				} else if(scrnType.equals("ISSUE")){
					setMap.put("TableName", "TB_ISSUE_FILE");
				} else {
					setMap.put("TableName", "TB_FILE");
				}
				
				if(scrnType.equals("INST")){
					setMap.put("fileID", reqSeq[0]);
					fileMgtService.update("instanceFile_SQL.updateInstanceFileDownCNT", setMap);
				}else{ fileMgtService.update("fileMgt_SQL.itemFileDownCnt_update", setMap); }
				

				HashMap drmInfoMap = new HashMap();
				
				String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
				String userName = StringUtil.checkNull(cmmMap.get("sessionUserNm"));
				String teamID = StringUtil.checkNull(cmmMap.get("sessionTeamId"));
				String teamName = StringUtil.checkNull(cmmMap.get("sessionTeamName"));
				
				drmInfoMap.put("userID", userID);
				drmInfoMap.put("userName", userName);
				drmInfoMap.put("teamID", teamID);
				drmInfoMap.put("teamName", teamName);
				drmInfoMap.put("orgFileName", original);
				drmInfoMap.put("downFile", downFile);
				
				// file DRM 적용
				String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
				String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
				if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
					//DRMUtil.setDRM(drmInfoMap);
					drmInfoMap.put("ORGFileDir", filePath);
					drmInfoMap.put("DRMFileDir", StringUtil.checkNull(DrmGlobalVal.DRM_DECODING_FILEPATH) + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//");
					drmInfoMap.put("Filename", filename);
					drmInfoMap.put("funcType", "download");
					returnValue = DRMUtil.drmMgt(drmInfoMap); // 암호화 
				}
				
				if(!"".equals(returnValue)) {
					downFile = returnValue;
				}
				
				request.setAttribute("downFile", downFile);
				request.setAttribute("orginFile", original);

				FileUtil.flMgtdownFile(request, response);

			}else{
				
				HashMap drmInfoMap = new HashMap();
				
				String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
				String userName = StringUtil.checkNull(cmmMap.get("sessionUserNm"));
				String teamID = StringUtil.checkNull(cmmMap.get("sessionTeamId"));
				String teamName = StringUtil.checkNull(cmmMap.get("sessionTeamName"));
				
				drmInfoMap.put("userID", userID);
				drmInfoMap.put("userName", userName);
				drmInfoMap.put("teamID", teamID);
				drmInfoMap.put("teamName", teamName);
				
				 for(int i=0; i<reqOrgiFileName.length; i++){
					returnValue = "";
					orgiFileName = new File(reqOrgiFileName[i]);
					sysFileName = new File(reqSysFileName[i]);
					
					drmInfoMap.put("orgFileName", reqOrgiFileName[i]);
					drmInfoMap.put("downFile", reqSysFileName[i].replace(reqFilePath[i], ""));
										
					String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
					String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
					if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
						drmInfoMap.put("ORGFileDir", reqFilePath[i]);
						drmInfoMap.put("DRMFileDir", StringUtil.checkNull(DrmGlobalVal.DRM_DECODING_FILEPATH) + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//");
						drmInfoMap.put("Filename", reqSysFileName[i].replace(reqFilePath[i], ""));
						drmInfoMap.put("funcType", "download");
						returnValue = DRMUtil.drmMgt(drmInfoMap); // 암호화 
					}
					
					if(!sysFileName.exists()){
						viewName = orgiFileName.getName();
						// 파일이 없을경우 변경했던 파일명 원복 
						for(int k=0; k<reqOrgiFileName.length; k++){
								orgiFileName = new File(reqOrgiFileName[k]);
								sysFileName = new File(reqSysFileName[k]);
								orgiFileName.renameTo(sysFileName);
						 }
						target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00078", new String[]{viewName}));
						model.addAttribute(AJAX_RESULTMAP, target);
					
						return nextUrl(AJAXPAGE);
					}

					if(!"".equals(returnValue)) {
						sysFileName = new File(returnValue);
					}
					
					if(sysFileName.exists()){
						
						sysFileName.renameTo(orgiFileName);
					}
				 }
				 
				 // zip file명 만들기 
				 Calendar cal = Calendar.getInstance();
				 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
				 java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HHmmssSSS");
				 String sdate = sdf.format(cal.getTime());
				 String stime = sdf2.format(cal.getTime());
				 String mkFileNm = sdate+stime;
				 
				 String path = GlobalVal.FILE_UPLOAD_ZIPFILE_DIR;
				 String fullPath = GlobalVal.FILE_UPLOAD_ZIPFILE_DIR+"downFiles"+sdf2+".zip";
				 String newFileNm = "FILE_"+mkFileNm+".zip";
				 
				 File zipFile = new File(fullPath); 
				 File dirFile = new File(path);
			  
				 if(!dirFile.exists()) {
				     dirFile.mkdirs();
				 } 
	
				 ZipOutputStream zos = null;
				 FileOutputStream os = null;
				 
				 try {
					 os = new FileOutputStream(zipFile);
					 zos = new ZipOutputStream(os);
					 byte[] buffer = new byte[1024 * 2];
					 int k = 0;
					 for(String file : reqOrgiFileName) {
						 filePath = reqFilePath[k];
						 if(new File(file).isDirectory()) { continue; }
				                
				         BufferedInputStream bis = null;
				         FileInputStream is = null;
				         try {
				        	 is = new FileInputStream (file);
				        	 bis = new BufferedInputStream(is);
				        	 file = file.replace(filePath, ""); 
					         
					         zos.putNextEntry(new ZipEntry(file));
					        			         
					         int length = 0;
					         while((length = bis.read(buffer)) != -1) {
					            zos.write(buffer, 0, length);
					         }
					         
				         } catch ( Exception e ) {
							 System.out.println(e.toString());
							 throw e;
						 } finally {
							 zos.closeEntry();
					         bis.close();
					         is.close();
					         k++;
						 }				         
					 }
				 } catch ( Exception e ) {
					 System.out.println(e.toString());
					 throw e;
				 } finally {
					 zos.closeEntry();
		             zos.close();
					 os.close();
				 }
			
	         //    request.setAttribute("orginFile", arg1);
				// 파일이름 원복
				for(int i=0; i<reqOrgiFileName.length; i++){
					setMap = new HashMap();
					orgiFileName = new File(reqOrgiFileName[i]);
					sysFileName = new File(reqSysFileName[i]);
					
					if(orgiFileName.exists()){
						orgiFileName.renameTo(sysFileName);
					}
					
					setMap.put("Seq",reqSeq[i]);
					// 각 파일 테이블의 [DownCNT] update
					if(scrnType.equals("BRD")){
						setMap.put("TableName", "TB_BOARD_ATTCH");
					} else if(scrnType.equals("ISSUE")){
						setMap.put("TableName", "TB_ISSUE_FILE");
					} else {
						setMap.put("TableName", "TB_FILE");
					}
					
					if(scrnType.equals("INST")){ 
						setMap.put("fileID",reqSeq[i]);
						fileMgtService.update("instanceFile_SQL.updateInstanceFileDownCNT", setMap);
					}else{ fileMgtService.update("fileMgt_SQL.itemFileDownCnt_update", setMap); }
				}
				String downFile = fullPath;
				request.setAttribute("downFile", downFile);
				request.setAttribute("orginFile", newFileNm);
				FileUtil.flMgtdownFile(request, response);
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00079"));
			target.put(AJAX_SCRIPT, "doSearchList();");
			
		}catch (Exception e) {
			 System.out.println(e);
			 throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/deleteFile.do")
	public String deleteFile( HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();
		String sysFile =  StringUtil.checkNull(request.getParameter("SysFile"), "");
		String itemID = StringUtil.checkNull(request.getParameter("ItemID"), "");
		Map stusMap = new HashMap();
		try{
			stusMap.put("itemId", itemID);
			Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",stusMap);
			
			File existFile = new File(sysFile);
			if(existFile.isFile() && existFile.exists()){existFile.delete();}
			fileMgtService.delete("fileMgt_SQL.itemFile_delete", cmmMap);
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00069")); // 삭제 성공
			target.put(AJAX_SCRIPT, "parent.goBack();");
			
		}catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/deleteFileFromLst.do")
	public String deleteFileFromLst( HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();
		Map setMap = new HashMap();
		
		try{
			String itemID = StringUtil.checkNull(request.getParameter("itemId"), "");

			String sysFile[] = StringUtil.checkNull(request.getParameter("sysFile")).split(",");
			String filePath[] = StringUtil.checkNull(request.getParameter("filePath")).split(",");
			String seq[] = StringUtil.checkNull(request.getParameter("seq")).split(",");
			
			String scrnType = StringUtil.checkNull(request.getParameter("scrnType"));
			Map setData = new HashMap();
			
			
			for(int i=0; seq.length>i; i++){
				setData.put("seq", seq[i]);
				if(scrnType.equals("BRD")){
					filePath[i] = commonService.selectString("boardFile_SQL.getFilePath", setData);
					sysFile[i] = commonService.selectString("boardFile_SQL.getFileSysName", setData);
				}else if(scrnType.equals("INST")){
					setData.put("fileID", seq[i]);
					filePath[i] = commonService.selectString("instanceFile_SQL.getInstanceFilePath", setData);
					sysFile[i] = commonService.selectString("instanceFile_SQL.getInstanceFileSysName", setData);
				}else {
					filePath[i] = commonService.selectString("fileMgt_SQL.getFilePathInSeq", setData);
					sysFile[i] = commonService.selectString("fileMgt_SQL.getFileSysName", setData);
				}
			}

			setMap.put("itemId", itemID);
			String taskFile = null;
			String fileName = "";
			for(int i=0; i<sysFile.length; i++){
				
				if(!sysFile[i].equals("")){
					fileName = filePath[i] + "/" + sysFile[i];
					File existfile = new File(fileName);
					//FileUtil.deleteFile(fileName);
					if(existfile.isFile() && existfile.exists()){
						existfile.delete();
					}
				}
				setMap.put("Seq", seq[i]);
				if(scrnType.equals("INST")){
					setMap.put("fileID", seq[i]);
					fileMgtService.delete("instanceFile_SQL.deleteInstanceFile", setMap);
				}else{
					fileMgtService.delete("fileMgt_SQL.itemFile_delete", setMap);
				}
				// task에 첨부된 파일 삭제 
				taskFile = fileMgtService.selectString("fileMgt_SQL.getFileFromTask",setMap);
				if(taskFile != null){
					setMap.put("taskFileID", taskFile);
					fileMgtService.delete("fileMgt_SQL.updateNullToTaskFile",setMap);
				}
			}
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00069")); // 삭제 성공
			target.put(AJAX_SCRIPT, "doSearchList();");
			
		}catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/deleteTempFile.do")
	public String deleteTempFile( HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		String fileName =  StringUtil.checkNull(request.getParameter("fileName"), "");
		try{
			String scrnType =  StringUtil.checkNull(request.getParameter("scrnType"), "");
			
			String sessionUserID =  StringUtil.checkNull(cmmMap.get("sessionUserId"), "");
			String filePath = "";
		
				if(scrnType.equals("BRD")){
					filePath = GlobalVal.FILE_UPLOAD_BOARD_DIR;
				}else if(scrnType.equals("CNG")){
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;
				}else if(scrnType.equals("ISSUE")){
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;
				}else if(scrnType.equals("SR")){
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;
				}else if(scrnType.equals("INST")){
					filePath = GlobalVal.FILE_UPLOAD_ITEM_DIR;		
				}else{
					// filePath = GlobalVal.FILE_UPLOAD_TMP_DIR;	
					filePath = GlobalVal.FILE_UPLOAD_BASE_DIR;
				}
			if(!fileName.equals("")){fileName=filePath+"//"+sessionUserID+"//"+fileName;FileUtil.deleteFile(fileName);}
		}catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(AJAXPAGE);
	}	
	
	@RequestMapping(value="/goMultiUpload.do")
	public String goMultiUpload(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException, Exception {
		
		String url = "file/multiFileUploadJ";
		try {
			
			 	/*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			    MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			 	
					if(map != null) {
						Iterator iter = map.keySet().iterator();
						while(iter.hasNext()) {
							String str = (String) iter.next();
							List<MultipartFile> fileList =  map.get(str);
							for(MultipartFile mpf : fileList) {
								File localFile = new File("c:\\temp\\" + StringUtils.trimAllWhitespace(mpf.getOriginalFilename()));
								OutputStream out = new FileOutputStream(localFile);
								out.write(mpf.getBytes());
								out.close();							
							}
						}
					}
			        return null;*/
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goMultiUploadFl.do")
	public String goMultiUploadFl(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/multiFileUploadFl";
		String itemID = StringUtil.checkNull(request.getParameter("itemId"), "1");
		Map resultValue = new HashMap();
		Map result = new HashMap();
		try {
				cmmMap.put("itemID", itemID);
				resultValue = fileMgtService.select("fileMgt_SQL.getItemClassCode", cmmMap);
				result.put("itemClassCode", resultValue.get("itemClassCode"));
				result.put("itemID", itemID);
				model.put("menu", getLabel(request, commonService));
				model.addAttribute(AJAX_RESULTMAP, result);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/selectFileList.do")
	public String selectFileList(HttpServletRequest request, ModelMap model, HttpServletResponse response) throws  ServletException, IOException, Exception {
		
		String itemID = StringUtil.checkNull(request.getParameter("itemId"), "1");
		String languageID = StringUtil.checkNull(request.getParameter("languageID"), "1");
		String baseUrl = StringUtil.checkNull(request.getParameter("baseUrl"), "1");
		
		Map mapValue = new HashMap();
		List getList = new ArrayList();
		
		try {
				mapValue.put("itemId", itemID);
				mapValue.put("languageID", languageID);
				mapValue.put("baseUrl", baseUrl);
				getList = commonService.selectList("fileMgt_SQL.selectFileList",mapValue);
				
				JSONArray jsonArray = new JSONArray(getList);							
				response.setCharacterEncoding("UTF-8"); // 한글깨짐현상 방지
				PrintWriter out = response.getWriter();
			    out.write(jsonArray.toString());
			    out.flush();
				
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/goRelatedDcmnts.do")
	public String goRelatedDcmnts(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws  ServletException, IOException, Exception {
		
		String url = "file/relatedDcmnts";
		
		String itemID = StringUtil.checkNull(request.getParameter("s_itemID"), "-1");
		String languageID = StringUtil.checkNull(request.getParameter("languageID"), "-1");
		
		try {
				model.put("itemID", itemID);
				
				Map setMap = new HashMap();
				setMap.put("s_itemID", itemID);
				setMap.put("languageID", languageID);
				List itemList = commonService.selectList("item_SQL.getCxnItemList",setMap);
			
				//TI.ItemID  in (31865,31881,31883,715268,715359,725111,726388,726394,726398)
				
				Map getMap = new HashMap();
				String rltdItemId ="";
				for(int i = 0; i < itemList.size(); i++){
					setMap = (HashMap)itemList.get(i);
					getMap.put("ItemID", setMap.get("ItemID"));
					
					if(i>0){
						rltdItemId += ","+setMap.get("ItemID").toString();
					}else{
						rltdItemId = setMap.get("ItemID").toString();
					}
				}
			
				/*model.addAttribute(HTML_HEADER, "HOME");
				model.put("noticType", noticType);Label Setting*/
				
				model.put("rltdItemId", rltdItemId);
				model.put("menu", getLabel(request, commonService));
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goFileMdlMgt.do")
	public String goFileMdlMgt(HttpServletRequest request, ModelMap model) throws  Exception {
		String url = "file/fileMdlMgt";
		String itemId = StringUtil.checkNull(request.getParameter("s_itemID"), "-1");
		String option  = StringUtil.checkNull(request.getParameter("option"), "-1");
		try {
				model.put("itemId", itemId);
				model.put("option", option);
				model.put("menu", getLabel(request, commonService));
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goFileMdlList.do")
	public String goFileMdlList(HttpServletRequest request, HttpServletResponse response, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		
		String url = "file/fileMdlList";
		String s_itemID = StringUtil.checkNull(request.getParameter("s_itemID"));	
		Map fileMap = new HashMap();
		String itemAthId = "";
		String Blocked = "";
		String LockOwner = "";
		try {
			fileMap.put("itemId", s_itemID);
			Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",fileMap);
			String itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",fileMap);
			if(result.get("AuthorID") != null){itemAthId = StringUtil.checkNull(result.get("AuthorID"));}
			if(result.get("Blocked") != null){Blocked = StringUtil.checkNull(result.get("Blocked"));}
			if(result.get("LockOwner") != null){LockOwner = StringUtil.checkNull(result.get("LockOwner"));}
			
			
						
			String sessionUserID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
			String sessionAuthLev = String.valueOf(cmmMap.get("sessionAuthLev"));
			
			if (StringUtil.checkNull(result.get("AuthorID")).equals(sessionUserID)
					|| StringUtil.checkNull(result.get("LockOwner")).equals(cmmMap.get("sessionUserId"))
					|| "1".equals(sessionAuthLev)) {
				model.put("myItem", "Y");
			}
			
			fileMap.put("languageID", StringUtil.checkNull(cmmMap.get("sessionCurrLangType")));
			fileMap.put("s_itemID", s_itemID);
			fileMap.put("fromToItemID", s_itemID);
			List itemList = commonService.selectList("item_SQL.getCxnItemIDList", fileMap); //getCxnItemIDList
			
			String rltdItemId = "";
			for(int i = 0; i < itemList.size(); i++){
				Map itemInfo = (HashMap)itemList.get(i);
				if (i == 0) {
					rltdItemId += StringUtil.checkNull(itemInfo.get("ItemID"));
				}else{					
					rltdItemId += "," + StringUtil.checkNull(itemInfo.get("ItemID"));
				}
			}
			if(!rltdItemId.equals("")){rltdItemId += ","+s_itemID;}else{ rltdItemId = s_itemID;}
			model.put("DocCategory", "ITM");
			model.put("hideBlocked", "Y");
			model.put("rltdItemId", rltdItemId);	
			model.put("itemFileOption", itemFileOption);			
			model.put("itemAthId", itemAthId);
			model.put("Blocked",Blocked);
			model.put("s_itemID", s_itemID);
			model.put("LockOwner", LockOwner);
			model.put("screenType", "model");
			model.put("menu", getLabel(request, commonService));
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goFileMdlDetail.do")
	public String goFileMdlDetail(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/fileMdlDetail";
		String isNew = StringUtil.checkNull(request.getParameter("isNew"), "");
		String fileSeq = StringUtil.checkNull(request.getParameter("fileSeq"), "");
		String fileName = StringUtil.checkNull(request.getParameter("fileName"), "");
		String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"), "");
		String creationTime = StringUtil.checkNull(request.getParameter("creationTime"), "");
		String writeUserNM = StringUtil.checkNull(request.getParameter("writeUserNM"), "");
		String sysFile = StringUtil.checkNull(request.getParameter("sysFile"), "");
		String fltpName = StringUtil.checkNull(request.getParameter("fltpName"), "");
		String ID = StringUtil.checkNull(request.getParameter("s_itemID"), "-1");
		String itemClassCode = ""; 
		
		List fileList = new ArrayList();
		Map fileMap = null;
		Map resultValue = new HashMap();
		try {
				Map result = new HashMap();
				if("Y".equals(cmmMap.get("isNEW"))){ // 신규
					result.put("isNew", isNew);
					result.put("fileSeq", "");
					result.put("fileName", "");
					result.put("fltpCode", "");
					result.put("creationTime","");
					result.put("WriteUserNM","");
					result.put("sysFile", "");
					result.put("fltpName", "");
					result.put("itemID",request.getParameter("itemId"));
					result.put("menu", getLabel(request, commonService));
					result.put("isNEW",cmmMap.get("isNEW"));
					cmmMap.put("itemID",request.getParameter("itemId"));
					resultValue = fileMgtService.select("fileMgt_SQL.getItemClassCode", cmmMap);
					result.put("itemClassCode", resultValue.get("itemClassCode"));
					
					model.put("isNEW", cmmMap.get("isNEW"));
					model.put("menu", getLabel(request, commonService));
					model.addAttribute(AJAX_RESULTMAP, result);
				}else{
					result.put("isNew", isNew);
					result.put("fileSeq", fileSeq);
					result.put("fileName", fileName);
					result.put("fltpCode", fltpCode);
					result.put("creationTime",creationTime);
					result.put("WriteUserNM",writeUserNM);
					result.put("sysFile", sysFile);
					result.put("fltpName", fltpName);
					result.put("itemID",request.getParameter("itemId"));
					result.put("menu", getLabel(request, commonService));
					result.put("isNEW",cmmMap.get("isNEW"));
					cmmMap.put("itemID",request.getParameter("itemId"));
					resultValue = fileMgtService.select("fileMgt_SQL.getItemClassCode", cmmMap);
					result.put("itemClassCode", resultValue.get("itemClassCode"));
										
					model.put("isNEW", cmmMap.get("isNEW"));
					model.put("menu", getLabel(request, commonService));
					model.addAttribute(AJAX_RESULTMAP, result);
				}
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/goDocumentList.do")
	public String goDocumentList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/documentMainMenu";
		Map itemMap = new HashMap();
		try {
				String searchValue = StringUtil.checkNull(request.getParameter("searchValue"));
				searchValue = StringUtil.replaceFilterString(searchValue);
			
				Map setMap = new HashMap();		
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
				model.put("isPublic", StringUtil.checkNull(request.getParameter("isPublic")));
				String screenType = StringUtil.checkNull(request.getParameter("screenType"));
				if(screenType.equals("main")){
					model.put("screenType", StringUtil.checkNull(request.getParameter("screenType")));
					model.put("searchKey", StringUtil.checkNull(request.getParameter("searchKey")));
					model.put("searchValue", searchValue);
					model.put("fltpCode", StringUtil.checkNull(request.getParameter("fltpCode")));
					model.put("itemTypeCode", StringUtil.checkNull(request.getParameter("itemTypeCode")));
					model.put("itemClassCode", StringUtil.checkNull(request.getParameter("classCode")));
					model.put("regMemberName", StringUtil.checkNull(request.getParameter("regMemberName")));
					
				}
				
		}catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/documentGridList.do")
	public String documentGridList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/documentGridList";
		try {
				Map setMap = new HashMap();	
				String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"), "");
				String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"), "");
				String frmType = StringUtil.checkNull(request.getParameter("frmType"), ""); 
				String screenType = StringUtil.checkNull(request.getParameter("screenType"), ""); 
				String docType = StringUtil.checkNull(request.getParameter("docType"), ""); 
				String s_itemID = StringUtil.checkNull(request.getParameter("s_itemID"), ""); 
				String csrEditable = StringUtil.checkNull(request.getParameter("csrEditable"), ""); 
				String isMember = StringUtil.checkNull(request.getParameter("isMember"), ""); 
				String isPublic = StringUtil.checkNull(request.getParameter("isPublic"), ""); 
				String DocCategory = StringUtil.checkNull(request.getParameter("DocCategory"), "ITM"); 
				String regMemberName = StringUtil.checkNull(request.getParameter("regMemberName"), "");
				 
				setMap.put("fltpCode", fltpCode);
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				
				String fltpName = "";
				if(!fltpCode.equals("")){
					fltpName = commonService.selectString("fileMgt_SQL.getFltpName",setMap);
				}else{
					fltpName = getLabel(request, commonService).get("LN00019").toString();
				}
				
				String parentID = StringUtil.checkNull(request.getParameter("parentID"), ""); 
				String projectID = StringUtil.checkNull(request.getParameter("projectID"), ""); 
				if(screenType.equals("mainV3") || screenType.equals("csrDtl")){
					Map projectMap = new HashMap();
					setMap.put("parentID", parentID);
					setMap.put("languageID", cmmMap.get("sessionCurrLangType"));
					projectMap = commonService.select("task_SQL.getProjectAuthorID",setMap);
					model.put("projectMap", projectMap);
					model.put("projectID", projectID);
					model.put("isMember", isMember);
				}
				
				if(screenType.equals("main")){
					model.put("screenType", StringUtil.checkNull(request.getParameter("screenType")));
					model.put("searchKey", StringUtil.checkNull(request.getParameter("searchKey")));
					model.put("searchValue", StringUtil.checkNull(request.getParameter("searchValue")));
					model.put("itemClassCode", StringUtil.checkNull(request.getParameter("itemClassCode")));
				}
				
				model.put("parentID", parentID);
				model.put("DocCategory", DocCategory);
				model.put("regMemberName", regMemberName);
				model.put("itemTypeCode", itemTypeCode);
				model.put("fltpCode", fltpCode);
				model.put("fltpName", fltpName);
				model.put("frmType", frmType);
				model.put("docType", docType);
				model.put("screenType", screenType);
				model.put("isPublic", isPublic);
				model.put("csrEditable", csrEditable);
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/documentDetail.do")
	public String documentDetail(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/documentDetail";
		try {
				Map fileMap  = new HashMap();
				Map setMap = new HashMap();	
				String seq = StringUtil.checkNull(request.getParameter("seq"), "");
				String isNew = StringUtil.checkNull(request.getParameter("isNew"), "");
				String DocumentID = StringUtil.checkNull(request.getParameter("DocumentID"), "");
				String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"), "");
				String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"), "");
				
				// String parentID = StringUtil.checkNull(request.getParameter("parentID"),"");	
				String screenType = StringUtil.checkNull(request.getParameter("screenType"),"");	
				String isMember = StringUtil.checkNull(request.getParameter("isMember"),"");	
				String path = StringUtil.checkNull(request.getParameter("path"),"");	
				setMap.put("seq", seq);
				setMap.put("DocumentID", DocumentID);
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				
				fileMap = commonService.select("fileMgt_SQL.getFileDetailList",setMap);
				String classCode = StringUtil.checkNull(fileMap.get("ClassCode"),"");
				
				model.put("fileMap", fileMap);		
				model.put("pageNum", request.getParameter("pageNum"));
				model.put("itemTypeCode", itemTypeCode);
				model.put("fltpCode", fltpCode);
				model.put("classCode", classCode);
				model.put("docType", request.getParameter("docType"));
				model.put("screenType", screenType);
				model.put("isMember", isMember);
				model.put("path", path);
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
				
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/olmDocList.do")
	public String olmDocList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/olmDocList";
		try {
				Map setMap = new HashMap();	
				String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"), "");
				String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"), "");
				String frmType = StringUtil.checkNull(request.getParameter("frmType"), ""); 
				String screenType = StringUtil.checkNull(request.getParameter("screenType"), ""); 
				String myDoc = StringUtil.checkNull(request.getParameter("myDoc"), ""); 
				String s_itemID = StringUtil.checkNull(request.getParameter("s_itemID"), ""); 
				String csrEditable = StringUtil.checkNull(request.getParameter("csrEditable"), ""); 
				String isMember = StringUtil.checkNull(request.getParameter("isMember"), ""); 
				String isPublic = StringUtil.checkNull(request.getParameter("isPublic"), ""); 
				String DocCategory = StringUtil.checkNull(request.getParameter("DocCategory"), "ITM"); 
				 
				setMap.put("fltpCode", fltpCode);
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				
				String fltpName = "";
				if(!fltpCode.equals("")){
					fltpName = commonService.selectString("fileMgt_SQL.getFltpName",setMap);
				}else{
					fltpName = getLabel(request, commonService).get("LN00019").toString();
				}
				
				String parentID = StringUtil.checkNull(request.getParameter("parentID"), ""); 
				String projectID = StringUtil.checkNull(request.getParameter("projectID"), ""); 
				if(screenType.equals("mainV3") || screenType.equals("csrDtl")){
					Map projectMap = new HashMap();
					setMap.put("parentID", parentID);
					setMap.put("languageID", cmmMap.get("sessionCurrLangType"));
					projectMap = commonService.select("task_SQL.getProjectAuthorID",setMap);
					model.put("projectMap", projectMap);
					model.put("projectID", projectID);
					model.put("isMember", isMember);
				}
				
				if(screenType.equals("main")){
					model.put("screenType", StringUtil.checkNull(request.getParameter("screenType")));
					model.put("searchKey", StringUtil.checkNull(request.getParameter("searchKey")));
					model.put("searchValue", StringUtil.checkNull(request.getParameter("searchValue")));
					model.put("itemClassCode", StringUtil.checkNull(request.getParameter("itemClassCode")));
				}
				
				model.put("parentID", parentID);
				model.put("DocCategory", DocCategory);
				model.put("itemTypeCode", itemTypeCode);
				model.put("fltpCode", fltpCode);
				model.put("fltpName", fltpName);
				model.put("frmType", frmType);
				model.put("myDoc", myDoc);
				model.put("screenType", screenType);
				model.put("isPublic", isPublic);
				model.put("csrEditable", csrEditable);
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/esmDocList.do")
	public String esmDocList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/esmDocList";
		try {
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
				Map setData = new HashMap();	
				String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"), "");
				String itemTypeCode = StringUtil.checkNull(request.getParameter("itemTypeCode"), "");
				String isMember = StringUtil.checkNull(request.getParameter("isMember"), ""); 
				String isPublic = StringUtil.checkNull(request.getParameter("isPublic"), ""); 
				String docCategory = StringUtil.checkNull(request.getParameter("docCategory"), ""); 
				String scrID = StringUtil.checkNull(request.getParameter("scrID"));
								
				setData.put("scrID", scrID);
				setData.put("isPublic", "N");
				setData.put("DocCategory", docCategory);
				setData.put("docDomain", "ESM");
				setData.put("languageID", cmmMap.get("sessionCurrLangType"));
				
				List fileList = commonService.selectList("fileMgt_SQL.getFile_gridList",setData);
				JSONArray gridData = new JSONArray(fileList);
				model.put("gridData", gridData);
				
			
				model.put("docCategory", docCategory);
				model.put("docDomain", "ESM");
				model.put("itemTypeCode", itemTypeCode);
				model.put("fltpCode", fltpCode);
				model.put("isPublic", isPublic);
				
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl(url);
	}
	
	@RequestMapping(value="/fileDetail.do")
	public String fileDetail(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/fileDetail";
		try {
			//임시저장된 파일이 존재할 수 있으므로 삭제
			String path=GlobalVal.FILE_UPLOAD_BASE_DIR + cmmMap.get("sessionUserId");
			if(!path.equals("")){FileUtil.deleteDirectory(path);}	
			
			Map fileMap  = new HashMap();
			Map setMap = new HashMap();	
			String seq = StringUtil.checkNull(request.getParameter("seq"), "");
			String isNew = StringUtil.checkNull(request.getParameter("isNew"), "");
			String DocumentID = StringUtil.checkNull(request.getParameter("DocumentID"), "");
			String itemClassCode = StringUtil.checkNull(request.getParameter("itemClassCode"), "");
			String scrnType = StringUtil.checkNull(request.getParameter("scrnType"), "");
			
			setMap.put("seq", seq);
			setMap.put("DocumentID", DocumentID);
			setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
			if(isNew.equals("N")){
				fileMap = commonService.select("fileMgt_SQL.getFileDetailList",setMap);
			}
			//fileMap.put("DocumentID", DocumentID);
			model.put("fileMap", fileMap);
			model.put("pageNum", request.getParameter("pageNum"));
			model.put("isNew", isNew);
			model.put("itemClassCode", itemClassCode);
			model.put("isPossibleEdit", "Y");			
			model.put("selectedItemBlocked", StringUtil.checkNull(request.getParameter("selectedItemBlocked"), ""));
			model.put("selectedItemAuthorID", StringUtil.checkNull(request.getParameter("selectedItemAuthorID"), ""));
			model.put("selectedItemLockOwner", StringUtil.checkNull(request.getParameter("selectedItemLockOwner"), ""));
			model.put("menu", getLabel(request, commonService));	/*Label Setting*/
			model.put("scrnType", scrnType);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/saveItemFileDetail.do")
	public String saveItemFileDetail(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		try {
				Map fileMap  = new HashMap();
				Map setMap = new HashMap();	
				String seq = StringUtil.checkNull(cmmMap.get("Seq"), "");
				String isNew = StringUtil.checkNull(cmmMap.get("isNew"), "");
				String fltpCode = StringUtil.checkNull(cmmMap.get("FltpCode"), "");
				String Description = StringUtil.checkNull(cmmMap.get("Description"), "");
				String DocumentID = StringUtil.checkNull(cmmMap.get("DocumentID"), "");				
				String sessionUserID = cmmMap.get("sessionUserId").toString();
				String scrnType = StringUtil.checkNull(cmmMap.get("scrnType"), "");
				String screenType = StringUtil.checkNull(cmmMap.get("screenType"), "");
				String blocked = StringUtil.checkNull(cmmMap.get("Blocked"), "0");
				String docCategory = StringUtil.checkNull(cmmMap.get("DocCategory"));
				String ChangeSetID = StringUtil.checkNull(cmmMap.get("ChangeSetID"));
				String Version = StringUtil.checkNull(cmmMap.get("Version"));
				
				if(screenType.equals("csrDtl")){
					fltpCode = "CSRDF";			
				}
								
				setMap.put("Seq", seq);
				setMap.put("itemID", DocumentID);
				setMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
				setMap.put("FltpCode", fltpCode);
				setMap.put("Description", Description);
				setMap.put("sessionUserId", sessionUserID);
				
				cmmMap.put("fltpCode", fltpCode);
				
				List fileList = new ArrayList();
				String fileMgt 	= StringUtil.checkNull(cmmMap.get("fileMgt"), "ITM");
				String userId 	= StringUtil.checkNull(cmmMap.get("sessionUserId").toString(), "");
				String linkType = StringUtil.checkNull(cmmMap.get("linkType"), "1");
	
				setMap.put("itemID", DocumentID);
				String curChangeSetID = StringUtil.checkNull(commonService.selectString("project_SQL.getCurChangeSetIDFromItem",setMap));

				String filePath = StringUtil.checkNull(fileMgtService.selectString("fileMgt_SQL.getFilePath",cmmMap), GlobalVal.FILE_UPLOAD_ITEM_DIR);  
				setMap.put("itemId",DocumentID);
				
				String itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",setMap);
				if(seq.equals("")){//insert
					
					cmmMap.put("itemId", DocumentID);
					Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",cmmMap);
					cmmMap.put("Status", StringUtil.checkNull(result.get("Status").toString()));
					cmmMap.put("KBN", "insert");
					
					int seqCnt = 0;
					seqCnt = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", cmmMap));
					//Read Server File
					String orginPath = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//";
					String targetPath = filePath;
					File dirFile = new File(targetPath);if(!dirFile.exists()) { dirFile.mkdirs();} 
					List tmpFileList = FileUtil.copyFiles(orginPath, targetPath);
					if(tmpFileList != null){
						
						if("VIEWER".equals(itemFileOption))
							filePath = "";
						
						for(int i=0; i<tmpFileList.size();i++){
							fileMap=new HashMap(); 
							HashMap resultMap=(HashMap)tmpFileList.get(i);
							fileMap.put("Seq", seqCnt);
							fileMap.put("DocumentID", DocumentID);
							fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
							fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
							fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
							fileMap.put("FilePath", filePath);
							fileMap.put("FileFormat", resultMap.get(FileUtil.FILE_EXT));
							fileMap.put("FltpCode", fltpCode);
							fileMap.put("FileMgt", fileMgt);
							fileMap.put("LinkType", linkType);
							fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
							fileMap.put("userId", userId);
							fileMap.put("projectID", StringUtil.checkNull(result.get("ProjectID").toString()));
							fileMap.put("DocCategory", "ITM");
							fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");					
							
							fileList.add(fileMap);
							seqCnt++;
						}
					}
					fileMgtService.save(fileList, cmmMap);					
				}
				else{//update					
				
					cmmMap.put("itemId", DocumentID);
					Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",cmmMap);
					cmmMap.put("Status", StringUtil.checkNull(result.get("Status")));
					cmmMap.put("KBN", "insert");
				
					//Read Server File
					String orginPath = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//";
					String targetPath = filePath;
					File dirFile = new File(targetPath);if(!dirFile.exists()) { dirFile.mkdirs();} 
					List tmpFileList = FileUtil.copyFiles(orginPath, targetPath);
					
					if(tmpFileList.size() > 0){						
						
						for(int i=0; i<tmpFileList.size();i++){
							fileMap=new HashMap(); 
							HashMap resultMap=(HashMap)tmpFileList.get(i);
							if("VIEWER".equals(itemFileOption))
								fileMap.put("FilePath", "VIEWER");
							
							fileMap.put("Seq", seq);
							fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
							fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
							fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
							fileMap.put("FltpCode", fltpCode);
							fileMap.put("FileFormat", resultMap.get(FileUtil.FILE_EXT));
							fileMap.put("Description", Description);
							fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
							fileMap.put("Blocked", blocked);
							
							if("ITM".equals(docCategory)) {
								fileMap.put("ChangeSetID", curChangeSetID);
							}
							
							fileMap.put("sessionUserId", userId);
							fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_update");					
							
							fileList.add(fileMap);
						}
						
					}else{
						setMap.put("Blocked", blocked);
						
						if("ITM".equals(docCategory)) {
							setMap.put("ChangeSetID", curChangeSetID);
						}
						
						commonService.insert("fileMgt_SQL.itemFile_update",setMap);
					}
					fileMgtService.save(fileList, cmmMap);	
					
					setMap.put("Version",Version);
					setMap.put("s_itemID",ChangeSetID);
					commonService.update("cs_SQL.updateChangeSetVersion",setMap);
				}
			
				model.put("fileMap", fileMap);
				model.put("pageNum", cmmMap.get("pageNum"));
				model.put("menu", getLabel(request, commonService));	/*Label Setting*/
				
				target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
				target.put(AJAX_SCRIPT, "parent.fnCallBack();");
				
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/saveMultiFile.do")
	public String saveMultiFile(HttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		String documentID = StringUtil.checkNull(cmmMap.get("id"), "");
		String fltpCode = StringUtil.checkNull(cmmMap.get("fltpCode"), "");
		String fileMgt 	= StringUtil.checkNull(cmmMap.get("fileMgt"), "ITM");
		String userId 	= StringUtil.checkNull(cmmMap.get("usrId"), "");
		String linkType = StringUtil.checkNull(cmmMap.get("linkType"), "1");
		String docCategory = StringUtil.checkNull(cmmMap.get("docCategory"), "ITM");
		String changeSetID = StringUtil.checkNull(cmmMap.get("changeSetID"), "");
		String projectID = StringUtil.checkNull(cmmMap.get("projectID"));
		String itemFileOption = "OLM";
		
		try{
			List fileList = new ArrayList();
			Map fileMap = null;
			Map stusMap = new HashMap();
			String filePath = StringUtil.checkNull(fileMgtService.selectString("fileMgt_SQL.getFilePath",cmmMap),GlobalVal.FILE_UPLOAD_ITEM_DIR);
			cmmMap.put("itemId", documentID);
			cmmMap.put("itemID", documentID);
		
			if("ITM".equals(docCategory)) {			
				Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",cmmMap);
				changeSetID = StringUtil.checkNull(commonService.selectString("project_SQL.getCurChangeSetIDFromItem",cmmMap));
				projectID = StringUtil.checkNull(result.get("ProjectID").toString());
				
				itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",cmmMap);
				cmmMap.put("Status", StringUtil.checkNull(result.get("Status")));
			}
			else {				
				cmmMap.put("Status", "");
			}
			cmmMap.put("KBN", "insert");				
			
			int seqCnt = 0;
			seqCnt = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", cmmMap));
			//Read Server File
			String orginPath = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//";
			String targetPath = filePath;
			List tmpFileList = FileUtil.copyFiles(orginPath, targetPath);
			
			// DRM 복호와 정보 
			
			HashMap drmInfoMap = new HashMap();			
			String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
			String userName = StringUtil.checkNull(cmmMap.get("sessionUserNm"));
			String teamID = StringUtil.checkNull(cmmMap.get("sessionTeamId"));
			String teamName = StringUtil.checkNull(cmmMap.get("sessionTeamName"));			
			drmInfoMap.put("userID", userID);
			drmInfoMap.put("userName", userName);
			drmInfoMap.put("teamID", teamID);
			drmInfoMap.put("teamName", teamName);
			
			String revisionYN = StringUtil.checkNull(commonService.selectString("fileMgt_SQL.getRevisionYN", cmmMap));	
			if(tmpFileList != null){
				
				if("VIEWER".equals(itemFileOption))
					filePath = "";
				
				for(int i=0; i<tmpFileList.size();i++){
					fileMap=new HashMap(); 
					HashMap resultMap=(HashMap)tmpFileList.get(i);
					fileMap.put("Seq", seqCnt);
					fileMap.put("DocumentID", documentID);
					fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
					fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
					fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
					fileMap.put("FileFormat", resultMap.get(FileUtil.FILE_EXT));
					fileMap.put("FilePath", filePath);
					fileMap.put("FltpCode", fltpCode);
					fileMap.put("FileMgt", fileMgt);
					fileMap.put("LinkType", linkType);
					fileMap.put("ChangeSetID", changeSetID);
					fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
					fileMap.put("userId", userId);
					fileMap.put("projectID", projectID);					
					fileMap.put("DocCategory", docCategory);
					fileMap.put("revisionYN", revisionYN);
					fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");	
					
					// File DRM 복호화
					String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
					String DRM_UPLOAD_USE = StringUtil.checkNull(GlobalVal.DRM_UPLOAD_USE);
					if(!"".equals(useDRM) && !"N".equals(DRM_UPLOAD_USE)){
						drmInfoMap.put("ORGFileDir", orginPath);
						drmInfoMap.put("DRMFileDir", targetPath);
						drmInfoMap.put("Filename", resultMap.get(FileUtil.UPLOAD_FILE_NM));	
						drmInfoMap.put("FileRealName", resultMap.get(FileUtil.FILE_NAME));							
						drmInfoMap.put("funcType", "upload");
						String returnValue = DRMUtil.drmMgt(drmInfoMap); // 복호화 
					}
					
					
					fileList.add(fileMap);
					seqCnt++;
				}
			}
			fileMgtService.save(fileList, cmmMap);
			
			if (!orginPath.equals("")) {
				FileUtil.deleteDirectory(orginPath);
			}
			
			target.put(AJAX_SCRIPT,  "parent.viewMessage();parent.$('#isSubmit').remove();");
			
		}catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.dhtmlx.alert('" + MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068") + "');parent.$('#isSubmit').remove()");
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	
	@RequestMapping(value="/saveTaskFile.do")
	public String saveTaskFile(MultipartHttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		XSSRequestWrapper xss = new XSSRequestWrapper(request);
		try{
			for (Iterator i = cmmMap.entrySet().iterator(); i.hasNext();) {
			    Entry e = (Entry) i.next(); // not allowed
			    if(!e.getKey().equals("loginInfo") && e.getValue() != null) {
			    	cmmMap.put(e.getKey(), xss.stripXSS(e.getValue().toString()));
			    }
			}
			String itemID 	= StringUtil.checkNull(xss.getParameter("itemID"), "");			
			String fltpCode = StringUtil.checkNull(xss.getParameter("fltpCode"), "");
			String fileMgt 	= StringUtil.checkNull(xss.getParameter("fileMgt"), "ITM");
			String userId 	= StringUtil.checkNull(StringUtil.checkNull(cmmMap.get("sessionUserId")), "");
			String linkType = StringUtil.checkNull(xss.getParameter("linkType"), "1");
			String fileID = StringUtil.checkNull(xss.getParameter("fileID"), "");
			String parentID = StringUtil.checkNull(xss.getParameter("parentID"), "");
			String projectID = StringUtil.checkNull(xss.getParameter("projectID"), "");			
			String changeSetID = StringUtil.checkNull(xss.getParameter("changeSetID"), "");
			String csrAuthorID = StringUtil.checkNull(xss.getParameter("csrAuthorID"), "");
			//taskResult fileUpload 추가 
			String sysfile = StringUtil.checkNull(xss.getParameter("sysFile"), "");
			String taskID = StringUtil.checkNull(xss.getParameter("taskID"), "");
			String taskTypeCode = StringUtil.checkNull(xss.getParameter("taskTypeCode"), "");
			
			//기존파일 삭제 
			File existFile = new File(sysfile); 
			if(existFile.isFile() && existFile.exists()){existFile.delete();}
			
			List fileList = new ArrayList();
			Map fileMap = new HashMap();;
			Map setMap = new HashMap();
			setMap.put("fltpCode",fltpCode);
			
			String filePath = StringUtil.checkNull(fileMgtService.selectString("fileMgt_SQL.getFilePath",setMap));
			File dirFile = new File(filePath);if(!dirFile.exists()) { dirFile.mkdirs();} 
			
			if("".equals(fileID)){ // 신규 등록				
				Iterator fileNameIter = request.getFileNames();
				String savePath = filePath; // 폴더 바꾸기				
				String fileName = "";
				int Seq = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", cmmMap));
				int seqCnt = 0;
				
				while (fileNameIter.hasNext()) {
				   MultipartFile mFile = request.getFile((String)fileNameIter.next());
				   fileName = mFile.getName();
				   
				   if (mFile.getSize() > 0) {						   
					   fileMap = new HashMap();
					   HashMap resultMap = FileUtil.uploadFile(mFile, savePath, true);
					   
					   fileMap.put("Seq", Seq);
					   fileMap.put("DocumentID", itemID);
					   fileMap.put("LinkType", linkType);
					   fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
					   fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
					   fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
					   fileMap.put("FilePath", resultMap.get(FileUtil.FILE_PATH));	
					   fileMap.put("FltpCode", fltpCode);
					   fileMap.put("FileMgt", fileMgt);
					   fileMap.put("userId", userId);
					   fileMap.put("projectID", projectID);
					   fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
					   fileMap.put("KBN", "insert");
					   fileMap.put("DocCategory", "ITM");
					   fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");
					
					   fileList.add(fileMap);
					   seqCnt++;
				   }
				}	
				fileMgtService.save(fileList, fileMap);
				
				if(taskID.equals("")){
					Map getMap = new HashMap();
					getMap.put("itemID", itemID);
					getMap.put("category", "A");
					getMap.put("changeSetID", changeSetID);
					getMap.put("taskTypeCode", taskTypeCode);
					taskID = StringUtil.checkNull(commonService.selectString("task_SQL.getTaskID",getMap),"");
				}
				if(taskID.equals("")){ // taskResult Actual == null일때 
					taskID = commonService.selectString("task_SQL.getMaxTaskID", setMap);
					setMap.put("taskID", taskID);
					setMap.put("seq", taskID);
					setMap.put("taskTypeCode", taskTypeCode);
					setMap.put("itemID", itemID);
					setMap.put("fileID", Seq);
					setMap.put("actor", userId);
					setMap.put("category", "A");
					setMap.put("changeSetID", changeSetID);
					setMap.put("userID", userId);
					commonService.insert("task_SQL.insertTask", setMap);
				}else{
					// task에 fileSeq update
					setMap.put("fileID", Seq);
					setMap.put("taskID", StringUtil.checkNull(cmmMap.get("taskID"), taskID));
					setMap.put("userId", userId);
					commonService.update("task_SQL.updateTaskFileID", setMap);
				}
				
			}else{ // 파일 수정
				Iterator fileNameIter = request.getFileNames();
				String savePath = filePath;
				String fileName = "";
				int Seq = Integer.parseInt(fileID);
				int seqCnt = 0;
				
				while (fileNameIter.hasNext()) {
				   MultipartFile mFile = request.getFile((String)fileNameIter.next());
				   fileName = mFile.getName();					   
				   if (mFile.getSize() > 0) {						   
					   fileMap = new HashMap();
					   HashMap resultMap = FileUtil.uploadFile(mFile, savePath, true);						   
					   fileMap.put("Seq", Seq);
					   fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
					   fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
					   fileMap.put("FltpCode", fltpCode);
					   fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
					   fileMap.put("sessionUserId", userId);
					   fileMap.put("KBN", "update");
					   fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_update"); 						   
					   fileList.add(fileMap);
					   seqCnt++;
				   }
				}	
					fileMgtService.save(fileList, fileMap);
			}
			
			model.put("menu", getLabel(request, commonService)); /* Label Setting */
			model.put("changeSetID", changeSetID);
			model.put("itemID", itemID);
			model.put("projectID", projectID);
			model.put("csrAuthorID", csrAuthorID);
			model.put("parentID", parentID);
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT,  "parent.$('#isSubmit').remove();parent.fnCallBack();");
			
		}catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/saveCsrFile.do")
	public String saveCsrFile(HttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		Map fileMap  = new HashMap();
		List fileList = new ArrayList();
		try{
			int seqCnt = Integer.parseInt(commonService.selectString("fileMgt_SQL.itemFile_nextVal", cmmMap));
			String projectID = StringUtil.checkNull(request.getParameter("projectID"), "");	
			fileMap.put("projectID", projectID);
			String linkType = StringUtil.checkNull(cmmMap.get("linkType"), "1");
			
			cmmMap.put("fltpCode", "CSRDF");
			String filePath = StringUtil.checkNull(fileMgtService.selectString("fileMgt_SQL.getFilePath",cmmMap),GlobalVal.FILE_UPLOAD_ITEM_DIR);
			String orginPath = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//";
			String targetPath = filePath;
			
			System.out.println("filePath :: "+filePath);
			File dirFile = new File(filePath);if(!dirFile.exists()) { dirFile.mkdirs();} 
			List tmpFileList = FileUtil.copyFiles(orginPath, targetPath);
			
			if(tmpFileList.size() != 0){
				for(int i=0; i<tmpFileList.size();i++){
					fileMap=new HashMap(); 
					HashMap resultMap=(HashMap)tmpFileList.get(i);
					fileMap.put("Seq", seqCnt);
					fileMap.put("DocumentID", projectID);
					fileMap.put("projectID", projectID);
					fileMap.put("FileName", resultMap.get(FileUtil.UPLOAD_FILE_NM));
					fileMap.put("FileRealName", resultMap.get(FileUtil.ORIGIN_FILE_NM));
					fileMap.put("FileSize", resultMap.get(FileUtil.FILE_SIZE));
					fileMap.put("FilePath", filePath);
					fileMap.put("FileFormat", resultMap.get(FileUtil.FILE_EXT));
					fileMap.put("FileMgt", "PJT");
					fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
					fileMap.put("userId", cmmMap.get("sessionUserId"));
					fileMap.put("LinkType", linkType);
					fileMap.put("FltpCode", "CSRDF");
					fileMap.put("KBN", "insert");
					fileMap.put("DocCategory", "PJT");
					fileMap.put("SQLNAME", "fileMgt_SQL.itemFile_insert");					
					fileList.add(fileMap);
					seqCnt++;
				}
			}
			
			if(fileList.size() != 0){
				fileMgtService.save(fileList, fileMap);
			}
			target.put(AJAX_SCRIPT,  "parent.viewMessage();parent.$('#isSubmit').remove();");
		}catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.dhtmlx.alert('" + MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068") + "');parent.$('#isSubmit').remove();");
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/subItemFileList.do")
	public String subFileList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/subItemFileList";
		try {
			String itemID = StringUtil.checkNull(request.getParameter("s_itemID"), "");			
			String showVersion = StringUtil.checkNull(request.getParameter("varFilter"), "N");	
			String showValid = StringUtil.checkNull(request.getParameter("varFilter"), "N");	
			String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"));
			
			Map setData = new HashMap();
			setData.put("itemID", itemID);
			setData.put("sessionCurrLangType", cmmMap.get("sessionCurrLangType"));
			Map itemTypeMap = commonService.select("common_SQL.itemTypeCode_commonSelect", setData);
			model.put("itemID", itemID);
			model.put("showValid", showValid);
			model.put("showVersion", showVersion);
			model.put("fltpCode", fltpCode);
			model.put("itemTypeName", StringUtil.checkNull(itemTypeMap.get("NAME")));
			model.put("itemTypeCode", StringUtil.checkNull(itemTypeMap.get("CODE")));
			model.put("menu", getLabel(request, commonService));
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	// Manage external files
	
	@RequestMapping(value="/extFileUpdateCount.do")
	public String extFileUpdateCount(HttpServletRequest request, Map cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();
		try {
			HashMap setMap = new HashMap();
			String fileSeq = StringUtil.checkNull(request.getParameter("fileSeq"));
			String scrnType = StringUtil.checkNull(request.getParameter("scrnType"),"");
			
			setMap.put("Seq",fileSeq);
			// 각 파일 테이블의 [DownCNT] update
			if(scrnType.equals("BRD")){
				setMap.put("TableName", "TB_BOARD_ATTCH");
			} else if(scrnType.equals("ISSUE")){
				setMap.put("TableName", "TB_ISSUE_FILE");
			} else {
				setMap.put("TableName", "TB_FILE");
			}

			commonService.update("fileMgt_SQL.itemFileDownCnt_update", setMap);
			target.put(AJAX_ALERT, ""); // 삭제 성공
			target.put(AJAX_SCRIPT, "");
		}
		catch(Exception e) {			
			System.out.println(e.toString());
			throw new ExceptionUtil(e.toString());
		}
		
		model.addAttribute(AJAX_RESULTMAP, target);		
		return nextUrl(AJAXPAGE);		
		
	}
	
	@RequestMapping(value = "/modExtFilePop.do")
	public String modExtFilePop(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		try {
			// 임시 폴더 삭제
			String reqSeq =  StringUtil.checkNull(request.getParameter("seqList"), "");
			String browserType =  StringUtil.checkNull(request.getParameter("browserType"), "");
			String isNew =  StringUtil.checkNull(request.getParameter("isNew"), "N");
			String itemClassCode =  StringUtil.checkNull(request.getParameter("itemClassCode"), "");
			String DocumentID =  StringUtil.checkNull(request.getParameter("DocumentID"), "");
			String seq[] = reqSeq.split(",");
			
			if(!"Y".equals(isNew)) {
				setMap.put("Seq", reqSeq);
				
				List fileList = commonService.selectList("fileMgt_SQL.getExtModFileList", setMap);
				model.put("fileList",fileList);
				
			}
			model.put("menu", getLabel(request, commonService));
			model.put("browserType",browserType);
			model.put("isNew",isNew);
			model.put("DocumentID",DocumentID);
			model.put("itemClassCode",itemClassCode);
			
		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}

		return nextUrl("/file/modExtFilePop");
	}
	
	@RequestMapping(value = "/saveEditExtFile.do")
	public String saveEditExtFile(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();		
		try{
			Map setMap = new HashMap();
			
			int maxCount = Integer.parseInt(StringUtil.checkNull(request.getParameter("maxCount"),"0"));
			String isNew = StringUtil.checkNull(request.getParameter("isNew"),"N");
			
			if ("Y".equals(isNew)) {
				for (int i = 0; i < maxCount ; i++) {
					String fltpCode = StringUtil.checkNull(request.getParameter("fltpCode"),""); 
					String name = StringUtil.checkNull(request.getParameter("Name_0"),"");
					String path = StringUtil.checkNull(request.getParameter("Path_0"),""); 
					String DocumentID = StringUtil.checkNull(request.getParameter("DocumentID"),""); 
					
					String seq = commonService.selectString("fileMgt_SQL.itemFile_nextVal", setMap);
					
					setMap.put("FltpCode", fltpCode);
					setMap.put("DocumentID", DocumentID);
					
					String projectID = commonService.selectString("model_SQL.getItemProjectID", setMap);
					String docCategory = commonService.selectString("fileMgt_SQL.getFltpDocCategory", setMap);
					String languageID = StringUtil.checkNull(cmmMap.get("sessionCurrLangType"),GlobalVal.DEFAULT_LANGUAGE);
					
					setMap.put("DocCategory", docCategory);
					setMap.put("FileRealName", name);
					setMap.put("FileFormat", FileUtil.getExt(name));
					//path = new String(URLEncoder.encode(path,"UTF-8"));
					setMap.put("Seq", seq);
					setMap.put("LinkType","EXT");
					setMap.put("FilePath", path);
					setMap.put("FileName", "");
					setMap.put("projectID", projectID);
					setMap.put("FileSize", 0);
					setMap.put("FileMgt", docCategory);
					setMap.put("userId", cmmMap.get("sessionUserId"));
					setMap.put("LanguageID", languageID);
					
					commonService.insert("fileMgt_SQL.itemFile_insert", setMap);
				}
			} else {
				for (int i = 0; i < maxCount ; i++) {
					String seq = StringUtil.checkNull(request.getParameter("ID_"+i),"");
					String name = StringUtil.checkNull(request.getParameter("Name_"+i),"");
					String path = StringUtil.checkNull(request.getParameter("Path_"+i),""); 
					String code = StringUtil.checkNull(request.getParameter("Code_"+i),"");
					
					setMap.put("FileRealName", name);
					//path = new String(URLEncoder.encode(path,"UTF-8"));
					setMap.put("Seq", seq);
					setMap.put("FilePath", path);
					setMap.put("fileOption", "ExtLink");
					setMap.put("userId", cmmMap.get("sessionUserId"));
					
					commonService.update("fileMgt_SQL.updateExtFile", setMap);
				}
			}			
				
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();parent.selfClose();");	
		}catch(Exception e){
			System.out.println(e.toString());
			target.put(AJAX_SCRIPT, "this.$('#isSubmit').remove()");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
		model.addAttribute(AJAX_RESULTMAP, target);		
		return nextUrl(AJAXPAGE);		
	}
	
	@RequestMapping(value = "/setViewFilePath.do")
	public String setViewFilePath(HttpServletRequest request, HashMap commandMap, ModelMap model) throws Exception {
		String key = request.getParameter("key");
		String seq = request.getParameter("seq");
		Map setMap = new HashMap();
		HashMap target = new HashMap();
		String url = GlobalVal.DOC_VIEWER_URL+"/SynapDocViewServer/viewer/doc.html?key="+key+"&contextPath=/SynapDocViewServer";
		try {
			setMap.put("Seq", seq);
			setMap.put("FilePath", url);		
//			setMap.put("userId", commandMap.get("sessionUserId"));
			setMap.put("flag", "Y");
			
			commonService.update("fileMgt_SQL.updateExtFile", setMap);
			//target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00150")); // 상신 완료
			target.put(AJAX_SCRIPT,"parent.fnCallback('"+url+"');parent.$('#isSubmit').remove()");

		} catch (Exception e) {
			System.out.println(e.toString());
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
			target.put(AJAX_ALERT,MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}

		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value = "/viewerFileDownload.do")
	public String viewerFileDownload(HttpServletRequest request, HashMap cmmMap, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		Map setMap = new HashMap();
		String filename = "";
		String original = "";
		String downFile = "";
		String returnValue = "";
		
		setMap.put("Seq", seq);
		Map fileInfo = commonService.select("fileMgt_SQL.selectDownFile",setMap);
		
		original = (String)fileInfo.get("original");
		filename = (String)fileInfo.get("filename");
		downFile = (String)fileInfo.get("downFile");
				
		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return null;
		}

		if ("".equals(original)) {
			original = filename;
		}

		HashMap drmInfoMap = new HashMap();
		
		String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
		String userName = StringUtil.checkNull(cmmMap.get("sessionUserNm"));
		String teamID = StringUtil.checkNull(cmmMap.get("sessionTeamId"));
		String teamName = StringUtil.checkNull(cmmMap.get("sessionTeamName"));
		
		drmInfoMap.put("userID", userID);
		drmInfoMap.put("userName", userName);
		drmInfoMap.put("teamID", teamID);
		drmInfoMap.put("teamName", teamName);
		drmInfoMap.put("orgFileName", original);
		drmInfoMap.put("downFile", filename);
		
		// file DRM 적용
		String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
		String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
		if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
			drmInfoMap.put("ORGFileDir", downFile.replace(filename, ""));
			drmInfoMap.put("DRMFileDir", StringUtil.checkNull(DrmGlobalVal.DRM_DECODING_FILEPATH) + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//");
			drmInfoMap.put("Filename", filename);
			drmInfoMap.put("funcType", "download");
			returnValue = DRMUtil.drmMgt(drmInfoMap); // 암호화 
		}
		

		if(!"".equals(returnValue)) {
			downFile = returnValue;
		}

		request.setAttribute("downFile", downFile);
		request.setAttribute("orginFile", original); // 20140627 request.setAttribute("orginFile", enOriginal); 수정

		FileUtil.downFile(request, response);

		return null;
	}
	
	@RequestMapping(value = "/updateFileRegMember.do")
	public String updateFileRegMember(HttpServletRequest request, HashMap commandMap, ModelMap model) throws Exception {
		String fileSeqs = StringUtil.checkNull(request.getParameter("fileSeqs"));
		String memberID = StringUtil.checkNull(request.getParameter("memberID"));
		Map setMap = new HashMap();
		HashMap target = new HashMap();
		try {
			setMap.put("seq", fileSeqs);
			setMap.put("regMemberID", memberID);		
			setMap.put("userID", commandMap.get("sessionUserId"));
			
			commonService.update("fileMgt_SQL.updateFileRegMember", setMap);
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00067")); 
			target.put(AJAX_SCRIPT, "doSearchList();");

		} catch (Exception e) {
			System.out.println(e.toString());
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
			target.put(AJAX_ALERT,MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}

		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/cxnItemFileList.do")
	public String cxnItemFileList(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		String url = "file/cxnItemFileList";
		try {
			String itemID = StringUtil.checkNull(request.getParameter("s_itemID"), "");			
			String itemIDs = request.getParameter("itemIDs");
			Map setData = new HashMap();
			setData.put("itemID", itemID);
			setData.put("sessionCurrLangType", cmmMap.get("sessionCurrLangType"));
			Map itemTypeMap = commonService.select("common_SQL.itemTypeCode_commonSelect", setData);
			model.put("itemID", itemID);
			model.put("itemIDs", itemIDs);
			model.put("s_itemID", itemID);
			model.put("itemTypeName", StringUtil.checkNull(itemTypeMap.get("NAME")));
			model.put("itemTypeCode", StringUtil.checkNull(itemTypeMap.get("CODE")));
			
			model.put("childCXN", StringUtil.checkNull(request.getParameter("childCXN"), ""));
			model.put("cxnTypeList", StringUtil.checkNull(request.getParameter("cxnTypeList"), ""));
			model.put("filter", StringUtil.checkNull(request.getParameter("filter"), ""));
			model.put("option", StringUtil.checkNull(request.getParameter("option"), ""));
						
			model.put("menu", getLabel(request, commonService));
		}
		catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
			
		return nextUrl(url);
	}
	
	@RequestMapping(value="/updateFileBlocked.do")
	public String updateFileBlocked(HttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		
		try{
			Map setMap = new HashMap();
			
			String seq =  StringUtil.checkNull(request.getParameter("seq"), "");
			
			setMap.put("seq",seq);
			
			commonService.update("fileMgt_SQL.updateFileBlocked", setMap);
			
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
			target.put(AJAX_SCRIPT,  "doSearchList();parent.$('#isSubmit').remove();");
			
		}catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
			//target.put(AJAX_ALERT, " 저장중 오류가 발생하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00068")); // 오류 발생
		}
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/editFileName.do")
	public String editFileName(HttpServletRequest request,HashMap cmmMap, ModelMap model) throws  ServletException, IOException, Exception {
		Map target = new HashMap();
		Map setMap = new HashMap();
	
		List fileList = new ArrayList();
		Map fileMap = null;
		Map stusMap = new HashMap();
		try{
			
				String documentID = StringUtil.checkNull(request.getParameter("documentID"));
				
				setMap.put("DocumentID", documentID);
				setMap.put("DocCategory", "ITM");
				setMap.put("isPublic", "N");
				setMap.put("languageID", cmmMap.get("sessionCurrLangType"));			
				List returnData = commonService.selectList("fileMgt_SQL.getFile_gridList", setMap);
				
				if(returnData != null) {
					for (int i = 0; i < returnData.size() ; i++) {		
					    Map map = (Map) returnData.get(i);
						String Seq = StringUtil.checkNull(map.get("Seq")); 
						fileMap = new HashMap();
					    fileMap.put("Seq", Seq);
					    fileMap.put("FileRealName", request.getParameter("Name_"+Seq));
					    fileMap.put("LanguageID", cmmMap.get("sessionCurrLangType"));
					    fileMap.put("FilePath", map.get("FilePath"));
					    fileMap.put("userId", cmmMap.get("sessionUserId"));
					    fileMap.put("KBN", "update");
	
					    fileMap.put("SQLNAME", "fileMgt_SQL.updateExtFile"); 
					   
					    fileList.add(fileMap);
					}	
				}
				
				fileMgtService.save(fileList, fileMap);				
			
				target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00067")); // 저장 성공
				target.put(AJAX_SCRIPT,  "parent.selfClose();");
			
		}catch (Exception e) {
				System.out.println(e);
				throw new ExceptionUtil(e.toString());
		}
		
		model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	}
	
	// 외부(File DonwLoad) 
	@RequestMapping(value = "/zhw_fileDownload.do")
	public String zhw_fileDownload(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		String url = "/file/zhw_fileDownload";		
		try {				
				String fileID = StringUtil.checkNull(request.getParameter("fileID")); 				
				model.put("fileID", fileID);	
				model.put("menu", getLabel(request, commonService)); //  Label Setting 			
		} catch (Exception e) {
			System.out.println(e);
		}
		return nextUrl(url);
	}
	
	
	@RequestMapping(value = "/selectFilePop.do")
	public String selectFilePop(HttpServletRequest request, HashMap cmmMap,ModelMap model) throws Exception {
		try {
			Map setMap = new HashMap();
			Map ParentMap = new HashMap();
			
			String s_itemID = StringUtil.checkNull(request.getParameter("s_itemID"));


			String hideBlocked = StringUtil.checkNull(request.getParameter("hideBlocked"),"Y"); 
			setMap.put("hideBlocked",hideBlocked);
			setMap.put("DocumentID",s_itemID);
			setMap.put("s_itemID",s_itemID);
			setMap.put("languageID", cmmMap.get("sessionCurrLangType"));
			setMap.put("DocCategory","ITM");
			setMap.put("isPublic", "N");
			setMap.put("itemId",s_itemID);

			Map result  = fileMgtService.select("fileMgt_SQL.selectItemAuthorID",setMap);
			String itemFileOption = fileMgtService.selectString("fileMgt_SQL.getFileOption",setMap);
			
			setMap.put("fromToItemID",s_itemID);
			List itemList = commonService.selectList("item_SQL.getCxnItemIDList", setMap);
			Map getMap = new HashMap();
			/** 첨부문서 관련문서 합치기, 관련문서 itemClassCodep 할당된 fltpCode 로 filtering */
			String rltdItemId = "";
			for(int i = 0; i < itemList.size(); i++){
				ParentMap = (HashMap)itemList.get(i);
				getMap.put("ItemID", ParentMap.get("ItemID"));
				if (i < itemList.size() - 1) {
				   rltdItemId += StringUtil.checkNull(ParentMap.get("ItemID"))+ ",";
				}else{
					rltdItemId += StringUtil.checkNull(ParentMap.get("ItemID"));
				}
			}
			
			setMap.put("rltdItemId", rltdItemId);			
			List fileList = commonService.selectList("fileMgt_SQL.getFile_gridList", setMap);		

			if(fileList.size() == 1) {
				model.put("downYN","Y");
				Map temp = (Map)fileList.get(0);				
				model.put("Seq", temp.get("Seq"));
				
			}
			
			String sessionUserID = cmmMap.get("sessionUserId").toString();
			// Login user editor 권한 추가
			String sessionAuthLev = String.valueOf(cmmMap.get("sessionAuthLev")); // 시스템 관리자
			if (StringUtil.checkNull(result.get("AuthorID")).equals(sessionUserID)
					|| StringUtil.checkNull(result.get("LockOwner")).equals(cmmMap.get("sessionUserId"))
					|| "1".equals(sessionAuthLev)) {
				model.put("myItem", "Y");
			}
			model.put("itemFileOption", itemFileOption);	
			model.put("fileList", fileList);
			model.put("menu", getLabel(request, commonService)); /* Label Setting */

		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}
		return nextUrl("/file/selectFilePop");
	}
	
	@RequestMapping(value = "/addFilePop.do")
	public String popupAddFile(HttpServletRequest request, HashMap cmmMap,
			ModelMap model) throws Exception {
		Map resultMap = new HashMap();
		Map result = new HashMap();
		try {
			// 임시 폴더 삭제
			String scrnType = StringUtil.checkNull(cmmMap.get("scrnType"));
			String id = StringUtil.checkNull(cmmMap.get("id")); 
			String docCategory = StringUtil.checkNull(cmmMap.get("docCategory")); 
			String changeSetID = StringUtil.checkNull(cmmMap.get("changeSetID")); 
			String projectID = StringUtil.checkNull(cmmMap.get("projectID"));
			String fltpCode = StringUtil.checkNull(cmmMap.get("fltpCode"),"");
			String delTmpFlg = StringUtil.checkNull(cmmMap.get("delTmpFlg"),"");
			
			String path = "";
			path = GlobalVal.FILE_UPLOAD_BASE_DIR + cmmMap.get("sessionUserId");
			if (!path.equals("") && "Y".equals(delTmpFlg)) {
				FileUtil.deleteDirectory(path);					
			}
			model.put("scrnType", scrnType);
			model.put("docCategory", docCategory);
			model.put("browserType", StringUtil.checkNull(cmmMap.get("browserType"), "IE"));
			model.put("usrId", StringUtil.checkNull(cmmMap.get("sessionUserId")));
			model.put("mgtId", StringUtil.checkNull(cmmMap.get("mgtId")));
			model.put("id", id);
			model.put("filePath", path);
			model.put("taskID", StringUtil.checkNull(cmmMap.get("taskID")));
			model.put("fltpCodeTsk", StringUtil.checkNull(cmmMap.get("fltpCode")));
			model.put("fileID", StringUtil.checkNull(cmmMap.get("fileID")));
			model.put("isTask", StringUtil.checkNull(cmmMap.get("isTask")));
			model.put("projectID",projectID);
			model.put("screenType", StringUtil.checkNull(cmmMap.get("screenType")));
			model.put("gubun", StringUtil.checkNull(cmmMap.get("gubun")));
			model.put("fltpCode", fltpCode);
			model.put("changeSetID", changeSetID);

			if (scrnType.equals("BRD")) {
				result.put("itemClassCode", "");
				result.put("itemID", "");
			} else {
				if (!id.equals("")) {
					cmmMap.put("itemID", id);
					resultMap = fileMgtService.select("fileMgt_SQL.getItemClassCode", cmmMap);
					result.put("itemClassCode", resultMap.get("itemClassCode"));
					result.put("itemID", id);
				}
			}
			model.put("instanceNo", StringUtil.checkNull(cmmMap.get("instanceNo")));
			model.put("instanceClass", StringUtil.checkNull(cmmMap.get("instanceClass")));
			model.put("menu", getLabel(request, commonService));
			model.addAttribute(AJAX_RESULTMAP, result);

		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}

		return nextUrl("/popup/addFilePop");
	}
	
	@RequestMapping(value = "/addFilePopV4.do")
	public String addFilePopV4(HttpServletRequest request,  HashMap cmmMap, ModelMap model) throws Exception {
		Map resultMap = new HashMap();
		Map result = new HashMap();
		try {
			// 임시 폴더 삭제
			String scrnType = StringUtil.checkNull(cmmMap.get("scrnType"));
			String id = StringUtil.checkNull(cmmMap.get("id")); 
			String docCategory = StringUtil.checkNull(cmmMap.get("docCategory")); 
			String changeSetID = StringUtil.checkNull(cmmMap.get("changeSetID")); 
			String projectID = StringUtil.checkNull(cmmMap.get("projectID"));
			String fltpCode = StringUtil.checkNull(cmmMap.get("fltpCode"),"");
			
			String path = "";

			
			path = GlobalVal.FILE_UPLOAD_BASE_DIR + cmmMap.get("sessionUserId");
			if (!path.equals("")) {
				FileUtil.deleteDirectory(path);
			}
			

			model.put("scrnType", scrnType);
			model.put("docCategory", docCategory);
			model.put("browserType", StringUtil.checkNull(cmmMap.get("browserType"), "IE"));
			model.put("usrId", StringUtil.checkNull(cmmMap.get("sessionUserId")));
			model.put("mgtId", StringUtil.checkNull(cmmMap.get("mgtId")));
			model.put("id", id);
			model.put("filePath", path);
			model.put("taskID", StringUtil.checkNull(cmmMap.get("taskID")));
			model.put("fltpCodeTsk", StringUtil.checkNull(cmmMap.get("fltpCode")));
			model.put("fileID", StringUtil.checkNull(cmmMap.get("fileID")));
			model.put("isTask", StringUtil.checkNull(cmmMap.get("isTask")));
			model.put("projectID",projectID);
			model.put("screenType", StringUtil.checkNull(cmmMap.get("screenType")));
			model.put("gubun", StringUtil.checkNull(cmmMap.get("gubun")));
			model.put("fltpCode", fltpCode);
			model.put("changeSetID", changeSetID);

			if (scrnType.equals("BRD")) {
				result.put("itemClassCode", "");
				result.put("itemID", "");
			} else {
				if (!id.equals("")) {
					cmmMap.put("itemID", id);
					resultMap = fileMgtService.select("fileMgt_SQL.getItemClassCode", cmmMap);
					result.put("itemClassCode", resultMap.get("itemClassCode"));
					result.put("itemID", id);
				}
			}
			model.put("instanceNo", StringUtil.checkNull(cmmMap.get("instanceNo")));
			model.put("instanceClass", StringUtil.checkNull(cmmMap.get("instanceClass")));
			model.put("menu", getLabel(request, commonService));
			model.addAttribute(AJAX_RESULTMAP, result);

		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionUtil(e.toString());
		}

		return nextUrl("/file/addFilePopV4");
	}
	
	/**
	 * 하위 항목 [Edit ] 이벤트
	 * 
	 * @param request
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editFileNamePop.do")
	public String editFileNamePop(HttpServletRequest request, HashMap cmmMap,
			ModelMap model) throws Exception {
		try {

			Map setMap = new HashMap();

			String documentID = StringUtil.checkNull(request.getParameter("DocumentID"));

			setMap.put("DocumentID", documentID);
			setMap.put("DocCategory", "ITM");
			setMap.put("isPublic", "N");
			setMap.put("languageID", cmmMap.get("sessionCurrLangType"));			
			List returnData = commonService.selectList("fileMgt_SQL.getFile_gridList", setMap);

			model.put("s_itemID", documentID);
			model.put("getList", returnData);
			model.put("menu", getLabel(request, commonService)); /* Label Setting */

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nextUrl("/popup/editFileNamePop");
	}
	
	
	@RequestMapping(value="/downloadExtLinkFile.do")
	 public String downloadExtLinkFile(HttpServletRequest request, HttpServletResponse response,HashMap commandMap, ModelMap model) throws Exception{

		Map target = new HashMap();
		Map setMap = new HashMap();
		Map setData = new HashMap();
		String url = "";
		
		String reqOrgiFileName[] = request.getParameter("seq").split(",");//StringUtil.checkNull(request.getParameter("originalFileName")).split(",");
		String reqSysFileName[] = request.getParameter("seq").split(",");//StringUtil.checkNull(request.getParameter("sysFileName")).split(",");
		String reqFilePath[] = request.getParameter("seq").split(",");
		String reqSeq[] = request.getParameter("seq").split(",");
		String isHom = request.getParameter("isHom");
		String scrnType = StringUtil.checkNull(request.getParameter("scrnType"));
		String returnValue = "";
		

		String path=GlobalVal.FILE_UPLOAD_BASE_DIR + commandMap.get("sessionUserId");
		if(!path.equals("")){FileUtil.deleteDirectory(path);}	

		for(int i=0; reqSeq.length>i; i++){
			setData.put("seq", reqSeq[i]);
		
			reqFilePath[i] = commonService.selectString("fileMgt_SQL.getFilePathInSeq", setData);

			reqOrgiFileName[i] = commonService.selectString("fileMgt_SQL.getFileName", setData);
			reqSysFileName[i] = reqFilePath[i] + commonService.selectString("fileMgt_SQL.getFileSysName", setData);
			downloadToDir(new URL(reqFilePath[i]),new File(GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(commandMap.get("sessionUserId"))), reqOrgiFileName[i]);
			reqOrgiFileName[i] = GlobalVal.FILE_UPLOAD_BASE_DIR + StringUtil.checkNull(commandMap.get("sessionUserId")) + "//" + reqOrgiFileName[i];
			reqSysFileName[i] = reqOrgiFileName[i];
		
		}
		/*
		File orgiFileName = null;
		File sysFileName = null;
		String filePath = null;
		String viewName = null;
		
		try{
			int fileCnt = reqOrgiFileName.length;
			
			HashMap drmInfoMap = new HashMap();
			
			String userID = StringUtil.checkNull(commandMap.get("sessionUserId"));
			String userName = StringUtil.checkNull(commandMap.get("sessionUserNm"));
			String teamID = StringUtil.checkNull(commandMap.get("sessionTeamId"));
			String teamName = StringUtil.checkNull(commandMap.get("sessionTeamName"));
			
			drmInfoMap.put("userID", userID);
			drmInfoMap.put("userName", userName);
			drmInfoMap.put("teamID", teamID);
			drmInfoMap.put("teamName", teamName);
			
			 for(int i=0; i<reqOrgiFileName.length; i++){
				returnValue = "";
				orgiFileName = new File(reqOrgiFileName[i]);
				sysFileName = new File(reqSysFileName[i]);
				
				drmInfoMap.put("orgFileName", reqOrgiFileName[i]);
				drmInfoMap.put("downFile", reqSysFileName[i].replace(reqFilePath[i], ""));
									
				String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
				String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
				if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
					drmInfoMap.put("ORGFileDir", reqFilePath[i]);
					drmInfoMap.put("DRMFileDir", StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH) + StringUtil.checkNull(commandMap.get("sessionUserId"))+"//");
					drmInfoMap.put("Filename", reqSysFileName[i].replace(reqFilePath[i], ""));
					drmInfoMap.put("funcType", "download");
					returnValue = DRMUtil.drmMgt(drmInfoMap); // 암호화 
				}
				
				if(!sysFileName.exists()){
					viewName = orgiFileName.getName();
					// 파일이 없을경우 변경했던 파일명 원복 
					for(int k=0; k<reqOrgiFileName.length; k++){
							orgiFileName = new File(reqOrgiFileName[k]);
							sysFileName = new File(reqSysFileName[k]);
							orgiFileName.renameTo(sysFileName);
					 }
					//target.put(AJAX_ALERT, "해당 파일을 서버에서 찾을 수 없습니다");
					target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00113"));
					model.addAttribute(AJAX_RESULTMAP, target);
				
					return nextUrl(AJAXPAGE);
				}

				if(!"".equals(returnValue)) {
					sysFileName = new File(returnValue);
				}
				
				if(sysFileName.exists()){
					
					sysFileName.renameTo(orgiFileName);
				}
			 }
			 
			 // zip file명 만들기 
			 Calendar cal = Calendar.getInstance();
			 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
			 java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HHmmssSSS");
			 String sdate = sdf.format(cal.getTime());
			 String stime = sdf2.format(cal.getTime());
			 String mkFileNm = sdate+stime;
			 
			 path = GlobalVal.FILE_UPLOAD_ZIPFILE_DIR;
			 String fullPath = GlobalVal.FILE_UPLOAD_ZIPFILE_DIR+"downFiles"+sdf2+".zip";
			 String newFileNm = "FILE_"+mkFileNm+".zip";
			 
			 File zipFile = new File(fullPath); 
			 File dirFile = new File(path);
		  
			 if(!dirFile.exists()) {
			     dirFile.mkdirs();
			 } 

			 ZipOutputStream zos = null;
			 zos = new ZipOutputStream(new FileOutputStream(zipFile));
			 
			 byte[] buffer = new byte[1024 * 2];
			 int k = 0;
			 for(String file : reqOrgiFileName) {
				 filePath = reqFilePath[k];
				 if(new File(file).isDirectory()) { continue; }
		                
		         BufferedInputStream bis = new BufferedInputStream(new FileInputStream (file)); 
		         file = file.replace(filePath, ""); 
		         
		         zos.putNextEntry(new ZipEntry(file));
		        			         
		         int length = 0;
		         while((length = bis.read(buffer)) != -1) {
		            zos.write(buffer, 0, length);
		         }
		                
		         zos.closeEntry();
		         bis.close();
		         k++;
			 }
	    	 zos.closeEntry();
             zos.close();
		
         //    request.setAttribute("orginFile", arg1);
			// 파일이름 원복
			for(int i=0; i<reqOrgiFileName.length; i++){
				setMap = new HashMap();
				orgiFileName = new File(reqOrgiFileName[i]);
				sysFileName = new File(reqSysFileName[i]);
				
				if(orgiFileName.exists()){
					orgiFileName.renameTo(sysFileName);
				}
				
				setMap.put("Seq",reqSeq[i]);
				// 각 파일 테이블의 [DownCNT] update
				if(scrnType.equals("BRD")){
					setMap.put("TableName", "TB_BOARD_ATTCH");
				} else if(scrnType.equals("ISSUE")){
					setMap.put("TableName", "TB_ISSUE_FILE");
				} else {
					setMap.put("TableName", "TB_FILE");
				}
				
				if(scrnType.equals("INST")){ 
					setMap.put("fileID",reqSeq[i]);
					fileMgtService.update("instanceFile_SQL.updateInstanceFileDownCNT", setMap);
				}else{ fileMgtService.update("fileMgt_SQL.itemFileDownCnt_update", setMap); }
			}
			String downFile = fullPath;
			request.setAttribute("downFile", downFile);
			request.setAttribute("orginFile", newFileNm);
			FileUtil.flMgtdownFile(request, response);
		
			
			//target.put(AJAX_ALERT, "파일다운로드가 완료되었습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(commandMap.get("sessionCurrLangCode") + ".WM00079"));
			target.put(AJAX_SCRIPT, "doSearchList();");
			
		}catch (Exception e) {
			 System.out.println(e);
			 throw new ExceptionUtil(e.toString());
			 //target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove()");
			 //target.put(AJAX_ALERT, "지정된 파일을 찾을 수 없습니다");
		}*/
		//model.addAttribute(AJAX_RESULTMAP, target);	
		return nextUrl(AJAXPAGE);
	 }
		
	public static void downloadToDir(URL url, File dir, String FileName) throws IOException {
		if (url==null) throw new IllegalArgumentException("url is null.");
		if (dir==null) throw new IllegalArgumentException("directory is null.");
		if (!dir.exists()) dir.mkdir();
		if (!dir.isDirectory()) throw new IllegalArgumentException("directory is not a directory.");
		downloadTo(url, dir, true, FileName);
	}
	
    private static void downloadTo(URL url, File targetFile, boolean isDirectory, String fileName) throws IOException{            
    	
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            File saveFilePath=null;
            
            if (isDirectory) {
            	fileName=URLDecoder.decode(fileName);
	            saveFilePath = new File(targetFile, fileName);
            }
            else {
            	saveFilePath=targetFile;
            }
            
            InputStream inputStream = httpConn.getInputStream();
             
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
            
            try {
            	 int bytesRead = -1;
                 byte[] buffer = new byte[4096];
                 while ((bytesRead = inputStream.read(buffer)) != -1) {
                     outputStream.write(buffer, 0, bytesRead);
                 }
            } catch ( Exception e ) {
            	System.out.println(e.toString());
            	throw e;
            } finally {
            	if(outputStream != null) {
            		try {
            			outputStream.close();
            			inputStream.close();
            		} catch ( Exception e ) {
                    	System.out.println(e.toString());
                    	throw e;
                    }
            	}
            }            
            
            System.out.println("File downloaded to " + saveFilePath);
        } else {
            System.err.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();	    
    }
}