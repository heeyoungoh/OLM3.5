<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="${root}${HTML_CSS_DIR}/style2.css"/>
<link rel="stylesheet" type="text/css" href="${root}${HTML_CSS_DIR}/tabs/jquery-ui.css"/>

<script src="${root}cmm/sk/js/jquery/jquery.js"          type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/jquery.cookie.js"   type="text/javascript"></script> 
<script src="${root}cmm/sk/js/jquery/jquery.treeview.js" type="text/javascript"></script> 

<script src="${root}cmm/sk/js/jquery/tabs/jquery-ui.js"  type="text/javascript"></script> <!-- tab --> 


<script type="text/javascript">
$(document).ready(function(e){
		
		var gubun = "${gubun}";
		leftMenuText(gubun);
		
		$("#tree").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});
		
		$("#tree02").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});
		
	})		
</script>

<script>

//서브 메뉴 닫기
function sub_menu_close(){
	parent.sub_menu_close();
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




function leftMenuText(gubun){
	if(gubun == "MM"){
		$("#selModuleKR").html("MM " + "${menu.LM00019}");	
		$("#selModuleEN").html("Material Management");	
	} else if(gubun == "SD"){
		$("#selModuleKR").html("SD "+ "${menu.LM00020}");	
		$("#selModuleEN").html("Sales & Distribution");		
	} else if(gubun == "PM"){
		$("#selModuleKR").html("PM "+ "${menu.LM00021}");	
		$("#selModuleEN").html("Plant Management");		
	} else if(gubun == "FI"){
		$("#selModuleKR").html("FI "+ "${menu.LM00022}");	
		$("#selModuleEN").html("Finance");		
	} else if(gubun == "HR"){
		$("#selModuleKR").html("HR "+ "${menu.LM00023}");	
		$("#selModuleEN").html("Human Resource");		
	} else {
		
	}
}

</script>
</head>
<body>
    <div id="lnb">
    	<ul class="lnb_menu">
        <li class="lnb_submenu over"><a href="#"><div id="selModuleKR"> </div><span id="selModuleEN"> </span></a>
        <div class="lnb_tree_frame">
           <!--tree menu start-->
          	<div class="lnb_tree sc" >
                <div id="sidetree" >
                    <ul id="tree" class="filetree treeview" >
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>Process
                            <ul style="display: block;">
                                <c:forEach var="procInfo" items="${processLvl1List}">
                                <li class="expandable"><div class="hitarea collapsable-hitarea"></div><span><a href="javascript:parent.addTab('process_tab_${procInfo.TREE_ID}','Process','/setTabMenu.do?s_itemID=${procInfo.TREE_ID}', this);">${procInfo.TREE_NM}</a></span>
                                    <ul>
                                        <c:forEach var="Lvl2Proc" items="${procInfo.Lvl2ProcList}">
                                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div><span><a href="javascript:parent.addTab('process_tab_${Lvl2Proc.TREE_ID}','Process','/setTabMenu.do?s_itemID=${Lvl2Proc.TREE_ID}', this);">${Lvl2Proc.TREE_NM}</a></span>
                                            <ul >
                                                <c:forEach var="Lvl3Proc" items="${Lvl2Proc.Lvl3ProcList}">
                                                <li class="expandable"><div class="hitarea collapsable-hitarea"></div><span><a href="javascript:parent.addTab('process_tab_${Lvl3Proc.TREE_ID}','Process','/setTabMenu.do?s_itemID=${Lvl3Proc.TREE_ID}', this);">${Lvl3Proc.TREE_NM}</a></span>
                                                    <ul >
                                                        <c:forEach var="Lvl4Proc" items="${Lvl3Proc.Lvl4ProcList}">
                                                            <li onclick="parent.addTab('process_tab_${Lvl4Proc.TREE_ID}','Process','/setTabMenu.do?s_itemID=${Lvl4Proc.TREE_ID}', this);"><a href="#">${Lvl4Proc.TREE_NM}</a></li>
                                                        </c:forEach>                                                        
                                                    </ul>
                                                </li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>Configuration
                            <ul id="confInfo" style="display: block;">
							  <c:forEach var="confInfo" items="${confInfoList}">
								  <li onclick="parent.addTab('configuration_tab_${confInfo.TREE_ID}','Configuration','/setTabMenu.do?s_itemID=${confInfo.TREE_ID}', this);"><a href="#">${confInfo.TREE_NM}</a></li>
							  </c:forEach>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>CBO Master
                            <ul style="display: block;">
							    <c:forEach var="subSystem" items="${subSystemList}">
	                                <li><div class="hitarea collapsable-hitarea"></div><a href="javascript:parent.addTab('cboMaster_tab_${subSystem.TREE_ID}','CBO Master','/setTabMenu.do?s_itemID=${subSystem.TREE_ID}', this);">${subSystem.TREE_NM}</a> 
	                                    <ul id="folder21">
	                                    <c:forEach var="subCBO" items="${subSystem.subCBOList}">
	                                      <li onclick="parent.addTab('cboMaster_tab_${subCBO.TREE_ID}','CBO Master','/setTabMenu.do?s_itemID=${subCBO.TREE_ID}', this);"><a href="#">${subCBO.TREE_NM}</a></li>
	                                    </c:forEach>
	                                    </ul>
	                                </li>
							    </c:forEach>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>IF Master
                            <ul id="ifInfo" style="display: block;">
							  <c:forEach var="subSystem" items="${subSystemList}">
	                                <li><div class="hitarea collapsable-hitarea"></div><a href="javascript:parent.addTab('ifMaster_tab_${subSystem.TREE_ID}','IF Master','/setTabMenu.do?s_itemID=${subSystem.TREE_ID}', this);">${subSystem.TREE_NM}</a> 
	                                    <ul id="folder21">
	                                    <c:forEach var="subIF" items="${subSystem.subIFList}">
	                                      <li onclick="parent.addTab('ifMaster_tab_${subIF.TREE_ID}','IF Master','/setTabMenu.do?s_itemID=${subIF.TREE_ID}', this);"><a href="#">${subIF.TREE_NM}</a></li>
	                                    </c:forEach>
	                                    </ul>
	                                </li>
							  </c:forEach>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div> 
            <!--tree menu end --> 
            </div>
        </li>
        
        <!--변경관리-->
        <li class="lnb_submenu" onmouseover="this.style.background='#b42626'" onmouseout="this.style.background='#FFF'"><a href="javascript:click_Change_Management();">${menu.LN00165}<span>Change Management</span></a>
        <div id="divChaMag" style="display:none;" class="lnb_report_frame" >
           <!--tree menu start-->
          	<div class="lnb_tree sc" >
                <div id="sidetree" >
                    <ul id="tree02" class="filetree treeview" >
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>${menu.LN00092}
                            <ul style="display: block;">
                                <li id='change_tab01' onclick="parent.addTab('change_tab01','${menu.LN00127}','/changeReqList.do?viewCheck=view', this);"><a href="#">${menu.LN00127}</a></li>
                                <li id='change_tab02' class="last" onclick="parent.addTab('change_tab02','${menu.LN00128}','/changeReqList.do?viewCheck=edit', this);"><a href="#">${menu.LN00128}</a></li>
                            </ul>
                        </li>
                        <li id='change_tab03' onclick="parent.addTab('change_tab03','${menu.LN00082}','/changeInfoList.do?screenMode=view&isMine=Y', this);"><a href="#">${menu.LN00082}</a></li>
                        <li id='change_tab04' onclick="parent.addTab('change_tab04','${menu.LN00137}','/changeSetAgrAprList.do', this);"><a href="#">${menu.LN00137}</a></li>
                        <li id='change_tab05' class="last" onclick="parent.addTab('change_tab05','${menu.LM00024}','/ctsList.do', this);"><a href="#">${menu.LM00024}</a></li>

                    </ul>
                </div>
            </div> 
            <!--tree menu end --> 
        </div>    
        </li>

    </ul>    
     <!--Lnb 화면 닫기버튼 --> 
	<a href="#" class="bClose" onclick='sub_menu_close();'><img src="${root}${HTML_IMG_DIR}/new/btn_close.png" alt="닫기" name="lnb_close"></img></a>
	</div> 
</body>
</html>