package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.service.IBaseService;

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
@Path("ICmsQueueService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface ICmsQueueService extends IBaseService<Queue, String> {

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    @GET
    @Path("/listPage")
    DataGrid listPage(CmsQueueListDto req);
}
