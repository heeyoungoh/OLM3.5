package xbolt.cmm.framework.handler;

import org.springframework.context.support.MessageSourceAccessor;

/**
 * 공통 서블릿 처리
 * @Class Name : MessageHandler.java
 * @Description : 공통
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 * @see

 */
public class MessageHandler {
	private static MessageSourceAccessor messageAccessor;
	
	public void setMessageAccessor(MessageSourceAccessor messageSourceAccessor)	{
		MessageHandler.messageAccessor = messageSourceAccessor;
	}

	public static String getMessage(String key)	{
		return messageAccessor.getMessage(key, "ko");
	}

	public static String getMessage(String key, Object[] obj)	{
		return messageAccessor.getMessage(key, obj, "ko");
	}
}
