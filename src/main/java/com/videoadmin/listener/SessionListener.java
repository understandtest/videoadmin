package com.videoadmin.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoadmin.utils.SysConstants;
import com.videoadmin.utils.redis.CacheUtil;

/**
 * 会话监听器
 * 
 */
public class SessionListener implements HttpSessionListener {
	private Logger logger = LogManager.getLogger(SessionListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		session.setAttribute(SysConstants.WEBTHEME, "default");
		logger.info("创建了一个Session连接:[" + session.getId() + "]");
		setAllUserNumber(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if (getAllUserNumber() > 0) {
			logger.info("销毁了一个Session连接:[" + session.getId() + "]");
		}
		session.removeAttribute(SysConstants.CURRENT_USER);
		setAllUserNumber(-1);
	}

	private void setAllUserNumber(int n) {
	    String key = SysConstants.CACHE_NAMESPACE + "SESSION:LOCK";
	    while (!CacheUtil.getLock(key)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
	    try {
    		Integer number = getAllUserNumber() + n;
    		if (number >= 0) {
    			logger.info("用户数：" + number);
    			//CacheUtil.getCache().set(Constants.ALLUSER_NUMBER, number, 60 * 60 * 24);
    			CacheUtil.getCache().set(SysConstants.ALLUSER_NUMBER, number);
    		}
	    
        } finally {
            CacheUtil.unlock(key);
        }
	}

	/** 获取在线用户数量 */
	public static Integer getAllUserNumber() {
		Integer v = (Integer) CacheUtil.getCache().get(SysConstants.ALLUSER_NUMBER);
		if (v != null) {
			return v;
		}
		return 0;
	}
}
