package com.example.poi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
//	/**
//	 * 首页地址
//	 */
//	@Value("${shiro.user.indexUrl}")
//	private String indexUrl;

	@Value("${file_upload_path}")
	private String fileUploadPath;

//	/**
//	 * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
//	 */
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:" + indexUrl);
////		registry.addRedirectViewController("/ems/", indexUrl);
//	}

	/**
     * 配置静态访问资源
     * @param registry
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		 registry.addResourceHandler("/ems/**").addResourceLocations("/","classpath:/static");
		/** 文件上传路径 */
		registry.addResourceHandler("/upload/**").addResourceLocations("file:" + fileUploadPath);
	}
	
//	/**
//     * 跨域支持
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	registry.addMapping("/**")
//    			.allowedHeaders("*")
//    			.allowedMethods("*")
//    			.allowedOrigins("*")
//    			.allowCredentials(true);
//    }
//
//    @Bean
//    public HandlerInterceptor getMyInterceptor(){
//        return new LoginInterceptor();
//    }
//    @Bean
//    public HandlerInterceptor getResourcesInterceptor(){
//        return new ResourcesInterceptor();
//    }
//@Override
//public void addInterceptors(InterceptorRegistry registry) {
//	InterceptorRegistration interceptorRegistration=registry.addInterceptor(getMyInterceptor());
//
//
//      	interceptorRegistration.excludePathPatterns("/favicon.ico");
//        interceptorRegistration.excludePathPatterns("/css/**");
//        interceptorRegistration.excludePathPatterns("/js/**");
//        interceptorRegistration.excludePathPatterns("/html/common/iweboffice/**");
//        interceptorRegistration.excludePathPatterns("/file/**");
//        interceptorRegistration.excludePathPatterns("/login.html");
//        interceptorRegistration.excludePathPatterns("/image/**");
//        interceptorRegistration.excludePathPatterns("/logout");
//        interceptorRegistration.excludePathPatterns("/login");
//        interceptorRegistration.excludePathPatterns("/**/none");
//        interceptorRegistration.excludePathPatterns("/office/weboffice");
//        interceptorRegistration.excludePathPatterns("/supplier/listByPage");
//        interceptorRegistration.excludePathPatterns("/supplier/listPage");
//        interceptorRegistration.excludePathPatterns("/workFlowLogger/getJumpUrlByParam");
//        interceptorRegistration.excludePathPatterns("/workFlowLogger/auditState");
//        interceptorRegistration.excludePathPatterns("/workFlowLogger/getWorkFlowLogByStatus");
//        interceptorRegistration.excludePathPatterns("/websocket/**");
//        interceptorRegistration.excludePathPatterns("/distVue/**");
//        interceptorRegistration.excludePathPatterns("/font/**");
//        interceptorRegistration.excludePathPatterns("/image/**");
//        interceptorRegistration.excludePathPatterns("/logo/**");
//	  //interceptorRegistration.excludePathPatterns("/**");
//
//
//      //包含路径
//	    interceptorRegistration.addPathPatterns("/");
//	    interceptorRegistration.addPathPatterns("/**");
//
//	    InterceptorRegistration cacheRegistration=registry.addInterceptor(getResourcesInterceptor());
//	    cacheRegistration.addPathPatterns("/js/**");
//	    cacheRegistration.addPathPatterns("/css/**");
//	    cacheRegistration.addPathPatterns("/modulesRequire/**");
//	}
    
}
