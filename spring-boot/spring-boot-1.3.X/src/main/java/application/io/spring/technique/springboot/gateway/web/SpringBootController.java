package application.io.spring.technique.springboot.gateway.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.springboot.api.model.SpringBoot;
import application.io.spring.technique.springboot.utils.SpringContextHolder1;
import application.io.spring.technique.springboot.utils.SpringContextHolder2;
import net.sf.json.JSONObject;

@SuppressWarnings({ "unused" })
@RestController
@RequestMapping("/spring-boot")
public class SpringBootController {

	@Autowired
	private Environment springBootEnvironment;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/helloSpringBoot", method = RequestMethod.GET)
	public Map<String, Object> helloSpringBoot() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		result.put("defaultProfiles", springBootEnvironment.getDefaultProfiles());
		result.put("activeProfiles", springBootEnvironment.getActiveProfiles());
		result.put("contextPath", springBootEnvironment.getProperty("server.context-path"));
		result.put("servletPath", springBootEnvironment.getProperty("server.servlet-path"));
		result.put("port", springBootEnvironment.getProperty("server.port"));
		
		data.put("status", 1);
		data.put("msg", "Hello Spring-Boot!");
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testApplicationContext", method = RequestMethod.GET)
	public Map<String, Object> testApplicationContext() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Spring applicationContext can be retrieved by multiple times in different places in a project
		result.put("applicationNameFromSpringContextHolder1", SpringContextHolder1.getApplicationContext().getApplicationName());
		result.put("displayNameFromSpringContextHolder1", SpringContextHolder1.getApplicationContext().getDisplayName());
		result.put("beanNamesFromSpringContextHolder1", Arrays.asList(SpringContextHolder1.getBeans()));
		result.put("applicationNameFromSpringContextHolder2", SpringContextHolder2.getApplicationContext().getApplicationName());
		result.put("displayNameFromSpringContextHolder2", SpringContextHolder2.getApplicationContext().getDisplayName());
		result.put("beanNamesFromSpringContextHolder2", Arrays.asList(SpringContextHolder2.getBeans()));
		
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testBeanUtils", method = RequestMethod.GET)
	public Map<String, Object> testBeanUtils() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Initialize a bean
		SpringBoot beforeDescribe = new SpringBoot();
		beforeDescribe.setId(123L);
		beforeDescribe.setName("Spring-Boot");
		beforeDescribe.setVersion("1.3.5");
		beforeDescribe.setReleaseTime(new Date());
		beforeDescribe.setOfficialUrl("https://spring.io/");
		beforeDescribe.setContributor("vins, ives");
		beforeDescribe.setRemark("to test BeanUtils.describe");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("createTime", new Date());
		jsonObject.put("updateTime", new Date());
		beforeDescribe.setExtendedField(jsonObject);
		
		// Describe a bean to a Map
		Map<String, String> afterDescribe = BeanUtils.describe(beforeDescribe);
		result.put("beforeDescribe", beforeDescribe);
		result.put("afterDescribe", afterDescribe);
		
		// Initialize a Map
		Map<String, Object> beforePopulate = new HashMap<>();
		beforePopulate.put("id", "123L");
		beforePopulate.put("name", "Spring-Boot");
		beforePopulate.put("version", "1.3.5");
		beforePopulate.put("releaseTime", new Date());
		beforePopulate.put("officialUrl", "https://spring.io/");
		beforePopulate.put("contributor", "vins, ives");
		beforePopulate.put("remark", "to test BeanUtils.populate");
		jsonObject = new JSONObject();
		jsonObject.put("createTime", new Date());
		jsonObject.put("updateTime", new Date());
		beforePopulate.put("extendedField", jsonObject);
		
		// Populate a Map to a bean
		SpringBoot afterPopulate = new SpringBoot();
		BeanUtils.populate(afterPopulate, beforePopulate);
		result.put("beforePopulate", beforePopulate);
		result.put("afterPopulate", afterPopulate);
		
		// Return data
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
}
