package com.cn.rmq.api.schedule.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息确认子系统服务接口
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Path("ICmsMessageService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface ICheckMessageService {
    /**
     * 处理所有长时间未确认的消息，和上游业务系统确认是否发送该消息
     */
    @GET
    @Path("/checkWaitingMessage")
    void checkWaitingMessage();
}
