var _sumOption = "누계";
var _totalOption = "==전체==";
var _selectOption = "==선택==";
var _ajaxErrMsg = "로딩중 장애가 발생했습니다.\n\n";//
var _functionErrMsg = "장애가 발생했습니다. 관리자에게 문의하십시오.\n(화면이미지 저장요망)";
var _sToday = "";
var _dateType = "";
var _datecount = 0;
var _dateItemName = "";

function expireSession(){location.href= "/";return eval( '({"info":[{"cnt":0,"mag":"세션이 만료되었습니다.\\n다시 로그인 해주시기 바랍니다.","pageNo":1,"rsultCd":"0","stus":"FAIL","totalCnt":0}],"items":null})' );}

function fnDateCompare(start_date, end_date){var start_dates = start_date.split("-");var end_dates = end_date.split("-");var date1 = new Date(start_dates[0],start_dates[1],start_dates[2]).valueOf();var date2 = new Date(end_dates[0],end_dates[1],end_dates[2]).valueOf();return date2 - date1;}

function fnDateSet(value, id){$('#'+id).val( makeDateType(value) );}

function doGetBeforeNextMonth(getYm,cal){if ( $("#"+getYm).val() == ""){alert("기준년월을 입력하세요.");$("#"+getYm).focus();return false;}var yyyymm = $("#"+getYm).val();var yyyy = yyyymm.substring(0,5);var mm = yyyymm.substring(5,7);if(mm == "02" && cal == "-"){mm = "01";setMonth = yyyy+mm;}else if(mm == "02" && cal == "+"){mm = "03";etMonth = yyyy+mm;}else{yyyymm = yyyymm+"-"+"01";setMonth = calcDate('m',yyyymm,'1',cal,'-');setMonth = setMonth.substring(0,7);}$("#"+getYm).val(setMonth);}

String.prototype.delMask = function() {var comma = /,/gi;var slash = /\//gi;var hipen = /-/gi;var collon = /:/gi;if ( this.length == 0 ) {return "";}return this.replace(comma,"").replace(slash,"").replace(hipen,"").replace(collon,"");};

function calcDate(aInterval, aValue1, aValue2, aMode, aDateFormat) {var lDate = new Date();var lYear = "";var lMonth = "";var lDay = "";aValue1 = aValue1.delMask();aValue2 = aValue2.delMask();if (aDateFormat == null || aDateFormat == "") {aDateFormat = "/";}if (aValue1.length != 8) {return 0;}lDate.setFullYear(aValue1.substring(0, 4));lDate.setMonth(aValue1.substring(4, 6) - 1);lDate.setDate(aValue1.substring(6));switch (aInterval) {case "m":case "M":if (aMode == "+") {lDate.setMonth(lDate.getMonth() + parseInt(aValue2));} else {lDate.setMonth(lDate.getMonth() - parseInt(aValue2));}break;case "d":case "D":if (aMode == "+") {lDate.setDate(lDate.getDate() + parseInt(aValue2));} else {lDate.setDate(lDate.getDate() - parseInt(aValue2));}break;default:return 0;}lYear  = lDate.getFullYear();lMonth = lDate.getMonth() + 1;lDay   = lDate.getDate();if (lYear.length == 2) {ldYear = "19" + lYear;}if (parseInt(lMonth) < 10) {lMonth = "0"  + lMonth;}if (parseInt(lDay)   < 10) {lDay   = "0"  + lDay;}return lYear + aDateFormat + lMonth + aDateFormat + lDay;}

function removeIsNotNumeric( num ) {var result = "";for (var i=0; i<num.length; i++) {var ch = num.charAt(i);if ( ch < '0' || ch > '9' ) {}else{result += ch;}}if (!isValidDateForCal(result)) {result = "";}return result;}

//function makeTelType(str){str = removeIsNotNumeric(str);var len = str.length;var returnVal = "";if(str == null || str == ""){return returnVal;}if( str.length <= 2 ){return returnVal;}else{if( str.indexOf("02") == 0 ){returnVal += str.substr(0,2) + " " + str.substr(2,len);}else if(str.substr(0,1) != "0"){returnVal += "02" + " " + str;}else{returnVal = str.substr(0,3) + " " + str.substr(3,len);}}return returnVal;}

function allFormElementsClear(docs){var len=docs.forms.length;for(var i=0;i<len;i++){formElementsCleared(docs.forms[i]);}}

function formElementsCleared(frm){var len=frm.elements.length;var type;for(var i=0;i<len;i++){type = frm.elements[i].type.toLowerCase();if(type=='text' || type=='hidden' || type=='textarea' || type=='password'){frm.elements[i].value = "";}else if(type=='radio'){frm.elements[0].checked = true;}else if(type=='checkbox'){frm.elements[0].checked = true;}else if(type.substring(0,7) == 'select-'){frm.elements[i].options[0].selected = true;}}}

function getRadioButtonValue( obj ){var _ret = "";if( obj.length == null || obj.length == "undifined" ){_ret = obj.value;}else{for(var i=0; i<obj.length; i++){if( obj[i].checked ){_ret = obj[i].value;break;}}}return _ret;}

function setRadioButtonValue( obj, arg1 ){if( obj.length == null || obj.length == "undifined" ){if( obj.value == arg1 ) {obj.checked = true;}}else{for(var i=0; i<obj.length; i++){if( obj[i].value == arg1 ) {obj[i].checked = true;}}}}

function isAvailableBytes( msg , nbytes ){return (getMsgByte(msg) > nbytes) ? false : true;}

function getMsgByte( msg ){var nbytes = 0;for (var z=0; z<msg.length; z++) {var ch = msg.charAt(z);if(escape(ch).length > 4) {nbytes += 2;} else if (ch == '\n') {if (msg.charAt(z-1) != '\r') {nbytes += 1;}else{nbytes += 1;}} else if (ch == '<' || ch == '>') {nbytes += 4;} else {nbytes += 1;}}return nbytes;}

function getMsgByte2( msg, byte ){var nbytes = 0;var rtnmsg = '';for (var z=0; z<msg.length; z++) {var ch = msg.charAt(z);if(escape(ch).length > 4) {nbytes += 2;} else if (ch == '\n') {if (msg.charAt(z-1) != '\r') {nbytes += 1;}else{nbytes += 1;}} else if (ch == '<' || ch == '>') {nbytes += 4;} else {nbytes += 1;}if( nbytes <= byte ){rtnmsg += ch;}}return rtnmsg;}

function makeDateType(str){var returnVal = "";if(str == null || str == ""){return returnVal;}str = removeIsNotNumeric(str);switch(str.length){case 6 :returnVal = str.substr(0,4) + "-" + str.substr(4,2);break;case 8 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2);break;case 12 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " " + str.substr(8,2) + ":" + str.substr(10,2);break;case 14 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " " + str.substr(8,2) + ":" + str.substr(10,2) + ":" + str.substr(12,2);break;case 16 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " ~ " + str.substr(8,4) + "-" + str.substr(12,2) + "-" + str.substr(14,2);break;default :}return returnVal;}

