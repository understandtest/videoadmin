package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

	public List<SysMenu> selectMenuById(Map<String,Object> params);
}