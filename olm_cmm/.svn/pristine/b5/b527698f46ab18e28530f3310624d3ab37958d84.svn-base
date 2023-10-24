package xbolt.cmm.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.framework.mdlparser.XMLParser;
import xbolt.cmm.framework.mdlparser.XMLParserARIS;
import xbolt.cmm.framework.mxgraph.imageExport;
import xbolt.cmm.framework.mxgraph.xmlExportFromARIS;
import xbolt.cmm.service.dtoMdl.*;
import xbolt.cmm.service.CommonService;

public class DiagramEditCRUDUtil {
	
	private final Log _log = LogFactory.getLog(this.getClass());
	private CommonService commonService;
	private static String dfMxGraphModel="<mxGraphModel style=\"default-style2\" dx=\"800\" dy=\"800\" grid=\"1\" guides=\"1\" tooltips=\"1\" connect=\"1\" fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"2000\" pageHeight=\"2000\" >";
	private static String dfCnnItemCategory = "MCN";
	private static String[] modelStatus=new String[] {"NEW","MOD","REL", "MOD1"};

	public DiagramEditCRUDUtil() {
	}
	
	public void save(List lst, Map cmmMap, CommonService commonService) throws Exception{
		this.commonService = commonService;
		
		String gubun = (String)cmmMap.get("GUBUN");
		if("blocked".equals(gubun)){}
		else if("save".equals(gubun)){saveDiagram(cmmMap);}
		else if("commit".equals(gubun)){commitDiagram(cmmMap);}
		else if("createE2e".equals(gubun)){createE2eTreeModel(cmmMap);}
		else if("saveARIS".equals(gubun)){createARISModel(cmmMap);}
		else if("insertBaseModel".equals(gubun)){insertBaseModel(cmmMap);}
		else if("batchSetMdlImage".equals(gubun)){batchSetMdlImage(cmmMap);}
	}
	
	public void saveModelBlocked(Map cmmMap) throws Exception{
		try {
			commonService.update("model_SQL.updateModelBlocked", cmmMap);
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }		
	}

	
	public void saveDiagram(Map cmmMap) throws Exception{
		String diagramXML = StringUtil.checkNull(cmmMap.get("diagram"));
		String modelID = StringUtil.checkNull(cmmMap.get("modelID"));
		
		diagramXML = diagramXML.replaceAll("  ", "  ");
		diagramXML = diagramXML.replaceAll("  ", " ");
		String dfLanguageID = StringUtil.checkNull(cmmMap.get("sessionCurrLangType"),"");			
		int mdlHeight = NumberUtil.getIntValue(cmmMap.get("mdlHeight"), 0);
		int mdlWidth = NumberUtil.getIntValue(cmmMap.get("mdlWidth"), 0);
		//if(mdlHeight<NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_H)){mdlHeight=NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_H);}
		//if(mdlWidth<NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_W)){mdlWidth=NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_W);}
			try {
			if(diagramXML.indexOf("<mxGraphModel")<0){
				String dfXml=dfMxGraphModel;
				if(diagramXML.indexOf("<root>") == 0){
					//diagramXML=dfXml+"<root>"+diagramXML+"</root>"+"</mxGraphModel>";
					diagramXML=dfXml+diagramXML+"</mxGraphModel>"; // 20150810 hyoh firefox <mxGraphModel Add
				}
				else{dfXml=dfXml+diagramXML+"</mxGraphModel>";}
			}
			try{
			XMLParser xmlparser = new XMLParser(diagramXML);
			xmlparser.parseXML();
			ArrayList<HashMap<String, String>> diagramObjs = xmlparser.getObects();		
			
			HashMap<String, String> baseElement = diagramObjs.get(0);	
			//String modelID = "";	if(baseElement.get("objID") != null) {modelID = baseElement.get("objID");}
	
			Map setData = new HashMap();				
			setData.put("ModelID", modelID);	
			setData.put("ModelXML", replaceTag(diagramXML));	
			setData.put("Deleted", "1");			
			setData.put("LanguageID", dfLanguageID);	
			setData.put("languageID", dfLanguageID);
			setData.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
			
			Map modelMap = new HashMap();					
			modelMap = commonService.select("model_SQL.getModel", setData);
		
			String s_itemID = "";if(baseElement.get("objID") != null) {s_itemID = baseElement.get("objID");}
			String status = StringUtil.checkNull(modelMap.get("Status"));
	
			Map setModelMap = new HashMap();				
			//setData.put("ModelID", selectString("model_SQL.getMaxModelIDString", map));
			setModelMap.put("ItemID", s_itemID);
			setModelMap.put("ModelID", modelID);
			setModelMap.put("LanguageID", dfLanguageID);
			setModelMap.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
			//setModelMap.put("Width", mdlWidth);
			//setModelMap.put("Height", mdlHeight);	
			String auto = StringUtil.checkNull(cmmMap.get("auto"));
			if(!auto.equals("Y")) setModelMap.put("Status", status.equals(modelStatus[2])?modelStatus[1]:(status.equals(modelStatus[1])?modelStatus[1]:modelStatus[0]));
			//System.out.println("1:::s_itemID : "+s_itemID+" , modelID="+modelID+", org Status="+StringUtil.checkNull(modelMap.get("Status"))+", chg Status="+setModelMap.get("Status"));
			setModelMap.put("Zoom", StringUtil.checkNull(cmmMap.get("viewScale")));
			commonService.update("model_SQL.updateModelDefInfo", setModelMap);	
			if(modelID.equals("")){commonService.insert("model_SQL.insertModelXML", setData);}
			else{commonService.update("model_SQL.updateModelXML", setData);}	
			}catch (FileNotFoundException fnfe) {
				System.out.println(fnfe.toString());throw new Exception(fnfe.toString());
			} catch (IOException ioe) {
				System.out.println(ioe.toString());throw new Exception(ioe.toString());
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println(e.toString());throw new Exception(e.toString());
			}
        } catch(Exception e) {
    		System.out.println(e.toString());
        	throw new Exception(e.toString());
        }
	}

	public void batchSetMdlImage(Map cmmMap) throws Exception{
		try {
			imageExport.savaFile(cmmMap);
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }
	}
	
