<%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@page import="org.apache.commons.io.filefilter.HiddenFileFilter"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.ss.usermodel.WorkbookFactory"%>
<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%

	String fileName = request.getAttribute("fileName").toString();

	fileName = getServletContext().getRealPath("/") + fileName;

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="cmm/css/content.css"/>
</head>
<body style="height: 100%">

<form id = "fileImport" name="fileImport" action="excelImportDB.do" enctype="multipart/form-data" method="post"  style="height: 25px;">
	<input type="text" readonly="readonly" value="${request.ItemID}"/>
	<input type="file" id = "fileDlg" name="fileDlg" ></input>
	<input type="submit" id = "submit" name="submit" value="Import" ></input>
	<input type="hidden" id = "fileName" name="fileName"></input>
</form>

<form action="executeImportExcelToDB.do" method="post" style="height: 500px;" >
	<input type="hidden" id="s_itemID" name="s_itemID" value="${request.ITEM_ID}"/>
	<input type="hidden" id="option" name="option" value="${request.ARCHITECTURE_CODE}"/>
<div id="sampleBoardDiv" class="hidden" style="width:100%;height:500px;">

<!-- search box
<div class="view_box" style="height:50px;">
	<div class="guide"><span class="tl"></span><span class="tr"></span></div>
	<div class="cont">
	</div>
	<div class="guide"><span class="lb"></span><span class="rb"></span></div>
</div>
-->

<!-- ���� -->
	<div style="font-size:0px; height: 470px;overflow-x: auto;overflow-y: auto;">
		<table class="imp_001" border="0" cellpadding="0" cellspacing="0" width="140%">
		<colgroup>
			<col width="4%">
			<col width="5%">
			<col width="8%">
			<col width="8%">
			<col width="3%">
			
			<col width="6%">
			<col width="8%">
			<col width="6%">
			<col width="6%">
			<col width="5%">
			
			<col width="5%">
			<col width="3%">
			<col width="8%">
			<col width="5%">
			<col width="5%">
			
			<col width="5%">
			<col width="5%">
			<col width="5%">
		</colgroup>			
		<tr>
			<th colspan="4">���μ���/��Ƽ��Ƽ �⺻����</th>
			<th colspan="7">���μ��� �Ӽ�</th>
			<th colspan="7" class="last">��Ƽ��Ƽ �Ӽ�</th>	
		</tr>	
		<tr>
			<th>ID</th>
			<th>ID(��)</th>
			<th>Process Name</th>
			<th>�Ұ� ���μ�</th>
			<th>����</th> 
			<th>����<br>����</th>
			<th>����</th>
			<th>Process<br> Owner</th>
			<th>�м���<br> �ۼ�å����</th>
			<th>����<br> Event</th>
			<th>����<br> Event</th>
			<th>����</th>
			<th>����</th>
			<th>����μ�</th>
			<th>������ü</th>
			<th>Ȱ������μ�/�ܺα�� (From)</th>
			<th>Ȱ������μ�/�ܺα�� (To)</th>
			<th class="last">���ý���</th>		
		</tr>
			<%
			if(fileName != null) {
			//if(false) {

				InputStream inp = new FileInputStream(fileName);
				
				Workbook wb = WorkbookFactory.create(inp);
				
				Sheet sheet = wb.getSheetAt(0);
				
				Row row   = null;
				Cell cell = null;

				int i = 2;
				String value = "";
				
				do {	
			%>
					<tr>
			<%		
					row = sheet.getRow(i);

					String style = "font-size:11px;height:200%;width:95%;border:0px;letter-spacing:-1px;overflow:hidden;";
					String cellBackgroundColor = "";
					cell = row.getCell(0);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					
					value = "";
					if (cell.getStringCellValue() != null) {
						value = cell.getStringCellValue();			
					}
					
					if(value.length() == 6) {
						style = style + "background-color:#FFE400;";
						cellBackgroundColor = "bgcolor=\"#FFE400\"";
					}
					

					String cellBackgroundColor1 = cellBackgroundColor;
					
					for(int j = 0; j < 20; j++) {
						
						if(j == 3) {
							continue;
						}
						
						String readOnly = "";
						if(j == 0 || j == 1) {
							readOnly = "readonly=\"readonly\"";
						}
						
								
						
						cell = row.getCell(j);
						
						value = "";
						if(cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);

							if (cell.getStringCellValue() != null) {
								value = cell.getStringCellValue();
							}				
						}
						
						
						
						

						if(j == 19) {
			%>
							<td>
								<input type="hidden" name="value" value="<%=value%>" ></input>
							</td>
			<%				

						}
						else {				
							
								
			%>					
							<td <%=cellBackgroundColor %>>
								<textarea rows="2" cols="" <%=readOnly%> style='<%=style%>'><%=value%></textarea>
							</td>						
			<%											
							
						}
						cellBackgroundColor = cellBackgroundColor1;
					}	
			%>
					</tr>	
			<%	
					
					i++;

					row = sheet.getRow(i);

				} while(row != null);	
			%>

			<%
			}
			%>
				<tr>
					<td colspan="19" height="1" class="hr1">&nbsp;</td>
				</tr>

		</table>
	</div>
	<div style="height:30px;">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="19" align="right">
				<input type="submit" name="submit" value="����"></input>
			</td>
		</tr>
	</table>
	</div>
</div>
</form>
</body>
</html>