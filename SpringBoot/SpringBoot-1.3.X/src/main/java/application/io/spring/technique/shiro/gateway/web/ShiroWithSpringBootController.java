package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.shiro.api.model.AuthorizationResource;
import application.io.spring.technique.shiro.api.model.AuthorizationRole;
import application.io.spring.technique.shiro.api.model.AuthorizationRoleResource;
import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.api.model.AuthorizationUserRole;
import application.io.spring.technique.shiro.api.service.AuthorizationResourceService;
import application.io.spring.technique.shiro.api.service.AuthorizationRoleResourceService;
import application.io.spring.technique.shiro.api.service.AuthorizationRoleService;
import application.io.spring.technique.shiro.api.service.AuthorizationUserRoleService;
import application.io.spring.technique.shiro.api.service.AuthorizationUserService;

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

	@RequestMapping(value = "/testAuthorizationUser", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationUser() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationUser query = new AuthorizationUser();
			query.setName("darienw");
			AuthorizationUser authorizationUser = authorizationUserService.selectOneByQuery(query);
			
			result.put("authorizationUser", authorizationUser);
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
	
	@RequestMapping(value = "/testAuthorizationUserRole", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationUserRole() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationUserRole query = new AuthorizationUserRole();
			query.setUserId(178L);
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
	
	@RequestMapping(value = "/testAuthorizationRoleResource", method = RequestMethod.GET)
	public Map<String, Object> testAuthorizationRoleResource() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		try {
			AuthorizationRoleResource query = new AuthorizationRoleResource();
			query.setRoleId(40L);
			List<AuthorizationRoleResource> authorizationResourcesOfRole40 = authorizationRoleResourceService.selectAllByQuery(query);
			Long numberOfAuthorizationResourcesOfRole40 = authorizationRoleResourceService.getAllCountByQuery(query);
			query.setRoleId(63L);
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
}
