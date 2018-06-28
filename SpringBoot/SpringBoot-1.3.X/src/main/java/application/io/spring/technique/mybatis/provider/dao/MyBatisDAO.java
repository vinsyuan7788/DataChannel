package application.io.spring.technique.mybatis.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisDAO {

	public void insertSelective(MyBatis bean) throws Exception;
	
	public void insertBatch(List<MyBatis> beans) throws Exception;

	public List<MyBatis> getPageableList(Map<String, Object> params) throws Exception;

	public List<MyBatis> selectByIdCollection(Map<String, Object> params) throws Exception;
	
	public Long getListCount(Map<String, Object> params) throws Exception;
	
	public void deleteByCondition(MyBatis condition) throws Exception;
	
	public void updateByPrimaryKeySelective(MyBatis bean) throws Exception;
}
