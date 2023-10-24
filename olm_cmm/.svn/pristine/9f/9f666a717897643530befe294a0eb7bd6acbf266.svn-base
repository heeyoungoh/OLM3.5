package xbolt.cmm.framework.dhtmlx.vault;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
/**
 * DhtmlxVault file upload handler
 * @Class Name : FileUploadHandler.java
 * @Description : DhtmlxValut Controller 제공한다.
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 */

@SuppressWarnings("serial")
public class FileUploadHandler extends HttpServlet {
	/** 파일ROOT경로 */		public static final String FILE_UPLOAD_DIR = GlobalVal.FILE_UPLOAD_TMP_DIR;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		//String uploadDir = FILE_UPLOAD_DIR;//"D:/";

		String mode = req.getParameter("mode");
		String action = "";

		ServletFileUpload uploader = null;
		List<FileItem> items = null;

		try{
			String scrnType=StringUtil.checkNull(req.getParameter("scrnType"));
			String usrId =StringUtil.checkNull(req.getParameter("usrId"));
			String mgtId=StringUtil.checkNull(req.getParameter("mgtId"));
			String id =StringUtil.checkNull(req.getParameter("id"));
			//System.out.println(StringUtil.checkNull(req.getParameter("scrnType"))+","+StringUtil.checkNull(req.getParameter("mgtId"))+","+StringUtil.checkNull(req.getParameter("id")));
			String uploadDir=GlobalVal.FILE_UPLOAD_BASE_DIR;
			/*if(scrnType.equals("BRD")){uploadDir=GlobalVal.FILE_UPLOAD_BOARD_DIR;}
			else{uploadDir=GlobalVal.FILE_UPLOAD_ITEM_DIR;}*/

			if (mode == null || (mode != null && !mode.equals("conf") && !mode.equals("sl"))) {
				uploader = new ServletFileUpload(new DiskFileItemFactory());
				items = uploader.parseRequest(req);
			}

			if (mode == null) {
				mode = "";
				for (FileItem item : items) {
					if (item != null) {
						if (item.getFieldName().equals("mode")){mode = getStringFromStream(item.getInputStream());}
						if (item.getFieldName().equals("action")){action = getStringFromStream(item.getInputStream());}
					}
				}
			}
			if (mode.equals("conf")) {
				int maxPostSize = 2000000;				
				resp.setHeader("Content-type", "text/json");
				resp.getWriter().write("{ \"maxFileSize\":" + maxPostSize + " }");
			}
			if (mode.equals("html4") || mode.equals("flash") || mode.equals("html5")) {
				if (action.equals("cancel")) {
					resp.setHeader("Content-type", "text/json");
					resp.getWriter().write("{\"state\":\"cancelled\"}");
				} else {
					//make root dir
					File cFile = new File(uploadDir);
					if (!cFile.isDirectory()){cFile.mkdir();}
					//make second dir
					if(!usrId.equals("")){uploadDir=uploadDir+ usrId +"//";
						cFile = new File(uploadDir);
						if (!cFile.isDirectory()){cFile.mkdir();}
					}
					String filename = "";
					Integer filesize = 0;		
					String serverFileName = "";
					for (FileItem item : items) {
						//1.file Upload
						if (!item.isFormField()) {
							// Process form file field (input type="file").
							String fieldname = item.getFieldName();
							filename = FilenameUtils.getName(item.getName());
							InputStream filecontent = item.getInputStream();
							
						    if(!FileUtil.isAllFile(FileUtil.getExt(filename))) {
						    	resp.setCharacterEncoding("UTF-8");
								resp.setHeader("Content-type", "text/html");
								resp.getWriter().write("{\"state\":false,\"name\":\"" + filename.replace("\"","\\\"") + "\",\"serverName\":\"" + serverFileName + "\",\"size\":" + filesize + ",\"extra\":{\"info\":\"just a way to send some extra data\",\"param\":\""+serverFileName+"\"}}");
								
							   return;
						    }

							// Write to file
							//String fileExt = StringUtil.checkNull(FilenameUtils.getExtension(filename));
							serverFileName = filename;//fileExt.length()>0?(DateUtil.getCurrentTime() + "." + fileExt):DateUtil.getCurrentTime();
							File f=new File(uploadDir + serverFileName);
							FileOutputStream fout=new FileOutputStream(f);
							byte buf[]=new byte[1024];
							int len;
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
							fout.close();
						}
					}					
					// Manual filesize value only for demo!  // filesize = 28428;			
					resp.setCharacterEncoding("UTF-8");
					
					System.out.println("{\"state\":true,\"name\":\"" + filename.replace("\"","\\\"") + "\",\"serverName\":\"" + serverFileName + "\",\"size\":" + filesize + ",\"extra\":{\"info\":\"just a way to send some extra data\",\"param\":\""+serverFileName+"\"}}");
					resp.setHeader("Content-type", "text/html");
					resp.getWriter().write("{\"state\":true,\"name\":\"" + filename.replace("\"","\\\"") + "\",\"serverName\":\"" + serverFileName + "\",\"size\":" + filesize + ",\"extra\":{\"info\":\"just a way to send some extra data\",\"param\":\""+serverFileName+"\"}}");
					
				}
			}

			HashMap p = new HashMap();
			Enumeration params = req.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String)params.nextElement();
				p.put(name, req.getParameter(name));
			}

			/*
			String fileName = req.getParameter("fileName");
			String fileSize = req.getParameter("fileSize");
			String fileKey = req.getParameter("fileKey");
			if (mode != null && mode.equals("sl") && fileName != null && fileSize != null && fileKey != null) {
				action = req.getParameter("action");
				if (action != null && action.equals("getUploadStatus")) {
					resp.setContentType("text/json");
					System.out.print("{state: true, name:'" + fileName + "'}");
				} else {
					FileOutputStream file = new FileOutputStream(uploadDir+fileName);
					file.write(IOUtils.readFully(req.getInputStream(), -1, false));
					file.close();
				}
			}	
			*/
		}catch(Exception ex){
			System.out.println(ex);		
		}
		
	}
	private String getStringFromStream(InputStream is) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}