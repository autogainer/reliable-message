package com.cn.rmq.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.RmqMessage;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.dal.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements IMessageService {

    @Reference
    IRmqService iRmqService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resendMessage(Message message) {
        log.info("【重新投递】start, messageId={}", message.getId());
        // 增加重发次数
        mapper.addResendTimes(message.getId());

        // 发送MQ消息
        RmqMessage rmqMessage = new RmqMessage();
        rmqMessage.setMessageId(message.getId());
        rmqMessage.setMessageBody(message.getMessageBody());
        // 发送http消息
        iRmqService.postMessage(rmqMessage);

        log.info("【重新投递】success, messageId={}", message.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resendMessageById(String messageId) {
        log.info("【resendMessageById】start, messageId={}", messageId);
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }

        // 校验消息是否存在
        Message message = mapper.selectByPrimaryKey(messageId);
        if (message == null) {
            throw new CheckException("message not exist");
        }

        // 增加重发次数
        mapper.addResendTimes(messageId);

        // 发送MQ消息
        RmqMessage rmqMessage = new RmqMessage();
        rmqMessage.setMessageId(message.getId());
        rmqMessage.setMessageBody(message.getMessageBody());
        // 发送http消息
        iRmqService.postMessage(rmqMessage);

        log.info("【resendMessageById】success, messageId={}", messageId);
    }

    @Override
    public int updateMessageDead(Short resendTimes) {
        return mapper.updateMessageDead(resendTimes);
    }

    @Override
    public int updateMessageDeadById(String id){
        return mapper.updateMessageDeadById(id);
    }
}
