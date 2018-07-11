package application.io.spring.authorization.business.api.service;

import java.util.List;

import application.io.spring.authorization.business.api.model.AuthorizationUser;
import application.io.spring.authorization.business.api.vo.AuthorizationUserResourceVo;
import application.io.spring.authorization.business.api.vo.AuthorizationUserRoleVo;
import application.io.spring.core.base.api.service.BaseService;

public interface AuthorizationUserService extends BaseService<AuthorizationUser> {
	
	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(AuthorizationUser query) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception;
}
