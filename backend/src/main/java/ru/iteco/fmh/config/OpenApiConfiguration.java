package ru.iteco.fmh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY;

/**
 * OpenApiConfiguration.
 * Конфигурация swagger для генерации документации API.
 *
 * @author Viktor_Loskutov
 */
@Profile({"dev"})
@Configuration
public class OpenApiConfiguration {

    @Value("${swagger.info.title}")
    private String title;

    @Value("${swagger.info.description}")
    private String description;

    @Value("${swagger.security.schemeName}")
    private String securitySchemeName;

    @Value("${swagger.security.format}")
    private String securityFormat;

    /**
     * Создание OpenAPI элемента, описывающего пространства api.
     *
     * @return {@link OpenAPI} описание документации для пространства api.
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(getSecurityItem())
                .components(getComponents())
                .info(apiInfo());
    }

    /**
     * Метод преобразует LocalDate в String
     * для более удобного визуального отображения
     * и избежания ошибок сериализации
     */
    @Bean
    public void configureLocalTimeSchema() {
        var schema = new Schema<LocalTime>();
        schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, schema);
    }

    /**
     * Метод формирует требования к безопасности для запросов.
     *
     * @return {@link SecurityRequirement} требования к безопасности.
     */
    private SecurityRequirement getSecurityItem() {
        return new SecurityRequirement()
                .addList(securitySchemeName);
    }

    /**
     * Метод формирует RequestParameter для заголовка Authorization token.
     *
     * @return {@link Components}, который будет присутствовать в каждом endpoint.
     */
    private Components getComponents() {
        return new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(APIKEY)
                        .in(HEADER)
                        .bearerFormat(securityFormat));
    }

    /**
     * Получение информации, содержащей краткое описание API.
     *
     * @return {@link Info} информация об API.
     */
    private Info apiInfo() {
        return new Info()
                .title(title)
                .description(description);
    }
}