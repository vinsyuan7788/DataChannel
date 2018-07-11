package application.org.mybatis.common.generator.mybatis.deal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;

import application.org.mybatis.common.generator.mybatis.bean.GeneratorTag;
import application.org.mybatis.common.generator.mybatis.constants.AttrConstants;
import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.constants.TagConstants;
import application.org.mybatis.common.generator.mybatis.rewrite.RemarkDefaultCommentGenerator;
import application.org.mybatis.common.generator.mybatis.utils.XmlUtils;
import lombok.Setter;

public abstract class AbstractCreateGeneratorXml {

	public static final String COMMENT_GENERATOR_CLASS = RemarkDefaultCommentGenerator.class.getName();

	protected Document document;
	protected Element generatorElement;
	protected Element classPathEntry;
	protected Map<String, Element> contextMap;
	@Setter
	protected String projectPath;
	@Setter
	protected String rootClass;

	public AbstractCreateGeneratorXml() {
		this.document = DocumentHelper.createDocument();
		this.contextMap = new HashMap<String, Element>();
	}

	public void initDocType() {
		DOMDocumentType docType = new DOMDocumentType();
		docType.setElementName("generatorConfiguration");
		docType.setPublicID("-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN");
		docType.setSystemID("http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");

		document.addDocType(docType.getElementName(), docType.getPublicID(), docType.getSystemID());
	}

	public void initGeneratorElement() {
		this.generatorElement = document.addElement(TagConstants.GENERATOR_CONFIGURATION);
	}

	public void initClassPathEntry() {
		this.classPathEntry = this.generatorElement.addElement(TagConstants.CLASS_PATH_ENTRY);
		this.classPathEntry.addAttribute("location", GeneratorConstants.DRIVER_JAR_PATH);
	}

	public void initContext(String contextId) {
		Element context = generatorElement.addElement(TagConstants.CONTEXT);
		context.addAttribute("id", contextId);
		context.addAttribute("targetRuntime", "MyBatis3");

		this.contextMap.put(contextId, context);
	}

	public void initPlugin(String contextId) {
		Element context = this.contextMap.get(contextId);
		Element plugin = context.addElement(TagConstants.PLUGIN);
		plugin.addAttribute("type", "org.mybatis.generator.plugins.SerializablePlugin");
	}

	// 备注初始化配置
	public void initCommentGenerator(String contextId) {
		Element context = this.contextMap.get(contextId);
		Element commentGenerator = context.addElement(TagConstants.COMMENT_GENERATOR);
		commentGenerator.addAttribute("type", COMMENT_GENERATOR_CLASS);

		commentGenerator.add(XmlUtils.getSuppressAllCommentsElement());
	}

	public void initJdbcConnection(String contextId) {
		Element context = this.contextMap.get(contextId);
		Element jdbcConnection = context.addElement(TagConstants.JDBC_CONNECTION);
		jdbcConnection.addAttribute("driverClass", GeneratorConstants.DATASOURCE_DRIVER_CLASS_NAME);
		jdbcConnection.addAttribute("connectionURL", GeneratorConstants.DATASOURCE_URL);
		jdbcConnection.addAttribute("userId", GeneratorConstants.DATASOURCE_USERNAME);
		jdbcConnection.addAttribute("password", GeneratorConstants.DATASOURCE_PASSWORD);
	
		jdbcConnection.add(XmlUtils.getOracleRemarkElement());
		jdbcConnection.add(XmlUtils.getMysqlRemarkElement());
	}

	public void initGenerator(String contextId, GeneratorTag generatorTag) throws IOException {
		Element context = this.contextMap.get(contextId);
		Element generator = context.addElement(generatorTag.getTagName());
		generator.addAttribute(AttrConstants.TARGET_PACKAGE, generatorTag.getTargetPackage());

		// 如果没有则创建文件
		String targetProject = generatorTag.getTargetProject();
		File file = new File(targetProject);
		if (!file.exists()) {
			file.mkdirs();
		}

		generator.addAttribute(AttrConstants.TARGET_PROJECT, targetProject);

		if (StringUtils.isNotBlank(generatorTag.getType())) {
			generator.addAttribute("type", generatorTag.getType());
		}

		generator.add(XmlUtils.getEnableSubPackagesElement());

		Map<String, String> propertyMap = generatorTag.getPropertyMap();
		if (propertyMap != null && !propertyMap.isEmpty()) {
			for (Entry<String, String> entry : propertyMap.entrySet()) {
				Element property = DocumentHelper.createElement("property");
				property.addAttribute("name", entry.getKey());
				property.addAttribute("value", entry.getValue());

				generator.add(property);
			}
		}
	}

