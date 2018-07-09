package application.io.spring.technique.log4j2.gateway.web;

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
@RequestMapping("/log4j2")
public class Log4j2Controller {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/helloLog4j2", method = RequestMethod.GET)
	public Map<String, Object> helloLog4j2() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("status", 1);
		data.put("msg", "Hello Log4j2!");
		data.put("result", result);
		return data;
	}
}
