package application.org.mybatis.common.generator.mybatis.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResuleMap {

	private String id;
	private String type;

	private List<ResultItem> resultItemList;

	public ResuleMap() {
		// 空构造函数
	}

	public ResuleMap(String id, String type, List<ResultItem> resultItemList) {
		super();
		this.id = id;
		this.type = type;
		this.resultItemList = resultItemList;
	}
}
