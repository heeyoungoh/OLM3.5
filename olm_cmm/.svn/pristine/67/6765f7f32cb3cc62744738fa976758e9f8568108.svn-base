package xbolt.cmm.framework.val;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@SuppressWarnings("rawtypes")
public class Session {
	public static final String USER_INFO = "loginInfo"; // 사용자정보
	
	public static void setUserInfo(HttpServletRequest request,Map userInfo){
		HttpSession session = request.getSession(true);
		session.setAttribute(USER_INFO, userInfo);
	}
	
	public static Map getUserInfo(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return (Map)session.getAttribute(USER_INFO);
	}

}
