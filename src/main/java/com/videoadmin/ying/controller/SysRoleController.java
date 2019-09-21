package com.videoadmin.ying.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.SysMenu;
import com.videoadmin.ying.po.SysRole;
import com.videoadmin.ying.service.SysRoleMenuService;
import com.videoadmin.ying.service.SysRoleService;
import com.videoadmin.ying.service.SysUserRoleRelService;

@Controller
@RequestMapping("/admin/sysRole")
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired 
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleRelService sysUserRoleRelService;
	
	@RequestMapping("/sysRoleList")
	public Object sysRoleList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取系统角色信息list入参:",paraMap);
		Page<?> page = sysRoleService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取系统角色信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/sysrole/sysRole_list";
	}
	
    @RequestMapping(value = "/toEdit")
    public Object toUpdateRoleInfo(ModelMap modelMap,HttpServletRequest request) {
    	 Map<String, Object> paramMap = new HashMap<String,Object>();
         //1.获取菜单
    	 logger.info("查询该用户下的所有权限入参...{}"+paramMap);
    	 paramMap.put("groupBy", 1);
    	 List<SysMenu> sysMenuList=sysRoleService.selectAllPression(paramMap);
         logger.info("查询该用户下的所有权限出参...{}"+sysMenuList);
         String sysMenus = JSON.toJSONString(sysMenuList, true);  
         //把sysMenus传送到页面
         modelMap.put("sysMenus", sysMenus);
         List list=new ArrayList<>();
         String permissions = JSON.toJSONString(list, true);  
         modelMap.put("permissions", permissions);
         //2.把角色传到修改页面
         String idsString = request.getParameter("id");
         if(DataUtil.isNotEmpty(idsString))
         {
             //得到传入的角色id
             int id = Integer.parseInt(idsString);
             //根据id查询角色 ，把角色传到页面
             logger.info("根据id查询角色入参..."+id);
             SysRole sysRole=sysRoleService.queryById(id);
             logger.info("根据id查询角色出参..."+sysRole);
             //把sysRole传送到页面
             modelMap.put("sysRole", sysRole);
             //根据角色的id 查询角色的权限
             logger.info("根据角色的id查询角色的权限入参..."+id);
             paramMap.clear();
             paramMap.put("roleId", id);
             List<SysMenu> permissionList=sysRoleService.selectAllPression(paramMap);
             logger.info("根据角色的id查询角色的权限出参..."+permissionList);
             permissions = JSON.toJSONString(permissionList, true);  
             modelMap.put("permissions", permissions);
             modelMap.addAttribute("types", "修改");
             
         }else{
        	 modelMap.addAttribute("types", "添加");
         }
        return "/sysrole/sysRole_edit";    
    }
   
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateRoleInfo(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		
		sysRoleService.addOrUpdateRole(param);
	    return setSuccessResponse(modelMap);
	}
	

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
		if(ids!=null&&!"".equals(ids)){
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("roleIds", ids);
			logger.info("查询角色对应的菜单id start...,入参：{}"+param);
			List<Integer> menuIds =sysRoleMenuService.queryRolePermissionsRef(param);
	        logger.info("查询角色对应的菜单id end...,出参：{}"+menuIds);
	        if(DataUtil.isNotEmpty(menuIds))
	        {
	            logger.info("删除物流公司角色权限关联表start...,入参：{}"+menuIds);
	            for (Integer integer : menuIds) {
	            	sysRoleMenuService.delete(integer);
				}
	        }
	        //删除用户与角色的关联信息
	        List<Integer> managerIds = sysUserRoleRelService.queryUserRoleRef(param);
	        if(DataUtil.isNotEmpty(managerIds))
	        {
	        	logger.info("删除角色与物流公司关联表start...,入参：{}"+managerIds);
	        	for (Integer integer : managerIds) {
	        		sysUserRoleRelService.delete(integer);
				}
	        }
	       //删除角色
	        String[] idstr=ids.split(",");
	        for (String string : idstr) {
				sysRoleService.delete(Integer.valueOf(string));
			}
		}
		return setSuccessResponse(modelMap);
	}
	 @RequestMapping(value = "/detail")
	    public Object detail(ModelMap modelMap,HttpServletRequest request) {
		 Map<String, Object> paramMap = new HashMap<String,Object>();
		 //1.获取菜单
    	 logger.info("查询该用户下的所有权限入参...{}"+paramMap);
    	 List<SysMenu> sysMenuList=sysRoleService.selectAllPression(paramMap);
         logger.info("查询该用户下的所有权限出参...{}"+sysMenuList);
         String sysMenus = JSON.toJSONString(sysMenuList, true);  
         //把sysMenus传送到页面
         modelMap.put("sysMenus", sysMenus);
         List list=new ArrayList<>();
         String permissions = JSON.toJSONString(list, true);  
         modelMap.put("permissions", permissions);
	        String idsString = request.getParameter("id");
	        if(DataUtil.isNotEmpty(idsString))
	         {
	             //得到传入的角色id
	             int id = Integer.parseInt(idsString);
	             //根据id查询角色 ，把角色传到页面
	             logger.info("根据id查询角色入参..."+id);
	             SysRole sysRole=sysRoleService.queryById(id);
	             logger.info("根据id查询角色出参..."+sysRole);
	             //把sysRole传送到页面
	             modelMap.put("sysRole", sysRole);
	             //根据角色的id 查询角色的权限
	             logger.info("根据角色的id查询角色的权限入参..."+id);
	             List<SysMenu> permissionList=sysRoleService.selectAllPression(paramMap);
	             logger.info("根据角色的id查询角色的权限出参..."+permissionList);
	             permissions = JSON.toJSONString(permissionList, true);  
	             modelMap.put("permissions", permissions);
	             
	         }
	        return "/sysrole/sysRole_detail";    
	   }
	
    @PostMapping("/validateRoleName")
    @ResponseBody
    public Object validateRoleName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        logger.info("{}查询角色名称是否存在 start...", param);
        Integer count = sysRoleService.validateRoleName(param);
        logger.info("{}查询角色名称是否存在 end.", count);
      
        if (count>0)
        {
            return "false";
        }else {
            return "true";
        }
    }

}