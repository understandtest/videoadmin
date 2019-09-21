package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;

import java.io.Serializable;


/**
 * <p>
 * 系统用户与角色关联表
 * </p>
 */
@TableName("sys_user_role_rel")
@SuppressWarnings("serial")
public class SysUserRoleRel extends BaseModel {

      /**
     * 用户ID
     */
         	   @TableField("user_id")
        	private Integer userId;
      /**
     * 角色ID
     */
         	   @TableField("role_id")
        	private Integer roleId;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}