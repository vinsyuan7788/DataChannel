package application.io.spring.business.authorization.provider.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.io.spring.business.authorization.api.model.AuthorizationUser;
import application.io.spring.business.authorization.api.service.AuthorizationUserService;
import application.io.spring.business.authorization.api.vo.AuthorizationUserResourceVo;
import application.io.spring.business.authorization.api.vo.AuthorizationUserRoleVo;
import application.io.spring.business.authorization.provider.dao.AuthorizationUserDAO;
import application.io.spring.common.utils.GsonUtils;
import application.io.spring.core.base.provider.service.BaseServiceImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("authorizationUserService")	
public class AuthorizationUserServiceImpl implements AuthorizationUserService {

	@Autowired
	private AuthorizationUserDAO authorizationUserDAO;
	
	@Override
	public Boolean insertSelective(AuthorizationUser bean) throws Exception {
		
		try {
			authorizationUserDAO.insertSelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | insertSelective throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}
	
	@Override
	public Boolean insertBatch(List<AuthorizationUser> beans) throws Exception {

		try {
			authorizationUserDAO.insertBatch(beans);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | insertBatch throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}

	@Override
	public AuthorizationUser selectOneByQuery(AuthorizationUser query) throws Exception {
		
		try {
			return authorizationUserDAO.getPageableList(getCondition(query, null, 1L, 0L)).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectOneByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}

	@Override
	public List<AuthorizationUser> selectAllByQuery(AuthorizationUser query) throws Exception {
		
		try {
			return authorizationUserDAO.getPageableList(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectAllByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public List<AuthorizationUser> selectListByQuery(AuthorizationUser query, String orderby, Long limit, Long offset) throws Exception {
		
		try {
			return authorizationUserDAO.getPageableList(getCondition(query, orderby, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectListByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}

	@Override
	public List<AuthorizationUser> selectListByPrimaryKeyCollection(List<Long> primaryKeys) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		params.put("ids", primaryKeys);
		
		try {
			return authorizationUserDAO.selectByIdCollection(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectListByPrimaryKeyCollection throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public Long getAllCountByQuery(AuthorizationUser query) throws Exception {
		
		try {
			return authorizationUserDAO.getListCount(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | getAllCountByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}

	@Override
	public Long getListCountByQuery(AuthorizationUser query, Long limit, Long offset) throws Exception {
		
		try {
			return authorizationUserDAO.getListCount(getCondition(query, null, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | getListCountByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public Boolean deleteByCondition(AuthorizationUser condition) throws Exception {
		
		try {
			authorizationUserDAO.deleteByCondition(condition);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | deleteByCondition throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}
	
	@Override
	public Boolean updateByPrimaryKeySelective(AuthorizationUser bean) throws Exception {
		
		try {
			authorizationUserDAO.updateByPrimaryKeySelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | updateByPrimaryKeySelective throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}
	
	public Map<String, Object> getCondition(AuthorizationUser query, String orderby, Long limit, Long offset) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		
    	if (query != null) {
    		params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
    	}
		
		if (orderby != null) {
			params.put("orderby", orderby);
		} else {
			params.put("orderby", null);
		}
		
		if (limit != null) {
			params.put("limit", limit);
		} else {
			params.put("limit", null);
		}
		
		if (offset != null) {
			params.put("offset", offset);
		} else {
			params.put("offset", null);
		}
		
		return params;
	}
	
	@Override
	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(AuthorizationUser query) throws Exception {
		return authorizationUserDAO.selectAllUserRolesByName(getCondition(query, null, null, null));
	}
	
	@Override
	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception {
		return authorizationUserDAO.selectAllUserResourcesByName(getCondition(query, null, null, null));
	}
}
