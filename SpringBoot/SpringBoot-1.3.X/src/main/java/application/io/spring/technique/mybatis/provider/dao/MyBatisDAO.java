package application.io.spring.technique.mybatis.provider.dao;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisDAO {

	public void insertSelective(MyBatis bean) throws Exception;
}
