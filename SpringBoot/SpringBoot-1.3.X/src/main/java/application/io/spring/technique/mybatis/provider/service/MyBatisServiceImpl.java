package application.io.spring.technique.mybatis.provider.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.io.spring.technique.mybatis.api.model.MyBatis;
import application.io.spring.technique.mybatis.api.service.MyBatisService;
import application.io.spring.technique.mybatis.provider.dao.MyBatisDAO;

@Service("myBatisService")
public class MyBatisServiceImpl implements MyBatisService {

	@Autowired
	private MyBatisDAO myBatisDAO;
	
	@Override
	public Boolean insertSelective(MyBatis bean) throws Exception {
		
		boolean isSuccessful = true;
		
		try {
			myBatisDAO.insertSelective(bean);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccessful = false;
		}
		
		return isSuccessful;
	}

	@Override
	public Boolean insertBatch(List<MyBatis> beans) throws Exception {

		boolean isSuccessful = true;
		
		try {
			myBatisDAO.insertBatch(beans);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccessful = false;
		}
		
		return isSuccessful;
	}

	@Override
	public List<MyBatis> selectByQuery(MyBatis query) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		
		BeanUtils.populate(query, params);
		
		try {
			return myBatisDAO.selectByQuery(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<MyBatis> selectByQuery(MyBatis query, String orderby, Integer limit, Integer offset) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		
		BeanUtils.populate(query, params);
		
		params.put("orderby", orderby);
		params.put("limit", limit);
		params.put("offset", offset);
		
		try {
			return myBatisDAO.selectByQuery(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
