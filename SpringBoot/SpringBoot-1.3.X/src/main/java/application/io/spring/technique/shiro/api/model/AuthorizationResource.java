package application.io.spring.technique.shiro.api.model;

import java.util.Date;

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
}
