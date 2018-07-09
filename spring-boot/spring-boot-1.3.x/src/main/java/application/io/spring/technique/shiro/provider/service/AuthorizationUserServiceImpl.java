//package application.io.spring.technique.shiro.provider.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import application.io.spring.core.base.provider.service.BaseServiceImpl;
//import application.io.spring.technique.shiro.api.model.AuthorizationUser;
//import application.io.spring.technique.shiro.api.service.AuthorizationUserService;
//import application.io.spring.technique.shiro.api.vo.AuthorizationUserResourceVo;
//import application.io.spring.technique.shiro.api.vo.AuthorizationUserRoleVo;
//import application.io.spring.technique.shiro.provider.dao.AuthorizationUserDAO;
//
//@Service("authorizationUserService")	
//public class AuthorizationUserServiceImpl extends BaseServiceImpl<AuthorizationUser> implements AuthorizationUserService {
//
//	@Autowired
//	private AuthorizationUserDAO authorizationUserDAO;
//	
//	@Override
//	public List<AuthorizationUserRoleVo> selectAllUserRolesByName(AuthorizationUser query) throws Exception {
//		return authorizationUserDAO.selectAllUserRolesByName(getCondition(query, null, null, null));
//	}
//	
//	@Override
//	public List<AuthorizationUserResourceVo> selectAllUserResourcesByName(AuthorizationUser query) throws Exception {
//		return authorizationUserDAO.selectAllUserResourcesByName(getCondition(query, null, null, null));
//	}
//}