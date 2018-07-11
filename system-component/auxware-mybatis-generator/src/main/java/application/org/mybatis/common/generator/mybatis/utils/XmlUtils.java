package application.org.mybatis.common.generator.mybatis.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import application.org.mybatis.common.generator.mybatis.bean.GeneratorTag;
import application.org.mybatis.common.generator.mybatis.bean.TableTag;

public class XmlUtils {

	public static void writeDocument2Xml(Document document, String xmlFileName) throws IOException {
		// 创建字符串缓冲区
		// StringWriter stringWriter = new StringWriter();
		// 设置文件编码
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent("    ");

		// 创建写文件方法
		// XMLWriter xmlWriter = new XMLWriter(fileWriter,xmlFormat);
		FileOutputStream output = null;
		XMLWriter writer = null;

		try {
			output = new FileOutputStream(xmlFileName);
			writer = new XMLWriter(output, xmlFormat);
			writer.write(document);
		} finally {
			IOUtils.closeQuietly(output);
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static String writeElement2String(Element element) throws IOException {
		StringWriter stringWriter = new StringWriter();
		writeFormat(element, stringWriter);
		return stringWriter.toString();
	}

	public static String writeDocument2String(Document document) throws IOException {
		StringWriter stringWriter = new StringWriter();
		writeFormat(document, stringWriter);
		return stringWriter.toString();
	}

	private static void writeFormat(Node node, StringWriter stringWriter) throws IOException {
		// 设置文件编码
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent("    ");

		// 创建写文件方法
		XMLWriter writer = null;

		try {
			// 创建字符串缓冲区
			writer = new XMLWriter(stringWriter, xmlFormat);
			writer.write(node);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static Element getSuppressAllCommentsElement() {
		Element property = DocumentHelper.createElement("property");
		property.addAttribute("name", "suppressAllComments");
		property.addAttribute("value", "true");

		return property;
	}
	
	public static Element getOracleRemarkElement() {
		Element property = DocumentHelper.createElement("property");
		property.addAttribute("name", "remarksReporting");
		property.addAttribute("value", "true");

		return property;
	}
	
	public static Element getMysqlRemarkElement() {
		Element property = DocumentHelper.createElement("property");
		property.addAttribute("name", "useInformationSchema");
		property.addAttribute("value", "true");

		return property;
	}

	public static Element getEnableSubPackagesElement() {
		Element property = DocumentHelper.createElement("property");
		property.addAttribute("name", "enableSubPackages");
		property.addAttribute("value", "true");

		return property;
	}

	public static GeneratorTag getGeneratorTagByContext(Element context, String tagName) {
		Element element = context.element(tagName);
		String targetPackage = element.attributeValue("targetPackage");
		String targetProject = element.attributeValue("targetProject");

		GeneratorTag generatorTag = new GeneratorTag();
		generatorTag.setTagName(tagName);
		generatorTag.setTargetPackage(targetPackage);
		generatorTag.setTargetProject(targetProject);
		return generatorTag;
	}

	@SuppressWarnings("unchecked")
	public static List<TableTag> getTableTagByContext(Element context) {
		List<Element> tableList = context.elements("table");

		List<TableTag> tableTagList = new ArrayList<TableTag>();
		for (Element table : tableList) {
			String domainObjectName = table.attributeValue("domainObjectName");
			String tableName = table.attributeValue("tableName");

			TableTag tableTag = new TableTag();
			tableTag.setDomainObjectName(domainObjectName);
			tableTag.setTableName(tableName);
			tableTagList.add(tableTag);
		}

		return tableTagList;
	}
}
