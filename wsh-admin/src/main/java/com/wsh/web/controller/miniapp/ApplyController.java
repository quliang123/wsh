package com.wsh.web.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.DateUtils;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Activity;
import com.wsh.system.domain.Apply;
import com.wsh.system.domain.Message;
import com.wsh.system.domain.TAuth;
import com.wsh.system.service.IActivityService;
import com.wsh.system.service.IApplyService;
import com.wsh.system.service.IMessageService;
import com.wsh.system.service.ITAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 报名Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Api(tags = "报名模块")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/apply" )
public class ApplyController extends BaseController {

    private final IApplyService iApplyService;

    private final IActivityService iActivityService;

    private final IMessageService iMessageService;

    private final ITAuthService itAuthService;

    /**
     * 查询报名列表
     */
    @ApiOperation("查询报名列表")
    @GetMapping("/list")
    public TableDataInfo list(Apply apply) {
        startPage();
        apply.setUserId(getUserId());
        List<Apply> list = iApplyService.queryList(apply);
        return getDataTable(list);
    }

    /**
     * 导出报名列表
     */
    @Log(title = "报名" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Apply apply) {
        List<Apply> list = iApplyService.queryList(apply);
        ExcelUtil<Apply> util = new ExcelUtil<Apply>(Apply.class);
        return util.exportExcel(list, "apply" );
    }

    /**
     * 获取报名详细信息
     */
    @GetMapping(value = "/{applyId}" )
    public AjaxResult getInfo(@PathVariable("applyId" ) Long applyId) {
        return AjaxResult.success(iApplyService.getById(applyId));
    }

    @ApiOperation("活动报名")
    @GetMapping("activityApply/{activityId}")
    public AjaxResult activityApply(@PathVariable("activityId") Long activityId){
        Activity activity = iActivityService.getById(activityId);
        if(null==activity){
            return AjaxResult.error("活动不存在或已删除");
        }
        if(activity.getDeadline().getTime()< DateUtils.getNowDate().getTime()){
            return AjaxResult.error("活动已截止");
        }
        TAuth authServiceOne = itAuthService.getOne(new QueryWrapper<TAuth>().eq("user_id", getUserId()));
        if(null==authServiceOne||authServiceOne.getStatus().equals("0")||authServiceOne.getStatus().equals("2")){
            return AjaxResult.error("账号认证状态不允许此操作");
        }
        Apply apply = iApplyService.getOne(new QueryWrapper<Apply>().eq("user_id", getUserId()).eq("activity_id", activityId));
        if(null==apply){
            //新增报名
            apply = new Apply();
            apply.setUserId(getUserId());
            apply.setStatus("1");
            apply.setActivityId(activityId);
            apply.setCreateTime(DateUtils.getNowDate());
            boolean save = iApplyService.save(apply);
            if(!save){
                return AjaxResult.error("报名失败");
            }
        }else{
            apply.setStatus(apply.getStatus().equals("0")?"1":"0");
            iApplyService.updateById(apply);
        }
        Message message = new Message();
        message.setUserId(apply.getUserId());
        message.setStatus(apply.getStatus());
        message.setParticulars(apply.getStatus().equals("1")?"您已成功报名"+activity.getHeadline()+",请准时参加。":"您已取消"+activity.getHeadline()+"报名");
        iMessageService.save(message);
        return AjaxResult.success(message.getParticulars());

    }

//    /**
//     * 新增报名
//     */
//    @PostMapping
//    @ApiOperation("新增报名")
//    public AjaxResult add(@RequestBody Apply apply) {
//        apply.setUserId(getUserId());
//        return iApplyService.edit(apply);
//    }

//    /**
//     * 报名
//     */
//    @PutMapping
//    @ApiOperation("报名")
//    public AjaxResult edit(@RequestBody Apply apply) {
//        apply.setUserId(getUserId());
//        return iApplyService.edit(apply);
//    }

    @PostMapping("/cancel")
    @Log(title = "取消报名" , businessType = BusinessType.UPDATE)
    @ApiOperation("取消报名")
    public AjaxResult cancel(@RequestBody Apply apply){
        return iApplyService.cancel(apply);
    }

    /**
     * 删除报名
     */
    @Log(title = "报名" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}" )
//    @ApiOperation("删除报名")
    public AjaxResult remove(@PathVariable Long[] applyIds) {
        return toAjax(iApplyService.removeByIds(Arrays.asList(applyIds)) ? 1 : 0);
    }


    /**
     * 根据活动id获取报名人员
     * @param activityId
     * @return
     */
    @GetMapping(value = "/getByactivityId/{activityId}" )
    @ApiOperation("根据活动id获取报名人员")
    public AjaxResult getByactivityId(@PathVariable Long activityId){
        startPage();
        return iApplyService.getByactivityId(activityId);
    }



    @GetMapping(value = "/count")
    @ApiOperation("我的订单数量")
    public AjaxResult count(){
        int count = iApplyService.count(new QueryWrapper<Apply>().eq("user_id", getUserId()));
        return AjaxResult.success(count);
    }

}
