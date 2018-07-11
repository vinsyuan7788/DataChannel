package application.org.mybatis.common.generator.mybatis.bean;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratorEntity {

	private String modulePackage;
	private String contextName;
	private Map<String, String> tableMap;
	private Map<String, String> treeTableMap;
	
	public GeneratorEntity(){
		this.tableMap = new HashMap<String, String>();
		this.treeTableMap = new HashMap<String, String>();
	}
	
}
