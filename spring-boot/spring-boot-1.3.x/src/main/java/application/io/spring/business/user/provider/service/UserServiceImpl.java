package application.io.spring.business.user.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.business.user.api.model.User;
import application.io.spring.business.user.api.service.UserService;
import application.io.spring.core.base.provider.service.BaseServiceImpl;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

}
