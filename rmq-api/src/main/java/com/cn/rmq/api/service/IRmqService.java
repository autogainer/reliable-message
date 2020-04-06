package com.cn.rmq.api.service;
import com.cn.rmq.api.model.RmqMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 消息服务接口
 *
 * @author Chen Nan
 */
@Path("IRmqService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IRmqService {
    /**
     * 创建预发送消息
     *
     * @param rmqMessage
     * 需要上送以下两个字段
     * private String messageBody;
     * private String consumerQueue;
     *
     * @return messageId  消息ID
     *
     */
    @POST
    @Path("/createPreMessage")
    RmqMessage createPreMessage(RmqMessage rmqMessage);


    /**
     * 确认发送消息
     * @param rmqMessage
     * 需要上送以下字段
     * private String messageId;
     */
    @POST
    @Path("/confirmAndSendMessage")
    void confirmAndSendMessage(RmqMessage rmqMessage);

    /**
     * 存储并发送消息
     *
     * @param rmqMessage
     * 需要上送以下两个字段
     * private String messageBody;
     * private String consumerQueue;
     */
    @POST
    @Path("/saveAndSendMessage")
    void saveAndSendMessage(RmqMessage rmqMessage);

    /**
     * 直接发送消息
     *
     * @param rmqMessage
     * 需要上送以下两个字段
     * private String messageBody;
     * private String consumerQueue;
     */
    @POST
    @Path("/directSendMessage")
    void directSendMessage(RmqMessage rmqMessage);

    /**
     * 根据消息ID删除消息
     * @param rmqMessage
     * 需要上送以下字段
     * private String messageId;
     */
    @POST
    @Path("/deleteMessageById")
    void deleteMessageById(RmqMessage rmqMessage);


    /**
     * http方式投递消息
     *
     * @param rmqMessage 消息信息
     */
    void postMessage(RmqMessage rmqMessage);
}
