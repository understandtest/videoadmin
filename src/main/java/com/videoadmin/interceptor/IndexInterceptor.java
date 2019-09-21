
package com.videoadmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.videoadmin.utils.StringUtils;
import com.videoadmin.utils.WebUtil;

public class IndexInterceptor implements HandlerInterceptor
{
	
	private static Logger logger = LoggerFactory.getLogger(IndexInterceptor.class);
    
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
    	
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
    	 String res = request.getParameter("res");
         if (StringUtils.isNotEmpty(res)) {
             String[] strs = res.split("_");
             //WebUtil.setSession("res",Integer.valueOf(strs[0]));
             //request.getSession(true).setAttribute("res",Integer.valueOf(strs[0]));
             WebUtil.setSession(request,"res", Integer.valueOf(strs[0]));
             System.out.println("---------1----------------"+Integer.valueOf(strs[0]));
             if(strs.length>1){
                 //WebUtil.setSession("active",Integer.valueOf(strs[1]));
                 //request.getSession(true).setAttribute("active",Integer.valueOf(strs[1]));
                 WebUtil.setSession(request,"active", Integer.valueOf(strs[1]));
                 System.out.println("---------2----------------"+Integer.valueOf(strs[1]));
             }
         }
        return true;
    }
}
