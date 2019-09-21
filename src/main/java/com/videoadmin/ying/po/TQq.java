package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 交流群
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_qq")
@SuppressWarnings("serial")
public class TQq extends BaseModel {

      /**
     * 名称
     */
            	private String name;
      /**
     * 链接地址
     */
         	   @TableField("link_url")
        	private String linkUrl;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

}