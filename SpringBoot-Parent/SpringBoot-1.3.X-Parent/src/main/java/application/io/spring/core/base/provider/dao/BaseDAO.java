package application.io.spring.core.base.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.core.base.api.model.Identifiable;

public interface BaseDAO<T extends Identifiable> {

	public void insertSelective(T bean) throws Exception;
	
	public void insertBatch(List<T> beans) throws Exception;

	public List<T> getPageableList(Map<String, Object> params) throws Exception;

	public List<T> selectByIdCollection(Map<String, Object> params) throws Exception;
	
	public Long getListCount(Map<String, Object> params) throws Exception;
	
	public void deleteByCondition(T condition) throws Exception;
	
	public void updateByPrimaryKeySelective(T bean) throws Exception;
}