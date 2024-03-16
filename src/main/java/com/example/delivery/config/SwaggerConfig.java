//package com.example.delivery.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public OpenAPI openAPI() {
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//
//        SecurityScheme securityScheme = new SecurityScheme()
//                .name(jwt) // 'name'은 보안 스키마를 참조할 때 사용되는 이름입니다. 헤더에서 사용될 이름이 아닙니다.
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT");
//
//        Components components = new Components()
//                .addSecuritySchemes(jwt, securityScheme); // 'jwt'는 문서 내에서 보안 스키마를 참조할 때 사용되는 이름입니다.
//
//        return new OpenAPI()
//                .components(components)
//                .info(apiInfo())
//                .addSecurityItem(securityRequirement);
//    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("스파르타 굿즈 판매 사이트 서버") // API의 제목
//                .description("과제5 에 대한 swagger 입니다.") // API에 대한 설명
//                .version("1.0.0"); // API의 버전
//    }
//}
//
