package com.hardyz.emos.wx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Docket createApiRest() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        // 配置ApiInfoBuilder
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("EMOS在线办公系统");
        // 封装ApiInfoBuilder
        ApiInfo build = apiInfoBuilder.build();
        docket.apiInfo(build);

        // 设置swagger显示的方法
        ApiSelectorBuilder selectorBuilder = docket.select();
        // 设置扫描的路径为所有包
        selectorBuilder.paths(PathSelectors.any());
        // 设置swagger扫描@ApiOperation注解描述的所有方法
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        docket = selectorBuilder.build();

        // 设置token校验信息
        ApiKey apiKey = new ApiKey("token", "token", "header");
        List<ApiKey> list = new ArrayList<ApiKey>(){{
            add(apiKey);
        }};
        docket.securitySchemes(list);

        // 设置作用范围
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "acessEverything");
        AuthorizationScope[] authorizationScopes = {authorizationScope};
        SecurityReference securityReference = new SecurityReference("token", authorizationScopes);
        List refList = new ArrayList(){{
            add(securityReference);
        }};
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        List cxtList = new ArrayList(){{
            add(context);
        }};
        docket.securityContexts(cxtList);


        return docket;
    }

}
