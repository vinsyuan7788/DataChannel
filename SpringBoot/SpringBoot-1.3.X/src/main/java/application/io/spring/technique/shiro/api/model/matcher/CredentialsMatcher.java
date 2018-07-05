package application.io.spring.technique.shiro.api.model.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 	This is a class to implement a custom credentials matcher
 * 
 * @author vinsy
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

	/**
	 * 	This is a method to implement how the credentials should be matched
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		/*
		 * 	Get the credentials from AuthenticationToken (e.g., UsernamePasswordToken, etc.)
		 * 	-- This token is the one that comes from "subject.login(userpasswordToken)"
		 */
		Object credentialsFromToken = token.getCredentials();
		
		/*
		 * 	Get the credentials from AuthenicationInfo
		 * 	-- This info is the one that comes from "doGetAuthenticationInfo" method in Realm (e.g., AuthorizingRealm, etc.)
		 */
		Object credentialsFromInfo = token.getCredentials();
		
		// Here specifies how the credentials should be matched
		return this.equals(credentialsFromToken, credentialsFromInfo);
	}
}
