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
import com.wsh.system.domain.TAuth;
import com.wsh.system.service.ITAuthService;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.common.core.page.TableDataInfo;

/**
 * 认证Controller
 * 
 * @author wsh
 * @date 2021-04-14
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/auth" )
public class SysTAuthController extends BaseController {

    private final ITAuthService iTAuthService;

    /**
     * 查询认证列表
     */
    @PreAuthorize("@ss.hasPermi('system:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(TAuth tAuth) {
        startPage();
        List<TAuth> list = iTAuthService.queryList(tAuth);
        return getDataTable(list);
    }

    /**
     * 导出认证列表
     */
    @PreAuthorize("@ss.hasPermi('system:auth:export')" )
    @Log(title = "认证" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TAuth tAuth) {
        List<TAuth> list = iTAuthService.queryList(tAuth);
        ExcelUtil<TAuth> util = new ExcelUtil<TAuth>(TAuth.class);
        return util.exportExcel(list, "auth" );
    }

    /**
     * 获取认证详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:auth:query')" )
    @GetMapping(value = "/{authId}" )
    public AjaxResult getInfo(@PathVariable("authId" ) Long authId) {
        return AjaxResult.success(iTAuthService.getById(authId));
    }

    /**
     * 新增认证
     */
    @PreAuthorize("@ss.hasPermi('system:auth:add')" )
    @Log(title = "认证" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TAuth tAuth) {
        return toAjax(iTAuthService.save(tAuth) ? 1 : 0);
    }

    /**
     * 修改认证
     */
    @PreAuthorize("@ss.hasPermi('system:auth:edit')" )
    @Log(title = "认证" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TAuth tAuth) {
        return toAjax(iTAuthService.updateById(tAuth) ? 1 : 0);
    }

    /**
     * 删除认证
     */
    @PreAuthorize("@ss.hasPermi('system:auth:remove')" )
    @Log(title = "认证" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{authIds}" )
    public AjaxResult remove(@PathVariable Long[] authIds) {
        return toAjax(iTAuthService.removeByIds(Arrays.asList(authIds)) ? 1 : 0);
    }
}
