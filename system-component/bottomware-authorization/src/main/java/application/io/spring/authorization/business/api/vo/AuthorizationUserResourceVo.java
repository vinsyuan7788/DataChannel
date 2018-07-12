package application.io.spring.authorization.business.api.vo;

import application.io.spring.authorization.business.api.model.AuthorizationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserResourceVo extends AuthorizationUser {

	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	
	private String resource;
	
	private String resourceCode;
	
	private Long resourcePid;
	
	private Long resourceParentId;
	
	private Long resourceLeftValue;
	
	private Long resourceRightValue;
	
	private String resourceId;
	
	private String resourceParentNode;
	
	private String resourceLeftNode;
	
	private String resourceRightNode;
	
	private Long resourceSeq;
	
	private String resourceType;

	@Override
	public String toString() {
		return "AuthorizationUserResourceVo [resourceName=" + resourceName + ", resource=" + resource
				+ ", resourceCode=" + resourceCode + ", resourcePid=" + resourcePid + ", resourceParentId="
				+ resourceParentId + ", resourceLeftValue=" + resourceLeftValue + ", resourceRightValue="
				+ resourceRightValue + ", resourceId=" + resourceId + ", resourceParentNode=" + resourceParentNode
				+ ", resourceLeftNode=" + resourceLeftNode + ", resourceRightNode=" + resourceRightNode
				+ ", resourceSeq=" + resourceSeq + ", resourceType=" + resourceType + "]";
	}
}
