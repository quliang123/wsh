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
 * 活动对象 activity
 * 
 * @author wsh
 * @date 2021-03-31
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("activity")
public class Activity implements Serializable {

private static final long serialVersionUID=1L;


    /** ID */
    @TableId(value = "activity_id")
    private Long activityId;

    /** 活动发布者id */
    @Excel(name = "活动发布者id")
    @ApiModelProperty(value = "活动发布者id")
    private Long publisherId;

    /** 活动发布者名字 */
    @Excel(name = "活动发布者名字")
    @ApiModelProperty(value = "活动发布者名字")
    private String publisherName;

    /** 组织人id */
    @Excel(name = "组织人id")
    @ApiModelProperty(value = "组织人id")
    private Long organizationId;

    /** 组织人名字 */
    @Excel(name = "组织人名字")
    @ApiModelProperty(value = "组织人名字")
    private String organizationName;

    /** 组织人电话 */
    @Excel(name = "组织人电话")
    @ApiModelProperty(value = "组织人电话")
    private String organizationPhone;

    @Excel(name = "活动内容")
    @ApiModelProperty(value = "活动内容")
    private String content;

    /** 活动时间 */
    @Excel(name = "活动时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "活动时间")
    private Date activityDate;

    /** 活动地点 */
    @Excel(name = "活动地点")
    @ApiModelProperty(value = "活动地点")
    private String activityPlace;

    @ApiModelProperty(value = "封面")
    private String cover;

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty(value = "标题")
    private String headline;

    /** 截止时间 */
    @Excel(name = "截止时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "截止时间")
    private Date deadline;

    /** 点赞数 */
    @Excel(name = "点赞数")
    @ApiModelProperty(value = "点赞数")
    private Long likeCount;

    /** 评论数 */
    @Excel(name = "评论数")
    @ApiModelProperty(value = "评论数")
    private Long discussCount;

    /** 活动介绍 */
    @Excel(name = "活动介绍")
    @ApiModelProperty(value = "活动介绍")
    private String introduce;

    /** 热门 */
    @Excel(name = "热门")
    @ApiModelProperty(value = "热门")
    private String hot;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
