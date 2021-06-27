package com.wsh.system.service;

import com.wsh.system.domain.TAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 认证Service接口
 *
 * @author wsh
 * @date 2021-04-14
 */
public interface ITAuthService extends IService<TAuth> {

    /**
     * 查询列表
     */
    List<TAuth> queryList(TAuth tAuth);
}
