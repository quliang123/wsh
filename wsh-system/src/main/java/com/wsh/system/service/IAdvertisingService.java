package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.system.domain.Advertising;

import java.util.List;

/**
 * 广告Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IAdvertisingService extends IService<Advertising> {

    /**
     * 查询列表
     */
    List<Advertising> queryList(Advertising advertising);
}
