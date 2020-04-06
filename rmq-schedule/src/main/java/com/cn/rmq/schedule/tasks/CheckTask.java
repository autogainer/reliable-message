package com.cn.rmq.schedule.tasks;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.rmq.api.schedule.service.ICheckMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>Description:
 * 消息确认子系统定时任务
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Component
@Slf4j
public class CheckTask {

    @Reference
    private ICheckMessageService checkMessageService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void task() {
        log.info("【CheckTask】start");

        checkMessageService.checkWaitingMessage();

        log.info("【CheckTask】end");
    }
}
