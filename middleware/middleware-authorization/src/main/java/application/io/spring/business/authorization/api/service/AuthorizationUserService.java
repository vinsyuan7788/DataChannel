package application.io.spring.business.authorization.api.service;

import java.util.List;

import application.io.spring.business.authorization.api.model.AuthorizationUser;
import application.io.spring.business.authorization.api.vo.AuthorizationUserResourceVo;
import application.io.spring.business.authorization.api.vo.AuthorizationUserRoleVo;
import application.io.spring.core.base.api.service.BaseService;

public interface AuthorizationUserService {

	public Boolean insertSelective(AuthorizationUser bean) throws Exception;
	
	public Boolean insertBatch(List<AuthorizationUser> beans) throws Exception;

	public AuthorizationUser selectOneByQuery(AuthorizationUser query) throws Exception;
	
	public List<AuthorizationUser> selectAllByQuery(AuthorizationUser query) throws Exception;
	
	public List<AuthorizationUser> selectListByQuery(AuthorizationUser query, String orderby, Long limit, Long offset) throws Exception;

	public List<AuthorizationUser> selectListByPrimaryKeyCollection(List<Long> primaryKeys) throws Exception;
	
	public Long getAllCountByQuery(AuthorizationUser query) throws Exception;

	public Long getListCountByQuery(AuthorizationUser query, Long limit, Long offset) throws Exception;

	public Boolean deleteByCondition(AuthorizationUser condition) throws Exception;

	public Boolean updateByPrimaryKeySelective(AuthorizationUser bean) throws Exception;
	
	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(AuthorizationUser query) throws Exception;
	
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception;
}
