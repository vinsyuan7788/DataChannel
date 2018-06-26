package application.io.spring.technique.shiro.api.model.realm;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * 	This is a class to customize a realm for authentication
 * 
 * @author vinsy
 *
 */
public class IvesRealm implements Realm, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 	This is a method to get the name of this realm
	 */
	@Override
	public String getName() {
		return "Ives Realm";
	}

	/**
	 * 	This is a method to specify what type of token is supported by this realm
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof UsernamePasswordToken) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 	This is a method to implement how a token should be authenticated
	 * 	-- This token is the one from "subject.login(token)"
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// Get principal and credentials from the token
		String principal = (String) token.getPrincipal();
		String credentials = new String((char[])token.getCredentials());
		
		// If principal and credentials are correct
		if (principal.equals("ives") && credentials.equals("bbb")) {
			
			// Return the authentication information
			return new SimpleAuthenticationInfo(principal, credentials, getName());
			
		// If either principal or credentials is incorrect
		} else {
			
			// Throw authentication exception
			throw new AuthenticationException("principal or credentials incorrect");
		}
	}
}
