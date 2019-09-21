package com.videoadmin.ying.dao;

import java.util.List;

import com.videoadmin.ying.po.SysSession;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
public interface SysSessionMapper extends BaseMapper<SysSession> {

	 void deleteBySessionId(String sessionId);

	 Integer queryBySessionId(String sessionId);

	 List<String> querySessionIdByAccount(String account);
}