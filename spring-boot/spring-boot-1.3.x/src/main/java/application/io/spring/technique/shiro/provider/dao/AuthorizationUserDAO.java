package application.io.spring.technique.shiro.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.core.base.provider.dao.BaseDAO;
import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.api.vo.AuthorizationUserResourceVo;
import application.io.spring.technique.shiro.api.vo.AuthorizationUserRoleVo;

public interface AuthorizationUserDAO extends BaseDAO<AuthorizationUser> {

	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(Map<String, Object> params) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(Map<String, Object> params) throws Exception;
}
