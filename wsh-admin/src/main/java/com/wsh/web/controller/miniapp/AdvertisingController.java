package com.wsh.web.controller.miniapp;

import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Advertising;
import com.wsh.system.service.IAdvertisingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 广告Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/advertising" )
public class AdvertisingController extends BaseController {

    private final IAdvertisingService iAdvertisingService;

    /**
     * 查询广告列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Advertising advertising) {
        startPage();
        List<Advertising> list = iAdvertisingService.queryList(advertising);
        return getDataTable(list);
    }

    /**
     * 导出广告列表
     */
    @Log(title = "广告" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Advertising advertising) {
        List<Advertising> list = iAdvertisingService.queryList(advertising);
        ExcelUtil<Advertising> util = new ExcelUtil<Advertising>(Advertising.class);
        return util.exportExcel(list, "advertising" );
    }

    /**
     * 获取广告详细信息
     */
    @GetMapping(value = "/{advertisingId}" )
    public AjaxResult getInfo(@PathVariable("advertisingId" ) Long advertisingId) {
        return AjaxResult.success(iAdvertisingService.getById(advertisingId));
    }

    /**
     * 新增广告
     */
    @PreAuthorize("@ss.hasPermi('system:advertising:add')" )
    @Log(title = "广告" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Advertising advertising) {
        return toAjax(iAdvertisingService.save(advertising) ? 1 : 0);
    }

    /**
     * 修改广告
     */
    @PreAuthorize("@ss.hasPermi('system:advertising:edit')" )
    @Log(title = "广告" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Advertising advertising) {
        return toAjax(iAdvertisingService.updateById(advertising) ? 1 : 0);
    }

    /**
     * 删除广告
     */
    @PreAuthorize("@ss.hasPermi('system:advertising:remove')" )
    @Log(title = "广告" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{advertisingIds}" )
    public AjaxResult remove(@PathVariable Long[] advertisingIds) {
        return toAjax(iAdvertisingService.removeByIds(Arrays.asList(advertisingIds)) ? 1 : 0);
    }
}
