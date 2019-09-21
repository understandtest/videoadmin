package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.SysRoleMenu;


public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	    public List<Integer> queryRolePermissionsRef(Map<String, Object> params);
}