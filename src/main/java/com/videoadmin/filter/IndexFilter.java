
package com.videoadmin.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.videoadmin.utils.StringUtils;
import com.videoadmin.utils.ThreadLocalUtil;
import com.videoadmin.utils.WebUtil;

public class IndexFilter extends AccessControlFilter{

	private static Logger logger = LoggerFactory.getLogger(IndexFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        logger.info("----------过滤路径------------:"+path);
     	
        
        return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String basePath = ThreadLocalUtil.getBasePath();
		httpResponse.sendRedirect(basePath+"/admin/login");
		return false;
	}

	
	  @Override
	  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
	      //将菜单的展开节点传递
	      HttpServletRequest req = (HttpServletRequest) request;
	      String res = request.getParameter("res");
	      String path = req.getServletPath();
	      System.out.println("路径-----"+path);
	      if(path.contains("/admin"))
	      {
	    	  Subject subject = getSubject(request, response);
		      if (!subject.isAuthenticated()) {
		          // 没有登录
		    	  System.out.println("没有登录-----"+path);
		    	  HttpServletResponse httpResponse = (HttpServletResponse) response;
		          String basePath = ThreadLocalUtil.getBasePath();
		  		  httpResponse.sendRedirect(basePath+"/admin/login");
		          return false;
		      }
	    	  System.out.println("res-----"+res);
		      if (StringUtils.isNotEmpty(res)) {
		            String[] strs = res.split("_");
		            WebUtil.setSession("res",Integer.valueOf(strs[0]));
		            if(strs.length>1){
		                WebUtil.setSession("active",Integer.valueOf(strs[1]));
		            }
		      }
		      System.out.println("获取session值："+ req.getSession().getAttribute("res"));
	      }
	      return true;
	  }

}
