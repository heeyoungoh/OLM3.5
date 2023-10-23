<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 1. Include JSP -->
<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>
<%@ page import="xbolt.cmm.framework.val.GlobalVal"%>

<script type="text/javascript" src="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.js?v=7.1.8"></script>
<link rel="stylesheet" href="${root}cmm/js/dhtmlx/dhtmlxV7/codebase/suite.css?v=7.1.8">

<link rel="stylesheet" type="text/css" href="${root}cmm/common/css/mainHome.css" />
<script type="text/javascript">
var curWFMode = "CurAprv";
var mlvl = "${sessionScope.loginInfo.sessionMlvl}";
var data = [
            { 
                 id: "toggle",
 				css: "toggle-button",
 				html: "<div><img src='${root}cmm/common/images/menu/icon_fold.png'/><span class='title'>My Page</span></div>",
             },            
             { 
                 id: "user",
 				html: "<div>" 
 				+ "<li style='text-align:center;'><img src='<%=GlobalVal.EMP_PHOTO_URL%>${memPhoto}' style='width:50px;height:50px;'/></li>" 
 				+ "<li style='text-align:center;'>${sessionScope.loginInfo.sessionUserNm}</li>"
 				+ "<li style='text-align:center;'>${sessionScope.loginInfo.sessionTeamName}</li>" 
 				+ "</div>"	
             },
             {
            	 id:     "sepId1",        
           	    type:   "separator"
        	 },
             { 
                 id: "sr", 
                 value: "문의/서비스",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_sr.png'/><span class='mgL15'>${menu.LN00333}</span></div>",
 	           
         		items: [
                     {
                         id: "srNew",
                         value: "New", 
         				count: "${srNewCnt}",
         	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_new.png'/><span class='mgL10'>${menu.LN00339}</span></div>"
                     },
                     {
                         id: "srIng",
                         value: "Processing", 
         				count: "${srIngCnt}",
       	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_ing.png'/><span class='mgL10'>${menu.LN00121}</span></div>"
                     },
                     {
                         id: "srAll",
                         value: "ALL",
         	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_all.png'/><span class='mgL10'>ALL</span></div>"
                     },
                     {
                         id: "srReq",
                         value: "Request",
     	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_plus.png'/><span class='mgL10'>${menu.LN00286}</span></div>"
     	                     
                     },
                     {
                    	 id: "srDesk",
         	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_sd.png'/><span class='mgL10'>Service Desk</span></div>"
                     }
                 ]
             },
             {
                 id: "projects",
                 value: "Project",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_project.png'/><span class='mgL15'>${menu.LN00131}</span></div>",
         		items: [
                     {
                         id: "ownerPjt",
                         value: "Manager", 
                         count: "${ownerPjtCnt}",
     	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_man.png'/><span class='mgL10'>${menu.LN00340}</span></div>"

                     },
                     {
                         id: "workPjt",
                         value: "Worker", 
         				count: "${workerPjtCnt}",
     	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_join.png'/><span class='mgL10'>${menu.LN00341}</span></div>"
                     }
                 ]
             },
             {
                 id: "csr",
                 value: "Task",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_csr.png'/><span class='mgL15'>${menu.LN00191}</span></div>",
         		items: [
                     {
                         id: "ownerCsr",
                         value: "Manager", 
                         count: "${ownerCsrCnt}",
     	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_man.png'/><span class='mgL10'>${menu.LN00342}</span></div>"

                     },
                     {
                         id: "workCsr",
                         value: "Member", 
         				count: "${workerCsrCnt}",
     	                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_scr_ing.png'/><span class='mgL10'>${menu.LN00343}</span></div>"
                     }
                 ]
             },
             {
                 id: "item",
                 value: "Contents",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_contents.png'/><span class='mgL15'>${menu.LN00145}</span></div>",
                       		items: [
                      {
                         id: "itemNew",
                         value: "New", 
         				count: "${itemNewCnt}",
                        html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_cs_new.png'/><span class='mgL10'>${menu.LN00339}</span></div>"
                            
                     },{
                         id: "itemMod",
                         value: "수정 중", 
         				count: "${itemModCnt}",
                            html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_edit.png'/><span class='mgL10'>${menu.LN00344}</span></div>"
                     },{
                         id: "itemAprv",
                         value: "승인 중", 
         				count: "${itemAprvCnt}",
                        html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_check.png'/><span class='mgL10'>${menu.LN00345}</span></div>"
                            
                     },{
                         id: "itemAll",
                         value: "All", 
         				count: "${itemTreeCnt}",
                        html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_cs_all.png'/><span class='mgL10'>${menu.LN00346}</span></div>"                	

                     },
                     {
                         id: "itemSub",
                         value: "Subscribe", 
         				count: "${itemSubCnt}",
                        html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_sub.png'/><span class='mgL10'>${menu.LN00347}</span></div>"
                            
                     }
                 ]
             },
             {
            	 id:     "sepId2",        
           	    type:   "separator"
        	 },
             {
                 id: "schedule",
                 value: "일정",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_sch.png'/><span class='mgL15'>${menu.LN00110}</span></div>",
         		items: [
                     {
                         id: "schdlToday",
                         value: "Today",
                         html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_today.png'/><span class='mgL10'>Today</span></div>"
                     },
                     {
                         id: "schdlWeek",
                         value: "Week",
                         html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_week.png'/><span class='mgL10'>Week</span></div>"
                     }
                 ]
             },
             {
                 id: "workflow",
                 value: "결재함",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_wf.png'/><span class='mgL15'>${menu.LN00242}</span></div>",
         		items: [
                           {
                               id: "curAprv",
                               value: "결재할문서",
                               html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_pay.png'/><span class='mgL10'>${menu.LN00243}</span></div>"
                           },
                           {
                               id: "areq",
                               value: "결재 상신",
                               html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_pay2.png'/><span class='mgL10'>${menu.LN00211}</span></div>"
                           },
                           {
                               id: "todoAprv",
                               value: "결재예정문서",
                               html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_pay3.png'/><span class='mgL10'>${menu.LN00244}</span></div>"
                           },
                           {
                               id: "refMgt",
                               value: "참조",
                               html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_pay4.png'/><span class='mgL10'>${menu.LN00245}</span></div>"
                           },
                           {
                        	   id: "cls",
                               value: "완료",
                               html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_pay5.png'/><span class='mgL10'>${menu.LN00118}</span></div>"
                           }
                        ]
                     
             },          
             {
                 id: "setting",
                 value: "Infomation",
                 html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_setting.png'/><span class='mgL15'>Infomation</span></div>",
                 items: [
                         {
                             id: "info",
                             value: "Information",
                             html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_info.png'/><span class='mgL10'>Personal Data</span></div>"
                         },
                         {
                             id: "office",
                             value: "Out of office-b",
                             html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_of.png'/><span class='mgL10'>Out of office</span></div>"
                         },
                         {
                             id: "role",
                             value: "Role",
                             html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_role.png'/><span class='mgL10'>Role</span></div>"
                                 
                         },
                         {
                             id: "dimension",
                             value: "Dimension",
                             html: "<div><img src='cmm/js/dhtmlx/dhtmlxSidebar/codebase/images/icon_sidebar_di.png'/><span class='mgL10'>Dimension</span></div>"
                                 
                         }
                     ]
             }
     ];
	var sidebar = new dhx.Sidebar("sidebar", {
		css: "dhx_widget--border_right"
	});
	
	
	sidebar.data.parse(data);		
	

	var events = [
					"click",
					"openMenu",
					"beforeHide",
					"afterHide",
					"beforeCollapse",
					"afterCollapse",
					"beforeExpand",
					"afterExpand"
				];

	var eventsContainer = document.querySelector(".dhx_sample-container__sidebar")
	var counter = 1;
		
	sidebar.events.on("click", function(id){
		setPage(id);
	});
	
	function setPage(id,varFilter){
		var data = "";
		var url = "";
		var target = "mainLayer";
		
		if(id === "toggle"){
			var toggleItem = sidebar.data.getItem("toggle");
			var userInfo = document.querySelector(".user-info_item");

			sidebar.toggle();

			if(sidebar.config.collapsed){
				sidebar.data.update("toggle", {html: "<div><img src='${root}cmm/common/images/menu/icon_unfold.png'/></div>"});
				sidebar.data.update("user", {html: "<div><img style='width:96%;height:100%' src='${root}cmm/common/images/menu/sidebar_photo.png'/></div>"});
				$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -44);
				$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -44);
			}
			else {
				sidebar.data.update("toggle", {html: "<div><img src='${root}cmm/common/images/menu/icon_fold.png'/><span class='title'>My Page</span></div>"});
				sidebar.data.update("user", {html: "<div>" 
													+ "<li style='margin:0 auto;text-align:center;'><img src='<%=GlobalVal.EMP_PHOTO_URL%>${memPhoto}' style='width:50px;height:50px;'/></li>" 
													+ "<li style='margin:0 auto;text-align:center;'>${sessionScope.loginInfo.sessionUserNm}</li>"
													+ "<li style='margin:0 auto;text-align:center;'>${sessionScope.loginInfo.sessionTeamName}</li>" 
													+ "</div>"	 });
				$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -200);
				$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -200);
			}
			return;
		}else if(id == "user") {
       		parent.goMenu('mySpaceV34.do?srType=${srType}&noticeType=4', '', true, layout_2E);
			return;
		}else if(id == "srNew"){
			url = "esrListMgt.do";
			data =  "srMode=mySR&searchStatus=NEW&srStatus=ING&pageNum=1&srType=${srType}&scrnType=srRcv&mainType=mySRDtl&multiComp=${multiComp}";
		}else if(id == "srIng"){
			url =  "esrListMgt.do";
			data = "srMode=mySR&srStatus=ING&pageNum=1&srType=${srType}&scrnType=srRcv&mainType=mySRDtl&multiComp=${multiComp}";
		}else if(id == "srAll"){
			url =  "esrListMgt.do";
			data =  "srMode=mySR&pageNum=1&srType=${srType}&scrnType=srRcv&mainType=mySRDtl&srStatus=ALL&multiComp=${multiComp}";
		}else if(id == "srReq") {
			url =  "esrListMgt.do";
			data =  "srMode=mySR&pageNum=1&srType=${srType}&scrnType=srRqst&mainType=mySRDtl&srStatus=ALL";			
		}
		else if(id == "srDesk"){		
			url = "srAreaInfo.do";
			data = "srType=${srType}";
		}else if(id == "ownerPjt"){		
			url = "myProjectList.do";
			data = "isNew=N&pjtMode=R&mainVersion=mainV5&authorID=${sessionScope.loginInfo.sessionUserId}&status=OPN&screenType=MYSPACE&mbrType=manager";
		}else if(id == "workPjt"){
			url = "myProjectList.do";
			data = "isNew=N&pjtMode=R&mainVersion=mainV5&status=OPN&screenType=MYSPACE&mbrType=worker";
		}else if(id == "ownerCsrAll"){
			url = "csrList.do";
			data = "&mainMenu=1&memberID=${sessionScope.loginInfo.sessionUserId}&screenType=MYSPACE&mbrType=all"; 
		}else if(id == "ownerCsr"){
			url = "csrList.do";
			data = "&mainMenu=1&memberID=${sessionScope.loginInfo.sessionUserId}&screenType=MYSPACE&mbrType=manager"; 
		}else if(id == "workCsr"){
			url = "csrList.do";
			data = "&mainMenu=1&memberID=${sessionScope.loginInfo.sessionUserId}&screenType=MYSPACE&mbrType=worker"; 
		}else if(id == "itemMod"){
			url = "ownerItemList.do";
			data = "ownerType=author&status=MOD1&option=MYSPACE";
		}else if(id == "itemModAll"){
			url = "ownerItemList.do";
			data = "ownerType=author&status=ING&option=MYSPACE&authorID=${sessionScope.loginInfo.sessionUserId}";
		}else if(id == "itemAll"){
			url = "ownerItemList.do";
			data = "ownerType=manager&option=MYSPACE";
		}else if(id == "itemNew"){
			url = "ownerItemList.do";
			data = "ownerType=author&status=NEW1&option=MYSPACE";
		}else if(id == "itemAprv"){
			url = "ownerItemList.do";
			data = "ownerType=author&statusList='NEW2','MOD2'&option=MYSPACE&authorID=${sessionScope.loginInfo.sessionUserId}";
		}else if(id == "itemSub"){
			url = "myItemList.do";
			data = "assignmentType=SUBSCR";
		}else if(id == "schdlToday"){
			url = "goSchdlList.do"; 
			data =  data + "&schdlType=today&screenType=MyPg&wfStepID=AREQ";
		}else if(id == "schdlWeek"){
			url = "goSchdlList.do"; 
			data =  data + "&schdlType=week&screenType=MyPg&wfStepID=AREQ";
		}else if(id == "curAprv"){
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=CurAprv&screenType=MyPg";
		}else if(id == "areq"){
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=AREQ&screenType=MyPg&wfStepID=AREQ";
		}else if(id == "todoAprv"){
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=ToDoAprv&screenType=MyPg";
		}else if(id == "refMgt"){
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=RefMgt&screenType=MyPg";
		}else if(id == "cls"){
			url = "wfInstanceList.do"; 
			data =  data + "&wfMode=Cls&screenType=MyPg";
		}else if(id == "info") { // Info
			url = "viewMyInfo.do"; 
			data =  data + "";
		}else if(id == "role"){
			url = "myRoleList.do";
			data = "s_itemID=${sessionScope.loginInfo.sessionUserId}&scrnType=mySpace";
		}else if(id == "dimension"){
			url = "userDimList.do";
			data = "ownerType=user&uesrType=1&languageID=${sessionScope.loginInfo.sessionCurrLangType}&s_itemID=${sessionScope.loginInfo.sessionUserId}&scrnType=mySpace";
		}else if(id == "office") { // Member Agent
			url = "mbrAgentCfg.do"; 
			data =  data + "";
		}else {
			return false;
		}

		if(id != "toggle"){
			$("#mainLayer").css("background","#fff");
		}

		if(varFilter != null && varFilter != '') {
			data += varFilter;
		}
		
		if(id == "srDesk"){		
			$("#mainFrame").attr("style", "display:block;");
			$("#mainLayer").attr("style", "display:none;");
			
			$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -200);
			$("#mainFrame").innerHeight($( window ).height());
			
			$('#mainFrame').attr('src',url+"?"+data);
		}else{
			
			$("#mainFrame").attr("style", "display:none;");
			$("#mainLayer").attr("style", "display:block;");
			$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -200);
			$("#mainLayer").css("background","#fff");
			
			ajaxPage(url, data, target);
		}
	}
	
	$(document).ready(function(){
		fnSidebarInit();	
		
		$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -200);
		$("#mainLayer").innerHeight($( window ).height());
		
		$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -200);
		$("#mainFrame").innerHeight($( window ).height());
		
		setDivSize();
		
		$( window ).resize( function() {
				if($(".dhx_sidebar ").width() == 43){	// minimize
			    		$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -44);
			    		$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -44);
				} else {
			    		$("#mainLayer").innerWidth(document.body.getBoundingClientRect().width -200);
			    		$("#mainFrame").innerWidth(document.body.getBoundingClientRect().width -200);
				}
			$("#mainLayer").innerHeight($( window ).height());
			 setDivSize();
        });
        
		var scrnType = "${scrnType}";
		
		if(scrnType != "") {
			if("${srID}" != '' && "${srID}" != null){setPage(scrnType,"&srID=${srID}");}
			else {setPage(scrnType);}
		}
		
		if("${mainType}" == "mySRDtl"){
			setPage('srAll','&srID=${srID}');
		}else if("${mainType}" == "myWFDtl"){
			setPage('curAprv', '&wfInstanceID=${wfInstanceID}');
		}else{		
			setSchdlFrame();
			fnClickedTab(1);
	        setESRFrame();
	        setModItemList();
	        setPjtList();
		}
        
	});
	
	 function setDivSize(){
        $("#boardDiv > div:nth-child(2)").innerHeight($("#boardDiv").height()-$("#boardDiv > div:nth-child(1)").height());
        $("#csListDiv > div:nth-child(2)").innerHeight($("#csListDiv").height()-$("#csListDiv > div:nth-child(1)").height());
        $("#schdlDiv > div:nth-child(2)").innerHeight($("#schdlDiv").height()-$("#schdlDiv > div:nth-child(1)").height());
        $("#esrListDiv > div:nth-child(2)").innerHeight($("#esrListDiv").height()-$("#esrListDiv > div:nth-child(1)").height());
        $("#cntDiv > div:nth-child(2)").innerHeight($("#cntDiv").height()-$("#cntDiv > div:nth-child(1)").height());
	 }
	 
	function fnSidebarInit(){
		
	}
		
	function setModItemList(){ 
        var url = "olmMySpaceItemList.do";
        var target = "itemFrame";
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
        ajaxPage(url, data, target);
    }
	function setPjtList(){ 
        var url = "olmMySpaceCSRList.do";
        var target = "pjtFrame";
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}";
        ajaxPage(url, data, target);
    }
	
    function setSchdlFrame(){
        var url = "olmMainSchdlList.do";   
        var target = "schdlFrame";
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&listSize=10&mySchdl=Y";
        ajaxPage(url, data, target);
    }
    
    function setESRFrame(){ 
        var url = "olmMainEsrList.do";
        var target = "esrFrame";
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&listSize=20&srMode=mySR&scrnType=mySpace&srType=${srType}";
        ajaxPage(url, data, target);
    } 
    	    
    function fnGoSRDetail(SRID,requestUserID,receiptUserID,status,srType){    	
    	var sessionUserID = "${sessionScope.loginInfo.sessionUserId}";
    	var url = ""; 
    	var scrnType = "SRDtl";
    	var screenType = "srRqst";
    	var url = "ispMgt.do";
    	
    	if(srType != "ISP"){
    		setPage("srIng","&srID="+SRID);
    	} else if(srType == "ISP"){
    		setPage("srIng","&srID="+SRID);
    	}
    }
    
    function fnClickedTab(avg) {
        $("#wfType").val(avg);
        var url = "mainWFInstList.do";
        var target = "subWFFrame";
        var wfMode = "";
        
        if(avg == 1){ // 결재 할 문서
        	wfMode="CurAprv";
        } else if(avg == 2){ // 결재 상신
        	wfMode = "AREQ";
        }
        curWFMode = wfMode;
        var data = "languageID=${sessionScope.loginInfo.sessionCurrLangType}&wfMode="+wfMode+"&screenType=MyPg&listSize=5";
        ajaxPage(url, data, target);
        
        var realMenuIndex = "1 2".split(' ');
		for ( var i = 0; i < realMenuIndex.length; i++) {
			if (realMenuIndex[i] == avg) {
				$("#pliugt" + realMenuIndex[i]).addClass("on");
			} else {
				$("#pliugt" + realMenuIndex[i]).removeClass("on");
			}
		}
		
		 $("#wfOnTab").val(wfMode);
    }
    
    function fnGoWFDetail(projectID,wfID,stepInstID,actorID,stepSeq,wfInstanceID,lastSeq,documentID,docCategory){		
		var mainMenu = "${mainMenu}";
		var url = "wfDocMgt.do?";
		var data = "projectID="+projectID+"&pageNum=1&isPop=Y&isMulti=N&actionType=view"
					+"&stepInstID="+stepInstID
					+"&actorID="+actorID
					+"&stepSeq="+stepSeq
					+"&wfInstanceID="+wfInstanceID
					+"&wfID="+wfID
					+"&documentID="+documentID
					+"&srID="+documentID
					+"&lastSeq="+lastSeq
					+"&docCategory="+docCategory
					+"&wfMode="+curWFMode;
				
		var w = 1200;
		var h = 650; 
		itmInfoPopup(url+data,w,h);
	}
    
    function fnWFListReload() {
        var avg = $("#wfType").val();
    	fnClickedTab(avg);
    }
    
    function doSearchList() {
    	fnWFListReload();
	}
    
    function fnGoMore(id, onTab){
    	var focusMenu ="";

       	if(onTab == "SCHDL"){
       		parent.goMenu('goSchdlListMgt.do?refPGID='+id, '', true, layout_2E);
       	} else if(onTab == "ESR"){
       		parent.goMenu('ispMgt.do?srType=ISP&focusMenu=4', '', true, layout_2E);
       	} else if(onTab == "WF"){
       		if($("#wfOnTab").val() == "CurAprv"){
       			setPage('curAprv');
    		}else if($("#wfOnTab").val() == "AREQ"){
    			setPage('areq');
    		}
       	} else if(onTab == "CngSet") {
	    	parent.clickMainMenu('CHANGESET', 'CHANGESET','','','','','.do?', '');
       	} else if(onTab == "csrList") {
			parent.goMenu("csrList.do?mainMenu=1&memberId=${sessionScope.loginInfo.sessionUserId}");
		}
       	
    }
