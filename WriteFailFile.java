package jofima;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class WriteFailFile {

	public WriteFailFile() {

		// Blank workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		// Create a blank sheet
		HSSFSheet sheet = workbook.createSheet("info");

		// Set<String> keyset = writeMap.keySet();

		Row row = null;

		Cell cell;
		// int cellnum =0;
		// int rownum = 0;

		// for (String key : keyset) {
		for (int i = 0; i < InosatData.notProcessedClient.size(); i++) {
			row = sheet.createRow(i);

			int cellnum = 0;

		
				cell = row.createCell(cellnum);
				// if (objArr instanceof String)
				cell.setCellValue((String) InosatData.notProcessedClient.get(i));
				cellnum++;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedName.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedAddress.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedLocal.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedNIF.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedTel.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedMorada2.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedCodPostal.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) InosatData.notProcessedDesigPostal.get(i));
				cellnum++;

			

		}
		
		
		int lastCell = row.getLastCellNum();

		//After filling up all the cells get the columns to the right size 
		for(int i=0;i<lastCell;i++){
		sheet.autoSizeColumn(i);
		}
		
		try {

			// Files file;

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HHmmss");
			Calendar cal = Calendar.getInstance();
			String dateTime = dateFormat.format(cal.getTime());
			
			
			Path path = Paths.get(NewFrame.pathFile +"\\"+ "falhas "+ dateTime + ".xls");
			Files.createDirectories(path.getParent());
			// Files.createFile(path);

			// Write the workbook in file system
			OutputStream out = new BufferedOutputStream(
					Files.newOutputStream(path));
			workbook.write(out);
			out.close();
			// System.out.println("x written successfully on disk.");
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null,"ERRO AO GRAVAR FICHEIRO","ERRO",JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
		}
	
		//JOptionPane.showMessageDialog(null,"FICHEIROS GRAVADOS COM SUCESSO","OK",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	
}
