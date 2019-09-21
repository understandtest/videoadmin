package com.videoadmin.ying.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysMenuMapper;
import com.videoadmin.ying.po.SysMenu;

/**
 * <p>
 * 菜单表  服务实现�?
 * </p>
 */
@Service("sysMenuService")
public class SysMenuService extends BaseService<SysMenu> {
	
	public List<SysMenu> selectMenuById(Map<String,Object> params)
	{
    	 List<SysMenu> result = new ArrayList<SysMenu>();
    	  //查询到角色id 下所有的菜单
    	 List<SysMenu> menus = ((SysMenuMapper)mapper).selectMenuById(params);
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

}