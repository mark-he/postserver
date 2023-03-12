package com.eagletsoft.post.api;

import com.eagletsoft.boot.framework.api.validation.JSValidInterceptor;
import com.eagletsoft.boot.framework.common.security.AccessCut;
import com.eagletsoft.boot.framework.common.validation.JSValidationFactoryBean;
import com.eagletsoft.boot.framework.common.validation.ValidRefCut;
import com.eagletsoft.boot.framework.data.json.ExtMapperFactory;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DefaultApiConfig extends SpringBootServletInitializer implements WebMvcConfigurer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    @Bean
    public ServletRegistrationBean apiServlet() {
        DispatcherServlet servlet = new ApiServlet();
        servlet.setThrowExceptionIfNoHandlerFound(true);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        servlet.setApplicationContext(applicationContext);

        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/*");
        bean.setName("defaultApi");
        Map<String, String> params = new HashMap<>();
        params.put("detectAllHandlerExceptionResolvers", "true");
        params.put("contextConfigLocation", "classpath*:spring/defaultApi.xml");
        bean.setInitParameters(params);
        bean.setLoadOnStartup(1);
        return bean;
    }

    /*
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Bean
    public FilterRegistrationBean shiroFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.addInitParameter("targetFilterLifecycle","true");
        bean.addUrlPatterns("/*");
        return bean;
    }
*/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH");
    }

    @Bean
    public HandlerExceptionResolver apiExceptionResolver() {
        return new ProjectApiExceptionResolver();
    }

    @Bean
    public AccessCut accessCut() {
        return new AccessCut();
    }

    @Bean
    public JSValidationFactoryBean jsValidationFactoryBean() {
        return new JSValidationFactoryBean();
    }

    @Bean
    public ValidRefCut validRefCut() {
        return new ValidRefCut();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(ExtMapperFactory.create());
        return converter;
    }

    @Bean
    public JSValidInterceptor jSValidInterceptor() {
        return new JSValidInterceptor(jsValidationFactoryBean());
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setProviderClass(HibernateValidator.class);
        bean.setValidationMessageSource(messageSource);
        bean.setMessageInterpolator(new ResourceBundleMessageInterpolator() {

        });
        return bean;
    }
}
