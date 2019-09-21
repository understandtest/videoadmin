package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.SysUserRoleRel;


public interface SysUserRoleRelMapper extends BaseMapper<SysUserRoleRel> {
	 public List<Integer> queryUserRoleRef(Map<String, Object> params);
}