package application.org.mybatis.common.generator.mybatis.runner.project;

import java.util.HashMap;
import java.util.Map;

import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.runner.AbstractGeneratorRunner;

/**
 * 	Three conditions for running this runner
 *  -- Do the project-level configuration in "generator.properties"
 * 	-- Do the module-level configuration in this Java file
 * 	-- Follow some rules when creating the table that needs to be reverse-engineered
 * 	   -- MUST create a table with a JSONB-typed column
 *     -- Need to create a sequence relating to the table (for key auto-increment)
 *     -- Set the default value associated with the key in the corresponding table
 *     
 * @author vinsy
 *
 */
public class JsonbModuleRunner extends AbstractGeneratorRunner {

	public static void main(String[] args) throws Exception {
		JsonbModuleRunner generatorRunner = new JsonbModuleRunner();
		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
		generatorRunner.generator();
	}
	
	@Override
	public void generator() throws Exception {

		// 此处是"com.royalnu.模块名"
		String modulePackage = "application.io.spring.bottomware.authorization.business";	
		// 此处是"context_最小颗粒度的模块名"
		String contextName = "context_business";		
		Map<String, String> tableMap = new HashMap<String, String>();
		// 此处不要以"t_"开头，可能引起问题，参见@AbstractReadGeneratorXml#getRestPathByTableName
		tableMap.put("x_authorization_user_role_resource_data", "AuthorizationUserRoleResourceData");
		// 此处同模型名
		String jsonSchemaName = "AuthorizationUserRoleResourceData";
		// 生成模板
		super.runJsonb(modulePackage, contextName, tableMap, jsonSchemaName);
		// 打印信息
		System.out.println("Templates auto-generation completed successfully!");
	}
}