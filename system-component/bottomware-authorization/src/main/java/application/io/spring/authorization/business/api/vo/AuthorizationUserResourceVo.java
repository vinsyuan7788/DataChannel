package application.io.spring.authorization.business.api.vo;

import application.io.spring.authorization.business.api.model.AuthorizationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserResourceVo extends AuthorizationUser {

	private static final long serialVersionUID = 1L;
	
	private Long resourceId;
	
	private String resourceName;
	
	private String resource;
	
	private String resourceType;
	
	private Long resourceParentId;
	
	private String resourceCode;

	@Override
	public String toString() {
		return "AuthorizationUserResourceVo [resourceId=" + resourceId + ", resourceName=" + resourceName
				+ ", resource=" + resource + ", resourceType=" + resourceType + ", resourceParentId=" + resourceParentId
				+ ", resourceCode=" + resourceCode + "]";
	}
}
