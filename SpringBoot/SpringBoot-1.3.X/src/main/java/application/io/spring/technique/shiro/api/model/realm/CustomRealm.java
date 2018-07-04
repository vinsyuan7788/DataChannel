package application.io.spring.technique.shiro.api.model.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 	This is a class to customize a realm for authentication and authorization
 * 	-- This class is responsible for specifying how to authenticate with the token and what the authorized information is
 * 
 * @author vinsy
 *
 */
public class CustomRealm extends AuthorizingRealm {

	/**
	 * 	This is a method to implement how a principal should be authorized
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 	This is a method to implement how a token should be authenticated
	 * 	-- This token is the one from "subject.login(token)"
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}
}
