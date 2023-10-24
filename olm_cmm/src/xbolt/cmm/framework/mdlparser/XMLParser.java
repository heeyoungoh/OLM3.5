package xbolt.cmm.framework.mdlparser;

import java.util.ArrayList;
import java.util.HashMap;

public class XMLParser {
	private String m_xmlStr;
	private ArrayList<HashMap<String, String>> m_objs = new ArrayList<HashMap<String,String>>();
	
	public XMLParser(String xmlStr){
		m_xmlStr = xmlStr;
	}
	
	public void parseXML(){
		HashMap<String, String> currMXCell = new HashMap<String, String>();
		boolean	isUserObj = false;		
		int startIndex	= m_xmlStr.indexOf("<");		
		String edgePath="";		
		do {
			int endIndex 		= m_xmlStr.indexOf(">", startIndex) + 1;			
			String currLine = m_xmlStr.substring(startIndex, endIndex);			
			int startMXTypeIndex	= currLine.indexOf("<") + 1;
			int endMXTypeIndex 		= currLine.indexOf(" ", startMXTypeIndex);
			if(endMXTypeIndex == -1) {endMXTypeIndex = currLine.indexOf(">", startMXTypeIndex);}
			
			String MXType = currLine.substring(startMXTypeIndex, endMXTypeIndex);			
			if (MXType.equals("UserObject")) {
				isUserObj = true;				
				currMXCell = new HashMap<String, String>();
				edgePath = "";
				m_objs.add(currMXCell);				
				parseUserObject(currLine,currMXCell);				
			}
			else if (MXType.equals("mxCell")) {
				if(isUserObj == false) {
					currMXCell = new HashMap<String, String>();
					edgePath = "";
					m_objs.add(currMXCell);
				}				
				parseMXCell(currLine,currMXCell);
			}
			else if (MXType.equals("mxGeometry")) {				
				parseMXGeometry(currLine,currMXCell);
			}
			else if (MXType.equals("mxPoint")) {				
				edgePath += currLine;
			}
			else if (MXType.equals("Array")) {				
				edgePath += currLine;
			}
			else if (MXType.equals("/Array")) {				
				edgePath += currLine;
				currMXCell.put("edgePath", edgePath);				
			}
			else if (MXType.equals("/mxGeometry")) {
				currMXCell.remove("edgePath");
				currMXCell.put("edgePath", edgePath);
			}
			
			if (MXType.equals("/UserObject")) {
				isUserObj = false;					
			}
			
			
			startIndex	= m_xmlStr.indexOf("<", endIndex);
			
		} while(startIndex != -1);		
	}	
	
	private void parseUserObject(String cellStr, HashMap<String, String> mxCell){		
		int startIndex	= cellStr.indexOf("id=\"");
		int endIndex=-1;
		String currStr="";
		
		if(startIndex!=-1){
			startIndex+= + 4;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("objID", currStr);	
		}		
		startIndex	= cellStr.indexOf("label=\"");
		if(startIndex!=-1){
			startIndex+= 7;		
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("label", currStr);			
		}			
		startIndex	= cellStr.indexOf("link=\"");
		if(startIndex!=-1){
			startIndex+= 6;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("link", currStr);
		}
		
		startIndex	= cellStr.indexOf("clientID=\"");
		if(startIndex!=-1){
			startIndex+= 10;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("clientID", currStr);
		}
		
		startIndex	= cellStr.indexOf("modelID=\"");
		if(startIndex!=-1){
			startIndex+= 9;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("modelID", currStr);
		}
	}	
	
	private void parseMXCell(String cellStr, HashMap<String, String> mxCell){		
		int startIndex	= cellStr.indexOf("id=\"");
		int endIndex 	= -1;
		String currStr  = "";
		if(startIndex != -1) {
			startIndex	+= 4;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("objID", currStr);
		}	
		
		startIndex	= cellStr.indexOf("value=\"");
		if(startIndex != -1) {
			startIndex	+= 7;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("label", currStr);
		}		
		
		startIndex	= cellStr.indexOf("parent=\"");
		if(startIndex != -1) {
			startIndex	+= 8;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);			
			mxCell.put("parent", currStr);
		}
		
		startIndex	= cellStr.indexOf("vertex=\"");
		if(startIndex != -1) {
			startIndex	+= 8;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("vertex", currStr);
			mxCell.put("objtype", "vertex");
//			mxCell.put("SymTypeCode", "SB00001");
//			mxCell.put("CategoryCode", "OJ");
		}
		
