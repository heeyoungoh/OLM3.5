package xbolt.cmm.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.io.File;
import org.springframework.ui.ModelMap;

import com.aspose.words.*;
import xbolt.cmm.framework.val.GlobalVal;


/**
 * 공통 서블릿 처리
 * @Class Name : MakeWordReport.java
 * @Description : wordReport 관련 함수
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2017. 05. 10. smartfactory		최초생성
 *
 * @since 2017. 05. 10.
 * @version 1.0
 * @see
 * 
 * Copyright (C) 2013 by SMARTFACTORY All right reserved.
 */
@SuppressWarnings("unused")
public class MakeWordReport {	
	
	public static String makeWordExportCJGLOBAL(HashMap commandMap, ModelMap model) {
		String returnValue = "";
		try{
			String LogoImgUrl = "";
			String modelImgPath = GlobalVal.MODELING_DIGM_DIR;
			String logoPath = GlobalVal.FILE_UPLOAD_TINY_DIR;
			String defaultFont = String.valueOf(model.get("defaultFont"));
		 	
			License license = new License();
			license.setLicense(logoPath + "Aspose.Words.lic");
			
			Document doc = new Document();
			DocumentBuilder builder = new DocumentBuilder(doc);	
			
			Map menu = (Map)model.get("menu");
		 	Map setMap = (HashMap)model.get("setMap");
		 	List allTotalList = (List)model.get("allTotalList");
		 	
		 	String onlyMap = String.valueOf(model.get("onlyMap"));
		 	String paperSize = String.valueOf(model.get("paperSize"));
		 	String itemNameOfFileNm = String.valueOf(model.get("ItemNameOfFileNm")).replaceAll("/", "&");
		 	
		 	Map e2eModelMap = (Map)model.get("e2eModelMap");
		 	Map e2eItemInfo = (Map)model.get("e2eItemInfo");
		 	Map e2eAttrMap = (Map)model.get("e2eAttrMap");
		 	Map e2eAttrNameMap = (Map)model.get("e2eAttrNameMap");
		 	Map e2eAttrHtmlMap = (Map)model.get("e2eAttrHtmlMap");
		 	
		 	Map piItemInfo = (Map)model.get("piItemInfo");
		 	Map piAttrMap = (Map)model.get("piAttrMap");
		 	Map piAttrNameMap = (Map)model.get("piAttrNameMap");
		 	Map piAttrHtmlMap = (Map)model.get("piAttrHtmlMap");
		 	
		 	List e2eDimResultList = (List)model.get("e2eDimResultList");		 	
		 	List subTreeItemIDList = (List)model.get("subTreeItemIDList");
		 	String selectedItemPath = String.valueOf(model.get("selectedItemPath"));
		 	String reportCode = String.valueOf(model.get("reportCode"));
		 	double titleCellWidth = 60.0;
		 	double contentCellWidth3 = 90.0;
			double contentCellWidth = 165.0;
			double mergeCellWidth = 390.0;
			double totalCellWidth = 450.0;
			String value = "";
			String name = "";
			String fontFamilyHtml = "<span style=\"font-family:"+defaultFont+"; font-size: 10pt;\">";
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
		    //builder.insertImage(imageFileName, 125, 25);
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
		    builder.write("Global Consolidation PI TFT");
		    
		    builder.endTable().setAllowAutoFit(false);
		        
		    builder.moveToDocumentEnd();
		//=========================================================================
			
			builder = new DocumentBuilder(doc);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			for (int totalCnt = 0;allTotalList.size() > totalCnt ; totalCnt++ ) {
				Map allTotalMap = (Map)allTotalList.get(totalCnt);
				Map titleItemMap = new HashMap();
				List totalList = (List)allTotalMap.get("totalList");
				if (!e2eModelMap.isEmpty()) {
					titleItemMap = e2eItemInfo;
				}else{
					titleItemMap = (Map)allTotalMap.get("titleItemMap");
				}
				
				//==================================================================================================
				/* 표지 */
				//if (totalList.size() > 0) { 
					currentSection = builder.getCurrentSection();
				    pageSetup = currentSection.getPageSetup();
				    pageSetup.setDifferentFirstPageHeaderFooter(true);
				   // pageSetup.setD
				 	// 표지 START
				 	builder.startTable();
				 	builder.getCellFormat().getBorders().setLineWidth(0.0);
				 	
				 	// 1.image
				 	builder.insertCell();
		    		builder.getCellFormat().setWidth(300.0);
				 	builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
				 	builder.insertHtml("<br>");
		    	//	builder.insertImage(imageFileName, 180, 36);
		    		builder.insertHtml("<br>");
		    		builder.endRow();
		    		
		    		// 2.프로세스 정의서
		    		builder.insertCell();
		    		builder.getFont().setColor(Color.BLACK);
				    builder.getFont().setBold(true);
				    builder.getFont().setName(defaultFont);
				    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
				    builder.getFont().setSize(60);
				    builder.insertHtml("<br>");
				    builder.getFont().setSize(36);
				    if (!e2eModelMap.isEmpty()) {
				    	builder.writeln("E2E Process report");
				    }else if(reportCode.equals("RP00031")){
				    	builder.writeln("PI Task report");
				    } else {
				    	builder.writeln("Process Definition Report");
				    }
					builder.endRow();
					
					// 3.선택한 L2 프로세스 정보
		    		builder.insertCell();
		    		builder.getFont().setColor(Color.BLACK);
				    builder.getFont().setBold(true);
				    builder.getFont().setName(defaultFont);
				    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
				    builder.getFont().setSize(26);
				    builder.getFont().setUnderline(0);
					builder.writeln("["+selectedItemPath+"]");
					builder.insertHtml("<br>");
		    		builder.insertHtml("<br>");
		    		builder.insertHtml("<br>");
					builder.endRow();
		    		
		    		// 4.선택한 L2 프로세스 정보 테이블
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
					builder.write(String.valueOf(menu.get("LN00060"))); // 작성자

					builder.insertCell();
					builder.getCellFormat().setWidth(100);
					builder.write(String.valueOf(menu.get("LN00131"))); // 프로젝트

					builder.insertCell();
					builder.getCellFormat().setWidth(70);
					builder.write(String.valueOf(menu.get("LN00013"))); // 생성일
					
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
					builder.write(StringUtil.checkNullToBlank(titleItemMap.get("Name")));				
					builder.insertCell();
				   	builder.getCellFormat().setWidth(100);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.write("Global Consolidation PI TFT");
					builder.insertCell();
				   	builder.getCellFormat().setWidth(70);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.write(StringUtil.checkNullToBlank(titleItemMap.get("CreateDT")));	
					builder.endRow();
					builder.endTable().setAlignment(TabAlignment.CENTER);
					builder.endRow();
					
					builder.endTable().setAllowAutoFit(false);
		    		///////////////////////////////////////////////////////////////////////////////////////
		    		// 표지 END
				    builder.insertBreak(BreakType.PAGE_BREAK);
		    		if (subTreeItemIDList.size() > 0) { 
		    			// content START	
						builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(14);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
					    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					    builder.insertHtml("<br>");
						builder.writeln("- Content -"); // content
						builder.insertHtml("<br>");
						
					    builder.getFont().setSize(11);
					    builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					    
						for (int lowlankCnt = 0;subTreeItemIDList.size() > lowlankCnt ; lowlankCnt++) {
							Map lowLankItemIdMap = (Map) subTreeItemIDList.get(lowlankCnt);
							
							String l2Item = StringUtil.checkNull(lowLankItemIdMap.get("l2Item"));
							List l3l4ItemIdList = (List) lowLankItemIdMap.get("l3l4ItemIdList");
							
							if (!l2Item.isEmpty()) {
								builder.writeln("\t" + l2Item);
							}
							
							for (int l3l4Cnt = 0;l3l4ItemIdList.size() > l3l4Cnt ; l3l4Cnt++) {
								Map l3l4ItemIdMap = (Map) l3l4ItemIdList.get(l3l4Cnt);
								String l3Item = StringUtil.checkNull(l3l4ItemIdMap.get("l3Item"));
								List l4ItemList = (List) l3l4ItemIdMap.get("l4ItemList");
								
								if (!l3Item.isEmpty()) {
									builder.writeln("\t\t" + l3Item);
								}
								for (int l4Cnt = 0;l4ItemList.size() > l4Cnt ; l4Cnt++) {
									builder.writeln("\t\t\t" + l4ItemList.get(l4Cnt).toString());
								}
							}
						}
		    		}
					// content END
					
					/* E2E : E2E기본정보 및 E2E모델맵 표시*/
					if (!e2eModelMap.isEmpty()) {
						builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
						//==================================================================================================
						// 머릿말 : START
						currentSection = builder.getCurrentSection();
					    pageSetup = currentSection.getPageSetup();
					    pageSetup.setDifferentFirstPageHeaderFooter(false);
					    pageSetup.setHeaderDistance(25);
					    builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
					    
						builder.startTable();
						builder.getRowFormat().clearFormatting();
						builder.getCellFormat().clearFormatting();
						builder.getRowFormat().setHeight(25.0);
						builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
						
						builder.insertCell();
						builder.getCellFormat().getBorders().setColor(Color.WHITE);
						builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
						builder.getFont().setName(defaultFont);
					    builder.getFont().setBold(true);
					    builder.getFont().setColor(Color.BLUE);
					    builder.getFont().setSize(14);
					    builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					   	name = StringUtil.checkNullToBlank(e2eItemInfo.get("ItemName")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
					   	name = name.replace("&#10;", " ");
					   	name = name.replace("&#xa;", "");
					   	name = name.replace("&nbsp;", " ");
					    builder.write("E2E Scenario : " + StringUtil.checkNullToBlank(e2eItemInfo.get("Identifier")) + " "+ name);
					   
					    builder.getFont().setName(defaultFont);
					    builder.getFont().setColor(Color.LIGHT_GRAY);
					    builder.getFont().setSize(12);
					    
					    builder.insertCell();
						builder.getCellFormat().getBorders().setColor(Color.WHITE);
						builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
						builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
						String path = String.valueOf(e2eItemInfo.get("Path"));
						if (path.equals("/")) {
							path = "";
						}
					    builder.write(path); 
					    builder.endRow();	
					    builder.endTable().setAllowAutoFit(false);	
					    
					    // 타이틀과 내용 사이 간격
					    builder.insertHtml("<hr size=4 color='silver'/>");
					    
					 	// 머릿말 : END
					 	builder.moveToDocumentEnd();
					  	//==================================================================================================
					  	//==================================================================================================
					  	// E2E 기본정보 표시
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
			 	 		builder.write(String.valueOf(e2eAttrNameMap.get("AT00003")));  // 개요
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(e2eAttrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00003")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00003")).replace("/upload", logoPath));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(e2eAttrMap.get("AT00003")).replace("/upload", logoPath)+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00003"))); // 개요 : 내용
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
			 	 		builder.write(String.valueOf(e2eAttrNameMap.get("AT00020")));  //  Module
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(e2eAttrHtmlMap.get("AT00020")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00020")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00020")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(e2eAttrMap.get("AT00020"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00020"))); // Main Module : 내용
			 	 		}
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(e2eAttrNameMap.get("AT00072")));  // 
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(e2eAttrHtmlMap.get("AT00072")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00072")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00072")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(e2eAttrMap.get("AT00072"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00072"))); // 연관 모듈 : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 3.ROW
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00060"))); // 작성자
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
				 		builder.write(StringUtil.checkNullToBlank(e2eItemInfo.get("Name"))); // 작성자 : 내용
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(e2eAttrNameMap.get("AT00018")));  // Due date
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(e2eAttrHtmlMap.get("AT00018")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00018")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00018")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(e2eAttrMap.get("AT00018"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00018"))); // Due date : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 4.ROW
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00013"))); // 생성일
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
				 		builder.write(StringUtil.checkNullToBlank(e2eItemInfo.get("CreateDT"))); // 생성일 : 내용
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00070")));  // 수정일
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
			 	 		builder.write(StringUtil.checkNullToBlank(e2eItemInfo.get("LastUpdated"))); // 수정일 : 내용
			 	 		builder.endRow();
			 	 		//==================================================================================================
			 	 		
			 	 		// 5.ROW
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(e2eAttrNameMap.get("AT00006")));  // 비고
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(e2eAttrHtmlMap.get("AT00006")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00006")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00006")).replace("/upload", logoPath));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(e2eAttrMap.get("AT00006")).replace("/upload", logoPath)+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(e2eAttrMap.get("AT00006"))); // 비고 : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 6.ROW (Dimension 정보 만큼 행 표시)
			 	 		for(int j=0; j<e2eDimResultList.size(); j++){
			 	 			Map dimResultMap = (Map) e2eDimResultList.get(j);
				 	 		
			 	 			builder.insertCell();
				 	 		//==================================================================================================	
				 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
				 	 		builder.getCellFormat().setWidth(titleCellWidth);
				 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
				 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
				 	 		builder.getFont().setBold (true);
				 	 		builder.write(String.valueOf(dimResultMap.get("dimTypeName")));
				 	 		
				 	 		builder.insertCell();
				 	 		builder.getCellFormat().setWidth(mergeCellWidth);
				 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
				 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
				 	 		builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(dimResultMap.get("dimValueNames")));
				 	 		//==================================================================================================	
			 	 			builder.endRow();
			 	 		}
			 	 		
			 	 		builder.endTable().setAllowAutoFit(false);
			 	 		builder.writeln();
					  	
					  	
					  	//==================================================================================================
						//==================================================================================================
					 	//프로세스 맵
					 	builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
				 	 	builder.startTable();
				 	    
				 	 	builder.insertCell();
				 	 	builder.getRowFormat().clearFormatting();
				 		builder.getCellFormat().clearFormatting();
				 		builder.getRowFormat().setHeight(20.0);
				 		builder.getRowFormat().setHeightRule(HeightRule.AUTO);
				 	 	builder.getCellFormat().setWidth(totalCellWidth);
				 	 	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
				 	 	
				 		String imgLang = "_"+StringUtil.checkNull(setMap.get("langCode"));
				 		String imgPath = modelImgPath+e2eModelMap.get("ModelID")+imgLang+"."+GlobalVal.MODELING_DIGM_IMG_TYPE;
				 		int width = Integer.parseInt(String.valueOf(e2eModelMap.get("Width")));
				 		int height = Integer.parseInt(String.valueOf(e2eModelMap.get("Height")));
				 		//System.out.println("프로세스맵 imgPath="+imgPath);
				 		try{
				 			if(!imgPath.equals("")){
					 			builder.insertHtml("<br>");
					 			builder.insertImage(imgPath, RelativeHorizontalPosition.PAGE, 30, RelativeVerticalPosition.PAGE,20,width,height,WrapType.INLINE);
					 			builder.insertHtml("<br>");
				 			}
				 		} catch(Exception ex){}
				 		
				 		builder.endTable().setAllowAutoFit(false);
					}
			 		//==================================================================================================
					// E2E 기본정보 END
				
