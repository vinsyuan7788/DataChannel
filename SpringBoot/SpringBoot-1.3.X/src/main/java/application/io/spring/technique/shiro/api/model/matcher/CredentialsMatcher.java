package application.io.spring.technique.shiro.api.model.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 	This is a class to implement a custom credentials matcher
 * 
 * @author vinsy
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

	// Necessary static variables
	private static final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	
	/**
	 * 	This is a method to implement how the credentials should be matched
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		/*
		 * 	Get the credentials from AuthenticationToken (e.g., UsernamePasswordToken, etc.)
		 * 	-- This token is the one that comes from "subject.login(userpasswordToken)"
		 */
		String rawPasswordFromToken = new String(((UsernamePasswordToken) token).getPassword());
		
		// Encode raw password with salt into an encoded password
		String encodedPassword = md5PasswordEncoder.encodePassword(rawPasswordFromToken, "royalnu-password");
		
		/*
		 * 	Get the credentials from AuthenicationInfo
		 * 	-- This info is the one that comes from "doGetAuthenticationInfo" method in Realm (e.g., AuthorizingRealm, etc.)
		 */
		String credentialsFromInfo = (String) info.getCredentials();
		
		// Here specifies how the credentials should be matched
		return this.equals(encodedPassword, credentialsFromInfo);
	}
}
