package com.wsh.system.domain.vo;

import com.wsh.system.domain.Activity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityVo extends Activity implements Serializable {

    /** 状态(0报名 1已取消) */
    private String status;
    //是否已点赞
    private Boolean liked;
}