</script>
</head>
<style>
	#mainMenu{
		overflow-x: hidden;
	}
	.dhx_sidebar{
		float:left;
	}
	#mainLayer{
		height:99%;
		float:left;
		overflow-y: auto;
		overflow-x: auto;
		padding:0 20px;
	}
	#mainWrapper{
		width: 94%;
	    height: 96%;
	    top: 2%;
	    min-width: 1250px;
	    display: inline-block;
 	    margin: auto 3%;
	}
	
	#mainFrame{
		height:99%;
		float:left;
		overflow-y: auto;
		overflow-x: auto;
		padding:0 20px;
	}
		
	.dhx_tooltip {
		display:block;
	}
	
	.dhx_widget {
	    font-weight: 400;
	    font-size: 14px;
	    line-height: 20px;
	    color: rgba(0,0,0,.7);
	}

	.dhx_tooltip {
	    pointer-events: none;
	    border-radius: 2px;
	    background-color: #333;
	    box-shadow: 0 2px 5px rgb(0 0 0 / 30%);
	    padding: 6px 12px;
	    transition: opacity .01s ease,transform .01s ease;
	    opacity: 1;
	    z-index: 999;
	    border:none;
	}
	
	.dhx_tooltip--right {
	    margin: 0 0 0 8px;
	}
	
	.dhx_tooltip__text {
	    font-size: 12px;
	}
