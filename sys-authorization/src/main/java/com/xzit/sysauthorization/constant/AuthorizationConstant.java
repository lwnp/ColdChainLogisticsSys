package com.xzit.sysauthorization.constant;

/**
 * 用于认证服务的常量字符串
 */
public interface AuthorizationConstant {
    String AUTHORIZATION_HEADER="Authorization";                       
    String TOKEN_HEADER="Bearer ";
    String ENCODE_SECRETE="marshmello";                              //密钥
    String CLIENT_ID="system-client";                                 //client 也是本系统的网关
    String AUTHORIZATION_ENDPOINT="http://127.0.0.1:9999";          //认证服务端点
    String CLIENT_REDIRECT_URI="http://127.0.0.1:8888/login/oauth2/code/system-client-oidc";
    String POST_LOGOUT_URI="http://127.0.0.1:8888/logout";


}
