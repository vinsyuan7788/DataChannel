package application.org.mybatis.common.generator.mybatis.runner;

import java.io.IOException;
import java.util.Map;

import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.deal.AbstractCreateGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.AbstractReadGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.common.BaseCreateGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.common.BaseReadGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.tree.TreeCreateGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.tree.TreeReadGeneratorXml;
import lombok.Setter;

public abstract class AbstractGeneratorRunner {

	@Setter
	private String projectPath;

	public void createXml(String modulePackage, String contextName, Map<String, String> tableMap) throws IOException {
		// 生成xml
		AbstractCreateGeneratorXml greateGeneratorXml = new BaseCreateGeneratorXml();

		greateGeneratorXml.initConfig();
		greateGeneratorXml.setProjectPath(this.projectPath);
		greateGeneratorXml.createDocument(contextName, modulePackage, tableMap);

		// 写入xml文件中
		greateGeneratorXml.writeDocumentToXml(GeneratorConstants.WRITE_XML_FILE_NAME);
	}

	public void createTreeXml(String modulePackage, String contextName, Map<String, String> tableMap)
			throws IOException {
		// 生成xml
		AbstractCreateGeneratorXml greateGeneratorXml = new TreeCreateGeneratorXml();

		greateGeneratorXml.initConfig();
		greateGeneratorXml.setProjectPath(this.projectPath);
		greateGeneratorXml.createDocument(contextName, modulePackage, tableMap);

		// 写入xml文件中
		greateGeneratorXml.writeDocumentToXml(GeneratorConstants.WRITE_XML_FILE_NAME);
	}

	//废弃
	public void run(String modulePackage, String contextName, Map<String, String> tableMap) throws Exception {
		// 生成xml
		this.createXml(modulePackage, contextName, tableMap);

		// 运行xml,生成文件
		AbstractReadGeneratorXml readGeneratorXml = new BaseReadGeneratorXml();
		readGeneratorXml.generator(GeneratorConstants.READ_XML_FILE_NAME);
	}

	public void runJsonb(String modulePackage, String contextName, Map<String, String> tableMap,String jsonScheamName) throws Exception {
		// 生成xml
		this.createXml(modulePackage, contextName, tableMap);

		// 运行xml,生成文件
		AbstractReadGeneratorXml readGeneratorXml = new BaseReadGeneratorXml();
		readGeneratorXml.generator(GeneratorConstants.READ_XML_FILE_NAME, jsonScheamName);
	}
	
	@SuppressWarnings("unused")
	public void runTree(String modulePackage, String contextName, Map<String, String> tableMap) throws Exception {
		// 生成xml
		this.createTreeXml(modulePackage, contextName, tableMap);

		// 运行xml,生成文件
		AbstractReadGeneratorXml readGeneratorXml = new TreeReadGeneratorXml();
		//readGeneratorXml.generator(GeneratorConstants.READ_XML_FILE_NAME);
	}

	public abstract void generator() throws Exception;
}
