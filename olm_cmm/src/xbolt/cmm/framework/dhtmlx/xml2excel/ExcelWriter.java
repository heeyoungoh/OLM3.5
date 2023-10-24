package xbolt.cmm.framework.dhtmlx.xml2excel;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import xbolt.cmm.framework.val.GlobalVal;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.*;


import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelWriter extends BaseWriter {
	private WritableWorkbook wb;
	private XSSFWorkbook wb2;
	private WritableSheet sheet;
	private XSSFSheet sheet2;
	private ExcelColumn[][] cols;
	private int colsNumber = 0;
	private ExcelXmlParser parser;
	private GlobalVal GlobalVal;
	
	public int headerOffset = 0;
	public int scale = 6;
	public String pathToImgs = "";//optional, physical path
	public int fontSize = 10;

	String bgColor = "";
	String lineColor = "";
	String headerTextColor = "";
	String scaleOneColor = "";
	String scaleTwoColor = "";
	String gridTextColor = "";
	String watermarkTextColor = "";

	private int cols_stat;
	private int rows_stat;
	RGBColor colors;
	private String watermark = null;
	
	public void generate(String xml, HttpServletResponse resp){
		parser = new ExcelXmlParser();
		try {
			parser.setXML(xml);
			createExcelxlsx(resp);
			setColorProfileXlsx();
			headerPrintXLSX(parser);
			rowsPrintXlsx(parser,resp);
			footerPrintXlsx(parser);
			insertHeaderXlsx(parser,resp);
			insertFooterXlsx(parser,resp);
			watermarkPrintXlsx(parser);
			outputExcelXlsx(resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void createExcel(HttpServletResponse resp) throws IOException {
		/* Save generated excel to file.
		 * Can be useful for debug output.
		 * */
		/*
		FileOutputStream fos = new FileOutputStream("d:/test.xls");
		wb = Workbook.createWorkbook(fos);
		*/
		wb = Workbook.createWorkbook(resp.getOutputStream());
		sheet = wb.createSheet("First Sheet", 0);
		colors = new RGBColor();
		
	}
	

	private void createExcelxlsx(HttpServletResponse resp) throws IOException {
		/* Save generated excel to file.
		 * Can be useful for debug output.
		 * */
		/*
		FileOutputStream fos = new FileOutputStream("d:/test.xls");
		wb = Workbook.createWorkbook(fos); resp.getOutputStream()
		*/
		wb2 = new XSSFWorkbook();
		sheet2 = wb2.createSheet("First Sheet");
		colors = new RGBColor();
	}


	private void outputExcel(HttpServletResponse resp) throws IOException, WriteException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		long date = System.currentTimeMillis();
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=grid_"+formatter.format(date)+".xls");
		resp.setHeader("Cache-Control", "max-age=0");
		wb.write();
		wb.close();
	}
	
	private void outputExcelXlsx(HttpServletResponse resp) throws IOException, WriteException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		long date = System.currentTimeMillis();
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=grid_"+formatter.format(date)+".xlsx");
		resp.setHeader("Cache-Control", "max-age=0");
		
		//FileOutputStream fileOut = null;
	    //fileOut = new FileOutputStream(GlobalVal.FILE_EXPORT_DIR+"grid_"+formatter.format(date)+".xlsx");
	    wb2.write(resp.getOutputStream());
	    //fileOut.close();
		   
	}


	private void headerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("head");
		
		int widths[] = parser.getWidths();
		this.cols_stat = widths.length;
		
		int sumWidth = 0;
		for (int i = 0; i < widths.length; i++) {
			sumWidth += widths[i];
		}
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				sheet.setRowView(i, 450);
				sheet.getSettings().setVerticalFreeze(i + 1);
				for (int j = 0; j < cols[i].length; j++) {
					sheet.setColumnView(j, widths[j]/scale);
					WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize - 1, WritableFont.BOLD);
					font.setColour(colors.getColor(headerTextColor, wb));
					WritableCellFormat f = new WritableCellFormat (font);
					f.setBackground(colors.getColor(bgColor, wb));
					f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
					f.setVerticalAlignment(VerticalAlignment.CENTRE);
	
					f.setAlignment(Alignment.CENTRE);
					String name = cols[i][j].getName();
					Label label = new Label(j, i, name, f);
					sheet.addCell(label);
					colsNumber = j;
				}
			}
			headerOffset = cols.length;
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					if (cspan > 0) {
						sheet.mergeCells(j, i, j + cspan - 1, i);
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet.mergeCells(j, i, j, i + rspan - 1);
					}
				}
			}
		}
	}

	private void headerPrintXLSX(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("head");
		
		int widths[] = parser.getWidths();
		this.cols_stat = widths.length;
		
		int sumWidth = 0;
		for (int i = 0; i < widths.length; i++) {
			sumWidth += widths[i];
		}
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				XSSFRow row = sheet2.createRow(i);
				for (int j = 0; j < cols[i].length; j++) {
					sheet2.setColumnWidth((short)j, (short)widths[j]*40);
					XSSFCellStyle style = wb2.createCellStyle();
					XSSFCell f = row.createCell(j);
					XSSFFont font = wb2.createFont();
					font.setFontHeightInPoints((short)fontSize);
					font.setFontName("ARIAL");
					font.setBold(true);
					font.setColor(IndexedColors.BLACK.index);
					style.setFont(font);
					
					style.setFillForegroundColor(new XSSFColor(Color.decode("#"+bgColor)));
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
					style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					style.setBorderRight(XSSFCellStyle.BORDER_THIN);
					style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					style.setBottomBorderColor(new XSSFColor(Color.decode("#"+lineColor)));
					style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
					style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

					String name = cols[i][j].getName();
					f.setCellStyle(style);
					f.setCellValue(name);
					//sheet2.autoSizeColumn(j);
					
					colsNumber = j;
				}
			}
			headerOffset = cols.length;
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					
					if (cspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(i, i, j, j + cspan - 1) );
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(i, i + rspan - 1, j, j) );
					}
				}
			}
		}
	}

	private void footerPrint(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("foot");
		if (cols == null) return;
		if (parser.getWithoutHeader() == false) {
			for (int i = 0; i < cols.length; i++) {
				sheet.setRowView(i + headerOffset, 450);
				for (int j = 0; j < cols[i].length; j++) {
					WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
					font.setColour(colors.getColor(headerTextColor, wb));
					WritableCellFormat f = new WritableCellFormat (font);
					f.setBackground(colors.getColor(bgColor, wb));
					f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
					f.setVerticalAlignment(VerticalAlignment.CENTRE);

					f.setAlignment(Alignment.CENTRE);
					String name = cols[i][j].getName();
					Label label = new Label(j, i + headerOffset, name, f);
					sheet.addCell(label);
				}
			}
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					if (cspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j + cspan - 1, headerOffset + i);
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet.mergeCells(j, headerOffset + i, j, headerOffset + i + rspan - 1);
					}
				}
			}
		}
		headerOffset += cols.length;
	}

	private void footerPrintXlsx(ExcelXmlParser parser) throws RowsExceededException, WriteException, IOException {
		cols = parser.getColumnsInfo("foot");
		if (cols == null) return;
		if (parser.getWithoutHeader() == false) {
			XSSFCellStyle style = wb2.createCellStyle();
			for (int i = 0; i < cols.length; i++) {
				XSSFRow row = sheet2.createRow(i+headerOffset);
				for (int j = 0; j < cols[i].length; j++) {
					XSSFCell f = row.createCell(j);
					XSSFFont font = wb2.createFont();
					font.setFontHeightInPoints((short)fontSize);
					font.setFontName("ARIAL");
					font.setBold(true);
					font.setColor(IndexedColors.BLACK.index);
					style.setFont(font);
					
					style.setFillForegroundColor(new XSSFColor(Color.decode("#"+bgColor)));
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
					style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					style.setBorderRight(XSSFCellStyle.BORDER_THIN);
					style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					style.setBottomBorderColor(new XSSFColor(Color.decode("#"+lineColor)));
					style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
					style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					String name = cols[i][j].getName();
					f.setCellValue(name);
					f.setCellStyle(style);
				}
			}
			for (int i = 0; i < cols.length; i++) {
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					
					if (cspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i, j, j + cspan - 1) );
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i + rspan - 1, j, j) );
					}
				}
			}
		}
		headerOffset += cols.length;
	}

	private void watermarkPrintXlsx(ExcelXmlParser parser) throws WriteException {
		if (watermark == null) return;

		XSSFCellStyle style = wb2.createCellStyle();
		XSSFFont font = wb2.createFont();
		font.setFontHeightInPoints((short)fontSize);
		font.setFontName("ARIAL");
		font.setBold(true);
		font.setColor(new XSSFColor(Color.decode("#"+watermarkTextColor)));
		style.setFont(font);

		style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(new XSSFColor(Color.decode("#"+lineColor)));

		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		XSSFRow row = sheet2.createRow(sheet2.getLastRowNum()+1);

		XSSFCell f = row.createCell(0);
		f.setCellStyle(style);
		sheet2.addMergedRegion( new CellRangeAddress(headerOffset, headerOffset,0,colsNumber) );
	}

	private void watermarkPrint(ExcelXmlParser parser) throws WriteException {
		if (watermark == null) return;
		
		WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
		font.setColour(colors.getColor(watermarkTextColor, wb));
		WritableCellFormat f = new WritableCellFormat (font);
		f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
		f.setVerticalAlignment(VerticalAlignment.CENTRE);

		f.setAlignment(Alignment.CENTRE);
		Label label = new Label(0, headerOffset, watermark , f);
		sheet.addCell(label);
		sheet.mergeCells(0, headerOffset, colsNumber, headerOffset);
	}

	private void rowsPrint(ExcelXmlParser parser, HttpServletResponse resp) throws WriteException, IOException {
		//do we really need them?
		ExcelRow[] rows = parser.getGridContent();
		if (rows == null) return;
		cols = parser.getColumnsInfo("body");
		this.rows_stat = rows.length;
		
		for (int i = 0; i < rows.length; i++) {
			ExcelCell[] cells = rows[i].getCells();
			sheet.setRowView(i + headerOffset, 400);
			for (int j = 0; j < cells.length; j++) {
				// sets cell font
				WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize, (cells[j].getBold()) ? WritableFont.BOLD : WritableFont.NO_BOLD, (cells[j].getItalic()) ? true : false);
				if ((!cells[j].getTextColor().equals(""))&&(parser.getProfile().equals("full_color")))
					font.setColour(colors.getColor(cells[j].getTextColor(), wb));
				else
					font.setColour(colors.getColor(gridTextColor, wb));
				WritableCellFormat f = new WritableCellFormat (font);

				// sets cell background color
				if ((!cells[j].getBgColor().equals(""))&&(parser.getProfile().equals("full_color"))) {
					Colour col = colors.getColor(cells[j].getBgColor(), wb);
					f.setBackground(col);
				} else {
					Colour bg;
					if (i%2 == 1) {
						bg = colors.getColor(scaleTwoColor, wb);
						
					} else {
						bg = colors.getColor(scaleOneColor, wb);
					}
					f.setBackground(bg);
				}

				f.setBorder(Border.ALL, BorderLineStyle.THIN, colors.getColor(lineColor, wb));
				f.setVerticalAlignment(VerticalAlignment.CENTRE);

				String al = cells[j].getAlign();
				if (al == "")
					al = cols[0][j].getAlign();
				if (al.equalsIgnoreCase("left")) {
					f.setAlignment(Alignment.LEFT);
				} else {
					if (al.equalsIgnoreCase("right")) {
						f.setAlignment(Alignment.RIGHT);
					} else {
						f.setAlignment(Alignment.CENTRE);
					}
				}
				try {
					double name = Double.parseDouble(cells[j].getValue());
					Number label = new Number(j, i + headerOffset, name, f);
					sheet.addCell(label);
				} catch (Exception e) {
					String name = cells[j].getValue();
					Label label = new Label(j, i + headerOffset, name, f);
					sheet.addCell(label);
				}
			}
		}
		
		for (int i = 0; i < cols.length; i++) {
			for (int j = 0; j < cols[i].length; j++) {
				int cspan = cols[i][j].getColspan();
				if (cspan > 0) {
					sheet.mergeCells(j, headerOffset + i, j + cspan - 1, headerOffset + i);
				}
				int rspan = cols[i][j].getRowspan();
				if (rspan > 0) {
					sheet.mergeCells(j, headerOffset + i, j, headerOffset + i + rspan - 1);
				}
			}
		}
		headerOffset += rows.length;
	}


	private void rowsPrintXlsx(ExcelXmlParser parser, HttpServletResponse resp) throws WriteException, IOException {
		//do we really need them?
		ExcelRow[] rows = parser.getGridContent();
		if (rows == null) return;
		cols = parser.getColumnsInfo("body");
		this.rows_stat = rows.length;

		for (int i = 0; i < rows.length; i++) {
			ExcelCell[] cells = rows[i].getCells();
			XSSFRow row = sheet2.createRow(i+headerOffset);
			XSSFCellStyle style = wb2.createCellStyle();
			//sheet.setRowView(i + headerOffset, 400);
			for (int j = 0; j < cells.length; j++) {
				// sets cell font
				XSSFFont font = wb2.createFont();
				font.setFontHeightInPoints((short)fontSize);
				font.setFontName("ARIAL");
				font.setBold(cells[j].getBold() ? true : false);
				font.setItalic(cells[j].getItalic() ? true : false);

				if ((!cells[j].getTextColor().equals(""))&&(parser.getProfile().equals("full_color")))
					font.setColor(new XSSFColor(Color.decode("#"+cells[j].getTextColor())));
				else
					font.setColor(new XSSFColor(Color.decode("#"+gridTextColor)));
				
				style.setFont(font);

				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

				// sets cell background color
				if ((!cells[j].getBgColor().equals(""))&&(parser.getProfile().equals("full_color"))) {

					style.setFillForegroundColor(new XSSFColor(Color.decode("#"+cells[j].getBgColor())));
				} else {
					if (i%2 == 1) {
						style.setFillForegroundColor(new XSSFColor(Color.decode("#"+scaleTwoColor)));
					} else {
						style.setFillForegroundColor(IndexedColors.WHITE.index);
					}
				}

				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setBorderTop(XSSFCellStyle.BORDER_THIN);                          
				style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				style.setBorderRight(XSSFCellStyle.BORDER_THIN);
				style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				style.setBottomBorderColor(new XSSFColor(Color.decode("#"+lineColor)));
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);


				String al = cells[j].getAlign();
				if (al == "")
					al = cols[0][j].getAlign();
				if (al.equalsIgnoreCase("left")) {
					style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
				} else {
					if (al.equalsIgnoreCase("right")) {
						style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
					} else {
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					}
				}
				try {
					double name = Double.parseDouble(cells[j].getValue());

					XSSFCell f = row.createCell(j);
					if(f != null) {
						f.setCellValue(name);
						f.setCellStyle(style);
					}
					else {
						f = row.createCell(j);
						f.setCellValue(name);
						f.setCellStyle(style);
					}
					
				} catch (Exception e) {
					String name = cells[j].getValue();
					try {
						XSSFCell f = row.createCell(j);
						if(f != null) {
							f.setCellValue(name);
							f.setCellStyle(style);
						}
						else {
							f = row.createCell(j);
							f.setCellValue(name);
							f.setCellStyle(style);
						}
					} catch (Exception e2) {
						System.out.println("erro2 =>" + e2.toString());
					}
					
				}
			}
		}
		
		for (int i = 0; i < cols.length; i++) {
			for (int j = 0; j < cols[i].length; j++) {
				int cspan = cols[i][j].getColspan();
				
				if (cspan > 0) {
					sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i, j, j + cspan - 1) );
				}
				int rspan = cols[i][j].getRowspan();
				if (rspan > 0) {
					sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i + rspan - 1, j, j) );
				}
			}
		}
		
		try {
			for (int i = 0; i < cols.length; i++) {
				XSSFRow row = sheet2.getRow(i);
				for (int j = 0; j < cols[i].length; j++) {
					int cspan = cols[i][j].getColspan();
					if (cspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i, j, j + cspan - 1) );
					}
					int rspan = cols[i][j].getRowspan();
					if (rspan > 0) {
						sheet2.addMergedRegion( new CellRangeAddress(headerOffset + i, headerOffset + i + rspan - 1, j, j) );
					}
				}
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		headerOffset += rows.length;
	}
	
	private void insertHeader(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getHeader() == true) {
			sheet.insertRow(0);
			sheet.setRowView(0, 5000);
			File imgFile = new File(pathToImgs + "/header.png");
			WritableImage img = new WritableImage(0, 0, cols[0].length, 1, imgFile);
			sheet.addImage(img);
			headerOffset++;
		}
	}
	
	private void insertHeaderXlsx(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getHeader() == true) {
			//sheet.setRowView(0, 5000);
			InputStream imgFile = new FileInputStream(pathToImgs + "/header.png");
			byte[] bytes = IOUtils.toByteArray(imgFile);
			
			int imgIndex = wb2.addPicture(bytes, wb2.PICTURE_TYPE_PNG);
			CreationHelper helper = wb2.getCreationHelper();
			Drawing drawing = sheet2.createDrawingPatriarch();
		   
		    ClientAnchor anchor = helper.createClientAnchor();
		    
		    anchor.setCol1(0);
		    anchor.setRow1(0); 
 
		    Picture pict = drawing.createPicture(anchor, imgIndex);
		    pict.resize(cols[0].length);
		   
			headerOffset++;
		}
	}
	
	private void insertFooter(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getFooter() == true) {
			sheet.setRowView(headerOffset, 5000);
			File imgFile = new File(pathToImgs + "/footer.png");
			WritableImage img = new WritableImage(0, headerOffset, cols[0].length, 1, imgFile);
			sheet.addImage(img);
		}
	}
	
	private void insertFooterXlsx(ExcelXmlParser parser, HttpServletResponse resp) throws IOException, RowsExceededException {
		if (parser.getFooter() == true) {
			//sheet.setRowView(headerOffset, 5000);
			InputStream imgFile = new FileInputStream(pathToImgs + "/footer.png");
			byte[] bytes = IOUtils.toByteArray(imgFile);
			int imgIndex = wb2.addPicture(bytes, wb2.PICTURE_TYPE_PNG);
			CreationHelper helper = wb2.getCreationHelper();
			Drawing drawing = sheet2.createDrawingPatriarch();
		   
		    ClientAnchor anchor = helper.createClientAnchor();
		    
		    anchor.setCol1(0);
		    anchor.setRow1(headerOffset); 
		    
		    Picture pict = drawing.createPicture(anchor, imgIndex);

		    pict.resize(cols[0].length);

		}
	}
	
	public int getColsStat() {
		return this.cols_stat;
	}
	
	public int getRowsStat() {
		return this.rows_stat;
	}

	private void setColorProfile() {
		String profile = parser.getProfile();
		if ((profile.equalsIgnoreCase("color"))||profile.equalsIgnoreCase("full_color")) {
			bgColor = "D1E5FE";
			lineColor = "A4BED4";
			headerTextColor = "000000";
			scaleOneColor = "FFFFFF";
			scaleTwoColor = "E3EFFF";
			gridTextColor = "000000";
			watermarkTextColor = "8b8b8b";
		} else {
			if (profile.equalsIgnoreCase("gray")) {
				bgColor = "E3E3E3";
				lineColor = "B8B8B8";
				headerTextColor = "000000";
				scaleOneColor = "FFFFFF";
				scaleTwoColor = "EDEDED";
				gridTextColor = "000000";
				watermarkTextColor = "8b8b8b";
			} else {
				bgColor = "FFFFFF";
				lineColor = "000000";
				headerTextColor = "000000";
				scaleOneColor = "FFFFFF";
				scaleTwoColor = "FFFFFF";
				gridTextColor = "000000";
				watermarkTextColor = "000000";
			}
		}
	}
	private void setColorProfileXlsx() {
		String profile = parser.getProfile();
		if ((profile.equalsIgnoreCase("color"))||profile.equalsIgnoreCase("full_color")) {
			bgColor = "D1E5FE";
			lineColor = "A4BED4";
			headerTextColor = "FFFFFF";
			scaleOneColor = "000000";
			scaleTwoColor = "E3EFFF";
			gridTextColor = "FFFFFF";
			watermarkTextColor = "8b8b8b";
		} else {
			if (profile.equalsIgnoreCase("gray")) {
				bgColor = "E3E3E3";
				lineColor = "B8B8B8";
				headerTextColor = "FFFFFF"; 
				scaleOneColor = "000000"; 
				scaleTwoColor = "EDEDED";
				gridTextColor = "FFFFFF"; 
				watermarkTextColor = "8b8b8b";
			} else {
				bgColor = "000000"; 
				lineColor = "FFFFFF"; 
				headerTextColor = "FFFFFF"; 
				scaleOneColor = "000000"; 
				scaleTwoColor = "000000"; 
				gridTextColor = "FFFFFF"; 
				watermarkTextColor = "FFFFFF"; 
			}
		}
	}
	
	public void setWatermark(String mark) {
		watermark = mark;	
	}
	
	public void setFontSize(int fontsize) {
		this.fontSize = fontsize;
	}
}
