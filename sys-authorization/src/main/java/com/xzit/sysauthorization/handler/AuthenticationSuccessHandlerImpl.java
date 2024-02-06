package com.xzit.sysauthorization.handler;

import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    UserFeignClient userFeignClient;
    private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

    @Setter
    private Consumer<OAuth2User> oauth2UserHandler = (user) -> {};
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            if (authentication.getPrincipal() != null) {
                this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal());
            }
        }
        UserDetailsDTO userDetailsDTO=(UserDetailsDTO)authentication.getPrincipal();
        userFeignClient.userAuthenticationSuccess(userDetailsDTO.getId());
        this.delegate.onAuthenticationSuccess(request, response, authentication);

    }
}
