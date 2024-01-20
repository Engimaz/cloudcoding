package cn.edu.hcnu.manager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Slf4j
@Configuration("managerSwaggerConfig")
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI api = new OpenAPI();

        api.info(new Info()
                .summary("AICHEN")
                .title("云上编程系统API")
                .version("1.0")
                .description("系统管理API")
                .contact(author())
        );
        api.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
        api.components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme().name(HttpHeaders.AUTHORIZATION).type(SecurityScheme.Type.HTTP).scheme("bearer")));
        return api;
    }


    private Contact author() {
        Contact contact = new Contact();
        contact.setEmail("2291649168@qq.com");
        contact.setName("AICHEN");
        contact.setUrl("暂无");
        return contact;
    }

}
