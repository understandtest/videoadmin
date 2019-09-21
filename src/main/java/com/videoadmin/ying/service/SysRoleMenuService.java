package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysRoleMenuMapper;
import com.videoadmin.ying.po.SysRoleMenu;

@Service("sysRoleMenuService")
public class SysRoleMenuService extends BaseService<SysRoleMenu> {
	 public List<Integer> queryRolePermissionsRef(Map<String, Object> params) {
	        return ((SysRoleMenuMapper)mapper).queryRolePermissionsRef(params);
	    }
}