

package com.videoadmin.realm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.WebUtil;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysSession;
import com.videoadmin.ying.service.SysLoginService;
import com.videoadmin.ying.service.SysSessionService;


public class MyRealm  extends AuthorizingRealm
{
	private final Logger logger = LogManager.getLogger();
	
    @Autowired
    private SysLoginService sysLoginService;
    
    @Autowired
	private RedisOperationsSessionRepository sessionRepository;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    /**
     * 用于的权限的认证。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysLogin yingAccount = WebUtil.getCurrentUser();
        List<Map<String, Object>> list = (List<Map<String, Object>>)sysLoginService.getCurrentUserPermission(yingAccount);
        logger.info("权限个数：{}", list.size());
        for (Map<String, Object> map : list) {
            info.addStringPermission(map.get("permission").toString());
        }
        return info;
    }

    /**
     * 首先执行这个登录验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
    	
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
    	Map<String, Object> params = new HashMap<String, Object>();
 		params.put("loginName", token.getUsername());
 		SysLogin account  = new SysLogin();
 		account = sysLoginService.getSysLoginByLogin(params);
 		if (account!=null) {
 			StringBuilder sb = new StringBuilder(100);
			for (int i = 0; i < token.getPassword().length; i++) {
				sb.append(token.getPassword()[i]);
			}
			if (account.getSysPwd().equals(sb.toString())) {
				saveSession(account.getSysName(),token.getHost());
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(account.getSysName(), account.getSysPwd(),
						account.getSysName());
				return authcInfo;
			}
			logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), sb.toString());
			return null;
 		}
 		else {
			logger.warn("No user: {}", token.getUsername());
			return null;
		}
    }
    
//    /** 保存session */
	private void saveSession(String account,String host) {
		// 踢出用户
		SysSession record = new SysSession();
		record.setAccount(account);
		List<?> sessionIds = sysSessionService.querySessionIdByAccount(record);
		Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setTimeout(-1000l);
        String currentSessionId= session.getId().toString();
		if (sessionIds != null) {
			for (Object sessionId : sessionIds) {
				record.setSessionId((String) sessionId);
				sysSessionService.deleteBySessionId(record);
				if (!currentSessionId.equals(sessionId)) {
				    sessionRepository.delete((String) sessionId);
	                sessionRepository.cleanupExpiredSessions();
				}
			}
		}
		// 保存用户
		record.setSessionId(currentSessionId);
		record.setIp(DataUtil.isBlank(host) ? session.getHost() : host);
		record.setStartTime(session.getStartTimestamp());
		sysSessionService.update(record);
	}
}
