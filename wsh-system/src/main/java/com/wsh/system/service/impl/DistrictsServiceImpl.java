package com.wsh.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.wsh.system.mapper.DistrictsMapper;
import com.wsh.system.domain.Districts;
import com.wsh.system.service.IDistrictsService;

import java.util.List;
import java.util.Map;

/**
 * 地区Service业务层处理
 *
 * @author wsh
 * @date 2021-04-13
 */
@Service
public class DistrictsServiceImpl extends ServiceImpl<DistrictsMapper, Districts> implements IDistrictsService {

    @Override
    public List<Districts> queryList(Districts districts) {
        LambdaQueryWrapper<Districts> lqw = Wrappers.lambdaQuery();
        if (districts.getPid() != null){
            lqw.eq(Districts::getPid ,districts.getPid());
        }
        if (districts.getDeep() != null){
            lqw.eq(Districts::getDeep ,districts.getDeep());
        }
        if (StringUtils.isNotBlank(districts.getName())){
            lqw.like(Districts::getName ,districts.getName());
        }
        if (StringUtils.isNotBlank(districts.getPinyin())){
            lqw.eq(Districts::getPinyin ,districts.getPinyin());
        }
        if (StringUtils.isNotBlank(districts.getPinyinShor())){
            lqw.eq(Districts::getPinyinShor ,districts.getPinyinShor());
        }
        if (StringUtils.isNotBlank(districts.getExtName())){
            lqw.like(Districts::getExtName ,districts.getExtName());
        }
        if (StringUtils.isNotBlank(districts.getOperator())){
            lqw.eq(Districts::getOperator ,districts.getOperator());
        }
        return this.list(lqw);
    }
}
