package application.org.mybatis.core.module.com;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 主键标识
 * @date 2014年3月3日下午5:55:34
 */
public class Identifiable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Long id;
	
}