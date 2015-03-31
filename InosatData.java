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
 * obtained the start and end index through the FindPattern class
 * 
 * 
 */

public class InosatData {

	@SuppressWarnings("rawtypes")
	Map workMap = DataWorkList.dataWork;

	//@SuppressWarnings("rawtypes")
	//Map writeMap = new TreeMap();

	List<String> addressValues = new ArrayList<String>();
	List<String> cliName = new ArrayList<String>();
	List<String> numClie = new ArrayList<String>();
	List<String> localidade = new ArrayList<String>();
	List<String> nif = new ArrayList<String>();
	List<String> telephone = new ArrayList<String>();
	List<String> codPostal = new ArrayList<String>();
	
	static List<String> notProcessedClient = new ArrayList<String>();
	static List<String> notProcessedName = new ArrayList<String>();
	static List<String> notProcessedAddress = new ArrayList<String>();
	static List<String> notProcessedLocal = new ArrayList<String>();
	static List<String> notProcessedNIF = new ArrayList<String>();
	static List<String> notProcessedTel = new ArrayList<String>();
	static List<String> notProcessedCodPostal = new ArrayList<String>();

	List<String> centralAddress = new ArrayList<String>();
	List<String> centralClient = new ArrayList<String>();
	List<String> centralName = new ArrayList<String>();
	List<String> centralTel = new ArrayList<String>();

	List<String> inoColCliente = new ArrayList<String>();
	List<String> inoPortaNum = new ArrayList<String>();
	List<String> inoNewAddress = new ArrayList<String>();
	List<String> inoFloorNum = new ArrayList<String>();
	List<String> inoLocal = new ArrayList<String>();
	List<String> inoCodigoPost = new ArrayList<String>();

	
	
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
					addressValues.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 3) {
					
					// in this case run the method to change the incorrect entries
					localidade.add(localChange(listValues.get(i).toString().toUpperCase()));
				} else if (i == 4) {
					nif.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 5) {
					telephone.add(listValues.get(i).toString().toUpperCase());
				} else if (i == 6) {
					codPostal.add(listValues.get(i).toString().toUpperCase());
				}
			}

		}// end of outer for

		// go through all addresses, find door number and separate
		for (String addressStr : addressValues) {

			
			// correct all addresses using method changeStreet
			subAddress.add(changeStreet(addressStr));
			subAddr = changeStreet(addressStr);
						
			//System.out.println(subAddress);
			// try{

			// for every address instantiate the FindPattern class that will search the
			// address with regular expressions for door no.
			FindPattern find = new FindPattern(subAddr);
			indDoorN.add(find.getIndDoorN());
			endIndexDoor.add(find.getEndIndexDoor());

		}
			// run the method that will concatenate some information after
			// getting the indexes
			mountString();

			// } catch (InterruptedException e) {
			// //
			// JOptionPane.showMessageDialog(null,"ERRO AO LER MORADA","ERRO",JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		
	}
	
	/**
	 * mountString method will concatenate the rest of the strings separated
	 * from door number and will add it to a list. It will also check if a
	 * door number exists and will use another list for a fail file.
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
					// list of addresses that were not processed
					notProcessedClient.add(numClie.get(index));
					notProcessedName.add(cliName.get(index));
					notProcessedAddress.add(addressValues.get(index));
					notProcessedLocal.add(localidade.get(index));
					notProcessedNIF.add(nif.get(index));
					notProcessedTel.add(telephone.get(index));
					notProcessedCodPostal.add(codPostal.get(index));

					// portaNum.add(" ");
					// floorNum.add(" ");
					// newAddress.add(inoAddressValues.get(index));

				} else if (codPostal.get(index).length() < 7){
					
					notProcessedClient.add(numClie.get(index));
					notProcessedName.add(cliName.get(index));
					notProcessedAddress.add(addressValues.get(index));
					notProcessedLocal.add(localidade.get(index));
					notProcessedNIF.add(nif.get(index));
					notProcessedTel.add(telephone.get(index));
					notProcessedCodPostal.add(codPostal.get(index));

					
				} else {

					inoCodigoPost.add(codPostal.get(index));
					inoLocal.add(localidade.get(index));
					inoPortaNum.add(tempDoor.substring(indDoorN.get(index),
							endIndexDoor.get(index)));
					inoFloorNum
							.add(tempDoor.substring(endIndexDoor.get(index)));
					inoNewAddress
							.add(tempDoor.substring(0, indDoorN.get(index)));
					inoColCliente.add(numClie.get(index) + " "
							+ cliName.get(index) + " "
							+ (tempDoor.substring(endIndexDoor.get(index))));

					centralClient.add(numClie.get(index));
					centralName.add(cliName.get(index));
					centralAddress.add(addressValues.get(index));
					centralTel.add(telephone.get(index));

				}

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

		// Set<String> keyset = writeMap.keySet();

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
				//	cell.setCellStyle();
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("MORADA");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("NºPORTA");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("LOCALIDADE");
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue("CODIGO POSTAL");
				cellnum++;

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
				cell.setCellValue((String) inoLocal.get(i-1));  // column 4
				++cellnum;
				cell = row.createCell(cellnum);
				cell.setCellValue((String) inoCodigoPost.get(i-1)); // column 5
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
