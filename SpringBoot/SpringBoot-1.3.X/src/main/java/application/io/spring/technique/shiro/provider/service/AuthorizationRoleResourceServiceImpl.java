package application.io.spring.technique.shiro.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.common.base.provider.service.BaseServiceImpl;
import application.io.spring.technique.shiro.api.model.AuthorizationRoleResource;
import application.io.spring.technique.shiro.api.service.AuthorizationRoleResourceService;

@Service("authorizationRoleResourceService")
public class AuthorizationRoleResourceServiceImpl extends BaseServiceImpl<AuthorizationRoleResource> implements AuthorizationRoleResourceService {

}
