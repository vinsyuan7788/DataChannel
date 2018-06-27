package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.shiro.api.model.utils.LoginInfo;
import application.io.spring.utils.ShiroUtils;

/**
 * 	This is a controller to test Shiro authentication
 * 	-- Key elements for Shiro authentication: subject, principal, credentials
 *     -- Subject: the object representing the user that access data-channel application
 *     -- Principal: can be user-name, phone-number, email, etc.
 *     -- Credentials: can be password, sign, digital-certificate, etc.
 *     
 * 	The procedure of Shiro authentication works as following:
 *  -- Initialization: realms will be set from the configuration file during initialization
 *     -- ModularRealmAuthenticator.setRealms(realms)
 * 	-- Running: whenever a subject logs in with a token
 *     -- Subject.login(token) ---> 
 *     -- SecurityManager.login(subject, token) ---> 
 *     -- Authenticator.authenticate(token) ---> 
 *     -- ModularRealmAuthenticator.doAuthenticate(token) ---> 											# Here is where authenticator comes into effect
 *     -- ModularRealmAuthenticator.doSingle/MultiRealmAuthentication(getRealms, token) --->
 *     -- ModularRealmAuthenticator.getAuthenticationStrategy ---> 										# Here is where authentication strategy comes into effect
 *     -- ModularRealmAuthenticator: realm.supports(token) ---> realm.getAuthenticationInfo(token)		# Here is where realms come into effect
 *     
 * @author vinsy
 *
 */
@SuppressWarnings({"unused"})
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
