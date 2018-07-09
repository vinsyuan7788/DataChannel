package application.io.spring.business.provider.service;

import org.springframework.stereotype.Service;

import application.io.spring.business.api.model.AuthorizationResource;
import application.io.spring.business.api.service.AuthorizationResourceService;
import application.io.spring.core.base.provider.service.BaseServiceImpl;

@Service("authorizationResourceService")
public class AuthorizationResourceServiceImpl extends BaseServiceImpl<AuthorizationResource> implements AuthorizationResourceService {

}
