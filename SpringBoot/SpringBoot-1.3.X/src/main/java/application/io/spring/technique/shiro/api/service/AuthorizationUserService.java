package application.io.spring.technique.shiro.api.service;

import java.util.List;

import application.io.spring.core.base.api.service.BaseService;
import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.api.vo.AuthorizationUserVo;

public interface AuthorizationUserService extends BaseService<AuthorizationUser> {

	public List<AuthorizationUserVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception;
}
