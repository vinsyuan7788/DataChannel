package application.io.spring.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:/mybatis/spring-mybatis.xml")
public class MyBatisConfiguration {

}
