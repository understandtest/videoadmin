package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import lombok.Data;

/**
 * @Author hong
 * @Date 19-9-10
 * 积分奖励实体类
 */
@Data
@TableName("t_award_integral")
public class AwardIntegral extends BaseModel {

    @TableField("integral_number")
    private Integer integralNumber;


}
