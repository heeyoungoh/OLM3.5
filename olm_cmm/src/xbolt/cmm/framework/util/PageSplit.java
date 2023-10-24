package xbolt.cmm.framework.util;

//base class
import java.util.Enumeration;
import java.util.Hashtable;

import xbolt.cmm.framework.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 공통 서블릿 처리
 * @Class Name : PageSplit.java
 * @Description : 페이징 처리를 위해 제공한다.
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
public class PageSplit{
	
	private static String defaultTemplate = null;
	protected static Log __log = LogFactory.getLog(PageSplit.class);
	
	static {
		StringBuffer defaultTemplateBuff = new StringBuffer();
		defaultTemplateBuff.append("<table width=\"105\" height=\"12\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		defaultTemplateBuff.append("<tr>");
		
		defaultTemplateBuff.append("<!--FRONT_START-->");
		defaultTemplateBuff.append("<td width=\"21\" height=\"12\" align=\"right\" valign=\"bottom\">");
		//defaultTemplateBuff.append("<a <!--FIRST_LINK-->href=\"#\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image24','','./cmm/images/first_page_button02s.gif',1)\"><img src=\"./cmm/images/icon_firstpage.png\" name=\"Image24\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("<a <!--FIRST_LINK-->href=\"#\" ><img src=\"./cmm/images/icon_firstpage.png\" name=\"Image24\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("</td>");
		defaultTemplateBuff.append("<td width=\"13\" height=\"12\" align=\"right\" valign=\"bottom\">");
		//defaultTemplateBuff.append("<a <!--PREV_LINK-->href=\"#\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image23','','./cmm/images/back_button02s.gif',1)\"><img src=\"./cmm/images/icon_previous.png\" name=\"Image23\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("<a <!--PREV_LINK-->href=\"#\" ><img src=\"./cmm/images/icon_previous.png\" name=\"Image23\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("</td>");
		defaultTemplateBuff.append("<!--FRONT_END-->");

		defaultTemplateBuff.append("<!--LOOP_START-->");
		defaultTemplateBuff.append("<td width=\"25\" height=\"12\" align=\"center\" valign=\"bottom\">");
		defaultTemplateBuff.append("<!--PAGE_LINK--><!--PAGE_NUMBER-->"); 
		defaultTemplateBuff.append("</td>");
		defaultTemplateBuff.append("<!--LOOP_END-->");

		defaultTemplateBuff.append("<!--BACK_START-->");
		defaultTemplateBuff.append("<td width=\"13\" height=\"12\" align=\"left\" valign=\"bottom\">");
		//defaultTemplateBuff.append("<a <!--NEXT_LINK--> onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image22','','./cmm/images/next_button02s.gif',1)\"><img src=\"./cmm/images/icon_next.png\" name=\"Image22\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("<a <!--NEXT_LINK-->><img src=\"./cmm/images/icon_next.png\" name=\"Image22\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("</td>");
		defaultTemplateBuff.append("<td width=\"21\" height=\"12\" align=\"left\" valign=\"bottom\">");
		//defaultTemplateBuff.append("<a <!--LAST_LINK--> onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image21','','./cmm/images/end_page_button02s.gif',1)\"><img src=\"./cmm/images/icon_endpage.png\" name=\"Image21\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("<a <!--LAST_LINK-->><img src=\"./cmm/images/icon_endpage.png\" name=\"Image21\" width=\"12\" height=\"12\" border=\"0\"></a>");
		defaultTemplateBuff.append("</td>");
		defaultTemplateBuff.append("<td width=\"12\" height=\"12\"></td>");
		defaultTemplateBuff.append("<!--BACK_END-->");
		
		defaultTemplateBuff.append("</tr>");
		defaultTemplateBuff.append("</table>");
		defaultTemplate = defaultTemplateBuff.toString();
	}
	
	/**
	 * 페이징 처리를 한다 : 
	 * 
	 * @param int 총건수, int 페이지, int scale, int pageScale, int 링크 URL
	 * @return String
	 * @throws 
	 */
	public String getSplitPageLink(int total, int page, int scale, int pageScale, String url) {
		// check template and default splitLinkString
		String linkHtmlContent = defaultTemplate;

		// basic info
		if(total == 0)	{total = 1;}
		int totalPageCount = getTotalPageCount(total, scale);
		int startPage = getFirstPage(totalPageCount, page, pageScale);
		int repeatCount = getRepeatCount(totalPageCount, page, pageScale);	
		int endPage = startPage + repeatCount;
		
		int previousPage = getPreviousPage(totalPageCount, page, pageScale);
		int nextPage = getNextPage(totalPageCount, page, pageScale);

		// make one line string
		linkHtmlContent = StringUtil.replaceByPattern(linkHtmlContent, "\n", "", true);

		// replace first, prev, next, last Factor
		Hashtable partsHash = new Hashtable();
		String pageDelim = ((url).indexOf("?") >= 0) ? "&" : "?"; 
		
		if (isFirstPageSet(totalPageCount, page, pageScale)) {
			// remove front icons (first icon and prev icon)
			//linkHtmlContent = StringUtils.replaceByPattern(linkHtmlContent, "<!--FRONT_START-->.*?<!--FRONT_END-->", "", true);
			partsHash.put("FIRST_LINK_IMG", "<img src=\"./cmm/images/icon_firstpage.png\" name=\"Image24\" width=\"12\" height=\"12\" border=\"0\" alt=\"처음 목록\">");
			partsHash.put("PREV_LINK_IMG", "<img src=\"./cmm/images/icon_previous.png\" name=\"Image23\" width=\"12\" height=\"12\" border=\"0\" alt=\"이전 목록\">");
			//partsHash.put("FIRST_LINK", "href=\"#\"");
			partsHash.put("FIRST_LINK", "href=\"#\" onClick=\"movePage('" + startPage + "');\" title=\"" + 1 + "\"");
			partsHash.put("PREV_LINK" , "href=\"#\" "+ (page==1?"":"onClick=\"movePage('" + (page-1) + "');\"") +" ");
		} else {
			partsHash.put("FIRST_LINK_IMG", "<img src=\"./cmm/images/icon_firstpage.png\" name=\"Image24\" width=\"12\" height=\"12\" border=\"0\" alt=\"처음 목록\">");
			partsHash.put("PREV_LINK_IMG", "<img src=\"./cmm/images/icon_previous.png\" name=\"Image23\" width=\"12\" height=\"12\" border=\"0\" alt=\"이전 목록\">");
			partsHash.put("FIRST_LINK", "href=\"#\" onClick=\"movePage('" + previousPage + "');\" title=\"" + 1 + "\"");
			partsHash.put("PREV_LINK" , "href=\"#\" onClick=\"movePage('" + (page-1) + "');\"");
		}
		
		if (isLastPageSet(totalPageCount, page, pageScale)) {
			// remove back icons (next icon And last icon)
			//linkHtmlContent = StringUtils.replaceByPattern(linkHtmlContent, "<!--BACK_START-->.*?<!--BACK_END-->", "", true);
			partsHash.put("NEXT_LINK_IMG" , "<img src=\"./cmm/images/icon_next.png\" name=\"Image22\" width=\"12\" height=\"12\" border=\"0\" alt=\"다음 목록\">");
			partsHash.put("LAST_LINK_IMG" , "<img src=\"./cmm/images/icon_endpage.png\" name=\"Image21\" width=\"12\" height=\"12\" border=\"0\" alt=\"마지막 목록\">");
			partsHash.put("NEXT_LINK" , "href=\"#\" "+ (page==(endPage-1)?"":"onClick=\"movePage('" + (page+1)+ "');\"") +" ");
			//partsHash.put("LAST_LINK" , "href=\"#\"");
			partsHash.put("LAST_LINK" , "href=\"#\" onClick=\"movePage('" + (endPage-1) + "');\" title=\"" + (endPage-1) + "\"");
		} else {
			partsHash.put("NEXT_LINK_IMG" , "<img src=\"./cmm/images/icon_next.png\" name=\"Image22\" width=\"12\" height=\"12\" border=\"0\" alt=\"다음 목록\">");
			partsHash.put("LAST_LINK_IMG" , "<img src=\"./cmm/images/icon_endpage.png\" name=\"Image21\" width=\"12\" height=\"12\" border=\"0\" alt=\"마지막 목록\">");
			partsHash.put("NEXT_LINK" , "href=\"#\" onClick=\"movePage('" + (page+1)+ "');\"");
			partsHash.put("LAST_LINK" , "href=\"#\" onClick=\"movePage('" + nextPage + "');\" title=\"" + nextPage + "\"");			
		}

		Enumeration en = partsHash.keys();
		while (en.hasMoreElements()) {
			String key = (String)en.nextElement();
			linkHtmlContent = StringUtil.replaceByPattern(linkHtmlContent, "<!--" + key + "-->", (String)partsHash.get(key), true);
		}
		
		// cut number line template
		String[][] delimArray = {
			{"<!--LOOP_START-->" , "<!--PAGE_LINK-->"},
			{"<!--PAGE_LINK-->"  , "<!--PAGE_NUMBER-->"},
			{"<!--PAGE_NUMBER-->", "<!--LOOP_END-->"}
		};
		String[] templateParts = new String[delimArray.length];
		
		StringBuffer numberLinkBuff = new StringBuffer();
		for (int i=0; i < delimArray.length; i++) {
			int startDelimIndex = defaultTemplate.indexOf(delimArray[i][0]);
			int endDelimIndex   = defaultTemplate.indexOf(delimArray[i][1]);
			if (startDelimIndex >= 0 && endDelimIndex >= 0) { // make parts
				templateParts[i] = defaultTemplate.substring(startDelimIndex  + delimArray[i][0].length(), endDelimIndex);
			}
		}

		for (int i=startPage; i < endPage; i++) {
			numberLinkBuff.append(templateParts[0]);
			if (i == page) {
				numberLinkBuff.append(templateParts[1]);
				numberLinkBuff.append("<strong>&nbsp;"+i+"&nbsp;</strong>");
				numberLinkBuff.append(templateParts[2]);
			} else {
				numberLinkBuff.append("<a href=\"#\" onClick=\"movePage('" + i + "');\" class=\"no2\">" );
//				numberLinkBuff.append("<a href=\"" + url + pageDelim + "page=" + i + "\" >" );
				numberLinkBuff.append(templateParts[1]);
				numberLinkBuff.append("&nbsp;"+i+"&nbsp;");
				numberLinkBuff.append("</a>");
				numberLinkBuff.append(templateParts[2]);
			}

		}
		
		String loopStart = "<!--LOOP_START-->";
		String loopEnd   = "<!--LOOP_END-->";
		linkHtmlContent = StringUtil.replaceByPattern(linkHtmlContent, loopStart + ".*?" + loopEnd, numberLinkBuff.toString(), true);

		return linkHtmlContent;
	}

	/**
	 * 총 페이지 갯수를 얻는다.
	 * 
	 * @param int 총 갯수, int scale
	 * @return int
	 * @throws 
	 */
	public int getTotalPageCount(int total, int scale) {
		int remainder = total % scale;
		int quotient = total / scale;
		
		return (remainder > 0) ? quotient + 1 : quotient; 
	}

	/**
	 * 나머지 페이지 갯수를 얻는다.
	 * 
	 * @param int 총 페이지 갯수, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getPageRemainder(int totalPageCount, int pageScale) {
		int remainder = totalPageCount % pageScale;
		return remainder; 
	}
	
	/**
	 * 몫을 얻는다.
	 * 
	 * @param int 총 페이지 갯수, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getPageQuotient(int totalPageCount, int pageScale) {
		int quotient = totalPageCount / pageScale;
		
		return quotient; 
	}
	

	/**
	 * 현재 페이지가 소속된 페이지 묶음의 첫번째 페이지 번호를 얻는다.
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getFirstPage(int totalPageCount, int page, int pageScale) {
		int remainder = page % pageScale;
		int quotient = page / pageScale;
		
		return (remainder > 0) ? (quotient * pageScale)+1 : ((quotient-1) * pageScale)+1; 
	}

	/**
	 * 현재 페이지 묶음의 요소 갯수(반복 횟수)
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getRepeatCount(int totalPageCount, int page, int pageScale) {
		int repeatCount = -1;
		// 다음 pageSet 의 첫번째 번호
		if (isLastPageSet(totalPageCount, page, pageScale)) {
			repeatCount = (getPageRemainder(totalPageCount, pageScale) == 0) ? pageScale : getPageRemainder(totalPageCount, pageScale);
		} else {
			repeatCount = pageScale;	
		}
		
		return repeatCount;
	}
	
	/**
	 * 이전 페이지 묶음의 가장 뒷쪽 페이지
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getPreviousPage(int totalPageCount, int page, int pageScale) {
		int previousPage = -1;
		if (getCurrentPageSet(page, pageScale) > 1) {
			previousPage = getFirstPage(totalPageCount, page, pageScale) - 1;
		}
		return previousPage;
	}

	/**
	 * 다음 페이지 묶음의 첫번째 페이지를 얻어온다.
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getNextPage(int totalPageCount, int page, int pageScale) {
		int nextPage = -1;
		
		if (!isLastPageSet(totalPageCount, page, pageScale)) {
			nextPage = getFirstPage(totalPageCount, page, pageScale) + pageScale;	
		}
		return nextPage;
	}

	/**
	 * 페이지 번호로 현재 페이지 묶음을 얻어온다.
	 * 
	 * @param int page, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getCurrentPageSet(int page, int pageScale) {
		int remainder = page % pageScale;
		int quotient = page / pageScale;
		
		return (remainder > 0) ? quotient + 1 : quotient; 
	}
	
	/**
	 * 총 페이지 묶음의 값을 얻어온다.
	 * 
	 * @param int 총 페이지 갯수, int pageScale
	 * @return int
	 * @throws 
	 */
	public int getTotalPageSetCount(int totalPageCount, int pageScale) {
		int remainder = totalPageCount % pageScale;
		int quotient = totalPageCount / pageScale;
		
		return (remainder > 0) ? quotient + 1 : quotient; 
	}

	/**
	 * 현재 페이지 묶음이 첫번째면 true
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return boolean
	 * @throws 
	 */
	public boolean isFirstPageSet(int totalPageCount, int page, int pageScale) {
		boolean firstPageSetFlag = false;
		
		if (getFirstPage(totalPageCount, page, pageScale) == 1) {
			firstPageSetFlag = true;	
		}	
		return firstPageSetFlag;
	}

	/**
	 * 현재 페이지 묶음이 마지막이면 true;
	 * 
	 * @param int 총 페이지 갯수, int page, int pageScale
	 * @return boolean
	 * @throws 
	 */
	public boolean isLastPageSet(int totalPageCount,  int page, int pageScale) {
		boolean lastPageSetFlag = false;
		
		int currentPageSet = getCurrentPageSet(page, pageScale);
		int totalPageSetCount = getTotalPageSetCount(totalPageCount, pageScale);
		if (currentPageSet == totalPageSetCount) {
			lastPageSetFlag = true;		
		}
		return lastPageSetFlag;
	}
	
}