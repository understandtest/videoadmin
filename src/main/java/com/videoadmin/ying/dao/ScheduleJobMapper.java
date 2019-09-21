package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.ScheduleJob;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {

	
	public List<ScheduleJob> queryList( Map<String, Object> paramMap);
	
	public int queryTotal(Map<String, Object> map);
	
	public int updateBatch(Map<String, Object> map);
	
	public void deleteBatch(Integer[] jobIds);
	
	public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
}