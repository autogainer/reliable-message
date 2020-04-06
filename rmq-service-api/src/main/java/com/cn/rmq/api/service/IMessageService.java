package com.cn.rmq.api.service;

import com.cn.rmq.api.model.po.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 消息服务接口
 *
 * @author Chen Nan
 */
@Path("IMessageService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IMessageService extends IBaseService<Message, String> {

    /**
     * 重发消息
     *
     * @param message 消息
     */
    @GET
    @Path("/listPage")
    void resendMessage(Message message);

    /**
     * 重发消息
     *
     * @param messageId 消息ID
     */
    void resendMessageById(String messageId);

    /**
     * 标记所有重发次数超过限制的消息为已死亡
     *
     * @param resendTimes 最大重发次数限制
     * @return 处理记录数量
     */
    int updateMessageDead(Short resendTimes);

    /**
     * 指定消息id已死亡
     *
     * @param id 消息ID
     * @return 处理记录数量
     */
    int updateMessageDeadById(String id);
}
