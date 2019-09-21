package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.ying.po.TVipHistory;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TVipHistoryMapper extends BaseMapper<TVipHistory> {
    public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
	
	public Map<String, Object> getVipHistoryInfo(@Param("cm") Map<String, Object> paramMap);
}