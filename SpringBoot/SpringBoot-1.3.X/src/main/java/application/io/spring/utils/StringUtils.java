package application.io.spring.utils;

/**
 * 	This is a class to provide utility regarding string
 * 
 * @author vinsy
 *
 */
public class StringUtils {

	/**
	 * 	This is a method to convert a String array to a string separated by comma
	 * 
	 * @param array
	 * @return
	 */
	public static String arrayToString(String[] array) {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i = 0; i < array.length; i++) {
			stringBuffer.append(array[i]);
			if (i < array.length - 1) stringBuffer.append(", ");
		}
		
		return stringBuffer.toString();
	}
}
