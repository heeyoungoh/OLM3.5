package xbolt.cmm.framework.mxgraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import xbolt.cmm.framework.mxgraph.imageexport.Constants;
import xbolt.cmm.framework.util.NumberUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import com.objectplanet.image.PngEncoder;

/**
 * mxgraph image export 처리
 * @Class Name : imageExport.java
 * @Description : mxgraph image Export을  사용하기 위해 제공한다.
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
public class imageExport {

	private static transient Hashtable<String, Image> imageCache = new Hashtable<String, Image>();
	private static transient SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	
	public static boolean savaFile(Map cmmMap) throws Exception{
		boolean isResult=false;
		String fModelID = StringUtil.checkNull(cmmMap.get("fileModelID"));
		String fname = StringUtil.checkNull(cmmMap.get("filename"));
		
		String format = StringUtil.checkNull(cmmMap.get("format"));
		String tmp = StringUtil.checkNull(cmmMap.get("bg"));
		int w = NumberUtil.getIntValue(cmmMap.get("w"));
		int h = NumberUtil.getIntValue(cmmMap.get("h"));
		String xml =StringUtil.checkNull(cmmMap.get("imgXml"));
		// Png 파일 생성시 여백생기는 문제로 주석처리 hyoh 20141022 
		//if(h<NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_H)){h=NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_H);}
		//if(w<NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_W)){w=NumberUtil.getIntValue(GlobalVal.MODELING_DIGM_IMG_MIN_W);}

		
		Color bg = (tmp != null && !tmp.equals("none")) ? mxUtils.parseColor(tmp) : null;
		// Checks parameters
		if (w > 0 && w <= Constants.MAX_WIDTH && h > 0 && h <= Constants.MAX_HEIGHT && format != null && xml != null && xml.length() > 0)
		{
			// Allows transparent backgrounds only for PNG
			if (bg == null && !format.equals("png")){
				bg = Color.WHITE;
			}
			String fileName="";
			String langCode = StringUtil.checkNull(cmmMap.get("sessionCurrLangCode")).toUpperCase();
			if (fname != null && fname.toLowerCase().endsWith(".xml")){
				//fileName = fname.substring(0, fname.length() - 4) + format;
				fileName = fname.substring(0, fname.length() - 4) + format;
			}else if(fname != null){
				//fileName = fname.substring(0, fname.length() - 4) + "_"+langCode+"."+format;
				fileName = fModelID + "_"+langCode+"."+format;
			}
			isResult=writeImage(format, fileName, w, h, bg, xml);
			
			/*
			List returnData = new ArrayList();	
			Map blankMap = new HashMap();
			returnData = selectList("common_SQL.langType_commonSelect", blankMap);
			if( returnData !=null && returnData.size()>0){
				String langCode = "";//StringUtil.checkNull(cmmMap.get("sessionCurrLangCode")).toUpperCase();		
				String[] fileName=new String[returnData.size()];			
				for(int i=0;i<returnData.size();i++){
					Map langMap = (HashMap) returnData.get(i);
					langCode = StringUtil.checkNull(langMap.get("LANG_CODE")).toUpperCase();
			
					if (fname != null && fname.toLowerCase().endsWith(".xml")){
						fileName[i] = fname.substring(0, fname.length() - 4) + format;
					}else if(fname != null){
						fileName[i] = fname.substring(0, fname.length() - 4) + "_"+langCode+"."+format;
					}					
				}				
				writeImage(format, fileName, w, h, bg, xml);
			}
			*/
		}	
		
		return isResult;
	}
	private static boolean writeImage(String format, String[] fname, int w, int h, Color bg, String xml)
			throws IOException, SAXException, ParserConfigurationException
	{
		boolean isResult=false;
		BufferedImage image = mxUtils.createBufferedImage(w, h, bg);
		FileOutputStream out=null;
		try {
			if (image != null)
			{
				Graphics2D g2 = image.createGraphics();
				mxUtils.setAntiAlias(g2, true, true);
				renderXml(xml, createCanvas(g2));
	
				String stordFilePath = GlobalVal.MODELING_DIGM_DIR;
				File dFile = new File(stordFilePath);
				if (!dFile.isDirectory()) {	dFile.mkdir();}
				for(int i=0;i<fname.length;i++){
					String fileNm = stordFilePath + File.separator + fname[i];
					// Uses faster PNG encoder
					if (format.equalsIgnoreCase("png"))	{
						PngEncoder encoder = (bg != null) ? new PngEncoder() : new PngEncoder(PngEncoder.COLOR_TRUECOLOR_ALPHA);
					    out = new FileOutputStream(fileNm);
					    encoder.encode(image,out);
						//encoder.encode(image, response.getOutputStream());	
					}else{
						File cFile = new File(fileNm);
						ImageIO.write(image, format, cFile);				
						//ImageIO.write(image, format, response.getOutputStream());
					}
				}
			}
			
			isResult=true;
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {
				}
			}
		}
		
		return isResult;
	}
	private static boolean writeImage(String format, String fname, int w, int h, Color bg, String xml)
			throws IOException, SAXException, ParserConfigurationException
	{
		boolean isResult=false;
		BufferedImage image = mxUtils.createBufferedImage(w, h, bg);
		FileOutputStream out=null;
		try {
			if (image != null)
			{
				Graphics2D g2 = image.createGraphics();
				mxUtils.setAntiAlias(g2, true, true);
				renderXml(xml, createCanvas(g2));
	
				String stordFilePath = GlobalVal.MODELING_DIGM_DIR;
				File dFile = new File(stordFilePath);
				if (!dFile.isDirectory()) {	dFile.mkdir();}
				String fileNm = stordFilePath + File.separator + fname;
				// Uses faster PNG encoder
				if (format.equalsIgnoreCase("png"))	{
					PngEncoder encoder = (bg != null) ? new PngEncoder() : new PngEncoder(PngEncoder.COLOR_TRUECOLOR_ALPHA);
				    out = new FileOutputStream(fileNm);
				    encoder.encode(image,out);
					//encoder.encode(image, response.getOutputStream());	
				}else{
					File cFile = new File(fileNm);
					ImageIO.write(image, format, cFile);				
					//ImageIO.write(image, format, response.getOutputStream());
				}
			}
			
			isResult=true;
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {
				}
			}
		}
		
		return isResult;
	}	
	private static void renderXml(String xml, mxICanvas2D canvas) throws SAXException, ParserConfigurationException, IOException
	{
		XMLReader reader = parserFactory.newSAXParser().getXMLReader();
		reader.setContentHandler(new mxSaxOutputHandler(canvas));
		reader.parse(new InputSource(new StringReader(xml)));
	}	
	private static mxGraphicsCanvas2D createCanvas(Graphics2D g2)
	{
		// Caches custom images for the time of the request
		final Hashtable<String, Image> shortCache = new Hashtable<String, Image>();
		mxGraphicsCanvas2D g2c = new mxGraphicsCanvas2D(g2)
		{
			public Image loadImage(String src)
			{
				// We can't do SSL connections currently
				if (src.startsWith("https://") && src.length() > 8){
					src = "http://" + src.substring(8, src.length());
				}
				// Relative path handling
				if (!src.startsWith("http://")){
					src = Constants.IMAGE_DOMAIN + src;
				}
				// Match old domains used for image hosting
				for (String domain : Constants.IMAGE_DOMAIN_MATCHES){
					if (src.startsWith(domain) && src.length() > domain.length()){
						src = Constants.IMAGE_DOMAIN + src.substring(domain.length(), src.length());
					}
				}
				// Uses local image cache by default
				Hashtable<String, Image> cache = shortCache;
				// Uses global image cache for all server-side images
				if (src.startsWith(Constants.IMAGE_DOMAIN))	{
					cache = imageCache;
				}

				Image image = cache.get(src);
				if (image == null){
					image = super.loadImage(src);

					if (image != null){	cache.put(src, image);
					}else{	cache.put(src, Constants.EMPTY_IMAGE);	}
				}
				else if (image == Constants.EMPTY_IMAGE){
					image = null;
				}
				return image;
			}
		};

		//g2c.setAutoAntiAlias(true);
		return g2c;
	}
	//=========================================================
	//save image file : END
	//=========================================================	
}
