package application.middleware.authorization.business.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserRole extends Identifiable {

	private static final long serialVersionUID = 1L;
	
	// user ID
    private Long userId;

    // role ID
    private Long roleId;

    // create time
    private Date createTime;
    
    // update time
    private Date updateTime;
    
    // extended field
    private Object extendedField;

    /**
     * 	For output display
     */
	@Override
	public String toString() {
		return "AuthorizationUserRole [userId=" + userId + ", roleId=" + roleId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", extendedField=" + extendedField + "]";
	}
}
