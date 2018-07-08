package application.io.spring.technique.shiro.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.io.spring.technique.shiro.api.model.matcher.CredentialsMatcher;
import application.io.spring.technique.shiro.api.model.realm.AuthorizationRealm;

/**
 * 	This is a class configure Shiro for Spring-boot
 * 
 * @author vinsy
 *
 */
@Configuration
public class ShiroConfiguration {
	
    /**
     * 	This is a bean to create a custom credential matcher
     * 
     * @return
     */
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    
    /**
     * 	This is a bean to create a custom authorization realm
     * 
     * @param matcher
     * @return
     */
    @Bean(name="authorizationRealm")
    public AuthorizationRealm authorizationRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
    	AuthorizationRealm authorizationRealm = new AuthorizationRealm();
        authorizationRealm.setCredentialsMatcher(matcher);
        return authorizationRealm;
    }
    
	/**
	 * 	This is a bean to create Shiro security manager
	 * 
	 * @param authorizationRealm
	 * @return
	 */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authorizationRealm") AuthorizationRealm authorizationRealm) {
    	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizationRealm);
        return securityManager;
    }
    
	/**
	 * 	This is a bean to create a Shiro filter
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        //配置登录的url和登录成功的url
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/home");
        
        /*
         * 	配置访问权限:
         * 	-- For URL pattern:
         *     -- ?: match one character
         *     -- *: match multiple characters
         *     -- **: match one or multiple level of directories
         * 	-- For accessibility:
         * 	   -- anon: 表示可以匿名访问
         * 	   -- authc: 表示需要认证才能访问
         */
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /* 
         * 	The following URL can be accessed anonymously
         * 	-- Including login and logout
         */
        filterChainDefinitionMap.put("/**/login*", "anon");
        filterChainDefinitionMap.put("/**/login*.*", "anon");
        filterChainDefinitionMap.put("/**/*Login*", "anon");
        filterChainDefinitionMap.put("/**/*Login*.*", "anon");
        filterChainDefinitionMap.put("/**/logout*","anon");
        filterChainDefinitionMap.put("/**/logout*.*","anon");
        filterChainDefinitionMap.put("/**/*Logout*", "anon");
        filterChainDefinitionMap.put("/**/*Logout*.*", "anon");
        /* 
         * 	The following resources can be accessed anonymously
         * 	-- Including the testing of Spring-Boot, MyBatis, etc.
         *  -- Including partial testing of Shiro, etc.
         * 	-- Including error, etc.
         */
        filterChainDefinitionMap.put("/**/spring-boot/**", "anon");
        filterChainDefinitionMap.put("/**/spring-boot/**.*", "anon");
        filterChainDefinitionMap.put("/**/mybatis/**", "anon");
        filterChainDefinitionMap.put("/**/mybatis/**.*", "anon");
        filterChainDefinitionMap.put("/**/shiro/helloShiro", "anon");
        filterChainDefinitionMap.put("/**/shiro/helloShiro.*", "anon");
        filterChainDefinitionMap.put("/**/shiro/authentication/**", "anon");
        filterChainDefinitionMap.put("/**/shiro/authorization/**", "anon");
        filterChainDefinitionMap.put("/**/shiro/session/**", "anon");
        filterChainDefinitionMap.put("/**/shiro/coding/**", "anon");
        filterChainDefinitionMap.put("/**/shiro/cryption/**", "anon");
        filterChainDefinitionMap.put("/error","anon");
        // The following URL must be accessed authentically
        filterChainDefinitionMap.put("/*", "authc");
        filterChainDefinitionMap.put("/*.*", "authc");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        return shiroFilterFactoryBean;
    }
	
    /**
     * 	This is a bean to facilitate Shiro configuration in Spring
     * 
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * 	This is a bean to enable the support of Shiro annotation
     * 	-- This configuration is necessary for Shiro annotation
     * 
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    
    /**
     * 	This is a bean to use the AOP class provided by Shiro for creation of proxy objects
     * 	-- This configuration is necessary for Shiro annotation
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {	
    	AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
