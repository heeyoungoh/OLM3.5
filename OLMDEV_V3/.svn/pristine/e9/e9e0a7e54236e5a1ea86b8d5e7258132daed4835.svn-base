function getCookie(cookieName) {var search = cookieName + "=";var cookie = document.cookie;if (cookie.length > 0) {startIndex = cookie.indexOf(cookieName);if (startIndex != -1) {startIndex += cookieName.length;endIndex = cookie.indexOf(";", startIndex);if (endIndex == -1){endIndex = cookie.length;}return unescape(cookie.substring(startIndex + 1, endIndex));} else {return false;}} else {return false;}}
function setCookie(cookieName, value, expiredays) {
	var today = new Date(); today.setDate( today.getDate() + expiredays );
	/*기존 cookie정보를 읽어오기*/
	var cookie = document.cookie;var cookieValue="";
	var schData = "";
	if(cookieName=="sfolmLgId"){schData="sfolmLdNtc_";}else{schData="sfolmLgId";}
	if (cookie.length > 0) {
		startIndex = cookie.indexOf(schData);startIndex1 = startIndex;
		if (startIndex != -1) {startIndex += schData.length;startIndex = cookie.indexOf("=", startIndex);endIndex = cookie.indexOf(";", startIndex);
			if (endIndex != -1){
				cookieValue=unescape(cookie.substring(startIndex1, endIndex))+";";
			}
		}
	}
	document.cookie = cookieValue+cookieName + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";" ;
}