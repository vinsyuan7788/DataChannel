package application.io.spring.technique.shiro.api.model.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import application.io.spring.technique.shiro.api.model.permission.BitPermission;

/**
 * 	This is a class to customize a realm for authentication and authorization
 * 	-- This class is responsible for specifying how to authenticate with the token and what the authorized information is
 * 
 * @author vinsy
 *
 */
public class AuthorizationRealmOfVins extends AuthorizingRealm {
	
	/**
	 * 	This is a method to implement how a principal should be authorized
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		// Print information
		System.out.println("=== AuthorizationRealmOfVins.doGetAuthorizationInfo"
				+ " | principals: " + principals + " ===");
		
		// Instantiate an AuthorizationInfo instance
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        
		// Add roles
		authorizationInfo.addRole("CTO");  
        authorizationInfo.addRole("CIO");  
        
        // Add bit-permission
        authorizationInfo.addObjectPermission(new BitPermission("|system:user1|10"));
//        authorizationInfo.addStringPermission("|system:user2|10");
//        
//        // Add wild-card-permission
//        authorizationInfo.addObjectPermission(new WildcardPermission("system:user1:*")); 
//        authorizationInfo.addStringPermission("system:user2:*");
        
        // Return the authorization information
        return authorizationInfo; 
	}

	/**
	 * 	This is a method to implement how a token should be authenticated
	 * 	-- This token is the one from "subject.login(token)"
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// Print information
		System.out.println("=== AuthorizationRealmOfVins.doGetAuthenticationInfo"
				+ " | token: " + token + " ===");
		
		// Get principal and credentials from the token
		String principal = (String) token.getPrincipal();
		String credentials = new String((char[])token.getCredentials());
		
		// If principal and credentials are correct
		if (principal.equals("vins") && credentials.equals("abc")) {
			
			// Return the authentication information
			return new SimpleAuthenticationInfo(principal, credentials, getName());
			
		// If either principal or credentials is incorrect
		} else {
			
			// Throw authentication exception
			throw new AuthenticationException("principal or credentials incorrect");
		}
	}
}
