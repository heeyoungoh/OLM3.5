package xbolt.cmm.framework.util;


import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.http.*;

import xbolt.cmm.framework.dhtmlx.xml2pdf.PDFWriter;



@SuppressWarnings("serial")
public class PDFGenerator extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String xml;
		xml = req.getParameter("grid_xml");
		xml = URLDecoder.decode(xml, "UTF-8");
		(new PDFWriter()).generate(xml, resp);
	}
}
