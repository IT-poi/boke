package com.cuit.boke.constant;

public interface GwConstants {

    String TRANSPARENT_USERID_FIELD = "X-UID";
    String TRANSPARENT_TOKEN_FIELD = "X-TOKE";
    String REQ_AUTH_HEAD_FIELD = "authorization";
    /** 用户id **/
    String JWT_USER_ID_FIELD = "id";

    /** 用户token **/
    String REDIS_USER_TOKEN_KEY = "tokens";

    /** 失效token **/
    String REDIS_USER_TOKEN_LOGOUT_KEY = "logoutTokens";

    String REDIS_RESET_TIME = "resetTime";

}