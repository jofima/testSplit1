package jofima;

//import java.util.ArrayList;
//import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPattern {

	//private List<Integer> indDoorN = new ArrayList<Integer>();
	//private List<Integer> endIndexDoor = new ArrayList<Integer>();
	
	private int subStart;
	private int subEnd;
	
	private int indDoorN;
	private int endIndexDoor;
	
	
	public int getIndDoorN() {
		return indDoorN;
	}

	public int getEndIndexDoor() {
		return endIndexDoor;
	}

	
	/**
	 * use regular expressions to find what's needed in the address
	 * Run another pattern to get just the digits - FindNum class
	 * 
	 * @param findPat
	 */
	
	public FindPattern(String findPat) {

		String pattern0 = "\\sN\\u00BA\\s\\d+"; //(space + N + � + space + 1 or more digits )
		String pattern1 = "(N�\\d+[a-z]\\s)|(N�\\s\\d+-[a-z]\\s)|(N�\\s\\d+)|(\\sN\\s\\d+)|(n�\\d+)|(\\sN�:\\s\\d+)|(N�.\\d+)|(N\\s�\\s\\d+)|(�\\s\\d+)|\\sN\\d+";
		String pattern2 = "(LOTE\\s\\d+)|(lOTE\\s\\d+)|(LOTE.\\d+)|(LOTE\\d+)|(Lt\\s\\d+)|(LT\\d+)|(LT.\\d+)|(LT\\s[A-Z])|(LOTE\\s[A-Z])|(LT-\\d+)|(\\sL-\\d+)|(L.-.\\d+)";
		String pattern3 = "(\\s\\d+\\w$)|(\\s\\d+,)|(\\sN\\d+$)|(\\d+$)|(\\s\\d+-)|(\\s\\d+\\s)";
		String pattern4 = "(,N\\d+)|(,N\\s\\d+)|(,\\s\\d+$)|(,\\s\\d+)|(,d+)|(\\s[A-Z]\\d+)";
		String pattern5 = "(RUA\\s\\d+)|(AVENIDA\\s\\d+)|(AV.\\s\\d+)";
//				+ ")|(AV.\\s\\d*)|(\\srua\\s\\d*)"
//				+ "|(av\\s\\d*)|([Rr]\\s\\d*)|(AVENIDA\\s\\d*)"
//				+ "|(Avenida\\s\\d*)|(avenida\\s\\d*)|(\\w\\s\\d*\\sDE)";

		Pattern p0 = Pattern.compile(pattern0, Pattern.CASE_INSENSITIVE);
		Pattern p1 = Pattern.compile(pattern1, Pattern.CASE_INSENSITIVE);
		Pattern p2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
		Pattern p3 = Pattern.compile(pattern3, Pattern.CASE_INSENSITIVE);
		Pattern p4 = Pattern.compile(pattern4, Pattern.CASE_INSENSITIVE);
		Pattern p5 = Pattern.compile(pattern5, Pattern.CASE_INSENSITIVE);

		Matcher m0 = p0.matcher(findPat);
		Matcher m1 = p1.matcher(findPat);
		Matcher m2 = p2.matcher(findPat);
		Matcher m3 = p3.matcher(findPat);
		Matcher m4 = p4.matcher(findPat);
		Matcher m5 = p5.matcher(findPat);

		
		int subIndex = 0;
		
		if (m5.find()){
		
			//System.out.println(findPat);
			//System.out.println("found" + m5.group());
			subIndex = m5.end();
			//System.out.println("found "+ subIndex);
		}
		// get the last index save use a substring from the last index
		// run a search again but add the last index for the ArrayList
		
		if (m0.find(subIndex)) {
			
			subStart = (m0.start());
			subEnd = (m0.end());
		
			
			FindNum numFound = new FindNum(findPat.substring(subStart, subEnd));
			
			indDoorN = subStart + numFound.getStartIndex();
			endIndexDoor = subStart + numFound.getEndIndex(); //
			//use and copy to other finds and test 
			
			//System.out.println(findPat);
			//System.out.println(m0.group());
		
		} else if (m1.find(subIndex)) {

			//System.out.println(findPat);
			//System.out.println(m1.group());

			subStart = (m1.start());
			subEnd = (m1.end());
		
			//System.out.println(subStart + " " + subEnd);
			
			FindNum numFound = new FindNum(findPat.substring(subStart, subEnd));
			
			indDoorN = subStart + numFound.getStartIndex();
			endIndexDoor = subStart + numFound.getEndIndex();

		} else if (m2.find(subIndex)) {

			subStart = (m2.start());
			subEnd = (m2.end());
			
			//System.out.println(subStart + " " + subEnd);
		
			FindNum numFound = new FindNum(findPat.substring(subStart, subEnd));
			
			indDoorN = subStart + numFound.getStartIndex();
			endIndexDoor = subStart + numFound.getEndIndex();

		} else if (m3.find(subIndex)) {

			subStart = (m3.start());
			subEnd = (m3.end());
		
			//System.out.println(subStart + " " + subEnd);
			
			FindNum numFound = new FindNum(findPat.substring(subStart, subEnd));
			
			indDoorN = subStart + numFound.getStartIndex();
			endIndexDoor = subStart + numFound.getEndIndex();
		
		
		} else if (m4.find(subIndex)) {

			subStart = (m4.start());
			subEnd = (m4.end());
		
			//System.out.println(subStart + " " + subEnd);
						
			FindNum numFound = new FindNum(findPat.substring(subStart, subEnd));
			
			indDoorN = subStart + numFound.getStartIndex();
			endIndexDoor = subStart + numFound.getEndIndex();
				
		} else {
			indDoorN = 0;
			endIndexDoor = 0;
			// subIndex = 0;

		}
	}
}
