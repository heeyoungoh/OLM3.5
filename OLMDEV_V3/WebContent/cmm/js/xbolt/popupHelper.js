//팝업창을 화면 가운데 띄운다.
//url : url
//popupname : 팝업 윈도우명
//x : width
//y : height
//scroll : 화면 scroll 여부( 'yes','no' )
//resize : 화면 resize 여부( 'yes','no' )
function popUp(url, popupname,x,y,scroll,resize){var win = window.open(url,popupname,"toolbar=no,width="+x+",height="+y+ ",top="+ (screen.availheight- y )/2+",left="+(screen.availwidth- x)/2 +",directories=no,status=no,scrollbars="+scroll+",resize="+resize+",menubar=no");return win;}
function modalPopup(url,w,h){var returnVal = showModalDialog(url,'',"dialogHeight:"+h+"px; dialogWidth:"+w+"px; resizable:yes; center:yes; status:no ;scroll:no");return returnVal;}
function openLoginPopup(url, w, h, popupName, param){var popWindow;popWindow = window.open(url+"?LGIN_ID="+param, popupName, "width="+w+", height="+h+", top=100,left=100 ,toolbar=no , status=no");popWindow.opener = self;}
function openPopup(url, w, h, popupName) {if(popupName==null) {popupName = 'newPopup';}if(w == null) {	w = 1020;h = 686;}
	var popWindow;
	try{popWindow = window.open(url, popupName, "width="+w+", height="+h+", top=100,left=100 ,toolbar=no , status=no");	popWindow.opener = self;} catch(e) {}
}

function itmInfoPopup(url, w, h, popupName) {if(popupName==null) {popupName = 'newPopup';}if(w == null) {	w = 1020;h = 686;}
var popWindow;
try{popWindow = window.open(url, popupName, "width="+w+", height="+h+", top=100,left=100,toolbar=no,status=no,resizable=yes");	popWindow.opener = self;} catch(e) {}
}

function openPopupScoll(url, w, h, popupName) {if(popupName==null) {popupName = 'newPopup';}if(w == null) {	w = 710;h = 700;}
	var popWindow;	
	popWindow = window.open(url, popupName, "scrollbars=yes,new,width="+w+", height="+h+", top=100,left=100 ,toolbar=no , status=no");
	popWindow.opener = self;
}
function openFullPopup(url, popupName) {if(popupName==null) {popupName = 'newPopup';}var popWindow;popWindow = window.open(url, popupName);popWindow.opener = self;}
/**레이어팝업 호출
 	url : 화면 url
 	data : request의 파라미터 정보. 예) ?paramA=xxx&paramB=yyy
 	callBack : 팝업이 확인되고서 호출되길 기대하는 함수
 	width: 가로사이즈
 	height : 세로사이즈
 */
var popup_callBack;
function fnOpenLayerPopup( url , data , callBack, width, height){
	popup_callBack = callBack;
	var $mask = $('#mask');
	if($mask.length == 0){	$('body').append("<div id='mask' class='mask' style='display:none; filter:alpha(opacity=20); opacity:0.2; -moz-opacity:0.2;' ></div>");$mask = $('#mask');}
	var $popupDiv = $('#popupDiv');
	if($popupDiv.length == 0){$('body').append("<div id='popupDiv' class='popup_div' style='display:none;'></div>");
	}else if($popupDiv.length == 1){$popupDiv.html('');
	}else{return false;}

	var maskHeight = $(document).height();    var maskWidth = $(window).width();
    var top  = (maskHeight-height)*2/7;    var left = (maskWidth-width)*2/5;
    top = (top<0)?0:top;    left = (left<0)?0:left;
	$('#popupDiv').css({'width':width,'height':height ,'position':'absolute','left':left,'top':top,'z-index':'9999999999','display':'none'});
	$mask.css({'width':maskWidth,'height':maskHeight ,'position':'absolute','left':0,'top':0,'z-index':'9999999998','display':'none' ,'background-color':'black'});
	$mask.show();
	$('#popupDiv').show();
	try{if(url.length>0)ajaxPageSyn(url, data, "popupDiv");}catch(e){}
	$('.popup_div').draggable({handle:".popup_title"});
	$('.popup_closeBtn').click(function(){
		$(".popup_div").hide();
		$("#mask").hide();
	});
}
function fnOpenMaskLayer(){var $mask = $('#mask');if($mask.length == 0){$('body').append("<div id='mask' class='mask' style='display:none; filter:alpha(opacity=20); opacity:0.2; -moz-opacity:0.2;' ></div>");$mask = $('#mask');}var maskHeight = $(document).height();var maskWidth = $(window).width();$mask.css({'width':maskWidth,'height':maskHeight ,'position':'absolute','left':0,'top':0,'z-index':'9000','display':'none' ,'background-color':'black'});$mask.show();


}
function fnCloseMaskLayer(){if($("#mask").length >0){$("#mask").hide();}}

function fnOpenUserPopup(doCallBack){
	var url  = "${root}popup/userPopup.do";
	var data = "";
	fnOpenLayerPopup(url,data,doCallBack,617,536);
}