	public void commitDiagram(Map cmmMap) throws Exception{		
		String diagramXML = StringUtil.checkNull(cmmMap.get("diagram"));
		diagramXML = diagramXML.replaceAll("  ", "  ");
		diagramXML = diagramXML.replaceAll("  ", " ");		
		String dfLanguageID = StringUtil.checkNull(cmmMap.get("sessionCurrLangType"),"");			
		int mdlHeight = NumberUtil.getIntValue(cmmMap.get("mdlHeight"), 0);
		int mdlWidth = NumberUtil.getIntValue(cmmMap.get("mdlWidth"), 0);
	
		String mdlDy = StringUtil.checkNull(cmmMap.get("mdlDy"), "");
		String mdlDx = StringUtil.checkNull(cmmMap.get("mdlDx"), "");
		String itemPjtID = StringUtil.checkNull(cmmMap.get("itemPjtID"), "");
		String wNewItem = StringUtil.checkNull(cmmMap.get("wNewItem"), ""); // Release with new items
		String defClientID = GlobalVal.DEF_CLIENT_ID;
		String clientID = "";
		String refCModelID = "";
		
		if(diagramXML.indexOf("<mxGraphModel")<0){
			String dfXml=dfMxGraphModel;			
			if(diagramXML.indexOf("<root>")==0){
				//dfXml=dfXml+"<root>"+diagramXML+"</root>"+"</mxGraphModel>";
				diagramXML=dfXml+diagramXML+"</mxGraphModel>"; // 20150810 hyoh firefox <mxGraphModel Add
			}
			else{dfXml=dfXml+diagramXML+"</mxGraphModel>";}
		}
		try{
			XMLParser xmlparser = new XMLParser(diagramXML);
			xmlparser.parseXML();		
			ArrayList<HashMap<String, String>> diagramObjs = xmlparser.getObects();	
			
			ArrayList<HashMap> newConnections = new ArrayList<HashMap>();			
			HashMap<String, String> baseElement = diagramObjs.get(0);	
		
			//1. 생성할 Diagram 정보 조회
			//for(int i = 0; i < diagramObjs.size(); i++) {
			//	 diagramObjs.get(i);
			//	System.out.println("diagramObjs["+i+"] :"+diagramObjs.get(i));
			//}		
			
			String modelID = "";			
			if(baseElement.get("objID") != null) {modelID = baseElement.get("objID");}
			
			//image file Save
			cmmMap.put("fileModelID", modelID);
			if(!StringUtil.checkNull(cmmMap.get("imgXml")).equals("")){imageExport.savaFile(cmmMap);}
			
			//2. Model XML정보 생성/수정
			Map setData = new HashMap();				
			setData.put("ModelID", modelID);	
			setData.put("ModelXML", diagramXML);	
			setData.put("Deleted", "1");			
			setData.put("LanguageID", dfLanguageID);			
			setData.put("languageID", dfLanguageID);			
			setData.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
			Map modelMap = new HashMap();					
			modelMap = commonService.select("model_SQL.getModel", setData);
	
			String s_itemID = StringUtil.checkNull(cmmMap.get("itemID"),"");	
			//if(baseElement.get("objID") != null) {s_itemID = baseElement.get("objID");}
			
			Map setModelMap = new HashMap();				
			//setData.put("ModelID", selectString("model_SQL.getMaxModelIDString", map));
			setModelMap.put("ItemID", s_itemID);
			setModelMap.put("ModelID", modelID);
			setModelMap.put("LanguageID",dfLanguageID);	
			setModelMap.put("Width", mdlWidth);
			setModelMap.put("Height", mdlHeight);	
			setModelMap.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
			setModelMap.put("Dx", mdlDx);
			setModelMap.put("Dy", mdlDy);
			setModelMap.put("Zoom", StringUtil.checkNull(cmmMap.get("viewScale")));
			
			setModelMap.put("Status", modelStatus[2]);			
			commonService.update("model_SQL.updateModelDefInfo", setModelMap);
		
			if(StringUtil.checkNull(modelMap.get("ModelIDTXT")).equals("")){
				commonService.insert("model_SQL.insertModelXML", setData);}
			else{commonService.update("model_SQL.updateModelXML", setData);}		
			
			String dfCnnItemClassCode = "NL00000";
			String dfCnnItemTypeCode = "MCN0001";	
			String dfItemClassCode = "NL00000";
			String dfItemTypeCode = "CN00101";				
			String elm_link = "";
			int elm_height=0;
			int elm_max_height=0;
			
			String elm_positionXS = "";
			String elm_positionYS = "";
			String elm_widthS;
			String elm_heightS;
			String dfFontFamily = StringUtil.checkNull(cmmMap.get("sessionDefFont"),"");	
			String symTypeCode = "";
			String attrType="";
			String elm_edge="";
			
			int displaySeq = 0;
			//3.Item, Element 생성/수정, Connetion정보 확인	
			//3.1 기존 Element정보  Delete Mark 
			commonService.update("model_SQL.updateDeletedFieldOfElement", setData);
			HashMap<String, String> mapBtwElementIDAndSymtype = new HashMap<String, String>();		
			for(int i = 2; i < diagramObjs.size(); i++) {
				Map insertData = new HashMap();			
				HashMap<String, String> currElement = diagramObjs.get(i);	
				attrType = StringUtil.checkNull(currElement.get("attrType"),"");	
				symTypeCode = StringUtil.checkNull(currElement.get("symTypeCode"),"");	// SB00018 default 커넥션 
				clientID = StringUtil.checkNull(currElement.get("clientID"),"");
				
				if(attrType.equals("Identifier")){}//read next element if 'attrType' element is exists			
				else if(symTypeCode.equals("defImg")){}//20150325 bshyun :: AT000037인 경우는 이미지를 강제로 할당한 것으로 Element저장 pass
				else{
					if(!clientID.equals("") && !clientID.equals(defClientID)) { // 다른server의 diagram복사시 link 새로 생성
						elm_link = "";
						insertData.put("refClientID", clientID);
						insertData.put("refCItemID", StringUtil.checkNull(currElement.get("link"),""));
						refCModelID = StringUtil.checkNull(currElement.get("modelID"),"");			
						if(!refCModelID.equals("")) { 
							setData = new HashMap();
							setData.put("ModelID", modelID);	
							setData.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
							setData.put("refCModelID", refCModelID); 
							commonService.update("model_SQL.updateModelDefInfo", setData); refCModelID = ""; 
						}
					}else {
						if(!wNewItem.equals("Y")){ 
							elm_link = StringUtil.checkNull(currElement.get("link"),"");	
						}else{	elm_link = "";	}// Release with new items 
					}
					
					elm_height = NumberUtil.getIntValue(currElement.get("height"),0);
					if(elm_height>elm_max_height){elm_max_height=elm_height;};
					
					elm_widthS = StringUtil.checkNull(currElement.get("width"),"");				
					if(NumberUtil.isNumeric(elm_widthS)){ elm_widthS = elm_widthS+".00";}
					
					elm_heightS = StringUtil.checkNull(currElement.get("height"),"");	
					if(NumberUtil.isNumeric(elm_heightS)){ elm_heightS = elm_heightS+".00";}
					
					elm_edge = StringUtil.checkNull(currElement.get("edge"),"");
					if(elm_edge.equals("1")){ 
						symTypeCode = "SB00000";					
					}
					
					elm_positionXS = StringUtil.checkNull(currElement.get("x"),"");
					if(NumberUtil.isNumeric(elm_positionXS)){ elm_positionXS = elm_positionXS+".00";}
					elm_positionYS = StringUtil.checkNull(currElement.get("y"),"");
					if(NumberUtil.isNumeric(elm_positionYS)){ elm_positionYS = elm_positionYS+".00";}
					
					insertData.put("ModelID", modelID);				
					insertData.put("ElementID", StringUtil.checkNull(currElement.get("objID"),""));
					insertData.put("FromID", StringUtil.checkNull(currElement.get("source"),""));
					insertData.put("Parent", StringUtil.checkNull(currElement.get("parent"),""));
					insertData.put("ToID", StringUtil.checkNull(currElement.get("target"),""));
					insertData.put("PositionX", StringUtil.checkNull(elm_positionXS,"0"));
					insertData.put("PositionY", StringUtil.checkNull(elm_positionYS,"0"));
					insertData.put("Height", elm_heightS); 
					insertData.put("Width", StringUtil.checkNull(elm_widthS,"0"));
										
					insertData.put("Style", StringUtil.checkNull(currElement.get("style"),""));	
					insertData.put("StrokeWidth", NumberUtil.getIntValue(currElement.get("strokeWidth"),0));
					insertData.put("GradientColor", StringUtil.checkNull(currElement.get("gradientColor"),""));
					insertData.put("FillColor", StringUtil.checkNull(currElement.get("fillColor"),""));
					insertData.put("StartArrow", StringUtil.checkNull(currElement.get("startArrow"),""));
					insertData.put("EndArrow", StringUtil.checkNull(currElement.get("endArrow"),""));
					insertData.put("EdgeStyle", StringUtil.checkNull(currElement.get("edgeStyle"),""));
					insertData.put("ExitX", StringUtil.checkNull(currElement.get("exitX"),null));
					insertData.put("ExitY", StringUtil.checkNull(currElement.get("exitY"),null));
					insertData.put("ExitPerimeter", StringUtil.checkNull(currElement.get("exitPerimeter"),null));
					insertData.put("EntryX", StringUtil.checkNull(currElement.get("entryX"),null));
					insertData.put("EntryY", StringUtil.checkNull(currElement.get("entryY"),null));
					insertData.put("EntryPerimeter", StringUtil.checkNull(currElement.get("entryPerimeter"),null));
					insertData.put("FontFamily", StringUtil.checkNull(currElement.get("fontFamily"),dfFontFamily));
					insertData.put("FontStyle", StringUtil.checkNull(currElement.get("fontStyle"),""));
					insertData.put("FontColor", StringUtil.checkNull(currElement.get("fontColor"),""));
					insertData.put("FontSize", StringUtil.checkNull(currElement.get("fontSize"),""));
					insertData.put("HTML", StringUtil.checkNull(currElement.get("html"),""));
					insertData.put("Link", elm_link);
					insertData.put("Path",StringUtil.checkNull(currElement.get("edgePath"),""));
					insertData.put("PlainText", replaceTag(StringUtil.checkNull(currElement.get("label"),"")));
					insertData.put("Relative", StringUtil.checkNull(currElement.get("relative"),""));	
					insertData.put("languageID", dfLanguageID);			
					insertData.put("AttrTypeCode", "AT00001");	
					insertData.put("Rotation", StringUtil.checkNull(currElement.get("rotation"),""));
					insertData.put("GradientDirection", StringUtil.checkNull(currElement.get("gradientDirection"),""));
					insertData.put("LabelBackgroundColor", StringUtil.checkNull(currElement.get("labelBackgroundColor"),""));
					insertData.put("Opacity", StringUtil.checkNull(currElement.get("opacity"),""));
					insertData.put("Shadow", StringUtil.checkNull(currElement.get("shadow"),""));
					insertData.put("Dashed", StringUtil.checkNull(currElement.get("dashed"),""));
					insertData.put("Relative", StringUtil.checkNull(currElement.get("relative"),"")); 
					insertData.put("StrokeColor", StringUtil.checkNull(currElement.get("strokeColor"),""));
					insertData.put("Rounded", StringUtil.checkNull(currElement.get("rounded"),""));
					insertData.put("LabelBorderColor", StringUtil.checkNull(currElement.get("labelBorderColor"),""));
					insertData.put("SpacingTop", StringUtil.checkNull(currElement.get("spacingTop"),""));
					insertData.put("StartFill", StringUtil.checkNull(currElement.get("startFill"),""));
					insertData.put("EndFill", StringUtil.checkNull(currElement.get("endFill"),""));
					insertData.put("StartSize", StringUtil.checkNull(currElement.get("startSize"),""));
					insertData.put("EndSize", StringUtil.checkNull(currElement.get("endSize"),""));
					insertData.put("SymTypeCode", symTypeCode);
					insertData.put("DefSymCode", "");
					insertData.put("Deleted", "0");			
					insertData.put("displaySeq", displaySeq); displaySeq++;
					if(!StringUtil.checkNull(currElement.get("link"),"").equals("")){
						insertData.put("ItemID",StringUtil.checkNull(currElement.get("link"),""));
					}
					
					//Symbol정보 조회 및 Setting
					Map symType = new HashMap();					
					Map getDataForSymbol = new HashMap();
					if(currElement.get("style") != null){
						if(currElement.get("style").equals("text")) { symTypeCode = "SB00013"; } // text copy
						getDataForSymbol.put("ImagePath", StringUtil.checkNull(currElement.get("style"),"")); 
						getDataForSymbol.put("SymTypeCode", symTypeCode);
						/*//bshyun 20141015 Connection style은 DB로 처리
						if(currElement.get("style").toString().equals("edgeStyle=orthogonalEdgeStyle")){getDataForSymbol.put("ImagePath", "NONE");
						}else{ 
							//getDataForSymbol.put("ImagePath", currElement.get("style")); // hyoh20140912 whiteSpace=wrap;dashed=1 입력시 SB00005인데, SB00007로insert되는 문제때문...
							getDataForSymbol.put("ImagePath", Style); // SB00005이면 Style = StringUtil.checkNull(currElement.get("style"),"")+"dashed=1";
						}*/	
					}else if(currElement.get("edge").toString().equals("1")){getDataForSymbol.put("ImagePath", "edgeStyle=none"); getDataForSymbol.put("SymTypeCode", symTypeCode);
					}else{getDataForSymbol.put("ImagePath", "NONE"); getDataForSymbol.put("SymTypeCode", symTypeCode); }				
					//symType = select("model_SQL.getSymTypeWithImagePath", getDataForSymbol);
					symType = commonService.select("model_SQL.getSymTypeWithSymCode", getDataForSymbol);
					String defLovCode = "";
					if(symType != null && symType.size() != 0) {
						if(symType.get("ItemTypeCode") != null){insertData.put("ItemTypeCode", symType.get("ItemTypeCode"));}
						if(symType.get("SymTypeCode") != null){insertData.put("SymTypeCode", symType.get("SymTypeCode"));insertData.put("DefSymCode", symType.get("SymTypeCode"));}
						if(symType.get("ClassCode") != null){insertData.put("ClassCode", symType.get("ClassCode"));}
						if(symType.get("ItemCategory") != null){insertData.put("CategoryCode", symType.get("ItemCategory"));}
						if(!elm_edge.equals("1"))if(symType.get("ImagePath") != null){insertData.put("Style", symType.get("ImagePath"));}
						//insertData.put("CategoryCode", "ST1");				
						mapBtwElementIDAndSymtype.put(insertData.get("ElementID").toString(), symType.get("SymTypeCode").toString());
						defLovCode = StringUtil.checkNull(symType.get("DefLovCode"));
					}
					else {
						insertData.put("ItemTypeCode", dfItemTypeCode);
						insertData.put("ClassCode", dfItemClassCode);
						insertData.put("CategoryCode", "");
						//insertData.put("CategoryCode", dfCnnItemCategory);
						mapBtwElementIDAndSymtype.put(insertData.get("ElementID").toString(), "");
					}
					/*
					if(insertData.get("CategoryCode").equals("MCN")) {
						insertData.put("PositionX","");
						insertData.put("PositionY","");
					}*/
					
					//3.2 isElementExist
					List returnData = new ArrayList();
					Map getData = new HashMap();				
					getData.put("ModelID", modelID);			
					getData.put("ElementID", StringUtil.checkNull(currElement.get("objID"),"-1"));
					getData.put("languageID", dfLanguageID);			
					
					returnData = commonService.selectList("model_SQL.isElementExist", getData); 
					
					if(currElement.get("objtype").toString().equals("edge")) { // cxn -> fromID, toID 조건 추가 하여 조회 없으면 link null setting 하여 New Cxn Item 생성
						getData.put("fromID", StringUtil.checkNull(currElement.get("source"),""));
						getData.put("toID", StringUtil.checkNull(currElement.get("target")));						
						List cxnElementList = commonService.selectList("model_SQL.isElementExist", getData); 
						getData.remove("fromID"); getData.remove("toID");						
						if(cxnElementList.size()==0) {							
							elm_link = "";
							insertData.put("Link", elm_link);
							insertData.put("ItemID", elm_link);
						}						
					}
					
					insertData.put("AuthorID", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));
					insertData.put("ItemStatus", "NEW1");
					insertData.put("Creator", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));
					insertData.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));
					String maxItemID = "";
					Map setMap = new HashMap();
					setMap.put("itemID", s_itemID);
					String curChangeSet = StringUtil.checkNull(commonService.selectString("project_SQL.getCurChangeSetIDFromItem", setMap));
					//3.2.1 엘레멘트가 없으면 insert
					String ownerTeamID = StringUtil.checkNull(cmmMap.get("sessionTeamId"),""); //20150629 item생성시 User의 TeamID,CompanyID 입력 
										
