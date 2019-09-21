package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.ScheduleJobLogMapper;
import com.videoadmin.ying.po.ScheduleJobLog;

/**
 * <p>
 * 定时任务日志  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogService extends BaseService<ScheduleJobLog> {
	
	public int queryTotal(Map<String, Object> map) {
		return ((ScheduleJobLogMapper)mapper).queryTotal(map);
	}
	
	public List<ScheduleJobLog> queryList(Map<String, Object> map) {
		return ((ScheduleJobLogMapper)mapper).queryList(map);
	}
}