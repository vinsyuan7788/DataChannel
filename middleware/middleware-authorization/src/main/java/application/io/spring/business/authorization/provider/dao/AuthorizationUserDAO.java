package application.io.spring.business.authorization.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.business.authorization.api.model.AuthorizationUser;
import application.io.spring.business.authorization.api.vo.AuthorizationUserResourceVo;
import application.io.spring.business.authorization.api.vo.AuthorizationUserRoleVo;
import application.io.spring.core.base.provider.dao.BaseDAO;

public interface AuthorizationUserDAO {
	
	public void insertSelective(AuthorizationUser bean) throws Exception;
	
	public void insertBatch(List<AuthorizationUser> beans) throws Exception;

	public List<AuthorizationUser> getPageableList(Map<String, Object> params) throws Exception;

	public List<AuthorizationUser> selectByIdCollection(Map<String, Object> params) throws Exception;
	
	public Long getListCount(Map<String, Object> params) throws Exception;
	
	public void deleteByCondition(AuthorizationUser condition) throws Exception;
	
	public void updateByPrimaryKeySelective(AuthorizationUser bean) throws Exception;

	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(Map<String, Object> params) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(Map<String, Object> params) throws Exception;
}
