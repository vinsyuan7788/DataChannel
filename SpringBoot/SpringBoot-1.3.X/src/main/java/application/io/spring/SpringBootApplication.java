package application.io.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ComponentScan("application.io.spring")
@ImportResource("classpath:/mybatis/spring-mybatis.xml")
//@EnableAspectJAutoProxy
public class SpringBootApplication {

	/**
	 * 	This is a main method for execution
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
}
