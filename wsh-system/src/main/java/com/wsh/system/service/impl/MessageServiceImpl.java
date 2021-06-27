package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.system.domain.Message;
import com.wsh.system.mapper.MessageMapper;
import com.wsh.system.service.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public List<Message> queryList(Message message) {
        LambdaQueryWrapper<Message> lqw = Wrappers.lambdaQuery();
        if (message.getUserId() != null){
            lqw.eq(Message::getUserId ,message.getUserId());
        }
        if (StringUtils.isNotBlank(message.getParticulars())){
            lqw.eq(Message::getParticulars ,message.getParticulars());
        }
        if (StringUtils.isNotBlank(message.getStatus())){
            lqw.eq(Message::getStatus ,message.getStatus());
        }
        return this.list(lqw);
    }
}
