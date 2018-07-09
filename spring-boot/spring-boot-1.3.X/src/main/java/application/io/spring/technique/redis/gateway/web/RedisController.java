package application.io.spring.technique.redis.gateway.web;

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
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/helloRedis", method = RequestMethod.GET)
	public Map<String, Object> helloRedis() throws Exception {
	
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("status", 1);
		data.put("msg", "Hello Redis!");
		data.put("result", result);
		return data;
	}
}
