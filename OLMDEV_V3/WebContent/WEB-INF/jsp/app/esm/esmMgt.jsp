<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><c:if test="${!empty htmlTitle}">${htmlTitle}</c:if></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/template/uiInc.jsp"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() {	
		var url = "${srTypeUrl}.do?srType=${srType}&scrnType=${scrnType}&mainType=${mainType}&srID=${srID}${varFilter}&reqDateLimit=${reqDateLimit}&defCategory=${defCategory}";
		$('#main').attr('src',url);	
	});
			
</script>
</head>
<body style="margin:0; text-align:center;">
<iframe name="main" id="main" width="100%" height="100%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0" align="center"></iframe>
</body>
</html>
