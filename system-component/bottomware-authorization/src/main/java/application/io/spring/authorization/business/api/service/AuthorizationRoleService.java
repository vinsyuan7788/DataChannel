package application.io.spring.authorization.business.api.service;

import application.io.spring.core.base.api.service.BaseService;
import application.io.spring.core.base.api.vo.PageVo;
import application.io.spring.authorization.business.api.model.AuthorizationRole;

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
public interface AuthorizationRoleService extends BaseService<AuthorizationRole> {

	// 根据jsonb条件查询分页数据
	public PageVo<AuthorizationRole> getJsonbPageableList(AuthorizationRole authorizationRole,Long offset, Long limit, String orderby);
}
