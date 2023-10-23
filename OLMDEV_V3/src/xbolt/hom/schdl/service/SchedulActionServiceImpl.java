package xbolt.hom.schdl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xbolt.cmm.service.CommonDataServiceImpl;
import xbolt.cmm.service.CommonService;


@Service("schedulService")
@SuppressWarnings("unchecked")
public class SchedulActionServiceImpl extends CommonDataServiceImpl implements CommonService{

	@Override
	public void save(List lst, Map map) throws Exception{

		if("insert".equals(map.get("KBN").toString())){
			for (int i = 0; i < lst.size(); i++) {
				Map data =(HashMap) lst.get(i);
				insert(map.get("SQLNAME").toString(), data);
			}
		} 
		
		if("update".equals(map.get("KBN").toString())){
			for (int i = 0; i < lst.size(); i++) {
				Map data =(HashMap) lst.get(i);
				update(map.get("SQLNAME").toString(), data);
			}
		} 
		
		if("delete".equals(map.get("KBN").toString())){
			for (int i = 0; i < lst.size(); i++) {
				Map data =(HashMap) lst.get(i);
				delete(map.get("SQLNAME").toString(), data);
			}
		} 

	}

}

