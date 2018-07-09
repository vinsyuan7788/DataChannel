package application.middleware.authorization.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.core.base.provider.service.BaseServiceImpl;
import application.middleware.authorization.business.api.model.AuthorizationResource;
import application.middleware.authorization.business.api.service.AuthorizationResourceService;

@Service("authorizationResourceService")
public class AuthorizationResourceServiceImpl extends BaseServiceImpl<AuthorizationResource> implements AuthorizationResourceService {

}
