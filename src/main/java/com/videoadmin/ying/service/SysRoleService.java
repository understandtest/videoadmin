package com.videoadmin.ying.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysRoleMapper;
import com.videoadmin.ying.dao.SysRoleMenuMapper;
import com.videoadmin.ying.po.SysMenu;
import com.videoadmin.ying.po.SysRole;
import com.videoadmin.ying.po.SysRoleMenu;

/**
 * <p>
 * 系统角色表 服务实现�?
 * </p>
 */
@Service("sysRoleService")
public class SysRoleService extends BaseService<SysRole> {

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	public List<Map<String, Object>> selectRoleList(Map<String, Object> params) {
		return ((SysRoleMapper) mapper).selectRoleList(params);
	}

	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
		logger.info("角色查询，入参：" + paramMap);
		Page<Map<String, Object>> page = getPageMap(paramMap);
		logger.info("角色查询，入参：" + page);
		page.setRecords(((SysRoleMapper) mapper).selectListPage(page, paramMap));
		logger.info("角色查询，出参：" + page.toString());
		return page;
	}

	public List<SysMenu> selectAllPression(Map<String, Object> params) {

		List<SysMenu> result = new ArrayList<SysMenu>();
		// 查询到角色id 下所有的菜单
		List<SysMenu> menus = ((SysRoleMapper) mapper).selectAllPression(params);
		return getSysPermissionsTree(null,menus);
	}

	
	private List<SysMenu>   getSysPermissionsTree(Integer parentId,List<SysMenu> menus)
    {
        List<SysMenu> result = new ArrayList<SysMenu>();
        if(parentId == null)
        {
            for(SysMenu ss:menus)
            {
                if(ss.getParentId()==null || "".equals(ss.getParentId()))
                {
                    ss.setChildMenus(getSysPermissionsTree(ss.getId(),menus));
                    result.add(ss);
                }
            }
        }
        else
        {
            for(SysMenu sk:menus)
            {
                if(sk.getParentId()!= null && (sk.getParentId().intValue() == parentId.intValue()))
                {
                    sk.setChildMenus(getSysPermissionsTree(sk.getId(),menus));
                    result.add(sk);
                }
            }
        }
        return result;
    }
	
	@Transactional
	public void addOrUpdateRole(Map<String, Object> param) {
		SysRole sysRole = new SysRole();
		SysRoleMenu yingRoleMenuRel = new SysRoleMenu();
		sysRole.setName(param.get("roleName").toString());
		//sysRole.setCreateBy(Integer.valueOf(param.get("userId").toString()));
		sysRole.setCreateTime(new Date());
		sysRole.setUpdateTime(new Date());
		int roleId;
		if (param.get("id") != null && !("").equals(param.get("id"))) {
			// 修改角色
			roleId = Integer.valueOf(param.get("id").toString());
			sysRole.setId(roleId);
			mapper.updateById(sysRole);
		} else {
			// 添加角色
			mapper.insert(sysRole);
			roleId = sysRole.getId();
		}
		yingRoleMenuRel.setRoleId(roleId);
		//yingRoleMenuRel.setCreateBy(Integer.valueOf(param.get("userId").toString()));
		yingRoleMenuRel.setCreateTime(new Date());
		yingRoleMenuRel.setUpdateTime(new Date());
		// 根据roleId删除关联表所有的权限
		// sysRolePermissionsMapper.deleteById(roleId);
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("role_id", roleId);
		sysRoleMenuMapper.deleteByMap(mapp);
		String permissionIds = param.get("permissionIds").toString();
		String[] permission = permissionIds.split(",");
		if (permission.length > 0) {
			for (int i = 0; i < permission.length; i++) {
				// 获得单个权限Id
				String str = permission[i];
				int permissionId = Integer.parseInt(str);
				if (permissionId != 0) {
					yingRoleMenuRel.setMenuId(permissionId);
					// 重新给role赋予权限
					sysRoleMenuMapper.insert(yingRoleMenuRel);
				}

			}
		}
	}
	public Integer validateRoleName(Map<String,Object> paraMap){
		return ((SysRoleMapper)mapper).validateRoleName(paraMap);
	}
}