function makeDateType(str,ymd){var returnVal = "";if(str == null || str == ""){return returnVal;}str = removeIsNotNumeric(str);switch(str.length){case 6 :returnVal = str.substr(0,4) + "-" + str.substr(4,2);break;case 8 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2);break;case 12 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " " + str.substr(8,2) + ":" + str.substr(10,2);break;case 14 :if(ymd == "YMD"){returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2);}else{returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " " + str.substr(8,2) + ":" + str.substr(10,2) + ":" + str.substr(12,2);}break;case 16 :returnVal = str.substr(0,4) + "-" + str.substr(4,2) + "-" + str.substr(6,2) + " ~ " + str.substr(8,4) + "-" + str.substr(12,2) + "-" + str.substr(14,2);break;default :}return returnVal;}

function makeTimeType(str){var returnVal = "";if(str == null || str == ""){return returnVal;}switch(str.length){case 4 :returnVal = str.substr(0,2) + ":" + str.substr(2,2);break;case 6 :returnVal = str.substr(0,2) + ":" + str.substr(2,2) + ":" + str.substr(4,2);break;case 8 :returnVal = str.substr(0,2) + ":" + str.substr(2,2) + ":" + str.substr(4,2) + ":" + str.substr(6,2);break;default :}return returnVal;}

function replaceAll(replaceStr, searchStr, replaceStr ){var temp = replaceStr;while( temp.indexOf( searchStr ) != -1 ){temp = temp.replace( searchStr, replaceStr );}return temp;}

String.prototype.replaceAll = function( searchStr, replaceStr ){var temp = this;while( temp.indexOf( searchStr ) != -1 ){temp = temp.replace( searchStr, replaceStr );}return temp;};

function checkTextObject(obj, msg) {if(obj == null || obj.value == "" || obj.value == "undefined") {alert(msg);obj.focus();obj.select();return false;}return true;}

function checkZeroTextObject(obj, msg) {if(obj == null || obj.value == "" || obj.value == "undefined" || obj.value == "0") {alert(msg);obj.focus();obj.select();return false;}return true;}

function checkTextObjectOtherFocus(obj, focusObj, msg) {if(obj.value == "") {alert(msg);focusObj.focus();return false;}return true;}

function checkZeroTextObjectOtherFocus(obj, focusObj, msg) {if(obj == null || obj.value == "" || obj.value == "undefined" || obj.value =="0") {alert(msg);focusObj.focus();return false;}return true;}

function insertComma(value) {return FormatCurrerny(value);}

function autoInsertComma(field){field.value = insertComma(field.value);}

function delComma(value) {try {value = value.replace(/,/g,"");}catch (e){alert(e);}return value;}

function getMoneyChk(val) {if(window.event.keyCode == 37 || window.event.keyCode == 38 || window.event.keyCode == 39 || window.event.keyCode == 40 || window.event.keyCode == 35 || window.event.keyCode == 36) {return;}if(!isMoneyStr(val)) {val.value = getOnlyMoneyStr(val);}val.value=FormatCurrerny(val.value);}

function isAlphaDigitStr(value) {for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(!isAlpha(chr) && !isDigit(chr)) {return false;}}return true;}

function isAlphaStr(value) {for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(!isAlpha(chr)) {return false;}}return true;}

function isAlpha(value) {var isAlpha = false;if(isUpperCase(value)) isAlpha = true;if(isLowerCase(value)) isAlpha = true;return isAlpha;}

function isDigitCombineLowerCase(value) {var bTrue = false;var bLowercase = false;var bDigit = false;if(isDigitLowerCaseStr(value)) {for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(isDigit(chr)) bDigit = true;if(isLowerCase(chr)) bLowercase = true;}if(bDigit && bLowercase) bTrue = true;}return bTrue;}

function isUpperCase(value) {if("A" <= value && value <= "Z"){return true;}else{return false;}}

function isLowerCase(value) {if("a" <= value && value <= "z"){return true;}else{return false;}}

function isDigitLowerCaseStr(value) {for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(!isDigitLowerCase(chr)) {return false;}}return true;}

function isDigitLowerCase(value) {var algi = '1234567890abcdefghijklmnopqrstuvwxyz';for(var i = 0; i < algi.length; i++) {var chr = algi.substr(i,1);if (chr == value) {return true;}}return false;}

function isDigitStr(val) {value = val.value;for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(!isDigit(chr)) {alert("숫자과 관련된 문자만 입력할 수 있습니다");val.value = getOnlyDigitStr(val);return false;}}return true;}

function getOnlyDigitStr(val) {value = val.value;var digitStr = "";for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(isDigit(chr)) {digitStr += chr;}}return digitStr;}

function isDigit(value) {var algi = '1234567890,';for(var i = 0; i < algi.length; i++) {var chr = algi.substr(i,1);if (chr == value) {return true;}}return false;}

function getOnlyMoneyStr(val) {value = val.value;var moneyStr = "";for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(isMoney(chr)) {moneyStr += chr;}}return moneyStr;}

function isMoneyStr(val) {value = val.value;for(var i = 0; i < value.length; i++) {var chr = value.substr(i,1);if(!isMoney(chr)) {alert("금액과 관련된 문자만 입력할 수 있습니다");return false;}}return true;}

function isMoney(value) {var algi = ',1234567890';for(var i = 0; i < algi.length; i++) {var chr = algi.substr(i,1);if (chr == value) {return true;}}return false;}

function formatCurrerny(number){var rValue ='';var EnableChar = "-0123456789";var Chr='';var EnableNumber = '';number = String(number);for (var i=0;i<number.length;i++) {Chr = number.charAt(i);if (EnableChar.indexOf(Chr) != -1){EnableNumber += Chr;}}var Minus = false;var ABSNumber = '';if (parseInt(EnableNumber)<0){Minus = true;ABSNumber = EnableNumber.substring(1, EnableNumber.length);}else{ABSNumber = EnableNumber;}if (ABSNumber.length < 4) {rValue = ABSNumber;if (Minus){rValue = "-"+ABSNumber;}return rValue;}var ReverseWords = '';for(i=ABSNumber.length;i>=0;i--){if (ABSNumber.charAt(i)!='-'){ReverseWords += ABSNumber.charAt(i);}}rValue = ReverseWords.substring(0, 3);var j=0;var dotCount = (ReverseWords.length/3)-1;for (j=1;j<=dotCount;j++){for(i=0;i<ReverseWords.length;i++){if (i==j*3){rValue+=","+ReverseWords.substring(i, i+3);}}}var elseN = (ReverseWords.length%3);if (elseN!=0){rValue+= ","+ReverseWords.substring(ReverseWords.length-elseN, ReverseWords.length);}ReverseWords = rValue;rValue = '';for(i=ReverseWords.length;i>=0;i--){if (ReverseWords.charAt(i)!='-'){rValue += ReverseWords.charAt(i);}}if (Minus) {rValue = "-"+rValue;}return rValue;}

