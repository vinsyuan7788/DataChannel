package application.io.spring.technique.shiro.gateway.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.shiro.api.model.utils.LoginInfo;
import application.io.spring.utils.FileUtils;
import application.io.spring.utils.ShiroUtils;

/**
 * 	This is a controller to test Shiro authentication
 * 	-- Key elements for Shiro authentication: subject, principal, credentials
 *     -- Subject: the object representing the user that access data-channel application
 *     -- Principal: can be user-name, phone-number, email, etc.
 *     -- Credentials: can be password, sign, digital-certificate, etc.
 *     
 * @author vinsy
 *
 */
@SuppressWarnings({"unused", "deprecation"})
@RestController
@RequestMapping("/shiro/authentication")
public class ShiroAuthenticationController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/testAuthentication", method = RequestMethod.GET)
	public Map<String, Object> testAuthentication() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authentication/shiro.ini", principal, credentials);
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			PrincipalCollection principals = currentUser.getPrincipals();
			result.put("loginMsg", principals + " has logged-in");
			
			// Logout current user
			currentUser.logout();
			result.put("logoutMsg", principals + " has logged-out");
			
			// Return data
			data.put("status", 1);
			data.put("msg", "authentication succeeds");
			data.put("result", result);
			return data;
			
		// If login fails
		} else {
			
			// Return data
			result.put("errMsg", loginInfo.getMsg());
			data.put("status", -1);
			data.put("msg", "authentication fails");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testAuthenticationWithCustomRealms", method = RequestMethod.GET)
	public Map<String, Object> testAuthenticationWithCustomRealms() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authentication/shiro-custom-realms.ini", principal, credentials);
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			PrincipalCollection principals = currentUser.getPrincipals();
			result.put("loginMsg", principals + " has logged-in");
			
			// Logout current user
			currentUser.logout();
			result.put("logoutMsg", principals + " has logged-out");
			
			// Return data
			data.put("status", 1);
			data.put("msg", "authentication succeeds");
			data.put("result", result);
			return data;
			
		// If login fails
		} else {
			
			// Return data
			result.put("errMsg", loginInfo.getMsg());
			data.put("status", -1);
			data.put("msg", "authentication fails");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testAuthenticationWithAuthenticatorAndAuthenticationStrategy", method = RequestMethod.GET)
	public Map<String, Object> testAuthenticationWithAuthenticatorAndAuthenticationStrategy() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authentication/shiro-authenticator-and-authentication-strategy.ini", principal, credentials);
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			PrincipalCollection principals = currentUser.getPrincipals();
			result.put("loginMsg", principals + " has logged-in");
			
			// Logout current user
			currentUser.logout();
			result.put("logoutMsg", principals + " has logged-out");
			
			// Return data
			data.put("status", 1);
			data.put("msg", "authentication succeeds");
			data.put("result", result);
			return data;
			
		// If login fails
		} else {
			
			// Return data
			result.put("errMsg", loginInfo.getMsg());
			data.put("status", -1);
			data.put("msg", "authentication fails");
			data.put("result", result);
			return data;
		}
	}
}
