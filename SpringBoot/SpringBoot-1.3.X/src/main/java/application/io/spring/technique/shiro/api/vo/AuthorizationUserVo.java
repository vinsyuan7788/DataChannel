package application.io.spring.technique.shiro.api.vo;

import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserVo extends AuthorizationUser {

	private static final long serialVersionUID = 1L;
	
	private Long resourceId;
	
	private String resourceName;
	
	private String resource;
	
	private String resourceType;
	
	private Long resourceParentId;
	
	private String resourceCode;

	@Override
	public String toString() {
		return "AuthorizationUserVo [resourceId=" + resourceId + ", resourceName=" + resourceName + ", resource="
				+ resource + ", resourceType=" + resourceType + ", resourceParentId=" + resourceParentId
				+ ", resourceCode=" + resourceCode + "]";
	}
}
