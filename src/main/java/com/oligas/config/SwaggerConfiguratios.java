package com.oligas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger config serve para conytrolar o swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguratios extends WebMvcConfigurationSupport {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("** API DE TESTE **")
                .description("Api com spring boot para teste.").termsOfServiceUrl("Licença Publica").license("Para uso de teste")
                .version("1.0").contact(new Contact("Nelson Seixas de souza","https://github.com/nsouzarj","nsouzarj@outlook.com")).
                build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
         .addResourceLocations("classpath:/META-INF/resources/webjars/");
         }

}
