package application.io.spring.technique.shiro.gateway.web;

import java.util.Arrays;
import java.util.HashMap;
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
import application.io.spring.utils.ShiroUtils;
import application.io.spring.utils.StringUtils;

/**
 * 	This is a controller to test Shiro authorization
 * 
 * @author vinsy
 *
 */
@SuppressWarnings({"unused", "deprecation"})
@RestController
@RequestMapping("/shiro/authorization")
public class ShiroAuthorizationController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/testRoles", method = RequestMethod.GET)
	public Map<String, Object> testRoles() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authorization/shiro-roles.ini", principal, credentials);
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			PrincipalCollection principals = currentUser.getPrincipals();
			result.put("loginMsg", principals + " has logged-in");
			
			String[] roles1 = new String[] { "VP", "CTO", "CIO", "PM" };
			String[] roles2 = new String[] { "CTO", "CIO", "PM" };
			String role = "VP";
			boolean ifCurrentUserHasAllRoles1 = currentUser.hasAllRoles(Arrays.asList(roles1));
			boolean ifCurrentUserHasAllRoles2 = currentUser.hasAllRoles(Arrays.asList(roles2));
			boolean[] ifCurrentUserHasRoles1 = currentUser.hasRoles(Arrays.asList(roles1));
			boolean ifCurrentUserHasRole1 = currentUser.hasRole(role);
			result.put("if " + principals + " has all roles of " + StringUtils.arrayToString(roles1), ifCurrentUserHasAllRoles1);
			result.put("if " + principals + " has all roles of " + StringUtils.arrayToString(roles2), ifCurrentUserHasAllRoles2);
			result.put("if " + principals + " has roles of " + StringUtils.arrayToString(roles1), ifCurrentUserHasRoles1);
			result.put("if " + principals + " has role of " + role, ifCurrentUserHasRole1);
			
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
