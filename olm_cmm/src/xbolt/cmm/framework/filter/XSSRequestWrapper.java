package xbolt.cmm.framework.filter;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper
{
  private static Pattern escapePattern = Pattern.compile("'");
  private static Pattern unescapePttern = Pattern.compile("&#39;");

  private static Pattern[] patterns = { 
    Pattern.compile("<script>(.*?)</script>", 2), 
    Pattern.compile("%3Cscript%3E(.*?)%3C%2Fscript%3E", 2), 
    Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 
    42), 
    Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 
    42), 
    Pattern.compile("%3C%2Fscript%3E", 2), 
    Pattern.compile("%3Cscript(.*?)%3E", 42), 
    Pattern.compile("</script>", 2), 
    Pattern.compile("<script(.*?)>", 42), 
    Pattern.compile("eval\\((.*?)\\)", 42), 
    Pattern.compile("expression\\((.*?)\\)", 42), 
    Pattern.compile("javascript:", 2), 
    Pattern.compile("vbscript:", 2), 
    Pattern.compile("onload(.*?)=", 42),  
    Pattern.compile("'"), 
    Pattern.compile("\"")};

  public XSSRequestWrapper(ServletRequest servletRequest)
  {
    super((HttpServletRequest)servletRequest);
  }
  
  @Override
  public String[] getParameterValues(String parameter)
  {
    String[] values = super.getParameterValues(parameter);
    
    if (values == null)
    {
      return null;
    }

    int count = values.length;

    String[] encodedValues = new String[count];

    for (int i = 0; i < count; i++)
    {
    	if("diagram".equals(parameter) || "grid_xml".equals(parameter))
    		encodedValues[i] = stripXSS2(values[i]);
    	else
    		encodedValues[i] = stripXSS(values[i]);
    		
    }

    return encodedValues;
  }

  @Override
  public String getParameter(String parameter)
  {
	String value = super.getParameter(parameter);
    if("diagram".equals(parameter) || "grid_xml".equals(parameter))
		return stripXSS2(value);
	else
		return stripXSS(value);
  }
  
  @Override
  public String getHeader(String name)
  {
    String value = super.getHeader(name);

    return stripXSS(value);
  }

  public String stripXSS(String value)
  {
    if (value != null)
    {
      value = value.replaceAll("", "");

      for (Pattern scriptPattern : patterns) {
        if (scriptPattern.matcher(value).find()) {
          value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
      }
    }
    return value;
  }
  
  public String stripXSS2(String value)
  {
    if (value != null)
    {
      value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
    return value;
  }
}