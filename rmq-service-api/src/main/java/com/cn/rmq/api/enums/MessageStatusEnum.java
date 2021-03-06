package com.cn.rmq.api.enums;

/**
 * <p>Title:</p>
 * <p>Description:消息状态常量</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
public enum MessageStatusEnum {
    /**
     * 待确认
     */
    WAIT((byte) 0),

    /**
     * 发送中
     */
    SENDING((byte) 1);

    private byte value;

    MessageStatusEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static String format(byte value){
        switch (value){
            case 0:
                return "待确认";
            case 1:
                return "发送中";
            default:
                return "";
        }
    }
}