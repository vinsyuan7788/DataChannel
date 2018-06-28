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
}
