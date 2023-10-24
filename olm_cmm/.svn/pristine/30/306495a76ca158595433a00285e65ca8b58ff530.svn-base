package xbolt.cmm.framework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class SessionConfig implements HttpSessionListener{
	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
    //중복로그인 지우기
    public synchronized static String getSessionCheck(String type,String compareId, String delYN, String sessionID){
    	//System.out.println(" getSessionCheck 속성 :"+type+" , compareId :"+compareId+" , sessions :"+sessions);
        String result = "";
        
		for( String key : sessions.keySet() ){
			HttpSession hs = sessions.get(key);			
			if(hs != null &&  hs.getAttribute(type) != null && hs.getAttribute(type).toString().equals(compareId) ){
				result =  key.toString(); 
			}
		}
		String sessionRemove = "";
		//System.out.println("sessionID :"+sessionID+" , result:"+result);
		if(!delYN.equals("N")) {
			if(!sessionID.equals("") && sessionID != null) { result = sessionID; }
			
			sessionRemove = removeSessionForDoubleLogin(result);
		}else {
			sessionRemove = setSessionActive(result, compareId);
		}
		
		//System.out.println("after removeSessionForDoubleLogin sessions : " + sessions);
        return result;
    }
    
    public synchronized static String getSessionIDInfo(String type,String compareId, String currSessionActive){
    	//System.out.println("getSessionIDInfo 속성 :"+type+" , compareId :"+compareId+" , sessions :"+sessions+ ", currSessionActive :"+currSessionActive);
        String result = "";        
		for( String key : sessions.keySet() ){
			HttpSession hs = sessions.get(key);			
			if(hs != null &&  hs.getAttribute(type) != null && hs.getAttribute(type).toString().equals(compareId) ){
				if(currSessionActive.equals("Y") && !StringUtil.checkNull(hs.getAttribute("loginInfo")).equals("")) {
					result =  key.toString();	// 선입자 활성화된 session	
				}else if(StringUtil.checkNull(hs.getAttribute("sessionActive")).equals("N2")) {
					result =  key.toString();	// 선입자 끊길 예정인 session 
				}else if(!StringUtil.checkNull(hs.getAttribute("sessionActive")).equals("N")){ // session 비활성화가 아닌것
					result =  key.toString();
				}
			}
		}
        return result;
    }
    
    private static String setSessionActive(String sessionID, String loginID){   
    	String sesionActive = ""; 
		if(sessionID != null && sessionID.length() > 0){
			sessions.get(sessionID).setAttribute("login_id", loginID);
			sessions.get(sessionID).setAttribute("sessionActive", "N"); // 끊길 예정인 session 
			sesionActive = "N";
		}
		return sesionActive;
    }

    private static String removeSessionForDoubleLogin(String userId){   
    	String sessionRemove = ""; 
    	//System.out.println("removeSessionForDoubleLogin sessionId:"+userId);
		if(userId != null && userId.length() > 0){
			sessions.get(userId).invalidate();
			sessions.remove(userId);    	
			sessionRemove = "Y";
		}
		return sessionRemove;
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
    	//System.out.println("세션생성 :"+ hse.getSession().getId());
        sessions.put(hse.getSession().getId(), hse.getSession());
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
    	//System.out.println("세션삭제 :"+ hse.getSession().getId());
        if(sessions.get(hse.getSession().getId()) != null){
            sessions.get(hse.getSession().getId()).invalidate();
            sessions.remove(hse.getSession().getId());	
        }
    }
}    