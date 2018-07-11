package application.org.mybatis.core.module.com;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeModel extends Identifiable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * <pre>
     * 父资源编号
     * </pre>
     * 
     */
    private Long parentId;

    /**
     * <pre>
     * 左值：比父节点左值大
     * </pre>
     * 
     */
    private Long leftValue;

    /**
     * <pre>
     * 右值：比父节点右值小
     * </pre>
     * 
     */
    private Long rightValue;
	
    /**
     */
    private String name;
    
    /**
     * <pre>
     * 排序号
     * </pre>
     * 
     */
    private Long seq;

}
