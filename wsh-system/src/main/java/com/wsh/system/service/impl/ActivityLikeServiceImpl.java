package com.wsh.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.wsh.system.mapper.ActivityLikeMapper;
import com.wsh.system.domain.ActivityLike;
import com.wsh.system.service.IActivityLikeService;

import java.util.List;
import java.util.Map;

/**
 * 活动点赞记录Service业务层处理
 *
 * @author wsh
 * @date 2021-04-13
 */
@Service
public class ActivityLikeServiceImpl extends ServiceImpl<ActivityLikeMapper, ActivityLike> implements IActivityLikeService {

    @Override
    public List<ActivityLike> queryList(ActivityLike activityLike) {
        LambdaQueryWrapper<ActivityLike> lqw = Wrappers.lambdaQuery();
        if (activityLike.getActivityId() != null){
            lqw.eq(ActivityLike::getActivityId ,activityLike.getActivityId());
        }
        if (activityLike.getUserId() != null){
            lqw.eq(ActivityLike::getUserId ,activityLike.getUserId());
        }
        return this.list(lqw);
    }
}
