package xbolt.custom.daelim.val;

import xbolt.cmm.framework.val.GetProperty;

public class DaelimGlobalVal {		
	private static String initFile = "otherSystemContent.properties";
	public static xbolt.cmm.framework.val.GetProperty dlproperty = new xbolt.cmm.framework.val.GetProperty(initFile);


	public static final String WEB_BP_URL = dlproperty.getProperty("IEP_POP_URL");
	public static final String DLM_WEBSERVICE_URL = dlproperty.getProperty("DLM_WEBSERVICE_URL");
	


}
