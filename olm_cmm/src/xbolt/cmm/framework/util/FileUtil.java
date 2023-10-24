package xbolt.cmm.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import xbolt.cmm.framework.val.GetProperty;
import xbolt.cmm.framework.val.GlobalVal;

/**
 * 공통 서블릿 처리
 * @Class Name : FileUtil.java
 * @Description : 파일 관련 처리를 위해 제공한다.
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
public class FileUtil {
	/** 이미지파일 목록 */		public static final String IMG_FILE_LIST[] = {"JPEG", "GIF", "BMP", "JPG", "TIF", "TIFF", "PNG", "JPE"};
	/** 엑셀파일 목록 */		public static final String EXCEL_FILE_LIST[] = {"XLS", "XLSX","CSV"};
	/** 업로드파일 목록 */		public static final String ALL_FILE_LIST[] = StringUtil.checkNull(GlobalVal.FILE_FORMAT_WHITE_LIST,"").split(",");/*{"XLS", "XLSX","HWP", "DOC", "DOCX", "PPT", "PPTX", "TXT"
		,"JPEG", "GIF", "BMP", "JPG", "TIF", "TIFF", "PNG", "JPE", "CSV", "WAV", "MP3", "ZIP", "XLSM", "PDF","PPSX","AVI","MP4","ASF","FLV","WMV","GUL","ENC","PPTM","XPS","DWG","HDC","MSG","XDW"};
		*/
	/**	저장된파일들 */		public static final String STORED_FILES = "STORED_FILES";

	/** 파일 업로드  파일ID */	public static final String FILE_ID = "AttFileID"; //"ATTFILE_NO";
	/** 파일 객체명 */			public static final String FILE_NAME = "FileNm";
	/** 파일 업로드 원 파일명 */	public static final String ORIGIN_FILE_NM = "FileNm";
	/** 업로드된 파일명 */		public static final String UPLOAD_FILE_NM = "SysFileNm";
	/** 파일 확장자 */			public static final String FILE_EXT = "FILE_EXT";
	/** 파일크기 */			public static final String FILE_SIZE = "FileSize";
	/** 파일경로 */			public static final String FILE_PATH = "FilePath";
	/** 파일설명 */			public static final String FILE_REMARK = "REMARK_";

	/** 파일ROOT경로 */		//public static final String FILE_UPLOAD_DIR = "/uniwas_nas/dscc/attach_file/";
	/** 파일ROOT경로 */		public static final String POST_UPLOAD_DIR = "/uniwas_nas/dscc/post/";
	/** 파일ROOT경로 */		public static final String FILE_UPLOAD_DIR = GlobalVal.FILE_UPLOAD_BASE_DIR;
	
	/** 파일ROOT경로 */		public static final String FILE_UPLOAD_ITEM_DIR = GlobalVal.FILE_UPLOAD_ITEM_DIR;
	/** 파일ROOT경로 */		public static final String FILE_EXPORT_DIR = GlobalVal.FILE_EXPORT_DIR;

	/** 버퍼크기 */			public static final int BUFF_SIZE = 2048;

	
	public static boolean isPicture(String ext) {
		boolean result = false;
		if(ext != null) {
			ext = ext.toUpperCase();
			for (String pic: IMG_FILE_LIST) {
				if(pic.equals(ext)) {
					result = true;
				}
			}
		}
		return result;
	}

	public static boolean isExcel(String ext) {
		boolean result = false;
		if(ext != null) {
			ext = ext.toUpperCase();
			for (String pic: EXCEL_FILE_LIST) {
				if(pic.equals(ext)) {
					result = true;
				}
			}
		}
		return result;
	}

	public static boolean isAllFile(String ext) {
		boolean result = false;
		if(ext != null && ALL_FILE_LIST.length > 0 && !"".equals(ALL_FILE_LIST[0])) {
			ext = ext.toUpperCase();
			for (String pic: ALL_FILE_LIST) {
				if(!"".equals(pic) && pic.equals(ext)) {
					result = true;
				}
			}
		}
		else if(ext != null && (ALL_FILE_LIST.length < 1 || "".equals(ALL_FILE_LIST[0]))) {
			result = true;
		}
		return result;
	}
	
	public static void deleteFile(String fileName) throws Exception{
		if(fileName != null && !fileName.equals("")) {
			File file = new File(fileName);
			if (!file.exists()) {throw new FileNotFoundException(fileName);}
			if (!file.isFile()) {throw new FileNotFoundException(fileName);}	
			file.delete();
		}
	}

	public static void deleteDirectory(String path) throws Exception{
		File file = new File(path);
	    File[] listOfFile = file.listFiles();
	    //file delete
	    if(listOfFile!=null){
		    for(File tempFile : listOfFile) {
		    	if(!tempFile.isDirectory()){tempFile.delete();}
		    }}
	    //delete empty directory
	    File fileDir = new File(path);
	    if(fileDir.isDirectory()){
		    Path dirPath = Paths.get(path);
		    Files.delete(dirPath);
		}
	}

	
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	public static String getExt(MultipartFile file) {
		return getExt(file.getOriginalFilename());
	}

	/**
	 * 첨부로 등록된 파일을 서버에 업로드한다.
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> uploadFile(MultipartFile file, String fileUploadDir, boolean isWrite) throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		//Write File 이후 Move File????
		String newName = null;
		String storedPath = "";//DateUtil.getCurrentTime().substring(0, 8);;
		String stordFilePath ="";
		if( fileUploadDir.equals("")) stordFilePath = FILE_UPLOAD_DIR+storedPath;
		else stordFilePath =  fileUploadDir + storedPath;
		
		//	String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
		String orginFileName = file.getOriginalFilename();

		String fileExt = getExt(orginFileName);
		long size = file.getSize();

		//너무 빠른 처리 시 파일명이 중복되어 1개 파일만 저장되는 경우가 있어서 잠시 멈춰주는 부분 추가
		Thread.sleep(1000);
		//newName 은 Naming Convention에 의해서 생성
		newName = DateUtil.getCurrentTime() + "." + fileExt;
		if(isWrite) writeFile(file, newName, stordFilePath);
		//storedFilePath는 지정
		map.put(FILE_NAME, file.getName());
		map.put(ORIGIN_FILE_NM, orginFileName);
		map.put(UPLOAD_FILE_NM, newName);
		map.put(FILE_EXT, fileExt);
		map.put(FILE_PATH, stordFilePath);
		map.put(FILE_SIZE, String.valueOf(size));

		return map;
	}

	/**
	 * 파일을 실제 물리적인 경로에 생성한다.
	 *
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(stordFilePath);

			if (!cFile.isDirectory()) {
				cFile.mkdir();
			}

			bos = new FileOutputStream(stordFilePath + File.separator + newName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
	
	public static List moveFiles(String orginPath, String targetPath) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		DirectoryStream<Path> dir = null;
		List fileList = new ArrayList();
		String newFileName="";
		File fTargetFolder = new File (targetPath);
		if ( !fTargetFolder.isDirectory()){fTargetFolder.mkdir();}
		File fOrginFolder = new File (orginPath);
	    String[] FileList = fOrginFolder.list();
	    File fOri = null;
	    File fTarget = null;
	    for( int i = 0; i < FileList.length; i++)
	    {
			fOri = new File ( orginPath + FileList[i]);
			String orginFileName = fOri.getName();
			String fileExt = getExt(orginFileName);
			//newName 은 Naming Convention에 의해서 생성
			newFileName = DateUtil.getCurrentTime() + "." + fileExt;
			
			fTarget = new File ( targetPath + newFileName);
			long size = fOri.length();				
			//너무 빠른 처리 시 파일명이 중복되어 1개 파일만 저장되는 경우가 있어서 잠시 멈춰주는 부분 추가
			Thread.sleep(1000);
			//file move
			fOri.renameTo(fTarget);
			
			map.put(FILE_NAME, fOri.getName());
			map.put(ORIGIN_FILE_NM, fOri.getName());
			map.put(UPLOAD_FILE_NM, newFileName);
			map.put(FILE_EXT, fileExt);
			map.put(FILE_PATH, targetPath);
			map.put(FILE_SIZE, String.valueOf(size));
			
			fileList.add(map);
	    
	    }
		return fileList;
	}
	
	public static List copyFiles(String orginPath, String targetPath) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		List fileList = new ArrayList();
		String newFileName="";
		File fTargetFolder = new File (targetPath);
		if ( !fTargetFolder.isDirectory()){fTargetFolder.mkdir();}
		File fOrginFolder = new File (orginPath);
	    String[] FileList = fOrginFolder.list();
	    //File fOri = null;
	    //File fTarget = null;
	    if(FileList!=null){
	    for( int i = 0; i < FileList.length; i++)
	    {
	    	map = new HashMap<String, String>();
	    	File fOri = new File ( orginPath + FileList[i]);
			String orginFileName = fOri.getName();
			String fileExt = getExt(orginFileName);
			//newName 은 Naming Convention에 의해서 생성
			newFileName = DateUtil.getCurrentTime() + "." + fileExt;
			
			File fTarget = new File ( targetPath + newFileName);
			long size = fOri.length();				
			//너무 빠른 처리 시 파일명이 중복되어 1개 파일만 저장되는 경우가 있어서 잠시 멈춰주는 부분 추가
			Thread.sleep(1000);
			//file copy
			 Files.copy(fOri.toPath(), fTarget.toPath()); 
			
			map.put(FILE_NAME, fOri.getName());
			map.put(ORIGIN_FILE_NM, fOri.getName());
			map.put(UPLOAD_FILE_NM, newFileName);
			map.put(FILE_EXT, fileExt);
			map.put(FILE_PATH, targetPath);
			map.put(FILE_SIZE, String.valueOf(size));
			
			fileList.add(map);
	    
	    }}
		return fileList;
	}
	
	public static void copyFile(String orginPath, String targetPath) throws Exception {
        byte[] buf = new byte[1024];
        try {
        	FileInputStream fin = new FileInputStream(orginPath);
        	FileOutputStream fout = new FileOutputStream(targetPath);
            int read = 0;
            while((read=fin.read(buf,0,buf.length))!=-1){
                fout.write(buf, 0, read);
            }
             
            fin.close();
            fout.close();
            
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}

	/**
	 * 서버의 파일을 다운로드한다.
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String downFileName = "";
		String orgFileName = (String)request.getAttribute("orginFile");//new String(((String)request.getAttribute("orginFile")).getBytes("8859_1"), "UTF-8");

		if ((String)request.getAttribute("downFile") == null) {
			downFileName = "";
		} else {
			//downFileName = (String)request.getAttribute("downFile");
			downFileName = new String(((String)request.getAttribute("downFile")).getBytes("8859_1"), "UTF-8");
		}
		String userAgent=request.getHeader("user-agent");
		boolean MSIE = userAgent.indexOf("MSIE") != -1;
		if(userAgent.indexOf("Trident")!=-1){MSIE=true;}

		if (orgFileName == null) {
			orgFileName = "";
		} else {
			//IE,FF 각각 다르게 파일이름을 적용해서 구분해주어야 한다.
			if(MSIE){
				//브라우저가 IE일 경우 저장될 파일 이름
				//공백이 '+'로 인코딩된것을 다시 공백으로 바꿔준다.
				//orgFileName = (URLEncoder.encode(orgFileName, "EUC-KR")+"_"+URLEncoder.encode(orgFileName, "8859_1")+"_"+URLEncoder.encode(orgFileName, "UTF-8")).replaceAll("\\+", " ");
				orgFileName = URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", " ");
			}else{
				//브라우저가 IE가 아닐 경우 저장될 파일 이름
				orgFileName = new String(orgFileName.getBytes("UTF-8"), "8859_1");
			}
			//		orgFileName = "테스트";//(String)request.getAttribute("orginFile");
		}

		File file = new File(downFileName);

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + orgFileName +"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
		int read = 0;

		while ((read = fin.read(b)) != -1) {
			outs.write(b, 0, read);
		}
		
		outs.close();
		fin.close();
	}
	
	/**
	 * 서버의 첨부파일관리 파일을 다운로드한다.
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void flMgtdownFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String downFileName = "";
		String orgFileName = (String)request.getAttribute("orginFile");//new String(((String)request.getAttribute("orginFile")).getBytes("8859_1"), "UTF-8");
		
		if ((String)request.getAttribute("downFile") == null) {
			downFileName = "";
		} else {
			downFileName = (String)request.getAttribute("downFile");
			//downFileName = new String(((String)request.getAttribute("downFile")).getBytes("8859_1"), "UTF-8");
		}
		
		boolean MSIE = request.getHeader("user-agent").indexOf("MSIE") != -1;

		if (orgFileName == null) {
			orgFileName = "";
		} else {
			if(MSIE){
				//브라우저가 IE일 경우 저장될 파일 이름
				//공백이 '+'로 인코딩된것을 다시 공백으로 바꿔준다.
				//orgFileName = (URLEncoder.encode(orgFileName, "EUC-KR")+"_"+URLEncoder.encode(orgFileName, "8859_1")+"_"+URLEncoder.encode(orgFileName, "UTF-8")).replaceAll("\\+", " ");
				orgFileName = URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", " ");
			}else{
				//orgFileName = new String(orgFileName.getBytes("UTF-8"), "8859_1");
				orgFileName  = URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", " ");
			}
			//		orgFileName = "�׽�Ʈ";//(String)request.getAttribute("orginFile");
		}
		
		File file = new File(downFileName);

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
		
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + orgFileName +"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
		
		int read = 0;

		while ((read = fin.read(b)) != -1) {
			outs.write(b, 0, read);
		}

		outs.close();
		fin.close();
	}
}
