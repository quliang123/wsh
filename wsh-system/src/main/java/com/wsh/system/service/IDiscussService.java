package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.system.domain.Discuss;

import java.util.List;

/**
 * 评论Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IDiscussService extends IService<Discuss> {

    /**
     * 查询列表
     */
    List<Discuss> queryList(Discuss discuss);

    AjaxResult getByactivityId(Long activityId);

    AjaxResult add(Discuss discuss);
}
