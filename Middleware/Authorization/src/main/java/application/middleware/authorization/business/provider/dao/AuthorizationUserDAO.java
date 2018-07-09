package application.middleware.authorization.business.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.core.base.provider.dao.BaseDAO;
import application.middleware.authorization.business.api.model.AuthorizationUser;
import application.middleware.authorization.business.api.vo.AuthorizationUserResourceVo;
import application.middleware.authorization.business.api.vo.AuthorizationUserRoleVo;

public interface AuthorizationUserDAO extends BaseDAO<AuthorizationUser> {

	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(Map<String, Object> params) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(Map<String, Object> params) throws Exception;
}
