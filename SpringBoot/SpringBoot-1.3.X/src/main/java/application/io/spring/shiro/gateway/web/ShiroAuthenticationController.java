package application.io.spring.shiro.gateway.web;

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

import application.io.spring.utils.FileUtils;

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
		
		/*
		 * 	Get the principal and credential
		 * 	-- Principal can be user-name, phone-number, email, etc.
		 * 	-- Credential can be password, sign, digital-certificate, etc.
		 */
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Get a security manager
		SecurityManager securityManager = new IniSecurityManagerFactory("classpath:shiro/shiro.ini")
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
			
		// Exceptionally
		} catch (Exception e) {
			
			// Return data
			result.put("errMsg", e.getMessage());
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
		
		/*
		 * 	Get the principal and credential
		 * 	-- Principal can be user-name, phone-number, email, etc.
		 * 	-- Credential can be password, sign, digital-certificate, etc.
		 */
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Get a security manager
		SecurityManager securityManager = new IniSecurityManagerFactory("classpath:shiro/shiro-custom-realms.ini")
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
			
			// Get the principals
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
			
		// Exceptionally
		} catch (Exception e) {
			
			// Return data
			result.put("errMsg", e.getMessage());
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
		
		/*
		 * 	Get the principal and credential
		 * 	-- Principal can be user-name, phone-number, email, etc.
		 * 	-- Credential can be password, sign, digital-certificate, etc.
		 */
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Get a security manager
		SecurityManager securityManager = new IniSecurityManagerFactory("classpath:shiro/shiro-authenticator-and-authentication-strategy.ini")
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
			
		// Exceptionally
		} catch (Exception e) {
			
			// Return data
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "authentication fails");
			data.put("result", result);
			return data;
		}
	}
}
