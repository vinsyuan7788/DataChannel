package application.io.spring.core.base.provider.service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import application.io.spring.common.exception.CommonException;
import application.io.spring.common.service.InitService;
import application.io.spring.common.spring.SpringContextHolder;
import application.io.spring.common.utils.json.GsonUtils;
import application.io.spring.core.base.api.model.Identifiable;
import application.io.spring.core.base.api.service.BaseService;
import application.io.spring.core.base.api.vo.PageVo;
import application.io.spring.core.base.provider.dao.BaseDAO;
import lombok.extern.log4j.Log4j2;

/**
 * 	This is a class that implements all commonly-used operation
 * 
 * @author vinsy
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
@Log4j2
public class BaseServiceImpl<T extends Identifiable> implements BaseService<T> {

	// Here are necessary instance variables
    private Class<T> classOfActualTypeArgument;
    private BaseDAO<T> baseDAO;

    /** 
     * 	This is a constructor for the initialization of instance variables
     */
	public BaseServiceImpl() {
		
		System.out.println("=== BaseServiceImpl.BaseServiceImpl"
				+ " | classOfActualTypeArgument: " + classOfActualTypeArgument + " ===");
		
		// Initialize class of actual type argument
		initClassOfActualTypeArgument();
		
		System.out.println("=== BaseServiceImpl.BaseServiceImpl"
				+ " | classOfActualTypeArgument: " + classOfActualTypeArgument + " ===");
	}
	
	/**
	 * 	This is a method to initialize classOfActualTypeArgument
	 */
	private void initClassOfActualTypeArgument() {
		
		/* 
		 * 	Get the type that extends this BaseServiceImpl<T extends Identifiable>
		 * 	-- E.g., if "public class UserServiceImpl extends BaseServiceImpl<User>", clazz will be "User" class
		 */
		classOfActualTypeArgument = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
	public int getInitOrder() {
		System.out.println("=== BaseServiceImpl.getInitOrder"
				+ " | initOrder: " + InitService.SERVICE_ORDER + " ===");
		return InitService.SERVICE_ORDER;
	}

	@Override
	public void init() throws CommonException {

		System.out.println("=== BaseServiceImpl.init"
				+ " | classOfActualTypeArgument: " + classOfActualTypeArgument
				+ " | baseDAO: " + baseDAO + " ===");
		
		// Initialize base DAO
		initBaseDAO();
		
		System.out.println("=== BaseServiceImpl.init"
				+ " | classOfActualTypeArgument: " + classOfActualTypeArgument
				+ " | baseDAO: " + baseDAO + " ===");
	}
	
	@Override
	public Boolean insertSelective(T bean) throws Exception {
		
		try {
			baseDAO.insertSelective(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | insertSelective throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}

	@Override
	public T selectOneByQuery(T query) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
        
		if(query != null) {
        	params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
        }
		
        params.put("offset", Integer.valueOf(0));
        params.put("limit", Integer.valueOf(1));
        
		try {
			return baseDAO.getPageableList(params).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectOneByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}

	@Override
	public List<T> selectAllByQuery(T query) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
        
		if(query != null) {
        	params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
        }
		
		try {
			return baseDAO.getPageableList(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectAllByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public List<T> selectListByQuery(T query, String orderby, Long limit, Long offset) throws Exception {
		
		try {
			return baseDAO.getPageableList(getCondition(query, offset, limit, orderby));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | selectListByQuery throws an exception"
					+ " | exception: " + e + " ===");
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
			log.error("=== BaseServiceImpl | selectListByPrimaryKeyCollection throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public Long getAllCountByQuery(T query) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
        
		if(query != null) {
        	params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
        }
		
		try {
			return baseDAO.getListCount(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | getAllCountByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}

	@Override
	public Long getListCountByQuery(T query, Long limit, Long offset) throws Exception {
		
		try {
			return baseDAO.getListCount(getCondition(query, offset, limit, null));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("=== BaseServiceImpl | getListCountByQuery throws an exception"
					+ " | exception: " + e + " ===");
			return null;
		}
	}
	
	@Override
	public List<T> getList(T query) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
        
		if(query != null) {
        	params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
        }
		
		try {
			return baseDAO.getPageableList(params);
		} catch (Exception e) {
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
			log.error("=== BaseServiceImpl | deleteByCondition throws an exception"
					+ " | exception: " + e + " ===");
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
			log.error("=== BaseServiceImpl | updateByPrimaryKeySelective throws an exception"
					+ " | exception: " + e + " ===");
			return false;
		}
	}
	
	public Map<String, Object> getCondition(T query, Long offset, Long limit, String orderby) throws Exception {
		
        Map<String, Object> params = new HashMap<>();
        
        if (query != null) {
        	 params = GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(query), Map.class);
        }
           
        return getCondition(params, offset, limit, orderby);
	}
	
	public Map<String, Object> getCondition(Map<String, Object> params, Long offset, Long limit, String orderby) {
        
		Long begin = Long.valueOf(0L);
        
		if (offset != null) {
			begin = offset;
		}
            
        Long realOffset = offset;
        if (limit == null) {
        	limit = Long.valueOf(10L);
        }
            
        if (limit != null) {
            if(offset == null) {
            	realOffset = Long.valueOf(0L);
            }
            realOffset = Long.valueOf(realOffset.longValue() * limit.longValue());
        }
        
        String orderbyStr = orderby;
        if (StringUtils.isBlank(orderby)) {
        	orderbyStr = " id desc ";
        } 
        params.put("offset", realOffset);
        params.put("beginIndex", begin);
        params.put("limit", limit);
        params.put("orderby", orderbyStr);
        return params;
	}
	
	public <K> PageVo<K> getPageVo(Map<String,Object> params) {
		
		Long offset = (Long) params.get("beginIndex");
		Long limit = (Long) params.get("limit");
		String orderby =  (String) params.get("orderby");
		
		PageVo<K> pageVo = new PageVo<K>();
		
		if (limit != null) {
			pageVo.setLimit(limit);			
		}
		
    	pageVo.setOffset(offset);
    	pageVo.setOrderby(orderby);
    	
    	return pageVo;
	}
}