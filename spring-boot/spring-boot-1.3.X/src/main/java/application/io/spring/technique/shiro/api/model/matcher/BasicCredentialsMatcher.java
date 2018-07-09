package application.io.spring.technique.shiro.api.model.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 	This is a class to implement a custom credentials matcher
 * 
 * @author vinsy
 *
 */
public class BasicCredentialsMatcher implements CredentialsMatcher {

	/**
	 * 	This is a method to implement how the credentials should be matched
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		/*
		 * 	Get the credentials from AuthenticationToken (e.g., UsernamePasswordToken, etc.)
		 * 	-- This token is the one that comes from "subject.login(userpasswordToken)"
		 */
		String credentialsFromToken = new String(((UsernamePasswordToken) token).getPassword());
		
		/*
		 * 	Get the credentials from AuthenicationInfo
		 * 	-- This info is the one that comes from "doGetAuthenticationInfo" method in Realm (e.g., AuthorizingRealm, etc.)
		 */
		String credentialsFromInfo = (String) info.getCredentials();
		
		// Print information
		System.out.println("=== BasicCredentialsMatcher.doCredentialsMatch"
				+ " | credentialsFromToken: " + credentialsFromToken
				+ " | credentialsFromInfo: " + credentialsFromInfo + " ===");
		
		// Here specifies how the credentials should be matched
		if (credentialsFromToken.equals(credentialsFromInfo)) {
			return true;
		} else {
			return false;
		}
	}
}
