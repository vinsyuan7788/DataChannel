package application.io.spring.technique.mybatis.api.service;

import java.util.List;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisService {

	public Boolean insertSelective(MyBatis bean) throws Exception;
	
	public Boolean insertBatch(List<MyBatis> beans) throws Exception;

	public MyBatis selectOneByQuery(MyBatis query) throws Exception;
	
	public List<MyBatis> selectAllByQuery(MyBatis query) throws Exception;
	
	public List<MyBatis> selectListByQuery(MyBatis query, String orderby, Long limit, Long offset) throws Exception;

	public Long getAllCountByQuery(MyBatis query) throws Exception;

	public Long getListCountByQuery(MyBatis query, Long limit, Long offset) throws Exception;

	public Boolean deleteByCondition(MyBatis condition) throws Exception;

	public Boolean updateByPrimaryKeySelective(MyBatis bean) throws Exception;
}
