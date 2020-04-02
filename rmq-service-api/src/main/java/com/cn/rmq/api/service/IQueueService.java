package com.cn.rmq.api.service;

import com.cn.rmq.api.model.dto.queue.QueueAddDto;
import com.cn.rmq.api.model.dto.queue.QueueUpdateDto;
import com.cn.rmq.api.model.po.Queue;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 消费队列服务接口
 *
 * @author Chen Nan
 */
@Path("ICmsMessageService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IQueueService extends IBaseService<Queue, String> {

    /**
     * 添加
     * @param req 添加对象参数
     */
    @GET
    @Path("/add")
    void add(QueueAddDto req);

    /**
     * 更新
     * @param req 更新对象参数
     */
    void update(QueueUpdateDto req);

    /**
     * 重发队列死亡消息
     * @param id 队列ID
     * @return 重发消息数量
     */
    int resendDead(String id);
}
