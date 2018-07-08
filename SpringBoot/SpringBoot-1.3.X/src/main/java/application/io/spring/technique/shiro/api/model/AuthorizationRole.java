package application.io.spring.technique.shiro.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRole extends Identifiable {

	private static final long serialVersionUID = 1L;
	
    // role name
    private String name;
    
    // role display sequence
    private Long seq;
    
    // role code
    private String code;
    
    // role group
    private String roleGroup;
    
    // create time
    private Date createTime;
    
    // update time
    private Date updateTime;
    
	// remark
    private String remark;
    
    // extended field
    private Object extendedField;

    /**
     * 	For output display
     */
	@Override
	public String toString() {
		return "AuthorizationRole [name=" + name + ", seq=" + seq + ", code=" + code + ", roleGroup=" + roleGroup
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", extendedField=" + extendedField + "]";
	}
}
