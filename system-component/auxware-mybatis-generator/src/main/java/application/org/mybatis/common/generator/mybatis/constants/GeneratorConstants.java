package application.org.mybatis.common.generator.mybatis.constants;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GeneratorConstants {
	
	private static Properties prop;
	
	private GeneratorConstants(){}
	
	// 连接远程库：无需修改
	public static  String DRIVER_JAR_PATH;
	public static  String DATASOURCE_DRIVER_CLASS_NAME;
	public static  String DATASOURCE_URL;
	public static  String DATASOURCE_USERNAME;
	public static  String DATASOURCE_PASSWORD;

	// 需修改成本地配置，无需提交
	public static  String TEMP_PROJECT_PATH ;
	public static  String BAS_PROJECT_PATH;
	public static  String USR_PROJECT_PATH;
	public static String GENERATOR_TABLE_CONF_NAME;

	// 无需修改：放在文件末尾
	public static  String WRITE_XML_FILE_NAME;
	public static  String READ_XML_FILE_NAME;
	public static  String READ_XML_FILE_NAME_MYBATIS;
	//jsonb mapping类
	public static  String JSONB_MAPPING;
	//jsonb jsonschema文件路径
	public static  String JSON_SCHEMA_PATH;
	//rediskeyprefix文件路径
	public static  String REDIS_KEY_PREFIX_PATH;
	//rediskeyprefix文件名
	public static  String REDIS_KEY_PREFIX_FILE;
	//rediskeyprefix文件中所需字段的名称
	public static  String REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX;
	public static  String REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY;
	
	static{
		InputStream in = null;
		try{
			prop = getProperties("generator.properties");
			
			DRIVER_JAR_PATH= prop.getProperty("datasource.driverJarPath", "");
			DATASOURCE_DRIVER_CLASS_NAME= prop.getProperty("datasource.driverClassName", "");
			DATASOURCE_URL= prop.getProperty("datasource.url", "");
			DATASOURCE_USERNAME= prop.getProperty("datasource.username", "");
			DATASOURCE_PASSWORD= prop.getProperty("datasource.password", "");
			
			TEMP_PROJECT_PATH = prop.getProperty("TEMP_PROJECT_PATH", "");
			BAS_PROJECT_PATH = prop.getProperty("BAS_PROJECT_PATH", "");
			USR_PROJECT_PATH = prop.getProperty("USR_PROJECT_PATH", "");
			GENERATOR_TABLE_CONF_NAME = prop.getProperty("GENERATOR_TABLE_CONF_NAME", "");
			
			WRITE_XML_FILE_NAME = TEMP_PROJECT_PATH + "generatorConfig_share.xml";
			READ_XML_FILE_NAME = TEMP_PROJECT_PATH + "generatorConfig_share.xml";
			READ_XML_FILE_NAME_MYBATIS = TEMP_PROJECT_PATH + "generatorConfig_mybatis.xml";
			
			JSONB_MAPPING = prop.getProperty("JSONB_MAPPING", "");
			JSON_SCHEMA_PATH = prop.getProperty("JSON_SCHEMA_PATH", "");
			REDIS_KEY_PREFIX_FILE = prop.getProperty("REDIS_KEY_PREFIX_FILE", "");
			REDIS_KEY_PREFIX_PATH = prop.getProperty("REDIS_KEY_PREFIX_PATH", "");
			REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX = prop.getProperty("REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX", "");
			REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY = prop.getProperty("REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY", "");
		}catch(Exception e){
			log.error("",e);
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
	
	private static Properties getProperties(String propName) throws IOException{
		InputStream in = null;
		Properties prop = null;
		try{
			prop = new Properties();
			String propPath = GeneratorConstants.class.getResource("/").getPath() + propName;
			in = new BufferedInputStream (new FileInputStream(propPath));
			prop.load(in);
			return prop;
		}finally{
			IOUtils.closeQuietly(in);
		}
		
	}
	
}
