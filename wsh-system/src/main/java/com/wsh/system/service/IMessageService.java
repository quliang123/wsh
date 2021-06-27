package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.system.domain.Message;

import java.util.List;

/**
 * 消息Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IMessageService extends IService<Message> {

    /**
     * 查询列表
     */
    List<Message> queryList(Message message);
}
