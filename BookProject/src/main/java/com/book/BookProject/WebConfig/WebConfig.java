package com.book.BookProject.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")  // 클라이언트에서 요청할 URL 패턴
                .addResourceLocations("file:/C:/FrontEnd/new2/BookProject/src/main/resources/static/images/"); // 실제 파일 위치
    }
}
