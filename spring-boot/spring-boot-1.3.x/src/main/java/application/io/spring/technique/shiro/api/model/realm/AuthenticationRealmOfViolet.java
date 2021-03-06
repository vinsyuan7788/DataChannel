package application.io.spring.technique.shiro.api.model.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * 	This is a class to customize a realm for authentication
 * 	-- This class is responsible for specifying how to authenticate with the token
 * 
 * @author vinsy
 *
 */
public class AuthenticationRealmOfViolet extends AuthenticatingRealm {

	/**
	 * 	This is a method to implement how a token should be authenticated
	 * 	-- This token is the one from "subject.login(token)"
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// Print information
		System.out.println("=== AuthenticationRealmOfViolet.doGetAuthenticationInfo"
				+ " | token: " + token + " ===");
		
		// Get principal and credentials from the token
		String principal = (String) token.getPrincipal();
		String credentials = new String((char[])token.getCredentials());
		
		// If principal and credentials are correct
		if (principal.equals("violet") && credentials.equals("cba")) {
			
			// Return the authentication information
			return new SimpleAuthenticationInfo(principal, credentials, getName());
			
		// If either principal or credentials is incorrect
		} else {
			
			// Throw authentication exception
			throw new AuthenticationException("principal or credentials incorrect");
		}
	}
}
