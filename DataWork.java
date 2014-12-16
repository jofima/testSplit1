package jofima;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SheetDataWriter;

public class DataWork {

	String addressStr;
	int maxNumCells;
	int lastRowNumber;

	ArrayList sheetData = new ArrayList();
	ArrayList rowCellNo = new ArrayList();

	Map dataWork = new TreeMap();

	public DataWork(File openFile) {

		try {
			FileInputStream file = new FileInputStream(openFile);

			// Create Workbook instance holding reference to .xls
			// file
			HSSFWorkbook readWorkbook = new HSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			HSSFSheet sheet = readWorkbook.getSheetAt(0);

			// this would be to store first and last row number to
			// iterate individual rows
			int firstRowNumber = sheet.getFirstRowNum();
			lastRowNumber = sheet.getLastRowNum();
			maxNumCells = sheet.getRow(0).getLastCellNum();
			System.out.println(maxNumCells);
			List data = new ArrayList();

			for (int i = 0; i <= lastRowNumber; i++) {

				HSSFRow cell = sheet.getRow(i);
				// System.out.println(i);
				rowCellNo.add(String.valueOf(i));

				for (int cellCounter = 0; cellCounter < maxNumCells; cellCounter++) {

					if (cell.getCell(cellCounter) == null) {
						cell.createCell(cellCounter);
						sheetData.add(cell.getCell(cellCounter));
						// rowCellNo.add(String.valueOf(i)+";"+String.valueOf(cellCounter));

					} else {
						sheetData.add(cell.getCell(cellCounter));
						// rowCellNo.add(String.valueOf(i)+";"+String.valueOf(cellCounter));

					}
				}
			}

			// System.out.println(sheetData);
			// System.out.println(rowCellNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mapIt();

	}// end of constructor

	
	// This method organizes the data input from the excel file into a map
	public void mapIt() {

		int i = 0;
		int x = 0;
		int z = maxNumCells;
		for (; i <= lastRowNumber; i++) {
			
			//String key, ArrayList value
			dataWork.put(rowCellNo.get(i), sheetData.subList(x, z));
			
			System.out.println(dataWork.get(String.valueOf(i)));
			x += maxNumCells;
			z += maxNumCells;
			
			System.out.println(((List)(dataWork.get(String.valueOf(i)))).get(2));		
			addressStr = ((List)(dataWork.get(String.valueOf(i)))).get(2).toString();
		
			// run the findit method with the separated address
			findIt(addressStr);
			
		} //end of for loop

		System.out.println(dataWork);
		System.out.println(((List)(dataWork.get("2"))).get(2));
		
		
		
	} // end of mapIt()
	
	public void findIt(String findPat) {
		
		Pattern p1 = Pattern.compile("(\\sN\\s)|(\\sNº)|(nº)|(N\\d*)");
		Pattern p2 = Pattern.compile("(\\w\\s\\d*)|(\\w\\s\\d*\\w$)");
		Matcher m = p1.matcher(findPat);
		Matcher m1 = p2.matcher(findPat);
		
		
		if (m.find()) {
			System.out.println("found " + m.group() + " at " + m.start());
			//System.out.println("no of matches " + m.groupCount());
		}else if ( m1.find()){
			System.out.println("found " + m1.group() + " at " + m1.start());
		}else {
			
		}
		
	} //end of FindIt()
	

}// end of class

// sheetData.add(data);

// System.out.println(data);
// System.out.println(sheetData);
//
// System.out.println("first row + last row + lastCell:"
// + firstRowNumber + " " + lastRowNumber + " "
// + maxNumCells);
//
// int numRow = 0;
//
// // Iterate through each rows one by one
// //Iterator<Row> rowIterator = sheet.iterator();
// while (cellsCnt.hasNext()) {
// Row row = (Row)cellsCnt.next();
//
// System.out.println("Row no. " + row.getRowNum());
//
// // For each row, iterate through all the columns
// Iterator<Cell> cellIterator = row.cellIterator();
//
// while (cellIterator.hasNext()) {
// Cell cell = cellIterator.next();
// // Check the cell type and format accordingly
// switch (cell.getCellType()) {
// case Cell.CELL_TYPE_NUMERIC:
// System.out.print(cell.getNumericCellValue()
// + " | ");
// data.add(cell);
//
// break;
// case Cell.CELL_TYPE_STRING:
// System.out.println("COL. "
// + cell.getColumnIndex());
// System.out.print(cell.getStringCellValue()
// + " | ");
// data.add(cell);
//
// rowsInfo.add(cell.getStringCellValue());
// cellIndexes.add((String.valueOf(cell
// .getRowIndex()) + ";" + String
// .valueOf(cell.getColumnIndex())));
//
// break;
// }
// sheetData.add(data);
// numRow++;
// infoCell.put(String.valueOf(numRow), data);
//
// }
// System.out.println("");
//
// }
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// // System.out.println("Save as file: " + filePath);
// // System.out.println(fileToSave);
// System.out.println(rowsInfo);
// System.out.println(cellIndexes);
// System.out.println("sheetdata" + sheetData);
// System.out.println("infocell: " + infoCell);
//
// }
//
// }
// });
//
// }
// }
//
//
// }
// Iterator rowsCount = sheet.rowIterator();
// while(rowsCount.hasNext()){
//
// HSSFRow rowss = (HSSFRow) rowsCount.next();
//
// Iterator cellsCnt = rowss.cellIterator();

// while (cellsCnt.hasNext()){
// Cell cell;

// Loop through cells

// switch(cell.getCellType()){
//
// case HSSFCell.CELL_TYPE_NUMERIC:
// System.out.println(cell.getNumericCellValue());
// sheetData.add(cell.getNumericCellValue());
// break;
// case HSSFCell.CELL_TYPE_STRING:
// System.out.println(cell.getStringCellValue());
// sheetData.add(cell.getStringCellValue());
// break;
// }