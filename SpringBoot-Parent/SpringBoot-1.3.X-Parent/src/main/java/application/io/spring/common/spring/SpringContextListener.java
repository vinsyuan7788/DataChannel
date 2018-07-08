package application.io.spring.common.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import application.io.spring.common.exception.CommonException;
import application.io.spring.common.service.AbstractInitService;
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
		
		String beans[] = context.getBeanDefinitionNames();	
		
		Map<Integer, List<Object>> beanMap = new HashMap<Integer, List<Object>>();
		for(String bean : beans){
			Object obj = context.getBean(bean);
			
			if(obj instanceof InitService){
				InitService initService = (InitService)obj;
				
				Integer key = initService.getOrder();
				List<Object> beanList = beanMap.get(key);
				if(beanList == null){
					beanList = new ArrayList<Object>();
					beanMap.put(key, beanList);
				}
				beanList.add(initService);
			} else if(obj instanceof AbstractInitService){
				AbstractInitService initService = (AbstractInitService)obj;
				Integer key = initService.getOrder();
				
				List<Object> beanList = beanMap.get(key);
				if(beanList == null){
					beanList = new ArrayList<Object>();
					beanMap.put(key, beanList);
				}
				beanList.add(initService);
			}
		}
		if(beanMap.isEmpty()){
			return;
		}
		Set<Integer> keySet = beanMap.keySet();
		Integer[] keyArr = keySet.toArray(new Integer[] {});   
		Arrays.sort(keyArr);
		for(int i = keyArr.length - 1; i >= 0; i --){
			Integer IKey = keyArr[i];
			List<Object> beanList = beanMap.get(IKey);
			for(Object obj : beanList){
				if(obj instanceof InitService){
					InitService initService = (InitService)obj;
					initService.init();
				} else if(obj instanceof AbstractInitService){
					AbstractInitService initService = (AbstractInitService)obj;
					initService.init();
				}
			}
		}
		
	}
}
