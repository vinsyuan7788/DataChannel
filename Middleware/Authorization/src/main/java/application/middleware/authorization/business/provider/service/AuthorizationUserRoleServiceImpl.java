package application.middleware.authorization.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.base.provider.service.BaseServiceImpl;
import application.middleware.authorization.business.api.model.AuthorizationUserRole;
import application.middleware.authorization.business.api.service.AuthorizationUserRoleService;

@Service("authorizationUserRoleService")	
public class AuthorizationUserRoleServiceImpl extends BaseServiceImpl<AuthorizationUserRole> implements AuthorizationUserRoleService {

}
