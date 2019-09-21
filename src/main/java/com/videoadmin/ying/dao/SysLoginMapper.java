package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysMenu;

public interface SysLoginMapper extends BaseMapper<SysLogin> {

	public SysLogin getSysLoginByLogin(Map<String, Object> paramMap);
	
	public List<Map<String, Object>> getPermission(@Param("id") Integer id);
	
	public List<SysMenu> getMenusByUserId(@Param("id") Integer id);
	
	public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);

	public Integer validateUsername(Map<String, Object> paraMap);
	
    public Map<String, Object> getUserInfo(@Param("cm") Map<String, Object> paramMap);
}