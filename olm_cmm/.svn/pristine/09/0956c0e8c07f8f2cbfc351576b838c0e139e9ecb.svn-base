package xbolt.cmm.framework.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 공통 서블릿 처리
 * @Class Name : DateUtil.java
 * @Description : 날짜와 시간에 대한 처리 기능을 제공한다.
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
/*
 * 포맷의 형태는 다음을 참조하여 포맷형태를 만들면 된다.
 * <pre>
 *  Symbol   Meaning                 Presentation        Example
 *  ------   -------                 ------------        -------
 *  G        era designator          (Text)              AD
 *  y        year                    (Number)            1996
 *  M        month in year           (Text & Number)     July & 07
 *  d        day in month            (Number)            10
 *  h        hour in am/pm (1~12)    (Number)            12
 *  H        hour in day (0~23)      (Number)            0
 *  m        minute in hour          (Number)            30
 *  s        second in minute        (Number)            55
 *  S        millisecond             (Number)            978
 *  E        day in week             (Text)              Tuesday
 *  D        day in year             (Number)            189
 *  F        day of week in month    (Number)            2 (2nd Wed in July)
 *  w        week in year            (Number)            27
 *  W        week in month           (Number)            2
 *  a        am/pm marker            (Text)              PM
 *  k        hour in day (1~24)      (Number)            24
 *  K        hour in am/pm (0~11)    (Number)            0
 *  z        time zone               (Text)              Pacific Standard Time
 *  '        escape for text         (Delimiter)
 *  ''       single quote            (Literal)           '
 */
@SuppressWarnings("unused")
public class DateUtil {
	protected static Log _log = LogFactory.getLog(DateUtil.class);

