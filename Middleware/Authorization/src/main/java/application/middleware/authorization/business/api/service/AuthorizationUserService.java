package application.middleware.authorization.business.api.service;

import java.util.List;

import application.io.spring.core.base.api.service.BaseService;
import application.middleware.authorization.business.api.model.AuthorizationUser;
import application.middleware.authorization.business.api.vo.AuthorizationUserResourceVo;
import application.middleware.authorization.business.api.vo.AuthorizationUserRoleVo;

public interface AuthorizationUserService extends BaseService<AuthorizationUser> {

	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(AuthorizationUser query) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception;
}
