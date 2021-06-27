package com.wsh.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wsh.common.core.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.wsh.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 报名对象 apply
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_apply")
public class Apply implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "apply_id")
    private Long applyId;

    /** 活动id */
    @Excel(name = "活动id")
    @ApiModelProperty(value = "活动id")
    private Long activityId;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 状态(0报名 1已取消) */
    @Excel(name = "状态(0报名 1已取消)")
    @ApiModelProperty(value = "状态(0报名 1已取消)")
    private String status;

    @TableField(exist = false)
    private User user;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    @TableField(exist = false)
    private Activity activity;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
