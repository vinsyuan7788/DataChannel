package application.io.spring.common.service;

public class InitOrder {
	
	/*
	 *  order 越大 约先初始化
	 */
	public static final Integer INTERCEPT_ORDER = 1000;    //  >= 1000
	public static final Integer QUEUE_ORDER = 10000;        // >= 10000
	public static final Integer CONTROLLER_ORDER = 20000;   // >= 20000
	public static final Integer SCHEDULE_ORDER = 30000;   // >= 20000
	public static final Integer SERVICE_ORDER = 50000;	    // >= 50000
	public static final Integer CONFIGURE_ORDER = 80000;   // >= 80000
}
