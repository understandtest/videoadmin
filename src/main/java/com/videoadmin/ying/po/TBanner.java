package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 轮播数据
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_banner")
@SuppressWarnings("serial")
public class TBanner extends BaseModel {

      /**
     * 类型 1=首页 2= 频道  3=广告 4=启动页
     */
         	   @TableField("pic_type")
        	private Integer picType;
      /**
     * 图片
     */
         	   @TableField("pic_url")
        	private String picUrl;
      /**
     * 链接类型 1=外部链接 2=内部影片
     */
         	   @TableField("link_type")
        	private Integer linkType;
      /**
     * 链接地址
     */
         	   @TableField("link_url")
        	private String linkUrl;
         	  /**
         	     * 是否有效 0=否 1=是
         	     */
         	 @TableField("is_show")
         	 private Integer isShow;
         	 /**
         	  * 图片来源
         	  */
         	 @TableField("pic_from")
         	 private Integer picFrom;
         	 /**
      	     * 标签类型ID
      	     */
      	 @TableField("tag_type_id")
      	 private Integer tagTypeId;
      	 /**
  	     * 标签ID
  	     */
  	 @TableField("tag_id")
  	 private Integer tagId;

	public Integer getIsShow() {
				return isShow;
			}

			public void setIsShow(Integer isShow) {
				this.isShow = isShow;
			}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getPicFrom() {
		return picFrom;
	}

	public void setPicFrom(Integer picFrom) {
		this.picFrom = picFrom;
	}

	public Integer getTagTypeId() {
		return tagTypeId;
	}

	public void setTagTypeId(Integer tagTypeId) {
		this.tagTypeId = tagTypeId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	
}