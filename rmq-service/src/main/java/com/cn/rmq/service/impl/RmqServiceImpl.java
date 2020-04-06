package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.RmqMessage;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.dal.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class RmqServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements IRmqService {

    @Reference
    private IMessageService messageService;

    @Override
    public RmqMessage createPreMessage(RmqMessage rmqMessage) {
        if (StringUtils.isBlank(rmqMessage.getConsumerQueue())) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(rmqMessage.getMessageBody())) {
            throw new CheckException("messageBody is empty");
        }

        String id = IdUtil.simpleUUID();

        // 插入预发送消息记录
        Message message = new Message();
        message.setId(id);
        message.setConsumerQueue(rmqMessage.getConsumerQueue());
        message.setMessageBody(rmqMessage.getMessageBody());
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        mapper.insertSelective(message);

        rmqMessage.setConsumerQueue(null);
        rmqMessage.setMessageBody(null);
        rmqMessage.setMessageId(id);
        return rmqMessage;
    }

    @Override
    public void confirmAndSendMessage(RmqMessage rmqMessage) {
        String messageId = rmqMessage.getMessageId();
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }

        // 获取消息
        Message message = mapper.selectByPrimaryKey(messageId);
        if (message == null) {
            throw new CheckException("message not exist");
        }

        // 更新消息状态为发送中
        Message updateBean = new Message();
        updateBean.setId(messageId);
        updateBean.setStatus(MessageStatusEnum.SENDING.getValue());
        updateBean.setConfirmTime(LocalDateTime.now());
        updateBean.setUpdateTime(LocalDateTime.now());
        mapper.updateByPrimaryKeySelective(updateBean);


        rmqMessage.setMessageBody(message.getMessageBody());
        // 发送http消息
        postMessage(rmqMessage);
    }

    @Override
    public void saveAndSendMessage(RmqMessage rmqMessage) {
        if (StringUtils.isBlank(rmqMessage.getConsumerQueue())) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(rmqMessage.getMessageBody())) {
            throw new CheckException("messageBody is empty");
        }

        String id = IdUtil.simpleUUID();

        // 插入发送消息记录
        Message message = new Message();
        message.setId(id);
        message.setConsumerQueue(rmqMessage.getConsumerQueue());
        message.setMessageBody(rmqMessage.getMessageBody());
        message.setStatus(MessageStatusEnum.SENDING.getValue());
        message.setCreateTime(LocalDateTime.now());
        message.setConfirmTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        mapper.insertSelective(message);

        // 发送http消息
        postMessage(rmqMessage);
    }

    @Override
    public void directSendMessage(RmqMessage rmqMessage) {
        String messageBody = rmqMessage.getMessageBody();
        String consumerQueue =rmqMessage.getConsumerQueue();

        if (StringUtils.isBlank(consumerQueue)) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(messageBody)) {
            throw new CheckException("messageBody is empty");
        }

        // 发送http消息
        postMessage(rmqMessage);
    }

    @Override
    public void deleteMessageById(RmqMessage rmqMessage) {
        String messageId = rmqMessage.getMessageId();
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }
        mapper.deleteByPrimaryKey(messageId);
    }



    @Override
    public void postMessage(RmqMessage rmqMessage) {
        try {
            String messageBody = rmqMessage.getMessageBody();
            Short postTimeout = rmqMessage.getPostTimeout();
            log.info("投递消息=[{}]", JSONUtil.toJsonStr(messageBody));
            // 调用下层业务方http接口投递消息
            String postRsp = HttpUtil.post(rmqMessage.getPostUrl(), messageBody, postTimeout);
            log.info("投递消息id=[{}], 返回应答=[{}]", rmqMessage.getMessageId(), postRsp);

            // 解析业务方返回结果
            JSONObject jsonObject = JSONUtil.parseObj(postRsp);
            Integer code = jsonObject.getInt(Constants.KEY_CODE);
            if (code.equals(Constants.CODE_SUCCESS)) {
                // code=CODE_SUCCESS，业务方处理正常
                Integer data = jsonObject.getInt(Constants.KEY_DATA);
                if (data == 1) {
                    // data=1，该消息业务处理成功
                    log.info("下层处理消息[{}]成功",rmqMessage.getMessageId());

                    deleteMessageById(rmqMessage);
                } else {
                    // data!=1，该消息业务处理失败，标记为死亡，不继续发送
                    log.info("下层处理消息[{}]失败，标记为死亡",rmqMessage.getMessageId());

                    //不考虑发送次数，直接标记为已死亡
                    int updateCount = messageService.updateMessageDeadById(rmqMessage.getMessageId());
                    log.info("更新[{}]条记录", updateCount);

                }
            } else {
                // 业务方处理异常，记录日志---->系统异常
                String msg = jsonObject.getStr(Constants.KEY_MSG);
                log.error("消息投递失败, messageId=[{}], code=[{}], msg=[{}]", rmqMessage.getMessageId(), code, msg);
            }
        } catch (HttpException e) {
            log.error("消息投递失败, messageId=[" + rmqMessage.getMessageId() + "], error:", e);
        } catch (Exception e) {
            log.error("消息投递失败, messageId=[" + rmqMessage.getMessageId() + "], error:", e);
        }
    }
}
