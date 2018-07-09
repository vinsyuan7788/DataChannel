package application.middleware.authorization.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.base.provider.service.BaseServiceImpl;
import application.middleware.authorization.business.api.model.AuthorizationRoleResource;
import application.middleware.authorization.business.api.service.AuthorizationRoleResourceService;

@Service("authorizationRoleResourceService")
public class AuthorizationRoleResourceServiceImpl extends BaseServiceImpl<AuthorizationRoleResource> implements AuthorizationRoleResourceService {

}
