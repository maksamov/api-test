package hr.keeper.util;

public class UtilMethods {
	
	public static String removeSpaces(String value) {
		return value.replaceAll(" ", "");
	}
	
	public static boolean checkStringEqualityWithoutSpaces(String firstcode, String secondcode) {
		
		firstcode = removeSpaces(firstcode);
		secondcode = removeSpaces(secondcode);
		
		return firstcode.contentEquals(secondcode);
	}

}
