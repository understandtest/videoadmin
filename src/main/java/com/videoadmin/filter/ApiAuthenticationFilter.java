
package com.videoadmin.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import com.videoadmin.api.base.ApiDataResult;
import com.videoadmin.utils.Jwt;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.utils.SysConstants;
import com.videoadmin.utils.WebUtil;

public class ApiAuthenticationFilter  extends AuthenticationFilter
{
    protected Logger log = Logger.getLogger("controller_info");
    
    @Override
    protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        
        String requestUrl = req.getRequestURI();
        log.info("API用户进入校验！" + requestUrl);
        if(requestUrl.contains("/api/")&&!requestUrl.contains("/login")&&!requestUrl.contains("/upload"))
        {
            //获取请求头部的Token
            String token = req.getHeader(SysConstants.AUTHORIZATION); 
            if (token != null && !StringUtils.isEmpty(token)) {
                //验证Token的有效性
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap = Jwt.validToken(token);
                if ((boolean) resultMap.get("isSuccess")) {
                    return true;
                } 
            }
            ApiDataResult dataResult = new ApiDataResult();
            dataResult.setRetCode(-1);
            dataResult.setRetMsg("token认证失效!");
            HttpServletResponse resp = (HttpServletResponse) response;
            WebUtil.apiObjectToJsonUtil(dataResult, resp);
            return false;
        }
        return true;
    }
    
}
