package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.videoadmin.ying.po.TMemberLogin;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TMemberLoginMapper extends BaseMapper<TMemberLogin> {
	public Integer getMemberLoginId(@Param("cm") Map<String, Object> paramMap);
	public List<Map<String, Object>> queryFromCodeList(@Param("cm") Map<String, Object> paramMap);
}