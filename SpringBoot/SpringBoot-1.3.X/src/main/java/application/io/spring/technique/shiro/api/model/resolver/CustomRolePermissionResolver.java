package application.io.spring.technique.shiro.api.model.resolver;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 	This is a class to resolve Permission instances according to roles
 * 
 * @author vinsy
 *
 */
public class CustomRolePermissionResolver implements RolePermissionResolver {

	/**
	 * 	This is a method to resolve Permission instances according to roles
	 */
	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if(roleString.equals("CTO")) {  
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));  
        }  
        return null;  
	}
}
