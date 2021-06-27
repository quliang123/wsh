package com.wsh.web.controller.system;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.enums.BusinessType;
import com.wsh.system.domain.Activity;
import com.wsh.system.service.IActivityService;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.common.core.page.TableDataInfo;

/**
 * 活动Controller
 * 
 * @author wsh
 * @date 2021-04-14
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/activity" )
public class SysActivityController extends BaseController {

    private final IActivityService iActivityService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:activity:list')")
    @GetMapping("/list")
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
    @PreAuthorize("@ss.hasPermi('system:activity:query')" )
    @GetMapping(value = "/{activityId}" )
    public AjaxResult getInfo(@PathVariable("activityId" ) Long activityId) {
        return AjaxResult.success(iActivityService.getById(activityId));
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
    public AjaxResult edit(@RequestBody Activity activity) {
        return toAjax(iActivityService.updateById(activity) ? 1 : 0);
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:remove')" )
    @Log(title = "活动" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}" )
    public AjaxResult remove(@PathVariable Long[] activityIds) {
        return toAjax(iActivityService.removeByIds(Arrays.asList(activityIds)) ? 1 : 0);
    }
}
