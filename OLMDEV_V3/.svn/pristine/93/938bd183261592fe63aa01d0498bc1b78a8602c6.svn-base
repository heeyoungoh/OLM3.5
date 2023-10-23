function ajaxTreeLoad(url, data, target, debug, noMsg, msg, callback) {
	//var StartDate = new Date();
	$.ajax({
		url: url,type: 'post',data: data
		,error: function(xhr, status, error) {('#loading').fadeOut(150);var errUrl = "errorPage.do";var errMsg = "status::"+status+"///error::"+error;var callback = "";var width = "300px";var height = "300px";var callBack = function(){};fnOpenLayerPopup(errUrl,errMsg,callBack,width,height);}
		,beforeSend: function(x) {$('#loading').fadeIn();if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(result){
			$('#loading').fadeOut(3000);if(debug){alert(result);}	if(result == 'error' || result == ""){if(noMsg != 'Y'){alert(msg);}
			}else{result = eval('(' + result + ')');target.loadJSArray(result);}	
			if(callback== null || callback==""){}
			else{ eval(callback);}
			//var StopDate = new Date();
			//var Diff = StopDate.getTime() - StartDate.getTime();
			//Diff = Diff/1000;			
			//alert("결과 끝 확인 : " +Diff + "초");	
		}
	});
}