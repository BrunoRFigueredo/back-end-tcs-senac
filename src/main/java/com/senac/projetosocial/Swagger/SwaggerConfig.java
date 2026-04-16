package com.senac.projetosocial.Swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Social API")
                        .version("1.0.0")
                        .description("API para gerenciamento de projetos sociais")
                        .contact(new Contact()
                                .name("Senac")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Informe o token JWT obtido no endpoint /login/")))
                .tags(List.of(
                        new Tag().name("Autenticação").description("Login e geração de token JWT"),
                        new Tag().name("Usuário").description("Cadastro e gerenciamento de usuários"),
                        new Tag().name("Instituição").description("Gerenciamento de instituições"),
                        new Tag().name("Projeto").description("Cadastro e acompanhamento de projetos sociais"),
                        new Tag().name("Categoria").description("Categorias dos projetos"),
                        new Tag().name("Serviço").description("Serviços vinculados aos projetos"),
                        new Tag().name("Insumo").description("Insumos utilizados nos projetos"),
                        new Tag().name("Projeto Insumo").description("Relação entre projetos e insumos"),
                        new Tag().name("Projeto Serviço").description("Relação entre projetos e serviços"),
                        new Tag().name("Prestação de Conta").description("Prestação de contas dos projetos"),
                        new Tag().name("Voluntário").description("Gerenciamento de voluntários"),
                        new Tag().name("Perfil e Permissão").description("Controle de acesso e perfis de usuário"),
                        new Tag().name("Imagem").description("Upload e gerenciamento de imagens")
                ));
    }
}
