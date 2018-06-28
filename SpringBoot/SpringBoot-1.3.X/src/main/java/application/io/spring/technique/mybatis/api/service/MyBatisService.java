package application.io.spring.technique.mybatis.api.service;

import java.util.List;

import application.io.spring.technique.mybatis.api.model.MyBatis;

public interface MyBatisService {

	public boolean insertSelective(List<MyBatis> beans) throws Exception;
}
