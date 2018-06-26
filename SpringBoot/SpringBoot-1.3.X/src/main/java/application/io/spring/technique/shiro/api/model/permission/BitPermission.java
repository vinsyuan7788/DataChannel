package application.io.spring.technique.shiro.api.model.permission;

import org.apache.shiro.authz.Permission;

public class BitPermission implements Permission {

	@Override
	public boolean implies(Permission p) {
		// TODO Auto-generated method stub
		return false;
	}
}
