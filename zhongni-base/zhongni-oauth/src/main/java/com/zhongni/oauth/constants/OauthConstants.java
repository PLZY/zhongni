package com.zhongni.oauth.constants;

public class OauthConstants {

    public static final String DEFAULT_ERROR_CODE = "-1";

    public static final String SYSTEM_NAME = "/oauth";
    public static final String DEFAULT_SUCCESS_CODE = "0";
    public static final String NODE_DEFAULT_SUCCESS_CODE = "200";

    public static final String DEFAULT_FAIL_MSG = "failure";

    public static final String DEFAULT_SUCCESS_MSG = "success";

    public static final String ENABLE = "ENABLE";

    public static final String DISABLE = "DISABLE";

    public static final Integer NOT_SELL = 0;

    public static final Integer HAVE_SELL = 1;

    /**
     * 图片最大尺寸（单位：Bit）
     */
    public static final long MAX_IMG_FILE_SIZE = 2097152;

    public static final String DEPLOYMENT_MODE = "deployment.mode";

    /**
     * 系统默认头像路径前缀
     */
    public static final String DEFAULT_IMG_PATH_PREFIX = "default@_@";

    /**
     * 默认分割符
     */
    public static final String DEFAULT_SPLIT = "@_@";

    /**
     * 三方用户登录时默认的平台内的钱包密码
     */
    public static final String DEFAULT_WALLET_PASSWORD = "888888";
    public static final String DEFAULT_IMAGE_PATH = "default@_@/home/img/1.png";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String REDIS_AUTHORIZATION_CODE_KEY_PREFIX = "oauth:authorization:code:";
    public static final String REDIS_ACCESS_TOKEN_KEY_PREFIX = "oauth:authorization:accessToken:";


}
