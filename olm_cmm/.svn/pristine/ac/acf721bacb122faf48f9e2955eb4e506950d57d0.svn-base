package xbolt.cmm.framework.resolver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import xbolt.cmm.framework.util.DateUtil;
import xbolt.cmm.framework.util.FileUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;


/**
 * @Class Name : MapArgumentResolver.java
 * @Description : 기본서비스 틀을 잡기 위함
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
public class MapArgumentResolver implements WebArgumentResolver{

private static String [][] catchUrls = {
	 {"/saveBoard.do","Content" },
	 {"/saveNewSr.do","description" },
	 {"/saveForumPost.do","content" },
	 {"/updateForumPost.do","content" },
	 {"/boardForumNew.do","content" },
	 {"/saveForumReply.do","content" },	 
	 {"/saveObjectInfo.do", "AT00003"},
	 {"/saveItemAttr.do", "AT00003"},
	 {"/saveReceiveSRInfo.do", "comment"},
	 {"/saveCSRInfo.do", "Description"},
	 {"/saveNewCr.do", "Description"},
	 {"/createSCRMst.do", "changeScope"},
	 {"/updateSCRInfo.do", "changeScope"},
	 {"/createESRMst.do", "description"},
	 {"/updateESRInfo.do", "comment"}
	};

	/**
	 * Controller의 메소드 argument의 이름이 "~MAP~"이라는 Map 객체가 있다면
	 * HTTP request 객체에 있는 파라미터이름과 값을 cmmMap에 담아 return한다.
	 * 배열인 파라미터 값은 배열로 Map에 저장한다.
	 * 또한 객체의 argument의 이름이 "~FILE~"이라면 File에 대한 가공도 이루어진다.
	 */
	@SuppressWarnings("unchecked")
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		String paramName = methodParameter.getParameterName();
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		String context 				= request.getContextPath();
		request.setAttribute("htmlTitle", GlobalVal.HTML_TITLE);
		//Set Image Path
		request.setAttribute("HTML_IMG_DIR", GlobalVal.HTML_IMG_DIR);
		request.setAttribute("HTML_IMG_DIR_ARC", GlobalVal.HTML_IMG_DIR_ARC);
		request.setAttribute("HTML_IMG_DIR_SHORTCUT", GlobalVal.HTML_IMG_DIR_SHORTCUT);
		request.setAttribute("HTML_IMG_DIR_POPUP", GlobalVal.HTML_IMG_DIR_POPUP);
		request.setAttribute("HTML_IMG_DIR_MODEL_SYMBOL", GlobalVal.HTML_IMG_DIR_MODEL_SYMBOL);
		request.setAttribute("HTML_IMG_DIR_ITEM", GlobalVal.HTML_IMG_DIR_ITEM);
		request.setAttribute("HTML_CSS_DIR", GlobalVal.HTML_CSS_DIR);
		//System.out.println("IMG="+GlobalVal.HTML_IMG_DIR+" ::: "+request.getServletPath());

		if(paramName != null && paramName.toUpperCase().indexOf("MAP")!=-1){

			String spliteParam ="<img ";
			String replaceStartParam = "src=\"data:image/png;base64,";
			String replaceEndParam = "/>";
			String changImgSrc = spliteParam+" border='0' src=\"";
			String findEndParam = "/>";
			Map<String, Object> cmmMap = new HashMap<String, Object>();
			String reqUrl 				= request.getServletPath();
			int catchUrlNo = getCatchUrlNo(reqUrl);
			String catchContent = "";
			if(catchUrlNo>-1){catchContent =StringUtil.checkNull(catchUrls[catchUrlNo][1]); }
			String s[] = reqUrl.split("[/]");
			request.setAttribute("selectedMenu", s[s.length-2]);
			request.setAttribute("selectedAction", s[s.length-1]);
			cmmMap.put("selectedMenu", s[s.length-2]);
			cmmMap.put("selectedAction", s[s.length-1]);

			Enumeration<?> enumeration = request.getParameterNames();

			cmmMap.put("DB_KEY", GlobalVal.DB_KEY);
			//Pageing설정
			cmmMap.put("LIST_SCALE", GlobalVal.LIST_SCALE);
			cmmMap.put("LIST_PAGE_SCALE", GlobalVal.LIST_PAGE_SCALE);

			//Login Information Setting
			String usrK = "";
			try {
				Map loginInfo = null;
				loginInfo = (Map) request.getSession(false).getAttribute("loginInfo");
				if(loginInfo!=null) {
					usrK = StringUtil.checkNull(loginInfo.get("sessionUserId"));
					cmmMap.putAll(loginInfo);
					cmmMap.put("loginInfo", loginInfo);
					cmmMap.put("contextPath", request.getContextPath());
					cmmMap.put("languageID",StringUtil.checkNull(loginInfo.get("sessionCurrLangType")));
					//System.out.println("cmmMap.get(languageID)="+cmmMap.get("languageID")+":::"+request.getParameter("languageID"));
				}
				else {
					cmmMap.put("sessionLoginId", "master");
					cmmMap.put("sessionUserId", "111");
				}
			}
			catch (Exception e) {System.out.println(e);}
			
			//SearchParam Setting
			try {
				Map searchInfo = (Map) request.getSession(false).getAttribute("searchInfo");
				if(searchInfo!=null) {
					cmmMap.putAll(searchInfo);
					cmmMap.put("searchInfo", searchInfo);
					cmmMap.put("contextPath", request.getContextPath());
				}
			}
			catch (Exception e) {System.out.println(e);}
			
			//TinyImage replace
			String value="";
			while(enumeration.hasMoreElements()){
				String key = (String) enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				if(values!=null){ 
					if(key.indexOf("OLM_ARRAY_VALUE") > 0) {
						String tempV = values[0];
						
						if(!"".equals(tempV)) {
							String[] tempVA = tempV.split(",");							
							cmmMap.put(key.replace("OLM_ARRAY_VALUE", ""), tempVA);
						}
						
					}
					else if(key.indexOf("OLM_MULTI_VALUE") > 0) {
						String tempV = values[0];
						String[] tempVA = tempV.split(",",-1);
						List tempL = new ArrayList();
						Map tempMap = new HashMap();
						for(int i=0; i<tempVA.length; i+=6) {
							tempMap = new HashMap();
							tempMap.put("attrCode", tempVA[i]);
							
							if(!"".equals(tempVA[i+1]))
								tempMap.put("lovCode", tempVA[i+1].split("\\*"));
							
							tempMap.put("searchValue", tempVA[i+2].replaceAll("comma", ",")); // 배열구분자인 comma와 파라미터 comma 구분
							tempMap.put("AttrCodeEscape", tempVA[i+3]);
							tempMap.put("constraint", tempVA[i+4]);
							tempMap.put("selectOption", tempVA[i+5]);
							tempL.add(tempMap);
						}
						
						cmmMap.put(key.replace("OLM_MULTI_VALUE", ""), tempL);
						// System.out.println(key.replace("OLM_MULTI_VALUE", "")+":"+tempL);
						// AttrCode:[{attrCode=AT00001, selectOption=AND, AttrCodeEscape=, constraint=, searchValue=A}, {attrCode=AT00003, selectOption=AND, AttrCodeEscape=, constraint=, searchValue=T}]
						
					}
					else if(key.indexOf("OLM_MULTI_DIM") > 0) {
						String tempV = values[0];
						JSONArray array = JSONArray.fromObject(tempV);						 
						List tempL = new ArrayList();
						Map tempMap = new HashMap();
						for(int i=0; i<array.size(); i++) {
							JSONObject dimData = (JSONObject)array.get(i);
							tempMap = new HashMap();
							tempMap.put("dimTypeID", dimData.get("dimTypeID"));														
							tempMap.put("dimValueID", dimData.get("dimValueID"));
							tempL.add(tempMap);
						}
						
						cmmMap.put(key.replace("OLM_MULTI_DIM", ""), tempL);
						
					}
					else {
						if(catchContent.equals(key) ||catchContent.equals("AT00003")){	
							//201606 <img로 시작하는 tag의 src값(data:image/png;base64)을 image로 변환하여 저장한다.
							cmmMap.put(key, (values.length > 1) ? values:values[0]);
							value = StringUtil.checkNull((values.length > 1) ? values:values[0]);
							String uploadFileName = "";
							String uploadPath = GlobalVal.FILE_UPLOAD_TINY_DIR;
							String[] replaceValues = value.split(spliteParam);
							String changeValue="";
							if( replaceValues.length > 1){
								changeValue = replaceValues[0];
								for(int i=1; i<replaceValues.length;i++){
									String fileUrl = "upload/";
									String replaceValue=replaceValues[i];
									int endIndex = replaceValue.indexOf(replaceEndParam);
									if(replaceValue.indexOf(replaceStartParam) > -1){
										String imageString = replaceValue.substring(replaceStartParam.length() + replaceValue.indexOf(replaceStartParam), endIndex-3);
										uploadFileName = DateUtil.getSysYearSecond()+usrK+i+".png";
										fileUrl += uploadFileName;
										changeValue+= " "+changImgSrc+fileUrl+"\""+replaceValue.substring(endIndex-1,replaceValue.length());										
										//System.out.println("**MapArgumentResolover class IN::: i="+i+":::fileUrl="+fileUrl+":::changeValue="+changeValue);
										//System.out.println("**MapArgumentResolover class IN::: i="+i+":::img orgin Data="+replaceValue+":::"+imageString);
										File uploadDir = new File(uploadPath);								
										if(!uploadDir.exists()){
											uploadDir.mkdir();
										}
										// create a buffered image
										BufferedImage image = null;
										byte[] imageByte;
		
										BASE64Decoder decoder = new BASE64Decoder();
										imageByte = decoder.decodeBuffer(imageString);
										ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
										image = ImageIO.read(bis);
										bis.close();
										// write the image to a file
										File outputfile = new File(uploadPath, uploadFileName);
										ImageIO.write(image, "png", outputfile);
									}else{
										changeValue += spliteParam +replaceValue;
									}
									//System.out.println("**MapArgumentResolover class OUT:: i="+i+":::fileUrl="+fileUrl+":::changeValue="+changeValue);
								}
								cmmMap.put(key, changeValue.replaceAll("/upload/", "upload/"));
							}else{
								cmmMap.put(key, (values.length > 1) ? values:values[0]);
							}
						}else {
							if(!cmmMap.containsKey(key)) {	
								
								for(int l=0; l<values.length; l++) {									
									if(values[l] != null && !"".equals(values[l])) {
										cmmMap.put(key, values[l]);	
									}
								}
								
							}
						}
					}
				}
			}
			if(paramName != null && paramName.toUpperCase().indexOf("FILE")!=-1){
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)webRequest.getNativeRequest();
				Iterator fileIter = mptRequest.getFileNames();

				ArrayList result = new ArrayList();
				String id = null;
				String oldName = "";

				int ii=0;
				String jj="";
				String totCnt="";
				String fileCnt="";
				String gubun="xx";
				cmmMap.put("FILE_GUNUM", gubun);
				String fileUplaodDir = "";
				while (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String)fileIter.next());
					//이미지 파일만 저장한다!!
					//다른 확장자를 저장해야 하는 경우 추가해야 한다.
					if(FileUtil.isPicture(FileUtil.getExt(mFile))) {

						//리스트로 넘어올때만 사용됨(mFile.getName()!='AttFileID')
						if(mFile.getName().length() > 11){
							gubun = "list";
							jj = mFile.getName().substring(11);
							totCnt = mptRequest.getParameter("TOT_CNT");

							//마지막 파일 리스트는 id값이 0으로 옴
							if(jj.equals('0')|| jj.equals("0")|| jj.equals(0)){
								jj = totCnt;
							}

							/*
							if(cmmMap.get("AttFileID_OLD"+jj)==null || cmmMap.get("AttFileID_OLD"+jj).equals("")) {
								id= service.nextId("TB_ATTFILE", false);
								cmmMap.put("AttFileID", id);
								cmmMap.put("FILE_ST"+jj, "in");
							}
							else {
								cmmMap.put("AttFileID", cmmMap.get("AttFileID_OLD"+jj));
								id = cmmMap.get("AttFileID_OLD"+jj).toString();
								cmmMap.put("FILE_ST"+jj, "up");
							}
							*/
						}
						//일반적으로 파일저장할때(mFile.getName()=='AttFileID')
						else{
							gubun="gen";
							if (!oldName.equals(mFile.getName().substring(0, mFile.getName().length()-1))) {
								if(mFile.getName().equals("AttFileID")){
									oldName = mFile.getName().substring(0, mFile.getName().length());
								}else{
									oldName = mFile.getName().substring(0, mFile.getName().length()-1);
								}

								if(cmmMap.get(oldName+"_OLD")==null || cmmMap.get(oldName+"_OLD").equals("")) {
									id= DateUtil.getCurrentTime();//service.nextId("TB_ATTFILE", false);
									cmmMap.put(oldName, id);
								}
								else {
									cmmMap.put(oldName, cmmMap.get(oldName+"_OLD"));
								}
							}
						}
						 System.out.println("1.mFile.getOriginalFilename() : "+mFile.getOriginalFilename());
						 System.out.println("1.mFile.getName() : "+mFile.getName());
						 System.out.println("1.mFile.getContentType() : "+mFile.getContentType());
						 System.out.println("1.mFile.getSize() : "+mFile.getSize());

						if (mFile.getSize() > 0) {
							//물리적 파일 저장
							HashMap map = FileUtil.uploadFile(mFile, fileUplaodDir, true);
							if(id != null) {
								map.put(FileUtil.FILE_ID, id);
							}
							else {
								map.put(FileUtil.FILE_ID, cmmMap.get(FileUtil.FILE_ID));
							}
							//map.put("FILE_REMARK", cmmMap.get(FileUtil.FILE_REMARK + mFile.getName()));

					 		//service.insert("CommonFile.cmmFile_insert", map);	//new mode
							if(mFile.getName().length() > 11){
								ii++;

								cmmMap.put("AttFileID"+jj, map.get("AttFileID"));
								//cmmMap.put("COMPANY_ID"+jj, mptRequest.getParameter("COMPANY_ID"+jj));
								fileCnt = jj+"/"+fileCnt;
								cmmMap.put("FILE_CNT", fileCnt);
								cmmMap.put("REL_TOT_CNT", ii);
							}
							cmmMap.put("FILE_GUNUM", gubun);
							result.add(map);
						}
					} else if(FileUtil.isExcel(FileUtil.getExt(mFile))){
						if (!oldName.equals(mFile.getName().substring(0, mFile.getName().length()-1))) {
							//oldName = mFile.getName().substring(0, mFile.getName().length());
							if(mFile.getName().length()==9){
								oldName = mFile.getName().substring(0, mFile.getName().length());
							}else{
								oldName = mFile.getName().substring(0, mFile.getName().length()-1);
							}
							if(cmmMap.get(oldName+"_OLD")==null || cmmMap.get(oldName+"_OLD").equals("")) {
								id= DateUtil.getCurrentTime();//service.nextId("TB_ATTFILE", false);								
								cmmMap.put(oldName, id);
							}
							else {
								cmmMap.put(oldName, cmmMap.get(oldName+"_OLD"));
							}
						}
						if (mFile.getSize() > 0) {
							//물리적 파일 저장
							HashMap map = FileUtil.uploadFile(mFile, fileUplaodDir, true);
							if(id != null) {
								map.put(FileUtil.FILE_ID, id);
							}
							else {
								map.put(FileUtil.FILE_ID, cmmMap.get(FileUtil.FILE_ID));
							}
							//service.insert("CommonFile.cmmFile_insert", map);	//new mode
							result.add(map);
						}
					}else if(FileUtil.isAllFile(FileUtil.getExt(mFile))){
						if (!oldName.equals(mFile.getName().substring(0, mFile.getName().length()-1))) {
							//oldName = mFile.getName().substring(0, mFile.getName().length());
							if(mFile.getName().length()==9){
								oldName = mFile.getName().substring(0, mFile.getName().length());
							}else{
								oldName = mFile.getName().substring(0, mFile.getName().length()-1);
							}
							if(cmmMap.get(oldName+"_OLD")==null || cmmMap.get(oldName+"_OLD").equals("")) {
								id= DateUtil.getCurrentTime();//service.nextId("TB_ATTFILE", false);
								cmmMap.put(oldName, id);
							}
							else {
								cmmMap.put(oldName, cmmMap.get(oldName+"_OLD"));
							}
						}

						if (mFile.getSize() > 0) {
							//물리적 파일 저장
							HashMap map = FileUtil.uploadFile(mFile, fileUplaodDir, true);
							if(id != null) {
								map.put(FileUtil.FILE_ID, id);
							}
							else {
								map.put(FileUtil.FILE_ID, cmmMap.get(FileUtil.FILE_ID));
							}
							//map.put("FILE_REMARK", cmmMap.get(FileUtil.FILE_REMARK + mFile.getName()));
							//service.insert("CommonFile.cmmFile_insert", map);	//new mode
							result.add(map);
						}
					}
				}
				cmmMap.put(FileUtil.STORED_FILES, result);
			}
			/*	암호화 기능부분. 모듈도 생략
			final String AJAXPARAM = "ajaxParam";
			for (int i = 100; i < 200; i++) {
				Object array_element = cmmMap.get(AJAXPARAM+i);
				if(array_element!=null) {
					cmmMap.put(AJAXPARAM+i, PoliceUtil.encryptString(array_element));
				}
			}*/

			return cmmMap;
		}
		return UNRESOLVED;
	}
	
	
	private int getCatchUrlNo(String url) {
		int iResult = -1;
		int i=0;
		for (String[] catchUrl : catchUrls) {
			//System.out.println("**MapArgumentResolover class ::: url="+url+", catchUrl[0]="+catchUrl[0]+", iResult="+iResult);
			if (url.indexOf(catchUrl[0])!=-1) {
				iResult = i;
				break;
			}
			i++;
		}
		return iResult;
	}
}