</style>
<body id="mainMenu">
	<section class="dhx_sample-container">
		<div class="dhx_sample-container__widget" id="sidebar"></div>
	</section>
	<iframe name="mainFrame" id="mainFrame" style="display:none;" width="99%" height="99%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
	<div class="noform" id="mainLayer">
<!-- 		<form name="mainLayerFrm" id="mainLayerFrm" method="post" action="#" onsubmit="return false;"> -->
			<input id="wfType" type="hidden" value=""/>
			<input type="hidden" id="wfOnTab" name="wfOnTab" value=""/>
			<div id="mainWrapper">
				<div id="leftDiv">
					<div id="cntDiv">
						<div class="secTit" style=" height:26%;"><ul><li class="titNM">My Item</li></ul></div>
						<div class="cntFrame">
							<div class="sec" style="background:#4265EE">
								<ul class="tit">
									<li>${menu.LN00145}</li>
									<li class="img"><img src="${root}cmm/common/images/menu/tit_myItem.png"></li>
								</ul>
								<ul class="con">
									<li class="bd" onClick="setPage('itemAll')"><p>${itemCnt}</p>${menu.LN00190}</li>
									<li class="" onClick="setPage('itemModAll')"><p>${itemModeCnt}</p>${menu.LN00350}</li>
								</ul>
							</div>
							<div class="sec" style="background:#4087FB">
								<ul class="tit">
									<li>${menu.LN00333}</li>
									<li class="img"><img src="${root}cmm/common/images/menu/tit_sr.png"></li>
								</ul>
								<ul class="con">
									<li class="bd" onClick="setPage('srNew')"><p>${srNewCnt}</p>${menu.LN00339}</li>
									<li class="" onClick="setPage('srIng')"><p>${srIngCnt}</p>${menu.LN00121}</li>
								</ul>
							</div>
							<div class="sec" style="background:#1BB4F8">
								<ul class="tit">
									<li>${menu.LN00242}</li>
									<li class="img"><img src="${root}cmm/common/images/menu/tit_wf.png"></li>
								</ul>
								<ul class="con">
									<li class="bd" onClick="setPage('areq')"><p>${wfAREQCnt}</p>${menu.LN00211}</li>
									<li class="" onClick="setPage('curAprv')"><p>${wfCurAprvCnt}</p>${menu.LN00195}</li>
								</ul>
							</div>
						</div>
					</div>
					
					<div id="csListDiv" style="height:44%;">
						<div class="secTit">
							<ul>
								<li class="titNM">${menu.LN00333}</li>
							</ul>
							<ul class="morebtn" onclick="setPage('srIng')">
								<li>more</li>
							</ul>
						</div>
						<div id="esrFrame" class="postInfo"></div>	
					</div>
										
					<div id="esrListDiv">
						<div class="secTit">
							<ul>
								<li class="titNM">${menu.LN00349}</li>
							</ul>
							<ul class="morebtn" onClick="setPage('ownerCsrAll')">
								<li>more</li>
							</ul>
						</div>
						<div id="pjtFrame" class="postInfo"></div>	
					</div>
				</div>
				
				<div id="rightDiv">
	
					
					<div id="schdlDiv">
						<div class="secTit">
							<ul>
								<li class="titNM">${menu.LN00110}</li>
							</ul>
							<ul class="morebtn" onClick="setPage('schdlWeek')">
								<li>more</li>
							</ul>
						</div>
						<div id="schdlFrame"></div>	
					</div>
					
					
					<div id="boardDiv" style="height:32%;">
			 			<div class="tabs">
							<ul>
								<li id="pliugt1" class="on titNM" onclick="javascript:fnClickedTab('1');">${menu.LN00243}</li>
								<li id="pliugt2" class="titNM" onclick="javascript:fnClickedTab('2');">${menu.LN00211}</li>
							</ul>
							<ul class="morebtn" onClick="javascript:fnGoMore('', 'WF');">
								<li>more</li>
							</ul>
						</div>
						<div id="subWFFrame" class="tabFrame postInfo"></div>	
					</div>
					
					<div id="esrListDiv">
						<div class="secTit">
							<ul>
								<li class="titNM">${menu.LN00344}</li>
							</ul>
							<ul class="morebtn" onClick="setPage('itemModAll')">
								<li>more</li>
							</ul>
						</div>
						<div id="itemFrame" class="postInfo"></div>	
					</div>
				</div>
		
			</div>
<!-- 		</form> -->
		</div>
</body>
</html>