package xbolt.cmm.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XboltCrossFilter
  implements Filter
{
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
  }

  public void destroy()
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    ((HttpServletResponse) response).addHeader("X-Frame-Options", "SAMEORIGIN");
    ((HttpServletResponse) response).addHeader("X-XSS-Protection","1; mode=block");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin","*");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET,POST");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
    ((HttpServletResponse) response).addHeader("Set-Cookie", "name=value; path=/; MaxAge=-1; SameSite=Strict"); 
    //((HttpServletResponse) response).addHeader("X-XSS-Protection","1; mode=block");
    chain.doFilter(new XSSRequestWrapper((HttpServletRequest)request), 
      response);
  }
}