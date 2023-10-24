package xbolt.cmm.framework.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.val.GlobalVal;

/**
 * 공통 서블릿 처리
 * @Class Name : JsonUtil.java
 * @Description : Json관련 처리를 위해 제공한다.
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
@SuppressWarnings("unchecked")
public class JsonUtil {
	protected static Log _log = LogFactory.getLog(JsonUtil.class);

	private final static String IMG_NEW_SRC = "@{new}";
	private final static String IMG_NEW_TARGET_OPEN = "<img src='";
	private final static String IMG_NEW_TARGET_CLOSE = "/images/btn_star.gif' width='13' height='13' />";

	/**
	 * MapToString
	 * @param map
	 * @param res
	 * @return void
	 * @exception
	 */
	public static void getConvertMap(Map<String, Object> map, HttpServletResponse res){
		JSONArray jObj = JSONArray.fromObject(map);
		sendToJson(jObj.toString(), res);
	}
	public static void returnGridJson(List list, final String[]cols, HttpServletResponse res, final String contextPath){
		sendToJson(parseGridJson(list, cols, 999999,1, contextPath), res);
	}
	public static void returnGridJson(List list, final String[]cols, final int totalPage, final int pageNum, HttpServletResponse res, final String contextPath){
		sendToJson(parseGridJson(list, cols, totalPage,pageNum, contextPath), res);
	}
	public static void returnGridMergeJson(List list1, List list2, final String[]cols1, final String[]cols2, final int totalPage, HttpServletResponse res, final String contextPath){
		sendToJson(parseGridMergeJson(list1, list2, cols1, cols2, totalPage, contextPath), res);
	}
	public static void returnTreeJson(List list, final String[]cols, HttpServletResponse res, final String contextPath){
		sendToJson(parseTreeJson(list, cols,contextPath), res);
	}
	public static void returnChartJson(List list, final String[]cols, HttpServletResponse res, final String contextPath){
		sendToJson(parseChartJson(list, cols,contextPath), res);
	}	
	public static void returnJson(List list, final String[]cols, HttpServletResponse res, final String contextPath){
		sendToJson(parseJson(list, cols,contextPath), res);
	}


	/**
	 * ListToString
	 * @param list
	 * @param res
	 * @return void
	 * @exception
	 */
	public static void getConvertList(List<Object> list, HttpServletResponse res){
		JSONArray jObj = JSONArray.fromObject(list);
		sendToJson(jObj.toString(), res);
	}


	public static void sendToJson(String jObj, HttpServletResponse res) {
		try {
			res.setHeader("Cache-Control", "no-cache");
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			if(!jObj.equals("{rows: [ ]}")){
				res.getWriter().print(jObj);
			}
			else {
				PrintWriter pw = res.getWriter();
				pw.write("데이터가 존재하지 않습니다.");
			}

			if(_log.isInfoEnabled())
			{
				_log.info("################################");
				_log.info("json Object :"+jObj);
				_log.info("################################");
			}
		} catch (IOException e) {
			MessageHandler.getMessage("json.send.message");
			e.printStackTrace();
		}
	}

	public static String parseGridJson(List list, final String[]cols, final int totalPage, final int pageNum, final String contextPath) {
		final String OPEN = "{ id : \"";
		final String DATA = "\", data : [ \"";
		final String COT = "\"";
		final String COMMA = ",";

		StringBuffer result = new StringBuffer("{ totalPage:"+totalPage+",pageNum:"+pageNum+",rows:[ ");

		if (list != null && list.size()>0 && cols != null && cols.length > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					result.append(COMMA);
				}
				Map map = (Map) list.get(i);
				result.append(OPEN).append(map.get("RNUM")).append(DATA).append(map.get("RNUM")).append(COT);

				for (String string : cols) {
					result.append(COMMA).append(COT).append(get(map.get(string), contextPath)).append(COT);
				}
				result.append("]}");
			}
		}

		result.append("]}");
		return result.toString();
	}
	
	public static String parseGridMergeJson(List list1, List list2, final String[]cols1, final String[]cols2, final int totalPage, final String contextPath) {
		final String OPEN = "{ id : \"";
		final String DATA = "\", data : [ \"";
		final String COT = "\"";
		final String COMMA = ",";

		StringBuffer result = new StringBuffer("{ totalPage : "+totalPage+", rows: [ ");

		if (list1 != null && list1.size()>0 && cols1 != null && cols1.length > 0) {
			for (int i = 0; i < list1.size(); i++) {
				if (i != 0) {
					result.append(COMMA);
				}
				Map map = (Map) list1.get(i);
				result.append(OPEN).append(map.get("RNUM")).append(DATA).append(map.get("RNUM")).append(COT);

				for (String string : cols1) {
					result.append(COMMA).append(COT).append(get(map.get(string), contextPath)).append(COT);
				}
				
				if( list2 != null && list2.size() > 0 && cols2 != null && cols2.length > 0){
					for (String string : cols2) {
						Object value = null;
						for(int j=0; j<list2.size(); j++){
							Map map2 = (Map) list2.get(j);
							if(map.get("MyItemID").equals(map2.get("MyItemID")) && map2.get("AttrType").equals(string)){
								value = get(map2.get("AttrValue"), contextPath);
							}
						}
						
						result.append(COMMA).append(COT).append(StringUtil.checkNull(value, "")).append(COT);
					}
				}
				result.append("]}");
			}
		}

		result.append("]}");
		return result.toString();
	}
	
	
	public static String parseTreeJson(List list, final String[]cols,final String contextPath) {
		final String OPEN = "[";
		final String COT = "\"";
		final String COMMA = ",";
		final String CLOSE = "]";

		StringBuffer result = new StringBuffer();

		if (list != null && list.size()>0 && cols != null && cols.length > 0) {
			result.append(OPEN);
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					result.append(COMMA);
				}
				Map map = (Map) list.get(i);
				result.append(OPEN);
				int j=0;
				for (String string : cols) {
					if (j>0) {
						result.append(COMMA);
					}
					result.append(COT).append(get(map.get(string), contextPath)).append(COT);
					j++;
				}
				result.append(CLOSE);
			}
			result.append(CLOSE);
		}
		//result.append("]}");
		return result.toString();
	}
	
	public static String parseChartJson(List list, final String[]cols, final String contextPath) {
		final String OPEN = "[";
		final String COT = "\"";
		final String COMMA = ",";
		final String CLOSE = "]";
		final String SEM = ":";
		String[] chartColor = GlobalVal.CHART_COLOR_8.split(" ");

		StringBuffer result = new StringBuffer();
		int minValue = 0;
		int maxValue = 0;

		if (list != null && list.size()>0 && cols != null && cols.length > 0) {
			result.append("[");
			int c=0;
			for (int i = 0; i < list.size(); i++) {
				if(c==8){c=0;}
				if (i != 0) {
					result.append(COMMA);
				}
				Map map = (Map) list.get(i);
				int value = NumberUtil.getIntValue(map.get("value"), 0);
				if( i== 0){
					minValue = value;
					maxValue = value;
				}
				else{
					if( minValue > value) minValue = value;
					if( maxValue < value) maxValue = value;
				}
				
				result.append("{");
				int j=0;
				for (String string : cols) {
					if (j>0) {
						result.append(COMMA);
					}
					result.append(string).append(SEM);
					result.append(COT).append(get(map.get(string), contextPath)).append(COT);
					j++;
				}
				result.append(COMMA);
				result.append("color").append(SEM);
				result.append(COT).append(get(chartColor[c], contextPath)).append(COT);
				
				result.append("}");
				c++;
			}
			
			result.append("]");
		}
		//System.out.println("chart json"+result.toString());
		return result.toString();
	}	
	//var dataset=[{id:1,sales:20,year:"02"},{id:2,sales:55,year:"03"},{id:3,sales:40,year:"04"},{id:4,sales:78,year:"05"},{id:5,sales:61,year:"06"},{id:6,sales:35,year:"07"},{id:7,sales:80,year:"08"},{id:8,sales:50,year:"09"},{id:9,sales:65,year:"10"},{id:10,sales:59,year:"11"}];var dataset_colors=[{id:1,sales:20,year:"02",color:"#ee4339"},{id:2,sales:55,year:"03",color:"#ee9336"},{id:3,sales:40,year:"04",color:"#eed236"},{id:4,sales:78,year:"05",color:"#d3ee36"},{id:5,sales:61,year:"06",color:"#a7ee70"},{id:6,sales:35,year:"07",color:"#58dccd"},{id:7,sales:80,year:"08",color:"#36abee"},{id:8,sales:50,year:"09",color:"#476cee"},{id:9,sales:65,year:"10",color:"#a244ea"},{id:10,sales:59,year:"11",color:"#e33fc7"}];var small_dataset=[{sales:35,year:"07"},{sales:50,year:"08"},{sales:65,year:"09"},{sales:30,year:"10"},{sales:45,year:"11"}];];
	public static String parseJson(List list, final String[]cols, final String contextPath) {
		final String OPEN = "[";
		final String COT = "\"";
		final String COMMA = ",";
		final String CLOSE = "]";
		final String SEM = ":";

		StringBuffer result = new StringBuffer();
		int minValue = 0;
		int maxValue = 0;

		if (list != null && list.size()>0 && cols != null && cols.length > 0) {
			result.append("[");
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					result.append(COMMA);
				}
				Map map = (Map) list.get(i);
				int value = NumberUtil.getIntValue(map.get("value"), 0);
				if( i== 0){
					minValue = value;
					maxValue = value;
				}
				else{
					if( minValue > value) minValue = value;
					if( maxValue < value) maxValue = value;
				}
				
				result.append("{");
				int j=0;
				for (String string : cols) {
					if (j>0) {
						result.append(COMMA);
					}
					result.append(string).append(SEM);
					result.append(COT).append(get(map.get(string), contextPath)).append(COT);
					j++;
				}
				result.append("}");
			}
			
			result.append("]");
		}

		return result.toString();
	}
	
	/**
	 * 엔터를 <br/>로 변환한다.
	 * @param object
	 * @return
	 */
	private static Object get(Object object, final String contextPath) {
		if(object == null) {
			return "";
		}
		if(object instanceof java.lang.String) {
			String result = StringUtil.replace((String)object, "\n", "<br/>");
			result = StringUtil.replace(result, IMG_NEW_SRC, IMG_NEW_TARGET_OPEN+contextPath + IMG_NEW_TARGET_CLOSE);
			result = StringUtil.replace(result, "\r", "<br/>");
			if(result.indexOf('"')!=-1) {
				result = StringUtil.replace(result, "\"", "＂");
			}
			if(result.indexOf('\\')!=-1) {
				result = StringUtil.replace(result, "\\", "/");
			}
			return result;
		}
		return object;
	}
}
