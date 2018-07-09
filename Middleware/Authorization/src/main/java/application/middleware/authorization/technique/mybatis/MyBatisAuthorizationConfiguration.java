package application.middleware.authorization.technique.mybatis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:/mybatis/spring-mybatis-authorization.xml")
public class MyBatisAuthorizationConfiguration {

}
