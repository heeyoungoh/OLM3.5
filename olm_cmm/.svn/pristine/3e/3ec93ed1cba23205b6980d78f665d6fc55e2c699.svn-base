package xbolt.cmm.framework.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import xbolt.cmm.framework.val.GlobalVal;

/**
 * 공통 서블릿 처리
 * @Class Name : StringUtil.java
 * @Description : 문자열 관련 처리를 위해 제공한다.
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
public class StringUtil {
	public static String checkNull(String str) {
		return (str==null||str.equals(""))? "":str;
	}
	public static String checkNull(Object str) {		
		if( str == null ){
			return "";
		} else {
			return String.valueOf(str);
		}
	}

	public static String checkNullToBlank(Object str) {		
		if( str == null ){
			return " ";
		} else {
			return String.valueOf(str);
		}
	}
	public static String checkNull(Object str, String replaceStr) {
		if( str == null ){
			return replaceStr;
		} else {
			return String.valueOf(str);
		}
	}
	public static String toUpperCase(String str) {
		return (str==null)? "":str.toUpperCase();
	}
	public static String toLowerCase(String str) {
		return (str==null)? "":str.toLowerCase();
	}
	public static String toKR(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "EUC-KR"); //EUC-KR
		}catch (UnsupportedEncodingException ex) {
			return null;
		}
	}
	public static String toKrMs(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "MS949");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}
	public static String toAscii(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
			//		return str;
		}

		try {
			return new String(str.getBytes("EUC-KR"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}
	public static String replace(String original, String find, String replace) {
		if(original==null || find==null || replace==null || original.length()<1 || find.length()<1) {
			return original;
		}
		int index=-1, fromIndex=0, tempIndex;
		StringBuffer sb=new StringBuffer();
		while((tempIndex=original.indexOf(find, fromIndex))>=0)
		{
			index=tempIndex;
			sb.append(original.substring(fromIndex, index)).append(replace);
			fromIndex=index+find.length();
		}
		if(sb.length()<1) {
			return original;
		}

		sb.append(original.substring(index+find.length()));
		return sb.toString();
	}
	public static String leftPadding(String src, String ch, int num, boolean mode) {
		if(mode){
			return leftPadding(src, ch, num - getStringLength(src));
		}else{
			return leftPadding(src, ch, num);
		}
	}
	public static String leftPadding(String src, String ch, int num) {
		String result = "";
		if(src == null || src.length() >= num) return src;
		int cnt = num - src.length();

		for(int i=0; i < cnt; i++) result += ch;

		return result+src;
	}
	public static String RightPadding(String src, String ch, int num) {
		String result = "";
		if(src == null || src.length() >= num) return src;
		int cnt = num - src.length();
		for(int i=0; i < cnt; i++) result += ch;
		return src+result;
	}
	public static String trim(String str) {
		return trim(str, "");
	}
	public static String trim(String str, String defValue) {
		if (str == null || "".equals(str)) return defValue;
		else if (str.trim().length() == 0) return defValue;
		else return str.trim();
	}
	public static String trim(Object src, String defValue) {
		if (src != null) return src.toString().trim();
		else return defValue;
	}
	public static String getNumber(String string){
		if(string == null) return "";
		char[] source = string.toCharArray();
		char[] result = new char[source.length];
		int j = 0;
		for (int i = 0 , y = result.length; i < y ; i++ ){
			if (Character.isDigit(source[i])) {
				result[j++] = source[i];
			}
		}
		return new String(result, 0 , j);
	}
	public static int getStringLength(String str){
		char ch[] = str.toCharArray();
		int max = ch.length;
		int count = 0;

		for (int i = 0; i < max; i++)
		{
			if (ch[i] > 0x80) {
				count++;
			}
			count++;
		}
		return count;
	}
	public static String fillZero( int length, long lvalue ){
		String value = "" +lvalue;
		return fillZero( length, value );
	}
	public static String fillZero( int length, String value ){
		if (value == null ) return "";
		char[] cValue = value.toCharArray();
		for (int i = 0; i < cValue.length; i++)
		{
			if( !Character.isDigit( cValue[i] ) ) return "";
		}

		String result = value;
		int intLength = getStringLength(result);

		if(intLength == length)  return result;
		else if (intLength > length) return hanSubstr( length, value );

		for (int i = 0; i < length; i++)
		{
			result = "0" + result;
			i = getStringLength(result)-1;
		}
		return result;
	}
	public static String hanSubstr(int length, String value) {
		if (value == null || value.length() == 0) return "";
		int szBytes = value.getBytes().length;

		if (szBytes <= length) return value;

		String result = new String(value.getBytes(), 0, length);
		if( result.equals("") ) {
			result = new String(value.getBytes(), 0, length-1);
		}

		return result;
	}
	public static String getFormat(String str, String format) {
		if( format == null || format.equals("") ) format = "###,###,###.###";
		String temp = null;

		if (str == null) {
			temp = "0";
		} else {
			double change = Double.valueOf(str).doubleValue();
			DecimalFormat decimal = new DecimalFormat(format);
			temp = decimal.format(change);
		}

		return temp;
	}
	public static String getFormat(int istr, String format) {
		String str = Integer.toString(istr);
		if( format == null || format.equals("") ) format = "###,###,###.###";

		String temp = null;
		if (str == null) {
			temp = "0";
		} else {
			double change = Double.valueOf(str).doubleValue();
			DecimalFormat decimal = new DecimalFormat(format);
			temp = decimal.format(change);
		}

		return temp;
	}
	public static boolean isAlphanumeric(String str){
		return StringUtils.isAlphanumeric(str);
	}
	public static String getRandomStr() {
		Random random = new Random();
		long l = random.nextLong();

		return Long.toString(l);
	}
	public static String getReplace(String inputValue, String src, String dist){
		return inputValue.replaceAll(src, dist);
	}

	public static String[] toArray(Object src){
		return toArray((String)src);
	}
	public static String[] toArray(Object src, String div){
		return toArray((String)src, div);
	}
	public static String[] toArray(String src){
		String div = "|";
		if (src.indexOf(div)!=-1) {
			return toArray(src, "[|]");
		}
		else {
			return toArray(src, "[,]");
		}
	}
	public static String[] toArray(String src, String div){
		if(div.indexOf('[')==-1) {
			div = "[" +div + "]";
		}
		return src.split(div);
	}

	public static String toUnicode(String s) {
		StringBuffer uni_s = new StringBuffer();
		String temp_s = null;
		final String U = "%u";
		final String OO = "00";
		for( int i=0 ; i < s.length() ; i++){
			temp_s = Integer.toHexString( s.charAt(i) );
			uni_s.append( U).append((temp_s.length()==4 ? temp_s : OO + temp_s ) );
		}
		return uni_s.toString();
	}
	
	public static String getCalcStr(String str, int sLoc, int eLoc) {
		byte[] bystStr;
		String rltStr = "";
		try
		{
			bystStr = str.getBytes();
			rltStr = new String(bystStr, sLoc, eLoc - sLoc);
		}
		catch(Exception e)
		{
			return "";
		}
		return rltStr;
	}    
	public static String replaceByPattern(String text, String pattern, String newStr, boolean ignoreCase) {
		try {
			Pattern reg = Pattern.compile(pattern);
			Matcher retString = reg.matcher(text);
			StringBuffer sb = new StringBuffer();
			
			 
			if (retString.find()) {
				retString.appendReplacement(sb, newStr);
			}
			retString.appendTail(sb);
			
			return sb.toString();
			
		} catch (Exception e) { 
			e.printStackTrace();
			return text;
		}		
	}
	public static String replaceByPattern(String text, String pattern, String newStr) {
		return replaceByPattern(text, pattern, newStr, true);
	}

	public static String replaceFilterString(String text) {
		String newStr = text;
		newStr = newStr.replaceAll("&lt;script&gt;", "");
		newStr = newStr.replaceAll("&lt;/script&gt;", "");
		newStr = newStr.replaceAll("&lt;", "<");
		newStr = newStr.replaceAll("&gt;", ">");
		newStr = newStr.replaceAll("&quot;", "\"");
		newStr = newStr.replaceAll("alert", "");
		String [] str = {
"alert", "prompt","window.","DOMContentLoaded"
,"FSCommand","onAbort","onActivate","onAfterPrint","onAfterUpdate","onBeforeActivate","onBeforeCopy","onBeforeCut",
"onBeforeDeactivate","onBeforeEditFocus","onBeforePaste","onBeforePrint","onBeforeUnload","onBeforeUpdate","onBegin",
"onBlur","onBounce","onCellChange","onChange","onClick","onContextMenu","onControlSelect","onCopy","onCut","onDataAvailable",
"onDataSetChanged","onDataSetComplete","onDblClick","onDeactivate","onDrag","onDragEnd","onDragLeave","onDragEnter","onDragOver",
"onDragDrop","onDragStart","onDrop","onEnd","onError","onErrorUpdate","onFilterChange","onFinish","onFocus","onFocusIn","onFocusOut",
"onHashChange","onHelp","onInput","onKeyDown","onKeyPress","onKeyUp","onLayoutComplete","onLoad","onLoseCapture","onMediaComplete","onMediaError",
"onMessage","onMouseDown","onMouseEnter","onMouseLeave","onMouseMove","onMouseOut","onMouseOver","onMouseUp","onMouseWheel","onMove","onMoveEnd",
"onMoveStart","onOffline","onOnline","onOutOfSync","onPaste","onPause","onPopState","onProgress","onPropertyChange","onReadyStateChange",
"onRedo","onRepeat","onReset","onResize","onResizeEnd","onResizeStart","onResume","onReverse","onRowsEnter","onRowExit","onRowDelete",
"onRowInserted","onScroll","onSeek","onSelect","onSelectionChange","onSelectStart","onStart","onStop","onStorage","onSyncRestored","onSubmit",
"onTimeError","onTrackChange","onUndo","onUnload","onURLFlip","seekSegmentTime", "document.cookie","vbscript","expression",
"addEventListener","cached","onpointerover","confirm"}; 
		
		for(int i=0; i<str.length; i++) {
			while ((newStr.toLowerCase()).contains(str[i].toLowerCase())) {
				newStr = newStr.replaceFirst("(?i)"+str[i], "");
				if(!newStr.contains(str[i])) {
					i=-1;
					break;
				}
			}
		}
		return newStr;
	}
	
	public static String chkURL(String url, String protocol) {		
		Pattern pattern = Pattern.compile(protocol+"(.*)/(.*)/(.*)");
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()){
			return matcher.group(2);
		}
		else
			return "";
		
	}


	public static boolean chkAuthority(String type,String param, String authLvl) {		
		
		switch(type) {
		case "ADMIN" : 
				if("ADMIN".equals(param) && !"1".equals(authLvl)) {
					return false;
				}
		     	
		     	if(param.indexOf("/adm/") > -1 && !"1".equals(authLvl)) {
					return false;			
				}
		     	break;
		case "svUrl" : 
				
				if(param != null && param.indexOf(GlobalVal.OLM_SERVER_URL) == -1) {
					return false;		
				}
				
		     	break;
		case "brdEdit" : 
				
				if(!param.equals(authLvl)) {
					return false;		
				}
				
		     	break;
		case "brdDel" : 
				
				if(!"1".equals(authLvl)) {
					return false;		
				}
				
		     	break;
		case "brdNew" : 
			
			   String[] params = param.split(",");
			   if(params[0].equals("Y") && !params[1].equals(params[2]) ) {
				   return false;
			   }
			   
			   if(!params[0].equals("Y") && 
					   !params[3].equals(params[4]) && 
					   (Integer.valueOf(params[3]) > 0 || Integer.valueOf(authLvl) > 2)					    
				  ) {
				   return false;
			   }
		}
     	
     	return true;		
		
	}
}
