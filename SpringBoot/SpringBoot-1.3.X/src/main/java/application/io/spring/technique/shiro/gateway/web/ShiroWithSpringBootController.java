package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.technique.shiro.api.model.AuthorizationUser;
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

	@RequestMapping(value = "/testSelectAuthorizationUser", method = RequestMethod.GET)
	public Map<String, Object> testSelectAuthorizationUser() throws Exception {
		
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
}
