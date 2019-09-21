package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

/**
 * <p>
 * 明星
 * </p>
 * 
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_star")
@SuppressWarnings("serial")
public class TStar extends BaseModel {

	/**
	 * 头像
	 */
	private String headpic;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 身高
	 */
	@TableField("height_num")
	private String heightNum;
	/**
	 * 图片类型 1=系统 2=外部
	 */
	@TableField("pic_type")
	private Integer picType;
	/**
	 * 三围
	 */
	private String bwh;
	/**
	 * 罩杯 基础数据
	 */
	private String cup;

	/**
	 * 热度
	 */
	private Integer heat;

	/**
	 * 简介
	 */
	@TableField("brief_context")
	private String briefContext;

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeightNum() {
		return heightNum;
	}

	public void setHeightNum(String heightNum) {
		this.heightNum = heightNum;
	}

	public String getBwh() {
		return bwh;
	}

	public void setBwh(String bwh) {
		this.bwh = bwh;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getBriefContext() {
		return briefContext;
	}

	public void setBriefContext(String briefContext) {
		this.briefContext = briefContext;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

}