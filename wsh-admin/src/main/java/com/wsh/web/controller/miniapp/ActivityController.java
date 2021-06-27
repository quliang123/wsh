package com.wsh.web.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Activity;
import com.wsh.system.domain.ActivityLike;
import com.wsh.system.domain.Apply;
import com.wsh.system.service.IActivityLikeService;
import com.wsh.system.service.IActivityService;
import com.wsh.system.service.IApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 活动Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Api(tags = "活动模块")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/activity" )
public class ActivityController extends BaseController {

    private final IActivityService iActivityService;

    private final IApplyService iApplyService;

    private final IActivityLikeService iActivityLikeService;
    /**
     * 查询活动列表
     */

    @GetMapping("/list")
    @ApiOperation("查询活动列表")
    public TableDataInfo list(Activity activity) {
        startPage();
        List<Activity> list = iActivityService.queryList(activity);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:activity:export')" )
    @Log(title = "活动" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Activity activity) {
        List<Activity> list = iActivityService.queryList(activity);
        ExcelUtil<Activity> util = new ExcelUtil<Activity>(Activity.class);
        return util.exportExcel(list, "activity" );
    }

    /**
     * 获取活动详细信息
     */
    @GetMapping(value = "/getInfo/{activityId}")
    @ApiOperation("获取活动详细信息")
    public AjaxResult getInfo(@PathVariable("activityId" ) Long activityId) {
        return iActivityService.getInfo(activityId,getUserId());
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:add')" )
    @Log(title = "活动" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Activity activity) {
        return toAjax(iActivityService.save(activity) ? 1 : 0);
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:edit')" )
    @Log(title = "活动" , businessType = BusinessType.UPDATE)
    @PutMapping
//    @ApiOperation("修改活动")
    public AjaxResult edit(@RequestBody Activity activity) {
        return toAjax(iActivityService.updateById(activity) ? 1 : 0);
    }

    /**
     * 删除活动
     */
    @Log(title = "活动" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}" )
    public AjaxResult remove(@PathVariable Long[] activityIds) {
        return toAjax(iActivityService.removeByIds(Arrays.asList(activityIds)) ? 1 : 0);
    }


    @GetMapping(value = "/like/{activityId}")
    @Log(title = "活动点赞" , businessType = BusinessType.UPDATE)
    @ApiOperation("点赞/取消点赞")
    public AjaxResult like(@PathVariable("activityId" ) Long activityId){
        Activity activity = iActivityService.getById(activityId);
        if(activity==null){
            return AjaxResult.error("活动不存在或已删除");
        }
        ActivityLike one = iActivityLikeService.getOne(new QueryWrapper<ActivityLike>().eq("activity_id", activityId).eq("user_id", getUserId()));
        if(one==null){
            //点赞
            ActivityLike activityLike = new ActivityLike();
            activityLike.setActivityId(activityId);
            activityLike.setUserId(getUserId());
            boolean save = iActivityLikeService.save(activityLike);
            if(!save){
                return AjaxResult.error("点赞失败，请重新尝试。");
            }
            return AjaxResult.success("点赞成功");
        }else{
            //取消点赞
            boolean b = iActivityLikeService.removeById(one.getPlId());
            if(!b){
                return AjaxResult.error("取消点赞失败，请重新尝试。");
            }
            return AjaxResult.success("取消点赞成功");
        }
    }

    /**
     * 获取活动点赞数
     * @param
     * @return
     */
    @ApiOperation("获取活动点赞数")
    @GetMapping("likeCount/{activityId}")
    public AjaxResult getLikeCount(@PathVariable("activityId" ) Long activityId){
     return AjaxResult.success(iActivityLikeService.count(new QueryWrapper<ActivityLike>().eq("activity_id",activityId)));
    }

    @GetMapping("/myOrder/{flag}")
    @ApiOperation("我的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag",value = "标志(0全部 1已报名 2取消 3已结束)",required = true)
    })
    public AjaxResult myOrder(@PathVariable("flag") String flag){
        startPage();
        return iActivityService.myOrder(getUserId(),flag);
    }

    @ApiOperation("获取我的订单总数")
    @GetMapping("/getOrderCount")
    public AjaxResult getOrderCount(){
        return AjaxResult.success(iApplyService.count(new QueryWrapper<Apply>().eq("user_id", getUserId())));
    }
}
