package application.io.spring.technique.springboot.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringContextListener2 implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		ApplicationContext springApplicationContext1 = event.getApplicationContext();
		ApplicationContext parentApplicationContext1 = springApplicationContext1.getParent();
		
		ApplicationContext springApplicationContext2 = ((ApplicationContextEvent) event).getApplicationContext();
		ApplicationContext parentApplicationContext2 = springApplicationContext2.getParent();
		
		System.out.println("=== SpringContextListener2"
				+ " | springApplicationContext1: " + springApplicationContext1
				+ " | parentApplicationContext1: " + parentApplicationContext1
				+ " | springApplicationContext2: " + springApplicationContext2
				+ " | parentApplicationContext2: " + parentApplicationContext2 + " ===");
	}
}
