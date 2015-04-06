package jofima;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * The goal here is to get from the Excel file the column with the address which
 * contains the door no. inside the address column and generate a new file with
 * concatenated clientNo+Name+FloorNo, address, doorNo, PostalCode
 * 
 * generated a whole lot of Lists for every column, information
 * 
 * For concatenation I separated the information into lists and joined the Strings
 * into a new List
 * 
 * Finding the door number I searched the address with regular expressions and
 * obtained the start and end index through the FindPattern class
 * 
 * 
 */

public class InosatData {

	@SuppressWarnings("rawtypes")
	Map workMap = DataWorkList.dataWork;

	//@SuppressWarnings("rawtypes")
	//Map writeMap = new TreeMap();
	
	
	List<String> numClie = new ArrayList<String>();
	List<String> cliName = new ArrayList<String>();
	List<String> addressValues = new ArrayList<String>();
	List<String> localidade = new ArrayList<String>();
	List<String> nif = new ArrayList<String>();
	List<String> telephone = new ArrayList<String>();
	List<String> morada2 = new ArrayList<String>();
	List<String> codPostal = new ArrayList<String>();
	List<String> desigPostal = new ArrayList<String>();
	
	static List<String> notProcessedClient = new ArrayList<String>();
	static List<String> notProcessedName = new ArrayList<String>();
	static List<String> notProcessedAddress = new ArrayList<String>();
	static List<String> notProcessedLocal = new ArrayList<String>();
	static List<String> notProcessedNIF = new ArrayList<String>();
	static List<String> notProcessedTel = new ArrayList<String>();
	static List<String> notProcessedMorada2 = new ArrayList<String>();
	static List<String> notProcessedCodPostal = new ArrayList<String>();
	static List<String> notProcessedDesigPostal = new ArrayList<String>();

	List<String> centralClient = new ArrayList<String>();
	List<String> centralName = new ArrayList<String>();
	List<String> centralAddress = new ArrayList<String>();
	List<String> centralTel = new ArrayList<String>();
	

	List<String> inoColCliente = new ArrayList<String>();
	List<String> inoNewAddress = new ArrayList<String>();
	List<String> inoPortaNum = new ArrayList<String>();
	List<String> inoCodigoPost = new ArrayList<String>();
	List<String> inoDesigPostal = new ArrayList<String>();
	//List<String> inoFloorNum = new ArrayList<String>();

		
	List<Integer> indDoorN = new ArrayList<Integer>();
	List<Integer> endIndexDoor = new ArrayList<Integer>();

	//int subIndex = 0;
	List<String> subAddress =new ArrayList<String>();
	String subAddr;
	// StringBuilder joinAddr = new StringBuilder();
	
