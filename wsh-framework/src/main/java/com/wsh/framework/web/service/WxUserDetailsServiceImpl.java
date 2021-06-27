//package com.wsh.framework.web.service;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.wsh.common.core.domain.entity.SysUser;
//import com.wsh.common.core.domain.entity.User;
//import com.wsh.common.core.domain.model.LoginUser;
//import com.wsh.common.core.domain.model.WxLoginUser;
//import com.wsh.common.enums.UserStatus;
//import com.wsh.common.exception.BaseException;
//import com.wsh.common.utils.StringUtils;
//import com.wsh.system.service.ISysUserService;
//import com.wsh.system.service.IUserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * 用户验证处理
// *
// * @author wsh
// */
//@Service
//public class WxUserDetailsServiceImpl implements UserDetailsService
//{
//    private static final Logger log = LoggerFactory.getLogger(WxUserDetailsServiceImpl.class);
//
//    @Autowired
//    private IUserService userService;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
//    {
//        User user = userService.getOne(new QueryWrapper<User>().eq("open_id",username));
//        if (StringUtils.isNull(user))
//        {
//            log.info("登录用户：{} 不存在.", username);
//            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
//        }
//        return createLoginUser(user);
//    }
//
//    public UserDetails createLoginUser(User user)
//    {
//        return new WxLoginUser(user);
//    }
//}
