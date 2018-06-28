package application.io.spring.technique.mybatis.api.service;

import java.util.List;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisService {

	public Boolean insertSelective(MyBatis bean) throws Exception;
	
	public Boolean insertBatch(List<MyBatis> beans) throws Exception;

	public List<MyBatis> selectByQuery(MyBatis query) throws Exception;
	
	public List<MyBatis> selectByQuery(MyBatis query, String orderby, Integer limit, Integer offset) throws Exception;
}
