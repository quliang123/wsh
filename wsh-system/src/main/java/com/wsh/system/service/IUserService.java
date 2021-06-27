package com.wsh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.User;
//import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author wsh
 * @date 2021-03-31
 */
public interface IUserService extends IService<User> {

    /**
     * 查询列表
     */
    List<User> queryList(User user);

    /**
     * openId获取用户信息
     * @param openId
     * @return
     */
    User selectByOpenId(String openId);
//
//    AjaxResult login(String code) throws WxErrorException;
//
//    AjaxResult info(String appid, String sessionKey, String signature, String rawData, String encryptedData, String iv);

}
