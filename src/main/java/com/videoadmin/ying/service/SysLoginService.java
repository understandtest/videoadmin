package com.videoadmin.ying.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysLoginMapper;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysMenu;

/**
 * <p>
 * 系统用户表  服务实现�?
 * </p>
 */
@Service("sysLoginService")
public class SysLoginService extends BaseService<SysLogin> {
	
	
	public SysLogin getSysLoginByLogin(Map<String, Object> paramMap) {
		return ((SysLoginMapper) mapper).getSysLoginByLogin(paramMap);
	}
	
	public List<Map<String, Object>> getCurrentUserPermission(SysLogin sysLogin){
		return ((SysLoginMapper)mapper).getPermission(sysLogin.getId());
	}
	
	public List<SysMenu> getMenusByUserId(Integer id){
        List<SysMenu> menus = ((SysLoginMapper)mapper).getMenusByUserId(id);
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
	                if(sk!= null && !"".equals(sk)&&sk.getParentId()!= null && (sk.getParentId().intValue() == parentId.intValue()))
	                {
	                    sk.setChildMenus(getSysPermissionsTree(sk.getId(),menus));
	                    result.add(sk);
	                }
	            }
	        }
	        return result;
	    }
	
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("管理员查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("管理员查询，入参："+page);
        page.setRecords(((SysLoginMapper)mapper).selectListPage(page, paramMap));
        logger.info("管理员查询，出参："+page.toString());
        return page;
	}

	public Integer validateUsername(Map<String,Object> paraMap){
		return ((SysLoginMapper)mapper).validateUsername(paraMap);
	}
	
    public Map<String, Object> getUserInfo(Map<String, Object> paramMap){
        logger.info("开始查询用户信息");
        return ((SysLoginMapper)mapper).getUserInfo(paramMap);
    }
}