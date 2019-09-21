package com.videoadmin.ying.controller;

import java.util.Date;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.MD5Util;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.SysConstants;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysUserRoleRel;
import com.videoadmin.ying.service.SysLoginService;
import com.videoadmin.ying.service.SysUserRoleRelService;


@Controller
@RequestMapping("/admin/sysUser")
public class SysUserController extends BaseController{
	
	@Autowired
	private SysLoginService sysLoginService;
	
	@Autowired 
	private SysUserRoleRelService sysUserRoleRelService;
	@RequestMapping("/sysUserList")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取系统用户信息list入参:",paraMap);
		Page<?> page = sysLoginService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取系统用户信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/sysuser/sysUser_list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询客户管理列表 start...{}", param);
        	    Map<String, Object> map=sysLoginService.getUserInfo(param);
        	    logger.info("{}查询客户管理列表 end...{}", map);
        	    modelMap.addAttribute("sysUser", map);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        	return "/sysuser/sysUser_edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("id", id);
        	 Map<String, Object> map=sysLoginService.getUserInfo(param);
        	modelMap.addAttribute("sysUser",map);
        }
        return "/sysuser/sysUser_detail";    
    }

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
		logger.info("删除用户数据，删除主键为：" + ids);
		String[] str = ids.split(",");
		if (str != null && !"".equals(str) && str.length > 0) {
			for (String ss : str) {
				if (ss != null && !"".equals(ss)) {
					// 获取选中的单个用户ID
					Integer id = Integer.valueOf(ss);
					SysLogin sysLogin=new SysLogin();
					sysLogin.setId(id);
					sysLogin=sysLoginService.selectOne(sysLogin);
					if(DataUtil.isNotEmpty(sysLogin)&&!sysLogin.getSysName().equals("admin")){
						Integer userId=sysLogin.getId();
						 logger.info("删除用户登录表信息execute delete ...{} ",userId );
	            		sysLoginService.delete(userId);
	            		SysUserRoleRel sysUserRoleRel=new SysUserRoleRel();
	            		sysUserRoleRel.setUserId(userId);
	            		sysUserRoleRel=sysUserRoleRelService.selectOne(sysUserRoleRel);
	            		if(DataUtil.isNotEmpty(sysUserRoleRel)){
	            			sysUserRoleRelService.delete(sysUserRoleRel.getId());
	            		}
	            		
	            	}
				}
			}
		}
		
			return setSuccessResponse(modelMap);  
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateUser(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	    
	    if (param!=null)
        {
	      //更新当前表
	        SysLogin login =new SysLogin();
	        login.setIsEnable(1);
	        if (param.get("nickName")!=null&& !("").equals(param.get("sysName").toString()))
	        {
	        	login.setNickName(param.get("nickName").toString());
	        }
	        if (param.get("sysName")!=null&& !("").equals(param.get("sysName").toString()))
	        {
	        	login.setSysName(param.get("sysName").toString());
	        }
	        if (param.get("sysPwd")!=null&& !("").equals(param.get("sysName").toString()))
	        {
	        	login.setSysPwd(MD5Util.md5(param.get("sysPwd").toString(), SysConstants.MD5_SALT));
	        }
	     
	        if(null != param.get("id") && !("").equals(param.get("id").toString())){
				//更新
	        	login.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	login.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				login.setCreateTime(new Date());
			}
            
	        //更新用户表
            logger.info("{}更新客户表 start...{}", login);
            sysLoginService.update(login);
            
            //更新用户角色表
            SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
			//Role ID
			if(null != param.get("roleId") && !("").equals(param.get("roleId").toString())){
				sysUserRoleRel.setRoleId(Integer.valueOf(param.get("roleId").toString()));
			}
			//UR ID
			if(null != param.get("relID") && !("").equals(param.get("relID").toString())){
				//更新
				sysUserRoleRel.setId(Integer.valueOf(param.get("relID").toString()));
				//sysUserRoleRel.setUpdateBy(getCurrUser().getUserId());
				sysUserRoleRel.setUpdateTime(new Date());
				
			}else{
				//添加
				//sysUserRoleRel.setCreateBy(userId);
				sysUserRoleRel.setCreateTime(new Date());
	        	if(login!=null){
	        		sysUserRoleRel.setUserId(login.getId());
	            }
			}
			sysUserRoleRelService.update(sysUserRoleRel);
	        return setSuccessResponse(modelMap,login);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateUsername")
    @ResponseBody
    public Object validateUsername(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证用户名是否存在入参:",param);
    	Integer id = sysLoginService.validateUsername(param);
		logger.info("{}验证用户名是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
    
    @RequestMapping(value = "/updatePasswordUser")
   	public String updateUserModel(ModelMap modelMap,HttpServletRequest request) {
       	 return "/sysuser/change_pass";
       }
       
       
    @RequestMapping(value = "/password")
   	@ResponseBody
   	public Object updateUser(ModelMap modelMap,HttpServletRequest request,@RequestParam String oldPassword ,@RequestParam String password) {
       	 SysLogin sysLogin = new SysLogin();
       	 sysLogin = (SysLogin) request.getSession(true).getAttribute(SysConstants.USER_ACTIVITY_INFO);
       	 String flag ="false";
       	 if(sysLogin != null && !"".equals(sysLogin))
       	 {
       		 String oldPwd = sysLogin.getSysPwd();
       		 String oldPasswordInto = MD5Util.md5(oldPassword, SysConstants.MD5_SALT);
       		 if(oldPasswordInto.equals(oldPwd))
       		 {
       			 String newPwd = MD5Util.md5(password, SysConstants.MD5_SALT);
       			 sysLogin.setSysPwd(newPwd);
       			 sysLoginService.update(sysLogin);
       			 flag="true";
       		 }
       	 }
       	 modelMap.put("flag", flag);
       	 return setSuccessResponse(modelMap);
       }
}