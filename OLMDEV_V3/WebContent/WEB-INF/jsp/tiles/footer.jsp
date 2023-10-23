<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>
<div id="footersection" align = "center">
	<div class="footerL mgL30" >
		<span style="font-size:20px;font-color:#ECECEC;font-weight:700;">SFOLM</span>			
		<span style="font-size:10px;font-color:#465866;vertical-align:baseline;">
			&nbsp;&nbsp;&copy;
			<span id="currentYear" style="font-size:10px;font-color:#465866;vertical-align:baseline;"></span>
			Smartfactory Inc. All rights reserved.
		</span>
	</div>
</div>
<script>
	var curYear =new Date().getFullYear();
	document.getElementById("currentYear").innerHTML = curYear;
</script>
