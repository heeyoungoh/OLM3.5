package xbolt.cmm.service.dtoMdl;

import java.util.ArrayList;

public class ARISObjDef {	
	private String isType;
	private String modelID;
	private String modelW;
	private String modelH;
	
	private String ID;
	private String typeNum;
	private String toRefID;
	private String symbolNum;
	private String GUID;
	private String symbolGUID;
	private String attrDefID;
	private String plainText;
	
	//About Lane
	private int startBorder;
	private int endBorder;
	
	//About ObjOcc
	private String defID;
	private int	posX;
	private int	posY;
	private int	width;
	private int	height;
	
	private ArrayList<ARISCxnDef> cxnDefs;

	// constroctors
	public ARISObjDef()
	{	
		cxnDefs = new ArrayList<ARISCxnDef>();
	}
	public String getIsType() {
		return isType;
	}

	public void setIsType(String isType) {
		this.isType = isType;
	}
	
	public String getModelID() {
		return modelID;
	}
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}
	
	public String getModelW() {
		return modelW;
	}
	public void setModelW(String modelW) {
		this.modelW = modelW;
	}
	public String getModelH() {
		return modelH;
	}
	public void setModelH(String modelH) {
		this.modelH = modelH;
	}
	public String s_itemID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public String getToRefID() {
		return toRefID;
	}

	public void setToRefID(String toRefID) {
		this.toRefID = toRefID;
	}

	public String getSymbolNum() {
		return symbolNum;
	}

	public void setSymbolNum(String symbolNum) {
		this.symbolNum = symbolNum;
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public String getSymbolGUID() {
		return symbolGUID;
	}
	public void setSymbolGUID(String symbolGUID) {
		this.symbolGUID = symbolGUID;
	}
	public String getAttrDefID() {
		return attrDefID;
	}
	public void setAttrDefID(String attrDefID) {
		this.attrDefID = attrDefID;
	}
	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	
	
	


	

	public int getStartBorder() {
		return startBorder;
	}

	public void setStartBorder(int startBorder) {
		this.startBorder = startBorder;
	}

	public int getEndBorder() {
		return endBorder;
	}

	public void setEndBorder(int endBorder) {
		this.endBorder = endBorder;
	}

	public String getDefID() {
		return defID;
	}

	public void setDefID(String defID) {
		this.defID = defID;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<ARISCxnDef> getCxnDefs() {
		return cxnDefs;
	}

	public void setCxnDefs(ArrayList<ARISCxnDef> cxnDefs) {
		this.cxnDefs = cxnDefs;
	}

}
