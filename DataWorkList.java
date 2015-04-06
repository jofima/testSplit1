package jofima;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class will read the excel file that was selected from the NewFrame class 
 * which launches a JFrame GUI with file selection, and organizes the information
 * by creating a map that is used in inosatData CentralData 
 * 
 * needs apache poi for excel file reading
 */


public class DataWorkList {

	//String addressStr; 
	int maxNumCells;
	static int lastRowNumber;

	@SuppressWarnings("rawtypes")
	ArrayList sheetData = new ArrayList();
	@SuppressWarnings("rawtypes")
	ArrayList rowCellNo = new ArrayList();

//	@SuppressWarnings("rawtypes")
//	ArrayList centralFile = new ArrayList();
//	@SuppressWarnings("rawtypes")
//	ArrayList centralCell = new ArrayList();

	@SuppressWarnings("rawtypes")
	static Map dataWork = new TreeMap();
	//Map centralData = new TreeMap();

	
	//Constructor that receives the file and separates the excel cells 
	@SuppressWarnings( "unchecked" )
	public DataWorkList(File openFile) {

		try {
			FileInputStream file = new FileInputStream(openFile);

			// Create Workbook instance holding reference to .xls
			// file
			HSSFWorkbook readWorkbook = new HSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			HSSFSheet sheet = readWorkbook.getSheetAt(0);

			// this would be to store first and last row number to
			// iterate individual rows
			//int firstRowNumber = sheet.getFirstRowNum();
			lastRowNumber = sheet.getLastRowNum();
			maxNumCells = sheet.getRow(0).getLastCellNum();//used to complete blank cells
			for (int i = 0; i <= lastRowNumber; i++) {

				HSSFRow cell = sheet.getRow(i);
				// add row numbers to a list 
				rowCellNo.add(String.valueOf(i));

				
				// iterate through the cells and add them to an arraylist for
				// processing
				for (int cellCounter = 0; cellCounter < maxNumCells; cellCounter++) {

					// this if was created to complete the no. of cells when
					// there are empty cells, normally the operation skips empty cells
					if (cell.getCell(cellCounter) == null) {
						cell.createCell(cellCounter);
						sheetData.add(cell.getCell(cellCounter));
						// rowCellNo.add(String.valueOf(i)+";"+String.valueOf(cellCounter));

					} else {
						sheetData.add(cell.getCell(cellCounter));
						// rowCellNo.add(String.valueOf(i)+";"+String.valueOf(cellCounter));
					}
					
				} // inner for loop
			} // outer for loop


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"ERRO AO LER FICHEIRO","ERRO",JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
		}

		// Start mapIt method after reading the excel
		try{
		mapIt();
		}catch (Exception e){
			JOptionPane.showMessageDialog(null,"ERRO AO PROCESSAR FICHEIRO","ERRO",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}// end of constructor

	
	// This method organizes the data input from the excel file into a map
	@SuppressWarnings({ "unchecked" })
	public void mapIt() throws Exception {

		int i = 0;
		int x = 0;
		int z = maxNumCells;
		for (; i <= lastRowNumber; i++) {

			// String key, ArrayList value
			// key - row no., value - list with excel info
			dataWork.put(rowCellNo.get(i), sheetData.subList(x, z));

			//System.out.println(dataWork.get(String.valueOf(i)));
			x += maxNumCells; //for whole row
			z += maxNumCells;



		} // end of for loop

	
		//Run and process inoSatData class that will process the data and files
	
		new InosatData().inosat();
		
	} // end of mapIt()

	
}// end of class

