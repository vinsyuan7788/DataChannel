package application.io.spring.business.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.business.api.model.AuthorizationUser;
import application.io.spring.business.api.vo.AuthorizationUserResourceVo;
import application.io.spring.business.api.vo.AuthorizationUserRoleVo;
import application.io.spring.core.base.provider.dao.BaseDAO;

public interface AuthorizationUserDAO extends BaseDAO<AuthorizationUser> {

	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(Map<String, Object> params) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(Map<String, Object> params) throws Exception;
}
