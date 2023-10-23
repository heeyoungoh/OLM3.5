package xbolt.cmm.framework.util.drm;

import SCSL.SLDsFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;

public class DRM_SOFTCAMP
{
	public static String upload_SAMPLE(HashMap drmInfoMap) throws Exception 
	{
		String srcFile,dstFile;

		srcFile="c:\\softcamp\\03_Sample\\test_Enc.xls";
		dstFile="c:\\softcamp\\03_Sample\\test_Dec.xls";
	
		SLDsFile sFile = new SLDsFile();
		

		sFile.SettingPathForProperty("c:\\softcamp\\02_Module\\02_ServiceLinker\\softcamp.properties"); 
		
		int retVal = sFile.CreateDecryptFileDAC (
		"c:\\softcamp\\04_KeyFile\\keyDAC_SVR0.sc",
		"SECURITYDOMAIN",
		srcFile,
		dstFile);
		System.out.println( " CreateDecryptFileDAC [" + retVal + "]");
		
		return "";
	}
  public static String upload(HashMap drmInfoMap) throws Exception
  {
    String returnValue = "";
    String ORGFileDir = StringUtil.checkNull(drmInfoMap.get("ORGFileDir"), "");
    String DRMFileDir = StringUtil.checkNull(drmInfoMap.get("DRMFileDir"), "");
    String Filename = StringUtil.checkNull(drmInfoMap.get("Filename"), "");
    String FileRealName = StringUtil.checkNull(drmInfoMap.get("FileRealName"), "");

    SLDsFile sFile = new SLDsFile();

    sFile.SettingPathForProperty("c:\\softcamp\\02_Module\\02_ServiceLinker\\softcamp.properties");
    
    System.out.println("=======UPload DRM 복호화 ========");
    System.out.println("orgFileDir + FileRealName  :"+ORGFileDir.replace("//", "\\").replace("/", "\\") + FileRealName);
    System.out.println("drmFileDir + DRMFileDir  :"+DRMFileDir.replace("//", "\\").replace("/", "\\") + FileRealName);
   
    int retVal = sFile.CreateDecryptFileDAC(
      "c:\\softcamp\\04_KeyFile\\keyDAC_SVR0.sc", 
      "SECURITYDOMAIN",
      ORGFileDir.replace("//", "\\").replace("/", "\\") + FileRealName, 
      DRMFileDir.replace("//", "\\").replace("/", "\\") + Filename);
    
    System.out.println(" CreateDecryptFileDAC [" + retVal + "]");
    
    String uploadAip = uploadAip(drmInfoMap);
    
    return returnValue;
  }

  public static String download(HashMap drmInfoMap) throws Exception
  {
    String returnValue = "";
    String ORGFileDir = StringUtil.checkNull(drmInfoMap.get("downFile"), "").replace("//", "\\").replace("/", "\\");
    String DRMFileDir = StringUtil.checkNull(drmInfoMap.get("DRMFileDir"), "").replace("//", "\\").replace("/", "\\");
    String Filename = StringUtil.checkNull(drmInfoMap.get("Filename"), "");
    String FileRealName = StringUtil.checkNull(drmInfoMap.get("orgFileName"));
    String userID = StringUtil.checkNull(drmInfoMap.get("userLoginID"), "");
    String userName = StringUtil.checkNull(drmInfoMap.get("userName"), "");
    String teamID = StringUtil.checkNull(drmInfoMap.get("teamID"), "");
    String teamName = StringUtil.checkNull(drmInfoMap.get("teamName"), "");
    
    SLDsFile sFile = new SLDsFile();
    sFile.SettingPathForProperty("c:\\softcamp\\02_Module\\02_ServiceLinker\\softcamp.properties");

    //int ret = sFile.SLDsEncFileDACV2("c:\\softcamp\\04_KeyFile\\keyDAC_SVR0.sc", "Hway", ORGFileDir, StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH).replace("//", "\\").replace("/", "\\") + "\\" + Filename, 1);
    //System.out.println("SLDsEncFileDAC :" + ret);
    String drmOutputDir = StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH).replace("//", "\\").replace("/", "\\");
    