function trimIt(src){var search = 0;while ( src.charAt(search) == " "){search = search + 1;}src = src.substring(search, (src.length));search = src.length - 1;while (src.charAt(search) ==" "){search = search - 1;}return src.substring(0, search + 1);}

function tagValidator(strz){var parseStr = strz.split(':');for(var i=0;i<parseStr.length;i++){var targetStr = parseStr[i];if(i+1 < parseStr.length){for(var j=i+1;j<parseStr.length;j++){if(targetStr == parseStr[j]){return false;}}}}return true;}

function makeTelNumber(str){str = removeIsNotNumeric(str);var returnVal = "";var lastNumber = "";if(str == null || str == ""){return returnVal;}if( str.length <= 2 ){return returnVal;}else{if( str.indexOf("02") == 0 ){returnVal += str.substr(0,2) + '-';lastNumber = str.substr(2, str.length);}else if(str.indexOf("0") == 0 ){returnVal = str.substr(0,3) + '-';lastNumber = str.substr(3, str.length);}else{lastNumber = str;}switch(lastNumber.length){case 7 :returnVal += (lastNumber.substr(0,3)+'-'+lastNumber.substr(3,7));break;case 8 :returnVal += (lastNumber.substr(0,4)+'-'+lastNumber.substr(4,8));break;default :returnVal += lastNumber;break;}}return returnVal;}

function encodeStr(data) {if(data != "") {data=str_replace(data,"&amp;","&");data=str_replace(data,"&quot;","\"");data=str_replace(data,"&#039;","'");data=str_replace(data,"&lt;","<");data=str_replace(data,"&gt;",">");data=str_replace(data,"%","%25");data=str_replace(data,"#","%23");data=str_replace(data,"&","%26");data=str_replace(data,"+","%2B");data=str_replace(data,"'","\'");data=str_replace(data,"\"","%22");data=str_replace(data,"@","%40");data=str_replace(data," ","%20");data=str_replace(data,"$","%24");data=str_replace(data,",","%2C");data=str_replace(data,"/","%3A");data=str_replace(data,":","%3B");data=str_replace(data,";","%3D");data=str_replace(data,"=","%3F");data=str_replace(data,"?","%40");data=str_replace(data,">","%3C");data=str_replace(data,"<","%3E");data=str_replace(data,"{","%7B");data=str_replace(data,"}","%7D");data=str_replace(data,"|","%7C");data=str_replace(data,"\\","%5C");data=str_replace(data,"^","%5E");data=str_replace(data,"~","%7E");data=str_replace(data,"[","%5B");data=str_replace(data,"]","%5D");data=str_replace(data,"`","%60");} else {data = "";}return data;}

function str_replace(str, target, replace){tmp=str.split(target);if(tmp.length > 1){str="";for(var i=0;i<tmp.length-1;i++){str=str+tmp[i]+replace;}str=str+tmp[tmp.length-1];}return str;}

function decodeStr(data){if(data != "") {data=str_replace(data,"%25","%");data=str_replace(data,"%23","#");data=str_replace(data,"%26","&");data=str_replace(data,"%2B","+");data=str_replace(data,"\'","'");data=str_replace(data,"%22","\"");data=str_replace(data,"%40","@");data=str_replace(data,"%20"," ");data=str_replace(data,"%24","$");data=str_replace(data,"%2C,","");data=str_replace(data,"%3A","/");data=str_replace(data,"%3B",":");data=str_replace(data,"%3D",";");data=str_replace(data,"%3F","=");data=str_replace(data,"%40","?");data=str_replace(data,"%3C",">");data=str_replace(data,"%3E","<");data=str_replace(data,"%7B","{");data=str_replace(data,"%7D","}");data=str_replace(data,"%7C","|");data=str_replace(data,"%5C","\\");data=str_replace(data,"%5E","^");data=str_replace(data,"%7E","~");data=str_replace(data,"%5B","[");data=str_replace(data,"%5D","]");data=str_replace(data,"%60","`");} else {data = "";}return data;}

function resizeFr(ifr){var ifrm = document.getElementById(ifr.id);var theHeight = ifrm.contentWindow.document.body.scrollHeight;ifrm.height = theHeight;}

function resizeHeight(ifr) {ifr = typeof ifr == 'string' ? document.getElementById(ifr) : ifr;ifr.setExpression('height',Content.document.body.scrollHeight);}

String.prototype.cut = function(len) {var str = this;var l = 0;for (var i=0; i<str.length; i++) {l += (str.charCodeAt(i) > 128) ? 2 : 1;if (l > len) return str.substring(0,i) + "...";}return str;};

String.prototype.bytes = function() {var str = this;var l = 0;for (var i=0; i<str.length; i++) l += (str.charCodeAt(i) > 128) ? 2 : 1;return l;};

function uploadFile() {}
uploadFile.prototype.domain = new String();
uploadFile.prototype.fileMsg = new Object();
uploadFile.prototype.fileUrlInfo = "";
uploadFile.prototype.fileUploadKey = new String();
uploadFile.prototype.fileObj = new Object();
uploadFile.prototype.fileInfo = new Array();
uploadFile.prototype.retFunction = "";
uploadFile.prototype.isUpload = false;
uploadFile.prototype.formName = new String();

uploadFile.prototype.upload = function (frm, obj, target) {if(target==null||target=="") target = "behindFrame";this.fileObj = obj;document.domain = this.domain;this.formName = frm.name;var fileName = "";if(true){var len=frm.elements.length; var type;for(var i=0;i<len;i++){type = frm.elements[i].type.toLowerCase();if(type=='file'){fileName = frm.elements[i].name;break;}}}this.fileMsg.innerHTML = "";this.fileMsg.innerHTML = "<font color='red'>파일첨부중...</font>";this.fileObj.disabled = true;var urlParam = this.fileUrlInfo+"/FileUpload.do?fileUploadKey="+this.fileUploadKey+"&fileName="+fileName;frm.target=target;frm.action=urlParam;frm.submit();};

uploadFile.prototype.setSuccess = function (apndxFileIdnfiNo,apndxFileLogicName,apndxFilePsyclName,fileSizeCtnt) {var idx = this.fileInfo.length;for(var i=0; i<idx; i++){var _frmName = this.fileInfo[i].formName;if( this.formName == _frmName ){idx = i;break;}}this.fileInfo[idx] = new Array();this.fileInfo[idx].formName=this.formName;this.fileInfo[idx].apndxFileIdnfiNo=apndxFileIdnfiNo;this.fileInfo[idx].apndxFileLogicName=apndxFileLogicName;this.fileInfo[idx].apndxFilePsyclName=apndxFilePsyclName;this.fileInfo[idx].fileSizeCtnt=fileSizeCtnt;this.fileObj.disabled = false;this.isUpload = true;eval('(' + this.retFunction + ')');};

