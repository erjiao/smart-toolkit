/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.bmsoft.toolkit.swagger.config;

import com.bmsoft.toolkit.swagger.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import springfox.boot.starter.autoconfigure.OpenApiAutoConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger配置
 *
 * @see OpenApiAutoConfiguration
 *
 * 因为 {@code OpenApiAutoConfiguration} 中会自动导入 {@code Swagger2DocumentationConfiguration} 配置,
 *
 * 而 {@code @EnableSwagger2} 的作用也是导入 {@code Swagger2DocumentationConfiguration} 配置,
 *
 * 故不管有无使用 {@code @EnableSwagger2} 注解, doc.html 的页面都可访问。
 *
 * 若想doc.html 页面不可访问, 需同时满足下面2个条件:
 *
 * 1. 不使用 {@code @EnableSwagger2} 注解（设置 smart-toolkit.swagger.enabled = false）
 * 2. 设置 springfox.documentation.enabled = false
 *
 *
 * @author llk
 */
@EnableSwagger2
@ConditionalOnProperty(name = "smart-toolkit.swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {


	@Bean
	public Docket api(SwaggerProperties swaggerProperties) {
	    // 1. 添加自定义请求头
        List<SwaggerProperties.Header> headers = swaggerProperties.getHeaders();
        List<RequestParameter> parameters = headers.stream().map(header -> new RequestParameterBuilder()
                .description(header.getDescription())
                .in(ParameterType.HEADER)
                .name(header.getName())
                .required(header.getRequired())
                .query(p -> p.model(model -> model.scalarModel(ScalarType.STRING)).defaultValue(header.getDefaultValue()))
                .build())
                .collect(Collectors.toList());

        // 2. 添加其他基本信息
		ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getGroupName())
                .host(swaggerProperties.getHost())
				.apiInfo(apiInfo(swaggerProperties))
                .globalRequestParameters(parameters)
                .select()
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));

		// 3. 添加路径
        List<String> basePaths = swaggerProperties.getBasePaths();
        List<String> excludePaths = swaggerProperties.getExcludePaths();
        basePaths.forEach(p -> builder.paths(PathSelectors.ant(p)));
        excludePaths.forEach(p -> builder.paths(PathSelectors.ant(p).negate()));

		return builder.build();
	}



	private static ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
		return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
				.license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
				.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
				.contact(new Contact(
				        swaggerProperties.getContact().getName(),
                        swaggerProperties.getContact().getUrl(),
						swaggerProperties.getContact().getEmail()
                ))
				.version(swaggerProperties.getVersion())
                .build();
	}

}
