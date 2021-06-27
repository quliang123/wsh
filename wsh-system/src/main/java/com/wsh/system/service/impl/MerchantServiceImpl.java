package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.system.domain.Merchant;
import com.wsh.system.mapper.MerchantMapper;
import com.wsh.system.service.IMerchantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户Service业务层处理
 *
 * @author wsh
 * @date 2021-03-09
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {

    @Override
    public List<Merchant> queryList(Merchant merchant) {
        LambdaQueryWrapper<Merchant> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(merchant.getMerName())){
            lqw.like(Merchant::getMerName ,merchant.getMerName());
        }
        if (StringUtils.isNotBlank(merchant.getCertificate())){
            lqw.eq(Merchant::getCertificate ,merchant.getCertificate());
        }
        if (StringUtils.isNotBlank(merchant.getInstitutionCode())){
            lqw.eq(Merchant::getInstitutionCode ,merchant.getInstitutionCode());
        }
        if (merchant.getValidTime() != null){
            lqw.eq(Merchant::getValidTime ,merchant.getValidTime());
        }
        if (StringUtils.isNotBlank(merchant.getLoginName())){
            lqw.like(Merchant::getLoginName ,merchant.getLoginName());
        }
        if (StringUtils.isNotBlank(merchant.getStatus())){
            lqw.eq(Merchant::getStatus ,merchant.getStatus());
        }
        if (StringUtils.isNotBlank(merchant.getPassword())){
            lqw.eq(Merchant::getPassword ,merchant.getPassword());
        }
        return this.list(lqw);
    }
}