uploadFile.prototype.setFail = function () {alert("첨부파일 저장시 에러가 발생하였습니다!! \n사이즈가 0인 파일은 첨부불가 대상입니다.");var idx = this.fileInfo.length;for(var i=0; i<idx; i++){var _frmName = this.fileInfo[i].formName;if( this.formName == _frmName ){idx = i;break;}}this.fileInfo[idx] = new Array();this.fileInfo[idx].formName="";this.fileInfo[idx].apndxFileIdnfiNo="";this.fileInfo[idx].apndxFileLogicName="";this.fileInfo[idx].apndxFilePsyclName="";this.fileInfo[idx].fileSizeCtnt="";this.fileObj.disabled = false;this.isUpload = true;eval('(' + this.retFunction + ')');};

function _chartXML(data, width, heigth, div) {var chart = new AnyChart("/koramco/images/chart/AnyChart.swf");chart.width = width;chart.height = heigth;chart.setData(data);chart.write(div);}

function makeFullID(prefix,id,len,div) {len = len-prefix.length;var retun_id = id+'';var id_len = retun_id.length ;for( var i=0; i<(8-id_len);i++){retun_id='0'+retun_id;}retun_id=prefix+retun_id;return retun_id ;}

function isNotEmptyById(id, isFocus){var _$ = $('#'+id);var value = _$.val();if(_$.length <1){alert('id가 '+id+'이(가) 존재하는지 확인하십시오.');return false;}else if(value == undefined || value == ''){try { _$.focus(); } catch (e) {;}var title = _$.attr('title');if(title == undefined || title == ''){alert(id+"의 title을 정의하십시오.");return false;}alert(_$.attr('title')+"은(는) 필수입니다.");if( isFocus) _$.focus();return false;}return true;}

function isValidDateById(id){var _$ = $('#'+id);var value = _$.val();if(_$.length <1){alert('id가'+id+'인 놈을 확인하십시오.');return false;}else if(value == undefined){alert('-_-');return false;}else if(value == ''){return true;}else if(!isValidDate(value)){try { _$.focus(); } catch (e) {;}var title = _$.attr('title');if(title == undefined || title == ''){alert(id+"의 title을 정의하십시오.");return false;}alert(_$.attr('title')+"은(는) 날짜형식이어야 합니다.");return false;}return true;}

function isValidYearMonthById(id){var _$ = $('#'+id);var value = _$.val();if(_$.length <1){alert('id가'+id+'인 놈을 확인하십시오.');return false;}else if(value == undefined){alert('-_-');return false;}else if(value == ''){return true;}else if(!isValidYearMonth(value)){try { _$.focus(); } catch (e) {;}var title = _$.attr('title');if(title == undefined || title == ''){alert(id+"의 title을 정의하십시오.");return false;}alert(_$.attr('title')+"은(는) yyyy-MM 타입이어야 합니다.");return false;}return true;}


function isValidDate(dateString) {var dateStr = /(\d{4})-(\d{2})-(\d{2})/;var matchArray = dateStr.exec(dateString);if (matchArray == null) {return false;}var year = matchArray[1];var month = matchArray[2];var day = matchArray[3];if (month < 1 || month > 12) {return false;}if (day < 1 || day > 31) {return false;}if ((month==4 || month==6 || month==9 || month==11) && day==31) {return false;}if (month == 2) {var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));if (day>29 || (day==29 && !isleap)) {return false;}}return true;}

function isValidDateForCal(dateString) {var dateStr = /(\d{4})-(\d{2})-(\d{2})/;var matchArray;var year = 0;var month = 0;var day = 0;if (dateString.length == 8) {year = dateString.substring(0,4);month = dateString.substring(4,6);day = dateString.substring(6,8);} else if (dateString.length == 10) {matchArray = dateStr.exec(dateString);if (matchArray == null) {return false;}year = matchArray[1];month = matchArray[2];day = matchArray[3];}if (month < 1 || month > 12) {return false;}if (day < 1 || day > 31) {return false;}if ((month==4 || month==6 || month==9 || month==11) && day==31) {return false;}if (month == 2) {var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));if (day>29 || (day==29 && !isleap)) {return false;}}return true;}

function isValidYearMonth(dateString) {var dateStr = /(\d{4})-(\d{2})/;var matchArray = dateStr.exec(dateString);if (matchArray == null) {return false;}var month = matchArray[2];if (month < 1 || month > 12) {return false;}return true;}

function isMailType(value) {mail01 = /[^@]+@[A-Za-z0-9_-]+[.]+[A-Za-z]+/;mail02 = /[^@]+@[A-Za-z0-9_-]+[.]+[A-Za-z0-9_-]+[.]+[A-Za-z]+/;mail03 = /[^@]+@[A-Za-z0-9_-]+[.]+[A-Za-z0-9_-]+[.]+[A-Za-z0-9_-]+[.]+[A-Za-z]+/;if(mail01.test(value)) return true;if(mail02.test(value)) return true;if(mail03.test(value)) return true;return false;}

function isBizID(bizID){var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);bizID = bizID.replace(/-/gi,'');for (var i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i);c2 = "0" + (checkID[8] * bizID.charAt(8));c2 = c2.substring(c2.length - 2, c2.length);chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));remander = (10 - (chkSum % 10)) % 10 ;if (Math.floor(bizID.charAt(9)) == remander) return true ;return false;}

function isValidUrl(urls) {if(urls ==""){return true;}var chkExp = /http:\/\/([\w\-]+\.)+/g;return chkExp.test(urls);}

function isValidSSN(ssn){var jumin1 = ssn.substring(0,6);var jumin2 = ssn.substring(7,14);if((jumin1.length==6) &&(jumin2.length==7)){var jumin=jumin1+jumin2;a = new Array(13);for (var i=0; i < 13; i++) {a[i] = parseInt(jumin.charAt(i));}var k = 11 - (((a[0] * 2) + (a[1] * 3) + (a[2] * 4) + (a[3] * 5) + (a[4] * 6) + (a[5] * 7) + (a[6] * 8) + (a[7] * 9) + (a[8] * 2) + (a[9] * 3) + (a[10] * 4) + (a[11] * 5)) % 11);if (k > 9){k -= 10;}if (k != a[12]){return false;}return true;}else{return false;}}

var vFilterSkipCodes = [8,32,9,27,37,38,39,40,36,35,33,34,46];

function filterOnlyNumber(evt) {var theEvent = evt || window.event;var key = theEvent.keyCode || theEvent.which;key = String.fromCharCode( key );var regex = /[0-9]|\./;if( !regex.test(key) ) {theEvent.returnValue = false;if(theEvent.preventDefault) theEvent.preventDefault();}}

