package application.io.spring.common.service;

import application.io.spring.common.exception.CommonException;

/**
 * 	This is an interface to be implemented for initialization of instance variables for injected beans <br/>
 * 	-- This interface also contains some static variables that represent the initialization order
 * 
 * @author vinsy
 *
 */
public interface InitService {
	
	/**
	 * 	This is a method to be overridden to get the initialization order
	 * 
	 * @return
	 */
	public int getInitOrder();

	/**
	 * 	This is a method to be overridden to initialize instance variables
	 * 
	 * @throws CommonException
	 */
	public void init() throws CommonException;
	
	/*********************************** Initialization Order *************************************/
	/**
	 * 	The initialization order for interceptor
	 */
	public static final Integer INTERCEPT_ORDER = 1000;    		// >= 1000
	/**
	 * 	The initialization order for queue
	 */
	public static final Integer QUEUE_ORDER = 10000;        	// >= 10000
	/**
	 * 	The initialization order for controller
	 */
	public static final Integer CONTROLLER_ORDER = 20000;   	// >= 20000
	/**
	 * 	The initialization order for scheduler
	 */
	public static final Integer SCHEDULE_ORDER = 30000;   		// >= 20000
	/**
	 * 	The initialization order for service
	 */
	public static final Integer SERVICE_ORDER = 50000;	    	// >= 50000
	/**
	 * 	The initialization order for configuration
	 */
	public static final Integer CONFIGURE_ORDER = 80000;   		// >= 80000
}
