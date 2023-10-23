//다중 업로드
var sub_scripts = new Array;
var sub_dms_scripts = new Array;
var sub_beforeFileName = "";
var sub_dms_beforeFileName = "";
var sub_idx = 0;
var sub_dms_idx = 0;
var scr_type = "_sub";

function make_array_sub(status, display_script, item){this.status = status;this.display_script = display_script;this.item = item;}

function attach_sub(type, obj){var val = obj.value;obj.style.display = 'none';	if(type == "1"){add_item_sub(type, ++sub_idx, val);} else {add_item_sub(type, ++sub_dms_idx, val);}	item_list_sub(type);}

function add_item2_sub(type, idx){
	var file_script;
	var fileNum;

	if(type == "1"){
		if (beforeFileName == "filex"+scr_type + idx) {fileNum = idx + 1;} else {fileNum = idx;}
		file_script = '<span id=file_item'+scr_type+idx+' class="file_attach"><input type=file name=filex'+scr_type+fileNum+' id=filex'+scr_type+fileNum+' onchange=attach_sub("1",this) size=1></span>';
		$('#file_item_P'+scr_type).append(file_script);
		sub_beforeFileName = "filex" + fileNum;
	} else {
		if (dms_beforeFileName == "dms_filex"+scr_type + idx) {fileNum = idx + 1;} else {fileNum = idx;}
		file_script = '<span id=dms_file_item'+scr_type+idx+' class="file_attach"><input type="file" name="dms_filex'+scr_type+fileNum+'" id="dms_filex'+scr_type+fileNum+'" onchange=attach_sub("2",this) size=1></span>';
		$('#dms_file_item_D'+scr_type).append(file_script);
		sub_dms_beforeFileName = "dms_filex"+scr_type+ fileNum;
	}
}

function add_item_sub(type, idx, val){
	var file_script;
	alert(type+"::::"+idx+"::::::"+val);
	if(type == "1"){file_script='<span id=file_item'+scr_type+idx+' class="file_attach" ><input type=file name=file'+scr_type+idx+' id=file'+scr_type+idx+' onchange=attach_sub("1",this) size=1></span>';}else{file_script='<span id=dms_file_item'+scr_type+idx+' class="file_attach" ><input type=file name=dms_file'+scr_type+idx+' id=dms_file'+scr_type+idx+' onchange=attach_sub("2",this) size=1></span>';}
	for(var i = 0 ; i < sub_scripts.length; i++){
		if(sub_scripts[i].item.length > 0 && val.length > 0  && sub_scripts[i].item == val  && sub_scripts[i].status) {
			alert("기존에 첨부파일 목록에 추가된 파일입니다. 다른 파일을 추가하십시오. ");
			document.getElementById("file_item"+scr_type+(idx-1)).innerHTML = '';
			add_item2_sub(type, idx);
			return;
		}
	}
	for(var i = 0 ; i < sub_dms_scripts.length; i++){
		if(sub_dms_scripts[i].item.length > 0 && val.length > 0  && sub_dms_scripts[i].item == val && sub_dms_scripts[i].status) {
			alert("기존에 DMS 파일 목록에 추가된 파일입니다. 다른 파일을 추가하십시오. ");
			document.getElementById("dms_file_item"+scr_type+(idx-1)).innerHTML = '';
			add_item2_sub(type, idx);
			return;
		}
	}	
	if(type == "1"){
		var seq = sub_scripts.length;
		var display_script = '<span id=display_item'+scr_type+idx+'>'+val+
			'&nbsp;&nbsp;<img src="./cmm/images/btn_file_cancel.png" onclick=remove_item_sub("1",'+seq+','+idx+')></span><br>';
		sub_scripts[seq] = new make_array_sub(true, display_script, val);
		$('#file_item_P'+scr_type).append(file_script);
	} else{
		var seq = sub_dms_scripts.length;
		var display_script = '<span id=dms_display_item'+scr_types+idx+'>'+val+
			'&nbsp;&nbsp;<img src="./cmm/images/btn_file_cancel.png" onclick=remove_item_sub("2",'+seq+','+idx+')></span><br>';
		sub_dms_scripts[seq] = new make_array_sub(true, display_script, val);
		$('#dms_file_item_D'+scr_type).append(file_script);
	}
}

function item_list_sub(type){
	var validate_cnt = 0;
	var display_scripts = '';
	var items = '';	
	if(type == "1"){	
		for(var i = 0 ; i < sub_scripts.length; i++){
			if(sub_scripts[i].status){
				validate_cnt++;
				display_scripts += '<b>'+
				validate_cnt+'</b>.'+sub_scripts[i].display_script;
				items += sub_scripts[i].item + "|";
			}
		}		
		if(validate_cnt == 0 ){display_scripts = '';}
		document.getElementById("display_items"+scr_type).innerHTML = display_scripts;
		document.getElementById("items"+scr_type).value = items;
	}else{
		for(var i = 0 ; i < sub_dms_scripts.length; i++){
			if(sub_dms_scripts[i].status){
				validate_cnt++;
				display_scripts += '<b>'+
				validate_cnt+'</b>.'+sub_dms_scripts[i].display_script;
				items += sub_dms_scripts[i].item + "|";
			}
		}		
		if(validate_cnt == 0 ){display_scripts = '';}
		document.getElementById("dms_display_items"+scr_type).innerHTML = display_scripts;
		document.getElementById("dms_items"+scr_type).value = items;
	}
}
function remove_item_sub(type,seq,idx){
	if( type == "1"){sub_scripts[seq].status = false;document.getElementById("file_item"+scr_type+(idx-1)).innerHTML = '';}else{sub_dms_scripts[seq].status = false;document.getElementById("dms_file_item"+scr_type+(idx-1)).innerHTML = '';}
	item_list_sub(type);
}
