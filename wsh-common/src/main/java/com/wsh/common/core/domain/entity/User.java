package com.wsh.common.core.domain.entity;

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
 * 用户对象 user
 * 
 * @author wsh
 * @date 2021-04-07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

private static final long serialVersionUID=1L;


    /** 用户ID */
    @TableId(value = "user_id")
    private Long userId;

    /** 微信开放id */
    @ApiModelProperty(value = "微信开放id")
    private String openId;

    /** 登录账号 */
    @Excel(name = "登录账号")
    @ApiModelProperty(value = "登录账号")
    private String loginName;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    @ApiModelProperty(value = "用户昵称")
    private String userName;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别" , readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(value = "用户性别")
    private String sex;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    /** 生日 */
    @Excel(name = "生日" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 微信号码 */
    @Excel(name = "微信号码")
    @ApiModelProperty(value = "微信号码")
    private String vxNumber;

    /** 婚姻状况(0未婚 1已婚) */
    @Excel(name = "婚姻状况(0未婚 1已婚)")
    @ApiModelProperty(value = "婚姻状况(0未婚 1已婚)")
    private String marriage;

    /** 祖籍 */
    @Excel(name = "祖籍")
    @ApiModelProperty(value = "祖籍")
    private String home;

    /** 头像路径 */
    @Excel(name = "头像路径")
    @ApiModelProperty(value = "头像路径")
    private String avatar;

    /** 认证(0未认证 1已认证) */
    @Excel(name = "认证(0未认证 1已认证)")
    @ApiModelProperty(value = "认证(0未认证 1已认证)")
    private String identification;

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
