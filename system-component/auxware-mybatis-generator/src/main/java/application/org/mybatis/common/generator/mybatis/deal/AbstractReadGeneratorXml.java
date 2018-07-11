package application.org.mybatis.common.generator.mybatis.deal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.api.ShellRunner;

import application.org.mybatis.common.generator.mybatis.bean.GeneratorTag;
import application.org.mybatis.common.generator.mybatis.bean.TableTag;
import application.org.mybatis.common.generator.mybatis.constants.GeneratorConstants;
import application.org.mybatis.common.generator.mybatis.constants.TagConstants;
import application.org.mybatis.common.generator.mybatis.deal.utils.PackagePrefixUtils;
import application.org.mybatis.common.generator.mybatis.utils.XmlUtils;

@SuppressWarnings("unchecked")
public abstract class AbstractReadGeneratorXml {
	
	public static final String XMLFILENAME_BAK = GeneratorConstants.READ_XML_FILE_NAME_MYBATIS;
	private static final String DAO_NAME = "DAO.java";
	private static final String SERVICE_NAME = "Service.java";
	private static final String SERVICE_IMPL_NAME = "ServiceImpl.java";
	private static final String ENCODING = "UTF-8";

	protected String serviceTemplateFilePath;
	protected String serviceImplTemplateFilePath;
	protected String controllerTemplateFilePath;

	protected Document document;
	protected List<Element> contextList;
	protected String rootDaoName;
	protected String rootSimpleClass;

	private static GeneratorTag serviceGeneratorTag = null;
	private static GeneratorTag modelGeneratorTag = null;
	private static GeneratorTag serviceImplGeneratorTag = null;
	private static GeneratorTag daoGeneratorTag = null;	
	private static GeneratorTag controllerGeneratorTag = null;
	
	public void read(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		this.document = reader.read(new File(fileName));
	}

	public void initContext() {
		this.contextList = document.getRootElement().elements("context");
	}

/*	public void initServiceGenerator() throws IOException {
		for (Element context : contextList) {
			GeneratorTag serviceGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_GENERATOR);
			GeneratorTag modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_MODEL_GENERATOR);
			GeneratorTag serviceImplGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_IMPL_GENERATOR);
			GeneratorTag controllerGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.CONTROLLER_GENERATOR);

			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				String domainObjectName = tableTag.getDomainObjectName();
				String modelFullName = modelGeneratorTag.getTargetPackage() + "." + domainObjectName;
				String serviceName = domainObjectName + "Service";
				String serviceFullName = serviceGeneratorTag.getTargetPackage() + "." + serviceName;
				String firstLowServiceName = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
				File serviceFile = new File(this.getFullName(serviceGeneratorTag, domainObjectName, "Service111111111.java"));

				Map<String, Object> params = new HashMap<String, Object>();
				params.put(TemplateConstant.TARGET_PACKAGE, serviceGeneratorTag.getTargetPackage());
				params.put(TemplateConstant.MODEL_FULL_NAME, modelFullName);
				params.put(TemplateConstant.MODEL_NAME, domainObjectName);
				String serviceTemplate = VelocityUtil.replace(serviceTemplateFilePath, params);

				if (!serviceFile.exists()) {
					FileUtils.writeStringToFile(serviceFile, serviceTemplate);
				}

				File serviceImplFile = new File(
						this.getFullName(serviceImplGeneratorTag, domainObjectName, "ServiceImpl.java"));
				params.put(TemplateConstant.TARGET_PACKAGE, serviceImplGeneratorTag.getTargetPackage());
				params.put(TemplateConstant.SERVICE_FULL_NAME, serviceFullName);
				params.put(TemplateConstant.FIRST_LOW_SERVICE_NAME, firstLowServiceName);
				
				String serviceImplTemplate = VelocityUtil.replace(serviceImplTemplateFilePath, params);
				
				if (!serviceImplFile.exists()) {
					FileUtils.writeStringToFile(serviceImplFile, serviceImplTemplate);
				}

				String restPath = this.getRestPathByTableName(tableTag.getTableName());
				File controllerlFile = new File(
						this.getFullName(controllerGeneratorTag, domainObjectName, "Controller.java"));
				params.put(TemplateConstant.TARGET_PACKAGE, controllerGeneratorTag.getTargetPackage());
				params.put(TemplateConstant.REST_PATH, restPath);

				String controllerTemplate = VelocityUtil.replace(controllerTemplateFilePath, params);
				
				if (!controllerlFile.exists()) {
					FileUtils.writeStringToFile(controllerlFile, controllerTemplate);
				}
			}
		}
	}*/

