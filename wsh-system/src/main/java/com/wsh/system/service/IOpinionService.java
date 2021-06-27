package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.system.domain.Opinion;

import java.util.List;

/**
 * 意见Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IOpinionService extends IService<Opinion> {

    /**
     * 查询列表
     */
    List<Opinion> queryList(Opinion opinion);
}
