package application.middleware.authorization.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.base.provider.service.BaseServiceImpl;
import application.middleware.authorization.business.api.model.AuthorizationRole;
import application.middleware.authorization.business.api.service.AuthorizationRoleService;

@Service("authorizationRoleService")
public class AuthorizationRoleServiceImpl extends BaseServiceImpl<AuthorizationRole> implements AuthorizationRoleService {

}