function filterOnlyTelNum(event){if((event.keyCode==189)) return true;return filterOnlyNumber(event);}

function filterOnlyMoney(event){if((event.keyCode==188)) return true;return filterOnlyNumber(event);}

function filterOnlyNumAndDot(event){if((event.keyCode==190)) return true;return filterOnlyNumber(event);}

function isValidIP(strIP) {var expUrl = /^(1|2)?\d?\d([.](1|2)?\d?\d){3}$/;return expUrl.test(strIP);}

function fnNvl(v1, v2) {if(!v1 || v1 == null || v1 == 'undefined') {return v2;}return v1;}

function fnResNoCheck(target) {var bResult = true;var resId = target.id.substr(0, target.id.length-1);var resNo = $("#"+resId+1).val() + $("#"+resId+2).val();if (resNo.length>0) {bResult = idNo_validate(resNo, target);}else {bResult = false;}return bResult;}

function fnValidation(target) {$(target).keyup(function() {clearValidation();});$(target).change(function() {clearValidation();});}

function validation() {var v = true;var titleMessage = "";$(".required").each(function() {if(fnIsEmptyField(this)) {v = false;$("#"+fnReplaceChar(this.id, '.', '_')+"Validation").remove();if(this.title && this.title != '' && this.title != 'undefined') {titleMessage = "<br/>'"+this.title + "'은(는) ";}else {titleMessage = "";}$("<font class='isValidation' color='red' id='"+fnReplaceChar(this.id, '.', '_')+"Validation'></font>").html('(*)').appendTo($(this).parent());$(this).focus();}});if(v) {$(".resNo").each(function() {if(fnIsEmptyField(this)) {v = fnResNoCheck(this);if(!v) {$(this).focus();}}});}return v;}

function clearValidation() {var v = true;var titleMessage = "";$(".isValidation").each(function() {var parentObject = $("#"+this.id.replace('Validation', ''))[0];$(this).remove();if(fnIsEmptyField(parentObject)) {v = false;if(parentObject.title && parentObject.title != '' && parentObject.title != 'undefined') {titleMessage = "<br/>'"+parentObject.title + "'은(는) ";}else {titleMessage = "";}$("<font class='isValidation' color='red' id='"+fnReplaceChar(parentObject.id, '.', '_')+"Validation'></font>").html('(*)').appendTo($(parentObject).parent());}});return v;}

function fnIsEmptyField(field){return field.type!='hidden' && field.readonly!=true && field.disabled!=true && $.trim($(field).val())=='';}

function fnReretVailiation() {$(".isValidation").each(function() {$(this).remove();});}

function isEditAfter(type) {var v = false;if(type == null) {type = "";}$(".editAfter"+type).each(function() {if(!fnIsEmptyField(this)) {v = true;}});return v;}

function parseDateFont() {$("font.dateType").each(function() {var va = new String($(this).html());$(this).html(parseStr2Date(va));$(this).removeClass("dateType");});}

function writeDate(input, d1, d2){document.write(parseStr2Date(input,d1,d2));}

function parseStr2Date(input, d1, d2) {var resultMsg = input;if (input!=null) {if(d1 != null) {var div1=d1;}else {var div1=".";}if(d2 != null) {var div2=d2;}else {var div2=":";}if(input.length>7) {resultMsg = input.substr(0,4)+ div1 + input.substr(4,2)+ div1 + input.substr(6,2);}if (input.length==14 && div2 != 'N') {resultMsg += " " + input.substr(8,2) + div2 + input.substr(10,2) + div2 + input.substr(12,2);}}return resultMsg;}

function writeContents(input){var resultMsg = input.split("\n").join("<br/>");resultMsg = input.split("<").join("&lt;");resultMsg = input.split(">").join("&gt;");document.write(resultMsg);}

function writeSubStr(input, c1, c2, target) {var result = input;if (c2 == null) {c2 = c1;c1 = 0;}if (input != null && input.length != 0 && input.length >= c2) {result = String(input).substring(c1, c2);}if (target != null) {if (document.getElementById(target)!=null) {target = document.getElementById(target);}target.value=result;}else {document.write(result);}return result;}

function writeSubStr2(input, c1, c2, target) {var result = input;if (c2 == null) {c2 = c1;c1 = 0;}if (input != null && input.length != 0 && input.length >= c2) {if( input.length > c2){result = String(input).substring(c1, c2) + "...";}else{result = input;}}if (target != null) {if (document.getElementById(target)!=null) {target = document.getElementById(target);}target.value=result;}else {document.write(result);}return result;}

function writeLastSubStr(input, c1, target) {var result = input;result = String(input).substring(c1, input.length);if (target != null) {if (document.getElementById(target)!=null) {target = document.getElementById(target);}target.value=result;}else {document.write(result);}return result;}

function GetAbsPosition(object) {var position = new Object;position.x = 0;position.y = 0;if( object!=null ) {position.x = object.offsetLeft;position.y = object.offsetTop;if( object.offsetParent ) {var parentpos = GetAbsPosition(object.offsetParent);position.x += parentpos.x;position.y += parentpos.y;}}position.cx = object.offsetWidth;position.cy = object.offsetHeight;return position;}

function selectAll(type) {var c = $('#'+type+'Checked').attr('checked');$('input[name='+type+']').attr('checked',c);}

function existFile(){var targetElement = document.getElementById(event.srcElement.id+"Exist");if(targetElement) {targetElement.value=event.srcElement.value;}}

function copyOptions(p, t, selectedCopyYn) {$(t).html('');for(var i=0; i< p.options.length; i++) {if (p.selectedIndex != i && selectedCopyYn == 'n') {var newOpt = new Option(p.options[i].text, p.options[i].value);t.options.add(newOpt);}}}

function getSelectedOptionsText(p) {p = document.getElementById(p);if(p != null && p.selectedIndex != null) {return p.options[p.selectedIndex].text;}return "";}

function fnMultiCharCountCut(content, maxLen) {var tempStr = "";var str_cnt = 0;for(var i=0; i < content.length ; i++) {tempStr = content.charAt(i);if(escape(tempStr).length > 4) {str_cnt += 3;}else {str_cnt += 1 ;}}if (str_cnt > maxLen){str_cnt = 0;tempStr2 = "";for(var i = 0; i < maxLen; i++) {tempStr = content.charAt(i);if(escape(tempStr).length > 4) {str_cnt += 3;}else {str_cnt += 1 ;}if (str_cnt > maxLen) {if(escape(tempStr).length > 4) {str_cnt -= 3;}else {str_cnt -= 1 ;}tempStr2 += "…";break;}else tempStr2 += tempStr;}content = tempStr2;}return content;}

