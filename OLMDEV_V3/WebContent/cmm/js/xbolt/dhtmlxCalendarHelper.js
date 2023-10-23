var _calendarObject = null;
var _calendarObjectId = "";

if(typeof dhtmlXCalendarObject == 'function') {
	dhtmlXCalendarObject.prototype.langData["KO"] = {
	    langname: 'KO',
	    dateformat: '%Y-%m-%d',
	    monthesFNames: [" 1월", " 2월", " 3월", " 4월", " 5월", " 6월", " 7월", " 8월", " 9월", " 10월", " 11월", " 12월"],
	    monthesSNames: [" 1월", " 2월", " 3월", " 4월", " 5월", " 6월", " 7월", " 8월", " 9월", " 10월", " 11월", " 12월"],
	    daysFNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
	    daysSNames: ["일", "월", "화", "수", "목", "금", "토"],
	    weekstart: 7,
	    msgClose: "닫기",
	    msgMinimize: "최소화",
	    msgToday: "오늘"
		};
}

function doCalendarInit()
{
	if( _calendarObject == null ){
		var divElement = document.createElement("div");
		divElement.setAttribute("id", "dhtmlxcalendar");
		divElement.setAttribute("style", "position: absolute; z-index: 999;height: 250px;");
		document.getElementsByTagName("body")[0].appendChild(divElement);
		_calendarObject = new dhtmlxCalendarObject('dhtmlxcalendar', false, {
	        isYearEditable: true
	    });
		//_calendarObject.setYearsRange(2000, 2500);
		//_calendarObject.setWeekStartDay(7);
		_calendarObject.loadUserLanguage(languageCode);
		_calendarObject.setSkin("dhx_terrace");
		//_calendarObject.draw();
		_calendarObject.hide();
		_calendarObject.hideTime();
		
		_calendarObject.attachEvent("onClick",function(date){ 
			doSelectDate(date);
		});
	}
	_calendarObjectId = "";
}

// [calendar] close
function closeCalendar() {
	if(_calendarObject._isVisible()){
		_calendarObject.hide();
	}
}

function doClickCalendar(type, obj, posX, posY, dateType, sToday, itemName)
{
	
	if( posX == null || posX == 'undefined' ) posX = 20;
	if( posY == null || posY == 'undefined' ) posY = -130;
	doCalendarInit();
	if(_calendarObject._isVisible()){
		_calendarObject.hide();
	}else{

		// [속성 edit화면]스크롤 된 화면에 calendar가 표시 될 경우의 calendar 위치 조정
		//  TODO : 다른 화면에서 calendar 높이 조정이 필요 한 경우 추후 공통 처리로 수정
		var divScrollTopHeight = 0;
		if (document.getElementById("editArea") != null ) {
			divScrollTopHeight = document.getElementById("editArea").scrollTop;
		}
		
		var calObj = 0;
		
		if (type == "N") {
			calObj = document.getElementById(obj);
			calObj.focus();
		} else {
			calObj = obj;
		}
		
		var target = document.getElementById(obj);
		if(!target) target = obj
		var targetLeft = window.pageXOffset + target.getBoundingClientRect().left;
		var targetTop = window.pageYOffset + target.getBoundingClientRect().top;
		
		var top = targetTop + target.getBoundingClientRect().height - divScrollTopHeight - 1;
		var left = targetLeft;
		
		// 달력을 표시할 텍스트 박스나 버튼이 해당화면의 아랫 쪽이나 화면 끝 쪽에 위치 할 때, 달력 위치 조정 
		if (targetTop > (document.body.clientHeight - 280)) {
			top = top - 263 - target.getBoundingClientRect().height;
		}
		
		if ( targetLeft > (document.body.clientWidth - 150)) {
			left = left - 150;
		}
		
		$("#dhtmlxcalendar").attr("style", "left:" + left +"px; top:" + top + "px; position: absolute !important; z-index:9999");  
		
		if (type == "N") {
			if(isValidDate($("#" + obj).val())) {
				_calendarObject.setDate($("#" + obj).val());
			} else {
				_calendarObject.setDate('');
			}
		} else {
			if(isValidDate(obj.value)) {
				_calendarObject.setDate(obj.value);
			} else {
				_calendarObject.setDate('');
			}
		}
		
		_calendarObject.show();
	}
	
	if(type == "Y")	_calendarObjectId = obj.id;
	else _calendarObjectId = obj;
	
	_sToday = sToday;
	_dateType = dateType;
	_datecount = 0;
	if( itemName == null || itemName == 'undefined' ) itemName = "해당항목";
	_dateItemName = itemName;	
}

function doSelectDate(date) {
	if(_dateType == "YM"){
		document.getElementById(_calendarObjectId).value = _calendarObject.getFormatedDate("%Y-%m", date);
		_calendarObject.hide();
	}else if(_dateType == "Before"){
		if(_calendarObject.getFormatedDate("%Y%m%d", date) < _sToday && _datecount == 0){
			alert(_dateItemName+"은(는) 익일부터 등록가능합니다.");
			document.getElementById(_calendarObjectId).value = makeDateType(_sToday);
			_calendarObject.hide();
		}else if(_calendarObject.getFormatedDate("%Y%m%d", date) >= _sToday){
			document.getElementById(_calendarObjectId).value = _calendarObject.getFormatedDate("%Y-%m-%d", date);
			_calendarObject.hide();
		}
		_datecount = _datecount + 1;
	}else if(_dateType == "Today"){
		var tommarow = calcDate("d", _sToday, "1", "+", "-");
		if(_calendarObject.getFormatedDate("%Y%m%d", date) <= _sToday && _datecount == 0){
			alert(_dateItemName+"은(는) 금일 이후일자만 등록가능합니다.");
			document.getElementById(_calendarObjectId).value = makeDateType(tommarow);
			_calendarObject.hide();
		}else if(_calendarObject.getFormatedDate("%Y%m%d", date) > _sToday){
			document.getElementById(_calendarObjectId).value = _calendarObject.getFormatedDate("%Y-%m-%d", date);
			_calendarObject.hide();
		}
		_datecount = _datecount + 1;
	}else if(_dateType == "funCall"){
		document.getElementById(_calendarObjectId).value = _calendarObject.getFormatedDate("%Y-%m-%d", date);
		_calendarObject.hide();
		doFunCallCalendar();
	}else{
		if( date == undefined ) {
			document.getElementById(_calendarObjectId).value = '';
		} else {
			document.getElementById(_calendarObjectId).value = _calendarObject.getFormatedDate("%Y-%m-%d", date);
		}
		_calendarObject.hide();
	}
	
	/*시작일자 종료일자 크기 비교 추가*/
	var startDate = $('#' + _calendarObjectId).attr('startDate');
	var endDate = $('#' + _calendarObjectId).attr('endDate');
	var objValue = document.getElementById(_calendarObjectId).value;
	if(startDate) {
		var d = $('#' + startDate).val();
		if(d && d != '') {
			if(fnDateCompare(d, objValue) < 0 ) {
				alert('시작일 보다 작을 수 없습니다.');
				$('#' + _calendarObjectId).val('');
				return false;
			}
		}
	} else if(endDate)  {
		var d = $('#' + endDate).val();
		if(d && d != '') {
			if(fnDateCompare(objValue, d) < 0 ) {
				alert('종료일보다 클 수 없습니다.');
				$('#' + _calendarObjectId).val('');
				return false;
			}
		}
	}
	$('#' + _calendarObjectId).trigger('change');
	/*시작일자 종료일자 크기 비교 추가 end*/ 
	return true;
}