package application.io.spring.bottomware.authorization.business.api.service;

import application.io.spring.bottomware.authorization.business.api.model.AuthorizationUserRole;
import application.io.spring.core.base.api.service.BaseService;
import application.io.spring.core.base.api.vo.PageVo;

/**
 *	This is a service interface auto-generated by underlying framework. <br/>
 *	<p>
 *  This interface can be modified according to business requirement.
 *  </p>
 *	
 *  @Description 该服务接口的内容可根据实际业务需求调整
 *	@author vinsy
 *  @date 2018/03/23
 */
public interface AuthorizationUserRoleService extends BaseService<AuthorizationUserRole> {

	// 根据jsonb条件查询分页数据
	public PageVo<AuthorizationUserRole> getJsonbPageableList(AuthorizationUserRole authorizationUserRole,Long offset, Long limit, String orderby);
}