	public static String getCurrentDay(){
		String result = "";
		try{
			/*
			CmcCcCommDao cmcCmCommDao = new CmcCcCommDao();
			Map paramMap = new HashMap();

			result = StringUtil.trim(cmcCmCommDao.getYYYYMMDD(paramMap), "");
			 */
		}catch (Exception e) {
			_log.error (StringUtil.toAscii("Exception:"), e);
		}

		return result;

	}
	public static String getCurrentTime(){
		return getCurrentTime(null);
	}
	public static String getCurrentTime(String format){
		if( format == null ) {
			format = "yyyyMMddHHmmssms";
		}

		return getTime(new Date(), format);
	}
	public static int getSysYearDay()	{
		return CastUtil.toInt(FormatUtil.matchFormat(new Date(), "yyyyMMdd"));
	}
	public static String getSysYearSecond()	{
		return FormatUtil.matchFormat(new Date(), "yyyyMMddHHmmss");
	}
	public static String getSysYear()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.YEAR));
	}
	public static String getSysMonth()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.MONTH) + 1);
	}
	public static String getSysDay()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	public static String getSysMonthDay()	{
		return FormatUtil.matchFormat(new Date(), "MMdd");
	}
	public static String getSysHour()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}
	public static String getSysMinute()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.MINUTE));
	}
	public static String getSysSecond()	{
		return CastUtil.toStr(Calendar.getInstance().get(Calendar.SECOND));
	}
	public static String getTime(String format){
		if(format == null || format.equals("")) {
			return "";
		}

		return getTime(new Date(), format);
	}
	public static String getTime(java.util.Date date, String format){
		if(date == null) return "";
		if(format == null || format.equals("")) format = "yyyy'년'MM'월'dd'일 'HH'시'mm'분'dd'초'";

		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}
	public static Date getDateMin(String date) {
		if (date==null || date.equals("") || !NumberUtil.isNumeric(date)) 	return null;
		int len = date.length();
		if (len<4 || len>8)		return null;
		
		return getDate(date.substring(0, 4), len>=6? date.substring(4, 6):"1", len>=8? date.substring(6, 8):"1", "0", "0", "0");
	}
	public static Date getDateMin(String year, String month, String day) {
		return getDate(year, month, day, "0", "0", "0");
	}
	public static Date getDateMin(int year, int month, int day) {
		return getDate(year, month, day, 0, 0, 0);
	}
	public static Date getDateMax(String date) {
		if (date==null || date.equals("") || !NumberUtil.isNumeric(date)) return null;
		int len = date.length();
		if (len<4 || len>8) 	return null;
		
		return getDate(date.substring(0, 4), len>=6? date.substring(4, 6):"1", len>=8? date.substring(6, 8):"1", "23", "59", "59");
	}
	public static Date getDateMax(String year, String month, String day) {
		return getDate(year, month, day, "23", "59", "59");
	}
	public static Date getDateMax(int year, int month, int day) {
		return getDate(year, month, day, 23, 59, 59);
	}
	public static Date getDate(String year, String month, String day, String hour, String minute, String second) {
		if (year.equals("") || month.equals("") || day.equals("") || hour.equals("") || minute.equals("") || second.equals("")) {
			return null;
		}
		return getDate(CastUtil.toInt(year), CastUtil.toInt(month), CastUtil.toInt(day), CastUtil.toInt(hour), CastUtil.toInt(minute), CastUtil.toInt(second));
	}
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static Date getDate(String datetime) throws Exception {
		String year, month, day, hour, minute, second;
		if (datetime==null ||"".equals(datetime) ||	datetime.length() != 14) 	return null;
		year = datetime.substring(0, 4);
		month = datetime.substring(4, 6);
		day = datetime.substring(6, 8);
		hour = datetime.substring(8, 10);
		minute = datetime.substring(10, 12);
		second = datetime.substring(12, 14);

		return getDate(CastUtil.toInt(year), CastUtil.toInt(month), CastUtil.toInt(day), CastUtil.toInt(hour), CastUtil.toInt(minute), CastUtil.toInt(second));
	}
	public static int getTimeInterval(Date d1, Date d2) throws Exception {
		int interval = 0;

		interval = (int)((d1.getTime() - d2.getTime()) / (60 * 60 * 1000));

		return interval;

	}
	public static String getTimeCalc(String strTime, int value, int nMode){
		String strRet = "";
		int hh = 0;	int mm = 0;
		if (strTime == null || strTime.length() != 4) return "";
		hh = Integer.parseInt(strTime.substring(0,2));
		mm = Integer.parseInt(strTime.substring(2));
		switch(nMode)
		{
		case 1: hh += value;break;
		case 2:	mm += value;break;
		default:
		}
		if (mm >= 60){
			hh += 1;
			mm -= 60;
		}
		if (hh >= 24) hh -= 24;

		strRet = hh + "" + mm;
		return strRet;
	}
	public static int getMinuteInterval(Date d1, Date d2) throws Exception {
		int interval = 0;
		interval = (int)((d1.getTime() - d2.getTime()) / (60 * 1000));
		return interval;
	}
	public static int getDateInterval(Date d1, Date d2) throws Exception {
		int interval = 0;
		interval = (int)((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
		return interval;
	}
	public static int getMonthInterval(Date d1, Date d2) throws Exception {
		int interval = 0;
		// 비교1 년도, 달
		int yyyy_d1 = CastUtil.toInt(getTime(d1, "yyyy"));
		int mm_d1 = CastUtil.toInt(getTime(d1, "MM"));
		// 비교2 년도, 달
		int yyyy_d2 = CastUtil.toInt(getTime(d2, "yyyy"));
		int mm_d2 = CastUtil.toInt(getTime(d2, "MM"));
		// 년도에 따른 달수 계산..
		int _month = ( yyyy_d2 - yyyy_d1 ) * 12;
		// 결과값
		interval = ( mm_d2 - mm_d1 ) + _month;

		return interval;
	}
	public static Date getDateAdd(Date date, int ymd, int amt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(ymd==1? Calendar.YEAR:ymd==2? Calendar.MONTH:ymd==3? Calendar.DAY_OF_MONTH:ymd==4? Calendar.HOUR:Calendar.MINUTE, amt);
		return cal.getTime();
	}
	public static Date getDateAdd(int ymd, int amt) {
		return getDateAdd(new Date(), ymd, amt);
	}
	public static String getDateCalc( String strDate, int intDate, String strymd ){
		int yyyy, mm, dd;
		strDate = StringUtil.getNumber(strDate);	//숫자아닌 다른 값 들어올 경우 처리
		String strRet = "";

		if (strDate.length() == 8){
			yyyy   = Integer.parseInt(strDate.substring(0,4));
			mm     = Integer.parseInt(strDate.substring(4,6));
			dd     = Integer.parseInt(strDate.substring(6,8));

			Calendar cal = Calendar.getInstance();
			cal.set(yyyy,mm-1,dd);
			if (strymd.equals("Y")){
				cal.add(Calendar.YEAR, + intDate);
			}else if (strymd.equals("M")){
				cal.add(Calendar.MONTH, + intDate);
			}else if (strymd.equals("D")){
				cal.add(Calendar.DATE, + intDate);
			}
			Date sdate = cal.getTime();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			strRet = date.format(sdate);
		}

		return strRet;
	}
	public static String DateTime(String datetime){
		if(datetime.length() == 14) return StringToDateTime(datetime);
		else return DateTimeToString(datetime);
	}
	public static String StringToDateTime(String datetime){
		if((datetime.length()<=0) || (datetime.length()!=14)) return datetime;

		return datetime.substring(0, 4) + "-" + datetime.substring(4, 6) + "-" + datetime.substring(6, 8) +
		" " + datetime.substring(8, 10) + ":" + datetime.substring(10, 12) + ":" + datetime.substring(12);
	}
	public static String DateTimeToString(String datetime){
		if((datetime.length()<=0) || (datetime.length()!=19)) {
			return datetime;
		}
		return datetime.substring(0, 4) + datetime.substring(5, 7) + datetime.substring(8, 10) +
		datetime.substring(11, 13) + datetime.substring(14, 16) + datetime.substring(17);
	}
	public static String Date(String date){
		if( date == null ) return "";
		if(date.length() == 8) return StringToDate(date);
		else return DateToString(date);
	}
	public static String StringToDate(String date){
		if( date == null ) return "";
		if((date.length()<=0) || (date.length()!=8)) return date;
	
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
	}
	public static String DateToString(String date){
		if( date == null ) return "";		
		if((date.length()<=0) || (date.length()!=10))	return date;
		
		return date.substring(0, 4) + date.substring(5, 7) + date.substring(8);
	}
	public static String getDayOfWeek(String date) {
		String result = "";
		if(date == null || date.length() < 8)  return result;
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.getDateMin(date.substring(0, 8)));
		switch(c.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY : result = "일"; break;
			case Calendar.MONDAY : result = "월"; break;
			case Calendar.TUESDAY : result = "화"; break;
			case Calendar.WEDNESDAY : result = "수"; break;
			case Calendar.THURSDAY : result = "목"; break;
			case Calendar.FRIDAY : result = "금"; break;
			case Calendar.SATURDAY : result = "토"; break;
		}

		return result;
	}
	public static int getIntDayOfWeek(String date) {
		int result = 0;
		if(date == null || date.length() < 8) return result;		
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.getDateMin(date.substring(0, 8)));
		return c.get(Calendar.DAY_OF_WEEK);
	}
	public static String getSystemDate(Date dDate, String strDateFormat){
		if (dDate == null) return "";
		SimpleDateFormat date = new SimpleDateFormat(strDateFormat);
		String strDate = date.format(dDate);
		return strDate;
	}
	public static String getLastDay(int year, int month){
		Calendar cal = Calendar.getInstance();
		cal.set(year,month,1);
		cal.add(Calendar.DATE, - 1);
		Date ydate = cal.getTime();
		SimpleDateFormat date = new SimpleDateFormat("dd");
		String strDate = date.format(ydate);
		return strDate;
	}
	public static String[] getHourList(){
		String[] list = new String[24];
		for(int i=0; i<24; i++){
			if(i<10) list[i] = "0" + i;
			else list[i] = "" + i;
		}
		return list;
	}
	public static String[] getMinuteList(){
		String[] list = new String[6];
		for(int i=0; i<6; i++){
			if(i<1)list[i] = "0" + i;
			else list[i] = "" + (i*10);
		}
		return list;
	}
	public static String toHourMinSec(int Second){
		String strHour = "" + (Second / 3600);
		String strMin = "" + ((Second % 3600) / 60);
		String strSec = "" + ((Second % 3600) % 60);
		if( strMin.length() == 1 ) strMin = "0" + strMin;
		if( strSec.length() == 1 ) strSec = "0" + strSec;
		return strHour + ":" + strMin + ":" + strSec;
	}
	public static String toHourMinSec(String Second){
		if (Second == null) {
			return "";
		}
		String hValue=null;
		String mValue=null;
		String sValue=null;
		String hmsValue=null;
		int hCal = 0;
		int mCal = 0;
		int mmCal = 0;
		int sCal = 0;
		int hmsCal=0;
		int hms = Integer.parseInt(Second);
		if(hms >= 3600){
			hCal = hms/3600;
			mCal = hms%3600;
			if(mCal >= 60){
				mmCal = mCal;
				mCal = mCal/60;
				sCal = mmCal%60;
			}else{
				sCal = mCal;
				mCal = 0;
			}
		}else if(hms >= 60){
			mCal =hms/60;
			sCal = hms%60;
		}else{
			sCal=hms;
		}
		hValue = String.valueOf(hCal).length() == 1 ? "0" + hCal : String.valueOf(hCal);
		mValue = String.valueOf(mCal).length() == 1 ? "0" + mCal : String.valueOf(mCal);
		sValue = String.valueOf(sCal).length() == 1 ? "0" + sCal : String.valueOf(sCal) ;
		hmsValue = hValue + " : " + mValue + " : " + sValue ;
		return hmsValue;
	}
	private static SimpleDateFormat logDateFormat	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DecimalFormat df = new DecimalFormat(",###");
	private static String monitorName 		= "";	// 모니터링명
	private static long currentTimeMillis 	= 0;	// 시스템 처리시간 모니터링
	private static double diffTime 			= 0;	// 시스템 처리시간
	
	public static void setCurrentTimeMillis( String name )
	{
		currentTimeMillis = System.currentTimeMillis();
		monitorName = name;
	}
	
	public static String isValidDate(String date) throws ParseException {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String format = sdf.format(sdf.parse(date));
		
		if (date.equals(format)) {
			result = date;
		} 
		
		return result;
	}
	
	
}
