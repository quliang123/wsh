package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.system.domain.Merchant;

import java.util.List;

/**
 * 商户Service接口
 *
 * @author wsh
 * @date 2021-03-09
 */
public interface IMerchantService extends IService<Merchant> {

    /**
     * 查询列表
     */
    List<Merchant> queryList(Merchant merchant);
}
