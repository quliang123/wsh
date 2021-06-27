package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.system.domain.Apply;

import java.util.List;

/**
 * 报名Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IApplyService extends IService<Apply> {

    /**
     * 查询列表
     */
    List<Apply> queryList(Apply apply);

    AjaxResult getByactivityId(Long activityId);

    AjaxResult edit(Apply apply);

    AjaxResult cancel(Apply apply);
}
