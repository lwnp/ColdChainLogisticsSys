package com.xzit.api.user.config;

import com.xzit.api.user.feign.ResourceFeignClient;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    ResourceFeignClient resourceFeignClient;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(conf-> conf.requestMatchers("/feign/**",
                                "/doc.html",
                                "/webjars/**",
                                "/v3/**",
                                "/swagger-resources/**").permitAll()
                        .anyRequest().access(((authentication, object) -> {
                            AntPathMatcher antPathMatcher = new AntPathMatcher();
                            boolean isMatch = false;
                            String requestURI = object.getRequest().getRequestURI();
                            List<ResourceRoleDTO> resourceRoles = resourceFeignClient.listResourceRoles().getData();
                            if(resourceRoles==null){
                                return new AuthorizationDecision(true);
                            }
                            for (ResourceRoleDTO resourceRoleDTO : resourceRoles) {
                                if (antPathMatcher.match(requestURI, resourceRoleDTO.getUrl())) {
                                    isMatch = true;
                                    List<String> roles = resourceRoleDTO.getRoleList();
                                    Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
                                    for (GrantedAuthority authority : authorities) {
                                        for (String role : roles) {
                                            if (authority.getAuthority().equals(role)) {
                                                return new AuthorizationDecision(true);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!isMatch) {
                                if (authentication.get() instanceof OAuth2AuthenticationToken) {
                                    return new AuthorizationDecision(false);
                                } else {
                                    return new AuthorizationDecision(true);
                                }
                            }
                            return new AuthorizationDecision(false);
                        })))
                .oauth2ResourceServer(oauth2-> {
                    oauth2.jwt(Customizer.withDefaults());
                    oauth2.accessDeniedHandler(accessDeniedHandler);
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
