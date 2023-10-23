function ajaxGridLoad(url, data, target, debug, noMsg, totCntrNm, callback, msg, msg2, maxCntNum, lockColNum) {
	var totCntName = totCntrNm;
	$.ajax({
		url: url,type: 'post',data: data
		,error: function(xhr, status, error) {('#loading').fadeOut(150);fnCheckSearch('E');var errUrl = "errorPage.do";var errMsg = "status::"+status+"///error::"+error;var callback = "";var width = "300px";var height = "300px";var callBack = function(){};fnOpenLayerPopup(errUrl,errMsg,callBack,width,height);}
		,beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(result){
			$('#loading').fadeOut(100);	fnCheckSearch('E'); if(debug){	alert(result);}	if(result == 'error' || result == ""){if(noMsg != 'Y'){alert(msg);}
			}else{
				result = eval('(' + result + ')');
				var rows = result.rows;
				var pageNum = result.pageNum;
				if(rows == ""){if(noMsg != 'Y'){/*alert(msg);*/}}
				target.parse(result,"json");
				if( pageNum == null || pageNum == undefined || pageNum == "" ){}else{target.changePage(pageNum);}
				var totCnt = target.getRowsNum();if(totCnt==""){totCnt="0";}
				
				/* Max 건수 check : maxCntNum 건 */
				if (target.getRowsNum() == maxCntNum && msg2 != '') {
					alert(msg2); // message 표시
				}
				
				if( totCntName == null || totCntName == undefined || totCntName == "" ){var totCntrNm = 'TOT_CNT';var totCntr = $('#'+totCntrNm);if(totCntr.length == 0){}else{$('#' + totCntrNm).html(totCnt);}
				}else{/*$('#' + totCntrNm).val(totCnt);*/$('#' + totCntName).html(totCnt);}
				if(callback== null || callback==""){}
				else{ eval(callback);}
				
				/* lock 처리를 해 줄 column이 존재 하는 경우, 해당 row의 lock 처리를 해 준다  */
				//if (lockColNum != '') {
				//	fnGridRowBlocked(target, lockColNum);
				//}
			}
		}
	});
}

/**
 * 그리드에 Row lock
 * @param target : 그리드
 * @param lockColNum : blocked 정보가 있는 column 번호
 */
function fnGridRowBlocked(target, lockColNum) {
	var ids = target.getAllRowIds(',');//모든 로우 데이터
	if(ids!=null && ids != ''){
		ids=ids.split(',');
		for (var i = 0; i < ids.length; i++) {
			var blocked = target.cells(ids[i], lockColNum).getValue();
			if (blocked != 0) {
				target.lockRow(i+1, true);
			}
		}
	}
}

/**
 * 그리드에 Row 추가 버튼생성
 * @param btnId : 추가 버튼ID
 * @param grid : 추가될 그리드
 * @param form : 수정시 사용될 form
 * @param rowIndex : 추가될 위치(0은 처음)
 * @param defData : 초기값("번호,이름,값,..." - ,를 구분값으로 저장)
 * @return
 */
function fnGridAddRow(btnId, grid, form, rowIndex, defData) {
	$('#'+btnId).click(function(){
		fnAddRow(grid, form, rowIndex, defData);
	});
	$('#'+btnId).css("cursor","hand");
}
/**
 * 그리드 Row 추가
 * @param grid
 * @param form
 * @param rowIndex
 * @param defData
 * @return
 */
function fnAddRow(grid, form, rowIndex, defData) {
	if (rowIndex == null) {
		rowIndex = "";
	}
	if (form == null) {
		form = $form();
	}
	if(defData==null) {
		defData = "";
	}
	var newId = (new Date()).valueOf();
	var gridUid = grid.entBox.id;
	var cnt = grid.getColumnsNum();
	grid.addRow(newId,defData,rowIndex);
	$add("rowStatus"+newId, "I",form);
	for (cin = 0; cin < cnt; cin++) {
		if(grid.getColType(cin)=='ch') {
			grid.cells(newId,cin).setValue(0);
		}
	}
	grid.selectRowById(newId);
	return newId;
}

function fnGridDeleteRow(btnId, grid, form, rowIndex) {
	$('#'+btnId).click(function(){
		var row= grid.getSelectedRowId();
		$("#rowStatus"+row).remove();
		grid.deleteSelectedItem();
	});
}

function fnGridDblClickEdit(grid, form) {
	grid.attachEvent("onRowDblClicked", function(id,ind){
		grid.editCell(id,ind);
	});
}

