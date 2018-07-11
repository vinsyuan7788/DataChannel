package application.org.mybatis.common.generator.mybatis.deal.tree;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.org.mybatis.common.generator.mybatis.bean.TableTag;
import application.org.mybatis.common.generator.mybatis.constants.TemplateConstant;
import application.org.mybatis.common.generator.mybatis.deal.AbstractSqlMapperDeal;
import application.org.mybatis.common.generator.mybatis.utils.VelocityUtils;

public class TreeSqlMapperDeal extends AbstractSqlMapperDeal {

	public static final String treeTemplateFilePath = "treeMapperTemplate.txt";
	
	public TreeSqlMapperDeal(String fileName,TableTag tableTag) {
		super(fileName, tableTag);
	}
	
	@Override
	protected void appendMapperXml(List<String> lineList, String parameterType) throws Exception{
		String treeTemplateStr = this.getTreeXml(tableTag.getTableName(), parameterType);
		lineList.add(lineList.size()-1, treeTemplateStr);
	}
	
	private String getTreeXml(String tableName, String modelFullName) throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(TemplateConstant.MODEL_FULL_NAME, modelFullName);
		params.put(TemplateConstant.TABLE_NAME, tableName);
		
		String xmlTemplateStr = VelocityUtils.replace(treeTemplateFilePath, params);
		
		return xmlTemplateStr;
	}

}