function fnTypingCheck(inputObjId, maxLen, cntObjId){var isCheck = true;var sContent = document.getElementById(inputObjId);if (sContent == null) sContent = inputObjId;var txtCnt;if(cntObjId !=null && cntObjId != "") txtCnt = document.getElementById(cntObjId);var tempStr = "";var str_cnt = 0;for(var i=0; i < sContent.value.length ; i++) {tempStr = sContent.value.charAt(i);if(escape(tempStr).length > 4) {str_cnt += 3;}else {str_cnt += 1 ;}}if (str_cnt > maxLen){isCheck = false;alert(maxLen+" Bytes를 초과하였습니다. 작성하신 글이 잘려서 보시됩니다.");str_cnt = 0;tempStr2 = "";for(var i = 0; i < maxLen; i++) {tempStr = sContent.value.charAt(i);if(escape(tempStr).length > 4) str_cnt += 3;else str_cnt += 1 ;if (str_cnt > maxLen){if(escape(tempStr).length > 4) str_cnt -= 3;else str_cnt -= 1 ;break;}else {tempStr2 += tempStr;}}sContent.value = tempStr2;}if(cntObjId !=null && cntObjId != "") txtCnt.innerHTML = str_cnt+"/" + maxLen + " Bytes&nbsp;&nbsp;&nbsp;";return isCheck;}

function fnMinLengthCheck(inputObjId, minLen){var sContent = document.getElementById(inputObjId);if (sContent == null) sContent = inputObjId;var tempStr = "";var str_cnt = 0;for(var i=0; i < sContent.value.length ; i++){tempStr = sContent.value.charAt(i);if(escape(tempStr).length > 4) str_cnt += 3;else str_cnt += 1 ;}if (str_cnt < minLen){sContent.focus();alert(minLen+" Bytes보다 적을 수 없습니다.");return true;}}

function fnNumberFormatComma(number, def) {number = new String(number);tmp = number.split('.');var str = new Array();var v = tmp[0].replace(/,/gi,'');for(var i=0; i<=v.length; i++) {str[str.length] = v.charAt(v.length-i);if(i%3==0 && i!=0 && i!=v.length) {str[str.length] = '.';}}str = str.reverse().join('').replace(/\./gi,',');if (tmp.length==2) {str = str + '.' + tmp[1];}if (str == '' && def != null) {str = def;}return str;}

function writeNumberFormatComma(number, def) {document.write(fnNumberFormatComma(number, def));}

function fnReplaceChar(str, tarCh, repCh) {var nowCh  = "";var sumStr = "";var len	= str.length;for (var i=0; i<len; i++) {if (str.charAt(i) == tarCh) {nowCh = repCh;}else {nowCh = str.charAt(i);}sumStr = sumStr + nowCh;}return sumStr;}

function writeReplaceStr(str, tarCh, repCh) {var sumStr = String(str).replace(tarCh,repCh);document.write(sumStr);return sumStr;}

function fnShowErrorMessage(divId, msg) {var target = document.getElementById(divId);$(target).html(msg);target.style.pixelLeft = 100;target.style.pixelTop = 70;target.style.width = 500;target.style.height = 430;}

function fnShowDiv(divId) {	var target = document.getElementById(divId);target.style.pixelLeft = 200;target.style.pixelTop = 100;target.style.width = 500;target.style.height = 430;}

function fnChangePage(page, pageingUrl) {if(document.searchForm != null) {if($('#pageNo')[0]==null) {$(document.searchForm).append("<input type='hidden' name='pageNo' id='pageNo' value='"+page+"'/>");}$('#pageNo').val(page);document.searchForm.submit();}else {document.location.href=pageingUrl;}}

function WindowReset( win ){win.document.body.scroll = "auto";var winBody = win.document.body;var marginWidth = parseInt(winBody.leftMargin)+parseInt(winBody.rightMargin);var wid = winBody.scrollWidth + (winBody.offsetWidth - winBody.clientWidth) + marginWidth -5;var hei = '700';win.resizeTo(wid, hei);}

function scrollOn( win ){win.document.body.scroll = "auto";}

function checkboxCheck(name, chk) {chk = chk.split(", ");name = document.getElementsByName(name);for(var i = 0; i < name.length; i++) {for (var j = 0; j < chk.length; j++) {if(String(name[i].value)==String(chk[j]) || chk == '') {name[i].checked=true;}}}}

function excelDown(form) {form = $form(form);if($("#excelYn")[0]==null) {$(form).append("<input type='hidden' name='excelYn' id='excelYn' value='Excel'/>");}form.submit();$("#excelYn").remove();}

function getDate(target) {var Today = new Date();var MyYear = Today.getYear();var MyMonth = Today.getMonth() + 1;var MyDate = Today.getDate();$(target).val(MyYear+''+MyMonth+MyDate);if ($(target).val()=='') {$('#'+target).val(MyYear+''+MyMonth+MyDate);}}

function fnTableToGrid(id) {tableToGrid("#"+id);$("#"+id).setGridWidth(750);$("#"+id).setGridHeight(285);}

function viewYn(id,b) {if(b) {$("#"+id).fadeIn(400);$("."+id).each(function() {$(this)[0].disabled=false;});}else {$("#"+id).hide(150);$("."+id).each(function() {$(this)[0].disabled=true;});}}

function checkedYn(id, val) {if(val.length>0) {$("#"+id)[0].checked=true;return true;}else {return false;}}

function resNoCheck() {var resResult = true;$(".resNo").each(function() {if(this.id.indexOf('1')!=-1) {if(this.type!='hidden' && this.readonly!=true && this.disabled!=true) {var resId = this.id.substr(0, this.id.length-1);var resNo = $("#"+resId+1).val() + $("#"+resId+2).val();if(!idNo_validate(resNo, this)) {return false;}}}});return resResult;}

function idNoValidate(value, target) {var temp = value;var temp2 = temp.substring(6,13);var titleMessage = '';var resId = target.id.substr(0, target.id.length-1);$("#"+resId+1+"ResNoCheck").remove();$("#"+resId+2+"ResNoCheck").remove();if(temp.length < 13) {if(target.title && target.title != '' && target.title != 'undefined') {titleMessage = "'"+target.title + "'은(는) ";}$("<font class='isValidation' color='red' id='"+fnReplaceChar(target.id, '.', '_')+"ResNoCheck'><br/></font>").html(titleMessage+' 주민등록번호가 길이가 유효하지 않습니다. 다시 입력하세요.').appendTo($(target).parent());return false;}if(temp2.charAt(0) <= "4") {for(i=0,sum=0; i<12; i++) {sum += (((i%8) + 2) * (temp.charAt(i) - "0"));}sum = 11 - (sum % 11);sum = sum % 10;if(sum != temp.charAt(12)) {if(target.title && target.title != '' && target.title != 'undefined') {titleMessage = "'"+target.title + "'은(는) ";}$("<font class='isValidation' color='red' id='"+fnReplaceChar(target.id, '.', '_')+"ResNoCheck'></font>").html(titleMessage+' 주민등록번호가 유효하지 않습니다. 다시 입력하세요.').appendTo($(target).parent());return false;}}else {if (fgn_no_chksum(temp) == false) {if(target.title && target.title != '' && target.title != 'undefined') {titleMessage = "'"+target.title + "'은(는) ";}$("<font class='isValidation' color='red' id='"+fnReplaceChar(target.id, '.', '_')+"ResNoCheck'></font>").html(titleMessage+' 주민등록번호가 유효하지 않습니다. 다시 입력하세요.').appendTo($(target).parent());return false;}}return true;}

