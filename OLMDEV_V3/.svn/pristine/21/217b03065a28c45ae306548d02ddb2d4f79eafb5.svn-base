<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="root"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SK Hynix MPM</title>

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<link rel="stylesheet" type="text/css" href="${root}${HTML_CSS_DIR}/style2.css"/>
<link rel="stylesheet" type="text/css" href="${root}${HTML_CSS_DIR}/tabs/jquery-ui.css"/>

<script src="${root}cmm/sk/js/jquery/jquery.js"          type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/jquery.cookie.js"   type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/jquery.treeview.js" type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/tabs/jquery-ui.js"  type="text/javascript"></script> <!-- tab --> 

<script>
$(document).ready(function(e){
			
	$('#main_wrap').css('width',$(window).width()-60-1+17);
	
	$('#main_wrap').css('height',$(window).height()-50-1+17);	
	$('#left').css('height',$(window).height()-50-1+17);
	$('#lnb_wrap').css('height',$(window).height()-50-1+17);
	
    $(".ui-tabs-panel").css('height', $(window).height()-50-1-23+17);
	 	
		
	$(window).resize(function(){
				
		$('#main_wrap').css('width',$(window).width()-60-1);
		
		$('#main_wrap').css('height',$(window).height()-50-1);	
		$('#left').css('height',$(window).height()-50-1);
		$('#lnb_wrap').css('height',$(window).height()-50-1);	
		
		$("#tabs").css('width', $(window).width()-60-1);
		
	    $(".ui-tabs-panel").css('height', $(window).height()-50-1-23);
		$(".ui-tabs-panel").css('width', $(window).width()-60-1);
		
		$('#tabs > div > iframe').css('min-width',1202);
		$('#tabs > div > iframe').css('width', $(window).width()-60-1);
		$('#tabs > div > iframe').css('min-height',726);
		$('#tabs > div > iframe').css('height', $(window).height()-50-1-23);
		
		
	});		
	initMenuTabs();
	initLanguageType();
	
});

function initLanguageType(){
	var langType = "${sessionScope.loginInfo.sessionCurrLangCode}";
	
	if(langType == "KO"){
		$('#LangKO').css('color','red');	
		$('#LangEN').css('color','black');	
		$('#LangZH').css('color','black');	
	} else if(langType == "EN"){
		$('#LangKO').css('color','black');	
		$('#LangEN').css('color','red');	
		$('#LangZH').css('color','black');	
	} else if(langType == "ZH"){
		$('#LangKO').css('color','black');	
		$('#LangEN').css('color','black');	
		$('#LangZH').css('color','red');	
	}
}

function initMenuTabs(){
	var tabs = $( "#tabs" ).tabs();
	// close icon: removing the tab on click
    tabs.delegate( "span.ui-icon-close", "click", function() {
      var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
      $( "#" + panelId ).remove();
      tabs.tabs( "refresh" );
    });
}


function sub_menu_click(){
	$('#lnb_wrap').css('display','');

}
//서브 메뉴 닫기
function sub_menu_close(){
	old_sub_id = null; // 서브메뉴 선택 초기화
	$('#lnb_wrap').css('display','none');	
	
	leftMenuOff();
}