/**
 * 수정된 줄은 전부 컬럼을 hidden으로 생성하여 반환한다.
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllCol(grid, form) {
//	var ids = grid.getAllRowIds().split(',');//전체 로우 데이터
//	var ids=grid.getCheckedRows(col_ind);//체크된 로우
	var ids=grid.getChangedRows(true);//수정된 로우 데이터만(추가된 로우여부)
	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	}
	else {
		alert('수정한 내용이 없습니다.');
		return false;
	}
}

/**
 * 수정된 줄은 전부 컬럼을 hidden으로 생성하여 반환한다.(값이 없을경우 alert 창 띄우지 않음)
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllColNoAlert(grid, form) {
	var ids=grid.getChangedRows(true);//수정된 로우 데이터만(추가된 로우여부)
	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	} else {
		return false;
	}
}

/**
 * 수정된 줄은 전부 컬럼을 hidden으로 생성하여 반환한다.(값이 없을경우 alert 창 띄우지 않음)
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllColNoAlertAll(grid, form) {
	var ids=grid.getAllRowIds(',');//모든 로우 데이터

	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	} else {
		return false;
	}
}

/**
 * 수정된 줄은 전부 컬럼을 hidden으로 생성하여 반환한다.(값이 없을경우 alert 창 띄우지 않음)
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllColNoAlertAll_muti(grid, form, gubun) {
	var ids=grid.getAllRowIds(',');//모든 로우 데이터

	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;

		$add("colName"+gubun, $("#gridCols_"+gridUid).val(), form);
		$add("rowCount"+gubun, ids.length, form);
		$add("colNum"+gubun, grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add(gubun+"r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add(gubun+"r"+i+"_type", rowType, form);
		}
		return true;
	} else {
		return false;
	}
}

/**
 * 컬럼을 모두 insert상태로 넘김.
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllColToInsert(grid, form) {
	var ids=grid.getAllRowIds(',');//모든 로우 데이터
	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "I";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	} else {
		return false;
	}
}

/**
 * Check 체크된 줄은 전부 컬럼을 hidden으로 생성하여 반환한다.
 * @param grid
 * @param form
 * @return
 */
function fnFetchDHTMLXAllCheck(grid, form, col) {

	if( col == undefined || col == '' ) col = 1;
	var ids = grid.getCheckedRows(col);	//선택된 체크박스

	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	} else {
		alert('선택된 내용이 없습니다.');
		return false;
	}
}

function fnFetchDHTMLXAllRowId(grid) {
	return grid.getAllRowIds(".").split('.');
}
/**
 * 선택된 줄의 컬럼을 hidden으로 생성하여 반환한다.
 * @param grid
 * @param form
 * @return
 */
function fnFetchSelectedCol(grid, col_ind, form) {
	var ids=grid.getCheckedRows(col_ind);//체크된 로우
	if(ids!=null && ids != ''){
		ids=ids.split(',');

		var gridUid = grid.entBox.id;
		$add("colName", $("#gridCols_"+gridUid).val(), form);
		$add("rowCount", ids.length, form);
		$add("colNum", grid.getColumnsNum(), form);
		for (i = 0; i < ids.length; i++) {
			for (j = 0; j < grid.getColumnsNum(); j++) {
				$add("r"+i+"_col"+j, grid.cells(ids[i],j).getValue(), form);//form에 "r" + rowIndex + "_col + colIndex의 이름과  ID를 가지고 값은 컬럼의 값을 가진 객체 생성
			}
			var rowType = $("#rowStatus"+ids[i]).val();
			if (rowType==null) {
				rowType = "U";
			}
			$add("r"+i+"_type", rowType, form);
		}
		return true;
	}
	else {
		alert('선택한 내용이 없습니다.');
		return false;
	}
}

/**
 * 그리드에 컴보박스(Select) 형식의 값 추가
 * @param grid
 * @param index : 컬럼 순번
 * @param key	: 값(code)
 * @param value : view
 */
function fnAddCombo(grid, index, key, value) {
	grid.getCombo(index).put(key, value);
}

function fnAddRowCombo(grid, id, index, key, value) {
	grid.getCustomCombo(id, index).put(key, value);
	// grid.getCustomCombo(id, index);
}

