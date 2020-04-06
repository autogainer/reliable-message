package com.cn.rmq.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 */
@Getter
@Setter
@ToString
public class RmqMessage implements Serializable {
    private String businessName;//业务名称 xul
    private String messageId;
    private String messageBody;
    private String consumerQueue;//消费队列 xul
    private String checkUrl;//消息确认URL  xul
    private String checkDuration;//消息确认条件，多长时间未确认的消息需进行确认（毫秒） xul
    private Short checkTimeout;//消息确认超时时间（毫秒） xul
    private String postUrl;//消息投递Url    xul
    private Short postTimeout;//消息投递超时时间（毫秒）    xul
}
