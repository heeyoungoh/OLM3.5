package xbolt.cmm.framework.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 파일 업로드 및 다운로드를 관리하는 클래스이다.
 *
 * @author SeongJong Jeon
 * @since 1.0
 */
public class FileManagement {

    private static List<String> imageExtensions = new ArrayList<String>();
    static {
        imageExtensions.addAll(Arrays.asList(new String[]{"gif", "jpg", "jpeg", "png","bmp"}));
    }
    
    /**
     * 파일을 업로드 한다.
     * <xmp>
     * 예)
     *  MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest)req;
	 *	Map<String, MultipartFile> files = multipartReq.getFileMap();
     *
     *  List<MultipartFile> multiFiles = new ArrayList<MultipartFile>();
     *  List<String> saveFileNames = new ArrayList<String>();
     *
     *  String fileName = null;
     *  for (MultipartFile file : files.values()) {
     *       multiFiles.add(file);
     *       fileName = file.getOriginalFilename();
     *       saveFileNames.add(FileManagement.changeSaveFileName(fileName));
     *   }
     *   FileManagement.uploadFile(req,"/imageFile",multiFiles,saveFileNames);
     * </xmp>
     * @param req
     * @param saveFileLocation  저장될 위치 (image인경우는 cntext_root/saveFileLocation 에 저장된다.)
     * @param multiFiles MultipartFile 리스트
     * @param saveFileNames file이름들의 리스트
     * @throws Exception
     */
    public static void uploadFile(HttpServletRequest req, String saveFileLocation, List<MultipartFile> multiFiles, List<String> saveFileNames) throws Exception {

        String applicationRealPath = applicationRealPath = req.getSession().getServletContext().getRealPath("");
        //String webInfoPath = webInfoPath = applicationRealPath + "/WEB-INF";
        File dir = new File(saveFileLocation);
        if (!dir.exists()) {
            dir.mkdir();
        }
        for (int i = 0; i < multiFiles.size(); i++) {
            if(checkImageFile(multiFiles.get(i).getOriginalFilename())){   //image인경우
                dir = new File(applicationRealPath+saveFileLocation);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                FileCopyUtils.copy(multiFiles.get(i).getInputStream(), new FileOutputStream(applicationRealPath+saveFileLocation + "/" + saveFileNames.get(i)));
            }else{
                FileCopyUtils.copy(multiFiles.get(i).getInputStream(), new FileOutputStream(saveFileLocation + "/" + saveFileNames.get(i)));
            }
        }
    }

    /**
     * 파일을 다운로드 한다.
     * <xmp>
     * 예)
	 * FileManagement.downloadFile(request,response,saveFileLocation,request.getParameter("originalFileName"),request.getParameter("saveFileName"));
     * client
     * <img src="http://localhost:8080/path/saveFileName/> 또는
     * <a href="/download.do?originalFileName=originalFileName&saveFileName=saveFileName/>"
     * </xmp>
     * @param req
     * @param res
     * @param path 다운로드할 위치 (image인경우는 cntext_root/saveFileLocation 에 다운로드 된다.)
     * @param originalFileName 다운로드할 파일명
     * @param saveFileName 실제 서버에 저장된 파일명
     * @throws Exception
     */
	public static void downloadFile(HttpServletRequest req, HttpServletResponse res, String path, String originalFileName, String saveFileName)throws Exception{
		/** Build File Full Path */
		StringBuffer fileFullPath = new StringBuffer();
        if(checkImageFile(saveFileName)){   //image인경우
            String imageDownloadPath = req.getSession().getServletContext().getRealPath("")+path;
            fileFullPath.append(imageDownloadPath).append(File.separatorChar).append(saveFileName);
        }else{
            fileFullPath.append(path).append(File.separatorChar).append(saveFileName);
        }

		File dwldFile = new File(fileFullPath.toString());
		/** Set Response Header */
		res.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		res.setContentLength((int)dwldFile.length());
		res.setHeader("Content-Disposition","attachment; fileName="+"\""+new String(originalFileName.getBytes("MS949"), "8859_1")+"\""+";");
		res.setHeader("Content-Transfer-Encoding","binary");
		OutputStream os = res.getOutputStream();
		InputStream is = null;
		is = new FileInputStream(dwldFile);
		FileCopyUtils.copy(is,os);
		os.flush();
	}

    /**
     * 저장될 파일명의 이름을 변경해준다.
     * <xmp>
     * 예)dd.jpg  -> dd_1121245206883.jpg  (nano seconds)
     * </xmp>
     * @param fileName
     * @return
     */
    public static String changeSaveFileName(String fileName){
        String[] fileNameSplit = fileName.split("\\.");
        fileNameSplit[fileNameSplit.length-2] = fileNameSplit[fileNameSplit.length-2]+"_"+System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for(String split : fileNameSplit){
            sb.append(split+".");
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
    
    /**
     * 이미지 파일인지를 체크한다.
     *
     * @param file
     * @return
     */
    public static boolean checkImageFile(String file) {
        String[] fileSplit = file.split("\\.");
        if (imageExtensions.contains(fileSplit[fileSplit.length - 1])) {
            return true;
        }
        return false;
    }
}