					if(returnData==null || returnData.size() == 0 ) { // 엘레멘트가 없으면 insert	
						if(elm_link.equals("")){ // link id 가 없으면 아이템 생성(20141003 생성한아이템을 엘레멘트에 objectId,linkId에 넣기 위해 먼저 생성 )
							//bshyun 20141015 symbolType에 없는 경우는 Item을 생성하지 않게 IF문 추가
							if(symType != null && symType.size() != 0) {
								//3.2.1.1---Insert New Item				
								maxItemID = StringUtil.checkNull(commonService.selectString("model_SQL.getMaxItemIDNew", getData),"1");					
								insertData.put("ItemID", maxItemID);
								insertData.put("Link", maxItemID);
								insertData.put("FromItemID", "");
								insertData.put("ToItemID", "");	
								insertData.put("projectID", itemPjtID);
								insertData.put("ownerTeamID", ownerTeamID); // 20150629 item생성시 User의 TeamID,CompanyID 입력 
								insertData.put("curChangeSet", curChangeSet);
								
								/** Auto identifer 생성  */
								String classCode = StringUtil.checkNull(insertData.get("ClassCode"));
								Map setClassData = new HashMap();
								setClassData.put("itemClassCode", classCode);
								Map itemClassInfo = commonService.select("item_SQL.getClassOption", setClassData);
								String autoID = StringUtil.checkNull(itemClassInfo.get("AutoID"));
								String preFix = StringUtil.checkNull(itemClassInfo.get("PreFix"));
								String identifier = "";
								String idLength = "";
								if(autoID.equals("Y")){
									setClassData.put("preFix", preFix);
									identifier = StringUtil.checkNull(commonService.selectString("item_SQL.getMaxPreFixIdentifier", setClassData));
									for(int k=0; 5-identifier.length() > k; k++){
										idLength = idLength + "0";
									}
									if(identifier.equals("")){
										identifier = preFix + "00001";
									}else{
										identifier = preFix + idLength + identifier;
									}
									
									insertData.put("identifier", identifier);
								}
								/*********************/								
								commonService.insert("model_SQL.insertItemNew", insertData);	// item insert
								
								//3.2.1.2 LinkID가 없으면 insertItemAttr : Multi Lang
								List getLanguageList = commonService.selectList("common_SQL.langType_commonSelect",cmmMap);			
								for(int j = 0; j < getLanguageList.size(); j++){
									Map getMap = (HashMap)getLanguageList.get(j);
									insertData.put("languageID", getMap.get("CODE") );				
									commonService.insert("model_SQL.insertItemAttr", insertData);					
								}
								// DefLovCode : Attr Recode 생성 : 새 Element 
								if(!defLovCode.equals("")){
									String plainText = StringUtil.checkNull(insertData.get("PlainText"));
									setData.put("lovCode", defLovCode);
									setData.put("languageID", StringUtil.checkNull(cmmMap.get("sessionDefLanguageId")));
									String attrTypeCode = StringUtil.checkNull(commonService.selectString("attr_SQL.getAttrTypeCode",setData),"");
									insertData.put("languageID", StringUtil.checkNull(cmmMap.get("sessionDefLanguageId")));	
									insertData.put("AttrTypeCode", attrTypeCode);
									insertData.put("LovCode", defLovCode);
									insertData.put("PlainText", defLovCode);
									commonService.insert("model_SQL.insertItemAttr", insertData);					
									insertData.put("AttrTypeCode", "AT00001");
									insertData.put("PlainText", plainText);
								}
							}
						}
						
						//3.2.1.3 Insert New Element	
						commonService.insert("model_SQL.insertElement", insertData);
						
						//3.2.1.4 Symbol Type에 따른  connection Item 생성 
						Map symAlloc = new HashMap();					
						Map getDataForSymAlloc = new HashMap();		
						getDataForSymAlloc.put("modelTypeCode", StringUtil.checkNull(modelMap.get("ModelTypeCode"),""));
						
						if(symType != null && symType.size() != 0){getDataForSymAlloc.put("SymTypeCode", symType.get("SymTypeCode"));}	
						
						getDataForSymAlloc.put("cxnCategoryCode", "Y");// ItemCategoryCode = CXN 인것만 조회 
						symAlloc = commonService.select("model_SQL.getSymAlloc", getDataForSymAlloc);
						String itemCategoryCode = StringUtil.checkNull(symAlloc.get("ItemCategoryCode"));
						Map insertDataFromModelToElement = new HashMap();				
						if(symAlloc !=null && symAlloc.size() != 0 && !itemCategoryCode.equals("") && itemCategoryCode != null) {						
							insertDataFromModelToElement.put("CategoryCode", symAlloc.get("ItemCategoryCode"));
							insertDataFromModelToElement.put("ItemTypeCode", symAlloc.get("ItemTypeCode"));
							insertDataFromModelToElement.put("ClassCode", symAlloc.get("ClassCode"));	
							insertDataFromModelToElement.put("ItemStatus", "NEW1");	
							
							insertDataFromModelToElement.put("FromItemID", maxItemID);
							insertDataFromModelToElement.put("ToItemID", maxItemID);
							
							maxItemID = "";			
							if(!elm_link.equals("")){ // link id 가 있으면 ObjecID 는 linkID로 insert
								insertDataFromModelToElement.put("ItemID", insertData.get("Link")); 				
							}else{		
								maxItemID = StringUtil.checkNull(commonService.selectString("model_SQL.getMaxItemIDNew", getData),"1");
								insertDataFromModelToElement.put("ItemID", maxItemID); 
								insertDataFromModelToElement.put("FromItemID", modelMap.get("ItemID"));		
								insertDataFromModelToElement.put("ownerTeamID", ownerTeamID); // 20150629 item생성시 User의 TeamID,CompanyID 입력 
								insertDataFromModelToElement.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),"")); 
								commonService.insert("model_SQL.insertItemNew", insertDataFromModelToElement);// 20140902 ST1 트리에 보이게						
							}
						} 								
				
						//3.2.1.5  connection 데이터 추가
						if(currElement.get("objtype").toString().equals("edge")) {newConnections.add((HashMap)insertData);}
					}
					//3.2.2 엘레멘트가 있으면 update
					else if(returnData.size() != 0 ) { 
						//3.2.2.1 hyoh 20140902 링크삭제시 아이템생성
						if(elm_link.equals("")){
							maxItemID = StringUtil.checkNull(commonService.selectString("model_SQL.getMaxItemIDNew", getData),"1");					
							insertData.put("ItemID", maxItemID);
							insertData.put("Link", maxItemID);
							insertData.put("FromItemID", "");	
							insertData.put("ToItemID", "");	
							insertData.put("projectID", itemPjtID);
							insertData.put("ownerTeamID", ownerTeamID); // 20150629 item생성시 User의 TeamID,CompanyID 입력 
							insertData.put("curChangeSet", curChangeSet);
							
							/** Auto identifer 생성  */
							String classCode = StringUtil.checkNull(insertData.get("ClassCode"));
							Map setClassData = new HashMap();
							setClassData.put("itemClassCode", classCode);
							Map itemClassInfo = commonService.select("item_SQL.getClassOption", setClassData);
							String autoID = StringUtil.checkNull(itemClassInfo.get("AutoID"));
							String preFix = StringUtil.checkNull(itemClassInfo.get("PreFix"));
							String identifier = "";
							String idLength = "";
							if(autoID.equals("Y")){
								setClassData.put("preFix", preFix);
								identifier = StringUtil.checkNull(commonService.selectString("item_SQL.getMaxPreFixIdentifier", setClassData));
								for(int k=0; 5-identifier.length() > k; k++){
									idLength = idLength + "0";
								}
								if(identifier.equals("")){
									identifier = preFix + "00001";
								}else{
									identifier = preFix + idLength + identifier;
								}
								
								insertData.put("identifier", identifier);
							}
							/*********************/
							commonService.insert("model_SQL.insertItemNew", insertData);	// item insert
					
							List getLanguageList = commonService.selectList("common_SQL.langType_commonSelect",cmmMap);			
							for(int j = 0; j < getLanguageList.size(); j++){
								Map getMap = (HashMap)getLanguageList.get(j);
								insertData.put("languageID", getMap.get("CODE") );				
								commonService.insert("model_SQL.insertItemAttr", insertData);					
							}
							elm_link = maxItemID;
						}else{ // Element 업데이시 TB_ITEM에 DefSymCode update hyoh20141024 ADD 
							//System.out.println("LinkID :"+elm_link);
							//System.out.println("updateDefSymCode itemId  :"+insertData.get("ItemID"));
							//System.out.println("updateDefSymCode :"+insertData.get("DefSymCode").toString());
							//update("model_SQL.updateItemFromToID", insertData); // update 시 아이템은 변경 안되게 hyoh 20141215
								
						}
						String sessionMlvl =  StringUtil.checkNull(cmmMap.get("sessionMlvl"),"");	
						String itemAuthID = "";
						String itemBlocked = "";
						Map itemInfo = new HashMap();
						//3.2.2.2 엘레멘트가 있으면 update
						HashMap elementAttr = (HashMap)returnData.get(0);	
						
						insertData.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
						
						insertData.put("s_itemID", elementAttr.get("Link"));
						String linkReleaseNo = StringUtil.checkNull(commonService.selectString("item_SQL.getItemReleaseNo", insertData));
						insertData.put("releaseNo", linkReleaseNo);
						
						if(elm_link.equals("")){//기존링크가 있으면 
							getData.put("ItemID", elementAttr.get("ObjectID"));					
							insertData.put("ElementID", elementAttr.get("ElementID"));
							insertData.put("ItemID", elementAttr.get("ObjectID"));							
							commonService.update("model_SQL.updateElement", insertData);
						}else{
							getData.put("ItemID", insertData.get("Link"));					
							insertData.put("ElementID", elementAttr.get("ElementID"));
							//insertData.put("ItemID", elementAttr.get("ObjectID"));
						}	
						commonService.update("model_SQL.updateElement", insertData);
						
						
						//3.2.2.3 Insert/Update Item Attr	
						itemInfo.put("itemId", getData.get("ItemID"));
						itemInfo = commonService.select("fileMgt_SQL.selectItemAuthorID", itemInfo);	
						itemAuthID = StringUtil.checkNull(itemInfo.get("AuthorID"));
						itemBlocked = StringUtil.checkNull(itemInfo.get("Blocked"));
						
						getData.put("languageID", dfLanguageID);					
						Map elemetAttrForCurr = new HashMap();					
						elemetAttrForCurr = commonService.select("model_SQL.getItemAttr", getData);	
						
						String ctgryCode = insertData.get("CategoryCode").toString().trim();
						if(ctgryCode.equals("OJ")){
							if(itemBlocked.equals("0")){ // ElementID ObjectID Blocked == 0
								if(sessionMlvl.equals("SYS") || itemAuthID.equals(StringUtil.checkNull(cmmMap.get("sessionUserId"),""))){						
									if(elemetAttrForCurr != null) { 
										insertData.put("languageID", cmmMap.get("sessionCurrLangType"));
										commonService.update("model_SQL.updateItemAttr", insertData);								
									} else {
										insertData.put("languageID",dfLanguageID);
										commonService.insert("model_SQL.insertItemAttr", insertData);
									}
									insertData.put("projectID", itemPjtID);
									commonService.update("model_SQL.updateProjectID", insertData);
								}
							}
						}else{
							if(elemetAttrForCurr != null) { 
								insertData.put("languageID", cmmMap.get("sessionCurrLangType"));
								commonService.update("model_SQL.updateItemAttr", insertData);								
							} else {
								insertData.put("languageID",dfLanguageID);
								commonService.insert("model_SQL.insertItemAttr", insertData);
							}
							insertData.put("projectID", itemPjtID);
							commonService.update("model_SQL.updateProjectID", insertData);
						}
						
						//3.2.2.4 Symbol Type에 따른  connection Item 생성 
						Map symAlloc = new HashMap();					
						Map getDataForSymAlloc = new HashMap();	
						getDataForSymAlloc.put("modelTypeCode", StringUtil.checkNull(modelMap.get("ModelTypeCode"),""));
						if(symType != null && symType.size() != 0){getDataForSymAlloc.put("SymTypeCode", symType.get("SymTypeCode"));}				
						
						getDataForSymAlloc.put("cxnCategoryCode", "Y");// ItemCategoryCode = CXN 인것만 조회 
						symAlloc = commonService.select("model_SQL.getSymAlloc", getDataForSymAlloc);
						String itemCategoryCode = StringUtil.checkNull(symAlloc.get("ItemCategoryCode"));
						
						Map insertDataFromModelToElement = new HashMap();				
						if(symAlloc !=null && symAlloc.size() != 0 && !itemCategoryCode.equals("") && itemCategoryCode != null) {
							insertDataFromModelToElement.put("CategoryCode", symAlloc.get("ItemCategoryCode"));
							insertDataFromModelToElement.put("ItemTypeCode", symAlloc.get("ItemTypeCode"));
							insertDataFromModelToElement.put("ClassCode", symAlloc.get("ClassCode"));	
							insertDataFromModelToElement.put("ItemStatus", "NEW1");	
							
							insertDataFromModelToElement.put("FromItemID", maxItemID);
							
							insertDataFromModelToElement.put("ToItemID", maxItemID);
							if(maxItemID.equals("")){ // maxItemID가 없을경우 Link
								insertDataFromModelToElement.put("ToItemID", insertData.get("Link"));
							}
							
							maxItemID = "";							
							if(!elm_link.equals("")){ // link id 가 있으면 ObjecID 는 linkID로 insert
								// update 시 st1 트리에 보이게 
								maxItemID = StringUtil.checkNull(commonService.selectString("model_SQL.getMaxItemIDNew", getData),"1");
								insertDataFromModelToElement.put("ItemID", maxItemID); 
								insertDataFromModelToElement.put("FromItemID", modelMap.get("ItemID"));		
								
							}else{		
								maxItemID = StringUtil.checkNull(commonService.selectString("model_SQL.getMaxItemIDNew", getData),"1");
								//insertDataFromModelToElement.put("ItemID", maxItemID); 
								insertDataFromModelToElement.put("FromItemID", modelMap.get("ItemID"));		
							}
							
							//Check Item
							if(!StringUtil.checkNull(modelMap.get("ItemID")).equals("")){
								Map itemMap = new HashMap();
								insertDataFromModelToElement.put("elm_link", elm_link);
								insertDataFromModelToElement.put("ownerTeamID", ownerTeamID); 
								itemMap = commonService.select("model_SQL.getItemInfoByST1Item", insertDataFromModelToElement); // CXN 유무확인 							
								insertDataFromModelToElement.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
								
								if((itemMap==null || itemMap.size()==0)){  // ST1없으면 생성
									if(symAlloc !=null && symAlloc.size() != 0) {
										commonService.insert("model_SQL.insertItemNew", insertDataFromModelToElement);}
								}						
							}					
						} 					
						//3.2.2.4  connection 데이터 추가
						if(currElement.get("objtype").toString().equals("edge")){newConnections.add((HashMap)insertData);}
					}				
					_log.info(i+". insertData:::"+insertData);
				}
			}
			
			//4.swimlane에 대해서 max height를 조절
			/*
			for(int i = 2; i < diagramObjs.size(); i++) {
				Map updateData = new HashMap();			
				HashMap<String, String> currElement = diagramObjs.get(i);	
				
				updateData.put("ModelID", modelID);				
				updateData.put("ElementID", StringUtil.checkNull(currElement.get("objID"),""));
				updateData.put("Height", elm_max_height);
				if(StringUtil.checkNull(currElement.get("style"),"").indexOf("swimlane") >0){
					update("model_SQL.updateElement", updateData);		
				}
			}	
			*/	
		
			//5.Connection정보 생성
			/*********
			//ConnetionItem category:MCN, ItemType:CN00101, From/To:X, CalssCode:NL00000
			//ITEM_ATTR : AT00001 - Y/N
			/*********/				
			for(int i = 0; i < newConnections.size(); i++) {
				HashMap currNewConnection = newConnections.get(i);
				
				String FromID = StringUtil.checkNull(currNewConnection.get("FromID"),"");
				String ToID = StringUtil.checkNull(currNewConnection.get("ToID"),"");
				
				if(!FromID.equals("") || !ToID.equals("")){ // MCN FromItemID OR ToID 하나만 있어도 update 되도록 수정 				
					Map updateSourceAndTargetInItem = new HashMap();				
					updateSourceAndTargetInItem.put("ItemID", currNewConnection.get("ItemID"));
					
					List returnData = new ArrayList();
					Map getData = new HashMap();				
					getData.put("ModelID", modelID);
					getData.put("ElementID", StringUtil.checkNull(currNewConnection.get("FromID"),""));				
					
					// FromID = source, ToID = target
					Map itemIDFromElementID = new HashMap();
				
					itemIDFromElementID = commonService.select("model_SQL.getItemIDFromElementID", getData);
					if(itemIDFromElementID != null){
						updateSourceAndTargetInItem.put("FromItemID", itemIDFromElementID.get("ObjectID"));
					}
					getData.remove("ElementID");
					getData.put("ElementID", StringUtil.checkNull(currNewConnection.get("ToID"),"-1"));
					
					itemIDFromElementID = new HashMap();				
					itemIDFromElementID = commonService.select("model_SQL.getItemIDFromElementID", getData);
					if(itemIDFromElementID != null){
						updateSourceAndTargetInItem.put("ToItemID", itemIDFromElementID.get("ObjectID"));
					}
					String FromSymType = "";
					String ToSymType = "";
					
					Map getDataForSymbol = new HashMap();
					Map symType = new HashMap();
					if(mapBtwElementIDAndSymtype.get(currNewConnection.get("FromID").toString()) != null) {
						FromSymType = StringUtil.checkNull(mapBtwElementIDAndSymtype.get(StringUtil.checkNull(currNewConnection.get("FromID"))));
					}				
					if(mapBtwElementIDAndSymtype.get(currNewConnection.get("ToID").toString()) != null) {
						ToSymType = StringUtil.checkNull(mapBtwElementIDAndSymtype.get(StringUtil.checkNull(currNewConnection.get("ToID"))));
					}				
					getDataForSymbol.put("FromSymType", FromSymType);
					getDataForSymbol.put("ToSymType", ToSymType);				
					
					symType = commonService.select("model_SQL.getSymTypeWithFromToSymtype", getDataForSymbol);
					if(symType != null && !symType.isEmpty()) {
						symType.put("CategoryCode", dfCnnItemCategory);
						symType.put("ModelID", modelID);
						symType.put("ElementID", currNewConnection.get("ElementID"));	
						symType.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));		
						commonService.update("model_SQL.updateElementCodes", symType);										
					}
					else {
						getDataForSymbol.remove("FromSymType");
						getDataForSymbol.remove("ToSymType");					
						getDataForSymbol.put("ToSymType", FromSymType);
						getDataForSymbol.put("FromSymType", ToSymType);
						
						symType = commonService.select("model_SQL.getSymTypeWithFromToSymtype", getDataForSymbol);
						if(symType != null && !symType.isEmpty()) {
							symType.put("CategoryCode", dfCnnItemCategory);
							symType.put("ModelID", modelID);
							symType.put("ElementID", currNewConnection.get("ElementID"));	
							symType.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));	
							commonService.update("model_SQL.updateElementCodes", symType);
						}
						else {
							symType = new HashMap();
							symType.put("ItemTypeCode", dfCnnItemTypeCode);
							symType.put("ClassCode", dfCnnItemClassCode);
						}					
					}
					updateSourceAndTargetInItem.put("CategoryCode", dfCnnItemCategory);
					updateSourceAndTargetInItem.put("ItemTypeCode", symType.get("ItemTypeCode"));
					updateSourceAndTargetInItem.put("ClassCode", symType.get("ClassCode"));
					updateSourceAndTargetInItem.put("LastUser", StringUtil.checkNull(cmmMap.get("sessionUserId"),""));
					commonService.update("model_SQL.updateItemFromToID", updateSourceAndTargetInItem); // Update CXN ITEM FromItemID, ToItemID
					
					////connection object 중복 제거 //////////////////////////////////////////////////////
					setData = new HashMap();
					setData.put("fromID", FromID);
					setData.put("toID", ToID);					
					setData.put("modelID", modelID);
										
					setData.put("fromObjectID", updateSourceAndTargetInItem.get("FromItemID"));
					setData.put("toObjectID",  updateSourceAndTargetInItem.get("ToItemID"));
					List connObjectIDs = commonService.selectList("model_SQL.getObjectConnIDs", setData);
					if(connObjectIDs.size() > 1){
						Map connObjectIDFirstData = (Map)connObjectIDs.get(0);
						// update TB_ELEMENT 기존  CXN ItemID 
						setData.put("elementID", StringUtil.checkNull(currNewConnection.get("ElementID"),""));
						setData.put("objectID", StringUtil.checkNull(connObjectIDFirstData.get("ItemID")));
						
						String link = StringUtil.checkNull(commonService.selectString("model_SQL.getElementLink", setData));					
						if(!link.equals("")){ setData.put("link", connObjectIDFirstData.get("ItemID")); }
						
						commonService.update("model_SQL.updateElementObject",setData); // Connection Element 에  objecID, link update 
						
						for(int idx = 0; idx < connObjectIDs.size(); idx++){
							Map cxnInfo = (Map)connObjectIDs.get(idx);
							
							String duplCXNID  = StringUtil.checkNull(cxnInfo.get("ItemID"));
							String RNUM = StringUtil.checkNull(cxnInfo.get("RNUM"));
							if(!RNUM.equals("1")){
								setData.put("ToItemID", duplCXNID); // 중복된 connection Item delete
								setData.put("itemID", duplCXNID); 
								
								String elementCNT = StringUtil.checkNull(commonService.selectString("model_SQL.getElementCNTFromObjectID", setData));						
								if(Integer.parseInt(elementCNT) == 0){
									commonService.delete("item_SQL.deleteItemAttr", setData);
									commonService.delete("item_SQL.processItemDelete", setData);
								}
							}
						}
					}
					///////////////////////////////////////////////////////////////////////////////////
				}
			}
					
			//6.Element 관련 정보 삭제
			List returnData = new ArrayList();
			Map getData = new HashMap();
			getData.put("ModelID", modelID);
			commonService.delete("model_SQL.deleteElementToDeleteWithModelID", getData);
		}catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.toString()); throw new Exception(fnfe.toString());
		} catch (IOException ioe) {
			System.out.println(ioe.toString()); throw new Exception(ioe.toString());
		} catch (Exception e) {
			System.out.println(e.toString()); throw new Exception(e.toString());
		}
	}
	
	private String replaceTag(String str) {
		str = str.replaceAll("'","′"); // 작은 따옴표를 ′ 로 치환
		//str = str.replaceAll("&quot;", "\"");	
		return str;
	}	
	
	public void createE2eTreeModel(Map cmmMap) throws Exception{	
		String ItemID = StringUtil.checkNull(cmmMap.get("ItemID"));
		String e2eItemIDS = StringUtil.checkNull(cmmMap.get("e2eItemIDS"));
			try {
			// 모델 생성
			Map setData = new HashMap();
			String newModelID = commonService.selectString("model_SQL.getMaxModelIDString", cmmMap).toString();
			
			setData.put("ModelID", newModelID);
			setData.put("ItemID", ItemID);
			setData.put("ModelTypeCode", "MT003");
			setData.put("MTCategory", "BAS");
			setData.put("Creator", cmmMap.get("sessionUserId"));
			setData.put("Deleted", "0");
			setData.put("Status", "REL");
			setData.put("Blocked", "0");
			commonService.insert("model_SQL.insertModelXML", setData);
			
			String[] e2eItemIDSArray = e2eItemIDS.split(",");
			for (int i = 0; e2eItemIDSArray.length > i;i++ ) {
				
			}
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }
	}

	
	public void createARISModel(Map cmmMap) throws Exception{
		XMLParserARIS xmlparser = new XMLParserARIS(StringUtil.checkNull(cmmMap.get("XML")));
		xmlparser.parseXML();
		try {
			Map modelInfo = new HashMap();
			modelInfo = xmlparser.getModelInfo();
			String modelName = StringUtil.checkNull(modelInfo.get("Name"),"ARIS Model");
			String oldModelID= StringUtil.checkNull(modelInfo.get("ID"));
			cmmMap.put("maxY", StringUtil.checkNull(modelInfo.get("maxY"),"0"));
			cmmMap.put("maxX", StringUtil.checkNull(modelInfo.get("maxX"),"0"));
			
			Map setMap = new HashMap();
	    	//0.Item 조회, 없으면 넘겨주는 값
			setMap.put("s_itemID",oldModelID);
			String ItemID=StringUtil.checkNull(commonService.selectString("model_SQL.getItemIDFromModelID", setMap));
			if(ItemID.equals("")){ItemID=StringUtil.checkNull(cmmMap.get("itemID"),StringUtil.checkNull(cmmMap.get("ItemID")));}
	    	//ItemID = "101493";//StringUtil.checkNull(cmmMap.get("itemID"));
	    	cmmMap.put("itemID", ItemID);
	    	
	    	//1.Create Model
			String modelID=commonService.selectString("model_SQL.getMaxModelIDString", setMap);
			String editModelID = modelID;
			setMap.put("ModelID",modelID);
	    	cmmMap.put("ModelID",modelID);
	    	
			setMap.put("Deleted", "0");
			setMap.put("Creator", cmmMap.get("sessionUserId"));
			setMap.put("ModelTypeCode", "MT001");
			setMap.put("MTCategory", "BAS");
			//setMap.put("Identifier", request.getParameter("newIdentifier"));
			setMap.put("Name", modelName);
			setMap.put("ItemID", ItemID);	
			setMap.put("Blocked", "0");
			setMap.put("GUBUN","insert");
			setMap.put("Status", "NEW1");
			setMap.put("Description", " ");
			commonService.insert("model_SQL.insertModel", setMap);				
			List getLanguageList = commonService.selectList("common_SQL.langType_commonSelect", setMap);			
			for(int i = 0; i < getLanguageList.size(); i++){
				Map getMap = (HashMap)getLanguageList.get(i);
				setMap.put("LanguageID", getMap.get("CODE") );				
				commonService.insert("model_SQL.insertModelAttr", setMap);
			}				
	        //2.Parsing ARIS XML
			ArrayList<ARISObjDef> diagramObjs = xmlparser.getARISObects();   	
			cmmMap.put("diagramObjs", diagramObjs);
			
			//3.Create mxGraphModel
			//3.1 Select mxGraph SymbolType
			List symTypeList = (List)commonService.selectList("model_SQL.getSymType", cmmMap);
	
			//3.2 Create XML
			cmmMap = xmlExportFromARIS.parsingXML(modelID, cmmMap, symTypeList);
			
			//commit시키기
			commitDiagram(cmmMap);
			
			// TB_ELEMENT의 [Parent],[PositionX],[PositionY]를 lane의 값을 기준으로 update
			groupElement(editModelID, StringUtil.checkNull(cmmMap.get("sessionUserId"),""));		
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }
	}
	
	/**
	 * TB_ELEMENT의 [Parent],[PositionX],[PositionY]를 lane의 값을 기준으로 update
	 * @param modelId
	 * @throws Exception 
	 */
	public void groupElement(String modelId, String sessionUserId) throws Exception {
		Map<String, String> setMap = new HashMap();
		Map<String, Object> updateMap = new HashMap();
		try {
			setMap.put("ModelID", modelId);
			// 해당 모델의 [SymTypeCode] = SB00001 인 Element 정보를 취득
			setMap.put("SymTypeCode", "SB00001");
			List laneElementList = commonService.selectList("report_SQL.getModelInfoWithSymTypeCode", setMap);
			
			// 해당 모델의 [SymTypeCode] != SB00001 and (CategoryCode= OJ or MOJ)인 Element 정보를 취득
			setMap.remove("SymTypeCode");
			setMap.put("NotInSymTypeCode", "SB00001");
			setMap.put("CategoryCode", "'OJ','MOJ'");
			List elementList = commonService.selectList("report_SQL.getModelInfoWithSymTypeCode", setMap);
			
			for (int i = 0; i < laneElementList.size(); i++) {
				Map laneElementMap = (Map) laneElementList.get(i);
				int parentPosX = Integer.parseInt(StringUtil.checkNull(laneElementMap.get("PositionX"), "0"));
				int parentPosY = Integer.parseInt(StringUtil.checkNull(laneElementMap.get("PositionY"), "0"));
				int parentWidth = Integer.parseInt(StringUtil.checkNull(laneElementMap.get("Width"), "0"));
				
				for (int j = 0; j < elementList.size(); j++) {
					Map elementMap = (Map) elementList.get(j);
					int myPosX = Integer.parseInt(StringUtil.checkNull(elementMap.get("PositionX"), "0"));
					int myPosY = Integer.parseInt(StringUtil.checkNull(elementMap.get("PositionY"), "0"));
					
					// lane 의 범위안에 해당 element의 X좌표가 들어가면, 해당 element의 Parent와 PosX, PosY를 upate 해줌
					if (parentPosX < myPosX && myPosX < (parentPosX + parentWidth)) {
						updateMap = new HashMap();
						updateMap.put("ModelID", modelId);
						updateMap.put("ElementID", elementMap.get("ElementID"));
						updateMap.put("Parent", laneElementMap.get("ElementID"));
						updateMap.put("PositionX", myPosX - parentPosX);
						updateMap.put("PositionY", myPosY - parentPosY);
						updateMap.put("LastUser", sessionUserId);
						commonService.update("report_SQL.updateElementInfo", updateMap);
					}
				}
				
			}
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }
	}
	
	public void insertBaseModel(Map cmmMap) throws Exception{	
		Map setMap = new HashMap();
		Map insertData = new HashMap();
		try {		
			commonService.update("model_SQL.updateDefSymCodeCl45", cmmMap);		
			List getItemList = commonService.selectList("model_SQL.getToItemListCl6", cmmMap);
			
			String plainText = null;
			String defSymCode = null;
			for(int i=0; i < getItemList.size(); i++){
				Map getItemMap = (Map) getItemList.get(i);
				setMap.put("ItemID", getItemMap.get("ItemID"));
				setMap.put("LanguageID", cmmMap.get("LanguageID"));
				plainText = commonService.selectString("model_SQL.getPlainText", setMap);
				
				if(plainText == null || plainText.equals("")){
					defSymCode = "SB00005";
				}else{
					defSymCode = "SB00007";
				}
				setMap.put("defSymCode", defSymCode);
				commonService.update("model_SQL.updateDefSymCodeCl6", setMap);
			}
			
			insertData.put("ItemID", cmmMap.get("ItemID"));
			insertData.put("LanguageID", cmmMap.get("LanguageID"));
			insertData.put("UserId", cmmMap.get("UserId")); 
			insertData.put("FontFamily", cmmMap.get("FontFamily")); 
			insertData.put("FontStyle", cmmMap.get("FontStyle")); 
			insertData.put("FontSize", cmmMap.get("FontSize")); 
			insertData.put("FontColor", cmmMap.get("FontColor")); 
			
			commonService.insert("model_SQL.createBaseModel", insertData);	
        } catch(Exception e) {
        	throw new Exception(e.toString());
        }	
	}
	
	
}
