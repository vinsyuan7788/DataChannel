package application.io.spring.technique.shiro.api.model.resolver;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 	This is a class to resolve Permission instances according to roles
 * 	-- This class is responsible for resolving permissions or adding resolved permissions according to the roles
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
		
		// Print information
		System.out.println("=== CustomRolePermissionResolver.resolvePermissionsInRole"
				+ " | roleString: " + roleString + " ===");
		
		// Here resolves Permission instances according to roles
		if(roleString.equals("CTO")) {  
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));  
        }  
        return null;  
	}
}
