package com.wsh.system.service.impl;

//import cn.binarywang.wx.miniapp.api.WxMaService;
//import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
//import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
//import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.domain.entity.User;
import com.wsh.common.core.redis.RedisCache;
//import com.wsh.system.config.WxMaConfiguration;
import com.wsh.system.mapper.UserMapper;
import com.wsh.system.service.IUserService;
//import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

//    @Value("${wx.miniapp.configs.appid}")
//    private String appid;
    @Autowired
    private RedisCache redisCache;

    @Override
    public List<User> queryList(User user) {
        LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(user.getLoginName())){
            lqw.like(User::getLoginName ,user.getLoginName());
        }
        if (StringUtils.isNotBlank(user.getUserName())){
            lqw.like(User::getUserName ,user.getUserName());
        }
        if (StringUtils.isNotBlank(user.getSex())){
            lqw.eq(User::getSex ,user.getSex());
        }
        if (StringUtils.isNotBlank(user.getPhonenumber())){
            lqw.eq(User::getPhonenumber ,user.getPhonenumber());
        }
        if (user.getBirthday() != null){
            lqw.eq(User::getBirthday ,user.getBirthday());
        }
        if (StringUtils.isNotBlank(user.getEmail())){
            lqw.eq(User::getEmail ,user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getVxNumber())){
            lqw.eq(User::getVxNumber ,user.getVxNumber());
        }
        if (StringUtils.isNotBlank(user.getMarriage())){
            lqw.eq(User::getMarriage ,user.getMarriage());
        }
        if (StringUtils.isNotBlank(user.getHome())){
            lqw.eq(User::getHome ,user.getHome());
        }
        if (StringUtils.isNotBlank(user.getAvatar())){
            lqw.eq(User::getAvatar ,user.getAvatar());
        }
        if (StringUtils.isNotBlank(user.getIdentification())){
            lqw.eq(User::getIdentification ,user.getIdentification());
        }
        return this.list(lqw);
    }

    @Override
    public User selectByOpenId(String openId) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("open_id",openId));
    }

//
//    /**
//     * 用户登录
//     * @param code
//     * @return
//     * @throws WxErrorException
//     */
//    @Override
//    public AjaxResult login(String code) throws WxErrorException {
//
//        if (StringUtils.isBlank(code)) {
//            return AjaxResult.error();
//        }
//
//        final WxMaService wxService = WxMaConfiguration.getMaService("wxc2706f765e6843d0");
//        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
//        String sessionKey = session.getSessionKey();
//        String openid = session.getOpenid();
//
////        RedisCache redisCache = new RedisCache();
//        String token = "access_token_" + sessionKey;
//
//        User one = this.getOne(new QueryWrapper<User>().eq("open_id", openid).last("limit 1"));
//        if (one ==null){
//            User user = new User();
//            user.setOpenId(openid);
//            boolean b = this.save(user);
//            if (b){
//                redisCache.setCacheObject(token,user);
//                redisCache.expire(token, 60 * 60 * 24);
//                return AjaxResult.success(sessionKey);
//            }
//            return AjaxResult.error();
//        }
//        redisCache.setCacheObject(token,one);
//        boolean b = redisCache.expire(token, 60 * 60 * 24);
//
//        if (b){
//            return AjaxResult.success(sessionKey);
//        }
//            return AjaxResult.error();
//    }
//
//    /**
//     * 同步用户信息
//     * @param appid
//     * @param sessionKey
//     * @param signature
//     * @param rawData
//     * @param encryptedData
//     * @param iv
//     * @return
//     */
//    @Override
//        public AjaxResult info(String appid, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
//
//        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
//
//        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
//            return AjaxResult.error();
//        }
//
//        // 解密用户信息
//        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
//        String openId = userInfo.getOpenId();
//        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//
//        User user = this.getOne(new QueryWrapper<User>().eq("open_id", openId).last("limit 1"));
//        if (user==null){
//            return AjaxResult.error();
//        }
//        user.setAvatar(userInfo.getAvatarUrl());
//        user.setUserName(userInfo.getNickName());
//        user.setSex(userInfo.getGender());
//        user.setPhonenumber(phoneNoInfo.getPhoneNumber());
//        boolean b = this.updateById(user);
//        if (b){
//            return AjaxResult.success();
//        }
//        return AjaxResult.error();
//    }

}
