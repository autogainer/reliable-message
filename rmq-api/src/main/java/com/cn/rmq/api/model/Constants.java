package com.cn.rmq.api.model;

/**
 * <p>Title: Constants</p>
 * <p>Description: 常量类</p>
 *
 * @author Chen Nan
 */
public class Constants {

    private Constants() {
        throw new RuntimeException("Constants.class can't be instantiated");
    }

    /* 通用应答码 URC-Universal Response Code */
    /**
     * 应答码：成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 应答码：失败
     */
    public static final int CODE_FAILURE = 1;

    /**
     * key
     */
    public static final String KEY_CODE = "code";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";

    /**
     * 用户session
     */
    public static final String SESSION_USER = "session_user";

    /**
     * MSG
     */
    public static final String MSG_SUCCESS = "SUCCESS";
    public static final String MSG_READ_CONFIG_ERROR = "读取配置文件错误";


}
