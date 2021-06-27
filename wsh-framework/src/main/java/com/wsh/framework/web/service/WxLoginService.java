package com.wsh.framework.web.service;

import com.wsh.common.constant.Constants;
import com.wsh.common.core.domain.model.LoginUser;
import com.wsh.common.core.domain.model.WxLoginUser;
import com.wsh.common.core.redis.RedisCache;
import com.wsh.common.exception.CustomException;
import com.wsh.common.exception.user.CaptchaException;
import com.wsh.common.exception.user.CaptchaExpireException;
import com.wsh.common.exception.user.UserPasswordNotMatchException;
import com.wsh.common.utils.MessageUtils;
import com.wsh.framework.manager.AsyncManager;
import com.wsh.framework.manager.factory.AsyncFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 * 
 * @author wsh
 */
@Component
public class WxLoginService
{
    @Autowired
    private WxTokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @return 结果
     */
    public String login(String username)
    {
        //TODO 这边做小程序登录

        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken("miniapp:"+username,username));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        WxLoginUser loginUser = (WxLoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
