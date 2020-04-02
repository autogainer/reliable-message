package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IBaseService;

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
@Path("ICmsMessageService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface ICmsMessageService extends IBaseService<Message, String> {

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    @GET
    @Path("/listPage")
    DataGrid listPage(CmsMessageListDto req);

    /**
     * 重发某个消息队列中的全部已死亡的消息
     *
     * @param consumerQueue 消费队列
     * @return 重发的消息数量
     */
    int resendAllDeadMessageByQueueName(String consumerQueue);
}
