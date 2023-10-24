package xbolt.cmm.framework.util;

//base class
import java.text.DecimalFormat;
import java.text.ParseException;


/**
 * 공통 서블릿 처리
 * @Class Name : NumberUtil.java
 * @Description : 숫자관련 처리를 위해 제공한다.
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
public class NumberUtil {
	public static boolean isNumeric(String str) {
		try{
			Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	public static boolean isCurrency(String str) {
		try{
			new DecimalFormat().parse(str.trim());
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}
	public static int curToInt(String arg) {
		try {
			return new DecimalFormat().parse(arg.trim()).intValue();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	public static long curToLong(String arg) {
		try {
			return new DecimalFormat().parse(arg.trim()).longValue();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	public static int getIntValue(Object src, int defValue) {
		String v_value="";
		if (src != null && !src.equals("undefined")) {
			try {
				v_value = String.valueOf(src);
				String[] vv_value= v_value.split(".");
				if(vv_value!=null && vv_value.length>0){return Integer.parseInt(vv_value[0]);}
				else{return Integer.parseInt(v_value);}
			} catch (Exception e) {
				System.out.print("NumberUtil Exception(src="+src+") :"+e);
				return defValue;
			}
		} else {
			return defValue;
		}
	}
	public static int getIntValue(Object src) {
		if (src != null && !src.equals("undefined")) {
			try {
				return getIntValue(StringUtil.trim(src,"0"),0);
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}
	public static long getLongValue(Object src, long defValue) {
		String v_value;
		if (src != null) {
			try {
				v_value = String.valueOf(src);
				return Long.parseLong(v_value);
			} catch (Exception e) {
				return defValue;
			}
		} else {
			return defValue;
		}
	}
	public static long getLongValue(Object src) {
		if (src != null) {
			try {
				return getLongValue(StringUtil.trim(src,"0"),0);
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}
	public static float getFloatValue(Object src, float defValue) {
		String v_value;
		if (src != null) {
			try {
				v_value = String.valueOf(src);
				return Float.parseFloat(v_value);
			} catch (Exception e) {
				return defValue;
			}
		} else {
			return defValue;
		}
	}
	public static boolean getBoolValue(String key, boolean defValue) {
		if (key == null) {
			return defValue;
		} else {
			if (key.equalsIgnoreCase("true")) { return true; }
			else { return false; }
		}
	}
	
	public static double getDoubleValue(Object src, double defValue) {
		String v_value;
		if (src != null) {
			try {
				v_value = String.valueOf(src);
				return Double.parseDouble(v_value);
			} catch (Exception e) {
				return defValue;
			}
		} else {
			return defValue;
		}
	}	
}
