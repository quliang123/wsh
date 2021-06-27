package com.wsh.framework.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.core.domain.entity.User;
import com.wsh.common.core.domain.model.WxLoginUser;
import com.wsh.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wsh.common.core.domain.entity.SysUser;
import com.wsh.common.core.domain.model.LoginUser;
import com.wsh.common.enums.UserStatus;
import com.wsh.common.exception.BaseException;
import com.wsh.common.utils.StringUtils;
import com.wsh.system.service.ISysUserService;

/**
 * 用户验证处理
 *
 * @author wsh
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService systemUserService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if(username.contains("miniapp:")){
            String replace = username.replace("miniapp:", "");
            User user = userService.getOne(new QueryWrapper<User>().eq("open_id",replace));
            if (StringUtils.isNull(user))
            {
                user = new User();
                user.setOpenId(replace);
                boolean save = userService.save(user);
                if(!save){
                    throw new UsernameNotFoundException("用户创建失败");
                }
            }
            return createWxLoginUser(user);
        }else{
            SysUser user = systemUserService.selectUserByUserName(username);
            if (StringUtils.isNull(user))
            {
                log.info("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
            }
            else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
            {
                log.info("登录用户：{} 已被删除.", username);
                throw new BaseException("对不起，您的账号：" + username + " 已被删除");
            }
            else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
            {
                log.info("登录用户：{} 已被停用.", username);
                throw new BaseException("对不起，您的账号：" + username + " 已停用");
            }

            return createLoginUser(user);
        }

    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }

    public UserDetails createWxLoginUser(User user)
    {
        return new WxLoginUser(user);
    }
}
