package application.io.spring.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.business.api.model.AuthorizationRole;
import application.io.spring.business.api.service.AuthorizationRoleService;
import application.io.spring.core.base.provider.service.BaseServiceImpl;

@Service("authorizationRoleService")
public class AuthorizationRoleServiceImpl extends BaseServiceImpl<AuthorizationRole> implements AuthorizationRoleService {

}
