package com.xzit.common.sys.constant;

/**
 * 用于认证服务的常量字符串
 */
public interface AuthorizationConstant {
    String AUTHORIZATION_HEADER="Authorization";                       
    String TOKEN_HEADER="Bearer ";
    String ENCODE_SECRETE="marshmello";                              //密钥
    String CLIENT_ID="system-client";                                 //client 也是本系统的网关
    String AUTHORIZATION_ENDPOINT="http://127.0.0.1:9999";          //认证服务端点
    String CLIENT_REDIRECT_URI_DEV="http://127.0.0.1:8888/login/oauth2/code/system-client-oidc";
    String CLIENT_REDIRECT_URI_PROD="http://120.55.161.138:8888/login/oauth2/code/system-client-oidc";
    String POST_LOGOUT_URI_DEV="http://127.0.0.1:8888/logout";
    String POST_LOGOUT_URI_PROD="http://120.55.161.138:8888/logout";
    String AUTHORIZE_URL="http://localhost:9999/oauth2/authorize";
    String TOKEN_URL="http://localhost:9999/oauth2/token";


}
