function ajaxSelect(url, data, target, defaultValue, isAll, headerKey, debug) {
	//if(!headerKey)
		headerKey='';
	$.ajax({
		url: url,type: 'post',data: data,error: function(xhr, status, error) {alert("ajax Error ::: status='" +status+"', error='"+error+"'"); },beforeSend: function(x) {if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){if(debug){alert(data);}$("#" + target).hide();if("true" == isAll || "y" == isAll || 'yes' == isAll) {$("#"+target).html("<option value='"+headerKey+"'>All</option>"+data);}else if("select" == isAll) {$("#"+target).html("<option value='"+headerKey+"'>Select</option>"+data);}else if("false" == isAll || "n" == isAll || 'no' == isAll) {$("#"+target).html(data);}else if(isAll!=null) {$("#"+target).html("<option value='"+headerKey+"'>"+isAll+"</option>"+data);}else {$("#"+target).html(data);}$("#" + target).fadeIn(10);if(defaultValue && defaultValue!=null && defaultValue != '') {$set(target, defaultValue);}}
	});	
}
function ajaxMultiSelect(url, data, target, defaultValue, isAll, headerKey, debug) {
	if(!headerKey)headerKey='';
	$.ajax({
		url: url,type: 'post',data: data,error: function(xhr, status, error) {alert("ajax Error ::: status='" +status+"', error='"+error+"'"); },beforeSend: function(x) {if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){if(debug){alert(data);}$("#" + target).hide();if("true" == isAll || "y" == isAll || 'yes' == isAll) {$("#"+target).html("<option value='"+headerKey+"'>All</option>"+data);}else if("select" == isAll) {$("#"+target).html("<option value='"+headerKey+"'>Select</option>"+data);}else if("false" == isAll || "n" == isAll || 'no' == isAll) {$("#"+target).html(data);}else if(isAll!=null) {$("#"+target).html("<option value='"+headerKey+"'>"+isAll+"</option>"+data);}else {$("#"+target).html(data);}if(defaultValue && defaultValue!=null && defaultValue != '') {$set(target, defaultValue);}}
	});	
}
function ajaxPage(url, data, target, preHtml, debug, debugTarget) {
	$.ajax({
		url: url,type: 'post',data: data,async: true,error: function(xhr, status, error) {alert(status+"||"+error);$('#loading').fadeOut(150); },beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}}
		,success: function(data){$('#loading').fadeOut(10);if(debug){if(debugTarget==null){	alert(data);}else {	$("#debugMod").val(data);$("#debugMod").show();}} $("#" + target).hide();if(preHtml!=null){data=preHtml+data;}$("#" + target).html(data);$("#" + target).fadeIn(10);}
	});
}
function ajaxTabPage(url, data, target, preHtml, debug, debugTarget) {
	$.ajax({
		url: url,type: 'post',data: data,async: true,error: function(xhr, status, error) {alert(status+"||"+error);closeMaskLayer(); },beforeSend: function(x) {openMaskLayer();if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}}
		,success: function(data){closeMaskLayer();if(debug){if(debugTarget==null){	alert(data);}else {	$("#debugMod").val(data);$("#debugMod").show();}} $("#" + target).hide();if(preHtml!=null){data=preHtml+data;}$("#" + target).html(data);$("#" + target).fadeIn(10);}
	});
}
function ajaxPageSyn(url, data, target, preHtml, debug, debugTarget) {
	$.ajax({
		url: url,type: 'post',data: data,async: false,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);},beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){$('#loading').fadeOut(10);if(debug) {if(debugTarget==null) {alert(data);}else {$("#debugMod").val(data);$("#debugMod").show();}}$("#" + target).hide();if(preHtml!=null)data=preHtml+data;$("#" + target).html(data);$("#" + target).fadeIn(10);}
	});
}
function ajaxValue(url, data, target, preHtml, debug) {
	$.ajax({
		url: url,type: 'post',data: data,error: function(xhr, status, error) {alert(status+"||"+error);},beforeSend: function(x) {if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){if(debug) {alert(data);}$("#" + target).hide();if(preHtml!=null && preHtml!=""){data=preHtml+data;}$("#" + target).val(data);$("#" + target).fadeIn(0);if($("#" + target)[0].onChange!=null) {$("#" + target)[0].onChange();}}
	});
}
function ajaxValues(url, data, target, preHtml, debug) {
	$.ajax({
		url: url,type: 'post',data: data,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);},beforeSend: function(x) {if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){if(debug) {if( data.length == 0 ) alert("아이디가 존재하지 않습니다 ");}$("#" + target).hide();if(preHtml!=null){ data=preHtml+data;}$("#" + target).val(data);$("#" + target).fadeIn(10);}
	});
}
function ajaxAppendPage(url, data, target, debug) {
	$.ajax({url: url,type: 'post',data: data,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);},beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){if(debug) {alert(data);}$('#loading').fadeOut(10);$("#" + target).hide();$("#" + target).fadeIn(10);$("#" + target).append(data);}
	});
}
function ajaxFileViewer(url, aData,seq) {
	$.ajax({
		url: url,
		type: 'post',
		data: aData,
		async: true,
		error: function(data) {alert("File Transfer Error"); },
		success: function(data){
			if(data.key == null || data.key == undefined) {
				alert("File Transfer Error");
			}
			else {
				var scUrl = "setViewFilePath.do"; 
				var scData = "key="+data.key+"&seq="+seq;
				var scTarget = "help_content";
				ajaxPage(scUrl, scData, scTarget);
			}
		}
	});
}
function ajaxCheckFile(url) {
	var check = false;
	$.ajax({
		url: url,
		type: 'get',
		async: true,
		error: function(data) {return false;},
		success: function(data){
			if(data.key == null || data.key == undefined) {
			}
			else {
				check = true;
			}
		}
	});
	
	return check;
}
function ajaxSubmit(submitForm, action, iFrameName) {if($("#isSubmit").val()=='true') {alert('저장 중입니다 잠시만 기다려 주십시오.');return false;} else {$add("isSubmit", "true", submitForm);if(action!=null) {submitForm.action = action;}if(iFrameName!=null) {submitForm.target=iFrameName;}else {makeFrame();submitForm.target="saveFrame";}submitForm.submit();return true;}}
function ajaxSubmitNoAlert(submitForm, action, iFrameName) {
	$add("isSubmit", "true", submitForm);
	if(action!=null) {
		submitForm.action = action;}if(iFrameName!=null) {
			//alert(iFrameName);
			submitForm.target=iFrameName;}else {makeFrame();submitForm.target="saveFrame";}submitForm.submit();return true;}
