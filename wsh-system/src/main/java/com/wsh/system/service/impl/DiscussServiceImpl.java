package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.utils.DateUtils;
import com.wsh.system.domain.Activity;
import com.wsh.system.domain.Discuss;
import com.wsh.system.mapper.ActivityMapper;
import com.wsh.system.mapper.DiscussMapper;
import com.wsh.system.service.IDiscussService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss> implements IDiscussService {

    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public List<Discuss> queryList(Discuss discuss) {
        LambdaQueryWrapper<Discuss> lqw = Wrappers.lambdaQuery();
        if (discuss.getActivityId() != null){
            lqw.eq(Discuss::getActivityId ,discuss.getActivityId());
        }
        if (StringUtils.isNotBlank(discuss.getContent())){
            lqw.eq(Discuss::getContent ,discuss.getContent());
        }
        if (discuss.getUserId() != null){
            lqw.eq(Discuss::getUserId ,discuss.getUserId());
        }

        return this.list(lqw);
    }

    /**
     * 根据活动id获取评论
     * @param activityId
     * @return
     */
    @Override
    public AjaxResult getByactivityId(Long activityId) {

        List<Discuss> discusses = this.list(new QueryWrapper<Discuss>().eq("activity_id", activityId));
        return AjaxResult.success(discusses);
    }

    /**
     * 新增评论
     * @param discuss
     * @return
     */
    @Override
    @Transactional
    public AjaxResult add(Discuss discuss) {
        discuss.setCreateTime(DateUtils.getNowDate());
        boolean b = this.save(discuss);
        if (b){
            Activity activity = activityMapper.selectById(discuss.getActivityId());
            if(null==activity){
                return AjaxResult.error("活动不存在或已删除");
            }
            Long count = activity.getDiscussCount();
            activity.setDiscussCount(++count);
            activityMapper.updateById(activity);
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
