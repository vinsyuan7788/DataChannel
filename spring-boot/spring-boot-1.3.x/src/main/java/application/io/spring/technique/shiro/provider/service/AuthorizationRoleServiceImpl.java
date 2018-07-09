package application.io.spring.technique.shiro.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.base.provider.service.BaseServiceImpl;
import application.io.spring.technique.shiro.api.model.AuthorizationRole;
import application.io.spring.technique.shiro.api.service.AuthorizationRoleService;

@Service("authorizationRoleService")
public class AuthorizationRoleServiceImpl extends BaseServiceImpl<AuthorizationRole> implements AuthorizationRoleService {

}
