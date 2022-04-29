//package com.daniel.cart.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * 解决前后端分离系统的跨域请求的问题
// */
//@Configuration
//public class CorsConfig {
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 允许任何域名使用
//        corsConfiguration.addAllowedOriginPattern("*");
//        // 允许任何头
//        corsConfiguration.addAllowedHeader("*");
//        // 允许任何方法（post、get等）
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setMaxAge(3600L);
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // 对接口配置跨域设置
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
//}