function fnSetColType(grid, index, type) {
	var types = "ro";
	var cnt = grid.getColumnsNum();

	for (cin = 1; cin < cnt; cin++) {
		if(cin == index) {
			types+= "," + type;
		}
		else {
			types+= "," + grid.getColType(cin);
		}
	}
	if(type == 'ron' || type == 'edn') {
		grid.setNumberFormat("0,000",index);
	}
	grid.setColTypes(types);
}
/**
 * Ajax를 이용한 Grid의 Combo 조회
 * @param url : 호출될 action 주소
 * @param data : 파라미터들.. get방식의 파라미터 형식으로 하는게 편하다.
 * @param grid : grid
 * @param colIndex : grid의 해당 컬럼
 * @param preHtml : 결과로 받아온 문구 앞에 붙일 내용(필수 아님)
 */
function ajaxGridCombo(url, data, grid, colIndex, debug) {
	$.ajax({
		url: url
		,type: 'post'
		,data: data
		,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);}
		,beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/xml;charset=UTF-8");}	}
		,success: function(data){
			$('#loading').fadeOut(100);
			if(debug) {
				alert(data);
			}
			var rows = data.split("|");
			for (gin = 1; gin < rows.length; gin++) {
				var row = rows[gin].split(",");
				fnAddCombo(grid, colIndex, row[0], row[1]);
			}
			fnGridReload(grid);
		}
	});
}


/**
 * Ajax를 이용한 Grid의 Combo 조회
 * @param url : 호출될 action 주소
 * @param data : 파라미터들.. get방식의 파라미터 형식으로 하는게 편하다.
 * @param grid : grid
 * @param colIndex : grid의 해당 컬럼
 * @param preHtml : 결과로 받아온 문구 앞에 붙일 내용(필수 아님)
 */
function ajaxGridComboSync(url, data, grid, colIndex, debug) {
	$.ajax({
		url: url
		,type: 'post'
		,data: data
		,async:false
		,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);}
		,beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/xml;charset=UTF-8");}	}
		,success: function(data){
			$('#loading').fadeOut(100);
			if(debug) {
				alert(data);
			}
			var rows = data.split("|");
			for (gin = 1; gin < rows.length; gin++) {
				var row = rows[gin].split(",");
				fnAddCombo(grid, colIndex, row[0], row[1]);
			}
		}
	});
}


function ajaxGridRowComboSync(url, data, grid, rowId, colIndex, value, debug) {
	$.ajax({
		url: url
		,type: 'post'
		,data: data
		,async:false
		,error: function(xhr, status, error) {alert(status); ('#loading').fadeOut(150);}
		,beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/xml;charset=UTF-8");}	}
		,success: function(data){
			$('#loading').fadeOut(100);
			if(debug) {
				alert(data);
			}
			
			var rows = data.split("|");
			for (gin = 1; gin < rows.length; gin++) {
				var row = rows[gin].split(",");
				fnAddRowCombo(grid, rowId, colIndex, row[0], row[1]);
			}
			
			grid.cells(rowId, colIndex).setValue(value);
		}
	});
}

/**
 * 그리드의 높이 재조정
 * @param grid
 * @param newGirdHeight
 * @return
 */
function setGridHeight(grid, newGirdHeight) {
	grid.entBox.style.height = newGirdHeight;
	grid.gridHeight = newGirdHeight;
	grid.setSizes();
}

/**
 * 모든 로우ID 가져오기
 * @param grid
 * @return
 */
function getAllRowID(grid) {
	var result = new Array();
	grid.forEachRow(function(id) {
		result[result.length] = id;
	});
	return result;
}
/**
 * 모든 로우 가져오기(마지막 컬럼 뒤에는 로우아이디를 넣어준다)
 * @param grid
 * @return
 */
function getAllRowData(grid) {
	var result = new Array();
	grid.forEachRow(function(id) {
		if(grid._deletedRows == null || grid._deletedRows.indexOf("|"+id+"|")==-1) {
			var data = new Array();
			for (j = 0; j < grid.getColumnsNum(); j++) {
				data[j] = grid.cells(id,j).getValue();
			}
			data[data.length] = id;
			result[result.length] = data;
		}
	});
	return result;
}

/**
 * Grid 전체 Row갯수 가져오기
 * @param grid
 * @return
 */
function getRowCount(grid) {
	var cnt = 0;
	grid.forEachRow(function(id) {
		if(grid._deletedRows == null || grid._deletedRows.indexOf("|"+id+"|")==-1) {
			cnt = cnt +1;
		}
	});
	return cnt;
}
/**
 * 로우 가져오기(마지막 컬럼 뒤에는 로우아이디를 넣어준다)
 * @param grid
 * @return
 */
