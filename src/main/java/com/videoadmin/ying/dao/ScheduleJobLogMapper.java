package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.ScheduleJobLog;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLog> {
	
	public List<ScheduleJobLog> queryList( Map<String, Object> paramMap);
	
	public int queryTotal(Map<String, Object> map);
	
}