package application.io.spring.technique.springboot.provider.service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import application.io.spring.technique.springboot.api.model.Identifiable;
import application.io.spring.technique.springboot.api.service.BaseService;
import application.io.spring.technique.springboot.provider.dao.BaseDAO;
import application.io.spring.technique.springboot.utils.SpringContextHolder;

@SuppressWarnings("unchecked")
public class BaseServiceImpl<T extends Identifiable> implements BaseService<T> {

	// Here are necessary instance variables
    private Class<T> classOfActualTypeArgument;
    private BaseDAO<T> baseDAO;

    /** 
     * 	This is a constructor for the initialization of instance variables
     */
	public BaseServiceImpl() {
		
		// Initialize class of actual type argument
		initClassOfActualTypeArgument();
		
		// Initialize base DAO
		initBaseDAO();
	}
	
	/**
	 * 	This is a method to initialize classOfActualTypeArgument
	 */
	private void initClassOfActualTypeArgument() {
		
		/* 
		 * 	Get the type that extends this BaseServiceImpl<T extends Identifiable>
		 * 	-- E.g., if "public class UserServiceImpl extends BaseServiceImpl<User>", clazz will be "User" class
		 */
		this.classOfActualTypeArgument = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 	This is a method to initialize baseDAO
	 */
	private void initBaseDAO() {
		
		// If baseDAO is existed, then no need to proceed and directly return
		if(baseDAO != null){
			return;
		}
		
		/* 
		 * 	Otherwise get the name from variable "clazz"
		 * 	-- E.g., if clazz is "User" class, then beanName is "User" string
		 */
		String beanName = classOfActualTypeArgument.getSimpleName();
		
		/*
		 * 	Convert the bean name to corresponding DAO name
		 * 	-- E.g., if beanName is "User", then daoName is "userDAO"
		 */
		String daoName = Character.toString(beanName.charAt(0)).toLowerCase() + beanName.substring(1) + "DAO";
		
		// If the DAO bean is not existed according to the DAO name, then no need to proceed and directly return
		if(!SpringContextHolder.existBean(daoName)){
			return;
		}
		
		// Otherwise get the DAO bean according to the DAO name
		baseDAO = SpringContextHolder.getBean(daoName);
	}
	
	@Override
	public Boolean insertSelective(T bean) throws Exception {
		
		try {
			baseDAO.insertSelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean insertBatch(List<T> beans) throws Exception {

		try {
			baseDAO.insertBatch(beans);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public T selectOneByQuery(T query) throws Exception {
		
		try {
			return baseDAO.getPageableList(getCondition(query, null, 1L, 0L)).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> selectAllByQuery(T query) throws Exception {
		
		try {
			return baseDAO.getPageableList(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<T> selectListByQuery(T query, String orderby, Long limit, Long offset) throws Exception {
		
		try {
			return baseDAO.getPageableList(getCondition(query, orderby, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> selectListByPrimaryKeyCollection(List<Long> primaryKeys) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		params.put("ids", primaryKeys);
		
		try {
			return baseDAO.selectByIdCollection(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Long getAllCountByQuery(T query) throws Exception {
		
		try {
			return baseDAO.getListCount(getCondition(query, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long getListCountByQuery(T query, Long limit, Long offset) throws Exception {
		
		try {
			return baseDAO.getListCount(getCondition(query, null, limit, offset));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteByCondition(T condition) throws Exception {
		
		try {
			baseDAO.deleteByCondition(condition);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public Boolean updateByPrimaryKeySelective(T bean) throws Exception {
		
		try {
			baseDAO.updateByPrimaryKeySelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Map<String, Object> getCondition(T query, String orderby, Long limit, Long offset) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		
		BeanUtils.populate(query, params);
		
		if (orderby != null) {
			params.put("orderby", orderby);
		} else {
			params.put("orderby", "id desc");
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
}