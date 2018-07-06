package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.shiro.api.model.utils.LoginInfo;
import application.io.spring.technique.shiro.utils.ShiroUtils;

/**
 * 	This is a controller to test Shiro authentication
 * 	-- Key elements for Shiro authentication: subject, principal, credentials
 *     -- Subject: the object representing the user that access data-channel application
 *     -- Principal: can be user-name, phone-number, email, etc.
 *     -- Credentials: can be password, sign, digital-certificate, etc.
 *     
 * 	The procedure of Shiro authentication works as following:
 *  -- Initialization: realms will be set from the configuration file during initialization
 *     -- {@link #org.apache.shiro.authc.pam.ModularRealmAuthenticator} ModularRealmAuthenticator.setRealms(realms)
 * 	-- Running: whenever a subject logs in with a token
 *     -- Subject.login(token) ---> 
 *     -- {@link #org.apache.shiro.mgt.SecurityManager} SecurityManager.login(subject, token) ---> 
 *     -- {@link #org.apache.shiro.authc.Authenticator} Authenticator.authenticate(token) ---> 
 *     -- {@link #org.apache.shiro.authc.pam.ModularRealmAuthenticator} ModularRealmAuthenticator.doAuthenticate(token) ---> 																			# Here is where authenticator comes into effect
 *     -- {@link #org.apache.shiro.authc.pam.ModularRealmAuthenticator} ModularRealmAuthenticator.doSingle/MultiRealmAuthentication(realms = getRealms, token) --->										# Here gets all the realms
 *     -- {@link #org.apache.shiro.authc.pam.ModularRealmAuthenticator} ModularRealmAuthenticator.getAuthenticationStrategy ---> 																		# Here is where authentication strategy comes into effect
 *     -- {@link #org.apache.shiro.authc.pam.ModularRealmAuthenticator} ModularRealmAuthenticator: realms.forEach(realm -> { realm.supports(token) ---> realm.getAuthenticationInfo(token) })			# Here is where realms come into effect
 *     -- if realm is AuthenticatingRealm, then：
 *        -- {@link #org.apache.shiro.realm.AuthenticatingRealm} AuthenticatingRealm.assertCredentialsMatch(token, info = doGetAuthenticationInfo(token))
 *        -- {@link #org.apache.shiro.realm.AuthenticatingRealm} AuthenticatingRealm.getCredentialsMatcher.doCredentialsMatch(token, info)
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
			
			// Get some information
			result.put("isAuthenticated", currentUser.isAuthenticated());
			result.put("isRemembered", currentUser.isRemembered());
			result.put("isRunAs", currentUser.isRunAs());
			
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
	
	@RequestMapping(value = "/testAuthenticationWithCredentialsMatcher", method = RequestMethod.GET)
	public Map<String, Object> testAuthenticationWithCredentialsMatcher() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authentication/shiro-credentials-matcher.ini", principal, credentials);
		
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
