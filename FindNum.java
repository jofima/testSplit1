package jofima;

//import java.util.ArrayList;
//import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindNum {

	private int startIndex;
	private int endIndex;
	
	
	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	
	/**
	 * use regular expressions to find what's needed in the address
	 * 
	 * @param findPat
	 */
	
	public FindNum(String findPat) {

		String pattern0 = "\\d+"; //( 1 or more digits )
		//String pattern1 = "(Nº\\d+[a-z]\\s)|(Nº\\s\\d+-[a-z]\\s)|(Nº\\s\\d+)|(\\sN\\s\\d+)|(nº\\d+)|(\\sNº:\\s\\d+)|(Nº.\\d+)|(N\\sº\\s\\d+)|(º\\s\\d+)|\\sN\\d+";
		
		Pattern p0 = Pattern.compile(pattern0, Pattern.CASE_INSENSITIVE);
		//Pattern p1 = Pattern.compile(pattern1, Pattern.CASE_INSENSITIVE);
		
		Matcher m0 = p0.matcher(findPat);
		//Matcher m1 = p1.matcher(findPat);
		
		
		int subIndex = 0;
		
		
		if (m0.find(subIndex)) {
			
			startIndex = (m0.start());
			endIndex = (m0.end());
		
			//System.out.println(findPat);
			//System.out.println(m0.group());
		
//		} else if (m1.find(subIndex)) {
//
//			//System.out.println(findPat);
//			//System.out.println(m1.group());
//
//			indDoorN = (m1.start());// start index number
//			endIndexDoor = (m1.end());// end index number
			// subIndex = 0;

						
		} else {
			startIndex = 0;
			endIndex = 0;
			// subIndex = 0;

		}
	}
}
