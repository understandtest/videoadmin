package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.SysMenu;
import com.videoadmin.ying.po.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

	
	public List<Map<String,Object>> selectRoleList(Map<String,Object> params);
    
    public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
    
    public List<SysMenu> selectAllPression(Map<String,Object> params);
  
	public Integer validateRoleName(Map<String, Object> paraMap);
}