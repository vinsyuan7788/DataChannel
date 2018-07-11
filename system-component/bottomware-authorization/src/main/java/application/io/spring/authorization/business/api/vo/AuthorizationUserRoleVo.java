package application.io.spring.authorization.business.api.vo;

import application.io.spring.authorization.business.api.model.AuthorizationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserRoleVo extends AuthorizationUser {

	private static final long serialVersionUID = 1L;

	private Long roleId;
	
	private String roleName;
	
	private String roleCode;
	
	private String roleGroup;

	@Override
	public String toString() {
		return "AuthorizationUserRoleVo [roleId=" + roleId + ", roleName=" + roleName + ", roleCode=" + roleCode
				+ ", roleGroup=" + roleGroup + "]";
	}
}
