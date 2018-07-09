package application.io.spring.technique.springboot.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Lazy(false)
@Component
public class SpringContextHolder2 implements ApplicationContextAware {
	
	// Necessary static variables
    private static ApplicationContext springApplicationContext;
    private static Set<String> beanNameSet = new HashSet<String>();

    /**
     * 	This is a method to get Spring application context and all names of injected beans in a static way
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
    	
    	// Store Spring application context and store it in a static way
    	springApplicationContext = applicationContext;
    	
    	// Get the names of all injected beans
    	String[] allBeanNames = applicationContext.getBeanDefinitionNames();
    	
    	// Store them in a static way
    	beanNameSet.addAll(Arrays.asList(allBeanNames));
    	
    	// Print information
    	System.out.println("=== SpringContextHolder2"
    			+ " | springApplicationContext: " + springApplicationContext
    			+ " | beanNameSet: " + beanNameSet + " ===");
    }
    
    /**
     * 	This is a method to get Spring applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return springApplicationContext;
    }

    /**
     * 	This is a method to check if a bean exists according to the bean name
     * 
     * @param name
     * @return
     */
    public static boolean existBean(String name) {
        checkApplicationContext();
        return beanNameSet.contains(name);
    }
    
    /**
     * 	This is a method to get an injected bean according to the bean name
     */
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) springApplicationContext.getBean(name);
    }

    /**
     * 	This is a method to get an injected bean according to the bean type
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) springApplicationContext.getBeansOfType(clazz);
    }
    
    /**
     * 	This is a method to get the names of all beans injected in Spring
     * 
     * @return
     */
    public static String[] getBeans() {
        checkApplicationContext();
        return springApplicationContext.getBeanDefinitionNames();
    }

    /**
     * 	This is a method to clear Spring applicationContext
     */
    public static void cleanApplicationContext() {
    	springApplicationContext = null;
    }

    /**
     * 	This is a method to check Spring applicationContext
     */
    private static void checkApplicationContext() {
        if (springApplicationContext == null) {
            throw new IllegalStateException("Spring applicationContext has not been injected yet!");
        }
    }
}
