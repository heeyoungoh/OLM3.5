package xbolt.cmm.framework.val;

import java.io.*;
import java.util.Properties;

public class GetProperty
{
    private static Properties prop = null;
    private static String initFile = "olmWebContent.properties";
    //private static String initPath = "/uniwas_nas/dscc/app/";
    private static String initPath = "//WEB-INF//config//";

    static
    {
          initProperty();
    }

    public GetProperty(String str)
    {
        this.initFile = str; 
        initProperty();
    }

    public static void initProperty()
    {
    	InputStream is = null; 
        try
        {   
        	// Class Loader
            ClassLoader cl;
            cl = Thread.currentThread().getContextClassLoader();
            if( cl == null ) cl = ClassLoader.getSystemClassLoader();
            is =  cl.getResourceAsStream(initFile);
        	// 지정 경로에서 읽어오기 
        	/*
        	File file = new File(initPath+initFile);
        	is = new FileInputStream(file);
        	*/
                
            prop = new Properties();
            prop.load(is);
            is.close();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            if (is !=null )
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            System.out.println("ERROR : getProperty Open Error!!");
        }
    }
    
    public static void reloadProperty(){
        initProperty();
    }
    public static String getProperty(String value)
    {
        return prop.getProperty(value.trim(), "");
    }

    public static String getProperty(String value, String defaultvalue)
    {
        return prop.getProperty(value.trim(), defaultvalue);
    }

    public void setProperty(String key, String value)
    {
        prop.setProperty(key, value);
    }

    public void save(String header) throws IOException
    {
        java.io.OutputStream out = null;
        prop.store(out, header);
    }

    public void list(PrintStream out)
    {
        prop.list(out);
    }

}