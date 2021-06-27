package com.wsh.web.controller.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wsh.common.annotation.Log;
import com.wsh.common.config.WshConfig;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.User;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.file.FileUploadUtils;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.framework.config.wx.WxMaConfiguration;
import com.wsh.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 用户Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/user" )
@Api(tags = "用户模块")
public class UserController extends BaseController {

    @Autowired
    private final IUserService iUserService;



    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(User user) {
        startPage();
        List<User> list = iUserService.queryList(user);
        return getDataTable(list);
    }

    /**
     * 导出用户列表
     */
    @Log(title = "用户" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(User user) {
        List<User> list = iUserService.queryList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.exportExcel(list, "user" );
    }

    /**
     * 获取用户详细信息
     */
    @GetMapping(value = "/{userId}" )
    public AjaxResult getInfo(@PathVariable("userId" ) Long userId) {
        return AjaxResult.success(iUserService.getById(userId));
    }


    @ApiOperation("获取我的个人信息")
    @GetMapping("getUserInfo")
    public AjaxResult getUserInfo(){
        return AjaxResult.success(iUserService.getById(getUserId()));
    }

    /**
     * 新增用户
     */
    @Log(title = "用户" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user) {
        return toAjax(iUserService.save(user) ? 1 : 0);
    }

    /**
     * 修改用户
     */
    @Log(title = "用户" , businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("用户修改")
    public AjaxResult edit(@RequestBody User user) {
        user.setUserId(getUserId());
        return toAjax(iUserService.updateById(user) ? 1 : 0);
    }

    /**
     * 删除用户
     */
    @Log(title = "用户" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}" )
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(iUserService.removeByIds(Arrays.asList(userIds)) ? 1 : 0);
    }


    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @ApiOperation("获取用户绑定手机号信息")
    @GetMapping("/wxPhone")
    public AjaxResult phone(String sessionKey,String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService("wxc2706f765e6843d0");

        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
//            return AjaxResult.error("user check failed");
//        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey,encryptedData, iv);
        User user = getUser();
        user.setPhonenumber(phoneNoInfo.getPhoneNumber());
        boolean b = iUserService.updateById(user);
        if(!b){
            return AjaxResult.error("数据绑定失败，请重新尝试。");
        }
        return AjaxResult.success(phoneNoInfo);
    }


    @ApiOperation("微信同步用户信息")
    @GetMapping("/wxInfo")
    public AjaxResult info( String sessionKey,String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService("wxc2706f765e6843d0");
        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
//            return AjaxResult.error("获取用户信息失败");
//        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey,encryptedData,iv);
        User user = getUser();
        if(null!=user){
            user.setAvatar(userInfo.getAvatarUrl());
            user.setUserName(userInfo.getNickName());
            user.setSex(userInfo.getGender());
            iUserService.updateById(user);
        }
        return AjaxResult.success(user);
    }




////    @GetMapping("/login")
////    @ApiOperation("用户登录")
//    public AjaxResult login(@RequestParam String code) throws WxErrorException {
//        return iUserService.login(code);
//
//    }
//
//
//    @PostMapping("/info")
//    @ApiOperation("同步用户信息")
//    public AjaxResult info(@RequestBody String appid, String sessionKey,
//                           String signature, String rawData, String encryptedData, String iv){
//
//        return iUserService.info(appid,sessionKey,signature,rawData,encryptedData,iv);
//
//    }


    @ApiOperation("修改用户头像")
    @PostMapping("modifyHeadPic")
    public AjaxResult modifyHeadPic(@RequestParam("avatarfile") MultipartFile file){
        User user = getUser();
        try {
            String avatar = FileUploadUtils.upload(WshConfig.getAvatarPath(),file);
            user.setAvatar(avatar);
            boolean b = iUserService.updateById(user);
            if(!b){
                return AjaxResult.error("修改失败，请重新尝试");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }


}
