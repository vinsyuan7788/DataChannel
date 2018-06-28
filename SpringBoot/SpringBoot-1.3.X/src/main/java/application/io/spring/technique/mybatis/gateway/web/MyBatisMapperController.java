package application.io.spring.technique.mybatis.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.mybatis.api.service.MyBatisService;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/mybatis/mapper")
public class MyBatisMapperController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MyBatisService myBatisService;
	
	@RequestMapping(value = "/testInsert", method = RequestMethod.POST)
	public Map<String, Object> testInsert() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testUpdate", method = RequestMethod.POST)
	public Map<String, Object> testUpdate() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testSelect", method = RequestMethod.POST)
	public Map<String, Object> testSelect() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testDelete", method = RequestMethod.POST)
	public Map<String, Object> testDelete() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("result", result);
		return data;
	}
}
