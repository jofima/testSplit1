package jofima;

import java.io.BufferedOutputStream;
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
 * The goal here is to get from the Excel file the column with the address which
 * contains the door no. inside the address column and generate a new file with
 * concatenated clientNo+Name+FloorNo, address, doorNo, PostalCode
 * 
 * For concatenation I separated the information into lists and joined the Strings
 * into a new List
 * 
 * Finding the door number I searched the address with regular expressions and
 * obtained the start and end index
 * 
 * 
 */

public class InosatData {

	@SuppressWarnings("rawtypes")
	Map workMap = DataWorkList.dataWork;

	//@SuppressWarnings("rawtypes")
	//Map writeMap = new TreeMap();

	List<String> inoAddressValues = new ArrayList<String>();
	List<String> cliName = new ArrayList<String>();
	List<String> numClie = new ArrayList<String>();
	List<String> localidade = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	List inoValues = new ArrayList();
	@SuppressWarnings("rawtypes")
	List inoFiles = new ArrayList();
	// @SuppressWarnings("rawtypes")
	// List writeFileIno = new ArrayList();
	@SuppressWarnings("rawtypes")
	List colCliente = new ArrayList();

	@SuppressWarnings("rawtypes")
	List portaNum = new ArrayList();

	List<String> newAddress = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	List floorNum = new ArrayList();

	List<Integer> indDoorN = new ArrayList<Integer>();
	List<Integer> endIndexDoor = new ArrayList<Integer>();

	//int subIndex = 0;
	String SubAddress;
	// StringBuilder joinAddr = new StringBuilder();

	// int indDoorN = 0;
	// int endIndexDoor = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void inosat() {

		Set<String> keyset = workMap.keySet();
		// int rownum = 0;

		for (String key : keyset) {
			// Row row = sheet.createRow(rownum++);
			List listValues = (List) workMap.get(key);

			// separate the the information into Lists so they can be worked
			// separately
			for (int i = 0; i < listValues.size(); i++) {

				// Column 0, 1, 2, 3 from excel file to separate Lists
				if (i == 0) {
					numClie.add(listValues.get(i).toString());
				} else if (i == 1) {
					cliName.add(listValues.get(i).toString());
				} else if (i == 2) {
					inoAddressValues.add(listValues.get(i).toString());
				} else if (i == 3) {
					localidade.add(listValues.get(i).toString());
				}
			}

		}// end of outer for

		// go through all addresses, find door number and separate
		for (String addressStr : inoAddressValues) {
			// run the findit method with the separated address
			SubAddress = addressStr;
			//try{
				
				FindPattern find = new FindPattern(SubAddress);
				indDoorN.add(find.getIndDoorN());
				endIndexDoor.add(find.getEndIndexDoor());
			//findIt(addressStr); 
			
			//}catch (Exception e) {
			//	JOptionPane.showMessageDialog(null,"ERRO AO LER MORADA","ERRO",JOptionPane.ERROR_MESSAGE);
			//	e.printStackTrace();
			//}

		}

//		try {
//			wait(10000);
		
			// run the method that will concatenate some information after getting the indexes
		mountString();
		
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}

