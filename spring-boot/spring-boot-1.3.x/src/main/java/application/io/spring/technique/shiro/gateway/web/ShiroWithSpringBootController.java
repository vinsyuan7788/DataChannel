package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.bottomware.authorization.business.api.model.AuthorizationResource;
import application.io.spring.bottomware.authorization.business.api.model.AuthorizationRole;
import application.io.spring.bottomware.authorization.business.api.model.AuthorizationRoleResource;
import application.io.spring.bottomware.authorization.business.api.model.AuthorizationUser;
import application.io.spring.bottomware.authorization.business.api.model.AuthorizationUserRole;
import application.io.spring.bottomware.authorization.business.api.service.AuthorizationResourceService;
import application.io.spring.bottomware.authorization.business.api.service.AuthorizationRoleResourceService;
import application.io.spring.bottomware.authorization.business.api.service.AuthorizationRoleService;
import application.io.spring.bottomware.authorization.business.api.service.AuthorizationUserRoleService;
import application.io.spring.bottomware.authorization.business.api.service.AuthorizationUserService;
import application.io.spring.bottomware.authorization.business.api.vo.AuthorizationUserResourceVo;
import application.io.spring.bottomware.authorization.business.api.vo.AuthorizationUserRoleVo;
import application.io.spring.bottomware.authorization.technique.shiro.utils.LoginInfo;
import application.io.spring.bottomware.authorization.technique.shiro.utils.ShiroUtils;

/**
 * 	This is a class to test the integration between Shiro and Spring-Boot
 * 	-- The data for this testing comes from Enterprise XYZNKJ's ElevenScore project
 * 	   -- Currently the tables regarding users, roles, (permissions of) resources and so on are listed as below:
 *        -- t_usr_user: the users that need roles and (permissions of) resources
 *        -- t_usr_user_role: the relation between users and roles
 *        -- t_usr_role: the roles that are necessary for the enterprise
 *        -- t_usr_role_src: the relation between roles and resources
 *        -- t_usr_src: the resources that can be accessed for users
 *        -- t_log_security_audit: log the operation when a user is added or modified
 * 
 * @author vinsy
 *
 */
