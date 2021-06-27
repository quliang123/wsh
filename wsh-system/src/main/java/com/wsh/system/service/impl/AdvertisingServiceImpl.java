package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.system.domain.Advertising;
import com.wsh.system.mapper.AdvertisingMapper;
import com.wsh.system.service.IAdvertisingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingMapper, Advertising> implements IAdvertisingService {

    @Override
    public List<Advertising> queryList(Advertising advertising) {
        LambdaQueryWrapper<Advertising> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(advertising.getPicture())){
            lqw.eq(Advertising::getPicture ,advertising.getPicture());
        }
        if (StringUtils.isNotBlank(advertising.getHeadline())){
            lqw.eq(Advertising::getHeadline ,advertising.getHeadline());
        }
        if (StringUtils.isNotBlank(advertising.getPath())){
            lqw.eq(Advertising::getPath ,advertising.getPath());
        }
        return this.list(lqw);
    }
}
