package xbolt.cmm.service.dtoMdl;

import java.util.*;
import xbolt.cmm.service.dtoMdl.Occ;

public class OccList {
	private List<Occ> m_occs = new ArrayList<Occ>(); 

	public void addOcc(long occID, String plainText, long modelID, long objectID, String categoryCode,  int PositionX, int PositionY, int width, int height) 
	{
		//System.out.println(occID + "//" + objectID + "//" + modelID + "//" + PositionX + "//" + PositionY + "//" + width + "//" + height);
		m_occs.add(new Occ(occID,  plainText, modelID, objectID, categoryCode, PositionX, PositionY, width, height));
		
		
	}
	
	public List<Occ> getOccs()
	{
		return m_occs;
	}
	
	public int getSize()
	{
		return m_occs.size();
	}
	
	public Occ getOcc(int index)
	{
		return m_occs.get(index);
	}
}
