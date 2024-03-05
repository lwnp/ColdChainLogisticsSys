package com.xzit.sysauthorization.config;

import com.xzit.api.user.feign.RoleFeignClient;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
public class AccessTokenCustomizerConfig {
    @Autowired
    RoleFeignClient roleFeignClient;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return (context)->{
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())){
                UserDetailsDTO userDetailsDTO= (UserDetailsDTO) context.getPrincipal().getPrincipal();
                List<String> userRoles=roleFeignClient.listUserRoles(userDetailsDTO.getUsername()).getData();
                context.getClaims().claims(claim -> {
                    claim.put("authorities", new HashSet<>(userRoles));
                    claim.put("userId",userDetailsDTO.getId());
                });
            }
        };

    }

}
