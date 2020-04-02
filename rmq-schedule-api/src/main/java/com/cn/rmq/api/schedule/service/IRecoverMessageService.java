package com.cn.rmq.api.schedule.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息恢复子系统服务接口
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Path("ICmsMessageService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IRecoverMessageService {
    /**
     * 处理状态为“发送中”但长时间没有被成功确认消费的消息
     */
    @GET
    @Path("/recoverSendingMessage")
    void recoverSendingMessage();
}
