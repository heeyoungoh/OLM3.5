﻿<%@page import="java.util.ArrayList"%>
<%@page contentType="application/msword; charset=utf-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="root"/>

<%@page import="java.io.*" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@page import="xbolt.cmm.framework.util.StringUtil" %>
<%@page import="xbolt.cmm.framework.val.GlobalVal" %>

<%@page import="com.aspose.words.*" %>
<%@page import="java.awt.Color" %>
<%@page import="java.text.SimpleDateFormat" %>

<%

try{
	
	String LogoImgUrl = "";
	String modelImgPath = GlobalVal.MODELING_DIGM_DIR;
	String logoPath = GlobalVal.FILE_UPLOAD_TINY_DIR;
	String defaultFont = String.valueOf(request.getAttribute("defaultFont"));
 	String fontFamilyHtml = "<span style=\"font-family:"+defaultFont+"; font-size: 10pt;\">";
	License license = new License();
	license.setLicense(logoPath + "Aspose.Words.lic");
	
	Document doc = new Document();
	DocumentBuilder builder = new DocumentBuilder(doc);	
	Map menu = (Map)request.getAttribute("menu");
 	Map setMap = (HashMap)request.getAttribute("setMap");
 	List allTotalList = (List)request.getAttribute("allTotalList");
 	
 	String onlyMap = String.valueOf(request.getAttribute("onlyMap"));
 	String paperSize = String.valueOf(request.getAttribute("paperSize"));
 	String itemNameOfFileNm = String.valueOf(request.getAttribute("ItemNameOfFileNm"));
 	List piSubItemList = (List)request.getAttribute("piSubItemList"); 	
 	List L4ProcessList = (List)request.getAttribute("L4ProcessList");
 	String selectedItemName = String.valueOf(request.getAttribute("selectedItemName"));
 	String selectedItemPath = String.valueOf(request.getAttribute("selectedItemPath"));
 	String reportCode = String.valueOf(request.getAttribute("reportCode"));
 	
 	List piKpiList = (List)request.getAttribute("piKpiList");
	List piRuleSetList = (List)request.getAttribute("piRuleSetList");
	List piToCheckList = (List)request.getAttribute("piToCheckList");
	Map selectedItemMap = (HashMap)request.getAttribute("selectedItemMap");

 	double titleCellWidth = 60.0;
 	double contentCellWidth3 = 90.0;
	double contentCellWidth = 165.0;
	double mergeCellWidth = 390.0;
	double totalCellWidth = 450.0;
	String value = "";
	String name = "";
//==================================================================================================
	Section currentSection = builder.getCurrentSection();
    PageSetup pageSetup = currentSection.getPageSetup();
    
    // page 여백 설정
	builder.getPageSetup().setRightMargin(30);
	builder.getPageSetup().setLeftMargin(30);
	builder.getPageSetup().setBottomMargin(30);
	builder.getPageSetup().setTopMargin(30);
	
	// PaperSize 설정
	if ("1".equals(paperSize)) {
		builder.getPageSetup().setPaperSize(PaperSize.A4);
	} else if ("2".equals(paperSize)) {
		builder.getPageSetup().setPaperSize(PaperSize.A3);
	}
//==================================================================================================

//=========================================================================
// TODO : FOOTER
	currentSection = builder.getCurrentSection();
    pageSetup = currentSection.getPageSetup();
    
    pageSetup.setDifferentFirstPageHeaderFooter(false);
    pageSetup.setFooterDistance(25);
    builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
    
    builder.startTable();
    builder.getCellFormat().getBorders().setLineWidth(0.0);
    builder.getFont().setName(defaultFont);
    builder.getFont().setColor(Color.BLACK);
    builder.getFont().setSize(10);
    
 	// 1.footer : Line
 	builder.getParagraphFormat().setSpaceBefore(7);
    builder.insertHtml("<hr size=5 color='silver'/>");
 	// 2.footer : logo
    builder.insertCell();
    builder.getCellFormat().setWidth(150.0);
    builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
    String imageFileName = logoPath + "logo_CJ.png";
   // builder.insertImage(imageFileName, 125, 25);
 	// 3.footer : current page / total page 
    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
    builder.insertCell();
    builder.getCellFormat().setWidth(150.0);
    // Set first cell to 1/3 of the page width.
    builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 / 3));
    // Insert page numbering text here.
    // It uses PAGE and NUMPAGES fields to auto calculate current page number and total number of pages.
    builder.insertField("PAGE", "");
    builder.write(" / ");
    builder.insertField("NUMPAGES", "");
    
 	// 4.footer : current page / total page 
    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
    builder.insertCell();
    builder.getCellFormat().setWidth(150.0);
    builder.write("PI TFT");    
    builder.endTable().setAllowAutoFit(false);        
    builder.moveToDocumentEnd();
	//=========================================================================
	
	builder = new DocumentBuilder(doc);	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//==================================================================================================
	/* 표지 */
 	// 표지 START
 	builder.startTable();
 	builder.getCellFormat().getBorders().setLineWidth(0.0);
 	
 	// 1.image
 	builder.insertCell();
  	builder.getCellFormat().setWidth(300.0);
 	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
 	builder.insertHtml("<br>");
  	builder.insertImage(imageFileName, 180, 36);
  	builder.insertHtml("<br>");
  	builder.endRow();
  		
	// 2.PI 과제 정의서
	builder.insertCell();
	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
    builder.getFont().setSize(32);
    builder.insertHtml("<br>");
    builder.write("PI 과제 정의서");
	builder.endRow();

	// 3.선택한 L2 프로세스 정보
	builder.insertCell();
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
    builder.getFont().setSize(18);
	builder.writeln("["+selectedItemName+"]"); // PI과제ID PI과제 명
	builder.insertHtml("<br>");
	builder.insertHtml("<br>");
	builder.insertHtml("<br>");
	builder.insertHtml("<br>");
	builder.insertHtml("<br>");
	builder.endRow();
	
	// 4.선택한 프로세스 정보 테이블
	///////////////////////////////////////////////////////////////////////////////////////
	builder.insertCell();
	builder.getCellFormat().setWidth(30); // 테이블 앞 여백 설정
	
	builder.insertCell();
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getCellFormat().setWidth(240);
	
	builder.startTable();
	builder.getRowFormat().clearFormatting();
	builder.getCellFormat().clearFormatting();
	
	// Make the header row.
	builder.insertCell();
	
	builder.getRowFormat().setHeight(30.0);
	builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
	
	// Some special features for the header row.
	builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(247, 247, 247));
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setSize(11);
	builder.getFont().setUnderline(0);
	builder.getFont().setBold(false);
	builder.getFont().setColor(Color.BLACK);
	builder.getFont().setName(defaultFont);
	
	builder.getCellFormat().setWidth(70);
	builder.write("Owner"); // 작성자

	builder.insertCell();
	builder.getCellFormat().setWidth(100);
	builder.write(String.valueOf(menu.get("LN00131"))); // 프로젝트

	builder.insertCell();
	builder.getCellFormat().setWidth(70);
	builder.write(String.valueOf(menu.get("LN00078"))); // 생성일
	
	builder.endRow();
	
	// Set features for the other rows and cells.
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	
	// Reset height and define a different height rule for table body
	builder.getRowFormat().setHeight(30.0);
	builder.getRowFormat().setHeightRule(HeightRule.AUTO);
	
	builder.insertCell();
   	builder.getCellFormat().setWidth(70);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.write(StringUtil.checkNullToBlank(selectedItemMap.get("Name")) + "("+StringUtil.checkNullToBlank(selectedItemMap.get("TeamName"))+")");				
	builder.insertCell();
   	builder.getCellFormat().setWidth(100);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.write(StringUtil.checkNullToBlank(selectedItemMap.get("RefPjtName"))+"/"+StringUtil.checkNullToBlank(selectedItemMap.get("ProjectName")));
	builder.insertCell();
   	builder.getCellFormat().setWidth(70);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.write(StringUtil.checkNullToBlank(selectedItemMap.get("CreateDT")));	
	builder.endRow();
	builder.endTable().setAlignment(TabAlignment.CENTER);
	builder.endRow();
	
	builder.endTable().setAllowAutoFit(false);
	builder.insertHtml("<br>");
    ///////////////////////////////////////////////////////////////////////////////////////
	
	
	
	builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);	
	// END 1.소과제 ////////////////////////////////////////////////////////////////////////////////////////////////////
	
	currentSection = builder.getCurrentSection();
    pageSetup = currentSection.getPageSetup();
    
    pageSetup.setDifferentFirstPageHeaderFooter(false);
    pageSetup.setHeaderDistance(25);
    builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
    
    //==================================================================================================
	// 머릿글 : START
	builder.startTable();
	builder.getRowFormat().clearFormatting();
	builder.getCellFormat().clearFormatting();
	
	// Make the header row.
	builder.insertCell();
	
	builder.getRowFormat().setHeight(26.0);
	// builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
	builder.getRowFormat().setHeightRule(HeightRule.AUTO);
	
	// Some special features for the header row.
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setSize(14);
	builder.getFont().setUnderline(0);
	builder.getFont().setBold(true);
	builder.getFont().setColor(Color.BLACK);
	builder.getFont().setName(defaultFont);
	
	builder.getCellFormat().setWidth(30);
	builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
	//builder.insertCell();
  		builder.insertImage(imageFileName, 125, 25);

	builder.insertCell();
	builder.getCellFormat().setWidth(80);
    builder.write("PI Task report"); 	
	
	builder.endRow();
	
	// Set features for the other rows and cells.
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setSize(11);
	builder.getFont().setUnderline(0);
	builder.getFont().setBold(false);
	
	// Reset height and define a different height rule for table body
	builder.getRowFormat().setHeight(30.0);
	builder.getRowFormat().setHeightRule(HeightRule.AUTO);
	
    builder.insertCell(); 	
    builder.getCellFormat().setWidth(30);
	builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
	
	builder.insertCell();
   	builder.getCellFormat().setWidth(35);builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.write(selectedItemName);  
  		
	builder.insertCell();
   	builder.getCellFormat().setWidth(45);builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.write(StringUtil.checkNullToBlank(selectedItemPath));
	builder.endRow();
	
	builder.endTable().setAllowAutoFit(false);
	 // 타이틀과 내용 사이 간격
    builder.insertHtml("<hr size=4 color='silver'/>");
 	// 머릿말 : END
 	builder.moveToDocumentEnd();
  	//==================================================================================================
  	
  	//==================================================================================================
	//. 기본정보 
 	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setSize(14);
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
	builder.writeln("1. "+menu.get("LN00005"));
	builder.getFont().setSize(11);
			
	currentSection = builder.getCurrentSection();
    pageSetup = currentSection.getPageSetup();
    pageSetup.setDifferentFirstPageHeaderFooter(false);
    pageSetup.setHeaderDistance(25);
   
	builder.startTable();	 	 		
    builder.getFont().setName(defaultFont);
    builder.getFont().setColor(Color.BLACK);
    builder.getFont().setSize(10);
	
	// Make the header row.
	builder.getRowFormat().clearFormatting();
	builder.getCellFormat().clearFormatting();
	builder.getRowFormat().setHeight(150.0);
	builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
	
 	// 1.ROW : 개요
	builder.insertCell();
	//==================================================================================================	
	builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
	builder.getCellFormat().setWidth(titleCellWidth);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setBold (true);
	builder.getFont().setSize(10);
	builder.write(String.valueOf(menu.get("LN00035")));  // 개요
	
	builder.insertCell();
	builder.getCellFormat().setWidth(mergeCellWidth);
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
	if(StringUtil.checkNullToBlank(selectedItemMap.get("Description")).contains("font-family")){
		builder.insertHtml(StringUtil.checkNullToBlank(selectedItemMap.get("Description")).replace("upload/", logoPath));
	}else{
		builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(selectedItemMap.get("Description")).replace("upload/", logoPath)+"</span>");
	}
	//==================================================================================================	
	builder.endRow();
	
	builder.getRowFormat().clearFormatting();
	builder.getCellFormat().clearFormatting();
	builder.getRowFormat().setHeight(30.0);
	builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
	
	// 2.ROW 
	builder.insertCell();
	//==================================================================================================	
	builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
	builder.getCellFormat().setWidth(titleCellWidth);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setBold (true);
	builder.write("기대효과");  //AT00023
	
	builder.insertCell();
	builder.getCellFormat().setWidth(mergeCellWidth);
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
	if(StringUtil.checkNullToBlank(selectedItemMap.get("AT00023")).contains("font-family")){
		builder.insertHtml(StringUtil.checkNullToBlank(selectedItemMap.get("AT00023")).replace("upload/", logoPath));
	}else{
		builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(selectedItemMap.get("AT00023")).replace("upload/", logoPath)+"</span>");
	}
	builder.endRow();
	//==================================================================================================
	
	// 3.ROW 
	builder.insertCell();	
	builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
	builder.getCellFormat().setWidth(titleCellWidth);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setBold (true);
	builder.write("Owner 조직");  
	
	builder.insertCell();
	builder.getCellFormat().setWidth(contentCellWidth);
	builder.getFont().setBold (false);
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
	builder.write(StringUtil.checkNullToBlank(selectedItemMap.get("OwnerTeamName")));  
	
	builder.insertCell();	
	builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
	builder.getCellFormat().setWidth(titleCellWidth);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
	builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
	builder.getFont().setBold (true);
	builder.write("Owner");  
	
	builder.insertCell();
	builder.getCellFormat().setWidth(contentCellWidth);
	builder.getFont().setBold (false);
	builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
	builder.write(StringUtil.checkNullToBlank(selectedItemMap.get("Name")));	
	builder.endRow();
	builder.endTable().setAllowAutoFit(false);
	builder.insertHtml("<BR>");
	//==================================================================================================
	
	if(piSubItemList != null){
	 	if(piSubItemList.size()>0){
	 		currentSection = builder.getCurrentSection();
		    pageSetup = currentSection.getPageSetup();
		    pageSetup.setDifferentFirstPageHeaderFooter(false);
		    pageSetup.setHeaderDistance(25);
    
	 		builder.startTable();	
	 		builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(25.0);
			builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
			
			builder.insertCell();
			builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setUnderline(0);
			builder.writeln("2. 하위 PI 세부과제 "); // 소과제	
			
		    builder.getFont().setSize(11);
		    builder.getFont().setBold(false);
		    builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		    builder.getCellFormat().getBorders().setLineWidth(0.0);
			for (int i = 0;piSubItemList.size() > i ; i++) {
				Map piSubItemMap = (Map) piSubItemList.get(i);
				
				String subItem = StringUtil.checkNull(piSubItemMap.get("SubPIIdentifier"))+" "+  StringUtil.checkNull(piSubItemMap.get("SubPIItemName"));
				builder.writeln("\t- " + subItem);
			}
			builder.endRow();
			builder.endTable().setAllowAutoFit(false);	
			
	 	}else{
	 		builder.insertHtml("<BR>");
	 	}
	}
	
	
	// subItem 정보 
	List piSubItemInfo = new ArrayList();
	if(piSubItemList != null && piSubItemList.size()>0){
		for (int i = 0;piSubItemList.size() > i ; i++) {
			Map piSubItemMap = (Map) piSubItemList.get(i);
			builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);			
			builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setUnderline(0);	
		    String subItem = StringUtil.checkNull(piSubItemMap.get("SubPIIdentifier"))+" "+  StringUtil.checkNull(piSubItemMap.get("SubPIItemName"));
			builder.writeln(subItem);			
			builder.insertHtml("<BR>");
			
			builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
			builder.writeln("1. "+menu.get("LN00005"));
			builder.getFont().setSize(11);	
			
			currentSection = builder.getCurrentSection();
		    pageSetup = currentSection.getPageSetup();
		    pageSetup.setDifferentFirstPageHeaderFooter(false);
		    pageSetup.setHeaderDistance(25);
    
	 		builder.startTable();	
	 		builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(25.0);
			builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
		  
			// 1.ROW : 개요
			builder.insertCell();
			//==================================================================================================	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.getFont().setSize(10);
			builder.write(String.valueOf(menu.get("LN00035")));  // 개요
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			if(StringUtil.checkNullToBlank(piSubItemMap.get("AT00003")).contains("font-family")){
				builder.insertHtml(StringUtil.checkNullToBlank(piSubItemMap.get("AT00003")).replace("upload/", logoPath));
			}else{
				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piSubItemMap.get("AT00003")).replace("upload/", logoPath)+"</span>");
			}
			//==================================================================================================	
			builder.endRow();
	
			// 2.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("Owner 조직");  
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(piSubItemMap.get("OwnerTeamName")));  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("Owner");  
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(piSubItemMap.get("OwnerName")));	
			builder.endRow();
			builder.endTable().setAllowAutoFit(false);
			builder.insertHtml("<BR>");
			//==================================================================================================
					
			// START SubItem 연관 프로세스 (subItemRelated) //////////////////////////////////////////////////////////////////////////////////
			currentSection = builder.getCurrentSection();
		    pageSetup = currentSection.getPageSetup();
		    pageSetup.setDifferentFirstPageHeaderFooter(false);
		    pageSetup.setHeaderDistance(25);
		  
			builder.startTable();	
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(25.0);
			builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
			
			builder.insertCell();
			builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);			    
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setBold(true);
			builder.writeln("2. 연관 프로세스");
			builder.getFont().setBold(false);
			builder.getFont().setSize(12);
			builder.getCellFormat().getBorders().setLineWidth(0.0);
			builder.insertHtml("<br>");
			
			List subItemRelatedList = (List)piSubItemMap.get("subItemRelatedList");
			if (subItemRelatedList.size() > 0 && subItemRelatedList != null) {		
				for (int j = 0;subItemRelatedList.size() > j ; j++) {
					Map subItemRelatedMap = (Map)subItemRelatedList.get(j);
		 			builder.writeln("\t" + subItemRelatedMap.get("PRCID")+' '+ subItemRelatedMap.get("PRCName"));
		 			if(StringUtil.checkNullToBlank(subItemRelatedMap.get("AT00003")).contains("font-family")){
		 				builder.write("\t - ");
		 				builder.insertHtml(StringUtil.checkNullToBlank(subItemRelatedMap.get("AT00003")));
		 			}else{
		 				builder.write("\t - ");
		 				builder.insertHtml(fontFamilyHtml + StringUtil.checkNullToBlank(subItemRelatedMap.get("AT00003")) + "</span>");
		 			}
		 			builder.insertHtml("<br>");
				}
				builder.endRow();
				builder.endTable().setAllowAutoFit(false);
			} // END L3ProcessList		
			//==================================================================================================
			
			//==================================================================================================
			//3. KPI (SubItem)
		 	builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
			builder.writeln("3. KPI");
			builder.getFont().setSize(11);
			
			List piSubItemKpiList = (List)piSubItemMap.get("piSubItemKpiList");
			if (piSubItemKpiList.size() > 0 && piSubItemKpiList != null) {			
				currentSection = builder.getCurrentSection();
			    pageSetup = currentSection.getPageSetup();
			    pageSetup.setDifferentFirstPageHeaderFooter(false);
			    pageSetup.setHeaderDistance(25);
		    
			    for(int k=0; k<piSubItemKpiList.size(); k++){
					Map subItemKpiMap = (HashMap) piSubItemKpiList.get(k);	
			    
				    builder.startTable();	 	 		
				    builder.getFont().setName(defaultFont);
				    builder.getFont().setColor(Color.BLACK);
				    builder.getFont().setSize(10);
					
					// Make the header row.
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(150.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
				 	// 1.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(subItemKpiMap.get("OBJID")));  
					
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(subItemKpiMap.get("OBJNM")));	
					builder.endRow();
					
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(30.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
					// 2.ROW 
					builder.insertCell();
					//==================================================================================================	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					if(StringUtil.checkNullToBlank(subItemKpiMap.get("OBJDESC")).contains("font-family")){
						builder.insertHtml(StringUtil.checkNullToBlank(subItemKpiMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
					}else{
						builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(subItemKpiMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
					}
					builder.endRow();
					//==================================================================================================
					// 3.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("산출공식"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(subItemKpiMap.get("KPIFORMULA")));  
					builder.endRow();
					
					//==================================================================================================
					// 4.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("단위"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(subItemKpiMap.get("AT00028")));  
					
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("주기"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(subItemKpiMap.get("AT00009")));	
					builder.endRow();
				
					builder.endTable().setAllowAutoFit(false);
					builder.insertHtml("<br>");
			    }
			}
			builder.insertHtml("<br>");
			//==================================================================================================
			// subItem KPI END
			
			//==================================================================================================
			// 4.Rule set (SubItem)
		 	builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
			builder.writeln("4. Rule");
			builder.getFont().setSize(11);
			
			List piSubItemRuleSetList = (List)piSubItemMap.get("piSubItemRuleSetList");
			if (piSubItemRuleSetList.size() > 0 && piSubItemRuleSetList != null) {		
				currentSection = builder.getCurrentSection();
			    pageSetup = currentSection.getPageSetup();
			    pageSetup.setDifferentFirstPageHeaderFooter(false);
			    pageSetup.setHeaderDistance(25);
			   
				builder.startTable();	
				builder.getRowFormat().clearFormatting();
				builder.getCellFormat().clearFormatting();
				builder.getRowFormat().setHeight(25.0);
				builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				
				builder.insertCell();
				builder.getFont().setBold(true);
				builder.getFont().setSize(11);
				builder.getCellFormat().getBorders().setLineWidth(0.0);
				String RuleSetRULECONTP = "";
				for(int l=0; l<piSubItemRuleSetList.size(); l++){
					Map ruleSetMap = (HashMap) piRuleSetList.get(l);	
					
					builder.startTable();	 	 		
				    builder.getFont().setName(defaultFont);
				    builder.getFont().setColor(Color.BLACK);
				    builder.getFont().setSize(10);
					
					// Make the header row.
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(150.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
				 	// 1.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("OBJID")));  
					
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("OBJNM")));	
					builder.endRow();
					
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(30.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
					// 2.ROW 
					builder.insertCell();
					//==================================================================================================	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					if(StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).contains("font-family")){
						builder.insertHtml(StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
					}else{
						builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
					}
					builder.endRow();
					//==================================================================================================
					// 3.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("통제유형"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("RULECONTP")));  
					builder.endRow();
					
					//==================================================================================================
					// 4.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("시스템 요구사항"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")));  
					if(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).contains("font-family")){
						builder.insertHtml(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).replace("upload/", logoPath).replace("<br/>","\n"));
					}else{
						builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
					}
					builder.endRow();
					
					builder.endTable().setAllowAutoFit(false);
				}
			// END SubItem RuleSet ==================================================================================================
			}else{
				builder.insertHtml("<br>");
			}
			//==================================================================================================
			// 5. To-Check
		 	builder.getFont().setColor(Color.DARK_GRAY);
		    builder.getFont().setSize(14);
		    builder.getFont().setBold(true);
		    builder.getFont().setName(defaultFont);
			builder.writeln("5. To-Check");
			builder.getFont().setSize(11);	
			
			List piSubItemToCheckList = (List)piSubItemMap.get("piSubItemToCheckList");
			if (piSubItemToCheckList.size() > 0 && piSubItemToCheckList != null) {		
				currentSection = builder.getCurrentSection();
			    pageSetup = currentSection.getPageSetup();
			    pageSetup.setDifferentFirstPageHeaderFooter(false);
			    pageSetup.setHeaderDistance(25);
			   
				builder.startTable();	
				builder.getRowFormat().clearFormatting();
				builder.getCellFormat().clearFormatting();
				builder.getRowFormat().setHeight(25.0);
				builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				
				builder.insertCell();
				builder.getFont().setBold(true);
				builder.getFont().setSize(11);
				builder.getCellFormat().getBorders().setLineWidth(0.0);
				//String RuleSetRULECONTP = "";
				for(int m=0; m<piSubItemToCheckList.size(); m++){
					Map piSubItemToCheckMap = (HashMap) piSubItemToCheckList.get(m);	
					
					builder.startTable();	 	 		
				    builder.getFont().setName(defaultFont);
				    builder.getFont().setColor(Color.BLACK);
				    builder.getFont().setSize(10);
					
					// Make the header row.
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(150.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
				 	// 1.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(piSubItemToCheckMap.get("OBJID")));  
					
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
					
					builder.insertCell();
					builder.getCellFormat().setWidth(contentCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write(StringUtil.checkNullToBlank(piSubItemToCheckMap.get("OBJNM")));	
					builder.endRow();
					
					builder.getRowFormat().clearFormatting();
					builder.getCellFormat().clearFormatting();
					builder.getRowFormat().setHeight(30.0);
					builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
					
					// 2.ROW 
					builder.insertCell();
					//==================================================================================================	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					if(StringUtil.checkNullToBlank(piSubItemToCheckMap.get("OBJDESC")).contains("font-family")){
						builder.insertHtml(StringUtil.checkNullToBlank(piSubItemToCheckMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
					}else{
						builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piSubItemToCheckMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
					}
					builder.endRow();
					//==================================================================================================
					// 3.ROW 
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("Portal 연계"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write("");  
					
					builder.insertCell();	
					builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					builder.getCellFormat().setWidth(titleCellWidth);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setBold (true);
					builder.write("Type"); 
					
					builder.insertCell();
					builder.getCellFormat().setWidth(mergeCellWidth);
					builder.getFont().setBold (false);
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					builder.write("");  
					builder.endRow();
					
					builder.endTable().setAllowAutoFit(false);
					builder.insertHtml("<br>");
				}
				builder.endRow();
				builder.endTable().setAllowAutoFit(false);
			} 		
			// END ToCheck ==================================================================================================
		builder.insertHtml("<br>");
		//==================================================================================================
		}
	}
	
	
	/*
	
	
	// START 프로세스 (L4ProcessList) //////////////////////////////////////////////////////////////////////////////////
	currentSection = builder.getCurrentSection();
    pageSetup = currentSection.getPageSetup();
    pageSetup.setDifferentFirstPageHeaderFooter(false);
    pageSetup.setHeaderDistance(25);
  
	builder.startTable();	
	builder.getRowFormat().clearFormatting();
	builder.getCellFormat().clearFormatting();
	builder.getRowFormat().setHeight(25.0);
	builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
	
	builder.insertCell();
	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setSize(14);			    
    builder.getFont().setName(defaultFont);
    builder.getFont().setBold(true);
	builder.writeln("2. 연관 프로세스");
	builder.getFont().setBold(true);
	builder.getFont().setSize(12);
	builder.getCellFormat().getBorders().setLineWidth(0.0);
	builder.insertHtml("<br>");
	if (L4ProcessList.size() > 0 && L4ProcessList != null) {		
		for (int i = 0;L4ProcessList.size() > i ; i++) {
			Map L4ProcessMap = (Map)L4ProcessList.get(i);
 			builder.writeln(" " + L4ProcessMap.get("PRCID")+' '+ L4ProcessMap.get("PRCName"));
 			if(StringUtil.checkNullToBlank(L4ProcessMap.get("AT00003")).contains("font-family")){
 				builder.insertHtml(StringUtil.checkNullToBlank(L4ProcessMap.get("AT00003")));
 			}else{
 			//	builder.write("\t");
 				builder.insertHtml(fontFamilyHtml + StringUtil.checkNullToBlank(L4ProcessMap.get("AT00003")) + "</span>");
 			}
 			builder.insertHtml("<br>");builder.insertHtml("<br>");
		}
		builder.endRow();
		builder.endTable().setAllowAutoFit(false);
		builder.insertHtml("<br>");
	} // END L4ProcessList		
	//==================================================================================================
	
	//==================================================================================================
	//3. KPI
 	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setSize(14);
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
	builder.writeln("3. KPI");
	builder.getFont().setSize(11);
	
	if (piKpiList.size() > 0 && piKpiList != null) {		
	currentSection = builder.getCurrentSection();
    pageSetup = currentSection.getPageSetup();
    pageSetup.setDifferentFirstPageHeaderFooter(false);
    pageSetup.setHeaderDistance(25);
    
	    for(int j=0; j<piKpiList.size(); j++){
			Map kpiMap = (HashMap) piKpiList.get(j);	
	    
		    builder.startTable();	 	 		
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setColor(Color.BLACK);
		    builder.getFont().setSize(10);
			
			// Make the header row.
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(150.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
		 	// 1.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(kpiMap.get("OBJID")));  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(kpiMap.get("OBJNM")));	
			builder.endRow();
			
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(30.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
			// 2.ROW 
			builder.insertCell();
			//==================================================================================================	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			if(StringUtil.checkNullToBlank(kpiMap.get("OBJDESC")).contains("font-family")){
				builder.insertHtml(StringUtil.checkNullToBlank(kpiMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
			}else{
				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(kpiMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
			}
			builder.endRow();
			//==================================================================================================
			// 3.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("산출공식"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(kpiMap.get("KPIFORMULA")));  
			builder.endRow();
			
			//==================================================================================================
			// 4.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("단위"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(kpiMap.get("AT00028")));  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("주기"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(kpiMap.get("AT00009")));	
			builder.endRow();
		
			builder.endTable().setAllowAutoFit(false);
			builder.insertHtml("<br>");
		//==================================================================================================
	    }
	}
	//==================================================================================================
	builder.insertHtml("<br>");
	//==================================================================================================
	// 4.Rule set
 	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setSize(14);
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
	builder.writeln("4. Rule");
	builder.getFont().setSize(11);
	
	if (piRuleSetList.size() > 0 && piRuleSetList != null) {		
		currentSection = builder.getCurrentSection();
	    pageSetup = currentSection.getPageSetup();
	    pageSetup.setDifferentFirstPageHeaderFooter(false);
	    pageSetup.setHeaderDistance(25);
	   
		builder.startTable();	
		builder.getRowFormat().clearFormatting();
		builder.getCellFormat().clearFormatting();
		builder.getRowFormat().setHeight(25.0);
		builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
		
		builder.insertCell();
		builder.getFont().setBold(true);
		builder.getFont().setSize(11);
		builder.getCellFormat().getBorders().setLineWidth(0.0);
		String RuleSetRULECONTP = "";
		for(int j=0; j<piRuleSetList.size(); j++){
			Map ruleSetMap = (HashMap) piRuleSetList.get(j);	
			
			builder.startTable();	 	 		
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setColor(Color.BLACK);
		    builder.getFont().setSize(10);
			
			// Make the header row.
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(150.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
		 	// 1.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("OBJID")));  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("OBJNM")));	
			builder.endRow();
			
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(30.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
			// 2.ROW 
			builder.insertCell();
			//==================================================================================================	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			if(StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).contains("font-family")){
				builder.insertHtml(StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
			}else{
				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(ruleSetMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
			}
			builder.endRow();
			//==================================================================================================
			// 3.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("통제유형"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("RULECONTP")));  
			builder.endRow();
			
			//==================================================================================================
			// 4.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("시스템 요구사항"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")));  
			if(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).contains("font-family")){
				builder.insertHtml(StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).replace("upload/", logoPath).replace("<br/>","\n"));
			}else{
				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(ruleSetMap.get("RULESYSREQ")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
			}
			builder.endRow();
			
			builder.endTable().setAllowAutoFit(false);
		}
		builder.endRow();
		builder.endTable().setAllowAutoFit(false);
	} 		
	// END RuleSet ==================================================================================================
			
	//==================================================================================================
	// 5. To-Check
 	builder.getFont().setColor(Color.DARK_GRAY);
    builder.getFont().setSize(14);
    builder.getFont().setBold(true);
    builder.getFont().setName(defaultFont);
	builder.writeln("5. To-Check");
	builder.getFont().setSize(11);	
	if (piToCheckList.size() > 0 && piToCheckList != null) {		
		currentSection = builder.getCurrentSection();
	    pageSetup = currentSection.getPageSetup();
	    pageSetup.setDifferentFirstPageHeaderFooter(false);
	    pageSetup.setHeaderDistance(25);
	   
		builder.startTable();	
		builder.getRowFormat().clearFormatting();
		builder.getCellFormat().clearFormatting();
		builder.getRowFormat().setHeight(25.0);
		builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
		
		builder.insertCell();
		builder.getFont().setBold(true);
		builder.getFont().setSize(11);
		builder.getCellFormat().getBorders().setLineWidth(0.0);
		String RuleSetRULECONTP = "";
		for(int j=0; j<piToCheckList.size(); j++){
			Map toCheckMap = (HashMap) piToCheckList.get(j);	
			
			builder.startTable();	 	 		
		    builder.getFont().setName(defaultFont);
		    builder.getFont().setColor(Color.BLACK);
		    builder.getFont().setSize(10);
			
			// Make the header row.
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(150.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
		 	// 1.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00106"))); // ID  
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(toCheckMap.get("OBJID")));  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00028"))); // 명칭
			
			builder.insertCell();
			builder.getCellFormat().setWidth(contentCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write(StringUtil.checkNullToBlank(toCheckMap.get("OBJNM")));	
			builder.endRow();
			
			builder.getRowFormat().clearFormatting();
			builder.getCellFormat().clearFormatting();
			builder.getRowFormat().setHeight(30.0);
			builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			
			// 2.ROW 
			builder.insertCell();
			//==================================================================================================	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write(StringUtil.checkNullToBlank(menu.get("LN00035")));  //LN00035 개요
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			if(StringUtil.checkNullToBlank(toCheckMap.get("OBJDESC")).contains("font-family")){
				builder.insertHtml(StringUtil.checkNullToBlank(toCheckMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n"));
			}else{
				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(toCheckMap.get("OBJDESC")).replace("upload/", logoPath).replace("<br/>","\n")+"</span>");
			}
			builder.endRow();
			//==================================================================================================
			// 3.ROW 
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("Portal 연계"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write("");  
			
			builder.insertCell();	
			builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			builder.getCellFormat().setWidth(titleCellWidth);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			builder.getFont().setBold (true);
			builder.write("Type"); 
			
			builder.insertCell();
			builder.getCellFormat().setWidth(mergeCellWidth);
			builder.getFont().setBold (false);
			builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			builder.write("");  
			builder.endRow();
			
			builder.endTable().setAllowAutoFit(false);
			builder.insertHtml("<br>");
		}
		builder.endRow();
		builder.endTable().setAllowAutoFit(false);
	} 		
	// END RuleSet ==================================================================================================
	*/
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	long date = System.currentTimeMillis();
    String fileName = "PI_" + itemNameOfFileNm + "_" + formatter.format(date) + ".docx";
    response.setContentType("application/msword");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("content-disposition","attachment; filename=" + fileName);
    
    doc.save(response.getOutputStream(), SaveFormat.DOCX);

} catch(Exception e){
	e.printStackTrace();
	
} finally{
	response.getOutputStream().flush();
	response.getOutputStream().close();
}

%>

