package xbolt.cmm.service.dtoMdl;

import xbolt.cmm.service.dtoMdl.RectForOcc;

public class Occ {
	private long 	m_occID;
	private long	m_objectID;
	private long	m_modelID;
	private String m_plainText;
	
	private RectForOcc m_rect;

	
	// constroctors
	public Occ(long occID, String plainText, long modelID, long objectID, String categoryCode, int positionX, int positionY, int width, int height)
	{	
		
		m_occID		= occID;
		m_objectID	= objectID;
		m_modelID	= modelID;
		m_plainText = plainText;
		
		m_rect  = new RectForOcc(positionX, positionY, width, height);
		
	}
	
	public Occ(long occID, long objectID, long modelID, int PositionX, int PositionY, int width, int height) 
	{
		//System.out.println("2222");
		
		m_occID		= occID;
		m_objectID	= objectID;
		m_modelID	= modelID;
		
		m_rect  = new RectForOcc(PositionX, PositionY, width, height);		
	}
	
	// get functions
	public long getOccID()
	{
		return m_occID;
	}
	
	
	public long getObjectID()
	{
		return m_objectID;
	}
	
	public long getModelID()
	{
		return m_modelID;
	}
	
	public RectForOcc getRect()
	{
		return m_rect;
	}
	
	public String getPlainText()
	{
		return m_plainText; 
	}
	
	// set functions
	public void setOccID(long occID)
	{
		m_occID = occID;
	}
	
	public void setObjectID(long objectID)
	{
		m_objectID = objectID;
	}
	
	public void setModelID(long modelID)
	{
		m_modelID = modelID;
	}
	
	public void setRect(RectForOcc rect)
	{
		m_rect = rect;
	}
	
	public void setPlainText(String plainText)
	{
		m_plainText = plainText; 
	}
}
