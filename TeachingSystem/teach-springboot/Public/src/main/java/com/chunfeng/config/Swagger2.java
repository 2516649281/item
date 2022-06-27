package com.chunfeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    //配置核心配置信息
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chunfeng.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    //声明api/文档属性 构建器
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("教务管理系统")
                .description("前端调试接口文档")
                .version("1.0.0")
                .build();
    }
}
