package application.io.spring.business.authorization.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.TreeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResource extends TreeModel {

	private static final long serialVersionUID = 1L;
	
	// what the resource is
    private String resource;

    // resource type: LEAF (can create or have children), BOTTOM (cannot create or have children)
    private String resourceType;

    // resource code
    private String code;

    // create time
    private Date createTime;
    
    // end time
    private Date updateTime;
    
    // Remark
    private String remark;
    
    // extended field
    private Object extendedField;

    /**
     * 	For output display
     */
	@Override
	public String toString() {
		return "AuthorizationResource [resource=" + resource + ", resourceType=" + resourceType + ", code=" + code
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", extendedField=" + extendedField + "]";
	}
}
