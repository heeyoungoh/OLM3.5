<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="xbolt.cmm.framework.util.PageSplit"%>
<!-- 1. Include JSP -->
<%@ include file="pagingParam.jsp"%>	
 
<%
int scale = paramScale; 
int pageScale = paramPageScale;
int totalCnt = paramTotCnt;

PageSplit pageSplitMgr = new PageSplit();
String pageSplit = pageSplitMgr.getSplitPageLink(
						  totalCnt
						, paramPage
						, 10
						, pageScale
						, "");
%>
<%=pageSplit%>
