package xbolt.custom.sk.cts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.service.CommonService;
import xbolt.cmm.service.CommonDataServiceImpl;


@Service("ctsService")
@SuppressWarnings("unchecked")
public class CtsServiceImpl extends CommonDataServiceImpl implements CommonService{

	@Override
	public void save(Map param) throws Exception{

		try{
			insert("sk_cts_SQL.insertCTSFile", param);
		} catch(Exception e) {
			System.out.println("CTS FILE INSERT ERROR :: " + e.getMessage());
		}
	}

}
