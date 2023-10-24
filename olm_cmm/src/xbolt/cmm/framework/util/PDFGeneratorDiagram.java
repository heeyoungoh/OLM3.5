package xbolt.cmm.framework.util;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;

import xbolt.cmm.framework.dhtmlx.xml2pdf.PDFWriter;



@SuppressWarnings("serial")
public class PDFGeneratorDiagram extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String xml;
		xml = req.getParameter("grid_xml");
		xml = URLDecoder.decode(xml, "UTF-8");
		
		Map imgInfo = new HashMap();
		imgInfo.put("imgName", StringUtil.checkNull(req.getParameter("drgFileName")));
		imgInfo.put("imgWidth", StringUtil.checkNull(req.getParameter("drgWidth")));
		imgInfo.put("imgHeight", StringUtil.checkNull(req.getParameter("drgHeight")));
		imgInfo.put("imgPercent", StringUtil.checkNull(req.getParameter("drgPercent")));
		
		(new PDFWriter()).generateDiagram(xml,imgInfo,resp);
	}
}
