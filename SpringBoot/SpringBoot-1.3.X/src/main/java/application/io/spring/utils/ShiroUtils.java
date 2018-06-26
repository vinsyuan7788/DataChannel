package application.io.spring.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import application.io.spring.technique.shiro.api.model.utils.LoginInfo;

/**
 * 	This is a class to provide utility class regarding Shiro
 * 
 * @author vinsy
 *
 */
@SuppressWarnings("deprecation")
public class ShiroUtils {

	/**
	 * 	This is a method to do the Shiro login
	 * 
	 * @param configFilePath
	 * @param principal
	 * @param credentials
	 * @return
	 */
	public static LoginInfo login(String configFilePath, String principal, String credentials) {
		
		LoginInfo loginInfo = new LoginInfo();
		
		// Get a security manager
		SecurityManager securityManager = new IniSecurityManagerFactory("classpath:" + configFilePath)
				.getInstance();
		
		// Set the security manager to the utility class
		SecurityUtils.setSecurityManager(securityManager);
		
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
