package application.org.mybatis.common.generator.mybatis.bean;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratorTag {
	private String tagName;
	private String targetPackage;
	private String targetProject;
	private String type;

	private Map<String, String> propertyMap;

	public GeneratorTag() {
		//  Do nothing because of 空构造函数
	}

	public GeneratorTag(String tagName, String targetProject, String targetPackage, Map<String, String> propertyMap) {
		super();
		this.tagName = tagName;
		this.targetProject = targetProject;
		this.targetPackage = targetPackage;
		this.propertyMap = propertyMap;
	}
}
