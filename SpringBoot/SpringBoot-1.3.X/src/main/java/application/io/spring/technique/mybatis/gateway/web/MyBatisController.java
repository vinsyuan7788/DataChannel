package application.io.spring.technique.mybatis.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.common.base.gateway.web.BaseController;
import application.io.spring.technique.mybatis.api.model.MyBatis;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/mybatis")
public class MyBatisController extends BaseController<MyBatis> {

	@Autowired
	private Environment springBootEnvironment;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/helloMyBatis", method = RequestMethod.GET)
	public Map<String, Object> helloMyBatis() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		result.put("defaultProfiles", springBootEnvironment.getDefaultProfiles());
		result.put("activeProfiles", springBootEnvironment.getActiveProfiles());
		result.put("driverClassName", springBootEnvironment.getProperty("spring.datasource.driver-class-name"));
		result.put("Type", springBootEnvironment.getProperty("spring.datasource.type"));
		result.put("Url", springBootEnvironment.getProperty("spring.datasource.url"));
		result.put("Username", springBootEnvironment.getProperty("spring.datasource.username"));
		result.put("Password", springBootEnvironment.getProperty("spring.datasource.password"));
		result.put("maxIdle", springBootEnvironment.getProperty("spring.datasource.max-idle"));
		result.put("maxWait", springBootEnvironment.getProperty("spring.datasource.max-wait"));
		result.put("maximumPoolSize", springBootEnvironment.getProperty("spring.datasource.maximum-pool-size"));
		result.put("maxActive", springBootEnvironment.getProperty("spring.datasource.max-active"));
		result.put("minIdle", springBootEnvironment.getProperty("spring.datasource.min-idle"));
		
		data.put("status", 1);
		data.put("msg", "Hello MyBatis!");
		data.put("result", result);
		return data;
	}
}
