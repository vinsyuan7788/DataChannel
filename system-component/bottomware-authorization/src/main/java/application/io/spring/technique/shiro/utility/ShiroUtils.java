package application.io.spring.technique.shiro.utility;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

	/**
	 * 	This is a salt for password encryption
	 */
	public static String SALT = "royalnu-password";
	
	/**
	 * 	This is a method to do the Shiro login (without rememberMe)
	 * 
	 * @param configFilePath
	 * @param principal
	 * @param credentials
	 * @return
	 */
	public static LoginInfo login(String principal, String credentials) {
		
		LoginInfo loginInfo = new LoginInfo();
		
		// Get current user from the utility class
		Subject currentUser = SecurityUtils.getSubject();
		
		// Construct a token with principal and credential
		UsernamePasswordToken userToken = new UsernamePasswordToken(principal, credentials);
		
		// Normally
		try {
			
			// Login current user with the token
			currentUser.login(userToken);
			
			// Return success information
			loginInfo.setIsLogin(true);
			loginInfo.setSubject(currentUser);
			loginInfo.setMsg("success");
			return loginInfo;
			
		// Exceptionally
		} catch (Exception e) {
		
			// Return failure information
			loginInfo.setIsLogin(false);
			loginInfo.setSubject(null);
			loginInfo.setMsg(e.getMessage());
			return loginInfo;
		}
	}
}
