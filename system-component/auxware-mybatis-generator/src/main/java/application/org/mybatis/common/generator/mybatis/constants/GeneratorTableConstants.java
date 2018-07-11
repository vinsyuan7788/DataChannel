package application.org.mybatis.common.generator.mybatis.constants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import application.io.spring.common.utils.file.CommonStreamUtil;
import application.org.mybatis.common.generator.mybatis.bean.GeneratorEntity;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GeneratorTableConstants {
	
	private GeneratorTableConstants(){}
	
	private enum GENERATOR_TYPE {MODULE, TABLE_COMMON, TABLE_TREE};
	private static final String TABLE_COMMON = "[table_common]";
	private static final String TABLE_TREE = "[table_tree]";
	
	// 连接远程库：无需修改
	public static List<GeneratorEntity> generatorEntityList;
	
	static{
		init();
	}
	
	private static void init(){
		generatorEntityList = new ArrayList<GeneratorEntity>();
		InputStream in = null;
		try {
			in = CommonStreamUtil.getInputStream(GeneratorConstants.GENERATOR_TABLE_CONF_NAME);
			List<String> configLines = IOUtils.readLines(in);
			
			GeneratorEntity entity = null;
			int i = 0;
			GENERATOR_TYPE type= null;
			while( i < configLines.size() ){
				String line = configLines.get(i);
				
				if(StringUtils.isBlank(line)){
					i += 1;
					continue;
				}
				if(line.startsWith("[module_")){
					type = GENERATOR_TYPE.MODULE;
					entity = new GeneratorEntity();
					generatorEntityList.add(entity);
				}else if(line.startsWith(TABLE_COMMON)){
					type = GENERATOR_TYPE.TABLE_COMMON;
					i += 1;
					continue;
				}else if(line.startsWith(TABLE_TREE)){
					type = GENERATOR_TYPE.TABLE_TREE;
					i += 1;
					continue;
				}
				
				switch (type){
				case MODULE:
					i += 1;
					line = configLines.get(i);
					entity.setModulePackage(line.split("=")[1]);
					i += 1;
					line = configLines.get(i);
					entity.setContextName(line.split("=")[1]);
					break;
				case TABLE_COMMON:
					entity.getTableMap().put(line.split("=")[0], line.split("=")[1]);
					break;
				case TABLE_TREE:
					entity.getTreeTableMap().put(line.split("=")[0], line.split("=")[1]);
					break;
				}
				
				i += 1;
			}
		} catch (Exception e) {
			log.error("",e);
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
	
//	public static void main(String[] args) {
//		System.out.println(new Gson().toJson(generatorEntityList));
//	}
	
}
