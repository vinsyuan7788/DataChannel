package application.io.spring.business.user.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/user")
public class UserController {

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
}
