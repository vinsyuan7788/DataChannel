package application.io.spring.technique.mybatis.gateway.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.mybatis.api.model.MyBatis;
import application.io.spring.technique.mybatis.api.service.MyBatisService;
import net.sf.json.JSONObject;

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
		
		MyBatis myBatis = new MyBatis();
		myBatis.setId(1L);
		myBatis.setName("MyBatis");
		myBatis.setVersion("3.4.1");
		myBatis.setReleaseTime(new Date());
		myBatis.setOfficialUrl("http://www.mybatis.org/mybatis-3/");
		myBatis.setContributor("Vins, Ives");
		myBatis.setRemark("None");
		myBatis.setExtendedField(new JSONObject());
		
		List<MyBatis> beans = new ArrayList<MyBatis>();
		beans.add(myBatis);
		boolean isAllInserted = myBatisService.insertSelective(beans);
		
		result.put("isAllInserted", isAllInserted);
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
