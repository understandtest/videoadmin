package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

import java.io.Serializable;


/**
 * <p>
 * 系统角色表
 * </p>
 */
@TableName("sys_role")
@SuppressWarnings("serial")
public class SysRole extends BaseModel {

      /**
     * 角色名称
     */
            	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}