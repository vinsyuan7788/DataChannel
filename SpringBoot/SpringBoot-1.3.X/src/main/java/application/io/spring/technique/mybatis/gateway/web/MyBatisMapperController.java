package application.io.spring.technique.mybatis.gateway.web;

import java.text.SimpleDateFormat;
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
		
		MyBatis bean = new MyBatis();
		bean.setName("MyBatis");
		bean.setVersion("3.4.1");
		bean.setReleaseTime(new Date());
		bean.setOfficialUrl("http://www.mybatis.org/mybatis-3/");
		bean.setContributor("Vins, Ives");
		bean.setRemark("from insertSelective");
		JSONObject jsonObject = new JSONObject();
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		jsonObject.put("createTime", now);
		jsonObject.put("updateTime", now);
		bean.setExtendedField(jsonObject);
		
		boolean isInsertSelectiveSuccessful = myBatisService.insertSelective(bean);
		
		bean = new MyBatis();
		bean.setName("MyBatis");
		bean.setVersion("3.4.1");
		bean.setReleaseTime(new Date());
		bean.setOfficialUrl("http://www.mybatis.org/mybatis-3/");
		bean.setContributor("Vins, Ives");
		bean.setRemark("from insertBatch");
		jsonObject = new JSONObject();
		now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		jsonObject.put("createTime", now);
		jsonObject.put("updateTime", now);
		bean.setExtendedField(jsonObject);
		List<MyBatis> beans = new ArrayList<MyBatis>();
		beans.add(bean);
		beans.add(bean);
		beans.add(bean);
		
		boolean isInsertBatchSuccessful = myBatisService.insertBatch(beans);
		
		if (isInsertSelectiveSuccessful == true && isInsertBatchSuccessful == true) {
			result.put("isInsertSelectiveSuccessful", isInsertSelectiveSuccessful);
			result.put("isInsertBatchSuccessful", isInsertBatchSuccessful);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
			return data;
		} else {
			result.put("isInsertSelectiveSuccessful", isInsertSelectiveSuccessful);
			result.put("isInsertBatchSuccessful", isInsertBatchSuccessful);
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
			return data;
		}
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
