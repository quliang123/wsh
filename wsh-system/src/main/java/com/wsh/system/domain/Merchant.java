package com.wsh.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wsh.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户对象 merchant
 * 
 * @author wsh
 * @date 2021-03-09
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("merchant")
public class Merchant implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "mer_id")
    private Long merId;

    /** 商户名 */
    @Excel(name = "商户名")
    @ApiModelProperty(value = "商户名")
    private String merName;

    /** 有效证件 */
    @Excel(name = "有效证件")
    @ApiModelProperty(value = "有效证件")
    private String certificate;

    /** 机构代码 */
    @Excel(name = "机构代码")
    @ApiModelProperty(value = "机构代码")
    private String institutionCode;

    /** 有效时间 */
    @Excel(name = "有效时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;

    /** 登录名 */
    @Excel(name = "登录名")
    private String loginName;

    /** 状态 0 失效 1有效 */
    @Excel(name = "状态 0 失效 1有效")
    private String status;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 删除表示 1正常 2删除 */
    private String delFlag;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
