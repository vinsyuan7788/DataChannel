package application.org.mybatis.common.generator.mybatis.deal.common;

import application.org.mybatis.common.generator.mybatis.bean.TableTag;
import application.org.mybatis.common.generator.mybatis.deal.AbstractReadGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.AbstractSqlMapperDeal;

public class BaseReadGeneratorXml extends AbstractReadGeneratorXml{
	
	public static final String SERVICE_TEMPLATEFILEPATH = "ServiceTemplate.txt";
	public static final String SERVICEIMPL_TEMPLATEFILEPATH = "ServiceImplTemplate.txt";
	public static final String CONTROLLER_TEMPLATEFILEPATH = "ControllerTemplate.txt";
	
	public BaseReadGeneratorXml(){
		super.rootDaoName = "BaseDAO";
		super.serviceTemplateFilePath = SERVICE_TEMPLATEFILEPATH;
		super.serviceImplTemplateFilePath = SERVICEIMPL_TEMPLATEFILEPATH;
		super.controllerTemplateFilePath = CONTROLLER_TEMPLATEFILEPATH;
		super.rootSimpleClass = "Identifiable";
	}

	@Override
	protected void sqlMapperDeal(String mapperPath, TableTag tableTag) throws Exception{
		AbstractSqlMapperDeal sqlMapperDeal = new BaseSqlMapperDeal(mapperPath, tableTag);
		sqlMapperDeal.init();
		sqlMapperDeal.createExpandXml();
		
		sqlMapperDeal.rewriteXml();
	}
	
}
