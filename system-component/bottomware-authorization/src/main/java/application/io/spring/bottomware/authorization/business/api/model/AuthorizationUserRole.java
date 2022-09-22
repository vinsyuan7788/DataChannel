package application.io.spring.bottomware.authorization.business.api.model;

import application.io.spring.core.base.api.model.Identifiable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *	This is a Java bean auto-generated by underlying framework. <br/>
 *	<p>
 *  This Java bean corresponds to a table in a database.
 *  </p>
 *	
 *  @Description 每个模型类对应一个数据库中的一张表
 *	@author vinsy
 *  @date 2018/03/23
 */
@Setter
@Getter
public class AuthorizationUserRole extends Identifiable {

	private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * 用户编号
     * </pre>
     * 
     */
    private Long userPrimaryId;

    /**
     * <pre>
     * 角色编号
     * </pre>
     * 
     */
    private Long rolePrimaryId;

    /**
     * <pre>
     * 创建时间
     * </pre>
     * 
     */
    private Date createTime;

    /**
     * <pre>
     * </pre>
     * 
     */
    private Date updateTime;

    /**
     * <pre>
     * </pre>
     * 
     */
    private Object extendedField;

    /**
     * <pre>
     * 对应x_authorization_user表中的user_id
     * </pre>
     * 
     */
    private String userId;

    /**
     * <pre>
     * 对应x_authorization_role表中的role_id
     * </pre>
     * 
     */
    private String roleId;

    /**
     * <pre>
     * </pre>
     * 
     */
    private String remark;

}