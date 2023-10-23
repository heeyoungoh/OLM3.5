package xbolt.cmm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.org.json.JSONArray;
import com.org.json.JSONObject;

import xbolt.cmm.framework.filter.XSSRequestWrapper;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.DRMUtil;
import xbolt.cmm.framework.util.DateUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.DrmGlobalVal;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;

/**
 * 공통 서블릿 처리
 * @Class Name : FileActionController.java
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
public class FileActionController extends XboltController{

	@Resource(name = "commonService")
	private CommonService commonService;

	/**
	 * 게시판에 업로드된 파일을 삭제한다.
	 * 2012/12/24
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/boardFileDelete.do")
	public String boardFileDelete(HashMap cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();		
		String delType = StringUtil.checkNull(cmmMap.get("delType"), "");
		if( delType.equals("1")){	//공지사항관련 파일 삭제
			cmmMap.put("BoardID", StringUtil.checkNull(cmmMap.get("BoardID"), ""));
			cmmMap.put("Seq", StringUtil.checkNull(cmmMap.get("Seq"), ""));
			target = commonService.select("boardFile_SQL.boardFile_select", cmmMap);	//new mode
		}
		try {
			
			String realFile = FileUtil.FILE_UPLOAD_DIR + target.get("FileName");
			File existFile = new File(realFile);
			if(existFile.exists() && existFile.isFile()){existFile.delete();}
			commonService.delete("boardFile_SQL.boardFile_delete", cmmMap);	//new mode
			/*
			if( delType.equals("BOARD")){
				//commonService.delete("CommonFile.commFile_delete", cmmMap);
			}
			 * */			
			//target.put(AJAX_ALERT, "파일 삭제가 성공하였습니다.");
			System.out.println("boardFileDelete try::"+MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00075"));
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

	//변경이력 파일첨부 삭제
	@RequestMapping(value="/changeSetFileDelete.do")
	public String changeSetFileDelete(HashMap cmmMap, ModelMap model) throws Exception {
		//Map target = commonService.select("CommonFile.commFile_select", cmmMap);
		Map map = new HashMap();
		Map target =  new HashMap();

		try {
			map.put("seq", StringUtil.checkNull(cmmMap.get("seq")));
			String FileName = StringUtil.checkNull(commonService.selectString("fileMgt_SQL.getFileSysName", map));	//new mode
			
			String fltpCode = StringUtil.checkNull(cmmMap.get("fltpCode"));
			map.put("fltpCode", fltpCode);
			String fltpPath = StringUtil.checkNull(commonService.selectString("fileMgt_SQL.getFilePath", map));
			String realFile = fltpPath + FileName;
			
			File existFile = new File(realFile);
			if(existFile.exists() && existFile.isFile()){existFile.delete();}

			//commonService.delete("CommonFile.commFile_delete", cmmMap);
			commonService.delete("project_SQL.changeSetFile_delete", cmmMap);	//new mode

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
	
	@RequestMapping(value="/itemFileDelete.do")
	public String itemFileDelete(HashMap cmmMap, ModelMap model) throws Exception {
		Map map = new HashMap();
		
		map.put("ItemID", cmmMap.get("ItemID"));
		map.put("Seq", cmmMap.get("Seq"));
		map.put("FilePath", GlobalVal.FILE_UPLOAD_ITEM_DIR);
		Map target = commonService.select("item_SQL.itemFile_select", map);	//new mode

		try {
			String linkType = StringUtil.checkNull(target.get("LinkType"), "");
			String realFile = StringUtil.checkNull(target.get("fullFileName"), "");
			if(linkType.equals("1") && realFile.length() > 0){
				File existFile = new File(realFile);
				if(existFile.exists() && existFile.isFile()){existFile.delete();}
			}
			commonService.delete("item_SQL.itemFile_delete", map);	//new mode

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
	

	@RequestMapping(value="/fileDelete.do")
	public String fileDelete(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		Map map = new HashMap();
		
		map.put("AttFileID", request.getParameter("AttFileID"));
		map.put("Seq", request.getParameter("Seq"));
		
		Map target = commonService.select("CommonFile.cmmFile_select", map);	//new mode

		try {
			String realFile = StringUtil.checkNull(target.get("fullFileName"), "");
			if(realFile.length() > 0){
				File existFile = new File(realFile);
				existFile.delete();
			}
			commonService.delete("CommonFile.cmmFile_delete", map);	//new mode

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

	
	/**
	 * 업로드된 파일을 삭제한다.
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fileDeleteNoMsg.do")
	public String fileDeleteNoMsg(Map cmmMap, ModelMap model) throws Exception {
		//Map target = commonService.select("CommonFile.commFile_select", cmmMap);
		Map target = commonService.select("CommonFile.cmmFile_select", cmmMap);	//new mode

		try {
			String realFile = FileUtil.FILE_UPLOAD_DIR + target.get("FILE_PATH") + File.separator + target.get("SYS_FILE_NAME");
			File existFile = new File(realFile);
			existFile.delete();

			//commonService.delete("CommonFile.commFile_delete", cmmMap);
			commonService.delete("CommonFile.cmmFile_delete", cmmMap);	//new mode

		}
		catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_ALERT, "파일 삭제중 오류가 발생하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00076")); // 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	/**
	 * 파일 다운로드 기능을 제공한다.
	 */
	@RequestMapping(value = "/fileDown.do")
	public String fileDown(HttpServletRequest request, Map cmmMap, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		String scrnType = request.getParameter("scrnType");
		Map setMap = new HashMap();
		setMap.put("seq", seq);
		String filename = "";
		String original = "";
		String downFile = "";
		String ORGFileDir = "";
		
		if("BRD".equals(scrnType)) {			
			filename = commonService.selectString("boardFile_SQL.getFileSysName", setMap);	//new mode
			original =  commonService.selectString("boardFile_SQL.getFileName", setMap);	//new mode
			downFile = GlobalVal.FILE_UPLOAD_BOARD_DIR + filename; 
			ORGFileDir = GlobalVal.FILE_UPLOAD_BOARD_DIR;
		}
		else if("excel".equals(scrnType)) {			
			filename = request.getParameter("filename");
			original =  request.getParameter("original");
			original = new String(original.getBytes("8859_1"), "UTF-8");
			downFile = FileUtil.FILE_EXPORT_DIR + filename;
			ORGFileDir = FileUtil.FILE_EXPORT_DIR;
		}
		else {
			filename = commonService.selectString("fileMgt_SQL.getFileSysName", setMap);	//new mode
			original =  commonService.selectString("fileMgt_SQL.getFileName", setMap);	//new mode
			downFile = FileUtil.FILE_UPLOAD_DIR + filename;
			ORGFileDir = FileUtil.FILE_UPLOAD_DIR;
		}

		//String enOriginal = new String(original.getBytes("8859_1"), "UTF-8");
		
		
		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return "cmm/utl/EgovFileDown";
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
		drmInfoMap.put("downFile", downFile);
		
		// file DRM 적용
		String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
		String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
		if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
			//DRMUtil.setDRM(drmInfoMap);
			drmInfoMap.put("ORGFileDir", ORGFileDir);
			drmInfoMap.put("DRMFileDir", StringUtil.checkNull(DrmGlobalVal.DRM_DECODING_FILEPATH) + StringUtil.checkNull(cmmMap.get("sessionUserId"))+"//");
			drmInfoMap.put("Filename", original);
			drmInfoMap.put("funcType", "download"); 
		}
		request.setAttribute("downFile", downFile);
		request.setAttribute("orginFile", original); // 20140627 request.setAttribute("orginFile", enOriginal); 수정

		FileUtil.downFile(request, response);

		return null;
	}	
	@RequestMapping(value = "/dsFileDown.do")
	public String handleRequest(HttpServletRequest request, Map cmmMap, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		String scrnType = request.getParameter("scrnType");
		Map setMap = new HashMap();
		setMap.put("seq", seq);
		String filename = "";
		String original = "";
		String filePath = "";
		String returnValue = "";
		
		if("BRD".equals(scrnType)) {			
			filename = commonService.selectString("boardFile_SQL.getFileSysName", setMap);	//new mode
			original =  commonService.selectString("boardFile_SQL.getFileName", setMap);	//new mode
			filePath = GlobalVal.FILE_UPLOAD_BOARD_DIR;
		}
		else {
			filename = commonService.selectString("fileMgt_SQL.getFileSysName", setMap);	//new mode
			original =  commonService.selectString("fileMgt_SQL.getFileName", setMap);	//new mode
			filePath = FileUtil.FILE_UPLOAD_DIR;
		}
		
		String downFile = FileUtil.FILE_UPLOAD_DIR + filename;
		
		String enOriginal = new String(original.getBytes("8859_1"), "UTF-8");

		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return "cmm/utl/EgovFileDown";
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
		request.setAttribute("orginFile", enOriginal);

		FileUtil.downFile(request, response);

		return null;
	}
	/**
	 * 엑셀 다운로드 기능을 제공한다.
	 * title : 제목
	 * headers : 컬럼명(컬럼1|컬럼2|컬럼3|...)
	 * cols : 컬럼명(컬럼1|컬럼2|컬럼3|...)
	 * key : sqlKey
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/excelDown.do")
	public String excelDown(Map cmmMap, ModelMap model) throws Exception {
		try {
			String title = getString(cmmMap.get("title"));
			String[]headers = StringUtil.toArray(cmmMap.get("headers"));
			String[]cols = StringUtil.toArray(cmmMap.get("cols"));

			String caltype = (String)cmmMap.get("coltype");
			HashMap hmColTypes = new HashMap();
			if(caltype.length() ==0 || caltype.equals("undefined"))
			{
				for(int i=0; i<cols.length; i++)
				{
					hmColTypes.put(cols[i], "T_C");
				}
			}
			else
			{
				String[]coltypes = StringUtil.toArray(cmmMap.get("coltype"));
				for(int i=0; i<cols.length; i++)
				{
					hmColTypes.put(cols[i], coltypes[i]);
				}

			}

			String sqlKey = getString(cmmMap.get("key")+SQL_GRID_LIST);
			if( title.lastIndexOf("> ") > 0)
			{
				title = title.substring(title.lastIndexOf("> ")+2);
			}
			model.addAttribute("fileName", URLEncoder.encode(title));
			model.addAttribute("title", title);
			model.addAttribute("headers", headers);
			model.addAttribute("cols", cols);
			model.addAttribute("coltypes", hmColTypes);
			model.addAttribute("bodyList", commonService.selectList(sqlKey, cmmMap));
		}catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		return FORWARD_EXCEL;
	}
	
	
	/**
	 * 엑셀 다운로드
	 * 서버에서 엑셀파일을 작성한다
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/excelFileDownload.do")
	public String excelFileDownload(Map cmmMap, ModelMap model,HttpServletResponse response) throws Exception {
		try {
			File file = makeExcelFile(cmmMap, response);
			pushFile(file, response);
		} catch (Exception e) {
			System.out.println(e.toString());
			throw e;
		}
		return null;
	}
	
	public static String getJSONFormatClean(String s) {
	    if (s == null) {
	        return null;
	    }
	    return StringEscapeUtils.unescapeHtml4(s).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	   
	}
	
	public File makeExcelFile(Map cmmMap, HttpServletResponse response) throws Exception {
        String[] headers = StringUtil.toArray(cmmMap.get("headers"));
        String[] ids = StringUtil.toArray(cmmMap.get("ids"));
        String[] aligns = StringUtil.toArray(cmmMap.get("aligns"));
        String[] widths = StringUtil.toArray(cmmMap.get("widths"));
        String[] hiddens = StringUtil.toArray(cmmMap.get("hiddens"));
	        
        String gridData = getJSONFormatClean(StringUtil.checkNull(cmmMap.get("gridExcelData")));
        String filepath = FileUtil.FILE_UPLOAD_DIR;        
        String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
        String filename = "data_list_"+ time +".xlsx";  
        String sessionDefFont = StringUtil.checkNull(cmmMap.get("sessionDefFont"));
        
        try {
			File file = new File( filepath+ "/" + filename );
			FileOutputStream fop = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
     		XSSFSheet sheet = workbook.createSheet("data_list_"+time);

     		XSSFCellStyle style = workbook.createCellStyle();
     		XSSFFont font = workbook.createFont();
 			font.setFontHeightInPoints((short)14);
 			font.setFontName(sessionDefFont);
 			font.setBold(true);
 			font.setItalic(false);
 			font.setColor(IndexedColors.GREY_80_PERCENT.index);
 			font.setUnderline(XSSFFont.U_SINGLE);
 			style.setFont(font);  
 			XSSFRow row = sheet.createRow(0);
 			XSSFCell cell = row.createCell(0);
 			cell.setCellStyle(style);
             
            style = workbook.createCellStyle();
 			font.setFontHeightInPoints((short)12);
 			font.setFontName(sessionDefFont);
 			font.setBold(true);
 			font.setItalic(false);
 			font.setColor(IndexedColors.GREY_80_PERCENT.index);
 			font.setUnderline(XSSFFont.U_NONE);
 			style.setFont(font);  
 			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            int rnum=0;

			row = sheet.createRow(rnum);
            //header
			for(int i=0;i<headers.length;i++){	        	
	        	cell = row.createCell(i);
            	cell.setCellStyle(style);
            	cell.setCellValue(getJSONFormatClean(headers[i]));
	        }
			// content
			rnum++;
			JSONArray jsonArray = new JSONArray(gridData);
			for (int idx = 0; idx < jsonArray.length(); idx++) {
				JSONObject jsonData = (JSONObject) jsonArray.get(idx);	
				row = sheet.createRow(rnum);
				for(int i=0; i<headers.length; i++){
					
					cell = row.createCell(i);
            		style = workbook.createCellStyle();
         			font.setFontHeightInPoints((short)11);
         			font.setFontName(sessionDefFont);
         			font.setBold(false);
         			font.setItalic(false);
         			font.setColor(IndexedColors.GREY_80_PERCENT.index);
         			font.setUnderline(XSSFFont.U_NONE);
         			style.setFont(font);  
         			style.setFillForegroundColor(IndexedColors.WHITE.index);
        			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        			style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
        			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        			
		            if("center".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_CENTER); 
            		else if("right".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_RIGHT); 
            		else if("left".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_LEFT); 
		            if(StringUtil.checkNull(widths[i]).equals("")) {
		            	sheet.setColumnWidth(i, 300*40);
		            }else {
		            	sheet.setColumnWidth(i, Integer.parseInt(widths[i])*40);
		            }
		            
		            if(StringUtil.checkNull(hiddens[i]).equals("true")) {
		            	sheet.setColumnHidden(i, true);
		            }else {
		            	sheet.setColumnWidth(i, Integer.parseInt(widths[i])*40);
		            }
		            
		            String data = "";
		            if(jsonData.has(ids[i])) {
		            	data = StringUtil.checkNull(jsonData.get(ids[i]));
		            }
					
            		cell.setCellStyle(style);
            		cell.setCellValue(data);
				}
				rnum++;
			}
			
		    workbook.write(fop); 
            fop.flush();
            fop.close();
            
	        return file;
        } catch ( Exception e ){
        	System.out.println(e.toString());
        	throw e;
        }
	}
	
	/**
	 * excel file을 조립한다
	 * @param cmmMap
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public File makeExcelFile_ORG(Map cmmMap, ModelMap model, HttpServletResponse response) throws Exception {
        String title = getString(cmmMap.get("title"));
        String[]headers = StringUtil.toArray(cmmMap.get("headers"));
        String[]cols = StringUtil.toArray(cmmMap.get("cols"));
        String[] widths= StringUtil.toArray(cmmMap.get("widths"));
        String[] aligns = StringUtil.toArray(cmmMap.get("aligns"));
        String sqlKey = getString(cmmMap.get("key"));
        
        String filepath = XboltController.getWebappRoot();
        String filename = title+".xlsx";
        String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
        filename = time+".xlsx";
        try {
			File file = new File( filepath+ "/" + filename );
			FileOutputStream fop = new FileOutputStream(file);
			
			List list = commonService.selectList(sqlKey,cmmMap); 
			//title
    		
    		XSSFWorkbook workbook = new XSSFWorkbook();
     		XSSFSheet sheet = workbook.createSheet(title);

     		XSSFCellStyle style = workbook.createCellStyle();
     		XSSFFont font = workbook.createFont();
 			font.setFontHeightInPoints((short)14);
 			font.setFontName("ARIAL");
 			font.setBold(true);
 			font.setItalic(false);
 			font.setColor(IndexedColors.GREY_80_PERCENT.index);
 			font.setUnderline(XSSFFont.U_SINGLE);
 			style.setFont(font);  
 			XSSFRow row = sheet.createRow(0);
 			XSSFCell cell = row.createCell(0);
 			cell.setCellStyle(style);
             
            style = workbook.createCellStyle();
 			font.setFontHeightInPoints((short)12);
 			font.setFontName("COURIER");
 			font.setBold(true);
 			font.setItalic(false);
 			font.setColor(IndexedColors.GREY_80_PERCENT.index);
 			font.setUnderline(XSSFFont.U_NONE);
 			style.setFont(font);  
 			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            int rnum=3;

			row = sheet.createRow(rnum);
            //header

            for(int i=0;i<headers.length;i++){
            	cell = row.createCell(i);
            	cell.setCellStyle(style);
            	cell.setCellValue(headers[i]);
        	}
            
            //content
            rnum++;
            for(Object o:list){
            	HashMap rowMap = (HashMap)o;
    			row = sheet.createRow(rnum);
            	for(int i=0;i<cols.length;i++){

                	cell = row.createCell(i);
            		style = workbook.createCellStyle();
         			font.setFontHeightInPoints((short)11);
         			font.setFontName("TAHOMA");
         			font.setBold(false);
         			font.setItalic(false);
         			font.setColor(IndexedColors.GREY_80_PERCENT.index);
         			font.setUnderline(XSSFFont.U_NONE);
         			style.setFont(font);  
         			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        			
		            if("center".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_CENTER); 
            		else if("right".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_RIGHT); 
            		else if("left".equals(aligns[i])) style.setAlignment(XSSFCellStyle.ALIGN_LEFT); 
            		sheet.setColumnWidth(i, Integer.parseInt(widths[i])*30);

					String data = (rowMap.get(cols[i])!=null)?rowMap.get(cols[i]).toString():null;
            		cell.setCellStyle(style);
            		cell.setCellValue(data);
            	}
            	rnum++;
            }

            workbook.write(fop); 
            fop.flush();
            fop.close();
            //workbook.close();
	        return file;
        } catch ( Exception e ){
        	System.out.println(e.toString());
        	throw e;
        }
	}
	
	/**
	 * excel file을 내보낸다
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	public void pushFile(File file, HttpServletResponse response) throws Exception {
		FileInputStream fin = null;
		try {
            fin = new FileInputStream(file);
            int ifilesize = (int)file.length();
            String filename = file.getName();
            byte b[] = new byte[ifilesize];

            response.setContentLength(ifilesize);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader( "Content-Disposition", "attachment; filename=" + filename+";" );
            ServletOutputStream oout = response.getOutputStream();
           
            while (fin.read(b) > 0) {
	            oout.write(b,0,ifilesize);
            }
	        oout.close();
            fin.close();
            file.delete();
        } catch ( Exception e ) {
        	System.out.println(e.toString());
        	throw e;
        } finally {
        	if(fin != null) {
        		try {
        			fin.close();
        		} catch ( Exception e ) {
                	System.out.println(e.toString());
                	throw e;
                }
        	}
        }
	}	
	
	@RequestMapping(value="/uploadImgFileScrn.do")
	public String uploadImgFileScrn(HashMap cmmMap,ModelMap model) throws Exception {
		return nextUrl("/cmm/imgFileUpload");	
	}
	
	@RequestMapping(value="/uploadImgFile.do")
	public String uploadImgFile(MultipartHttpServletRequest request, HashMap cmmMap,ModelMap model) throws Exception {
		Map target = new HashMap();
		XSSRequestWrapper xss = new XSSRequestWrapper(request);
		
		//Map map = new HashMap();
		try {
			/*Map<String,String[]> params = request.getParameterMap();

			for(String[] values : params.values()){
			  
			}*/
			
			Iterator fileNameIter = request.getFileNames();
			String savePath = GlobalVal.FILE_UPLOAD_TINY_DIR;
			String fileName = "";
		    while (fileNameIter.hasNext()) {
			   MultipartFile mFile = request.getFile((String)fileNameIter.next());
			   fileName = mFile.getOriginalFilename();
			   if (mFile.getSize() > 0) {	
				   String ext = FileUtil.getExt(fileName);
				   
				   if(!FileUtil.isPicture(ext)) {
					   target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00074")); // 성공
					   target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
					   model.addAttribute(AJAX_RESULTMAP, target);
						
					   return nextUrl(AJAXPAGE);	
				   }
				   HashMap resultMap = FileUtil.uploadFile(mFile, savePath, true);
				   fileName = resultMap.get(FileUtil.UPLOAD_FILE_NM)+"^";
			   }
			}				
			//target.put(AJAX_ALERT, "파일 업로드에 성공하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00071")); // 성공
			target.put(AJAX_SCRIPT, "parent.doReturn('"+fileName+"','');parent.$('#isSubmit').remove();");
		}
		catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_SCRIPT, "parent.$('#isSubmit').remove();");
			//target.put(AJAX_ALERT, "파일 업로드 중 오류가 발생하였습니다.");
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00074")); // 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);
		
		return nextUrl(AJAXPAGE);	
	}
	
	/**
	 * 업로드된 파일을 삭제한다.
	 * @param cmmMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/removeFile.do")
	public String removeFile(Map cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();

		try {
			String uploadDir = GlobalVal.FILE_UPLOAD_BASE_DIR;
			String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
			String fileName = StringUtil.checkNull(cmmMap.get("fileName")); 
			String removeAll = StringUtil.checkNull(cmmMap.get("removeAll")); 
		   
			String realFile = uploadDir+ userID +"//" + fileName.trim();
			if(removeAll.equals("Y")) {
				realFile =  uploadDir+ userID;
			}
			
			File existFile = new File(realFile);			 
			if(removeAll.equals("Y")) {
				 File[] fileList = existFile.listFiles();				 
				 for(int i=0; i<fileList .length; i++){
			            fileList[i].delete() ;
			      }
			}else {
					existFile.delete();
			}
		}
		catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00076")); // 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}
	
	@RequestMapping(value="/removeFileList.do")
	public String removeFileList(Map cmmMap, ModelMap model) throws Exception {
		Map target = new HashMap();

		try {
			String uploadDir = GlobalVal.FILE_UPLOAD_BASE_DIR;
			String userID = StringUtil.checkNull(cmmMap.get("sessionUserId"));
			String fileNameList[] = StringUtil.checkNull(cmmMap.get("fileNameList")).split(","); 
		   
			String fileName = "";
			String realFile ="";
			for(int idx=0; idx<fileNameList.length; idx++) {
				fileName = StringUtil.checkNull(fileNameList[idx]);
				
				realFile = uploadDir+ userID +"//" + fileName;
				File existFile = new File(realFile);		 
				existFile.delete() ;
			}
			target.put(AJAX_SCRIPT, "fnClose();");
		}
		catch (Exception e) {
			System.out.println(e);
			target.put(AJAX_ALERT, MessageHandler.getMessage(cmmMap.get("sessionCurrLangCode") + ".WM00076")); // 오류
		}
		model.addAttribute(AJAX_RESULTMAP, target);

		return nextUrl(AJAXPAGE);
	}

}
