package application.io.spring.business.user.gateway.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.io.spring.business.user.api.model.User;
import application.io.spring.business.user.api.service.UserService;
import application.io.spring.common.utils.response.ResponseUtils;
import application.io.spring.core.base.gateway.web.BaseController;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<User> {

	@Resource
	private HttpServletResponse response;
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add() throws Exception {
		
		Map<String, Object> result = ResponseUtils.getResult();
		
		User user = new User();
		user.setUsername("vinsyuan7788");
		user.setPassword("vinsyuan7788");
		userService.insertSelective(user);
		
		ResponseUtils.writeResponse(response, 1, "success", result);
	}
}
