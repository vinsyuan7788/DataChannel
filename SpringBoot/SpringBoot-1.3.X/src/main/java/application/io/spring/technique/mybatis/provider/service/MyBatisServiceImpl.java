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
		
		try {
			myBatisDAO.insertSelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean insertBatch(List<MyBatis> beans) throws Exception {

		try {
			myBatisDAO.insertBatch(beans);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MyBatis selectOneByQuery(MyBatis query) throws Exception {
		
		try {
			return myBatisDAO.getPageableList(getCondition(query, null, 1L, 0L)).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MyBatis> selectAllByQuery(MyBatis query) throws Exception {
		
		try {
			return myBatisDAO.getPageableList(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<MyBatis> selectListByQuery(MyBatis query, String orderby, Long limit, Long offset) throws Exception {
		
		try {
			return myBatisDAO.getPageableList(getCondition(query, orderby, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long getAllCountByQuery(MyBatis query) throws Exception {
		
		try {
			return myBatisDAO.getListCount(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long getListCountByQuery(MyBatis query, Long limit, Long offset) throws Exception {
		
		try {
			return myBatisDAO.getListCount(getCondition(query, null, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteByCondition(MyBatis condition) throws Exception {
		
		try {
			myBatisDAO.deleteByCondition(condition);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public Boolean updateByPrimaryKeySelective(MyBatis bean) throws Exception {
		
		try {
			myBatisDAO.updateByPrimaryKeySelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static Map<String, Object> getCondition(MyBatis query, String orderby, Long limit, Long offset) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		
		BeanUtils.populate(query, params);
		
		if (orderby != null) {
			params.put("orderby", orderby);
		} else {
			params.put("orderby", "id desc");
		}
		
		if (limit != null) {
			params.put("limit", limit);
		} else {
			params.put("limit", null);
		}
		
		if (offset != null) {
			params.put("offset", offset);
		} else {
			params.put("offset", null);
		}
		
		return params;
	}
}