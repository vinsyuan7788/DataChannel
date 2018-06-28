package application.io.spring.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * 	This is a class to provide utility regarding file
 * 
 * @author vinsy
 *
 */
public class FileUtils {

	/**
	 * 	This is a method to write an object to a path
	 * 
	 * @param object
	 * @param path
	 * @throws Exception 
	 */
	public static void writeObjectToPath(Object object, String path) throws Exception {
		
		// Get a file and create it if it does not exist
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		// Write the object to a specific path
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