	public void initServiceGenerator() throws IOException {
		for (Element context : contextList) {
			//GeneratorTag serviceGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
			//		TagConstants.SERVICE_GENERATOR);
			//GeneratorTag modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
			//		TagConstants.JAVA_MODEL_GENERATOR);

			serviceGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_GENERATOR);
			modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_MODEL_GENERATOR);
			serviceImplGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_IMPL_GENERATOR);
			daoGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_CLIENT_GENERATOR);
			controllerGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.CONTROLLER_GENERATOR);
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				
				String domainObjectName = tableTag.getDomainObjectName();
				String modelJava = modelGeneratorTag.getTargetPackage() + "." + domainObjectName;
				String servicePath = this.getFullName(serviceGeneratorTag, domainObjectName, SERVICE_NAME);
				
				
				File serviceFile = new File(servicePath);
				if (!serviceFile.exists()) {
					FileUtils.writeStringToFile(serviceFile, "");
				}
				List<String> resultList = new ArrayList<String>();
				//修改service生成兼容jsonb
				resultList.add("package " + serviceGeneratorTag.getTargetPackage() + ";");
				resultList.add("");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".api.service.BaseService;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".api.vo.PageVo;");
				resultList.add("import " + modelJava + ";");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a service interface auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This interface can be modified according to business requirement.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 该服务接口的内容可根据实际业务需求调整");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("public interface " + domainObjectName + "Service extends BaseService" + "<"
						+ domainObjectName + "> {");
				resultList.add("");
				resultList.add("	// 根据jsonb条件查询分页数据");
				resultList.add("	public PageVo<" + domainObjectName + "> getJsonbPageableList(" + domainObjectName + " " + toLowerCase(domainObjectName) + ",Long offset, Long limit, String orderby);");
				resultList.add("}");
				
				FileUtils.writeLines(serviceFile, ENCODING, resultList);
			}
		}
	}
	
	public void initServiceImplGenerator() throws IOException {
		for (Element context : contextList) {
			/*GeneratorTag serviceGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_GENERATOR);
			GeneratorTag modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_MODEL_GENERATOR);
			GeneratorTag serviceImplGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.SERVICE_IMPL_GENERATOR);
			GeneratorTag daoGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_CLIENT_GENERATOR);*/
			
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				
				String domainObjectName = tableTag.getDomainObjectName();
				String modelJava = modelGeneratorTag.getTargetPackage() + "." + domainObjectName;
				String serviceImplPath = this.getFullName(serviceImplGeneratorTag, domainObjectName, SERVICE_IMPL_NAME);
				
				File serviceImplFile = new File(serviceImplPath);
				if (!serviceImplFile.exists()) {
					FileUtils.writeStringToFile(serviceImplFile, "");
				}
				List<String> resultList = new ArrayList<String>();
				
				//获取jsonb字段
				String modelPath = this.getFullName(modelGeneratorTag, domainObjectName, ".java");
				File modelFile = new File(modelPath);
				List<String> lineList = FileUtils.readLines(modelFile, ENCODING);
				List<String> changeList = this.dealDAOFile(lineList, domainObjectName);
				List<String> jsonbFields = getJsonbFields(changeList);

				//修改serviceimpl生成兼容jsonb
				resultList.add("package " + serviceImplGeneratorTag.getTargetPackage() + ";");
				resultList.add("");
				resultList.add("import java.util.HashMap;");
				resultList.add("import java.util.Map;");
				resultList.add("import " + modelJava + ";");
				resultList.add("import javax.annotation.Resource;");
				resultList.add("import net.sf.json.JSONObject;");
				resultList.add("import org.springframework.stereotype.Service;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".utils.json.GsonUtils;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".api.vo.PageVo;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".provider.service.BaseServiceImpl;");
				resultList.add("import " + serviceGeneratorTag.getTargetPackage() + "." + domainObjectName + "Service;");
				resultList.add("import " + daoGeneratorTag.getTargetPackage() + "." + domainObjectName + "DAO;");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a service implementation class auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This class can be modified according to business requirement.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 该服务接口实现类的内容可根据实际业务需求调整");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("@Service(\"" + toLowerCase(domainObjectName) + "Service\")");
				resultList.add("public class " + domainObjectName + "ServiceImpl extends BaseServiceImpl" + "<"
						+ domainObjectName + "> implements " + domainObjectName + "Service" +  " {");
				resultList.add("");
				resultList.add("	@Resource");
				resultList.add("    private " + domainObjectName + "DAO " + toLowerCase(domainObjectName) + "DAO;");
				resultList.add("");
				resultList.add("	// 根据jsonb条件查询分页数据");
				resultList.add("	@SuppressWarnings(\"unchecked\")");
				resultList.add("	@Override");
				resultList.add("	public PageVo<" + domainObjectName + "> getJsonbPageableList(" + domainObjectName + " " + toLowerCase(domainObjectName) + ",Long offset, Long limit, String orderby){");
				resultList.add("		Map<String,Object> params = new HashMap<String,Object>();");
				resultList.add("		if (" + toLowerCase(domainObjectName) + " != null) {");
				resultList.add("    		params = GsonUtil.GSON.fromJson(GsonUtil.GSON.toJson(" + toLowerCase(domainObjectName) + "), Map.class); ");
				resultList.add("    	}");
				resultList.add("");
				resultList.add("		// 将json字符串转为map并将其作为查询条件");
				for(int i = 0; i < jsonbFields.size(); i++)
				{
					resultList.add("		JSONObject  jasonObject" + i + " = JSONObject.fromObject(" + toLowerCase(domainObjectName) + ".get" + toUpperCase(jsonbFields.get(i)) + "());");
					resultList.add("		Map<String,Object> map" + i + " = (Map<String,Object>)jasonObject" + i + ";");
					resultList.add("		for (Map.Entry<String,Object> entry" + i + " : map" + i + ".entrySet()) {");
					resultList.add("		    params.put(entry" + i + ".getKey(), entry" + i + ".getValue());");
					resultList.add("		}");
				}
				resultList.add("		params = this.getCondition(params, offset, limit, orderby);");
				resultList.add("    	PageVo<" + domainObjectName + "> pageVo = this.getPageVo(params);");
				resultList.add("    	pageVo.setRows(" + toLowerCase(domainObjectName) + "DAO.getJsonbPageableList(params));");
				resultList.add("    	pageVo.setTotal(" + toLowerCase(domainObjectName) + "DAO.getJsonbListCount(params));");
				resultList.add("    	return pageVo;");
				resultList.add("	}");
				resultList.add("}");
				
				FileUtils.writeLines(serviceImplFile, ENCODING, resultList);
			}
		}
	}
	
	
	private String getRestPathByTableName(String tableName) {
		String restPath = "/";
		String tempTableName = tableName;
		if( tableName.split("_").length == 3){
			tempTableName = tableName.substring(tableName.indexOf("_") + 1);
		}
		if(tempTableName.startsWith("t_")){
			tempTableName = tempTableName.substring(2);
		}
		restPath += tempTableName.substring(0, tempTableName.indexOf("_")) + "/"
				+ tempTableName.substring(tempTableName.indexOf("_") + 1);

		return restPath;
	}

	protected String getFullName(GeneratorTag generatorTag, String domainObjectName, String suffix) {
		return generatorTag.getTargetProject() + "/" + generatorTag.getTargetPackage().replaceAll("\\.", "/") + "/"
				+ domainObjectName + suffix;
	}

	/**
	* @Title: initControllerGenerator 
	* @Description: TODO(初始化Controller类) 
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unused")
	public void initControllerGenerator() throws IOException {
		for (Element context : contextList) {
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {			
				String domainObjectName = tableTag.getDomainObjectName();
				String controllerlPath = this.getFullName(controllerGeneratorTag, domainObjectName, "Controller.java");
				File controllerlFile = new File(controllerlPath);
				Boolean wasOverwritten = true;
				if (!controllerlFile.exists()) {
					FileUtils.writeStringToFile(controllerlFile, "");
					wasOverwritten = false;
				}
				
				//覆盖controllerlFile
				List<String> resultList = new ArrayList<String>();
				
				//获取jsonb字段
				String modelPath = this.getFullName(modelGeneratorTag, domainObjectName, ".java");
				File modelFile = new File(modelPath);
				List<String> lineList = FileUtils.readLines(modelFile, ENCODING);
				List<String> changeList = this.dealDAOFile(lineList, domainObjectName);
				List<String> jsonbFields = getJsonbFields(changeList);
				
				//包路径
				String packageString = GeneratorConstants.REDIS_KEY_PREFIX_PATH.replaceAll("/", ".");
				
				// 添加要写的内容
				resultList.add("package " + controllerGeneratorTag.getTargetPackage() + ";");
				resultList.add("");
				resultList.add("import java.util.HashMap;");
				resultList.add("import java.util.Map;");
				resultList.add("import java.util.List;");
				resultList.add("import javax.annotation.Resource;");
				resultList.add("import javax.servlet.http.HttpServletRequest;");
				resultList.add("import javax.servlet.http.HttpServletResponse;");
				resultList.add("import javax.servlet.http.HttpSession;");
				resultList.add("import org.springframework.beans.factory.annotation.Autowired;");
				resultList.add("import org.springframework.web.bind.annotation.RequestMapping;");
				resultList.add("import org.springframework.web.bind.annotation.RequestMethod;");
				resultList.add("import org.springframework.web.bind.annotation.RequestParam;");
				resultList.add("import org.springframework.web.bind.annotation.RestController;");
				resultList.add("import com.fasterxml.jackson.core.JsonProcessingException;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".exception.CommonException;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".utils.json.GsonUtils;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".validator.JsonSchemaValidator;");
				resultList.add("import " + PackagePrefixUtils.CACHE_PACKAGE_PREFIX + ".redis.service.StringRedisCache;");
				resultList.add("import " + PackagePrefixUtils.CACHE_PACKAGE_PREFIX + ".utils.FileRedisCacheUtils;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".gateway.web.BaseController;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".api.vo.PageVo;");
				resultList.add("import " + modelGeneratorTag.getTargetPackage() + "." + domainObjectName + ";");
				resultList.add("import " + serviceGeneratorTag.getTargetPackage() + "." + domainObjectName + "Service;");
				resultList.add("import " + packageString +"FilePath;");
				resultList.add("import " + packageString +"InitializationUtil;");
				resultList.add("import " + packageString + GeneratorConstants.REDIS_KEY_PREFIX_FILE + ";");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a controller class auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This class can be modified according to business requirement.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 该控制器类的内容可根据实际业务需求调整");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("@SuppressWarnings(\"unused\")");
				resultList.add("@RestController");
				resultList.add("@RequestMapping(value = \"" + this.getRestPathByTableName(tableTag.getTableName()) + "\")");
				resultList.add("public class " + domainObjectName +"Controller extends BaseController<" + domainObjectName + "> {");
				resultList.add("");
				resultList.add("	@Autowired");
				resultList.add("	private FilePath filePath;");
				resultList.add("	@Resource");
				resultList.add("	private " + domainObjectName + "Service " + toLowerCase(domainObjectName) +"Service;");
				resultList.add("	@Resource");
				resultList.add("	private StringRedisCache stringRedisCache;");
				resultList.add("");
				resultList.add("	@Autowired");
				resultList.add("	private HttpSession session;");
				resultList.add("	@Autowired");
				resultList.add("	private HttpServletRequest request;");
				resultList.add("	@Autowired");
				resultList.add("	private HttpServletResponse response;");
				resultList.add("");
				resultList.add("	// 测试增加一条记录并查询：提交代码时必须注释或删除该方法");
				resultList.add("	@RequestMapping(value = \"/test_add_and_query\", method = RequestMethod.GET)");
				resultList.add("	public Map<String, Object> insertAndQuery(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws Exception {");
				resultList.add("");
				resultList.add("		// Initialization of maps to store results and return data to front-end");
				resultList.add("		Map<String, Object> result = new HashMap<>();");
				resultList.add("		Map<String, Object> data = new HashMap<>();");
				resultList.add("");
				resultList.add("		// Insert a record and query the corresponding table");
				resultList.add("		try {");
				resultList.add("			" + toLowerCase(domainObjectName) + "Service.insertSelective(" + toLowerCase(domainObjectName) + ");");
				resultList.add("			List<" + domainObjectName + "> " + toLowerCase(domainObjectName) + "s = " + toLowerCase(domainObjectName) + "Service.getList(null);");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", " + toLowerCase(domainObjectName) + "s);");
				resultList.add("			result.put(\"message\", \"success\");");
				resultList.add("		} catch (Exception e) {");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", null);");
				resultList.add("			result.put(\"message\", e.getMessage());");
				resultList.add("		}");
				resultList.add("");
				resultList.add("		// Return stored results to front-end");
				resultList.add("		result.put(\"" + toLowerCase(domainObjectName) + "\", " + toLowerCase(domainObjectName) + ");");
				resultList.add("		data.put(\"result\", result);");
				resultList.add("		return data;");
				resultList.add("	}");
				resultList.add("");
				resultList.add("	// 测试删除所有记录并查询：提交代码时必须注释或删除该方法");
				resultList.add("	@RequestMapping(value = \"/test_delete_and_query\", method = RequestMethod.GET)");
				resultList.add("	public Map<String, Object> deleteAndQuery(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws Exception {");
				resultList.add("");
				resultList.add("		// Initialization of maps to store results and return data to front-end");
				resultList.add("		Map<String, Object> result = new HashMap<>();");
				resultList.add("		Map<String, Object> data = new HashMap<>();");
				resultList.add("");
				resultList.add("		// Delete all records and query the corresponding table");
				resultList.add("		try {");
				resultList.add("			" + domainObjectName + " condition = new " + domainObjectName + "();");
				resultList.add("			" + toLowerCase(domainObjectName) + "Service.deleteByCondition(condition);");
				resultList.add("			List<" + domainObjectName + "> " + toLowerCase(domainObjectName) + "s = " + toLowerCase(domainObjectName) + "Service.getList(null);");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", " + toLowerCase(domainObjectName) + "s);");
				resultList.add("			result.put(\"message\", \"success\");");
				resultList.add("		} catch (Exception e) {");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", null);");
				resultList.add("			result.put(\"message\", e.getMessage());");
				resultList.add("		}");
				resultList.add("");
				resultList.add("		// Return stored results to front-end");
				resultList.add("		result.put(\"" + toLowerCase(domainObjectName) + "\", " + toLowerCase(domainObjectName) + ");");
				resultList.add("		data.put(\"result\", result);");
				resultList.add("		return data;");
				resultList.add("	}");
				resultList.add("}");
				resultList.add("");		
				
				// 写到文件中
				FileUtils.writeLines(controllerlFile, ENCODING, resultList);
				
				// 打印信息
				if (wasOverwritten == true) {
					System.out.println("Existing file " + controllerlFile + " was overwritten");
				}
			}
		}
	}
	
	/**
	* @Title: initControllerGenerator 
	* @Description: TODO(初始化Controller类) 
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void initControllerGenerator(String jsonScheamName) throws IOException {
		for (Element context : contextList) {
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {			
				String domainObjectName = tableTag.getDomainObjectName();
				String controllerlPath = this.getFullName(controllerGeneratorTag, domainObjectName, "Controller.java");
				File controllerlFile = new File(controllerlPath);
				Boolean wasOverwritten = true;
				if (!controllerlFile.exists()) {
					FileUtils.writeStringToFile(controllerlFile, "");
					wasOverwritten = false;
				}
				
				//覆盖controllerlFile
				List<String> resultList = new ArrayList<String>();
				
				//获取jsonb字段
				String modelPath = this.getFullName(modelGeneratorTag, domainObjectName, ".java");
				File modelFile = new File(modelPath);
				List<String> lineList = FileUtils.readLines(modelFile, ENCODING);
				List<String> changeList = this.dealDAOFile(lineList, domainObjectName);
				List<String> jsonbFields = getJsonbFields(changeList);
				
				//包路径
				String packageString = GeneratorConstants.REDIS_KEY_PREFIX_PATH.replaceAll("/", ".");
				
				// 添加要写的内容
				resultList.add("package " + controllerGeneratorTag.getTargetPackage() + ";");
				resultList.add("");
				resultList.add("import java.util.HashMap;");
				resultList.add("import java.util.Map;");
				resultList.add("import java.util.List;");
				resultList.add("import javax.annotation.Resource;");
				resultList.add("import javax.servlet.http.HttpServletRequest;");
				resultList.add("import javax.servlet.http.HttpServletResponse;");
				resultList.add("import javax.servlet.http.HttpSession;");
				resultList.add("import org.springframework.beans.factory.annotation.Autowired;");
				resultList.add("import org.springframework.web.bind.annotation.RequestMapping;");
				resultList.add("import org.springframework.web.bind.annotation.RequestMethod;");
				resultList.add("import org.springframework.web.bind.annotation.RequestParam;");
				resultList.add("import org.springframework.web.bind.annotation.RestController;");
				resultList.add("import com.fasterxml.jackson.core.JsonProcessingException;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".exception.CommonException;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".utils.json.GsonUtils;");
				resultList.add("import " + PackagePrefixUtils.COMMON_PACKAGE_PREFIX + ".validator.JsonSchemaValidator;");
				resultList.add("import " + PackagePrefixUtils.CACHE_PACKAGE_PREFIX + ".redis.service.StringRedisCache;");
				resultList.add("import " + PackagePrefixUtils.CACHE_PACKAGE_PREFIX + ".utils.FileRedisCacheUtils;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".gateway.web.BaseController;");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".api.vo.PageVo;");
				resultList.add("import " + modelGeneratorTag.getTargetPackage() + "." + domainObjectName + ";");
				resultList.add("import " + serviceGeneratorTag.getTargetPackage() + "." + domainObjectName + "Service;");
				resultList.add("import " + packageString +"FilePath;");
				resultList.add("import " + packageString +"InitializationUtil;");
				resultList.add("import " + packageString + GeneratorConstants.REDIS_KEY_PREFIX_FILE + ";");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a controller class auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This class can be modified according to business requirement.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 该控制器类的内容可根据实际业务需求调整");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("@SuppressWarnings(\"unused\")");
				resultList.add("@RestController");
				resultList.add("@RequestMapping(value = \"" + this.getRestPathByTableName(tableTag.getTableName()) + "\")");
				resultList.add("public class " + domainObjectName +"Controller extends BaseController<" + domainObjectName + "> {");
				resultList.add("");
				resultList.add("	@Autowired");
				resultList.add("	private FilePath filePath;");
				resultList.add("	@Resource");
				resultList.add("	private " + domainObjectName + "Service " + toLowerCase(domainObjectName) +"Service;");
				resultList.add("	@Resource");
				resultList.add("	private StringRedisCache stringRedisCache;");
				resultList.add("");
				resultList.add("	@Autowired");
				resultList.add("	private HttpSession session;");
				resultList.add("	@Autowired");
				resultList.add("	private HttpServletRequest request;");
				resultList.add("	@Autowired");
				resultList.add("	private HttpServletResponse response;");
				resultList.add("");
				resultList.add("	// 测试增加一条记录并查询：提交代码时必须注释或删除该方法");
				resultList.add("	@RequestMapping(value = \"/test_add_and_query\", method = RequestMethod.GET)");
				resultList.add("	public Map<String, Object> insertAndQuery(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws Exception {");
				resultList.add("");
				resultList.add("		// Initialization of maps to store results and return data to front-end");
				resultList.add("		Map<String, Object> result = new HashMap<>();");
				resultList.add("		Map<String, Object> data = new HashMap<>();");
				resultList.add("");
				resultList.add("		// Insert a record and query the corresponding table");
				resultList.add("		try {");
				resultList.add("			" + toLowerCase(domainObjectName) + "Service.insertSelective(" + toLowerCase(domainObjectName) + ");");
				resultList.add("			List<" + domainObjectName + "> " + toLowerCase(domainObjectName) + "s = " + toLowerCase(domainObjectName) + "Service.getList(null);");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", " + toLowerCase(domainObjectName) + "s);");
				resultList.add("			result.put(\"message\", \"success\");");
				resultList.add("		} catch (Exception e) {");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", null);");
				resultList.add("			result.put(\"message\", e.getMessage());");
				resultList.add("		}");
				resultList.add("");
				resultList.add("		// Return stored results to front-end");
				resultList.add("		result.put(\"" + toLowerCase(domainObjectName) + "\", " + toLowerCase(domainObjectName) + ");");
				resultList.add("		data.put(\"result\", result);");
				resultList.add("		return data;");
				resultList.add("	}");
				resultList.add("");
				resultList.add("	// 测试删除所有记录并查询：提交代码时必须注释或删除该方法");
				resultList.add("	@RequestMapping(value = \"/test_delete_and_query\", method = RequestMethod.GET)");
				resultList.add("	public Map<String, Object> deleteAndQuery(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws Exception {");
				resultList.add("");
				resultList.add("		// Initialization of maps to store results and return data to front-end");
				resultList.add("		Map<String, Object> result = new HashMap<>();");
				resultList.add("		Map<String, Object> data = new HashMap<>();");
				resultList.add("");
				resultList.add("		// Delete all records and query the corresponding table");
				resultList.add("		try {");
				resultList.add("			" + domainObjectName + " condition = new " + domainObjectName + "();");
				resultList.add("			" + toLowerCase(domainObjectName) + "Service.deleteByCondition(condition);");
				resultList.add("			List<" + domainObjectName + "> " + toLowerCase(domainObjectName) + "s = " + toLowerCase(domainObjectName) + "Service.getList(null);");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", " + toLowerCase(domainObjectName) + "s);");
				resultList.add("			result.put(\"message\", \"success\");");
				resultList.add("		} catch (Exception e) {");
				resultList.add("			result.put(\"" + toLowerCase(domainObjectName) + "s\", null);");
				resultList.add("			result.put(\"message\", e.getMessage());");
				resultList.add("		}");
				resultList.add("");
				resultList.add("		// Return stored results to front-end");
				resultList.add("		result.put(\"" + toLowerCase(domainObjectName) + "\", " + toLowerCase(domainObjectName) + ");");
				resultList.add("		data.put(\"result\", result);");
				resultList.add("		return data;");
				resultList.add("	}");
				resultList.add("");
				resultList.add("	// 新增");
				resultList.add("	@RequestMapping(value = \"/add" + domainObjectName + "\", method = RequestMethod.POST)");
				resultList.add("	public String add" + domainObjectName + "(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws CommonException, JsonProcessingException {");
				resultList.add("");
				resultList.add("		Map<String, Object> result = new HashMap<String, Object>();");
				resultList.add("		Map<String, Object> data = new HashMap<String, Object>();");
				resultList.add("		this.checkAdd(" + toLowerCase(domainObjectName) + ");");
				resultList.add("");
				resultList.add("		// 实际校验的json字符串");
				resultList.add("		String checkJson = " + toLowerCase(domainObjectName) + ".get" + toUpperCase(jsonbFields.get(0)) + "().toString();");
				resultList.add("		// 文件目录=系统根目录+文件存放相当路径");
				resultList.add("		String fileDirectory = InitializationUtil.getRootPath() + filePath.getFileRelativePath();");
				resultList.add("		// 从缓存获取校验json的schema");
				resultList.add("		String checkSchema = FileRedisCacheUtil.getFileContent(stringRedisCache,RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX + ", fileDirectory, RedisKeyPrefixUtil." + jsonScheamName + ");");
				resultList.add("		// 从待校验的json字符串解析出实际业务内容的key");
				resultList.add("		String jsonKey = RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + ";");
				resultList.add("		// 根据源json字符串，校验规则，实际业务内容key值返回校验结果和实际的业务json");
				resultList.add("		result = JsonSchemaValidator.validateJsonByFgeByJsonString(checkJson, checkSchema, jsonKey);");
				resultList.add("		if ((boolean) result.get(\"success\")) {");
				resultList.add("			" + toLowerCase(domainObjectName) + ".set" + toUpperCase(jsonbFields.get(0)) + "(result.get(RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + "));");
				resultList.add("    		" + toLowerCase(domainObjectName) + "Service.insertSelective(" + toLowerCase(domainObjectName) + ");");
				resultList.add("    	    data.put(\"code\", \"0\");");
				resultList.add("    	    data.put(\"msg\", \"添加成功\");");
				resultList.add("            return GsonUtil.GSON.toJson(data);");
				resultList.add("        } else {");
				resultList.add("        	 data.put(\"code\", \"1\");");
				resultList.add("        	 data.put(\"result\", result);");
				resultList.add("        	 data.put(\"msg\", \"添加失败\");");
				resultList.add("             return GsonUtil.GSON.toJson(data);");
				resultList.add("        }");
				resultList.add("	}");
				resultList.add("");
				resultList.add("	// 查询");
				resultList.add("	@RequestMapping(value = \"/get" + domainObjectName + "PageableList\", method = RequestMethod.GET)");
				resultList.add("	public String get" + domainObjectName + "PageableList(" + domainObjectName + " " + toLowerCase(domainObjectName) + ", @RequestParam(required = false) Long offset, @RequestParam(required = false) Long limit,String orderby) throws CommonException {");
				resultList.add("");
				resultList.add("		Map<String, Object> result = new HashMap<String, Object>();");
				resultList.add("		Map<String, Object> data = new HashMap<String, Object>();");
				resultList.add("		this.checkAdd(" + toLowerCase(domainObjectName) + ");");
				resultList.add("		String fileDirectory = InitializationUtil.getRootPath() + filePath.getFileRelativePath();");
				resultList.add("		result = JsonSchemaValidator.validateJsonByFgeByJsonString(" + toLowerCase(domainObjectName) + ".get" + toUpperCase(jsonbFields.get(0)) + "().toString(), FileRedisCacheUtil.getFileContent(stringRedisCache,RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX + ", fileDirectory, RedisKeyPrefixUtil." + jsonScheamName + "), RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + ");");
				resultList.add("		if ((boolean) result.get(\"success\")) {");
				resultList.add("			" + toLowerCase(domainObjectName) + ".set" + toUpperCase(jsonbFields.get(0)) + "(result.get(RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + "));");
				resultList.add("			PageVo<" + domainObjectName + "> pageVo = " + toLowerCase(domainObjectName) + "Service.getJsonbPageableList(" + toLowerCase(domainObjectName) + ", offset, limit, orderby);");
				resultList.add("    	    data.put(\"code\", \"0\");");
				resultList.add("    	    data.put(\"msg\", \"查询成功\");");
				resultList.add("    	    data.put(\"result\", pageVo);");
				resultList.add("            return GsonUtil.GSON.toJson(data);");
				resultList.add("        } else {");
				resultList.add("        	 data.put(\"code\", \"1\");");
				resultList.add("        	 data.put(\"result\", result);");
				resultList.add("        	 data.put(\"msg\", \"查询失败\");");
				resultList.add("             return GsonUtil.GSON.toJson(data);");
				resultList.add("        }");
				resultList.add("	}");
				resultList.add("");
				resultList.add("	// 更新");
				resultList.add("	@RequestMapping(value = \"/update" + domainObjectName + "\", method = RequestMethod.POST)");
				resultList.add("	public String update" + domainObjectName + "(" + domainObjectName + " " + toLowerCase(domainObjectName) + ") throws CommonException {");				
				resultList.add("");
				resultList.add("		Map<String, Object> result = new HashMap<String, Object>();");
				resultList.add("		Map<String, Object> data = new HashMap<String, Object>();");
				resultList.add("		this.checkAdd(" + toLowerCase(domainObjectName) + ");");
				resultList.add("		String fileDirectory = InitializationUtil.getRootPath() + filePath.getFileRelativePath();");
				resultList.add("		result = JsonSchemaValidator.validateJsonByFgeByJsonString(" + toLowerCase(domainObjectName) + ".get" + toUpperCase(jsonbFields.get(0)) + "().toString(), FileRedisCacheUtil.getFileContent(stringRedisCache,RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX + ", fileDirectory, RedisKeyPrefixUtil." + jsonScheamName + "), RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + ");");
				resultList.add("		if ((boolean) result.get(\"success\")) {");
				resultList.add("			" + toLowerCase(domainObjectName) + ".set" + toUpperCase(jsonbFields.get(0)) + "(result.get(RedisKeyPrefixUtil." + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + "));");
				resultList.add("    		" + toLowerCase(domainObjectName) + "Service.updateByPrimaryKeySelective(" + toLowerCase(domainObjectName) + ");");
				resultList.add("    	    data.put(\"code\", \"0\");");
				resultList.add("    	    data.put(\"msg\", \"修改成功\");");
				resultList.add("            return GsonUtil.GSON.toJson(data);");
				resultList.add("        } else {");
				resultList.add("        	 data.put(\"code\", \"1\");");
				resultList.add("        	 data.put(\"result\", result);");
				resultList.add("        	 data.put(\"msg\", \"修改失败\");");
				resultList.add("             return GsonUtil.GSON.toJson(data);");
				resultList.add("        }");
				resultList.add("	}");
				resultList.add("}");
				resultList.add("");		
				
				// 写到文件中
				FileUtils.writeLines(controllerlFile, ENCODING, resultList);
				
				// 打印信息
				if (wasOverwritten == true) {
					System.out.println("Existing file " + controllerlFile + " was overwritten");
				}
			}
		}
	}

	/**
	* @Title: initJsonSchema 
	* @Description: TODO(初始化jsonSchema文件) 
	* @param @param jsonScheamName
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void initJsonSchema(String jsonScheamName) throws IOException {
		String jsonSchemaPath = GeneratorConstants.BAS_PROJECT_PATH + GeneratorConstants.JSON_SCHEMA_PATH;
		
//		File jsonScheamFile = new File(jsonSchemaPath + jsonScheamName);
		File jsonScheamFile = new File(jsonSchemaPath + jsonScheamName + ".json");
		
		Boolean wasOverwritten = true;
		if (!jsonScheamFile.exists()) {
			FileUtils.writeStringToFile(jsonScheamFile, "");
			wasOverwritten = false;
		}
		
		// 添加要写的内容
		List<String> resultList = new ArrayList<String>();
		resultList.add("{");
		resultList.add("    \"$schema\": \"http://json-schema.org/draft-04/schema#\",");
		resultList.add("    \"title\": \"" + jsonScheamName + "\",");
		resultList.add("    \"description\": \"JSON数据格式验证\",");
		resultList.add("    \"type\": \"object\",");
		resultList.add("    \"properties\": {");
		resultList.add("        \"data\": {");
		resultList.add("            \"$ref\": \"#/definitions/mntent\"");
		resultList.add("        }");
		resultList.add("    },");
		resultList.add("    \"additionalProperties\": false,");
		resultList.add("    \"definitions\": {");
		resultList.add("        \"mntent\": {");
		resultList.add("            \"title\": \"mntent\",");
		resultList.add("            \"description\": \"An fstab entry\",");
		resultList.add("            \"type\": \"object\",");
		resultList.add("            \"properties\": {");
		resultList.add("				\"注释\": \"此处替换要校验的字段和规则\"");
		resultList.add("            },");
		resultList.add("            \"additionalItems\": false,");
		resultList.add("            \"required\":[],");
		resultList.add("            \"additionalProperties\": false");
		resultList.add("        }");
		resultList.add("    }");
		resultList.add("}");
		
		// 写到文件中
		FileUtils.writeLines(jsonScheamFile, ENCODING, resultList);	
		
		// 打印信息
		if (wasOverwritten == true) {
			System.out.println("Existing file " + jsonScheamFile + " was overwritten");
		}
	}
	
	/**
	* @Title: addRedisKeyPrefix 
	* @Description: TODO(添加) 
	* @param @param jsonScheamName
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void addRedisKeyPrefix(String jsonScheamName) throws IOException {
		
		String redisKeyPrefixPath = GeneratorConstants.BAS_PROJECT_PATH + "src/main/java/" + GeneratorConstants.REDIS_KEY_PREFIX_PATH;
		File redisKeyPrefixFile = new File(redisKeyPrefixPath + GeneratorConstants.REDIS_KEY_PREFIX_FILE + ".java");
		if (!redisKeyPrefixFile.exists()) {
			/*FileUtils.writeStringToFile(redisFile, "");
			//包路径
			String packageString = GeneratorConstants.REDIS_KEY_PREFIX_PATH.replaceAll("/", ".");
			List<String> resultList = new ArrayList<String>();
			resultList.add("package " + packageString + ";");
			resultList.add("");
			resultList.add("public class " + GeneratorConstants.REDIS_KEY_PREFIX_FILE +" {");
			resultList.add("");
			resultList.add("	//前端传过来的json真正业务逻辑的json key");
			resultList.add("	public static final String jsonDateKey = \"data\";");
			resultList.add("");
			resultList.add("	//文件存入缓存的key前缀");
			*/
			FileUtils.writeStringToFile(redisKeyPrefixFile, "");
			//包路径
			String packageString = GeneratorConstants.REDIS_KEY_PREFIX_PATH.replaceAll("/", ".");
			String targetPackage = packageString.substring(0, packageString.lastIndexOf("."));
			//拼写文件内容
			List<String> resultList = new ArrayList<String>();
			resultList.add("package " + targetPackage + ";");
			resultList.add("");	
			resultList.add("/**");
			resultList.add(" *	This is an utility class auto-generated by underlying framework. <br/>");
			resultList.add(" *	<p>");
			resultList.add(" *  This class can be modified according to business requirement.");
			resultList.add(" *  </p>");
			resultList.add(" *	");
			resultList.add(" * 	@ClassName: RedisKeyPrefixUtil");
			resultList.add(" *  @Description redis缓存前缀公共类，避免redis缓存key重复，建议key值所需的前缀都在此类配置");
			resultList.add(" *	@author vinsy");
			resultList.add(" *  @date 2018年7月11日 下午4:24:24 ");
			resultList.add(" *	");
			resultList.add(" *	@Description 以下仅以es-preferential-server中的RedisKeyPrefixUtil作为例子，更多用法参考es-api-server和es-manger-server");
			resultList.add(" *	@author vinsy");
			resultList.add(" *  @date 2018/03/23");
			resultList.add(" */");
			resultList.add("public class " + GeneratorConstants.REDIS_KEY_PREFIX_FILE + " {");
			resultList.add("");
			resultList.add("	//文件存入缓存的key前缀：以下值来自es-preferential-server，仅作举例用");
			resultList.add("	public static final String " + GeneratorConstants.REDIS_KEY_PREFIX_FILE_FILE_KEY_PREFIX + "=\"preferential-file-\";");
			resultList.add("");
			resultList.add("	//前端传过来的json真正业务逻辑的json key：以下值来自es-preferential-server，仅作举例用");
			resultList.add("	public static final String " + GeneratorConstants.REDIS_KEY_PREFIX_FILE_JSON_DATE_KEY + " = \"data\";");
			resultList.add("");
			resultList.add("	public static final String " + jsonScheamName + " = \"" + jsonScheamName + "\";");
			resultList.add("}");
			resultList.add("");
			FileUtils.writeLines(redisKeyPrefixFile, ENCODING, resultList);	
