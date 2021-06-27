package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.User;
import com.wsh.system.domain.Activity;
import com.wsh.system.domain.Apply;
import com.wsh.system.domain.Message;
import com.wsh.system.mapper.ActivityMapper;
import com.wsh.system.mapper.ApplyMapper;
import com.wsh.system.mapper.MessageMapper;
import com.wsh.system.service.IActivityService;
import com.wsh.system.service.IApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 报名Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private IActivityService iActivityService;

    @Override
    public List<Apply> queryList(Apply apply) {
        LambdaQueryWrapper<Apply> lqw = Wrappers.lambdaQuery();
        if (apply.getActivityId() != null){
            lqw.eq(Apply::getActivityId ,apply.getActivityId());
        }
        if (apply.getUserId() != null){
            lqw.eq(Apply::getUserId ,apply.getUserId());
        }
        if (StringUtils.isNotBlank(apply.getStatus())){
            lqw.eq(Apply::getStatus ,apply.getStatus());
        }
        List<Apply> list = this.list(lqw);
        List<Long> activityIds = new ArrayList<>();
        for (Apply apply1 : list) {
            activityIds.add(apply1.getActivityId());
        }
        if(activityIds.size()>0){
            List<Activity> activityList = iActivityService.list(new QueryWrapper<Activity>().in("activity_id", activityIds));
            for (Activity activity : activityList) {
                for (Apply apply1 : list) {
                    if(activity.getActivityId().compareTo(apply1.getActivityId())==0){
                        apply1.setActivity(activity);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据活动id获取报名人员
     * @param activityId
     * @return
     */
    @Override
    public AjaxResult getByactivityId(Long activityId) {
        QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id",activityId);
        queryWrapper.eq("status",1);
        List<Apply> applies = this.list(queryWrapper);
        if (applies.isEmpty()){
            return AjaxResult.success();
        }
        ArrayList<Long> users = new ArrayList<>();
        for (Apply apply : applies) {
            users.add(apply.getUserId());
        }
        List<User> userList = userService.list(new QueryWrapper<User>().in("user_id", users));
        for (Apply apply : applies) {
            for (User user : userList) {
                if(apply.getUserId().compareTo(user.getUserId())==0){
                    apply.setUser(user);
                }
            }
        }
        return AjaxResult.success(applies);
    }

    /**
     * 报名和取消报名
     * @param apply
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult edit(Apply apply) {
        if (apply.getUserId()==null || apply.getActivityId()==null){
            return AjaxResult.error();
        }
        Apply getOne = this.getOne(new QueryWrapper<Apply>().eq("user_id", apply.getUserId()).eq("activity_id",apply.getActivityId()).last("limit 1"));
//        如果传过来的是已取消，重新设置成报名
        if (getOne!=null && getOne.getStatus().equals("1")){
            apply.setStatus("0");
            boolean b = this.updateById(getOne);
            if (b){
                Message message = new Message();
                message.setUserId(apply.getUserId());
                message.setStatus("0");
                Activity activity = activityMapper.selectById(apply.getActivityId());
                message.setParticulars("您已成功取消报名"+activity.getHeadline()+",请准时参加。");
                messageMapper.insert(message);
                return AjaxResult.success();
            }
            return  AjaxResult.error();
        }
//        如果报名表没数据，那么新增报名数据
        if (getOne==null){
            Apply apply2 = new Apply();
            apply2.setActivityId(apply.getActivityId());
            apply2.setUserId(apply.getUserId());
            apply2.setStatus("1");
            boolean b = this.save(apply2);
            if (b){
                Message message = new Message();
                message.setUserId(apply.getUserId());
                message.setStatus("0");
                Activity activity = activityMapper.selectById(apply.getActivityId());
                message.setParticulars("您已成功报名"+activity.getHeadline()+",请准时参加。");
                messageMapper.insert(message);
                return AjaxResult.success();
            }
            return AjaxResult.error();
        }
        return AjaxResult.error();

    }

    /**
     * 取消报名
     * @param apply
     * @return
     */
    @Override
    public AjaxResult cancel(Apply apply) {
        if (apply.getUserId()==null || apply.getActivityId()==null){
            return AjaxResult.error();
        }
        Apply one = this.getOne(new QueryWrapper<Apply>().eq("user_id", apply.getUserId()).eq("activity_id",apply.getActivityId()).last("limit 1"));
        one.setStatus("1");
        boolean b = updateById(one);
        if (b){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
