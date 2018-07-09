package application.middleware.authorization.technique.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import application.middleware.authorization.business.api.model.AuthorizationUser;
import application.middleware.authorization.business.api.service.AuthorizationUserService;
import application.middleware.authorization.business.api.vo.AuthorizationUserResourceVo;
import application.middleware.authorization.business.api.vo.AuthorizationUserRoleVo;
import lombok.extern.log4j.Log4j2;

/**
 * 	This is a class to customize a realm for authentication and authorization
 * 	-- This class is responsible for specifying how to authenticate with the token and what the authorized information is
 * 
 * @author vinsy
 *
 */
@Log4j2
public class AuthorizationRealm extends AuthorizingRealm {

	@Autowired
	private AuthorizationUserService authorizationUserService;
	
	/**
	 * 	This is a method to implement how a principal should be authorized
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		// Normally
		try {
			
			// Get the username from realm
			String username = (String) principals
					.fromRealm(getClass().getName())
					.iterator()
					.next();
			
			// Query all roles according to the username
			AuthorizationUser authorizationUser = new AuthorizationUser();
			authorizationUser.setName(username);
			List<AuthorizationUserRoleVo> authorizationUserRoleVos = authorizationUserService.selectAllUserRolesByName(authorizationUser);
			
			/* 
			 * 	Store all roles into a set
			 * 	-- Using set here just in case that one user may have the duplicate role
			 */
			Set<String> authorizationRoles = new HashSet<String>();
			for (AuthorizationUserRoleVo authorizationUserRoleVo : authorizationUserRoleVos) {
				authorizationRoles.add(authorizationUserRoleVo.getRoleCode());
			}			
			
			// Query all (permissions of) resources according to username
			List<AuthorizationUserResourceVo> authorizationUserResourceVos = authorizationUserService.selectAllUserResourcesByName(authorizationUser);
			
			/* 
			 * 	Store all resources into a set
			 * 	-- Using set here since different roles may have the same (permission of) resource
			 *     -- In this case there is no need to store duplicate (permission of) resource
			 */
			Set<String> authorizationResources = new HashSet<String>();
			for (AuthorizationUserResourceVo authorizationUserResourceVo : authorizationUserResourceVos) {
				authorizationResources.add(authorizationUserResourceVo.getResourceCode());
			}
			
			// Put all roles and resources into Shiro for authorization check
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addRoles(authorizationRoles);
			authorizationInfo.addStringPermissions(authorizationResources);
			
			// Return authorization information
			return authorizationInfo;
			
		// Exceptionally
		} catch (Exception e) {
			
			// Log the error message and return null
			log.error("=== AuthorizationRealm.doGetAuthorizationInfo | there is an exception"
					+ " | exception message: " + e.getMessage() + " ===");
			return null;
		}
	}

	/**
	 * 	This is a method to implement how a token should be authenticated
	 * 	-- This token is the one from "subject.login(token)"
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// Normally
		try {
			
			// Get username from pass-in token
			String username = ((UsernamePasswordToken) token).getUsername();
			
			// Query authorization user according to username
			AuthorizationUser query = new AuthorizationUser();
			query.setName(username);
			AuthorizationUser authorizationUser = authorizationUserService.selectOneByQuery(query);
			
			// Return authentication information
			return new SimpleAuthenticationInfo(
					authorizationUser.getName(),
					authorizationUser.getPassword(), 
					getClass().getName()
			);
			
		// Exceptionally
		} catch (Exception e) {
			
			// Log the error message and return null
			log.error("=== AuthorizationRealm.doGetAuthenticationInfo | there is an exception"
					+ " | exception message: " + e.getMessage() + " ===");
			return null;
		}
	}
}