	String streetIn;
	String streetOut;

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
					numClie.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 1) {
					cliName.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 2) {
					//run changeStreet method for correction when getting values
					addressValues.add(changeStreet(listValues.get(i).toString().toUpperCase()));
				} else if (i == 3) {
					localidade.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 4) {
					nif.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 5) {
					telephone.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 6) {
					morada2.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 7) {
					codPostal.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 8) {
					// in this case run the method to change the incorrect entries
					desigPostal.add(localChange(listValues.get(i).toString().toUpperCase()));
				} //end ifs
			} //inner for

		}// end of outer for

		// go through all addresses, find door number and separate
		for (String addressStr : addressValues) {

			
//			// correct all addresses using method changeStreet 
//			//(this should be run directly when mounting the arraylist above
			subAddress.add(addressStr);
//			subAddr = changeStreet(addressStr);
//						
			//System.out.println(subAddress);
			// try{

			// for every address instantiate the FindPattern class that will search the
			// address with regular expressions for door no.
			FindPattern find = new FindPattern(addressStr);
			indDoorN.add(find.getIndDoorN());
			endIndexDoor.add(find.getEndIndexDoor());

		}
			// run the method that will concatenate some information after
			// getting the indexes
			mountString();

			// } catch (InterruptedException e) {
			// //
			// JOptionPane.showMessageDialog(null,"ERRO AO LER MORADA","ERRO",JOptionPane.ERROR_MESSAGE);
			
			// e.printStackTrace();
			// }
		
	}
	
	/**
	 * mountString method will concatenate the rest of the strings separated
	 * from door number and will add it to a list. It will also check if a
	 * door number exists and will use another list for a fail file as well as
	 * incorrect postal codes.
	 * 
	 */
	
	public void mountString() {

		// System.out.println(indDoorN);
		// System.out.println(endIndexDoor);

		try {
			int index = 0;
			for (; index < indDoorN.size(); index++) {
				String tempDoor = subAddress.get(index);

				if (indDoorN.get(index) == 0 & endIndexDoor.get(index) == 0) {

					// add numclient to a new list and save a file with this
					// information
					
					/* list of addresses that were not processed due to not finding
					 * door number with pattern
					 */
					notProcessedClient.add(numClie.get(index));
					notProcessedName.add(cliName.get(index));
					notProcessedAddress.add(addressValues.get(index));
					notProcessedLocal.add(localidade.get(index));
					notProcessedNIF.add(nif.get(index));
					notProcessedTel.add(telephone.get(index));
					notProcessedMorada2.add(morada2.get(index));					
					notProcessedCodPostal.add(codPostal.get(index));
					notProcessedDesigPostal.add(desigPostal.get(index));
					
					// portaNum.add(" ");
					// floorNum.add(" ");
					// newAddress.add(inoAddressValues.get(index));

				} else if (codPostal.get(index).length() < 7){
					
					/*
					 * list that was not processed due to incomplete postal code
					 */
					notProcessedClient.add(numClie.get(index));
					notProcessedName.add(cliName.get(index));
					notProcessedAddress.add(addressValues.get(index));
					notProcessedLocal.add(localidade.get(index));
					notProcessedNIF.add(nif.get(index));
					notProcessedTel.add(telephone.get(index));
					notProcessedMorada2.add(morada2.get(index));
					notProcessedCodPostal.add(codPostal.get(index));
					notProcessedDesigPostal.add(desigPostal.get(index));
					
				} else {
					/*
					 * hold in each list the information to be processed to file
					 * with the required concatenation of information in each
					 * field / list - inosat file 
					 */
					inoColCliente.add(numClie.get(index) + " "+ cliName.get(index) + " "
							+ (tempDoor.substring(endIndexDoor.get(index))));
					inoNewAddress
							.add(tempDoor.substring(0, indDoorN.get(index)));
					inoPortaNum.add(tempDoor.substring(indDoorN.get(index),
							endIndexDoor.get(index)));
					inoCodigoPost.add(codPostal.get(index));
					inoDesigPostal.add(desigPostal.get(index));
					//inoFloorNum.add(tempDoor.substring(endIndexDoor.get(index)));
					
					/*
					 * central file
					 */
					centralClient.add(numClie.get(index));
					centralName.add(cliName.get(index));
					centralAddress.add(addressValues.get(index)+" ("+localidade.get(index)+")");
					centralTel.add(telephone.get(index));

				} // end of ifs

			}// end of for loop

			// this for loop is used for concatenation
			// for (int i = 0; i < numClie.size(); i++) {
			// colCliente.add(numClie.get(i) + " " + cliName.get(i) + " "
			// + floorNum.get(i));
			// }// end of for loop

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "ERRO AO LER MORADA (COL 3?)",
					"ERRO", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// initiate writeInoFile after organizing the information
		writeInoFile();
	}

	/**
	 * 
	 * The method writeInoFiles is used to create the excel worksheet and it's
	 * rows and cells with the information gathered through the Lists - that
	 * represent rows After all the cells are filled write the excel file to
	 * disk
	 * 
	 */
	
	public void writeInoFile() {

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

		Row row = null;

		Cell cell;
		// int cellnum =0;
		// int rownum = 0;

		// for (String key : keyset) {
		for (int i = 0; i <= inoColCliente.size(); i++) {
			row = sheet.createRow(i);

			int cellnum = 0;

			// for first row create cells with specific labels due to concatenation
			if (i == 0) {
				cell = row.createCell(cellnum);
				// if (objArr instanceof String)
				cell.setCellValue("CLIENTE");
				cell.setCellStyle(style);
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("MORADA");
				cell.setCellStyle(style);
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("NºPORTA");
				cell.setCellStyle(style);
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("CODIGO POSTAL");
				cell.setCellStyle(style);
				cellnum++;
				cell = row.createCell(cellnum);
				cell.setCellValue("DESIGNAÇÃO POSTAL");
				cell.setCellStyle(style);
				++cellnum;
				// All other rows create cells with the data from the lists
			} else {
				cell = row.createCell(cellnum);
				
				cell.setCellValue((String) inoColCliente.get(i-1)); // column 1
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) inoNewAddress.get(i-1)); // column 2
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) inoPortaNum.get(i-1)); // column 3
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) inoCodigoPost.get(i-1)); // column 5
				cellnum++;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) inoDesigPostal.get(i-1));  // column 4
				++cellnum;
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
	
		// instantiate missed address file
		new WriteFailFile();
		
		new WriteCentralFile(centralClient, centralName, centralAddress, centralTel);
		//JOptionPane.showMessageDialog(null,"FICHEIROS GRAVADOS COM SUCESSO","OK",JOptionPane.INFORMATION_MESSAGE);
	}
	

	public String changeStreet(String street) {
		
		String newStreet = null;
		try {

			Path path = Paths.get(NewFrame.pathFile + "\\" + "ruas.txt");

			BufferedReader br = Files.newBufferedReader(path);
			String line;
			while ((line = br.readLine()) != null) {
				// String[] split = new String[2];
				String[] split = line.split(",");

				streetIn = split[0];
				streetOut = split[1];

				if (street.startsWith(streetIn)) {
					// }
					//System.out.println(split[0]);
					//System.out.println(split[1]);
					// newStreet = street.replaceAll("^" + streetIn, streetOut);
					newStreet = street.replaceFirst(streetIn, streetOut);
					//System.out.println(street);
					// System.out.println(newStreet);
					break;
					// return newStreet;

				} else {
					newStreet = street;
				}
			} // while loop
			
			br.close();
		
		} catch (NoSuchFileException e) {
			
			JOptionPane.showMessageDialog(null,"FICHEIRO RUAS.TXT NÃO ENCONTRADO: "+NewFrame.pathFile,"ERRO",JOptionPane.ERROR_MESSAGE);
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null,"ERRO ALTERAÇÕES FICHEIRO RUAS.TXT","ERRO",JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
			
		}

		return newStreet;
	} // end of method changeStreet

	private String localChange(String locali) {

		String newLocalidade = null;

		try {

			Path path = Paths.get(NewFrame.pathFile + "\\" + "localidades.txt");

			BufferedReader br = Files.newBufferedReader(path);
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");

				String localIn = split[0];
				String localOut = split[1];

				if (locali.startsWith(localIn)) {
					
					newLocalidade = locali.replaceFirst(localIn, localOut);

					break;

				} else {
					newLocalidade = locali;
				}
			} // while loop
			
			br.close();

		} catch (NoSuchFileException e) {

			JOptionPane.showMessageDialog(null,
					"FICHEIRO LOCALIDADES.TXT NÃO ENCONTRADO: " + NewFrame.pathFile,
					"ERRO", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "ERRO ALTERAÇÕES FICHEIRO LOCALIDADES.TXT", "ERRO", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();

		}

		return newLocalidade;

	}

}// end of class
