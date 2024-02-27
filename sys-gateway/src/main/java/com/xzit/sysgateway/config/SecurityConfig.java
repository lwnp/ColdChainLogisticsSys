package com.xzit.sysgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {
    @Bean

   public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
       http.authorizeExchange(conf->{
           conf.anyExchange().permitAll();

       });
       return http.build();
   }


}