package com.videoadmin.ying.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TRechargeCode;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
public interface TRechargeCodeMapper extends BaseMapper<TRechargeCode> {

	public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
	
	public List<LinkedHashMap<String, Object>> selectListExport(@Param("cm") Map<String, Object> paramMap);
}