// actual addTab function: adds new tab using the input from the form above
var maxTabCnt = 5;
function addTab(id, label, url) {
	$('#footwrap').css('display','none');
	var index = null;
	var tabs = $("#tabs").tabs();
	var tabCounter = $('#tabs > div').length; // 현재 탭의 갯수
	var addFlag = true;

	$('#tabs > div').each(function(index, item){
        if(id == $(item).attr("id")){
        	//alert("1. ID : "+id);
        	//alert("2. ID : "+$(item).attr("id"));
        	index = $('#tabs a[href=\"#'+id+'\"]').parent().index(); // 추가된 탭 인덱스		
    		tabs.tabs("option", "active", index); // 해당 인덱스 활성화
    		
    		addFlag = false; // 추가 탭 생성 안함
    		$('#lnb_wrap').css('display','none');
        	return;	
        }	
    });
	if(!addFlag){
		return;
	}
	if(tabCounter > maxTabCnt){
		//alert("탭의 갯수는 최대 "+maxTabCnt+"개 까지 입니다.");
		alert("Max Tab Count : 5");
		return;
	}
	
	var tabtemp01 = "<li><a href='";
	var tabtemp02 = "'>";
	var tabtemp03 = "</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>";
	//alert("${pageContext.request.contextPath}");
	if(addFlag){
		sub_menu_close();    
		var h = $('#main_wrap').height()-23;
		var tabTempTotal = tabtemp01+"#"+id+tabtemp02+label+tabtemp03;
		var li = $( tabTempTotal ),
//	        tabContentHtml = "<div id='sub_container' style='min-height:100%;'><div>"; 
		<!-- url = "http://10.144.71.194:8080/report/hr/test"  // 임시 레포트 주소-->
			//url = "./MPM_Sample_detail.html"  // 임시 레포트 주소
			//url = "${pageContext.request.contextPath}"+"/mainHomSingle2.do";  
			url = "${pageContext.request.contextPath}"+url;  
        tabContentHtml = "<iframe name='tab_ifr_content'   src='"+url+"' width='100%' height='100%' min-width='1202px' min-height='726px' rameborder='0' marginwidth='0' marginheight='0' scrolling='auto'></iframe>"; 
        
		tabs.find( ".ui-tabs-nav" ).append( li );
		tabs.append( "<div id='"+id+"' style='height:"+h+"px' style='background:#000;' >"+tabContentHtml+"</div>" );
		tabs.tabs("refresh");
		
		index = $('#tabs a[href=\"#'+id+'\"]').parent().index(); // 추가된 탭 인덱스		
		tabs.tabs("option", "active", index); // 해당 인덱스 활성화
		
		var param = "tabID="    + id
		          + "&tabLabel="   + label
		          + "&tabUrl="     + url
	      ; 
		
	    $.ajax({
	        type: "POST",
	        url: "<c:url value='/insertVisitLog.do'/>",
	        data: param,
	        async: false,
	        success: function(data) {
	        	
	        	
	        },
	        error : function() {
	        }
	    });
	}
}	


var isChange = true;
function click_Change_Management(){
    if(isChange){
        $('#divChaMag').css('display','');
        isChange = false;
    } else {
        $('#divChaMag').css('display','none');
        isChange = true;
    }
}

var isDocument = true;
function click_Documents(){
    if(isDocument){
        $('#divDocuments').css('display','');
        isDocument = false;
    } else {
        $('#divDocuments').css('display','none');
        isDocument = true;
    }
}

var selGubun = "";
function click_LeftMenu(gubun){
	
	if(selGubun != gubun){
		selGubun = gubun;
		$('#gubun').val(gubun);
		document.mainHomFrm.target = "subFrame";            
		document.mainHomFrm.action = "mainHomSingleInner.do";            
		document.mainHomFrm.submit();
	}
	
	$('#lnb_wrap').css('display','block');
}

function leftMenuOff(){
	var leftMenuList = ['MM','SD','PM','FI','HR'];
	// Off
	for(var idx=0; idx< leftMenuList.length; idx++){
		$("#leftMenu"+leftMenuList[idx]).removeClass("over");
	}
}

function leftMenuOn(gubun){
	// On
	var idxID = "leftMenu";
	idxID = idxID + gubun;
	$("#"+idxID).addClass("over");
}

function leftMenuOnOff(gubun){
	leftMenuOff();
	leftMenuOn(gubun);
}

function click_TopIcon(){
	document.mainHomFrm.action = "mainHomSingle.do";
	document.mainHomFrm.target = "";  
	document.mainHomFrm.submit();
}

function click_logout(){
	//document.mainHomFrm.action = "index.do";
	document.mainHomFrm.action = "mainHomSingleLogout.do";
	//document.mainHomFrm.action = "login/logoutForm.do";
	document.mainHomFrm.target = "";  
	document.mainHomFrm.submit();
}

function onmouseover_UserName(){
	$('#UserNameUL').css('display','');
}
function onmouseout_UserName(){
	$('#UserNameUL').css('display','none');
}

