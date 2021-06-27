package com.wsh.web.controller.miniapp;

import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.SecurityUtils;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Merchant;
import com.wsh.system.service.IMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 商户Controller
 * 
 * @author wsh
 * @date 2021-03-09
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/merchant" )
public class MerchantController extends BaseController {

    private final IMerchantService iMerchantService;

    /**
     * 查询商户列表
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:list')")
    @GetMapping("/list")
    public TableDataInfo list(Merchant merchant) {
        startPage();
        List<Merchant> list = iMerchantService.queryList(merchant);
        return getDataTable(list);
    }

    /**
     * 导出商户列表
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:export')" )
    @Log(title = "商户" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Merchant merchant) {
        List<Merchant> list = iMerchantService.queryList(merchant);
        ExcelUtil<Merchant> util = new ExcelUtil<Merchant>(Merchant.class);
        return util.exportExcel(list, "merchant" );
    }

    /**
     * 获取商户详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:query')" )
    @GetMapping(value = "/{merId}" )
    public AjaxResult getInfo(@PathVariable("merId" ) Long merId) {
        return AjaxResult.success(iMerchantService.getById(merId));
    }

    /**
     * 新增商户
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:add')" )
    @Log(title = "商户" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Merchant merchant) {
        merchant.setPassword(SecurityUtils.encryptPassword(merchant.getPassword()));
        return toAjax(iMerchantService.save(merchant) ? 1 : 0);
    }

    /**
     * 修改商户
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:edit')" )
    @Log(title = "商户" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Merchant merchant) {
        return toAjax(iMerchantService.updateById(merchant) ? 1 : 0);
    }

    /**
     * 删除商户
     */
    @PreAuthorize("@ss.hasPermi('system:merchant:remove')" )
    @Log(title = "商户" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{merIds}" )
    public AjaxResult remove(@PathVariable Long[] merIds) {
        return toAjax(iMerchantService.removeByIds(Arrays.asList(merIds)) ? 1 : 0);
    }
}
