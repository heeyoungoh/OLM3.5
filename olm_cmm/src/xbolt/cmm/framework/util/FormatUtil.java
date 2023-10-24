package xbolt.cmm.framework.util;

//base class
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xbolt.cmm.framework.util.CastUtil;

/**
 * 공통 서블릿 처리
 * @Class Name : FormatUtil.java
 * @Description : 
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2014. 01. 13. smartfactory		최초생성
 *
 * @since 2014. 01. 13.
 * @version 1.0
 * @see
 * 
 * Copyright (C) 2013 by SMARTFACTORY All right reserved.
 */
public class FormatUtil {
	/**
	 * <pre>
	 * ex) matchFormat("20010101", "####/##/##") -> "2001/01/01"
	 *       matchFormat("12345678", "##/## : ##") -> "12/34 : 56"
	 * </pre>
	 * ----- Logging Comment -------
	 */ 
	public static String matchFormat(String str, String format) {
		if(str == null || str.length() == 0 ) return "";
		int len = format.length(); 
		char[] result = new char[len];
		for(int i=0,j=0; i<len; i++,j++) {
			if(format.charAt(i)=='#') {
				try {
					result[i]= str.charAt(j);
				}catch(StringIndexOutOfBoundsException e) {
					result[i]= '\u0000';
				}
			} else {
				result[i]= format.charAt(i);
				j--;
			}
		}
		return new String(result);
	}
	
	/**
	 * <pre>
	 * ex) releaseFormat("2001/01/01", "####/##/##") -> "20010101"
	 *       releaseFormat("123/456", "###/#########") -> "123456"
	 * </pre>
	 */
	public static String releaseFormat(String str, String format) {
		if(str == null || str.length() == 0 ) return str;
		int len = format.length();
		char[] result = new char[len];
		for(int i=0,j=0; i<len; i++,j++) {
			if(format.charAt(i)=='#') {
				try {
					result[j] = str.charAt(i);
				}catch(StringIndexOutOfBoundsException e) {
					result[j]= '\u0000';
				}
			} else {
				j--;
			}
		}
		return (new String(result)).trim();
	}

	/**
	 * <pre>
	 * ex) matchFormat(new Date(), "yyyy/MM/dd a hh:mm:ss") -> "2001/09/18 ���� 03:31:32"
	 * </pre>
	 */
	public static String matchFormat(Date dt, String format) {
		if (dt == null) return "";
		try {
			return new SimpleDateFormat(format).format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "invalid format";
 	}
 	
 	public static String toCurFormat(String arg) { return toCurFormat(CastUtil.toDbl(arg)); }
	public static String toCurFormat(double arg) { return new DecimalFormat("###,##0").format(arg); }
	
	/**
	 * <pre>
	 * ex) toPointFormat("35000", 1) -> "35000.1"
	 *      toPointFormat("35000", 234) -> "35000.234"
	 * </pre>
	 **/
	public static String toPointFormat(String arg, int pointDigit) { return toPointFormat(CastUtil.toDbl(arg), pointDigit); }
	
	/**
	 * <pre>
	 * ex) toPointFormat("35000.0", 123) -> "35000.123"
	 *      toPointFormat("35000.0", 432) -> "35000.432"* </pre>
	 **/
	public static String toPointFormat(double arg, int pointDigit) {
		if (pointDigit<=0) {
			return new DecimalFormat("##0").format(arg);
		} else {
			String formatStr = "##0.";
			//for (int i=0; i<pointDigit; i++) formatStr += "0";
			formatStr +=pointDigit;
			return new DecimalFormat(formatStr).format(arg);
		}
	}
	
	public static Date toDate(String str) {
		if(str == null || str.length() == 0 ) return null;
		String format = (str.length()==8)? "yyyyMMdd":(str.indexOf("-")>=0)? "yyyy-MM-dd":"yyyy/MM/dd";
		try {
			return new SimpleDateFormat(format).parse(str);
		} catch (ParseException ex) { return null; }
	}
	public static String getDate(String pattern) { 
		/*
		  <pattern>
		  G: Era designator
		  y: Year
		  M: Month in year
		  w: Week in year
		  W: Week in month
		  D: Day in year
		  d: Day in month
		  F: Day of week in month
		  E: Day in week
		  a: Am/pm marker
		  H: Hour in day (0-23)
		  k: Hour in day (1-24)
		  K: Hour in am/pm (0-11)
		  h: Hour in am/pm (1-12)
		  m: Minute in hour
		  s: Second in minute
		  S: Millisecond
		  z: Time zone
		  Z: Time zone
		*/

		SimpleDateFormat format = new SimpleDateFormat(pattern);
	    Date curDate = new Date();
	    return format.format(curDate);
	}
	public static String toPassword(String str, String format) {
		if(str == null || str.length() == 0 ) return "";
		if(format.length() != 1) return str;
		String result = "";
		for(int i=0; i<str.length(); i++) {
			result += format;
		}
		return result;
	}
	
	/**
     * ( \ / : * ? " < > |)
     * A file name cannot contain any of the following characters
     * Replace special characters
     */
    public static String makeValidFileName(String fileName) {
    	String ILLEGAL_EXP = "[:\\%*?:|\"<>]";
    	if(fileName == null || fileName.trim().length() == 0 )
            return String.valueOf(System.currentTimeMillis());  
    	return fileName.replaceAll(ILLEGAL_EXP, "").replace("/", "_");
    }
    
    /**
     * ( \, /, ?, *, [, ] )
     * A Sheet name cannot contain any of the following characters
     * Replace special characters
     */
    public static String makeValidSheetName(String sheetName) {
    	String ILLEGAL_EXP = "[\\*?\\[\\]]";
    	if(sheetName == null || sheetName.trim().length() == 0 || sheetName.trim().length() > 31 )
            return String.valueOf(System.currentTimeMillis());  
    	return sheetName.replaceAll(ILLEGAL_EXP, "").replace("/", "_");
    }
}