function fnDetail(mgtID, ID){
	var url="boardDetailPop.do";
	var data=data="languageID=${sessionScope.loginInfo.sessionCurrLangType}&BoardID="+ID+"&BoardMgtID="+mgtID+"&noticType="+$('#noticType').val();
	fnOpenLayerPopup(url,data,"",617,436);/*openPopup(url,400,400,"new");*/
}
</script>
</head>



<body>
<form name="mainHomFrm" id="mainHomFrm" target="subFrame" method="post" >
    <input type="hidden" id="gubun" name="gubun" value=""></input>
</form>
<!--wrap Start (min-width:1280px)-->
<div id="wrap">

<!--Header Start-->
<div ID="header">
    <h1><a href="javascript:click_TopIcon();">
        
    <img src="${root}${HTML_IMG_DIR}/new/logo.png" />    
    <!-- Master Process Management -->
    <!-- 
    <img src="${root}${HTML_IMG_DIR}/new/logo_MPM.png" />
    -->
    </a></h1>
    <div class="hd_menu">
    <ul class="hd_cont">
            <li>
                <!--search_report start-->
                <div class="search_report" style="width:150px;display:block;" >
                    <div class="search_tbl">
                            <table class="tbl_style01">
                                <colgroup>
                                    <col width="" />
                                </colgroup>
                                <tr>
                                    <td class="right">
                                    <a id="LangKO" style="color:red;" href="${pageContext.request.contextPath}/changeLanguage.do?LANGUAGE=1042&NAME=Korean">KOR </a>
                                    <a id="LangEN" href="${pageContext.request.contextPath}/changeLanguage.do?LANGUAGE=1033&NAME=English">ENG </a>
                                    <a id="LangZH" href="${pageContext.request.contextPath}/changeLanguage.do?LANGUAGE=2052&NAME=Chinese">CHN </a>
                                    </td>
                                </tr>
                            </table>
                    </div>           
                </div>
                <!--search_report End-->
            </li>

            <li class="user">
                <a href="#">
                <img src="${root}${HTML_IMG_DIR}/new/img_user.png">${sessionScope.loginInfo.sessionUserNm}</b>
                </a>               
            </li>
            <li class="logout"><a href="javascript:click_logout();"><span>logout</span></a></li>
        </ul>
    </div>
    <div class="clear"></div>
</div> 
<!--Header End-->


<!--Left Menu Start-->
<div ID="left">
    <ul class="left_menu">
        <li id="leftMenuCTS" class="lm"><a href="javascript:addTab('change_tab05','CTS 관리','/ctsList.do')">CTS관리</a></li>
        <li id="leftMenuMM"  class="pr"><a href="javascript:click_LeftMenu('MM')">${menu.LM00019}</a></li>
        <li id="leftMenuSD"  class="sm"><a href="javascript:click_LeftMenu('SD')">${menu.LM00020}</a></li>
        <li id="leftMenuPM"  class="pm"><a href="javascript:click_LeftMenu('PM')">${menu.LM00021}</a></li>
        <li id="leftMenuFI"  class="fi"><a href="javascript:click_LeftMenu('FI')">${menu.LM00022}</a></li>
        <li id="leftMenuHR"  class="hr"><a href="javascript:click_LeftMenu('HR')">${menu.LM00023}</a></li>
        <li id="leftMenuDO"  class="mm"><a href="javascript:click_LeftMenu('DO')">${menu.LN00029}</a></li>
    </ul>
    <div class="clear"></div>
</div>
<!--Left Menu End-->



<div id="main_wrap">
	<!-- tab box Start -->
    <div id="tab_box" >
    	<div id="tabs" style="min-width:1202px;">
		  <ul>
<!-- 		    <li><a href="#tabs-1">예제리포트</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li> -->
		  </ul>
		  <div >
		  </div>
		</div>	
    </div>
<!-- tab box end -->
	<!--main_container Start -->
	<div id="main_container">
		<!--메인상단 Start -->
	    <ul class="top_cont">
