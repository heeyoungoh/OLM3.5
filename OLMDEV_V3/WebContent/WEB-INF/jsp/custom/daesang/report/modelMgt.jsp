<%@ page import = "java.util.List, java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<c:url value="/" var="root"/>

<%@ include file="/WEB-INF/jsp/template/tagInc.jsp"%>
<%@ include file="/WEB-INF/jsp/template/loadingInc.jsp"%>

<script>
   var p_gridArea;   

   $(document).ready(function(){   
      // 초기 표시 화면 크기 조정 
      $("#grdGridArea").attr("style","height:"+(setWindowHeight() - 220)+"px;");
      // 화면 크기 조정
      window.onresize = function() {
         $("#grdGridArea").attr("style","height:"+(setWindowHeight() - 220)+"px;");
      };
      
     $("#excel").click(function(){doExcel();return false;});
      
     gridInit();   
     doSearchList();
   });
   
   function setWindowHeight(){
      var size = window.innerHeight;
      var height = 0;
      if( size == null || size == undefined){
         height = document.body.clientHeight;
      }else{
         height=window.innerHeight;
      }return height;
   }
   
   //===============================================================================
   // BEGIN ::: GRID
   function gridInit(){      
      var d = setGridData();
      p_gridArea = fnNewInitGrid("grdGridArea", d);
      p_gridArea.enablePaging(true,50,null,"pagingArea",true,"recInfoArea");
      p_gridArea.setPagingSkin("bricks");   
      p_gridArea.attachEvent("onPageChanged", function(ind,fInd,lInd){$("#currPage").val(ind);});
   }
   
   function setGridData(){
      var result = new Object();
      result.title = "${title}";
      result.key = "custom_SQL.getDSModelList";
      result.header = "${menu.LN00024},Path,${menu.LN00106},Item Name,${menu.LN00004},Model Name,Model Category,Model Status,${menu.LN00070},${menu.LN00105}";
      result.cols = "Path|ID|ItemName|ItemAuthor|ModelName|MTCategory|ModelStatus|LastUpdated|MDLastUser";
      result.widths = "50,170,100,130,70,130,100,80,80,70";
      result.sorting = "str,str,str,str,str,str,str,str,str,str,str";
      result.aligns = "center,left,center,left,center,left,center,center,center,center";
      if($("#L1List").val() != '' && $("#L1List").val() != null){
    	  result.data = "&L1List=" + $("#L1List").val();
      }
      return result;
   }
      
   function doSearchList(){
	   	var d = setGridData();
		fnLoadDhtmlxGridJson(p_gridArea, d.key, d.cols, d.data);
   }
   
	function doExcel() {	
    	p_gridArea.toExcel("${root}excelGenerate");
   	}   
</script>
<form name="downPrcFrm" id="downPrcFrm" action="" method="post"  onsubmit="return false;">
<input type="hidden" id="currPage" name="currPage" value="${pageNum}"/>
<div id="proDiv" style="overflow:auto;width:100%">
	<div class="floatL msg" style="width:100%">
		<img src="${root}${HTML_IMG_DIR}/bullet_blue.png" id="subTitle_baisic">&nbsp;&nbsp;L5 Model List</span>
	</div>	
	<div>
		<li class="floatL pdR20">
		 	<select id="L1List" Name="L1List" style="width:150px;">
		       	<option value=''>Select</option>
		       	<c:forEach items="${L1List}" var="item" varStatus="status"> 
		       		<option value="${item.Identifier}">${item.Identifier} ${item.PlainText}</option>
		       	</c:forEach>
		    </select>
		    &nbsp;<input type="image" class="image searchList" src="${root}${HTML_IMG_DIR}/btn_icon_search.gif" value="Search" onclick="doSearchList()"/>
	    </li>
    </div>
	<div class="countList" >
		<li class="count">Total  <span id="TOT_CNT"></span></li>
        <li class="floatR">
           <span class="btn_pack small icon"><span class="down"></span><input value="Down" type="submit" id="excel"></span>
         </li>
	</div>
	<div id="gridDiv" style="width:100%;" class="clear">
		<div id="grdGridArea" style="width:100%"></div>
	</div>
	<div style="width:100%;" class="paginate_regular"><div id="pagingArea" style="display:inline-block;"></div><div id="recinfoArea" class="floatL pdL5"></div></div>
</div>
</form>
<iframe name="blankFrame" id="blankFrame" style="width:0px;height:0px; display:none;"></iframe>