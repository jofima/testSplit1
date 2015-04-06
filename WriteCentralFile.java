package jofima;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class WriteCentralFile {

	
	WriteCentralFile(List<String> centralClient, List<String> centralName, List<String> centralAddress, List<String> centralTel){
		
		// Blank workbook
				HSSFWorkbook workbook = new HSSFWorkbook();

				// Create a blank sheet
				HSSFSheet sheet = workbook.createSheet("info");

				//Get access to HSSFCellStyle 
				HSSFCellStyle style = workbook.createCellStyle();
				//Create HSSFFont object from the workbook
		        HSSFFont font=workbook.createFont();
		        // set the weight of the font 
		        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        // attach the font to the style created earlier 
		        style.setFont(font);
		        
				// Set<String> keyset = writeMap.keySet();

				Row row = null;

				Cell cell;
				// int cellnum =0;
				// int rownum = 0;

				// for (String key : keyset) {
				for (int i = 0; i <= centralClient.size(); i++) {
					row = sheet.createRow(i);

					int cellnum = 0;

											// for first row create cells with specific labels due to concatenation
						if (i == 0) {
							cell = row.createCell(cellnum);
							// if (objArr instanceof String)
							cell.setCellValue("NUMERO CLIENTE");
							cell.setCellStyle(style);
							++cellnum;
							cell = row.createCell(cellnum);
							cell.setCellValue("NOME");
							cell.setCellStyle(style);
							++cellnum;
							cell = row.createCell(cellnum);
							cell.setCellValue("MORADA");
							cell.setCellStyle(style);
							++cellnum;
							cell = row.createCell(cellnum);
							cell.setCellValue("TELEFONE");
							cell.setCellStyle(style);
							cellnum++;

							// All other rows create cells with the data from the lists
						} else {
											
						cell = row.createCell(cellnum);
						// if (objArr instanceof String)
						cell.setCellValue((String) centralClient.get(i-1));
						cellnum++;
						cell = row.createCell(cellnum);
						cell.setCellValue((String) centralName.get(i-1));
						++cellnum;
						cell = row.createCell(cellnum);
						cell.setCellValue((String) centralAddress.get(i-1));
						++cellnum;
						cell = row.createCell(cellnum);
						cell.setCellValue((String) centralTel.get(i-1));
						
						cellnum++;

						}

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
					
					
					Path path = Paths.get(NewFrame.pathFile +"\\"+ "central "+ dateTime + ".xls");
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
			
				JOptionPane.showMessageDialog(null,"FICHEIROS GRAVADOS COM SUCESSO","OK",JOptionPane.INFORMATION_MESSAGE);
			}
			
			
			
		
		
	}
	
