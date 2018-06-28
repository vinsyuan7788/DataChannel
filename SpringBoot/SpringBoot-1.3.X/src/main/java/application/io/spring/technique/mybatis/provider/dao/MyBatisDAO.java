package application.io.spring.technique.mybatis.provider.dao;

import org.apache.ibatis.annotations.Mapper;

import application.io.spring.technique.mybatis.api.model.MyBatis;

@Mapper
public interface MyBatisDAO {

	public void insertSelective(MyBatis bean) throws Exception;
}
