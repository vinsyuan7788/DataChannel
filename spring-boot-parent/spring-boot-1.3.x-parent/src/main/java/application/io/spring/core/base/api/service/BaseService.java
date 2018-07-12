package application.io.spring.core.base.api.service;

import java.util.List;

import application.io.spring.common.service.InitService;
import application.io.spring.core.base.api.model.Identifiable;

/**
 * 	This is a interface that serves as a service to be implemented for commonly-used operation
 * 
 * @author vinsy
 *
 * @param <T>
 */
public interface BaseService<T extends Identifiable> extends InitService {

	public Boolean insertSelective(T bean) throws Exception;

	public T selectOneByQuery(T query) throws Exception;
	
	public List<T> selectAllByQuery(T query) throws Exception;
	
	public List<T> selectListByQuery(T query, String orderby, Long limit, Long offset) throws Exception;

	public List<T> selectListByPrimaryKeyCollection(List<Long> primaryKeys) throws Exception;
	
	public Long getAllCountByQuery(T query) throws Exception;

	public Long getListCountByQuery(T query, Long limit, Long offset) throws Exception;
	
	public List<T> getList(T query) throws Exception;

	public Boolean deleteByCondition(T condition) throws Exception;

	public Boolean updateByPrimaryKeySelective(T bean) throws Exception;
}