package com.bmsoft.toolkit.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * <p>Spring Boot 默认不支持 @PropertySource 读取yaml文件</p>
 * <p>故通过实现 ${@link PropertySourceFactory} 来补充yml文件读取的功能</p>
 *
 * <h3>Example usage</h3>
 *
 * 结合 @PropertySource 一起使用
 *
 * <pre class='code'>
 * &#064;PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:blog.yaml")
 * public class ClassA {
 *
 * }
 * </pre>
 *
 * @author llk
 * @date 2019-10-04 19:25
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        String sourceName = name != null ? name : resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }


    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
}
