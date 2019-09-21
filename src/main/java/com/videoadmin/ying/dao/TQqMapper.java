package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.videoadmin.ying.po.TQq;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TQqMapper extends BaseMapper<TQq> {
	public List<Map<String, Object>> selectListPage(@Param("cm") Map<String, Object> paramMap);
}