package com.wsh.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 消息对象 message
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("message")
public class Message implements Serializable {

private static final long serialVersionUID=1L;


    /** ID */
    @TableId(value = "message_id")
    private Long messageId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;


    /** 消息类型 (1,报名通知2,活动通知,3系统通知)*/
    private String msgType;

    /** 详情 */
    @Excel(name = "详情")
    @ApiModelProperty(value = "详情")
    private String particulars;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String status;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
