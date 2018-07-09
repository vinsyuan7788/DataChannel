package application.io.spring.business.authorization.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.business.authorization.api.model.AuthorizationRoleResource;
import application.io.spring.business.authorization.api.service.AuthorizationRoleResourceService;
import application.io.spring.core.base.provider.service.BaseServiceImpl;

@Service("authorizationRoleResourceService")
public class AuthorizationRoleResourceServiceImpl extends BaseServiceImpl<AuthorizationRoleResource> implements AuthorizationRoleResourceService {

}
