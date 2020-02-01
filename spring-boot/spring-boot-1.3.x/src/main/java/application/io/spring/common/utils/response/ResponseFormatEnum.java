package application.io.spring.common.utils.response;

import lombok.Getter;

/**
 * 	【12/20/2019】定义返回格式的枚举类
 * 
 * @author vinsy
 *
 */
@Getter
enum ResponseFormatEnum {

	STATUS("status", "表征返回值的状态"),
	MSG("msg", "表征返回信息"),
	RESULT("result", "表征返回结果");
	
	private String key;
	
	private String description;
	
	ResponseFormatEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}
}
