package application.io.spring.common.utils.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonStreamUtil {

	private CommonStreamUtil(){
		//私有构造
	}
	
	public static InputStream getInputStream(String fileName) throws FileNotFoundException {
		String classPathFileName = fileName;
		if (fileName.startsWith("/")) {
			classPathFileName = classPathFileName.substring(1);
		}
		String filePath = getRealPath() + classPathFileName;
		File configFile = new File(filePath);
		log.debug("filePath:"+filePath + ";configFile:"+configFile.getAbsolutePath()+"==configFile.exists()"+configFile.exists());
		if (configFile.exists()) {
			return new BufferedInputStream(new FileInputStream(configFile));
		} else {
			return CommonStreamUtil.class.getResourceAsStream(fileName);
		}
	}

	public static String getRealPath() {

		String realPath = CommonStreamUtil.class.getResource("/").getPath();
		log.debug("fileInfo:" + realPath);
		if(realPath.endsWith(".jar!/")){
			realPath = realPath.substring(0, realPath.length() - 1);
			realPath = realPath.substring(0, realPath.lastIndexOf("/") + 1);  
		}
		if(realPath.startsWith("file:/")){
			realPath = realPath.substring("file:".length());
		}
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			log.error("", e);
		}
		
		return realPath;

	}
}
