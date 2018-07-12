package application.io.spring.core.base.provider.dao;

import java.util.List;
import java.util.Map;

import application.io.spring.common.exception.CommonException;
import application.io.spring.core.base.api.model.Identifiable;

/**
 * 	This is a class that serves as DAO to be implemented for commonly-used operation
 * 
 * @author vinsy
 *
 * @param <T>
 */
public interface BaseDAO<T extends Identifiable> {

	public int insert(T bean) throws CommonException;

	public int insertSelective(T bean) throws CommonException;
	
	public int updateByPrimaryKeySelective(T bean) throws CommonException;

	public int updateByPrimaryKey(T bean) throws CommonException;
    
	public T selectByPrimaryKey(Long id) throws CommonException;

	public List<T> selectByIdCollection(Map<String,Object> params) throws CommonException;
	
	public List<T> getPageableList(Map<String, Object> params) throws CommonException;
    
	public long getListCount(Map<String, Object> params) throws CommonException;
    
	public int deleteByPrimaryKey(Long id) throws CommonException;
	
	public void deleteByCondition(T condition) throws CommonException;
}