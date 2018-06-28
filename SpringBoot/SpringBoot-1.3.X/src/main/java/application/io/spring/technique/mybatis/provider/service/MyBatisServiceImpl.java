package application.io.spring.technique.mybatis.provider.service;

import java.util.List;

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
	public boolean insertSelective(List<MyBatis> beans) throws Exception {
		
		boolean isAllInserted = true;
		
		for (MyBatis bean : beans) {
			try {
				myBatisDAO.insertSelective(bean);
			} catch (Exception e) {
				isAllInserted = false;
			}
		}
		
		return isAllInserted;
	}
}