function getRowData(grid) {
	var result = new Array();
	var row= grid.getSelectedRowId();
	for (j = 0; j < grid.getColumnsNum(); j++) {
		result[j] = grid.cells(row,j).getValue();
	}
	result[result.length] = row;
	return result;
}
/**
 * 해당 그리드의 모든 데이터를 컬럼명과 매칭하여 ajax의 data(request의 get방식)으로 생성하여 반환한다.
 * - 삭제된 로우 제외 추가
 * @param grid
 * @return
 */
function getAllMapData(grid) {
	var gridUid = grid.entBox.id;
	var rows = getAllRowData(grid);
	var cols = $get("gridCols_"+gridUid).split('|');
	var result = "&gridUid="+gridUid;
	var i, j = 0, temp;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			for(j = 0; j < cols.length; j++) {
				temp = row[j+1];
				try {
					temp = temp.replace('%','');
					temp = temp.replace('&lt;','<');
					temp = temp.replace('&gt;','>');
					temp = temp.replace('=','-');
				}catch (e) {}
				result += "&" + cols[j] + "=" + temp;
			}
		}
	return result;
}
/**
 * 해당 그리드의 선택된 로우의 데이터를 컬럼명과 매칭하여 ajax의 data(request의 get방식)으로 생성하여 반환한다.
 * @param grid
 * @return
 */
function getMapData(grid) {
	var gridUid = grid.entBox.id;
	var row = getRowData(grid);
	var cols = $get("gridCols_"+gridUid).split('|');
	var result = "&gridUid="+gridUid;
	for(j = 0; j < cols.length; j++) {
		try {
			result += "&" + cols[j] + "=" + row[j+1].replace('%','').replace('&lt;','<').replace('&gt;','>');
		} catch(e) {result += "&" + cols[j] + "=" + row[j+1];}
	}
	return result;
}

/**
 * 해당 그리드의 컬럼명모음을 가져온다.
 * @param grid
 * @return
 */
function getCols(grid){
	var gridUid = grid.entBox.id;
	return $get("gridCols_"+gridUid);
}

/**
 * 해당 그리드의 모든 row중 해당 컬럼에 searchValue가 있는지 검색하여 있다면 true 반환.
 * @param grid
 * @param colIndex
 * @param searchValue
 * @return boolean
 */
function searchExistValue(grid, colIndex, searchValue) {
	var result = false;
	grid.forEachRow(function(id) {
		if(searchValue == grid.cells(id,colIndex).getValue()) {
			result = true;
		}
	});
	return result;
}
/**
 * 해당 그리드의 모든 row중 해당 컬럼에 searchValue가 아닌 값이 있는지 검색하여 있다면 true 반환.
 * @param grid
 * @param colIndex
 * @param searchValue
 * @return boolean
 */
function searchExistExecptValue(grid, colIndex, searchValue) {
	var result = false;
	grid.forEachRow(function(id) {
		if(searchValue != grid.cells(id,colIndex).getValue()) {
			result = true;
		}
	});
	return result;
}

/**
 * 해당 그리드의 모든 row중 해당 컬럼에 최대값을 검색하여 반환.
 * @param grid
 * @param colIndex
 * @return boolean
 */
function searchMaxValue(grid, colIndex) {
	var result = '';
	grid.forEachRow(function(id) {
		if(result < grid.cells(id,colIndex).getValue()) {
			result = grid.cells(id,colIndex).getValue();
		}
	});
	return result;
}
/**
 * 해당 그리드의 모든 row중 해당 컬럼에 최소값을 검색하여 반환.
 * @param grid
 * @param colIndex
 * @return boolean
 */
function searchMinValue(grid, colIndex) {
	var result = null;
	grid.forEachRow(function(id) {
		if(result == null || result > grid.cells(id,colIndex).getValue()) {
			result = grid.cells(id,colIndex).getValue();
		}
	});
	return result;
}

/**
 * 해당 그리드의 모든 row중 해당 컬럼에 value로 세팅
 * @param grid
 * @param colIndex
 * @param value
 * @return boolean
 */
function setColValue(grid, colIndex, value) {
	var result = false;
	grid.forEachRow(function(id) {
		grid.cells(id,colIndex).setValue(value);
	});
	return result;
}

/**
 * 해당 그리드의 모든 row중 해당 컬럼에 값을 합하여 반환.
 * @param grid
 * @param colIndex
 * @return boolean
 */
