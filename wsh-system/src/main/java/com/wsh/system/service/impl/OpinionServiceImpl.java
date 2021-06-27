package com.wsh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.system.domain.Opinion;
import com.wsh.system.mapper.OpinionMapper;
import com.wsh.system.service.IOpinionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 意见Service业务层处理
 *
 * @author wsh
 * @date 2021-03-31
 */
@Service
public class OpinionServiceImpl extends ServiceImpl<OpinionMapper, Opinion> implements IOpinionService {

    @Override
    public List<Opinion> queryList(Opinion opinion) {
        LambdaQueryWrapper<Opinion> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(opinion.getContent())){
            lqw.eq(Opinion::getContent ,opinion.getContent());
        }
        if (StringUtils.isNotBlank(opinion.getPicture())){
            lqw.eq(Opinion::getPicture ,opinion.getPicture());
        }
        if (StringUtils.isNotBlank(opinion.getPhone())){
            lqw.eq(Opinion::getPhone ,opinion.getPhone());
        }
        return this.list(lqw);
    }
}
