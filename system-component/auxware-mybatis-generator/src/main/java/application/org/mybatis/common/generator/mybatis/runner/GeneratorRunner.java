package application.org.mybatis.common.generator.mybatis.runner;

import java.util.List;
import java.util.Map;

import application.org.mybatis.common.generator.mybatis.bean.GeneratorEntity;
import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.constants.GeneratorTableConstants;

public class GeneratorRunner extends AbstractGeneratorRunner {

	public static void main(String[] args) throws Exception {
		AbstractGeneratorRunner generatorRunner = new GeneratorRunner();
		generatorRunner.setProjectPath(GeneratorConstants.BAS_PROJECT_PATH);
		generatorRunner.generator();
	}

	public void generator() throws Exception {
		List<GeneratorEntity> generatorEntityList = GeneratorTableConstants.generatorEntityList;
		if (generatorEntityList == null) {
			return;
		}

		for (GeneratorEntity generatorEntity : generatorEntityList) {
			String modulePackage = generatorEntity.getModulePackage();
			String contextName = generatorEntity.getContextName();
			Map<String, String> tableMap = generatorEntity.getTableMap();
			if(tableMap != null && !tableMap.isEmpty()){
				super.run(modulePackage, contextName, tableMap);				
			}
			
			Map<String, String> treeTableMap = generatorEntity.getTreeTableMap();
			if(treeTableMap != null && !treeTableMap.isEmpty()){
				super.runTree(modulePackage, contextName, treeTableMap);				
			}
			
		}
	}

}
