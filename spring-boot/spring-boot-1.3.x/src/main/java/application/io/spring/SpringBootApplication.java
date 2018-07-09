package application.io.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("application.io.spring")
//@EnableAspectJAutoProxy
public class SpringBootApplication extends SpringBootServletInitializer {

	/**
	 * 	This is a main method for execution
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {    
        return application.sources(SpringBootApplication.class);    
    }
}