					// PI 과제기본정보 */
					if(reportCode.equals("RP00031")){ 
						builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
						//==================================================================================================
						// 머릿말 : START
						currentSection = builder.getCurrentSection();
					    pageSetup = currentSection.getPageSetup();
					    pageSetup.setDifferentFirstPageHeaderFooter(false);
					    pageSetup.setHeaderDistance(25);
					    builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
					    
						builder.startTable();
						builder.getRowFormat().clearFormatting();
						builder.getCellFormat().clearFormatting();
						builder.getRowFormat().setHeight(25.0);
						builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
						
						builder.insertCell();
						builder.getCellFormat().getBorders().setColor(Color.WHITE);
						builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
						builder.getFont().setName(defaultFont);
					    builder.getFont().setBold(true);
					    builder.getFont().setColor(Color.BLUE);
					    builder.getFont().setSize(14);
					    builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					   	name = StringUtil.checkNullToBlank(piItemInfo.get("ItemName")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
					   	name = name.replace("&#10;", " ");
					   	name = name.replace("&#xa;", "");
					   	name = name.replace("&nbsp;", " ");
					    builder.write("PI 과제 : " + StringUtil.checkNullToBlank(piItemInfo.get("Identifier")) + " "+ name);
					   
					    builder.getFont().setName(defaultFont);
					    builder.getFont().setColor(Color.LIGHT_GRAY);
					    builder.getFont().setSize(10);
					    
					    builder.insertCell();
						builder.getCellFormat().getBorders().setColor(Color.WHITE);
						builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
						builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
						String path = String.valueOf(piItemInfo.get("Path"));
						if (path.equals("/")) {
							path = "";
						}
					    builder.write(path); 
					    builder.endRow();	
					    builder.endTable().setAllowAutoFit(false);	
					    
					    // 타이틀과 내용 사이 간격
					    builder.insertHtml("<hr size=4 color='silver'/>");
					    
					 	// 머릿말 : END
					 	builder.moveToDocumentEnd();
					  	//==================================================================================================
					  	//==================================================================================================
					  	// PI과제 기본정보 표시
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
			 	 		builder.write(String.valueOf(piAttrNameMap.get("AT00003")));  // PI 개요
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(piAttrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(piAttrMap.get("AT00003")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(piAttrMap.get("AT00003")).replace("/upload", logoPath));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piAttrMap.get("AT00003")).replace("/upload", logoPath)+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(piAttrMap.get("AT00003"))); // PI 개요 : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 2.ROW : 비고
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(piAttrNameMap.get("AT00006")));  // PI 비고
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(piAttrHtmlMap.get("AT00006")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(piAttrMap.get("AT00006")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(piAttrMap.get("AT00006")).replace("/upload", logoPath));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piAttrMap.get("AT00006")).replace("/upload", logoPath)+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(piAttrMap.get("AT00006"))); // PI 비고 : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		builder.getRowFormat().clearFormatting();
						builder.getCellFormat().clearFormatting();
						builder.getRowFormat().setHeight(30.0);
			 	 		builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			 	 		
			 	 		// 3.ROW
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00060"))); // 작성자
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
				 		builder.write(StringUtil.checkNullToBlank(piItemInfo.get("Name"))); // 작성자 : 내용
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(piAttrNameMap.get("AT00022")));  // PI 담당자
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(piAttrHtmlMap.get("AT00022")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(piAttrMap.get("AT00022")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(piAttrMap.get("AT00022")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piAttrMap.get("AT00022"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(piAttrMap.get("AT00022"))); // Due date : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 4.ROW
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00013"))); // 생성일
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
				 		builder.write(StringUtil.checkNullToBlank(piItemInfo.get("CreateDT"))); // 생성일 : 내용
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00070")));  // 수정일
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
			 	 		builder.write(StringUtil.checkNullToBlank(piItemInfo.get("LastUpdated"))); // 수정일 : 내용
			 	 		builder.endRow();
			 	 		//==================================================================================================
			 	 				
			 	 		// 5.ROW : 오너조직, Owner
		 	 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(piAttrNameMap.get("AT00033"))); // 오너조직
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(piAttrHtmlMap.get("AT00033")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(piAttrMap.get("AT00033")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(piAttrMap.get("AT00033")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piAttrMap.get("AT00033"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(piAttrMap.get("AT00033"))); // 오너조직 : 내용
			 	 		}
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(piAttrNameMap.get("AT00012"))); // 오너
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(piAttrHtmlMap.get("AT00012")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(piAttrMap.get("AT00012")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(piAttrMap.get("AT00012")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(piAttrMap.get("AT00012"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(piAttrMap.get("AT00012"))); // 오너
			 	 		}
			 	 		builder.endRow();
			 	 		//==================================================================================================
			 	 		
			 	 		builder.endTable().setAllowAutoFit(false);
			 	 		builder.writeln();
					  	
					  	
					}			
				//} 
				// PI 과제기본정보 END */
				//==================================================================================================
				if (totalList.size() > 0) { 
			 	for (int index = 0; totalList.size() > index ; index++) {
			 		
			 		if (totalList.size() != 1) {
			 			builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
			 		}
			 		
			 		Map totalMap = (Map)totalList.get(index);
			 		
			 		List prcList = (List)totalMap.get("prcList");
			 		Map rowPrcData =  (HashMap) prcList.get(0); 
			 		List activityList = (List)totalMap.get("activityList");
			 		List elementList = (List)totalMap.get("elementList");
			 		List cnitemList = (List)totalMap.get("cnitemList");
			 		List dimResultList = (List)totalMap.get("dimResultList");
			 		List ruleSetList = (List)totalMap.get("ruleSetList");
			 		List kpiList = (List)totalMap.get("kpiList");
			 		List requirementList = (List)totalMap.get("requirementList");
			 		List attachFileList = (List)totalMap.get("attachFileList");
			 		List toCheckList = (List)totalMap.get("toCheckList");
			 		Map attrMap = (Map)totalMap.get("attrMap");
			 		Map attrNameMap = (Map)totalMap.get("attrNameMap");
			 		Map attrHtmlMap = (Map)totalMap.get("attrHtmlMap");
			 		Map modelMap = (Map)totalMap.get("modelMap");
			 		Map attrRsNameMap = (Map)totalMap.get("attrRsNameMap");
			 		Map attrRsHtmlMap = (Map)totalMap.get("attrRsHtmlMap");
			 		 		
			 		currentSection = builder.getCurrentSection();
			 	    pageSetup = currentSection.getPageSetup();
			 	    
			 	    pageSetup.setDifferentFirstPageHeaderFooter(false);
			 	    pageSetup.setHeaderDistance(25);
			 	    builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
			 	    
			 	    //==================================================================================================
			 		// NEW 머릿글 : START
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
					name = StringUtil.checkNullToBlank(rowPrcData.get("ItemName")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			 	   	name = name.replace("&#10;", " ");
			 	   	name = name.replace("&#xa;", "");
			 	    name = name.replace("&nbsp;", " ");
			 	    builder.write("Process definition report"); 
			 	  //  builder.write(rowPrcData.get("Identifier") + " "+ name);  	
					
					builder.endRow();
					
					// Set features for the other rows and cells.
					builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					builder.getFont().setSize(10);
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
					builder.write(rowPrcData.get("Identifier") + " "+ name);  
		    		
					builder.insertCell();
				   	builder.getCellFormat().setWidth(45);builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
					builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					builder.write(StringUtil.checkNullToBlank(rowPrcData.get("Path")));
					builder.endRow();
					
					builder.endTable().setAllowAutoFit(false);
					 // 타이틀과 내용 사이 간격
			 	    builder.insertHtml("<hr size=4 color='silver'/>");
			 	 	// 머릿말 : END
			 	 	builder.moveToDocumentEnd();
			 	  	//==================================================================================================
			 	  		
			 		// 아이템 속성 		
			 		if ("N".equals(onlyMap)) {
			 			// 프로세스 기본 정보 Title
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("1. " + String.valueOf(menu.get("LN00005")));
						
			 			builder.startTable();
			 	 		
			 	 	    builder.getFont().setName(defaultFont);
			 	 	    builder.getFont().setColor(Color.BLACK);
			 	 	    builder.getFont().setSize(10);
			 	 		
			 	 		// Make the header row.
			 	 		builder.getRowFormat().clearFormatting();
			 	 		builder.getCellFormat().clearFormatting();
			 	 		builder.getRowFormat().setHeight(250.0);
			 	 		builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			 	 		
			 	 	 	// 1.ROW : 개요
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00003")));  // 개요
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(attrMap.get("AT00003")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(attrMap.get("AT00003")).replace("/upload", logoPath));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(attrMap.get("AT00003")).replace("/upload", logoPath)+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00003"))); // 개요 : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		// 2.ROW : 클래스,SystemType
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getRowFormat().setHeight(30.0);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00016")));  // 계층
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		
		 	 			builder.getFont().setBold(false);
		 	 			builder.write(StringUtil.checkNullToBlank(rowPrcData.get("ClassName"))); // 계층 : 내용
			 	 		//==================================================================================================	
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00009")));  // 주기
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		 	 			builder.getFont().setBold(false);
		 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00009"))); // 주기
			 	 		//==================================================================================================	
			 	 	 	builder.endRow();	 	 		
			 	 	
			 	 		
			 	 		builder.getRowFormat().clearFormatting();
						builder.getCellFormat().clearFormatting();
						builder.getRowFormat().setHeight(30.0);
			 	 		//builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			 	 		
			 	 		// 3.ROW : Input,Output
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00015")));  // Input
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00015")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(attrMap.get("AT00015")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(attrMap.get("AT00015")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(attrMap.get("AT00015"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00015"))); // Input : 내용
			 	 		}
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00016")));  // Output
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00016")))) { // type이 HTML인 경우
			 	 			if(StringUtil.checkNullToBlank(attrMap.get("AT00016")).contains("font-family")){
			 	 				builder.insertHtml(StringUtil.checkNullToBlank(attrMap.get("AT00016")));
			 	 			}else{
			 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(attrMap.get("AT00016"))+"</span>");
			 	 			}
			 	 		} else {
			 	 			builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00016"))); // Output : 내용
			 	 		}
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		 	 		
			 	 		// 4.ROW : 비고
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00006")));  // 비고
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(mergeCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		 	 			builder.getFont().setBold(false);
		 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00006"))); // 비고 : 내용
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 		builder.getRowFormat().clearFormatting();
						builder.getCellFormat().clearFormatting();
						builder.getRowFormat().setHeight(30.0);
			 	 		builder.getRowFormat().setHeightRule(HeightRule.AUTO); // TODO:높이 내용에 맞게 변경
			 	 		
			 	 		// 9.ROW : 조직,작성자
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getRowFormat().setHeight(30.0);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(attrNameMap.get("AT00010")));  // 조직
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		 	 			builder.getFont().setBold(false);
		 	 			builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00010"))); // 조직 : 내용
		 	 			
		 	 			builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00060")));  // 작성자
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.write(StringUtil.checkNullToBlank(rowPrcData.get("Name"))); // 작성자 : 내용
			 	 		//==================================================================================================	
			 	 		builder.endRow();
			 	 		
			 	 	
			 	 		// 10.ROW : 수정일, Progress
			 	 		builder.insertCell();
			 	 		//==================================================================================================	
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getRowFormat().setHeight(30.0);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write(String.valueOf(menu.get("LN00070"))); // 수정일
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
				 		builder.write(StringUtil.checkNullToBlank(rowPrcData.get("LastUpdated"))); // 수정일 : 내용
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
			 	 		builder.getCellFormat().setWidth(titleCellWidth);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
			 	 		builder.getFont().setBold (true);
			 	 		builder.write("Progress");  // Progress
			 	 		
			 	 		builder.insertCell();
			 	 		builder.getCellFormat().setWidth(contentCellWidth);
			 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
			 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
			 	 		builder.getFont().setBold(false);
			 	 		builder.write(StringUtil.checkNullToBlank(attrMap.get("AT00026"))); // Progress : 내용
			 	 		builder.endRow();
			 	 		//==================================================================================================
			 	 				
			 	 		// 11.ROW (Dimension 정보 만큼 행 표시)
			 	 		for(int j=0; j<dimResultList.size(); j++){
			 	 			Map dimResultMap = (Map) dimResultList.get(j);
				 	 		
			 	 			builder.insertCell();
				 	 		//==================================================================================================	
				 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
				 	 		builder.getCellFormat().setWidth(titleCellWidth);
				 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
				 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
				 	 		builder.getFont().setBold (true);
				 	 		builder.write(String.valueOf(dimResultMap.get("dimTypeName")));
				 	 		
				 	 		builder.insertCell();
				 	 		builder.getCellFormat().setWidth(mergeCellWidth);
				 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
				 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
				 	 		builder.getFont().setBold(false);
			 	 			builder.write(StringUtil.checkNullToBlank(dimResultMap.get("dimValueNames")));
				 	 		//==================================================================================================	
			 	 			builder.endRow();
			 	 		}
			 	 		
			 	 		builder.endTable().setAllowAutoFit(false);
			 	 		builder.writeln();
			 	 		
			 	 		builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
			 	 		
			 	 		// 2. 선/후행 프로세스
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("2. " + String.valueOf(menu.get("LN00178"))+"Process");
						
				 		if (elementList.size() > 0) {
				 			Map cnProcessData = new HashMap();
					 		
							builder.startTable();			
							builder.getRowFormat().clearFormatting();
							builder.getCellFormat().clearFormatting();
							
							// Make the header row.
							builder.insertCell();
							builder.getRowFormat().setHeight(20.0);
							builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
							
							// Some special features for the header row.
							builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
							builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
							builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
							builder.getFont().setSize(10);
							builder.getFont().setBold (true);
							builder.getFont().setColor(Color.BLACK);
							builder.getFont().setName(defaultFont);
							
							builder.getCellFormat().setWidth(10);
							builder.write(String.valueOf(menu.get("LN00024"))); // No

							builder.insertCell();
							builder.getCellFormat().setWidth(20);
							builder.write(String.valueOf(menu.get("LN00042"))); // 구분

							builder.insertCell();
							builder.getCellFormat().setWidth(30);
							builder.write(String.valueOf(menu.get("LN00106"))); // ID

							builder.insertCell();
							builder.getCellFormat().setWidth(60);
							builder.write(String.valueOf(menu.get("LN00028"))); // 명칭

							builder.insertCell();
							builder.getCellFormat().setWidth(120);
							builder.write(String.valueOf(menu.get("LN00035"))); // 개요
							builder.endRow();	
							
							// Set features for the other rows and cells.
							builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
							builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
							
							// Reset height and define a different height rule for table body
							builder.getRowFormat().setHeight(30.0);
							builder.getRowFormat().setHeightRule(HeightRule.AUTO);
							
							for(int j=0; j<elementList.size(); j++){
								cnProcessData = (HashMap) elementList.get(j);
							    
						    	builder.insertCell();
							    if( j==0){
							    	// Reset font formatting.
							    	builder.getFont().setBold(false);
							    }
							    
								String itemName = StringUtil.checkNullToBlank(cnProcessData.get("Name")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
								itemName = itemName.replace("&#10;", " ");
								itemName = itemName.replace("&#xa;", "");
								itemName = itemName.replace("&nbsp;", " ");
								String processInfo = StringUtil.checkNullToBlank(cnProcessData.get("Description")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
								processInfo = processInfo.replace("&#10;", " ");
								processInfo = processInfo.replace("&#xa;", "");
								processInfo = processInfo.replace("&nbsp;", " ");
								builder.getCellFormat().setWidth(10);
								builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
							   	builder.write(StringUtil.checkNullToBlank(cnProcessData.get("RNUM")));	
								
							   	builder.insertCell();
							   	builder.getCellFormat().setWidth(20);
								builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
								builder.write(StringUtil.checkNullToBlank(cnProcessData.get("KBN")));				
								builder.insertCell();
							   	builder.getCellFormat().setWidth(30);
								builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
								builder.write(StringUtil.checkNullToBlank(cnProcessData.get("ID")));				
								builder.insertCell();
							   	builder.getCellFormat().setWidth(60);
								builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
								builder.write(itemName);				
								builder.insertCell();
								builder.getCellFormat().setWidth(120);
								builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
								builder.write(processInfo);
								
								builder.endRow();
							}	
							
							builder.endTable().setAllowAutoFit(false);	
				 		}
				 		
				 		//==================================================================================================
				 		// 3. PI 과제
				 		builder.insertHtml("<br>");
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("3. PI 과제");
						
						if (requirementList.size() > 0) {
							Map rowCnData = new HashMap();
							
							builder.getFont().setSize(10);
							//builder.getFont().setBold (true);
							builder.getFont().setColor(Color.BLACK);
							builder.getFont().setName(defaultFont);
							
							for(int j=0; j<requirementList.size(); j++){						
								rowCnData = (HashMap) requirementList.get(j);
								List rList = (List)rowCnData.get("resultSubList");
								builder.startTable();
								builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								
								// 1.ROW
								builder.insertCell();
								builder.getRowFormat().setHeight(40.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
								
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(menu.get("LN00106"))); // ID
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
						 		builder.write(StringUtil.checkNullToBlank(rowCnData.get("fromItemIdentifier"))); // ID : 내용
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(menu.get("LN00028")));  // 명칭
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
				 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("fromItemName"))); // 명칭 : 내용
					 	 		//==================================================================================================	
					 	 		builder.endRow();
				 	 			
					 	 		builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								builder.getRowFormat().setHeight(50.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				 	 			
					 	 		// 2.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00003")));  // 개요
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00003")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00003"))); // 개요 : 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
								
					 	 		// 3.ROW
					 	 		if(!rList.get(1).equals("")){
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(rList.get(1)));  
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(rList.get(3)))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rList.get(2)).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rList.get(2)).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rList.get(2)).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rList.get(2))); 
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		}
					 	 		
					 	 	    // 4.ROW
					 	 	    if(!rList.get(4).equals("")){
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(rList.get(4))); 
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(rList.get(6)))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rList.get(5)).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rList.get(5)).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rList.get(5)).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rList.get(5)));
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 	    }
							    
								builder.endTable().setAllowAutoFit(false);	
								builder.insertHtml("<br>");
							}	
							
						}
						//==================================================================================================
								
						//==================================================================================================
				 		//4. KPI
				 		if (ruleSetList.size() == 0) {
				 			builder.insertHtml("<br>");
				 		}
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("4. KPI");
						if (kpiList.size() > 0) {
							Map rowCnData = new HashMap();
							builder.startTable();
							builder.getRowFormat().clearFormatting();
							builder.getCellFormat().clearFormatting();
							
							// Set features for the other rows and cells.
							builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
							builder.getCellFormat().setWidth(100.0);
							builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
							
							// Reset height and define a different height rule for table body
							builder.getRowFormat().setHeight(30.0);
							builder.getRowFormat().setHeightRule(HeightRule.AUTO);
							for(int j=0; j<kpiList.size(); j++){
								rowCnData = (HashMap) kpiList.get(j);
								List kpiSubList = (List)rowCnData.get("resultSubList");
								// 1.ROW
								builder.insertCell();
								builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
								
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(menu.get("LN00106"))); // ID
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
						 		builder.write(StringUtil.checkNullToBlank(rowCnData.get("toItemIdentifier"))); // ID : 내용
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(menu.get("LN00028")));  // 명칭
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
				 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("toItemName"))); // 명칭 : 내용
					 	 		//==================================================================================================	
					 	 		builder.endRow();
				 	 			
					 	 		builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								builder.getRowFormat().setHeight(50.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				 	 			
					 	 		// 2.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00003")));  // 개요
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00003")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00003"))); // 개요 : 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		
					 	 		// 3.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00007")));  // 산출식
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00007")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00007")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00007")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00007")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00007"))); // IT 요구사항: 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		
								// 4.ROW
								builder.insertCell();
								builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);						
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00028")));  // 단위
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
						 		builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00028"))); // 단위 
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00009")));  //주기
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
				 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00009"))); // 주기
					 	 		//==================================================================================================	
					 	 		builder.endRow(); 
							}	 
							builder.endTable().setAllowAutoFit(false);
						} 
						//==================================================================================================	
								
				 		//==================================================================================================
				 		// 5.Rule set
				 		builder.insertHtml("<br>");
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("5. Rule");
						
						if (ruleSetList.size() > 0) {
							Map rowCnData = new HashMap();
							
							builder.getFont().setSize(10);
							//builder.getFont().setBold (true);
							builder.getFont().setColor(Color.BLACK);
							builder.getFont().setName(defaultFont);
							
							for(int j=0; j<ruleSetList.size(); j++){						
								rowCnData = (HashMap) ruleSetList.get(j);			 		
								builder.startTable();
								builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								
								// 1.ROW
								builder.insertCell();
								builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
								
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(menu.get("LN00106"))); // ID
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
						 		builder.write(StringUtil.checkNullToBlank(rowCnData.get("toItemIdentifier"))); // ID : 내용
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(menu.get("LN00028")));  // 명칭
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
				 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("toItemName"))); // 명칭 : 내용
					 	 		//==================================================================================================	
					 	 		builder.endRow();
				 	 			
					 	 		builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								builder.getRowFormat().setHeight(50.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				 	 			
								// 2.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00003")));  // 개요
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00003")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00003")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00003"))); // 개요 : 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		
					 	 		// 2.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00019")));  // 통제유형
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00019")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00019")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00019")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00019")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00019"))); //  통제유형
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		
					 	 		// 3.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrRsNameMap.get("AT00021")));  // IT 요구사항
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrRsHtmlMap.get("AT00021")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowCnData.get("AT00021")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowCnData.get("AT00021")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowCnData.get("AT00021")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowCnData.get("AT00021"))); // IT 요구사항: 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();						
								builder.endTable().setAllowAutoFit(false);	
								builder.insertHtml("<br>");
							}	
							
						} 
						//==================================================================================================
						
						
						//==================================================================================================
				 		//6. To_Check
				 		if (kpiList.size() == 0) {
				 			builder.insertHtml("<br>");
				 		}
			 	 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("6. To Check");
						
						if (toCheckList.size() > 0) {
							Map rowTchckData = new HashMap();
							for(int j=0; j<toCheckList.size(); j++){
								rowTchckData = (HashMap) toCheckList.get(j);
								if(j >0 ){builder.insertHtml("<br>");}
								builder.startTable();
								builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								
								// Set features for the other rows and cells.
								builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
								builder.getCellFormat().setWidth(100.0);
								builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
								
								// Reset height and define a different height rule for table body
								builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AUTO);		
								// 1.ROW
								builder.insertCell();
								builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);						
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(menu.get("LN00106"))); // ID
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
						 		builder.write(StringUtil.checkNullToBlank(rowTchckData.get("toItemIdentifier"))); // ID : 내용
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(menu.get("LN00028")));  // 명칭
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
				 	 			builder.write(StringUtil.checkNullToBlank(rowTchckData.get("toItemName"))); // 명칭 : 내용
					 	 		//==================================================================================================	
					 	 		builder.endRow();
				 	 			
					 	 		builder.getRowFormat().clearFormatting();
								builder.getCellFormat().clearFormatting();
								builder.getRowFormat().setHeight(50.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
				 	 			
					 	 		// 2.ROW
								builder.insertCell();
					 	 		//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrNameMap.get("AT00003")));  // 개요
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(mergeCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
					 	 			if(StringUtil.checkNullToBlank(rowTchckData.get("toItemDescription")).contains("font-family")){
					 	 				builder.insertHtml(StringUtil.checkNullToBlank(rowTchckData.get("toItemDescription")).replace("/upload", logoPath));
					 	 			}else{
					 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowTchckData.get("toItemDescription")).replace("/upload", logoPath)+"</span>");
					 	 			}
					 	 		} else {
					 	 			builder.getFont().setBold(false);
					 	 			builder.write(StringUtil.checkNullToBlank(rowTchckData.get("toItemDescription"))); // 개요 : 내용
					 	 		}
					 	 		//==================================================================================================	
					 	 		builder.endRow();
					 	 		
					 	 		builder.getRowFormat().setHeight(30.0);
								builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);							
					 	 		// 3.ROW
								builder.insertCell();					
								//==================================================================================================	
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold(true);
					 	 		builder.write(String.valueOf(attrNameMap.get("AT00062")));  // Portal 연계
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
					 	 		builder.write(StringUtil.checkNullToBlank(rowTchckData.get("AT00062")));//Potal 연계
						 		//builder.write(StringUtil.checkNullToBlank("")); 
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
					 	 		builder.getCellFormat().setWidth(titleCellWidth);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
					 	 		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
					 	 		builder.getFont().setBold (true);
					 	 		builder.write(String.valueOf(attrNameMap.get("AT00066")));  // Type
					 	 		
					 	 		builder.insertCell();
					 	 		builder.getCellFormat().setWidth(contentCellWidth);
					 	 		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
					 	 		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
					 	 		builder.getFont().setBold(false);
					 	 		builder.write(StringUtil.checkNullToBlank(rowTchckData.get("AT00066")));// Type
					 	 		//==================================================================================================	
					 	 		builder.endRow();	
				 	 			builder.endTable().setAllowAutoFit(false);	
							}	
							
						} 
				 		
			 			builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
				 		
				 		// 7.업무처리 절차 Title
				 		if (null == modelMap) {
				 			builder.insertHtml("<br>");
				 		}
				 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("7. " + String.valueOf(menu.get("LN00197")));
						
			 	 		if (null != modelMap) {
			 	 		//==================================================================================================
			 		 		//프로세스 맵
			 		 	 	builder.startTable();
			 		 	 	builder.insertCell();
			 		 	 	builder.getRowFormat().clearFormatting();
			 		 		builder.getCellFormat().clearFormatting();
			 		 		builder.getRowFormat().setHeight(20.0);
			 		 		builder.getRowFormat().setHeightRule(HeightRule.AUTO);
			 		 	 	builder.getCellFormat().setWidth(totalCellWidth);
			 		 	 	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 		 	 	
			 		 		String imgLang = "_"+StringUtil.checkNull(setMap.get("langCode"));
			 		 		String imgPath = modelImgPath+modelMap.get("ModelID")+imgLang+"."+GlobalVal.MODELING_DIGM_IMG_TYPE;
			 		 		int width = Integer.parseInt(String.valueOf(modelMap.get("Width")));
			 		 		int height = Integer.parseInt(String.valueOf(modelMap.get("Height")));
			 		 		System.out.println("ProcessMapimgPath="+imgPath);
			 		 		try{
			 		 			builder.insertHtml("<br>");
			 		 			builder.insertImage(imgPath, RelativeHorizontalPosition.PAGE, 30, RelativeVerticalPosition.PAGE,20,width,height,WrapType.INLINE);
			 		 			builder.insertHtml("<br>");
			 		 		} catch(Exception ex){}
			 		 		
			 		 		
			 		 		builder.endTable().setAllowAutoFit(false);
			 		 		
			 		 		//==================================================================================================
			 	 		}
				 		
				 		// 액티비티리스트
				 		// Build the other cells.
				 		// 액티비티리스트나 연관항목중 한건이라도 존재하면 새로운 페이지를 추가한다
				 		//if (activityList.size() > 0 || cnitemList.size() > 0) {
				 			builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
				 		//}
						
				 		// 7.액티비티 리스트 Title
				 		if (activityList.size() == 0) {
				 			builder.insertHtml("<br>");
				 		}
				 		builder.getFont().setColor(Color.DARK_GRAY);
					    builder.getFont().setSize(11);
					    builder.getFont().setBold(true);
					    builder.getFont().setName(defaultFont);
						builder.writeln("8. " + String.valueOf(menu.get("LN00151")));
						builder.insertHtml("<br>");
						if (activityList.size() > 0) {
							Map rowActData = new HashMap();	
							for(int j=0; j<activityList.size(); j++){
								rowActData = (HashMap) activityList.get(j);
								builder.getFont().setColor(Color.DARK_GRAY);
							    builder.getFont().setSize(11);
							    builder.getFont().setBold(true);
							    builder.getFont().setName(defaultFont);
							    
							    String identifier =  StringUtil.checkNullToBlank(rowActData.get("Identifier"));
							    String itemName = StringUtil.checkNullToBlank(rowActData.get("ItemName")).replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
							    itemName = itemName.replace("&#10;", " ");
								itemName = itemName.replace("&#xa;", "");
								itemName = itemName.replace("&nbsp;", " ");
							    builder.writeln(identifier+" "+itemName); //명칭
							    builder.insertHtml("<br>");
								builder.writeln("[GuideLine]");// GuideLine
								builder.getFont().setBold(false);
								builder.getFont().setSize(10);
				 	 			if(StringUtil.checkNullToBlank(rowActData.get("AT00008")).contains("font-family")){	
									builder.insertHtml(StringUtil.checkNullToBlank(rowActData.get("AT00008")).replace("/upload", logoPath));
				 	 			}else{
				 	 				builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowActData.get("AT00008")).replace("/upload", logoPath)+"</span>");
				 	 			}			 	 
								builder.insertHtml("<br>");
								builder.insertHtml("<br>");
								
								//////// L5 RuleSet : CN00107 ////////////////////////////////////////////////////////////////////////////////////////////////
								builder.getFont().setBold(true);
								builder.getFont().setSize(11);
								builder.writeln("[Rule]");
								builder.getFont().setBold(true);
								builder.getFont().setSize(10);
								List actRuleSetList = (List)rowActData.get("actRuleSetList");
								if (actRuleSetList.size() > 0) {
									Map rowActRuleSetData = new HashMap();
									for(int m=0; m<actRuleSetList.size(); m++){
										rowActRuleSetData = (HashMap)actRuleSetList.get(m);							
										builder.writeln(StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemIdentifier")) +" "+ StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemName"))); // toItemDescription
									
										builder.startTable();
										builder.getRowFormat().clearFormatting();
										builder.getCellFormat().clearFormatting();
										
										// Set features for the other rows and cells.
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getCellFormat().setWidth(100.0);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										
										// Reset height and define a different height rule for table body
										builder.getRowFormat().setHeight(30.0);
										builder.getRowFormat().setHeightRule(HeightRule.AUTO);		
										// 1.ROW														
										builder.getRowFormat().clearFormatting();
										builder.getCellFormat().clearFormatting();
										builder.getRowFormat().setHeight(50.0);
										builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
										
										// 2.ROW
										builder.insertCell();
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold (true);
										builder.write(String.valueOf(attrNameMap.get("AT00003")));  // 개요
										
										builder.insertCell();
										builder.getCellFormat().setWidth(mergeCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
											if(StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemDescription")).contains("font-family")){
												builder.insertHtml(StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemDescription")).replace("/upload", logoPath));
											}else{
												builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemDescription")).replace("/upload", logoPath)+"</span>");
											}
										} else {
											builder.getFont().setBold(false);
											builder.write(StringUtil.checkNullToBlank(rowActRuleSetData.get("toItemDescription"))); // 개요 : 내용
										}
										//==================================================================================================	
										builder.endRow();
										
										builder.getRowFormat().setHeight(30.0);
										builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);							
										// 3.ROW
										builder.insertCell();					
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold(true);
										builder.write(String.valueOf(attrNameMap.get("AT00019")));  // 통제유형
										
										builder.insertCell();
										builder.getCellFormat().setWidth(mergeCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										builder.getFont().setBold(false);
										builder.write(StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00019")));//통제유형
									
										//==================================================================================================	
										builder.endRow();	
										
										//4.ROW : 시스템요구사항
										builder.insertCell();					
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold(true);
										builder.write(String.valueOf(attrNameMap.get("AT00021")));  // 시스템요구사항
										
										builder.insertCell();
										builder.getCellFormat().setWidth(mergeCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										builder.getFont().setBold(false);
																		
										if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00021")))) { // type이 HTML인 경우
											if(StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00021")).contains("font-family")){
												builder.insertHtml(StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00021")).replace("/upload", logoPath));
											}else{
												builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00021")).replace("/upload", logoPath)+"</span>");
											}
										} else {
											builder.getFont().setBold(false);
											builder.write(StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00021"))); // 시스템요구사항 : 내용
										}
										
										//==================================================================================================	
										builder.endRow();	
										
										//5.ROW : 비고 
										builder.insertCell();					
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold(true);
										builder.write(String.valueOf(attrNameMap.get("AT00006")));  // 비고
										
										builder.insertCell();
										builder.getCellFormat().setWidth(mergeCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										builder.getFont().setBold(false);
										builder.write(StringUtil.checkNullToBlank(rowActRuleSetData.get("AT00006"))); // 비고
									
										//==================================================================================================	
										builder.endRow();	
										
										builder.endTable().setAllowAutoFit(false);
										
										if(actRuleSetList.size() > m+1) {builder.insertHtml("<br>");}
									
									}
								}			
								builder.insertHtml("<br>");
								
								////////L5 TOCHECK////////////////////////////////////////////////////////////////////////////////////////////////
								builder.getFont().setBold(true);
								builder.getFont().setSize(11);
								builder.writeln("[To Check]"); 
								builder.getFont().setBold(true);
								builder.getFont().setSize(10);
								List actToCheckList = (List)rowActData.get("actToCheckList"); 
								if (actToCheckList.size() > 0) {
									Map rowActToCheckData = new HashMap();
									
									for(int m=0; m<actToCheckList.size(); m++){
										rowActToCheckData = (HashMap)actToCheckList.get(m);	
										builder.writeln(StringUtil.checkNullToBlank(rowActToCheckData.get("toItemIdentifier")) +" "+ StringUtil.checkNullToBlank(rowActToCheckData.get("toItemName"))); // toItemDescription
										builder.startTable();
										builder.getRowFormat().clearFormatting();
										builder.getCellFormat().clearFormatting();
										
										// Set features for the other rows and cells.
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getCellFormat().setWidth(100.0);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										
										// Reset height and define a different height rule for table body
										builder.getRowFormat().setHeight(30.0);
										builder.getRowFormat().setHeightRule(HeightRule.AUTO);		
										// 1.ROW					
										
										builder.getRowFormat().clearFormatting();
										builder.getCellFormat().clearFormatting();
										builder.getRowFormat().setHeight(50.0);
										builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
										
										// 2.ROW
										builder.insertCell();
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold (true);
										builder.write(String.valueOf(attrNameMap.get("AT00003")));  // 개요
										
										builder.insertCell();
										builder.getCellFormat().setWidth(mergeCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										if ("1".equals(StringUtil.checkNullToBlank(attrHtmlMap.get("AT00003")))) { // type이 HTML인 경우
											if(StringUtil.checkNullToBlank(rowActToCheckData.get("toItemDescription")).contains("font-family")){
												builder.insertHtml(StringUtil.checkNullToBlank(rowActToCheckData.get("toItemDescription")).replace("/upload", logoPath));
											}else{
												builder.insertHtml(fontFamilyHtml+StringUtil.checkNullToBlank(rowActToCheckData.get("toItemDescription")).replace("/upload", logoPath)+"</span>");
											}
										} else {
											builder.getFont().setBold(false);
											builder.write(StringUtil.checkNullToBlank(rowActToCheckData.get("toItemDescription"))); // 개요 : 내용
										}
										//==================================================================================================	
										builder.endRow();
										
										builder.getRowFormat().setHeight(30.0);
										builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);							
										// 3.ROW
										builder.insertCell();					
										//==================================================================================================	
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold(true);
										builder.write(String.valueOf(attrNameMap.get("AT00062")));  // Portal 연계
										
										builder.insertCell();
										builder.getCellFormat().setWidth(contentCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										builder.getFont().setBold(false);
										builder.write(StringUtil.checkNullToBlank(rowActToCheckData.get("AT00062")));//Potal 연계
										//builder.write(StringUtil.checkNullToBlank("")); 
										
										builder.insertCell();
										builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(179, 179, 179));
										builder.getCellFormat().setWidth(titleCellWidth);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
										builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
										builder.getFont().setBold (true);
										builder.write(String.valueOf(attrNameMap.get("AT00066")));  // Type
										
										builder.insertCell();
										builder.getCellFormat().setWidth(contentCellWidth);
										builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
										builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
										builder.getFont().setBold(false);
										builder.write(StringUtil.checkNullToBlank(rowActToCheckData.get("AT00066")));// Type
										//==================================================================================================	
										builder.endRow();	
										builder.endTable().setAllowAutoFit(false);
										
										if(actToCheckList.size() > m+1) {builder.insertHtml("<br>");}
									}
								}						
								builder.insertHtml("<br>");
								
								////////L5 ROLE  ////////////////////////////////////////////////////////////////////////////////////////////////
								builder.getFont().setBold(true);
								builder.getFont().setSize(11);
								builder.writeln("[Role]"); 				
								builder.getFont().setBold(true);
								builder.getFont().setSize(10);
								List actRoleList = (List)rowActData.get("actRoleList"); 
								if (actRoleList.size() > 0) {
									Map rowActRoleData = new HashMap();
									
									for(int m=0; m<actRoleList.size(); m++){
										rowActRoleData = (HashMap)actRoleList.get(m);	
										builder.writeln(StringUtil.checkNullToBlank(rowActRoleData.get("fromItemIdentifier")) +" "+ StringUtil.checkNullToBlank(rowActRoleData.get("fromItemName"))); // toItemDescription
									//	builder.startTable();
									//	builder.getRowFormat().clearFormatting();
									//	builder.getCellFormat().clearFormatting();								
									
										
										if(actRoleList.size() > m+1) {builder.insertHtml("<br>");}
									}
								}		
								
								builder.insertHtml("<br>");
							
								builder.getFont().setBold(true);
								builder.getFont().setSize(11);
								builder.writeln("[System Type]");
								builder.getFont().setSize(10);
								builder.getFont().setBold(false);
								builder.writeln(StringUtil.checkNullToBlank(rowActData.get("AT00037"))); // SystemType
								builder.insertHtml("<br>");
								
								builder.getFont().setBold(true);
								builder.getFont().setSize(11);
								builder.writeln("[T-Code]");
								builder.getFont().setSize(10);
								builder.getFont().setBold(false);
								builder.writeln(StringUtil.checkNullToBlank(rowActData.get("AT00014"))); // 화면코드
								builder.insertHtml("<br>");
							}
						}
						//==================================================================================================
						
				 	} else {
				 		if (null != modelMap) {
				 	 		//==================================================================================================
			 		 		//프로세스 맵
			 		 	 	builder.startTable();
			 		 	    
			 		 	 	builder.insertCell();
			 		 	 	builder.getRowFormat().clearFormatting();
			 		 		builder.getCellFormat().clearFormatting();
			 		 		builder.getRowFormat().setHeight(20.0);
			 		 		builder.getRowFormat().setHeightRule(HeightRule.AUTO);
			 		 	 	builder.getCellFormat().setWidth(totalCellWidth);
			 		 	 	builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
			 		 	 	
			 		 		String imgLang = "_"+StringUtil.checkNull(setMap.get("langCode"));
			 		 		String imgPath = modelImgPath+modelMap.get("ModelID")+imgLang+"."+GlobalVal.MODELING_DIGM_IMG_TYPE;
			 		 		int width = Integer.parseInt(String.valueOf(modelMap.get("Width")));
			 		 		int height = Integer.parseInt(String.valueOf(modelMap.get("Height")));
			 		 		System.out.println("ProcessimgPath="+imgPath);
			 		 		try{
			 		 			builder.insertHtml("<br>");
			 		 			builder.insertImage(imgPath, RelativeHorizontalPosition.PAGE, 30, RelativeVerticalPosition.PAGE,20,width,height,WrapType.INLINE);
			 		 			builder.insertHtml("<br>");
			 		 		} catch(Exception ex){}
			 		 		
			 		 		
			 		 		builder.endTable().setAllowAutoFit(false);
			 		 		
			 		 		//==================================================================================================
			 	 		}
				 	}
		 	 	
			 	} 
			}
			}
			
			String extLangCode = StringUtil.checkNull(commandMap.get("extLangCode"));
			
			setMap.put("languageID", commandMap.get("sessionCurrLangType"));			
			String identifier = StringUtil.checkNull(commandMap.get("identifier"));
			//String filePath = "C://TEMP//" ;
			String filePath = StringUtil.checkNull(commandMap.get("filePath"));
			if(filePath.equals("") || filePath == null){
				filePath = "D://OLMFILE//document/FLTP001/";
			}
			File dirFile = new File(filePath); if(!dirFile.exists()){dirFile.mkdirs();} 
		    String fileName = identifier+"."+itemNameOfFileNm+"(Process)"+"_"+extLangCode+".docx";
		   // String downFile = FileUtil.FILE_EXPORT_DIR + fileName;	
		    String downFile = filePath + fileName;	
			doc.save(downFile);
			
			// file DRM 적용			
			String useDRM = StringUtil.checkNull(GlobalVal.USE_DRM);
			String useDownDRM = StringUtil.checkNull(GlobalVal.DRM_DOWN_USE);
			if(!"".equals(useDRM) && !"N".equals(useDownDRM)){
				HashMap drmInfoMap = new HashMap();			
				String userID = StringUtil.checkNull(commandMap.get("sessionUserId"));
				String userName = StringUtil.checkNull(commandMap.get("sessionUserNm"));
				String teamID = StringUtil.checkNull(commandMap.get("sessionTeamId"));
				String teamName = StringUtil.checkNull(commandMap.get("sessionTeamName"));
				
				drmInfoMap.put("userID", userID);
				drmInfoMap.put("userName", userName);
				drmInfoMap.put("teamID", teamID);
				drmInfoMap.put("teamName", teamName);
				drmInfoMap.put("orgFileName", fileName);
				drmInfoMap.put("downFile", downFile);
				drmInfoMap.put("filePath", filePath);			
				drmInfoMap.put("funcType", "report");
				returnValue = DRMUtil.drmMgt(drmInfoMap); // 암호화 
			}
			
			returnValue = "success";
			
		} catch(Exception e){
			System.out.println(e.toString());
		}	
		return returnValue;
	}
	
}
