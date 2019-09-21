package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;

/**
 * <p>
 * 分类
 * </p>
 * 
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_classify")
@SuppressWarnings("serial")
public class TClassify extends BaseModel {

	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 图片类型 1=系统 2=外部
	 */
	@TableField("icon_type")
	private Integer iconType;
	/**
	 * 分类图标
	 */
	@TableField("classify_icon")
	private String classifyIcon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassifyIcon() {
		return classifyIcon;
	}

	public void setClassifyIcon(String classifyIcon) {
		this.classifyIcon = classifyIcon;
	}

	public Integer getIconType() {
		return iconType;
	}

	public void setIconType(Integer iconType) {
		this.iconType = iconType;
	}

}