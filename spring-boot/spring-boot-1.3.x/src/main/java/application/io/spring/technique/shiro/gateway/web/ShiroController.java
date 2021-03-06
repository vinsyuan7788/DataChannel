package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	This is a class to test Shiro
 * 
 * @author vinsy
 *
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/shiro")
public class ShiroController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/helloShiro", method = RequestMethod.GET)
	public Map<String, Object> helloShiro() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("status", 1);
		data.put("msg", "Hello Shiro!");
		data.put("result", result);
		return data;
	}
}
