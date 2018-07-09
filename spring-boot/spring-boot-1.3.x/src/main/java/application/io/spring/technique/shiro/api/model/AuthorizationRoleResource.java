package application.io.spring.technique.shiro.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRoleResource extends Identifiable {

	private static final long serialVersionUID = 1L;
	
	// role ID
    private Long roleId;

    // resource ID
    private Long resourceId;

    // create time
    private Date createTime;
    
    // update time
    private Date udpateTime;
    
    // extended field
    private Object extended_field;

    /**
     * 	For output display
     */
	@Override
	public String toString() {
		return "AuthorizationRoleResource [roleId=" + roleId + ", resourceId=" + resourceId + ", createTime=" + createTime
				+ ", udpateTime=" + udpateTime + ", extended_field=" + extended_field + "]";
	}
}
