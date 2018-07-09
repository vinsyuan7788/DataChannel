package application.io.spring.business.authorization.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.business.authorization.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.utility.LoginInfo;
import application.io.spring.technique.shiro.utility.ShiroUtils;

@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/middleware/authorization")
public class TestAuthorizationController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
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
