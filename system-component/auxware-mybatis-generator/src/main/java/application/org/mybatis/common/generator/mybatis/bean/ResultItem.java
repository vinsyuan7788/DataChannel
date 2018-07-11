package application.org.mybatis.common.generator.mybatis.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultItem {

	private String tag;
	private String column;
	private String property;
	private String jdbcType;
	private String javaType;
	public ResultItem() {
		// 空构造函数
	}

	public ResultItem(String tag, String column, String property, String jdbcType, String javaType) {
		super();
		this.tag = tag;
		this.column = column;
		this.property = property;
		this.jdbcType = jdbcType;
		this.javaType = javaType;
	}

}
