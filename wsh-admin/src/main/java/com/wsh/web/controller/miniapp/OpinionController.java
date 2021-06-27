package com.wsh.web.controller.miniapp;

import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Opinion;
import com.wsh.system.service.IOpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 意见Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/opinion" )
@Api(tags = "意见模块")
public class OpinionController extends BaseController {

    private final IOpinionService iOpinionService;

    /**
     * 查询意见列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Opinion opinion) {
        startPage();
        List<Opinion> list = iOpinionService.queryList(opinion);
        return getDataTable(list);
    }

    /**
     * 导出意见列表
     */
    @Log(title = "意见" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Opinion opinion) {
        List<Opinion> list = iOpinionService.queryList(opinion);
        ExcelUtil<Opinion> util = new ExcelUtil<Opinion>(Opinion.class);
        return util.exportExcel(list, "opinion" );
    }

    /**
     * 获取意见详细信息
     */
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iOpinionService.getById(id));
    }

    /**
     * 新增意见
     */
    @Log(title = "意见" , businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增意见")
    public AjaxResult add(@RequestBody Opinion opinion) {
        return toAjax(iOpinionService.save(opinion) ? 1 : 0);
    }

    /**
     * 修改意见
     */
    @Log(title = "意见" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Opinion opinion) {
        return toAjax(iOpinionService.updateById(opinion) ? 1 : 0);
    }

    /**
     * 删除意见
     */
    @Log(title = "意见" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iOpinionService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
