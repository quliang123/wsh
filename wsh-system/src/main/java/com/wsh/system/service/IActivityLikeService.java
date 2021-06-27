package com.wsh.system.service;

import com.wsh.system.domain.ActivityLike;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 活动点赞记录Service接口
 *
 * @author wsh
 * @date 2021-04-13
 */
public interface IActivityLikeService extends IService<ActivityLike> {

    /**
     * 查询列表
     */
    List<ActivityLike> queryList(ActivityLike activityLike);
}
