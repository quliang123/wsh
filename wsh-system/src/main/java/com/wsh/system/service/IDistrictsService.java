package com.wsh.system.service;

import com.wsh.system.domain.Districts;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 地区Service接口
 *
 * @author wsh
 * @date 2021-04-13
 */
public interface IDistrictsService extends IService<Districts> {

    /**
     * 查询列表
     */
    List<Districts> queryList(Districts districts);
}
