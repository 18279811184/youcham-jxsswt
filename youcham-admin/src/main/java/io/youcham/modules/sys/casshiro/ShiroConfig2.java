//package io.youcham.modules.sys.casshiro;
//
//import io.buji.pac4j.filter.CallbackFilter;
//import io.buji.pac4j.filter.LogoutFilter;
//import io.buji.pac4j.filter.SecurityFilter;
//import io.buji.pac4j.subject.Pac4jSubjectFactory;
//import io.youcham.modules.sys.shiro.RedisShiroSessionDAO;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.servlet.DispatcherType;
//import javax.servlet.Filter;
//
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.jasig.cas.client.session.SingleSignOutFilter;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.pac4j.core.config.Config;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.filter.DelegatingFilterProxy;
//
///**
// * @author gongtao
// * @version 2018-03-30 10:49
// * @update 2018-08-29 升级 pac4j 版本到 4.0.0
// **/
//@Configuration
//public class ShiroConfig2 {
//
//    /** 项目工程路径 */
//    @Value("${cas.project.url}")
//    private String projectUrl;
//
//    /** 项目cas服务路径 */
//    @Value("${cas.server.url}")
//    private String casServerUrl;
//
//    /** 客户端名称 */
//    @Value("${cas.client-name}")
//    private String clientName;
//
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListenerBean(){
//        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listenerRegistrationBean= new ServletListenerRegistrationBean<>();
//        listenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
//        listenerRegistrationBean.setEnabled(true);
//        System.out.println("================================singleListener执行");
//        return listenerRegistrationBean;
//
//    }
//    
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public FilterRegistrationBean singleSignOutFilterBean(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setName("singleFilter");
//        filterRegistrationBean.setFilter(new SingleSignOutFilter()); 
//        Map<String,String> initParameters = new HashMap<String, String>();
//        initParameters.put("casServerUrlPrefix", casServerUrl);
//        filterRegistrationBean.setInitParameters(initParameters);
//        filterRegistrationBean.addUrlPatterns("/"); 
//        filterRegistrationBean.setEnabled(true);
//        System.out.println("================================singleFilter执行");
//        return filterRegistrationBean;
//    }
//
//    
//    @Bean("sessionManager")
//    public SessionManager sessionManager(RedisShiroSessionDAO redisShiroSessionDAO,
//                                         @Value("${youcham.redis.open}") boolean redisOpen,
//                                         @Value("${youcham.shiro.redis}") boolean shiroRedis){
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        //设置session过期时间为1小时(机构：毫秒)，默认为30分钟
//        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//
//        //如果开启redis缓存且youcham.shiro.redis=true，则shiro session存到redis里
//        if(redisOpen && shiroRedis){
//            sessionManager.setSessionDAO(redisShiroSessionDAO);
//        }
//        return sessionManager;
//    }
//    
//    @Bean("securityManager")
//    public DefaultWebSecurityManager securityManager(Pac4jSubjectFactory subjectFactory, SessionManager sessionManager, CasRealm casRealm){
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(casRealm);
//        manager.setSubjectFactory(subjectFactory);
//        manager.setSessionManager(sessionManager);
//        return manager;
//    }
//
//    @Bean
//    public CasRealm casRealm(){
//        CasRealm realm = new CasRealm();
//        // 使用自定义的realm
//        realm.setClientName(clientName);
//        realm.setCachingEnabled(false);
//        //暂时不使用缓存
//        realm.setAuthenticationCachingEnabled(false);
//        realm.setAuthorizationCachingEnabled(false);
//        //realm.setAuthenticationCacheName("authenticationCache");
//        //realm.setAuthorizationCacheName("authorizationCache");
//        return realm;
//    }
//
//    /**
//     * 使用 pac4j 的 subjectFactory
//     * @return
//     */
//    @Bean
//    public Pac4jSubjectFactory subjectFactory(){
//        return new Pac4jSubjectFactory();
//    }
//
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
//        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*");
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
//        return filterRegistration;
//    }
//
//    /**
//     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
//     * @param shiroFilterFactoryBean
//     */
//    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
//        /*下面这些规则配置最好配置到配置文件中 */
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//       
////        filterChainDefinitionMap.put("/**","anon");
//        
//	      filterChainDefinitionMap.put("/swagger/**", "anon");
//	      filterChainDefinitionMap.put("/v2/api-docs", "anon");
//	      filterChainDefinitionMap.put("/swagger-ui.html", "anon");
//	      filterChainDefinitionMap.put("/webjars/**", "anon");
//	      filterChainDefinitionMap.put("/swagger-resources/**", "anon");
//	
//	      filterChainDefinitionMap.put("/statics/**", "anon");
////	      filterChainDefinitionMap.put("/login.html", "anon");
//	      filterChainDefinitionMap.put("/login2.html", "anon");
//	      filterChainDefinitionMap.put("/not_permission.html", "anon");
//	       //登录测试页面
//	      filterChainDefinitionMap.put("/modules/loginindex/**", "anon");
//	      filterChainDefinitionMap.put("/sys/login", "anon");
//	      filterChainDefinitionMap.put("/sys/wxLogin/**", "anon");
//	      filterChainDefinitionMap.put("/favicon.ico", "anon");
//	      filterChainDefinitionMap.put("/captcha.jpg", "anon");
//	       //微信页面
//	      filterChainDefinitionMap.put("/modules/wechat/**", "anon");
//	       //微信考试
//	      filterChainDefinitionMap.put("/ins/insexaminationpaper/**", "anon");
//	      filterChainDefinitionMap.put("/ins/instopicwechat/**", "anon");
//	       //新闻
//	      filterChainDefinitionMap.put("/ins/insinform/**", "anon");
//	      filterChainDefinitionMap.put("/ins/inscolumn/**", "anon");
//	      filterChainDefinitionMap.put("/ins/inssuggestions/**", "anon");
//	      filterChainDefinitionMap.put("/fileAction/**", "anon");
//	      
//	    
//	        filterChainDefinitionMap.put("/callback", "callbackFilter");
//	        filterChainDefinitionMap.put("/logout", "logout");
//	        filterChainDefinitionMap.put("/**", "securityFilter");
//	      //  结束
////	      filterChainDefinitionMap.put("/**", "authc");
//        
//        // filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//    }
//
//
//    /**
//     * shiroFilter
//     * @param securityManager
//     * @param config
//     * @return
//     */
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, Config config) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        // 必须设置 SecurityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
////        shiroFilterFactoryBean.setLoginUrl(casServerUrl + "/login");
//        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        // 添加casFilter到shiroFilter中
//        loadShiroFilterChain(shiroFilterFactoryBean);
//        Map<String, Filter> filters = new HashMap<>(3);
//        //cas 资源认证拦截器
//        SecurityFilter securityFilter = new SecurityFilter();
//        securityFilter.setConfig(config);
//        securityFilter.setClients(clientName);
//        filters.put("securityFilter", securityFilter);
//        //cas 认证后回调拦截器
//        CallbackFilter callbackFilter = new CallbackFilter();
//        callbackFilter.setConfig(config);
//        callbackFilter.setDefaultUrl(projectUrl);
//        filters.put("callbackFilter", callbackFilter);
//        // 注销 拦截器
//        LogoutFilter logoutFilter = new LogoutFilter();
//        logoutFilter.setConfig(config);
//        logoutFilter.setCentralLogout(true);
//        logoutFilter.setLocalLogout(true);
////        logoutFilter.setDefaultUrl(projectUrl + "/callback?client_name=" + clientName);
//        logoutFilter.setDefaultUrl(projectUrl);
//        filters.put("logout",logoutFilter);
//        shiroFilterFactoryBean.setFilters(filters);
//        return shiroFilterFactoryBean;
//    }
//
////    @Bean
////    public SessionDAO sessionDAO(){
////        return new MemorySessionDAO();
////    }
////
////    /**
////     * 自定义cookie名称
////     * @return
////     */
////    @Bean
////    public SimpleCookie sessionIdCookie(){
////        SimpleCookie cookie = new SimpleCookie("sid");
////        cookie.setMaxAge(-1);
////        cookie.setPath("/");
////        cookie.setHttpOnly(false);
////        return cookie;
////    }
////
////    @Bean
////    public DefaultWebSessionManager sessionManager(SimpleCookie sessionIdCookie, SessionDAO sessionDAO){
////        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
////        sessionManager.setSessionIdCookie(sessionIdCookie);
////        sessionManager.setSessionIdCookieEnabled(true);
////        //30分钟
////        sessionManager.setGlobalSessionTimeout(180000);
////        sessionManager.setSessionDAO(sessionDAO);
////        sessionManager.setDeleteInvalidSessions(true);
////        sessionManager.setSessionValidationSchedulerEnabled(true);
////        return sessionManager;
////    }
//
//    /**
//     * 下面的代码是添加注解支持
//     */
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
//        // https://zhuanlan.zhihu.com/p/29161098
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    @Bean
//    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//    
//    
//    
//}