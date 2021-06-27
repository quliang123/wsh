package com.wsh.web.controller.system;

import java.util.List;
import java.util.Set;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wsh.common.core.domain.model.WxLoginBody;
import com.wsh.framework.config.wx.WxMaConfiguration;
import com.wsh.system.service.IUserService;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wsh.common.constant.Constants;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.SysMenu;
import com.wsh.common.core.domain.entity.SysUser;
import com.wsh.common.core.domain.model.LoginBody;
import com.wsh.common.core.domain.model.LoginUser;
import com.wsh.common.utils.ServletUtils;
import com.wsh.framework.web.service.SysLoginService;
import com.wsh.framework.web.service.SysPermissionService;
import com.wsh.framework.web.service.TokenService;
import com.wsh.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author wsh
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserService iUserService;


    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    @PostMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestBody WxLoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        final WxMaService wxService = WxMaConfiguration.getMaService("wxc2706f765e6843d0");
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(loginBody.getCode());
//            WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
//            session.setOpenid("oqezn5NpI1KcXeAdLSt3iqkncj1M");
//            session.setSessionKey();
            // 生成令牌
            String token = loginService.wxLogin(session);
            ajax.put(Constants.TOKEN, token);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
