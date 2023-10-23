<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<%@ page import="xbolt.cmm.framework.util.StringUtil"%>
<%
/*
 * 파라메터 세팅부문
 */
int paramTotCnt		= "".equals(StringUtil.checkNull(request.getParameter("totCnt"),"")) ? 0:Integer.parseInt(request.getParameter("totCnt"));
int paramPage		= "".equals(StringUtil.checkNull(request.getParameter("page"),"")) ? 0:Integer.parseInt(request.getParameter("page"));
int paramScale		= "".equals(StringUtil.checkNull(request.getParameter("scale"),"")) ? GlobalVal.LIST_SCALE:Integer.parseInt(request.getParameter("scale"));
int paramPageScale	= "".equals(StringUtil.checkNull(request.getParameter("pageScale"),"")) ? GlobalVal.LIST_PAGE_SCALE:Integer.parseInt(request.getParameter("pageScale"));
%>
<script type="text/javascript">
var listScale = <%=paramScale%>;
var pageScale = <%=paramPageScale%>;
</script> 

