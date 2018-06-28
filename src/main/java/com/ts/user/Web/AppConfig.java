package com.ts.user.Web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NewUserInterceptor newUserInterceptor() {
        return new NewUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // intercepts routes.
        registry.addInterceptor(newUserInterceptor()).addPathPatterns("/");
    }
}
