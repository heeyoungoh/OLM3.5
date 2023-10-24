package xbolt.cmm.framework.util;

/**
 * 공통 서블릿 처리
 * @Class Name : CastUtil.java
 * @Description : 카멜표기법을 바로 사용하기 위해 제공한다.
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
public class CastUtil {

	/**
	 * <pre>
	 * int형을 문자열로 변환한다.
	 * 참고) byte,short형은 int로 자동형변환되어 실행된다.
	 * 활용 예) CastUtil.toStr(111); //->111
	 * </pre>
	 * @param	arg int값
	 * @return 	변화된 문자열
	 * ----- Logging Comment -------
	 * 
	 */
	public static String toStr(int arg) {return Integer.toString(arg); }

	/**
	 * <pre>
	 * long형을 문자열로 변환한다.
	 * </pre>
	 * @param	arg			long값
	 * @return 	변화된 문자열
	 * ----- Logging Comment -------
	 * 
	 */
	public static String toStr(long arg) { return Long.toString(arg); }

	/**
	 * <pre>
	 * double형을 문자열로 변환한다.
	 * 참고) float형은 double로 자동형변환되어 실행된다.
	 * 주의) double형의 경우 scale이 크면 'E'표시형식으로 나타남
	 * </pre>
	 * @param	arg			double값
	 * @return 	변화된 문자열
	 * ----- Logging Comment -------
	 * 
	 */
	public static String toStr(double arg) { return Double.toString(arg); }

	/**
	 * <pre>
	 * long형을 정수로 변환한다.
	 * 주의) 정수범위 초과시 데이타손실
	 * </pre>
	 * @param	arg			long값
	 * @return 	변환된 정수
	 * ----- Logging Comment -------
	 * 
	 */
	public static int toInt(long arg) { return new Long(arg).intValue(); }

	/**
	 * <pre>
	 * double형을 정수로 변환한다.
	 * 참고) float는 double로 자동형변환되어 실행된다.
	 * 주의) 정수범위 초과시 또는 소수이하의 값 데이타손실
	 * </pre>
	 * @param	arg			double값
	 * @return 	변환된 정수
	 * ----- Logging Comment -------
	 * 
	 */
	public static int toInt(double arg) { return new Double(arg).intValue(); }

	/**
	 * <pre>
	 * String형을 정수로 변환한다.
	 * </pre>
	 * @param	arg			숫자형태의 문자열
	 * @return 	변환된 정수
	 * @throws	NumberFormatException	문자열이 정수범위를 초과하거나 숫자 이외의 문자가 있을때
	 * ----- Logging Comment -------
	 * 
	 */
	public static int toInt(String arg) { return new Integer(arg.trim()).intValue(); }

	/**
	 * <pre>
	 * Object형을 정수로 변환한다.
	 * 참고) DB의 NUMBER 필드를 쿼리한 결과는 array에 Double형으로 저장되어 있다.
	 *       array에서 뽑아낸 Object형을 이 메소드를 통하여 정수형으로 변환할때 사용한다.
	 * 주의) Object는 Double형이어야 한다.
	 * </pre>
	 * @param	arg			Double형의 Object
	 * @return 	변환된 정수
	 * @throws	ClassCastException	Object가 Double형이 아닌경우
	 * ----- Logging Comment -------
	 * 
	 */
	public static int toInt(Object arg) { return ((Double)arg).intValue(); }

	/**
	 * <pre>
	 * String형을 double형으로 변환한다.
	 * </pre>
	 * @param	arg			문자열
	 * @return 	변환된 double
	 * @throws	NumberFormatException	문자열이 불량일때(숫자,period,E 이외의 문자)
	 * ----- Logging Comment -------
	 * 
	 */
	public static double toDbl(String arg) { return new Double(arg.trim()).doubleValue(); }


	/**
	 * <pre>
	 * String형을 int형으로 변환하여 반환한다.
	 * </pre>
	 * @param	intStr : 변환할 문자열
	 * @param	initValue : 변환 실패시 반환할 초기값
	 * @return 	변환된 int
	 * ----- Logging Comment -------
	 * 
	 */
	public static int intValue(String intStr, int initValue) {
		int retInt = initValue;
		try {
			if (intStr != null && intStr.trim().length() > 0) {
				retInt = Integer.parseInt(intStr.trim());
			}
		} catch (NumberFormatException e) {}
		return retInt;
	}
}
