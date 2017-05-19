package com.wang.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages ={"com.wang.controller"})
public class SwaggerConfig {
    
    @Bean
    public Docket customDocket() {
    	return new Docket(DocumentationType.SWAGGER_2)
        .select()  // 选择那些路径和api会生成document
        .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
        .paths(PathSelectors.any()) // 对所有路径进行监控
        .build().apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("测试API接口文档")
        		.description("wang©2017 Copyright.")
                .contact(new Contact("wang", "", "wslyy99@163.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("2.0")
                .build();
    }
}
