package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysUserRoleRelMapper;
import com.videoadmin.ying.po.SysUserRoleRel;

/**
 * <p>
 * 系统用户与角色关联表  服务实现�?
 * </p>
 */
@Service("sysUserRoleRelService")
public class SysUserRoleRelService extends BaseService<SysUserRoleRel> {
	public List<Integer> queryUserRoleRef(Map<String, Object> params) { 
        return ((SysUserRoleRelMapper)mapper).queryUserRoleRef(params);
    }
}