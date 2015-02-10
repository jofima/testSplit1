package jofima;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


/**
 * organizes the columns and writes the processed exel file (central)
 *  with timestamp in the file name for versioning
 * 
 * 
 *
 */

@SuppressWarnings("unused")
public class CentralData {

	@SuppressWarnings("rawtypes")
	Map workMap = DataWorkList.dataWork;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void central() {

		// System.out.println(workMap);

		// 0, 1, 2, 5

		// Blank workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		// Create a blank sheet
		HSSFSheet sheet = workbook.createSheet("info");

		// HSSFRow row;
		Row row = null;

		Set<String> keyset = workMap.keySet();
		int rownum = 0;
		for (String key : keyset) {
			row = sheet.createRow(rownum++);

			List listValues = (List) workMap.get(key);
			List centralValues = new ArrayList();
			
			for (int i = 0; i < listValues.size(); i++) {
				if (i == 0 | i == 1 | i == 2 | i == 5) {
					centralValues.add(listValues.get(i));
				} // end of if
			}

			// System.out.println(centralValues);

			Object[] objArray = centralValues.toArray();
			String[] objArr = new String[objArray.length];
			
			for (int i = 0; i < objArray.length; i++) {
				objArr[i] = objArray[i].toString();
				//System.out.println(objArr[i]);
			}

			// int mapSize = workMap.size();

			int cellnum = 0;
			// for (Object obj : objArr) {
			for (; cellnum < objArr.length;) {

				Cell cell = row.createCell(cellnum++);
				// if (objArr instanceof String)
				cell.setCellValue(objArr[cellnum - 1]);
				// else if (obj instanceof Integer)
				// cell.setCellValue((Integer) obj);
			}
		}// end of outer for loop
		
		int lastCell = row.getLastCellNum();
		
		// after all the cells have been processed get the columns to
		// a correct size
		for(int i=0;i<lastCell;i++){
		sheet.autoSizeColumn(i);
		}
		

		try {

			// get timestamp for file name - wroks as file versioning
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HHmmss");
			Calendar cal = Calendar.getInstance();
			String dateTime = dateFormat.format(cal.getTime());
			
			Path path = Paths.get(NewFrame.pathFile +"\\" +"central "+ dateTime + ".xls");
			
			//Path path = Paths.get("c:\\temp\\central.xls");
			Files.createDirectories(path.getParent());
			//Files.createFile(path);
			
			// Write the workbook in file system
			OutputStream out = new BufferedOutputStream(Files.newOutputStream(path));
			workbook.write(out);
			out.close();
			//System.out.println("x written successfully on disk.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"ERRO AO GRAVAR FICHEIRO","ERRO",JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
		} // end of catch
	}// end of central method

}
