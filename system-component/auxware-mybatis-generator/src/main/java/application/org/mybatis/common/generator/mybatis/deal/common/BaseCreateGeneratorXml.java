package application.org.mybatis.common.generator.mybatis.deal.common;

import application.org.mybatis.common.generator.mybatis.deal.AbstractCreateGeneratorXml;
import application.org.mybatis.common.generator.mybatis.rewrite.RemarkDefaultCommentGenerator;

public class BaseCreateGeneratorXml extends AbstractCreateGeneratorXml{

//	public static final String PROJECT_PATH = GeneratorConstants.PROJECT_PATH;
	public static final String COMMENT_GENERATOR_CLASS = RemarkDefaultCommentGenerator.class.getName();
	
	public BaseCreateGeneratorXml() {
		super();
		super.rootClass = "com.royalnu.core.module.com.Identifiable";
	}

}