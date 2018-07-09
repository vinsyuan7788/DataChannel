package application.io.spring.core.base.gateway.web;

import java.lang.reflect.ParameterizedType;

import application.io.spring.common.exception.CommonException;
import application.io.spring.common.service.InitService;
import application.io.spring.common.spring.SpringContextHolder;
import application.io.spring.core.base.api.model.Identifiable;
import application.io.spring.core.base.api.service.BaseService;

/**
 * 	This is a class that serves as a controller and can be extended for commonly-used operation
 * 
 * @author vinsy
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseController<T extends Identifiable> implements InitService {

	// Here are necessary instance variables
    private Class<T> classOfActualTypeArgument;
    private BaseService<T> baseService;

    /** 
     * 	This is a constructor for the initialization of instance variables
     */
	public BaseController() {
		
		// Initialize class of actual type argument
		initClassOfActualTypeArgument();
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
	 * 	This is a method to initialize baseService
	 */
	private void initBaseService() {
		
		// If baseService is existed, then no need to proceed and directly return
		if(baseService != null){
			return;
		}
		
		/* 
		 * 	Otherwise get the name from variable "clazz"
		 * 	-- E.g., if clazz is "User" class, then beanName is "User" string
		 */
		String beanName = classOfActualTypeArgument.getSimpleName();
		
		/*
		 * 	Convert the bean name to corresponding service name
		 * 	-- E.g., if beanName is "User", then daoName is "userService"
		 */
		String serviceName = Character.toString(beanName.charAt(0)).toLowerCase() + beanName.substring(1) + "Service";
		
		// If the service bean is not existed according to the service name, then no need to proceed and directly return
		if(!SpringContextHolder.existBean(serviceName)){
			return;
		}
		
		// Otherwise get the service bean according to the service name
		baseService = SpringContextHolder.getBean(serviceName);
	}
	
	@Override
	public int getInitOrder() {
		return InitService.CONTROLLER_ORDER;
	}

	@Override
	public void init() throws CommonException {

		// Initialize base service
		initBaseService();
	}
}