function ajaxSubmitNoAdd(submitForm, action, iFrameName) {if(action!=null) {submitForm.action = action;}if(iFrameName!=null) {submitForm.target=iFrameName;}else {makeFrame();submitForm.target="saveFrame";} submitForm.submit();return true;}
function makeFrame(ifrmNm){var ifrm = $('#'+ifrmNm);if(ifrm.length == 0){/*$('body').append("<iframe id='"+ifrmNm+"' src='about:blank' style='display:none' frameborder='0'></iframe>");*/}}
function closeMaskLayer(){if($("#mask").length >0){$("#mask").hide();}}
function ajaxRadio(url, data, target, defaultValue,radioID, isDisabled) {

	$.ajax({
		url: url,
		type: 'post',
		data: data,
		error: function(xhr, status, error) {alert("ajax Error ::: status='" +status+"', error='"+error+"'"); },
		beforeSend: function(x) {if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(data){

			$("#" + target).hide();		
			$("#"+target).html(data);
			$("#" + target).fadeIn(10);
			
			if(defaultValue && defaultValue!=null && defaultValue != '') {
				$('input:radio[name='+radioID+']:input[value=' + defaultValue + ']').prop("checked", true);
			}
			
			if(isDisabled != null && isDisabled == "Y") {
				$("input[name="+radioID+"]").prop("disabled",true);
			}
				
		}
			
	});	
}