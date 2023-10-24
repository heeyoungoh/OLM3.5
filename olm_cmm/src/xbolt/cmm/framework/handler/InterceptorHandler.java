package xbolt.cmm.framework.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class InterceptorHandler extends HandlerInterceptorAdapter {

	private final Log _log = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	@SuppressWarnings({"unchecked","unused"})
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj) throws Exception{

		if(_log.isInfoEnabled())
		{
			_log.info("##################################################");
			_log.info("Class : InterceptorHandler; Function : preHandle");
			_log.info("Info:"+  req.getRequestURI());
		}

		Map<String, Object> map = WebUtils.getParametersStartingWith(req, null);
		if(_log.isInfoEnabled())
		{
			_log.info("##################################################");
		}		
		
		return super.preHandle(req, res, obj);

	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj, ModelAndView modelAndView) throws Exception{

		//		if(_log.isInfoEnabled())
		//		 {
		//			 _log.info("##################################################");
		//			 _log.info("########### post Handle ##########################");
		//		 }
		//
		//		 if(_log.isInfoEnabled())
		//		 {
		//			 _log.info("##################################################");
		//		 }
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res,
			Object obj, Exception e) throws Exception{
		//		if(_log.isInfoEnabled())
		//		 {
		//			 _log.info("##################################################");
		//			 _log.info("########### afterCompletion ######################");
		//		 }
		//
		//		 if(_log.isInfoEnabled())
		//		 {
		//			 _log.info("##################################################");
		//		 }
	}

}
