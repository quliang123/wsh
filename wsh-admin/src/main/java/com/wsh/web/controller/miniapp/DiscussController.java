package com.wsh.web.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.User;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Discuss;
import com.wsh.system.service.IDiscussService;
import com.wsh.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 评论Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Api(tags = "评论模块")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/discuss" )
public class DiscussController extends BaseController {

    private final IDiscussService iDiscussService;

    private final IUserService iUserService;

    /**
     * 查询评论列表
     */
    @ApiOperation("获取评论列表")
    @GetMapping("/list")
    public TableDataInfo list(Discuss discuss) {
        startPage();
        List<Discuss> list = iDiscussService.queryList(discuss);
         List<Long> userIds = new ArrayList<>();
        for (Discuss discuss1 : list) {
            userIds.add(discuss1.getUserId());
        }
        if(userIds.size()>0){
            List<User> userList = iUserService.list(new QueryWrapper<User>().in("user_id", userIds));
            for (User user : userList) {
                for (Discuss discuss1 : list) {
                    if(user.getUserId().compareTo(discuss1.getUserId())==0){
                        discuss1.setUser(user);
                    }
                }
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @Log(title = "评论" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Discuss discuss) {
        List<Discuss> list = iDiscussService.queryList(discuss);
        ExcelUtil<Discuss> util = new ExcelUtil<Discuss>(Discuss.class);
        return util.exportExcel(list, "discuss" );
    }

    /**
     * 获取评论详细信息
     */
    @GetMapping(value = "/{discussId}" )
    @ApiOperation("获取评论详细信息")
    public AjaxResult getInfo(@PathVariable("discussId" ) Long discussId) {
        return AjaxResult.success(iDiscussService.getById(discussId));
    }

    /**
     * 新增评论
     */
    @Log(title = "评论" , businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增评论")
    public AjaxResult add(@RequestBody Discuss discuss) {
        discuss.setUserId(getUserId());
        return iDiscussService.add(discuss);
    }

    /**
     * 修改评论
     */
    @Log(title = "评论" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Discuss discuss) {
        return toAjax(iDiscussService.updateById(discuss) ? 1 : 0);
    }

    /**
     * 删除评论
     */
    @ApiOperation("删除评论")
    @Log(title = "评论" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{discussId}" )
    public AjaxResult remove(@PathVariable("discussId")Long discussId) {
        Discuss discuss = iDiscussService.getById(discussId);
        if(null==discuss){
            return AjaxResult.error("评论不存在或已删除");
        }
        if(discuss.getUserId().compareTo(getUserId())!=0){
            return AjaxResult.error("没有权限删除");
        }
        boolean b = iDiscussService.removeById(discussId);
        if(!b){
            return AjaxResult.error("评论失败，请重新尝试");
        }
        return AjaxResult.success();
    }

    /**
     * 删除我的评论
     * @param commonId
     * @return
     */
    @DeleteMapping("/deleteMyCommon/{commonId}")
    public AjaxResult deleteMyCommon(@PathVariable("commonId") Long commonId){
        Discuss discuss = iDiscussService.getById(commonId);
        if(null==discuss){
            return AjaxResult.error("删除失败，数据不存在或已删除");
        }
        if(discuss.getUserId().compareTo(getUserId())!=0){
            return AjaxResult.error("无权限操作此条数据");
        }
        boolean b = iDiscussService.removeById(commonId);
        if(!b){
            return AjaxResult.error("删除失败，请重新尝试");
        }
        return AjaxResult.success();
    }


    /**
     * 根据活动id获取评论
     * @param activityId
     * @return
     */
    @ApiOperation("根据活动id获取评论")
    public AjaxResult getByactivityId(@PathVariable Long activityId){
        startPage();
        return iDiscussService.getByactivityId(activityId);
    }

}
