package application.io.spring.bottomware.authorization.business.api.vo;

import application.io.spring.bottomware.authorization.business.api.model.AuthorizationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserRoleVo extends AuthorizationUser {

	private static final long serialVersionUID = 1L;
	
	private String roleName;
	
	private String roleCode;
	
	private Long rolePid;
	
	private Long roleParentId;
	
	private Long roleLeftValue;
	
	private Long roleRightValue;
	
	private String roleId;
	
	private String roleParentNode;
	
	private String roleLeftNode;
	
	private String roleRightNode;
	
	private Long roleSeq;
	
	private String roleGroup;

	@Override
	public String toString() {
		return "AuthorizationUserRoleVo [roleName=" + roleName + ", roleCode=" + roleCode + ", rolePid=" + rolePid
				+ ", roleParentId=" + roleParentId + ", roleLeftValue=" + roleLeftValue + ", roleRightValue="
				+ roleRightValue + ", roleId=" + roleId + ", roleParentNode=" + roleParentNode + ", roleLeftNode="
				+ roleLeftNode + ", roleRightNode=" + roleRightNode + ", roleSeq=" + roleSeq + ", roleGroup="
				+ roleGroup + "]";
	}
}