function sumColValue(grid, colIndex, condition) {
	var result = 0;
	if(condition == null) {
		grid.forEachRow(function(id) {
			if(grid.cells(id,colIndex).getValue() != null && grid.cells(id,colIndex).getValue() != '') {
				result += grid.cells(id,colIndex).getValue()*1;
			}
		});
	}
	else {
		grid.forEachRow(function(id) {
			if(grid.cells(id,colIndex).getValue() != null && grid.cells(id,colIndex).getValue() != '') {
				switch (condition.status) {
				case ">" :
					if(grid.cells(id,condition.col).getValue() > condition.value) {
						result += grid.cells(id,colIndex).getValue()*1;
					}
					break;
				case "<" :
					if(grid.cells(id,condition.col).getValue() < condition.value) {
						result += grid.cells(id,colIndex).getValue()*1;
					}
					break;
				case "=" :
					if(grid.cells(id,condition.col).getValue() == condition.value) {
						result += grid.cells(id,colIndex).getValue()*1;
					}
					break;
				case "!=" :
					if(grid.cells(id,condition.col).getValue() != condition.value) {
						result += grid.cells(id,colIndex).getValue()*1;
					}
					break;
				}
			}
		});
	}
	return result;
}


function fnGridReload(grid) {
	grid.forEachRow(function(id) {
		with(grid) {
			var data = new Array();
			for (j = 0; j < getColumnsNum(); j++) {
				cells(id,j).setValue(cells(id,j).getValue());
			}
		}
	});
}

function fnGridReadOnlyRow(grid, rid, t) {
	if(t==null) {
		t = true;
	}
	if(t == 'false') {
		grid._readOnlyRows = grid._readOnlyRows.replace(rid+"|", "");
		t = false;
	}
	else {
		grid._readOnlyRows += rid + "|";
	}
	grid.lockRow(rid,t);
}

function $cell(cols) {
	var result = new Object();
	if (cols != null && cols.indexOf("|") != -1) {
		result.cols = ("NO|"+cols).split("|");
		for(colIndex = 0; colIndex < result.cols.length; colIndex++) {
			eval("result."+result.cols[colIndex]+"='"+colIndex+"'");
		}
	}
	return result;
};

