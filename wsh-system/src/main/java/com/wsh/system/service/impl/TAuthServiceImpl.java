package com.wsh.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.wsh.system.mapper.TAuthMapper;
import com.wsh.system.domain.TAuth;
import com.wsh.system.service.ITAuthService;

import java.util.List;
import java.util.Map;

/**
 * 认证Service业务层处理
 *
 * @author wsh
 * @date 2021-04-14
 */
@Service
public class TAuthServiceImpl extends ServiceImpl<TAuthMapper, TAuth> implements ITAuthService {

    @Override
    public List<TAuth> queryList(TAuth tAuth) {
        LambdaQueryWrapper<TAuth> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tAuth.getUserName())){
            lqw.like(TAuth::getUserName ,tAuth.getUserName());
        }
        if (StringUtils.isNotBlank(tAuth.getWechatNo())){
            lqw.eq(TAuth::getWechatNo ,tAuth.getWechatNo());
        }
        if (tAuth.getUserId() != null){
            lqw.eq(TAuth::getUserId ,tAuth.getUserId());
        }
        if (StringUtils.isNotBlank(tAuth.getStatus())){
            lqw.eq(TAuth::getStatus ,tAuth.getStatus());
        }
        return this.list(lqw);
    }
}