	public void initTable(String contextId, String tableName, String modelName) {
		Element context = this.contextMap.get(contextId);
		Element table = context.addElement(TagConstants.TABLE);
		table.addAttribute("schema", "");
		table.addAttribute("tableName", tableName);
		table.addAttribute(AttrConstants.DOMAIN_OBJECT_NAME, modelName);
		table.addAttribute("enableCountByExample", "false");
		table.addAttribute("enableUpdateByExample", "false");
		table.addAttribute("enableDeleteByExample", "false");
		table.addAttribute("enableSelectByExample", "false");
		table.addAttribute("selectByExampleQueryId", "false");
//		Element generatedKey = table.addElement("generatedKey");
//		generatedKey.addAttribute("column", "id");
//		generatedKey.addAttribute("sqlStatement", "select nextval('"+tableName+"_id_seq')");
//		generatedKey = table.addElement("generatedKey");
//		generatedKey.addAttribute("column", "id");
//		generatedKey.addAttribute("sqlStatement", "select currval('"+tableName+"_id_seq')");
	}

	public void initConfig() {
		// 初始化docType
		this.initDocType();
		// 初始化根节点
		this.initGeneratorElement();
		this.initClassPathEntry();
	}

	public void createDocument(String contextId, String modulePackage, Map<String, String> tableMap)
			throws IOException {
		this.initContext(contextId);
		this.initCommentGenerator(contextId);
		this.initJdbcConnection(contextId);

		Map<String, String> modelPropertyMap = new HashMap<String, String>();
		modelPropertyMap.put("trimStrings", "true");
		modelPropertyMap.put("rootClass", rootClass);
		GeneratorTag javaModelGenerator = new GeneratorTag(TagConstants.JAVA_MODEL_GENERATOR,
				this.projectPath + "src/main/java", modulePackage + ".api.model", modelPropertyMap);

		GeneratorTag sqlMapGenerator = new GeneratorTag(TagConstants.SQL_MAP_GENERATOR,
				this.projectPath + "src/main/resources", "mybatis.postgresql." + modulePackage, null);
		GeneratorTag javaClientGenerator = new GeneratorTag(TagConstants.JAVA_CLIENT_GENERATOR,
				this.projectPath + "src/main/java", modulePackage + ".provider.dao", null);
		javaClientGenerator.setType("XMLMAPPER");
		GeneratorTag serviceGenerator = new GeneratorTag(TagConstants.SERVICE_GENERATOR,
				this.projectPath + "src/main/java", modulePackage + ".api.service", null);
		GeneratorTag serviceImplGenerator = new GeneratorTag(TagConstants.SERVICE_IMPL_GENERATOR,
				this.projectPath + "src/main/java", modulePackage + ".provider.service", null);
		GeneratorTag controllerGenerator = new GeneratorTag(TagConstants.CONTROLLER_GENERATOR,
				this.projectPath + "src/main/java", modulePackage + ".gateway.web", null);

		List<GeneratorTag> generatorTagList = new ArrayList<GeneratorTag>();
		generatorTagList.add(javaModelGenerator);
		generatorTagList.add(sqlMapGenerator);
		generatorTagList.add(javaClientGenerator);
		generatorTagList.add(serviceGenerator);
		generatorTagList.add(serviceImplGenerator);
		generatorTagList.add(controllerGenerator);
		for (GeneratorTag tag : generatorTagList) {
			this.initGenerator(contextId, tag);
		}

		if (tableMap == null || tableMap.isEmpty()) {
			return;
		}

		for (Entry<String, String> entry : tableMap.entrySet()) {
			this.initTable(contextId, entry.getKey(), entry.getValue());
		}
	}

	public void writeDocumentToXml(String fileName) throws IOException {
		XmlUtils.writeDocument2Xml(document, fileName);
	}
}