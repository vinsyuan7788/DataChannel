package application.io.spring.core.base.api.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVo<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String orderby;
	
	private Long limit;
	
	private Long offset;
	
	private Long total;
	
	private List<T> rows;

	@Override
	public String toString() {
		return "PageVo [orderby=" + orderby + ", limit=" + limit + ", offset=" + offset + ", total=" + total + ", rows="
				+ rows + "]";
	}	
}