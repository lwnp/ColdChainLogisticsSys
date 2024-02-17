package com.xzit.api.user.config;

import com.xzit.api.user.feign.ResourceFeignClient;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    ResourceFeignClient resourceFeignClient;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(conf->conf.requestMatchers("/feign/**").permitAll()
                        .anyRequest().access(((authentication, object) -> {
                            boolean isMatch=false;
                            String requestURI = object.getRequest().getRequestURI();
                            List<ResourceRoleDTO> resourceRoles=resourceFeignClient.listResourceRoles().getData();
                            for (ResourceRoleDTO resourceRoleDTO:resourceRoles){
                                if(requestURI.equals(resourceRoleDTO.getUrl())){
                                    isMatch=true;
                                    break;
                                }
                            }

                            return new AuthorizationDecision(true);
                        })))
                .oauth2ResourceServer(oauth2-> {
                    oauth2.jwt(Customizer.withDefaults());
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
