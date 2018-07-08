package application.io.spring.common.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import application.io.spring.common.exception.CommonException;
import application.io.spring.common.service.InitService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(value = 0)
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (((ApplicationContextEvent) event).getApplicationContext().getParent() == null) {
			try {
				this.initSystem(SpringContextHolder.getApplicationContext());
			} catch (CommonException e) {
				log.error("=== SpringContextListener.onApplicatinoEvent | there is an exception"
						+ " | exception information: " + e.getMessage() + " ===");
			}
		}
	}
	
	private void initSystem(ApplicationContext context) throws CommonException{
		
		// Get the names of all injected beans
		String[] beanNames = context.getBeanDefinitionNames();	
		
		// Initialize a map stores the mapping of initialization order and corresponding beans
		Map<Integer, List<Object>> initOrderAndCorrespondingBeans = new HashMap<Integer, List<Object>>();
		
		// Traverse all bean names
		for (String beanName : beanNames) {
			
			// Get the injected bean according to bean name
			Object injectedBean = context.getBean(beanName);
			
			// If the injected bean is an instance of InitService
			if (injectedBean instanceof InitService) {
				
				// Get the initialization order of injected bean
				Integer initOrder = ((InitService) injectedBean).getInitOrder();
				
				// Get corresponding beans to the initialization order from the map
				List<Object> beans = initOrderAndCorrespondingBeans.get(initOrder);
				
				// If corresponding beans do not exist, then create a list and put it to the map
				if (beans == null) {
					beans = new ArrayList<Object>();
					initOrderAndCorrespondingBeans.put(initOrder, beans);
				}
				
				// Add the corresponding bean to bean list
				beans.add(injectedBean);
			}	
		}
		
		// If the map is empty, then directly return
		if (initOrderAndCorrespondingBeans.isEmpty()) {
			return;
		}
		
		// Otherwise get the initialization order from the map as array and do the sorting
		Integer[] initOrders = initOrderAndCorrespondingBeans.keySet().toArray(new Integer[] {});   
		Arrays.sort(initOrders);
		
		// Traverse the initialization orders
		for (int i = initOrders.length - 1; i >= 0; i --) {
			
			// Get the corresponding beans according to initialization order
			List<Object> correspondingBeans = initOrderAndCorrespondingBeans.get(initOrders[i]);
			
			// Traverse corresponding beans
			for (Object injectedBean : correspondingBeans) {
				
				// If the bean is an instance of InitService, invoke its "init" method
				if(injectedBean instanceof InitService){
					((InitService) injectedBean).init();
				}
			}
		}
		
	}
}