@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/shiro/with-spring-boot")
public class ShiroWithSpringBootController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AuthorizationUserService authorizationUserService;
	@Autowired
	private AuthorizationRoleService authorizationRoleService;
	@Autowired
	private AuthorizationUserRoleService authorizationUserRoleService;
	@Autowired
	private AuthorizationResourceService authorizationResourceService;
	@Autowired
	private AuthorizationRoleResourceService authorizationRoleResourceService;

	@RequiresRoles(value = { "franchiseeBase", "imaginaryRole" }, logical = Logical.OR)
	@RequiresPermissions(value = { "boxOperate", "imaginaryPermission" }, logical = Logical.OR)
	@RequestMapping(value = "/testAuthorizationUser", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationUser() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationUser query = new AuthorizationUser();
			query.setName("darienw");
			
			AuthorizationUser authorizationUser = authorizationUserService.selectOneByQuery(query);
			result.put("authorizationUser", authorizationUser);
			
			List<AuthorizationUserRoleVo> authorizationUserRoleVos = authorizationUserService.selectAllUserRolesByName(query);
			Set<String> authorizationRoles = new HashSet<>();
			for (AuthorizationUserRoleVo authorizationUserRoleVo : authorizationUserRoleVos) {
				authorizationRoles.add(authorizationUserRoleVo.getRoleCode());
			}
			result.put("authorizationRoles", authorizationRoles);
			result.put("numberOfAuthorizationRoles", authorizationRoles.size());
			
			List<AuthorizationUserResourceVo> authorizationUserResourceVos = authorizationUserService.selectAllUserResourcesByName(query);
			Set<String> authorizationResources = new HashSet<>();
			for (AuthorizationUserResourceVo authorizationUserResourceVo : authorizationUserResourceVos) {
				authorizationResources.add(authorizationUserResourceVo.getResourceCode());
			}	
			result.put("authorizationResources", authorizationResources);
			result.put("numberOfAuthorizationResources", authorizationResources.size());
			
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
	 		return data;
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
	 		return data;
		}
	}
	
	@RequiresRoles(value = { "imaginaryRole" })
	@RequiresPermissions(value = { "imaginaryPermission" })
	@RequestMapping(value = "/testAuthorizationRole", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationRole() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationRole query = new AuthorizationRole();
			query.setCode("admin");
			
			AuthorizationRole authorizationRole = authorizationRoleService.selectOneByQuery(query);
			
			result.put("authorizationRole", authorizationRole);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
	 		return data;
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
	 		return data;
		}
	}
	
	@RequiresRoles(value = { "franchiseeBase" })
	@RequiresPermissions(value = { "boxOperate" })
	@RequestMapping(value = "/testAuthorizationUserRole", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationUserRole() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationUserRole query = new AuthorizationUserRole();
			query.setId(178L);
			
			List<AuthorizationUserRole> authorizationRolesOfUser178 = authorizationUserRoleService.selectAllByQuery(query);
			Long numberOfAuthorizationRolesOfUser178  = authorizationUserRoleService.getAllCountByQuery(query);
			
			result.put("authorizationRolesOfUser178", authorizationRolesOfUser178);
			result.put("numberOfAuthorizationRolesOfUser178", numberOfAuthorizationRolesOfUser178);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
	 		return data;
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
	 		return data;
		}
	}
	
	@RequiresRoles(value = { "franchiseeBase", "imaginaryRole" }, logical = Logical.OR)
	@RequiresPermissions(value = { "boxOperate", "imaginaryPermission" }, logical = Logical.AND)
	@RequestMapping(value = "/testAuthorizationResource", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationResource() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationResource query = new AuthorizationResource();
			query.setCode("boxOperate");
			
			List<AuthorizationResource> authorizationResources = authorizationResourceService.selectAllByQuery(query);
			
			result.put("authorizationResources", authorizationResources);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
	 		return data;
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
	 		return data;
		}
	}
	
	@RequiresRoles(value = { "franchiseeBase", "imaginaryRole" }, logical = Logical.OR)
	@RequiresPermissions(value = { "boxOperate", "imaginaryPermission" }, logical = Logical.OR)
	@RequestMapping(value = "/testAuthorizationRoleResource", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationRoleResource() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationRoleResource query = new AuthorizationRoleResource();
			query.setId(40L);
			
			List<AuthorizationRoleResource> authorizationResourcesOfRole40 = authorizationRoleResourceService.selectAllByQuery(query);
			Long numberOfAuthorizationResourcesOfRole40 = authorizationRoleResourceService.getAllCountByQuery(query);
			
			query.setId(63L);
			
			List<AuthorizationRoleResource> authorizationResourcesOfRole63 = authorizationRoleResourceService.selectAllByQuery(query);
			Long numberOfAuthorizationResourcesOfRole63 = authorizationRoleResourceService.getAllCountByQuery(query);
			
			result.put("authorizationResourcesOfRole40", authorizationResourcesOfRole40);
			result.put("numberOfAuthorizationResourcesOfRole40", numberOfAuthorizationResourcesOfRole40);
			result.put("authorizationResourcesOfRole63", authorizationResourcesOfRole63);
			result.put("numberOfAuthorizationResourcesOfRole63", numberOfAuthorizationResourcesOfRole63);
			data.put("status", 1);
			data.put("msg", "success");
			data.put("result", result);
	 		return data;
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			data.put("status", -1);
			data.put("msg", "failure");
			data.put("result", result);
	 		return data;
		}
	}
	
	@RequestMapping(value = "/testPostWithoutShiroAnnotation", method = RequestMethod.POST)
	public Map<String, Object> testPostWithoutShiroAnnotation() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/testGetWithoutShiroAnnotation", method = RequestMethod.GET)
	public Map<String, Object> testGetWithoutShiroAnnotation() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(AuthorizationUser authorizationUser) throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Do the Shiro login and get the login information
		LoginInfo loginInfo = ShiroUtils.login(authorizationUser.getName(), authorizationUser.getPassword());
		
		// If login succeeds
		if (loginInfo.getIsLogin().booleanValue() == true) {
			
			// Get the subject
			Subject currentUser = loginInfo.getSubject();
			
			// Get the principal
			String username = (String) currentUser.getPrincipal();
			result.put("loginMsg", username + " has logged-in");
			
			// Get the session of current user
			Session session = currentUser.getSession();
			
			// Store user-name into session
			session.setAttribute("username", username);
			
			// Check what roles and resources current user have
			AuthorizationUser query = new AuthorizationUser();
			query.setName(username);
			List<AuthorizationUserRoleVo> authorizationUserRoleVos = authorizationUserService.selectAllUserRolesByName(query);
			Set<String> authorizationUserRoles = new HashSet<>();
			for (AuthorizationUserRoleVo authorizationUserRoleVo : authorizationUserRoleVos) {
				authorizationUserRoles.add(authorizationUserRoleVo.getRoleCode());
			}
			result.put("authorizationUserRoles", authorizationUserRoles);
			result.put("authorizationUserRolesSize", authorizationUserRoles.size());
			List<AuthorizationUserResourceVo> authorizationUserResourceVos = authorizationUserService.selectAllUserResourcesByName(query);
			Set<String> authorizationUserResources = new HashSet<>();
			for (AuthorizationUserResourceVo authorizationUserResourceVo : authorizationUserResourceVos) {
				authorizationUserResources.add(authorizationUserResourceVo.getResourceCode());
			}
			result.put("authorizationUserResources", authorizationUserResources);
			result.put("authorizationUserResourcesSize", authorizationUserResources.size());
			
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
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Map<String, Object> logout() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		// Get the subject
		Subject currentUser = SecurityUtils.getSubject();
		
		// If subject is not null
		if (currentUser != null) {
		
			// Store some information
			result.put("principal", currentUser.getPrincipal());
			result.put("isRemembered", currentUser.isRemembered());
			
			// if subject is authenticated
			if (currentUser.isAuthenticated()) {
				
				// Log the subject out
				currentUser.logout();
			
				// Return data
				data.put("status", 1);
				data.put("msg", "success");
				data.put("result", result);
				return data;
				
			// If subject is not authenticated
			} else {
				
				// Return data
				data.put("status", -1);
				data.put("msg", "subject not authenticated");
				data.put("result", result);
				return data;
			}
			
		// If subject is null
		} else {
			
			// Return data
			data.put("status", -1);
			data.put("msg", "subject not existed");
			data.put("result", result);
			return data;
		}
	}
}