//			System.out.println(redisKeyPrefixFile.getPath());
		}else{
			List<String> lineList = FileUtils.readLines(redisKeyPrefixFile, ENCODING);
			List<String> resultList = new ArrayList<String>();
			for(int i = 0; i <= lineList.size(); i++)
			{
				String str = lineList.get(i);
				if("}".trim().equals(str))
				{
					resultList.add("");
					resultList.add("	public static final String " + jsonScheamName + " = \"" + jsonScheamName + "\";");
					resultList.add("}");
					FileUtils.writeLines(redisKeyPrefixFile, ENCODING, resultList);
					break;
				}else if(str.contains(jsonScheamName)){
					break;
					
				}else{
					resultList.add(str);
				}
			}
			System.out.println("Existing file " + redisKeyPrefixFile + " was modified");	
		}
	}
	
	
	
	public void initBak() throws IOException {
		for (Element context : contextList) {
			GeneratorTag daoGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_CLIENT_GENERATOR);

			List<Element> tableList = context.elements("table");

			for (Element table : tableList) {
				String domainObjectName = table.attributeValue("domainObjectName");

				String daoPath = this.getFullName(daoGeneratorTag, domainObjectName, DAO_NAME);

				File daoFile = new File(daoPath);
				if (daoFile.exists()) {
					FileUtils.writeByteArrayToFile(new File(daoPath + "_bak"), FileUtils.readFileToByteArray(daoFile));
				}
			}

		}
	}

	public void initRewriteXml() throws IOException {
		for (Element context : contextList) {
			Element serviceElement = context.element(TagConstants.SERVICE_GENERATOR);
			context.remove(serviceElement);

			Element serviceImplElement = context.element(TagConstants.SERVICE_IMPL_GENERATOR);
			context.remove(serviceImplElement);

			Element controllerElement = context.element(TagConstants.CONTROLLER_GENERATOR);
			context.remove(controllerElement);
		}

		XmlUtils.writeDocument2Xml(document, XMLFILENAME_BAK);
	}

	public void runShellGenerator() {
		String[] config = new String[] { "-configfile", XMLFILENAME_BAK, "-overwrite" };
		ShellRunner.main(config);
	}

	// 去掉model中的getter setter，并添加注解、序列化
	public void rewriteModel() throws IOException {
		
		for (Element context : contextList) {
			GeneratorTag modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_MODEL_GENERATOR);

			List<Element> tableList = context.elements("table");
			
			for (Element table : tableList) {
				String domainObjectName = table.attributeValue("domainObjectName");
				String modelPath = this.getFullName(modelGeneratorTag, domainObjectName, ".java");
				File modelFile = new File(modelPath);
				List<String> lineList = FileUtils.readLines(modelFile, ENCODING);
				List<String> resultList = this.dealDAOFile(lineList, domainObjectName);
				FileUtils.writeLines(modelFile, ENCODING, this.cleanBlankSpace(resultList));
			}
		}
	}

	private List<String> cleanBlankSpace(List<String> srcList) {
		List<String> lineList = new ArrayList<String>();
		String lastLine = "";
		for (String line : srcList) {
			if ("".equals(line) && "".equals(lastLine)) {
				continue;
			}
			lastLine = line;

			lineList.add(line);
		}

		return lineList;
	}

	private List<String> dealDAOFile(List<String> lineList, String domainObjectName) {
		List<String> resultList = new ArrayList<String>();
		String publicClass = "public class " + domainObjectName + " extends " + rootSimpleClass;
		boolean isGetSet = false;
		for (String line : lineList) {
			if (line.startsWith(publicClass)) {
				resultList.add("import lombok.Getter;");
				resultList.add("import lombok.Setter;");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a Java bean auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This Java bean corresponds to a table in a database.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 每个模型类对应一个数据库中的一张表");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("@Setter");
				resultList.add("@Getter");

				resultList.add(line);

				resultList.add("");
				resultList.add("	private static final long serialVersionUID = 1L;");
				resultList.add("");
				continue;
			}

			if (line.endsWith("{")) {
				isGetSet = true;
				continue;
			}

			if (line.startsWith("    }")) {
				isGetSet = false;
				continue;
			}

			if (isGetSet) {
				continue;
			}

			resultList.add(line);
		}

		return resultList;
	}

	public void rewriteDAO() throws IOException {
		for (Element context : contextList) {
			GeneratorTag modelGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_MODEL_GENERATOR);
			GeneratorTag daoGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_CLIENT_GENERATOR);
			
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				String domainObjectName = tableTag.getDomainObjectName();
				String modelJava = modelGeneratorTag.getTargetPackage() + "." + domainObjectName;
				String daoPath = this.getFullName(daoGeneratorTag, domainObjectName, DAO_NAME);

				File daoFile = new File(daoPath);
				List<String> lineList = FileUtils.readLines(daoFile, ENCODING);
				List<String> resultList = new ArrayList<String>();

				//修改dao生成兼容jsonb
				resultList.add(lineList.get(0));
				resultList.add("");
				resultList.add("import " + PackagePrefixUtils.BASE_PACKAGE_PREFIX + ".provider.dao." + rootDaoName + ";");
				resultList.add("import " + modelJava + ";");
				resultList.add("import java.util.List;");
				resultList.add("import java.util.Map;");
				resultList.add("");
				resultList.add("/**");
				resultList.add(" *	This is a DAO interface auto-generated by underlying framework. <br/>");
				resultList.add(" *	<p>");
				resultList.add(" *  This interface can be modified according to business requirement.");
				resultList.add(" *  </p>");
				resultList.add(" *	");
				resultList.add(" *  @Description 该DAO接口的内容可根据实际业务需求调整");
				resultList.add(" *	@author vinsy");
				resultList.add(" *  @date 2018/03/23");
				resultList.add(" */");
				resultList.add("public interface " + domainObjectName + "DAO extends " + rootDaoName + "<"
						+ domainObjectName + "> {");
				resultList.add("");
				resultList.add("	// 根据jsonb条件查询分页数据");
				resultList.add("	List<" + domainObjectName + "> getJsonbPageableList(Map<String,Object> params);");
				resultList.add("");
				resultList.add("	// 根据jsonb条件查询总数量");
				resultList.add("	long getJsonbListCount(Map<String, Object> params);");
				resultList.add("}");

				FileUtils.writeLines(daoFile, ENCODING, resultList);
			}
		}
	}

	public void recoveryBak() throws IOException {
		for (Element context : contextList) {
			GeneratorTag daoGeneratorTag = XmlUtils.getGeneratorTagByContext(context,
					TagConstants.JAVA_CLIENT_GENERATOR);
			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				String daoPath = this.getFullName(daoGeneratorTag, tableTag.getDomainObjectName(), DAO_NAME);

				File daoBakFile = new File(daoPath + "_bak");
				if (daoBakFile.exists()) {
					FileUtils.writeByteArrayToFile(new File(daoPath), FileUtils.readFileToByteArray(daoBakFile));
					FileUtils.deleteQuietly(daoBakFile);
				}
			}
		}
	}

	// 添加mapper_expand.xml
	public void createMapperExpand() throws Exception {
		for (Element context : contextList) {
			GeneratorTag sqlMapGeneratorTag = XmlUtils.getGeneratorTagByContext(context, TagConstants.SQL_MAP_GENERATOR);

			List<TableTag> tableTagList = XmlUtils.getTableTagByContext(context);
			for (TableTag tableTag : tableTagList) {
				String mapperPath = this.getFullName(sqlMapGeneratorTag, tableTag.getDomainObjectName(), "Mapper.xml");
				this.sqlMapperDeal(mapperPath, tableTag);
			}
		}
	}

	protected void sqlMapperDeal(String mapperPath, TableTag tableTag) throws Exception {

	}

	public void generator(String fileName, String jsonScheamName) throws Exception {
		// 读取自动化配置文档
		this.read(fileName);
		// 初始化上下文
		this.initContext();

		// 备份已存在的DAO
		this.initBak();
		// 自动化生成Service, Controller
		
		
		this.initServiceGenerator();
		
		// 重写mybatis-generator-config.xml配置文件
		this.initRewriteXml();
		// 运行mybatis自动化生成脚本，生成model,xml,dao
		this.runShellGenerator();
		this.initServiceImplGenerator();
		this.initControllerGenerator(jsonScheamName);
		
		this.initJsonSchema(jsonScheamName);
		this.addRedisKeyPrefix(jsonScheamName);
		// 运行mybatis自动化生成脚本，生成model,xml,dao
		//this.runShellGenerator();
		// 重写model，去掉getter setter变为注解
		this.rewriteModel();

		// 第一次生成，简化DAO类，直接集成超类
		this.rewriteDAO();

		// 如果原先DAO已存在，则从原先中恢复
		this.recoveryBak();

		// 生成mapper_expand.xml文件
		this.createMapperExpand();
	}
	
	public void generator(String fileName) throws Exception {
		// 读取自动化配置文档
		this.read(fileName);
		// 初始化上下文
		this.initContext();

		// 备份已存在的DAO
		this.initBak();
		// 自动化生成Service, Controller
		
		
		this.initServiceGenerator();
		
		// 重写mybatis-generator-config.xml配置文件
		this.initRewriteXml();
		// 运行mybatis自动化生成脚本，生成model,xml,dao
		this.runShellGenerator();
		this.initServiceImplGenerator();
		this.initControllerGenerator();
		
		// 运行mybatis自动化生成脚本，生成model,xml,dao
		//this.runShellGenerator();
		// 重写model，去掉getter setter变为注解
		this.rewriteModel();

		// 第一次生成，简化DAO类，直接集成超类
		this.rewriteDAO();

		// 如果原先DAO已存在，则从原先中恢复
		this.recoveryBak();

		// 生成mapper_expand.xml文件
		this.createMapperExpand();
	}
	
	 //首字母大写转小写
    public static String toLowerCase(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
       return  name;
    }
    
  //首字母小写转大写
    public static String toUpperCase(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
       return  name;
    }
    
    //根据model类内容。获取jsonb字段
    public static List<String> getJsonbFields(List<String> lineList) {
    	List<String> jsonbFields = new ArrayList<String>();
    	for (String line : lineList) {
    		if(line.contains("Object"))
    		{
    			String jsonbField = line.substring(19, line.lastIndexOf(";"));
    			jsonbFields.add(jsonbField);
    		}
    	}
    	return jsonbFields;
    }
}
