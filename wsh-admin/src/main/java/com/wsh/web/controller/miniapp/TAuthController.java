package com.wsh.web.controller.miniapp;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.utils.DateUtils;
import com.wsh.common.utils.bean.BeanUtils;
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
@Api(tags = "认证")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/auth" )
public class TAuthController extends BaseController {

    private final ITAuthService iTAuthService;


    @ApiOperation("提交认证")
    @PostMapping("submitAuth")
    public AjaxResult submitAuth(@RequestBody TAuth auth){
        auth.setUserId(getUserId());
        TAuth authServiceOne = iTAuthService.getOne(new QueryWrapper<TAuth>().eq("user_id", getUserId()).last("limit 1"));
        boolean success;
        if(authServiceOne!=null){
            auth.setAuthId(authServiceOne.getAuthId());
            auth.setStatus("0");
            success = iTAuthService.updateById(auth);
        }else{
            auth.setUserId(getUserId());
            auth.setCreateTime(DateUtils.getNowDate());
            success = iTAuthService.save(auth);
        }
        return toAjax(success);
    }


    @ApiOperation("获取我的认证")
    @GetMapping("getMyAuth")
    public AjaxResult getMyAuth(){
        TAuth auth = iTAuthService.getOne(new QueryWrapper<TAuth>().eq("user_id", getUserId()).last("limit 1"));
        return AjaxResult.success(auth);
    }
}
