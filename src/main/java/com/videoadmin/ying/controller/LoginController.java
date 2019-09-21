package com.videoadmin.ying.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.videoadmin.base.BaseController;
import com.videoadmin.exception.LoginException;
import com.videoadmin.utils.LoginHelper;
import com.videoadmin.utils.MD5Util;
import com.videoadmin.utils.Resources;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.utils.SysConstants;
import com.videoadmin.utils.WebUtil;
import com.videoadmin.ying.dto.LoginInDto;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysMenu;
import com.videoadmin.ying.service.SysLoginService;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Autowired
    private SysLoginService sysLoginService;
   
    @RequestMapping(value="/login")
    public String indexLogin() throws Exception
    {
        return "login";
    }
    
	
	/**
	 * 
	 * Description: 检验验证码<br>
	 * Implement: <br>
	 *
	 * @param vericode
	 * @param request
	 * @return 
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="/checkVericode",method=RequestMethod.POST)
	public String checkVericode(@RequestParam(required=false) String vericode,HttpServletRequest request){
	    String sysVericode = request.getSession().getAttribute(SysConstants.VERICODE_KEY).toString();
	    if(vericode.equalsIgnoreCase(sysVericode.toLowerCase())){
	        return "true";
	    }else{
	        return "false";
	    }
	}
	
	
	@PostMapping(value = "/manager/login")
	@ResponseBody
	public Object login(@ModelAttribute  LoginInDto loginUser, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
        if (loginUser != null && !StringUtils.isEmpty(loginUser.getUsername())&& !StringUtils.isEmpty(loginUser.getPass()))
        {
        	String password = MD5Util.md5(loginUser.getPass(), SysConstants.MD5_SALT);
        	if (LoginHelper.login(loginUser.getUsername(), password)) {
        	      logger.info("登录成功:");
        		  request.setAttribute("msg", "[" + loginUser.getUsername() + "]登录成功.");
                  modelMap.put("account", loginUser.getUsername());
                  SysLogin sysLogin = new SysLogin();
                  Map<String, Object> params = new HashMap<String, Object>();
                  params.put("loginName", loginUser.getUsername());
                  sysLogin = sysLoginService.getSysLoginByLogin(params);
                  request.getSession(true).setAttribute(SysConstants.USER_ACTIVITY_INFO, sysLogin);
                  logger.info("将个人信息存储到 session中");
                  return setSuccessResponse(modelMap);
        	  }
        }
        request.setAttribute("msg", "[" + loginUser.getUsername() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
	}
	
	
	@RequestMapping("/manager/index")
    public String index(ModelMap modelMap,HttpServletRequest request)
    {
	    logger.info("进入首页:");
	    SysLogin sysLogin = (SysLogin)request.getSession(false).getAttribute(SysConstants.USER_ACTIVITY_INFO);
	    logger.info("进入首页:");
	    if(sysLogin != null && !"".equals(sysLogin))
	    {
	        logger.info("登陆姓名:"+sysLogin.getSysName());
	        modelMap.put("loginName", sysLogin.getSysName());
	        request.getSession(true).setAttribute(SysConstants.CURRENT_USER, sysLogin);
	        List<SysMenu> menus = new ArrayList<SysMenu>();
	        menus = sysLoginService.getMenusByUserId(sysLogin.getId());
	        //modelMap.put("menus", menus);
	        WebUtil.setSession("menus", menus);
	        return "index";
	    }
	    return null;
    }
	
	
	/**
     * 
     * Description: 用户登出<br>
     * Implement: <br>
     *
     * @param modelMap
     * @param ra
     * @return 
     * @see
     */
    @GetMapping("/logout")
    public String logout(ModelMap modelMap,RedirectAttributes ra,HttpServletRequest request) {
    	Subject currentUser = SecurityUtils.getSubject();
        if (SecurityUtils.getSubject().getSession() != null)
        {
            currentUser.logout();
        }
        return "redirect:/admin/login";
    }

}
