<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>
<c:url value="/" var="root"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 OTitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<script src="${root}cmm/js/tinymce_v5/tinymce.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var chkReadOnly = true;
</script>
<script src="<c:url value='/cmm/js/xbolt/tinyEditorHelper.js'/>" type="text/javascript"></script>

<script type="text/javascript">
	
	$(document).ready(function(){	
		if(document.getElementById('htmlReport')!=null&&document.getElementById('htmlReport')!=undefined){
			document.getElementById('htmlReport').style.height = (height - 140)+"px";			

			window.onresize = function() {
				document.getElementById('htmlReport').style.height = (height - 160)+"px";	
			};
		}
		
	});
	
</script>
<body>
<form name="objectInfoFrm" id="objectInfoFrm" action="" method="post" onsubmit="return false;">
	<input type="hidden" id="s_itemID" name="s_itemID" value="${s_itemID}" />
	<input type="hidden" id="ArcCode" name="ArcCode" value="${option}"/>
	<input type="hidden" id="onlyMap" name="onlyMap"  value="" />
	<input type="hidden" id="paperSize" name="paperSize"  value="" />
	<input type="hidden" id="lovCode" name="lovCode"  value="" />
</form>
<!-- BIGIN :: -->
<form name="frontFrm" id="frontFrm" action="#" method="post" onsubmit="return false;"> 
<div id="htmltop">
</div>

<div class="clear"></div>

<div id="htmlReport" style="padding:15px;">
	<div id="process">
		<p class="cont_title mgB15">PAPAGO</p>
		<table class="tbl_blue01">
			<colgroup>
				<col width="20%">
				<col width="40%">
				<col width="40%">	
			</colgroup>
			<tr>
				<th class="last pdT10 pdB10">PlainText</th>
				<th class="last pdT10 pdB10">${menu.LN00365}</th>
				<th class="last pdT10 pdB10">${menu.LN00366}</th>
			</tr>
			<tr>
				<td class="alignL pdL10 last pdT10 pdB10">${attr.AT00003}</td>
				<td class="alignL pdL10 last pdT10 pdB10">
				
				<textarea class="tinymceText" style="width:100%;" readonly="readonly">
					<div class="mceNonEditable">${plainText} </div>		
				</textarea>
				</td>
				<td class="alignL pdL10 last pdT10 pdB10">
					<textarea class="tinymceText" style="width:100%;" readonly="readonly">
					<div class="mceNonEditable">${transPlainText} </div>		
					</textarea>
				</td>
			</tr>
		</table>
	</div>
	${transPlainText}
</div>
</form>
</body>
</head>
</html>

<!-- END :: FRAME -->
