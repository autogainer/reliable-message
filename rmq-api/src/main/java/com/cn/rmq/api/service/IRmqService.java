package com.cn.rmq.api.service;
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
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     * @return 消息ID
     */
    @GET
    @Path("/createPreMessage")
    String createPreMessage(@QueryParam("consumerQueue") String consumerQueue, @QueryParam("messageBody") String messageBody);

    @GET
    List<User> getUsers();
    /**
     * 确认发送消息
     *
     * @param messageId 消息 ID
     */
    @GET
    @Path("{id: \\d+}")
    void confirmAndSendMessage(String messageId);

    /**
     * 存储并发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     */
    @GET
    @Path("{id: \\d+}")
    void saveAndSendMessage(String consumerQueue, String messageBody);

    /**
     * 直接发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     */
    @GET
    @Path("{id: \\d+}")
    void directSendMessage(String consumerQueue, String messageBody);

    /**
     * 根据消息ID删除消息
     * @param messageId 消息ID
     */
    @GET
    @Path("{id: \\d+}")
    void deleteMessageById(String messageId);
}
