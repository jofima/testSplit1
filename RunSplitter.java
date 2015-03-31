package jofima;


/**
 * Will launch the JFrame class that opens a Gui frame with file selection option
 * The chosen excel file is separated into a map and lists for better manipulation
 * After organizing the columns the files will get organized into rows and columns
 * and 2 excel files will be saved in the same folder of the selected file
 * 
 */

public class RunSplitter {
	
		
	public static void main (String [] args) {
		
		new NewFrame().runGui();// launch the Gui box with file selection
		
		//new GuiReadExcel().openGui();
		
		//new WriteExcelFile().writeFile();
		
		
	}
	
}

	

