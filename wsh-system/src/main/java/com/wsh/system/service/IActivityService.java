package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.system.domain.Activity;

import java.util.List;

/**
 * 活动Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 查询列表
     */
    List<Activity> queryList(Activity activity);

    AjaxResult like(Long activityId);

    AjaxResult getInfo(Long activityId, Long useId);

    AjaxResult myOrder(Long userId, String flag);
}
