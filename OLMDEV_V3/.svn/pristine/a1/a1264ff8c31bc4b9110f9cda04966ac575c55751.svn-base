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
		
		$("#tree03").treeview({
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

function leftMenuText(gubun){
    if(gubun == "DO"){
		$("#selModuleKR").html("${menu.LN00029}");	
		$("#selModuleEN").html("Documents");
		
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
                    <ul id="tree03" class="filetree treeview" >
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>${menu.LM00004}
                            <ul style="display: block;">
                                <li id='output_tab06' onclick="parent.addTab('output_tab06','${menu.LM00009}','/outputAttchFileList.do?subCode=6&subMenu=EC01', this);"><a href="#">${menu.LM00009}</a></li>
                                <li id='output_tab07' onclick="parent.addTab('output_tab07','${menu.LM00010}','/outputAttchFileList.do?subCode=6&subMenu=EC02', this);"><a href="#">${menu.LM00010}</a></li>
                                <li id='output_tab08' onclick="parent.addTab('output_tab08','${menu.LM00011}','/outputAttchFileList.do?subCode=6&subMenu=EC03', this);"><a href="#">${menu.LM00011}</a></li>
                                <li id='output_tab09' onclick="parent.addTab('output_tab09','${menu.LM00012}','/outputAttchFileList.do?subCode=6&subMenu=EC04', this);"><a href="#">${menu.LM00012}</a></li>
                                <li class="last" id='output_tab10' onclick="parent.addTab('output_tab10','${menu.LM00013}','/outputAttchFileList.do?subCode=6&subMenu=EC05', this);"><a href="#">${menu.LM00013}</a></li>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>${menu.LM00003}
                            <ul style="display: block;">
                                <li id='output_tab01' onclick="parent.addTab('output_tab01','Process','/outputItemFileList.do?subCode=pro', this);"><a href="#">Process</a></li>
                                <li id='output_tab02' onclick="parent.addTab('output_tab02','Configuration','/outputItemFileList.do?subCode=con', this);"><a href="#">Configuration</a></li>
                                <li id='output_tab03' onclick="parent.addTab('output_tab03','CBO Master','/outputItemFileList.do?subCode=cbo', this);"><a href="#">CBO Master</a></li>
                                <li id='output_tab04' onclick="parent.addTab('output_tab04','Interface Master','/outputItemFileList.do?subCode=int', this);"><a href="#">Interface Master</a></li>
                                <li class="last" id='output_tab05' onclick="parent.addTab('output_tab05','Technical Script','/outputItemFileList.do?subCode=tec', this);"><a href="#">Technical Script</a></li>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>${menu.LM00005}
                            <ul style="display: block;">
                                <li id='output_tab11' onclick="parent.addTab('output_tab11','${menu.LM00017}','/outputAttchFileList.do?subCode=7&subMenu=MNC01', this);"><a href="#">${menu.LM00017}</a></li>
                                <li id='output_tab12' class="last" onclick="parent.addTab('output_tab12','${menu.LM00014}','/outputAttchFileList.do?subCode=7&subMenu=MNC02', this);"><a href="#">${menu.LM00014}</a></li>
                            </ul>
                        </li>
                        <li class="expandable"><div class="hitarea collapsable-hitarea"></div>${menu.LM00007}
                            <ul style="display: block;">
                                <li id='output_tab14' onclick="parent.addTab('output_tab14','${menu.LM00015}','/outputAttchFileList.do?subCode=9&subMenu=PPC01', this);"><a href="#">${menu.LM00015}</a></li>
                                <li class="last" id='output_tab15' onclick="parent.addTab('output_tab15','${menu.LM00016}','/outputAttchFileList.do?subCode=9&subMenu=PPC02', this);"><a href="#">${menu.LM00016}</a></li>
                            </ul>
                        </li>
                        <li id='output_tab13' onclick="parent.addTab('output_tab13','${menu.LM00006}','/outputAttchFileList.do?subCode=8', this);"><a href="#">${menu.LM00006}</a></li>
                        <li id='output_tab16' class="last" onclick="parent.addTab('output_tab16','${menu.LM00008}','/outputAttchFileList.do?subCode=10', this);"><a href="#">${menu.LM00008}</a></li>

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