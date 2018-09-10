package com.lance.config.res;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;//过时API

/**
 * 
 * 不影响 springboot 默认静态资源的情况下，添加静态资源路径
 * 注:springboot默认静态资源配置
 * spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/ # Locations of static resources.
 */
@Configuration
public class ResConfig implements WebMvcConfigurer  {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加静态资源配置
    	registry.addResourceHandler("/mystatic/**").addResourceLocations("classpath:/mystatic/");
    	//1.其中 addResourceLocations 的参数是动参，可以这样写 addResourceLocations(“classpath:/img1/”, “classpath:/img2/”, “classpath:/img3/”);
    	
    	//参考 WebMvcAutoConfiguration  默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources） 
    	//1.1优先级 META-INF/resources/ > resources >static >public
    	//registry.addResourceHandler("/**")
            //  .addResourceLocations("META-INF/resources/");
        //    .addResourceLocations("resources/")
        //      .addResourceLocations("static/")
        //    .addResourceLocations("public/")
    	//1.2 默认配置的 /webjars/** 映射到 classpath:/META-INF/resources/webjars/ 
    	//  registry.addResourceHandler("/webjars/**")  
      //.addResourceLocations("classpath:/META-INF/resources/webjars/");  
    	
    	//2.可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
    	//registry.addResourceHandler("/api_files/**").addResourceLocations("file:D:/data/api_files");
    }



}