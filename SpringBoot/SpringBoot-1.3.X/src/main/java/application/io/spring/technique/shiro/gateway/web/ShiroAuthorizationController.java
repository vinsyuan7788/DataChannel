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
			
			// Predicate if a subject has specific roles: using "hasRoles" API
			String[] roles1 = new String[] { "VP", "CTO", "CIO", "PM" };
			String[] roles2 = new String[] { "CTO", "CIO", "PM" };
			String role = "VP";
			boolean ifCurrentUserHasAllRoles1 = currentUser.hasAllRoles(Arrays.asList(roles1));
			boolean ifCurrentUserHasAllRoles2 = currentUser.hasAllRoles(Arrays.asList(roles2));
			boolean[] ifCurrentUserHasRoles1 = currentUser.hasRoles(Arrays.asList(roles1));
			boolean ifCurrentUserHasRole1 = currentUser.hasRole(role);
			result.put("hasRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles1), ifCurrentUserHasAllRoles1);
			result.put("hasRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles2), ifCurrentUserHasAllRoles2);
			result.put("hasRoles: if " + principals + " has roles of " + StringUtils.arrayToString(roles1), ifCurrentUserHasRoles1);
			result.put("hasRoles: if " + principals + " has role of " + role, ifCurrentUserHasRole1);
			
			// Check if a subject has specific roles: using "checkRoles" API
			try {
				currentUser.checkRole(role);
				result.put("checkRoles: if " + principals + " has role of " + role, true);
			} catch (Exception e) {
				result.put("checkRoles: if " + principals + " has role of " + role, false);
			}
			try {
				currentUser.checkRoles(roles2);
				result.put("checkRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles2), true);
			} catch (Exception e) {
				result.put("checkRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles2), false);
			}
			try {
				currentUser.checkRoles(roles1);
				result.put("checkRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles1), true);
			} catch (Exception e) {
				result.put("checkRoles: if " + principals + " has all roles of " + StringUtils.arrayToString(roles1), false);
			}
			
			// Logout current user
			currentUser.logout();
			result.put("logoutMsg", principals + " has logged-out");
			
			// Return data
			data.put("status", 1);
			data.put("msg", "authorization succeeds");
			data.put("result", result);
			return data;
			
		// If login fails
		} else {
			
			// Return data
			result.put("errMsg", loginInfo.getMsg());
			data.put("status", -1);
			data.put("msg", "authorization fails");
			data.put("result", result);
			return data;
		}
	}
	
	@RequestMapping(value = "/testPermissions", method = RequestMethod.GET)
	public Map<String, Object> testPermissions() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the principal and credential
		String principal = request.getParameter("username");
		String credentials = request.getParameter("password");
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login("shiro/authorization/shiro-permission.ini", principal, credentials);
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			PrincipalCollection principals = currentUser.getPrincipals();
			result.put("loginMsg", principals + " has logged-in");
			
			// Predicate if a subject has specific permissions: using "isPermitted" API
			String[] permission1 = new String[] { "system:user:create", "system:user:update", "system:user:delete", "system:user:select" };
			String[] permission2 = new String[] { "system:user:create", "system:user:update", "system:user:delete" };
			String permission = "system:user:create";
			boolean ifCurrentUserHasAllPermissions1 = currentUser.isPermittedAll(permission1);
			boolean ifCurrentUserHasAllPermissions2 = currentUser.isPermittedAll(permission2);
			boolean[] ifCurrentUserHasPermissions1 = currentUser.isPermitted(permission1);
			boolean ifCurrentUserHasPermission1 = currentUser.isPermitted(permission);
			result.put("isPermitted: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission1), ifCurrentUserHasAllPermissions1);
			result.put("isPermitted: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission2), ifCurrentUserHasAllPermissions2);
			result.put("isPermitted: if " + principals + " has permissions of " + StringUtils.arrayToString(permission1), ifCurrentUserHasPermissions1);
			result.put("isPermitted: if " + principals + " has permission of " + permission, ifCurrentUserHasPermission1);
			
			// Check if a subject has specific permissions: using "checkPermissions" API
			try {
				currentUser.checkPermission(permission);
				result.put("checkPermissions: if " + principals + " has permission of " + permission, true);
			} catch (Exception e) {
				result.put("checkPermissions: if " + principals + " has permission of " + permission, false);
			}
			try {
				currentUser.checkPermissions(permission2);
				result.put("checkPermissions: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission2), true);
			} catch (Exception e) {
				result.put("checkPermissions: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission2), false);
			}
			try {
				currentUser.checkPermissions(permission1);
				result.put("checkPermissions: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission1), true);
			} catch (Exception e) {
				result.put("checkPermissions: if " + principals + " has all permissions of " + StringUtils.arrayToString(permission1), false);
			}
						
			// Logout current user
			currentUser.logout();
			result.put("logoutMsg", principals + " has logged-out");
			
			// Return data
			data.put("status", 1);
			data.put("msg", "authorization succeeds");
			data.put("result", result);
			return data;
			
		// If login fails
		} else {
			
			// Return data
			result.put("errMsg", loginInfo.getMsg());
			data.put("status", -1);
			data.put("msg", "authorization fails");
			data.put("result", result);
			return data;
		}
	}
}