function fgn_no_chksum(reg_no) {var sum = 0;var odd = 0;var buf = new Array(13);for (var i = 0; i < 13; i++) {buf[i] = parseInt(reg_no.charAt(i));}odd = buf[7]*10 + buf[8];if (odd%2 != 0) {return false;}if ((buf[11] != 6)&&(buf[11] != 7)&&(buf[11] != 8)&&(buf[11] != 9)) {return false;}var multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];for (i = 0, sum = 0; i < 12; i++) {sum += (buf[i] *= multipliers[i]);}sum=11-(sum%11);if (sum>=10) {sum-=10;}sum += 2;if (sum>=10) {sum-=10;}if ( sum != buf[12]){return false;}else {return true;}}

function fnIsNotNumber(target) {if(isNaN(target.value)) {alert("숫자만 입력이 가능합니다.");$(target).val('');}}

function fnCheckNumber() {if( (event.keyCode < 48 ) || (event.keyCode > 57) ) {event.returnValue = false;}}

function fnCheckID(id) {var reg_exp = new RegExp("^[a-zA-Z][a-zA-Z0-9]{3,11}$", "g");var match = reg_exp.exec(id);if(id == "") {alert("ID를 입력하세요.");return true;}if(id.length <6 ||id.length > 20) {alert("ID는 6자 이상 20자 이하로 입력하세요.");return true;}if(match == null) {alert("ID는 첫글자는 영문으로 시작하며 영문과 숫자 조합만 가능합니다.");return true;}return false;}

function fnAddData(id) {return "&"+id+"="+$get(id);}

function $form(form) {if(form!=null) {return form;}else {var formName = "commandMap";form = $("form");if(form==null || form.length == 0) {var target = event;if(target==null) {target = $("#container")[0];}else {target = target.srcElement;}$("<form name='"+formName+"' id='"+formName+"' method='post'></form>").appendTo($(target).parent());form = document.commandMap;}else {form = form[0];}return form;}}

function $add(id, val, form) {form = $form(form);$("#"+id).remove();$(form).append("<input type='hidden' name='"+id+"' id='"+id+"' value='"+val+"'/>");return form;}

function $val(name){return $("input[name="+name+"]").filter(function() {if (this.checked) return this;}).val();}

function $get(id){if(!(o = document.getElementById(id))) {name = document.getElementsByName(id);o = name[0];if (!o) {return "";}}var targetName = o.name;if(o!=null && targetName != '') {switch (o.type) {case 'checkbox' :case 'radio' :var result = $("input[name="+targetName+"]").filter(function() {if (this.checked) return this;}).val();if(result == null) result = "";return result;case 'select-one' :return o.value;case 'text' :case 'hidden' :case 'textarea' :case 'password' :return $("input[name="+targetName+"]").val();}}return '';}

function $set(id, value){try {if(!(o = document.getElementById(id))) {name = document.getElementsByName(id);o = name[0];}var targetName = o.name;if(o!=null && targetName != '') {switch (o.type) {case 'checkbox' :case 'radio' :$("input[name="+targetName+"]").filter('input[value='+value+']').attr("checked", "true");break;case 'select-one' :$("#"+id+" option[value="+value+"]").attr("selected", "true");break;case 'text' :case 'textarea' :$("#"+id).val(value);break;}}} catch (e) {}}

function $setData(value){var result = Object();if (value != null && value.indexOf("&") != -1) {var list = value.split("&");for (var i = 0; i < list.length; i++) {if (list[i] != null && list[i].indexOf("=") != -1) {var inputData = list[i].split("=");if (inputData[0] != null && inputData[0].length > 0 && inputData.length == 2 && inputData[1] != null) {if((o = $("#"+inputData[0])[0])!=null) {switch (o.type) {case 'checkbox' :case 'radio' :$("input[id="+inputData[0]+"]").filter('input[value='+inputData[1]+']').attr("checked", "true");break;case 'text' :case 'textarea' :$("input[id="+inputData[0]+"]").attr("value", inputData[1]);break;}}try {var o = eval("result."+inputData[0]+"='"+inputData[1]+"'");} catch (e) {}}}}}return result;}

function $file(id,param) {var maxFileNum = $("#posblAtchFileNumber").val();if(maxFileNum==null || maxFileNum==""){maxFileNum = 9;}var fileObj = document.getElementById( id );$("<div id='"+id+"ListDiv'></div>").appendTo($(fileObj).parent());var multi_selector = new MultiSelector( document.getElementById( id+'ListDiv' ), maxFileNum, id, param);multi_selector.addElement( fileObj );}

String.prototype.comma = function() {tmp = this.split('.');var str = new Array();var v = tmp[0].replace(/,/gi,'');for(var i=0; i<=v.length; i++) {str[str.length] = v.charAt(v.length-i);if(i%3==0 && i!=0 && i!=v.length) {str[str.length] = '.';}}str = str.reverse().join('').replace(/\./gi,',');return (tmp.length==2) ? str + '.' + tmp[1] : str;};

function onlyNum(obj) {obj.value = fnToNumber(obj.value);}

function fnToNumber(val) {var re = /[^0-9\.\,\-]/gi;return val.replace(re, '');}

function fnRemoveComma(val){var re=/,/g;return val.replace(re, "");}

function fnRound(val, depth) {if(depth == null) {return Math.round(val/10)*10;}else {for(var dep = 0; dep < depth ; dep++) {val = val * 10;}val = Math.round(val*1);for(var dep = 0; dep < depth ; dep++) {val = val / 10;}}if(val == null || val == '' || val == ' ') {val = 0;}return val;}

function fnPrintArea(divName, winTitle, path)  {
	var printThis =  document.getElementById(divName).innerHTML; 
	var win = null;
	win = window.open("","_blank","width=800,height=450");
    win.document.open();
    win.document.write('<'+'html'+'><'+'head'+'>');
    win.document.write('<'+'title'+'>' + winTitle + '<'+'/title'+'>');
    win.document.write('<'+'link rel="stylesheet" type="text/css" href="'+ path +'cmm/css/style.css"'+'>');
    win.document.write('<'+'/'+'head'+'>');   
    win.document.write('<'+'body'+'>');
    win.document.write('<'+'div '+'style="width:100%;height:450px; overflow:auto;margin-bottom:5px;overflow-x:hidden;">');
    win.document.write(printThis);
    win.document.write('<'+'/'+'div'+'>');
    win.document.write('<'+'/'+'body'+'><'+'/'+'html'+'>');
    win.document.close();
    win.print();
    win.close();
}

