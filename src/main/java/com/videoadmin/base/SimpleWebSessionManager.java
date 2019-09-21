
package com.videoadmin.base;

import java.util.Collection;
import java.util.Iterator;

import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.videoadmin.utils.SysConstants;



public class SimpleWebSessionManager extends DefaultWebSessionManager implements WebSessionManager 
{
    @Autowired
    private CacheManager shiroCacheManager;

    private final static Logger logger = Logger
            .getLogger(SimpleWebSessionManager.class);

    public SimpleWebSessionManager() {
        super();
    }

    public void validateSessions() {
        if (logger.isInfoEnabled())
            logger.info("验证所有存活的Session");
        int invalidCount = 0;
        Collection<?> activeSessions = getActiveSessions();
        if (activeSessions != null && !activeSessions.isEmpty()) {
            for (Iterator<?> i$ = activeSessions.iterator(); i$.hasNext();) {
                Session session = (Session) i$.next();
                try {
                    SessionKey key = new DefaultSessionKey(session.getId());
                    validate(session, key);
                } catch (InvalidSessionException e) {
                    if (shiroCacheManager != null) {
                        SimpleSession s = (SimpleSession) session;
                        if (s.getAttribute(SysConstants.CURRENT_USER) != null)
                            shiroCacheManager.getCache(null).remove(s.getAttribute(SysConstants.CURRENT_USER));
                    }
                    if (logger.isDebugEnabled()) {
                        boolean expired = e instanceof ExpiredSessionException;
                        String msg = (new StringBuilder()).append(
                                "验证 session id [").append(
                                session.getId()).append("]").append(
                                expired ? " (expired)" : " (stopped)")
                                .toString();
                        logger.debug(msg);
                    }
                    invalidCount++;
                }
            }

        }
        if (logger.isInfoEnabled()) {
            String msg = "session 验证结束";
            if (invalidCount > 0)
                msg = (new StringBuilder()).append(msg).append("  [").append(
                        invalidCount).append("] session 验证停止.")
                        .toString();
            else
                msg = (new StringBuilder()).append(msg).append(
                        "  没有session被停止.").toString();
            logger.info(msg);
        }
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.shiroCacheManager = cacheManager;
    }
}
