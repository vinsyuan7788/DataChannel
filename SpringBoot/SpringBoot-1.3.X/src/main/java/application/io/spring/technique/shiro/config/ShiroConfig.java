//package application.io.spring.technique.shiro.config;
//
//import java.util.LinkedHashMap;
//
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import application.io.spring.technique.shiro.api.model.matcher.CredentialsMatcher;
//import application.io.spring.technique.shiro.api.model.realm.AuthorizationRealm;
//
///**
// * 	This is a class configure Shiro for Spring-boot
// * 
// * @author vinsy
// *
// */
//@Configuration
//public class ShiroConfig {
//
//	/**
//	 * 	This is a bean to create a Shiro filter
//	 * 
//	 * @param securityManager
//	 * @return
//	 */
//	@Bean(name="shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
//        
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//        
//        //配置登录的url和登录成功的url
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		shiroFilterFactoryBean.setSuccessUrl("/home");
//        
//        //配置访问权限
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/jsp/login.jsp*", "anon"); 	//表示可以匿名访问
//        filterChainDefinitionMap.put("/testLogin", "anon"); 
//        filterChainDefinitionMap.put("/testLogout*","anon");
//        filterChainDefinitionMap.put("/jsp/error.jsp*","anon");
//        filterChainDefinitionMap.put("/jsp/index.jsp*","authc");
//        filterChainDefinitionMap.put("/*", "authc");				//表示需要认证才可以访问
//        filterChainDefinitionMap.put("/**", "authc");				//表示需要认证才可以访问
//        filterChainDefinitionMap.put("/*.*", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        
//        return shiroFilterFactoryBean;
//    }
//	
//	/**
//	 * 	This is a bean to create Shiro security manager
//	 * 
//	 * @param authorizationRealm
//	 * @return
//	 */
//    @Bean(name="securityManager")
//    public SecurityManager securityManager(@Qualifier("authorizationRealm") AuthorizationRealm authorizationRealm) {
//        System.out.println("--------------shiro已经加载----------------");
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(authorizationRealm);
//        return securityManager;
//    }
//    
//    /**
//     * 	This is a bean to create a custom authorization realm
//     * 
//     * @param matcher
//     * @return
//     */
//    @Bean(name="authorizationRealm")
//    public AuthorizationRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
//        AuthorizationRealm authorizationRealm = new AuthorizationRealm();
//        authorizationRealm.setCredentialsMatcher(matcher);
//        return authorizationRealm;
//    }
//    
//    /**
//     * 	This is a bean to create a custom credential matcher
//     * 
//     * @return
//     */
//    @Bean(name="credentialsMatcher")
//    public CredentialsMatcher credentialsMatcher() {
//        return new CredentialsMatcher();
//    }
//    
//    /**
//     * 	This is a bean to facilitate Shiro configuration in Spring
//     * 
//     * @return
//     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//    
//    /**
//     * 	This is a bean to enable the support of Shiro annotation
//     * 	-- This configuration is necessary for Shiro annotation
//     * 
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//    
//    /**
//     * 	This is a bean to use the AOP class provided by Shiro for creation of proxy objects
//     * 	-- This configuration is necessary for Shiro annotation
//     * 
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//}