function fnAddCookie(id, cookieID, cookieName){
	  var maxItemNum = 10;
	  var expire = 1; 
	  if (cookieID) {
	  	var cookieArray = cookieID.split(",");	  
	  	cookieArray.unshift(id);
	    
	    if (cookieArray.length > maxItemNum ) cookieArray.length = 10;
	    cookieID = cookieArray.join(",");
	    setCookie(cookieName, cookieID, expire);
	  } else {
	    setCookie(cookieName, id, expire);
	  }
}

function fnOpenItems(currIdx,itemIDs){
    if(itemIDs.length > 0){
	    var fristItemID = itemIDs[currIdx];
		$("#openItemID").val(fristItemID);
		
		$("#currIdx").val(currIdx);
		$("#openItemList").val(itemIDs);
		
		if(itemIDs.length-1 == Number(currIdx)){
			$("#preDis").attr("style", "display:done;");
			$("#preAbl").attr("style", "display:none;");
		}else{
			$("#preDis").attr("style", "display:none;");
			$("#preAbl").attr("style", "display:done;");
		}
		
		if(Number(currIdx) == 0){
			$("#nextDis").attr("style", "display:done;");
			$("#nextAbl").attr("style", "display:none;");
		}else{
			$("#nextDis").attr("style", "display:none;");
			$("#nextAbl").attr("style", "display:done;");
		}
    }else{
    	$("#preDis").attr("style", "display:done;");
		$("#preAbl").attr("style", "display:none;");
		
		$("#nextDis").attr("style", "display:done;");
		$("#nextAbl").attr("style", "display:none;");
    }
}


function fnGoNextItem(){
	var currIdx = $("#currIdx").val();
	if(Number(currIdx)-1 == 0){
		$("#nextDis").attr("style", "display:done;");
		$("#nextAbl").attr("style", "display:none;");
		
		$("#preDis").attr("style", "display:none;");
		$("#preAbl").attr("style", "display:done;");
	}else{ 
		$("#nextDis").attr("style", "display:none;");
		$("#nextAbl").attr("style", "display:done;");
	}
	var itemIDs = $("#openItemList").val().split(','); 
    
	var openItemID = itemIDs[Number(currIdx)-1];
	
    $("#openItemID").val(openItemID);	
	$("#currIdx").val(Number(currIdx)-1);
	
	fnGoBackNextPage(openItemID, "Y", Number(currIdx)-1);
}

function fnGoBackItem(){
	var currIdx = $("#currIdx").val();
	var itemIDs = $("#openItemList").val().split(','); 

	if(itemIDs.length-1 == Number(currIdx)+1){
		$("#preDis").attr("style", "display:done;");
		$("#preAbl").attr("style", "display:none;");
		
		$("#nextDis").attr("style", "display:none;");
		$("#nextAbl").attr("style", "display:done;");
	}else{
		$("#nextDis").attr("style", "display:none;");
		$("#nextAbl").attr("style", "display:done;");
	}
	
	var openItemID = itemIDs[Number(currIdx)+1];
	
    $("#openItemID").val(openItemID);
	$("#currIdx").val(Number(currIdx)+1);
	
	fnGoBackNextPage(openItemID, "Y", Number(currIdx)+1);
}

function fnCheckUserAccRight(itemID, func, msg, msg2){
	$.ajax({
		url: "checkItemAccRight.do",
		type: 'post',
		data: "&itemID="+itemID,
		error: function(xhr, status, error) { 
		},
		success: function(data){				
			data = data.replace("<script>","").replace(";<\/script>","");
			fnCallbackCheckAccCtrl(data, itemID, func, msg, msg2);
		}
	});	
}

function fnCallbackCheckAccCtrl(accRight, itemID, func, msg, msg2){	
	if(accRight == "Y"){ 
		eval(func);
	} else {
		if(accRight == "N"){
			alert(msg); window.close();	
			return;
		}else{		
			alert(msg2); window.close();
			return;
		}
	}
}

function fnMasterChk(state) {
    event.stopPropagation();
    grid.data.forEach(function (row) {
        grid.data.update(row.id, { "checkbox" : state })
    })
}

function fnGridExcelDownLoad(gridType, disPlayHidden){ // dhtmlx v 7.1.8 grid excel download
	if(typeof gridType == "undefined" || gridType == null || gridType == "") gridType = grid;
	var excelDataRow = new Array();
	var headers = new Array();
	var ids = new Array();
	var aligns = new Array();
	var widths = new Array();
	var hiddens = new Array();
	
	if(disPlayHidden == "Y"){ // grid hidden column도 출력되도록
		for(var i2=0; i2 < gridType.config.columns.length; i2++){
			if(gridType.config.columns[i2].id != "checkbox"){
				headers.push(gridType.config.columns[i2].header[0].text);
				ids.push(gridType.config.columns[i2].id);
				hiddens.push(gridType.config.columns[i2].hidden);
				
				if(gridType.config.columns[i2].align == ""){
					aligns.push(gridType.config.columns[i2].align);
				}else{
					aligns.push("center");
				}
				
				if(gridType.config.columns[i2].width != ""){
					widths.push(gridType.config.columns[i2].width);
				}
			}
		}
	}else{
		for(var i2=0; i2 < gridType.config.columns.length; i2++){
			if(!gridType.config.columns[i2].hidden){
				if(gridType.config.columns[i2].id != "checkbox"){
					headers.push(gridType.config.columns[i2].header[0].text);
					ids.push(gridType.config.columns[i2].id);
					if(gridType.config.columns[i2].align == ""){
						aligns.push(gridType.config.columns[i2].align);
					}else{
						aligns.push("center");
					}
					
					if(gridType.config.columns[i2].width != ""){
						widths.push(gridType.config.columns[i2].width);
					}
					
				}
			}
		}
	}
		
	for(var i=0; i< gridType.data._order.length; i++) {
		excelDataRow.push(gridType.data._order[i]);				
	}
			
	var jsonData = JSON.stringify(excelDataRow);	
	var excelForm = document.createElement("form");
	excelForm.setAttribute("charset", "UTF-8");
	excelForm.setAttribute("method", "Post");  
	
	var data = {
		gridExcelData : jsonData,
		headers : headers,
		ids : ids,
		aligns : aligns,
		widths : widths,
		hiddens : hiddens
	};
	
	if (data) {
      for (var key in data) {
        var input = document.createElement("input");
        input.id = key;
        input.name = key;
        input.type = "hidden";
        input.value = data[key];
        excelForm.appendChild(input);
      }
    }
    
    document.body.appendChild(excelForm);
    
    $("#isSubmit").val('');
    var url = "excelFileDownload.do";	        
	ajaxSubmit(excelForm, url, "blankFrame");
	
}

