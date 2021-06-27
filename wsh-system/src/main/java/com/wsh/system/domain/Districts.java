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
 * 地区对象 districts
 * 
 * @author wsh
 * @date 2021-04-13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("districts")
public class Districts implements Serializable {

private static final long serialVersionUID=1L;


    /** 编号 */
    @TableId(value = "id")
    private Integer id;

    /** 上级编号 */
    @Excel(name = "上级编号")
    @ApiModelProperty(value = "上级编号")
    private Long pid;

    /** 层级 */
    @Excel(name = "层级")
    @ApiModelProperty(value = "层级")
    private Integer deep;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    private String name;

    /** 拼音 */
    @Excel(name = "拼音")
    @ApiModelProperty(value = "拼音")
    private String pinyin;

    /** 拼音缩写 */
    @Excel(name = "拼音缩写")
    @ApiModelProperty(value = "拼音缩写")
    private String pinyinShor;

    /** 扩展名 */
    @Excel(name = "扩展名")
    @ApiModelProperty(value = "扩展名")
    private String extName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