    System.out.println("=======Download DRM 복호화 ========");
    System.out.println("orgFileDir + FileRealName  :"+ORGFileDir);
    System.out.println("DRMFileDir + Filename  :" + drmOutputDir + "\\" + Filename);
    int retVal = sFile.CreateDecryptFileDAC(
    	      "c:\\softcamp\\04_KeyFile\\keyDAC_SVR0.sc", 
    	      "SECURITYDOMAIN",
    	      ORGFileDir.replace("//", "\\").replace("/", "\\"), 
    	      drmOutputDir.replace("//", "\\").replace("/", "\\") + "\\" + Filename);
    System.out.println("download 복호화 결과값  retVal :"+retVal);
    
    // Aip 암호화 
    drmInfoMap.put("drmOutputDir", drmOutputDir);
    String downloadAip = downloadAip(drmInfoMap);
    
    //System.out.println("downloadAip :" + downloadAip);
    return downloadAip;
    // return StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH) + "//" + Filename;
    
  }

  public static String reportDRM(HashMap drmInfoMap) throws ExceptionUtil {
    String returnValue = "";

    boolean bret = false;
    boolean nret = false;
    boolean iret = false;
    int fileType = 0;
    try {
      String filePath = StringUtil.checkNull(drmInfoMap.get("filePath"), "");
      if (filePath.equals("")) {
        filePath = FileUtil.FILE_EXPORT_DIR;
      }

      String filename_org = StringUtil.checkNull(drmInfoMap.get("orgFileName"));
      String userID = StringUtil.checkNull(drmInfoMap.get("userLoginID"), "");

      SLDsFile sFile = new SLDsFile();
      sFile.SettingPathForProperty("c:\\softcamp\\02_Module\\02_ServiceLinker\\softcamp.properties");

      sFile.SLDsInitDAC();
      sFile.SLDsAddUserDAC("SECURITYDOMAIN", "111001101", 0, 0, 0);

      int ret = sFile.SLDsEncFileDACV2("c:\\softcamp\\04_KeyFile\\keyDAC_SVR0.sc", "Hway", filePath.replace("//", "\\").replace("/", "\\") + filename_org, StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH).replace("//", "\\").replace("/", "\\") + "\\" + filename_org, 0);
      System.out.println("SLDsEncFileDAC :" + ret);
      returnValue = StringUtil.checkNull(GlobalVal.DRM_DECODING_FILEPATH) + "//" + filename_org;
    } catch (Exception e) {
      throw new ExceptionUtil(e.toString());
    }

    return returnValue;
  }
  
  /////////////////////////////////////////////2023.09 AIP 추가  //////////////////////////////////////////////////////////////////////////////
  // DecryptFile
  public static String uploadAip(HashMap drmInfoMap) throws Exception {
	  
	String LegacySystemID = "2WsAas55Im8jv7keL0a5lfXBsH1sy9gt4y/DJVb+6hccSSkx/pFbWnH0R68c108kIEO1Wxyk0vJ890VsuMJr8UigHI5H5HYqiKDq7q12GIBPfIanBR2i1g3JEonh0xAkGsW8Gl5Kbz0lMwU8jeS6V8hAuMcozOfTe89t3cdZroLp0Gu+QxpyZQ==";
	String RemoteLoginId = "aipgateway";
	String RemoteLoginPw =  "Qazwsx!@12";
	String RemoteServer = "\\\\10.176.224.63\\olmfile";
	String ReportRemoteServer = "\\\\10.176.224.63\\OLM3.2";
	String UserPrincipalName = "hecSecure@aipgw.com";
	String aipUrl = "https://aipgwdev.hec.co.kr/";
	String labelID = "3c381d04-f1ec-4fd7-ac39-91e42085e2b5"; // DEV
	// String labelID = "cdcaf456-5176-400c-b0e6-6301b2c47597"; // OPS
	// String OutputDirectory = "\\\\10.176.224.63\\olmfile\\document\\DRM\\";
	//String FilePath = "\\\\10.176.224.63\\olmfile\\document\\FLTP001\\";
	  
  	System.out.println("aip_hec upload ="+drmInfoMap);
  	String filePath = StringUtil.checkNull( drmInfoMap.get("DRMFileDir")).replace("//","\\").replace("/", "\\"); // targetPath
  	String fileName = StringUtil.checkNull(drmInfoMap.get("Filename"));
  	String result = "";
  	
  	//암호화되어 있지 않으면 종료
  	if(!IsLabeledOrProtected(filePath+fileName)) {
  		System.out.println("암호화 X ");
  		return result;
  	}
      
  	URL url               = null;
    HttpsURLConnection con = null;
    OutputStream os       = null;  
    BufferedReader br     = null;
     
    StringBuilder response = null;
    int status             = 0;
    String strUrl          = "";
    String responseLine    = "";
              
    // Map에 데이터 담기
    Map<String, Object> fileMap = new HashMap<>();
    fileMap.put("LegacySystemID", LegacySystemID); 
    fileMap.put("IsRemote", true);
    fileMap.put("RemoteLoginId", RemoteLoginId);// 운영 -> // fileMap.put("RemoteLoginId", "aipgateway");
    fileMap.put("RemoteLoginPw", RemoteLoginPw); // 운영 -> // fileMap.put("RemoteLoginPw", "Qazwsx!@12");
    fileMap.put("userPrincipalName", UserPrincipalName);
    fileMap.put("decryptFlags","0");   
     
    fileMap.put("FilePath", filePath + fileName);        //  fileInfo.setFilePath("\\\\10.10.10.1\\AIPShare\\TestFiles\\SampleFile.xlsx");
    fileMap.put("RemoteServer", RemoteServer); // fileInfo.setRemoteServer("\\\\10.10.10.1\\AIPShare\\TestFiles\\");
    fileMap.put("outputDirectory",filePath); // \\\\10.176.224.63\\olmfile\\document\\FLTP001\\ (예시) 
    
    // Map -> JSON
    JSONObject fileJsonObject = new JSONObject(fileMap);
    String fileJsonOjectString = fileJsonObject.toString();
              
    strUrl =  aipUrl + "RestAPI/api/DecryptFile";
     
    url = new URL(strUrl);
     
	SSLContext ssl = SSLContext.getInstance("TLSv1.2"); 
	ssl.init(null, null, new SecureRandom());
		
    con = (HttpsURLConnection) url.openConnection();
    con.setSSLSocketFactory(ssl.getSocketFactory());
      
    con.setRequestMethod("PUT");
    con.setRequestProperty("Content-Type", "application/json; utf-8");
    con.setRequestProperty("Accept", "application/json");        
     
    con.setDoOutput(true);        
      
    os = con.getOutputStream();
      
    byte[] input = fileJsonOjectString.getBytes("utf-8");
    os.write(input, 0, input.length);    
      
    status = con.getResponseCode();
    System.out.println("status :"+status);
    if (status == 200) {
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));                
        response = new StringBuilder();
          
        while ((responseLine = br.readLine()) != null) 
        {
             response.append(responseLine.trim());
        }
    }
    result = response.toString();
    System.out.println("aip 복호화 result : "+result);
      
    return result;
  }
  
  public static String downloadAip(HashMap drmInfoMap) throws Exception {  
	String LegacySystemID = "2WsAas55Im8jv7keL0a5lfXBsH1sy9gt4y/DJVb+6hccSSkx/pFbWnH0R68c108kIEO1Wxyk0vJ890VsuMJr8UigHI5H5HYqiKDq7q12GIBPfIanBR2i1g3JEonh0xAkGsW8Gl5Kbz0lMwU8jeS6V8hAuMcozOfTe89t3cdZroLp0Gu+QxpyZQ==";
	String RemoteLoginId = "aipgateway";
	String RemoteLoginPw =  "Qazwsx!@12";
	String RemoteServer = "\\\\10.176.224.63\\olmfile";
	String UserPrincipalName = "hecSecure@aipgw.com";
	String aipUrl = "https://aipgwdev.hec.co.kr/";
	String labelID = "3c381d04-f1ec-4fd7-ac39-91e42085e2b5"; // DEV
	// String labelID = "cdcaf456-5176-400c-b0e6-6301b2c47597"; // OPS
	//String OutputDirectory = "\\\\10.176.224.63\\olmfile\\document\\DRM\\";
	//String FilePath = "\\\\10.176.224.63\\olmfile\\document\\FLTP001\\";
	  	
	String fileName = StringUtil.checkNull(drmInfoMap.get("Filename"));
	//String filePath = StringUtil.checkNull(drmInfoMap.get("downFile")).replace("//","\\").replace("/", "\\").replace(fileName, ""); 
	 	
	String filePath = StringUtil.checkNull(drmInfoMap.get("drmOutputDir"));
  	//암호화되어 있으면 종료
  	if(IsLabeledOrProtected(filePath +fileName)) {
  		return "";
  	}
  	
      URL url               = null;
      HttpsURLConnection con = null;
      OutputStream os       = null;   
      BufferedReader br     = null;
      
      StringBuilder response = null;
      int status             = 0;
      String strUrl          = "";
      String responseLine    = "";
      
      
      // String remoteServer = "\\\\10.176.224.63\\AIPShare\\TestFiles\\";

      // Map에 데이터 담기
      Map<String, Object> fileMap = new HashMap<>();
      fileMap.put("LegacySystemID", LegacySystemID);
      fileMap.put("IsRemote", true);
      fileMap.put("RemoteLoginId", RemoteLoginId); // 운영 -> // fileMap.put("RemoteLoginId", "aipgateway");
      fileMap.put("RemoteLoginPw", RemoteLoginPw); // 운영 -> // fileMap.put("RemoteLoginPw", "Qazwsx!@12");
      fileMap.put("LabelID", labelID);
      fileMap.put("userPrincipalName", UserPrincipalName);
      
      fileMap.put("FilePath", filePath+fileName);        //  fileInfo.setFilePath("\\\\10.10.10.1\\AIPShare\\TestFiles\\SampleFile.xlsx");
      fileMap.put("RemoteServer", RemoteServer); // fileInfo.setRemoteServer("\\\\10.10.10.1\\AIPShare\\TestFiles\\");
      fileMap.put("outputDirectory", filePath); // fileInfo.setOutputDirectory("\\\\10.10.10.1\\AIPShare\\TestFiles\\");
      System.out.println("aip download fileMap :"+fileMap);

      // Map -> JSON
      JSONObject fileJsonObject = new JSONObject(fileMap);
      String fileJsonObjectString = fileJsonObject.toString();
      
      System.out.println("SetLabel fileJsonObjectString :"+fileJsonObjectString);
      //AIP G/W API를 호출하기 위한 API URL입니다.(각 API별 Reqeust 예시 참조)
      strUrl = aipUrl + "RestAPI/api/Label/SetLabel";        
      url = new URL(strUrl);
      
		SSLContext ssl = SSLContext.getInstance("TLSv1.2"); 
		ssl.init(null, null, new SecureRandom());
		
      con = (HttpsURLConnection) url.openConnection();
     // con.setSSLSocketFactory(ssl.getSocketFactory());
      
      con.setRequestMethod("PUT");
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      con.setRequestProperty("Accept", "application/json");        
      con.setDoOutput(true);        
      
      os = con.getOutputStream();
      
      byte[] input = fileJsonObjectString.getBytes("utf-8");
      os.write(input, 0, input.length);                   
      
      status = con.getResponseCode();
      
      // 1 : Success 결과성공, 9999 : MIP SDK Exception  
      System.out.println("downLoad status  :"+status);
      if (status == 200) {
          br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));                
          response = new StringBuilder();
          
          while ((responseLine = br.readLine()) != null) 
          {
              response.append(responseLine.trim());
          }
      }        
      return filePath + fileName;
  }
  
  // report
  public String report(HashMap drmInfoMap) throws Exception {    
	String LegacySystemID = "2WsAas55Im8jv7keL0a5lfXBsH1sy9gt4y/DJVb+6hccSSkx/pFbWnH0R68c108kIEO1Wxyk0vJ890VsuMJr8UigHI5H5HYqiKDq7q12GIBPfIanBR2i1g3JEonh0xAkGsW8Gl5Kbz0lMwU8jeS6V8hAuMcozOfTe89t3cdZroLp0Gu+QxpyZQ==";
	String RemoteLoginId = "aipgateway";
	String RemoteLoginPw =  "Qazwsx!@12";
	String RemoteServer = "\\\\10.176.224.63\\olmfile";
	String ReportRemoteServer = "\\\\10.176.224.63\\OLM3.2";
	String UserPrincipalName = "hecSecure@aipgw.com";
	String aipUrl = "https://aipgwdev.hec.co.kr/";
	String labelID = "3c381d04-f1ec-4fd7-ac39-91e42085e2b5"; // DEV
	// String labelID = "cdcaf456-5176-400c-b0e6-6301b2c47597"; // OPS
	// String OutputDirectory = "\\\\10.176.224.63\\olmfile\\document\\DRM\\";
	//String FilePath = "\\\\10.176.224.63\\olmfile\\document\\FLTP001\\";
  	String fileName = StringUtil.checkNull(drmInfoMap.get("orgFileName"));
  	//String filePath = StringUtil.checkNull(drmInfoMap.get("downFile")).replace("//","\\").replace("/", "\\"); 
  	//if(filePath.equals("")){	filePath = StringUtil.checkNull(FileUtil.FILE_EXPORT_DIR).replace("//","\\").replace("/", "\\"); }
  	
  	// String filePath = "\\\\10.176.224.63\\OLM3.2\\webapps\\ROOT\\doc\\export\\";
  	String filePath = "\\\\10.176.224.63\\olmfile\\document\\";
  	
  	String result = "";
  	//암호화되어 있으면 종료
  	if(IsLabeledOrProtected(filePath +fileName)) {
  		return result;
  	}
  	
      URL url               = null;
      HttpsURLConnection con = null;
      OutputStream os       = null;   
      BufferedReader br     = null;
      
      StringBuilder response = null;
      int status             = 0;
      String strUrl          = "";
      String responseLine    = "";
      
      // String remoteServer = "\\\\10.176.224.63\\AIPShare\\TestFiles\\";

      // Map에 데이터 담기
      Map<String, Object> fileMap = new HashMap<>();
      fileMap.put("LegacySystemID", LegacySystemID);
      fileMap.put("IsRemote", true);
      fileMap.put("RemoteLoginId", RemoteLoginId); // 운영 -> // fileMap.put("RemoteLoginId", "aipgateway");
      fileMap.put("RemoteLoginPw", RemoteLoginPw); // 운영 -> // fileMap.put("RemoteLoginPw", "Qazwsx!@12");
      fileMap.put("LabelID", labelID);
      fileMap.put("userPrincipalName", UserPrincipalName);
      
      fileMap.put("FilePath", filePath+fileName);        //  fileInfo.setFilePath("\\\\10.10.10.1\\AIPShare\\TestFiles\\SampleFile.xlsx");
      fileMap.put("RemoteServer", ReportRemoteServer); // fileInfo.setRemoteServer("\\\\10.10.10.1\\AIPShare\\TestFiles\\");
      fileMap.put("outputDirectory", filePath); // fileInfo.setOutputDirectory("\\\\10.10.10.1\\AIPShare\\TestFiles\\");
      System.out.println("aip download fileMap :"+fileMap);

      // Map -> JSON
      JSONObject fileJsonObject = new JSONObject(fileMap);
      String fileJsonObjectString = fileJsonObject.toString();
      
      System.out.println("SetLabel fileJsonObjectString :"+fileJsonObjectString);
      //AIP G/W API를 호출하기 위한 API URL입니다.(각 API별 Reqeust 예시 참조)
      strUrl = aipUrl + "RestAPI/api/Label/SetLabel";        
      url = new URL(strUrl);
      
		SSLContext ssl = SSLContext.getInstance("TLSv1.2"); 
		ssl.init(null, null, new SecureRandom());
		
      con = (HttpsURLConnection) url.openConnection();
     // con.setSSLSocketFactory(ssl.getSocketFactory());
      
      con.setRequestMethod("PUT");
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      con.setRequestProperty("Accept", "application/json");        
      con.setDoOutput(true);        
      
      os = con.getOutputStream();
      
      byte[] input = fileJsonObjectString.getBytes("utf-8");
      os.write(input, 0, input.length);                   
      
      status = con.getResponseCode();
      
      // 1 : Success 결과성공, 9999 : MIP SDK Exception  
      System.out.println("report aip 암호화 status  :"+status);
      if (status == 200) {
          br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));                
          response = new StringBuilder();
          
          while ((responseLine = br.readLine()) != null) 
          {
              response.append(responseLine.trim());
          }
          System.out.println("report  aip 암호화 완료 !!!response :"+response); 
      }       
      
      return result;
  }
  
  // 암화화 확인 
  public static boolean IsLabeledOrProtected(String filePath) throws Exception {
	String LegacySystemID = "2WsAas55Im8jv7keL0a5lfXBsH1sy9gt4y/DJVb+6hccSSkx/pFbWnH0R68c108kIEO1Wxyk0vJ890VsuMJr8UigHI5H5HYqiKDq7q12GIBPfIanBR2i1g3JEonh0xAkGsW8Gl5Kbz0lMwU8jeS6V8hAuMcozOfTe89t3cdZroLp0Gu+QxpyZQ==";
	String RemoteLoginId = "aipgateway";
	String RemoteLoginPw =  "Qazwsx!@12";
	String RemoteServer = "\\\\10.176.224.63\\olmfile";
	String ReportRemoteServer = "\\\\10.176.224.63\\OLM3.2";
	String UserPrincipalName = "hecSecure@aipgw.com";
	String aipUrl = "https://aipgwdev.hec.co.kr/";
	String labelID = "3c381d04-f1ec-4fd7-ac39-91e42085e2b5"; // DEV
	// String labelID = "cdcaf456-5176-400c-b0e6-6301b2c47597"; // OPS
	// String OutputDirectory = "\\\\10.176.224.63\\olmfile\\document\\DRM\\";
	//String FilePath = "\\\\10.176.224.63\\olmfile\\document\\FLTP001\\";
		
  	boolean retVal = false;
  	
      StringBuffer sb       = null;
      StringBuilder resSb   = null;
      URL url               = null;
      HttpsURLConnection con = null;        
      
      BufferedReader br  = null;
      int status         = 0;
      String strReqParam = "";
      String strUrl      = "";
      
     // String remoteServer = "\\\\10.176.224.63\\AIPShare\\TestFiles\\";
      Map<String, String> params = new HashMap<String, String>();
      
      params.put("LegacySystemID", URLEncoder.encode(LegacySystemID, "UTF-8"));
      params.put("IsRemote",       URLEncoder.encode("true", "UTF-8"));
      params.put("RemoteLoginId",  RemoteLoginId);// 운영->//params.put("RemoteLoginId",  URLEncoder.encode("aipgateway", "UTF-8"));
      params.put("RemoteLoginPw",  RemoteLoginPw);// 운영 -> // params.put("RemoteLoginPw",  URLEncoder.encode("Qazwsx!@12", "UTF-8"));
      params.put("FilePath",      URLEncoder.encode(filePath, "UTF-8"));     // params.put("FilePath", URLEncoder.encode(\\\\10.10.10.1\\AIPShare\\TestFiles\\SampleFile.xlsx, "UTF-8"));
      params.put("RemoteServer",  URLEncoder.encode(RemoteServer, "UTF-8")); // params.put("RemoteServer",  URLEncoder.encode("\\\\10.10.10.1\\AIPShare\\TestFiles\\", "UTF-8"));

      sb = new StringBuffer();
      
      for (String key : params.keySet()) 
      {
        if (sb.length() > 0) 
        {
            sb.append("&");
        }
        sb.append(key);
        sb.append("=");
        sb.append(params.get(key));
      }
              
      strReqParam = sb.toString();      
      
      strUrl =  aipUrl + "RestAPI/api/IsLabeledOrProtected?" + strReqParam;

      url = new URL(strUrl);
      
		SSLContext ssl = SSLContext.getInstance("TLSv1.2"); 
		ssl.init(null, null, new SecureRandom());
		
      con = (HttpsURLConnection) url.openConnection();
      con.setSSLSocketFactory(ssl.getSocketFactory());
      con.setRequestMethod("GET");        
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      status = con.getResponseCode();
      
      if (status == 200) {
          br = new BufferedReader(new InputStreamReader(con.getInputStream()));
          resSb = new StringBuilder();
          String line = "";

          while ((line = br.readLine()) != null) {
              resSb.append(line);
          }            
      }        
           
      try {
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(resSb.toString());
			String FileEncryptedStatus = object.get("FileEncryptedStatus").toString();
			
			if(!FileEncryptedStatus.equals("0"))
				retVal = true;
      }
      catch(ParseException e) {
      	System.out.println(e);
      }
  	return retVal;
  }
}