package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/shiro/credentials-matcher")
public class ShiroCredentialsMatcherController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/testCredentialsMatcher", method = RequestMethod.GET)
	public Map<String, Object> testCredentialsMatcher() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("result", result);
		return data;
	}
}
