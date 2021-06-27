package com.wsh.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 认证对象 t_auth
 * 
 * @author wsh
 * @date 2021-04-14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_auth")
public class TAuth implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "auth_id")
    private Long authId;

    /** 名字 */
    @Excel(name = "名字")
    private String userName;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remark;

    /** 微信号 */
    @Excel(name = "微信号")
    private String wechatNo;

    /** 创建者 */
    private String createBy;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 创建时间 */
    private Date createTime;

    /** 认证状态 */
    @Excel(name = "认证状态")
    private String status;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
