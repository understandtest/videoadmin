package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * AUTHER lbh
 * Date 2019/5/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_video_pay")
public class TVideoPay extends BaseModel {

    @TableField("video_id")
    private Integer videoId;

    @TableField("member_id")
    private Integer memberId;


}