var $selectValue = function(grid, cols) {
	this._grid = grid;
	this._cols = cols;
	this._grid._readOnlyRows = "|";
	this.cid = function (colName) {
		return eval("this._cols."+colName);
	};

/*
	this.total = function () {
		var result = this._grid.getRowsNum();
		if(this._grid._deletedRows != null) {
			try {
				result -= (this._grid._deletedRows.split("|").length-2);
			}
			catch (e) {}
		}
		return result;
	};
*/
	this.total = function () {
		var cnt = 0;
		grid.forEachRow(function(id) {
			if(grid._deletedRows == null || grid._deletedRows.indexOf("|"+id+"|")==-1) {
				cnt = cnt +1;
			}
		});
		return cnt;
	};



	this.max = function(colName) {
		if(this.total()>0) {
			return searchMaxValue(this._grid, this.cid(colName));
		}
		else {
			return "";
		}
	};
	this.min = function(colName) {
		if(this.total()>0) {
			return searchMinValue(this._grid, this.cid(colName));
		}
		else {
			return "";
		}
	};
	/**
	 * 선택된 Row ID를 반환한다.
	 */
	this.selectedRow = function() {
		return this._grid.getSelectedRowId();
	};
	this.select = function(rowId) {
		return this._grid.selectRowById(rowId);
	};
	/**
	 * 해당 컬럼에 검색한 값이 있는가 여부
	 */
	this.exist = function(colName, searchValue) {
		return searchExistValue(this._grid, this.cid(colName), searchValue);
	};
	/**
	 * 해당 컬럼에 검색한 값을 제외한 경우가 있는가 여부
	 */
	this.existExecpt = function(colName, searchValue) {
		return searchExistExecptValue(this._grid, this.cid(colName), searchValue);
	};
	/**
	 * 해당 그리드의 rid의 row에서 colName에 해당되는 cell의 값을 반환한다.
	 * 만약 rid가 생략(parameter를 1개만 넣었을 경우를 의미)될 경우 해당 그리드의 선택된 row의 값이 반환된다.
	 */
	this.get = function(rid, colName) {
		try {
			if (rid!=null && colName != null) {
				return this._grid.cells(rid,this.cid(colName)).getValue();
			}
			else if (this._grid.getSelectedRowId()!=null) {
				if (rid!=null && colName == null) {//rid가 아닌 컬럼이다.
					colName = rid;
				}
				if (colName!=null) {
					return this._grid.cells(this._grid.getSelectedRowId(), this.cid(colName)).getValue();
				}
				else if (this._grid.getSelectedCellIndex()!=null) {
					return this._grid.cells(this._grid.getSelectedRowId(), this._grid.getSelectedCellIndex()).getValue();
				}
			}
		}
		catch(e) {//해당 컬럼이 존재 하지 않는 등 오류 발생시..
			return "";
		}
	};
	/**
	 * 해당 그리드의 rid를 가진 row의 colName의 cell에 value를 설정한다.
	 * 만약 rid가 생략(parameter를 2개만 넣었을 경우를 의미)될 경우 해당 그리드의 선택된 row에 값이 할당된다.
	 * 만약 rid와 colName이 생략(parameter를 1개만 넣었을 경우를 의미)될 경우 해당 그리드의 선택된 row에 선택된 cell에 값이 할당된다.
	 */
	this.set = function(rid, colName, value) {
		if ((colName == null || value == null)) {
			if (this._grid.getSelectedRowId()!=null) {
				if (colName != null && value == null) {//rid가 컬럼, colName이 value
					value = colName;
					colName = rid;
					rid = this._grid.getSelectedRowId();
				}
				else if (colName == null && value == null) {
					value = rid;
					colName = this._grid.getSelectedCellIndex();
					rid = this._grid.getSelectedRowId();
				}
			}
		}
		if(this._grid._readOnlyRows.indexOf(rid) != -1) {
			return false;
		}
		return this._grid.cells(rid,this.cid(colName)).setValue(value+'');
	};
	this.setAll = function(colName, value) {
		return setColValue(this._grid, this.cid(colName), value);
	};
	this.sum = function(colName, condition) {
		condition.col = this.cid(condition.col);
		return sumColValue(this._grid, this.cid(colName), condition);
	};
	this.add = function(defData, rowIndex, form) {
		if(defData==null) {
			defData = "신규";
		}
		if(rowIndex==null) {
			rowIndex = this._grid.getSelectedRowId();
		}
		return fnAddRow(this._grid, form, rowIndex, defData);
	};
	this.copyAdd = function(rowIndex, defData, form) {
		if(rowIndex==null) {
			rowIndex = this._grid.getSelectedRowId();
		}
		if(defData==null) {
			defData = "복사";
		}
		var rid = this.add(rowIndex, defData, form);
		this._grid.copyRowContent(rowIndex,rid);
		this._grid.selectRowById(rowIndex);
		return rid;
	};
	this.hide = function(colName, condition) {
		if(condition==null) {
			condition = true;
		}
		this._grid.setColumnHidden(this.cid(colName),condition);
	};
	this.show = function(colName, condition) {
		if(condition==null) {
			condition = false;
		}
		this._grid.setColumnHidden(this.cid(colName),condition);
	};
	this.event = function(event, func) {
		this._grid.attachEvent(event, func);
	};
	this.forEachRow = function(func) {
		this._grid.forEachRow(func);
	};
	this.remove = function(func) {
		var rid = this.selectedRow();
		var _deletedRows = this._grid._deletedRows;
		if(_deletedRows == null) {
			this._grid._deletedRows = "|";
		}
		if (rid != null) {
			$("#rowStatus"+rid).remove();
			this._grid._deletedRows += rid + "|";
			this._grid.deleteSelectedItem();
		}
		else {
			alert("선택된 항목이 없습니다.\n삭제하실 항목을 선택하여 주십시오.");
		}
	};
	this.lockRow = function(rid, t) {
		fnGridReadOnlyRow(this._grid,rid, t);
	};
	this.readOnly = function(rid, t) {
		fnGridReadOnlyRow(this._grid,rid, t);
	};
	this.selectRowById = function(rid) {
		this._grid.selectRowById(rowIndex);
	};
	this.setMathRound = function(colName) {
		this._grid.setMathRound(this.cid(colName));
	};
	this.setColType = function(colName, type) {
		fnSetColType(this._grid, this.cid(colName), type);
	};
	this.setCombo = function(colName, arg, sqlId) {
		fnGridCombo(this._grid, this.cid(colName), arg, sqlId);
	};
	this.allRowID = function() {
		return getAllRowID(this._grid);
	};
};




