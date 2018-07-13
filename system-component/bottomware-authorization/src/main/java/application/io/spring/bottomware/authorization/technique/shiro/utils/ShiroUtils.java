package application.io.spring.bottomware.authorization.technique.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;

/**
 * 	This is a class to provide utility for Shiro
 * 
 * @author vinsy
 *
 */
public class ShiroUtils {

	/**
	 * 	This is a salt for password encryption
	 */
	public static String SALT = "royalnu-password";
	
	/**
	 * 	Privatize the constructor so that this class cannot be instantiated
	 */
	private ShiroUtils() {}
	
	/**
	 * 	This is a method to get the SecurityManager instance from Shiro. <br/>
	 * 	<p>
	 * 	This method is the same as "SecurityUtils.getSecurityManager" in Shiro,
	 * 	which however hides Shiro from the caller. 
	 * 	</p>
	 * 
	 * @return
	 */
	public static SecurityManager getSecurityManager() {
		return SecurityUtils.getSecurityManager();
	}
	
	/**
	 * 	This is a method to set the SecurityManager instance into Shiro. <br/>
	 * 	<p>
	 * 	This method is the same as "SecurityUtils.setSecurityManager" in Shiro,
	 * 	which however hides Shiro from the caller. 
	 * 	</p>
	 * 
	 * @param securityManager
	 */
	public static void setSecurityManager(SecurityManager securityManager) {
		SecurityUtils.setSecurityManager(securityManager);
	}
	
	/**
	 * 	This is a method to get the Subject instance from Shiro. <br/>
	 * 	<p>
	 * 	This method is the same as "SecurityUtils.getSubject" in Shiro,
	 * 	which however hides Shiro from the caller. 
	 * 	</p>
	 * 
	 * @return
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
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
