package application.io.spring.authorization.business.provider.dao;

import application.io.spring.core.base.provider.dao.BaseDAO;
import application.io.spring.authorization.business.api.model.AuthorizationUserRole;
import java.util.List;
import java.util.Map;

/**
 *	This is a DAO interface auto-generated by underlying framework. <br/>
 *	<p>
 *  This interface can be modified according to business requirement.
 *  </p>
 *	
 *  @Description 该DAO接口的内容可根据实际业务需求调整
 *	@author vinsy
 *  @date 2018/03/23
 */
public interface AuthorizationUserRoleDAO extends BaseDAO<AuthorizationUserRole> {

	// 根据jsonb条件查询分页数据
	List<AuthorizationUserRole> getJsonbPageableList(Map<String,Object> params);

	// 根据jsonb条件查询总数量
	long getJsonbListCount(Map<String, Object> params);
}
