package application.io.spring.technique.mybatis.gateway.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/mybatis/with-spring-boot")
public class MyBatisWithSpringBootController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MyBatisService myBatisService;
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "/testInsert", method = RequestMethod.POST)
	public Map<String, Object> testInsert() throws Exception {
		
		long startTime = System.currentTimeMillis();
		
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
		String now = dateFormat.format(new Date());
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
		now = dateFormat.format(new Date());
		jsonObject.put("createTime", now);
		jsonObject.put("updateTime", now);
		bean.setExtendedField(jsonObject);
		List<MyBatis> beans = new ArrayList<MyBatis>();
		beans.add(bean);
		beans.add(bean);
		beans.add(bean);
		
		boolean isInsertBatchSuccessful = myBatisService.insertBatch(beans);
		
		if (isInsertSelectiveSuccessful && isInsertBatchSuccessful) {
			result.put("isInsertSelectiveSuccessful", isInsertSelectiveSuccessful);
			result.put("isInsertBatchSuccessful", isInsertBatchSuccessful);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		} else {
			result.put("isInsertSelectiveSuccessful", isInsertSelectiveSuccessful);
			result.put("isInsertBatchSuccessful", isInsertBatchSuccessful);
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testSelect", method = RequestMethod.POST)
	public Map<String, Object> testSelect() throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		MyBatis query = new MyBatis();
		query.setName("MyBatis");
		query.setVersion("3.4.1");
		
		MyBatis resultFromSelectOneByQuery = myBatisService.selectOneByQuery(query);
		List<MyBatis> resultFromSelectAllByQuery = myBatisService.selectAllByQuery(query);
		List<MyBatis> resultFromSelectListByQuery = myBatisService.selectListByQuery(query, "id desc", 10L, 0L);
		Long resultFromGetAllCountByQuery = myBatisService.getAllCountByQuery(query);
		Long resultFromGetListCountByQuery = myBatisService.getListCountByQuery(query, 10L, 0L);
		
		List<Long> primaryKeys = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L,
				11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L);
		
		List<MyBatis> resultFromSelectListByPrimaryKeyCollection = myBatisService.selectListByPrimaryKeyCollection(primaryKeys);
		
		if (resultFromSelectOneByQuery != null && resultFromSelectAllByQuery != null && resultFromSelectListByQuery != null
				&& resultFromGetAllCountByQuery != null && resultFromGetListCountByQuery != null 
				&& resultFromSelectListByPrimaryKeyCollection != null) {
			result.put("resultFromSelectOneByQuery", resultFromSelectOneByQuery);
			result.put("resultFromSelectAllByQuery", resultFromSelectAllByQuery);
			result.put("resultFromSelectListByQuery", resultFromSelectListByQuery);
			result.put("resultFromGetAllCountByQuery", resultFromGetAllCountByQuery);
			result.put("resultFromGetListCountByQuery", resultFromGetListCountByQuery);
			result.put("resultFromSelectListByPrimaryKeyCollection", resultFromSelectListByPrimaryKeyCollection);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		} else {
			result.put("resultFromSelectOneByQuery", resultFromSelectOneByQuery);
			result.put("resultFromSelectAllByQuery", resultFromSelectAllByQuery);
			result.put("resultFromSelectListByQuery", resultFromSelectListByQuery);
			result.put("resultFromGetAllCountByQuery", resultFromGetAllCountByQuery);
			result.put("resultFromGetListCountByQuery", resultFromGetListCountByQuery);
			result.put("resultFromSelectListByPrimaryKeyCollection", resultFromSelectListByPrimaryKeyCollection);
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testDelete", method = RequestMethod.POST)
	public Map<String, Object> testDelete() throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		MyBatis condition = new MyBatis();
		condition.setRemark("from insertBatch");
		condition.setVersion("3.4.1");
		
		boolean isDeleteByConditionSuccessful = myBatisService.deleteByCondition(condition);
		
		if (isDeleteByConditionSuccessful) {
			result.put("isDeleteByConditionSuccessful", isDeleteByConditionSuccessful);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		} else {
			result.put("isDeleteByConditionSuccessful", isDeleteByConditionSuccessful);
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testUpdate", method = RequestMethod.POST)
	public Map<String, Object> testUpdate() throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		MyBatis query = new MyBatis();
		query.setRemark("from InsertSelective");
		query.setVersion("3.4.1");
		
		MyBatis bean = myBatisService.selectOneByQuery(query);
		
		String extendedField = (String) bean.getExtendedField();
		JSONObject newExtendeField = JSONObject.fromObject(extendedField);
		newExtendeField.put("updateTime", dateFormat.format(new Date()));
		bean.setExtendedField(newExtendeField);
		
		boolean isUpdateByPrimaryKeySelectiveSuccessful = myBatisService.updateByPrimaryKeySelective(bean);
		
		if (isUpdateByPrimaryKeySelectiveSuccessful) {
			result.put("isUpdateByPrimaryKeySelectiveSuccessful", isUpdateByPrimaryKeySelectiveSuccessful);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		} else {
			result.put("isUpdateByPrimaryKeySelectiveSuccessful", isUpdateByPrimaryKeySelectiveSuccessful);
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("executionTime", (System.currentTimeMillis() - startTime) + "ms");
			data.put("result", result);
			return data;
		}
	}
}
