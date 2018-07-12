package application.org.mybatis.common.generator.mybatis.deal;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;

import application.org.mybatis.common.generator.mybatis.bean.ResultItem;
import application.org.mybatis.common.generator.mybatis.bean.TableTag;
import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.constants.TemplateConstant;
import application.org.mybatis.common.generator.mybatis.utils.VelocityUtils;
import application.org.mybatis.common.generator.mybatis.utils.XmlUtils;

public abstract class AbstractSqlMapperDeal {

	protected Document document;
	protected String fileName;
	protected String expandFileName;
	protected Element mapper;
	protected TableTag tableTag;

	public AbstractSqlMapperDeal() {

	}

	public AbstractSqlMapperDeal(String fileName, TableTag tableTag) {
		this.fileName = fileName;
		this.expandFileName = fileName.substring(0, fileName.length() - ".xml".length()) + "_expand.xml";
		this.tableTag = tableTag;
	}

	public void init() throws DocumentException {
		SAXReader reader = new SAXReader();
		this.document = reader.read(new File(this.fileName));
		mapper = this.document.getRootElement();
	}

	@SuppressWarnings("unchecked")
	public void createExpandXml() throws IOException {
		
		// 如果存在则不重写expand文件
		File expandFile = new File(expandFileName);
		if (expandFile.exists()) {
			return;
		}

		Document expandDocument = DocumentHelper.createDocument();
		DocumentType docType = this.document.getDocType();
		expandDocument.addDocType(docType.getElementName(), docType.getPublicID(), docType.getSystemID());

		Element expandMapper = expandDocument.addElement(mapper.getName());
		expandMapper.addAttribute("namespace", mapper.attributeValue("namespace"));
		
		
		//新增jsonb查询 add by vinsy 开始
		
		//获取表字段
		Element resultMap = this.mapper.element("resultMap");
		List<ResultItem> resultItemList = new ArrayList<ResultItem>();

		List<Element> elementList = resultMap.elements();
		for (Element element : elementList) {
			ResultItem item = new ResultItem(element.getName(), element.attributeValue("column"),
					element.attributeValue("property"), element.attributeValue("jdbcType"), element.attributeValue("javaType"));
			resultItemList.add(item);
		}
		
		//查询语句 getJsonbPageableList
		Element select = expandMapper.addElement("select");
		select.addAttribute("id", "getJsonbPageableList");
		select.addAttribute("parameterType", "java.util.Map");
		select.addAttribute("resultMap", "BaseResultMap");
		select.setText("select");
		Element include = select.addElement("include");
		include.addAttribute("refid", "Base_Column_List");
		select.setText("from " + tableTag.getTableName());
		//select.setText("select <include refid=\"Base_Column_List\"/>from " + tableTag.getTableName());
		
		Element where = select.addElement("where");
		
		for (ResultItem item : resultItemList) {
			Element ifElement = where.addElement("if");
			
			if("BIGINT".equals(item.getJdbcType())){
				ifElement.addAttribute("test", item.getProperty() + " != null ");
			}else{
				ifElement.addAttribute("test", item.getProperty() + " != null and " + item.getProperty()  + " != ''");				
			}
			
			String timeSuffix = "";
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.TIMESTAMP).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.TIME).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.DATE).equals(item.getJdbcType())){
				timeSuffix = ":: timestamp with time zone";
			}
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.OTHER).equals(item.getJdbcType()))
			{//新增jsonb扩展，add by vinsy
				ifElement.addText(" and (" +
						item.getColumn() + "->>'" + "param')::text = #{param,jdbcType=VARCHAR} ");
			}else{
				ifElement.addText(" and " +
						item.getColumn() + " = #{" + item.getProperty() + ",jdbcType=" + item.getJdbcType() + "} "+timeSuffix + " ");
			}
			
		}
		
		Element orderbyIfElement = select.addElement("if");
		orderbyIfElement.addAttribute("test", "orderby != null "); 
		orderbyIfElement.setText(" order by ${orderby} ");
		
		Element limitIfElement = select.addElement("if");
		limitIfElement.addAttribute("test", "limit != null "); 
		limitIfElement.setText(" limit ${limit} ");
       
		Element offsetIfElement = select.addElement("if");
		offsetIfElement.addAttribute("test", "offset != null "); 
		offsetIfElement.setText(" offset ${offset} ");
		
		//查询语句 getJsonbListCount
		Element select1 = expandMapper.addElement("select");
		select1.addAttribute("id", "getJsonbListCount");
		select1.addAttribute("parameterType", "java.util.Map");
		select1.addAttribute("resultType", "java.lang.Long");
		select1.setText("select count(*) from " + tableTag.getTableName());
		Element where1 = select1.addElement("where");
		
		for (ResultItem item : resultItemList) {
			Element ifElement = where1.addElement("if");
			
			if("BIGINT".equals(item.getJdbcType())){
				ifElement.addAttribute("test", item.getProperty() + " != null ");
			}else{
				ifElement.addAttribute("test", item.getProperty() + " != null and " + item.getProperty()  + " != ''");				
			}
			
			String timeSuffix = "";
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.TIMESTAMP).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.TIME).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.DATE).equals(item.getJdbcType())){
				timeSuffix = ":: timestamp with time zone";
			}
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.OTHER).equals(item.getJdbcType()))
			{//新增jsonb扩展，add by vinsy
				ifElement.addText(" and (" +
						item.getColumn() + "->>'" + "param')::text = #{param,jdbcType=VARCHAR} ");
			}else{
				ifElement.addText(" and " +
						item.getColumn() + " = #{" + item.getProperty() + ",jdbcType=" + item.getJdbcType() + "} "+timeSuffix + " ");
			}
			
		}
		
		//新增jsonb查询 add by vinsy 结束
		
		
		
		XmlUtils.writeDocument2Xml(expandDocument, this.expandFileName);
	}

	@SuppressWarnings("unchecked")
	public void rewriteXml() throws Exception {
		Element resultMap = this.mapper.element("resultMap");
		String parameterType = resultMap.attributeValue("type");

		List<ResultItem> resultItemList = new ArrayList<ResultItem>();

		List<Element> elementList = resultMap.elements();
		for (Element element : elementList) {
			ResultItem item = new ResultItem(element.getName(), element.attributeValue("column"),
					element.attributeValue("property"), element.attributeValue("jdbcType"), element.attributeValue("javaType"));
			resultItemList.add(item);
		}

		Element sql = this.createQueryElement(resultItemList);
//		Element pageElement = this.createPageableListSelect(parameterType, this.tableTag.getTableName());
//		Element countElement = this.createListCount(this.tableTag.getTableName());
//		Element idsElement = this.selectByIdCollection(this.tableTag.getTableName());

		List<String> lineList = FileUtils.readLines(new File(this.fileName), "UTF-8");

		String str = "</resultMap>";
		for (int i = 0; i < lineList.size(); i++) {
			String line = lineList.get(i);
			if (StringUtils.isNotBlank(line) && line.endsWith(str)) {
				lineList.add(i + 1, XmlUtils.writeElement2String(sql));
				break;
			}
		}
		
		String insertStr = "<insert";
		String addStr = "useGeneratedKeys=\"true\" keyProperty=\"id\"";
		for (int i = 0; i < lineList.size(); i++) {
			String line = lineList.get(i);
			if (StringUtils.isNotBlank(line) && line.indexOf(insertStr) != -1) {
				String begin = line.substring(0, line.indexOf(">"));
				lineList.set(i,begin + addStr + ">");
				continue;
			}
		}
		
		
		
//		lineList.add(lineList.size() - 1, XmlUtils.writeElement2String(pageElement));
//		lineList.add(lineList.size() - 1, XmlUtils.writeElement2String(countElement));
//		lineList.add(lineList.size() - 1, XmlUtils.writeElement2String(idsElement));
		lineList.add(lineList.size() - 1, this.getMapperXml(tableTag.getTableName(), parameterType));

		this.appendMapperXml(lineList, parameterType);

		FileUtils.writeLines(new File(fileName), "UTF-8", lineList);
	}

	protected void appendMapperXml(List<String> lineList, String parameterType) throws Exception {
	}

	private Element createQueryElement(List<ResultItem> resultItemList) {
		Element sql = DocumentHelper.createElement("sql");
		sql.addAttribute("id", "Query_Where_Clause");
		Element where = sql.addElement("where");
		for (ResultItem item : resultItemList) {
			Element ifElement = where.addElement("if");
			
			if("BIGINT".equals(item.getJdbcType())){
				ifElement.addAttribute("test", item.getProperty() + " != null ");
			}else{
				ifElement.addAttribute("test", item.getProperty() + " != null and " + item.getProperty()  + " != ''");				
			}
			
			String timeSuffix = "";
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.TIMESTAMP).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.TIME).equals(item.getJdbcType())||
					JdbcTypeNameTranslator.getJdbcTypeName(Types.DATE).equals(item.getJdbcType())){
				timeSuffix = ":: timestamp with time zone";
			}
			if(JdbcTypeNameTranslator.getJdbcTypeName(Types.OTHER).equals(item.getJdbcType()))
			{//新增jsonb扩展，add by vinsy
				ifElement.addText(" and " +
						item.getColumn() + " = #{" + item.getProperty() + ",jdbcType=" + item.getJdbcType() + ",javaType=" + item.getJavaType() + ", typeHandler=" + GeneratorConstants.JSONB_MAPPING + "} "+timeSuffix + " ");
			}else{
				ifElement.addText(" and " +
						item.getColumn() + " = #{" + item.getProperty() + ",jdbcType=" + item.getJdbcType() + "} "+timeSuffix + " ");
			}
			
		}

		return sql;
	}
	
	public static final String mapperFilePath = "mapper.txt";
	
	private String getMapperXml(String tableName, String modelFullName) throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(TemplateConstant.MODEL_FULL_NAME, modelFullName);
		params.put(TemplateConstant.TABLE_NAME, tableName);
		
		String xmlTemplateStr = VelocityUtils.replace(mapperFilePath, params);
		
		return xmlTemplateStr;
	}

	public static void main(String[] args) throws IOException {

	}
}