<!-- 	    	메인상단 메인이미지 -->
	        <li class="main_image"></li>
	        

	        <li class="bookmark sc_red">
	            <h3>${menu.LN00001}</h3>
	            <ul class="bookmark_list ">
                    <c:forEach var="notice" items="${noticeList}">
                        <li><a href="javascript:fnDetail('${notice.BoardMgtID}','${notice.BoardID}');"><span>${notice.Subject}</span></a></li> 
                    </c:forEach>
	            </ul>
	        </li>
	        
	    </ul>
<!-- 	    메인하단 Start  -->
	    <ul class="bottom_cont">
<!-- 	    	메인하단 메뉴박스 -->
	        <li class="menu_box">
<!-- 	        	메인하단 메뉴박스 상단 -->
	        	<ul class="box_cont">
	                <li class="pr"><a><span>${menu.LM00019}</span><p>Purchasing / Material</p></a></li>
	                <li class="sm"><a><span>${menu.LM00020}</span><p>Sales & Logistics</p></a></li>
	                <li class="pm"><a><span>${menu.LM00021}</span><p>Equiment</p></a></li>
	                <li class="fi"><a><span>${menu.LM00022}</span><p>Finance</p></a></li>
	                <li class="hr"><a><span>${menu.LM00023}</span><p>Human Resource</p></a></li>
	               
	            </ul>
	            <ul class="box_cont" >
	                <li class="lm"><a href="javascript:addTab('change_tab05','CTS 관리','/ctsList.do')"><span>CTS 관리</span></a></li>
	                <li class="blank dis"><a href="#"></a></li>
	                <li class="blank dis"><a href="#"></a></li>
	                <li class="blank dis"><a href="#"></a></li>
	                <li class="blank dis"><a href="#"></a></li>
	            </ul>
	            
	        </li>
	        
	        <!--
	        <li class="recently">
	            <h3>Recently Report</h3>
	            <ul class="recently_list">
	            <li><a href="#"><span>수선비 계획 vs 실적(월/분기별)</span></a>11.13</li>
	            <li><a href="#"><span>자재 유형별 실적</span></a>11.13</li>
	            <li><a href="#"><span>월별출고현황(원자재,부자재,S/P)</span></a>11.13</li>
	            <li><a href="#"><span>장비별TCO분석(장비별),(구매가 대비 수선비 비율 포함)</span></a>11.12</li>
	            <li><a href="#"><span>장비별 수선비 vs 생산효율 비교</span></a>11.12</li>
	            <li><a href="#"><span>자재 Life Cycle주 기(Maker/Model/장비별)현황</span></a>11.12</li>
	            <li><a href="#"><span>PM계획 대비 준수율(일/주/월별)</span></a>11.12</li>
	            <li><a href="#"><span>Vendor별 출고현황</span></a>11.12</li>
	            <li><a href="#"><span>일별 창고재고 현황(Memory)</span></a>11.12</li>
	            <li><a href="#"><span>공정/Maker/Mode/장비별 동일 Part 고장건수</span></a>11.12</li>
	            <li><a href="#"><span>장비별 수선비 vs 생산효율 비교</span></a>11.12</li>
	            <li><a href="#"><span>자재 Life Cycle주 기(Maker/Model/장비별)현황</span></a>11.12</li>
	            <li><a href="#"><span>PM계획 대비 준수율(일/주/월별)</span></a>11.12</li>
	            <li><a href="#"><span>Vendor별 출고현황</span></a>11.12</li>
	            <li><a href="#"><span>일별 창고재고 현황(Memory)</span></a>11.12</li>
	            <li><a href="#"><span>공정/Maker/Mode/장비별 동일 Part 고장건수</span></a>11.12</li>
	            </ul>
	        </li>
	        -->
	    </ul>		
    </div>
    <!-- main_container end-->
</div>
<!--메인화면 End-->

<!--Lnb 화면 start-->
<div id="lnb_wrap" style="display:none">
<iframe id='subFrame' name='subFrame'  src='${pageContext.request.contextPath}/mainHomSingleInner.do' width="350px" height="100%"></iframe>
</div>
<!--Lnb 화면 end -->


<div ID="footwrap" style="display:none;">
<img src="${root}${HTML_IMG_DIR}/new/footer_logo.png"/>
</div>

</div>
<!--wrap End-->


</body>
</html>
