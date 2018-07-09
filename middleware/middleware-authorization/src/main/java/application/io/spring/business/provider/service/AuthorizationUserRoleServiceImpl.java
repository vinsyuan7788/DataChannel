package application.io.spring.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.business.api.model.AuthorizationUserRole;
import application.io.spring.business.api.service.AuthorizationUserRoleService;
import application.io.spring.core.base.provider.service.BaseServiceImpl;

@Service("authorizationUserRoleService")	
public class AuthorizationUserRoleServiceImpl extends BaseServiceImpl<AuthorizationUserRole> implements AuthorizationUserRoleService {

}
