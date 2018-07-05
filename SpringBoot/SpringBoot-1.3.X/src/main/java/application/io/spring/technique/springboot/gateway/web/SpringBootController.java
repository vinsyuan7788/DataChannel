package application.io.spring.technique.springboot.gateway.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.springboot.utils.SpringContextHolder1;
import application.io.spring.technique.springboot.utils.SpringContextHolder2;

@SuppressWarnings("unused")
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
		
		result.put("applicationNameFromSpringContextHolder1", SpringContextHolder1.getApplicationContext().getApplicationName());
		result.put("displayNameFromSpringContextHolder1", SpringContextHolder1.getApplicationContext().getDisplayName());
		result.put("beanNamesFromSpringContextHolder1", Arrays.asList(SpringContextHolder1.getBeans()));
		result.put("applicationNameFromSpringContextHolder2", SpringContextHolder2.getApplicationContext().getApplicationName());
		result.put("displayNameFromSpringContextHolder2", SpringContextHolder2.getApplicationContext().getDisplayName());
		result.put("beanNamesFromSpringContextHolder2", Arrays.asList(SpringContextHolder2.getBeans()));
		
		data.put("status", 1);
		data.put("msg", "Hello Spring-Boot!");
		data.put("result", result);
		return data;
	}
}
