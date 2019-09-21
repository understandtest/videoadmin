package com.videoadmin.ying.dao;

import java.util.Map;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TRate;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
public interface TRateMapper extends BaseMapper<TRate> {

	
	public Map<String,Object> selectRateInfo();
}