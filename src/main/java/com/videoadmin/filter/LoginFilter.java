
package com.videoadmin.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;
import com.videoadmin.base.BaseResult;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.utils.ThreadLocalUtil;

public class LoginFilter extends AccessControlFilter
{
    private static final Logger logger = Logger.getLogger(LoginFilter.class);
    
    private String noLoginUrl;

    public String getNoLoginUrl() {
        return noLoginUrl;
    }

    public void setNoLoginUrl(String noLoginUrl) {
        this.noLoginUrl = noLoginUrl;
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        logger.info("验证是否具有访问权限");
        if (subject.isAuthenticated()) {
            logger.info("用户已登陆，允许访问!");
            
            /**
             * 资源和当前选中菜单
             */
//            String res = request.getParameter("res");
//            if (!StringUtils.isEmpty(res)) {
//                request.setAttribute("res", Integer.valueOf(res));
//            }
/*            *//**
             * 获取当前用户的菜单
             *//*
            ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            List<Map<String, Object>> treeMenus =ac2.getBean(IMenuService.class).selectShowMenus(null);
            request.setAttribute("treeMenus", treeMenus);
            
            *//**
             * 获取当前用户的权限
             *//*
            String username = subject.getPrincipal().toString();
            Set<String> permissions =  ac2.getBean(IAccountService.class).findPermissions(username) ;
            request.setAttribute("permissions", permissions);
            
            *//**
             * 获取当前时间
             * 
             *//*
            Date date = new Date();
            long dateTime = date.getTime();
            request.setAttribute("dateTime", dateTime);*/
            
            /**
             * 获取个人信息
             */
//            HttpServletRequest req = (HttpServletRequest)request;
//            LoginUserOutDto outDto = new LoginUserOutDto();
//            outDto = (LoginUserOutDto)req.getSession(true).getAttribute(SysConstants.USER_ACTIVITY_INFO);
//            if(outDto==null)
//            {
//                return false;
//            }
//            request.setAttribute("outDto",outDto);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String basePath = ThreadLocalUtil.getBasePath();
        boolean isAjax = StringUtils.isAjax(httpRequest);
        if (isAjax) {
            PrintWriter writer = httpResponse.getWriter();
            BaseResult baseResult = new BaseResult();
            baseResult.setResultStatus(false);
            baseResult.setMsg("请登录");
            baseResult.setUrl(basePath + getNoLoginUrl());
            writer.print(JSON.toJSONString(baseResult));
            return false;
        }
        httpResponse.sendRedirect(basePath + getNoLoginUrl());
        return false;
    }


}
