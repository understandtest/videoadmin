package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

import java.io.Serializable;


/**
 * <p>
 * 等级表
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_level")
@SuppressWarnings("serial")
public class TLevel extends BaseModel {

    /**
     * 等级名称
     */
    private String name;
    /**
     * 等级排序
     */
    @TableField("sort_no")
    private Integer sortNo;
    /**
     * 图片类型 1=系统 2=外部
     */
    @TableField("icon_type")
    private Integer iconType;
    /**
     * 等级图标
     */
    @TableField("le_icon")
    private String leIcon;
    /**
     * 推广几人
     */
    @TableField("ex_num")
    private Integer exNum;
    /**
     * 观影次数
     */
    @TableField("day_num")
    private Integer dayNum;

    @TableField("view_num")
    private Integer viewNum;

    @TableField("cache_num")
    private Integer cacheNum;

    public Integer getCacheNum() {
        return cacheNum;
    }

    public void setCacheNum(Integer cacheNum) {
        this.cacheNum = cacheNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getLeIcon() {
        return leIcon;
    }

    public void setLeIcon(String leIcon) {
        this.leIcon = leIcon;
    }

    public Integer getExNum() {
        return exNum;
    }

    public void setExNum(Integer exNum) {
        this.exNum = exNum;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getIconType() {
        return iconType;
    }

    public void setIconType(Integer iconType) {
        this.iconType = iconType;
    }

}