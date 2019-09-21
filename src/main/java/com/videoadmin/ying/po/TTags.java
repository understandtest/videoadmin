package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

import java.io.Serializable;


/**
 * <p>
 * 标签
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_tags")
@SuppressWarnings("serial")
public class TTags extends BaseModel {

    /**
     * 标签类型
     */
    @TableField("tag_type_id")
    private Integer tagTypeId;
    /**
     * 图片类型 1=系统 2=外部
     */
    @TableField("pic_type")
    private Integer picType;
    /**
     * 标签图标
     */
    @TableField("pic_url")
    private String picUrl;
    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    public Integer getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(Integer tagTypeId) {
        this.tagTypeId = tagTypeId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

}