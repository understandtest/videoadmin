package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

import java.io.Serializable;


/**
 * <p>
 * 标签类型
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_tag_type")
@SuppressWarnings("serial")
public class TTagType extends BaseModel {

    /**
     * 名称
     */
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}