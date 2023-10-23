package xbolt.cmm.framework.val;

import xbolt.cmm.framework.util.StringUtil;

public class DrmGlobalVal {		
	private static String initFile = "otherSystemContent.properties";
	public static xbolt.cmm.framework.val.GetProperty DrmProperty = new xbolt.cmm.framework.val.GetProperty(initFile);
	
	public static final String DRM_KEY_ID = StringUtil.checkNull(DrmProperty.getProperty("DRM_KEY_ID"),""); //DRM_KEY ID
	public static final String DRM_DECODING_FILEPATH = StringUtil.checkNull(DrmProperty.getProperty("DRM_DECODING_FILEPATH"),""); 
	
	public static final String DRM_SOFTCAMP_KEY = StringUtil.checkNull(DrmProperty.getProperty("DRM_SOFTCAMP_KEY"));
	public static final String DRM_SOFTCAMP_MODULE_PATH = StringUtil.checkNull(DrmProperty.getProperty("DRM_SOFTCAMP_MODULE_PATH"));
	
	public static final String AIP_NAS = StringUtil.checkNull(DrmProperty.getProperty("AIP_NAS"));
	public static final String AIP_SVR_ADDR = StringUtil.checkNull(DrmProperty.getProperty("AIP_SVR_ADDR"));
	public static final String AIP_SYS_ID = StringUtil.checkNull(DrmProperty.getProperty("AIP_SYS_ID"));
	
}
