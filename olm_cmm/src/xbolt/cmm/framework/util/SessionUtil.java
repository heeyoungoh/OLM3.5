package xbolt.cmm.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 공통 서블릿 처리
 * @Class Name : SessionUtil.java
 * @Description : Session정보를제공한다.
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
public class SessionUtil {
	protected static Log _log = LogFactory.getLog(SessionUtil.class);

	/**
	 * Session String형 Attribute 값 저장
	 * @param name - Attribute name
	 * @param strData - Attribute value
	 * @return session
	 */
	public static HttpSession setSessionAttrString(HttpServletRequest request, String name, String data) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(name);
		session.setAttribute(name, data);
		return session;
	}
	
	/**
	 * Session integer형 Attribute 값 저장
	 * @param name - Attribute name
	 * @param strData - Attribute value
	 * @return session
	 */
	public static HttpSession setSessionAttrInt(HttpServletRequest request, String name, int data) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(name);
		session.setAttribute(name, data);
		return session;
	}
}
