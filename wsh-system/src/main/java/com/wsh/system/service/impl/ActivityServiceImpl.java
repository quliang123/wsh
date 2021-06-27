package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.system.domain.Activity;
import com.wsh.system.domain.ActivityLike;
import com.wsh.system.domain.Apply;
import com.wsh.system.domain.vo.ActivityVo;
import com.wsh.system.mapper.ActivityMapper;
import com.wsh.system.mapper.ApplyMapper;
import com.wsh.system.service.IActivityLikeService;
import com.wsh.system.service.IActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private IActivityLikeService iActivityLikeService;

    @Override
    public List<Activity> queryList(Activity activity) {
        LambdaQueryWrapper<Activity> lqw = Wrappers.lambdaQuery();
        if (activity.getPublisherId() != null){
            lqw.eq(Activity::getPublisherId ,activity.getPublisherId());
        }
        if (StringUtils.isNotBlank(activity.getPublisherName())){
            lqw.like(Activity::getPublisherName ,activity.getPublisherName());
        }
        if (activity.getOrganizationId() != null){
            lqw.eq(Activity::getOrganizationId ,activity.getOrganizationId());
        }
        if (StringUtils.isNotBlank(activity.getOrganizationName())){
            lqw.like(Activity::getOrganizationName ,activity.getOrganizationName());
        }
        if (StringUtils.isNotBlank(activity.getOrganizationPhone())){
            lqw.eq(Activity::getOrganizationPhone ,activity.getOrganizationPhone());
        }
        if (activity.getActivityDate() != null){
            lqw.eq(Activity::getActivityDate ,activity.getActivityDate());
        }
        if (StringUtils.isNotBlank(activity.getActivityPlace())){
            lqw.eq(Activity::getActivityPlace ,activity.getActivityPlace());
        }
        if (StringUtils.isNotBlank(activity.getHeadline())){
            lqw.eq(Activity::getHeadline ,activity.getHeadline());
        }
        if (activity.getDeadline() != null){
            lqw.eq(Activity::getDeadline ,activity.getDeadline());
        }
        if (activity.getLikeCount() != null){
            lqw.eq(Activity::getLikeCount ,activity.getLikeCount());
        }
        if (activity.getDiscussCount() != null){
            lqw.eq(Activity::getDiscussCount ,activity.getDiscussCount());
        }
        if (StringUtils.isNotBlank(activity.getIntroduce())){
            lqw.eq(Activity::getIntroduce ,activity.getIntroduce());
        }
        if (StringUtils.isNotBlank(activity.getHot())){
            lqw.eq(Activity::getHot ,activity.getHot());
        }
        lqw.orderByDesc(Activity::getHot);
        return this.list(lqw);
    }

    /**
     * 点赞
     * @param activityId
     * @return
     */
    @Override
    public AjaxResult like(Long activityId) {
        Activity activity = getById(activityId);
        Long count = activity.getLikeCount();
        if (count==null){
            activity.setLikeCount(1L);
        }else {
            activity.setLikeCount(++count);
        }
        boolean b = updateById(activity);
        if (b){
            return AjaxResult.success();
        }
        return AjaxResult.success();
    }

    /**
     * 获取活动详细信息
     * @param activityId
     * @return
     */
    @Override
    public AjaxResult getInfo(Long activityId, Long useId) {
        Activity activity = this.getById(activityId);
        Apply apply = applyMapper.selectOne(new QueryWrapper<Apply>().eq("user_id", useId).eq("activity_id",activityId));
        ActivityVo activityVo = new ActivityVo();
        BeanUtils.copyProperties(activity,activityVo);
        if (apply==null || apply.getStatus().equals("0")){
            activityVo.setStatus("0");
        }else {
            activityVo.setStatus("1");
        }
        ActivityLike one = iActivityLikeService.getOne(new QueryWrapper<ActivityLike>().eq("user_id", useId).eq("activity_id", activityId));
        if(null!=one){
            activityVo.setLiked(Boolean.TRUE);
        }
        return AjaxResult.success(activityVo);
    }

    /**
     * 我的订单
     * @param userId
     * @return
     */
    @Override
    public AjaxResult myOrder(Long userId, String flag) {


        if (flag.equals("0")){
            List<Apply> applies = applyMapper.selectList(new QueryWrapper<Apply>().eq("user_id", userId));
            ArrayList<Long> list = new ArrayList<>();
            for (Apply apply : applies) {
                list.add(apply.getActivityId());
            }
            if(list.size()>0){
                List<Activity> activities = this.list(new QueryWrapper<Activity>().in("activity_id", list));
                return AjaxResult.success(activities);
            }
            return AjaxResult.success();
        }

        if (flag.equals("1")){
            List<Apply> applies = applyMapper.selectList(new QueryWrapper<Apply>().eq("user_id", userId).eq("status","0"));
            ArrayList<Long> list = new ArrayList<>();
            for (Apply apply : applies) {
                list.add(apply.getActivityId());
            }
            if(list.size()>0){
                List<Activity> activities = this.list(new QueryWrapper<Activity>().in("activity_id", list));
                return AjaxResult.success(activities);
            }
            return AjaxResult.success();
        }
        if (flag.equals("2")){
            List<Apply> applies = applyMapper.selectList(new QueryWrapper<Apply>().eq("user_id", userId).eq("status","1"));
            ArrayList<Long> list = new ArrayList<>();
            for (Apply apply : applies) {
                list.add(apply.getActivityId());
            }
            if(list.size()>0){
                List<Activity> activities = this.list(new QueryWrapper<Activity>().in("activity_id", list));
                return AjaxResult.success(activities);
            }
            return AjaxResult.success();
        }
        if (flag.equals("3")){
            List<Apply> applies = applyMapper.selectList(new QueryWrapper<Apply>().eq("user_id", userId));
            ArrayList<Long> list = new ArrayList<>();
            for (Apply apply : applies) {
                list.add(apply.getActivityId());
            }

            QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();

            Date d = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(d);
            queryWrapper.apply("date_format(deadline,'%Y-%m-%d') <= date_format('"+date+"','%Y-%m-%d')");
            queryWrapper.in("activity_id", list);

            List<Activity> activities = this.list(queryWrapper);
            return AjaxResult.success(activities);

        }

        return AjaxResult.error();
    }
}
