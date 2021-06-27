package com.wsh.web.controller.miniapp;

import java.util.List;
import java.util.Arrays;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.wsh.system.domain.Districts;
import com.wsh.system.service.IDistrictsService;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.common.core.page.TableDataInfo;

/**
 * 地区Controller
 * 
 * @author wsh
 * @date 2021-04-13
 */
@Api(tags = "地区")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/districts" )
public class DistrictsController extends BaseController {

    private final IDistrictsService iDistrictsService;

    /**
     * 查询地区列表
     */
    @ApiOperation("获取地区")
    @GetMapping("/list")
    public TableDataInfo list(Districts districts) {
        startPage();
        List<Districts> list = iDistrictsService.queryList(districts);
        return getDataTable(list);
    }

    /**
     * 导出地区列表
     */
    @Log(title = "地区" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Districts districts) {
        List<Districts> list = iDistrictsService.queryList(districts);
        ExcelUtil<Districts> util = new ExcelUtil<Districts>(Districts.class);
        return util.exportExcel(list, "districts" );
    }

    /**
     * 获取地区详细信息
     */
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Integer id) {
        return AjaxResult.success(iDistrictsService.getById(id));
    }

    /**
     * 新增地区
     */
    @Log(title = "地区" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Districts districts) {
        return toAjax(iDistrictsService.save(districts) ? 1 : 0);
    }

    /**
     * 修改地区
     */
    @Log(title = "地区" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Districts districts) {
        return toAjax(iDistrictsService.updateById(districts) ? 1 : 0);
    }

    /**
     * 删除地区
     */
    @Log(title = "地区" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(iDistrictsService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
