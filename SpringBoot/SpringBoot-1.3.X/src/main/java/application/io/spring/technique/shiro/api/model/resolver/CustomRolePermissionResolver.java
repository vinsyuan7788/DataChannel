package application.io.spring.technique.shiro.api.model.resolver;

import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

public class CustomRolePermissionResolver implements RolePermissionResolver {

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		// TODO Auto-generated method stub
		return null;
	}
}
