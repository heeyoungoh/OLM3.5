package xbolt.cmm.framework.mdlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.service.dtoMdl.ARISObjDef;
import xbolt.cmm.service.dtoMdl.ARISCxnDef;
import xbolt.cmm.service.dtoMdl.ARISCxnOccPos;

public class XMLParserARIS {
	private final Log _log = LogFactory.getLog(this.getClass());

	private String m_xmlStr;
	private ArrayList<HashMap<String, String>> m_objs = new ArrayList<HashMap<String,String>>();	
	private ArrayList<ARISObjDef> m_defARIS = new ArrayList<ARISObjDef>();	
	private Map m_modelInfo;
	private String m_positionMaxX;
	private String m_positionMaxY;
	private double per_x=0.4;
	private double per_y=0.4;
	
	public XMLParserARIS(String xmlStr){
		m_positionMaxX="0";m_positionMaxY="0";
		m_modelInfo = new HashMap();
		int startIdx = xmlStr.indexOf("<Group");
		m_xmlStr = xmlStr.substring(startIdx);
		//System.out.println("xml:::"+m_xmlStr);
	}
	
	public void parseXML(){
		HashMap<String, String> currMXCell = new HashMap<String, String>();
		boolean	isObjDef = false;		
		
		//1.Read ObjDef
		String subStr = "ObjDef";
		int startIndex = m_xmlStr.indexOf("<"+subStr);		
		int endIndex = m_xmlStr.indexOf("</"+subStr, startIndex) + 1;		
		if(startIndex!=-1){
			do {
				ARISObjDef objDef = new ARISObjDef();		
				endIndex 		= m_xmlStr.indexOf("</"+subStr, startIndex) + 1;			
				String currLine = m_xmlStr.substring(startIndex, endIndex);			
					
				currMXCell = new HashMap<String, String>();
				m_objs.add(currMXCell);				
				parseObjectDef(currLine,currMXCell,objDef);
				if(objDef!=null && isCheckObjDef(objDef)){m_defARIS.add(objDef);}
				startIndex	= m_xmlStr.indexOf("<"+subStr, endIndex);
				
			} while(startIndex != -1);	
		}
		
		//2.MODEL
		int startIdx = m_xmlStr.indexOf("<Model");
		int endIdx = m_xmlStr.indexOf("</Model");
		m_xmlStr = m_xmlStr.substring(startIdx, endIdx);
		//System.out.print("Model XML:::"+m_xmlStr);
		
		//2.1 Read Lane		
		subStr = "Lane";
		startIndex = m_xmlStr.indexOf("<"+subStr);		
		if(startIndex!=-1){
			endIndex = m_xmlStr.indexOf("</"+subStr, startIndex) + 1;		
			do {
				ARISObjDef objOcc = new ARISObjDef();				
				endIndex 		= m_xmlStr.indexOf("</"+subStr, startIndex) + 1;			
				String currLine = m_xmlStr.substring(startIndex, endIndex);			
				
				currMXCell = new HashMap<String, String>();
				m_objs.add(currMXCell);				
				parseObjectLane(currLine,currMXCell,objOcc);
				if(objOcc!=null && !StringUtil.checkNull(objOcc.getIsType()).equals("") && !StringUtil.checkNull(objOcc.getPlainText()).replaceAll(" ", "").equals("")){m_defARIS.add(objOcc);}
				startIndex	= m_xmlStr.indexOf("<"+subStr, endIndex);
				
			} while(startIndex != -1);	
		}
		
		//2.2 Read AttrDef	
		String modelAttrDef=m_xmlStr.substring(endIndex);
		//System.out.print("modelAttrDef::"+modelAttrDef);
		startIndex	= modelAttrDef.indexOf("<AttrDef AttrDef.Type=\"AT_ID\"");
		int subStrLng=0;
		String currStr="";
		if(startIndex!=-1){
			subStr="TextValue=\"";
			subStrLng = subStr.length();
			String cellStr2 =modelAttrDef.substring(startIndex); 
			startIndex	= cellStr2.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr2.indexOf("\"", startIndex);
				currStr = cellStr2.substring(startIndex, endIndex);
				m_modelInfo.put("ID", currStr);
			}
		}
		startIndex	= modelAttrDef.indexOf("<AttrDef AttrDef.Type=\"AT_NAME\"");
		if(startIndex!=-1){
			subStr="TextValue=\"";
			subStrLng = subStr.length();
			String cellStr2 =modelAttrDef.substring(startIndex); 
			startIndex	= cellStr2.indexOf(subStr);
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr2.indexOf("\"", startIndex);
				currStr = cellStr2.substring(startIndex, endIndex);//StringUtil.toKR(cellStr2.substring(startIndex, endIndex));
				m_modelInfo.put("Name", currStr);
			}	
		}
		
		//2.3 Read ObjOcc		
		subStr = "ObjOcc";
		startIndex = m_xmlStr.indexOf("<"+subStr);
		if(startIndex!=-1){
			endIndex = m_xmlStr.indexOf("</"+subStr, startIndex) + 1;	
			do {
				ARISObjDef objOcc = new ARISObjDef();				
				endIndex 		= m_xmlStr.indexOf("</"+subStr, startIndex) + 1;			
				String currLine = m_xmlStr.substring(startIndex, endIndex);			
				
				currMXCell = new HashMap<String, String>();
				m_objs.add(currMXCell);				
				parseObjectOcc(currLine,currMXCell,objOcc);				
				if(objOcc!=null && isCheckObjDef(objOcc)){
					int positionX = NumberUtil.getIntValue(objOcc.getPosX(),0) + NumberUtil.getIntValue(objOcc.getWidth(),0) + 20;
					int positionY = NumberUtil.getIntValue(objOcc.getPosY(),0) + NumberUtil.getIntValue(objOcc.getHeight(),0) + 20;
					if(Integer.parseInt(m_positionMaxX)<positionX){m_positionMaxX=StringUtil.checkNull(positionX,"0");}
					if(Integer.parseInt(m_positionMaxY)<positionY){m_positionMaxY=StringUtil.checkNull(positionY,"0");}
					m_defARIS.add(objOcc);
					}
				startIndex	= m_xmlStr.indexOf("<"+subStr, endIndex);
				
			} while(startIndex != -1);	
		}
		
	}	
	private boolean isCheckObjDef(ARISObjDef objDef){
		boolean isSucc = true;
		if(objDef.getSymbolNum().equals("ST_ORG_UNIT_TYPE_1")){isSucc=false;}
		return isSucc;
	}
	
	private void parseObjectDef(String cellStr, HashMap<String, String> mxCell, ARISObjDef objDef){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="ObjDef.ID=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("objDefID", currStr);	
			objDef.setID(currStr);
		}		
		subStr="TypeNum=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("typeNum", currStr);			
			objDef.setTypeNum(currStr);
		}			
		subStr="ToCxnDefs.IdRefs=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("toRefID", currStr);
			objDef.setToRefID(currStr);
		}
		subStr="SymbolNum=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);		
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("symbolNum", currStr);
			objDef.setSymbolNum(currStr);
		}
		
		subStr="GUID>";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);		
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf(subStr, startIndex)-2;
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("GUID", currStr);
			objDef.setGUID(currStr);
		}
		subStr="SymbolGUID>";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);		
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf(subStr, startIndex)-2;
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("symbolGUID", currStr);
			objDef.setSymbolGUID(currStr);
		}		
		startIndex	= cellStr.indexOf("AttrDef.Type=\"AT_ID\"");
		if(startIndex!=-1){
			subStr="TextValue=\"";
			subStrLng = subStr.length();
			String cellStr2 =cellStr.substring(startIndex); 
			startIndex	= cellStr2.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr2.indexOf("\"", startIndex);
				currStr = cellStr2.substring(startIndex, endIndex);
				mxCell.put("attrDefID", currStr);
				objDef.setAttrDefID(currStr);
			}
		}
		startIndex	= cellStr.indexOf("AttrDef.Type=\"AT_NAME\"");
		if(startIndex!=-1){
			subStr="TextValue=\"";
			subStrLng = subStr.length();
			String cellStr2 =cellStr.substring(startIndex); 
			startIndex	= cellStr2.indexOf(subStr);
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr2.indexOf("\"", startIndex);
				currStr = cellStr2.substring(startIndex, endIndex);
				mxCell.put("plainText", currStr);
				objDef.setPlainText(currStr);
			}	
		}
		
		//Read CxnDef
		startIndex	= cellStr.indexOf("<CxnDef");
		if(startIndex!=-1){
			HashMap<String, String> currMXCell = new HashMap<String, String>();
			ArrayList<ARISCxnDef> cxnDefs = new ArrayList<ARISCxnDef>();
			do {
				ARISCxnDef cxnDef = new ARISCxnDef();			
				endIndex 		= cellStr.indexOf("</CxnDef>", startIndex) + 1;	
				String currLine = cellStr.substring(startIndex, endIndex);			
				
				currMXCell = new HashMap<String, String>();
				parseCxnDef(currLine,currMXCell,cxnDef);				
				cxnDefs.add(cxnDef);
				startIndex	= cellStr.indexOf("<CxnDef", endIndex);
				
			} while(startIndex != -1);	
			objDef.setCxnDefs(cxnDefs);
		}
		
		objDef.setIsType("DEF");
	}
	
	private void parseCxnDef(String cellStr, HashMap<String, String> mxCell, ARISCxnDef cxnDef){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="CxnDef.ID=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("cxnDefID", currStr);	
			cxnDef.setID(currStr);
		}
		subStr="CxnDef.Type=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("type", currStr);			
			cxnDef.setType(currStr);
		}
		subStr="ToObjDef.IdRef=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("toRefID", currStr);			
			cxnDef.setToRefID(currStr);
		}
		
		subStr="<AttrDef AttrDef.Type=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			//mxCell.put("plainText", currStr);
			//cxnDef.setPlainText(currStr);
		}	

		if(currStr.equals("AT_CXN_ROLE")){
			subStr="<PlainText TextValue=\"";
			subStrLng = subStr.length();
			startIndex	= cellStr.indexOf(subStr);
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("plainText", currStr);
				cxnDef.setPlainText(currStr);
			}
		}
	}

	private void parseObjectLane(String cellStr, HashMap<String, String> mxCell, ARISObjDef objDef){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="Orientation=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
		}	
		//Check VERTICAL
		if(currStr.equals("VERTICAL")){
			mxCell.put("orientation", currStr);	
			
			subStr="StartBorder=\"";
			subStrLng = subStr.length();
			startIndex	= cellStr.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_x));
				mxCell.put("startBorder", currStr);
				objDef.setStartBorder(NumberUtil.getIntValue(currStr));
			}
			subStr="EndBorder=\"";
			subStrLng = subStr.length();
			startIndex	= cellStr.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_x));
				mxCell.put("endBorder", currStr);
				objDef.setEndBorder(NumberUtil.getIntValue(currStr));
			}	
			
			subStr="TextValue=\"";
			subStrLng = subStr.length();
			startIndex	= cellStr.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);//StringUtil.toUTF8(cellStr.substring(startIndex, endIndex));
				mxCell.put("plainText", currStr);
				objDef.setPlainText(currStr);
				objDef.setIsType("LANE");	
			}
		}
	}
	private void parseObjectOcc(String cellStr, HashMap<String, String> mxCell, ARISObjDef objOcc){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="ObjOcc.ID=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("objOccID", currStr);	
			objOcc.setID(currStr);
		}		
		subStr="ObjDef.IdRef=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("defID", currStr);
			objOcc.setDefID(currStr);
		}
		subStr="SymbolNum=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);		
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("symbolNum", currStr);
			objOcc.setSymbolNum(currStr);
		}
		String currLine="";
		//Position
		startIndex	= cellStr.indexOf("<Position");	
		if(startIndex!=1){
			endIndex 		= cellStr.indexOf("/>", startIndex) + 1;			
			currLine = cellStr.substring(startIndex, endIndex);			
	
			subStr="Pos.X=\"";
			subStrLng = subStr.length();
			startIndex	= currLine.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= currLine.indexOf("\"", startIndex);
				currStr = currLine.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_x));
				mxCell.put("posX", currStr);
				objOcc.setPosX(NumberUtil.getIntValue(currStr,0));
			}
			subStr="Pos.Y=\"";
			subStrLng = subStr.length();
			startIndex	= currLine.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= currLine.indexOf("\"", startIndex);
				currStr = currLine.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_y));
				mxCell.put("posY", currStr);
				objOcc.setPosY(NumberUtil.getIntValue(currStr,0));
			}
		}
		
		//Size
		startIndex	= cellStr.indexOf("<Size");	
		if(startIndex!=1){
			endIndex 		= cellStr.indexOf("/>", startIndex) + 1;			
			currLine = cellStr.substring(startIndex, endIndex);			
	
			subStr="Size.dX=\"";
			subStrLng = subStr.length();
			startIndex	= currLine.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= currLine.indexOf("\"", startIndex);
				currStr = currLine.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_x));
				mxCell.put("width", currStr);
				objOcc.setWidth(NumberUtil.getIntValue(currStr,0));
			}
			subStr="Size.dY=\"";
			subStrLng = subStr.length();
			startIndex	= currLine.indexOf(subStr);		
			if(startIndex!=-1){
				startIndex+= subStrLng;		
				endIndex 	= currLine.indexOf("\"", startIndex);
				currStr = currLine.substring(startIndex, endIndex);
				currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_y));
				mxCell.put("height", currStr);
				objOcc.setHeight(NumberUtil.getIntValue(currStr,0));
			}
		}
		
		//Read CxnOcc
		startIndex	= cellStr.indexOf("<CxnOcc");	
		if(startIndex!=-1){
			HashMap<String, String> currMXCell = new HashMap<String, String>();
			ArrayList<ARISCxnDef> cxnOccs = new ArrayList<ARISCxnDef>();
			do {
				ARISCxnDef cxnOcc = new ARISCxnDef();			
				endIndex 		= cellStr.indexOf("</CxnOcc>", startIndex) + 1;	
				currLine = cellStr.substring(startIndex, endIndex);					
				currMXCell = new HashMap<String, String>();
				parseCxnOcc(currLine,currMXCell,cxnOcc);				
				if(cxnOcc!=null){cxnOccs.add(cxnOcc);}
				startIndex	= cellStr.indexOf("<CxnOcc", endIndex);
				
			} while(startIndex != -1);		
			objOcc.setCxnDefs(cxnOccs);
		}
		
		objOcc.setIsType("OCC");
	}	
	
	private void parseCxnOcc(String cellStr, HashMap<String, String> mxCell, ARISCxnDef cxnOcc){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="CxnOcc.ID=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("cxnOccID", currStr);	
			cxnOcc.setID(currStr);
		}
		subStr="CxnDef.IdRef=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("defID", currStr);			
			cxnOcc.setDefID(currStr);
		}
		subStr="ToObjOcc.IdRef=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("toRefID", currStr);			
			cxnOcc.setToRefID(currStr);
		}
		subStr="Diagonal=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("diagonal", currStr);			
			cxnOcc.setDiagonal(currStr);
		}
		
		//Read CxnOcc Position
		startIndex	= cellStr.indexOf("<Position");		
		if(startIndex!=-1){
			HashMap<String, String> currMXCell = new HashMap<String, String>();
			ArrayList<ARISCxnOccPos> cxnOccPoss = new ArrayList<ARISCxnOccPos>();
			do {
				ARISCxnOccPos cxnOccPos = new ARISCxnOccPos();			
				endIndex 		= cellStr.indexOf("/>", startIndex) + 1;	
				String currLine = cellStr.substring(startIndex, endIndex);			
				currMXCell = new HashMap<String, String>();
				parseCxnOccPos(currLine,currMXCell,cxnOccPos);				
				//System.out.println("Postion="+currLine+" X="+StringUtil.checkNull(cxnOccPos.getPosX()));
				if(cxnOccPos!=null){cxnOccPoss.add(cxnOccPos);}
				startIndex	= cellStr.indexOf("<Position", endIndex);
				
			} while(startIndex != -1);
			cxnOcc.setCxnOccPoss(cxnOccPoss);
		}
	}
	private void parseCxnOccPos(String cellStr, HashMap<String, String> mxCell, ARISCxnOccPos cxnOccPos){	
		int subStrLng = 0;
		String subStr = "";
		
		subStr="Pos.X=\"";
		subStrLng = subStr.length();
		int startIndex	= cellStr.indexOf(subStr);
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= subStrLng;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			//System.out.println("Postion posX="+currStr);
			currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_x));
			mxCell.put("posX", currStr);	
			cxnOccPos.setPosX(NumberUtil.getIntValue(currStr,0));
		}
		subStr="Pos.Y=\"";
		subStrLng = subStr.length();
		startIndex	= cellStr.indexOf(subStr);
		if(startIndex!=-1){
			startIndex+= subStrLng;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			//System.out.println("Postion posY="+currStr);
			currStr=StringUtil.checkNull(Math.round(NumberUtil.getIntValue(currStr)*per_y));
			mxCell.put("posY", currStr);			
			cxnOccPos.setPosY(NumberUtil.getIntValue(currStr,0));
		}
	}
	
	public ArrayList<HashMap<String, String>> getObects() {
		return m_objs;
	}
	
	public ArrayList<ARISObjDef> getARISObects() {
		return m_defARIS;
	}
	public Map getModelInfo(){
		m_modelInfo.put("maxX", m_positionMaxX);
		m_modelInfo.put("maxY", m_positionMaxY);
		return m_modelInfo;
	}
}
