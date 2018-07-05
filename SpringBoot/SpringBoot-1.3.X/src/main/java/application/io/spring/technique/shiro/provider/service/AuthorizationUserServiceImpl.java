package application.io.spring.technique.shiro.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.common.base.provider.service.BaseServiceImpl;
import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.api.service.AuthorizationUserService;

@Service("authorizationUserService")	
public class AuthorizationUserServiceImpl extends BaseServiceImpl<AuthorizationUser> implements AuthorizationUserService {

}