//	
//	/** 
//	* Search address string with regular expressions and get the index at door no.
//	*
//	*
//	*/
//	
//	public void findIt(String findPat) throws Exception {
//
//		//int subIndex = 0;
//		
//		//regular expressions for searching door no.
//		String pattern1 = "(\\sN\\u00BA\\s\\d+)|(Nº\\s\\d+)|(,N\\d+)|(,N\\s\\d+)|(\\sN\\s\\d+)||(nº\\d+)(\\sNº.\\s\\d+)";
//		String pattern2 = "(LOTE\\s\\d*)|(lote\\s\\d*)|(Lote\\s\\d*)|(Lote\\d*)|(Lt\\s\\d*)|(Lt\\d*)|(Lt.\\d*)";
//		String pattern3 = "(\\s\\d+\\w$)|(\\s\\d*,)|(\\sN\\d+$)|(\\d$)|(\\s\\d*\\s)";
//		String pattern4 = "(RUA\\s\\d*)|(AV.\\s\\d*)|(\\srua\\s\\d*)"
//				+ "|(av\\s\\d*)|([Rr]\\s\\d*)|(AVENIDA\\s\\d*)"
//				+ "|(Avenida\\s\\d*)|(avenida\\s\\d*)|(\\w\\s\\d*\\sDE)";
//		
//		Pattern p1 = Pattern.compile("\\sN\\u00BA\\s\\d+");
//		//Pattern p1 = Pattern.compile(pattern1, Pattern.CASE_INSENSITIVE);
//		Pattern p2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
//		Pattern p3 = Pattern.compile(pattern3, Pattern.CASE_INSENSITIVE);
//		Pattern p4 = Pattern.compile(pattern4, Pattern.CASE_INSENSITIVE);
//
//		
//		Matcher m1 = p1.matcher(findPat);
//		Matcher m2 = p2.matcher(findPat);
//		Matcher m3 = p3.matcher(findPat);
//		Matcher m4 = p4.matcher(findPat);
//
//		//findPat = findPat.trim().intern().toUpperCase().toString();
//		int subIndex =0;
//		//if (m4.find()){
//		//	
//		//	System.out.println(findPat);
//		//	System.out.println("found" + m4.group());
//		//	subIndex = m4.end();
//		//	System.out.println("found "+ subIndex);
//		//}
//		//get the last index save use a substring from the last index
//		//run a search again but add the last index for the ArrayList
//		
//		
//		if (m1.find()) {
//			
//			System.out.println(findPat);
//			System.out.println(m1.group());
//			
//			indDoorN.add(m1.start());//start index number
//			endIndexDoor.add(m1.end());//end index number
//			//subIndex = 0;
//		
//		} else if (m2.find()) {
//			
//			System.out.println(findPat);
//			System.out.println(m2.group());
//			indDoorN.add(m2.start());
//			endIndexDoor.add(m2.end());
//			//subIndex = 0;
//		
//		} else if (m3.find()) {
//			
//			System.out.println(findPat);
//			System.out.println(m3.group());
//			indDoorN.add(m3.start());
//			endIndexDoor.add(m3.end());
//			//subIndex = 0;
//			
//		} else {
//			indDoorN.add(0);
//			endIndexDoor.add(0);
//			//subIndex = 0;
//
//		}
//			
//	} // end of FindIt()

	@SuppressWarnings({ "unchecked" })
	public void mountString() {

		System.out.println(indDoorN);
		System.out.println(endIndexDoor);
		
	try {
		int index = 0;
		for (; index < indDoorN.size(); index++) {
			String tempDoor = inoAddressValues.get(index);

			portaNum.add(tempDoor.substring(indDoorN.get(index),
					endIndexDoor.get(index)));
			floorNum.add(tempDoor.substring(endIndexDoor.get(index)));
			newAddress.add(tempDoor.substring(0, indDoorN.get(index)));

		}// end of for loop

		// this for loop is used for concatenation
		for (int i = 0; i < numClie.size(); i++) {
			colCliente.add(numClie.get(i) + " " + cliName.get(i) + " "
					+ floorNum.get(i));
		}// end of for loop

	} catch (Exception e){
		
		JOptionPane.showMessageDialog(null,"ERRO AO LER MORADA (COL 3?)","ERRO",JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
	}

		// initiate writeInoFile after organizing the information
		writeInoFile();
	}

	
	/**
	 * 
	 * The method writeInoFiles is used to create the excel worksheet
	 * and it's rows and cells with the information gathered through the
	 * Lists - that represent rows
	 * After all the cells are filled write the excel file to disk
	 * 
	 */
	
	public void writeInoFile() {

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
		for (int i = 0; i <= DataWorkList.lastRowNumber; i++) {
			row = sheet.createRow(i);

			int cellnum = 0;

			// for first row create cells with specific labels due to concatenation
			if (i == 0) {
				cell = row.createCell(cellnum);
				// if (objArr instanceof String)
				cell.setCellValue("CLIENTE");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("MORADA");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("NºPORTA");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("LOCALIDADE");
				cellnum++;

				// All other rows create cells with the data from the lists
			} else {
				cell = row.createCell(cellnum);
				// if (objArr instanceof String)
				cell.setCellValue((String) colCliente.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) newAddress.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) portaNum.get(i));
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) localidade.get(i));
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
			
			
			Path path = Paths.get(NewFrame.pathFile +"\\"+ "inosat "+ dateTime + ".xls");
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
	

	

}// end of class
