package xbolt.cmm.framework.mxgraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import xbolt.cmm.framework.mdlparser.XMLParserARIS;
import xbolt.cmm.framework.mxgraph.imageexport.Constants;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.dtoMdl.ARISCxnDef;
import xbolt.cmm.service.dtoMdl.ARISCxnOccPos;
import xbolt.cmm.service.dtoMdl.ARISObjDef;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import com.objectplanet.image.PngEncoder;

/**
 * mxgraph xml export 처리
 * @Class Name : imageExport.java
 * @Description : mxgraph xml Export을  사용하기 위해 제공한다.
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
public class xmlExportFromARIS {

	private static transient Hashtable<String, Image> imageCache = new Hashtable<String, Image>();
	private static transient SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	
	public static Map parsingXML(String modelID, Map cmmMap, List symTypeList) throws Exception{
		XMLParserARIS xmlparser = new XMLParserARIS(StringUtil.checkNull(cmmMap.get("XML")));
		xmlparser.parseXML();
		ArrayList<ARISObjDef> diagramObjs = (ArrayList<ARISObjDef>) cmmMap.get("diagramObjs");
		String maxY = StringUtil.checkNull(cmmMap.get("maxY"),"0");
		String maxX = StringUtil.checkNull(cmmMap.get("maxX"),"0");
		
		//3.2 create xml
		String parentID="";
		String diagramForXml = "";
		String objHeight=""; String objWidth="";
		diagramForXml = "<mxGraphModel style=\"default-style2\" dx=\"800\" dy=\"800\" grid=\"0\" guides=\"1\" tooltips=\"1\" connect=\"1\" fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"2000\" pageHeight=\"2000\">";
		diagramForXml += "<root>";
		diagramForXml += "<mxCell id=\"" + modelID + "\"/>";
		diagramForXml += "<mxCell id=\"" + (Integer.parseInt(modelID)+1) + "\" parent=\"" + modelID + "\"/>\n";
		
		modelID=StringUtil.checkNull(Integer.parseInt(modelID)+1);
		parentID=modelID;
		//isType:DEF, LANE, OCC
		//////////////////////////////////////////////////////////////////////////////////////////////
		//3.2.1 Create Object xml
		String arisType="";
 		for(int i=0;i<diagramObjs.size();i++){
			ARISObjDef occ = new ARISObjDef();
			occ = diagramObjs.get(i);
			objHeight="";objWidth="";arisType=StringUtil.checkNull(occ.getIsType());
			if(arisType.equals("OCC") || arisType.equals("LANE") ){
    			ARISObjDef def = new ARISObjDef();
    			modelID=StringUtil.checkNull(Integer.parseInt(modelID)+1);
				for(int j=0;j<diagramObjs.size();j++){
	    			def = diagramObjs.get(j);
		    		//System.out.println("0=="+occ.getDefID()+","+def.s_itemID());
	    			if(def.getIsType().equals("DEF") && StringUtil.checkNull(occ.getDefID()).equals(def.s_itemID())){break;}
				}
				String tempStr = arisType.equals("LANE") ? StringUtil.checkNull(occ.getPlainText()): StringUtil.checkNull(def.getPlainText());
    			if(!StringUtil.checkNull(def.getAttrDefID()).equals("")){				
    				diagramForXml += "<UserObject ";
    				diagramForXml += "id=\"" + (modelID).toString() + "\" ";
    				diagramForXml += "link=\""+ StringUtil.checkNull(def.getAttrDefID()) + "\" ";
    				if(!tempStr.equals("")){diagramForXml += "label=\"" + tempStr + "\" ";}			
    				diagramForXml += ">";
    				diagramForXml += "<mxCell ";
    			}
    			else {
           			diagramForXml += "<mxCell id=\"" + (modelID).toString() + "\" ";			
    				if(!tempStr.equals("")){diagramForXml += "value=\"" + tempStr + "\" ";}
    			}			

				//System.out.println("1.i="+i+"::arisType"+arisType+"::xml="+diagramForXml);
 				diagramForXml += "style=\"";
 				for(int s=0;s<symTypeList.size();s++){
					Map getMap = (HashMap)symTypeList.get(s);
					if((StringUtil.checkNull(def.getSymbolGUID(),StringUtil.checkNull(def.getSymbolNum())).equals(StringUtil.checkNull(getMap.get("ArisSymbolGUID"), StringUtil.checkNull(getMap.get("ArisSymbolNum")))))
							|| (arisType.equals("LANE")&&StringUtil.checkNull(getMap.get("SymTypeCode")).equals("SB00001"))){
						if(!StringUtil.checkNull(getMap.get("ImagePath")).equals("")) {diagramForXml += StringUtil.checkNull(getMap.get("ImagePath"))+ ";";	}
	    				if(!StringUtil.checkNull(getMap.get("DefStrokeColor")).equals("")){diagramForXml += "strokeColor=" + getMap.get("DefStrokeColor").toString() + ";";	}		
	    				if(!StringUtil.checkNull(getMap.get("DefStrokeWidth")).equals("") && !StringUtil.checkNull(getMap.get("DefStrokeWidth")).equals("0")){diagramForXml += "DefStrokeWidth=" + getMap.get("DefStrokeWidth").toString() + ";";	}		
	    				if(!StringUtil.checkNull(getMap.get("DefGradientColor")).equals("")){diagramForXml += "gradientColor=" + getMap.get("DefGradientColor").toString() + ";";}	
	    				if(!StringUtil.checkNull(getMap.get("DefColor")).equals("")){diagramForXml += "fillColor=" + getMap.get("DefColor").toString() + ";";}
	    				if(!StringUtil.checkNull(getMap.get("DefFontColor")).equals("")) {diagramForXml += "fontColor=" + getMap.get("DefFontColor").toString() + ";";}	
	    				
	    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFontSize"),"0").equals("0")) {diagramForXml += "fontSize=" + cmmMap.get("sessionDefFontSize").toString() + ";";}	
	    				//if(!StringUtil.checkNull(getMap.get("Rotation")).equals("") && !StringUtil.checkNull(getMap.get("Rotation")).equals("0")) {diagramForXml += "rotation=" + getMap.get("Rotation").toString()+";";}	
	    				//if(!StringUtil.checkNull(getMap.get("GradientDirection")).equals("") && !StringUtil.checkNull(getMap.get("GradientDirection")).equals("0")) {diagramForXml += "gradientDirection=" + getMap.get("GradientDirection").toString()+";";}	
	    				//if(!StringUtil.checkNull(getMap.get("Opacity")).equals("") && !StringUtil.checkNull(getMap.get("Opacity")).equals("0")) {diagramForXml += "opacity=" + getMap.get("Opacity").toString()+";";}
	    				if(!StringUtil.checkNull(getMap.get("DefShadow")).equals("") && !StringUtil.checkNull(getMap.get("DefShadow")).equals("0")) {diagramForXml += "shadow=" + getMap.get("DefShadow").toString()+";";}
	    				//if(!StringUtil.checkNull(getMap.get("Dashed")).equals("")  && !StringUtil.checkNull(getMap.get("Dashed")).equals("0")) {diagramForXml += "dashed=" + getMap.get("Dashed").toString()+";";}
	    				//if(!StringUtil.checkNull(getMap.get("Rounded")).equals("")  && !StringUtil.checkNull(getMap.get("Rounded")).equals("0")) {diagramForXml += "rounded=" + getMap.get("Rounded").toString()+";";}
	    				//if(!StringUtil.checkNull(getMap.get("SpacingTop")).equals("")  && !StringUtil.checkNull(getMap.get("SpacingTop")).equals("0")) {diagramForXml += "spacingTop=" + getMap.get("SpacingTop").toString() + ";";}
	    				//if(!StringUtil.checkNull(getMap.get("LabelBackgroundColor")).equals("")) {diagramForXml += "labelBackgroundColor=" + getMap.get("LabelBackgroundColor").toString() + ";";}	
	    				//if(!StringUtil.checkNull(getMap.get("LabelBorderColor")).equals("")) {diagramForXml += "labelBorderColor=" + getMap.get("LabelBorderColor").toString() + ";";}
	    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFont")).equals("")) {diagramForXml += "fontFamily=" + cmmMap.get("sessionDefFont").toString() + ";";}
	    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFontStyle")).equals("")) {diagramForXml += "fontStyle=" + cmmMap.get("sessionDefFontStyle").toString() + ";";}
	    				//if(!StringUtil.checkNull(cmmMap.get("sessionDefFontColor")).equals("")) {diagramForXml += "fontColor=" + cmmMap.get("sessionDefFontColor").toString() + ";";}	
	    				//if(!StringUtil.checkNull(getMap.get("StartFill")).equals("") && !StringUtil.checkNull(getMap.get("StartFill")).equals("-1")) {diagramForXml += "startFill=" + getMap.get("StartFill").toString() + ";";}		
	    				//if(!StringUtil.checkNull(getMap.get("EndFill")).equals("") && !StringUtil.checkNull(getMap.get("EndFill")).equals("-1")) {diagramForXml += "endFill=" + getMap.get("EndFill").toString() + ";";}
	    				//if(!StringUtil.checkNull(getMap.get("StartSize")).equals("") && !StringUtil.checkNull(getMap.get("StartSize")).equals("0")) {diagramForXml += "startSize=" + getMap.get("StartSize").toString() + ";";}		
	    				//if(!StringUtil.checkNull(getMap.get("EndSize")).equals("") && !StringUtil.checkNull(getMap.get("EndSize")).equals("0")) {diagramForXml += "endSize=" + getMap.get("EndSize").toString();}
   	    				diagramForXml += "symTypeCode="+StringUtil.checkNull(getMap.get("SymTypeCode")) + ";";
  	    				if(arisType.equals("LANE")){diagramForXml += "startSize=38;";}
  	    				if(StringUtil.checkNull(getMap.get("SymTypeCode")).equals("SB00006")){diagramForXml += "spacingTop=-50;labelBackgroundColor=none;";}
   	    				if(!StringUtil.checkNull(getMap.get("DefHeight")).equals("")){objHeight=StringUtil.checkNull(getMap.get("DefHeight"));}
	    				if(!StringUtil.checkNull(getMap.get("DefWidth")).equals("")){objWidth=StringUtil.checkNull(getMap.get("DefWidth"));}
  	    				break;
					}
				}
				diagramForXml += "\" parent=\""+parentID+"\" ";		    	    				
				diagramForXml += "vertex=\"1\">\n";  	
				
				//System.out.println("2.i="+i+"::arisType"+arisType+"::xml="+diagramForXml);
				if(arisType.equals("OCC")){
    				diagramForXml += "<mxGeometry as=\"geometry\" ";    	    				
    				if(!StringUtil.checkNull(occ.getPosX()).equals("")) {diagramForXml += "x=\"" + StringUtil.checkNull(occ.getPosX()) + "\" ";}			
    				if(!StringUtil.checkNull(occ.getPosY()).equals("")) {diagramForXml += "y=\"" + StringUtil.checkNull(occ.getPosY()) + "\" ";}
    				//if(!StringUtil.checkNull(occ.getWidth()).equals("")) {diagramForXml += "width=\"" + StringUtil.checkNull(occ.getWidth()) + "\" ";}			
    				//if(!StringUtil.checkNull(occ.getHeight()).equals("")) {diagramForXml += "height=\"" + StringUtil.checkNull(occ.getHeight()) + "\" ";}
    				if(!objWidth.equals("")){diagramForXml += "width=\"" + objWidth + "\" ";def.setModelW(objWidth);}
    				if(!objHeight.equals("")){diagramForXml += "height=\"" + objHeight+ "\" ";def.setModelH(objHeight);}	
    				diagramForXml += "/>\n";
    			}else if(arisType.equals("LANE")){
    				int width=NumberUtil.getIntValue(occ.getEndBorder(),0)-NumberUtil.getIntValue(occ.getStartBorder(),0);
    				diagramForXml += "<mxGeometry as=\"geometry\" ";    	    				
    				if(!StringUtil.checkNull(def.getStartBorder()).equals("")) {diagramForXml += "x=\"" + StringUtil.checkNull(occ.getStartBorder()) + "\" ";}			
    				diagramForXml += "y=\"" + StringUtil.checkNull("0") + "\" ";//if(!StringUtil.checkNull(occ.getPosY()).equals("")) {diagramForXml += "y=\"" + StringUtil.checkNull(occ.getPosY()) + "\" ";}
    				if(width>0) {diagramForXml += "width=\"" + StringUtil.checkNull(width) + "\" ";}			
    				diagramForXml += "height=\"" + maxY + "\" ";//if(!StringUtil.checkNull(occ.getHeight()).equals("")) {diagramForXml += "height=\"" + StringUtil.checkNull(occ.getHeight()) + "\" ";}
    				diagramForXml += "/>\n";	    				
    			}
				diagramForXml += "</mxCell>\n";	
				if(!StringUtil.checkNull(def.getAttrDefID()).equals("")) {diagramForXml += "</UserObject>";}	
				occ.setModelID(modelID);
				}
			
		}
		//System.out.println("2=="+diagramForXml);
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		//3.2.2 Create Connection xml
		for(int i=0;i<diagramObjs.size();i++){
			ARISObjDef occ = new ARISObjDef();
			occ = diagramObjs.get(i);
			//Object에 연결된 connection정보 load
			if(occ.getIsType().equals("OCC") && occ.getCxnDefs()!=null && occ.getCxnDefs().size()>0){
    			ARISObjDef t_occ = new ARISObjDef();  	    			
    			//연결된 OCC의 position정보 조회
				for(int j=0; j<occ.getCxnDefs().size();j++){    
	    			ARISCxnDef cnn = new ARISCxnDef();
	    			cnn = occ.getCxnDefs().get(j);
	    			
	    			int posCnt =0;if(cnn.getCxnOccPoss()!=null){posCnt=cnn.getCxnOccPoss().size();}
	    			//System.out.println("posCnt="+posCnt+", size="+StringUtil.checkNull(cnn.getCxnOccPoss().size()));
        			modelID=StringUtil.checkNull(Integer.parseInt(modelID)+1);
        			diagramForXml += "<mxCell id=\"" + (modelID).toString() + "\" ";
        			boolean isDefCxn = false;
        			for(int k=0; k<diagramObjs.size();k++){
        				ARISObjDef def = new ARISObjDef();
        				def = diagramObjs.get(k);
        				if(def.getIsType().equals("DEF")){
        					for(int cxn=0; cxn<def.getCxnDefs().size();cxn++){
        						ARISCxnDef defCxn = new ARISCxnDef();defCxn=def.getCxnDefs().get(cxn);  
        						//System.out.println("cnn.getDefID()="+cnn.getDefID()+", defCxn.s_itemID()="+defCxn.s_itemID()+", defCxn.getPlainText()="+defCxn.getPlainText());
        						if(StringUtil.checkNull(cnn.getDefID(),"occ").equals(StringUtil.checkNull(defCxn.s_itemID(),"def"))){
        							isDefCxn=true;
        		        			if(!StringUtil.checkNull(defCxn.getPlainText()).equals("")){diagramForXml += "value=\"" + StringUtil.checkNull(defCxn.getPlainText()) + "\" ";}
        		        			break;
        						}
        					}
        					if(isDefCxn){break;}
        				}	        			
        			}
    				diagramForXml += "style=\"";
    				
    				String style=posCnt<3?"edgeStyle=none;":"edgeStyle=orthogonalEdgeStyle;";
    				String symTypeCode=posCnt<3?"SB00018":"SB00017";
    				if(!style.equals("")) {diagramForXml += style + ";";}
    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFontSize"),"0").equals("0")) {diagramForXml += "fontSize=" + cmmMap.get("sessionDefFontSize").toString() + ";";}	
    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFont")).equals("")) {diagramForXml += "fontFamily=" + cmmMap.get("sessionDefFont").toString() + ";";}
    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFontStyle")).equals("")) {diagramForXml += "fontStyle=" + cmmMap.get("sessionDefFontStyle").toString() + ";";}
    				if(!StringUtil.checkNull(cmmMap.get("sessionDefFontColor")).equals("")) {diagramForXml += "fontColor=" + cmmMap.get("sessionDefFontColor").toString() + ";";}	
	    				diagramForXml += "symTypeCode="+symTypeCode + ";";
	    				
    				//exit, entry처리
   	    			for(int k=0;k<diagramObjs.size();k++){
   	    				t_occ = new ARISObjDef();
   	    				t_occ = diagramObjs.get(k);    					
    	    			if(t_occ.getIsType().equals("OCC") && t_occ.s_itemID().equals(cnn.getToRefID())){break;}
    				}  
   	    			int dfPostion=10;
    				int s_positionX=NumberUtil.getIntValue(occ.getPosX(),0);int s_positionY=NumberUtil.getIntValue(occ.getPosY(),0);
    				int s_positionMinX=s_positionX-dfPostion;
    				int s_positionMaxX=s_positionX+dfPostion;
    				int s_positionMinY=s_positionY-dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionY*0.95)));
    				int s_positionMaxY=s_positionY+dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionY*1.05)));
    				int s_sizeY=NumberUtil.getIntValue(occ.getHeight(),0)+s_positionY;
    				int s_sizeMinY = s_sizeY-dfPostion;int s_sizeMaxY = s_sizeY+dfPostion;
    				int t_positionX=NumberUtil.getIntValue(t_occ.getPosX(),0);int t_positionY=NumberUtil.getIntValue(t_occ.getPosY(),0);
    				int t_positionMinX=t_positionX-dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionX*0.95)));
    				int t_positionMaxX=t_positionX+dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionX*1.05)));
    				int t_positionMinY=t_positionY-dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionY*0.95)));
    				int t_positionMaxY=t_positionY+dfPostion;//Integer.parseInt(StringUtil.checkNull(Math.round(t_positionY*1.05)));
    				//int t_sizeX=NumberUtil.getIntValue(t_occ.getWidth(),0)+t_positionX;
    				int t_sizeY=NumberUtil.getIntValue(t_occ.getHeight(),0)+t_positionY;	    				
    				//int t_sizeMinX = t_sizeX-10;//Integer.parseInt(StringUtil.checkNull(Math.round(t_sizeX*0.95)));
    				int t_sizeMinY = t_sizeY-dfPostion;int t_sizeMaxY = t_sizeY+dfPostion;
    				//System.out.println("s_positionX="+s_positionX+" occ.getPosX()="+occ.getPosX()+"occ.getPosY()="+occ.getPosY()+",s_sizeX="+s_sizeX+",s_sizeY="+s_sizeY+",s_sizeMinX="+s_sizeMinX+",s_sizeMinY="+s_sizeMinY);
    				//System.out.println("t_positionX="+t_positionX+" t_occ.getPosX()="+t_occ.getPosX()+"t_occ.getPosY()="+t_occ.getPosY()+",t_sizeX="+t_sizeX+",t_sizeY="+t_sizeY+",t_sizeMinX="+t_sizeMinX+",t_sizeMinY="+t_sizeMinY);
     				String pointY="100";String pointX="100";
     				String exitX="";String exitY="";String entryX="";String entryY="";
     				for(int k=0;k<posCnt;k++){
     					ARISCxnOccPos pos = new ARISCxnOccPos();
     					pos = cnn.getCxnOccPoss().get(k);
     					if(k==0){
     						if(s_positionMinX<=pos.getPosX() && pos.getPosX()<=s_positionMaxX){exitX="0";exitY="0.5";}
     						else if(s_positionMinY<=pos.getPosY() && pos.getPosY()<=s_positionMaxY){exitX="0.5";exitY="0";}
     						else if(s_sizeMinY<=pos.getPosY() && pos.getPosY()<=s_sizeMaxY){exitX="0.5";exitY="1";}
     						//posY:504 s_sizeMinY=getHeight-dfPostion:168
     						else{exitX="1";exitY="0.5";}
    	    				//System.out.println("pos.getPosX()="+pos.getPosX()+",pos.getPosY()="+pos.getPosY());
     					}else if(k==(posCnt-1)){
     						if(t_positionMinX<=pos.getPosX() && pos.getPosX()<=t_positionMaxX){entryX="0";entryY="0.5";}
     						else if(t_positionMinY<=pos.getPosY() && pos.getPosY()<=t_positionMaxY){entryX="0.5";entryY="0";}
     						else if(t_sizeMinY<=pos.getPosY() && pos.getPosY()<=t_sizeMaxY){entryX="0.5";entryY="1";}
     						else{entryX="1";entryY="0.5";}
    	    				//System.out.println("pos.getPosX()="+pos.getPosX()+",pos.getPosY()="+pos.getPosY());
     					}
     				}
    				if(!exitX.equals("")) {diagramForXml += "exitX=" + exitX + ";";}	
    				if(!exitY.equals("")) {diagramForXml += "exitY=" + exitY + ";";}	
    				if(!entryX.equals("")) {diagramForXml += "entryX=" + entryX + ";";}	
    				if(!entryY.equals("")) {diagramForXml += "entryY=" + entryY + ";";}		    				
    						
    				diagramForXml += "\" parent=\""+parentID+"\"";	
     				diagramForXml += " source=\""+occ.getModelID()+"\" ";	
    	     		diagramForXml += " target=\""+t_occ.getModelID()+"\" ";	
    				diagramForXml += " edge=\"1\">\n";
    				diagramForXml += "\t<mxGeometry as=\"geometry\" relative=\"1\" height=\"100\" width=\"100\">\n ";
    				diagramForXml += "\t\t<mxPoint y=\""+pointY+"\" as=\"sourcePoint\"/> ";
    				diagramForXml += "<mxPoint x=\""+pointX+"\" as=\"targetPoint\"/>\n ";
    				diagramForXml += "\t</mxGeometry>\n";	
    				diagramForXml += "</mxCell>\n";	
				}  				
			}
		}    			
		////////////////////////////////////////////////////////////////////////////   		
		
		diagramForXml += "</root>";
		diagramForXml += "</mxGraphModel>";	
		
		//_log.info("ARIS XML:::"+diagramForXml);		
		//System.out.println("ARIS XML:::"+diagramForXml);
		
		//cmmMap.put("itemID", diagramForXml);
		cmmMap.put("diagram", diagramForXml);
		cmmMap.put("mdlHeight", maxY);
		cmmMap.put("mdlWidth", maxX);
		
		return cmmMap;
	}

}
