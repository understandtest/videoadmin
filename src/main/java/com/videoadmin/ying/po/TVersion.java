package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 版本控制
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_version")
@SuppressWarnings("serial")
public class TVersion extends BaseModel {

      /**
     * 版本类型
     */
         	   @TableField("version_type")
        	private Integer versionType;
      /**
     * 链接地址
     */
         	   @TableField("version_url")
        	private String versionUrl;
      /**
     * 版本号
     */
         	   @TableField("version_code")
        	private String versionCode;
         	  /**
         	     * 是否强制更新 0=否 1=是
         	     */
         	         	   @TableField("is_update")
         	        	private Integer isUpdate;
         	         	 /**
         	         	     * 更新内容简介
         	         	     */
         	         	         	   @TableField("version_context")
         	         	        	private String versionContext;

	public Integer getVersionType() {
		return versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getVersionContext() {
		return versionContext;
	}

	public void setVersionContext(String versionContext) {
		this.versionContext = versionContext;
	}

}