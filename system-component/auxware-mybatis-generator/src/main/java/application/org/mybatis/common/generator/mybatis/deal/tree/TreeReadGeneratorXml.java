package application.org.mybatis.common.generator.mybatis.deal.tree;

import application.org.mybatis.common.generator.mybatis.bean.TableTag;
import application.org.mybatis.common.generator.mybatis.deal.AbstractReadGeneratorXml;
import application.org.mybatis.common.generator.mybatis.deal.AbstractSqlMapperDeal;

public class TreeReadGeneratorXml extends AbstractReadGeneratorXml{
	
	public static final String SERVICE_TEMPLATEFILEPATH = "treeServiceTemplate.txt";
	public static final String SERVICEIMPL_TEMPLATEFILEPATH = "treeServiceImplTemplate.txt";
	public static final String CONTROLLER_TEMPLATEFILEPATH = "treeControllerTemplate.txt";
	
	public TreeReadGeneratorXml(){
		super.rootDaoName = "TreeDao";
		super.serviceTemplateFilePath = SERVICE_TEMPLATEFILEPATH;
		super.serviceImplTemplateFilePath = SERVICEIMPL_TEMPLATEFILEPATH;
		super.controllerTemplateFilePath = CONTROLLER_TEMPLATEFILEPATH;
		super.rootSimpleClass = "TreeModel";
	}

	@Override
	protected void sqlMapperDeal(String mapperPath, TableTag tableTag) throws Exception{
		AbstractSqlMapperDeal sqlMapperDeal = new TreeSqlMapperDeal(mapperPath, tableTag);
		sqlMapperDeal.init();
		sqlMapperDeal.createExpandXml();
		
		sqlMapperDeal.rewriteXml();
	}
	
}