		startIndex	= cellStr.indexOf("edge=\"");
		if(startIndex != -1) {
			startIndex	+= 6;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("edge", currStr);
			mxCell.put("objtype", "edge");	
			mxCell.put("relative", "1");	
			
			startIndex	= cellStr.indexOf("target=\"");
			if(startIndex != -1) {
				startIndex	+= 8;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				cellStr.substring(startIndex, endIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("target", currStr);
			}
			
			startIndex	= cellStr.indexOf("source=\"");
			if(startIndex != -1) {
				startIndex	+= 8;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				cellStr.substring(startIndex, endIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("source", currStr);
			}			
//			mxCell.put("SymTypeCode", "SB00101");
//			mxCell.put("CategoryCode", "ST1");
			
			startIndex	= cellStr.indexOf("rotation=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("rotation", currStr);
			} 
			
			startIndex	= cellStr.indexOf("gradientDirection=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("gradientDirection", currStr);
			}
			
			startIndex	= cellStr.indexOf("LabelBackgroundColor=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("LabelBackgroundColor", currStr);
			}
			
			startIndex	= cellStr.indexOf("Opacity=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("Opacity", currStr);
			}
			
			startIndex	= cellStr.indexOf("Shadow=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("Shadow", currStr);
			}
			
			startIndex	= cellStr.indexOf("Dashed=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("Dashed", currStr);
			}
			
			startIndex	= cellStr.indexOf("strokeColor=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("strokeColor", currStr);
			}
			
			startIndex	= cellStr.indexOf("fillColor=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("fillColor", currStr);
			}
								
			startIndex	= cellStr.indexOf("rounded=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("rounded", currStr);
			}
			
			startIndex	= cellStr.indexOf("fontFamily=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("fontFamily", currStr);
			}
			
			startIndex	= cellStr.indexOf("fontColor=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("fontColor", currStr);
			}
			
			startIndex	= cellStr.indexOf("fontStyle=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("fontStyle", currStr);
			}
			
			startIndex	= cellStr.indexOf("labelBorderColor=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("labelBorderColor", currStr);
			}
			
			startIndex	= cellStr.indexOf("spacingTop=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("spacingTop", currStr);
			}
			
			startIndex	= cellStr.indexOf("startFill=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("startFill", currStr);
			}
			
			startIndex	= cellStr.indexOf("endFill=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("endFill", currStr);
			}
			
			startIndex	= cellStr.indexOf("startSize=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("startSize", currStr);
			}
			
			startIndex	= cellStr.indexOf("endSize=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("endSize", currStr);
			}
			
			startIndex	= cellStr.indexOf("horizontal=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("horizontal", currStr);
			}
			
			startIndex	= cellStr.indexOf("symTypeCode=\"");
			if(startIndex != -1) {
				startIndex += 9;
				endIndex 	= cellStr.indexOf("\"", startIndex);
				currStr = cellStr.substring(startIndex, endIndex);
				mxCell.put("symTypeCode", currStr);
			}
		}		
		
		startIndex	= cellStr.indexOf("style=\"");
		if(startIndex != -1) {
			startIndex	+= 7;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			
			String[] styles = currStr.split(";");			
			for(int i = 0; i < styles.length; i++) {
				if(styles[i].equals("image")) {
					String imageStyle = "image;";
					i++;
					imageStyle += styles[i];
					mxCell.put("style", imageStyle);
					continue;
				}				
				String[] currStyle = styles[i].split("=");					
				if(currStyle[0].equals("roundedd")) {
					mxCell.put("style", styles[0]);
					/*mxCell.remove("SymTypeCode");
					mxCell.put("SymTypeCode", "SB00001");*/
					continue;
				}else if(currStyle[0].equals("shape")) {
					mxCell.put("style", styles[0]);
					continue;
				}else if(currStyle[0].equals("whiteSpace")) {
					mxCell.put("style", styles[0]);
					continue;
				}else if(currStyle[0].equals("edgeStyle")) {
					mxCell.put("style", styles[0]);
					continue;
				}else if(currStyle[0].equals("AttrType")) {	//AttrType이 있는 경우는 Element에서 일단 제외
					mxCell.put("attrType", currStyle[1]);
					continue;
				}			
				if(currStyle.length == 0) {					
				}else if(currStyle.length == 1) {					
					if(mxCell.get("style") == null) {						
						mxCell.put("style", currStyle[0]);						
						/*if(currStyle[0].equals("rhombus")) {
							mxCell.remove("SymTypeCode");
							mxCell.put("SymTypeCode", "SB00002");
						}*/
					}					
				}else if (currStyle.length == 2){
					if(mxCell.get(currStyle[0]) == null) {
						mxCell.put(currStyle[0], currStyle[1]);
					}
				}				
			}			
		}		
	}
	
	private void parseMXGeometry(String cellStr, HashMap<String, String> mxCell) {		
		int startIndex	= cellStr.indexOf("x=\"");
		int endIndex 	= 0; 
		String currStr = "";
		if(startIndex != -1) {
			startIndex += 3;
			endIndex 	= cellStr.indexOf("\"", startIndex);		
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("x", currStr);
		}
		
		startIndex	= cellStr.indexOf("y=\"");
		if(startIndex != -1) {
			startIndex += 3;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("y", currStr);
		}
		
		startIndex	= cellStr.indexOf("width=\"");
		if(startIndex != -1) {
			startIndex += 7;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("width", currStr);
		}
		
		startIndex	= cellStr.indexOf("height=\"");
		if(startIndex != -1) {
			startIndex += 8;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("height", currStr);
		}	
		
		startIndex	= cellStr.indexOf("relative=\"");
		if(startIndex != -1) {
			startIndex += 10;
			endIndex 	= cellStr.indexOf("\"", startIndex);
			currStr = cellStr.substring(startIndex, endIndex);
			mxCell.put("relative", currStr);			
		} else if(mxCell.get("edge") != null && mxCell.get("edge").equals("1")) {
			mxCell.put("x", "");
			mxCell.put("y", "");				
		}
		
	}
	
	public ArrayList<HashMap<String, String>> getObects() {
		return m_objs;
	}
}
