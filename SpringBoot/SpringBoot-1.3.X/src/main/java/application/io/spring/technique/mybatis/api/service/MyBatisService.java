package application.io.spring.technique.mybatis.api.service;

import java.util.List;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisService {

	public boolean insertSelective(MyBatis bean) throws Exception;
	
	public boolean insertBatch(List<MyBatis> beans) throws Exception;
}
