package application.io.spring.technique.mybatis.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.provider.service.BaseServiceImpl;
import application.io.spring.technique.mybatis.api.model.MyBatis;
import application.io.spring.technique.mybatis.api.service.MyBatisService;

@Service("myBatisService")
public class MyBatisServiceImpl extends BaseServiceImpl<MyBatis> implements MyBatisService {

}