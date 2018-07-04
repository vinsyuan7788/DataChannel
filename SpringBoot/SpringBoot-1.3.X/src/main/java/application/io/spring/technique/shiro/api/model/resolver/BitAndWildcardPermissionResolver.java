package application.io.spring.technique.shiro.api.model.resolver;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import application.io.spring.technique.shiro.api.model.permission.BitPermission;

/**
 * 	This is a class to resolve a permission string to a Permission instance
 * 	-- This class is responsible for resolving permissions
 * 
 * @author vinsy
 *
 */
public class BitAndWildcardPermissionResolver implements PermissionResolver {

	/**
	 * 	This is a method to resolve a permission string to a Permission instance
	 */
	@Override  
    public Permission resolvePermission(String permissionString) {
		
		// Print information
		System.out.println("=== BitAndWildcardPermissionResolver.resolvePermission"
				+ " | permissionString: " + permissionString + " ===");
		
		// Here resolves a Permission instance according to permission string
        if (permissionString.startsWith("|")) {
        	return new BitPermission(permissionString);  
        }
        return new WildcardPermission(permissionString);  
    }  
}
