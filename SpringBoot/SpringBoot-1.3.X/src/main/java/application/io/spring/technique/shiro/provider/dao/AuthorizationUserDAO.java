package application.io.spring.technique.shiro.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.common.base.provider.dao.BaseDAO;
import application.io.spring.technique.shiro.api.model.AuthorizationUser;
import application.io.spring.technique.shiro.api.vo.AuthorizationUserVo;

public interface AuthorizationUserDAO extends BaseDAO<AuthorizationUser> {

	public List<AuthorizationUserVo> selectAllUserResourcesByName(Map<String, Object> params) throws Exception;
}
